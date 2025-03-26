package com.shop.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String customerName;
    private String customerEmail;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus status;
} 