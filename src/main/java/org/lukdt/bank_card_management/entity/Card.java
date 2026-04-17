package org.lukdt.bank_card_management.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="cards")
public class Card {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="card_number_encrypted", nullable = false)
    private String cardNumberEncrypted;

    @Column(name="owner_id", nullable = false)
    private Long ownerId;

    @Column(name="issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name="expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private Status status;

    @Column(name="balance", nullable = false)
    private BigDecimal balance;

    public Card() {}

    public Card(String cardNumberEncrypted, Long ownerId, LocalDate issueDate, LocalDate expiryDate, Status status, BigDecimal balance) {
        this.cardNumberEncrypted = cardNumberEncrypted;
        this.ownerId = ownerId;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumberEncrypted() {
        return cardNumberEncrypted;
    }

    public void setCardNumberEncrypted(String cardNumberEncrypted) {
        this.cardNumberEncrypted = cardNumberEncrypted;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}