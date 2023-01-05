package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Transaction;
import java.util.List;

public interface TransactionService {

    public Transaction createTransaction(Transaction transaction, int activeUserId);
    public List<Transaction> getAllTransactionsByProductId(int product_id);

}
