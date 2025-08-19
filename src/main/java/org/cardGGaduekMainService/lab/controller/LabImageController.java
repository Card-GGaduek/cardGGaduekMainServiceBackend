package org.cardGGaduekMainService.lab.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "실험실 이미지")
@Log4j2
@RestController
@RequestMapping("/api/lab")
public class LabImageController {

    private static final String S3_BASE_URL = "https://cardggaduek.s3.ap-southeast-2.amazonaws.com/image";

    // 행운의 아이템 이미지 URL 반환
    @ApiOperation(value = "행운의 아이템 이미지 조회", notes = "행운의 아이템 이미지 URL을 반환하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "행운 아이템 조회 성공", response = String.class)
    )
    @GetMapping("/fortune/image/{fileName}")
    public ResponseEntity<String> getLuckyItemImageUrl(@PathVariable String fileName) {
        String imageUrl = S3_BASE_URL + "/items/" + fileName + ".png";
        log.info("[{}] 행운 아이템 이미지 URL 반환: {}", SuccessCode.IMAGE_LUCKY_ITEM_SENT.getCode(), imageUrl);
        return ResponseEntity.ok(imageUrl);
    }

    // 소비 분석 이미지 URL 반환
    @ApiOperation(value = "소비 분석 이미지 조회", notes = "소비 분석 이미지 URL을 반환하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "소비 분석 이미지 조회 성공", response = String.class)
    )
    @GetMapping("/analysis/image/{category}")
    public ResponseEntity<String> getAnalysisImageUrl(@PathVariable String category) {
        String imageUrl = S3_BASE_URL + "/analysis/" + category.toLowerCase() + ".png";
        log.info("[{}] 소비 분석 이미지 URL 반환: {}", SuccessCode.IMAGE_ANALYSIS_SENT.getCode(), imageUrl);
        return ResponseEntity.ok(imageUrl);
    }
}
