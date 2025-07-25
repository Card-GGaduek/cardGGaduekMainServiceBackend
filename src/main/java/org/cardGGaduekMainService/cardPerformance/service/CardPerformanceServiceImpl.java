package org.cardGGaduekMainService.cardPerformance.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardPerformance.domain.CardPerformanceVO;
import org.cardGGaduekMainService.cardPerformance.dto.CardPerformanceDTO;
import org.cardGGaduekMainService.cardPerformance.mapper.CardPerformanceMapper;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardPerformanceServiceImpl implements CardPerformanceService {

    private final CardPerformanceMapper cardPerformanceMapper;

    @Override
    public List<CardPerformanceDTO> getCardPerformances(Long memberId) {
        //현재 달의 실적
        YearMonth yearMonth = YearMonth.now();
        String yearMonthString = yearMonth.toString();

        List<CardPerformanceVO> cardPerformances = cardPerformanceMapper.selectByMemberId(memberId);

        if (cardPerformances.isEmpty()) {
            throw new CustomException(ErrorCode.TRANSACTION_NOT_FOUND);
        }
        return cardPerformances.stream()
                .map(vo -> CardPerformanceDTO.builder()
                        .cardId(vo.getCardId())
                        .yearMonth(yearMonthString)
                        .cardProductName(vo.getCardProductName())
                        .requiredMonthlySpending(vo.getRequiredMonthlySpending())
                        .spentAmount(vo.getSpentAmount())
                        .build()
                )
                .collect(Collectors.toList());
    }




}
