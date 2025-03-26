package com.shop.presentation.controller;

import com.shop.domain.model.Order;
import com.shop.domain.model.OrderItem;
import com.shop.domain.model.OrderStatus;
import com.shop.domain.port.OrderUseCase;
import com.shop.presentation.dto.OrderDto;
import com.shop.presentation.dto.OrderItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing orders")
public class OrderController {
    private final OrderUseCase orderUseCase;

    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates a new order with the provided details")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = toDomain(orderDto);
        Order savedOrder = orderUseCase.createOrder(order);
        return ResponseEntity.ok(toDto(savedOrder));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID")
    public ResponseEntity<OrderDto> getOrder(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id) {
        return orderUseCase.getOrder(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieves all orders in the system")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderUseCase.getAllOrders().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status", description = "Updates the status of an existing order")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "New order status", required = true)
            @RequestParam OrderStatus status) {
        Order updatedOrder = orderUseCase.updateOrderStatus(id, status);
        return ResponseEntity.ok(toDto(updatedOrder));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order", description = "Deletes an order by its ID")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "Order ID", required = true)
            @PathVariable Long id) {
        orderUseCase.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    private Order toDomain(OrderDto dto) {
        List<OrderItem> items = dto.getItems().stream()
                .map(item -> OrderItem.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return Order.builder()
                .id(dto.getId())
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .items(items)
                .totalAmount(dto.getTotalAmount())
                .orderDate(dto.getOrderDate())
                .status(dto.getStatus())
                .build();
    }

    private OrderDto toDto(Order order) {
        List<OrderItemDto> items = order.getItems().stream()
                .map(item -> OrderItemDto.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .items(items)
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .build();
    }
} 