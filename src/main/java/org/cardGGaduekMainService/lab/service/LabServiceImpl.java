package org.cardGGaduekMainService.lab.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.coupon.couponProduct.service.CouponProductService;
import org.cardGGaduekMainService.lab.domain.FortuneVO;
import org.cardGGaduekMainService.lab.domain.LuckyItemVO;
import org.cardGGaduekMainService.lab.domain.MissionProgressVO;
import org.cardGGaduekMainService.lab.domain.SpendingAnalysisResultVO;
import org.cardGGaduekMainService.lab.domain.enums.FortuneLevel;
import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;
import org.cardGGaduekMainService.lab.dto.FortuneResponseDTO;
import org.cardGGaduekMainService.lab.dto.MissionProgressDTO;
import org.cardGGaduekMainService.lab.dto.SpendingAnalysisResultDTO;
import org.cardGGaduekMainService.lab.mapper.LabMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class LabServiceImpl implements LabService {
    private final LabMapper labMapper;
    private final CouponProductService couponProductService;
    private int generateRandomFortuneIndex() {
        int[] validIndexes = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        return validIndexes[(int) (Math.random() * validIndexes.length)];
    }

    @Override
    public List<MissionProgressDTO> getAllMissionsWithProgress(Long memberId) {
        List<MissionProgressVO> list = labMapper.selectAllMissionsWithProgress(memberId);
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(MissionProgressDTO::from).collect(Collectors.toList());
    }

    @Override
    public List<MissionProgressDTO> getMissionProgress(Long memberId) {
        List<MissionProgressVO> list = labMapper.selectMissionProgressByMemberId(memberId);
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(MissionProgressDTO::from).collect(Collectors.toList());
    }

    private void ensureMissionProgressExists(Long memberId) {
        List<Long> existingMissionIds = labMapper.selectMissionIdsInProgressByMember(memberId);
        List<Long> allCurrentMissionIds = labMapper.selectCurrentMissionIds(); // 기간 유효한 모든 미션

        for (Long missionId : allCurrentMissionIds) {
            if (!existingMissionIds.contains(missionId)) {
                labMapper.insertMissionProgress(memberId, missionId, 1, 0); // status_code_id = 1 (예: 진행중)
            }
        }
    }

    @Override
    @Transactional // 🔥 트랜잭션 추가 필요
    public void updateMissionProgressByTransactions(Long memberId, List<SpendingCategory> transactionCategories) {
        // 🔹 미션 진행 현황이 없으면 자동 생성
        ensureMissionProgressExists(memberId);

        // 🔥 업데이트 전 현재 진행 상황 조회
        List<MissionProgressVO> beforeUpdate = labMapper.selectAllMissionsWithProgress(memberId);

        // 🔹 실제 거래 건수 기반으로 진행률 재계산 및 업데이트
        recalculateAndUpdateMissionProgress(memberId);

        // 🔥 업데이트 후 진행 상황 조회 및 미션 성공 체크
        List<MissionProgressVO> afterUpdate = labMapper.selectAllMissionsWithProgress(memberId);

        checkAndIssueCouponsForCompletedMissions(memberId, beforeUpdate, afterUpdate);
    }

    @Transactional
    public void recalculateAndUpdateMissionProgress(Long memberId) {
        // 현재 진행 중인 모든 미션 조회
        List<Long> currentMissionIds = labMapper.selectCurrentMissionIds();

        for (Long missionId : currentMissionIds) {
            // 각 미션별로 실제 거래 건수 카운트하여 진행률 업데이트
            labMapper.updateMissionProgressByActualTransactionCount(memberId, missionId);
        }
    }

    @Override
    @Transactional
    public void syncMissionProgressWithTransactions(Long memberId) {
        // 🔹 미션 진행 현황이 없으면 자동 생성
        ensureMissionProgressExists(memberId);

        // 🔹 실제 거래 건수 기반으로 모든 미션 진행률 재계산
        recalculateAndUpdateMissionProgress(memberId);
    }


    @Override
    public FortuneResponseDTO drawTodayFortune(Long memberId) {
        // 이미 뽑았는지 확인
        FortuneVO today = labMapper.selectTodayFortuneByMemberId(memberId);
        if (today != null) return FortuneResponseDTO.from(today);

        // 아직 안 뽑았다면 랜덤으로 Fortune 생성
        FortuneLevel randomLevel = FortuneLevel.getRandom(); // 랜덤 FortuneLevel
        LuckyItemVO randomItem = labMapper.selectRandomLuckyItem(); // 랜덤 LuckyItem (mapper 작성 필요)

        // insert용 VO 구성
        labMapper.insertFortune(memberId, randomLevel.getIndex(), randomItem.getId());

        // 다시 조회해서 결과 반환
        FortuneVO result = labMapper.selectTodayFortuneByMemberId(memberId);
        return FortuneResponseDTO.from(result);
    }


    @Override
    public FortuneResponseDTO getTodayFortune(Long memberId) {
        // 1. 오늘자 운세 조회
        FortuneVO fortune = labMapper.selectTodayFortuneByMemberId(memberId);
        LocalDate todayKST = LocalDate.now(ZoneId.of("Asia/Seoul"));

        if (fortune != null) return FortuneResponseDTO.from(fortune);


        // 2. 운세가 없다면 새로 생성
        LuckyItemVO luckyItem = labMapper.selectRandomLuckyItem();
        int fortuneIndex = generateRandomFortuneIndex();

        labMapper.insertFortune(memberId, fortuneIndex, luckyItem.getId());

        // 3. 다시 조회해서 DTO로 반환
        FortuneVO newFortune = labMapper.selectTodayFortuneByMemberId(memberId);
        return newFortune != null ? FortuneResponseDTO.from(newFortune) : null;
    }

    @Override
    public SpendingAnalysisResultDTO getSpendingAnalysis(Long memberId) {
        SpendingAnalysisResultVO resultVO = labMapper.selectSpendingAnalysisResultByMemberId(memberId);

        if (resultVO == null) {
            updateSpendingAnalysis(memberId); // 없는 경우엔 NON으로 생성
            resultVO = labMapper.selectSpendingAnalysisResultByMemberId(memberId); // 다시 조회
        }

        return SpendingAnalysisResultDTO.from(resultVO); // 변환 메서드가 있다고 가정
    }

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

    // 🔥 새로 추가할 메서드
    private void checkAndIssueCouponsForCompletedMissions(Long memberId,
                                                          List<MissionProgressVO> beforeUpdate,
                                                          List<MissionProgressVO> afterUpdate) {

        for (int i = 0; i < beforeUpdate.size() && i < afterUpdate.size(); i++) {
            MissionProgressVO before = beforeUpdate.get(i);
            MissionProgressVO after = afterUpdate.get(i);

            // 미션 성공 조건: 업데이트 전에는 미완료, 업데이트 후에는 완료
            boolean wasIncomplete = before.getProgressValue() < before.getGoalValue();
            boolean isNowComplete = after.getProgressValue() >= after.getGoalValue();

            if (wasIncomplete && isNowComplete) {
                // 🎉 미션 성공! 쿠폰 발급
                try {
                    couponProductService.issueCouponByMissionReward(memberId, after.getReward());
                    log.info("미션 성공 쿠폰 발급 완료 - memberId: {}, mission: {}, reward: {}",
                            memberId, after.getMissionTitle(), after.getReward());
                } catch (Exception e) {
                    log.error("미션 성공 쿠폰 발급 실패 - memberId: {}, mission: {}",
                            memberId, after.getMissionTitle(), e);
                }
            }
        }
    }
}
