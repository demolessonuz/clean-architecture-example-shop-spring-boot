package com.shop.presentation.dto;
import com.shop.domain.model.OrderStatus;
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
public class OrderDto {
    private Long id;
    private String customerName;
    private String customerEmail;
    private List<OrderItemDto> items;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus status;
} 