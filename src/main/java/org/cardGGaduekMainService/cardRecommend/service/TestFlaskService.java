package org.cardGGaduekMainService.cardRecommend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TestFlaskService {

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public Map<String,Object> fetchRawSpend() {
        String url = "http://127.0.0.1:5000/data";
        return restTemplate.getForObject(url, Map.class);
    }
}
