# US035: REST API Implementation using Spring Boot and HATEOAS

### Table of Contents

1. [Introduction](#introduction)
2. [Requirements](#requirements)
3. [REST API and HATEOAS](#rest-api-and-hateoas)
4. [Spring Boot](#spring-boot)
5. [Implementation Details](#implementation-details)
    - [Controller Layer](#controller-layer)
    - [Service Layer](#service-layer)
    - [Repository Layer](#repository-layer)
    - [DTO Layer](#dto-layer)
    - [HATEOAS Integration](#hateoas-integration)
6. [Conclusion](#conclusion)

## Introduction

This technical report provides an explanation of how a REST API can be implemented using Spring Boot, with the addition
of HATEOAS (Hypermedia as the Engine of Application State). This report guides through the theoretical and practical
aspects of developing RESTful controllers.

## Requirements

The user story for this report is as follows:

- _**As a Product Owner, I want the project to have a REST API (HTTP REST controllers, implemented using Spring Boot,
  including HATEOAS).**_

This involves creating a RESTful API that will be augmented with HATEOAS for enhanced hypermedia-driven navigation
between related resources.

**Objectives:**

- Designing REST endpoints with REST controllers.
- Implementing proper data handling via services and repositories.
- Integrating HATEOAS to ensure discoverability of related resources.
- Adapting the project to run on Spring Boot framework.

**Technical Stack Review:**

- **Programming Language:** Java
- **Framework:** Spring Boot
- **Data Persistence:** Spring Data JPA
- **Hypermedia Layer:** Spring HATEOAS
- **Build Tool:** Maven

## REST API and HATEOAS

**REST (Representational State Transfer):** An architectural style that leverages HTTP methods to create web services
following a stateless, client-server model. RESTful services expose CRUD operations via:

- `GET`: Retrieve a resource
- `POST`: Create a new resource
- `PUT`: Update an existing resource
- `PATCH`: Partially update a resource
- `DELETE`: Remove a resource
- `HEAD`: Retrieve metadata of a resource
- `OPTIONS`: Retrieve supported methods for a resource
- `TRACE`: Echo back the request

**HATEOAS (Hypermedia as the Engine of Application State):** A constraint of REST architecture that promotes embedding
hypermedia links into the API response, allowing clients to dynamically navigate resources without prior knowledge of
specific URLs.

## Spring Boot

**Spring Boot:** A framework that simplifies the development of Spring-based applications by providing pre-configured
defaults. Implementing Spring Boot in the project allows developers to focus on business logic rather than
configuration. It enables developers to quickly set up, configure, and run production-grade applications without the
need for extensive configuration.

- **Rapid Development:** Reduces boilerplate code and configuration, enabling faster development.
- **Auto-Configuration:** Automatically configures Spring components based on dependencies included in the project.
- **Embedded Servers:** Allows applications to run with embedded servers like Tomcat.
- **Spring Initializr:** A web-based tool to quickly generate project structures with predefined templates.
- **Production-Ready Metrics:** Provides metrics, health checks, and externalized configuration via Spring Actuator.

## Implementation Details

### Controller Layer

The controller layer is responsible for handling incoming HTTP requests and returning appropriate responses.

* `@RestController` annotation defines controllers for handling incoming HTTP requests.
* `@RequestMapping` specifies the base URL for the controller.

**Example:**

```java

@RestController
@RequestMapping("/actuators")
public class ActuatorRESTController {
    // Controller methods and attributes
}
```

* `@RequestBody` binds the request body to a method parameter (e.g., a DTO object).

**Example:**

```java

@PostMapping
public ResponseEntity<ActuatorDTO> createActuator(@RequestBody ActuatorDTO actuatorDTO) {
    // Create actuator logic
}
```

* `@PathVariable` extracts values from the URL path.

**Example:**

```java

@PutMapping("/{id}/deactivate")
public ResponseEntity<DeviceDTO> deactivateDevice(@PathVariable("id") String id) {
    // Deactivate device logic
}
```

* `@RequestParam` retrieves query parameters from the URL.

**Example:**

```java

@GetMapping("/peak-power-consumption")
public ResponseEntity<Double> getPeakPowerConsumptionInAGivenTimePeriod(@RequestParam String sensorModelName, @RequestBody PeriodDTO periodDTO) {
    // Get peak power consumption logic
}
```

* `ResponseEntity` customizes the HTTP response with status codes and headers.

**Example:**

```java

@GetMapping("/{id}")
public ResponseEntity<ActuatorDTO> getActuatorById(@PathVariable("id") String id) {
    // Get actuator by ID logic
}
```

* Annotations like `@PostMapping`, `@GetMapping`, `@PutMapping`, and `@DeleteMapping` define the HTTP methods for
  controller methods.

**Example:**

```java

@GetMapping
public ResponseEntity<List<ActuatorDTO>> getAllActuators() {
    // Get all actuators logic
}

@PostMapping
public ResponseEntity<ActuatorDTO> createActuator(@RequestBody ActuatorDTO actuatorDTO) {
    // Create actuator logic
}

@PutMapping("/{id}")
public ResponseEntity<DeviceDTO> updateDevice(@PathVariable("id") String id, @RequestBody DeviceDTO deviceDTO) {
    // Update device logic
}
```

### Service Layer

The service layer encapsulates business logic and interacts with the repository layer.

* `@Service` annotation marks the class as a service component.

**Example:**

```java

@Service
public class DeviceServiceImpl implements IDeviceService {
    // Service methods
}
```

### Repository Layer

The repository layer manages data persistence and retrieval. Spring Data JPA simplifies database interactions through
repository interfaces.

* `@Repository` annotation marks the class as a repository component.

**Example:**

```java

@Repository
public interface IHouseRepositorySpringData extends JpaRepository<HouseDataModel, String> {
    // Custom query methods
}
```

### DTO Layer

The DTO (Data Transfer Object) layer defines objects for transferring data between layers. DTOs are used to encapsulate
data and reduce coupling between layers. They are often used to map entities to a more user-friendly representation.

In order to use HATEOAS, the DTOs should extend `RepresentationModel<ActuatorDTO>`. This allows the DTOs to contain
links to
related resources.

**Example:**

```java
public class ActuatorDTO extends RepresentationModel<ActuatorDTO> {
    // DTO attributes
}
```

### HATEOAS Integration

HATEOAS can be integrated into the project by adding the `spring-hateoas` dependency to the `pom.xml` file.

```xml

<dependency>
    <groupId>org.springframework.hateoas</groupId>
    <artifactId>spring-hateoas</artifactId>
</dependency>
```

After that, the DTOs should extend `RepresentationModel<ActuatorDTO>` to enable the DTOs to contain links to related
resources.

**Example:**

```java
public class ActuatorDTO extends RepresentationModel<ActuatorDTO> {
    // DTO attributes and methods
}
```

Using those links, the client can navigate between resources using HTTP methods. Here’s how you can add HATEOAS links in
a controller method:

**Example:**

```java

@GetMapping("/{id}")
public ResponseEntity<ReadingDTO> getReading(@PathVariable("id") String id) {
   try {
      ReadingId readingId = new ReadingId(id);
      Optional<Reading> reading = readingService.getReading(readingId);
      if (reading.isPresent()) {
         ReadingDTO readingDTO = readingMapper.toReadingDTO(reading.get())
                 .add(linkTo(methodOn(ReadingRESTController.class)
                         .getReading(id)).withSelfRel());
         return new ResponseEntity<>(readingDTO, HttpStatus.OK);
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }
}
```

In this example, a link to retrieve the reading by ID is added to the `ReadingDTO` object. The client can use this link
to
navigate to the resource.

## Conclusion

Implementing a REST API using Spring Boot and integrating HATEOAS enhances the navigation and discoverability of related
resources. The controller layer handles HTTP requests, the service layer encapsulates business logic, and the repository
layer manages data persistence. By incorporating HATEOAS, the API becomes more intuitive and easier to use, ultimately
delivering a better experience for clients interacting with the API. This approach ensures that the project adheres to
modern RESTful principles and provides a robust foundation for future enhancements.