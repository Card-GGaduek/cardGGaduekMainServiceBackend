package org.cardGGaduekMainService.member.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.common.util.EncryptService;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberFindDTO;
import org.cardGGaduekMainService.auth.dto.MemberJoinRequest;
import org.cardGGaduekMainService.member.dto.MemberUpdateDTO;
import org.cardGGaduekMainService.member.dto.MyPageDTO;
import org.cardGGaduekMainService.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final EncryptService encryptService;


    @Transactional
    @Override
    public void addMember(MemberJoinRequest memberJoinRequest) {

        String encryptedEmail = encryptService.aesEncrypt(memberJoinRequest.getEmail());
        Optional<MemberVO> memberByEmail = Optional.ofNullable(memberMapper.getMemberByEmail(encryptedEmail));

        if(memberByEmail.isPresent()) throw new CustomException(ErrorCode.MEMBER_EMAIL_DUPLICATE);
        else {

            String encryptedPwd = encryptService.encryptPwd(memberJoinRequest.getPassword());
            String encryptedPhone = encryptService.aesEncrypt(memberJoinRequest.getPhone());
//            String encryptedEmail = encryptService.aesEncrypt(memberJoinDTO.getEmail());

            MemberVO memberCreate = MemberVO.builder()
                        .name(memberJoinRequest.getName())
                        .password(encryptedPwd)
                        .phone(encryptedPhone)
                        .email(encryptedEmail)
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

            memberMapper.createMember(memberCreate);
        }


    }

    @Override
    public MemberFindDTO findMemberByEmail(String email) {

        String encryptedEmail = encryptService.aesEncrypt(email);
        Optional<MemberVO> memberByEmail = Optional.ofNullable(memberMapper.getMemberByEmail(encryptedEmail));

        if (!memberByEmail.isPresent()) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        else {
            String decryptedPhone = encryptService.aesDecrypt(memberByEmail.get().getPhone());
            String decryptedEmail = encryptService.aesDecrypt(memberByEmail.get().getEmail());

            MemberFindDTO memberFind = MemberFindDTO.builder()
                    .id(memberByEmail.get().getId())
                    .name(memberByEmail.get().getName())
                    .phone(decryptedPhone)
                    .email(decryptedEmail)
                    .createdAt(memberByEmail.get().getCreatedAt())
                    .updatedAt(memberByEmail.get().getUpdatedAt())
                    .deletedAt(memberByEmail.get().getDeletedAt())
                    .isActive(memberByEmail.get().isActive())
                    .build();

            return memberFind;

        }


    }

    @Override
    public MemberFindDTO findMemberById(Long id) {
        Optional<MemberVO> memberById = Optional.ofNullable(memberMapper.getMemberById(id));
        if (!memberById.isPresent()) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        else {
            String decryptedPhone = encryptService.aesDecrypt(memberById.get().getPhone());
            String decryptedEmail = encryptService.aesDecrypt(memberById.get().getEmail());

            MemberFindDTO memberFind = MemberFindDTO.builder()
                    .id(memberById.get().getId())
                    .name(memberById.get().getName())
                    .phone(decryptedPhone)
                    .email(decryptedEmail)
                    .createdAt(memberById.get().getCreatedAt())
                    .updatedAt(memberById.get().getUpdatedAt())
                    .deletedAt(memberById.get().getDeletedAt())
                    .isActive(memberById.get().isActive())
                    .build();

            return memberFind;
        }
    }

    public void updateMemberInfo(MemberUpdateDTO memberUpdateDTO){
        if(memberUpdateDTO.getEmail() != null && !memberUpdateDTO.getEmail().isEmpty()){
            String hashedEmail = encryptService.aesEncrypt(memberUpdateDTO.getEmail());
            memberUpdateDTO.setEmail(hashedEmail);
        }

        if(memberUpdateDTO.getPhone() != null && !memberUpdateDTO.getPhone().isEmpty()){
            String encryptedPhone = encryptService.aesEncrypt(memberUpdateDTO.getPhone());
            memberUpdateDTO.setPhone(encryptedPhone);
        }

        memberMapper.updateMember(memberUpdateDTO);
    }


    @Override
    public MyPageDTO findMyPageInfo(Long memberId) {
        if (memberMapper.getMemberById(memberId) == null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

        MyPageDTO myPageInfo = memberMapper.getMyPageInfo(memberId);
        if (myPageInfo == null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

        myPageInfo.setEmail(encryptService.aesDecrypt(myPageInfo.getEmail()));
        myPageInfo.setPhone(encryptService.aesDecrypt(myPageInfo.getPhone()));
        return myPageInfo;
    }
}