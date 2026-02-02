package com.stocktonner.backend.repositories;

import com.stocktonner.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
        //Buscando usuario por email e repassando diretamente ao "UserDetails"
        UserDetails findByEmail(String email);
}
