# java-user-microservice-backend

![Java Version](https://img.shields.io/badge/Java-11-blue)
![Spring Boot Version](https://img.shields.io/badge/Spring%20Boot-2.5.14-green)
![Maven Version](https://img.shields.io/badge/Gradle-7.1.1-blue)

The project is a code generator using CLI manual:

```bash
mkdir user-microservice
cd user-microservice
gradle init --type java-application

Edita el build.gradle

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
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    runtimeOnly 'com.h2database:h2'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    developmentOnly 'org.springframework.boot:spring-boot-devtools'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
    useJUnitPlatform()
}

creando estrutura de carpetas

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

### Architecture based on microservices and DDD

```bash
├── main
│   ├── java
│   │   └── com
│   │       └── project
│   │           └── segurityjwt
│   │               ├── application
│   │               │   └── controller
│   │               │       ├── AuthController.java
│   │               │       ├── ClientController.java
│   │               │       ├── ProductController.java
│   │               │       └── SaleController.java
│   │               ├── Application.java
│   │               ├── domain
│   │               │   ├── dto
│   │               │   │   ├── AuthenticationRequest.java
│   │               │   │   ├── AuthenticationResponse.java
│   │               │   │   ├── LoginDto.java
│   │               │   │   └── ResponseJwtDataDto.java
│   │               │   ├── entity
│   │               │   │   ├── Role.java
│   │               │   │   └── Usuario.java
│   │               │   ├── repository
│   │               │   │   └── UserRepository.java
│   │               │   └── service
│   │               │       └── UserSecurityService.java
│   │               └── infrastructure
│   │                   └── auth
│   │                       ├── aspect
│   │                       │   ├── AppConfigAspect.java
│   │                       │   ├── SecuredLoggingAspect.java
│   │                       │   └── UnauthorizedAccessAspect.java
│   │                       ├── CorsConfig.java
│   │                       ├── JwtFilter.java
│   │                       ├── JwtUtil.java
│   │                       └── SecurityConfig.java
│   └── resources
│       ├── application.properties
│       ├── static
│       └── templates
└── test
    └── java
        └── com
            └── project
                └── segurityjwt
                    └── ApplicationTests.java

```

##         

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

- [JDK 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven 3.9](https://maven.apache.org)
- Git access
  to [template-springboot-service](https://gitlab.com/prodigio-master/centro-excelencia/desarrollo/java/template-springboot-service)

## Getting started

Once you have the .jar artifact, you just have to run `java -jar <path of your jar file> <command>`

### consumer yml file

### warnings

    1. You just have to modify the `outPath` attribute which is the path where your project will be created
    2. in outPath cannot use backslash (\), just use slash (/), example : C:/user/something

```yaml
service:
    name: "developer-test-service"
    description: "custom project description"
    outPath: "<path/where/project/will/be/created>" # cannot use backslash (\), just use slash (/), example : C:/user/something
    packageName: "com.custom.name"
    version: "v1"
operations:
    -   name: "UserService"
        rootPath: "https://jsonplaceholder.typicode.com"
        headers:
            -   name: "is a global header"
                value: "Some value"
        expose:
            name: "UserController"
            rootPath: "users"
        methods:
            -   name: "getUser"
                type: "GET"
                path: "/users/{0}"
                attributes:
                    -   name: "id"
                        type: "Integer"
                headers:
                    -   name: "is a local header"
                        value: "Some value"
                mapTo:
                    name: "UserData"
                    attributes:
                        -   name: "userId"
                            type: "int"
                            from: "id"
                        -   name: "userEmail"
                            type: "String"
                            from: "email"
                        -   name: "street"
                            type: "String"
                            from: "address.street"
                        -   name: "lat"
                            type: "Double"
                            from: "address.geo.lat"
                        -   name: "lng"
                            type: "Double"
                            from: "address.geo.lng"
                expose:
                    path: ""
                    type: "GET_ONE"
            -   name: "getAllUsers"
                type: "GET"
                path: "/users"
                headers:
                    -   name: "is a local header"
                        value: "Some value"
                mapTo:
                    name: "UserData"
                    many: true
                    attributes:
                        -   name: "userId"
                            type: "int"
                            from: "id"
                        -   name: "userEmail"
                            type: "String"
                            from: "email"
                        -   name: "street"
                            type: "String"
                            from: "address.street"
                        -   name: "lat"
                            type: "Double"
                            from: "address.geo.lat"
                        -   name: "lng"
                            type: "Double"
                            from: "address.geo.lng"
                expose:
                    path: ""
                    type: "GET_ALL"
            -   name: "addUser"
                type: "POST"
                path: "/users"
                headers:
                    -   name: "is a local header"
                        value: "Some value"
                request:
                    name: "AddUserDto"
                    attributes:
                        -   name: "userName"
                            type: "String"
                        -   name: "id"
                            type: "Long"
                mapTo:
                    name: "AuxiliarCreatedUSer"
                    attributes:
                        -   name: "creationId"
                            type: "Long"
                            from: "id"
                expose:
                    path: ""
                    type: "CREATE"
            -   name: "editUser"
                type: "PUT"
                path: "/users/{0}"
                attributes:
                    -   name: "id"
                        type: "Integer"
                headers:
                    -   name: "is a local header"
                        value: "Some value"
                request:
                    name: "EditUserDto"
                    attributes:
                        -   name: "userName"
                            type: "String"
                        -   name: "id"
                            type: "Long"
                mapTo:
                    name: "EditedUserResponse"
                    attributes:
                        -   name: "creationId"
                            type: "Long"
                            from: "id"
                expose:
                    path: ""
                    type: "UPDATE"
    -   name: "WikiService"
        rootPath: "https://hacker-news.firebaseio.com/v0/item/8863.json"
        expose:
            name: "WikiController"
            rootPath: "news"
        methods:
            -   name: "getWikiData"
                type: "GET"
                path: ""
                mapTo:
                    name: "WikiData"
                    attributes:
                        -   name: "createdIn"
                            type: "String"
                            from: "by"
                        -   name: "newsId"
                            type: "Integer"
                            from: "id"
                        -   name: "kidsData"
                            type: "String"
                            many: true
                            from: "kids"
                expose:
                    path: ""
                    type: "GET_ALL"
    -   name: "CatService"
        rootPath: "https://api.artic.edu/api/v1/artworks"
        expose:
            name: "CatController"
            rootPath: "animals"
        methods:
            -   name: "getAnimalData"
                type: "GET"
                path: "/search?q={0}"
                attributes:
                    -   name: "searchTerm"
                        type: "String"
                mapTo:
                    name: "AnimalData"
                    attributes:
                        -   name: "jsonData"
                            type: "String"
                            from: "data"
                            many: true
                            json: true
                        -   name: "total"
                            type: "Integer"
                            from: "pagination.total"
                        -   name: "links"
                            type: "String"
                            many: true
                            from: "info.license_links"
                        -   name: "customConfig"
                            type: "String"
