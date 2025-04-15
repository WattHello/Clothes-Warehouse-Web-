//WarehouseService.java

package com.humber.clothes_warehouse.services;

import com.humber.clothes_warehouse.models.Brand;
import com.humber.clothes_warehouse.models.Warehouse;
import com.humber.clothes_warehouse.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Marks this class as a Spring service component
@Service
@Transactional 
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    //Retrieves all brands from the repository
    public List<Warehouse> getAllBrands() {
        return warehouseRepository.findAll();
    }

    //New warehouse brand to the repository
    @Transactional
    public void saveBrand(Warehouse warehouse) throws RuntimeException {
        //If no ID provided, generate one
        if (warehouse.getId() == null) {
            //Find the maximum ID currently in use
            Integer maxId = warehouseRepository.findMaxId();
            //Set the new ID to max + 1 (or 1 if no existing records)
            warehouse.setId(maxId != null ? maxId + 1 : 1);
        } else {
            //Validate provided ID
            if (warehouse.getId() <= 0) {
                throw new RuntimeException("ID must be a positive number.");
            }
            if (warehouseRepository.existsById(warehouse.getId())) {
                throw new RuntimeException("An item with ID " + warehouse.getId() + " already exists");
            }
        }
        
        warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getByBrandAndYear(Brand brand, int year) {
        return warehouseRepository.findByBrandAndYear(brand, year);
    }
    
    public List<Warehouse> getByBrand(Brand brand) {
        return warehouseRepository.findByBrandOrderByNameAsc(brand);
    }

    public List<Warehouse> getByBrandAndYear2022(Brand brand) {
        return warehouseRepository.findByBrandAndYear2022(brand);
    }

    //Paginated method
    public Page<Warehouse> getAllBrandsPaginated(int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return warehouseRepository.findAll(pageable);
    }

    public void deleteItem(Integer id) {
        warehouseRepository.deleteById(id);
    }

    public Integer getNextId() {
        Integer maxId = warehouseRepository.findMaxId();
        return maxId != null ? maxId + 1 : 1;
    }

    //Search inventory items by name
    public List<Warehouse> searchByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllBrands();
        }
        return warehouseRepository.findByNameContainingIgnoreCase(searchTerm);
    }

    //Get all brands sorted by a specific field
    public List<Warehouse> getAllBrandsSorted(String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return warehouseRepository.findAll(sort);
    }

    // Search inventory items by name with pagination
    public Page<Warehouse> searchByNamePaginated(String searchTerm, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
            Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return warehouseRepository.findAll(pageable);
        }
        
        return warehouseRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
    }
}
