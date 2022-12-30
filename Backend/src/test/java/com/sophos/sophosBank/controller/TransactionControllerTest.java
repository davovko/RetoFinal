package com.sophos.sophosBank.controller;

import com.sophos.sophosBank.entity.Transaction;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import com.sophos.sophosBank.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TransactionControllerTest {
    @InjectMocks
    TransactionController transactionController;
    @Mock
    TransactionService transactionService;
    @Mock
    UserDetailServiceImplementation userDetailServiceImplementation;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllTransactionsByProductId() {
        List<Transaction> mockListTransactions = new ArrayList<>();
        Transaction mockTransaction1 = new Transaction();
        Transaction mockTransaction2 = new Transaction();
        mockListTransactions.add(mockTransaction1);
        mockListTransactions.add(mockTransaction2);

        int productId = 3;

        when(transactionService.getAllTransactionsByProductId(productId)).thenReturn(mockListTransactions);

        ResponseEntity<List<Transaction>> response = transactionController.getAllTransactionsByProductId(productId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void createTransactionSuccessfully(){
        Transaction mockTransaction = new Transaction();

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(transactionService.createTransaction(mockTransaction, 1)).thenReturn(mockTransaction);
        var response = transactionController.createTransaction(mockTransaction, any());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode() );
    }
    @Test
    void createTransactionFailed(){
        Transaction mockTransaction = new Transaction();

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(transactionService.createTransaction(mockTransaction, 1)).thenThrow(IllegalArgumentException.class);
        var response = transactionController.createTransaction(mockTransaction, any());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() );
    }
}