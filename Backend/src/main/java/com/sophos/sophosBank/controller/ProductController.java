package com.sophos.sophosBank.controller;

import com.sophos.sophosBank.entity.Product;
import com.sophos.sophosBank.entity.HttpResponse;
import com.sophos.sophosBank.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    UserDetailServiceImplementation userDetailServiceImplementation;
    @GetMapping("/listProductsByUserId/{customer_id}")
    public ResponseEntity<List<Product>> getAllProductsByCustomerId(@PathVariable("customer_id") int customer_id){
        return new ResponseEntity<>(productService.getAllProductsByCustomerId(customer_id), HttpStatus.OK);
    }
    @GetMapping("/{product_id}")
    public ResponseEntity<Product> getProductById(@PathVariable("product_id") int product_id){
        return productService.getProductById(product_id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/createProduct")
    public ResponseEntity<HttpResponse> createProduct(@RequestBody Product product, HttpServletRequest request){
        HttpResponse response = new HttpResponse();
        int activeUserId = userDetailServiceImplementation.userActive(request);
        try{
            response.success = true;
            response.data = productService.createProduct(product, activeUserId);
            return new ResponseEntity<HttpResponse>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.success = false;
            response.message= e.getMessage();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.OK);
        }
    }
    @PatchMapping("/updateGmfExempt/{product_id}")
    public ResponseEntity<HttpResponse> updateGmfExempt(@PathVariable("product_id") int product_id, HttpServletRequest request){
        HttpResponse response = new HttpResponse();
        int activeUserId = userDetailServiceImplementation.userActive(request);
        try{
            response.success = true;
            response.data = productService.updateGmfExempt(product_id, activeUserId).isGmf_exempt();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.success = false;
            response.message= e.getMessage();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.OK);
        }

    }
    @PatchMapping("/updateStatusAccount/{product_id}/{status_account_id}")
    public ResponseEntity<HttpResponse> updateStatusAccount(@PathVariable("product_id") int product_id, @PathVariable("status_account_id") int status_account_id, HttpServletRequest request){
        HttpResponse response = new HttpResponse();
        int activeUserId = userDetailServiceImplementation.userActive(request);
        try{
            response.success = true;
            response.data = productService.updateStatusAccount(status_account_id, product_id,activeUserId).getStatus_account_id();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            response.success = false;
            response.message= e.getMessage();
            return new ResponseEntity<HttpResponse>(response, HttpStatus.OK);
        }

    }
}
