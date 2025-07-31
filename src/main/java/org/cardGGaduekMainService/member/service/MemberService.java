package org.cardGGaduekMainService.member.service;

import org.cardGGaduekMainService.member.dto.MemberFindDTO;
import org.cardGGaduekMainService.auth.dto.MemberJoinRequest;
import org.cardGGaduekMainService.member.dto.MemberUpdateDTO;

public interface MemberService {

    void addMember(MemberJoinRequest memberJoinRequest);
    MemberFindDTO findMemberByEmail(String email);
    MemberFindDTO findMemberById(Long id);
    void updateMemberInfo(MemberUpdateDTO memberUpdateDTO);

}
