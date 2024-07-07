# Acceptance Criteria

## Sensor of Power Consumption

- **Scenario 1:** The user wants to be able to create a Sensor for Power Consumtion
    - **When** the user wants to have a `SensorofPowerConsumption` available for future implementation/construction
    - **Then** the system must enable a Sensor for Power Consumption to the user.
    - **And** the system must save the parameters given to construct such sensor (`SensorModelName` and `DeviceId`).
    - **And** the system must generate a unique `SensorId` for the Sensor.


- **Scenario 2** The user wants to be able to get a `PowerConsumptionValue` of a `SensorOfPowerConsumption` in a given
  instant in Watts.
    - **Given** the user has a `SensorOfPowerConsumption` object.
    - **When** the user requests the `Value`.
    - **Then** the system must return the `PowerConsumptionValue` of the object, that can be transformed into String
      format for better visualization