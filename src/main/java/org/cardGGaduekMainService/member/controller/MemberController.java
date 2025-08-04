package org.cardGGaduekMainService.member.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.dto.MemberJoinRequest;
import org.cardGGaduekMainService.member.dto.MemberUpdateDTO;
import org.cardGGaduekMainService.member.service.MemberService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/member/register")
    public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.addMember(memberJoinRequest);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_CREATE_SUCCESS));
    }

    @PatchMapping("/api/member/{id}")
    public ResponseEntity<ApiResponse<MemberUpdateDTO>> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDTO memberUpdateDTO){
        memberService.updateMemberInfo(memberUpdateDTO);
        memberUpdateDTO.setId(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_UPDATE_SUCCESS,memberUpdateDTO));
    }

}
