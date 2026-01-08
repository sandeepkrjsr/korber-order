package com.korber.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.korber.order.client.InventoryClient;
import com.korber.order.entity.Order;
import com.korber.order.repository.OrderRepository;

public class OrderServiceTest {

    @Mock
    private InventoryClient inventoryClient; // ✅ correct mock

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

        // Mock inventoryClient behavior
        doNothing().when(inventoryClient).reserve(productId, quantity);

        // Mock repository save
        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);
        savedOrder.setProductId(productId);
        savedOrder.setQuantity(quantity);
        savedOrder.setStatus("PLACED");

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Call service
        Order result = orderService.placeOrder(productId, quantity);

        // Verify
        assertNotNull(result);
        assertEquals("PLACED", result.getStatus());
        assertEquals(productId, result.getProductId());
        assertEquals(quantity, result.getQuantity());

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
