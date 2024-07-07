# Acceptance Criteria

## Sensor of Average Power Consumption

- **Scenario 1:** The user wants to be able to create a Sensor of Average Power Consumption
    - **When** the user wants to have a `SensorOfAveragePowerConsumption` available in the system
    - **Then** the system must enable a Sensor of Average Power Consumption to the user.
    - **And** the system must save the parameters given to construct such a sensor.
    - **And** the system must generate a unique `SensorId` for the Sensor.


- **Scenario 2:** The user wants to be able to get the average power consumption over a period in watts
    - **Given** that the user has a `SensorOfAveragePowerConsumption` available in the system
    - **When** the user requests the average power consumption over a period in watts
    - **Then** the system must provide the average power consumption over a period in watts.