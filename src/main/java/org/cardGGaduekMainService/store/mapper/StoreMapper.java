package org.cardGGaduekMainService.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.card.benefit.domain.BenefitVO;
import org.cardGGaduekMainService.store.domain.StoreVO;

import java.util.List;

@Mapper
public interface StoreMapper {

    // 지도에서 검색 결과 확인
    List<StoreVO> getStores(String keyword);

    // 가맹점 및 카드 혜택 확인
    List<BenefitVO> findBenefitsByCategory();

    // 혜택 카드 목록 조회
    List<CardVO> findCardsByStore();

    // 혜택 카드 상세 조회
    CardVO getCardInfo();

    // 결제 카드 연동 (추후 논의 후 결정)
    int linkCardToPayment();

    //내 카드 혜택 적용 가능한 가맹점 찾기 (추후 논의 후 결정)
    List<StoreVO> findStoresByMyCard();




}
