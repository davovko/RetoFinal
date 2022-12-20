package com.sophos.sophosBank.repository;

import com.sophos.sophosBank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
