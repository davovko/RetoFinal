package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Transaction;
import com.sophos.sophosBank.repository.ProductRepository;
import com.sophos.sophosBank.repository.TransactionRepository;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class TransactionServiceImplementationTest {
    @InjectMocks
    private TransactionServiceImplementation transactionServiceImplementation;
    @Mock
    ProductRepository productRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    com.sophos.sophosBank.security.UserDetailServiceImplementation UserDetailServiceImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllTransactionsByProductId(){
        int product_id = 1;

        Transaction mockTransaction1 = new Transaction();
        mockTransaction1.setTransaction_id(1);
        mockTransaction1.setTransaction_type_id(1);
        mockTransaction1.setProduct_id(1);
        mockTransaction1.setTransaction_date(LocalDateTime.parse("2022-12-27T15:37:27.252280400"));
        mockTransaction1.setDescription("CONSIGNACIÓN 1");
        mockTransaction1.setTransaction_value(1000);

        Transaction mockTransaction2 = new Transaction();
        mockTransaction2.setTransaction_id(1);
        mockTransaction2.setTransaction_type_id(2);
        mockTransaction2.setProduct_id(1);
        mockTransaction2.setTransaction_date(LocalDateTime.parse("2022-11-21T14:37:27.252280400"));
        mockTransaction2.setDescription("RETIRO 1");
        mockTransaction2.setTransaction_value(500);

        List<Transaction> mockListTransactions = new ArrayList<>();
        mockListTransactions.add(mockTransaction1);
        mockListTransactions.add(mockTransaction2);

        Mockito.when(transactionRepository.findAllTransactionsByProductId(product_id)).thenReturn(mockListTransactions);

        List<Transaction> response = transactionServiceImplementation.getAllTransactionsByProductId(product_id);

        Assertions.assertEquals(2, mockListTransactions.size());
    }


}