package org.cardGGaduekMainService.product.booking.service;

import lombok.extern.log4j.Log4j2;
import org.cardGGaduekMainService.card.benefit.service.CardBenefitService;
import org.cardGGaduekMainService.card.domain.CardVO;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.coupon.memberCoupon.service.MemberCouponService;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.product.booking.dto.*;
import org.cardGGaduekMainService.product.booking.mapper.BookingMapper;
import org.cardGGaduekMainService.product.rooms.mapper.RoomsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Log4j2
@Service
public class BookingServiceImpl implements BookingService{

    private final BookingMapper bookingMapper;
    private final MemberCouponService memberCouponService;
    private final RoomsMapper roomsMapper;
    private final CardBenefitService cardBenefitService;
    private final CardMapper cardMapper;

    @Autowired
    public BookingServiceImpl(BookingMapper bookingMapper, MemberCouponService memberCouponService, RoomsMapper roomsMapper, CardBenefitService cardBenefitService, CardMapper cardMapper){
        this.bookingMapper = bookingMapper;
        this.memberCouponService = memberCouponService;
        this.roomsMapper = roomsMapper;
        this.cardBenefitService = cardBenefitService;
        this.cardMapper = cardMapper;
    }



    @Override
    @Transactional(readOnly = true)
    public PriceResponseDTO calculatePrice(PriceRequestDTO priceRequest){
        BigDecimal originalPrice = calculateOriginalPrice(priceRequest.getRoomId(), priceRequest.getCheckInDate(), priceRequest.getCheckOutDate());

        BigDecimal couponDiscountAmount = BigDecimal.ZERO;
        BigDecimal priceAfterCoupon = originalPrice;
        if(priceRequest.getCouponProductId() != null){
            MemberCouponVO memberCouponVO = memberCouponService.validateMemberCoupon(
                    priceRequest.getMemberId(),
                    priceRequest.getCouponProductId()
            );
            couponDiscountAmount = memberCouponService.getDiscountAmount(memberCouponVO);
            priceAfterCoupon = originalPrice.subtract(couponDiscountAmount);
        }

        BigDecimal cardDiscountAmount = BigDecimal.ZERO;

        if(priceRequest.getCardId() != null){
            String category = "HOTEL";
            cardDiscountAmount = cardBenefitService.getCardDiscountAmount(
                    priceRequest.getCardId(),
                    priceAfterCoupon,
                    category
            );
        }

        BigDecimal finalPrice = priceAfterCoupon.subtract(cardDiscountAmount);
        if(finalPrice.compareTo(BigDecimal.ZERO) < 0){
            finalPrice = BigDecimal.ZERO;
        }

        return new PriceResponseDTO(originalPrice, couponDiscountAmount, cardDiscountAmount, finalPrice);
    }
    @Override
    public BigDecimal calculateOriginalPrice(Long roomId, LocalDate checkInDate, LocalDate checkOutDate){
        BigDecimal pricePerNight = roomsMapper.findPriceById(roomId);
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return pricePerNight.multiply(BigDecimal.valueOf(nights));
    }

    @Override
    @Transactional
    public Long createBooking(BookingRequestDTO bookingRequest){
//        String currentStatus = roomsMapper.findRoomStatusById(bookingRequest.getRoomId());
//        if(!"예약가능".equals(currentStatus)){
//            roomsMapper.updateRoomStatus(bookingRequest.getRoomId(), "예약불가능");
//        }

        Long overlappingId = bookingMapper.findOverlappingBookingId(
                bookingRequest.getRoomId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate()
        );

        if(overlappingId != null){
            throw new CustomException(ErrorCode.ROOM_NOT_AVAILABLE);
        }

        if(bookingRequest.getCardId() != null){
            CardVO card = cardMapper.findById(bookingRequest.getCardId());

            if(card == null || !card.getMemberId().equals(bookingRequest.getMemberId())){
                throw new CustomException(ErrorCode.CARD_NOT_FOUND);
            }
        }

        BigDecimal originalPrice = calculateOriginalPrice(bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());
        BigDecimal priceAfterCoupon = originalPrice;

        if(bookingRequest.getCouponProductId() != null && bookingRequest.getCouponProductId() > 0L) {
            MemberCouponVO memberCouponVO = memberCouponService.validateMemberCoupon(
                    bookingRequest.getMemberId(),
                    bookingRequest.getCouponProductId()
            );

            BigDecimal discountAmount = memberCouponService.getDiscountAmount(memberCouponVO);

            priceAfterCoupon = originalPrice.subtract(discountAmount);
        }


        BigDecimal finalPrice = priceAfterCoupon;
        BigDecimal cardDiscountAmount = BigDecimal.ZERO;
        if(bookingRequest.getCardId() != null){
            String category = "HOTEL";

            cardDiscountAmount = cardBenefitService.getCardDiscountAmount(
                    bookingRequest.getCardId(),
                    priceAfterCoupon,
                    category
            );
            finalPrice = priceAfterCoupon.subtract(cardDiscountAmount);
        }

        if(finalPrice.compareTo(BigDecimal.ZERO) < 0){
            finalPrice = BigDecimal.ZERO;
        }

        bookingRequest.setTotalPrice(finalPrice);
        bookingRequest.setStatus("PENDING");
        bookingMapper.createBooking(bookingRequest);


        Long newBookingId = bookingRequest.getId();

        if(newBookingId == null){
            throw new CustomException(ErrorCode.BOOKING_NOT_FOUND);
        }
        return newBookingId;
    }

//    public void completePayment(String orderId){
//        Long bookingId = paymentRepository.findBookingIdByOrderId(orderId);
//
//        bookingMapper.updateBookingStatus(bookingId, "CONFIRMED");
//    }

    public PaymentReadyDTO preparePayment(Long bookingId){
        BookingDetailDTO bookingDetailDTO = bookingMapper.findBookingDetailsByBookingId(bookingId);

        if(bookingDetailDTO == null){
            throw new CustomException(ErrorCode.BOOKING_NOT_FOUND);
        }

        String orderId = "ORD_" + bookingId + "_" + System.currentTimeMillis();
        String orderName = bookingDetailDTO.getAccommodationName() + " " + bookingDetailDTO.getRoomName();
        BigDecimal amount = bookingDetailDTO.getTotalPrice();
        String customerName = bookingDetailDTO.getName();

        return new PaymentReadyDTO(orderId, orderName, amount, customerName);
    }
    @Override
    @Transactional(readOnly = true)
    public List<BookingDetailDTO> findBookingsByMemberId(Long memberId){
        return bookingMapper.findBookingDetailsByMemberId(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingCapacityDTO> findBookingsByAccommodationId(Long accommodationId){
        return bookingMapper.getBookingsByAccommodationId(accommodationId);
    }
}
