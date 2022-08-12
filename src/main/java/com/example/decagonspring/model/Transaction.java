package com.example.decagonspring.model;/*


 /**
  @author Okala III
 */


public class Transaction implements Comparable<Transaction> {

    private String accountName;
    private String accountNo;
    private double amount;
    private TransactionType transactionType;// (DEPOSIT or WITHDRAWAL);
    private long createdAt;

    public Transaction(String accountName, String accountNo, double amount, TransactionType transactionType, long createdAt) {
        this.accountName = accountName;
        this.accountNo = accountNo;
        this.amount = amount;
        this.transactionType = transactionType;
        this.createdAt = createdAt;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public int compareTo(Transaction o) {
        return Long.compare(this.getCreatedAt(),o.getCreatedAt());
    }
}

