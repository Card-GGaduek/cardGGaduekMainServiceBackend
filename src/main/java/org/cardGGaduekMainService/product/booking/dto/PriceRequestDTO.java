package org.cardGGaduekMainService.product.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceRequestDTO {
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long couponProductId;
    private Long cardId;
    private Long memberId; // 쿠폰, 카드 유효성 검사를 위해 필요
}
