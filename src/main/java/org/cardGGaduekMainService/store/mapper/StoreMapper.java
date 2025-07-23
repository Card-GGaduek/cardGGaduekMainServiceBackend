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
    List<BenefitVO> findBenefitsByCategory(@Param("benefit_category") Long categoryId);

    // 혜택 카드 목록 조회
    List<CardVO> findCardsByStore(@Param("store_id") Long storeId);

    // 혜택 카드 상세 조회
    CardVO getCardInfo(@Param("card_id") Long cardId);

    // 결제 카드 연동
    int linkCardToPayment(@Param("user_id") Long userId,
                          @Param("card_id") Long cardId,
                          @Param("payment_id") Long paymentId);

    //내 카드 혜택 적용 가능한 가맹점 찾기
    List<StoreVO> findStoresByMyCard(@Param("user_id") Long userId,
                                     @Param("card_id") Long cardId);




}
