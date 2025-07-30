package org.cardGGaduekMainService.product.rooms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface RoomsMapper {
    BigDecimal findPriceById(@Param("id") Long roomId);
}
