package org.cardGGaduekMainService.cardSummary.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.cardSummary.domain.CardCateSummaryVO;

import java.util.List;

@Mapper
public interface CardCateSummaryMapper {

    List<CardCateSummaryVO> selectCurrentMonthSummary(@Param("memberId") Long memberId);


}
