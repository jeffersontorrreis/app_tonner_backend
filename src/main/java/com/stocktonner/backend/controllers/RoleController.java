package com.stocktonner.backend.controllers;

import com.stocktonner.backend.dtos.RoleDTO;
import com.stocktonner.backend.dtos.UserDTO;
import com.stocktonner.backend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public RoleDTO insert(@RequestBody RoleDTO dto){
        return roleService.insert(dto);
    }
}
