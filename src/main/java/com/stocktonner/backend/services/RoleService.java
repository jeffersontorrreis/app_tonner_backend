package com.stocktonner.backend.services;

import com.stocktonner.backend.dtos.RoleDTO;
import com.stocktonner.backend.dtos.UserDTO;
import com.stocktonner.backend.entities.Role;
import com.stocktonner.backend.entities.User;
import com.stocktonner.backend.repositories.RoleRepository;
import com.stocktonner.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository rolerepository;


    @Transactional
    public RoleDTO insert (RoleDTO dto){
        Role entity = new Role();
        entity.setAuthority(dto.getAuthority());

        entity = rolerepository.save(entity);
        return  new RoleDTO(entity);
    }
}
