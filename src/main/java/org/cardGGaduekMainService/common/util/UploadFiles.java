package org.cardGGaduekMainService.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UploadFiles {
    public static void downloadImage(HttpServletResponse response, File file) {
        try {
            response.setContentType("image/png");
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            Files.copy(file.toPath(), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException("이미지 전송 실패", e);
        }
    }
}
