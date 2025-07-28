package org.cardGGaduekMainService.cardProduct.service;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import java.util.List;

public interface CardProductService {
    List<CardProductVO> getTop5CardProductsByBenefitCount();
}
