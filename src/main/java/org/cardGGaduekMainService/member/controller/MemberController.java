package org.cardGGaduekMainService.member.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.auth.dto.MemberJoinRequest;
import org.cardGGaduekMainService.member.dto.MemberUpdateDTO;
import org.cardGGaduekMainService.member.dto.MyPageDTO;
import org.cardGGaduekMainService.member.service.MemberService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = "사용자 정보")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    @PostMapping("/api/member/register")
//    @ApiOperation(value = "회원가입", notes = "일반 로그인을 위한 회원가입 API")
//    @ApiResponses(
//            @ApiResponse(code = 200, message = "회원가입 성공")
//    )
//    public ResponseEntity<CustomApiResponse<Void>> registerMember(@ApiParam(value = "이메일, 패스워드, 이름, 전화번호") @RequestBody MemberJoinRequest memberJoinRequest) {
//        memberService.addMember(memberJoinRequest);
//        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.MEMBER_CREATE_SUCCESS));
//    }


//    @PatchMapping("/api/member/{id}")
//    public ResponseEntity<CustomApiResponse<MemberUpdateDTO>> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDTO memberUpdateDTO){
//        memberService.updateMemberInfo(memberUpdateDTO);
//        memberUpdateDTO.setId(id);
//        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.MEMBER_UPDATE_SUCCESS,memberUpdateDTO));
//    }

    @ApiOperation(value = "내 정보 조회", notes = "로그인된 사용자의 정보를 조회 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "내 정보 조회 성공", response = MyPageDTO.class)
    )
    @GetMapping("/api/member/me")
    public ResponseEntity<CustomApiResponse<MyPageDTO>> memberFindById(@AuthenticationPrincipal LoginMember loginMember){
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.MEMBER_FETCH_SUCCESS, memberService.findMyPageInfo(loginMember.getId())));
    }

}
