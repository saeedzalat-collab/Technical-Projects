# HR Management System (Case Study + Demo)

This repository documents and demonstrates a **Human Resources Management System** that I **personally led the design and implementation of** during my professional role.  
It combines **case study documentation** (how I approached the problem and designed the solution) with a **demo implementation** (sanitized Spring Boot backend).

---

## My Role (Leadership)

- **System Design Lead** → I designed the overall architecture (backend layers, database schema, API contracts, multi-tenancy strategy).  
- **Implementation Lead** → I built the core backend services myself (authentication, employees, projects, work logs, vacations).  
- **Cross-Team Collaboration** → I defined REST contracts and worked closely with the frontend team (React) to integrate.  
- **Mentorship** → I guided junior engineers on coding standards and testing practices.

---

## Case Study

### Problem
HR operations were manual, with spreadsheets and email approvals leading to inefficiency and poor visibility.

### Solution
I led the team to deliver an **internal HR Management System**:
- Secure authentication (JWT-based)
- Role-based access (Admin, Manager, Employee)
- Employee data management
- Work log tracking & reporting
- Vacation request & approval workflow
- Multi-tenant support (per-company Tenant ID)

### Impact
- Reduced approval cycle time from **3 days → a few hours**  
- Automated reporting dashboards improved manager visibility  
- Cut HR manual workload by **30%+**  

---

## Architecture

- **Backend:** Spring Boot, Spring Security, JPA/Hibernate  
- **Database:** MySQL (prod), H2 (demo)  
- **Frontend:** React (separate team)  
- **Other:** Docker, Flyway, Swagger/OpenAPI  

![System Architecture](docs/system-architecture.png)

See detailed docs:  
- [Architecture](docs/architecture.md)  
- [Database ERD](docs/erd.png)  
- [API Endpoints](docs/api.endpoints.md)  

---

## Demo Backend (Sanitized)

To demonstrate how I **implement systems I design**, I created a simplified **Spring Boot demo backend** in `/demo-backend`.

### Features
- Employees: CRUD + pagination
- Work Logs: create & list by employee/day
- Projects: basic project management
- Vacations: request + approve/reject
- REST APIs documented with Swagger (`/swagger-ui.html`)
- H2 DB (in-memory, no setup required)

### Tech
- Java 17, Spring Boot 3
- Spring Data JPA, Lombok
- Swagger/OpenAPI
- H2 database

### Quickstart
```bash
cd demo-backend
mvn spring-boot:run
