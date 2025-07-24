package org.cardGGaduekMainService.lab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.cardGGaduekMainService.lab.domain.MissionProgressVO;

import java.time.LocalDateTime;

@Data
public class MissionProgressDTO {
    private Long missionId;
    private String missionTitle;
    private String description;
    private String reward;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endAt;

    private int progressValue;
    private int goalValue;
    private String progressStatus;

    public static MissionProgressDTO from(MissionProgressVO vo) {
        MissionProgressDTO dto = new MissionProgressDTO();
        dto.setMissionId(vo.getMissionId());
        dto.setMissionTitle(vo.getMissionTitle());
        dto.setDescription(vo.getDescription());
        dto.setReward(vo.getReward());
        dto.setStartAt(vo.getStartAt());
        dto.setEndAt(vo.getEndAt());
        dto.setProgressValue(vo.getProgressValue());
        dto.setGoalValue(vo.getGoalValue());
        dto.setProgressStatus(vo.getProgressStatus());
        return dto;
    }
}
