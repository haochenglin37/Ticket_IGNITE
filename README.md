# Ticket_IGNITE

Proof-of-concept ticketing service that uses Apache Ignite as a
distributed data store.

## Tech stack

- Java 17
- Spring Boot 2.7.18
- Apache Ignite 2.15
- REST + JSON
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

2. Sample data for one demo event with two seats is loaded at startup. Use
   the REST endpoints to manage seats.

   Reserve a seat:

   ```bash
   curl -X POST \
        "http://localhost:8080/events/1/seats/1/reserve?customer=Alice"
   ```

   Check seat status:

   ```bash
   curl "http://localhost:8080/events/1/seats/1"
   ```

   Cancel a reservation:

   ```bash
   curl -X POST "http://localhost:8080/events/1/seats/1/cancel"
   ```

   On success, the service returns a JSON representation of the seat or
   ticket. Invalid operations yield an error response.

3. A simple web page is available for manual testing:

   ```
   http://localhost:8080/index.html
   ```

   Enter the event ID, seat ID and customer name to attempt a reservation.
   Additional forms allow checking seat status and canceling a reservation.

## Running tests

```bash
mvn test
```

The tests cover the reservation service and the REST controller.
