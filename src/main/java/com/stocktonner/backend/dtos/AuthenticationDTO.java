package com.stocktonner.backend.dtos;

import com.stocktonner.backend.entities.User;

public class AuthenticationDTO {
    private String email;
    private String password;

    public AuthenticationDTO(){

    }

    public AuthenticationDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationDTO(User entity) {
        email = entity.getEmail();
        password = entity.getPassword();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
