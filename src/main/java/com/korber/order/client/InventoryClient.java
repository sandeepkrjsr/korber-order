
package com.korber.order.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.korber.inventory.dto.InventoryReserveResponse;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate;
    
    public InventoryClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

    public InventoryReserveResponse reserve(Long productId, int quantity) {
        return  restTemplate.postForObject(
        			"http://localhost:8081/inventory/update",
        			Map.of("productId", productId, "quantity", quantity),
        			InventoryReserveResponse.class
        		);
    }
    
}
