package org.cardGGaduekMainService.transaction.service;

import lombok.RequiredArgsConstructor;

import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.lab.service.LabService;
import org.cardGGaduekMainService.transaction.domain.TransactionVO;
import org.cardGGaduekMainService.transaction.dto.CardTransactionsDTO;
import org.cardGGaduekMainService.transaction.dto.TransactionDTO;
import org.cardGGaduekMainService.transaction.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final LabService labService;

    @Override
    public void insertTransaction(TransactionVO transactionVO) {
        // 1. 거래 등록
        transactionMapper.insertTransaction(transactionVO);

        // 2. 소비 분석 결과 갱신
        labService.updateSpendingAnalysis(transactionVO.getMemberId());
    }

    @Override
    public List<CardTransactionsDTO> getTransactionsGroupedByCard(Long memberId) {
        List<TransactionDTO> allTransactions = transactionMapper.selectTransactionsWithCardInfoByMemberId(memberId);

        if (allTransactions.isEmpty()) {
            throw new CustomException(ErrorCode.TRANSACTION_NOT_FOUND);
        }

        Map<Long, List<TransactionDTO>> grouped = allTransactions.stream()
                .collect(Collectors.groupingBy(TransactionDTO::getCardId));

        return grouped.entrySet().stream()
                .map(entry -> {
                    Long cardId = entry.getKey();
                    List<TransactionDTO> txList = entry.getValue();
                    TransactionDTO first = txList.get(0);

                    return CardTransactionsDTO.builder()
                            .cardId(cardId)
                            .cardName(first.getCardName())
                            .transactions(txList)
                            .build();
                })
                .collect(Collectors.toList());
    }
}

