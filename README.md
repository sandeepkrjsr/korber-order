# Korber Order Service

## Overview
The **Order Service** manages customer orders and integrates with the **Inventory Service** to reserve stock. 
It uses an in-memory **H2 database** for order storage and **Liquibase** for schema management and initial data loading.

---

## Technologies
- Java 25, Spring Boot 4.0.1
- Spring Data JPA, H2 Database
- Liquibase for DB versioning
- JUnit 5 & Mockito for testing
- REST APIs
- Feign client / REST template to communicate with Inventory Service

---

## Setup

Clone the repository:
git clone https://github.com/sandeepkrjsr/korber-order.git
cd korber-order
Build & run:
./mvnw clean install
./mvnw spring-boot:run

Service runs on http://localhost:8080

H2 console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:orderdb)

## API Endpoints

Place Order
POST /orders
Request body:
{
  "productId": 101,
  "quantity": 3
}
Reserves stock via the Inventory Service and creates a new order.

Get Orders
GET /orders/{orderId}
Returns details of a specific order.

Database & Liquibase
Schema and initial CSV data (orders.csv) are managed via Liquibase (db/changelog/db.changelog-master-order.xml).

H2 in-memory database is used for development and testing.

## Testing

Unit tests: OrderServiceTest using JUnit 5 + Mockito

Integration tests: OrderControllerIT with @SpringBootTest and H2

Run all tests:
./mvnw test
Notes
Communicates with Inventory Service to reserve stock before order placement.

CRUD operations can be tested via Postman.

ChatGPT can make mistakes. Check important info. See Cookie Preferences.
