//package org.cardGGaduekMainService.store.service;
//
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;
//import org.cardGGaduekMainService.card.domain.CardVO;
//import org.cardGGaduekMainService.store.domain.StoreVO;
//import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
//import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;
//import org.cardGGaduekMainService.store.mapper.StoreMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class StoreServiceImpl implements StoreService {
//
//
//    private final StoreMapper storeMapper;
//
//    // 지도에서 매장 검색
//    @Override
//    public List<StoreSearchResponseDTO> findStores(StoreSearchConditionDTO conditionDTO) {
//        List<StoreVO> stores = storeMapper.getStores(conditionDTO);
//        return stores.stream()
//                .map(store -> StoreSearchResponseDTO.builder()
//                        .id(store.getId())
//                        .name(store.getName())
//                        .address(store.getAddress())
//                        .latitude(store.getLatitude())
//                        .longitude(store.getLongitude())
//                        .openTime(store.getOpenTime().toString())
//                        .closeTime(store.getCloseTime().toString())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<CardBenefitDTO> findBenefitsByStoreCategory() {
//        return List.of();
//    }
//
//    @Override
//    public List<CardVO> getCardsByStore() {
//        return List.of();
//    }
//
//    @Override
//    public CardVO getCardInfo() {
//        return null;
//    }
//
//    @Override
//    public int getLinkCardToPayment() {
//        return 0;
//    }
//
//    @Override
//    public List<StoreVO> getStoresByMyCard() {
//        return List.of();
//    }
//}
