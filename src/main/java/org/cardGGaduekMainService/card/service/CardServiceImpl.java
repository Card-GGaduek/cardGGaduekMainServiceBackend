package org.cardGGaduekMainService.card.service;

import org.cardGGaduekMainService.card.dto.CardFrontDTO;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardMapper cardMapper;

    @Override
    public List<CardFrontDTO> getCardFrontInfo(Long memberId) {
        return cardMapper.getCardFrontInfo(memberId);
    }
}
