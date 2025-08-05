package org.cardGGaduekMainService.lab.controller;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.common.util.UploadFiles;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Log4j2
@RestController
@RequestMapping("/api/lab")
public class LabImageController {

    private static final String S3_BASE_URL = "https://cardggaduek.s3.ap-southeast-2.amazonaws.com/image";


    // 행운의 아이템 이미지 URL 반환
    @GetMapping("/fortune/image/{fileName}")
    public ResponseEntity<String> getLuckyItemImageUrl(@PathVariable String fileName) {
        String imageUrl = S3_BASE_URL + "/items/" + fileName + ".png";
        log.info("[{}] 행운 아이템 이미지 URL 반환: {}", SuccessCode.IMAGE_LUCKY_ITEM_SENT.getCode(), imageUrl);
        return ResponseEntity.ok(imageUrl);
    }

    // 소비 분석 이미지 URL 반환
    @GetMapping("/analysis/image/{category}")
    public ResponseEntity<String> getAnalysisImageUrl(@PathVariable String category) {
        String imageUrl = S3_BASE_URL + "/analysis/" + category.toLowerCase() + ".png";
        log.info("[{}] 소비 분석 이미지 URL 반환: {}", SuccessCode.IMAGE_ANALYSIS_SENT.getCode(), imageUrl);
        return ResponseEntity.ok(imageUrl);
    }
}
