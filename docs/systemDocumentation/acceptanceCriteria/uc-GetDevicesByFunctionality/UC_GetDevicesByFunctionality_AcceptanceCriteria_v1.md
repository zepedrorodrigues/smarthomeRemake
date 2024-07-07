# Acceptance  Criteria

### UC - Get Devices Sorted By Functionality

- **Scenario 1**: The Power User [or Administrator]  retrieves a categorized list of all devices.
    - **Given** the Power User [or Administrator] requests a list of all devices in a house grouped by device
      functionality types.
    - **When** the System retrieves all `SensorType` from the `SensorTypeRepository`.
    - **And** the System retrieves all `Sensors` from the `SensorRepository`.
    - **And** for each `Sensor` retrieved, using its `SensorModelName`, the System retrieves the `SensorModel` from
      the `SensorModelRepository`.
    - **And** using the `SensorModel` retrieved, using its `SensorTypeId` attribute, the System retrieves
      the `SensorType` from the `SensorTypeRepository`.
    - **And** the System groups the `Devices` based on the `SensorType` of the `Sensor` instances attributed to
      that `Device` into a `HashMap<SensorType, Set<Device>>`.
    - **Then** the System transforms these domain objects into Strings and DTOs and returns them to the Power
      User [or Administrator] a `HashMap<String,Set<DeviceDTO>>`.

- **Scenario 2**: The Power User [or Administrator]  retrieves a categorized list of all devices, but there are no
  devices in the house.
    - **Given** the Power User [or Administrator] requests a list of all devices in a house grouped by device
      functionality types.
    - **When** the System retrieves all `SensorType` from the `SensorTypeRepository`.
    - **And** the System retrieves all `Sensors` from the `SensorRepository`.
    - **And** for each `Sensor` retrieved, using its `SensorModelName`, the System retrieves the `SensorModel` from
      the `SensorModelRepository`.
    - **And** using the `SensorModel` retrieved, using its `SensorTypeId` attribute, the System retrieves
      the `SensorType` from the `SensorTypeRepository`.
    - **And** the System groups the `Devices` based on the `SensorType` of the `Sensor` instances attributed to
      that `Device` into a `HashMap<SensorType, Set<Device>>`.
    - **Then** the System returns an empty `HashMap<String,Set<DeviceDTO>>` to the Power User [or Administrator].

- **Scenario 3**: The Power User [or Administrator] retrives a categorized list of all devices, but there are no sensors
  in the house.
    - **Given** the Power User [or Administrator] requests a list of all devices in a house grouped by device
      functionality types.
    - **When** the System retrieves all `SensorType` from the `SensorTypeRepository`.
    - **And** the System retrieves all `Sensors` from the `SensorRepository`.
    - **And** for each `Sensor` retrieved, using its `SensorModelName`, the System retrieves the `SensorModel` from
      the `SensorModelRepository`.
    - **And** using the `SensorModel` retrieved, using its `SensorTypeId` attribute, the System retrieves
      the `SensorType` from the `SensorTypeRepository`.
    - **And** the System groups the `Devices` based on the `SensorType` of the `Sensor` instances attributed to
      that `Device` into a `HashMap<SensorType, Set<Device>>`.
    - **Then** the System returns an empty `HashMap<String,Set<DeviceDTO>>` to the Power User [or Administrator].