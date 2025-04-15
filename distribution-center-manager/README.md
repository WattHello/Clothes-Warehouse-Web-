# Distribution Center Manager

This is a Spring Boot REST API application that manages distribution centers for the Clothes Warehouse project.

# Features

- Manage distribution centers (add, delete, list)
- Manage items in distribution centers (add, delete, search)
- Request items from distribution centers with quantity support
- Item quantity tracking and management
- Basic authentication security
- Multiple profile support (dev and qa)

# Technologies Used

- Spring Boot 3.2.3
- Spring Data JPA
- Spring Security
- H2 Database
- Lombok

# Getting Started

# Prerequisites

- Have Java 17 or higher
- Have Maven 3.6 or higher

# Running the Application

Clone the repository and navigate to the project directory. Then run the application with the desired profile:

# Development Profile (Default)

*On application.properties, be sure to uncomment on which default profile you want to use before starting

# Development profile (port 8081)
mvn spring-boot:run "-Dspring.profiles.active=dev"

# QA Profile

# QA profile (port 8082)
mvn spring-boot:run "-Dspring.profiles.active=qa"

# Profiles Configuration

The application supports two different profiles with separate configurations:

# Development Profile
- Port: 8081
- Database: In-memory H2 (devdb)
- Logging: DEBUG level for application code
- URL: http://localhost:8081

# QA Profile
- Port: 8082
- Database: In-memory H2 (qadb)
- Logging: INFO level for application code
- URL: http://localhost:8082

# API Endpoints

The application exposes the following REST API endpoints:

- `GET /api/distribution-centers` - Get all distribution centers
- `GET /api/distribution-centers/{id}` - Get a distribution center by ID
- `POST /api/distribution-centers` - Create a new distribution center
- `DELETE /api/distribution-centers/{id}` - Delete a distribution center
- `POST /api/distribution-centers/{id}/items` - Add an item to a distribution center
- `DELETE /api/distribution-centers/{distributionCenterId}/items/{itemId}` - Delete an item from a distribution center
- `GET /api/distribution-centers/items/search?brand=BRAND&name=NAME` - Search for items by brand and name
- `POST /api/distribution-centers/{distributionCenterId}/items/{itemId}/request?quantity=QUANTITY` - Request an item from a distribution center

# Item Request Example

When requesting items, the system will:
1. Check if the requested quantity is available
2. Reduce the quantity from the distribution center if available
3. Return the updated item information or an error if quantity is insufficient

Example request:
POST /api/distribution-centers/1/items/3/request?quantity=2
Authorization: Basic YWRtaW46YWRtaW4=

# Authentication

The API is secured with basic authentication. The default credentials are:

- Username: admin
- Password: admin

# Integration with Clothes Warehouse

This application is designed to work with the Clothes Warehouse application. When running both applications:

# For Development Environment
- Distribution Center Manager runs on port 8081
- Clothes Warehouse runs on port 8080
- Clothes Warehouse connects to Distribution Center Manager at http://localhost:8081

# For QA Environment
- Distribution Center Manager runs on port 8082
- Clothes Warehouse runs on port 8083
- Clothes Warehouse connects to Distribution Center Manager at http://localhost:8082