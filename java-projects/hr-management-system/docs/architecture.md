# HR Management System — Architecture (Led design & implementation)

## Overview
This document summarizes the architecture I led for an internal HR platform covering:
- Employees, projects, work logs, vacation workflows
- Role-based access (Admin / Manager / Employee)
- Production on MySQL; demo/dev on H2
- Spring Boot (REST), React SPA, JWT security

---

## System Context
```mermaid
flowchart LR
  A[Admin] --> FE[React SPA]
  M[Manager] --> FE
  E[Employee] --> FE

  FE --> API["Spring Boot API<br/>Controllers, Services, Repositories"]
  API --> DB["MySQL (prod)<br/>H2 (demo)"]
  API --> SEC["JWT / RBAC"]
  API --> MAIL["Email / SMTP"]
  API --> OBS["Logging & Tracing"]
```
---
## Key choices
- Spring Boot for cohesive web + security + data stack
- JWT + RBAC for stateless auth and role-based authorization
- JPA/Hibernate for persistence + migrations via Flyway (prod)
- React SPA for fast UX; decoupled from backend via REST
  
---

## Backend Architecture

### Layering
- Controllers – REST endpoints; validation; DTO mapping
- Services – business rules; transactions; orchestration
- Repositories – Spring Data JPA; query methods
- Entities – JPA domain model

### Cross-cutting
- Security – filters for JWT; method-level @PreAuthorize
- Exception Handling – global @ControllerAdvice
- Observability – structured logs; request IDs
---
## Security Model
- Auth: JWT bearer tokens (Authorization: Bearer <token>)
- Roles: ADMIN, MANAGER, EMPLOYEE
- Tenancy (optional): X-Tenant-Id header for multi-tenant isolation
---
## Data Model (ERD)
```mermaid
erDiagram
  EMPLOYEE {
    string employeeId PK
    string firstName
    string lastName
    string email UK
    enum   role
  }

  PROJECT {
    string code PK
    string name
  }

  WORK_LOG {
    long   id PK
    date   logDay
    time   fromTime
    time   toTime
    string description
  }

  VACATION {
    long   id PK
    date   startDate
    date   endDate
    enum   status  "PENDING|APPROVED|REJECTED"
  }

  EMPLOYEE ||--o{ WORK_LOG  : logs
  PROJECT  ||--o{ WORK_LOG  : used_in
  EMPLOYEE ||--o{ VACATION  : requests

```
--
## Example Use-Case (Sequence)
```mermaid
sequenceDiagram
  actor Emp as Employee
  participant FE as React SPA
  participant API as Spring Boot
  participant DB as MySQL/H2

  Emp->>FE: Fill vacation form
  FE->>API: POST /employees/vacations/request
  API->>DB: INSERT Vacation(status=PENDING)
  API-->>FE: 201 Created + body
  note over API: Optional: send email to Manager
```
--
## Deployment
- Containers: Docker images for API and DB
- DB Migrations: Flyway on app startup (prod)
- Envs: application-{env}.yml with secrets in env vars
- CI/CD: build, test, scan, push image; deploy to env
--
## Outcomes
- Approval cycle cut from days → hours
- Centralized visibility for managers
- Clear separation of concerns → maintainable & testable
