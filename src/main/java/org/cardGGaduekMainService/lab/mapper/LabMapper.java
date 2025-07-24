package org.cardGGaduekMainService.lab.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.lab.domain.FortuneVO;
import org.cardGGaduekMainService.lab.domain.LuckyItemVO;
import org.cardGGaduekMainService.lab.domain.MissionProgressVO;
import org.cardGGaduekMainService.lab.domain.SpendingAnalysisResultVO;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface LabMapper {
    List<MissionProgressVO> selectMissionProgressByMemberId(Long memberId);
    FortuneVO selectTodayFortuneByMemberId(Long memberId);
    SpendingAnalysisResultVO selectSpendingAnalysisResultByMemberId(Long memberId);
    LuckyItemVO selectRandomLuckyItem();
    void insertFortune(@Param("memberId") Long memberId,
                       @Param("fortuneIndex") int fortuneIndex,
                       @Param("itemId") int itemId);
    String selectMostSpentCategory(Long memberId);
    int updateSpendingAnalysisResult(@Param("memberId") Long memberId, @Param("category") String category);
    void insertSpendingAnalysisResult(@Param("memberId") Long memberId, @Param("category") String category);
}
