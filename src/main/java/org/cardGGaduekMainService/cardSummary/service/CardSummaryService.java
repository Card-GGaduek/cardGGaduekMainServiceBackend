package org.cardGGaduekMainService.cardSummary.service;

import org.cardGGaduekMainService.cardSummary.dto.CardSummaryDTO;

import java.util.List;

public interface CardSummaryService {

    List<CardSummaryDTO> getMonthlyCardSummary(Long memberId);
}
