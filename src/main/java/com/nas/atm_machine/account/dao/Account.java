package com.nas.atm_machine.account.dao;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "T_ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="account_number")
    private Long accountNumber;
    @Column(name="pin")
    private String pin;
    @Column(name="opening_balance")
    private BigDecimal openingBalance;
    @Column(name="over_draft")
    private BigDecimal overDraft;

    public Account() {
    }

    public Account(Long accountNumber, String pin, BigDecimal openingBalance, BigDecimal overDraft) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.openingBalance = openingBalance;
        this.overDraft = overDraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(pin, account.pin) && Objects.equals(openingBalance, account.openingBalance) && Objects.equals(overDraft, account.overDraft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, pin, openingBalance, overDraft);
    }

    @Override
    public String toString() {
        return "Account{" +
                "iD=" + id +
                ", accountNumber=" + accountNumber +
                ", pin=" + pin +
                ", openingBalance=" + openingBalance +
                ", overDraft=" + overDraft +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BigDecimal getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(BigDecimal overDraft) {
        this.overDraft = overDraft;
    }

    public BigDecimal getAvailableBalance() {
        return this.openingBalance.add(this.overDraft);
    }
}
