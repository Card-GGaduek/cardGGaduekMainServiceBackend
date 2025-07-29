package org.cardGGaduekMainService.cardSummary.service;

import org.cardGGaduekMainService.cardSummary.dto.CardCateSummaryDTO;

import java.util.List;

public interface CardCateSummaryService {

    List<CardCateSummaryDTO> getCurrentMonthCategorySummary(Long memberId);
}
