package com.humber.distribution_center_manager.config;

import com.humber.distribution_center_manager.models.Brand;
import com.humber.distribution_center_manager.models.DistributionCenter;
import com.humber.distribution_center_manager.models.Item;
import com.humber.distribution_center_manager.repositories.DistributionCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader {

    @Autowired
    private DistributionCenterRepository distributionCenterRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            System.out.println("DataLoader is running and initializing data");
            
            //Create distribution centers
            DistributionCenter dc1 = DistributionCenter.builder()
                    .name("Toronto Distribution Center")
                    .latitude(43.6532)
                    .longitude(-79.3832)
                    .items(Arrays.asList(
                            Item.builder()
                                    .id(1)
                                    .name("Red T-Shirt")
                                    .brand(Brand.GUCCI)
                                    .yearOfCreation(2020)
                                    .price(1500.0)
                                    .quantity(10)
                                    .build(),
                            Item.builder()
                                    .id(2)
                                    .name("Designer Jeans")
                                    .brand(Brand.PRADA)
                                    .yearOfCreation(2019)
                                    .price(2000.0)
                                    .quantity(5)
                                    .build()
                    ))
                    .build();

            DistributionCenter dc2 = DistributionCenter.builder()
                    .name("Vancouver Distribution Center")
                    .latitude(49.2827)
                    .longitude(-123.1207)
                    .items(Arrays.asList(
                            Item.builder()
                                    .id(3)
                                    .name("Limited Edition Hoodie")
                                    .brand(Brand.DIOR)
                                    .yearOfCreation(2022)
                                    .price(2500.0)
                                    .quantity(8)
                                    .build(),
                            Item.builder()
                                    .id(4)
                                    .name("Designer Sneakers")
                                    .brand(Brand.SUPREME)
                                    .yearOfCreation(2022)
                                    .price(3000.0)
                                    .quantity(3)
                                    .build()
                    ))
                    .build();

            DistributionCenter dc3 = DistributionCenter.builder()
                    .name("Montreal Distribution Center")
                    .latitude(45.5017)
                    .longitude(-73.5673)
                    .items(Arrays.asList(
                            Item.builder()
                                    .id(5)
                                    .name("Classic Jacket")
                                    .brand(Brand.BALENCIAGA)
                                    .yearOfCreation(2021)
                                    .price(3500.0)
                                    .quantity(7)
                                    .build(),
                            Item.builder()
                                    .id(6)
                                    .name("Summer Dress")
                                    .brand(Brand.VERSACE)
                                    .yearOfCreation(2021)
                                    .price(2800.0)
                                    .quantity(4)
                                    .build()
                    ))
                    .build();

            DistributionCenter dc4 = DistributionCenter.builder()
                    .name("Calgary Distribution Center")
                    .latitude(51.0447)
                    .longitude(-114.0719)
                    .items(Arrays.asList(
                            Item.builder()
                                    .id(7)
                                    .name("Winter Coat")
                                    .brand(Brand.STONE_ISLAND)
                                    .yearOfCreation(2021)
                                    .price(2200.0)
                                    .quantity(6)
                                    .build(),
                            Item.builder()
                                    .id(8)
                                    .name("Casual Shirt")
                                    .brand(Brand.OFF_WHITE)
                                    .yearOfCreation(2022)
                                    .price(1800.0)
                                    .quantity(9)
                                    .build()
                    ))
                    .build();

            //Save distribution centers
            distributionCenterRepository.saveAll(Arrays.asList(dc1, dc2, dc3, dc4));
            
            System.out.println("DataLoader has successfully initialized data");
        };
    }
} 