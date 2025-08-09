package org.cardGGaduekMainService.lab.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.lab.domain.FortuneVO;
import org.cardGGaduekMainService.lab.domain.LuckyItemVO;
import org.cardGGaduekMainService.lab.domain.MissionProgressVO;
import org.cardGGaduekMainService.lab.domain.SpendingAnalysisResultVO;

import java.util.List;

@Mapper
public interface LabMapper {
    List<MissionProgressVO> selectAllMissionsWithProgress(Long memberId);
    List<MissionProgressVO> selectMissionProgressByMemberId(Long memberId);
    List<Long> selectMissionIdsInProgressByMember(@Param("memberId") Long memberId);
    List<Long> selectCurrentMissionIds();
    void insertMissionProgress(@Param("memberId") Long memberId,
                               @Param("missionId") Long missionId,
                               @Param("statusCodeId") Integer statusCodeId,
                               @Param("progressValue") Integer progressValue);
    void updateMissionProgressByActualTransactionCount(@Param("memberId") Long memberId,
                                                       @Param("missionId") Long missionId);
    List<String> selectTransactionCategoriesThisMonth(Long memberId);
    FortuneVO selectTodayFortuneByMemberId(Long memberId);
    SpendingAnalysisResultVO selectSpendingAnalysisResultByMemberId(Long memberId);
    LuckyItemVO selectRandomLuckyItem();
    void insertFortune(@Param("memberId") Long memberId,
                       @Param("fortuneIndex") int fortuneIndex,
                       @Param("itemId") int itemId);
    String selectMostSpentCategory(Long memberId);
    int updateSpendingAnalysisResult(@Param("memberId") Long memberId, @Param("category") String category);
    void insertSpendingAnalysisResult(@Param("memberId") Long memberId, @Param("category") String category);
    List<Long> selectAllMemberIds();
    boolean existsTransactionThisMonth(Long memberId);
}
