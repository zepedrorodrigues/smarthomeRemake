# Acceptance Criteria

## Add Actuator to Device

- **Scenario 1**: Adding Actuator with Valid Data
    - **Given** the actuator addition process,
    - **When** a power user or administrator provides valid data to add an actuator to a device,
    - **Then** the actuator should be successfully added to the device without errors, indicating a successful addition.

- **Scenario 2**: Adding Actuator with Invalid Data
    - **Given** the actuator addition process,
    - **When** a power user or administrator provides invalid data while adding an actuator to a device (e.g., selecting
      an invalid model),
    - **Then** the actuator addition should fail, and an appropriate error message should be displayed indicating the
      failure.
    - **And** the actuator should not be added to the device.

- **Scenario 3**: Adding Actuator with Null Data
    - **Given** the actuator addition process,
    - **When** a power user or administrator attempts to add an actuator with null data,
    - **Then** the actuator addition should fail, and an appropriate error message should be displayed indicating the
      failure.
    - **And** the actuator should not be added to the device.

- **Scenario 4**: Adding Actuator to Invalid Device
    - **Given** the actuator addition process,
    - **When** a power user or administrator attempts to add an actuator to an invalid device,
    - **Then** the actuator addition should fail, and an appropriate error message should be displayed indicating the
      failure.
    - **And** the actuator should not be added to the device.



