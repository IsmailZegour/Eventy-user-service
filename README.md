# User Service

User Service est un microservice dédié à la gestion des utilisateurs. Ce service fournit des opérations pour créer, récupérer, mettre à jour et supprimer des utilisateurs.

## Technologies Utilisées

- Java 17+
- Spring Boot 3.3.5
- Spring Data JPA
- PostgreSQL
- Spring Cloud (Eureka Client, OpenFeign)
- Lombok
- MapStruct
- Springdoc OpenAPI (Swagger)

## Prérequis

- Java 17 ou plus
- Maven
- PostgreSQL (configuré comme base de données pour ce service)

## Configuration

1. Configurez la base de données PostgreSQL et mettez à jour le fichier `application.yml` avec vos informations d'accès :

    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/userdb
        username: <your-username>
        password: <your-password>
    ```

2. Assurez-vous que l'URL d'Eureka est correcte dans `application.yml` pour l'enregistrement du service :

    ```yaml
    eureka:
      client:
        service-url:
          defaultZone: http://localhost:8761/eureka
    ```

## Démarrage

1. Cloner le dépôt :

    ```bash
    git clone <repository-url>
    cd user-service
    ```

2. Compiler et démarrer le service :

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Endpoints API

### Obtenir tous les utilisateurs
- **GET** `/users`
- Réponse : Liste des utilisateurs existants

### Obtenir un utilisateur par ID
- **GET** `/users/{id}`
- Réponse : Détails d'un utilisateur spécifique

### Créer un utilisateur
- **POST** `/users`
- Corps de la requête : JSON contenant les détails de l'utilisateur
- Réponse : L'utilisateur créé

### Mettre à jour un utilisateur
- **PUT** `/users/{id}`
- Corps de la requête : JSON contenant les détails mis à jour de l'utilisateur
- Réponse : L'utilisateur mis à jour

### Supprimer un utilisateur
- **DELETE** `/users/{id}`
- Réponse : 204 No Content en cas de succès

## Documentation Swagger

Accédez à la documentation complète de l'API via Swagger :

