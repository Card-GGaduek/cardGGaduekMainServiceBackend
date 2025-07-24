package org.cardGGaduekMainService.store.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.card.benefit.domain.CardBenefitVO;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.store.domain.StoreVO;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;

import java.util.List;

@Mapper
public interface StoreMapper {

    // DB 매장 리스트 가져오기
    List<StoreVO> getStores(StoreSearchConditionDTO conditionDTO);

    // DB 매장 정보 가져오기
    StoreVO getStoreById(Long id);
}
