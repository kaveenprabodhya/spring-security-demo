# Spring Brewery Web App

## Overview
This project is a web application for managing a brewery, built using Spring Boot. It includes both RESTful APIs and traditional MVC controllers, with Spring Security for authentication and authorization.

## Technology Stack
- Spring Boot 2.6.x
- Spring Security
- Spring Data JPA
- H2 Database (for development)
- Thymeleaf (for server-side rendering)
- Maven

## Features
- CRUD operations for managing brewery entities (e.g., beers, breweries).
- RESTful API for integration with other systems.
- MVC controllers for web-based interactions.
- Spring Security for securing the application.
    1. Basic Authentication
    2. In Memory Authentication
    3. JDBC Authentiocation Managing
    4. Custom Authentication filter adding
    5. Adding Roles
    6. Adding Authrities
    7. CSRF Protection
    8. Adding Login Form
    9. Remember me tokens
    10. User Lockout events


## Architecture
The application follows a layered architecture with the following key components:

- **Model**: Represents the data of the application (e.g., Beer, Brewery, Customer entities).
- **Repository**: Provides CRUD operations for the entities (e.g., BeerRepository).
- **Service**: Contains the business logic (e.g., BeerService).
- **Controller**: Handles incoming web requests and interacts with the service layer (e.g., BeerController, WebController).
- **Security**: Manages authentication and authorization (e.g., SecurityConfig).


## API Endpoints

### Public Endpoints
- `GET /api/v1/beer/` - Retrieve a list of beers.
- `GET /api/v1/beer/{ID}` - Retrieve a beer by ID.
- `GET /api/v1/beerUPC/{UPC}` - Retrieve a beer by UPC.

### Secured Endpoints
- `POST /api/v1/beers` - Add a new beer (secured).
- `PUT /api/v1/beers/{id}` - Update a beer (secured).
- `DELETE /api/v1/beers/{id}` - Delete a beer (secured).
- `GET /api/v1/breweries` - Retrieve a list of breweries (secured).

## Web Endpoints

### Public Pages
- `GET /` - Home page.
- `GET /login` - Login page.
- `GET /beers/find` - Find beers page.
- `GET /beers` - List of beers page.

### Secured Pages
- `GET /beers/{beerId}` - View beer details page.
- `GET /beers/new` - Add new beer page.
- `POST /beers/new` - Handle new beer submission.
- `GET /beers/{beerId}/edit` - Edit beer page.
- `POST /beers/{beerId}/edit` - Handle beer edit submission.
- `GET /brewery/breweries`, `/breweries/index`, `/breweries/index.html`, `/breweries.html` - List of breweries page.
- `GET /customers/find` - Find customers page.
- `GET /customers/{customerId}` - View customer details page.
- `GET /customers/new` - Add new customer page.
- `POST /customers/new` - Handle new customer submission.
- `GET /customers/{customerId}/edit` - Edit customer page.
- `POST /customers/{customerId}/edit` - Handle customer edit submission.


## Testing
The application includes unit and integration tests to ensure the correctness of the business logic and the proper functioning of the web endpoints.
