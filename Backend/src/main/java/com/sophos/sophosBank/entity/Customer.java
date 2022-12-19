package com.sophos.sophosBank.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
    private int identification_type_Id;
    private String identification_number;
    private String first_name;
    private String middle_name = null;
    private String last_name;
    private String second_last_name = null;
    private String email;
    private LocalDate date_of_birth;
    private LocalDateTime creation_date = LocalDateTime.now();
    private int creation_user_id;
    private LocalDateTime modification_date = null;
    private Integer modification_user_id = null;
    public Integer getModification_user_id() {
        return modification_user_id;
    }

    public LocalDateTime getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }

    public LocalDateTime getModification_date() {
        return modification_date;
    }

    public void setModification_date(LocalDateTime modification_date) {
        this.modification_date = modification_date;
    }

    public void setModification_user_id(Integer modification_user_id) {
        this.modification_user_id = modification_user_id;
    }

    public Customer() {
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getIdentification_type_Id() {
        return identification_type_Id;
    }

    public void setIdentification_type_Id(int identification_type_Id) {
        this.identification_type_Id = identification_type_Id;
    }

    public String getIdentification_number() {
        return identification_number;
    }

    public void setIdentification_number(String identification_number) {
        this.identification_number = identification_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSecond_last_name() {
        return second_last_name;
    }

    public void setSecond_last_name(String second_last_name) {
        this.second_last_name = second_last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getCreation_user_id() {
        return creation_user_id;
    }

    public void setCreation_user_id(int creation_user_id) {
        this.creation_user_id = creation_user_id;
    }
}
