package org.cardGGaduekMainService.cardSummary.service;


import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.cardSummary.domain.CardCateSummaryVO;
import org.cardGGaduekMainService.cardSummary.dto.CardCateSummaryDTO;
import org.cardGGaduekMainService.cardSummary.mapper.CardCateSummaryMapper;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardCateSummaryServiceImpl implements CardCateSummaryService {

    private final CardCateSummaryMapper cardCateSummaryMapper;

    @Override
    public List<CardCateSummaryDTO> getCurrentMonthCategorySummary(Long memberId) {
        List<CardCateSummaryVO> cardCateSummaryVOS = cardCateSummaryMapper.selectCurrentMonthSummary(memberId);


        if (cardCateSummaryVOS.isEmpty()) {
            throw new CustomException(ErrorCode.TRANSACTION_NOT_FOUND);
        }

        long totalPercent = cardCateSummaryVOS.stream()
                .mapToLong(CardCateSummaryVO::getTotalSpent)
                .sum();

        return cardCateSummaryVOS.stream()
                .map(vo -> new CardCateSummaryDTO(
                        vo.getTransactionCategory().name(),
                        vo.getTransactionCategory().getDisplayName(),
                        vo.getTotalSpent(),
                        totalPercent > 0 ? vo.getTotalSpent() * 100.0 / totalPercent : 0.0
                ))
                .collect(Collectors.toList());
    }
}
