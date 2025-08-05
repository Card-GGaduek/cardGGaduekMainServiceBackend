package org.cardGGaduekMainService.card.service;

import lombok.RequiredArgsConstructor;
import org.cardGGaduekMainService.card.dto.*;
import org.cardGGaduekMainService.card.mapper.CardMapper;
import org.cardGGaduekMainService.exception.CustomException;
import org.cardGGaduekMainService.exception.ErrorCode;
import org.cardGGaduekMainService.member.domain.MemberVO;
import org.cardGGaduekMainService.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardMapper cardMapper;
    private final MemberMapper memberMapper;

    @Override
    public List<CardFrontDTO> getCardFrontInfo(Long memberId) {
        List<CardFrontDTO> cardList = cardMapper.getCardFrontInfo(memberId);
        if (cardList == null || cardList.isEmpty()) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        return cardList;
    }

    @Override
    public CardBackDTO getCardDetail(Long cardId) {
        CardBackDTO cardDetail = cardMapper.getCardDetailInfo(cardId);
        if (cardDetail == null) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }

        List<CardBenefitInfoDTO> benefits = cardMapper.getCardBenefits(cardId);
        cardDetail.setBenefits(benefits);

        return cardDetail;
    }

    @Override
    public void deleteCard(Long cardId) {
        cardMapper.softDeleteCard(cardId);
    }

    @Override
    public void updateCardImage(Long cardId, String imageUrl) {
        cardMapper.updateCustomImageUrl(cardId, imageUrl);
    }

    @Override
    public List<CardDTO> findCardByMember(Long memberId){
        List<CardInfoDTO> cardInfos = cardMapper.findCardsByMemberId(memberId);

        return cardInfos.stream().map(info -> {
            CardDTO dto = new CardDTO();
            dto.setId(info.getId());
            dto.setImageUrl(info.getCardImageUrl());

            String lastFourDigits = info.getCardNumber().substring(info.getCardNumber().length() - 4);
            dto.setName(info.getCardProductName() + " (" + lastFourDigits + ")");

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MyCardDTO> findMyCards(Long memberId) {
        MemberVO memberById = memberMapper.getMemberById(memberId);
        if (memberById == null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);

        List<MyCardDTO> myCards = cardMapper.findMyCards(memberId);

        return myCards;


    }
}
