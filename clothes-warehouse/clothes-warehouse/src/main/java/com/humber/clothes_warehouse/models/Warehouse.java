//Warehouse.java

package com.humber.clothes_warehouse.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Generate getters for all fields and setters for all no-final fields.
@Data

//Generates a no-arguments constructor.
@NoArgsConstructor

//Generate a constructor with one argument for each field in the class.
@AllArgsConstructor

//Generates a builder pattern for the class, allowing for a fluent interface to construct objects with named parameters.
@Builder

//JPA Implemntation
@Entity
@Table(name = "warehouse_items")
public class Warehouse {
    @Id
    private Integer id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private Brand brand;
    
    private int yearOfCreation;
    private Double price;
}
