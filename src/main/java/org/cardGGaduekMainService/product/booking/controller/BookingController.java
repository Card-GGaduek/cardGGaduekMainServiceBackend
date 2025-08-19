package org.cardGGaduekMainService.product.booking.controller;

import io.swagger.annotations.*;
import org.cardGGaduekMainService.auth.dto.LoginMember;
import org.cardGGaduekMainService.product.booking.dto.*;
import org.cardGGaduekMainService.product.booking.service.BookingService;
import org.cardGGaduekMainService.response.CustomApiResponse;
import org.cardGGaduekMainService.response.SuccessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "예약 관리")
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @ApiOperation(value = "숙박 시설 예약", notes = "숙박 시설 예약 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "예약 성공")
    )
    @PostMapping
    public ResponseEntity<CustomApiResponse<Map<String,Long>>> handleCreateBooking(@ApiParam("예약 정보") @RequestBody BookingRequestDTO bookingRequest, @AuthenticationPrincipal LoginMember loginMember){
        bookingRequest.setMemberId(loginMember.getId());

        Long newBookingId = bookingService.createBooking(bookingRequest);

        Map<String,Long> data = Collections.singletonMap("bookingId", newBookingId);

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.BOOKING_REQUEST_SUCCESS, data));
    }

    @ApiOperation(value = "", notes = "")
    @ApiResponses(
            @ApiResponse(code = 200, message = "")
    )
    @PostMapping("/price")
    public ResponseEntity<CustomApiResponse<PriceResponseDTO>> getPrice(@RequestBody PriceRequestDTO priceRequestDTO, @AuthenticationPrincipal LoginMember loginMember){
        priceRequestDTO.setMemberId(loginMember.getId());
        PriceResponseDTO priceResponseDTO = bookingService.calculatePrice(priceRequestDTO);
        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, priceResponseDTO));
    }

    @ApiOperation(value = "내 예약 정보 조회", notes = "나으 예약 정보를 조회하는 API")
    @ApiResponses(
            @ApiResponse(code = 200, message = "예약 정보 조회 성공", response = BookingDetailDTO.class, responseContainer = "List" )
    )
    @GetMapping
    public ResponseEntity<CustomApiResponse<List<BookingDetailDTO>>> handleGetBookingsByMemberId(@AuthenticationPrincipal LoginMember loginMember) {
        // 서비스 계층을 호출하여 예약 내역을 조회합니다.
        List<BookingDetailDTO> bookings = bookingService.findBookingsByMemberId(loginMember.getId());

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, bookings));
    }

    @ApiOperation(value = "", notes = "")
    @ApiResponses(
            @ApiResponse(code = 200, message = "")
    )
    @GetMapping("/{accommodationId}")
    public ResponseEntity<CustomApiResponse<List<BookingCapacityDTO>>> getBookingByAccommodationId(@PathVariable("accommodationId") Long accommodationId) {
        List<BookingCapacityDTO> bookings = bookingService.findBookingsByAccommodationId(accommodationId);

        return ResponseEntity.ok(CustomApiResponse.success(SuccessCode.BOOKING_FETCH_SUCCESS, bookings));
    }
}
