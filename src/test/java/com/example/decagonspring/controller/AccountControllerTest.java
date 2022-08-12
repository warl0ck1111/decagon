package com.example.decagonspring.controller;

import com.example.decagonspring.dto.CreateAccountRequest;
import com.example.decagonspring.dto.TransactionRequest;
import com.example.decagonspring.dto.TransactionResponse;
import com.example.decagonspring.model.Account;
import com.example.decagonspring.model.Transaction;
import com.example.decagonspring.model.TransactionType;
import com.example.decagonspring.service.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
class AccountControllerTest {
    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountServiceImpl accountServiceImpl;

    @Test
    void testCreateAccount() {
        AccountController accountController = new AccountController(new AccountServiceImpl());
        String actualCreateAccountResult = accountController
                .createAccount(new CreateAccountRequest("Dr Jane Doe", "4105551212"));
    }


    @Test
    void testDeposit() throws Exception {
        when(this.accountServiceImpl.deposit((String) any(), anyDouble()))
                .thenReturn(new TransactionResponse(50, 50));

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(10.0);
        transactionRequest.setAccountNo("3");
        String content = (new ObjectMapper()).writeValueAsString(transactionRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/account/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"balance\":50.0,\"amount\":50.0}"));
    }

    @Test
    void testGetTransactions() throws Exception {
        when(this.accountServiceImpl.getTransactions((String) any())).thenReturn(new ArrayList<Transaction>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/account/transactions");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetTransactions2() throws Exception {
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList.add(new Transaction("Dr Jane Doe", "3", 10.0, TransactionType.WITHDRAWAL, 1L));
        when(this.accountServiceImpl.getTransactions((String) any())).thenReturn(transactionList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/account/transactions");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"accountName\":\"Dr Jane Doe\",\"accountNo\":\"3\",\"amount\":10.0,\"transactionType\":\"WITHDRAWAL\",\"createdAt"
                                        + "\":1}]"));
    }

    @Test
    void testGetAccounts() throws Exception {
        when(this.accountServiceImpl.getAccountMap()).thenReturn(new HashMap<String, Account>(1));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/account");
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{}"));
    }


    @Test
    void testWithDraw() throws Exception {
        when(this.accountServiceImpl.withdraw((String) any(), anyDouble()))
                .thenReturn(new TransactionResponse(50, 50));

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(10.0);
        transactionRequest.setAccountNo("3");
        String content = (new ObjectMapper()).writeValueAsString(transactionRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/account/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"balance\":50.0,\"amount\":50.0}"));
    }
}

