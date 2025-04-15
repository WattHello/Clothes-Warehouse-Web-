//WarehouseController.java

package com.humber.clothes_warehouse.controllers;

import com.humber.clothes_warehouse.models.Brand;
import com.humber.clothes_warehouse.models.Warehouse;
import com.humber.clothes_warehouse.services.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //Marks this class as a Spring MVC controller
@RequestMapping("/") //Base mapping for all endpoints in this controller
public class WarehouseController {

    @Autowired //Injects WarehouseService dependency
    private WarehouseService warehouseService;

    @Value("Java Eats") //Hardcoded warehouse name value
    private String warehouseName;
    
    //Redirect root URL to the register page
    @GetMapping("/")
    public String index() {
        return "redirect:/register";
    }

    //Handles GET request for the home page
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("wName", warehouseName); //Adds warehouse name to the model
        return "home"; //Returns the home view
    }

    //Handles GET request for the inventory page
    @GetMapping("/inventory")
    public String inventory(Model model,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "5") int size,
                          @RequestParam(defaultValue = "id") String sort,
                          @RequestParam(defaultValue = "asc") String dir) {
        
        Page<Warehouse> pageItems = warehouseService.getAllBrandsPaginated(page, size, sort, dir);
        
        model.addAttribute("brands", pageItems.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageItems.getTotalPages());
        model.addAttribute("totalItems", pageItems.getTotalElements());
        model.addAttribute("sortField", sort);
        model.addAttribute("sortDir", dir);
        model.addAttribute("reverseSortDir", dir.equals("asc") ? "desc" : "asc");
        
        return "inventory";
    }

    //Handles GET request for adding a new brand
    @GetMapping("/add")
    public String addBrand(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        model.addAttribute("brandOptions", Brand.values());
        return "add-to-cart";
    }

    //Handles POST request for saving a new brand
    @PostMapping("/save")
    public String saveBrand(@ModelAttribute Warehouse warehouse, Model model) {
        try {
            //Validate price (must be at least 1000)
            if (warehouse.getPrice() < 1000) {
                model.addAttribute("message", "Price must be at least 1000");
                model.addAttribute("brandOptions", Brand.values());
                return "add-to-cart";
            }

            //Validate year
            if (warehouse.getYearOfCreation() >= 2023) {
                model.addAttribute("message", "Year of creation must be before 2023");
                model.addAttribute("brandOptions", Brand.values());
                return "add-to-cart";
            }

            warehouseService.saveBrand(warehouse);
            return "redirect:/inventory";
            
        } catch (RuntimeException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("brandOptions", Brand.values());
            return "add-to-cart";
        }
    }
    //Handles GET request for inventory fliter
    @GetMapping("/inventory/filter")
    public String filterByBrand(@RequestParam(required = false) Brand brand, Model model) {
        if (brand != null) {
            model.addAttribute("brands", warehouseService.getByBrand(brand));
        } else {
            model.addAttribute("brands", warehouseService.getAllBrands());
        }
        return "inventory";
    }
    
    //Handles GET request for inventory fliter for 2022
    @GetMapping("/inventory/filter2022")
    public String filterByBrand2022(@RequestParam Brand brand, Model model) {
        model.addAttribute("brands", warehouseService.getByBrandAndYear2022(brand));
        model.addAttribute("selectedBrand", brand);
        return "inventory";
    }
    
    //Handles GET request for inventory search
    @GetMapping("/inventory/search")
    public String searchInventory(@RequestParam String query, Model model) {
        model.addAttribute("brands", warehouseService.searchByName(query));
        model.addAttribute("searchQuery", query);
        return "inventory";
    }
}
