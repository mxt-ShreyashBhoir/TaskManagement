# Task Management API (Spring Boot • Java • Gradle)

A simple **Task Management REST API** built using **Spring Boot 3, Java 17, Gradle**, and **H2 in-memory database**.  
Supports **CRUD operations**, filtering by `status` and `priority`, request validation, and proper error handling.

---

## 🛠 Tech Stack
- **Java 17**
- **Spring Boot 3** (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`)
- **H2** in-memory database
- **JUnit 5** + Spring Boot test
- **Gradle** build system

---

## 📦 Installation & Running

### Prerequisites
- Java 17 or higher installed
- Gradle installed *(or use Gradle Wrapper if included)*

# 1. Clone this repository
git clone https://github.com/mxt-ShreyashBhoir/TaskManagement.git

# 2. Run with Gradle
gradle bootRun

# OR if Gradle Wrapper is included
./gradlew bootRun


application port:
http://localhost:8080

H2 Console:
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: 



| Method   | Endpoint          | Description                     |
| -------- | ----------------- | ------------------------------- |
| `POST`   | `/api/tasks`      | Create a new task               |
| `GET`    | `/api/tasks/{id}` | Get task by ID                  |
| `GET`    | `/api/tasks`      | List all tasks *(with filters)* |
| `PUT`    | `/api/tasks/{id}` | Update an existing task         |
| `DELETE` | `/api/tasks/{id}` | Delete a task                   |


| Field         | Type   | Required | Notes               |
| ------------- | ------ | -------- | ------------------- |
| `title`       | string | Yes      | 3–100 characters    |
| `description` | string | No       | Max 1000 characters |
| `status`      | enum   | Yes      | See allowed values  |
| `priority`    | enum   | Yes      | See allowed values  |
| `dueDate`     | date   | Yes      | Format `YYYY-MM-DD` |




🧪 Sample Requests & Responses (curl)
1️⃣ Create a Task
curl -X POST http://localhost:8080/api/tasks \
 -H "Content-Type: application/json" \
 -d '{
   "title": "Complete Spring Boot Assignment",
   "description": "Build a task management API",
   "status": "PENDING",
   "priority": "HIGH",
   "dueDate": "2025-08-20"
 }'


Response (201 Created):

{
  "id": 1,
  "title": "Complete Spring Boot Assignment",
  "description": "Build a task management API",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-08-20",
  "createdAt": "2025-08-14T14:45:12.123",
  "updatedAt": "2025-08-14T14:45:12.123"
}

2️⃣ Get Task by ID
curl -X GET http://localhost:8080/api/tasks/1


Response (200 OK):

{
  "id": 1,
  "title": "Complete Spring Boot Assignment",
  "description": "Build a task management API",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-08-20",
  "createdAt": "2025-08-14T14:45:12.123",
  "updatedAt": "2025-08-14T14:45:12.123"
}

3️⃣ List All Tasks (No Filter)
curl -X GET http://localhost:8080/api/tasks

4️⃣ Filter by Status
curl -X GET "http://localhost:8080/api/tasks?status=PENDING"

5️⃣ Filter by Priority
curl -X GET "http://localhost:8080/api/tasks?priority=HIGH"


Response (200 OK):

[
 {
  "id": 1,
  "title": "Complete Spring Boot Assignment",
  "description": "Build a task management API",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-08-20",
  "createdAt": "2025-08-14T14:45:12.123",
  "updatedAt": "2025-08-14T14:45:12.123"
 }
]

6️⃣ Update Task
curl -X PUT http://localhost:8080/api/tasks/1 \
 -H "Content-Type: application/json" \
 -d '{
   "title": "Complete Spring Boot Assignment - Updated",
   "description": "API with updates",
   "status": "IN_PROGRESS",
   "priority": "CRITICAL",
   "dueDate": "2025-08-25"
 }'


Response (200 OK):

{
  "id": 1,
  "title": "Complete Spring Boot Assignment - Updated",
  "description": "API with updates",
  "status": "IN_PROGRESS",
  "priority": "CRITICAL",
  "dueDate": "2025-08-25",
  "createdAt": "2025-08-14T14:45:12.123",
  "updatedAt": "2025-08-14T15:10:45.456"
}

7️⃣ Delete Task
curl -X DELETE http://localhost:8080/api/tasks/1


Response (204 No Content):

(no body)

8️⃣ Validation Error Example
curl -X POST http://localhost:8080/api/tasks \
 -H "Content-Type: application/json" \
 -d '{
   "title": "Hi",
   "status": "INVALID_STATUS"
 }'


Response (400 Bad Request):

{
  "timestamp": "2025-08-14T15:15:00.789",
  "status": 400,
  "errors": {
    "title": "Title must be between 3 and 100 characters",
    "priority": "Priority is required",
    "status": "must be a valid enum value"
  }
}

🧪 Running Tests
gradle test


Covers:

Controller tests for create, validation, not found

Repository test for findByStatus

📂 Project Structure
src/main/java/com/yourname/taskmanager/
├── TaskManagerApplication.java
├── controller/TaskController.java
├── dto/TaskRequestDto.java
├── dto/TaskResponseDto.java
├── entity/Task.java
├── enums/Priority.java
├── enums/TaskStatus.java
├── exception/GlobalExceptionHandler.java
├── exception/TaskNotFoundException.java
├── repository/TaskRepository.java
├── service/TaskService.java
src/test/java/com/yourname/taskmanager/
├── controller/TaskControllerTest.java
├── repository/TaskRepositoryTest.java
└── TaskManagerApplicationTests.java
