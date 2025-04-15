//WarehouseRepository.java

//This class serves as a simple in-memory repository for managing warehouse data.

package com.humber.clothes_warehouse.repositories;

import com.humber.clothes_warehouse.models.Brand;
import com.humber.clothes_warehouse.models.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    @Query("SELECT MAX(w.id) FROM Warehouse w")
    Integer findMaxId();
    
    //Custom query to find items by brand and year
    @Query("SELECT w FROM Warehouse w WHERE w.brand = ?1 AND w.yearOfCreation = ?2")
    List<Warehouse> findByBrandAndYear(Brand brand, int year);
    
    //Find all the items of a specific brand
    List<Warehouse> findByBrandOrderByNameAsc(Brand brand);
    
    //Custom query for specific brand items and year 2022
    @Query("SELECT w FROM Warehouse w WHERE w.brand = ?1 AND w.yearOfCreation = 2022")
    List<Warehouse> findByBrandAndYear2022(Brand brand);
    
    //Add pagination and sorting methods
    Page<Warehouse> findAll(Pageable pageable);
    
    //Find by brand with pagination and sorting
    Page<Warehouse> findByBrand(Brand brand, Pageable pageable);
    
    //Search inventory items by name (case insensitive)
    @Query("SELECT w FROM Warehouse w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Warehouse> findByNameContainingIgnoreCase(String searchTerm);
    
    //Search inventory items by name with pagination (case insensitive)
    @Query("SELECT w FROM Warehouse w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Warehouse> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);
}
