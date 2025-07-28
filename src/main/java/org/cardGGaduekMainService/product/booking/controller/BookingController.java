package org.cardGGaduekMainService.product.booking.controller;

import org.apache.ibatis.javassist.bytecode.annotation.LongMemberValue;
import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;
import org.cardGGaduekMainService.product.booking.service.BookingService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String,Long>>> handleCreateBooking(@RequestBody BookingRequestDTO bookingRequest){
        Long newBookingId = bookingService.createBooking(bookingRequest);

        Map<String,Long> data = Collections.singletonMap("bookingId", newBookingId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_REQUEST_SUCCESS, data));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<BookingDetailDTO>>> handleGetBookingsByMemberId(@PathVariable Long memberId) {
        // 서비스 계층을 호출하여 예약 내역을 조회합니다.
        List<BookingDetailDTO> bookings = bookingService.findBookingsByMemberId(memberId);

        // ApiResponse 유틸리티 클래스를 사용하여 표준화된 성공 응답을 생성합니다.
        // .ok()는 HTTP 200 상태 코드를 의미합니다.
        // 이 코드가 작동하려면 ApiResponse 와 SuccessCode 클래스/Enum이 필요합니다.
        // return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, bookings));

        // ApiResponse 클래스가 없으므로, 기존 Map 방식과 유사하게 성공 응답을 구성합니다.
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, bookings));
    }
}
