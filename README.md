
# 💸 Wallet Service

Wallet Service is a backend REST API developed with Java and Spring Boot. It allows wallet management and provides full API documentation via Swagger. The system uses PostgreSQL as the database and Docker for containerization.

---

## 📚 Table of Contents

- [Tech Stack](#-tech-stack)
- [Requirements](#-requirements)
- [Installation](#-installation)
- [Running the Application](#-running-the-application)
- [API Documentation](#-api-documentation)
- [Postman Collection](#-postman-collection)
- [Testing](#-testing)

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Swagger (OpenAPI)**
- **Maven**

---

---

## ✅ Requirements

Make sure the following tools are installed on your system:

- [Java 21+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [Git](https://git-scm.com/)

---

## 🛠️ Installation

Clone the repository and navigate into the project directory:

```bash
git clone https://github.com/Leonardox7/wallet-service.git
cd wallet-service
```

Install dependencies:

```bash
mvn install
```

---

## ▶️ Running the Application

Start the application using Docker Compose:

```bash
docker compose up -d
```

This will spin up:
- The Spring Boot API
- A PostgreSQL container

The API will be available at:

```
http://localhost:8080/api/wallet-service/v1/wallets
```

---

## 📄 API Documentation

Swagger UI is available at:

```
http://localhost:8080/api/wallet-service/v1/wallets/swagger-ui/index.html
```

It provides full access to all endpoints and their descriptions.

---

## 📬 Postman Collection

A Postman collection file (`RcPay.postman_collection.json`) is included at the root of the project.

You can import it into [Postman](https://www.postman.com/) to test all available endpoints.

---

## 🧪 Testing

To run unit and integration tests, use:

```bash
mvn test
```
---