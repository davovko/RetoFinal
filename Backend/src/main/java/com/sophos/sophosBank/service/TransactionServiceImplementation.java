package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Product;
import com.sophos.sophosBank.entity.Transaction;
import com.sophos.sophosBank.repository.ProductRepository;
import com.sophos.sophosBank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImplementation implements TransactionService  {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {

        transaction.setDescription(transaction.getDescription().toUpperCase());

        if(transaction.getTransaction_type_id() != 1){
            transaction.setTransaction_value(transaction.getTransaction_value() * (-1));
        }

        Optional<Product> oldProduct = productRepository.findById(transaction.getProduct_id());
        Product newProduct = oldProduct.get();
        double availableBalance = oldProduct.get().getAvailable_balance();
        double maxValue = oldProduct.get().isGmf_exempt() ? -3000000: -3000000 + (transaction.getTransaction_value() * -0.004);
        String message = "";
        Transaction gmfTransaction = new Transaction();
        if(oldProduct.get().getStatus_account_id() == 2 && transaction.getTransaction_type_id() != 1){
            message = "No se puede realizar la transacción. La cuenta está inactiva";
        }
        else {
            switch (transaction.getTransaction_type_id()){
                case 1: // CONSIGNACION
                    newProduct.setBalance(oldProduct.get().getBalance() + transaction.getTransaction_value());
                    newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                    newProduct.setModification_date(LocalDateTime.now());
                    newProduct.setModification_user_id(1);
                    productRepository.save(newProduct);
                    return transactionRepository.save(transaction);
                case 2: // RETIRO
                    switch (oldProduct.get().getProduct_type_id()){
                        case 1: // CUENTA DE AHORROS
                            if (availableBalance < Math.abs(transaction.getTransaction_value())){
                                message = "No se puede realizar la transacción. No tiene saldo disponible";
                                break;
                            } else{
                                newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : (Math.abs(transaction.getTransaction_value() * 0.004))));
                                newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                newProduct.setModification_date(LocalDateTime.now());
                                newProduct.setModification_user_id(1);
                                productRepository.save(newProduct);

                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt()){
                                    gmfTransaction.setTransaction_type_id(2);
                                    gmfTransaction.setProduct_id(oldProduct.get().getProduct_id());
                                    gmfTransaction.setDescription("COBRO GMF / 4x1000");
                                    gmfTransaction.setTransaction_value(transaction.getTransaction_value() * 0.004);//?????
                                    transactionRepository.save(gmfTransaction);
                                }
                                return transaction;
                            }
                        case 2: //CUENTA CORRIENTE

                            if((availableBalance - Math.abs(transaction.getTransaction_value())) < maxValue){
                                message = "No se puede realizar la transacción. No tiene saldo disponible";
                                break;
                            } else {

                                newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : (Math.abs(transaction.getTransaction_value() * 0.004))));

                                if((availableBalance - Math.abs(transaction.getTransaction_value())) > 0){
                                    newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                } else {
                                    newProduct.setAvailable_balance(0);
                                }

                                newProduct.setModification_date(LocalDateTime.now());
                                newProduct.setModification_user_id(1);
                                productRepository.save(newProduct);

                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt()){
                                    gmfTransaction.setTransaction_type_id(2);
                                    gmfTransaction.setProduct_id(oldProduct.get().getProduct_id());
                                    gmfTransaction.setDescription("COBRO GMF / 4x1000");
                                    gmfTransaction.setTransaction_value(transaction.getTransaction_value() * 0.004);//?????
                                    transactionRepository.save(gmfTransaction);
                                }
                                return transaction;

                            }

                    }
                    break;
                case 3: // TRANSACCION ENTRE CUENTAS
                    Transaction destinationTransaction = new Transaction();
                    destinationTransaction.setTransaction_type_id(1);//????????????
                    destinationTransaction.setProduct_id(transaction.getDestination_product_id());
                    destinationTransaction.setDescription("TRANSFERENCIA CUENTA N° " + oldProduct.get().getAccount_number());
                    destinationTransaction.setTransaction_value(Math.abs(transaction.getTransaction_value()));
                    destinationTransaction.setOrigin_product_id(transaction.getProduct_id());

                    Optional<Product> oldDestinationProduct = productRepository.findById(transaction.getDestination_product_id());
                    Product newDestinationProduct = oldDestinationProduct.get();
                    double newBalance = oldDestinationProduct.get().getBalance() + Math.abs(transaction.getTransaction_value());
                    newDestinationProduct.setBalance(newBalance);
                    newDestinationProduct.setAvailable_balance(oldDestinationProduct.get().isGmf_exempt() ? newBalance : newBalance - (newBalance * 0.004));
                    newDestinationProduct.setModification_date(LocalDateTime.now());
                    newDestinationProduct.setModification_user_id(1);
                    switch (oldProduct.get().getProduct_type_id()){
                        case 1: // CUENTA DE AHORROS
                            if (availableBalance < Math.abs(transaction.getTransaction_value())){
                                message = "No se puede realizar la transacción. No tiene saldo disponible";
                                break;
                            } else{

                                newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : Math.abs(transaction.getTransaction_value() * 0.004)));
                                newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                newProduct.setModification_date(LocalDateTime.now());
                                newProduct.setModification_user_id(1);
                                productRepository.save(newProduct);

                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt()){
                                    gmfTransaction.setTransaction_type_id(2);
                                    gmfTransaction.setProduct_id(oldProduct.get().getProduct_id());
                                    gmfTransaction.setDescription("COBRO GMF / 4x1000");
                                    gmfTransaction.setTransaction_value(transaction.getTransaction_value() * 0.004);//?????
                                    transactionRepository.save(gmfTransaction);
                                }

                                productRepository.save(newDestinationProduct);
                                transactionRepository.save(destinationTransaction);
                                return transaction;
                            }
                        case 2: //CUENTA CORRIENTE
                            if((availableBalance - Math.abs(transaction.getTransaction_value())) < maxValue){
                                message = "No se puede realizar la transacción. No tiene saldo disponible";
                                break;
                            } else {
                                newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : (Math.abs(transaction.getTransaction_value() * 0.004))));

                                if((availableBalance -  Math.abs(transaction.getTransaction_value())) > 0){
                                    newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                } else {
                                    newProduct.setAvailable_balance(0);
                                }

                                newProduct.setModification_date(LocalDateTime.now());
                                newProduct.setModification_user_id(1);
                                productRepository.save(newProduct);

                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt()){
                                    gmfTransaction.setTransaction_type_id(2);
                                    gmfTransaction.setProduct_id(oldProduct.get().getProduct_id());
                                    gmfTransaction.setDescription("COBRO GMF / 4x1000");
                                    gmfTransaction.setTransaction_value(transaction.getTransaction_value() * 0.004);//?????
                                    transactionRepository.save(gmfTransaction);
                                }

                                productRepository.save(newDestinationProduct);
                                transactionRepository.save(destinationTransaction);
                                return transaction;

                            }
                    }break;


            }
            //return transactionRepository.save(transaction);
        }


        throw new IllegalArgumentException(message);
    }

    @Override
    public List<Transaction> getAllTransactionsByProductId(int product_id) {
        return transactionRepository.findAllTransactionsByProductId(product_id);
    }

}
