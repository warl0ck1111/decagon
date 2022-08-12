package com.example.decagonspring.service;

import com.example.decagonspring.exception.ApiRequestException;
import com.example.decagonspring.model.Account;
import com.example.decagonspring.model.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    private Account account;

    @BeforeEach
    void testConstructor() {
        assertTrue((new AccountServiceImpl()).getAccountMap().isEmpty());
        account = new Account("4105551212", "Dr Jane Doe", "097988989");
        account.setBalance(8000);
        accountServiceImpl.getAccountMap().put("4105551212", account);
    }

    @Test
    void testGenerateAccountNumber() {
        String accountNumber = this.accountServiceImpl.generateAccountNumber();
        assertEquals(10, accountNumber.length());
        assertTrue(this.accountServiceImpl.getAccountMap().containsKey("4105551212"));

    }

    @Test
    void testIsPhoneNumberTaken() {
        assertTrue(this.accountServiceImpl.isPhoneNumberTaken("097988989"));
        assertFalse(this.accountServiceImpl.isPhoneNumberTaken("1234567890"));
    }

    @Test
    void testCreateAccount() {
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("Dr Jane Doe", "097988989"));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("3", "097988989"));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount(null, "097988989"));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("", "097988989"));
        assertThrows(ApiRequestException.class,
                () -> this.accountServiceImpl.createAccount("Dr Jane Doe", "097988989"));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("Dr Jane Doe", null));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("Dr Jane Doe", ""));
    }

    @Test
    void testDeposit() {
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("877", 100));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("4105551212", 0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit(null, 10.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("", 10.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("4105551212", 0.5));


    }

    @Test
    void testDepositShouldFailWhenAccountNumberNotFound() {
        ApiRequestException apiRequestException = assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("9898988989", 900));
        String expectedMessage = "Account not found";
        String actualMessage = apiRequestException.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testDepositShouldFailWhenAccountNumberNotEqualTo10Digits() {
        ApiRequestException apiRequestException = assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("41055", 885));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("", 10.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("67676776776776776", 10.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("null", 10.0));
        String expectedMessage = "invalid account number";
        String actualMessage = apiRequestException.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));

    }
    @Test
    void testDepositShouldFailWhenDepositAmountLessThan1() {
        ApiRequestException apiRequestException = assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.deposit("4105551212", 0.5));
        String expectedMessage = "invalid amount: amount cannot be lass than 1";
        String actualMessage = apiRequestException.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    void testWithdraw() {

        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("4105551212", 0.5));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("4105551212", 100000.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("", 10.0));
        double previousBalance = account.getBalance();
        int previousTransactionListSize = accountServiceImpl.getTransactions("").size();
        accountServiceImpl.withdraw(account.getAccountNo(), 50);
        double newBalance = account.getBalance();
        int newTransactionListSize = accountServiceImpl.getTransactions("").size();
        assertEquals((newBalance+50), previousBalance);
        assertEquals((previousTransactionListSize+1), newTransactionListSize);
    }

    @Test
    void testWithDrawShouldFailForAttemptWithdrawalMoreThanAvailableBalance() {
        ApiRequestException apiRequestException = assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("4105551212", 10000000));
        String expectedMessage = "Insufficient Balance";
        String actualMessage = apiRequestException.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testWithDrawShouldFailWhenAccountNumberNotEqualTo10Digits() {
        ApiRequestException apiRequestException = assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("41055", 885));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("", 10.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("67676776776776776", 10.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("null", 10.0));
        String expectedMessage = "invalid account number";
        String actualMessage = apiRequestException.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));

    }
@Test
    void testWithDrawShouldFailWhenWithdrawAmountLessThan1() {
        ApiRequestException apiRequestException = assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("4105551212", 0.5));
        String expectedMessage = "invalid amount: amount cannot be lass than 1";
        String actualMessage = apiRequestException.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testGetTransactions() {
        assertTrue(this.accountServiceImpl.getTransactions("4105551212").isEmpty());
        assertTrue(this.accountServiceImpl.getTransactions(null).isEmpty());
        assertTrue(this.accountServiceImpl.getTransactions("").isEmpty());
        accountServiceImpl.withdraw(account.getAccountNo(), 50);
        accountServiceImpl.withdraw(account.getAccountNo(), 150);
        assertEquals(2, this.accountServiceImpl.getTransactions("4105551212").size());
        assertFalse(this.accountServiceImpl.getTransactions("4105551212").isEmpty());

    }


}

