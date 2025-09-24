# HR Management System — API Endpoints (Summary)

This summary mirrors the controllers visible in Swagger. Use the live UI for full request/response schemas and parameter details.

---

## 📑 Table of Contents
- [Conventions](#-conventions)
- [Employee Controller](#-employee-controller)
- [Admin Controller](#-admin-controller)
- [Typical Payloads](#-typical-payloads-indicative)

---

## 🔐 Conventions
- JWT in `Authorization: Bearer <token>`
- Unless stated, **Employee** endpoints require an authenticated employee; **Admin** endpoints require `ADMIN`.
- Pagination: `page`, `size` (where applicable)
- Dates are ISO: `YYYY-MM-DD`

---

## 👤 Employee Controller

| Method | Path | Purpose / Notes |
|---|---|---|
| **PUT** | `/employees/worklogs/{workLogId}` | Update a work log entry |
| **POST** | `/employees` | Create/register employee (context-specific) |
| **POST** | `/employees/vacations/request` | Submit a vacation request |
| **POST** | `/employees/profile/logout` | Logout current session |
| **POST** | `/employees/profile/avatar` | Upload/replace profile avatar |
| **DELETE** | `/employees/profile/avatar` | Remove profile avatar |
| **POST** | `/employees/login/recordwork` | Record work during/after login (per Swagger label) |
| **POST** | `/employees/login/change-password` | Change password |
| **POST** | `/employees/forgot-password` | Start password reset |
| **GET** | `/employees/worklogs/day/{day}` | Fetch work logs for a given day |
| **GET** | `/employees/vacations/pending` | View own pending vacations |
| **GET** | `/employees/vacations/leave-types` | Available leave types |
| **GET** | `/employees/vacations/filter` | Filter own vacations (query params) |
| **GET** | `/employees/vacations/dates` | Calendar of vacation dates |
| **GET** | `/employees/vacations/dashboard` | Summary dashboard for vacations |
| **GET** | `/employees/projects/dropdown` | Lightweight project list for UI dropdown |
| **GET** | `/employees/dashboard/home` | Employee home metrics |
| **GET** | `/employees/calendar/{year}/{month}` | Calendar data for month |
| **GET** | `/employees/assignedProjects` | Projects assigned to employee |
| **GET** | `/employees/assignedProjects/{projectId}` | Details for an assigned project |
| **DELETE** | `/employees/worklogs/task/{taskClickId}/day/{day}` | Delete work entry by task/day |
| **DELETE** | `/employees/vacations/{id}` | Cancel own vacation request |

---

## 🛠 Admin Controller

| Method | Path | Purpose / Notes |
|---|---|---|
| **PUT** | `/admin/updateProject/{projectName}` | Update project (by name) |
| **PUT** | `/admin/updateEmployee/{employeeId}` | Update employee |
| **PUT** | `/admin/projects/{projectId}/start` | Mark project as started |
| **PUT** | `/admin/projects/{projectId}/complete` | Mark project as complete |
| **POST** | `/admin/projects/{projectId}/assign` | Assign employees to a project |
| **POST** | `/admin/employees/{employeeId}/leaves/{leaveId}/reject` | Reject leave |
| **POST** | `/admin/employees/{employeeId}/leaves/{leaveId}/approve` | Approve leave |
| **POST** | `/admin/employees/{employeeId}/assign` | Assign employee to project/team |
| **POST** | `/admin/employees/addEmployee` | Create employee (admin path) |
| **POST** | `/admin/createProject` | Create project |
| **GET** | `/admin/teams` | List teams |
| **GET** | `/admin/projects` | List projects |
| **GET** | `/admin/projects/{projectId}` | Project details |
| **GET** | `/admin/projects/dropdown` | Project list for dropdown |
| **GET** | `/admin/employees/{employeeId}/worklogs` | Employee work logs |
| **GET** | `/admin/employees/{employeeId}/vacations` | Employee vacations |
| **GET** | `/admin/employees/{employeeId}/projects` | Employee’s projects |
| **GET** | `/admin/employees/{employeeId}/profile/leaves` | Leave profile for employee |
| **GET** | `/admin/employees/{employeeId}/details` | Employee details |
| **GET** | `/admin/employees/getAllEmployees` | All employees |
| **GET** | `/admin/employees/filter` | Filter employees |
| **GET** | `/admin/employees/employeesStatus` | Status summary |
| **GET** | `/admin/employees/dropdown` | Employees for dropdown |
| **GET** | `/admin/dashboard/home` | Admin home metrics |
| **GET** | `/admin/calendar/requiredData` | Calendar base data |
| **GET** | `/admin/calendar/projects` | Calendar projects view |

---

## 📦 Typical Payloads (indicative)
### Create Work Log
```json
POST /employees/worklogs
{
  "employeeId": "E-1002",
  "logDay": "2025-09-15",
  "fromTime": "08:00:00",
  "toTime": "10:30:00",
  "projectCode": "PRJ-001",
  "description": "Feature implementation"
}
