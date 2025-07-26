package org.cardGGaduekMainService.product.booking.service;

import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;
import org.cardGGaduekMainService.product.booking.mapper.BookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface BookingService {
    void createBooking(BookingRequestDTO bookingRequest);

    List<BookingDetailDTO> findBookingsByMemberId(Long memberId);
}