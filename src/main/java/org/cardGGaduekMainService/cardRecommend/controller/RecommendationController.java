package org.cardGGaduekMainService.cardRecommend.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardRecommend.dto.RecommendDTO;
import org.cardGGaduekMainService.cardRecommend.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService svc;

    @GetMapping("/{memberId}")
    public ResponseEntity<RecommendDTO> get(@PathVariable Long memberId) {
        return ResponseEntity.ok(svc.build(memberId));
    }

}
