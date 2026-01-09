
package com.korber.order.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.korber.inventory.dto.InventoryReserveResponse;
import com.korber.order.client.InventoryClient;
import com.korber.order.dto.OrderResponse;
import com.korber.order.entity.Order;
import com.korber.order.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final InventoryClient inventoryClient;
    
    public OrderService(OrderRepository repository, InventoryClient inventoryClient) {
		this.repository = repository;
		this.inventoryClient = inventoryClient;
	}

    public OrderResponse placeOrder(Long productId, int quantity) {
    	
    	InventoryReserveResponse reserveResponse = inventoryClient.reserve(productId, quantity);

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());
        repository.save(order);
        
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setProductId(order.getProductId());
        response.setProductName(reserveResponse.getProductName());
        response.setQuantity(order.getQuantity());
        response.setStatus(order.getStatus());
        response.setReservedFromBatchIds(reserveResponse.getReservedBatchIds());
        response.setMessage("Order placed. Inventory reserved.");

        return response;
    }
    
}
