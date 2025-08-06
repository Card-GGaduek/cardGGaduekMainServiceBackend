package org.cardGGaduekMainService.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberUpdateDTO;
import org.cardGGaduekMainService.member.dto.MyBookingDTO;
import org.cardGGaduekMainService.member.dto.MyPageDTO;

import java.util.List;

@Mapper
public interface MemberMapper {

    void createMember(MemberVO memberVO);
    void createNaverMember(MemberVO memberVO);
    MemberVO getMemberByEmail(String email);
    MemberVO getMemberById(Long id);
    MemberVO getMemberByNaverId(String naverId);
    int updateMember(MemberUpdateDTO memberUpdateDTO);
    List<MyBookingDTO> getBookingsByMemberId(Long memberId);
    MyPageDTO getMyPageInfo(Long memberId);
}
