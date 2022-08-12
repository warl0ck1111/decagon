package com.example.decagonspring.service;

import com.example.decagonspring.dto.TransactionResponse;
import com.example.decagonspring.model.Transaction;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Okala Bashir .O.
 */
public interface AccountService {

    String createAccount(String accountName, String phone);
    TransactionResponse deposit(String accountNumber, double amount);
    TransactionResponse withdraw(String accountNumber, double amount);
    Collection<Transaction> getTransactions(String accountNumber);


}
