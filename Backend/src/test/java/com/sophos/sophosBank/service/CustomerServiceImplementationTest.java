package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.repository.CustomerRepository;
import com.sophos.sophosBank.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
class CustomerServiceImplementationTest {

    @InjectMocks
    private CustomerServiceImplementation customerServiceImplementation;
    @Mock
    CustomerRepository customerRepositoryMock;
    @Mock
    ProductRepository productRepositoryMock;
    @Mock
    com.sophos.sophosBank.security.UserDetailServiceImplementation UserDetailServiceImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validEmail() {
        String email = "davo.vko@gmail.com";

        boolean response = customerServiceImplementation.validEmail(email);

        Assertions.assertTrue(response);
    }

    @Test
    void invalidEmail() {
        String email = "davo.vko@gmailcom";

        boolean response = customerServiceImplementation.validEmail(email);

        Assertions.assertFalse(response);
    }

    @Test
    void checkAgeWhenCustomerIsYounger() {
        LocalDate date_of_birth = LocalDate.parse("2020-11-18");

        boolean response = customerServiceImplementation.checkAge(date_of_birth);

        Assertions.assertFalse(response, "Cliente menor de edad");
    }

    @Test
    void checkAgeWhenCustomerIsAdult() {
        LocalDate date_of_birth = LocalDate.parse("1991-11-18");

        boolean response = customerServiceImplementation.checkAge(date_of_birth);

        Assertions.assertTrue(response, "Cliente mayor de edad");
    }

    @Test
    void checkEmailWhenIsCreationCustomerAndEmailDoesntExist() {

        String email = "pepe.perez@gmail.com";
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
        int customerId = 0;

        boolean response = customerServiceImplementation.checkEmail(email,customerId);

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

        boolean response = customerServiceImplementation.checkEmail(email,customerId);

        Assertions.assertFalse(response);
    }

    @Test
    void checkEmailWhenCustomerIsUpdatedAndEmailNotExist() {

        String email = "pepe.perez@gmail.com";
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByEmail(email)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = customerServiceImplementation.checkEmail(email,customerId);

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

        boolean response = customerServiceImplementation.checkEmail(email,customerId);

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

        boolean response = customerServiceImplementation.checkEmail(email,customerId);

        Assertions.assertFalse(response);
    }
    @Test
    void checkIdentificationNumberWhenIsCreationCustomerAndIdentificationNumberDoesntExist() {

        String identificationNumber = "1085292999";
        int identification_type_Id = 1;
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByIdentificationNumber(identificationNumber, identification_type_Id)).thenReturn(mockCustomer);
        int customerId = 0;

        boolean response = customerServiceImplementation.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

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

        boolean response = customerServiceImplementation.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

        Assertions.assertFalse(response);
    }

    @Test
    void checkIdentificationNumberWhenCustomerIsUpdatedAndIdentificationNumberNotExist() {

        String identificationNumber = "1085292944";
        int identification_type_Id = 1;
        Customer mockCustomer = null;

        Mockito.when(customerRepositoryMock.findCustomerByIdentificationNumber(identificationNumber, identification_type_Id)).thenReturn(mockCustomer);
        int customerId = 24;

        boolean response = customerServiceImplementation.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

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

        boolean response = customerServiceImplementation.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

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

        boolean response = customerServiceImplementation.checkIdentificationNumber(identificationNumber, identification_type_Id,customerId);

        Assertions.assertFalse(response);
    }

    @Test
    void getAllCustomers(){

        Customer mockCustomer1 = new Customer();
        mockCustomer1.setCustomer_id(25);
        mockCustomer1.setIdentification_type_Id(1);
        mockCustomer1.setIdentification_number("1085293330");
        mockCustomer1.setFirst_name("PEPE");
        mockCustomer1.setLast_name("PEREZ");
        mockCustomer1.setEmail("pepe.perez@gmail.com");
        mockCustomer1.setDate_of_birth(LocalDate.now());
        mockCustomer1.setDate_of_birth(LocalDate.now());
        mockCustomer1.setStatus(true);

        Customer mockCustomer2 = new Customer();
        mockCustomer2.setCustomer_id(26);
        mockCustomer2.setIdentification_type_Id(1);
        mockCustomer2.setIdentification_number("1085293335");
        mockCustomer2.setFirst_name("SARA");
        mockCustomer2.setLast_name("CORAL");
        mockCustomer2.setEmail("sara.coral@gmail.com");
        mockCustomer2.setDate_of_birth(LocalDate.now());
        mockCustomer2.setDate_of_birth(LocalDate.now());
        mockCustomer2.setStatus(true);

        List<Customer> mockListCustomers = new ArrayList<>();

        mockListCustomers.add(mockCustomer1);
        mockListCustomers.add(mockCustomer2);

        Mockito.when(customerRepositoryMock.findAll()).thenReturn(mockListCustomers);

        List<Customer> response = customerServiceImplementation.getAllCustomers();

        Assertions.assertEquals(2, response.size());
    }
    @Test
    void getAllCustomerById(){

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

        int customerId = 25;

        Mockito.when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        List<Customer> response = customerServiceImplementation.getAllCustomers();

        Assertions.assertEquals(1, response.size());
    }
}