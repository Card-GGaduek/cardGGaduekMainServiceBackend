package org.cardGGaduekMainService.member.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.common.util.EncryptService;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.dto.MemberFindDTO;
import org.cardGGaduekMainService.member.dto.MemberJoinDTO;
import org.cardGGaduekMainService.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final EncryptService encryptService;


    @Transactional
    @Override
    public void addMember(MemberJoinDTO memberJoinDTO) {

        String encryptedEmail = encryptService.aesEncrypt(memberJoinDTO.getEmail());
        Optional<MemberVO> memberByEmail = Optional.ofNullable(memberMapper.getMemberByEmail(encryptedEmail));

        if(memberByEmail.isPresent()) throw new CustomException(ErrorCode.MEMBER_EMAIL_DUPLICATE);
        else {

            String encryptedPwd = encryptService.encryptPwd(memberJoinDTO.getPassword());
            String encryptedPhone = encryptService.aesEncrypt(memberJoinDTO.getPhone());
//            String encryptedEmail = encryptService.aesEncrypt(memberJoinDTO.getEmail());

            MemberVO memberCreate = MemberVO.builder()
                        .name(memberJoinDTO.getName())
                        .password(encryptedPwd)
                        .phone(encryptedPhone)
                        .email(encryptedEmail)
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
                    .created_at(memberByEmail.get().getCreated_at())
                    .updated_at(memberByEmail.get().getUpdated_at())
                    .deleted_at(memberByEmail.get().getDeleted_at())
                    .is_active(memberByEmail.get().is_active())
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
                    .created_at(memberById.get().getCreated_at())
                    .updated_at(memberById.get().getUpdated_at())
                    .deleted_at(memberById.get().getDeleted_at())
                    .is_active(memberById.get().is_active())
                    .build();

            return memberFind;
        }
    }
}
