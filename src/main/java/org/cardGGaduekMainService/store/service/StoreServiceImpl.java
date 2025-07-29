package org.cardGGaduekMainService.store.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.benefit.dto.CardBenefitDTO;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.store.domain.StoreVO;
import org.cardGGaduekMainService.store.dto.StoreBenefitRawDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;
import org.cardGGaduekMainService.store.dto.StoreWithBenefitDTO;
import org.cardGGaduekMainService.store.mapper.StoreMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {


    private final StoreMapper storeMapper;


    // 지도에서 매장 검색
    @Override
    public List<StoreSearchResponseDTO> findStores(StoreSearchConditionDTO conditionDTO) {

        List<StoreVO> stores = storeMapper.getStores(conditionDTO);

        if (stores.isEmpty()) throw new CustomException(ErrorCode.STORE_NOT_FOUND);

        return stores.stream()
                .map(StoreSearchResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public StoreSearchResponseDTO getStoreDetail(Long id) {
        StoreVO store = storeMapper.getStoreById(id);
        if (store == null) throw new CustomException(ErrorCode.STORE_NOT_FOUND);
        return StoreSearchResponseDTO.from(store);
    }

    // 회원이 보유한 카드로 혜택 적용 가능한 매장 목록 조회
    /**
     * DB 에서 가져온 Raw 데이터를 가공 -> Benefit DTO 구조로 변환
     *
     * */
    @Override
    public Map<String, List<StoreWithBenefitDTO>> findStoresByMemberCards(Long memberId) {


        // 1) Mapper 호출 → Raw DTO 리스트 가져오기
        List<StoreBenefitRawDTO> rawData = storeMapper.getStoresByMemberCards(memberId);

        // 2) 데이터 없으면 예외 처리 (비즈니스 로직 기준)
        if (rawData.isEmpty()) throw new CustomException(ErrorCode.NO_STORES_FOR_MEMBER);

        // 3) 카테고리별 → 매장별 → 혜택 리스트 구조로 그룹화
        Map<String, Map<Long, StoreWithBenefitDTO>> grouped = new LinkedHashMap<>();

        for (StoreBenefitRawDTO raw : rawData) {
            String storeCategory = (raw.getStoreCategory() != null) ? raw.getStoreCategory() : "기타";
            Long storeId = raw.getStoreId();

            grouped.putIfAbsent(storeCategory, new LinkedHashMap<>());
            Map<Long, StoreWithBenefitDTO> storeMap = grouped.get(storeCategory);

            // 매장 정보 초기화
            if (!storeMap.containsKey(storeId)) {
                storeMap.put(storeId, StoreWithBenefitDTO.builder()
                        .storeId(storeId)
                        .storeName(raw.getStoreName())
                        .address(raw.getStoreName())
                        .latitude(raw.getLatitude())
                        .longitude(raw.getLongitude())
                        .openTime(raw.getOpenTime())
                        .closeTime(raw.getCloseTime())
                        .storeCategory(raw.getStoreCategory())
                        .benefits(new ArrayList<>())
                        .build()
                );

                // 혜택 추가
                if (raw.getCardName() != null && raw.getBenefitDescription() != null) {
                    storeMap.get(storeId).getBenefits().add(CardBenefitDTO.builder()
                            .cardName(raw.getCardName())
                            .description(raw.getBenefitDescription())
                            .build());
                }
            }
        }
        // 4) LinkedHashMap → 최종 응답 구조 변환
        return grouped.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> new ArrayList<>(e.getValue().values()),
                        (a, b) -> b, LinkedHashMap::new));
    }
    @Override
    public List<CardVO> getCardsByStore() {
        return List.of();
    }

    @Override
    public CardVO getCardInfo() {
        return null;
    }

    @Override
    public int getLinkCardToPayment() {
        return 0;
    }

    @Override
    public List<StoreVO> getStoresByMyCard() {
        return List.of();
    }







}

