# Shop Application

A Spring Boot application implementing a clean architecture pattern for an e-commerce shop system.

## Project Overview

This project demonstrates a clean architecture implementation with the following features:
- Product management (CRUD operations)
- Order management (CRUD operations)
- Clean separation of concerns
- Domain-driven design principles
- Spring Boot and Spring Data JPA integration

## Architecture Layers

The project follows a clean architecture pattern with the following layers:

### 1. Domain Layer (`com.shop.domain`)
- Contains business logic and domain models
- Defines repository interfaces
- Independent of external frameworks
- Contains:
  - Models (Order, Product, OrderItem)
  - Repository interfaces
  - Domain enums (OrderStatus)

### 2. Application Layer (`com.shop.application`)
- Implements use cases
- Orchestrates domain objects
- Contains:
  - Services (OrderService, ProductService)
  - Use case implementations
  - Transaction management

### 3. Infrastructure Layer (`com.shop.infrastructure`)
- Implements technical concerns
- Contains:
  - JPA entities
  - Repository implementations
  - Database configurations
  - External service integrations

### 4. Presentation Layer (`com.shop.presentation`)
- Handles HTTP requests
- Contains:
  - Controllers
  - DTOs
  - Request/Response mappings

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database (for development)
- Maven
- Lombok
- Swagger/OpenAPI

## Prerequisites

- JDK 17 or later
- Maven 3.6 or later
- IDE (IntelliJ IDEA, Eclipse, etc.)

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd shop
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Project Structure

```
src/main/java/com/shop/
├── domain/
│   ├── model/
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── OrderStatus.java
│   │   └── Product.java
│   └── repository/
│       ├── OrderRepository.java
│       └── ProductRepository.java
├── application/
│   └── service/
│       ├── OrderService.java
│       └── ProductService.java
├── infrastructure/
│   └── persistence/
│       ├── entity/
│       │   ├── OrderJpaEntity.java
│       │   ├── OrderItemJpaEntity.java
│       │   └── ProductJpaEntity.java
│       ├── repository/
│       │   ├── OrderJpaRepository.java
│       │   └── ProductJpaRepository.java
│       └── repository/impl/
│           ├── OrderRepositoryImpl.java
│           └── ProductRepositoryImpl.java
└── presentation/
    ├── controller/
    │   ├── OrderController.java
    │   └── ProductController.java
    └── dto/
        ├── OrderDto.java
        ├── OrderItemDto.java
        └── ProductDto.java
```

## Database Configuration

The application uses H2 in-memory database for development. Configuration can be found in:
```
src/main/resources/application.properties
```

## Testing

Run the tests using:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 