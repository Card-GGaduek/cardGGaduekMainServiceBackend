package org.cardGGaduekMainService.cardPerformance.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.cardPerformance.domain.CardPerformanceVO;

import java.util.List;

@Mapper
public interface CardPerformanceMapper {

    List<CardPerformanceVO> selectByMemberId(@Param("memberId") Long memberId);

}
