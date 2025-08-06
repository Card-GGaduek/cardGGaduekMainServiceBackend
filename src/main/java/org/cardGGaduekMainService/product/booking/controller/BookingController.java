package org.cardGGaduekMainService.product.booking.controller;

import org.apache.ibatis.javassist.bytecode.annotation.LongMemberValue;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;
import org.cardGGaduekMainService.product.booking.dto.PriceRequestDTO;
import org.cardGGaduekMainService.product.booking.dto.PriceResponseDTO;
import org.cardGGaduekMainService.product.booking.service.BookingService;
import org.cardGGaduekMainService.response.ApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ApiResponse<Map<String,Long>>> handleCreateBooking(@RequestBody BookingRequestDTO bookingRequest, @AuthenticationPrincipal LoginMember loginMember){
        bookingRequest.setMemberId(loginMember.getId());

        Long newBookingId = bookingService.createBooking(bookingRequest);

        Map<String,Long> data = Collections.singletonMap("bookingId", newBookingId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_REQUEST_SUCCESS, data));
    }

    @PostMapping("/price")
    public ResponseEntity<ApiResponse<PriceResponseDTO>> getPrice(@RequestBody PriceRequestDTO priceRequestDTO){
        PriceResponseDTO priceResponseDTO = bookingService.calculatePrice(priceRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, priceResponseDTO));
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingDetailDTO>>> handleGetBookingsByMemberId(@AuthenticationPrincipal LoginMember loginMember) {
        // 서비스 계층을 호출하여 예약 내역을 조회합니다.
        List<BookingDetailDTO> bookings = bookingService.findBookingsByMemberId(loginMember.getId());

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, bookings));
    }
}
