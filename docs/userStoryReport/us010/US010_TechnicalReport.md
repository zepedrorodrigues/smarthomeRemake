# US010 - Sensor of Temperature

### Table of Contents

1. [Requirements](#1-requirements)
    - [Dependency on Other User stories](#dependency-on-other-user-stories)
2. [Analysis](#2-analysis)
    - [Relevant Domain Model excerpt](#relevant-domain-model-excerpt)
3. [Design](#3-design)
    - [Class diagram](#class-diagram)
    - [Sequence diagram](#sequence-diagram)
    - [Applied design patterns and principles](#applied-design-patterns-and-principles)
4. [Tests](#4-tests)
    - [Acceptance Tests](#acceptance-tests)
    - [Unit tests](#unit-tests)
    - [Integration tests](#integration-tests)
5. [Implementation](#5-implementation)
6. [Conclusion](##6-conclusion)

## 1. Requirements

_As Product Owner, I want the system to have a type of sensor that measures
temperature (C)._

Requirements for a sensor of temperature:

- The sensor must know the device that it belongs to.
- The sensor must be able to provide the current temperature.
- The sensor must provide the temperature value in Celsius.

### Dependency on other User Stories

Although the **US010** does not have a direct dependency on other user stories, it is worth noting that **US007**(... I want to add a sensor to an existing device in
a room. The sensor must be of a model of an existing type of sensor) is deeply connected to this user story. In the context 
of the **US007**, the sensor added to the device could be the one described in this user story.

Also, it´s important to mention that a `SensorOfTemperature` returns a specific value, `TemperatureValue`, and a class was created to represent
this value in the system as well.

## 2. Analysis

The SensorOfTemperature class is composed by the following attributes:

| Attribute           | Rules                                                                                                                                  |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| **SensorId**        | Must be a unique identifier. It is generated automatically.                                                                            |
| **DeviceId**        | Must not be null or empty. Must correspond to an existing device identity saved in the system.                                         |
| **SensorModelName** | Must not be null or empty. Must correspond to an existing model saved in the system.                                                   |
| **Value**           | Must be a TemperatureValue object that represents the current temperature. At the moment this is a default value for testing purposes. |

The TemperatureValue, which is created in the context of the SensorOfTemperature class, is composed by the following attributes:

| Attribute            | Rules                                                                                              |
|----------------------|----------------------------------------------------------------------------------------------------|
| **temperatureValue** | Must be a double that represents the current temperature and handles negative and positive values. |

The interaction with the sensor of temperature involves:

1. Requesting the current temperature (for now the system generates a default value, as
there is not yet a connection to the hardware that provides this information).
2. The system returns the current temperature value in a decimal format(Celsius).

### Relevant Domain Model excerpt

Below is the relevant domain model excerpt for this user story:

![Domain Model Excerpt](domainModelExcerpt_US010.png)

## 3. Design

### Class Diagram

Below is the class diagram for this user story:
![US010_ClassDiagram_v2.png](../../systemDocumentation/c4ImplementationView/level4CodeDiagram/domain/sensor/sensorOfTemperature/sensorOfTemperature_ClassDiagram_v03.png)

### Sequence Diagram

A sequence diagram for **US010** is not provided, since the only requirement is for the system to have a sensor that provides
the current temperature. So, there is no interaction with other components or actors in the system at the moment.

### Applied Design Patterns and Principles

* **Information Expert and Single Responsibility** - Each created class is focused on a single responsibility. Also each class knows how to manage its own data and responsibilities encapsulating the logic within the class itself.


* **Creator** - The _SensorFactory_ class plays a crucial role in the creation of sensors, including _SensorOfTemperature_.
  It encapsulates the logic required to instantiate any sensor, ensuring that each sensor is created with a valid state
  and associated with the correct device and sensor model.


* **Low Coupling** - By decoupling the creation process from the _SensorOfTemperature_ class responsibilities, the system gains flexibility. Adjustments to how sensors are created or initialized can be made
  independently of their core functionalities.


* **High Cohesion** - The _SensorOfTemperature_ class is responsible for managing temperature sensor data, while the 
  _SensorRepository_ class is solely concerned with storing and retrieving sensor information and the _SensorFactory_ 
  class is responsible for creating sensors. This separation ensures that classes are focused and understandable.


* **Repository** - The _SensorRepository_, _SensorTypeRepository_, and _SensorModelRepository_ classes act as Repository
  patterns, providing a way to store and retrieve sensor data, sensor types, and sensor models, respectively.


* **Interface Segregation Principle(ISP)** - The _Sensor_ interface defines a contract for sensor behavior without imposing any
  unnecessary methods on the implementing classes, such as _SensorOfTemperature_. This follows the ISP by ensuring that implementing classes only need to provide implementations for methods that make sense for their
  specific type of sensor.


* **Value Object** - The _TemperatureValue_ class is a value object that encapsulates the current temperature value. 
  By treating the temperature value as a value object, the system ensures that the value is immutable and can be shared without risk of modification.

## 4. Tests

### Acceptance Tests

- `Scenario 1:` Create and associate a sensor of temperature with a device.
    - **Given** the system is equipped with devices.
    - **When** a user requests to add a sensor of temperature to a device.
    - **Then** the system should be able to create and save a sensor of temperature.


- `Scenario 2:` Retrieving the current temperature from a device.
    - **Given** the system is equipped with devices that have temperature sensors.
    - **When** a user requests the current temperature from a device(sensor).
    - **Then** the system should provide the current temperature.

### Unit Tests

Below are some relevant unit tests for the class `SensorOfTemperature`:

| Test Case                                                             | Expected Outcome                                                                  |
|-----------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| Test creating a temperature sensor with the provided device identity. | The system should correctly return a temperature sensor associated to the device. |
| Test retrieving the sensor identity.                                  | The system should return the accurate sensor identity.                            |
| Test retrieving the associated device identity.                       | The system should return the accurate device identity.                            | 
| Test retrieving the default temperature value.                        | The system should return the default temperature value defined in the system      |

Below are some relevant unit tests for the class `TemperatureValue`:

| Test Case                                                           | Expected Outcome                                                            |
|---------------------------------------------------------------------|-----------------------------------------------------------------------------|
| Test creating a temperature value with the provided value.          | The system should return a temperature value, value object.                 |
| Verify the accessibility of the current positive temperature value. | The system should return the current positive temperature value as a string |
| Verify the accessibility of the current negative temperature value. | The system should return the current negative temperature value as a string |

For more information, please refer to the
[SensorOfTemperatureTest](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/test/java/smarthome/domain/sensor/SensorOfTemperatureTest.java)
and
[TemperatureValueTest](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/test/java/smarthome/domain/sensor/vo/values/TemperatureValueTest.java)
classes.

### Integration Tests

Integration tests are not specified for **US010** at this time.

## 5. Implementation

The `SensorOfTemperature` class implements the `Sensor` interface and encapsulates the logic for managing the current temperature:

```java
public class SensorOfTemperature implements Sensor {
    // Implementation of the class
}

protected SensorOfTemperature(DeviceId deviceId, SensorModelName sensorModelName) {
    // Implementation of the constructor
    this.value = new TemperatureValue(25.0); //Default value
}

@Override
public Value getValue() {
    // Implementation of the method
}
```

The `TemperatureValue` class, implements the `Value` interface and encapsulates the current temperature value, ensuring immutability and
data integrity.

```java
public class TemperatureValue implements Value {
    // Implementation of the class
}

public TemperatureValue(double temperatureValue) {
    // Implementation of the constructor
}

@Override
public String valueToString() {
    // Implementation of the method
}
```

For more information, please refer to the
[SensorOfTemperature](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/domain/sensor/SensorOfTemperature.java)
and
[TemperatureValue](https://github.com/Departamento-de-Engenharia-Informatica/2023-2024-switch-dev-project-assignment-switch-project-2023-2024-grupo6/blob/main/src/main/java/smarthome/domain/sensor/vo/values/TemperatureValue.java)
classes.

## 6. Conclusion

In conclusion, this User Story introduces the functionality of providing the current temperature into the system, utilizing
the `SensorOfTemperature` and `TemperatureValue` classes for effective management of temperature data.

[Back to Top](#us010---sensor-of-temperature)