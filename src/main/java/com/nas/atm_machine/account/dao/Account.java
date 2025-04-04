package com.nas.atm_machine.account.dao;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
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

    public BigDecimal getAvailableBalance() {
        return this.openingBalance.add(this.overDraft);
    }
}
