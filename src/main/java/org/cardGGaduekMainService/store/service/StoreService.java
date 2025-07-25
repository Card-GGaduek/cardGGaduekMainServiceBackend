package org.cardGGaduekMainService.store.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.store.domain.StoreVO;
import org.cardGGaduekMainService.store.dto.StoreBenefitRawDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;
import org.cardGGaduekMainService.store.dto.StoreWithBenefitDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface StoreService {


    // 지도에서 매장 검색
    List<StoreSearchResponseDTO> findStores(StoreSearchConditionDTO conditionDTO);

    // 매장 상세정보 조회
    StoreSearchResponseDTO getStoreDetail(Long id);

    /**
     * 회원이 보유한 카드로 혜택 적용 가능한 매장 목록 조회
     * @param memberId 회원 ID
     * @return 카테고리별 매장 목록 (매장별 카드 혜택 포함)
     * */
    Map<Integer, List<StoreWithBenefitDTO>> findStoresByMemberCards(Long memberId);

    // 가맹점 제휴 카드 목록 조회
    List<CardVO> getCardsByStore();

    // 혜택 카드 상세 조회
    CardVO getCardInfo();

    // 결제 카드 연동
    int getLinkCardToPayment();

    // 내 카드 혜택 적용 가능한 가맹점 찾기
    List<StoreVO> getStoresByMyCard();

}
