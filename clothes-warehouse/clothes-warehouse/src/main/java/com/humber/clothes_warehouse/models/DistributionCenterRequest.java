package com.humber.clothes_warehouse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionCenterRequest {
    private String name;
    private Brand brand;
    private int quantity;
} 