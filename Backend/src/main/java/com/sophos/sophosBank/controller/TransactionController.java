package com.sophos.sophosBank.controller;

import com.sophos.sophosBank.entity.Transaction;
import com.sophos.sophosBank.entity.HttpResponse;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import com.sophos.sophosBank.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    UserDetailServiceImplementation userDetailServiceImplementation;

    @GetMapping("/{product_id}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByProductId(@PathVariable("product_id") int product_id){
        return new ResponseEntity<>(transactionService.getAllTransactionsByProductId(product_id), HttpStatus.OK);
    }

    @PostMapping("/createTransaction")
    public ResponseEntity<HttpResponse> createProduct(@RequestBody Transaction transaction, HttpServletRequest request){
        HttpResponse response = new HttpResponse();
        int activeUserId = userDetailServiceImplementation.userActive(request);
        try{
            response.success = true;
            response.data = transactionService.createTransaction(transaction, activeUserId);
            return new ResponseEntity<HttpResponse>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.success = false;
            response.message= e.getMessage();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.OK);
        }
    }
}
