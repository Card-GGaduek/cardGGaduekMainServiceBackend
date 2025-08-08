package org.cardGGaduekMainService.cardRecommend.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardRecommend.domain.StoreBenefitVO;
import org.cardGGaduekMainService.cardRecommend.dto.*;
import org.cardGGaduekMainService.cardRecommend.mapper.StoreBenefitMapper;
import org.cardGGaduekMainService.cardRecommend.util.BenefitUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class CardBenefitCalculator {

    private final StoreBenefitMapper storeBenefitMapper;

    public CardBenefitDTO calc(CardProductVO prod,
                               Map<String,Integer> spend,
                               boolean owned) {

        var ruleByStore = storeBenefitMapper.findByCardProductId(prod.getId())
                .stream()
                .collect(groupingBy(StoreBenefitVO::getStoreName));

        Map<String,Integer> benefitByStore = new LinkedHashMap<>();
        int benefitTotal = 0;
        for (var e : spend.entrySet()) {
            String store = e.getKey();
            if ("total".equals(store)) continue; // total 키 제외

            int best = ruleByStore.getOrDefault(store, List.of())
                    .stream()
                    .mapToInt(r -> BenefitUtil.toWon(e.getValue(), r))
                    .max().orElse(0);
            benefitByStore.put(store, best);
            benefitTotal += best;
        }

        int required = Optional.ofNullable(prod.getRequiredMonthlySpent()).orElse(0L).intValue();
        // total 계산은 "total" 키가 있으면 그걸 쓰고, 없으면 가맹점 합으로 계산
        int totalPredicted = Optional.ofNullable(spend.get("total"))
                .orElseGet(() -> spend.entrySet().stream()
                        .filter(en -> !"total".equals(en.getKey()))
                        .mapToInt(Map.Entry::getValue)
                        .sum());

        // 카드 1장 기준 최소실적 충족 여부
        boolean ok = totalPredicted >= required;

        return CardBenefitDTO.builder()
                .cardProductId(prod.getId())
                .cardProductName(prod.getCardProductName())
                .cardProductImageUrl(prod.getCardImageUrl())
                .owned(owned)
                .meetsRequiredSpend(ok)
                .benefitByStore(new BenefitByStoreDTO(benefitByStore))
                .requiredMonthlySpent(required)
                .totalBenefit(benefitTotal)
                .build();
    }
}
