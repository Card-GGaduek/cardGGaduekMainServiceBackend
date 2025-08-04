package org.cardGGaduekMainService.product.rooms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.product.rooms.domain.RoomsVO;
import org.cardGGaduekMainService.product.rooms.dto.RoomsDTO;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface RoomsMapper {
    BigDecimal findPriceById(@Param("id") Long roomId);
    List<RoomsDTO> findByAccommodationId(Long accommodationId);

    String findRoomStatusById(Long roomId);
    void updateRoomStatus(@Param("roomId") Long roomId, @Param("status") String status);
}
