package org.cardGGaduekMainService.product.booking.controller;

import org.apache.ibatis.javassist.bytecode.annotation.LongMemberValue;
import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;
import org.cardGGaduekMainService.product.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> handleCreateBooking(@RequestBody BookingRequestDTO bookingRequest){
        Map<String, Object> response = new HashMap<>();

        try {
            Long newBookingId = bookingService.createBooking(bookingRequest);

            response.put("success", true);
            response.put("message", "예약이 성공적으로 완료되었습니다.");
            response.put("bookingId", newBookingId);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            response.put("success", false);
            response.put("message", "예약 처리 중 오류가 발생했습니다: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> handleGetBookingsByMemberId(@PathVariable Long memberId) {
        // 서비스 계층을 호출하여 예약 내역을 조회합니다.
        List<BookingDetailDTO> bookings = bookingService.findBookingsByMemberId(memberId);

        // ApiResponse 유틸리티 클래스를 사용하여 표준화된 성공 응답을 생성합니다.
        // .ok()는 HTTP 200 상태 코드를 의미합니다.
        // 이 코드가 작동하려면 ApiResponse 와 SuccessCode 클래스/Enum이 필요합니다.
        // return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, bookings));

        // ApiResponse 클래스가 없으므로, 기존 Map 방식과 유사하게 성공 응답을 구성합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "예약 내역 조회가 완료되었습니다."); // SuccessCode.BOOKING_FETCH_SUCCESS.getMessage()
        response.put("data", bookings);
        return ResponseEntity.ok(response);
    }
}
