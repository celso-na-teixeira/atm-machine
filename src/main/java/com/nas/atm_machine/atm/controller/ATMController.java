package com.nas.atm_machine.atm.controller;

import com.nas.atm_machine.account.service.AccountBalance;
import com.nas.atm_machine.atm.ErrorResponse;
import com.nas.atm_machine.atm.WithdrawalRequest;
import com.nas.atm_machine.atm.exception.ATMException;
import com.nas.atm_machine.atm.exception.ATMOutOfCashException;
import com.nas.atm_machine.atm.exception.InsufficientFundsException;
import com.nas.atm_machine.atm.service.ATMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/atm")
public class ATMController {

    private final ATMService atmService;

    public ATMController(ATMService atmService) {
        this.atmService = atmService;
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(Principal principal, @RequestBody WithdrawalRequest request) {
        log.info("Requesting withdrawal of: {}", request.getAmount());
        try {
            Long accountNumber = Long.parseLong(principal.getName());
            AccountBalance accountBalance = atmService.withdrawal(accountNumber, request.getAmount());
            return ResponseEntity.ok(accountBalance);
        } catch (InsufficientFundsException exception) {
            log.warn(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Insufficient funds", exception.getMessage(), "/api/atm/withdrawal"));
        } catch (ATMOutOfCashException exception) {
            log.warn(exception.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "ATM out of cash", exception.getMessage(), "/api/atm/withdrawal"));
        } catch (ATMException exception) {
            log.warn(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Withdrawal failed", exception.getMessage(), "/api/atm/withdrawal"));
        } catch (Exception e) {
            log.error("Unexpected error during withdrawal", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Internal server error", "An unexpected error occurred. Please try again later."));
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
