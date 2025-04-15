package com.humber.distribution_center_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humber.distribution_center_manager.models.Brand;
import com.humber.distribution_center_manager.models.DistributionCenter;
import com.humber.distribution_center_manager.models.Item;
import com.humber.distribution_center_manager.services.DistributionCenterService;

@RestController
@RequestMapping("/api/distribution-centers")
public class DistributionCenterController {

    @Autowired
    private DistributionCenterService distributionCenterService;

    @GetMapping
    public List<DistributionCenter> getAllDistributionCenters() {
        return distributionCenterService.getAllDistributionCenters();
    }

    @GetMapping("/{id}")
    public DistributionCenter getDistributionCenterById(@PathVariable Long id) {
        return distributionCenterService.getDistributionCenterById(id);
    }

    @PostMapping
    public DistributionCenter createDistributionCenter(@RequestBody DistributionCenter distributionCenter) {
        return distributionCenterService.saveDistributionCenter(distributionCenter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistributionCenter(@PathVariable Long id) {
        distributionCenterService.deleteDistributionCenter(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Item> addItemToDistributionCenter(@PathVariable Long id, @RequestBody Item item) {
        Item addedItem = distributionCenterService.addItemToDistributionCenter(id, item);
        if (addedItem != null) {
            return ResponseEntity.ok(addedItem);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{distributionCenterId}/items/{itemId}")
    public ResponseEntity<Void> deleteItemFromDistributionCenter(
            @PathVariable Long distributionCenterId, @PathVariable Integer itemId) {
        distributionCenterService.deleteItemFromDistributionCenter(distributionCenterId, itemId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/items/search")
    public List<Item> findItemsByBrandAndName(
            @RequestParam Brand brand, @RequestParam String name) {
        return distributionCenterService.findItemsByBrandAndName(brand, name);
    }

    @PostMapping("/{distributionCenterId}/items/{itemId}/request")
    public ResponseEntity<Item> requestItem(
            @PathVariable Long distributionCenterId,
            @PathVariable Integer itemId,
            @RequestParam int quantity) {
        Item updatedItem = distributionCenterService.requestItem(distributionCenterId, itemId, quantity);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.badRequest().build();
    }
} 