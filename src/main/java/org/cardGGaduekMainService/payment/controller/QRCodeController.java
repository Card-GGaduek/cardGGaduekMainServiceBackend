package org.cardGGaduekMainService.payment.controller;

import org.cardGGaduekMainService.payment.domain.QRCodeVO;
import org.cardGGaduekMainService.payment.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/payment")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/qr")
    public String generateQRCode(@RequestBody QRCodeVO request) {
        // timestamp는 서버에서 찍어줌
        request.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return qrCodeService.generateQRCode(request);
    }
}
