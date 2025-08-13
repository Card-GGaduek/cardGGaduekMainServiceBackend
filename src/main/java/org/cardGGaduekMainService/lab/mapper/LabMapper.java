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

    // 1. 시즌별 미션
    List<MissionProgressVO> selectAllMissionsWithProgress(Long memberId);
    void updateMissionProgressByActualTransactionCount(@Param("memberId") Long memberId,
                                                       @Param("missionId") Long missionId);
    List<Long> selectCurrentMissionIds();
    List<Long> selectMissionIdsInProgressByMember(@Param("memberId") Long memberId);
    void insertMissionProgress(@Param("memberId") Long memberId,
                               @Param("missionId") Long missionId,
                               @Param("statusCodeId") Integer statusCodeId,
                               @Param("progressValue") Integer progressValue);

    // 2. 오늘의 소비 운세
    FortuneVO selectTodayFortuneByMemberId(Long memberId);
    LuckyItemVO selectRandomLuckyItem();
    void insertFortune(@Param("memberId") Long memberId,
                       @Param("fortuneIndex") int fortuneIndex,
                       @Param("itemId") int itemId);

    // 3. 이번 달 소비 성향 분석
    SpendingAnalysisResultVO selectSpendingAnalysisResultByMemberId(Long memberId);
    String selectMostSpentCategory(Long memberId);
    int updateSpendingAnalysisResult(@Param("memberId") Long memberId, @Param("category") String category);
    void insertSpendingAnalysisResult(@Param("memberId") Long memberId, @Param("category") String category);

    // 4. 스케줄러
    List<Long> selectAllMemberIds();
    boolean existsTransactionThisMonth(Long memberId);
}
