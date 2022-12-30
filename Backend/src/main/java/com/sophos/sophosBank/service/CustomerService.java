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
    public Customer createCustomer(Customer customer, int activeUserId);
    public Customer updateCustomer(Customer customer, int customer_id, int activeUserId);
    public boolean deleteCustomerById(int customer_id, int activeUserId);
    public String allValidations(Customer customer);
}
