package org.cardGGaduekMainService.codef.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.codef.dto.CodefTokenResponse;
import org.cardGGaduekMainService.codef.dto.cardDetailInfo.MyCardDetailResponse;
import org.cardGGaduekMainService.codef.dto.cardInfo.MyCardResponse;
import org.cardGGaduekMainService.codef.dto.request.AccountRequest;
import org.cardGGaduekMainService.codef.dto.request.CodefAccountRegisterRequest;
import org.cardGGaduekMainService.codef.dto.response.CodefAccountRegisterResponse;
import org.cardGGaduekMainService.codef.service.CodefService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CodefController {

    private final CodefService codefService;

    @GetMapping("/api/codef/token")
    public ResponseEntity<ApiResponse<CodefTokenResponse>> getCodefToken() {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CODEF_TOKEN_FETCH_SUCCESS, codefService.getCodefToken()));
    }

    @PostMapping("/api/codef/account/register")
    public ResponseEntity<ApiResponse<Void>> getConnectedId(@AuthenticationPrincipal LoginMember loginMember, @RequestBody AccountRequest accountRequest) {

        CodefAccountRegisterRequest codefAccountRegisterRequest = new CodefAccountRegisterRequest();
        List<AccountRequest> accountRequestList = new ArrayList<>();
        accountRequestList.add(accountRequest);
        codefAccountRegisterRequest.setAccountRequestList(accountRequestList);

        codefService.registerAccount(loginMember.getId(), codefAccountRegisterRequest);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CODEF_ACCOUNT_ADD_SUCCESS));
    }


//    @GetMapping("/api/codef/card/my/detail")
//    public ResponseEntity<ApiResponse<List<MyCardDetailResponse>>> getMyCardDetailList(@AuthenticationPrincipal LoginMember loginMember, @RequestParam String orgCode) {
//
//        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, codefService.getMyCardDetailList(loginMember.getId(), orgCode)));
//
//    }

    @GetMapping("/api/codef/card/my/all")
    public ResponseEntity<ApiResponse<List<MyCardResponse>>> getMyCardList(@AuthenticationPrincipal LoginMember loginMember) {
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, codefService.getMyCardList(loginMember.getId())));
    }

    @GetMapping("/api/codef/card/my")
    public ResponseEntity<ApiResponse<List<MyCardResponse>>> getMyCardListByOrganization(@AuthenticationPrincipal LoginMember loginMember, @RequestParam String orgCode) {

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.CARD_SUMMARY_FETCH_SUCCESS, codefService.getMyCardListByOrganization(loginMember.getId(), orgCode)));
    }





}
