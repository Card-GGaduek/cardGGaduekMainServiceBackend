package org.cardGGaduekMainService.cardSummary.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardSummary.domain.CardSummaryVO;
import org.cardGGaduekMainService.cardSummary.dto.CardSummaryDTO;
import org.cardGGaduekMainService.cardSummary.mapper.CardSummaryMapper;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardSummaryServiceImpl implements CardSummaryService {
    private final CardSummaryMapper cardSummaryMapper;

    @Override
    public List<CardSummaryDTO> getMonthlyCardSummary(Long memberId) {
        List<CardSummaryVO> cardSummaryVOS = cardSummaryMapper.selectMonthlySpendingByMemberId(memberId);

        if (cardSummaryVOS.isEmpty()) {
            throw new CustomException(ErrorCode.TRANSACTION_NOT_FOUND);
        }

        List<CardSummaryDTO> cardSummaryDTOS= new ArrayList<>();

        for (int i = 0; i < cardSummaryVOS.size(); i++) {
            CardSummaryVO current = cardSummaryVOS.get(i);
            Long diff = 0L;
            if (i + 1 < cardSummaryVOS.size()) {
                CardSummaryVO prev = cardSummaryVOS.get(i + 1);
                diff = current.getTotalSpent() - prev.getTotalSpent();
            }
            cardSummaryDTOS.add(
                    CardSummaryDTO.builder()
                            .yearMonth(current.getYearMonth())
                            .totalSpent(current.getTotalSpent())
                            .spendingDiff(diff)
                            .build()
            );
        }

        return cardSummaryDTOS;
    }



    }
