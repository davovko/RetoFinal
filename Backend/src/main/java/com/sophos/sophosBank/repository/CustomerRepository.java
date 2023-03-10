package com.sophos.sophosBank.repository;

import com.sophos.sophosBank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT * FROM customers WHERE identification_number = :identification_number AND identification_type_id = :identification_type_id", nativeQuery = true)
    public Customer findCustomerByIdentificationNumber(@Param("identification_number") String identification_number, @Param("identification_type_id") int identification_type_id);

    @Query(value = "SELECT * FROM customers WHERE email = :email", nativeQuery = true)
    public Customer findCustomerByEmail(@Param("email") String email);


}

