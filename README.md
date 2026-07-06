# TasksAPI

REST API do zarządzania zadaniami stworzone w Spring Boot.

## Technologie

- Java 21
- Spring Boot
- Spring Security
- Hibernate
- MySQL
- JUnit 5
- Mockito
- Gradle

## Funkcjonalności

- Rejestracja użytkownika
- Logowanie
- JWT Authentication
- CRUD zadań
- Walidacja danych
- Obsługa wyjątków

## Architektura

Controller
↓
Service
↓
Repository
↓
Database

## Struktura

src/main/java/com.crud.tasks

config/
controller/
domain/
mapper/
repository/
scheduler/
service/
trello/

## Pokrycie Testów

>AdminConfigTest
> GlobalHttpErrorHandlerTest
> TaskControllerTest
> TrelloControllerTest
> TaskMapperTest
> EmailSchedulerTest
> DbServiceTest
> SimpleEmailServiceTest
> TrelloServiceTest
> TrelloClientTest
> TrelloMapperTest
> TrelloFacadeTest

## Uruchomienie

1. Clone
2. Konfiguracja application.properties
3. Uruchom MySQL
4. ./gradlew bootRun

## Dokumentacja API

Swagger:
http://localhost:8080/swagger-ui/index.html

<img width="1442" height="513" alt="image" src="https://github.com/user-attachments/assets/93f2e530-3685-42d4-a4e0-e6c8a7144829" />

<img width="1436" height="637" alt="image" src="https://github.com/user-attachments/assets/b6d9b59e-aa82-4e44-8778-270259bd4a09" />
