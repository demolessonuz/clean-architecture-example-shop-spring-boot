package com.shop.infrastructure.persistence.repository.impl;

import com.shop.domain.model.Order;
import com.shop.domain.model.OrderItem;
import com.shop.domain.model.OrderStatus;
import com.shop.domain.model.Product;
import com.shop.domain.repository.OrderRepository;
import com.shop.infrastructure.persistence.entity.OrderJpaEntity;
import com.shop.infrastructure.persistence.entity.OrderItemJpaEntity;
import com.shop.infrastructure.persistence.entity.ProductJpaEntity;
import com.shop.infrastructure.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = toEntity(order);
        OrderJpaEntity savedEntity = orderJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        orderJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return orderJpaRepository.existsById(id);
    }

    private OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity();
        entity.setId(order.getId());
        entity.setOrderDate(order.getOrderDate());
        entity.setStatus(order.getStatus().name());
        
        List<OrderItemJpaEntity> items = order.getItems().stream()
                .map(this::toOrderItemEntity)
                .collect(Collectors.toList());
        entity.setItems(items);
        
        return entity;
    }

    private OrderItemJpaEntity toOrderItemEntity(OrderItem orderItem) {
        OrderItemJpaEntity entity = new OrderItemJpaEntity();
        entity.setId(orderItem.getId());
        entity.setQuantity(orderItem.getQuantity());
        entity.setPrice(orderItem.getPrice().doubleValue());
        
        ProductJpaEntity productEntity = new ProductJpaEntity();
        productEntity.setId(orderItem.getProductId());
        entity.setProduct(productEntity);
        
        return entity;
    }

    private Order toDomain(OrderJpaEntity entity) {
        List<OrderItem> items = entity.getItems().stream()
                .map(this::toOrderItemDomain)
                .collect(Collectors.toList());
        
        return Order.builder()
                .id(entity.getId())
                .orderDate(entity.getOrderDate())
                .status(OrderStatus.valueOf(entity.getStatus()))
                .items(items)
                .build();
    }

    private OrderItem toOrderItemDomain(OrderItemJpaEntity entity) {
        return OrderItem.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .productName(entity.getProduct().getName())
                .quantity(entity.getQuantity())
                .price(BigDecimal.valueOf(entity.getPrice()))
                .subtotal(BigDecimal.valueOf(entity.getPrice() * entity.getQuantity()))
                .build();
    }
} 