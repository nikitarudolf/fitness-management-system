# 🏋️ Fitness Management System

A backend application for managing fitness club operations, including user management, trainer scheduling, subscriptions, gym visits, and workout bookings.

## ✨ Features

* 🔐 JWT-based authentication and authorization
* 👥 User and trainer management
* 📅 Workout scheduling and booking system
* 🎫 Subscription management
* 🏃 Gym visit tracking
* 📊 Administrative functionality and club statistics
* 🗄 Database versioning with Flyway
* 📖 API documentation with Swagger/OpenAPI
* 🐳 Dockerized deployment

---

## 🛠 Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate
* Bean Validation

### Database

* PostgreSQL
* Flyway

### Security

* JWT Authentication
* Role-Based Access Control (RBAC)

### DevOps & Tools

* Maven
* Docker
* Docker Compose
* Swagger / OpenAPI
* Lombok

---

## 🏗 Architecture

The project follows a layered architecture:

```text
Controller Layer
        ↓
Service Layer
        ↓
Repository Layer
        ↓
PostgreSQL Database
```

### Main Modules

```text
admin          - administration and statistics
user           - user management
trainer        - trainer management
subscription   - subscription management
booking        - workout booking system
gym            - visit tracking
security       - authentication and authorization
common         - shared utilities and exception handling
```

---

## 📋 Business Functionality

### User Management

* Registration and authentication
* Profile management
* Role-based access control

### Trainer Management

* Trainer registration
* Schedule management
* Session organization

### Subscription Management

* Subscription creation
* Subscription tracking
* Membership administration

### Booking System

* Training session booking
* Schedule slot management
* Booking status control

### Gym Visits

* Visit registration
* Attendance tracking

---

## 🗄 Database

The database schema is managed through Flyway migrations:

```text
V1 - Users and Trainers
V2 - Subscriptions
V3 - Schedules and Bookings
V4 - Visits
V5 - Initial Data
```

---

## 🚀 Getting Started

### Prerequisites

* Java 21
* Maven 3.9+
* Docker
* PostgreSQL

### Clone Repository

```bash
git clone https://github.com/YOUR_USERNAME/fitness-management-system.git
cd fitness-management-system/system
```

### Run with Docker

```bash
docker-compose up --build
```

### Run Locally

```bash
mvn clean install
mvn spring-boot:run
```

---

## 📖 API Documentation

After application startup Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

or

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🎯 Learning Goals

This project was developed as a diploma project to gain practical experience with:

* Enterprise Java development
* REST API design
* Spring ecosystem
* Database design
* Authentication and authorization
* Docker-based deployment
* Database migrations
* Layered application architecture

---
