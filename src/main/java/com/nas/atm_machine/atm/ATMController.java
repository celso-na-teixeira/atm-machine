package com.nas.atm_machine.atm;

import com.nas.atm_machine.account.service.AccountBalance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/atm")
public class ATMController {

    private final ATMService atmService;

    public ATMController(ATMService atmService) {
        this.atmService = atmService;
    }

    @PutMapping("/withdrawal/{amount}")
    public ResponseEntity<?> withdrawal(Principal principal, @PathVariable("amount") String amount) {
        log.info("Requesting withdrawal");
        try {
            Long accountNumber = Long.parseLong(principal.getName());
            AccountBalance accountBalance = atmService.withdrawal(accountNumber, amount);
            return ResponseEntity.ok(accountBalance);
        }catch (Exception e) {
            log.error("Unexpected error getting a List of all accounts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<?> checkBalance(Principal principal) {
        try {
            Long accountNumber = Long.parseLong(principal.getName());
            log.info("checking balance for account number: {}", accountNumber);

            AccountBalance accountBalance = atmService.checkBalance(accountNumber);
            return ResponseEntity.ok(accountBalance);
        }catch (Exception e) {
            log.error("Unexpected error checking balance for account number: {}", principal.getName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
