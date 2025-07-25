package org.cardGGaduekMainService.store.controller;


import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;
import org.cardGGaduekMainService.store.dto.StoreWithBenefitDTO;
import org.cardGGaduekMainService.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 가맹점 리스트 검색 API
     * 엔드포인트 : /api/stores/search
     * 예시 : /api/stores/search?keyword=스타벅스&categoryId=2
     *
     * @param conditionDTO 검색 조건 ( 키워드, 카테고리, 위치 좌표)
     * @return 검색된 가맹점 목록
     * */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StoreSearchResponseDTO>>> searchStores(StoreSearchConditionDTO conditionDTO){
        List<StoreSearchResponseDTO> stores = storeService.findStores(conditionDTO);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STORE_SEARCH_SUCCESS, stores));
    }


    /**
     * 가맹점 상세조회 API
     * 엔드포인트 : /api/stores/{id}
     * 예시 : /api/stores/110
     *
     * @param id 가맹점 고유 ID
     * @return 가맹점 상세 정보
     *
     * */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StoreSearchResponseDTO>> getStoreDetail(@PathVariable Long id){
        StoreSearchResponseDTO store = storeService.getStoreDetail(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STORE_GET_SUCCESS, store));
    }

    /**
     * 내 카드 혜택 적용 가능한 매장 조회
     * @param memberId 회원 ID
     *
     * */
    @GetMapping("/my-cards")
    public ResponseEntity<ApiResponse<Map<Integer,List<StoreWithBenefitDTO>>>> getStoresByMemberCards(@RequestParam Long memberId){

        Map<Integer,List<StoreWithBenefitDTO>> result = storeService.findStoresByMemberCards(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STORE_GET_SUCCESS, result));
    }
}
