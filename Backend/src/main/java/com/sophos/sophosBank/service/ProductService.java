package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public Product createProduct(Product product, int activeUserId);
    public List<Product> getAllProductsByCustomerId(int customer_id);
    public Optional<Product> getProductById(int product_id);
    public Product updateGmfExempt(int product_id, int activeUserId);
    public Product updateStatusAccount(int status_account_id, int product_id, int activeUserId);
}
