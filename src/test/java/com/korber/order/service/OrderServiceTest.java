package com.korber.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.korber.inventory.dto.InventoryReserveResponse;
import com.korber.order.client.InventoryClient;
import com.korber.order.dto.OrderResponse;
import com.korber.order.entity.Order;
import com.korber.order.repository.OrderRepository;

public class OrderServiceTest {

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldPlaceOrderSuccessfully() {
        Long productId = 1005L;
        int quantity = 3;

        InventoryReserveResponse reserveResponse = new InventoryReserveResponse();
        reserveResponse.setProductId(productId);
        reserveResponse.setProductName("Laptop");
        reserveResponse.setReservedBatchIds(List.of(1L));

        when(inventoryClient.reserve(productId, quantity))
                .thenReturn(reserveResponse);

        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);
        savedOrder.setProductId(productId);
        savedOrder.setQuantity(quantity);
        savedOrder.setStatus("PLACED");

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponse result = orderService.placeOrder(productId, quantity);

        assertNotNull(result);
        assertEquals("PLACED", result.getStatus());
        assertEquals(productId, result.getProductId());
        assertEquals(quantity, result.getQuantity());
        assertEquals("Laptop", result.getProductName());
        assertEquals(List.of(1L), result.getReservedFromBatchIds());

        verify(inventoryClient, times(1)).reserve(productId, quantity);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldFailIfInventoryNotAvailable() {
        Long productId = 1005L;
        int quantity = 1000; // too high

        // Mock inventoryClient to throw exception
        doThrow(new RuntimeException("Insufficient stock"))
                .when(inventoryClient).reserve(productId, quantity);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                orderService.placeOrder(productId, quantity)
        );

        assertEquals("Insufficient stock", exception.getMessage());

        verify(inventoryClient, times(1)).reserve(productId, quantity);
        verify(orderRepository, never()).save(any(Order.class));
    }
}
