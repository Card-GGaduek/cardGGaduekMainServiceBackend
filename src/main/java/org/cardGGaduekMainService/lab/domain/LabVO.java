package org.cardGGaduekMainService.lab.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabVO {
    private List<MissionProgressVO> missionProgressList;
    private FortuneVO todayFortune;
    private SpendingAnalysisResultVO spendingAnalysis;
}
