package com.nas.atm_machine.account.controller;

import com.nas.atm_machine.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

}
