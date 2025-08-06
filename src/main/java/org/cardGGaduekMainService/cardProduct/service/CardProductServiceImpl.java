package org.cardGGaduekMainService.cardProduct.service;

import org.cardGGaduekMainService.cardProduct.domain.CardProductVO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDTO;
import org.cardGGaduekMainService.cardProduct.dto.CardProductDetailDTO;
import org.cardGGaduekMainService.cardProduct.mapper.CardProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public CardProductDetailDTO getCardProductDetail(Long id) {
        List<CardProductDetailDTO> results = cardProductMapper.findDetailById(id);

        // 1. 조회 결과가 없으면 null 반환
        if (results == null || results.isEmpty()) {
            return null;
        }

        // 2. resultMap으로 그룹화된 첫 번째 결과 객체를 가져옴
        CardProductDetailDTO cardDetail = results.get(0);

        // 3. 만약 JOIN된 혜택이 하나도 없어서 benefits 리스트가 null이라면,
        //    빈 리스트로 초기화해준다. (프론트엔드 에러 방지)
        if (cardDetail != null && cardDetail.getBenefits() == null) {
            cardDetail.setBenefits(new ArrayList<>());
        }

        return cardDetail;
    }
}
