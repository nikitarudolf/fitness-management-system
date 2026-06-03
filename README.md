# 🏋️ Fitness Management System

Fitness Management System is a backend application designed to automate fitness club operations. The system provides functionality for managing users, trainers, memberships, workout bookings, gym visits, and club administration.

## 🚀 Features

* JWT-based authentication and authorization
* User and trainer management
* Membership and subscription management
* Workout scheduling and booking
* Gym visit tracking
* Club statistics and administration
* Database versioning with Flyway
* OpenAPI/Swagger documentation
* Dockerized deployment

## 🛠 Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL
* Flyway

### Tools

* Maven
* Docker
* Docker Compose
* Swagger / OpenAPI

## 🏗 Architecture

The project follows a **feature-based modular monolith architecture**. Business functionality is organized into independent modules, making the codebase easier to maintain, extend, and scale.

```text
src/main/java/com/fitnessmanage/system

├── admin
├── booking
├── gym
├── security
├── subscription
├── trainer
├── user
└── common
```

### Modules

| Module       | Responsibility                          |
| ------------ | --------------------------------------- |
| admin        | Club administration and statistics      |
| booking      | Workout scheduling and booking          |
| gym          | Gym visit tracking                      |
| subscription | Membership management                   |
| trainer      | Trainer profiles and schedules          |
| user         | User management                         |
| security     | Authentication and authorization        |
| common       | Shared utilities and exception handling |

Each feature contains its own business logic, DTOs, repositories, services, and controllers.

## 📋 Business Functionality

### Authentication

* User registration
* User login
* JWT token generation
* Role-based access control

### Membership Management

* Purchase subscriptions
* Manage subscription plans
* Track active memberships

### Trainer Management

* Register trainers
* Manage trainer schedules
* View trainer information

### Workout Booking

* Create schedule slots
* Book training sessions
* Manage booking status

### Gym Management

* Register visits
* Monitor gym occupancy

### Administration

* Register trainers
* Assign subscriptions
* View club statistics

## 🗄 Database Migrations

Database schema is managed using Flyway.

```text
V1 - Users and Trainers
V2 - Subscriptions
V3 - Schedules and Bookings
V4 - Visits
V5 - Default Data
```

## 🐳 Running with Docker

```bash
docker-compose up --build
```

## ⚙️ Running Locally

### Clone repository

```bash
git clone https://github.com/your-username/fitness-management-system.git
cd fitness-management-system/system
```

### Start PostgreSQL

Configure PostgreSQL and update the database settings in:

```text
src/main/resources/application.yml
```

### Run application

```bash
mvn clean install
mvn spring-boot:run
```

## 📖 API Documentation

After startup Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## 🎯 Project Goals

The project was developed as a diploma project to gain practical experience in:

* Backend development with Java and Spring Boot
* REST API design
* Database design and migrations
* Authentication and authorization
* Docker-based deployment
* Feature-based application architecture
