package org.cardGGaduekMainService.codef.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.codef.dto.CodefTokenResponse;
import org.cardGGaduekMainService.codef.dto.cardInfo.MyCardResponse;
import org.cardGGaduekMainService.codef.dto.request.AccountRequest;
import org.cardGGaduekMainService.codef.dto.request.CodefAccountRegisterRequest;
import org.cardGGaduekMainService.codef.service.CodefService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "CODEF 연동")
@RestController
@RequiredArgsConstructor
public class CodefController {

    private final CodefService codefService;

    @ApiOperation(value = "CODEF 토큰 발급", notes = "CODEF 토큰 발급 요청 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "CODEF 토큰 발급 성공", response = CodefTokenResponse.class)
    )
    @GetMapping("/api/codef/token")
    public ResponseEntity<CustomApiResponse<CodefTokenResponse>> getCodefToken() {
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CODEF_TOKEN_FETCH_SUCCESS, codefService.getCodefToken()));
    }

    @ApiOperation(value = "카드사 연동", notes = "카드사에 연동하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "카드사 연동 성공")
    )
    @PostMapping("/api/codef/account/register")
    public ResponseEntity<CustomApiResponse<Void>> getConnectedId(@AuthenticationPrincipal LoginMember loginMember, @ApiParam(value = "카드사 코드, 카드사 홈페이지 아이디/비밀번호") @RequestBody AccountRequest accountRequest) {

        CodefAccountRegisterRequest codefAccountRegisterRequest = new CodefAccountRegisterRequest();
        List<AccountRequest> accountRequestList = new ArrayList<>();
        accountRequestList.add(accountRequest);
        codefAccountRegisterRequest.setAccountRequestList(accountRequestList);

        codefService.registerAccount(loginMember.getId(), codefAccountRegisterRequest);
        codefService.getMyCardList(loginMember.getId());

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CODEF_ACCOUNT_ADD_SUCCESS));
    }


//    @GetMapping("/api/codef/card/my/detail")
//    public ResponseEntity<ApiResponse<List<MyCardDetailResponse>>> getMyCardDetailList(@AuthenticationPrincipal LoginMember loginMember, @RequestParam String orgCode) {
//
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, codefService.getMyCardDetailList(loginMember.getId(), orgCode)));
//
//    }

    @ApiOperation(value = "내 카드 목록조회", notes = "카드사에 등록된 내 카드 목록을 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "내 카드 목록 조회 성공", response = MyCardResponse.class, responseContainer = "List")
    )
    @GetMapping("/api/codef/card/my/all")
    public ResponseEntity<CustomApiResponse<List<MyCardResponse>>> getMyCardList(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, codefService.getMyCardList(loginMember.getId())));
    }


//    @GetMapping("/api/codef/card/my")
//    public ResponseEntity<CustomApiResponse<List<MyCardResponse>>> getMyCardListByOrganization(@AuthenticationPrincipal LoginMember loginMember, @ApiParam(value = "카드사 코드") @RequestParam String orgCode) {
//
//        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, codefService.getMyCardListByOrganization(loginMember.getId(), orgCode)));
//    }





}
