package org.cardGGaduekMainService.store.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.store.domain.StoreVO;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StoreService {


    // 지도에서 매장 검색
    List<StoreSearchResponseDTO> findStores(StoreSearchConditionDTO conditionDTO);

    // 매장 상세정보 조회
    StoreSearchResponseDTO getStoreDetail(Long id);

    // 지도 카테고리 가맹점 목록 및 카드 혜택 간략 확인
    List<CardBenefitDTO> findBenefitsByStoreCategory();

    // 가맹점 제휴 카드 목록 조회
    List<CardVO> getCardsByStore();

    // 혜택 카드 상세 조회
    CardVO getCardInfo();

    // 결제 카드 연동
    int getLinkCardToPayment();

    // 내 카드 혜택 적용 가능한 가맹점 찾기
    List<StoreVO> getStoresByMyCard();

}
