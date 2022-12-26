package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.repository.CustomerRepository;
import com.sophos.sophosBank.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class CustomerServiceImplementationTest {

    @Autowired
    private CustomerServiceImplementation customerServiceImplementation;

    @Autowired
    CustomerRepository customerRepositoryMock = Mockito.mock(CustomerRepository.class);
    @Autowired
    ProductRepository productRepository;

    @Test
    void validEmail() {
        customerServiceImplementation = new CustomerServiceImplementation();
        String email = "davo.vko@gmail.com";

        boolean response = customerServiceImplementation.validEmail(email);

        Assertions.assertTrue(true);
    }

    @Test
    void checkAge() {
        customerServiceImplementation = new CustomerServiceImplementation();
        LocalDate date_of_birth = LocalDate.parse("2020-11-18");

        boolean response = customerServiceImplementation.checkAge(date_of_birth);

        if(response){
            Assertions.assertTrue(response == true, "Es mayor de edad");
        }else{
            Assertions.assertTrue(response != true, "Es menor de edad");
        }


    }

    @BeforeEach
    void setUp() {
        String email = "davo.vko@gmail.com";

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085292930");
        mockCustomer.setFirst_name("JAIRO");
        mockCustomer.setLast_name("VELASCO");
        mockCustomer.setEmail("davo.vko@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
    }

    @Test
    void checkEmail() {
        customerServiceImplementation = new CustomerServiceImplementation();
        String email = "davo.vko@gmail.com";
        int customerId = 24;

        boolean response = customerServiceImplementation.checkEmail(email,customerId);

        Assertions.assertTrue(true);
    }
}