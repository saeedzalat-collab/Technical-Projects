# Invoice Management Service

A clean, runnable Spring Boot microservice that demonstrates a basic **Invoice Management** domain.  
This starter includes a REST API, JPA/H2 persistence, OpenAPI/Swagger docs, and a simple (permit-all) security config to keep demos frictionless.

> Current scope (MVP): generic CRUD-like endpoints using `Invoice`, `InvoiceLine`, and `Provider` entity scaffolds.  
> The README also provides step-by-step guidance to **evolve** it into a production-style billing app.

---

## Features (MVP)

- Spring Boot 3.x (Java 17+), Maven build
- REST endpoints:
  - `POST /invoices` – create an invoice (scaffold)
  - `GET  /invoices/{id}` – fetch one
  - `GET  /invoices` – list all (optional filters)
  - `POST /invoices/{invoiceId}/lines` – add invoice line
  - `GET  /invoices/{invoiceId}/lines` – list lines
  - `POST /providers` / `GET /providers` – basic provider CRUD
- H2 in-memory DB (no external setup)
- Swagger UI (OpenAPI) out of the box
- Simple Security (all endpoints are open → easy to demo/Postman)
- ModelMapper bean ready (for DTO mapping)

---

## Tech Stack

- **Java 17**, **Spring Boot 3.x**
- Spring Web, Spring Data JPA, Spring Validation
- **H2** in-memory database (dev)
- **springdoc-openapi** (Swagger UI)
- Lombok (optional), ModelMapper

---

## Project Structure

```
invoice-management-service/
├─ pom.xml
├─ README.md
├─ src/
│  ├─ main/java/com/example/invoicemanagement/
│  │  ├─ InvoiceManagementServiceApplication.java
│  │  ├─ config/MapperConfig.java
│  │  ├─ domain/
│  │  │  ├─ entity/
│  │  │  │  ├─ Invoice.java
│  │  │  │  ├─ InvoiceLine.java
│  │  │  │  └─ Provider.java
│  │  │  └─ repository/
│  │  │     ├─ InvoiceRepository.java
│  │  │     ├─ InvoiceLineRepository.java
│  │  │     └─ ProviderRepository.java
│  │  ├─ security/SecurityConfig.java
│  │  ├─ service/
│  │  │  ├─ InvoiceService.java
│  │  │  ├─ ProviderService.java
│  │  │  └─ impl/...
│  │  └─ web/
│  │     ├─ controller/
│  │     │  ├─ InvoiceController.java
│  │     │  └─ ProviderController.java
│  │     └─ model/
│  │        ├─ request/...
│  │        └─ response/...
│  └─ main/resources/application.yml
```

> Package names are examples—keep your existing ones if different.

---

## Run

```bash
# From the project root:
mvn spring-boot:run
```

- Swagger UI: **http://localhost:8088/swagger-ui/index.html**
- OpenAPI JSON: **http://localhost:8088/v3/api-docs**
- H2 Console: **http://localhost:8088/h2-console**
  - JDBC URL: `jdbc:h2:mem:testdb`
  - User: `sa`, Password: *(empty)*

> Port is configured in `src/main/resources/application.yml`.

---

## Configuration

`src/main/resources/application.yml` (excerpt):

```yaml
server:
  port: 8088

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

springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui/index.html
```

---

## Security

`SecurityConfig` permits all requests (no auth) to simplify demos and Postman tests:

```java
http.csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
```

> If you want JWT/login later, see the **Enhancements** section.

---

## Domain (MVP)

Minimal entity scaffolds to keep the starter small and focused:

```java
// Invoice.java
@Entity @Table(name = "invoices")
@Data @NoArgsConstructor @AllArgsConstructor
public class Invoice {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate invoiceDate;
  private String currency;
  private String userId;           // who the invoice belongs to
  @ManyToOne(fetch = FetchType.LAZY)
  private Provider provider;
}

// InvoiceLine.java
@Entity @Table(name = "invoice_lines")
@Data @NoArgsConstructor @AllArgsConstructor
public class InvoiceLine {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private BigDecimal unitPrice;
  private Integer quantity;
  private BigDecimal taxRate;      // optional

  @ManyToOne(fetch = FetchType.LAZY)
  private Invoice invoice;
}

// Provider.java
@Entity @Table(name = "providers")
@Data @NoArgsConstructor @AllArgsConstructor
public class Provider {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String taxId;
  private String email;
  private String phone;
}
```

> Evolve additional entities (e.g., `User`, `Address`) as needed (outside MVP).

---

## REST API

### Create Invoice (scaffold)
`POST /invoices`

**Request body** (example):
```json
{
  "invoiceDate": "2025-09-24",
  "currency": "USD",
  "userId": "USR-1001",
  "providerId": 1,
  "notes": "September services"
}
```

**Sample cURL**
```bash
curl -X POST http://localhost:8088/invoices   -H "Content-Type: application/json"   -d '{
        "invoiceDate": "2025-09-24",
        "currency": "USD",
        "userId": "USR-1001",
        "providerId": 1,
        "notes": "September services"
      }'
```

**Response (example)**
```json
{
  "id": 123,
  "message": "Created",
  "data": {
    "id": 123,
    "invoiceDate": "2025-09-24",
    "currency": "USD",
    "userId": "USR-1001",
    "provider": { "id": 1, "name": "Acme" },
    "totalAmount": 960.00
  }
}
```

---

### Get by ID
`GET /invoices/{id}`

**Sample cURL**
```bash
curl http://localhost:8088/invoices/123
```

**Response (found)**
```json
{
  "id": 123,
  "message": "OK",
  "data": { "...invoice fields, with lines if expanded..." }
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

### List all (with optional filters)
`GET /invoices?userId=USR-1001&providerId=1&page=0&size=10`

**Sample cURL**
```bash
curl "http://localhost:8088/invoices?userId=USR-1001&providerId=1&page=0&size=10"
```

**Response (paged example)**
```json
{
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "content": [
    { "...invoice summary..." }
  ]
}
```

---

### Add Invoice Line
`POST /invoices/{invoiceId}/lines`

**Request body** (example):
```json
{
  "description": "Development hours",
  "quantity": 12,
  "unitPrice": 80.00,
  "taxRate": 0.0
}
```

**Sample cURL**
```bash
curl -X POST http://localhost:8088/invoices/123/lines   -H "Content-Type: application/json"   -d '{
        "description": "Development hours",
        "quantity": 12,
        "unitPrice": 80.0,
        "taxRate": 0.0
      }'
```

---

### List Invoice Lines
`GET /invoices/{invoiceId}/lines`

**Sample cURL**
```bash
curl http://localhost:8088/invoices/123/lines
```

---

### Provider CRUD (minimal)
`POST /providers` • `GET /providers` • `GET /providers/{id}` • `PUT /providers/{id}` • `DELETE /providers/{id}`

**Create Provider (example)**
```http
POST /providers
```
```json
{
  "name": "Acme Supplies",
  "taxId": "TAX-12345",
  "email": "billing@acme.example",
  "phone": "+1-202-555-0123"
}
```

---

## Quick Postman Collection (minimal)

Save the JSON below as `Invoice-Management.postman_collection.json` and import into Postman:

```json
{
  "info": { "name": "Invoice Management Service", "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json" },
  "item": [
    {
      "name": "Create Invoice",
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "url": { "raw": "http://localhost:8088/invoices", "protocol": "http", "host": ["localhost"], "port": "8088", "path": ["invoices"] },
        "body": { "mode": "raw", "raw": "{\n  \"invoiceDate\": \"2025-09-24\",\n  \"currency\": \"USD\",\n  \"userId\": \"USR-1001\",\n  \"providerId\": 1,\n  \"notes\": \"September services\"\n}" }
      }
    },
    {
      "name": "Get Invoice by ID",
      "request": { "method": "GET", "url": "http://localhost:8088/invoices/{{id}}" }
    },
    {
      "name": "List Invoices",
      "request": { "method": "GET", "url": "http://localhost:8088/invoices" }
    },
    {
      "name": "Add Invoice Line",
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "url": { "raw": "http://localhost:8088/invoices/{{id}}/lines", "protocol": "http", "host": ["localhost"], "port": "8088", "path": ["invoices","{{id}}","lines"] },
        "body": { "mode": "raw", "raw": "{\n  \"description\": \"Development hours\",\n  \"quantity\": 12,\n  \"unitPrice\": 80.00,\n  \"taxRate\": 0.0\n}" }
      }
    },
    {
      "name": "Create Provider",
      "request": {
        "method": "POST",
        "header": [{ "key": "Content-Type", "value": "application/json" }],
        "url": { "raw": "http://localhost:8088/providers", "protocol": "http", "host": ["localhost"], "port": "8088", "path": ["providers"] },
        "body": { "mode": "raw", "raw": "{\n  \"name\": \"Acme Supplies\",\n  \"taxId\": \"TAX-12345\",\n  \"email\": \"billing@acme.example\",\n  \"phone\": \"+1-202-555-0123\"\n}" }
      }
    }
  ]
}
```

---

## Enhancements (turn it into production)

Use this checklist to evolve the scaffold into a production-style service:

### 1) Rich Domain + Totals
- Add `status` (DRAFT, ISSUED, PAID, CANCELLED), `dueDate`, discounts/taxes at header level.
- Compute totals from lines (subtotal, tax, grand total).

### 2) Filtering & Pagination
- `GET /invoices?status=ISSUED&from=2025-09-01&to=2025-09-30&page=0&size=10`
- Use `Pageable` and Spring Data Specifications.

### 3) Authentication & Authorization
- Add **JWT** login (`POST /auth/login`) and roles (`USER`, `ADMIN`).
- Protect create/update/delete endpoints.

### 4) Persistence Profiles
- Switch H2 → **MySQL/PostgreSQL** via Spring profiles.  
- Add `docker-compose.yml` for local DB.

### 5) Observability & Quality
- Actuator + Micrometer
- Structured logging (correlation IDs)
- Unit/Integration tests (MockMvc/Testcontainers)
- Flyway migrations

---

## Troubleshooting

- **Port already in use**: change `server.port` in `application.yml` or free the port.
- **Swagger UI not loading**: ensure the app starts cleanly; hit `/v3/api-docs` to confirm OpenAPI is served.
- **H2 Console fails**: verify JDBC URL `jdbc:h2:mem:testdb` and user `sa` (empty password).

---

## Housekeeping

- `.gitignore` should exclude `target/`, IDE files, and logs.
- No secrets in the repo. If you add external DBs, keep creds in env vars or a `.env` (not committed).

---

## License

This sample is intended for educational/portfolio use. You can adapt and reuse it in your personal projects.
