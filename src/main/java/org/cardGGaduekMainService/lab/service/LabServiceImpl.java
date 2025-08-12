package org.cardGGaduekMainService.lab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.coupon.couponProduct.service.CouponProductService;
import org.cardGGaduekMainService.lab.domain.FortuneVO;
import org.cardGGaduekMainService.lab.domain.LuckyItemVO;
import org.cardGGaduekMainService.lab.domain.MissionProgressVO;
import org.cardGGaduekMainService.lab.domain.SpendingAnalysisResultVO;
import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;
import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;
import org.cardGGaduekMainService.lab.mapper.LabMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class LabServiceImpl implements LabService {
    private final LabMapper labMapper;
    private final CouponProductService couponProductService;

    // 1. 시즌별 미션
    // 1.1 미션 목록과 진행현황을 DTO로 반환 (Controller에서 호출)
    @Override
    public List<MissionProgressDTO> getAllMissionsWithProgress(Long memberId) {
        List<MissionProgressVO> list = labMapper.selectAllMissionsWithProgress(memberId);
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(MissionProgressDTO::from).collect(Collectors.toList());
    }

    // 1.2 미션 목록과 진행현황을 VO로 반환 (내부에서 사용)
    @Override
    public List<MissionProgressVO> getAllMissionsWithProgressVO(Long memberId) {
        List<MissionProgressVO> list = labMapper.selectAllMissionsWithProgress(memberId);
        return (list != null) ? list : List.of();
    }

    // 1.3 사용자 미션 조회 시 전체 동기화 및 쿠폰 발급 처리 (Controller에서 호출)
    @Override
    @Transactional
    public void syncMissionProgressWithTransactions(Long memberId) {
        log.warn("✅ syncMissionProgressWithTransactions called for memberId={}", memberId);
        ensureMissionProgressExists(memberId); // 최초 진입 시 VO 생성용
        recalculateAndUpdateMissionProgress(memberId); // 거래 기반으로 진행률 업데이트
        List<MissionProgressVO> progressList = getAllMissionsWithProgressVO(memberId);   // 진행률 계산 후
        checkAndIssueCouponsForCompletedMissions(memberId, progressList); // 쿠폰 발급

    }

    // 1.4 실시간 거래 발생 시 미션 진행률 업데이트 (TransactionService에서 호출)
    @Override
    @Transactional
    public void updateMissionProgressByTransactions(Long memberId, List<SpendingCategory> transactionCategories) {
        // 미션 진행 현황이 없으면 자동 생성
        ensureMissionProgressExists(memberId);
        // 실제 거래 건수 기반으로 진행률 재계산 및 업데이트
        recalculateAndUpdateMissionProgress(memberId);
    }



    // 2. 오늘의 소비 운세
    // 2.1 오늘의 운세 생성 및 조회 (Controller에서 호출)
    @Override
    public FortuneResponseDTO getTodayFortune(Long memberId) {
        // 1. 오늘자 운세 조회
        FortuneVO fortune = labMapper.selectTodayFortuneByMemberId(memberId);
        if (fortune != null) return FortuneResponseDTO.from(fortune);

        // 2. 운세가 없다면 새로 생성
        LuckyItemVO luckyItem = labMapper.selectRandomLuckyItem();
        int fortuneIndex = generateRandomFortuneIndex();
        labMapper.insertFortune(memberId, fortuneIndex, luckyItem.getId());

        // 3. 다시 조회해서 DTO로 반환
        FortuneVO newFortune = labMapper.selectTodayFortuneByMemberId(memberId);
        return newFortune != null ? FortuneResponseDTO.from(newFortune) : null;
    }



    // 3. 이달의 소비 성향 분석
    // 3.1 소비 분석 결과 조회 (Controller에서 호출)
    @Override
    public SpendingAnalysisResultDTO getSpendingAnalysis(Long memberId) {
        SpendingAnalysisResultVO resultVO = labMapper.selectSpendingAnalysisResultByMemberId(memberId);

        if (resultVO == null) {
            updateSpendingAnalysis(memberId); // 없는 경우엔 NON
            resultVO = labMapper.selectSpendingAnalysisResultByMemberId(memberId); // 다시 조회
        }

        return SpendingAnalysisResultDTO.from(resultVO); // 변환 메서드가 있다고 가정
    }

    // 3.2 소비 분석 결과 업데이트 (Controller에서 호출)
    @Override
    public void updateSpendingAnalysis(Long memberId) {
        String categoryStr = labMapper.selectMostSpentCategory(memberId);
        if (categoryStr == null) categoryStr = "NON";
        SpendingCategory category = SpendingCategory.fromName(categoryStr);

        int updated = labMapper.updateSpendingAnalysisResult(memberId, category.name());
        if (updated == 0) {
            labMapper.insertSpendingAnalysisResult(memberId, category.name());
        }
    }

    // 3.3 전체 회원의 소비 분석을 일괄 업데이트 (Scheduler에서 매일 실행)
    @Override
    public void updateSpendingCategoryDaily() {
        List<Long> memberIds = labMapper.selectAllMemberIds();

        for (Long memberId : memberIds) {
            boolean hasTransactions = labMapper.existsTransactionThisMonth(memberId);

            SpendingCategory category;
            if (!hasTransactions) {
                category = SpendingCategory.NON;
            } else {
                String mostSpentCode = labMapper.selectMostSpentCategory(memberId);
                category = SpendingCategory.fromCode(mostSpentCode);
            }

            SpendingAnalysisResultVO existing = labMapper.selectSpendingAnalysisResultByMemberId(memberId);
            if (existing != null) {
                labMapper.updateSpendingAnalysisResult(memberId, category.name());
            } else {
                labMapper.insertSpendingAnalysisResult(memberId, category.name());
            }
        }
    }



    // HELPER 메서드
    // 운세 지수 랜덤 생성 (10~100 중 하나)
    private int generateRandomFortuneIndex() {
        int[] validIndexes = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        return validIndexes[(int) (Math.random() * validIndexes.length)];
    }

    // 현재 진행 중인 미션의 진행현황이 없으면 자동 생성
    private void ensureMissionProgressExists(Long memberId) {
        List<Long> existingMissionIds = labMapper.selectMissionIdsInProgressByMember(memberId);
        List<Long> allCurrentMissionIds = labMapper.selectCurrentMissionIds(); // 기간 유효한 모든 미션

        for (Long missionId : allCurrentMissionIds) {
            if (!existingMissionIds.contains(missionId)) {
                labMapper.insertMissionProgress(memberId, missionId, 1, 0); // status_code_id = 1 (예: 진행중)
            }
        }
    }

    // 실제 거래 건수를 기반으로 모든 미션의 진행률을 재계산
    @Transactional
    public void recalculateAndUpdateMissionProgress(Long memberId) {
        // 현재 진행 중인 모든 미션 조회
        List<Long> currentMissionIds = labMapper.selectCurrentMissionIds();

        for (Long missionId : currentMissionIds) {
            // 각 미션별로 실제 거래 건수 카운트하여 진행률 업데이트
            labMapper.updateMissionProgressByActualTransactionCount(memberId, missionId);
        }
    }

    // 완료된 미션에 대해 쿠폰 발급 처리 (중복 발급 방지)
    private void checkAndIssueCouponsForCompletedMissions(Long memberId, List<MissionProgressVO> progressList) {
        for (MissionProgressVO progress : progressList) {
            boolean isCompleted = progress.getProgressValue() >= progress.getGoalValue();

            if (isCompleted) {
                // 중복 발급 방지
                if (couponProductService.hasCouponForMission(memberId, progress.getMissionId())) {
                    log.debug("이미 발급된 미션 쿠폰 - memberId: {}, missionId: {}, mission: {}", memberId, progress.getMissionId(), progress.getMissionTitle());
                    continue;
                }
                try {
                    couponProductService.issueCouponByMissionReward(memberId, progress.getReward());
                    log.info("✅ 미션 성공 쿠폰 발급 시도 - memberId: {}, mission: {}, reward: {}",
                            memberId, progress.getMissionTitle(), progress.getReward());
                } catch (Exception e) {
                    log.error("❌ 미션 쿠폰 발급 실패 - memberId: {}, mission: {}", memberId, progress.getMissionTitle(), e);
                }

            }
        }
    }


}
