package com.sophos.sophosBank.controller;

import com.sophos.sophosBank.entity.Product;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
import com.sophos.sophosBank.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProductControllerTest {
    @InjectMocks
    ProductController productController;
    @Mock
    ProductService productServiceMock;
    @Mock
    UserDetailServiceImplementation userDetailServiceImplementation;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllProductsByCustomerId() {
        List<Product> mockListProducts = new ArrayList<>();
        Product mockProduct1 = new Product();
        Product mockProduct2 = new Product();
        mockListProducts.add(mockProduct1);
        mockListProducts.add(mockProduct2);

        int customerId = 2;

        when(productServiceMock.getAllProductsByCustomerId(customerId)).thenReturn(mockListProducts);

        ResponseEntity<List<Product>> response = productController.getAllProductsByCustomerId(customerId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test
    void getProductById() {
        Optional<Product> mockProduct = Optional.of(new Product());
        int productId = 3;

        when(productServiceMock.getProductById(productId)).thenReturn(mockProduct);
        ResponseEntity<Product> response = productController.getProductById(productId);
        Assertions.assertEquals( HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void getProductByIdFailed() {
        Optional<Product> mockProduct = Optional.empty();;
        int productId = 3;

        when(productServiceMock.getProductById(productId)).thenReturn(mockProduct);
        ResponseEntity<Product> response = productController.getProductById(productId);
        Assertions.assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    void createProductSuccessfully(){
        Product mockProduct = new Product();

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(productServiceMock.createProduct(mockProduct, 1)).thenReturn(mockProduct);
        var response = productController.createProduct(mockProduct, any());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode() );
    }
    @Test
    void createProductFailed(){
        Product mockProduct = new Product();

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(productServiceMock.createProduct(mockProduct, 1)).thenThrow(IllegalArgumentException.class);
        var response = productController.createProduct(mockProduct, any());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() );
    }
    @Test
    void updateGmfExemptSuccessfully(){
        Product mockProduct = new Product();
        mockProduct.setGmf_exempt(true);
        int productId = 4;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(productServiceMock.updateGmfExempt(productId, 1)).thenReturn(mockProduct);

        var response = productController.createProduct(mockProduct, any());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode() );
    }
    @Test
    void updateGmfExemptFailed(){
        Product mockProduct = new Product();
        mockProduct.setGmf_exempt(false);
        int productId = 4;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(productServiceMock.updateGmfExempt(productId, 1)).thenThrow(IllegalArgumentException.class);
        var response = productController.updateGmfExempt(productId, any());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() );
    }
    @Test
    void updateStatusAccountSuccessfully(){
        Product mockProduct = new Product();
        mockProduct.setStatus_account_id(1);
        int productId = 4;
        int statusAccountId = 2;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(productServiceMock.updateStatusAccount(statusAccountId, productId, 1)).thenReturn(mockProduct);

        var response = productController.updateStatusAccount(productId, statusAccountId, any());

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode() );
    }
    @Test
    void updateStatusAccountFailed(){
        Product mockProduct = new Product();
        mockProduct.setStatus_account_id(3);
        int productId = 4;
        int statusAccountId = 2;

        when(userDetailServiceImplementation.userActive(any())).thenReturn(1);
        when(productServiceMock.updateStatusAccount(statusAccountId, productId, 1)).thenThrow(IllegalArgumentException.class);
        var response = productController.updateStatusAccount(productId, statusAccountId, any());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() );
    }
}