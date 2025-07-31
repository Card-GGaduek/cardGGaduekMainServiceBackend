package org.cardGGaduekMainService.cardProduct.service;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDTO;

import java.util.List;

public interface CardProductService {
    List<CardProductVO> getTop5CardProductsByBenefitCount();

    List<CardProductDTO> getAllCardProducts();

    CardProductVO getCardProductDetail(Long productId);
}
