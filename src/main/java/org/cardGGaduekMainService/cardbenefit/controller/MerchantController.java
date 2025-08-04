package org.cardGGaduekMainService.cardbenefit.controller;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardbenefit.domain.Merchant;
import org.cardGGaduekMainService.cardbenefit.service.MerchantServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantServiceImpl merchantServiceImpl;



    @GetMapping
    public List<Merchant> getAllMerchants() {
        return merchantServiceImpl.getAllMerchants();
    }

    @GetMapping("/{id}")
    public Merchant getMerchantDetail(@PathVariable Long id) {
        return merchantServiceImpl.getMerchantDetail(id);
    }

    @GetMapping("/{id}/benefits")
    public Merchant getMerchantWithBenefits(@PathVariable Long id) {
        return merchantServiceImpl.getMerchantWithBenefits(id);
    }
}
