package org.cardGGaduekMainService.product.booking.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.product.booking.dto.BookingDetailDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookingMapper {
    /**
     * 새로운 예약을 생성하고, 생성된 예약의 ID를 DTO에 다시 설정합니다.
     * @param bookingRequestDTO 저장할 예약 정보
     * @return INSERT된 행의 수 (보통 1)
     */
    Long createBooking(BookingRequestDTO bookingRequestDTO);

    List<BookingDetailDTO> findBookingDetailsByMemberId(Long memberId);

    BookingDetailDTO findBookingDetailsByBookingId(Long memberId);

    void updateBookingStatus(@Param("bookingId") Long bookingId, @Param("status") String status);

    Long findOverlappingBookingId(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    void cancelOldPendingBookings();
}
