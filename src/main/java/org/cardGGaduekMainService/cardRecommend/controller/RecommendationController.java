package org.cardGGaduekMainService.cardRecommend.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardRecommend.dto.CardRecommendDTO;
import org.cardGGaduekMainService.cardRecommend.service.RecommendationService;
import org.cardGGaduekMainService.cardRecommend.service.TestFlaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final TestFlaskService testFlaskService;

    @GetMapping("/{memberId}")
    public ResponseEntity<CardRecommendDTO> get(@PathVariable Long memberId) {
        return ResponseEntity.ok(recommendationService.build(memberId));
    }

    //@GetMapping("/flask-test")
    //public Map<String, Object> getFlaskTest() {
        //return testFlaskService.callFlaskTest();
    //}

}
