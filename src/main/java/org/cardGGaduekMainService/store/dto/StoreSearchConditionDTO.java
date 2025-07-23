package org.cardGGaduekMainService.store.dto;

import lombok.Data;

@Data
public class StoreSearchConditionDTO {
    private String keyword;             // 매장명 검색 키워드
    private Long storeCategoryId;     // 카테고리 ID
    private Double latitude;            // 사용자 위치 위도
    private Double longitude;           // 사용자 위치 경도
    private Double radius;         // 검색 반경(km) 구현할지 말지 고민중
}
