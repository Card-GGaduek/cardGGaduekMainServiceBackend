package org.cardGGaduekMainService.codef.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.cardGGaduekMainService.codef.dto.CodefTokenResponse;
import org.cardGGaduekMainService.codef.enums.CodefEndPoint;
import org.cardGGaduekMainService.common.util.HttpClientUtil;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class CodefTokenProvider {

    private final String clientId;
    private final String clientSecret;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CodefTokenProvider(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public CodefTokenResponse getCodefToken() {
        BufferedReader br = null;
        try {
            // 1) API URL
            URL url = new URL(CodefEndPoint.CODEF_OAUTH_TOKEN.getPath());

            System.out.println(clientId + ":" + clientSecret);

            String params = "grant_type=client_credentials&scope=read";

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Basic Auth 헤더 생성
            String auth = clientId + ":" + clientSecret;
            String authHeader = "Basic " + Base64.encodeBase64String(auth.getBytes(StandardCharsets.UTF_8));
            con.setRequestProperty("Authorization", authHeader);

            con.setDoInput(true);
            con.setDoOutput(true);

            // 바디 전송
            try (OutputStream os = con.getOutputStream()) {
                os.write(params.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            } else {
                throw new RuntimeException("API 호출 실패: " + responseCode);
            }

            // 응답 읽기
            StringBuilder responseStr = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                responseStr.append(inputLine);
            }

            // URL Decode + JSON → DTO 변환
            String decodedJson = URLDecoder.decode(responseStr.toString(), StandardCharsets.UTF_8);

            br.close();
            return objectMapper.readValue(decodedJson, CodefTokenResponse.class);



        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.API_REQUEST_FAILED);
        }

    }


}
