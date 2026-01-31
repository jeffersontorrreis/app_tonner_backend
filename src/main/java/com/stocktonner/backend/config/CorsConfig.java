package com.stocktonner.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.origins}")   //Chamando aqueles endereços informados no "application.properties"
    private String corsOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        /*Estamos mapeando tudo que os endereços do "cors.origins" terão acesso. Isto é, terão acesso a todas as
        * rotas(/**), todos os vermos https, tudo que se refere a headers, credenciais(importante para sessões e token.
          Além disso veja que o "maxAge" tem 3600 segundos que equivale a 1 h. então tudo isso é mapeador e salvo em cache
          por 1 h, depois que passa 1 h apagar, afim de evitar repetições de dados.*/
    }
}
