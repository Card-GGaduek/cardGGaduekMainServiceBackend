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

    // DB 매장 리스트 가져오기
    List<StoreVO> getStores(StoreSearchConditionDTO conditionDTO);

    // DB 매장 정보 가져오기
    StoreVO getStoreById(Long id);

    // DB 회원-카드 테이블에서 카드헤택과 가맹점 카테고리 매핑시켜서 혜택 가맹점 가져오기
    List<StoreBenefitRawDTO> getStoresByMemberCards(Long memberId);

}
