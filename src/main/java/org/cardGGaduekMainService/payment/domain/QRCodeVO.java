package org.cardGGaduekMainService.payment.domain;

public class QRCodeVO {
    private Long memberId;
    private Long cardId;
    private String timestamp;

    public QRCodeVO() {}

    public QRCodeVO(Long memberId, Long cardId, String timestamp) {
        this.memberId = memberId;
        this.cardId = cardId;
        this.timestamp = timestamp;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
