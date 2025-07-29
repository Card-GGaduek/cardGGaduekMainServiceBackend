package org.cardGGaduekMainService.cardSummary.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardCateSummaryDTO {

    private String categoryCode;
    private String categoryName;
    private Long cateTotalSpent;
    private Double catePercentage;
}
