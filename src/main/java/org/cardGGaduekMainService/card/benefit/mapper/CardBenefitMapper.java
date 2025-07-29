package org.cardGGaduekMainService.card.benefit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;

import java.util.List;

@Mapper
public interface CardBenefitMapper {
    CardBenefitVO findBenefitByProductAndCategory(
            @Param("cardProductId") Long cardProductId,
            @Param("benefitCategory") String benefitCategory
    );

    CardBenefitVO findBestBenefitForStore(
            @Param("benefitCategory") String benefitCategory,
            @Param("userCardProductIds")List<Long> userCardProductIds
    );
}
