package org.cardGGaduekMainService.lab.service;

import lombok.RequiredArgsConstructor;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LabServiceImpl implements LabService {
    private final LabMapper labMapper;

    @Override
    public List<MissionProgressDTO> getMissionProgress(Long memberId) {
        List<MissionProgressVO> list = labMapper.selectMissionProgressByMemberId(memberId);
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(MissionProgressDTO::from).collect(Collectors.toList());
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
        labMapper.insertFortune(memberId, randomLevel.getIndex(), randomItem.getItemId());

        // 다시 조회해서 결과 반환
        FortuneVO result = labMapper.selectTodayFortuneByMemberId(memberId);
        return FortuneResponseDTO.from(result);
    }


    @Override
    public FortuneResponseDTO getTodayFortune(Long memberId) {
        FortuneVO fortune = labMapper.selectTodayFortuneByMemberId(memberId);
        if (fortune == null) return null;

        FortuneLevel level = FortuneLevel.fromIndex(fortune.getFortuneIndex());
        fortune.setFortuneLevel(level);
        return FortuneResponseDTO.from(fortune);
    }

    @Override
    public SpendingAnalysisResultDTO getSpendingAnalysis(Long memberId) {
        SpendingAnalysisResultVO vo = labMapper.selectSpendingAnalysisResultByMemberId(memberId);
        return vo != null ? SpendingAnalysisResultDTO.from(vo) : null;
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


}
