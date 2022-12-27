package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product createProduct(Product product, HttpServletRequest request);
    public List<Product> getAllProductsByCustomerId(int customer_id);
    public Optional<Product> getProductById(int product_id);
    public Product updateGmfExempt(int product_id, HttpServletRequest request);
    public Product updateStatusAccount(int status_account_id, int product_id, HttpServletRequest request);
    public String newAccountNumber(int product_id, int product_type_id);
}
