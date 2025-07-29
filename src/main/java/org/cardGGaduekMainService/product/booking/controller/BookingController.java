package org.cardGGaduekMainService.product.booking.controller;

import org.apache.ibatis.javassist.bytecode.annotation.LongMemberValue;
import org.cardGGaduekMainService.member.dto.MemberCouponDTO;
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
    public ResponseEntity<ApiResponse<Void>> handleCreateBooking(@RequestBody BookingRequestDTO bookingRequest){

        bookingService.createBooking(bookingRequest);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_REQUEST_SUCCESS));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<BookingDetailDTO>>> handleGetBookingsByMemberId(@PathVariable Long memberId) {
        // 1. 서비스에서 예약 내역 리스트를 조회합니다.
        List<BookingDetailDTO> bookings = bookingService.findBookingsByMemberId(memberId);

        // 2. ApiResponse.success() 메소드에 조회한 bookings 데이터를 함께 전달합니다.
        // 이렇게 하면 bookings를 포함한 ApiResponse 객체 하나가 생성됩니다.
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, bookings));
    }
}
