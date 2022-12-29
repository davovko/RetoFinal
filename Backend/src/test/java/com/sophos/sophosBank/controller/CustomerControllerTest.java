package com.sophos.sophosBank.controller;

import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import com.sophos.sophosBank.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CustomerControllerTest {
    @InjectMocks
    CustomerController customerControllerMock;
    @Mock
    CustomerService customerServiceMock;
    @Mock
    UserDetailServiceImplementation userDetailServiceImplementation;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getCustomers() {
        List<Customer> mockListCustomers = new ArrayList<Customer>();
        Customer mockCustomer1 = new Customer();
        Customer mockCustomer2 = new Customer();
        mockListCustomers.add(mockCustomer1);
        mockListCustomers.add(mockCustomer2);

        when(customerServiceMock.getAllCustomers()).thenReturn(mockListCustomers);
        //when(customerRepositoryMock.findAll()).thenReturn(mockListCustomers);
        ResponseEntity<List> responseEntity = new ResponseEntity<>(mockListCustomers, HttpStatus.OK);

        List<Customer> response = (List<Customer>) customerControllerMock.getCustomers();

        Assertions.assertTrue(mockListCustomers.equals(responseEntity.getBody()));
    }
    @Test
    void getCustomerById() {
    }


}