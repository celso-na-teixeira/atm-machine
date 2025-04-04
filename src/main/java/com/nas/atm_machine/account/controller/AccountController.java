package com.nas.atm_machine.account.controller;

import com.nas.atm_machine.account.dao.Account;
import com.nas.atm_machine.account.service.AccountBalance;
import com.nas.atm_machine.account.service.AccountService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    private  AccountService accountService;




}
