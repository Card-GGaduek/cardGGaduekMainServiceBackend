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
    private final CardProductMapper cardProductMapper;
    private final CardBenefitCalculator calc;
    private final SpendPredictionService spendpredictionService;

    @Override
    public CardRecommendDTO build(Long memberId) {

        // 예측된 지출 정보 가져오기
        Map<String,Integer> spend = spendpredictionService.predict(memberId);

        /* 1. 보유 카드 */
        List<CardVO> ownedCards = cardMapper.findValidByMember(memberId);
        Set<Long> ownedIds = ownedCards.stream()
                .map(CardVO::getCardProductId)
                .collect(toSet());

        List<CardBenefitDTO> ownedCardDTO = ownedCards.stream().map(c ->
                calc.calc(cardProductMapper.findById(c.getCardProductId()), spend, true)
        ).toList();

        CardCombinationDTO currentCard = CardCombinationDTO.builder()
                .cards(ownedCardDTO)
                .aggregateBenefit(
                        ownedCardDTO.stream().mapToInt(CardBenefitDTO::getTotalBenefit).sum())
                .build();

        /* 2. 후보 풀 (보유 포함) */
        List<CardBenefitDTO> poolCard = cardProductMapper.findAllActive().stream()
                .map(p -> calc.calc(p, spend, ownedIds.contains(p.getId())))
                .toList();

        List<CardBenefitDTO> best = greedy(poolCard);
        int bestAggBenefit = best.stream().mapToInt(CardBenefitDTO::getTotalBenefit).sum();

        return CardRecommendDTO.builder()
                .memberId(memberId)
                .current(currentCard)
                .recommendation(
                        CardCombinationDTO.builder().cards(best).aggregateBenefit(bestAggBenefit).build())
                .additionalSaving(bestAggBenefit - currentCard.getAggregateBenefit())
                .predictionModel("XGBoost")
                .build();
    }

    /* Greedy Top-3 */
    private List<CardBenefitDTO> greedy(List<CardBenefitDTO> poolCard) {

        Set<String> covered = new HashSet<>();
        List<CardBenefitDTO> picks = new ArrayList<>();

        while (picks.size() < 3) {
            CardBenefitDTO best = null; int bestGain = -1;

            for (CardBenefitDTO cardBenefitDTO : poolCard) {
                if (picks.contains(cardBenefitDTO) || !cardBenefitDTO.isMeetsRequiredSpend()) continue;

                int gain = cardBenefitDTO.getBenefitByStore().map().entrySet().stream()
                        .filter(e -> !covered.contains(e.getKey()))
                        .mapToInt(Map.Entry::getValue).sum();

                if (gain > bestGain) { bestGain = gain; best = cardBenefitDTO; }
            }
            if (best == null) break;
            picks.add(best);
            covered.addAll(best.getBenefitByStore().map().keySet());
        }
        return picks;
    }
}
