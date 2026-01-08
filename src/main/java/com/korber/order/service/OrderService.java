
package com.korber.order.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.korber.order.client.InventoryClient;
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

    public Order placeOrder(Long productId, int quantity) {
        inventoryClient.reserve(productId, quantity);

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        return repository.save(order);
    }
    
}
