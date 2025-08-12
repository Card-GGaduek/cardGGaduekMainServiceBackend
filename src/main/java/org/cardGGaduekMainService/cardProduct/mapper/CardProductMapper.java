package org.cardGGaduekMainService.cardProduct.mapper;

import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDTO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDetailDTO;

import java.util.List;

public interface CardProductMapper {
    List<CardProductVO> getTop5CardProductsByBenefitCount();
    List<CardProductDTO> findAllCardProducts();
    CardProductVO findCardProductDetail(Long id);
    String findNameById(Long cardProductId);

    List<CardProductDetailDTO> findDetailById(Long id);

    CardProductVO findCardProductByName(@Param("name") String name);

    void createCardProduct(CardProductVO cardProduct);
}
