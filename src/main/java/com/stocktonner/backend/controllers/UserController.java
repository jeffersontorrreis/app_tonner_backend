package com.stocktonner.backend.controllers;

import com.stocktonner.backend.dtos.UserDTO;
import com.stocktonner.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/sign-up")
public class UserController {

    @Autowired
    private UserService userServices;

    @PostMapping
    public UserDTO insert(@Valid @RequestBody UserDTO dto){
        return userServices.insert(dto);
    }
}
