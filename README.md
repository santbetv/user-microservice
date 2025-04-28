# java-user-microservice-backend

![Java Version](https://img.shields.io/badge/Java-11-blue)
![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-2.5.14-green)
![Maven Version](https://img.shields.io/badge/Gradle-7.1.1-blue)

The project is a code generator using CLI manual:

```bash
# Creando el proyecto
mkdir user-microservice
cd user-microservice
gradle init --type java-application

# Editado el build.gradle

plugins {
    id 'org.springframework.boot' version '2.5.14'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
}

group = 'com.globallogic'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'com.auth0:java-jwt:4.3.0'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springdoc:springdoc-openapi-ui:1.5.12'
    runtimeOnly 'com.h2database:h2'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    testImplementation('org.springframework.boot:spring-boot-starter-test')
    testImplementation 'org.junit.jupiter:junit-jupiter:5.7.2'
}

test {
    useJUnitPlatform()
}


# creando estrutura de carpetas

# Código fuente principal
mkdir -p src/main/java/com/globallogic/usermicroservice
mkdir -p src/main/resources

# Código de pruebas
mkdir -p src/test/java/com/globallogic/usermicroservice
mkdir -p src/test/resources

# Archivos iniciales
touch src/main/java/com/globallogic/usermicroservice/UserMicroserviceApplication.java
touch src/main/resources/application.properties
touch src/test/java/com/globallogic/usermicroservice/UserMicroserviceApplicationTests.java

```

### Diagrama-de-componentes

![Architecture1](img/diagrama-de-componentes.png)

### Diagrama-de-secuencia-login

![Architecture2](img/diagrama-de-secuencia-login.png)

### Diagrama-de-secuencia-sign-up

![Architecture3](img/diagrama-de-secuencia-sign-up.png)

### Architecture based on microservices and DDD

```bash
├── main
│   ├── generated
│   ├── java
│   │   └── com
│   │       └── globallogic
│   │           └── usermicroservice
│   │               ├── application
│   │               │   └── service
│   │               │       └── impl
│   │               │           └── UserService.java
│   │               ├── domain
│   │               │   ├── dto
│   │               │   │   ├── PhoneRequestDto.java
│   │               │   │   ├── UserLoginResponseDto.java
│   │               │   │   ├── UserRequestDto.java
│   │               │   │   └── UserResponseDto.java
│   │               │   ├── entity
│   │               │   │   ├── Phone.java
│   │               │   │   └── User.java
│   │               │   └── repository
│   │               │       └── UserRepository.java
│   │               ├── Infrastructure
│   │               │   ├── common
│   │               │   │   ├── configuration
│   │               │   │   │   ├── CorsConfig.java
│   │               │   │   │   ├── JwtUtil.java
│   │               │   │   │   ├── SecurityConfig.java
│   │               │   │   │   └── SwaggerConfig.java
│   │               │   │   └── exception
│   │               │   │       ├── ErrorGeneric.java
│   │               │   │       ├── ErrorResponse.java
│   │               │   │       └── GlobalExceptionHandler.java
│   │               │   └── controller
│   │               │       └── UserController.java
│   │               └── UserMicroserviceApplication.java
│   └── resources
│       └── application.properties
└── test
    ├── java
    │   └── com
    │       └── globallogic
    │           └── usermicroservice
    │               ├── application
    │               │   └── service
    │               │       └── impl
    │               │           └── UserServiceTest.java
    │               ├── Infrastructure
    │               │   └── controller
    │               │       └── UserControllerTest.java
    │               └── UserMicroserviceApplicationTest.java
    └── resources

```

## Route to swagger in localhost

```bash
https://microserviceuser-production.up.railway.app/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
```

## Route to swagger in localhost

```bash
http://localhost:8000/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
```

## Route to h2 in localhost

```bash
http://localhost:8000/h2
```

The architecture of the generated project consists of :

1. `configuration` contains all the external library implementations to be configured inside spring-boot.
2. `controller` contains the communication interfaces with the client.
3. `entity` contains the persistence domains.
4. `exception` contains the custom-exception classes handled by in the code.
5. `extension` contains the classes that modify system functionality.
6. `helper` contains functionality necessary for general operation.
7. `dto` contains classes that separate in-memory objects from the database.
8. `repository` contains the classes or components that encapsulate the logic necessary to access the data sources.
9. `service` contains the interfaces and implementations that define the functionality provided by the service.

## Requirements

To run the application artifact you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Gradle 7.1.1](https://docs.gradle.org/7.1.1/userguide/userguide.html)

# Download

### GitHub access to  [microservice-user](https://github.com/santbetv/user-microservice)

## Data dump

```bash
  
# Flow complete
{
  "name": "Juan Perez",
  "email": "juanperez@example.com",
  "password": "Passw0rd1",
  "phones": [
    {
      "number": 1234567890,
      "citycode": 1,
      "contrycode": "57"
    }
  ]
}

# Flow successful
{
  "id": "f84d02ad-8031-4f67-83d0-b58e2e205950",
  "created": "2025-04-28T04:26:26.389934",
  "lastLogin": "2025-04-28T04:26:26.389955",
  "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFucGVyZXpAZXhhbXBsZS5jb20iLCJpc3MiOiJhcGkiLCJleHAiOjE3NDU4MzMyODYsImlhdCI6MTc0NTgzMjM4Nn0.NADxYh1tigZ_iV5OCsoKW7pUdFPhnmhdU3utL8tRxMI",
  "active": true
}

# Request JSON inválido (password mala)

{
  "name": "Pedro Ramirez",
  "email": "pedro@example.com",
  "password": "password", 
  "phones": [
    {
      "number": 987654321,
      "citycode": 2,
      "contrycode": "57"
    }
  ]
}

# Flow error
{
  "error": [
    {
      "timestamp": "2025-04-28T04:25:55.972378",
      "codigo": 400,
      "detail": "La contraseña debe tener 1 mayúscula, exactamente 2 números, y entre 8 y 12 caracteres."
    }
  ]
}

#Request JSON inválido (email malo)

{
  "name": "Maria Lopez",
  "email": "maria-lopez.com", 
  "password": "Asdf1234",
  "phones": [
    {
      "number": 5555555,
      "citycode": 3,
      "contrycode": "57"
    }
  ]
}

# Flow error
{
  "error": [
    {
      "timestamp": "2025-04-28T04:25:23.51741",
      "codigo": 400,
      "detail": "Formato de correo inválido. Debe ser aaaa@dominio.com"
    }
  ]
}
 ``` 

## Getting started for console

Once you have the .jar artifact, you just have to run `java -jar <path of your jar file> <command>`
or command line `./gradlew bootRun` to run the project.

### consumer yml file

### Contract

```yaml
{
  "openapi": "3.0.1",
  "info": {
    "title": "API User Management",
    "description": "Microservicio para registro de usuarios y autenticación con JWT.",
    "contact": {
      "name": "Equipo de Desarrollo",
      "email": "devs@empresa.com"
    },
    "version": "1.0"
  },
  "servers": [
    {
      "url": "http://localhost:8000",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/api/auth/sign-up": {
      "post": {
        "tags": [
          "user-controller"
        ],
        "summary": "Registrar un nuevo usuario",
        "description": "Crea un usuario nuevo",
        "operationId": "signUp",
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "$ref": "#/components/schemas/UserRequestDto"
              }
            }
          },
          "required": true
        },
        "responses": {
          "201": {
            "description": "Usuario creado exitosamente",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserResponseDto"
                }
              }
            }
          },
          "400": {
            "description": "Datos de entrada inválidos",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserResponseDto"
                }
              }
            }
          },
          "409": {
            "description": "El correo ya existe en el sistema",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserResponseDto"
                }
              }
            }
          }
        }
      }
    },
    "/api/auth/login": {
      "post": {
        "tags": [
          "user-controller"
        ],
        "summary": "Login de usuario",
        "description": "Valida el token JWT del usuario",
        "operationId": "login",
        "parameters": [
          {
            "name": "Authorization",
            "in": "header",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "Login exitoso",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserLoginResponseDto"
                }
              }
            }
          },
          "401": {
            "description": "Token inválido o expirado",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/UserLoginResponseDto"
                }
              }
            }
          }
        },
        "security": [
          {
            "bearerAuth": [ ]
          }
        ]
      }
    }
  },
  "components": {
    "schemas": {
      "PhoneRequestDto": {
        "type": "object",
        "properties": {
          "id": {
            "type": "string",
            "format": "uuid"
          },
          "number": {
            "type": "string"
          },
          "citycode": {
            "type": "string"
          },
          "contrycode": {
            "type": "string"
          }
        }
      },
      "UserRequestDto": {
        "required": [
          "email",
          "password"
        ],
        "type": "object",
        "properties": {
          "name": {
            "type": "string"
          },
          "email": {
            "type": "string"
          },
          "password": {
            "pattern": "^(?=.*[A-Z])(?=(?:.*\\d.*\\d)[^\\d]*$)[a-zA-Z\\d]{8,12}$",
            "type": "string"
          },
          "phones": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/PhoneRequestDto"
            }
          }
        }
      },
      "UserResponseDto": {
        "type": "object",
        "properties": {
          "id": {
            "type": "string",
            "format": "uuid"
          },
          "created": {
            "type": "string",
            "format": "date-time"
          },
          "lastLogin": {
            "type": "string",
            "format": "date-time"
          },
          "token": {
            "type": "string"
          },
          "active": {
            "type": "boolean"
          }
        }
      },
      "UserLoginResponseDto": {
        "type": "object",
        "properties": {
          "id": {
            "type": "string",
            "format": "uuid"
          },
          "created": {
            "type": "string",
            "format": "date-time"
          },
          "lastLogin": {
            "type": "string",
            "format": "date-time"
          },
          "token": {
            "type": "string"
          },
          "name": {
            "type": "string"
          },
          "email": {
            "type": "string"
          },
          "password": {
            "type": "string"
          },
          "phones": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/PhoneRequestDto"
            }
          },
          "active": {
            "type": "boolean"
          }
        }
      }
    },
    "securitySchemes": {
      "bearerAuth": {
        "type": "http",
        "scheme": "bearer",
        "bearerFormat": "JWT"
      }
    }
  }
}
```