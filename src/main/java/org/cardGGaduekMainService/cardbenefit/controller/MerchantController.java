package org.cardGGaduekMainService.cardbenefit.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardbenefit.domain.Merchant;
import org.cardGGaduekMainService.cardbenefit.service.MerchantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;



    @GetMapping
    public List<Merchant> getAllMerchants() {
        return merchantService.getAllMerchants();
    }

    @GetMapping("/{id}")
    public Merchant getMerchantDetail(@PathVariable Long id) {
        return merchantService.getMerchantDetail(id);
    }
}
