package org.cardGGaduekMainService.lab.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;
import org.cardGGaduekMainService.lab.service.LabService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "실험실 관리")
@RestController
@RequestMapping("/api/lab")
@RequiredArgsConstructor
public class LabController {
    private final LabService labService;

    // 1. 미션 진행 현황
    @ApiOperation(value = "미션 진행 현황 조회", notes = "사용자의 미션 진행도를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "미션 진행도 현황 조회 성공", response = MissionProgressDTO.class, responseContainer = "List")
    )
    @GetMapping("/missions")
    public ResponseEntity<CustomApiResponse<List<MissionProgressDTO>>> getAllMissionsWithProgress(@AuthenticationPrincipal LoginMember loginMember) {

        labService.syncMissionProgressWithTransactions(loginMember.getId());

        List<MissionProgressDTO> missions = labService.getAllMissionsWithProgress(loginMember.getId());
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.MISSION_PROGRESS_FETCH_SUCCESS, missions));
    }

    // 2. 오늘의 소비 운세
    @ApiOperation(value = "소비 운세 조회", notes = "사용자의 소비 운세를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "소비 운세 조회 성공", response = FortuneResponseDTO.class)
    )
    @GetMapping("/fortune")
    public ResponseEntity<CustomApiResponse<FortuneResponseDTO>> getTodayFortune(@AuthenticationPrincipal LoginMember loginMember) {
        FortuneResponseDTO fortune = labService.getTodayFortune(loginMember.getId());
        if (fortune == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.FORTUNE_FETCH_SUCCESS, fortune));
    }

    // 3. 소비 성향 분석 결과
    @ApiOperation(value = "소비 성향 분석 결과", notes = "사용자의 소비 성향 분석 결과를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "소비 성향 분석 결과 조회 성공", response = SpendingAnalysisResultDTO.class)
    )
    @GetMapping("/analysis")
    public ResponseEntity<CustomApiResponse<SpendingAnalysisResultDTO>> getSpendingAnalysis(@AuthenticationPrincipal LoginMember loginMember) {

        labService.updateSpendingAnalysis(loginMember.getId());

        SpendingAnalysisResultDTO analysis = labService.getSpendingAnalysis(loginMember.getId());
        if (analysis == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.ANALYSIS_FETCH_SUCCESS, analysis));
    }

    // 4. Lab 통합 조회
    @ApiOperation(value = "실험실 통합 조회", notes = "실험실에 필요한 모든 정보를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "실험실 통합 조회 성공")
    )
    @GetMapping
    public ResponseEntity<CustomApiResponse<Map<String, Object>>> getLabData(@AuthenticationPrincipal LoginMember loginMember) {
        Map<String, Object> result = new HashMap<>();

        labService.syncMissionProgressWithTransactions(loginMember.getId());
        result.put("missions", labService.getAllMissionsWithProgress(loginMember.getId()));

        result.put("fortune", labService.getTodayFortune(loginMember.getId()));

        labService.updateSpendingAnalysis(loginMember.getId());
        result.put("analysis", labService.getSpendingAnalysis(loginMember.getId()));

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.LAB_OVERVIEW_FETCH_SUCCESS, result));
    }
}
