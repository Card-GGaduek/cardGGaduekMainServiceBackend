package org.cardGGaduekMainService.cardRecommend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SpendPredictMapper {

    void updateSpendPreidct(@Param("memberId") Long memberId,
                        @Param("yearMonth") String yearMonth,
                        @Param("json") String json);

    String findSpendPredict(@Param("memberId") Long memberId,
                            @Param("yearMonth") String yearMonth);
}
