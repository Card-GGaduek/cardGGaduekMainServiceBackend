package org.cardGGaduekMainService.product.booking.service;

import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;
import org.cardGGaduekMainService.product.booking.mapper.BookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    private final BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(BookingMapper bookingMapper){
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional
    public Long createBooking(BookingRequestDTO bookingRequest){
        bookingRequest.setStatus("CONFIRMED");

        Long insertedRows = bookingMapper.createBooking(bookingRequest);

        if(insertedRows == 0){
            throw new RuntimeException("데이터베이스에 예약 정보를 저장하지 못했습니다.");
        }

        Long newBookingId = bookingRequest.getId();

        if(newBookingId == null){
            throw new RuntimeException("예약 생성 후 ID를 가져오는 데 실패했습니다. MyBatis 설정을 확인하세요.");
        }
        System.out.println("새로운 예약이 성공적으로 생성되었습니다. 예약 ID: " + newBookingId);
        return newBookingId;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDetailDTO> findBookingsByMemberId(Long memberId){
        return bookingMapper.findBookingDetailsByMemberId(memberId);
    }
}
