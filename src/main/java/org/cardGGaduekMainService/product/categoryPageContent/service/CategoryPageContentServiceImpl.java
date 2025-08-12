package org.cardGGaduekMainService.product.categoryPageContent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.product.categoryPageContent.dto.BenefitDetailDTO;
import org.cardGGaduekMainService.product.categoryPageContent.dto.FinalBenefitResponseDTO;
import org.cardGGaduekMainService.product.categoryPageContent.mapper.CategoryPageContentMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryPageContentServiceImpl implements CategoryPageContentService {

    private final CategoryPageContentMapper categoryPageContentMapper;
    private final CardMapper cardMapper;
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    @Override
    public List<FinalBenefitResponseDTO> getBenefitContentForMember(String categoryName, Long memberId) {
        // ... 1, 2단계는 동일 (사용자 카드 조회, DB에서 모든 혜택 조회)
        List<Long> userCardProductIds = cardMapper.findAllCardProductIdsByMemberId(memberId);
        if (userCardProductIds == null || userCardProductIds.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("categoryName", categoryName);
        params.put("userCardProductIds", userCardProductIds);
        List<BenefitDetailDTO> allBenefits = categoryPageContentMapper.findAllApplicableBenefitsForMember(params);

        // 3. 가맹점(storeName)별로 그룹핑
        Map<String, List<BenefitDetailDTO>> benefitsByStore = allBenefits.stream()
                .collect(Collectors.groupingBy(BenefitDetailDTO::getStoreName));

        List<FinalBenefitResponseDTO> finalResultList = new ArrayList<>();

        // 4. 각 가맹점별로 최고의 혜택을 계산
        for (Map.Entry<String, List<BenefitDetailDTO>> entry : benefitsByStore.entrySet()) {
            List<BenefitDetailDTO> benefitsForOneStore = entry.getValue();

            BenefitDetailDTO bestBenefitForStore = null;
            // [수정] 최대 할인액을 BigDecimal.ZERO로 초기화
            BigDecimal maxDiscountAmount = BigDecimal.ZERO;

            for (BenefitDetailDTO currentBenefit : benefitsForOneStore) {
                BigDecimal currentCalculatedDiscount = BigDecimal.ZERO;
                // [수정] 기준 가격도 BigDecimal로 처리 (null 체크 포함)
                BigDecimal basePrice = Optional.ofNullable(currentBenefit.getPrice()).orElse(BigDecimal.ZERO);

                // 기준 가격이 0이면 계산할 필요 없음
                if (basePrice.compareTo(BigDecimal.ZERO) == 0) continue;

                // [수정] 혜택 타입에 따라 BigDecimal로 실제 할인액 계산
                if ("PERCENT".equals(currentBenefit.getValueType()) && currentBenefit.getRateValue() != null) {
                    // 퍼센트 할인 계산: price * (rate / 100)
                    BigDecimal rateAsFraction = currentBenefit.getRateValue().divide(ONE_HUNDRED);
                    currentCalculatedDiscount = basePrice.multiply(rateAsFraction)
                            .setScale(0, RoundingMode.DOWN); // 소수점 이하 버림
                } else if ("AMOUNT".equals(currentBenefit.getValueType()) && currentBenefit.getAmountValue() != null) {
                    // 금액 할인
                    currentCalculatedDiscount = currentBenefit.getAmountValue();
                }

                // [수정] compareTo를 사용하여 더 좋은 혜택(할인액이 더 큰)을 찾으면 교체
                if (currentCalculatedDiscount.compareTo(maxDiscountAmount) > 0) {
                    maxDiscountAmount = currentCalculatedDiscount;
                    bestBenefitForStore = currentBenefit;
                }
            }

            // 찾은 최고의 혜택을 최종 DTO로 변환
            if (bestBenefitForStore != null) {
                FinalBenefitResponseDTO dto = new FinalBenefitResponseDTO();
                dto.setId(bestBenefitForStore.getContentId());
                dto.setTitle(bestBenefitForStore.getStoreName());
                dto.setImageUrl(bestBenefitForStore.getImageUrl());
                dto.setLinkUrl(bestBenefitForStore.getLinkUrl());
                dto.setPrice(bestBenefitForStore.getPrice());
                dto.setCardName(bestBenefitForStore.getCardName());
                dto.setBenefitDescription(bestBenefitForStore.getDescription());
                dto.setValueType(bestBenefitForStore.getValueType());
                dto.setRateValue(bestBenefitForStore.getRateValue());
                dto.setAmountValue(bestBenefitForStore.getAmountValue());
                dto.setCalculatedDiscountAmount(maxDiscountAmount); // 최종 계산된 할인액 추가
                finalResultList.add(dto);
            }
        }

        return finalResultList;
    }
}