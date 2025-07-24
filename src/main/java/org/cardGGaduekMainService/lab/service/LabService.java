package org.cardGGaduekMainService.lab.service;

import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;

import java.util.List;

public interface LabService {
    List<MissionProgressDTO> getMissionProgress(Long memberId);
    FortuneResponseDTO drawTodayFortune(Long memberId);
    FortuneResponseDTO getTodayFortune(Long memberId);
    SpendingAnalysisResultDTO getSpendingAnalysis(Long memberId);
    void updateSpendingAnalysis(Long memberId);
}
