package com.sophos.sophosBank.service;

import com.sophos.sophosBank.components.Validations;
import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.repository.CustomerRepository;
import com.sophos.sophosBank.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllCustomers(){

        Customer mockCustomer1 = new Customer();
        Customer mockCustomer2 = new Customer();

        List<Customer> mockListCustomers = new ArrayList<>();

        mockListCustomers.add(mockCustomer1);
        mockListCustomers.add(mockCustomer2);

        when(customerRepositoryMock.findAll()).thenReturn(mockListCustomers);

        List<Customer> response = customerServiceImplementation.getAllCustomers();

        Assertions.assertTrue(mockListCustomers.size() == response.size() );
        Assertions.assertTrue(mockListCustomers.containsAll(response) && response.containsAll(mockListCustomers));
    }
    @Test
    void getAllCustomerById(){

        Optional<Customer> mockCustomer = Optional.of(new Customer());
        mockCustomer.get().setCustomer_id(25);

        int customerId = 25;

        when(customerRepositoryMock.findById(customerId)).thenReturn(mockCustomer);

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
        when(validationsMock.checkAge(LocalDate.now())).thenReturn(true);
        when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        when(validationsMock.validEmail(anyString())).thenReturn(true);

        String response = customerServiceImplementation.allValidations(mockCustomer, mockCustomer.getCustomer_id());

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
        when(validationsMock.checkAge(LocalDate.now())).thenReturn(false);
        when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        when(validationsMock.validEmail(anyString())).thenReturn(true);

        String response = customerServiceImplementation.allValidations(mockCustomer, mockCustomer.getCustomer_id());

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

        int activeUserId = 1;

        when(validationsMock.checkAge(LocalDate.now())).thenReturn(true);
        when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        when(validationsMock.validEmail(anyString())).thenReturn(true);
        when(customerRepositoryMock.save(mockCustomer)).thenReturn(mockCustomer);
        Customer response = customerServiceImplementation.createCustomer(mockCustomer, activeUserId);

        Assertions.assertEquals(mockCustomer, response);
    }
    @Test
    void createCustomerFailed(){
        Customer mockCustomer = new Customer();
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293670");
        mockCustomer.setFirst_name("carlos");
        mockCustomer.setLast_name("calero");
        mockCustomer.setEmail("carlos.calero@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        int activeUserId = 1;

        when(validationsMock.checkAge(LocalDate.now())).thenReturn(false);
        when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        when(validationsMock.validEmail(anyString())).thenReturn(true);
        when(customerRepositoryMock.save(mockCustomer)).thenReturn(mockCustomer);

        try{
            when(customerServiceImplementation.createCustomer(mockCustomer, activeUserId))
                    .thenThrow(new IllegalArgumentException("El cliente es menor de edad. "));
        } catch (Exception e){
            Assertions.assertEquals("El cliente es menor de edad. ", e.getMessage());
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }


    }
    @Test
    void updateCustomerSuccessfully(){
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293670");
        mockCustomer.setFirst_name("JAIRO");
        mockCustomer.setLast_name("VELASCO");
        mockCustomer.setEmail("davo.vko@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        Customer mockCustomer2 = mockCustomer;

        int customerId = 24;
        int activeUserId = 1;

        when(validationsMock.checkAge(LocalDate.now())).thenReturn(true);
        when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        when(validationsMock.validEmail(anyString())).thenReturn(true);
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(mockCustomer2));
        when(customerRepositoryMock.save(mockCustomer)).thenReturn(mockCustomer);
        Customer response = customerServiceImplementation.updateCustomer(mockCustomer, customerId, activeUserId);

        Assertions.assertEquals(mockCustomer, response);
    }
    @Test
    void updateCustomerFailed(){
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293670");
        mockCustomer.setFirst_name("carlos");
        mockCustomer.setLast_name("calero");
        mockCustomer.setEmail("carlos.calero@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        int activeUserId = 1;
        int customerId = 24;

        when(validationsMock.checkAge(LocalDate.now())).thenReturn(false);
        when(validationsMock.checkIdentificationNumber(anyString(), anyInt(),anyInt())).thenReturn(true);
        when(validationsMock.checkEmail(anyString(), anyInt())).thenReturn(true);
        when(validationsMock.validEmail(anyString())).thenReturn(true);

        try{
            when(customerServiceImplementation.updateCustomer(mockCustomer, customerId, activeUserId))
                    .thenThrow(new IllegalArgumentException("El cliente es menor de edad. "));
        } catch (Exception e){
            Assertions.assertEquals("El cliente es menor de edad. ", e.getMessage());
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    void deleteCustomerByIdSucessfully(){
        int customerId = 24;
        int activeUserId = 1;

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293670");
        mockCustomer.setFirst_name("JAIRO");
        mockCustomer.setLast_name("VELASCO");
        mockCustomer.setEmail("davo.vko@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        when(productRepositoryMock.findAllActiveProductsByCustomerId(customerId)).thenReturn(0);
        when(customerRepositoryMock.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(customerRepositoryMock.save(mockCustomer)).thenReturn(mockCustomer);

        boolean response = customerServiceImplementation.deleteCustomerById(customerId, activeUserId);

        Assertions.assertTrue(response);
    }
    @Test
    void deleteCustomerByIdFailed(){
        int customerId = 24;
        int activeUserId = 1;

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomer_id(24);
        mockCustomer.setIdentification_type_Id(1);
        mockCustomer.setIdentification_number("1085293670");
        mockCustomer.setFirst_name("JAIRO");
        mockCustomer.setLast_name("VELASCO");
        mockCustomer.setEmail("davo.vko@gmail.com");
        mockCustomer.setDate_of_birth(LocalDate.now());
        mockCustomer.setStatus(true);

        when(productRepositoryMock.findAllActiveProductsByCustomerId(customerId)).thenReturn(2);

        try{
            when(customerServiceImplementation.deleteCustomerById(customerId, activeUserId))
                    .thenThrow(new IllegalArgumentException("No se puede eliminar. El cliente aun tiene cuentas sin cancelar."));
        } catch (Exception e){
            Assertions.assertEquals("No se puede eliminar. El cliente aun tiene cuentas sin cancelar.", e.getMessage());
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }

    }

}
