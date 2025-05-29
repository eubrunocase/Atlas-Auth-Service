# Atlas Auth Service

## Visão Geral

O Atlas Auth Service é um microserviço responsável por gerenciar autenticação e autorização de usuários na plataforma Atlas. Ele expõe endpoints para registro, login, refresh de token e consulta de perfis, além de emitir e validar JSON Web Tokens (JWT).

## Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.4.5**
* **Spring Cloud 2024.0.1**
* **Spring Cloud Netflix Eureka Client**
* **Spring Security (Core, Crypto, JWT)**
* **Spring Data JPA**
* **Banco de Dados H2 (default) ou PostgreSQL**
* **Lombok**
* **SpringDoc OpenAPI (Swagger UI)**
* **Maven Wrapper**
* **Docker & Docker Compose**

## Pré-requisitos

* **Java 21+**
* **Maven 3.8+**
* **Docker & Docker Compose** (opcional)
* **Servidor Eureka** em execução (ex.: `http://localhost:8761`)

## Instalação e Execução

### Executando localmente

1. Clone o repositório:

   ```bash
   git clone <URL_DO_REPO>
   cd Atlas-Auth-Service
   ```
2. Configure variáveis de ambiente (opcionais):

   ```bash
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/authdb
   export SPRING_DATASOURCE_USERNAME=usuario
   export SPRING_DATASOURCE_PASSWORD=senha
   export JWT_SECRET="SUA_CHAVE_SECRETA_AQUI"
   export EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://localhost:8761/eureka
   ```
3. Build e execução:

   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

### Usando Docker

1. Build da imagem:

   ```bash
   docker build -t atlas-auth-service .
   ```
2. Executar o container:

   ```bash
   docker run -d --name atlas-auth-service \
     -p 8081:8081 \
     -e SPRING_DATASOURCE_URL=jdbc:h2:mem:authdb \
     -e JWT_SECRET="SUA_CHAVE_SECRETA_AQUI" \
     -e EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://host.docker.internal:8761/eureka \
     atlas-auth-service
   ```

## Estrutura de Pastas

```text
Atlas-Auth-Service/
├── src/
│   ├── main/
│   │   ├── java/com/atlas/auth
│   │   │   ├── AtlasAuthServiceApplication.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   └── AuthController.java
│   │   │   ├── model/
│   │   │   │   └── User.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java
│   │   │   └── service/
│   │   │       └── AuthService.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/com/atlas/auth/AtlasAuthServiceTests.java
├── Dockerfile
├── pom.xml
├── mvnw, mvnw.cmd
└── HELP.md
```

## Configuração (application.yml)

```yaml
server:
  port: 8081

spring:
  application:
    name: atlas-auth-service
  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:h2:mem:authdb}
    username: ${SPRING_DATASOURCE_USERNAME:sa}
    password: ${SPRING_DATASOURCE_PASSWORD:}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: "${EUREKA_CLIENT_SERVICEURL_DEFAULTZONE:http://localhost:8761/eureka}"

security:
  jwt:
    secret: "${JWT_SECRET:MinhaChaveSecreta}"
    expiration-ms: 3600000
```

## Endpoints Principais

| Método | URL                      | Descrição                             |
| ------ | ------------------------ | ------------------------------------- |
| POST   | `/atlas/auth/register`   | Registra novo usuário                 |
| POST   | `/atlas/auth/login`      | Autentica e retorna JWT               |
| GET    | `/atlas/auth/users/{id}` | Busca informações de um usuário (JWT) |

## Documentação da API

A documentação interativa está disponível em:

* `http://localhost:8081/swagger-ui.html`
* `http://localhost:8081/swagger-ui/index.html`


