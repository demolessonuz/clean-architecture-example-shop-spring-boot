package com.shop.presentation.controller;

import com.shop.domain.model.Product;
import com.shop.domain.port.ProductUseCase;
import com.shop.presentation.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {
    private final ProductUseCase productUseCase;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = toDomain(productDto);
        Product savedProduct = productUseCase.createProduct(product);
        return ResponseEntity.ok(toDto(savedProduct));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    public ResponseEntity<ProductDto> getProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id) {
        return productUseCase.getProduct(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves all products in the system")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productUseCase.getAllProducts().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product")
    public ResponseEntity<ProductDto> updateProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id,
            @RequestBody ProductDto productDto) {
        Product product = toDomain(productDto);
        Product updatedProduct = productUseCase.updateProduct(id, product);
        return ResponseEntity.ok(toDto(updatedProduct));
    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Updates the stock quantity of a product")
    public ResponseEntity<ProductDto> updateStock(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "New stock quantity", required = true)
            @RequestParam int quantity) {
        Product updatedProduct = productUseCase.updateStock(id, quantity);
        return ResponseEntity.ok(toDto(updatedProduct));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product by its ID")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true)
            @PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private Product toDomain(ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .build();
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }
} 