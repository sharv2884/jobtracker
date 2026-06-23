# Job Application Tracker

A RESTful API built with Java and Spring Boot for tracking job applications. Built as a learning project covering Spring Boot, PostgreSQL, JWT authentication, Docker, and cloud deployment.

## Tech Stack

- **Java 17** — core language
- **Spring Boot 3.5** — web framework and auto-configuration
- **Spring Data JPA + Hibernate** — ORM and database access
- **PostgreSQL** — relational database (hosted on Neon)
- **Spring Security + JWT** — authentication and route protection *(in progress)*
- **Docker** — containerization *(planned)*
- **Render** — cloud deployment *(planned)*
- **Swagger / OpenAPI** — API documentation *(planned)*

## Project Status

| Phase | Description | Status |
|-------|-------------|--------|
| 1 | Project setup, DB connection | ✅ Done |
| 2 | Job Application CRUD, exception handling, pagination | ✅ Done |
| 3 | JWT Authentication (register/login) | 🔄 In progress |
| 4 | Swagger docs + polish | ⏳ Pending |
| 5 | Docker + docker-compose | ⏳ Pending |
| 6 | GitHub + Render deployment | ⏳ Pending |

## Project Structure

```
src/main/java/com/sharv/jobtracker/
├── config/          # Security and app configuration
├── controller/      # HTTP request handlers
├── dto/             # Data Transfer Objects (request/response shapes)
├── entity/          # JPA entities (database table mappings)
├── exception/       # Custom exceptions and global error handler
├── repository/      # Spring Data JPA repositories
└── service/         # Business logic
```

## API Endpoints

All endpoints are prefixed with `/api/applications`.

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/applications` | Create a new job application |
| GET | `/api/applications` | Get all applications (paginated) |
| GET | `/api/applications/{id}` | Get a single application by ID |
| PUT | `/api/applications/{id}` | Update an application |
| DELETE | `/api/applications/{id}` | Delete an application |

### Query Parameters for GET /api/applications

| Parameter | Default | Description |
|-----------|---------|-------------|
| `page` | 0 | Page number (zero-indexed) |
| `size` | 10 | Items per page |
| `sortBy` | createdAt | Field to sort by |
| `direction` | desc | Sort direction (asc or desc) |
| `status` | — | Filter by status (APPLIED, INTERVIEW, OFFER, REJECTED, WITHDRAWN) |

### Example Request Body (POST / PUT)

```json
{
  "companyName": "Google",
  "jobTitle": "Backend Engineer",
  "status": "APPLIED",
  "appliedDate": "2026-06-11",
  "notes": "Applied via referral"
}
```

### Application Statuses

`APPLIED` `INTERVIEW` `OFFER` `REJECTED` `WITHDRAWN`

## Running Locally

### Prerequisites

- Java 17+
- Maven
- A PostgreSQL database (local or [Neon](https://neon.tech) free tier)

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/sharv2884/jobtracker.git
   cd jobtracker
   ```

2. Configure your database connection in `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:postgresql://<host>/<dbname>?sslmode=require
   spring.datasource.username=<your_username>
   spring.datasource.password=<your_password>
   ```

3. Run the application
   ```bash
   mvn spring-boot:run
   ```

4. The API will be available at `http://localhost:8080`

## Exception Handling

All errors return a consistent JSON structure:

```json
{
  "timestamp": "2026-06-11T19:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Job application not found with id: 5"
}
```

Validation errors include a `fields` object showing which fields failed and why.

## What I Learned Building This

- How Spring Boot's layered architecture works (Controller → Service → Repository → Entity)
- What JPA and Hibernate do and how entities map to database tables
- How Spring Data's derived query methods auto-generate SQL from method names
- How pagination works with `Pageable` and `Page<T>`
- How `@RestControllerAdvice` enables centralized exception handling across the app
- How Lombok reduces boilerplate with `@Data`, `@RequiredArgsConstructor`, etc.
- Why DTOs exist and how they decouple the API contract from the database schema

## Concepts Reviewed (Phase 2 Recap)

Before starting Phase 3 (JWT Auth), did a full pass reviewing:
- Spring Boot auto-configuration, IoC, and dependency injection
- JPA/Hibernate entity mapping and lifecycle hooks (`@PrePersist`, `@PreUpdate`)
- Spring Data derived query methods and pagination (`Page`, `Pageable`)
- Centralized exception handling via `@RestControllerAdvice`
- DTO pattern and Bean Validation
- Constructor-based dependency injection in the Service layer
- `ResponseEntity` and HTTP status code control in Controllers

## Author

[Sharvani A R](https://github.com/sharv2884)
