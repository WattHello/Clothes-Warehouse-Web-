package com.humber.distribution_center_manager.services;

import com.humber.distribution_center_manager.models.Brand;
import com.humber.distribution_center_manager.models.DistributionCenter;
import com.humber.distribution_center_manager.models.Item;
import com.humber.distribution_center_manager.repositories.DistributionCenterRepository;
import com.humber.distribution_center_manager.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DistributionCenterService {

    @Autowired
    private DistributionCenterRepository distributionCenterRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<DistributionCenter> getAllDistributionCenters() {
        return distributionCenterRepository.findAll();
    }

    public DistributionCenter getDistributionCenterById(Long id) {
        return distributionCenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Distribution center not found with id: " + id));
    }

    public DistributionCenter saveDistributionCenter(DistributionCenter distributionCenter) {
        return distributionCenterRepository.save(distributionCenter);
    }

    public void deleteDistributionCenter(Long id) {
        distributionCenterRepository.deleteById(id);
    }

    public Item addItemToDistributionCenter(Long distributionCenterId, Item item) {
        DistributionCenter distributionCenter = getDistributionCenterById(distributionCenterId);
        item.setDistributionCenterId(distributionCenterId);
        distributionCenter.getItems().add(item);
        distributionCenterRepository.save(distributionCenter);
        return item;
    }

    public void deleteItemFromDistributionCenter(Long distributionCenterId, Integer itemId) {
        DistributionCenter distributionCenter = getDistributionCenterById(distributionCenterId);
        Optional<Item> itemOpt = itemRepository.findByIdAndDistributionCenterId(itemId, distributionCenterId);
        
        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();
            distributionCenter.getItems().remove(item);
            distributionCenterRepository.save(distributionCenter);
            //Also delete from item repository to ensure complete removal
            itemRepository.delete(item);
            System.out.println("Item " + itemId + " successfully deleted from distribution center " + distributionCenterId);
        } else {
            System.out.println("Item " + itemId + " not found in distribution center " + distributionCenterId);
        }
    }

    public List<Item> findItemsByBrandAndName(Brand brand, String name) {
        return itemRepository.findByBrandAndNameContainingIgnoreCase(brand, name);
    }

    public Item requestItem(Long distributionCenterId, Integer itemId, int quantity) {
        DistributionCenter distributionCenter = getDistributionCenterById(distributionCenterId);
        Optional<Item> itemOpt = itemRepository.findByIdAndDistributionCenterId(itemId, distributionCenterId);
        
        if (itemOpt.isEmpty()) {
            return null;
        }
        
        Item item = itemOpt.get();
        
        if (item.getQuantity() < quantity) {
            return null;
        }
        
        //Update the quantity
        item.setQuantity(item.getQuantity() - quantity);
        
        //Save both the item and distribution center
        item = itemRepository.save(item);
        distributionCenterRepository.save(distributionCenter);
        
        return item;
    }
} 