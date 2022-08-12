package com.example.decagonspring.dto;

/**
 * @author Okala Bashir .O.
 */
public class TransactionResponse {

    private double balance;
    private double amount;

    public TransactionResponse(double amount,double balance) {
        this.balance = balance;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
