package com.humber.distribution_center_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private Environment environment;
    
    @Value("${server.port}")
    private String serverPort;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api-docs", "/api-docs.html", "/api-docs-qa.html", 
                                 "/api-manager", "/api-manager.html", "/api-manager-qa.html", 
                                 "/swagger-ui/**", "/static/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic();
        
        http.headers(headers -> headers.frameOptions().disable());
        
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:8080",  //Dev Clothes Warehouse
            "http://localhost:8083",  //QA Clothes Warehouse
            "http://127.0.0.1:8080",  //Alternative Dev access 
            "http://127.0.0.1:8083"   //Alternative QA access
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner securityInitData() {
        return args -> {
            String[] activeProfiles = environment.getActiveProfiles();
            if (activeProfiles.length > 0) {
                String activeProfile = activeProfiles[0];
                
                System.out.println("===============================================");
                System.out.println("DISTRIBUTION CENTER MANAGER");
                System.out.println("-----------------------------------------------");
                System.out.println("ACTIVE PROFILE: " + activeProfile.toUpperCase());
                System.out.println("SERVER PORT: " + serverPort);
                
                if (activeProfile.equals("qa")) {
                    System.out.println("API DOCS: http://localhost:" + serverPort + "/api-docs-qa.html");
                    System.out.println("API MANAGER: http://localhost:" + serverPort + "/api-manager-qa.html");
                    System.out.println("QA PROFILE: Connect Clothes Warehouse from port 8083");
                } else {
                    System.out.println("API DOCS: http://localhost:" + serverPort + "/api-docs.html");
                    System.out.println("API MANAGER: http://localhost:" + serverPort + "/api-manager.html");
                    System.out.println("DEV PROFILE: Connect Clothes Warehouse from port 8080");
                }
                
                System.out.println("H2 CONSOLE: http://localhost:" + serverPort + "/h2-console");
                System.out.println("===============================================");
            } else {
                System.out.println("WARNING: Running with default profile (none specified)");
            }
        };
    }
} 