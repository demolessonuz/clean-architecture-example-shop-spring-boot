package com.shop.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderJpaEntity order;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;
    
    private Integer quantity;
    private Double price;
} 