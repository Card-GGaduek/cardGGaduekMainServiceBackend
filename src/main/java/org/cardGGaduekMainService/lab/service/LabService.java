package org.cardGGaduekMainService.lab.service;

import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;
import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;

import java.util.List;

public interface LabService {
    List<MissionProgressDTO> getAllMissionsWithProgress(Long memberId);
    List<MissionProgressDTO> getMissionProgress(Long memberId);
    void updateMissionProgressByTransactions(Long memberId, List<SpendingCategory> transactionCategories);
    void syncMissionProgressWithTransactions(Long memberId);
    void recalculateAndUpdateMissionProgress(Long memberId); // 🔥 추가
    FortuneResponseDTO drawTodayFortune(Long memberId);
    FortuneResponseDTO getTodayFortune(Long memberId);
    SpendingAnalysisResultDTO getSpendingAnalysis(Long memberId);
    void updateSpendingAnalysis(Long memberId);
    void updateSpendingCategoryDaily();
}
