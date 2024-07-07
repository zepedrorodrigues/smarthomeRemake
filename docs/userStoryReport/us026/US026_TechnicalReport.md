# US026 - Sensor of Electric Energy Consumption

### Table of Contents

1. [Requirements](#1-requirements)
    - [Dependency on other user stories](#dependency-on-other-user-stories)
2. [Analysis](#2-analysis)
    - [Relevant domain model excerpt](#relevant-domain-model-excerpt)
3. [Design](#3-design)
    - [Class diagram](#class-diagram)
    - [Sequence diagram](#sequence-diagram)
    - [Applied design patterns and principles](#applied-design-patterns-and-principles)
4. [Tests](#4-tests)
    - [Acceptance tests](#acceptance-tests)
    - [Unit tests](#unit-tests)
    - [Integration tests](#integration-tests)
5. [Implementation](#5-implementation)
6. [Conclusion](#6-conclusion)

## 1. Requirements

_As Product Owner, I want the system to have a type of sensor that gives the
electric energy consumption over a period (Wh)._

Requirements for the sensor

- The sensor should be able to provide the electric energy consumption over a period in watt*hour.

### Dependency on other User Stories

Although this user story does not depend on any other user story, it is important to note that this sensor type
will be available for the user to choose when adding a sensor to a device. This dependency is from **US007**.

## 2. Analysis

The `SensorOfElectricEnergyConsumption` class is composed by the following attributes:

| Attribute           | Rules                                                                                                                     |
|---------------------|---------------------------------------------------------------------------------------------------------------------------|
| **SensorId**        | Must be a unique identifier. It is generated automatically.                                                               |
| **SensorModelName** | Must not be null or empty. Must correspond to a model of an existing type.                                                |
| **Value**           | Must be a `ElectricEnergyConsumptionValue` object that represents the electric energy consumption. It should not be null. |
| **DeviceId**        | Must not be null or empty. Must correspond to an existing device in the house.                                            |                                                                          

The `ElectricEnergyConsumptionValue`, which is created class is composed by the following attributes:

| Attribute                          | Rules                                                                                                                         |
|------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| **ElectricEnergyConsumptionValue** | Must be a double value that represents the electric energy consumption over a period in watt*hour. It should not be negative. |

The interaction with the sensor of electric energy consumption involves:

1. Requesting the electric energy consumption over a period in watt*hour.
2. For the time being, the system generates a default value, as there is no connection to a real sensor.
3. The system will be able to provide the electric energy consumption over a period in watt*hour.

### Relevant domain model excerpt

Below is the relevant excerpt of the domain model for this User Story:

![domainExcerpt_US026.png](domainExcerpt_US026.png)

## 3. Design

### Class Diagram

Below is the class diagram for this User Story:

![SensorOfElectricEnergyConsumption_ClassDiagram_v1.png](../../systemDocumentation/c4ImplementationView/level4CodeDiagram/domain/sensor/sensorOfElectricEnergyConsumption/SensorOfElectricEnergyConsumption_ClassDiagram_v1.png)

### Sequence Diagram

A sequence diagram for US025 is not provided, since the only requirement is for the system to have a sensor that
provides the electric energy consumption over a period in watt*hour. So, there is no interaction with other components
or actors in the system.

### Applied design patterns and principles

* **Information Expert** - The `SensorOfElectricEnergyConsumption` class knows how to manage its identity, value, and
  associated device, encapsulating this logic within the class itself.
* **Creator** - The `SensorFactory` plays a crucial role in the creation of sensors,
  including `SensorOfElectricEnergyConsumption`.
  It encapsulates the logic required to instantiate a sensor, ensuring that each sensor is created with a proper state
  and associated with the correct device and sensor model. This pattern helps in maintaining the consistency and
  integrity of sensor creation across the system.
* **Low Coupling** - The `SensorFactory` plays a pivotal role in this User Story by centralizing the instantiation of
  `SensorOfElectricEnergyConsumption` instances. By decoupling the creation process from the sensor's operational and
  data
  management responsibilities, the system gains flexibility. Adjustments to how sensors are created or initialized can
  be made independently of their core functionalities and interactions with the `SensorRepository`.
* **High Cohesion** - Each class is focused on a single responsibility. For instance, the `SensorRepository` is solely
  concerned with storing and retrieving sensor information, ensuring that classes are focused and understandable.
* **Single Responsibility Principle (SRP)** - The `SensorOfElectricEnergyConsumption` class focuses on managing electric
  energy consumption` sensor data,
  distinct from the `SensorRepository` which deals with data storage and retrieval. This separation ensures that changes
  in data management don't interfere with sensor functionality, simplifying maintenance and scalability.
* **Repository** - The `SensorRepository`, `SensorTypeRepository`, and `SensorModelRepository` act as Repository
  patterns. They provide a collection-like interface for accessing sensor, sensor type, and sensor model objects from
  the domain model, abstracting away the details of the data access layer.
* **Interface Segregation** - The `Sensor` interface defines a contract for sensor behavior without imposing any
  unnecessary methods on the implementing classes, such as `SensorOfElectricEnergyConsumption`. This follows the
  Interface
  Segregation Principle by ensuring that implementing classes only need to provide implementations for methods that make
  sense for their specific type of sensor.
* **Value Object** - The `ElectricEnergyConsumptionValue` class is a value object that encapsulates the electric energy
  consumption value. By representing this value as an object, the system can ensure that the value is always valid and
  consistent, preventing accidental misuse or modification.

## 4. Tests

### Acceptance tests

- **Scenario 1:** The user wants to be able to create a Sensor of Electric Energy Consumption
    - **When** the user wants to have a `SensorOfElectricEnergyConsumption` available in the system
    - **Then** the system must enable a Sensor of Electric Energy Consumption to the user.
    - **And** the system must save the parameters given to construct such sensor.
    - **And** the system must generate a unique `SensorId` for the Sensor.

- **Scenario 2:** The user wants to be able to get the electric energy consumption over a period in watt*hour
    - **Given** that the user has a `SensorOfElectricEnergyConsumption` available in the system
    - **When** the user requests the electric energy consumption over a period in watt*hour
    - **Then** the system must provide the electric energy consumption over a period in watt*hour.

### Unit tests

Below are some relevant unit tests for this user story, focusing on the creation of electric energy consumption sensors:

| Test Case                                                                    | Expected Outcome                                                                                                   |
|------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| Test creating an electric energy consumption sensor with valid parameters.   | The system should create an electric energy consumption sensor with the specified device ID and sensor model name. |
| Test creating an electric energy consumption sensor with invalid parameters. | The system should return an error message.                                                                         |

Below are some relevant unit tests for this user story, focusing on the retrieval and validation of electric energy
consumption readings:

| Test Case                                             | Expected Outcome                                                                           |
|-------------------------------------------------------|--------------------------------------------------------------------------------------------|
| Test retrieving the electric energy consumption value | The system should return the electric energy consumption value associated with the sensor. |

Below are some relevant unit tests for this user story, focusing on the creation of electric energy consumption values:

| Test Case                                                                       | Expected Outcome                                                                        |
|---------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|
| Test creating an electric energy consumption value with a valid value (>= 0).   | The system should create an electric energy consumption value with the specified value. |
| Test creating an electric energy consumption value with an invalid value (< 0). | The system should return an error message.                                              |

For more information, please check
the [SensorOfElectricEnergyConsumptionTest](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/test/java/smarthome/domain/sensor/SensorOfElectricEnergyConsumptionTest.java),
and [ElectricEnergyConsumptionValueTest](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/test/java/smarthome/domain/sensor/vo/values/ElectricEnergyConsumptionValueTest.java)
classes.

### Integration tests

Integration tests are not specified for US025 at this time. This is because US025's scope is confined to
displaying electric energy consumption based on existing sensor data, a process that does not involve intricate
interactions
between multiple system components or external dependencies. Should the integration landscape of the
system evolve to include more complex interactions relevant to US025, the need for integration tests will be revisited.

## 5. Implementation

The `SensorOfElectricEnergyConsumption` class implements the `Sensor` interface, specializing in the management of
electric energy consumption data. Below are the relevant methods of this class:

```java


public class SensorOfElectricEnergyConsumption implements Sensor {
    // Attributes

    protected SensorOfElectricEnergyConsumption(DeviceId deviceId, SensorModelName sensorModelName) {
        // Implementation of the constructor
    }
}
```

The `ElectricEnergyConsumptionValue` class, implementing the `Value` interface, encapsulates the electric energy
consumption, ensuring immutability and data integrity. Below are the relevant methods of this class:

```java
public class ElectricEnergyConsumptionValue implements Value {
    // Attributes

    public ElectricEnergyConsumptionValue(double value) {
        // Implementation of the method
    }
}
```

For more information, please check the
[SensorOfElectricEnergyConsumption](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/domain/sensor/SensorOfElectricEnergyConsumption.java),
and [ElectricEnergyConsumptionValue](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/domain/sensor/vo/values/ElectricEnergyConsumptionValue.java)
classes.

## 6. Conclusion

To summarize, this User Story integrates the functionality of displaying electric energy consumption into the system,
utilizing the `SensorOfElectricEnergyConsumption` and `ElectricEnergyConsumptionValue` classes for effective data
management and representation. The implementation involves key attributes, such as the `sensorModelName` and `value`,
which are essential for the accurate representation and retrieval of electric energy consumption data.

[Back to Top](#us026---sensor-of-electric-energy-consumption)