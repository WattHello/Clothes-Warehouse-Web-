package com.humber.clothes_warehouse.controllers;

import com.humber.clothes_warehouse.models.Brand;
import com.humber.clothes_warehouse.services.DistributionCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyRole('ADMIN', 'WAREHOUSE_EMPLOYEE')")
public class WarehouseEmployeeController {

    @Autowired
    private DistributionCenterService distributionCenterService;

    @GetMapping("/dc-management")
    public String distributionCenterManagement(Model model) {
        model.addAttribute("distributionCenters", distributionCenterService.getAllDistributionCenters());
        model.addAttribute("brands", Brand.values());
        return "employee/dc-management";
    }
    
    @PostMapping("/add-to-dc")
    public String addItemToDistributionCenter(
            @RequestParam Long distributionCenterId,
            @RequestParam String name,
            @RequestParam Brand brand,
            @RequestParam int yearOfCreation,
            @RequestParam double price,
            @RequestParam int quantity,
            Model model) {
        
        boolean success = distributionCenterService.addItemToDistributionCenter(
            distributionCenterId, name, brand, yearOfCreation, price, quantity);
            
        if (success) {
            return "redirect:/employee/dc-management?addSuccess=true";
        }
        
        return "redirect:/error?message=Failed to add item to distribution center";
    }
} 