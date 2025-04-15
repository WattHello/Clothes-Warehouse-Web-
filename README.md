# Clothes Warehouse Project

This project consists of two Spring Boot applications:

1. **Clothes Warehouse** - A web application for managing a clothes warehouse inventory
2. **Distribution Center Manager** - A REST API for managing distribution centers

# Project Structure

├── clothes-warehouse/         # Clothes Warehouse web application
└── distribution-center-manager/ # Distribution Center Manager REST API

# Features

# Clothes Warehouse

- User authentication and authorization (Admin, Warehouse Employee roles)
- Inventory management (add, delete, list, filter, sort)
- Integration with Distribution Center Manager for item requests
- Responsive UI with Bootstrap

# Distribution Center Manager

- REST API for managing distribution centers
- Basic authentication security (username/password)
- Item management within distribution centers
- Item request functionality
- Add items to distribution centers
- Quantity-aware inventory management for all items
- Support for multiple profile configurations (dev/qa)

# Technologies Used

- Spring Boot 3.2.3
- Spring Data JPA
- Spring Security
- Thymeleaf
- Bootstrap
- H2 Database
- Lombok

# Important: Running with Profiles

This application supports two profiles: `dev` and `qa`.

# Running in QA Mode:

For the Distribution Center Manager (runs on port 8082):

cd distribution-center-manager
mvn spring-boot:run -Dspring-boot.run.profiles=qa

For the Clothes Warehouse (runs on port 8083):

cd clothes-warehouse/clothes-warehouse
mvn spring-boot:run -Dspring-boot.run.profiles=qa

# Running in Development Mode:

For the Distribution Center Manager (runs on port 8081):

cd distribution-center-manager
mvn spring-boot:run -Dspring-boot.run.profiles=dev

For the Clothes Warehouse (runs on port 8080):

cd clothes-warehouse/clothes-warehouse
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Port Configuration

- **Dev Profile**:
  - Distribution Center Manager: Port 8081
  - Clothes Warehouse: Port 8080

- **QA Profile**:
  - Distribution Center Manager: Port 8082  
  - Clothes Warehouse: Port 8083

# API Documentation

The Distribution Center Manager provides API documentation through Swagger UI:

- **DEV Environment**: http://localhost:8081/api-docs.html
- **QA Environment**: http://localhost:8082/api-docs-qa.html

The QA environment has a special version of the API documentation with a yellow banner that clearly indicates it's the QA environment running on port 8082.

This documentation automatically updates to reflect the current port based on the active profile.

# API Manager

The Distribution Center Manager provides an API Manager interface for testing and interacting with the API:

- **DEV Environment**: http://localhost:8081/api-manager.html
- **QA Environment**: http://localhost:8082/api-manager-qa.html

The QA environment has a special version of the API Manager with a yellow banner that clearly indicates it's the QA environment running on port 8082.

# H2 Database Console

You can access the in-memory H2 database console at:

- **DEV Environment**: http://localhost:8081/h2-console
- **QA Environment**: http://localhost:8082/h2-console

Connection settings:
- JDBC URL: `jdbc:h2:mem:devdb` (for DEV) or `jdbc:h2:mem:qadb` (for QA)
- Username: `sa`
- Password: `password`

# Troubleshooting

- If you see a message about "default profile" being used, make sure you're using the correct parameter format:

  -Dspring-boot.run.profiles=qa
    
- Always start the Distribution Center Manager FIRST, then start the Clothes Warehouse

- If you see a "Connection refused" error, check that both applications are running and using the correct profile

# Default Credentials

- Username: admin
- Password: admin

# Admin Features

- View and manage all warehouse inventory items
- View all available distribution centers and their items
- Request items from distribution centers to replenish warehouse stock
- Add items to distribution centers

# Warehouse Employee Features

- View and manage warehouse inventory
- View distribution centers and their available items
- Add items to distribution centers

# Project Documentation

For more detailed information about each application, please refer to their respective README files:

- [Clothes Warehouse README](clothes-warehouse/clothes-warehouse/README.md)
- [Distribution Center Manager README](distribution-center-manager/README.md) 