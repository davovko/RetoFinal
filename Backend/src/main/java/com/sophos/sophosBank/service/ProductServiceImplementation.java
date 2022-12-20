package com.sophos.sophosBank.service;

import com.sophos.sophosBank.entity.Product;
import com.sophos.sophosBank.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public Product createProduct(Product product) {

        Product productGmfExempt = productRepository.checkGmfExempt(product.getCustomer_id());

        if ( productGmfExempt == null || !product.isGmf_exempt()){
            product.setAccount_number("xx");
            product.setStatus_account_id(1);
            product.setCreation_user_id(1);
            productRepository.save(product);

            product.setAccount_number( newAccountNumber(product.getProduct_id(), product.getProduct_type_id()));

            return productRepository.save(product);
        }
        String message;
        message = productGmfExempt != null ? "No se puede crear esta cuenta ya que la cuenta " + productGmfExempt.getAccount_number() + " ya se encuentra exenta GMF." : "";
        throw new IllegalArgumentException(message);

    }

    @Override
    public List<Product> getAllProductsByCustomerId(int customer_id) {
        return productRepository.findAllProductsByCustomerId(customer_id);
    }

    @Override
    public Optional<Product> getProductById(int product_id) {
        return productRepository.findById(product_id);
    }

    @Override
    public Product updateGmfExempt(int product_id){

        Optional<Product> oldProduct = productRepository.findById(product_id);
        Product productGmfExempt = productRepository.checkGmfExempt(oldProduct.get().getCustomer_id());
        if ( productGmfExempt == null || productGmfExempt.getProduct_id() == product_id){

            Product newProduct = oldProduct.get();
            boolean newState = oldProduct.get().isGmf_exempt() ? false : true;
            newProduct.setGmf_exempt(newState);
            newProduct.setAvailable_balance(oldProduct.get().getBalance() <= 0 ? 0 : newState ? oldProduct.get().getBalance() : Math.round(oldProduct.get().getBalance() - oldProduct.get().getBalance() * 0.004));
            newProduct.setModification_date(LocalDateTime.now());
            newProduct.setModification_user_id(1);

            return productRepository.save(newProduct);

        }
        String message;
        message = productGmfExempt != null ? "No se puede modificar ya que la cuenta " + productGmfExempt.getAccount_number() + " ya se encuentra exenta GMF." : "";
        throw new IllegalArgumentException(message);
    }

    @Override
    public Product updateStatusAccount(int status_account_id, int product_id){

        Optional<Product> oldProduct = productRepository.findById(product_id);
        String message;

        if(oldProduct.get().getStatus_account_id() != 3){

            Product newProduct = oldProduct.get();
            newProduct.setStatus_account_id(status_account_id);
            newProduct.setModification_date(LocalDateTime.now());
            newProduct.setModification_user_id(1);

            if (status_account_id != 3){ // activar o desactivar
                return productRepository.save(newProduct);
            } else{ // si va a cancelar
                if(oldProduct.get().getBalance() == 0){ // si no hay deuda
                    newProduct.setGmf_exempt(false);
                    return productRepository.save(newProduct);
                } else { //si hay deuda
                    message = "No puede cancelar la cuenta " + oldProduct.get().getAccount_number() + " ya que su saldo no es $0. Saldo de cuenta: $" + oldProduct.get().getAvailable_balance();
                }
            }
        }
         else {
            message = "La cuenta " + oldProduct.get().getAccount_number() + " no puede cambiar de estado ya que se encuenta cancelada ";
        }
        throw new IllegalArgumentException(message);
    }

    public String newAccountNumber(int product_id, int product_type_id){

        String accountNumber = "";

        for (int i = 0; i < (8 - Integer.toString(product_id).length()); i++){
            accountNumber = accountNumber + "0";
        }

        switch (product_type_id){
            case 1: accountNumber = "46" + accountNumber + product_id;
                break;
            case 2: accountNumber = "23" + accountNumber + product_id;
                break;
        }

        return accountNumber;
    }


    public Product checkGmfExempt(int customer_id){
        return productRepository.checkGmfExempt(customer_id);
    }
}
