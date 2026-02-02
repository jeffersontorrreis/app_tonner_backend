package com.stocktonner.backend.services;

import com.stocktonner.backend.dtos.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public ResponseEntity login(AuthenticationDTO dto){
        /* Conseguimos trazer o email e password graças ao metodo "loadUserByUsername" que acessa o banco.
        *  Aqui acontece também uma comparação do password que o usuario informa, e que vira um hash, e é
        *  comparado com o hash de senha do usuario que esta salvo no banco. Se esses dois hash's forem iguais, significa
        *  que a senha é valida. Com as credenciais correta, passa para a linha de baixo que ja é o "authenticationManager.*/
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        //Caso os dados do usuario exista o mesmo é autenticado
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }
}
