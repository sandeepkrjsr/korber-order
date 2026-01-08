
package com.korber.order.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate;
    
    public InventoryClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

    public void reserve(Long productId, int quantity) {
        restTemplate.postForObject(
                "http://localhost:8081/inventory/update",
                Map.of("productId", productId, "quantity", quantity),
                Void.class
        );
    }
}
