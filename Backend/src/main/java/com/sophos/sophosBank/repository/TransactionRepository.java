package com.sophos.sophosBank.repository;

import com.sophos.sophosBank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>  {
    @Query(value = "SELECT * FROM transactions WHERE product_id = :product_id ORDER BY transaction_date DESC", nativeQuery = true)
    public List<Transaction> findAllTransactionsByProductId(@Param("product_id") int product_id);

}
