package org.cardGGaduekMainService.cardRecommend.mapper;

import org.cardGGaduekMainService.card.domain.CardVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("cardRecommendMapper")
public interface CardMapper {
    //내 카드 찾기
    List<CardVO> findValidByMember(Long memberId);
}
