# Transaction Starter Project

This project implements the Customer Transactions exercise using Java and Spring Boot.

## Before you start

The project can be built and tested using the Maven wrapper.

## Run Tests

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

Expected result:

```text
Tests run: 6
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```



## Technologies Used

- Java 17
- Spring Boot
- Maven
- Spring Web
- Spring Data JPA
- H2 embedded database
- JUnit / Spring Boot Test

## Exercise

The application implements the four required transaction operations:

1. Create transaction
2. Get transaction
3. Update transaction status
4. Get all transactions for a customer

## Transaction Fields

Every transaction contains:

- Transaction ID
- Customer ID
- Amount
- Currency
- Transaction Type
- Transaction Status

## Validation Rules

The following validation and business rules are implemented:

- Transaction ID must be unique.
- Customer ID must be provided.
- Transaction amount must be greater than zero.
- A transaction must exist before it can be retrieved or updated.
- Only a PENDING transaction can be changed to COMPLETED.

### Amount Validation

The transaction amount must be greater than zero.

Example error:

```json
{
  "error": "Amount must be greater than zero"
}

### Duplicate Transaction ID

A Transaction ID must be unique.

Example error:

```json
{
  "error": "Transaction ID already exists: TXN100"
}

### Transaction Not Found

If the requested transaction does not exist, the application returns an error.

Example:

```json
{
  "error": "Transaction not found: DOESNOTEXIST"
}

## Status Transition

A transaction can move from:

PENDING → COMPLETED

Only a transaction with the current status `PENDING` can be updated to `COMPLETED`.

A transaction that is already `COMPLETED` cannot be changed back to `PENDING`.

### Why this rule?

Once a transaction is completed, it is considered finished. Therefore, the application does not allow it to move back to an earlier status.

## API

The application runs on:

http://localhost:8080

### Create Transaction

POST /api/transactions

Example request:

```json
{
  "transactionId": "TXN100",
  "customerId": "CUST100",
  "amount": 500.00,
  "currency": "USD",
  "transactionType": "PAYMENT",
  "transactionStatus": "PENDING"
}

Successful response:

201 Created

Get Transaction

GET /api/transactions/{transactionId}

Example:

GET /api/transactions/TXN100

Successful response:

200 OK

Update Transaction Status

PATCH /api/transactions/{transactionId}/status?status=COMPLETED

Example:

PATCH /api/transactions/TXN100/status?status=COMPLETED

Successful response:

200 OK

Get Customer Transactions

GET /api/transactions/customer/{customerId}

Example:

GET /api/transactions/customer/CUST100

This returns all transactions belonging to the specified Customer ID.

Successful response:

200 OK

## Application Workflow

The application follows a layered architecture:

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

### Controller

The Controller receives HTTP requests and returns HTTP responses.

### Service

The Service contains the business logic and validation rules.

### Repository

The Repository communicates with the H2 database using Spring Data JPA.

### Entity

The Transaction entity represents transaction data stored in the database.

## Exception Handling

The application uses centralized exception handling with `@RestControllerAdvice`.

The following exceptions are handled:

- `IllegalArgumentException` → `400 Bad Request`
- `ResourceNotFoundException` → `404 Not Found`

This provides consistent JSON error responses from the API.

Example error response:

```json
{
  "error": "Transaction not found: DOESNOTEXIST"
}

## Database

The application uses the H2 embedded database.

Spring Data JPA is used for database persistence and repository operations.

No separate database installation is required to run the project.

## Project Structure

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


                ## Testing

Automated tests are implemented using JUnit.

The tests cover meaningful transaction behaviour, including:

- Successful transaction operations
- Amount validation
- Duplicate Transaction ID handling
- Transaction-not-found handling
- Status update behaviour

Run the tests using:

### Linux / macOS

```bash
./mvnw clean test

mvnw.cmd clean test

Tests run: 6
Failures: 0
Errors: 0
Skipped: 0

## Manual API Testing

The following API operations were manually tested:

- POST /api/transactions
- GET /api/transactions/{transactionId}
- PATCH /api/transactions/{transactionId}/status
- GET /api/transactions/customer/{customerId}

Both successful operations and important validation/error scenarios were tested during development.

## Assumptions

- Transaction IDs uniquely identify transactions.
- Transaction amounts must be greater than zero.
- A transaction must exist before it can be retrieved or updated.
- Only PENDING transactions can be updated, and the new status must be COMPLETED or FAILED.
- A COMPLETED transaction is treated as final for the status-update operation.
- H2 is sufficient for the scope of this exercise.

## Known Limitations

The implementation focuses on the four required transaction operations.

Possible future improvements include:

- More comprehensive field validation
- Additional integration tests
- Production database configuration
- Authentication and authorization
- Structured logging and monitoring
- Additional transaction status rules
- API documentation using OpenAPI/Swagger

## Running the Complete Project

From the project root:

### 1. Run tests

```cmd
mvnw.cmd clean test

mvnw.cmd spring-boot:run

curl.exe -i "http://localhost:8080/api/transactions/TXN100"

## Project Status

- Create Transaction: Completed
- Get Transaction: Completed
- Update Transaction Status: Completed
- Get Customer Transactions: Completed
- Validation: Completed
- Exception Handling: Completed
- H2 Persistence: Completed
- Automated Tests: 6/6 passing
- Manual API Testing: Completed
- Maven Build: Successful
