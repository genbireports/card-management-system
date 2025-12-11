# Credit Card Management System (CCMS)

## Overview
A Spring Boot application managing the lifecycle of consumer credit cards, including product definitions, blocking/freezing logic, and replacement workflows.

## Tech Stack
* **Java 17**
* **Spring Boot 3.2**
* **H2 Database** (In-memory for testing)
* **Maven**

## How to Run
1. `mvn clean install`
2. `mvn spring-boot:run`
3. Access H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)