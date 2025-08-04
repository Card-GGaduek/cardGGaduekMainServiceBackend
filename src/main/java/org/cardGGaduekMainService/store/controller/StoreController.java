package org.cardGGaduekMainService.store.controller;


import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(StoreController.class);
    private final StoreService storeService;

    /**
     * 가맹점 리스트 검색 API
     * 엔드포인트 : /api/stores/search
     * 예시 : /api/stores/search?lat=37.4979&lng=127.0276&keyword=카페&benefit=true
     *
     * @param conditionDTO 검색 조건 ( 키워드, 카테고리, 위치 좌표)
     * @return 검색된 가맹점 목록
     * */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<StoreWithBenefitDTO>>> searchStores(@ModelAttribute StoreSearchConditionDTO conditionDTO){
        List<StoreWithBenefitDTO> stores = storeService.findStores(conditionDTO);
        log.info("검색 요청={}",conditionDTO);
        System.out.println("====== [매장 검색 요청] ======");
        System.out.println("keyword = " + conditionDTO.getKeyword());
        System.out.println("category = " + conditionDTO.getStoreCategory());
        System.out.println("latitude = " + conditionDTO.getLatitude());
        System.out.println("longitude = " + conditionDTO.getLongitude());
        System.out.println("radius = " + conditionDTO.getRadius());

        log.info("🔁 응답 DTO 목록 (StoreSearchResponseDTO):");
        stores.forEach(store -> log.info("  ➤ ID={}, Name={}, Lat={}, Lng={}",
                store.getStoreId(),
                store.getStoreName(),
                store.getLatitude(),
                store.getLongitude()
        ));
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
    public ResponseEntity<ApiResponse<StoreWithBenefitDTO>> getStoreDetail(@PathVariable Long id){
        StoreWithBenefitDTO store = storeService.findStoreDetail(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STORE_GET_SUCCESS, store));
    }

    /**
     * 내 카드 혜택 적용 가능한 매장 조회
     * @param memberId 회원 ID
     *
     * */
    @GetMapping("/my-cards")
    public ResponseEntity<ApiResponse<Map<String,List<StoreWithBenefitDTO>>>> getStoresByMemberCards(@RequestParam Long memberId){

        Map<String,List<StoreWithBenefitDTO>> result = storeService.findStoresByMemberCards(memberId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.STORE_GET_SUCCESS, result));
    }
}
