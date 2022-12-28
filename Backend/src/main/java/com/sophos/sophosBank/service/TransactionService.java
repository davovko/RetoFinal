package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Transaction;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TransactionService {

    public Transaction createTransaction(Transaction transaction, HttpServletRequest request);
    public List<Transaction> getAllTransactionsByProductId(int product_id);

}
