package org.cardGGaduekMainService.codef.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.mapper.CardProductMapper;
import org.cardGGaduekMainService.cardProduct.service.CardProductService;
import org.cardGGaduekMainService.codef.dto.cardDetailInfo.MyCardDetailResponse;
import org.cardGGaduekMainService.codef.dto.cardInfo.MyCardResponse;
import org.cardGGaduekMainService.codef.dto.request.CodefAccountAddRequest;
import org.cardGGaduekMainService.codef.dto.request.CodefAccountRegisterRequest;
import org.cardGGaduekMainService.codef.dto.CodefTokenResponse;
import org.cardGGaduekMainService.codef.dto.response.CodefAccountRegisterResponse;
import org.cardGGaduekMainService.codef.enums.CodefEndPoint;
import org.cardGGaduekMainService.codef.mapper.CodefMapper;
import org.cardGGaduekMainService.common.util.HttpClientUtil;
import org.cardGGaduekMainService.common.util.RSAUtil;
import org.cardGGaduekMainService.common.util.RedisUtil;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
;import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodefService {

    private final CodefTokenProvider codefTokenProvider;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RSAUtil rsaUtil;
    private final CodefMapper codefMapper;
    private final CardProductMapper cardProductMapper;
    private final CardMapper cardMapper;

    public CodefTokenResponse getCodefToken() {

        CodefTokenResponse response = codefTokenProvider.getCodefToken();
        redisUtil.setDataWithTTL("CodefToken", response.getAccessToken(), response.getExpiresIn());
        return response;

    }

    @Transactional
    public void registerAccount(Long memberId, CodefAccountRegisterRequest codefAccountRegisterRequest) {

        String connectedId = codefMapper.getConnectedId(memberId);
        System.out.println(connectedId);

        if (connectedId == null || connectedId.isEmpty()) registerConnectedIdAndAccount(memberId, codefAccountRegisterRequest);
        else {

            System.out.println("진입");

            List<String> organizationCodeByConnectedId = codefMapper.getOrganizationCodeByConnectedId(connectedId);
            String organizationCode = codefAccountRegisterRequest.getAccountRequestList().get(0).getOrganization();
            if (organizationCodeByConnectedId.contains(organizationCode)) throw new CustomException(ErrorCode.CODEF_ORGANIZATION_ALREADY_CONNECTED);

            CodefAccountAddRequest codefAccountAddRequest = new CodefAccountAddRequest(codefAccountRegisterRequest.getAccountRequestList(), connectedId);
            registerAccountByConnectedId(codefAccountAddRequest);
        }



    }



    public List<MyCardDetailResponse> getMyCardDetailList(Long memberId, String orgCode) {
        String accessToken = redisUtil.getData("CodefToken");

        if (accessToken == null) {
            accessToken = getCodefToken().getAccessToken();
        }

        String connectedId = codefMapper.getConnectedId(memberId);
        List<String> orgCodeList = codefMapper.getOrganizationCodeByConnectedId(connectedId);

        if (!orgCodeList.contains(orgCode)) throw new CustomException(ErrorCode.CODEF_ORGANIZATION_NOT_CONNECTED);

        String url = CodefEndPoint.CODEF_MY_CARD_DETAIL_LIST.getPath();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        List<MyCardDetailResponse> myCardDetailList = new ArrayList<>();

        try {

            String jsonRequestBody =
                    "{\"organization\":\"" + orgCode + "\", \"connectedId\":\"" + connectedId + "\"}";
            String response = (String) HttpClientUtil.post(url, headers, jsonRequestBody);
            String decodedJson = URLDecoder.decode(response, "UTF-8");
            System.out.println(decodedJson);
            MyCardDetailResponse myCardDetailResponse = objectMapper.readValue(decodedJson, MyCardDetailResponse.class);
            myCardDetailList.add(myCardDetailResponse);

            return myCardDetailList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
        }






    }

    public List<MyCardResponse> getMyCardList(Long memberId) {
        String accessToken = redisUtil.getData("CodefToken");

        if (accessToken == null) {
            accessToken = getCodefToken().getAccessToken();
        }

        String connectedId = codefMapper.getConnectedId(memberId);

        if (connectedId == null || connectedId.isEmpty()) throw new CustomException(ErrorCode.CODEF_MEMBER_NOT_REGISTERED);

        List<String> orgCodeList = codefMapper.getOrganizationCodeByConnectedId(connectedId);

        if (orgCodeList.isEmpty()) throw new CustomException(ErrorCode.CODEF_ORGANIZATION_NOT_CONNECTED);

        String url = CodefEndPoint.CODEF_MY_CARD_LIST.getPath();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        List<MyCardResponse> myCardResponseList = new ArrayList<>();

        try {
            orgCodeList.forEach(orgCode -> {

                try {
                    String jsonRequestBody =
                            "{\"organization\":\"" + orgCode + "\", \"connectedId\":\"" + connectedId + "\"}";
                    String response = (String) HttpClientUtil.post(url, headers, jsonRequestBody);
                    String decodedJson = URLDecoder.decode(response, "UTF-8");

                    JsonNode root = objectMapper.readTree(decodedJson);
                    System.out.println(root);
                    String code = root.get("result").get("code").asText();

                    System.out.println(code);

                    if (code.equals("CF-00000")) {

                        JsonNode dataNode = root.path("data");
                        System.out.println(dataNode);
                        if (dataNode.isArray()) {
                            for (JsonNode element : dataNode) {
                                MyCardResponse myCardResponse = objectMapper.treeToValue(element, MyCardResponse.class);
                                myCardResponse.setOrganizationCode(orgCode);
                                myCardResponseList.add(myCardResponse);
                            }

                        } else {
                            MyCardResponse myCardResponse = objectMapper.treeToValue(dataNode, MyCardResponse.class);
                            myCardResponse.setOrganizationCode(orgCode);
                            myCardResponseList.add(myCardResponse);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CustomException(ErrorCode.API_REQUEST_FAILED);
                }

            });

            myCardResponseList.forEach(myCardResponse -> {
                addMyCardInfoToDatabase(memberId, myCardResponse);
            });

            return myCardResponseList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
        }



    }


    public List<MyCardResponse> getMyCardListByOrganization(Long memberId, String orgCode) {
        String accessToken = redisUtil.getData("CodefToken");

        if (accessToken == null) {
            accessToken = getCodefToken().getAccessToken();
        }

        String connectedId = codefMapper.getConnectedId(memberId);

        if (connectedId == null || connectedId.isEmpty()) throw new CustomException(ErrorCode.CODEF_MEMBER_NOT_REGISTERED);

        List<String> orgCodeList = codefMapper.getOrganizationCodeByConnectedId(connectedId);

        if (!orgCodeList.contains(orgCode)) throw new CustomException(ErrorCode.CODEF_ORGANIZATION_NOT_CONNECTED);

        String url = CodefEndPoint.CODEF_MY_CARD_LIST.getPath();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        List<MyCardResponse> myCardResponseList = new ArrayList<>();

        try {
            String jsonRequestBody =
                    "{\"organization\":\"" + orgCode + "\", \"connectedId\":\"" + connectedId + "\"}";
            String response = (String) HttpClientUtil.post(url, headers, jsonRequestBody);
            String decodedJson = URLDecoder.decode(response, "UTF-8");

            JsonNode root = objectMapper.readTree(decodedJson);
            System.out.println(root);
            String code = root.get("result").get("code").asText();

            System.out.println(code);

            if (code.equals("CF-00000")) {

                JsonNode dataNode = root.path("data");
                System.out.println(dataNode);
                if (dataNode.isArray()) {
                    for (JsonNode element : dataNode) {
                        MyCardResponse myCardResponse = objectMapper.treeToValue(element, MyCardResponse.class);
                        myCardResponse.setOrganizationCode(orgCode);
                        myCardResponseList.add(myCardResponse);
                    }

                } else {
                    MyCardResponse myCardResponse = objectMapper.treeToValue(dataNode, MyCardResponse.class);
                    myCardResponse.setOrganizationCode(orgCode);
                    myCardResponseList.add(myCardResponse);
                }

            }

            myCardResponseList.forEach(myCardResponse -> {

            });

            return myCardResponseList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
        }




    }


    private void registerConnectedIdAndAccount(Long memberId, CodefAccountRegisterRequest codefAccountRegisterRequest) {
        String accessToken = redisUtil.getData("CodefToken");

        if (accessToken == null) {
            accessToken = getCodefToken().getAccessToken();
        }

        String url = CodefEndPoint.CODEF_REGISTER_ACCOUNT.getPath();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        try {
            codefAccountRegisterRequest.getAccountRequestList().forEach(accountRequest -> {
                try {
                    accountRequest.setPassword(rsaUtil.rsaEncrypt(accountRequest.getPassword()));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
            String jsonRequestBody = objectMapper.writeValueAsString(codefAccountRegisterRequest);
            String response = (String) HttpClientUtil.post(url, headers, jsonRequestBody);
//            log.debug("Google API 응답: {}", response);
            String decodedJson = URLDecoder.decode(response, "UTF-8");

            CodefAccountRegisterResponse codefAccountRegisterResponse = objectMapper.readValue(decodedJson, CodefAccountRegisterResponse.class);
            System.out.println(codefAccountRegisterResponse);

            codefMapper.createConnectedId(memberId, codefAccountRegisterResponse.getData().getConnectedId());

            codefAccountRegisterResponse.getData().getSuccessList().forEach(success -> {
                codefMapper.addAccount(codefAccountRegisterResponse.getData().getConnectedId(), success.getOrganization());
            });

//            return objectMapper.readValue(decodedJson, CodefAccountRegisterResponse.class);

        } catch (Exception e) {
//            log.error("Google API 호출 또는 파싱 실패", e);
            e.printStackTrace();
            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
        }
    }


    private void registerAccountByConnectedId(CodefAccountAddRequest codefAccountAddRequest) {
        String accessToken = redisUtil.getData("CodefToken");

        if (accessToken == null) {
            accessToken = getCodefToken().getAccessToken();
        }

        String url = CodefEndPoint.CODEF_ADD_ACCOUNT.getPath();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        try {
            codefAccountAddRequest.getAccountRequestList().forEach(accountRequest -> {
                try {
                    accountRequest.setPassword(rsaUtil.rsaEncrypt(accountRequest.getPassword()));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
            String jsonRequestBody = objectMapper.writeValueAsString(codefAccountAddRequest);
            String response = (String) HttpClientUtil.post(url, headers, jsonRequestBody);
            String decodedJson = URLDecoder.decode(response, "UTF-8");
            JsonNode root = objectMapper.readTree(decodedJson);

            String code = root.path("result").path("code").asText();

            if (code.equals("CF-00000")) {

                String myConnectedId = root.path("data").path("connectedId").asText();

                root.path("data").path("successList").forEach(success -> {
                    String organizationCode = success.path("organization").asText();
                    codefMapper.addAccount(myConnectedId, organizationCode);
                });

            } else {
                throw new CustomException(ErrorCode.CODEF_BAD_REQUEST);
            }

            System.out.println(root);


        } catch (Exception e) {
//            log.error("Google API 호출 또는 파싱 실패", e);
            e.printStackTrace();
            throw new CustomException(ErrorCode.PLACE_FETCH_FAIL);
        }

    }


    private void addMyCardInfoToDatabase(Long memberId, MyCardResponse myCardResponse) {

        String cardProductName = myCardResponse.getResCardName();
        String cardImageUrl = myCardResponse.getResImageLink();
        String cardType = myCardResponse.getResCardType();
        String organizationCode = myCardResponse.getOrganizationCode();

        CardProductVO cardProductByName = cardProductMapper.findCardProductByName(cardProductName);

        if (cardProductByName == null) {
            CardProductVO cardProductCreate = CardProductVO.builder()
                    .cardImageUrl(cardImageUrl)
                    .issuedDate(LocalDateTime.now())
                    .cardProductName(cardProductName)
                    .annualFee(0L)
                    .cardType(cardType)
                    .organizationCode(organizationCode)
                    .requiredMonthlySpent(0L)
                    .build();

            cardProductMapper.createCardProduct(cardProductCreate);

            String cardNumber = myCardResponse.getCardNumber();
            String cvc = "";
            String cardPassword = "";
            String createdAt = myCardResponse.getResIssueDate();
            Long cardProductId = cardProductCreate.getId();

            LocalDateTime expirationDate = null;

            if (!myCardResponse.getResValidPeriod().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                // LocalDate 변환
                LocalDate localDate = LocalDate.parse(myCardResponse.getResValidPeriod(), formatter);

                // LocalDateTime (자정 시간)
                expirationDate = localDate.atStartOfDay();

            }
            // yyyyMMdd 포맷 정의


            if (cardMapper.findCardByCardNumber(cardNumber) == null) {
                CardVO cardCreate = CardVO.builder()
                        .cardPassword(cardPassword)
                        .cardNumber(cardNumber)
                        .expirationDate(expirationDate)
                        .cardProductId(cardProductId)
                        .cvc("")
                        .memberId(memberId)
                        .build();

                cardMapper.createCard(cardCreate);

            }

        } else {
            String cardNumber = myCardResponse.getCardNumber();
            String cvc = "";
            String cardPassword = "";
            String createdAt = myCardResponse.getResIssueDate();
            Long cardProductId = cardProductByName.getId();

            LocalDateTime expirationDate = null;

            if (!myCardResponse.getResValidPeriod().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

                // LocalDate 변환
                LocalDate localDate = LocalDate.parse(myCardResponse.getResValidPeriod(), formatter);

                // LocalDateTime (자정 시간)
                expirationDate = localDate.atStartOfDay();

            }
            // yyyyMMdd 포맷 정의


            if (cardMapper.findCardByCardNumber(cardNumber) == null) {
                CardVO cardCreate = CardVO.builder()
                        .cardPassword(cardPassword)
                        .cardNumber(cardNumber)
                        .expirationDate(expirationDate)
                        .cardProductId(cardProductId)
                        .cvc("")
                        .memberId(memberId)
                        .build();

                cardMapper.createCard(cardCreate);

            }

        }










    }








}
