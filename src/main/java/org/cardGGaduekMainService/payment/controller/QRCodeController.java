package org.cardGGaduekMainService.payment.controller;

import org.cardGGaduekMainService.payment.domain.QRCodeVO;
import org.cardGGaduekMainService.payment.service.QRCodeService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/payment")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/qr")
    public ResponseEntity<ApiResponse<String>> generateQRCode(@RequestBody QRCodeVO request) {
        // 서버에서 timestamp 자동 생성
        request.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        String qrBase64 = qrCodeService.generateQRCode(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.QR_CREATE_SUCCESS, qrBase64));
    }
}
