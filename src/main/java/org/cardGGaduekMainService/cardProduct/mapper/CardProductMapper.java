package org.cardGGaduekMainService.cardProduct.mapper;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDTO;

import java.util.List;

public interface CardProductMapper {
    List<CardProductVO> getTop5CardProductsByBenefitCount();
    List<CardProductDTO> findAllCardProducts();
    CardProductVO findCardProductDetail(Long id);
    String findNameById(Long cardProductId);
}
