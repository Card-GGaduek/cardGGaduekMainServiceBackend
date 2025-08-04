package org.cardGGaduekMainService.cardRecommend.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardRecommend.domain.StoreBenefitVO;
import org.cardGGaduekMainService.cardRecommend.dto.*;
import org.cardGGaduekMainService.cardRecommend.mapper.StoreBenefitMapper;
import org.cardGGaduekMainService.cardRecommend.util.BenefitUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class CardBenefitCalculator {

    private final StoreBenefitMapper ruleMapper;

    public CardBenefitDTO calc(CardProductVO prod,
                               Map<String,Integer> spend,
                               boolean owned) {

        var ruleByStore = ruleMapper.findByCardProductId(prod.getId())
                .stream()
                .collect(groupingBy(StoreBenefitVO::getStoreName));

        Map<String,Integer> byStore = new LinkedHashMap<>();
        int total = 0;
        for (var e : spend.entrySet()) {
            int best = ruleByStore.getOrDefault(e.getKey(), List.of())
                    .stream()
                    .mapToInt(r -> BenefitUtil.toWon(e.getValue(), r))
                    .max().orElse(0);
            byStore.put(e.getKey(), best);
            total += best;
        }

        boolean ok = spend.values().stream().mapToInt(Integer::intValue).sum()
                >= prod.getRequiredMonthlySpent();

        return CardBenefitDTO.builder()
                .cardProductId(prod.getId())
                .cardProductName(prod.getCardProductName())
                .owned(owned)
                .meetsRequiredSpend(ok)
                .benefitByStore(new BenefitByStoreDTO(byStore))
                .totalBenefit(total)
                .build();
    }
}
