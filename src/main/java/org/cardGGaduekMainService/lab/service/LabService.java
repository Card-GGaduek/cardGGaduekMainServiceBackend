package org.cardGGaduekMainService.lab.service;

import org.cardGGaduekMainService.lab.domain.MissionProgressVO;
import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;
import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;

import java.util.List;

public interface LabService {
    List<MissionProgressVO> getAllMissionsWithProgressVO(Long memberId);
    List<MissionProgressDTO> getAllMissionsWithProgress(Long memberId);
    void updateMissionProgressByTransactions(Long memberId, List<SpendingCategory> transactionCategories);
    void syncMissionProgressWithTransactions(Long memberId);
    void recalculateAndUpdateMissionProgress(Long memberId); // 🔥 추가
    FortuneResponseDTO getTodayFortune(Long memberId);
    SpendingAnalysisResultDTO getSpendingAnalysis(Long memberId);
    void updateSpendingAnalysis(Long memberId);
    void updateSpendingCategoryDaily();
}
