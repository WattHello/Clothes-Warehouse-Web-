package com.humber.clothes_warehouse;

import com.humber.clothes_warehouse.models.Brand;
import com.humber.clothes_warehouse.models.Role;
import com.humber.clothes_warehouse.models.Warehouse;
import com.humber.clothes_warehouse.repositories.WarehouseRepository;
import com.humber.clothes_warehouse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ClothesWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClothesWarehouseApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(WarehouseRepository repo) {
		return args -> {
			repo.save(Warehouse.builder()
					.id(1)
					.name("Red T-Shirt")
					.brand(Brand.GUCCI)
					.yearOfCreation(2020)
					.price(1500.0)
					.build());

			repo.save(Warehouse.builder()
					.id(2)
					.name("Designer Jeans")
					.brand(Brand.PRADA)
					.yearOfCreation(2019)
					.price(2000.0)
					.build());

			repo.save(Warehouse.builder()
					.id(3)
					.name("Limited Edition Hoodie")
					.brand(Brand.DIOR)
					.yearOfCreation(2022)
					.price(2500.0)
					.build());

			repo.save(Warehouse.builder()
					.id(4)
					.name("Designer Sneakers")
					.brand(Brand.SUPREME)
					.yearOfCreation(2022)
					.price(3000.0)
					.build());
		};
	}
	
	@Bean
	public CommandLineRunner userDataLoader(UserService userService) {
		return args -> {
			//Create an admin user on startup
			try {
				userService.registerUser("admin", "admin", Role.ADMIN);
				System.out.println("Default admin user created");
			} catch (Exception e) {
				System.out.println("Admin user already exists");
			}
		};
	}
}
