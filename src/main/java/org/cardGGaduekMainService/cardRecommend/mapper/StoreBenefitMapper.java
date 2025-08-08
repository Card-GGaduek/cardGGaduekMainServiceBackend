package org.cardGGaduekMainService.cardRecommend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.cardRecommend.domain.StoreBenefitVO;

import java.util.List;

@Mapper
public interface StoreBenefitMapper {

    List<StoreBenefitVO> findByCardProductId(Long cardProductId);
}
