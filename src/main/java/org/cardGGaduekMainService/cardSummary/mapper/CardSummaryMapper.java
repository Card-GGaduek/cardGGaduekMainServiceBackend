package org.cardGGaduekMainService.cardSummary.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.cardSummary.domain.CardSummaryVO;

import java.util.List;

@Mapper
public interface CardSummaryMapper {

    List<CardSummaryVO> selectMonthlySpendingByMemberId(Long memberId);


}
