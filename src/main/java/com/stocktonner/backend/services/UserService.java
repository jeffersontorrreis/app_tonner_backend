package com.stocktonner.backend.services;


import com.stocktonner.backend.dtos.RoleDTO;
import com.stocktonner.backend.dtos.UserDTO;
import com.stocktonner.backend.entities.Role;
import com.stocktonner.backend.entities.User;
import com.stocktonner.backend.repositories.RoleRepository;
import com.stocktonner.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO insert (UserDTO dto){
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.getRoles().clear();

        for (RoleDTO rolDto : dto.getRoles()) {
            // ← BUSCA a Role existente do banco ao invés de criar uma nova
            Role rol = roleRepository.findById(rolDto.getId())
                    .orElseThrow(() -> new RuntimeException("Role não encontrada: " + rolDto.getId()));
            entity.getRoles().add(rol);
        }
        entity = repository.save(entity);
        return  new UserDTO(entity);
    }

}
