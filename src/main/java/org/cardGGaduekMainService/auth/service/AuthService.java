package org.cardGGaduekMainService.auth.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.MemberAuthProvider;
import org.cardGGaduekMainService.auth.TokenResponse;
import org.cardGGaduekMainService.common.util.EncryptService;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberAuthProvider memberAuthProvider;
    private final PasswordEncoder passwordEncoder;
    private final EncryptService encryptService;
    private final MemberMapper memberMapper;


    public TokenResponse memberLogin(String email, String pwd) {
//        Optional<Member> findMember = memberRepository.findByEmail(email);
        String encryptedEmail = encryptService.aesEncrypt(email);
        Optional<MemberVO> memberByEmail = Optional.ofNullable(memberMapper.getMemberByEmail(encryptedEmail));

        if (!memberByEmail.isPresent()) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

        MemberVO member = memberByEmail.get();

        if (!passwordEncoder.matches(pwd, member.getPassword())) throw new CustomException(ErrorCode.INVALID_PASSWORD);

        String accessToken = memberAuthProvider.createToken(member.getId());

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);

        return tokenResponse;

    }

}
