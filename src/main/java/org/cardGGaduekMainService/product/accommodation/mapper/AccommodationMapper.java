package org.cardGGaduekMainService.product.accommodation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.product.accommodation.dto.AccommodationDTO;
import org.cardGGaduekMainService.product.booking.dto.BookingRequestDTO;

@Mapper
public interface AccommodationMapper {
    AccommodationDTO findById(Long id);
}
