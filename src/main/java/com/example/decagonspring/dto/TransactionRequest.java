package com.example.decagonspring.dto;

/**
 * @author Okala Bashir .O.
 */
public class TransactionRequest {

    private String accountNo;
    private double amount;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
