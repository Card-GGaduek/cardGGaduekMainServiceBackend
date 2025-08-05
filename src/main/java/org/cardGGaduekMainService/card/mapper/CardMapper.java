package org.cardGGaduekMainService.card.mapper;

import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.card.dto.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardMapper {
    List<CardFrontDTO> getCardFrontInfo(Long memberId);

    CardBackDTO getCardDetailInfo(Long cardId);
    List<CardBenefitInfoDTO> getCardBenefits(Long cardId);

    Long findCardProductIdByCardId(Long userCardId);

    List<Long> findAllCardProductIdsByMemberId(Long memberId);

    int softDeleteCard(@Param("cardId") Long cardId);   // is_valid=0으로 업데이트
    void updateCustomImageUrl(@Param("cardId") Long cardId, @Param("imageUrl") String imageUrl);
  
//    /**
//     * 특정 카드가 특정 회원의 소유인지 확인합니다.
//     * @param cardId 확인할 카드 ID
//     * @param memberId 회원 ID
//     * @return 소유하고 있다면 1, 아니면 0
//     */
    CardVO findById(Long cardId);
    List<CardInfoDTO> findCardsByMemberId(Long memberId);


    List<MyCardDTO> findMyCards(Long memberId);
}
