package com.sophos.sophosBank.service;

import com.sophos.sophosBank.components.Validations;
import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import com.sophos.sophosBank.repository.CustomerRepository;
import com.sophos.sophosBank.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class CustomerServiceImplementation implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserDetailServiceImplementation UserDetailServiceImplementation;
    @Autowired
    Validations validations;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(int customer_id) {
        return customerRepository.findById(customer_id);
    }

    @Override
    public Customer createCustomer(Customer customer, int activeUserId) {
        String message = allValidations(customer);

        if (message.isEmpty()){
            customer.setIdentification_number(customer.getIdentification_number().trim().toUpperCase());
            customer.setFirst_name(customer.getFirst_name().trim().toUpperCase());
            customer.setMiddle_name(customer.getMiddle_name() != null ? customer.getMiddle_name().trim().toUpperCase() : null);
            customer.setLast_name(customer.getLast_name().trim().toUpperCase());
            customer.setSecond_last_name(customer.getSecond_last_name() != null ? customer.getSecond_last_name().trim().toUpperCase() : null);

            customer.setEmail(customer.getEmail().trim().toLowerCase());
            customer.setCreation_user_id(activeUserId);
            return customerRepository.save(customer);
        }

        throw new IllegalArgumentException(message);
    }

    @Override
    public Customer updateCustomer(Customer customer, int customer_id, int activeUserId) {
        String message = allValidations(customer);

        if (message.isEmpty()){
            Optional<Customer> oldCustomer = customerRepository.findById(customer_id);
            Customer newCustomer = oldCustomer.get();

            newCustomer.setIdentification_type_Id(customer.getIdentification_type_Id());
            newCustomer.setIdentification_number(customer.getIdentification_number().trim().toUpperCase());
            newCustomer.setFirst_name(customer.getFirst_name().trim().toUpperCase());
            newCustomer.setMiddle_name(customer.getMiddle_name() != null ? customer.getMiddle_name().trim().toUpperCase() : null);
            newCustomer.setLast_name(customer.getLast_name().trim().toUpperCase());
            newCustomer.setSecond_last_name(customer.getSecond_last_name() != null ? customer.getSecond_last_name().trim().toUpperCase() : null);
            newCustomer.setEmail(customer.getEmail().trim().toLowerCase());
            newCustomer.setDate_of_birth((customer.getDate_of_birth()));
            newCustomer.setModification_date(LocalDateTime.now());
            newCustomer.setModification_user_id(activeUserId);

            return customerRepository.save(newCustomer);
        }

        throw new IllegalArgumentException(message);
    }

    @Override
    public boolean deleteCustomerById(int customer_id, int activeUserId) {

        int activeProducts = productRepository.findAllActiveProductsByCustomerId(customer_id);

        if(activeProducts == 0){
            Optional<Customer> oldCustomer = customerRepository.findById(customer_id);
            Customer newCustomer = oldCustomer.get();

            newCustomer.setStatus(false);
            newCustomer.setModification_user_id(activeUserId);
            customerRepository.save(newCustomer);
            return true;
        }
        String message = "No se puede eliminar. El cliente aun tiene cuentas sin cancelar.";
        throw new IllegalArgumentException(message);
    }
    @Override
    public String allValidations(Customer customer){
        boolean adult = validations.checkAge(customer.getDate_of_birth());
        boolean duplicateIdentification = validations.checkIdentificationNumber(customer.getIdentification_number().trim().toUpperCase(), customer.getIdentification_type_Id(), 0);
        boolean email = validations.checkEmail(customer.getEmail().trim().toLowerCase(), 0);
        boolean validEmail = validations.validEmail(customer.getEmail().trim());
        boolean validName = (customer.getFirst_name().trim().length() >= 2 && customer.getLast_name().trim().length() >= 2 ? true: false);

        String message = !adult ? "El cliente es menor de edad. ": "";
        message += !duplicateIdentification ? "El número de identificación ya existe en la base de datos. ": "";
        message += !email ? "El email ya existe en la base de datos.": "";
        message += !validEmail ? "El email ingresado no tiene el formato válido.": "";
        message += !validName ? "La extensión del Primer Nombre y del Primer Apellido NO puede ser menor a 2 caracteres.": "";

        return message;
    }
}
