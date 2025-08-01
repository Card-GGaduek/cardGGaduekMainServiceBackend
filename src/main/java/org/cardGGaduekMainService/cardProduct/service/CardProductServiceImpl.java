package org.cardGGaduekMainService.cardProduct.service;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDTO;
import org.cardGGaduekMainService.cardProduct.mapper.CardProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardProductServiceImpl implements CardProductService {

    @Autowired
    private CardProductMapper cardProductMapper;

    @Override
    public List<CardProductVO> getTop5CardProductsByBenefitCount() {
        return cardProductMapper.getTop5CardProductsByBenefitCount();
    }

    @Override
    public List<CardProductDTO> getAllCardProducts(){
        return cardProductMapper.findAllCardProducts();
    }

    @Override
    public CardProductVO getCardProductDetail(Long productId){
        return cardProductMapper.findCardProductDetail(productId);
    }
}
