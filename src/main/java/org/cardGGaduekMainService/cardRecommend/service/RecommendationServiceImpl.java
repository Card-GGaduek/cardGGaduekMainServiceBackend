package org.cardGGaduekMainService.cardRecommend.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.cardRecommend.dto.*;
import org.cardGGaduekMainService.cardRecommend.mapper.*;
import org.springframework.stereotype.Service;
import java.util.*;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final CardMapper cardMapper;
    private final CardProductMapper productMapper;
    private final CardBenefitCalculator calc;
    private final SpendPredictionService spendSvc;

    @Override
    public RecommendDTO build(Long memberId) {

        Map<String,Integer> spend = spendSvc.predict(memberId);

        /* 1. 보유 카드 */
        List<CardVO> ownedCards = cardMapper.findValidByMember(memberId);
        Set<Long> ownedIds = ownedCards.stream()
                .map(CardVO::getCardProductId)
                .collect(toSet());

        List<CardBenefitDTO> ownedDtos = ownedCards.stream().map(c ->
                calc.calc(productMapper.findById(c.getCardProductId()), spend, true)
        ).toList();

        CombinationDTO current = CombinationDTO.builder()
                .cards(ownedDtos)
                .aggregateBenefit(
                        ownedDtos.stream().mapToInt(CardBenefitDTO::getTotalBenefit).sum())
                .build();

        /* 2. 후보 풀 (보유 포함) */
        List<CardBenefitDTO> pool = productMapper.findAllActive().stream()
                .map(p -> calc.calc(p, spend, ownedIds.contains(p.getId())))
                .toList();

        List<CardBenefitDTO> best = greedy(pool);
        int bestAgg = best.stream().mapToInt(CardBenefitDTO::getTotalBenefit).sum();

        return RecommendDTO.builder()
                .memberId(memberId)
                .current(current)
                .recommendation(
                        CombinationDTO.builder().cards(best).aggregateBenefit(bestAgg).build())
                .additionalSaving(bestAgg - current.getAggregateBenefit())
                .predictionModel("RF")
                .build();
    }

    /* Greedy Top-3 */
    private List<CardBenefitDTO> greedy(List<CardBenefitDTO> pool) {

        Set<String> covered = new HashSet<>();
        List<CardBenefitDTO> picks = new ArrayList<>();

        while (picks.size() < 3) {
            CardBenefitDTO best = null; int bestGain = -1;

            for (CardBenefitDTO cand : pool) {
                if (picks.contains(cand) || !cand.isMeetsRequiredSpend()) continue;

                int gain = cand.getBenefitByStore().map().entrySet().stream()
                        .filter(e -> !covered.contains(e.getKey()))
                        .mapToInt(Map.Entry::getValue).sum();

                if (gain > bestGain) { bestGain = gain; best = cand; }
            }
            if (best == null) break;
            picks.add(best);
            covered.addAll(best.getBenefitByStore().map().keySet());
        }
        return picks;
    }
}
