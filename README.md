# Dual Database Spring Boot Demo

This project is a Spring Boot 3 application that connects to two separate Microsoft SQL Server databases in the same service. It exposes REST endpoints for reading and writing records in each database independently, and one combined endpoint that returns data from both sources.

## Tech Stack

- Java 17
- Spring Boot 3.5.15
- Spring Web
- Spring Data JPA
- Microsoft SQL Server JDBC Driver
- Maven

## Project Purpose

The application demonstrates how to:

- configure multiple `DataSource` beans in one Spring Boot project
- create separate JPA configurations for each database
- bind repositories to the correct entity manager and transaction manager
- expose REST APIs for both databases from a single application

## Architecture Overview

The project contains two independent persistence flows.

### Database 1 Flow

- Configuration: `MyConfig`
- Entity: `MyEntity`
- Repository: `MyRepo`
- Service: `MyService`

### Database 2 Flow

- Configuration: `MyConfigT`
- Entity: `MyEntityT`
- Repository: `MyRepoT`
- Service: `MyServiceT`

### Request Flow

1. A request reaches `MyController`.
2. The controller delegates to either `MyService` or `MyServiceT`.
3. The service calls its matching JPA repository.
4. The repository uses the configured entity manager for that database.
5. The entity manager works through the matching data source and transaction manager.
6. The response is wrapped in `ResponseData` and returned as JSON.

## Why Multiple JPA Beans Are Defined

Because the application talks to two databases, Spring Boot cannot rely on a single default JPA setup.

Each database needs its own:

- `DataSource`: database connection details such as URL, username, password, and driver
- `EntityManagerFactory`: JPA/Hibernate setup for a specific set of entities and one data source
- `PlatformTransactionManager`: transaction handling for repository operations on that database

The repository configuration explicitly binds each repository to the correct entity manager and transaction manager so that DB1 repositories do not execute against DB2, and vice versa.

## Configuration

Database properties are defined in `src/main/resources/application.properties` using these prefixes:

- `spring.datasource.db1.*`
- `spring.datasource.db2.*`

Global JPA settings are also defined there, including:

- SQL Server dialect
- schema update mode
- SQL logging

## Important Security Note

The current `application.properties` contains live database connection settings. For local development or shared repositories, move sensitive values such as database passwords out of source control and provide them through environment variables, external configuration, or a local profile-specific properties file.

## API Endpoints

Base path:

```text
/api/twoDBFetch
```

### Database 1

- `GET /api/twoDBFetch/db1` - fetch all DB1 records
- `GET /api/twoDBFetch/db1/{id}` - fetch one DB1 record by id
- `POST /api/twoDBFetch/db1` - insert a DB1 record

Example request body:

```json
{
  "userName": "Alice",
  "accountNumber": "1234567890"
}
```

### Database 2

- `GET /api/twoDBFetch/db2` - fetch all DB2 records
- `GET /api/twoDBFetch/db2/{id}` - fetch one DB2 record by id
- `POST /api/twoDBFetch/db2` - insert a DB2 record

Example request body:

```json
{
  "mobileNumber": "9999999999",
  "userName": "Bob"
}
```

### Combined

- `GET /api/twoDBFetch/all` - fetch records from both databases in one response

## Build and Run

### Run with Maven Wrapper

On Windows:

```powershell
./mvnw.cmd spring-boot:run
```
The project is packaged as a WAR file.

## Main Source Files

- `src/main/java/com/example/db/DbApplication.java`
- `src/main/java/com/example/db/controller/MyController.java`
- `src/main/java/com/example/db/configuration/MyConfig.java`
- `src/main/java/com/example/db/configuration/MyConfigT.java`
- `src/main/resources/application.properties`

## Notes

- `hibernate.hbm2ddl.auto=update` allows Hibernate to update tables automatically. This is convenient for demos but should be reviewed carefully before production use.
- The `/all` endpoint performs two separate reads. It is not a distributed transaction across both databases.