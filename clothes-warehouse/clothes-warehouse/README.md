# Clothes Warehouse

This is a Spring Boot web application that manages a clothes warehouse inventory.

# Features

- User authentication and authorization (Admin, Warehouse Employee roles)
- Inventory management (add, delete, list, filter, sort)
- Integration with Distribution Center Manager for item requests
- Distribution center management view for administrators
- Request items from distribution centers with quantity tracking
- Responsive UI with Bootstrap

# Technologies Used

- Spring Boot 3.2.3
- Spring Data JPA
- Spring Security
- Thymeleaf
- Bootstrap
- H2 Database
- Lombok

# Getting Started

# Prerequisites

- Have Java 17 or higher
- Have Maven 3.6 or higher
- Have the Distribution Center Manager running (see below)

# Running the Application

# Development Profile (Default)

1. First, start the Distribution Center Manager on port 8081:

cd ../distribution-center-manager
mvn spring-boot:run "-Dspring.profiles.active=dev"

2. Then, start the Clothes Warehouse application:

cd clothes-warehouse
mvn spring-boot:run "-Dspring.profiles.active=dev"

3. Access the application at: http://localhost:8080

# QA Profile

1. First, start the Distribution Center Manager on port 8082:

cd ../distribution-center-manager
mvn spring-boot:run "-Dspring.profiles.active=qa"

2. Then, start the Clothes Warehouse application:

cd clothes-warehouse
mvn spring-boot:run "-Dspring.profiles.active=qa"

3. Access the application at: http://localhost:8083

# Default Credentials

- Username: admin
- Password: admin

# Pages

- Home - Welcome page
- Login - User authentication
- Register - User registration
- Inventory - View and filter inventory
- Add Item - Add new items to inventory
- Admin Management - Manage inventory and request items from distribution centers
- Distribution Center Management - View and manage items in distribution centers

# Admin Features

The admin page provides several management features:
- View and delete warehouse inventory items
- View all available distribution centers and their items
- Request items from distribution centers to replenish warehouse stock
- Add new items to distribution centers

# Item Request Workflow

1. Navigate to the Admin Management page
2. Go to the "Request Item" tab
3. Enter the item name, select a brand, and specify the quantity
4. Submit the request
5. The system will search for the item across all distribution centers
6. If an item with sufficient quantity is found:
   - The quantity will be deducted from the distribution center
   - The item will be added to the warehouse inventory
   - You'll be redirected back to the admin page
7. If no item is found or quantity is insufficient:
   - You'll be redirected to an error page

# Integration with Distribution Center Manager

The application integrates with the Distribution Center Manager API with different configurations based on the active profile:

# Development Profile

distribution.center.api.url=http://localhost:8081
distribution.center.api.username=admin
distribution.center.api.password=admin

# QA Profile

distribution.center.api.url=http://localhost:8082
distribution.center.api.username=admin
distribution.center.api.password=admin

This integration allows:
- Viewing all available distribution centers
- Viewing items available in each distribution center
- Requesting items from distribution centers to replenish inventory
- Adding new items to distribution centers