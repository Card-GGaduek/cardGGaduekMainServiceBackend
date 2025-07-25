package org.cardGGaduekMainService.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberJoinDTO;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    void createMember(MemberVO memberVO);
    void createNaverMember(MemberVO memberVO);
    MemberVO getMemberByEmail(String email);
    MemberVO getMemberById(Long id);
    MemberVO getMemberByNaverId(String naverId);
}
