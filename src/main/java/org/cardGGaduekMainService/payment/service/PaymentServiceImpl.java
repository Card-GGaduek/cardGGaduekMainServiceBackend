package org.cardGGaduekMainService.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.payment.dto.PaymentCompleteForm;
import org.cardGGaduekMainService.product.booking.service.BookingService;
import org.cardGGaduekMainService.transaction.dto.TransactionDTO;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionCategory;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionMethod;
import org.cardGGaduekMainService.transaction.domain.enums.TransactionStatus;
import org.cardGGaduekMainService.transaction.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final IamportClient iamportClient;
    private final BookingService bookingService;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public void confirmAndRecord(PaymentCompleteForm form, Long loginMemberId) {
        Payment payment = fetchPayment(form.getImp_uid());

        if (!"paid".equalsIgnoreCase(payment.getStatus())) {
            throw new IllegalStateException("결제 상태가 paid 아님: " + payment.getStatus());
        }
        BigDecimal paidAmount = payment.getAmount();
        if (form.getAmount() == null || paidAmount.compareTo(form.getAmount()) != 0) {
            throw new IllegalStateException("결제 금액 불일치: paid=" + paidAmount + ", req=" + form.getAmount());
        }

        LocalDateTime paidAt = payment.getPaidAt() != null
                ? payment.getPaidAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : LocalDateTime.now();

        // 요구사항에 맞춰 고정
        TransactionCategory category = TransactionCategory.ACCOMODATION; // ← transaction_category_code 에 들어감
        String storeCategory = "HOTEL";                                  // ← store_category 에 들어감

        // ✅ DB not null 컬럼용 storeCategory 값 지정
        //    - 프론트 form.category가 오면 그걸 사용
        //    - 없으면 'ACCOMMODATION' 같은 기본값으로

        TransactionDTO dto = TransactionDTO.builder()
                .memberId(firstNonNull(loginMemberId, form.getMemberId()))
                .cardId(form.getCardId())
                .storeName(nvl(form.getStoreName(), payment.getName()))
                .storeCategory(storeCategory)                // ✅ 여기!
                .amount(paidAmount)
                .transactionCategory(category)
                .transactionStatus(TransactionStatus.APPROVED)
                .transactionMethod(TransactionMethod.CREDIT)
                .date(paidAt)
                .approvalCode(nvl(payment.getApplyNum(), "00000000"))
                .memo("imp_uid=" + form.getImp_uid() + ", merchant_uid=" + form.getMerchant_uid())
                .build();

        transactionService.createTransaction(dto);

        if (form.getBookingId() != null) {
            bookingService.updateBookingStatus(form.getBookingId(), "CONFIRMED");
        }
    }


    private Payment fetchPayment(String impUid) {
        try {
            IamportResponse<Payment> resp = iamportClient.paymentByImpUid(impUid);
            if (resp.getResponse() == null) {
                throw new IllegalStateException("결제 정보를 찾을 수 없음: imp_uid=" + impUid);
            }
            return resp.getResponse();
        } catch (IamportResponseException | IOException e) {
            throw new RuntimeException("PortOne 결제 조회 실패: " + e.getMessage(), e);
        }
    }

    private static <T> T firstNonNull(T a, T b) { return a != null ? a : b; }
    private static String nvl(String v, String d) { return (v == null || v.isBlank()) ? d : v; }


}
