# 📚 College Management System API

A **Spring Boot REST API** for managing students, subjects, professors, and admission records in a college environment.

This project demonstrates:

- Advanced **JPA relationship mappings**
- Proper **DTO-based architecture**
- **RESTful API design**
- Handling **Many-to-Many relationships**
- Avoiding **N+1 query problems**
- Efficient **Hibernate query optimization**

---

# 🚀 Tech Stack

| Technology         | Purpose                    |
|--------------------|----------------------------|
| Spring Boot        | Backend framework          |
| Spring Data JPA    | Data access layer          |
| Hibernate          | ORM implementation         |
| MySQL              | Relational database        |
| Jackson            | JSON serialization         |
| Jakarta Validation | Request validation         |
| Lombok             | Boilerplate code reduction |

---

# 🏗 Architecture
```text
Controller Layer
      │
      ▼
Service Layer
      │
      ▼
Repository Layer
      │
      ▼
Database (MySQL)
```

---


### Layers

**Controller Layer**
- Handles HTTP requests
- Returns response DTOs

**Service Layer**
- Contains business logic
- Handles transactions

**Repository Layer**
- Data access using Spring Data JPA

---

# 📊 Entity Relationships

## Student
Represents a student enrolled in the college.

Attributes

- id
- name
- registrationNumber

Relationships

- ManyToMany → Subject
- OneToOne → AdmissionRecord

---

## Subject
Represents a course offered by the college.

Attributes

- id
- title

Relationships

- ManyToMany → Student
- ManyToOne → Professor

---

## Professor
Represents a faculty member.

Attributes

- id
- title

Relationships

- OneToMany → Subject

---

## AdmissionRecord
Stores admission details of a student.

Attributes

- student_id
- fees

Relationships

- OneToOne → Student

Uses **@MapsId** to share the same primary key with Student.

---

# 📡 REST API Endpoints

Base URL: `http://localhost:8080`

---

# 👨‍🎓 Student APIs

| Method | Endpoint                                    | Description                        |
|--------|---------------------------------------------|------------------------------------|
| GET    | /students                                   | Get all students                   |
| GET    | /students?name={name}                       | Search students by name            |
| GET    | /students/{id}                              | Get student by ID                  |
| GET    | /students/registration/{registrationNumber} | Get student by registration number |
| GET    | /students/{id}/subjects                     | Get subjects of a student          |
| POST   | /students                                   | Create new student                 |
| POST   | /students/{studentId}/subjects/{subjectId}  | Assign subject to student          |
| DELETE | /students/{studentId}/subjects/{subjectId}  | Unassign subject                   |


### Example Request

```json
POST /students

{
  "name": "Subrat Sahoo",
  "registrationNumber": "2301326263",
  "subjectIds": [10,11],
  "fees": 50000
}
```

---

## 📘 Subject APIs

| Method | Endpoint | Description |
|------|------|------|
| GET | `/subjects` | Get all subjects |
| GET | `/subjects/{id}` | Get subject by ID |
| GET | `/subjects?title={title}` | Search subject by title |
| GET | `/subjects/{id}/students` | Get students enrolled in subject |
| POST | `/subjects` | Create new subject |
| PUT | `/subjects/{subjectId}/professor/{professorId}` | Assign professor |
| DELETE | `/subjects/{subjectId}/professor` | Unassign professor |
| DELETE | `/subjects/{id}` | Delete subject |


### Example Request

```json
POST /subjects

{
  "title": "Computer Networks"
}
```

---

## 👨‍🏫 Professor APIs

| Method | Endpoint                    | Description                      |
|--------|-----------------------------|----------------------------------|
| GET    | `/professors`               | Get all professors               |
| GET    | `/professors/{id}`          | Get professor by ID              |
| GET    | `/professors?title={title}` | Search professor by title        |
| GET    | `/professors/{id}/subjects` | Get subjects taught by professor |
| POST   | `/professors`               | Create new professor             |


### Example Request

```json
POST /professors

{
  "title": "Dr. Anil Kumar"
}
```

---

## ⚡ Performance Optimizations Implemented

### DTO Based Responses

Entities are not exposed directly to API responses.

**Benefits:**

- Avoid circular JSON
- Avoid lazy loading issues
- Control response structure

---

### Lazy Loading

All relationships use:

```java
FetchType.LAZY
```

This prevents unnecessary data loading.

---

### Projection Queries

For read-heavy operations, **DTO projections** are used to avoid N+1 query problems.

**Example:**

```java
@Query("""
SELECT new StudentFlatDto(
    s.id,
    s.name,
    s.registrationNumber,
    sub.title,
    p.title
)
FROM StudentEntity s
LEFT JOIN s.subjects sub
LEFT JOIN sub.professor p
""")
List<StudentFlatDto> findAllStudentsFlat();
```

---

### Efficient Relationship Updates

When assigning subjects:

```java
student.addSubject(subject);
```

Hibernate only updates the join table.

Generated SQL:

```sql
INSERT INTO student_subject_table (student_id, subject_id)
VALUES (?, ?);
```

---

## 🛡 Validation

Input validation is handled using **Jakarta Validation**.

**Example:**

```java
@NotBlank
@Pattern(regexp = "^[A-Za-z]+$")
@Size(min = 6, max = 30)
private String name;
```

---

## 🏆 Highlights of the Implementation

### Proper DTO Architecture
Entities are never exposed directly.

---

### Correct RESTful API Design
Endpoints follow proper resource-based patterns.

---

### Efficient ManyToMany Relationship Handling
Join tables are updated without unnecessary entity modifications.

---

### Understanding of Hibernate Internals

The project correctly handles:

- Lazy loading
- Dirty checking
- Persistence context
- Bidirectional relationship synchronization

---

## 📌 Possible Improvements

Future enhancements could include:

- Pagination for large datasets
- Swagger / OpenAPI documentation
- Authentication & authorization
- Replacing ManyToMany with an explicit join entity
- Global exception handling

---

## 🎯 Conclusion

This project demonstrates a well-designed **Spring Boot backend system** implementing:

- RESTful API design
- Efficient JPA relationship mapping
- DTO-based response architecture
- Hibernate performance optimization techniques

It also showcases practical solutions for common ORM challenges such as **lazy loading issues, N+1 query problems, and entity graph traversal**.
