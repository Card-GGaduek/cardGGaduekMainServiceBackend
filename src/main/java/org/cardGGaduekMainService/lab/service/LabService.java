package org.cardGGaduekMainService.lab.service;

import org.cardGGaduekMainService.lab.domain.MissionProgressVO;
import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;
import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;

import java.util.List;

public interface LabService {

    // 1. 시즌별 미션
    List<MissionProgressDTO> getAllMissionsWithProgress(Long memberId);
    List<MissionProgressVO> getAllMissionsWithProgressVO(Long memberId);
    void syncMissionProgressWithTransactions(Long memberId);
    void updateMissionProgressByTransactions(Long memberId, List<SpendingCategory> transactionCategories);
    void recalculateAndUpdateMissionProgress(Long memberId);

    // 2. 오늘의 소비 운세
    FortuneResponseDTO getTodayFortune(Long memberId);

    // 3. 이번 달 소비 성향 분석
    SpendingAnalysisResultDTO getSpendingAnalysis(Long memberId);
    void updateSpendingAnalysis(Long memberId);
    void updateSpendingCategoryDaily();
}
