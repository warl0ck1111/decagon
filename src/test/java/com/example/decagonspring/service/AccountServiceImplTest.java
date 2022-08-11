package com.example.decagonspring.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.decagonspring.exception.ApiRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Test
    void testGenerateAccountNumber() {
        String accountNumber = this.accountServiceImpl.generateAccountNumber();
        assertTrue(accountNumber.length()==10);

    }

    @Test
    void testIsPhoneNumberTaken2() {
        assertTrue(this.accountServiceImpl.isPhoneNumberTaken("4105551212"));
        assertFalse(this.accountServiceImpl.isPhoneNumberTaken(""));
    }

    @Test
    void testCreateAccount() {
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("Dr Jane Doe", "4105551212"));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("3", "4105551212"));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount(null, "4105551212"));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.createAccount("", "4105551212"));
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
    void testWithdraw() {
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("4105551212", 10.0));
        assertThrows(ApiRequestException.class, () -> this.accountServiceImpl.withdraw("4105551212", 0.5));
    }

    @Test
    void testGetTransactions() {
        assertTrue(this.accountServiceImpl.getTransactions("4105551212").isEmpty());
        assertTrue(this.accountServiceImpl.getTransactions(null).isEmpty());
        assertTrue(this.accountServiceImpl.getTransactions("").isEmpty());
    }

    @Test
    void testConstructor() {
        assertTrue((new AccountServiceImpl()).getAccountMap().isEmpty());
    }
}

