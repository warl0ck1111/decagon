package com.example.decagonspring.controller;

import com.example.decagonspring.dto.TransactionResponse;
import com.example.decagonspring.service.AccountServiceImpl;
import com.example.decagonspring.dto.CreateAccountRequest;
import com.example.decagonspring.model.Transaction;
import com.example.decagonspring.dto.TransactionRequest;
import com.example.decagonspring.model.Account;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Okala Bashir .O.
 */
@RestController
@RequestMapping("api/v1/account")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public String createAccount(@RequestBody CreateAccountRequest accountRequest) {
        return accountService.createAccount(accountRequest.getAccountName(), accountRequest.getPhoneNo());
    }

    @PostMapping("/withdraw")
    public TransactionResponse withDraw(@RequestBody TransactionRequest transactionRequest) {
        return accountService.withdraw(transactionRequest.getAccountNo(), transactionRequest.getAmount());
    }

    @PostMapping("/deposit")
    public TransactionResponse deposit(@RequestBody TransactionRequest transactionRequest) {
        return accountService.deposit(transactionRequest.getAccountNo(), transactionRequest.getAmount());
    }

    @GetMapping("/transactions")
    public Collection<Transaction> getTransactions(@RequestParam(required = false) String accountNo) {
        return accountService.getTransactions(accountNo);
    }

    @GetMapping
    public Map<String,Account> getAccounts(@RequestParam(required = false) String accountNo) {
        return accountService.getAccountMap();
    }



}
