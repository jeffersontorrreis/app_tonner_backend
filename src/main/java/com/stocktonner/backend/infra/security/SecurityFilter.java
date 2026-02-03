package com.stocktonner.backend.infra.security;

import com.stocktonner.backend.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    //É nesse metodo que vamos recuperar os dados do token
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        //Caso exista o token
        if(token != null){
            var email = tokenService.validateToken(token); //Validando o token...
            UserDetails user = userRepository.findByEmail(email); //Quando validou o token recebeu o email que estava dentro do token..


            //Nesse momento o springSecurity ja pegou todos os dados que precisa para comparar e ver se tem ou não peril com acesso as rotas...
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //se o token for null, é como se passasse para o proximo filtro, que é "UsernamePasswordAuthenticationFilter.class"
        filterChain.doFilter(request, response);
    }

    //Metodo aqui, que é chamado la em cima e passado para a variavel "token"...
    private String recoverToken(HttpServletRequest request){

        //É no header que vamos ter a chave "authorization" que é por la que o usuario vai passar o token.
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null; //Quer dizer que não foi passado nenhum token

        /*Caso contrario se não for null, receberá o token: Imagine como se nós tivessemos
        * alterando o "auth" no postman e escolhessemos "Bear: token".*/
        return authHeader.replace("Bearer ", "");
    }
}
