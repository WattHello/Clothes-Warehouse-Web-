package com.humber.clothes_warehouse.controllers;

import com.humber.clothes_warehouse.models.Brand;
import com.humber.clothes_warehouse.models.DistributionCenterRequest;
import com.humber.clothes_warehouse.models.Warehouse;
import com.humber.clothes_warehouse.services.DistributionCenterService;
import com.humber.clothes_warehouse.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private DistributionCenterService distributionCenterService;

    @GetMapping("/manage")
    public String manageInventory(Model model, 
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "id") String sort,
                                @RequestParam(defaultValue = "asc") String dir) {
        Page<Warehouse> pageItems = warehouseService.getAllBrandsPaginated(page, size, sort, dir);
        
        model.addAttribute("items", pageItems.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageItems.getTotalPages());
        model.addAttribute("totalItems", pageItems.getTotalElements());
        model.addAttribute("distributionCenters", distributionCenterService.getAllDistributionCenters());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("sortField", sort);
        model.addAttribute("sortDir", dir);
        model.addAttribute("reverseSortDir", dir.equals("asc") ? "desc" : "asc");
        
        return "admin/manage";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam String query, 
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(defaultValue = "id") String sort,
                            @RequestParam(defaultValue = "asc") String dir,
                            Model model) {
        Page<Warehouse> pageItems = warehouseService.searchByNamePaginated(query, page, size, sort, dir);
        
        model.addAttribute("items", pageItems.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageItems.getTotalPages());
        model.addAttribute("totalItems", pageItems.getTotalElements());
        model.addAttribute("distributionCenters", distributionCenterService.getAllDistributionCenters());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("searchQuery", query);
        model.addAttribute("sortField", sort);
        model.addAttribute("sortDir", dir);
        model.addAttribute("reverseSortDir", dir.equals("asc") ? "desc" : "asc");
        
        return "admin/manage";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Integer id) {
        warehouseService.deleteItem(id);
        return "redirect:/admin/manage";
    }

    @PostMapping("/request-item")
    public String requestItem(@RequestParam String name,
                             @RequestParam Brand brand,
                             @RequestParam int quantity,
                             Model model) {
        DistributionCenterRequest request = DistributionCenterRequest.builder()
                .name(name)
                .brand(brand)
                .quantity(quantity)
                .build();

        Map<String, Object> item = distributionCenterService.findItemInDistributionCenters(request);
        
        if (item != null) {
            boolean success = distributionCenterService.requestItemFromDistributionCenter(item, quantity);
            if (success) {
                return "redirect:/admin/manage?success=true";
            }
        }
        
        return "redirect:/error?message=Stock cannot be replenished";
    }
    
    @PostMapping("/add-to-distribution-center")
    public String addItemToDistributionCenter(@RequestParam Long distributionCenterId,
                                            @RequestParam String name,
                                            @RequestParam Brand brand,
                                            @RequestParam int yearOfCreation,
                                            @RequestParam double price,
                                            @RequestParam int quantity,
                                            Model model) {
        
        boolean success = distributionCenterService.addItemToDistributionCenter(
            distributionCenterId, name, brand, yearOfCreation, price, quantity);
            
        if (success) {
            return "redirect:/admin/manage?addSuccess=true";
        }
        
        return "redirect:/error?message=Failed to add item to distribution center";
    }
} 