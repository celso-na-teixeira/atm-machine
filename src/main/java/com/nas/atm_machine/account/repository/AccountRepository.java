package com.nas.atm_machine.account.repository;

import com.nas.atm_machine.account.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository <Account, Long> {

    //@Query("select a from Account a where a.accountNumber = ?1")
    Optional <Account> findByAccountNumber(Long accountNumber);
}
