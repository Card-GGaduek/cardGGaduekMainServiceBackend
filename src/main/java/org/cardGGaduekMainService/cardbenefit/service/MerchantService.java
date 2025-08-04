package org.cardGGaduekMainService.cardbenefit.service;

import org.cardGGaduekMainService.cardbenefit.domain.Merchant;

import java.util.List;

public interface MerchantService {
    Merchant getMerchantWithBenefits(Long id);
    public List<Merchant> getAllMerchants();
    public Merchant getMerchantDetail(Long id);

}
