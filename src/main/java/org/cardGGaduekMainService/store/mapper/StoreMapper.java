package org.cardGGaduekMainService.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.store.domain.StoreVO;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;


import java.util.List;

@Mapper
public interface StoreMapper {

    // 지도에서 검색 결과 확인
    List<StoreVO> getStores(String keyword);

    // 가맹점 및 카드 혜택 확인
    List<CardBenefitVO> getBenefitsByCategory();

    // 혜택 카드 목록 조회
    List<CardVO> getCardsByStore();

    // 혜택 카드 상세 조회
    CardVO getCardInfo();

    // 결제 카드 연동 (추후 논의 후 결정)
    int getLinkCardToPayment();

    //내 카드 혜택 적용 가능한 가맹점 찾기 (추후 논의 후 결정)
    List<StoreVO> getStoresByMyCard();

    // DB 매장 리스트 가져오기
    List<StoreVO> getStores(StoreSearchConditionDTO conditionDTO);


    // DB 매장 정보 가져오기
    StoreVO getStoreById(Long id);
}
