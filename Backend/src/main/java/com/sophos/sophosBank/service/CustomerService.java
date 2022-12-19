package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerService {
    public Customer createCustomer(Customer customer);
    public List<Customer> getAllCustomers();
    public Optional<Customer> getCustomerById(int customer_id);
    public Customer updateCustomer(Customer customer, int customer_id);
    public boolean deleteCustomerById(int customer_id);
}
