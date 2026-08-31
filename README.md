# Transaction Starter Project

A Spring Boot REST API for managing customer transactions.

This project implements the four required transaction operations using **Java, Spring Boot, Spring Data JPA, and H2**.

---

## 🚀 Project Overview

The application provides APIs to:

1. Create a transaction
2. Get a transaction by Transaction ID
3. Update transaction status
4. Get all transactions for a customer

It also includes validation, business rules, centralized exception handling, database persistence, and automated tests.

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot | Application framework |
| Spring Web | REST APIs |
| Spring Data JPA | Database persistence |
| H2 | Embedded database |
| Maven | Build and dependency management |
| JUnit / Spring Boot Test | Automated testing |

---

## ▶️ Before You Start

The project uses the Maven Wrapper, so a separate Maven installation is not required.

### Run Tests

**Linux / macOS**

```bash
./mvnw clean test
```

**Windows**

```cmd
mvnw.cmd clean test
```

**Windows PowerShell / VS Code**

```powershell
.\mvnw.cmd clean test
```

### Expected Test Result

```text
Tests run: 6
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

## 📋 Transaction Fields

Every transaction contains:

| Field | Description |
|---|---|
| Transaction ID | Unique identifier for the transaction |
| Customer ID | Identifies the customer |
| Amount | Transaction amount |
| Currency | Transaction currency |
| Transaction Type | Type of transaction |
| Transaction Status | Current transaction status |

---

## ✅ Validation Rules

The application implements the following validation and business rules:

- Transaction ID must be unique.
- Customer ID must be provided.
- Transaction amount must be greater than zero.
- A transaction must exist before it can be retrieved or updated.
- Only a transaction with `PENDING` status can be updated.
- The new status must be `COMPLETED` or `FAILED`.
- Invalid, empty, blank, or null status values are rejected.

### Amount Validation

The transaction amount must be greater than zero.

Example error:

```json
{
  "error": "Amount must be greater than zero"
}
```

### Duplicate Transaction ID

A Transaction ID must be unique.

Example error:

```json
{
  "error": "Transaction ID already exists: TXN100"
}
```

### Transaction Not Found

If the requested transaction does not exist, the application returns an error.

Example:

```json
{
  "error": "Transaction not found: DOESNOTEXIST"
}
```

---

## 🔄 Status Transition

A transaction can be updated only when its current status is `PENDING`.

Allowed new statuses:

```text
PENDING → COMPLETED
PENDING → FAILED
```

A transaction that is already completed or failed cannot be updated again.

### Why this rule?

Once a transaction reaches a final status, it is treated as finished. Therefore, the application does not allow the status to be changed again.

---

# 🌐 API

The application runs on:

`http://localhost:8080`

---

## 1. Create Transaction

**POST**

`/api/transactions`

### Example Request

```json
{
  "transactionId": "TXN100",
  "customerId": "CUST100",
  "amount": 500.00,
  "currency": "USD",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}
```

### Successful Response

```text
201 Created
```

The transaction is created successfully.

---

## 2. Get Transaction

**GET**

`/api/transactions/{transactionId}`

### Example

```text
GET /api/transactions/TXN100
```

### Successful Response

```text
200 OK
```

The requested transaction is returned.

### If Transaction Does Not Exist

```text
404 Not Found
```

Example:

```json
{
  "error": "Transaction not found: DOESNOTEXIST"
}
```

---

## 3. Update Transaction Status

**PATCH**

`/api/transactions/{transactionId}/status?status=COMPLETED`

### Example

```text
PATCH /api/transactions/TXN100/status?status=COMPLETED
```

The transaction must currently have `PENDING` status.

The new status must be:

```text
COMPLETED
```

or

```text
FAILED
```

### Successful Response

```text
200 OK
```

---

## 4. Get Customer Transactions

**GET**

`/api/transactions/customer/{customerId}`

### Example

```text
GET /api/transactions/customer/CUST100
```

Returns all transactions belonging to the specified Customer ID.

### Successful Response

```text
200 OK
```

If there are no matching transactions, an empty list is returned:

```json
[]
```

---

# 🔄 Application Workflow

The application follows a layered architecture:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Validation / Business Rules
     ↓
Repository
     ↓
H2 Database
     ↓
HTTP Response
```

### Controller

The Controller receives HTTP requests and returns HTTP responses.

### Service

The Service contains the business logic and validation rules.

### Repository

The Repository communicates with the H2 database using Spring Data JPA.

### Entity

The `Transaction` entity represents transaction data stored in the database.

---

# ⚠️ Exception Handling

The application uses centralized exception handling with:

`@RestControllerAdvice`

The following exceptions are handled:

| Exception | HTTP Status |
|---|---|
| `IllegalArgumentException` | `400 Bad Request` |
| `ResourceNotFoundException` | `404 Not Found` |

This provides consistent JSON error responses from the API.

### Example Error Response

```json
{
  "error": "Transaction not found: DOESNOTEXIST"
}
```

---

# 🗄️ Database

The application uses the **H2 embedded database**.

Spring Data JPA is used for database persistence and repository operations.

No separate database installation is required to run the project.

---

# 📁 Project Structure

```text
src
├── main
│   └── java
│       └── com.example.transactionstarter
│           ├── controller
│           │   └── TransactionController.java
│           ├── service
│           │   └── TransactionService.java
│           ├── transaction
│           │   ├── Transaction.java
│           │   └── TransactionRepository.java
│           └── exception
│               ├── GlobalExceptionHandler.java
│               └── ResourceNotFoundException.java
│
└── test
    └── java
        └── com.example.transactionstarter
            └── service
                └── TransactionServiceTest.java
```

---

# 🧪 Testing

Automated tests are implemented using **JUnit and Spring Boot Test**.

The tests cover meaningful transaction behaviour, including:

- Successful transaction creation
- Amount validation
- Duplicate Transaction ID handling
- Transaction-not-found handling
- Transaction status update behaviour

### Automated Test Result

```text
Tests run: 6
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

### Manual API Testing

The following API operations were manually tested:

- `POST /api/transactions`
- `GET /api/transactions/{transactionId}`
- `PATCH /api/transactions/{transactionId}/status`
- `GET /api/transactions/customer/{customerId}`

Both successful operations and important validation/error scenarios were tested during development.

Detailed input/output records are available in:

`TEST_RUN_OUTPUT.txt`

---

# 📝 Test Coverage Summary

The project test record covers:

| Test Scenario | Result |
|---|---|
| Create transaction | ✅ |
| Invalid amount | ✅ |
| Duplicate Transaction ID | ✅ |
| Get transaction | ✅ |
| Transaction not found | ✅ |
| Update transaction status | ✅ |
| Invalid status | ✅ |
| Update non-PENDING transaction | ✅ |
| Get customer transactions | ✅ |
| Customer with no transactions | ✅ |

---

# 💡 Assumptions

- Transaction IDs uniquely identify transactions.
- Transaction amounts must be greater than zero.
- A transaction must exist before it can be retrieved or updated.
- Only `PENDING` transactions can be updated.
- The new status must be `COMPLETED` or `FAILED`.
- A transaction that reaches a final status cannot be updated again.
- H2 is sufficient for the scope of this exercise.

---

# 🚧 Known Limitations

The implementation focuses on the four required transaction operations.

Possible future improvements include:

- More comprehensive field validation
- Additional integration tests
- Production database configuration
- Authentication and authorization
- Structured logging and monitoring
- Additional transaction status rules
- API documentation using OpenAPI/Swagger

---

# ▶️ Running the Complete Project

From the project root:

### 1. Run Tests

**Linux / macOS**

```bash
./mvnw clean test
```

**Windows**

```cmd
mvnw.cmd clean test
```

**Windows PowerShell / VS Code**

```powershell
.\mvnw.cmd clean test
```

### 2. Start the Application

```cmd
mvnw.cmd spring-boot:run
```

The application starts on:

`http://localhost:8080`

### 3. Test an API

Example:

```cmd
curl.exe -i "http://localhost:8080/api/transactions/TXN100"
```

---

# 📊 Project Status

| Feature | Status |
|---|---|
| Create Transaction | ✅ Completed |
| Get Transaction | ✅ Completed |
| Update Transaction Status | ✅ Completed |
| Get Customer Transactions | ✅ Completed |
| Validation | ✅ Completed |
| Exception Handling | ✅ Completed |
| H2 Persistence | ✅ Completed |
| Automated Tests | ✅ 6/6 Passing |
| Manual API Testing | ✅ Completed |
| Maven Build | ✅ Successful |

---

## 🎯 Summary

This project demonstrates a complete Spring Boot transaction service with:

**REST APIs → Validation → Business Logic → JPA Repository → H2 Database → Exception Handling → Automated Testing**

The implementation covers the required transaction operations and their important success, validation, and error scenarios.
