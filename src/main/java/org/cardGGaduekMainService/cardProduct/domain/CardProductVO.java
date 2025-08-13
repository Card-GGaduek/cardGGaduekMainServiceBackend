package org.cardGGaduekMainService.cardProduct.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardProductVO {
    private Long id;                        // 카드상품 ID (PK)
    private String cardType;                // 카드타입
    private String organizationCode;                    // 은행 ID (FK)
    private String cardProductName;         // 카드 상품 이름
    private String cardImageUrl;            // 카드 이미지 URL
    private Long annualFee;// 연회비
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" )
    private LocalDateTime issuedDate;       // 발급일자 (기본값 CURRENT_TIMESTAMP)
    private Long requiredMonthlySpent;   // 기준 실적

    //Top5 조회 결과에서만 추가되는 계산 컬럼
    private Long benefitCount;              // 혜택 개수 (JOIN + COUNT 결과)
}