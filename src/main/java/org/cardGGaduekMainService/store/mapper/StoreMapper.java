package org.cardGGaduekMainService.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.store.domain.StoreVO;
import org.cardGGaduekMainService.store.dto.StoreBenefitRawDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;
import org.cardGGaduekMainService.store.dto.StoreWithBenefitDTO;


import java.util.List;

@Mapper
public interface StoreMapper {

    // 지도에서 검색 결과 확인
    List<StoreVO> getStores(StoreSearchConditionDTO conditionDTO);

//    // 가맹점 및 카드 혜택 확인
//    List<StoreBenefitRawDTO> getStoresWithBenefits(StoreSearchConditionDTO conditionDTO);
//
//    // 혜택 카드 목록 조회
//    List<CardVO> getCardsByStore();
//
//    // 혜택 카드 상세 조회
//    CardVO getCardInfo();
//
//    // 결제 카드 연동 (추후 논의 후 결정)
//    int getLinkCardToPayment();
//
//    //내 카드 혜택 적용 가능한 가맹점 찾기 (추후 논의 후 결정)
//    List<StoreVO> getStoresByMyCard();

    // DB 가맹점 및 카드 혜택 확인
    List<StoreVO> getStoresWithBenefit(StoreSearchConditionDTO conditionDTO);


    // DB 매장 정보 가져오기
    StoreVO getStoreById(Long id);

    // DB 회원-카드 테이블에서 카드헤택과 가맹점 카테고리 매핑시켜서 혜택 가맹점 가져오기
    List<StoreBenefitRawDTO> getStoresByMemberCards(Long memberId);

}
