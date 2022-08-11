package com.example.decagonspring.service;

import com.example.decagonspring.model.Transaction;

import java.util.List;

/**
 * @author Okala Bashir .O.
 */
public interface AccountService<E> {

    E createAccount(String accountName, String phone);
    E deposit(String accountNumber, double amount);
    E withdraw(String accountNumber, double amount);
    List<Transaction> getTransactions(String accountNumber);


}
