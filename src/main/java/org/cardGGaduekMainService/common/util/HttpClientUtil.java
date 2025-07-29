package org.cardGGaduekMainService.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
public class HttpClientUtil {

    public static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET();

        // 헤더 세팅
        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            requestBuilder.header(header.getKey(), header.getValue());
        }

        HttpRequest request = requestBuilder.build();

        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                return response.body();
            } else {
                throw new RuntimeException("API 호출 실패: HTTP 상태 코드 " + statusCode + "\n응답: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("API 요청 또는 응답 실패", e);
        }
    }


    public static Object post(String apiUrl, Map<String, String> requestHeaders, String requestBody) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody));

        for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
            requestBuilder.header(header.getKey(), header.getValue());
        }

        HttpRequest request = requestBuilder.build();


        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            int statusCode = response.statusCode();
            if (statusCode == 200) {
                return response.body();
            } else {
                throw new RuntimeException("API 호출 실패: HTTP 상태 코드 " + statusCode + "\n응답: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("API 요청 또는 응답 실패", e);
        }



    }
}
