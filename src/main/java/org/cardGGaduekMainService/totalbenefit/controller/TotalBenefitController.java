package org.cardGGaduekMainService.totalbenefit.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.totalbenefit.dto.TotalBenefitDTO;
import org.cardGGaduekMainService.totalbenefit.service.TotalBenefitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/total-benefit")
public class TotalBenefitController {

    private final TotalBenefitService totalBenefitService;

    @GetMapping
    public ResponseEntity<TotalBenefitDTO> getBenefitSummary(
            @RequestParam Long memberId,
            @RequestParam String yearMonth  // ex: "2025-07"
    ) {
        TotalBenefitDTO summary = totalBenefitService.getTotalBenefitSummary(memberId, yearMonth);
        return ResponseEntity.ok(summary);
    }
}
