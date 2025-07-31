package org.cardGGaduekMainService.member.service;

import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberFindDTO;
import org.cardGGaduekMainService.member.dto.MemberJoinDTO;
import org.cardGGaduekMainService.member.dto.MemberUpdateDTO;

import java.util.Optional;

public interface MemberService {

    void addMember(MemberJoinDTO memberJoinDTO);
    MemberFindDTO findMemberByEmail(String email);
    MemberFindDTO findMemberById(Long id);
    void updateMemberInfo(MemberUpdateDTO memberUpdateDTO);

}
