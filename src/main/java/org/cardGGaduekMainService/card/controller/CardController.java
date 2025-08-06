package org.cardGGaduekMainService.card.controller;

import com.google.protobuf.Api;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.card.dto.*;
import org.cardGGaduekMainService.common.s3.S3Uploader;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.card.service.CardService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final S3Uploader s3Uploader;

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/{cardId}/image")
    public ResponseEntity<ApiResponse<CardImageDTO>> updateCardImage(@PathVariable Long cardId,
                                                                     @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {   
            // ✅ 이미지가 없으면 기본 이미지로 변경
            if (imageFile == null || imageFile.isEmpty()) {
                cardService.updateCardImage(cardId, null); // custom_image_url 초기화
                return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_IMAGE_UPDATE, new CardImageDTO(null)));
            }

            // ✅ 이미지가 있으면 S3에 업로드
            String imageUrl = s3Uploader.upload(imageFile, "image/cardImage"); // 🔥 이 경로로 저장
            cardService.updateCardImage(cardId, imageUrl);
            return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_IMAGE_UPDATE, new CardImageDTO(imageUrl)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(ErrorCode.IMAGE_UPLOAD_FAILED));
        }
    }

    @GetMapping("/front")
    public ResponseEntity<ApiResponse<List<CardFrontDTO>>> getCardFrontInfo(@AuthenticationPrincipal LoginMember loginMember) {
        List<CardFrontDTO> cards = cardService.getCardFrontInfo(loginMember.getId());
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_FRONT_FETCH_SUCCESS, cards));
    }

    @GetMapping("/back/{cardId}")
    public ResponseEntity<ApiResponse<CardBackDTO>> getCardDetail(@PathVariable Long cardId) {
        CardBackDTO detail = cardService.getCardDetail(cardId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_BACK_FETCH_SUCCESS, detail));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CardDTO>>> getCardsByMember(@AuthenticationPrincipal LoginMember loginMember){
        List<CardDTO> cards = cardService.findCardByMember(loginMember.getId());
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cards));
    }
//    @GetMapping("/{memberId}")
//    public ResponseEntity<List<CardVO>> getCardsByMember(@PathVariable Long memberId) {
//        List<CardVO> cards = cardService.findCardsByMember(memberId);
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, cards));
//    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<MyCardDTO>>> getMyCards(@AuthenticationPrincipal LoginMember loginMember){
        Long memberId = loginMember.getId();
        List<MyCardDTO> myCards = cardService.findMyCards(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, myCards));
    }
}
