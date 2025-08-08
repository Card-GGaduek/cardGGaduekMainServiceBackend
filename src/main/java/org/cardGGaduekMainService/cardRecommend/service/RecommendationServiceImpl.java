package org.cardGGaduekMainService.cardRecommend.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.cardRecommend.dto.*;
import org.cardGGaduekMainService.cardRecommend.mapper.CardMapper;
import org.cardGGaduekMainService.cardRecommend.mapper.CardProductMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    //private static final int MAX_PICK = 3;

    private final CardMapper cardMapper;
    private final CardProductMapper cardProductMapper;
    private final CardBenefitCalculator cardBenefitCalculator;
    private final SpendPredictionService spendPredictionService;

    @Override
    public CardRecommendDTO build(Long memberId) {

        // 0) 예측 지출 불러오기 (가맹점별 금액 + optional "total")
        Map<String, Integer> spend = spendPredictionService.predict(memberId);

        int totalPredicted = Optional.ofNullable(spend.get("total"))
                .orElseGet(() -> spend.entrySet().stream()
                        .filter(e -> !"total".equals(e.getKey()))
                        .mapToInt(Map.Entry::getValue)
                        .sum());

        // 1) 보유 카드 불러오기
        List<CardVO> ownedCards = cardMapper.findValidByMember(memberId);
        Set<Long> ownedIds = ownedCards.stream()
                .map(CardVO::getCardProductId)
                .collect(toSet());

        // 2) 보유 카드의 혜택 계산
        List<CardBenefitDTO> ownedDtos = ownedCards.stream()
                .map(c -> cardBenefitCalculator.calc(
                        cardProductMapper.findById(c.getCardProductId()),
                        spend,
                        true))
                .toList();

        CardCombinationDTO current = CardCombinationDTO.builder()
                .cards(ownedDtos)
                .aggregateBenefit(ownedDtos.stream().mapToInt(CardBenefitDTO::getTotalBenefit).sum())
                .build();

        // 3) 후보 풀 계산 (보유 포함)
        List<CardBenefitDTO> pool = cardProductMapper.findAllActive().stream()
                .map(p -> cardBenefitCalculator.calc(p, spend, ownedIds.contains(p.getId())))
                .toList();

        // 4) "조합 최소실적 합 ≤ total" 제약을 반영한 그리디 Top-K
        List<CardBenefitDTO> best = greedyWithTotalConstraint(pool, totalPredicted,3 );
        int bestAgg = best.stream().mapToInt(CardBenefitDTO::getTotalBenefit).sum();

        return CardRecommendDTO.builder()
                .memberId(memberId)
                .current(current)
                .recommendation(CardCombinationDTO.builder()
                        .cards(best)
                        .aggregateBenefit(bestAgg)
                        .build())
                .additionalSaving(bestAgg - current.getAggregateBenefit())
                .predictionModel("XGBoost")
                .build();
    }

    /**
     * total(예측 총지출) 제약을 만족하면서, 미커버 가맹점 혜택 증가분이 큰 카드부터 최대 K장 선택.
     * - 조합의 최소실적 합 ≤ total 을 보장
     * - 각 후보는 개별 최소실적 충족 가능(meetsRequiredSpend)해야 함
     */
    private List<CardBenefitDTO> greedyWithTotalConstraint(List<CardBenefitDTO> pool,
                                                           int totalPredicted,
                                                           int k) {

        // 같은 카드 중복 방지 & 가맹점 중복 커버 방지
        Set<Long> pickedIds = new HashSet<>();
        Set<String> coveredStores = new HashSet<>();

        // 남은 '실적 예산' (조합 최소실적 합의 상한)
        int remainingRequiredBudget = totalPredicted;

        // 선택 결과
        List<CardBenefitDTO> picks = new ArrayList<>(k);

        // 카드별 정렬/중복 관리를 위해 id 기준으로 pool을 map화(선택사항)
        Map<Long, CardBenefitDTO> byId = pool.stream()
                .collect(Collectors.toMap(CardBenefitDTO::getCardProductId, c -> c, (a, b) -> a));

        while (picks.size() < k) {
            CardBenefitDTO best = null;
            int bestGain = -1;

            for (CardBenefitDTO cand : byId.values()) {
                Long id = cand.getCardProductId();
                if (pickedIds.contains(id)) continue;

                // 개별 카드가 자체 최소실적 충족 가능한지(예측 total 기준)
                if (!cand.isMeetsRequiredSpend()) continue;

                // 이 카드의 최소실적
                int req = Math.max(0, cand.getRequiredMonthlySpent());

                // 남은 예산으로 실적을 못 채우면 스킵
                if (req > remainingRequiredBudget) continue;

                // 커버되지 않은 가맹점에 대해 증가혜택 계산
                int gain = cand.getBenefitByStore().map().entrySet().stream()
                        .filter(e -> !"total".equals(e.getKey()))
                        .filter(e -> !coveredStores.contains(e.getKey()))
                        .mapToInt(Map.Entry::getValue)
                        .sum();

                if (gain > bestGain) {
                    bestGain = gain;
                    best = cand;
                }
            }

            if (best == null) break;

            picks.add(best);
            pickedIds.add(best.getCardProductId());
            remainingRequiredBudget -= Math.max(0, best.getRequiredMonthlySpent());
            coveredStores.addAll(best.getBenefitByStore().map().keySet());
        }

        return picks;
    }
}
