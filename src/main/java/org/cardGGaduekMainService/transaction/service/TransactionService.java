package org.cardGGaduekMainService.transaction.service;

import org.cardGGaduekMainService.transaction.dto.CardTransactionsDTO;
import org.cardGGaduekMainService.transaction.dto.TransactionDTO;

import java.util.List;


public interface TransactionService {

    List<CardTransactionsDTO> getTransactionsGroupedByCard(Long memberId);


}
