package org.cardGGaduekMainService.card.benefit.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.dto.StoreBenefitDTO;

import java.util.List;

@Mapper
public interface CardBenefitMapper {
    CardBenefitVO findBenefitByProductAndCategory(
            @Param("cardProductId") Long cardProductId,
            @Param("benefitCategory") String benefitCategory
    );

    CardBenefitVO findBestBenefitForStore(
            @Param("storeName") String storeName,
            @Param("userCardProductIds")List<Long> userCardProductIds
    );

    List<StoreBenefitDTO> findBenefits(@Param("cardProductId") Long cardProductId, @Param("benefitCategory") String storeCategory);

    List<StoreBenefitDTO> findAllBenefitsForCategory(@Param("categoryName") String categoryName, @Param("userCardProductIds") List<Long> userCardProductIds);

    List<StoreBenefitDTO> findBenefitsByStoreName(@Param("storeName") String storeName,
                                                  @Param("userCardProductIds") List<Long> userCardProductIds);
}
