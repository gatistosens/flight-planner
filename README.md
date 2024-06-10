# Flight Planner Application

Welcome to the Flight Planner application! This project allows you to manage and search for flights using a web interface. Below is a guide to get you started with setting up and running the application.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Security Configuration](#security-configuration)
- [Database Configuration](#database-configuration)
- [Technologies Used](#technologies-used)

## Prerequisites

To run this project, you need to have the following installed:
- Java 22 or higher
- Gradle 8.8 or higher

## Installation

1. **Clone the repository:**

   ```sh
   git clone https://github.com/yourusername/flight-planner.git
   cd flight-planner
   
2. Build the project:
   
   ```sh
   ./gradlew build
## Running the Application

  To run the application, you can use the following command:
  ```sh
  ./gradlew bootRun
  ```

The application will start on port `8080` by default. You can access the H2 database console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) with the following credentials:

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** `password`

## API Endpoints

### Public Endpoints
- **Search Airports:** `GET /api/airports?search={searchTerm}`
- **Search Flights:** `POST /api/flights/search`
- **Get Flight by ID:** `GET /api/flights/{id}`

### Admin Endpoints
- **Get Flight by ID:** `GET /admin-api/flights/{id}`
- **Add a Flight:** `PUT /admin-api/flights`
- **Delete a Flight:** `DELETE /admin-api/flights/{id}`

### Testing Endpoint
- **Clear Flights:** `POST /testing-api/clear`

### Example Requests

**Search Flights:**

```json
POST /api/flights/search
{
  "from": "JFK",
  "to": "LAX",
  "departureDate": "2024-06-15"
}
```
**Add a Flight:**

```json
PUT /admin-api/flights
{
  "from": {
    "country": "USA",
    "city": "New York",
    "airport": "JFK"
  },
  "to": {
    "country": "USA",
    "city": "Los Angeles",
    "airport": "LAX"
  },
  "carrier": "Delta",
  "departureTime": "2024-06-15 10:00",
  "arrivalTime": "2024-06-15 13:00"
}

```
## Security Configuration

The security configuration is defined in `SecurityConfiguration.java`. Basic HTTP authentication is enabled, and the following endpoints are publicly accessible:
- `/testing-api/**`
- `/api/**`
- `/error/**`

All other endpoints require authentication. The default admin credentials are:
- **Username:** `codelex-admin`
- **Password:** `Password123`

## Database Configuration

The application uses an H2 in-memory database for development and testing. The database schema is managed using Liquibase. The change logs are located in `src/main/resources/db/changelog`.

## Technologies Used

- **Language:** Java
- **Frameworks:** Spring Boot
- **Build Tool:** Gradle
- **Database:** H2 (In-memory)
- **Database Migration:** Liquibase
- **Authentication:** Basic HTTP authentication
- **Others:** Jakarta Persistence (JPA), Jakarta Validation

<p align="right">(<a href="#readme-top">back to top</a>)</p>

