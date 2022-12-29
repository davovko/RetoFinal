package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Product;
import com.sophos.sophosBank.entity.Transaction;
import com.sophos.sophosBank.repository.ProductRepository;
import com.sophos.sophosBank.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TransactionServiceImplementationTest {
    @InjectMocks
    private TransactionServiceImplementation transactionServiceImplementation;
    @Mock
    ProductRepository productRepositoryMock;
    @Mock
    TransactionRepository transactionRepositoryMock;
    @Mock
    com.sophos.sophosBank.security.UserDetailServiceImplementation UserDetailServiceImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllTransactionsByProductId(){
        int product_id = 1;

        Transaction mockTransaction1 = new Transaction();
        mockTransaction1.setTransaction_id(1);
        mockTransaction1.setTransaction_type_id(1);
        mockTransaction1.setProduct_id(1);
        mockTransaction1.setTransaction_date(LocalDateTime.parse("2022-12-27T15:37:27.252280400"));
        mockTransaction1.setDescription("CONSIGNACIÓN 1");
        mockTransaction1.setTransaction_value(1000);

        Transaction mockTransaction2 = new Transaction();
        mockTransaction2.setTransaction_id(1);
        mockTransaction2.setTransaction_type_id(2);
        mockTransaction2.setProduct_id(1);
        mockTransaction2.setTransaction_date(LocalDateTime.parse("2022-11-21T14:37:27.252280400"));
        mockTransaction2.setDescription("RETIRO 1");
        mockTransaction2.setTransaction_value(500);

        List<Transaction> mockListTransactions = new ArrayList<>();
        mockListTransactions.add(mockTransaction1);
        mockListTransactions.add(mockTransaction2);

        when(transactionRepositoryMock.findAllTransactionsByProductId(product_id)).thenReturn(mockListTransactions);

        List<Transaction> response = transactionServiceImplementation.getAllTransactionsByProductId(product_id);

        Assertions.assertTrue(mockListTransactions.size() == response.size() );
        Assertions.assertTrue(mockListTransactions.containsAll(response) && response.containsAll(mockListTransactions));
    }
    @Test
    void createTransactionConsignment(){
        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransaction_type_id(1);//CONSIGNACIÓN
        mockTransaction.setProduct_id(1);
        mockTransaction.setDescription("CONSIGNACIÓN 1");
        mockTransaction.setTransaction_value(1000);

        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setBalance(1000);
        mockOldProduct.get().setAvailable_balance(1000);
        mockOldProduct.get().setGmf_exempt(true);
        Product mockNewProduct = mockOldProduct.get();

        int activeUserId = 1;

        when(productRepositoryMock.findById(mockTransaction.getProduct_id())).thenReturn(mockOldProduct);
        when(productRepositoryMock.save(mockNewProduct)).thenReturn(mockNewProduct);
        when(transactionRepositoryMock.save(mockTransaction)).thenReturn(mockTransaction);

        Transaction response = transactionServiceImplementation.createTransaction(mockTransaction, activeUserId);

        Assertions.assertEquals(2000, mockNewProduct.getAvailable_balance());
        Assertions.assertEquals(2000, mockNewProduct.getBalance());
        Assertions.assertEquals(mockTransaction, response);
    }
    @Test
    void createTransactionWithdrawalGmfExempt(){
        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransaction_type_id(2);//RETIRO
        mockTransaction.setProduct_id(1);
        mockTransaction.setDescription("RETIRO 1");
        mockTransaction.setTransaction_value(1000);

        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_type_id(1);
        mockOldProduct.get().setBalance(1000);
        mockOldProduct.get().setAvailable_balance(1000);
        mockOldProduct.get().setGmf_exempt(true);
        Product mockNewProduct = mockOldProduct.get();

        int activeUserId = 1;

        when(productRepositoryMock.findById(mockTransaction.getProduct_id())).thenReturn(mockOldProduct);

        Transaction response = transactionServiceImplementation.createTransaction(mockTransaction, activeUserId);

        Assertions.assertEquals(0, mockNewProduct.getAvailable_balance());
        Assertions.assertEquals(0, mockNewProduct.getBalance());
        Assertions.assertEquals(mockTransaction, response);
    }
    @Test
    void createTransactionTransferGMFExempt(){
        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransaction_type_id(3);//TRANSACCION ENTRE CUENTAS
        mockTransaction.setProduct_id(1);
        mockTransaction.setDescription("TRANSACCIÓN 1");
        mockTransaction.setTransaction_value(500);
        mockTransaction.setDestination_product_id(2);

        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(1);
        mockOldProduct.get().setProduct_type_id(1);//CUENTA DE AHORROS
        mockOldProduct.get().setBalance(1000);
        mockOldProduct.get().setAvailable_balance(1000);
        mockOldProduct.get().setAccount_number("4600000002");
        mockOldProduct.get().setGmf_exempt(true);
        Product mockNewProduct = mockOldProduct.get();

        Transaction mockDestinationTransaction = new Transaction();
        mockDestinationTransaction.setTransaction_type_id(1);
        mockDestinationTransaction.setProduct_id(mockTransaction.getDestination_product_id());
        mockDestinationTransaction.setDescription("TRANSFERENCIA CUENTA N° " + mockOldProduct.get().getAccount_number());
        mockDestinationTransaction.setTransaction_value(Math.abs(mockTransaction.getTransaction_value()));
        mockDestinationTransaction.setOrigin_product_id(mockTransaction.getProduct_id());

        int activeUserId = 1;

        when(productRepositoryMock.findById(mockTransaction.getProduct_id())).thenReturn(mockOldProduct);
        //when(transactionServiceImplementation.createTransaction(mockDestinationTransaction, activeUserId)).thenReturn(mockDestinationTransaction);
        when(productRepositoryMock.save(mockNewProduct)).thenReturn(mockNewProduct);
        when(transactionRepositoryMock.save(mockTransaction)).thenReturn(mockTransaction);

        Transaction response = transactionServiceImplementation.createTransaction(mockTransaction, activeUserId);

        Assertions.assertEquals(0, mockNewProduct.getAvailable_balance());
        Assertions.assertEquals(0, mockNewProduct.getBalance());
        Assertions.assertEquals(mockTransaction, response);
    }
    @Test
    void createTransactionErrorBalanceNotAvailable(){
        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransaction_type_id(2);//RETIRO
        mockTransaction.setProduct_id(1);
        mockTransaction.setDescription("TRANSACCIÓN 1");
        mockTransaction.setTransaction_value(1000);
        mockTransaction.setDestination_product_id(2);

        Optional<Product> mockOldProduct = Optional.of(new Product());
        mockOldProduct.get().setProduct_id(1);
        mockOldProduct.get().setProduct_type_id(1);//CUENTA DE AHORROS
        mockOldProduct.get().setBalance(500);
        mockOldProduct.get().setAvailable_balance(500);
        mockOldProduct.get().setAccount_number("4600000002");
        mockOldProduct.get().setGmf_exempt(true);

        int activeUserId = 1;

        when(productRepositoryMock.findById(mockTransaction.getProduct_id())).thenReturn(mockOldProduct);

        try{

            when(transactionServiceImplementation.createTransaction(mockTransaction, activeUserId))
                    .thenThrow(new IllegalArgumentException("No se puede realizar la transacción. No tiene saldo disponible"));
        } catch (Exception e){
            Assertions.assertEquals("No se puede realizar la transacción. No tiene saldo disponible", e.getMessage());
            Assertions.assertTrue(e instanceof IllegalArgumentException);
        }
    }



}