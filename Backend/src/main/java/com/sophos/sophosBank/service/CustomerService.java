package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Customer;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Optional<Customer> getCustomerById(int customer_id);
    public Customer createCustomer(Customer customer, HttpServletRequest request) throws UnsupportedEncodingException;
    public Customer updateCustomer(Customer customer, int customer_id, HttpServletRequest request);
    public boolean deleteCustomerById(int customer_id, HttpServletRequest request);
    public boolean checkAge(LocalDate date_of_birth);
    public boolean checkIdentificationNumber(String identification_number, int identification_type_Id, int customer_id);
    public boolean checkEmail(String email, int customer_id);
    public boolean validEmail(String email);
}
