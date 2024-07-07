# Acceptance Criteria

## Sensor of Sunset

- **Scenario 1:** Creating a Sunset Sensor
    - **When** a user wants to add a Sunset Sensor to the system,
    - **Then** the system should enable the user to add a Sunset Sensor,
    - **And** the system should validate and save the parameters to configure the Sunset Sensor,
    - **And** the system should generate a unique `SensorId` for the Sunset Sensor.


- **Scenario 2**: Retrieving sunset time for a given date
    - **Given** the system is equipped with sunset sensors.
    - **When** a user requests the sunset time for a specific date.
    - **Then** the system should display the sunset time for that date.


- **Scenario 3**: Retrieving sunset time without specifying a date
    - **Given** the system is equipped with sunset sensors.
    - **When** a user requests the sunset time without specifying a date.
    - **Then** the system should automatically use the current date.
    - **And** the system should display the sunset time for the current date.