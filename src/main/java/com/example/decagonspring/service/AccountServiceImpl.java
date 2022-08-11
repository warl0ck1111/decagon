package com.example.decagonspring.service;

import com.example.decagonspring.exception.ApiRequestException;
import com.example.decagonspring.model.Transaction;
import com.example.decagonspring.model.TransactionType;
import com.example.decagonspring.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Okala Bashir .O.
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService<Account> {

    private final Map<String, Account> accountMap = new ConcurrentHashMap<>();
    private final List<Transaction> transactionList = new ArrayList<>();

    public String generateAccountNumber() {
        log.info("generating account number");
        Random rand = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int n = rand.nextInt(10);
            accountNumber.append(n);
        }
        System.out.print(accountNumber);
        if (accountMap.containsKey(accountNumber.toString())) {
            log.info("generated account No taken... regenerating");
            generateAccountNumber();
        }

        log.info("Account number is: {}", accountNumber);
        return accountNumber.toString();
    }

    public boolean isPhoneNumberTaken(String phoneNo) {
        Set<Map.Entry<String, Account>> entries = accountMap.entrySet();
        return entries.stream().anyMatch(entry -> entry.getValue().getPhoneNo().equals(phoneNo));
    }

    @Override
    public synchronized Account createAccount(String accountName, String phoneNo) {
        if (accountName == null || accountName.trim().isEmpty()) throw new ApiRequestException("Invalid account name");
        if (phoneNo == null || phoneNo.trim().isEmpty()) throw new ApiRequestException("Invalid phone number");

        boolean phoneNumberTaken= isPhoneNumberTaken(phoneNo);
        if (phoneNumberTaken) throw new ApiRequestException("Phone No Already Taken");
        String generatedAccountNumber = generateAccountNumber();
        Account account = new Account(generatedAccountNumber, accountName, phoneNo);
        accountMap.put(generatedAccountNumber, account);
        return account;
    }

    @Override
    public synchronized Account deposit(String accountNumber, double amount) {
        if (amount < 1) throw new ApiRequestException("Invalid Amount:deposit amount cannot be less than 1");
        if (accountNumber == null || accountNumber.trim().isEmpty() || accountNumber.length()!=10)
            throw new ApiRequestException("invalid account number");

        //retrieve account details
        Account account = accountMap.get(accountNumber);
        if (account == null) throw new ApiRequestException("Account not found");
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        accountMap.put(accountNumber, account);
        log.info("Deposit successful");

        Transaction transaction = new Transaction(account.getAccountName(),accountNumber, amount, TransactionType.DEPOSIT, Instant.now().getEpochSecond());
        transactionList.add(transaction);
        log.info("putting data into map:\n " + transactionList);

        return account;
    }

    @Override
    public synchronized Account withdraw(String accountNumber, double amount) {
        if (amount < 1) throw new ApiRequestException("invalid amount: amount cannot be lass than 1");
        Account account = accountMap.get(accountNumber);
        if (accountNumber == null || accountNumber.trim().isEmpty() || accountNumber.length()!=10)
            throw new ApiRequestException("invalid account number");
        if (amount > account.getBalance()) {
            log.info("Insufficient Balance");
            throw new ApiRequestException("Insufficient Balance");
        }
        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        accountMap.put(accountNumber, account);
        log.info("Withdrawal successful");


        Transaction transaction = new Transaction(account.getAccountName(), accountNumber, amount, TransactionType.WITHDRAWAL, Instant.now().getEpochSecond());
        transactionList.add(transaction);
        log.info("putting data into map:\n " + transactionList);
        return account;
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    @Override
    public synchronized List<Transaction> getTransactions(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            return transactionList;
        } else {

            Predicate<Transaction> transactionsThatMatchAccountNumber = transaction -> transaction.getAccountNo().equals(accountNumber);
            return transactionList.stream().filter(transactionsThatMatchAccountNumber).sorted().collect(Collectors.toList());
        }
    }


}
