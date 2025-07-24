package org.cardGGaduekMainService.cardProduct.domain;

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
    private Long id;                        // 카드상품ID
    private String cardType;                // 카드타입
    private Long bankId;                    // 은행ID
    private String cardProductName;         // 카드상품이름
    private Long annualFee;                 // 연회비
    private LocalDateTime issuedDate;       // 상품생성일
    private Long requiredMonthlySpending;   // 기준실적
}