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
   the REST endpoint to reserve a seat. Example using `curl`:

   ```bash
   curl -X POST \
        "http://localhost:8080/events/1/seats/1/reserve?customer=Alice"
   ```

   On success, the service returns a JSON representation of the created
   ticket. Attempting to reserve an already-reserved seat or specifying a
   wrong `eventId` yields an error response.

3. A simple web page is available for manual testing:

   ```
   http://localhost:8080/index.html
   ```

   Enter the event ID, seat ID and customer name to attempt a reservation.

## Running tests

```bash
mvn test
```

The tests cover the reservation service and the REST controller.
