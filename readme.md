# Ticket Booking System Backend

A fully containerized backend for a movie ticket booking platform. This system handles  booking logic, specifically designed to handle **seat locking** (preventing double-bookings).

---

## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.4
* **Database:** PostgreSQL 15
* **Caching:** Redis
* **Message Queue:** RabbitMQ
* **Build Tool:** Maven
* **Containerization:** Docker & Docker Compose

---

## Architecture Modules

1. **User Module**: JWT Authentication, Registration, Role Management.
2. **Venue Module**: Administration of Cities, Theaters, Screens, and Seat Layouts.
3. **Movie Module**: Catalog management for Movies and Showtimes.
4. **Booking Module**: Transactional booking flow, payment simulation, and ticket generation.

---

## Getting Started

### Prerequisites

* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Must be running)
* Java 21 SDK

### 1. Clone the Repository

```bash
git clone https://github.com/levyashvin/ticket-booking-demo.git
cd ticket-booking-demo
```

### 2. Start Infrastructure

Spin up PostgreSQL, Redis, and RabbitMQ containers.

```bash
docker-compose up -d
```

> **Note:** Please wait 10-15 seconds for the database to fully initialize.

### 3. Run the Application

```bash
mvnw spring-boot:run
```

---

## API Documentation

### Interactive Swagger UI

Once the server is running, explore the endpoints via the browser:
**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### Postman Workflow

1. **Register User:** `POST /api/v1/auth/register`
2. **Login:** `POST /api/v1/auth/authenticate`
    * *Copy the Bearer Token from the response.*
3. **Admin Setup:** Create City -> Theater -> Screen -> Movie -> Show.
4. **Book Ticket:** `POST /api/v1/bookings`
    * *Header:* `Authorization: Bearer <YOUR_TOKEN>`

---

## Testing

This project uses **Testcontainers**. Docker must be running to execute tests, as it spins up a temporary real PostgreSQL instance.

```bash
mvn test
```
