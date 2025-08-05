package org.cardGGaduekMainService.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardStoreBenefitDTO {
    private Long id;                    // 가맹점 고유 ID
    private String name;                // 가맹점 이름
    private String address;             // 가맹점 주소
    private Double latitude;            // 가맹점 위도
    private Double longitude;           // 가맹점 경도
    List<CardBenefitDTO> benefits;      // 가맹점 혜택 리스트
}
