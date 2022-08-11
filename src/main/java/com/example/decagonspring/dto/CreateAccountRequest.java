package com.example.decagonspring.dto;

/**
 * @author Okala Bashir .O.
 */
public class CreateAccountRequest {

    private String accountName;
    private String phoneNo;

    public CreateAccountRequest(String accountName, String phoneNo) {
        this.accountName = accountName;
        this.phoneNo = phoneNo;
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
}
