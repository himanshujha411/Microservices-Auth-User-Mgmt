# StackSplit - Auth Service 🛡️

This is the **Authentication microservice** of the shopping cart web application.  
It handles user registration, login, and profile-related operations using **Spring Boot**, **Spring Security**, and **PostgreSQL**.

> 🧩 Part of a microservices-based 3-tier architecture:
> - `auth-service` (This repo)
> - `orders-service`
> - `delivery-service`
> - `frontend` (React)

---

## 🔧 Prerequisites

Ensure the following are installed before running:

- [Java JDK 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Git](https://git-scm.com/)

> ⚠️ Make sure the other microservices are also running for full functionality.

---

## ⚙️ Tech Stack

- Java 17  
- Spring Boot  
- Spring Security (JWT-based or Basic)  
- Spring Data JPA  
- PostgreSQL  
- Maven

---

## 📁 Folder Structure

```
auth-service/
├── bo/ # Business objects (DTOs or domain logic)
├── common/ # Shared utilities or constants
├── config/ # Configuration classes (e.g., security, beans)
├── controller/ # REST controllers (expose endpoints)
├── dao/ # Repository layer (DAO interfaces)
├── security/ # Security configs (JWT filters, auth providers)
├── util/ # Utility classes and helpers
├── vo/ # Value objects (Request/Response models)
├── AuthApplication.java # Main Spring Boot application entry point
└── application.properties
```

---

## 🚀 Running the Service

1. **Clone the Repository**

   ```
   git clone https://github.com/himanshujha411/Microservices-Auth-User-Mgmt.git
   cd Microservices-Auth-User-Mgmt.git
   ```
   
2. **Configure PostgreSQL**

Create a database (e.g., auth_db) and update the following in src/main/resources/application.properties:
```
server.port=8081

spring.datasource.url=jdbc:postgresql://localhost:5432/auth_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Build and Run**
```
./mvnw spring-boot:run
```
