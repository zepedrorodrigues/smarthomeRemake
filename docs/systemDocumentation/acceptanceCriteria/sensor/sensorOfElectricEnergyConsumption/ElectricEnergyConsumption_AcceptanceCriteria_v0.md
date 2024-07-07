# Acceptance Criteria

## Sensor of Electric Energy Consumption

- **Scenario 1:** The user wants to be able to create a Sensor of Electric Energy Consumption
    - **When** the user wants to have a `SensorOfElectricEnergyConsumption` available in the system
    - **Then** the system must enable a Sensor of Electric Energy Consumption to the user.
    - **And** the system must save the parameters given to construct such a sensor.
    - **And** the system must generate a unique `SensorId` for the Sensor.


- **Scenario 2:** The user wants to be able to get the electric energy consumption over a period in watt*hour
    - **Given** that the user has a `SensorOfElectricEnergyConsumption` available in the system
    - **When** the user requests the electric energy consumption over a period in watt*hour
    - **Then** the system must provide the electric energy consumption over a period in watt*hour.