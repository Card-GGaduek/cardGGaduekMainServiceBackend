package org.cardGGaduekMainService.product.booking.service;

import org.cardGGaduekMainService.card.benefit.service.CardBenefitService;
import org.cardGGaduekMainService.coupon.memberCoupon.domain.MemberCouponVO;
import org.cardGGaduekMainService.coupon.memberCoupon.service.MemberCouponService;
import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;
import org.cardGGaduekMainService.product.booking.dto.PaymentReadyDTO;
import org.cardGGaduekMainService.product.booking.mapper.BookingMapper;
import org.cardGGaduekMainService.product.rooms.mapper.RoomsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    private final BookingMapper bookingMapper;
    private final MemberCouponService memberCouponService;
    private final RoomsMapper roomsMapper;
    private final CardBenefitService cardBenefitService;

    @Autowired
    public BookingServiceImpl(BookingMapper bookingMapper, MemberCouponService memberCouponService, RoomsMapper roomsMapper, CardBenefitService cardBenefitService){
        this.bookingMapper = bookingMapper;
        this.memberCouponService = memberCouponService;
        this.roomsMapper = roomsMapper;
        this.cardBenefitService = cardBenefitService;
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
            String category = "호텔";

            cardDiscountAmount = cardBenefitService.getCardDiscountAmount(
                    bookingRequest.getCardId(),
                    priceAfterCoupon,
                    category
            );
            finalPrice = priceAfterCoupon.subtract(cardDiscountAmount);
        }

        bookingRequest.setTotalPrice(finalPrice);
        bookingRequest.setStatus("PENDING");
        bookingMapper.createBooking(bookingRequest);

        Long newBookingId = bookingRequest.getId();

        if(newBookingId == null){
            throw new RuntimeException("예약 생성 후 ID를 가져오는 데 실패했습니다. MyBatis 설정을 확인하세요.");
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
            throw new RuntimeException("존재하지 않는 예약입니다.");
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
}
