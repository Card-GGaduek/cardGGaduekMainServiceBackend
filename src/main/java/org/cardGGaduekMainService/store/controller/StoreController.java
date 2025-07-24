package org.cardGGaduekMainService.store.controller;


import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.store.dto.StoreSearchConditionDTO;
import org.cardGGaduekMainService.store.dto.StoreSearchResponseDTO;
import org.cardGGaduekMainService.store.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;


    /**
     * 가맹점 검색 API
     * 엔드포인트 : /api/stores/search
     * 예시 : /api/stores/search?keyword=스타벅스&categoryId=2
     *
     * @param conditionDTO 검색 조건 ( 키워드, 카테고리, 위치 좌표)
     * @return 검색된 가맹점 목록
     * */
    @GetMapping("/search")
    public ResponseEntity<List<StoreSearchResponseDTO>> searchStores(StoreSearchConditionDTO conditionDTO){
        List<StoreSearchResponseDTO> stores = storeService.findStores(conditionDTO);
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreSearchResponseDTO> getStoreDetail(@PathVariable Long id){
        StoreSearchResponseDTO store = storeService.getStoreDetail(id);
        return ResponseEntity.ok(store);
    }
}
