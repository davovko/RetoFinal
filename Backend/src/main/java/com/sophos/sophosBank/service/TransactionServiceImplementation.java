package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Product;
import com.sophos.sophosBank.entity.Transaction;
import com.sophos.sophosBank.repository.ProductRepository;
import com.sophos.sophosBank.repository.TransactionRepository;
import com.sophos.sophosBank.security.UserDetailServiceImplementation;
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
    @Autowired
    UserDetailServiceImplementation UserDetailServiceImplementation;
    int transactionNumber = 1;

    @Override
    public Transaction createTransaction(Transaction transaction, int activeUserId) {

        transaction.setDescription(transaction.getDescription().toUpperCase());

        if(transaction.getTransaction_type_id() != 1){
            transaction.setTransaction_value(transaction.getTransaction_value() * (-1));
        }

        Optional<Product> oldProduct = productRepository.findById(transaction.getProduct_id());
        Product newProduct = oldProduct.get();
        newProduct.setModification_date(LocalDateTime.now());
        newProduct.setModification_user_id(activeUserId);
        double availableBalance = oldProduct.get().getAvailable_balance();
        double maxValue = oldProduct.get().isGmf_exempt() ? -3000000: -3000000 + (transaction.getTransaction_value() * -0.004);
        String message = "";

        Transaction gmfTransaction = new Transaction();
        if(!oldProduct.get().isGmf_exempt() && transaction.getTransaction_type_id() != 1){
            gmfTransaction.setTransaction_type_id(2);
            gmfTransaction.setProduct_id(oldProduct.get().getProduct_id());
            gmfTransaction.setDescription("COBRO GMF / 4x1000");
            gmfTransaction.setTransaction_value(transaction.getTransaction_value() * -0.004);
        }

        if(oldProduct.get().getStatus_account_id() == 2 && transaction.getTransaction_type_id() != 1){
            message = "No se puede realizar la transacci??n. La cuenta est?? inactiva";
        }
        else {
            switch (transaction.getTransaction_type_id()){
                case 1: // CONSIGNACION
                    newProduct.setBalance(oldProduct.get().getBalance() + transaction.getTransaction_value());
                    newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                    productRepository.save(newProduct);
                    return transactionRepository.save(transaction);
                case 2: // RETIRO
                    switch (oldProduct.get().getProduct_type_id()){
                        case 1: // CUENTA DE AHORROS
                            if (availableBalance < Math.abs(transaction.getTransaction_value())){
                                message = "No tiene saldo disponible. Valor m??ximo de transacci??n : $ "+availableBalance;
                                break;
                            } else{
                                if(transaction.getDescription() != "COBRO GMF / 4x1000"){
                                    newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : (Math.abs(transaction.getTransaction_value() * 0.004))));
                                    newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                    productRepository.save(newProduct);
                                }

                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt() && transactionNumber < 2){
                                    transactionNumber++;
                                    createTransaction(gmfTransaction,activeUserId);
                                    transactionNumber = transactionNumber == 2 ? 1 : transactionNumber;
                                }
                                return transaction;
                            }
                        case 2: //CUENTA CORRIENTE

                            if((availableBalance - Math.abs(transaction.getTransaction_value())) < maxValue){
                                message = "No tiene saldo disponible. Valor m??ximo de transacci??n : $ "+ Math.abs(maxValue);
                                break;
                            } else {

                                newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : (Math.abs(transaction.getTransaction_value() * 0.004))));

                                if(transaction.getDescription() != "COBRO GMF / 4x1000"){
                                    if((availableBalance - Math.abs(transaction.getTransaction_value())) > 0){
                                        newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                    } else {
                                        newProduct.setAvailable_balance(0);
                                    }
                                    productRepository.save(newProduct);
                                }

                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt() && transactionNumber < 2){
                                    transactionNumber++;
                                    createTransaction(gmfTransaction,activeUserId);
                                    transactionNumber = transactionNumber == 2 ? 1 : transactionNumber;
                                }
                                return transaction;
                            }
                    }
                    break;
                case 3: // TRANSACCION ENTRE CUENTAS
                    Transaction destinationTransaction = new Transaction();
                    destinationTransaction.setTransaction_type_id(1);//CONSIGNACI??N
                    destinationTransaction.setProduct_id(transaction.getDestination_product_id());
                    destinationTransaction.setDescription("TRANSFERENCIA CUENTA N?? " + oldProduct.get().getAccount_number());
                    destinationTransaction.setTransaction_value(Math.abs(transaction.getTransaction_value()));
                    destinationTransaction.setOrigin_product_id(transaction.getProduct_id());

                    switch (oldProduct.get().getProduct_type_id()){
                        case 1: // CUENTA DE AHORROS
                            if (availableBalance < Math.abs(transaction.getTransaction_value())){
                                message = "No tiene saldo disponible. Valor m??ximo de transacci??n : $ "+availableBalance;
                                break;
                            } else{
                                if(transaction.getDescription() != "COBRO GMF / 4x1000"){
                                    newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : Math.abs(transaction.getTransaction_value() * 0.004)));
                                    newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                    productRepository.save(newProduct);
                                }

                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt() && transactionNumber < 2){
                                    transactionNumber++;
                                    createTransaction(gmfTransaction,activeUserId);
                                    transactionNumber = transactionNumber == 2 ? 1 : transactionNumber;
                                }
                                createTransaction(destinationTransaction,activeUserId);
                                return transaction;
                            }
                        case 2: //CUENTA CORRIENTE
                            if((availableBalance - Math.abs(transaction.getTransaction_value())) < maxValue){
                                message = "No tiene saldo disponible. Valor m??ximo de transacci??n : $ " + Math.abs(maxValue);
                                break;
                            } else {
                                newProduct.setBalance(oldProduct.get().getBalance() - Math.abs(transaction.getTransaction_value()) - (oldProduct.get().isGmf_exempt() ? 0 : (Math.abs(transaction.getTransaction_value() * 0.004))));

                                if(transaction.getDescription() != "COBRO GMF / 4x1000"){
                                    if((availableBalance -  Math.abs(transaction.getTransaction_value())) > 0){
                                        newProduct.setAvailable_balance(oldProduct.get().isGmf_exempt() ? oldProduct.get().getBalance() : oldProduct.get().getBalance() - (oldProduct.get().getBalance() * 0.004));
                                    } else {
                                        newProduct.setAvailable_balance(0);
                                    }
                                    productRepository.save(newProduct);
                                }
                                transactionRepository.save(transaction);

                                if(!oldProduct.get().isGmf_exempt() && transactionNumber < 2){
                                    transactionNumber++;
                                    createTransaction(gmfTransaction,activeUserId);
                                    transactionNumber = transactionNumber == 2 ? 1 : transactionNumber;
                                }
                                createTransaction(destinationTransaction,activeUserId);

                                return transaction;

                            }
                    }break;
            }
        }
        throw new IllegalArgumentException(message);
    }

    @Override
    public List<Transaction> getAllTransactionsByProductId(int product_id) {
        return transactionRepository.findAllTransactionsByProductId(product_id);
    }

}
