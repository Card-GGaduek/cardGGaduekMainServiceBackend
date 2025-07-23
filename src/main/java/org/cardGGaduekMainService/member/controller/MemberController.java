package org.cardGGaduekMainService.member.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.member.dto.MemberJoinDTO;
import org.cardGGaduekMainService.member.dto.MemberLoginDTO;
import org.cardGGaduekMainService.member.service.MemberService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/member/register")
    public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody MemberJoinDTO memberJoinDTO) {
        memberService.addMember(memberJoinDTO);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_CREATE_SUCCESS));
    }


}
