package org.cardGGaduekMainService.payment.service;

import org.cardGGaduekMainService.payment.dto.PaymentCompleteForm;

public interface PaymentService {
    void confirmAndRecord(PaymentCompleteForm form, Long loginMemberId);
}
