package com.shop.domain.port;

import com.shop.domain.model.Order;
import com.shop.domain.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderUseCase {
    Order createOrder(Order order);
    Optional<Order> getOrder(Long id);
    List<Order> getAllOrders();
    void deleteOrder(Long id);
    Order updateOrderStatus(Long id, OrderStatus status);
} 