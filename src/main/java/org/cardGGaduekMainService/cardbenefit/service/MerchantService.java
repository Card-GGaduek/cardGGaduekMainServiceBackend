package org.cardGGaduekMainService.cardbenefit.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardbenefit.domain.Merchant;
import org.cardGGaduekMainService.cardbenefit.mapper.MerchantMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantMapper merchantMapper;


    public List<Merchant> getAllMerchants() {
        return merchantMapper.findAll();
    }

    public Merchant getMerchantDetail(Long id) {
        return merchantMapper.findById(id);
    }
}
