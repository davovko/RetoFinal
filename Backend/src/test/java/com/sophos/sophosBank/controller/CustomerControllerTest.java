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
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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

        ResponseEntity<List<Customer>> response = customerControllerMock.getCustomers();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getCustomerById() {
        Optional<Customer> mockCustomer = Optional.of(new Customer());
        int customerId = 1;

        when(customerServiceMock.getCustomerById(customerId)).thenReturn(mockCustomer);
        ResponseEntity<Customer> response = customerControllerMock.getCustomerById(customerId);
        Assertions.assertEquals( HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getCustomerByIdFailed() {
        Optional<Customer> mockCustomer = Optional.empty();
        int customerId = 1;

        when(customerServiceMock.getCustomerById(customerId)).thenReturn(mockCustomer);
        ResponseEntity<Customer> response = customerControllerMock.getCustomerById(customerId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void createCustomerSuccessfully(){
        Customer mockCustomer = new Customer();

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(customerServiceMock.createCustomer(mockCustomer, 1)).thenReturn(mockCustomer);
        var response = customerControllerMock.createCustomer(mockCustomer, any());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode() );
    }
    @Test
    void createCustomerFailed(){
        Customer mockCustomer = new Customer();

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(customerServiceMock.createCustomer(mockCustomer, 1)).thenThrow(IllegalArgumentException.class);

        var response = customerControllerMock.createCustomer(mockCustomer, any());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() );
    }
    @Test
    void updateCustomerSuccessfully(){
        Customer mockCustomer = new Customer();
        int customerId = 2;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(customerServiceMock.updateCustomer(mockCustomer, customerId, 1)).thenReturn(mockCustomer);
        var response = customerControllerMock.updateCustomer(mockCustomer, customerId, any());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode() );
    }
    @Test
    void updateCustomerFailed(){
        Customer mockCustomer = new Customer();
        int customerId = 2;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(customerServiceMock.updateCustomer(mockCustomer, customerId, 1)).thenThrow(IllegalArgumentException.class);

        var response = customerControllerMock.updateCustomer(mockCustomer, customerId, any());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() );
    }
    @Test
    void deleteCustomerSuccessfully(){
        int customerId = 2;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(customerServiceMock.deleteCustomerById(customerId, 1)).thenReturn(true);
        var response = customerControllerMock.deleteCustomerById(customerId, any());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode() );
    }
    @Test
    void deleteCustomerFailed(){
        int customerId = 2;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(customerServiceMock.deleteCustomerById(customerId, 1)).thenThrow(IllegalArgumentException.class);

        var response = customerControllerMock.deleteCustomerById(customerId, any());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() );
    }


}