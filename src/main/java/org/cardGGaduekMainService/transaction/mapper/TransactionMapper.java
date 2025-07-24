package org.cardGGaduekMainService.transaction.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cardGGaduekMainService.transaction.domain.TransactionVO;

@Mapper
public interface TransactionMapper {
    void insertTransaction(TransactionVO transactionVO);
}

