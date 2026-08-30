# AI Usage Disclosure

## Tool Used

- ChatGPT

## Purpose of Use

ChatGPT was used as a learning and development aid during the project.

It was used to:

- Understand Spring Boot concepts and the provided starter project.
- Understand REST APIs and the Controller, Service, Repository, and Entity layers.
- Discuss validation, exception handling, and status transition approaches.
- Assist with debugging and understanding test failures.
- Review implementation approaches and project documentation.

## AI-Suggested Material and Changes

ChatGPT provided explanations, implementation suggestions, code examples, and troubleshooting guidance during development.

The suggestions were reviewed against the challenge requirements and the existing starter project. I adapted the implementation where necessary rather than using suggestions without review.

During development, some suggested approaches and API request formats required correction or adjustment. These were identified through compilation, testing, and local API execution and were corrected before finalizing the implementation.

## Verification

The final implementation was verified by:

- Running `mvnw.cmd clean test`
- Running the Spring Boot application locally
- Testing the REST APIs
- Testing validation and exception scenarios
- Testing duplicate Transaction ID handling
- Testing transaction-not-found handling
- Testing status updates
- Testing customer transaction retrieval

Final automated test result:

Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS