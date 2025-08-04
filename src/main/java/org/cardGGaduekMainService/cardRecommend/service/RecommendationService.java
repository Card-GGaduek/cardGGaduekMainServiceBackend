package org.cardGGaduekMainService.cardRecommend.service;

import org.cardGGaduekMainService.cardRecommend.dto.RecommendDTO;

public interface RecommendationService {

    RecommendDTO build(Long memberId);
}
