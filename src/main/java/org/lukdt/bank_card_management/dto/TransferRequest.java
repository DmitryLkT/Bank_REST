package org.lukdt.bank_card_management.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransferRequest {
    @NotNull
    private Long senderId;
    @NotNull
    private Long recipientId;
    @NotNull
    @DecimalMin(value="0.01")
    private BigDecimal summa;

    public TransferRequest() {}
    public TransferRequest(Long senderId, Long recipientId, BigDecimal summa) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.summa = summa;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }
}
