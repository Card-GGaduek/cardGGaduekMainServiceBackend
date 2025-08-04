package org.cardGGaduekMainService.cardProduct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardProductDTO {
    private Long id;                        // 카드상품 ID (PK)
    private String cardProductName;         // 카드 상품 이름
    private String cardImageUrl;            // 카드 이미지 URL
}
