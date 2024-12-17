package fr.etrangecantina.services.msgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200"); // Origine autorisée (votre app Angular)
        config.addAllowedMethod("*"); // Méthodes autorisées (GET, POST, etc.)
        config.addAllowedHeader("*"); // En-têtes autorisés
        config.setAllowCredentials(true); // Si nécessaire pour cookies ou autorisation

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Appliquer à toutes les routes

        return new CorsWebFilter(source);
    }
}
