package com.sophos.sophosBank.controller;

import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.entity.HttpResponse;
import com.sophos.sophosBank.entity.Product;
import com.sophos.sophosBank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{customer_id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customer_id") int customer_id){
        return customerService.getCustomerById(customer_id)
                .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<HttpResponse> createCustomer(@RequestBody Customer customer){
        HttpResponse response = new HttpResponse();
        try{
            response.success = true;
            response.data = customerService.createCustomer(customer);
            return new ResponseEntity<HttpResponse>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.success = false;
            response.message= e.getMessage();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.OK);
        }
    }

    @PutMapping("/updateCustomer/{customer_id}")
    public ResponseEntity<HttpResponse> updateCustomer(@RequestBody Customer customer, @PathVariable("customer_id") int customer_id){
        HttpResponse response = new HttpResponse();
        try{
            response.success = true;
            response.data = customerService.updateCustomer(customer, customer_id);
            return new ResponseEntity<HttpResponse>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.success = false;
            response.message= e.getMessage();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.OK);
        }

    }

    @DeleteMapping("/{customer_id}")
    public ResponseEntity deleteCustomerById(@PathVariable("customer_id") int customer_id){
        HttpResponse response = new HttpResponse();
        try{
            response.success = true;
            response.data = customerService.deleteCustomerById(customer_id);
            return new ResponseEntity<HttpResponse>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.success = false;
            response.message= e.getMessage();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.OK);
        }
    }
}
