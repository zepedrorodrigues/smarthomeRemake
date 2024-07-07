# US031 - Data Persistence using JPA.

### Table of Contents

1. [Requirements](#1-requirements)
2. [Design](#2-design)
  - [Architecture](#architecture)
  - [Class Diagram](#class-diagram)
  - [Applied Design Patterns and Principles](#applied-design-patterns-and-principles)
3. [Implementation](#3-implementation)
  - [Repository](#repository)
  - [Data Model](#data-model)
4. [Conclusion](#4-conclusion)

## 1. Requirements

_As Product Owner, I want the system to support data persistence using JPA._

Requirements for JPA:

- The system shall support data persistence using JPA.
- The system must have a repository layer to interact with the database.
- The system shall support the creation of a new entity.
- The system shall support the retrieval of an entity by its ID.
- The system shall support the retrieval of all entities.
- The system shall support the update of an entity if needed.
- The system shall support the retrieval of all entities by a specific attribute.

Although it is generally considered an anti-pattern to use JPA for storing only a single instance of an entity, in this
specific case, we have chosen to persist the 'House' entity uniquely given the emphasis and importance this class holds
in the business logic and requirements of the present project.
This decision was made to ensure that the system adheres to the business rule that stipulates only one 'House' per
system.
This approach simplifies the management of data related to the 'House', such as its location and ensures data integrity
by preventing any duplicities or inconsistencies that might arise from multiple instances. Therefore, the use of this
anti-pattern was a deliberate choice to align the system design with the strategic needs of the business, providing
an effective and tailored solution for the current context.

## 2. Design

### Architecture

JPA (Java Persistence API) is a Java specification for managing relational data in applications.
It provides a standard way to map Java objects to database tables and vice versa.
JPA is part of the Java platform and is commonly used in applications to interact with databases.

In our project, we use JPA to manage the persistence of entities in the system.
JPA provides an object-relational mapping (ORM) framework that simplifies the interaction between Java objects and
relational databases.
It allows developers to define entity classes that represent database tables and use annotations to map the fields
of the entity to the columns of the table.

The JPA architecture consists of the following components:

- **Entity**: An entity is a Java class that represents a database table. It is annotated with the `@Entity`
  annotation to indicate that it is a persistent entity. Each entity class must have a primary key field annotated
  with the `@Id` annotation.
- **Id**: The `@Id` annotation is used to mark the primary key field of an entity. It indicates that the field is the
  unique identifier of the entity.
- **EntityManager**: The EntityManager is the interface used to interact with the persistence context. It is
  responsible for managing the lifecycle of entities, including persisting, merging, removing, and querying entities.
- **EntityManagerFactory**: The EntityManagerFactory is a factory class that creates EntityManager instances. It is
  created using the Persistence Unit configuration and is used to create and manage EntityManager instances.
- **Transaction**: A transaction is a unit of work that is performed on the database. It consists of one or more
  database operations that are executed atomically. Transactions ensure data consistency and integrity by committing
  changes to the database only if all operations are successful.
- **Commit**: A commit is the process of persisting changes to the database. It marks the end of a transaction and
  saves the changes made to the entities in the database.
- **Persist**: The persist operation is used to save a new entity to the database. It adds the entity to the persistence
  context and schedules it for insertion into the database.
- **Merge**: The merge operation is used to update an entity in the database. It copies the state of the detached
  entity to the managed entity and persists the changes to the database.
- **JPQL (Java Persistence Query Language)**: JPQL is a query language that is used to perform database queries on
  entities. It is similar to SQL but operates on entity objects rather than database tables.

### Class Diagram

![ClassDiagramRepository_v0.png](../../systemDocumentation/c4ImplementationView/level4CodeDiagram/domain/repository/ClassDiagramRepository_v0.png)

### Applied Design Patterns and Principles

* **Repository Pattern**: The Repository pattern is a design pattern that separates the logic that retrieves data from
  the underlying data store from the business logic that acts on the data. It provides a way to access data without
  exposing the details of the data store implementation. In this user story, we implemented the Repository pattern to
  interact with the database using JPA. Each repository class is responsible for managing the persistence of a specific
  entity type.


* **Data Model Pattern**: The Data Model pattern is a design pattern that separates the domain model from the data
  model.
  It provides a way to represent entities in a format that can be stored in a database. In our project, we use Data
  Model
  objects to represent entities in a format that can be stored in the database. These Data Model objects contain all the
  information necessary for the entity to be persisted in the database. We use Data Model objects to map domain objects
  to Data Model objects that represent entities.


* **Data Mapper Pattern**: The Data Mapper pattern is a design pattern that separates the domain model from the
  persistence layer. It provides a way to map data between the domain model and the database schema. In our project,
  we use the Data Mapper pattern to map entities to data models that represent the database tables. The data mapper
  classes are responsible for converting entities to data models and vice versa. This separation allows us to keep
  the domain model clean and decoupled from the database schema.

## 3. Implementation

### Repository

All of the repositoriesJPA class implement the generic `IRepository` interface (which in turn extends
Java's `Repository`
interface). This interface defines the shared methods that all repositoriesJPA classes must implement. The shared
methods include:

- `save`: This method is used to save a new entity to the database. It takes an entity as a parameter and returns the
  saved entity.
- `findAll`: This method is used to retrieve all entities of a specific type from the database. It returns an iterable
  collection of entities.
- `getByIdentity`: This method is used to retrieve an entity by its identity. It takes an identity object as a parameter
  and returns an optional entity.
- `containsIdentity`: This method is used to check if an entity with a specific identity exists in the database. It
  takes
  an identity object as a parameter and returns a boolean value indicating whether the entity exists.

Below is the relevant code snippet for the generic repository class (in this case, the `RoomRepositoryJPAImpl` class)
with the implementation of the shared methods:

```java

public class RoomRepositoryJPAImpl implements IRoomRepository {
    // Attributes
    
    public RoomRepositoryJPAImpl(RoomDataModelMapper roomDataModelMapper) {
        // Implementation of the constructor
    }

    public Room save(Room room) {
        // Implementation of the method
    }

    public Iterable<Room> findAll() {
        // Implementation of the method
    }

    public Optional<Room> getByIdentity(RoomId roomId) {
        // Implementation of the method
    }

    public boolean containsIdentity(RoomId roomId) {
        // Implementation of the method
    }
}
```

Some of the repositoriesJPA classes have specific methods that are not part of the `Repository` interface. These only
apply to the specific entity they are managing.
Below are the specific methods for the:

- `ActuatorModelRepositoryJPAImpl` class:

```java
public class ActuatorModelRepositoryJPAImpl implements IActuatorModelRepository {

    public Iterable<ActuatorModel> findByActuatorTypeIdentity(ActuatorTypeName actuatorTypeName) {
        // Implementation of the method
    }
}
```

- `ActuatorRepositoryJPAImpl` class:

```java
public class ActuatorRepositoryJPAImpl implements IActuatorRepository {

    public Iterable<Actuator> findByDeviceIdentity(DeviceId deviceId) {
        // Implementation of the method
    }
}
```

- `SensorModelRepositoryJPAImpl` class:

```java
public class SensorModelRepositoryJPAImpl implements ISensorModelRepository {

    public Iterable<SensorModel> findBySensorTypeIdentity(SensorTypeId sensorTypeId) {
        // Implementation of the method
    }
}
```

- `SensorRepositoryJPAImpl` class:

```java
public class SensorRepositoryJPAImpl implements ISensorRepository {

    public Iterable<Sensor> getByDeviceIdentity(DeviceId deviceId) {
        // Implementation of the method
    }

    public Iterable<Sensor> getByDeviceIdentityAndSensorModel(DeviceId deviceId, SensorModelName sensorModelName) {
        // Implementation of the method
    }
}
```

- `DeviceRepositoryJPAImpl` class:

```java
public class DeviceRepositoryJPAImpl implements IDeviceRepository {

    public Iterable<Device> findByRoomIdentity(RoomId roomId) {
        // Implementation of the method
    }

    public Device update(Device device) {
        // Implementation of the method
    }
}
```

- `HouseRepositoryJPAImpl` class:

```java
public class HouseRepositoryJPAImpl implements IHouseRepository {

    public House update(House house) {
        // Implementation of the method
    }
}
```

- `ReadingRepositoryJPAImpl` class:

```java
public class ReadingRepositoryJPAImpl implements IReadingRepository {

    public Iterable<Reading> findBySensorIdInAGivenPeriod(SensorId sensorId, LocalDateTime start, LocalDateTime end) {
        // Implementation of the method
    }
}
```

For more information on the implementation of the JPA repositories, please refer to the following classes:

[ActuatorModelRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/ActuatorModelRepositoryJPAImpl.java)

[ActuatorRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/ActuatorRepositoryJPAImpl.java)

[ActuatorTypeRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/ActuatorTypeRepositoryJPAImpl.java)

[DeviceRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/DeviceRepositoryJPAImpl.java)

[HouseRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/HouseRepositoryJPAImpl.java)

[ReadingRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/ReadingRepositoryJPAImpl.java)

[RoomRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/RoomRepositoryJPAImpl.java)

[SensorModelRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/SensorModelRepositoryJPAImpl.java)

[SensorRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/SensorRepositoryJPAImpl.java)

[SensorTypeRepositoryJPAImpl](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/jpa/SensorTypeRepositoryJPAImpl.java)

### Data Model

A data model is a conceptual representation of the structure and organization of data within the system. It defines the
relationships between different types of data, the rules for how data can be stored and manipulated, and the constraints
that govern the data.

Data models are crucial for JPA repositories as they serve as the foundation for managing and persisting data in a
relational database using Java objects.

Below is an example of a `DataModel` class (in this case, the `SensorDataModel` class) where the entities and
relationships within the database are defined . the data model.

```java

public class SensorDataModel {

    @Id
    private String sensorId;
    private String deviceId;
    private String sensorModelName;

    public SensorDataModel() {
      // Implementation of the constructor
    }

  public SensorDataModel(Sensor sensor) {
    // Implementation of the constructor
    }

    public String getSensorId() {
        // Implementation of the method
    }

    public String getDeviceId() {
        // Implementation of the method
    }

    public String getSensorModelName() {
      // Implementation of the method
    }

}
```

For more information on the implementation of the Data Models, please refer to the following classes:

[ActuatorDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/ActuatorDataModel.java)

[ActuatorModelDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/ActuatorModelDataModel.java)

[ActuatorTypeDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/ActuatorTypeDataModel.java)

[DeviceDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/DeviceDataModel.java)

[HouseDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/HouseDataModel.java)

[ReadingDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/ReadingDataModel.java)

[RoomDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/RoomDataModel.java)

[SensorDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/SensorDataModel.java)

[SensorModelDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/SensorModelDataModel.java)

[SensorTypeDataModel](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/persistence/datamodel/SensorTypeDataModel.java)


## 4. Conclusion

The implementation of JPA has enhanced our system's data management capabilities by providing a framework for
handling complex data structures and relationships. Through the use of the Repository
pattern, our application's communication with the database has become more efficient, reducing our need on direct
SQL manipulation. The implementation of JPA has also enabled us to perform complex database queries using JPQL,
providing a powerful tool for retrieving and manipulating data. The use of Data Models has allowed us to define the
structure and relationships of our data in a clear and concise manner, facilitating the interaction between Java objects
and the database.

We made a strategic decision to uniquely persist the 'House' entity to comply with specific
business rules, ensuring data integrity by preventing duplication.
The rest of the classes rely on the generic repository class to manage their persistence, simplifying the data
management process and ensuring consistency across the system. Working towards the dependence on abstractions rather
than concretions, the dependency injection of interfaces classes has allowed us to decouple the repository classes from
the rest of the system, making it easier to test and maintain.
This approach has led to a more organized, flexible and easily extensible data
management system that meets our operational requirements and can easily accommodate future changes and expansions.

[Back to top](#us031---data-persistence-using-jpa)
