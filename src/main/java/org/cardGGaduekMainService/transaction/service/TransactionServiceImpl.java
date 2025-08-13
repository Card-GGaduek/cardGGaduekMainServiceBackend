package org.cardGGaduekMainService.transaction.service;

import lombok.RequiredArgsConstructor;

import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.lab.domain.enums.SpendingCategory;
import org.cardGGaduekMainService.lab.service.LabService;
import org.cardGGaduekMainService.transaction.dto.CTransactionDTO;
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
    public void createTransaction(TransactionDTO dto) {
        // 1. 거래 저장
        transactionMapper.insertTransaction(dto.toEntity());

        // 2. 거래 카테고리를 SpendingCategory로 변환
        SpendingCategory category = SpendingCategory.fromTransactionCategory(dto.getTransactionCategory());

        // 3. LabService를 통해 미션 진행도 업데이트
        labService.updateMissionProgressByTransactions(dto.getMemberId(), List.of(category));

        // 4. 소비 분석 결과 갱신
        labService.updateSpendingAnalysis(dto.getMemberId());
    }

    @Override
    public List<CardTransactionsDTO> getTransactionsGroupedByCard(Long memberId) {
        List<CTransactionDTO> allTransactions =
                transactionMapper.selectTransactionsWithCardInfoByMemberId(memberId);

        if (allTransactions.isEmpty()) {
            throw new CustomException(ErrorCode.TRANSACTION_NOT_FOUND);
        }

        Map<Long, List<CTransactionDTO>> grouped =
                allTransactions.stream()
                        .collect(Collectors.groupingBy(CTransactionDTO::getCardId));

        return grouped.entrySet().stream()
                .map(entry -> {
                    Long cardId        = entry.getKey();
                    List<CTransactionDTO> list = entry.getValue();

                    CTransactionDTO head = list.get(0);
                    Long cardProductId = head.getCardProductId();
                    String cardName = head.getCardName();

                    List<CTransactionDTO> txList = list.stream()
                            .filter(r -> r.getId() != null)
                            .collect(Collectors.toList());

                    return CardTransactionsDTO.builder()
                            .cardId(cardId)
                            .cardProductId(cardProductId)
                            .cardName(cardName)
                            .transactions(txList)
                            .build();
                })
                .collect(Collectors.toList());
    }


}

