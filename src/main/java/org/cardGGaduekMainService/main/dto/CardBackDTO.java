package org.cardGGaduekMainService.main.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardBackDTO {
    private String cardName;
    private String cardCompany;
    private List<String> benefits;
}
