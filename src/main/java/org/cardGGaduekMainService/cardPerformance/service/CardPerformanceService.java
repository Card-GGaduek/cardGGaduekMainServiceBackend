package org.cardGGaduekMainService.cardPerformance.service;


import org.cardGGaduekMainService.cardPerformance.dto.CardPerformanceDTO;
import java.util.List;


public interface CardPerformanceService {

    List<CardPerformanceDTO> getCardPerformances(Long memberId);

}
