package org.cardGGaduekMainService.transaction.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cardGGaduekMainService.transaction.domain.TransactionVO;
import org.cardGGaduekMainService.transaction.dto.CTransactionDTO;
import java.util.List;

@Mapper
public interface TransactionMapper {

    List<CTransactionDTO> selectTransactionsWithCardInfoByMemberId(@Param("memberId") Long memberId);
    void insertTransaction(TransactionVO transactionVO);

}

