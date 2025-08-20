package org.cardGGaduekMainService.card.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.auth.dto.TokenResponse;
import org.cardGGaduekMainService.card.dto.*;
import org.cardGGaduekMainService.common.s3.S3Uploader;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.card.service.CardService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "카드 관리")
@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final S3Uploader s3Uploader;

    @ApiOperation(value = "카드 삭제", notes = "카드 삭제 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카드 삭제 성공")
    )
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@ApiParam(value = "카드ID") @PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @ApiOperation(value = "내 카드 조회(결제용)", notes = "로그인된 사용자의 결제할 카드를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "내 카드 조회 성공", response = CardDTO.class, responseContainer = "List" )
    )
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<CardDTO>>> getCardsByMember(@AuthenticationPrincipal LoginMember loginMember){
        List<CardDTO> cards = cardService.findCardByMember(loginMember.getId());
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cards));
    }

    @ApiOperation(value = "카드 이미지 업데이트", notes = "카드 커스텀 이미지 업데이트 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "OK")
    )
    @PostMapping("/{cardId}/image")
    public ResponseEntity<CustomApiResponse<CardImageDTO>> updateCardImage(
            @ApiParam(value = "카드 이미지 파일")
            @PathVariable Long cardId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {   
            // 이미지가 없으면 기본 이미지로 변경
            if (imageFile == null || imageFile.isEmpty()) {
                cardService.updateCardImage(cardId, null); // custom_image_url 초기화
                return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_IMAGE_UPDATE, new CardImageDTO(null)));
            }

            // 이미지가 있으면 S3에 업로드
            String imageUrl = s3Uploader.upload(imageFile, "image/cardImage");
            cardService.updateCardImage(cardId, imageUrl);
            return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_IMAGE_UPDATE, new CardImageDTO(imageUrl)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomApiResponse.error(ErrorCode.IMAGE_UPLOAD_FAILED));
        }
    }

//    @ApiOperation(value = "내 카드 조회", notes = "로그인된 사용자의 카드를 조회하는 API")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "내 카드 조회 성공", response = CardDTO.class, responseContainer = "List" )
//    )
//    @GetMapping
//    public ResponseEntity<CustomApiResponse<List<CardDTO>>> getCardsByMember(@AuthenticationPrincipal LoginMember loginMember){
//        List<CardDTO> cards = cardService.findCardByMember(loginMember.getId());
//        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARDPRODUCT_FETCH_SUCCESS, cards));
//    }

    @ApiOperation(value = "내 카드 조회", notes = "로그인된 사용자의 카드를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "내 카드 조회 성공", response = CardDTO.class, responseContainer = "List" )
    )
    @GetMapping("/my")
    public ResponseEntity<CustomApiResponse<List<MyCardDTO>>> getMyCards(@AuthenticationPrincipal LoginMember loginMember){
        Long memberId = loginMember.getId();
        List<MyCardDTO> myCards = cardService.findMyCards(memberId);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, myCards));
    }

    @GetMapping("/front")
    public ResponseEntity<CustomApiResponse<List<CardFrontDTO>>> getCardFrontInfo(@AuthenticationPrincipal LoginMember loginMember) {
        Long memberId = loginMember.getId();
        List<CardFrontDTO> cards = cardService.getCardFrontInfo(memberId);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_FRONT_FETCH_SUCCESS, cards));
    }
}
