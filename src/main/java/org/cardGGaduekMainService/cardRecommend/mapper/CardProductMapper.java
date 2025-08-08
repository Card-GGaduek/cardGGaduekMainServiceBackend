package org.cardGGaduekMainService.cardRecommend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("cardRecommendProductMapper")
public interface CardProductMapper {

    List<CardProductVO> findAllActive();   // XML
    CardProductVO findById(Long id);       // XML
}
