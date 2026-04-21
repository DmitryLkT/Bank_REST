package org.lukdt.bank_card_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description="Ответ с данными карты")
public class CardResponse {
    @Schema(description = "Маскированный номер карты", example = "**** **** **** 1234")
    private String cardNumber;

    @Schema(description = "Имя владельца", example = "Иван")
    private String ownerName;

    @Schema(description = "Фамилия владельца", example = "Иванов")
    private String ownerSurname;

    @Schema(description = "Дата окончания срока службы карты", example = "2030-06-15")
    private LocalDate expiryDate;

    @Schema(description = "Статус карты", example = "ACTIVE")
    private String status;

    @Schema(description = "Баланс карты", example = "12596.09")
    private BigDecimal balance;

    public CardResponse() {}

    public CardResponse(String cardNumber, String ownerName, String ownerSurname,
                        LocalDate expiryDate, String status, BigDecimal balance) {
        this.cardNumber = cardNumber;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.expiryDate = expiryDate;
        this.status = status;
        this.balance = balance;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
