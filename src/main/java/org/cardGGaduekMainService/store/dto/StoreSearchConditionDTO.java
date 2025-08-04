package org.cardGGaduekMainService.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreSearchConditionDTO {
    private String keyword;             // 매장명 검색 키워드
    private String storeCategory;     // 카테고리 ID
    private Double latitude;            // 사용자 위치 위도
    private Double longitude;           // 사용자 위치 경도
    private Double radius;         // 검색 반경(km) 구현할지 말지 고민중
    private boolean benefit;
}
