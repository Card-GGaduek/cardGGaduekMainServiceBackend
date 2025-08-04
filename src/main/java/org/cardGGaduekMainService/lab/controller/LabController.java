package org.cardGGaduekMainService.lab.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;
import org.cardGGaduekMainService.lab.service.LabService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab")
@RequiredArgsConstructor
public class LabController {
    private final LabService labService;

    // 1. 미션 진행 현황
    @GetMapping("/missions")
    public ResponseEntity<ApiResponse<List<MissionProgressDTO>>> getAllMissionsWithProgress(@RequestParam Long memberId) {
        List<MissionProgressDTO> missions = labService.getAllMissionsWithProgress(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MISSION_PROGRESS_FETCH_SUCCESS, missions));
    }

    // 2. 오늘의 소비 운세
    @GetMapping("/fortune")
    public ResponseEntity<ApiResponse<FortuneResponseDTO>> getTodayFortune(@RequestParam Long memberId) {
        FortuneResponseDTO fortune = labService.getTodayFortune(memberId);
        if (fortune == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.FORTUNE_FETCH_SUCCESS, fortune));
    }

    // 3. 소비 성향 분석 결과
    @GetMapping("/analysis")
    public ResponseEntity<ApiResponse<SpendingAnalysisResultDTO>> getSpendingAnalysis(@RequestParam Long memberId) {
        SpendingAnalysisResultDTO analysis = labService.getSpendingAnalysis(memberId);
        if (analysis == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ANALYSIS_FETCH_SUCCESS, analysis));
    }

    // 4. Lab 통합 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLabData(@RequestParam Long memberId) {
        Map<String, Object> result = new HashMap<>();
        result.put("missions", labService.getAllMissionsWithProgress(memberId));
        result.put("fortune", labService.getTodayFortune(memberId));
        result.put("analysis", labService.getSpendingAnalysis(memberId));

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.LAB_OVERVIEW_FETCH_SUCCESS, result));
    }
}
