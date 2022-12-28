package com.sophos.sophosBank.components;

import com.sophos.sophosBank.entity.Customer;
import com.sophos.sophosBank.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
public class Validations {

    @Autowired
    CustomerRepository customerRepository;
    public boolean checkAge(LocalDate date_of_birth){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Period age = Period.between(LocalDate.parse(date_of_birth.format(formatter), formatter), LocalDate.now());

        return age.getYears() >= 18;
    }
    public boolean checkIdentificationNumber(String identification_number, int identification_type_Id, int customer_id){
        boolean response = true;
        Customer customer = customerRepository.findCustomerByIdentificationNumber(identification_number, identification_type_Id);

        if(customer != null){
            if(customer_id == 0){
                response = false;
            }else if(customer.getCustomer_id() != customer_id ){
                response = false;
            }
        }

        return response ;
    }
    public boolean checkEmail(String email, int customer_id){
        boolean response = true;
        Customer customer = customerRepository.findCustomerByEmail(email);

        if(customer != null){
            if(customer_id == 0){
                response = false;
            } else if(customer.getCustomer_id() != customer_id ){
                response = false;
            }
        }

        return response;
    }
    public boolean validEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        return pattern.matcher(email).find();
    }

}
