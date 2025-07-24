package org.cardGGaduekMainService.transaction.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.lab.service.LabService;
import org.cardGGaduekMainService.transaction.domain.TransactionVO;
import org.cardGGaduekMainService.transaction.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final LabService labService; // 분석 서비스 의존

    @Override
    public void insertTransaction(TransactionVO transactionVO) {
        // 1. 거래 등록
        transactionMapper.insertTransaction(transactionVO);

        // 2. 소비 분석 결과 갱신
        labService.updateSpendingAnalysis(transactionVO.getMemberId());
    }
}

