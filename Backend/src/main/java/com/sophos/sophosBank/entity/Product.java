package com.sophos.sophosBank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private int customer_id;
    private int product_type_id;
    private String account_number;
    private int status_account_id;
    private double balance = 0;
    private double available_balance = 0;
    private boolean gmf_exempt;
    private LocalDateTime creation_date = LocalDateTime.now();
    private int creation_user_id;
    private LocalDateTime modification_date = null;
    private Integer modification_user_id = null;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_type_id() {
        return product_type_id;
    }

    public void setProduct_type_id(int product_type_id) {
        this.product_type_id = product_type_id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public int getStatus_account_id() {
        return status_account_id;
    }

    public void setStatus_account_id(int status_account_id) {
        this.status_account_id = status_account_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(double available_balance) {
        this.available_balance = available_balance;
    }

    public boolean isGmf_exempt() {
        return gmf_exempt;
    }

    public void setGmf_exempt(boolean gmf_exempt) {
        this.gmf_exempt = gmf_exempt;
    }

    public int getCreation_user_id() {
        return creation_user_id;
    }

    public void setCreation_user_id(int creation_user_id) {
        this.creation_user_id = creation_user_id;
    }

    public LocalDateTime getModification_date() {
        return modification_date;
    }

    public void setModification_date(LocalDateTime modification_date) {
        this.modification_date = modification_date;
    }

    public Integer getModification_user_id() {
        return modification_user_id;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_user_id(Integer modification_user_id) {
        this.modification_user_id = modification_user_id;
    }
}

