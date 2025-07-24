package org.cardGGaduekMainService.payment.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import org.cardGGaduekMainService.payment.domain.QRCodeVO;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class QRCodeService {

    public String generateQRCode(QRCodeVO qr) {
        String data = String.format("memberId=%d&cardId=%d&timestamp=%s",
                qr.getMemberId(), qr.getCardId(), qr.getTimestamp());

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            byte[] qrBytes = baos.toByteArray();
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(qrBytes);
        } catch (WriterException | java.io.IOException e) {
            throw new RuntimeException("QR 코드 생성 실패", e);
        }
    }
}
