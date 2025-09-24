# Bug Tracker — API Endpoints (Summary)

This summary mirrors the main controllers for **Projects**, **Issues**, and **Comments**. Use Swagger UI for parameters/schemas.

**Base URL (dev):** `http://localhost:8080`

---

## 🔐 Conventions
- Auth (if enabled): `Authorization: Bearer <token>`
- Pagination: `?page=0&size=20&sort=createdAt,desc`
- Filters are simple query params (status, priority, assigneeId, projectId, from, to).
- All bodies are **JSON**.

---

## 🗂️ Projects

| Method | Path            | Notes                    |
|:-----:|------------------|--------------------------|
| GET   | `/projects`      | List (paged, sortable)   |
| POST  | `/projects`      | Create                   |
| GET   | `/projects/{id}` | Details                  |
| PUT   | `/projects/{id}` | Update                   |
| DELETE| `/projects/{id}` | Soft delete (recommended)|

**Create Project**
```http
POST /projects
```
```json
{ "key": "BUG", "name": "Bug Tracker", "description": "Team bug tracking", "active": true }
```

---

## 🐞 Issues

| Method | Path                     | Notes                                                                 |
|:-----:|---------------------------|-----------------------------------------------------------------------|
| GET   | `/issues`                 | Filters: `status,priority,assigneeId,projectId,from,to,page,size,sort` |
| POST  | `/issues`                 | Create (reporter = current user, if auth enabled)                     |
| GET   | `/issues/{id}`            | Details                                                               |
| PUT   | `/issues/{id}`            | Update fields (title, desc, priority, assignee)                       |
| PATCH | `/issues/{id}/transition` | State change: `OPEN→IN_PROGRESS→RESOLVED→CLOSED` (supports `REOPENED`) |
| DELETE| `/issues/{id}`            | Optional: soft delete                                                 |

**Create Issue**
```http
POST /issues
```
```json
{
  "projectId": 1,
  "title": "Login throws 500 on null email",
  "description": "Repro: submit empty email in /login.",
  "priority": "HIGH",
  "assigneeId": "USR-2002"
}
```

**Transition Issue**
```http
PATCH /issues/123/transition
```
```json
{ "to": "IN_PROGRESS" }
```

---

## 💬 Comments

| Method | Path                          | Notes                  |
|:-----:|--------------------------------|------------------------|
| GET   | `/issues/{id}/comments`        | List                   |
| POST  | `/issues/{id}/comments`        | Add comment            |
| DELETE| `/issues/{id}/comments/{cid}`  | Remove (author/admin)  |

**Add Comment**
```http
POST /issues/123/comments
```
```json
{ "body": "Investigating in auth service; suspect NPE in validator." }
```

---

## 🔍 Filtering Examples
```http
GET /issues?status=OPEN&priority=HIGH&projectId=1&page=0&size=10&sort=updatedAt,desc
GET /issues?assigneeId=USR-2002&from=2025-09-01&to=2025-09-30
```
