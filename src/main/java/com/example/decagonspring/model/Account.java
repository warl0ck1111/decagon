package com.example.decagonspring.model;

/**
 * @author Okala Bashir .O.
 */

public class Account {
    private String accountNo; // TODO: 8/11/2022 change to be consistent either use accountNumber or accountNo
    private String accountName;
    private String phoneNo;
    private double balance = 0.00;

    public Account(String accountNo, String accountName, String phoneNo) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.phoneNo = phoneNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}