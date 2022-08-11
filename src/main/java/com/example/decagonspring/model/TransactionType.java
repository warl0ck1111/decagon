package com.example.decagonspring.model;

/**
 * @author Okala Bashir .O.
 */
public enum TransactionType {
    WITHDRAWAL("WITHDRAW"),
    DEPOSIT("DEPOSIT");

    private String name;

    TransactionType(String name) {
        this.name = name;
    }
}
