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

- **Scenario 3:** The administrator wants to configure the location of the house with invalid parameters
    - **Given** the system has a list of available devices and rooms.
    - **When** a Power User or Administrator attempts to add a sensor with an invalid type.
    - **Then** the system should reject the addition and display an error message.

