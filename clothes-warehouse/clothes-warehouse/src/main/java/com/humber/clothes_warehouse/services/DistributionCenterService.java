package com.humber.clothes_warehouse.services;

import com.humber.clothes_warehouse.models.Brand;
import com.humber.clothes_warehouse.models.DistributionCenterRequest;
import com.humber.clothes_warehouse.models.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistributionCenterService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WarehouseService warehouseService;
    
    @Autowired
    private Environment environment;

    @Value("${distribution.center.api.url}")
    private String apiUrl;

    @Value("${distribution.center.api.username}")
    private String username;

    @Value("${distribution.center.api.password}")
    private String password;

    public List<Map<String, Object>> getAllDistributionCenters() {
        try {
            String url = apiUrl + "/api/distribution-centers";
            System.out.println("Making API call to: " + url);
            
            HttpHeaders headers = createHeaders();
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Map[].class
            );
            
            System.out.println("API call successful. Received " + response.getBody().length + " distribution centers.");
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            System.err.println("ERROR getting distribution centers: " + e.getMessage());
            e.printStackTrace();
            return Arrays.asList();
        }
    }

    public Map<String, Object> findItemInDistributionCenters(DistributionCenterRequest request) {
        try {
            String url = apiUrl + "/api/distribution-centers/items/search?brand=" + request.getBrand() + "&name=" + request.getName();
            System.out.println("Making API call to: " + url);
            
            HttpHeaders headers = createHeaders();
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Map[].class
            );

            List<Map<String, Object>> items = Arrays.asList(response.getBody());
            System.out.println("API call successful. Found " + items.size() + " matching items.");
            
            if (items.isEmpty()) {
                return null;
            }

            //Find the first item that has enough quantity
            for (Map<String, Object> item : items) {
                Integer quantity = (Integer) item.get("quantity");
                if (quantity >= request.getQuantity()) {
                    System.out.println("Found item with sufficient quantity: " + item);
                    return item;
                }
            }
            
            System.out.println("No items with sufficient quantity found.");
            return null;
        } catch (Exception e) {
            System.err.println("ERROR finding items: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean requestItemFromDistributionCenter(Map<String, Object> item, int quantity) {
        try {
            Long distributionCenterId = Long.valueOf(item.get("distributionCenterId").toString());
            Integer itemId = (Integer) item.get("id");

            String url = apiUrl + "/api/distribution-centers/" + distributionCenterId + "/items/" + itemId + "/request?quantity=" + quantity;
            System.out.println("Making API call to: " + url);
            
            HttpHeaders headers = createHeaders();
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Item request successful. Adding to warehouse inventory.");
                
                //Add the item to the warehouse
                Warehouse warehouse = Warehouse.builder()
                        .id(warehouseService.getNextId())
                        .name((String) item.get("name"))
                        .brand(Brand.valueOf(item.get("brand").toString()))
                        .yearOfCreation((Integer) item.get("yearOfCreation"))
                        .price((Double) item.get("price"))
                        .build();

                warehouseService.saveBrand(warehouse);
                System.out.println("Item successfully added to warehouse inventory.");
                return true;
            }
            
            System.out.println("API call returned unsuccessful status code: " + response.getStatusCode());
            return false;
        } catch (Exception e) {
            System.err.println("ERROR requesting item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean addItemToDistributionCenter(Long distributionCenterId, String name, Brand brand, 
                                              int yearOfCreation, double price, int quantity) {
        try {
            //Create the item payload
            Map<String, Object> itemData = new HashMap<>();
            //Generate a unique ID based on current timestamp
            itemData.put("id", (int)(System.currentTimeMillis() % 100000));
            itemData.put("name", name);
            itemData.put("brand", brand.toString());
            itemData.put("yearOfCreation", yearOfCreation);
            itemData.put("price", price);
            itemData.put("quantity", quantity);
            
            String url = apiUrl + "/api/distribution-centers/" + distributionCenterId + "/items";
            System.out.println("Making API call to: " + url);
            System.out.println("Item data: " + itemData);
            
            HttpHeaders headers = createHeaders();
            //Send POST request to add item
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(itemData, headers),
                    Map.class
            );
            
            boolean success = response.getStatusCode().is2xxSuccessful();
            System.out.println("API call result: " + (success ? "SUCCESS" : "FAILURE"));
            return success;
        } catch (Exception e) {
            System.err.println("ERROR adding item: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        
        //Debug the active profile and API URL
        String activeProfile = Arrays.toString(environment.getActiveProfiles());
        System.out.println("Active Profile: " + activeProfile);
        System.out.println("Using API URL: " + apiUrl);
        System.out.println("Using credentials: " + username + " / [PROTECTED]");
        
        return headers;
    }
} 