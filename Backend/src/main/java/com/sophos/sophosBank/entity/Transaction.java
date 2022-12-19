package com.sophos.sophosBank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transaction_id;
    private int transaction_type_id;
    private int product_id;
    private LocalDateTime transaction_date = LocalDateTime.now();
    private String description;
    private double transaction_value;
    private Integer destination_product_id = null;
    private Integer origin_product_id = null;

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public int getTransaction_type_id() {
        return transaction_type_id;
    }

    public void setTransaction_type_id(int transaction_type_id) {
        this.transaction_type_id = transaction_type_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public LocalDateTime getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(LocalDateTime transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTransaction_value() {
        return transaction_value;
    }

    public void setTransaction_value(double transaction_value) {
        this.transaction_value = transaction_value;
    }

    public Integer getDestination_product_id() {
        return destination_product_id;
    }

    public void setDestination_product_id(Integer destination_product_id) {
        this.destination_product_id = destination_product_id;
    }

    public Integer getOrigin_product_id() {
        return origin_product_id;
    }

    public void setOrigin_product_id(Integer origin_product_id) {
        this.origin_product_id = origin_product_id;
    }
}
