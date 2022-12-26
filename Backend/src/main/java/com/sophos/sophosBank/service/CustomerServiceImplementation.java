package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import com.sophos.sophosBank.repository.CustomerRepository;
import com.sophos.sophosBank.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CustomerServiceImplementation implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserDetailServiceImplementation UserDetailServiceImplementation;

    @Override
    public Customer createCustomer(Customer customer, HttpServletRequest request) {
        boolean adult = checkAge(customer.getDate_of_birth());
        boolean duplicateIdentification = checkIdentificationNumber(customer.getIdentification_number(), customer.getIdentification_type_Id(), 0);
        boolean email = checkEmail(customer.getEmail(), 0);
        boolean validEmail = validEmail(customer.getEmail().trim());
        boolean validName = (customer.getFirst_name().trim().length() >= 2 && customer.getLast_name().trim().length() >= 2 ? true: false);

        if (adult && duplicateIdentification && email && validEmail && validName){
            customer.setIdentification_number(customer.getIdentification_number().trim().toUpperCase());
            customer.setFirst_name(customer.getFirst_name().trim().toUpperCase());
            customer.setMiddle_name(customer.getMiddle_name() != null ? customer.getMiddle_name().trim().toUpperCase() : null);
            customer.setLast_name(customer.getLast_name().trim().toUpperCase());
            customer.setSecond_last_name(customer.getSecond_last_name() != null ? customer.getSecond_last_name().trim().toUpperCase() : null);

            customer.setEmail(customer.getEmail().trim().toLowerCase());
            customer.setCreation_user_id(UserDetailServiceImplementation.userActive(request));
            return customerRepository.save(customer);
        }
        ;
        String message = !adult ? "El cliente es menor de edad. ": "";
        message += !duplicateIdentification ? "El número de identificación ya existe en la base de datos. ": "";
        message += !email ? "El email ya existe en la base de datos.": "";
        message += !validEmail ? "El email ingresado no tiene el formato válido.": "";
        message += !validName ? "La extensión del Primer Nombre y del Primer Apellido NO puede ser menor a 2 caracteres.": "";
        throw new IllegalArgumentException(message);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(int customer_id) {
        return customerRepository.findById(customer_id);
    }

    @Override
    public Customer updateCustomer(Customer customer, int customer_id, HttpServletRequest request) {
        boolean adult = checkAge(customer.getDate_of_birth());
        boolean duplicateIdentification = checkIdentificationNumber(customer.getIdentification_number(), customer.getIdentification_type_Id(), customer_id);
        boolean email = checkEmail(customer.getEmail(), customer_id);
        boolean validEmail = validEmail(customer.getEmail().trim());
        boolean validName = (customer.getFirst_name().trim().length() >= 2 && customer.getLast_name().trim().length() >= 2 );

        if (adult && duplicateIdentification && email && validEmail && validName){
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
            newCustomer.setModification_user_id(UserDetailServiceImplementation.userActive(request));

            return customerRepository.save(newCustomer);
        }
        String message = !adult ? "El cliente es menor de edad. ": "";
        message += !duplicateIdentification ? "El número de identificación ya existe en la base de datos. ": "";
        message += !email ? "El email ya existe en la base de datos.": "";
        message += !validEmail ? "El email ingresado no tiene el formato válido.": "";
        message += !validName ? "La extensión del Primer Nombre y del Primer Apellido NO puede ser menor a 2 caracteres.": "";
        throw new IllegalArgumentException(message);
    }

    @Override
    public boolean deleteCustomerById(int customer_id, HttpServletRequest request) {

        int activeProducts = productRepository.findAllActiveProductsByCustomerId(customer_id);

        if(activeProducts == 0){
            Optional<Customer> oldCustomer = customerRepository.findById(customer_id);
            Customer newCustomer = oldCustomer.get();

            newCustomer.setStatus(false);
            newCustomer.setModification_user_id(UserDetailServiceImplementation.userActive(request));
            customerRepository.save(newCustomer);
            return true;
        }
        String message = "No se puede eliminar. El cliente aun tiene cuentas sin cancelar.";
        throw new IllegalArgumentException(message);
    }

    public boolean checkAge(LocalDate date_of_birth){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Period age = Period.between(LocalDate.parse(date_of_birth.format(formatter), formatter), LocalDate.now());

        return age.getYears() >= 18;
    }


    public boolean checkIdentificationNumber(String identification_number, int identification_type_Id, int customer_id){
        boolean response = true;
        Customer customer = customerRepository.findCustomerByIdentificationNumber(identification_number);

        if(customer != null){
            if(customer.getIdentification_number().equals(identification_number) && customer.getIdentification_type_Id() == identification_type_Id && customer_id == 0){
                response = false;
            }else if(customer.getIdentification_number().equals(identification_number) && customer.getIdentification_type_Id() == identification_type_Id && customer.getCustomer_id() != customer_id ){
                response = false;
            }
        }

        return response ;
    }

    public boolean checkEmail(String email, int customer_id){
        boolean response = true;
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findCustomerByEmail(email));

        if(customer != null){
            if(customer.get().getEmail().equals(email) && customer_id == 0){
                response = false;
            } else if(customer.get().getEmail().equals(email) && customer.get().getCustomer_id() != customer_id ){
                response = false;
            }
        }

        return response;
    }

    public boolean validEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        return pattern.matcher(email).find();
    }
}
