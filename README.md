# Ticket_IGNITE

Proof-of-concept ticketing service that uses Apache Ignite as a
distributed data store.

## Tech stack

- Java 17
- Spring Boot 2.7.18
- Apache Ignite 2.15
- REST + JSON
- HTTP Basic authentication
- Maven for build
- JUnit 5 for tests

## Project structure

```text
ticketing-ignite/
├── pom.xml
└── src
    ├── main/java/com/example/ticketing
    │   ├── TicketingApplication.java
    │   ├── config/
    │   ├── model/
    │   ├── repository/
    │   ├── service/
    │   └── web/
    └── test/java/com/example/ticketing
        ├── service/
        └── web/
```

## Prerequisites

- Java 17+
- Maven 3+

## Running the application

1. Start the Spring Boot application, which will also start an embedded
   Ignite node:

   ```bash
   mvn spring-boot:run -Dspring-boot.run.jvmArguments="\
   --add-exports=java.base/jdk.internal.misc=ALL-UNNAMED \
   --add-opens=java.base/java.nio=ALL-UNNAMED \
   --add-opens=java.base/sun.nio.ch=ALL-UNNAMED \
   --add-opens=java.base/java.lang=ALL-UNNAMED \
   --add-opens=java.base/java.lang.invoke=ALL-UNNAMED \
   --add-opens=java.base/java.util=ALL-UNNAMED \
   --add-opens=java.base/java.util.concurrent=ALL-UNNAMED \
   --add-opens=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED \
   --add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED \
   -Djava.net.preferIPv4Stack=true \
   -Xms512m -Xmx512m"
   ```

2. Sample data for one demo event with two seats is loaded at startup. A default admin user (`admin` / `admin`) is created. Register a regular account before accessing user endpoints:

   ```bash
   curl -H "Content-Type: application/json" -d '{"username":"user","password":"password"}' http://localhost:8080/register
   ```

   Reserve a seat:

   ```bash
   curl -u user:password -X POST "http://localhost:8080/events/1/seats/1/reserve"
   ```

   List your tickets:

   ```bash
   curl -u user:password "http://localhost:8080/me/tickets"
   ```

   Cancel a ticket:

   ```bash
   curl -u user:password -X DELETE "http://localhost:8080/me/tickets/{ticketId}"
   ```

   On success, the service returns a JSON representation of the seat or
   ticket. Invalid operations yield an error response.

   Admin endpoints allow creating events and managing seats:

   ```bash
   curl -u admin:admin -H "Content-Type: application/json" \
     -d '{"name":"Concert","seatCount":5}' \
     -X POST "http://localhost:8080/admin/events"

   curl -u admin:admin "http://localhost:8080/admin/events/1/seats"

   curl -u admin:admin -H "Content-Type: application/json" \
     -d '{"reserved":false}' -X PUT "http://localhost:8080/admin/seats/{seatId}"
   ```
   
3. A simple web page is available for manual testing without prior authentication:


   ```
   http://localhost:8080/index.html
   ```

   Log in using the `admin/admin` account or a registered user. After login the
   page reveals user features for checking seats, reserving, listing and
   cancelling tickets. Admins receive additional forms for creating events and
   managing seats.


## Running tests

```bash
mvn test
```

The tests cover the reservation service and the REST controller.
