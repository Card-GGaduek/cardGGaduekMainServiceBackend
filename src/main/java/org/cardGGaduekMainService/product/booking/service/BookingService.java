package org.cardGGaduekMainService.product.booking.service;

import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;
import org.cardGGaduekMainService.product.booking.dto.PriceRequestDTO;
import org.cardGGaduekMainService.product.booking.dto.PriceResponseDTO;
import org.cardGGaduekMainService.product.booking.mapper.BookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface BookingService {
    Long createBooking(BookingRequestDTO bookingRequest)
            ;
    BigDecimal calculateOriginalPrice(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    List<BookingDetailDTO> findBookingsByMemberId(Long memberId);

    PriceResponseDTO calculatePrice(PriceRequestDTO priceRequest);
    //    void completePayment(Long bookingId);
}