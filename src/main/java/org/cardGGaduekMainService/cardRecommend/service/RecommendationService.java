package org.cardGGaduekMainService.cardRecommend.service;

import org.cardGGaduekMainService.cardRecommend.dto.CardRecommendDTO;

public interface RecommendationService {

    CardRecommendDTO build(Long memberId);
}
