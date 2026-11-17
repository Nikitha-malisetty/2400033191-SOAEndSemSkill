# Spring Boot + Docker Compose: Multi-Container Deployment

This project implements the lab task: run an `app-service` Spring Boot application and an `app-db` PostgreSQL database together using Docker Compose. The API endpoint is **GET http://localhost:8080/api/data**.

## Requirements
- Java 21 (only needed if running Spring Boot directly on your computer)
- Maven 3.9+ (only needed for local Maven commands)
- Docker Desktop with Docker Compose (required for the containerized deployment)

## Run the complete multi-container application (recommended)

1. Extract the ZIP file.
2. Open the extracted `docker-compose-springboot-demo` folder in VS Code or a terminal.
3. Start Docker Desktop and wait until it is running.
4. Open Command Prompt or PowerShell in the project folder.
5. Run:

```bash
docker compose up -d --build
```

6. Check that both containers are running:

```bash
docker compose ps
```

7. Test the endpoint in a browser, Postman, or terminal:

```text
http://localhost:8080/api/data
```

Expected JSON (the database rows are returned by PostgreSQL):

```json
[
  {
    "id": 1,
    "name": "Spring Boot",
    "description": "Application service connected to PostgreSQL"
  },
  {
    "id": 2,
    "name": "Docker Compose",
    "description": "Application and database run as separate containers"
  }
]
```

You can also test the health endpoint at `http://localhost:8080/api/health`.

## Useful Docker commands

```bash
docker compose logs -f
docker compose logs app-service
docker compose logs app-db
docker compose down
```

To remove the containers **and the database volume/data** and start from a clean database:

```bash
docker compose down -v
docker compose up -d --build
```

## Run Spring Boot directly (optional)

If you want to run the Spring Boot application from your IDE while PostgreSQL runs in Docker:

1. Start only the database:

```bash
docker compose up -d app-db
```

2. In the project folder, run:

```bash
mvn spring-boot:run
```

3. Open `http://localhost:8080/api/data`.

Stop the local application with Ctrl+C. Stop the database with `docker compose down`.

## How the containers communicate

- Compose creates a shared bridge network named by Compose for `app-network`.
- The application connects to PostgreSQL using the service name `app-db`, not `localhost`.
- Docker's internal DNS resolves `app-db` to the database container.
- `depends_on` with the health check makes Compose wait until PostgreSQL is ready before starting the application.

## Viva answers

**1. Why is Docker Compose used instead of running individual `docker run` commands?**

Docker Compose defines all related services, networks, volumes, ports, environment variables, and dependencies in one YAML file. One command can build and start the complete application stack, making deployment repeatable and easier to manage.

**2. How do containerized services communicate using service names in a shared Docker network?**

Containers attached to the same Docker network can resolve one another through Docker's internal DNS. Here, the Spring Boot container uses `app-db` as the database hostname in `jdbc:postgresql://app-db:5432/demo_db`. It should not use `localhost`, because inside the app container `localhost` refers to the app container itself.
