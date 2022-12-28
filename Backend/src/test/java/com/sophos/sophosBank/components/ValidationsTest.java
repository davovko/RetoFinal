package com.sophos.sophosBank.components;

import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.repository.CustomerRepository;
import com.sophos.sophosBank.repository.ProductRepository;
import com.sophos.sophosBank.service.CustomerServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class ValidationsTest {


    @InjectMocks
    private Validations validations;
    @Mock
    CustomerRepository customerRepositoryMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validEmail() {
        String email = "davo.vko@gmail.com";

        boolean response = validations.validEmail(email);

        Assertions.assertTrue(response);
    }

    @Test
    void invalidEmail() {
        String email = "davo.vko@gmailcom";

        boolean response = validations.validEmail(email);

        Assertions.assertFalse(response);
    }

    @Test
    void checkAgeWhenCustomerIsYounger() {
        LocalDate date_of_birth = LocalDate.parse("2020-11-18");

        boolean response = validations.checkAge(date_of_birth);

        Assertions.assertFalse(response, "Cliente menor de edad");
    }

    @Test
    void checkAgeWhenCustomerIsAdult() {
        LocalDate date_of_birth = LocalDate.parse("1991-11-18");

        boolean response = validations.checkAge(date_of_birth);

        Assertions.assertTrue(response, "Cliente mayor de edad");
    }

    @Test
    void checkEmailWhenIsCreationCustomerAndEmailDoesntExist() {

        String email = "pepe.perez@gmail.com";
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
        int customerId = 0;

        boolean response = validations.checkEmail(email,customerId);

        Assertions.assertTrue(response);
    }

    @Test
    void checkEmailWhenIsCreationCustomerAndEmailExists() {

        String email = "pepe.perez@gmail.com";
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085244930");
        mockCustomer.setFirst_name("PEPE");
        mockCustomer.setLast_name("PEREZ");
        mockCustomer.setEmail("pepe.perez@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
        int customerId = 0;

        boolean response = validations.checkEmail(email,customerId);

        Assertions.assertFalse(response);
    }

    @Test
    void checkEmailWhenCustomerIsUpdatedAndEmailNotExist() {

        String email = "pepe.perez@gmail.com";
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = validations.checkEmail(email,customerId);

        Assertions.assertTrue(response);
    }

    @Test
    void checkEmailWhenCustomerIsUpdatedAndEmailDoesntExistInAnotherUser() {

        String email = "davo.vko@gmail.com";
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085292930");
        mockCustomer.setFirst_name("DAVID");
        mockCustomer.setLast_name("VELASCO");
        mockCustomer.setEmail("davo.vko@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = validations.checkEmail(email,customerId);

        Assertions.assertTrue(response);
    }

    @Test
    void checkEmailWhenCustomerIsUpdatedAndEmailExistInAnotherUser() {

        String email = "pepe.perez@gmail.com";
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(25);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293330");
        mockCustomer.setFirst_name("PEPE");
        mockCustomer.setLast_name("PEREZ");
        mockCustomer.setEmail("pepe.perez@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = validations.checkEmail(email,customerId);

        Assertions.assertFalse(response);
    }
    @Test
    void checkIdentificationNumberWhenIsCreationCustomerAndIdentificationNumberDoesntExist() {

        String identificationNumber = "1085292999";
        int identification_type_Id = 1;
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByIdentificationNumber(identificationNumber, identification_type_Id)).thenReturn(mockCustomer);
        int customerId = 0;

        boolean response = validations.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

        Assertions.assertTrue(response);
    }

    @Test
    void checkIdentificationNumberWhenIsCreationCustomerAndIdentificationNumberExists() {

        String identificationNumber = "1085292930";
        int identification_type_Id = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085292930");
        mockCustomer.setFirst_name("PEPE");
        mockCustomer.setLast_name("PEREZ");
        mockCustomer.setEmail("pepe.perez@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerRepositoryMock.findCustomerByIdentificationNumber(identificationNumber, identification_type_Id)).thenReturn(mockCustomer);
        int customerId = 0;

        boolean response = validations.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

        Assertions.assertFalse(response);
    }

    @Test
    void checkIdentificationNumberWhenCustomerIsUpdatedAndIdentificationNumberNotExist() {

        String identificationNumber = "1085292944";
        int identification_type_Id = 1;
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByIdentificationNumber(identificationNumber, identification_type_Id)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = validations.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

        Assertions.assertTrue(response);
    }

    @Test
    void checkIdentificationNumberWhenCustomerIsUpdatedAndIdentificationNumberDoesntExistInAnotherUser() {

        String identificationNumber = "10862727389";
        int identification_type_Id = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085292930");
        mockCustomer.setFirst_name("DAVID");
        mockCustomer.setLast_name("VELASCO");
        mockCustomer.setEmail("davo.vko@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerRepositoryMock.findCustomerByIdentificationNumber(identificationNumber, identification_type_Id)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = validations.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

        Assertions.assertTrue(response);
    }

    @Test
    void checkIdentificationNumberWhenCustomerIsUpdatedAndIdentificationNumberExistInAnotherUser() {

        String identificationNumber = "1085293330";
        int identification_type_Id = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(25);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293330");
        mockCustomer.setFirst_name("PEPE");
        mockCustomer.setLast_name("PEREZ");
        mockCustomer.setEmail("pepe.perez@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerRepositoryMock.findCustomerByIdentificationNumber(identificationNumber, identification_type_Id)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = validations.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

        Assertions.assertFalse(response);
    }

}