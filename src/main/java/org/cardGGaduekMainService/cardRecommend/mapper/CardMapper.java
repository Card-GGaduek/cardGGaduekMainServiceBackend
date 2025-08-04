package org.cardGGaduekMainService.cardRecommend.mapper;

import org.cardGGaduekMainService.card.domain.CardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardMapper {

    List<CardVO> findValidByMember(Long memberId);
}
