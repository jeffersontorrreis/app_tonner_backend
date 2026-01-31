package com.stocktonner.backend.services;


import com.stocktonner.backend.dtos.RoleDTO;
import com.stocktonner.backend.dtos.UserDTO;
import com.stocktonner.backend.entities.Role;
import com.stocktonner.backend.entities.User;
import com.stocktonner.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO insert (UserDTO dto){
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.getRoles().clear();

        for(/*Alvo*/RoleDTO rolDto/*Iterador*/ : dto.getRoles()/*Acessando as roles pelo "UserDTO"*/){
            Role rol = new Role();
            rol.setId(rolDto.getId());
            entity.getRoles().add(rol);

            /*Lembre-se que DTO são apenas objetos de transferencia de dados entre camadas, logo
            * não é uma entidade que de fato representa uma tabela com os dados de fato. Por isso
            * ainda sim precisamos instanciar a entidade "Role" . */
        }
        entity = repository.save(entity);
        return  new UserDTO(entity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        return user;
    }
}
