package org.cardGGaduekMainService.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.auth.MemberAuthProvider;
import org.cardGGaduekMainService.auth.TokenResponse;
import org.cardGGaduekMainService.auth.dto.NaverProfileResponse;
import org.cardGGaduekMainService.common.util.EncryptService;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.mapper.MemberMapper;
import org.cardGGaduekMainService.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
@PropertySource("classpath:application-secret.properties")
@RequiredArgsConstructor
public class NaverLoginService {
    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private String naverTokenApiUrl = "https://nid.naver.com/oauth2.0/token";
    private String naverMemberInfoApiUrl = "https://openapi.naver.com/v1/nid/me";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MemberMapper memberMapper;
    private final MemberAuthProvider memberAuthProvider;
    private final MemberService memberService;
    private final EncryptService encryptService;

    public TokenResponse naverLogin(String code, String state) {
        NaverProfileResponse naverProfileResponse = findNaverProfile(code, state);

        Optional<MemberVO> memberByNaverId = Optional.ofNullable(memberMapper.getMemberByNaverId(naverProfileResponse.getId()));
        if (memberByNaverId.isPresent()) {
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(memberAuthProvider.createToken(memberByNaverId.get().getId()));
            return tokenResponse;
        } else {

            Optional<MemberVO> memberByEmail = Optional.ofNullable(memberMapper.getMemberByEmail(naverProfileResponse.getEmail()));
            if (memberByEmail.isPresent()) {
                throw new CustomException(ErrorCode.MEMBER_EMAIL_DUPLICATE);
            }

            String encryptedEmail = encryptService.aesEncrypt(naverProfileResponse.getEmail());
            String encryptedPhone = encryptService.aesEncrypt(naverProfileResponse.getMobile());


            MemberVO memberCreate = MemberVO.builder()
                    .name(naverProfileResponse.getName())
                    .phone(encryptedPhone)
                    .email(encryptedEmail)
                    .naverUserId(naverProfileResponse.getId())
                    .build();


            memberMapper.createNaverMember(memberCreate);

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(memberAuthProvider.createToken(memberCreate.getId()));
            return tokenResponse;

        }


    }




    private NaverProfileResponse findNaverProfile(String code, String state) {
            String url = naverTokenApiUrl +
                    "?grant_type=authorization_code" +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&code=" + code +
                    "&state=" + state;

            // 2. HttpRequest 객체 생성 (GET 방식)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                // 4. 응답 파싱
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = objectMapper.readTree(response.body());

                    String accessToken = jsonNode.get("access_token").asText();
                    String refreshToken = jsonNode.get("refresh_token").asText();

                    // 여기서 저장 또는 리턴
                    System.out.println("Access Token: " + accessToken);
//                    System.out.println("Refresh Token: " + refreshToken);

                    NaverProfileResponse naverMemberInfo = getNaverMemberInfo(accessToken);
                    System.out.println(naverMemberInfo);
                    return naverMemberInfo;

                } else {
                    System.err.println("네이버 토큰 요청 실패: " + response.body());

                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        throw new CustomException(ErrorCode.INVALID_TOKEN);
    }

//    public String getNaverMemberInfo(String accessToken) {
//        String url = naverMemberInfoApiUrl;
//
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("Authorization", "Bearer " + accessToken);
//
//        String myInfo = HttpClientUtil.get(url, requestHeaders);
//
//        return myInfo;
//
//
//    }

    private NaverProfileResponse getNaverMemberInfo(String accessToken) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(naverMemberInfoApiUrl))
                    .GET()
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode rootNode = objectMapper.readTree(response.body());
                JsonNode responseNode = rootNode.get("response");

                return objectMapper.treeToValue(responseNode, NaverProfileResponse.class);
            } else {
                System.err.println("네이버 사용자 정보 요청 실패: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.NAVER_LOGIN_FAILED);
        }

        return null;
    }








}
