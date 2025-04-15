package com.humber.distribution_center_manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
public class ProfileConfig {

    @Autowired
    private Environment environment;

    @Bean
    public CommandLineRunner profileLogger() {
        return args -> {
            String[] activeProfiles = environment.getActiveProfiles();
            
            System.out.println("\n\n============================================================");
            System.out.println("DISTRIBUTION CENTER MANAGER - PROFILE CONFIGURATION");
            System.out.println("============================================================");
            
            if (activeProfiles.length == 0) {
                String defaultProfile = environment.getProperty("spring.profiles.default");
                System.out.println("WARNING: No active profile explicitly set.");
                System.out.println("Using default profile: " + (defaultProfile != null ? defaultProfile : "default"));
            } else {
                System.out.println("ACTIVE PROFILES: " + Arrays.toString(activeProfiles));
                
                for (String profile : activeProfiles) {
                    if ("qa".equalsIgnoreCase(profile)) {
                        System.out.println("Running in QA mode (port 8082)");
                        System.out.println("Clothes Warehouse should connect from port 8083");
                    } else if ("dev".equalsIgnoreCase(profile)) {
                        System.out.println("Running in DEVELOPMENT mode (port 8081)");
                        System.out.println("Clothes Warehouse should connect from port 8080");
                    } else {
                        System.out.println("Running with profile: " + profile);
                    }
                }
            }
            
            System.out.println("============================================================\n\n");
        };
    }
} 