//package org.cardGGaduekMainService.store.dto;
//
//import org.cardGGaduekMainService.store.domain.StoreVO;
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class StoreSearchResponseDTOTest {
//
//    @Test
//    void from() {
//        // given: StoreVO 생성
//    StoreVO store = StoreVO.builder()
//            .id(1L)
//            .name("스타벅스 강남점")
//            .address("서울 강남구 강남대로 456")
//            .latitude(37.4979)
//            .longitude(127.0276)
//            .openTime(new Date()) // 현재 시간
//            .closeTime(new Date(System.currentTimeMillis() + 3600000)) // 1시간 후
//            .build();
//
//    // when: DTO 변환
//    StoreSearchResponseDTO dto = StoreSearchResponseDTO.from(store);
//
//    // then: 변환된 시간 형식 확인
//    assertNotNull(dto.getOpenTime());
//    assertNotNull(dto.getCloseTime());
//
//        System.out.println("Open Time: " + dto.getOpenTime());
//        System.out.println("Close Time: " + dto.getCloseTime());
//
//    // HH:mm 형식 검증 (길이 = 5, ":" 포함)
//    assertTrue(dto.getOpenTime().matches("\\d{2}:\\d{2}"));
//    assertTrue(dto.getCloseTime().matches("\\d{2}:\\d{2}"));
//}
//}