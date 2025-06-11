# 🏢 Technical Task - Microservices Project

> A modern microservices architecture built with Spring Boot 3.x for managing companies and users

## 🚀 Tech Stack

![Java](https://img.shields.io/badge/Java-24-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

- **Java 24**
- **Spring Boot 3.x**
- **Spring Eureka** (Service Discovery)
- **Spring Web, Data JPA**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Lombok**

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │ Discovery Service│
│   Port: 8080    │    │   (Eureka)      │
└─────────────────┘    └─────────────────┘
         │                       │
         └───────────┬───────────┘
                     │
    ┌────────────────┼────────────────┐
    │                │                │
┌───▼────┐      ┌────▼─────┐     ┌────▼──────┐
│Company │      │   User   │     │PostgreSQL│
│Service │      │ Service  │     │ Database  │
└────────┘      └──────────┘     └───────────┘
```

## 📦 Project Modules

| Module | Description | Port |
|--------|-------------|------|
| `api-gateway` | Routes requests to microservices | 8080 |
| `discovery-service` | Eureka server for service discovery | 8761 |
| `company-service` | Manages company operations | 8081 |
| `user-service` | Manages user/customer operations | 8082 |

## 🛠️ Quick Start

### Prerequisites
- Java 24
- Docker & Docker Compose
- PostgreSQL

### Running with Docker
```bash
# Clone the repository
git clone https://github.com/xhenezzz/technicaltask-project.git
cd technicaltask-project

# Start all services
docker-compose up -d

# Check if services are running
docker-compose ps
```

### Running Locally
```bash
# Start Discovery Service first
cd discovery-service
./mvnw spring-boot:run

# Start Company Service
cd ../company-service
./mvnw spring-boot:run

# Start User Service
cd ../user-service
./mvnw spring-boot:run

# Start API Gateway
cd ../api-gateway
./mvnw spring-boot:run
```

## 📋 API Documentation

### 🏢 Company Service

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/company/all` | Get all companies | - |
| `POST` | `/company/create` | Create new company | `{"companyName": "Google"}` |
| `PUT` | `/company/edit/{id}` | Update company | `{"companyName": "Microsoft"}` |
| `DELETE` | `/company/delete/{id}` | Delete company | - |

### 👤 Customer Service

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `GET` | `/customer/info/{id}` | Get customer info | - |
| `POST` | `/customer/create` | Create new customer | `{"username": "xhene", "password": "12345"}` |
| `PUT` | `/customer/update/{id}` | Update customer | `{"companyId": 1, "position": "BACKEND"}` |
| `DELETE` | `/customer/delete/{id}` | Delete customer | - |

### 💼 Available Positions
- `BACKEND`
- `FRONTEND` 
- `QA`
- `CEO`
- `PRODUCT`

## 🧪 API Examples

### Create Company
```bash
curl -X POST http://localhost:8080/company/create \
  -H "Content-Type: application/json" \
  -d '{"companyName": "Google"}'
```

### Create Customer
```bash
curl -X POST http://localhost:8080/customer/create \
  -H "Content-Type: application/json" \
  -d '{"username": "xhene", "password": "12345"}'
```

### Update Customer Position
```bash
curl -X PUT http://localhost:8080/customer/update/1 \
  -H "Content-Type: application/json" \
  -d '{"companyId": 1, "position": "BACKEND"}'
```

## 🌐 Service URLs

- **API Gateway**: http://localhost:8080
- **Eureka Dashboard**: http://localhost:8761
- **Company Service**: http://localhost:8081
- **User Service**: http://localhost:8082

## 📊 Database Schema

### Companies Table
```sql
CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Customers Table
```sql
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    company_id BIGINT REFERENCES companies(id),
    position VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🔧 Configuration

### Environment Variables
```env
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=technical_task
DB_USERNAME=postgres
DB_PASSWORD=password

# Eureka
EUREKA_SERVER=http://localhost:8761/eureka
```

## 🐳 Docker Configuration

```yaml
# docker-compose.yml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: technical_task
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
      
  discovery-service:
    build: ./discovery-service
    ports:
      - "8761:8761"
      
  # ... other services
```

## 👨‍💻 Author

**Aidyn Kelbetov** - [@xhenezzz](https://github.com/xhenezzz)

---

⭐ **Star this repository if you found it helpful!**
