package org.cardGGaduekMainService.card.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CardMapper {
    int softDeleteCard(@Param("cardId") Long cardId);   // is_valid=0으로 업데이트
}
