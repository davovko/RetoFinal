package com.sophos.sophosBank.service;

import com.sophos.sophosBank.repository.ProductRepository;
import com.sophos.sophosBank.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplementationTest {
    @InjectMocks
    private ProductServiceImplementation productServiceImplementation;
    @Mock
    ProductRepository productRepositoryMock;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllProductsByCustomerId() {
        Product mockProduct1 = new Product();
        Product mockProduct2 = new Product();

        List<Product> mockListProducts = new ArrayList<>();

        mockListProducts.add(mockProduct1);
        mockListProducts.add(mockProduct2);

        int customerId = 24;

        when(productRepositoryMock.findAllProductsByCustomerId(customerId)).thenReturn(mockListProducts);

        List<Product> response = productServiceImplementation.getAllProductsByCustomerId(customerId);

        Assertions.assertTrue(mockListProducts.size() == response.size() );
        Assertions.assertTrue(mockListProducts.containsAll(response) && response.containsAll(mockListProducts));
    }
    @Test
    void getProductById(){
        Optional<Product> mockProduct = Optional.of(new Product());
        mockProduct.get().setProduct_id(1);

        int productId = 1;

        when(productRepositoryMock.findById(productId)).thenReturn(mockProduct);

        Optional<Product> response = productServiceImplementation.getProductById(productId);

        Assertions.assertEquals(mockProduct, response);
    }
    @Test
    void createProductGmfExeptSuccessfully(){
        Product mockProduct = new Product();
        mockProduct.setCustomer_id(1);
        mockProduct.setProduct_type_id(1);
        mockProduct.setStatus_account_id(1);
        mockProduct.setGmf_exempt(true);
        mockProduct.setCreation_user_id(1);

        int activeUserId = 1;

        when(productRepositoryMock.checkGmfExempt(mockProduct.getCustomer_id())).thenReturn(null);
        when(productRepositoryMock.save(mockProduct)).thenReturn(mockProduct);

        Product response = productServiceImplementation.createProduct(mockProduct, activeUserId);

        Assertions.assertEquals(mockProduct, response);
    }
    @Test
    void createProductGmfSuccessfully(){
        Product mockProduct = new Product();
        mockProduct.setCustomer_id(1);
        mockProduct.setProduct_type_id(1);
        mockProduct.setStatus_account_id(1);
        mockProduct.setGmf_exempt(false);
        mockProduct.setCreation_user_id(1);

        int activeUserId = 1;

        when(productRepositoryMock.checkGmfExempt(mockProduct.getCustomer_id())).thenReturn(null);
        when(productRepositoryMock.save(mockProduct)).thenReturn(mockProduct);

        Product response = productServiceImplementation.createProduct(mockProduct, activeUserId);

        Assertions.assertEquals(mockProduct, response);
    }
    @Test
    void createProductGmfExeptFailed(){
        Product mockProduct = new Product();
        mockProduct.setCustomer_id(1);
        mockProduct.setProduct_type_id(1);
        mockProduct.setStatus_account_id(1);
        mockProduct.setGmf_exempt(true);
        mockProduct.setCreation_user_id(1);

        Product mockProductGmfExempt = new Product();
        mockProductGmfExempt.setGmf_exempt(true);
        mockProductGmfExempt.setAccount_number("4600000001");

        int activeUserId = 1;

        when(productRepositoryMock.checkGmfExempt(mockProduct.getCustomer_id())).thenReturn(mockProductGmfExempt);

        try{
            when(productServiceImplementation.createProduct(mockProduct, activeUserId))
                    .thenThrow(new IllegalArgumentException("No se puede crear esta cuenta ya que la cuenta " + mockProductGmfExempt.getAccount_number() + " ya se encuentra exenta GMF."));
        } catch (Exception e){
            Assertions.assertEquals("No se puede crear esta cuenta ya que la cuenta " + mockProductGmfExempt.getAccount_number() + " ya se encuentra exenta GMF.", e.getMessage());
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    void updateGmfExemptSuccessfully(){
        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(2);
        mockOldProduct.get().setCustomer_id(1);
        Product mockNewProduct = mockOldProduct.get();
        int productId = 2;
        int activeUserId = 1;
        when(productRepositoryMock.findById(productId)).thenReturn(mockOldProduct);
        when(productRepositoryMock.checkGmfExempt(mockOldProduct.get().getCustomer_id())).thenReturn(null);
        when(productRepositoryMock.save(mockNewProduct)).thenReturn(mockNewProduct);

        Product response = productServiceImplementation.updateGmfExempt(productId, activeUserId);

        Assertions.assertEquals(mockNewProduct, response);
    }
    @Test
    void updateGmfExemptFailed(){
        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(2);
        mockOldProduct.get().setCustomer_id(1);

        Product mockProductGmfExempt = new Product();
        mockProductGmfExempt.setGmf_exempt(true);

        int productId = 2;
        int activeUserId = 1;
        when(productRepositoryMock.findById(productId)).thenReturn(mockOldProduct);
        when(productRepositoryMock.checkGmfExempt(mockOldProduct.get().getCustomer_id())).thenReturn(mockProductGmfExempt);

        try{
            when(productServiceImplementation.updateGmfExempt(productId, activeUserId))
                    .thenThrow(new IllegalArgumentException("No se puede modificar ya que la cuenta " + mockOldProduct.get().getAccount_number() + " ya se encuentra exenta GMF."));
        } catch (Exception e){
            Assertions.assertEquals("No se puede modificar ya que la cuenta " + mockOldProduct.get().getAccount_number() + " ya se encuentra exenta GMF.", e.getMessage());
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }
    }
    @Test
    void activateAccountSuccessfully(){
        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(1);
        mockOldProduct.get().setStatus_account_id(2);

        Product mockNewProduct = mockOldProduct.get();

        int status_account_id = 1;
        int product_id = 1;
        int activeUserId = 1;

        when(productRepositoryMock.findById(product_id)).thenReturn(mockOldProduct);
        when(productRepositoryMock.save(mockNewProduct)).thenReturn(mockNewProduct);

        Product response = productServiceImplementation.updateStatusAccount(status_account_id, product_id, activeUserId);

        Assertions.assertEquals(mockNewProduct, response);
    }
    @Test
    void inactivateAccountSuccessfully(){
        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(1);
        mockOldProduct.get().setStatus_account_id(1);

        Product mockNewProduct = mockOldProduct.get();

        int status_account_id = 2;
        int product_id = 1;
        int activeUserId = 1;

        when(productRepositoryMock.findById(product_id)).thenReturn(mockOldProduct);
        when(productRepositoryMock.save(mockNewProduct)).thenReturn(mockNewProduct);

        Product response = productServiceImplementation.updateStatusAccount(status_account_id, product_id, activeUserId);

        Assertions.assertEquals(mockNewProduct, response);
    }
    @Test
    void cancelAccountSuccessfully(){
        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(1);
        mockOldProduct.get().setStatus_account_id(1);
        mockOldProduct.get().setBalance(0);

        Product mockNewProduct = mockOldProduct.get();

        int status_account_id = 3;
        int product_id = 1;
        int activeUserId = 1;

        when(productRepositoryMock.findById(product_id)).thenReturn(mockOldProduct);
        when(productRepositoryMock.save(mockNewProduct)).thenReturn(mockNewProduct);

        Product response = productServiceImplementation.updateStatusAccount(status_account_id, product_id, activeUserId);

        Assertions.assertEquals(mockNewProduct, response);
    }
    @Test
    void cancelAccountFailed(){
        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(1);
        mockOldProduct.get().setStatus_account_id(1);
        mockOldProduct.get().setBalance(500);

        int status_account_id = 3;
        int product_id = 1;
        int activeUserId = 1;

        when(productRepositoryMock.findById(product_id)).thenReturn(mockOldProduct);

        try{
            when(productServiceImplementation.updateStatusAccount(status_account_id, product_id, activeUserId))
                    .thenThrow(new IllegalArgumentException("No puede cancelar la cuenta " + mockOldProduct.get().getAccount_number() + " ya que su saldo no es $0. Saldo de cuenta: $" + mockOldProduct.get().getAvailable_balance()));
        } catch (Exception e){
            Assertions.assertEquals("No puede cancelar la cuenta " + mockOldProduct.get().getAccount_number() + " ya que su saldo no es $0. Saldo de cuenta: $" + mockOldProduct.get().getAvailable_balance(), e.getMessage());
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }

    }

}