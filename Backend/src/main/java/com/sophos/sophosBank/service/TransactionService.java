package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    public Transaction createTransaction(Transaction transaction, HttpServletRequest request);
    public List<Transaction> getAllTransactionsByProductId(int product_id);

}
