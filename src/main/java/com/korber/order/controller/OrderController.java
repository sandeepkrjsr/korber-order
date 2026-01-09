package com.korber.order.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korber.order.dto.OrderRequest;
import com.korber.order.dto.OrderResponse;
import com.korber.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;
    
    public OrderController(OrderService service) {
		this.service = service;
	}

    @PostMapping
    public OrderResponse placeOrder(@RequestBody OrderRequest request) {
        return service.placeOrder(request.getProductId(), request.getQuantity());
    }

}
