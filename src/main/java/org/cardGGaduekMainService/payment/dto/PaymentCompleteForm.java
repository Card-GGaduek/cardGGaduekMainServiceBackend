package org.cardGGaduekMainService.payment.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentCompleteForm {
    private String imp_uid;        // PortOne 결제 고유ID
    private String merchant_uid;   // 상점 주문번호(로그용)
    private BigDecimal amount;     // 최종 결제금액
    private String category;       // 선택(없으면 UNKNOWN 처리)
    private Long memberId;         // 토큰 안 붙은 경우 대비
    private Long cardId;           // 결제 카드 ID
    private String storeName;      // 가맹점명(숙소/상품명)
}
