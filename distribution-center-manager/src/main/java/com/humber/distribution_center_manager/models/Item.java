package com.humber.distribution_center_manager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    private Integer id;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private Brand brand;
    
    private int yearOfCreation;
    private Double price;
    
    //New field for quantity
    private Integer quantity;

    @Column(name = "distribution_center_id")
    private Long distributionCenterId;
} 