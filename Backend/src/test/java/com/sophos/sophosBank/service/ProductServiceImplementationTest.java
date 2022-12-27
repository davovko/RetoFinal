package com.sophos.sophosBank.service;

import com.sophos.sophosBank.repository.ProductRepository;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplementationTest {
    @InjectMocks
    private ProductServiceImplementation productServiceImplementation;
    @Mock
    ProductRepository productRepository;
    @Mock
    com.sophos.sophosBank.security.UserDetailServiceImplementation UserDetailServiceImplementation;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void newSavingsAccountNumber() {
        int product_id = 12;
        int product_type_id = 1;

        String response = productServiceImplementation.newAccountNumber(product_id, product_type_id);

        Assertions.assertEquals("4600000012", response);
    }

    @Test
    void newCurrentAccountNumber() {
        int product_id = 14;
        int product_type_id = 2;

        String response = productServiceImplementation.newAccountNumber(product_id, product_type_id);

        Assertions.assertEquals("2300000014", response);
    }
}