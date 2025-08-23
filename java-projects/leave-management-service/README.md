# Leave Management Service

A clean, runnable Spring Boot microservice that demonstrates a basic **Leave Management** domain.  
This starter includes a REST API, JPA/H2 persistence, OpenAPI/Swagger docs, and a simple (permit-all) security config to keep demos frictionless.

> Current scope (MVP): generic CRUD-like endpoints using a `LeaveRequest` entity scaffold (id, name, description, active).  
> The README also provides step-by-step guidance to **evolve** it into a full leave-flow app.

---

## Features (MVP)

- Spring Boot 3.3 (Java 17+), Maven build
- REST endpoints:
  - `POST /api/leaverequests` – create a leave request (scaffold)
  - `GET  /api/leaverequests/{id}` – fetch one
  - `GET  /api/leaverequests` – list all
- H2 in-memory DB (no external setup)
- Swagger UI (OpenAPI) out of the box
- Simple Security (all endpoints are open → easy to demo/postman)
- ModelMapper bean ready (for future DTO mapping)

---

## Tech Stack

- **Java 17**, **Spring Boot 3.3**
- Spring Web, Spring Data JPA, Spring Validation
- **H2** in-memory database
- **springdoc-openapi** (Swagger UI)
- Lombok (optional), ModelMapper

---

##  Project Structure

```
leave-management-service/
├─ pom.xml
├─ README.md
├─ src/
│  ├─ main/java/com/example/leavemanagementservice/
│  │  ├─ LeaveManagementServiceApplication.java
│  │  ├─ config/MapperConfig.java
│  │  ├─ io/
│  │  │  ├─ entity/LeaveRequest.java
│  │  │  └─ repository/LeaveRequestRepository.java
│  │  ├─ security/SecurityConfig.java
│  │  ├─ service/
│  │  │  ├─ LeaveRequestService.java
│  │  │  └─ impl/LeaveRequestServiceImpl.java
│  │  └─ ui/
│  │     ├─ controller/LeaveRequestController.java
│  │     └─ model/Dtos.java          # RequestModel / ResponseModel (generic)
│  └─ main/resources/application.yml
```

---

##  Run

```bash
# From the project root:
mvn spring-boot:run
```

- Swagger UI: **http://localhost:8082/swagger-ui/index.html**
- OpenAPI JSON: **http://localhost:8082/v3/api-docs**
- H2 Console: **http://localhost:8082/h2-console**
  - JDBC URL: `jdbc:h2:mem:testdb`
  - User: `sa`, Password: *(empty)*

> Port is configured in `src/main/resources/application.yml`.

---

##  Configuration

`src/main/resources/application.yml` (excerpt):

```yaml
server:
  port: 8082

spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
    driverClassName: org.h2.Driver
    username: sa
    password: ""
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true
      path: /h2-console
```

---

##  Security

`SecurityConfig` permits all requests (no auth) to simplify demos and Postman tests:

```java
http.csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
```

> If you want JWT/login later, see the **Enhancements** section.

---

##  Domain (MVP)

The scaffolded `LeaveRequest` entity (simple fields to keep the starter minimal):

```java
@Entity
@Table(name = "leaverequests")
@Data @NoArgsConstructor @AllArgsConstructor
public class LeaveRequest {
  @Id
  private String id;
  private String name;         // placeholder (use employee name or title)
  private String description;  // placeholder (use reason)
  private boolean active;      // placeholder (use active flag)
}
```

> You’ll evolve this to real leave fields: `employeeId`, `startDate`, `endDate`, `leaveType`, `status`, `comment`, etc. (see **Enhancements** below).

---

##  REST API

### Create Leave (scaffold)
`POST /api/leaverequests`

**Request body** (uses `ui.model.RequestModel` as a generic DTO):
```json
{
  "name": "Annual Leave for Saeed",
  "description": "Family trip",
  "active": true
}
```

**Sample cURL**
```bash
curl -X POST http://localhost:8082/api/leaverequests   -H "Content-Type: application/json"   -d '{
        "name": "Annual Leave for Saeed",
        "description": "Family trip",
        "active": true
      }'
```

**Response**
```json
{
  "id": "generated-uuid",
  "message": "Created",
  "data": {
    "id": "generated-uuid",
    "name": "Annual Leave for Saeed",
    "description": "Family trip",
    "active": true
  }
}
```

---

### Get by ID
`GET /api/leaverequests/{id}`

**Sample cURL**
```bash
curl http://localhost:8082/api/leaverequests/REPLACE_WITH_ID
```

**Response (found)**
```json
{
  "id": "REPLACE_WITH_ID",
  "message": "OK",
  "data": { "...entity fields..." }
}
```

**Response (not found)**
```json
{
  "id": null,
  "message": "Not Found",
  "data": null
}
```

---

### List all
`GET /api/leaverequests`

**Sample cURL**
```bash
curl http://localhost:8082/api/leaverequests
```

**Response**
```json
{
  "id": null,
  "message": "OK",
  "data": [
    { "...entity1..." },
    { "...entity2..." }
  ]
}
```

---

##  Quick Postman Collection (minimal)

Save the JSON below as `Leave-Management.postman_collection.json` and import into Postman:

```json
{
  "info": { "name": "Leave Management Service", "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json" },
  "item": [
    {
      "name": "Create Leave",
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "url": { "raw": "http://localhost:8082/api/leaverequests", "protocol": "http", "host": ["localhost"], "port": "8082", "path": ["api","leaverequests"] },
        "body": { "mode": "raw", "raw": "{\n  \"name\": \"Annual Leave for Saeed\",\n  \"description\": \"Family trip\",\n  \"active\": true\n}" }
      }
    },
    {
      "name": "Get Leave by ID",
      "request": {
        "method": "GET",
        "url": "http://localhost:8082/api/leaverequests/{{id}}"
      }
    },
    {
      "name": "List Leaves",
      "request": {
        "method": "GET",
        "url": "http://localhost:8082/api/leaverequests"
      }
    }
  ]
}
```

---

##  Enhancements (turn it into a real Leave system)

Use this checklist to evolve the scaffold into a production-style service:

### 1) Rich Domain Model
- Add fields to `LeaveRequest`:
  ```java
  private String employeeId;
  private LocalDate startDate;
  private LocalDate endDate;
  private String leaveType;          // e.g., "PLANNED", "SICK", "UNPAID"
  private String status;             // "REQUESTED", "APPROVED", "REJECTED", "CANCELLED"
  private String approverId;
  private String comment;
  ```
- Validation in `RequestModel` using `@NotNull`, `@FutureOrPresent`, etc.

### 2) Business Workflow
- Endpoints:
  - `POST   /api/leaverequests` (submit)
  - `PATCH  /api/leaverequests/{id}/approve`
  - `PATCH  /api/leaverequests/{id}/reject`
  - `PATCH  /api/leaverequests/{id}/cancel`
- Service layer enforces valid transitions:
  - REQUESTED → APPROVED/REJECTED
  - APPROVED → CANCELLED (maybe under conditions)
- Prevent overlaps for the same employee and date range.

### 3) Filtering & Pagination
- `GET /api/leaverequests?status=APPROVED&leaveType=PLANNED&page=0&size=10`
- Use `Pageable` and Spring Data specs, or QueryDSL.

### 4) Authentication & Authorization
- Add **JWT**:
  - `POST /api/auth/login` → returns JWT
  - Roles: `USER` (submitter), `MANAGER` (approver), `ADMIN`
- Protect approve/reject endpoints: `hasRole('MANAGER')`

### 5) Persistence
- Switch H2 → **PostgreSQL/MySQL** via application profiles.  
- Add `docker-compose.yml` to spin up DB locally.

### 6) Observability & Quality
- Actuator + Micrometer
- Structured logging (correlation IDs)
- Unit/Integration tests (MockMvc/Testcontainers)
- Flyway migrations

---

##  Troubleshooting

- **Port already in use**: change `server.port` in `application.yml` or free the port.
- **Swagger UI not loading**: ensure the app starts cleanly; hit `/v3/api-docs` to confirm OpenAPI is served.
- **H2 Console fails**: verify JDBC URL `jdbc:h2:mem:testdb` and user `sa` (empty password).

---

##  Housekeeping

- `.gitignore` should exclude `target/`, IDE files, and logs.
- No secrets in the repo. If you add external DBs, keep creds in env vars or a `.env` (not committed).

---

##  License

This sample is intended for educational/portfolio use. You can adapt and reuse it in your personal projects.
