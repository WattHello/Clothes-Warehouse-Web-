package com.humber.distribution_center_manager.repositories;

import com.humber.distribution_center_manager.models.Brand;
import com.humber.distribution_center_manager.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByBrandAndName(Brand brand, String name);
    
    @Query("SELECT i FROM Item i WHERE i.brand = ?1 AND LOWER(i.name) LIKE LOWER(CONCAT('%', ?2, '%'))")
    List<Item> findByBrandAndNameContainingIgnoreCase(Brand brand, String nameFragment);
    
    Optional<Item> findByIdAndDistributionCenterId(Integer id, Long distributionCenterId);
} 