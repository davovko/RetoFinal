package com.sophos.sophosBank.repository;

import com.sophos.sophosBank.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM products WHERE customer_id = :customerId ORDER BY status_account_id ASC, balance  DESC ", nativeQuery = true)
    public List<Product> findAllProductsByCustomerId(@Param("customerId") int customer_id);

    @Query(value = "SELECT COUNT(*) FROM products WHERE customer_id = :customerId AND status_account_id != 3", nativeQuery = true)
    public int findAllActiveProductsByCustomerId(@Param("customerId") int customer_id);

    @Query(value = "SELECT COUNT(*) FROM products WHERE product_type_id = :product_type_id", nativeQuery = true)
    public int countAccounts(@Param("product_type_id") int product_type_id);

    @Query(value = "SELECT * FROM products WHERE customer_id = :customer_id AND gmf_exempt = TRUE AND status_account_id != 3", nativeQuery = true)
    public Product checkGmfExempt(@Param("customer_id") int customer_id);

}
