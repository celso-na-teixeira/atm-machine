package com.nas.atm_machine.atm.controller;

import com.nas.atm_machine.account.dto.AccountBalance;
import com.nas.atm_machine.atm.WithdrawalRequest;
import com.nas.atm_machine.atm.service.ATMService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/atm")
public class ATMController {

    private final ATMService atmService;

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(Principal principal, @Validated @RequestBody WithdrawalRequest request) {
        log.info("Requesting withdrawal of: {}", request.getAmount());
        Long accountNumber = Long.parseLong(principal.getName());
        AccountBalance accountBalance = atmService.withdrawal(accountNumber, request.getAmount());
        return ResponseEntity.ok(accountBalance);
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

   /* @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidTopTalentDataException(MethodArgumentNotValidException methodArgumentNotValidException) {
        // handle validation exception
    }*/
}
