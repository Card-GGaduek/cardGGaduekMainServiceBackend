package org.cardGGaduekMainService.cardbenefit.mapper;

import org.cardGGaduekMainService.cardbenefit.domain.Merchant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MerchantMapper {
    List<Merchant> findAll();
    Merchant findById(Long id);
    Merchant findMerchantWithBenefits(Long id);
}
