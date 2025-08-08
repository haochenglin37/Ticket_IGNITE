# Ticket_IGNITE

Proof-of-concept ticketing service that uses Apache Ignite as a
distributed data store.

## Prerequisites

- Java 17+
- Maven 3+

## Running the application

1. Start the Spring Boot application, which will also start an embedded
   Ignite node:

   ```bash
   mvn spring-boot:run
   ```

2. Reserve a seat by calling the REST endpoint. Example using `curl`:

   ```bash
   curl -X POST \
        http://localhost:8080/events/1/seats/1/reserve
   ```

   On success, the service returns a JSON representation of the created
   ticket. Attempting to reserve an already-reserved seat or specifying a
   wrong `eventId` yields an error response.

## Running tests

```bash
mvn test
```

The tests cover the reservation service and the REST controller.
