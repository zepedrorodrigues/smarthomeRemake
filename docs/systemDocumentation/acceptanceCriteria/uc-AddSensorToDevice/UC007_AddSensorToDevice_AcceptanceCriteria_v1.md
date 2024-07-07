# Acceptance Criteria

## UC007 - Add Sensor to Device

- **Scenario 1:** Adding a sensor to an existing device
    - **Given** the system has a list of available devices and rooms.
    - **When** a Power User or Administrator selects an existing device and a room.
    - **Then** the system should allow the selection of a sensor type.
    - **And** the system should associate the selected sensor type with the specified device.
    - **And**  the system should confirm the successful addition of the sensor to the device.

- **Scenario 2:** Adding a sensor to a non-existent device
    - **Given** the system has a list of available rooms.
    - **When** a Power User or Administrator attempts to add a sensor to a non-existent device.
    - **Then** the system should reject the addition and display an error message.

- **Scenario 3:** Adding a sensor to an existing device with model validation
    - **Given** the system has a list of available devices, rooms, and sensor models.
    - **When** a Power User or Administrator selects an existing device and a room.
    - **Then** the system should allow the selection of a sensor type from the existing sensor models.
    - **And** the system should verify that the selected sensor type is an existing model.
    - **And** if the selected sensor type is valid,
    - **Then** the system should associate the selected sensor type with the specified device.
    - **And** the system should confirm the successful addition of the sensor to the device.
    - **Otherwise**, if the selected sensor type is not an existing model,
    - **Then** the system should display an error message indicating that the sensor model does not exist.


