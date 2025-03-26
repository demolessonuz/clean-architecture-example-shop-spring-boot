package com.shop.application.service;

import com.shop.domain.model.Order;
import com.shop.domain.model.OrderItem;
import com.shop.domain.model.OrderStatus;
import com.shop.domain.model.Product;
import com.shop.domain.repository.OrderRepository;
import com.shop.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(Order order) {
        // Validate products and calculate total
        validateAndCalculateOrder(order);
        
        // Set initial status and date
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    private void validateAndCalculateOrder(Order order) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setSubtotal(product.getPrice().multiply(new BigDecimal(item.getQuantity())));
            totalAmount = totalAmount.add(item.getSubtotal());
        }
        
        order.setTotalAmount(totalAmount);
    }
} 