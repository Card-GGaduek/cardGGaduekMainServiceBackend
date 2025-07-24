package org.cardGGaduekMainService.lab.dto;

import lombok.Data;
import org.cardGGaduekMainService.lab.domain.SpendingAnalysisResultVO;
import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;

@Data
public class SpendingAnalysisResultDTO {
    private String category;         // FOOD, SHOPPING, ...
    private String resultTitle;      // enum에서 가져오는 결과 문구
    private String imageUrl;         // enum에서 가져오는 이미지 경로
    private String analysisMonth;    // '2025-07'

    public static SpendingAnalysisResultDTO from(SpendingAnalysisResultVO vo) {
        SpendingCategory category = vo.getCategory();

        SpendingAnalysisResultDTO dto = new SpendingAnalysisResultDTO();
        dto.setCategory(category.name());
        dto.setResultTitle(category.getResultTitle());
        dto.setImageUrl("/api/lab/analysis/image/" + category.name().toLowerCase());
        dto.setAnalysisMonth(vo.getAnalysisMonth().toString());
        return dto;
    }
}
