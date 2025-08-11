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

        // (변경) 가맹점별 중복 없이 "최대 혜택만" 합산
        CardCombinationDTO current = CardCombinationDTO.builder()
                .cards(ownedDtos)
                .aggregateBenefit(aggregateUniqueBenefit(ownedDtos))
                .build();

        // 3) 후보 풀 계산 (보유 포함)
        List<CardBenefitDTO> pool = cardProductMapper.findAllActive().stream()
                .map(p -> cardBenefitCalculator.calc(p, spend, ownedIds.contains(p.getId())))
                .toList();

        // 4) 3장 제약 + '증분 혜택' 기준 그리디 (중복 매장 업그레이드 반영)
        List<CardBenefitDTO> best = greedyTopKWithMarginalGain(pool, totalPredicted, 3);

        // (변경) 추천 조합 총합도 가맹점별 최대 혜택만 합산
        int bestAgg = aggregateUniqueBenefit(best);

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
     * 3장 제약 + '증분 혜택' 기반 그리디:
     * - 개별 실적 가능(cand.isMeetsRequiredSpend())
     * - 조합 실적 합 예산 내(req <= remainingRequiredBudget)
     * - marginalGain = Σ max(0, cand[store] - currentBestByStore[store])
     * - 선택 후 currentBestByStore를 매장별 max로 업데이트
     */
    private List<CardBenefitDTO> greedyTopKWithMarginalGain(List<CardBenefitDTO> pool,
                                                            int totalPredicted,
                                                            int k) {
        Set<Long> pickedIds = new HashSet<>();
        int remainingRequiredBudget = totalPredicted;
        List<CardBenefitDTO> picks = new ArrayList<>(k);

        // 매장별 현재까지 확보한 최대 혜택 (중복 매장 업그레이드 반영)
        Map<String, Integer> currentBestByStore = new HashMap<>();

        Map<Long, CardBenefitDTO> byId = pool.stream()
                .collect(Collectors.toMap(CardBenefitDTO::getCardProductId, c -> c, (a, b) -> a));

        while (picks.size() < k) {
            CardBenefitDTO best = null;
            int bestGain = -1;

            for (CardBenefitDTO cand : byId.values()) {
                if (pickedIds.contains(cand.getCardProductId())) continue;
                if (!cand.isMeetsRequiredSpend()) continue;

                int req = Math.max(0, cand.getRequiredMonthlySpent());
                if (req > remainingRequiredBudget) continue;

                // 현재 최대 혜택 대비 '증분'만 합산
                int gain = 0;
                if (cand.getBenefitByStore() != null && cand.getBenefitByStore().map() != null) {
                    for (var e : cand.getBenefitByStore().map().entrySet()) {
                        String store = e.getKey();
                        if ("total".equals(store)) continue;
                        int candVal = e.getValue() == null ? 0 : e.getValue();
                        int curVal  = currentBestByStore.getOrDefault(store, 0);
                        if (candVal > curVal) gain += (candVal - curVal);
                    }
                }

                if (gain > bestGain) {
                    bestGain = gain;
                    best = cand;
                } else if (gain == bestGain && best != null) {
                    // 타이브레이커: 보유 → req 낮음 → totalBenefit 큼
                    boolean candOwned = cand.isOwned();
                    boolean bestOwned = best.isOwned();
                    int candReq = Math.max(0, cand.getRequiredMonthlySpent());
                    int bestReq = Math.max(0, best.getRequiredMonthlySpent());
                    int candTot = cand.getTotalBenefit();
                    int bestTot = best.getTotalBenefit();

                    if ((candOwned && !bestOwned)
                            || (candOwned == bestOwned && candReq < bestReq)
                            || (candOwned == bestOwned && candReq == bestReq && candTot > bestTot)) {
                        best = cand;
                    }
                }
            }

            // 더 고를 카드가 없거나 증가 혜택이 0 이하면 종료
            if (best == null || bestGain <= 0) break;

            picks.add(best);
            pickedIds.add(best.getCardProductId());
            remainingRequiredBudget -= Math.max(0, best.getRequiredMonthlySpent());

            // 선택 카드 반영: 매장 최대 혜택 업데이트
            if (best.getBenefitByStore() != null && best.getBenefitByStore().map() != null) {
                for (var e : best.getBenefitByStore().map().entrySet()) {
                    String store = e.getKey();
                    if ("total".equals(store)) continue;
                    int val = e.getValue() == null ? 0 : e.getValue();
                    currentBestByStore.merge(store, val, Math::max);
                }
            }
        }
        return picks;
    }

    /**
     * 여러 카드의 혜택 맵을 합치되,
     * 같은 가맹점은 '한 번만', 그리고 '최대 혜택'만 반영해서 총합을 구함.
     */
    private int aggregateUniqueBenefit(List<CardBenefitDTO> cards) {
        Map<String, Integer> storeMax = new HashMap<>();
        for (CardBenefitDTO c : cards) {
            if (c.getBenefitByStore() == null || c.getBenefitByStore().map() == null) continue;
            for (Map.Entry<String, Integer> e : c.getBenefitByStore().map().entrySet()) {
                String store = e.getKey();
                if ("total".equals(store)) continue;
                int val = e.getValue() == null ? 0 : e.getValue();
                storeMax.merge(store, val, Math::max);
            }
        }
        return storeMax.values().stream().mapToInt(Integer::intValue).sum();
    }
}
