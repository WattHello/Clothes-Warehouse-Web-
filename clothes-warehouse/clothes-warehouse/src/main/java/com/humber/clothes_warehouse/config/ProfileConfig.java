package com.humber.clothes_warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class ProfileConfig {

    @Autowired
    private Environment environment;
    
    @Value("${server.port:8080}")
    private String serverPort;
    
    @Value("${distribution.center.api.url:http://localhost:8081}")
    private String distributionCenterUrl;

    @Bean
    public CommandLineRunner profileLogger() {
        return args -> {
            String[] activeProfiles = environment.getActiveProfiles();
            
            System.out.println("\n\n============================================================");
            System.out.println("CLOTHES WAREHOUSE - PROFILE CONFIGURATION");
            System.out.println("============================================================");
            
            if (activeProfiles.length == 0) {
                String defaultProfile = environment.getProperty("spring.profiles.default");
                System.out.println("WARNING: No active profile explicitly set.");
                System.out.println("Using default profile: " + (defaultProfile != null ? defaultProfile : "default"));
            } else {
                System.out.println("ACTIVE PROFILES: " + Arrays.toString(activeProfiles));
                
                for (String profile : activeProfiles) {
                    if ("qa".equalsIgnoreCase(profile)) {
                        System.out.println("Running in QA mode (port " + serverPort + ")");
                        System.out.println("Connecting to Distribution Center at: " + distributionCenterUrl);
                    } else if ("dev".equalsIgnoreCase(profile)) {
                        System.out.println("Running in DEVELOPMENT mode (port " + serverPort + ")");
                        System.out.println("Connecting to Distribution Center at: " + distributionCenterUrl);
                    } else {
                        System.out.println("Running with profile: " + profile);
                    }
                }
            }
            
            System.out.println("User Credentials: admin / admin");
            System.out.println("============================================================\n\n");
        };
    }
} 