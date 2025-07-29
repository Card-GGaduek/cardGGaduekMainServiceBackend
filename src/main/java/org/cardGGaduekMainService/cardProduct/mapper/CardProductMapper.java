package org.cardGGaduekMainService.cardProduct.mapper;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import java.util.List;

public interface CardProductMapper {
    List<CardProductVO> getTop5CardProductsByBenefitCount();
}
