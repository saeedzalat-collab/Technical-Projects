# HR Management System — Architecture (Led design & implementation)

## 🎯 Overview
This document summarizes the architecture I led for an internal HR platform covering:
- Employees, projects, work logs, vacation workflows
- Role-based access (Admin / Manager / Employee)
- Production on MySQL; demo/dev on H2
- Spring Boot (REST), React SPA, JWT security

---

## 🧭 System Context

flowchart LR
  subgraph Users
    A[Admin] --- M[Manager] --- E[Employee]
  end

  A -->|HTTPS/JSON| FE[React SPA]
  M -->|HTTPS/JSON| FE
  E -->|HTTPS/JSON| FE

  FE -->|REST| API[Spring Boot API<br/>Controllers, Services, Repositories]
  API -->|JPA| DB[(MySQL (prod)<br/>H2 (demo))]
  API --> SEC[(JWT / RBAC)]
  API --> MAIL[(Email / SMTP)]
  API -.-> OBS[(Logging & Tracing)]

  %% optional styling
  classDef fe fill:#dbeafe,stroke:#1f2937,stroke-width:1px;
  classDef svc fill:#dcfce7,stroke:#1f2937,stroke-width:1px;
  classDef db fill:#fef3c7,stroke:#1f2937,stroke-width:1px;
  class FE fe
  class API svc
  class DB db


