package org.cardGGaduekMainService.lab.controller;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.common.util.UploadFiles;
import org.cardGGaduekMainService.response.SuccessCode;
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
    private static final String ITEM_PATH = "/Users/yeahy/upload/items";
    private static final String ANALYSIS_PATH = "/Users/yeahy/upload/analysis";

    // 1. 행운의 아이템 이미지 제공
    @GetMapping("/fortune/image/{fileName}")
    public void getLuckyItemImage(@PathVariable String fileName, HttpServletResponse response) {
        File file = new File(ITEM_PATH, fileName + ".png");
        if (!file.exists()) {
            log.warn("🔍 [행운 이미지 없음] 기본 이미지로 대체: {}", fileName);
            file = new File(ITEM_PATH, "default.png");
        } else {
            log.info("✅ [{}] {}", SuccessCode.IMAGE_LUCKY_ITEM_SENT.getCode(), SuccessCode.IMAGE_LUCKY_ITEM_SENT.getMessage());
        }

        UploadFiles.downloadImage(response, file);
    }

    // 2. 소비 성향 분석 이미지 제공
    @GetMapping("/analysis/image/{category}")
    public void getAnalysisImage(@PathVariable String category, HttpServletResponse response) {
        File file = new File(ANALYSIS_PATH, category.toLowerCase() + ".png");
        if (!file.exists()) {
            log.warn("🔍 [소비 분석 이미지 없음] 기본 이미지로 대체: {}", category);
            file = new File(ANALYSIS_PATH, "non.png");
        } else {
            log.info("✅ [{}] {}", SuccessCode.IMAGE_ANALYSIS_SENT.getCode(), SuccessCode.IMAGE_ANALYSIS_SENT.getMessage());
        }

        UploadFiles.downloadImage(response, file);
    }
}
