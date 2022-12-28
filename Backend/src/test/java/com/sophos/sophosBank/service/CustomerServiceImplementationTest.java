package com.sophos.sophosBank.service;

import com.sophos.sophosBank.components.Validations;
import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.repository.CustomerRepository;
import com.sophos.sophosBank.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
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

import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class CustomerServiceImplementationTest {

    @InjectMocks
    private CustomerServiceImplementation customerServiceImplementation;
    @Mock
    CustomerRepository customerRepositoryMock;
    @Mock
    ProductRepository productRepositoryMock;
    @Mock
    Validations validationsMock;
    @Mock
    com.sophos.sophosBank.security.UserDetailServiceImplementation userDetailServiceImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

        Assertions.assertTrue(mockListCustomers.size() == response.size() );
        Assertions.assertTrue(mockListCustomers.containsAll(response) && response.containsAll(mockListCustomers));
    }
    @Test
    void getAllCustomerById(){

        Optional<Customer> mockCustomer = Optional.of(new Customer());
        mockCustomer.get().setCustomer_id(25);
        mockCustomer.get().setIdentification_type_Id(1);
        mockCustomer.get().setIdentification_number("1085293330");
        mockCustomer.get().setFirst_name("PEPE");
        mockCustomer.get().setLast_name("PEREZ");
        mockCustomer.get().setEmail("pepe.perez@gmail.com");
        mockCustomer.get().setDate_of_birth(LocalDate.now());
        mockCustomer.get().setStatus(true);

        int customerId = 25;

        Mockito.when(customerRepositoryMock.findById(customerId)).thenReturn(mockCustomer);

        Optional<Customer> response = customerServiceImplementation.getCustomerById(customerId);

        Assertions.assertEquals(mockCustomer, response);
    }
    @Test
    void allValidationsTrue(){
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
        Mockito.when(validationsMock.checkAge(LocalDate.now())).thenReturn(true);
        Mockito.when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        Mockito.when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        Mockito.when(validationsMock.validEmail(anyString())).thenReturn(true);

        String response = customerServiceImplementation.allValidations(mockCustomer);

        Assertions.assertEquals("", response);
        Assertions.assertEquals(0, response.length());

    }
    @Test
    void oneOrMoreValidationsFalse(){
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
        Mockito.when(validationsMock.checkAge(LocalDate.now())).thenReturn(false);
        Mockito.when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        Mockito.when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        Mockito.when(validationsMock.validEmail(anyString())).thenReturn(true);

        String response = customerServiceImplementation.allValidations(mockCustomer);

        Assertions.assertEquals("El cliente es menor de edad. ", response);
    }
    @Test
    void createCustomerSuccessfully(){
        Customer mockCustomer = new Customer();
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293670");
        mockCustomer.setFirst_name("carlos");
        mockCustomer.setLast_name("calero");
        mockCustomer.setEmail("carlos.calero@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Mockito.when(customerServiceImplementation.allValidations(mockCustomer)).thenReturn("");
        Mockito.when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        Mockito.when(customerRepositoryMock.save(mockCustomer)).thenReturn(mockCustomer);
        Customer response = customerServiceImplementation.createCustomer(mockCustomer, any());

        Assertions.assertEquals(mockCustomer, response);
    }




}
