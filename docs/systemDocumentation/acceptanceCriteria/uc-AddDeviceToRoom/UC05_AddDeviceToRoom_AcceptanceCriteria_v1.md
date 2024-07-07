# Acceptance Criteria

## UC Add a Device to a Room

- **Scenario 1**: Adding a device to a room
    - **Given** a list of rooms is available.
    - **When** a power user or administrator selects a room and specifies a new device's details.
    - **Then** the system should validate the device's details.
    - **And** the system should save the new device in the selected room.
    - **And** the system should confirm the addition by displaying the room with the new device.


- **Scenario 2**: Adding a device with invalid details
    - **Given** a list of rooms is available.
    - **When** a power user or administrator attempts to add a device with incomplete or invalid details.
    - **Then** the system should reject the device addition.
    - **And** the system should indicate the addition was unsuccessful due to the provided details.


- **Scenario 3**: Adding a device to a room that does not exist
    - **Given** a list of rooms is available.
    - **When** a power user or administrator attempts to add a device to a room that does not exist.
    - **Then** the system should reject the device addition.
    - **And** the system should indicate the addition was unsuccessful due to the room not existing.


- **Scenario 4**: Test the persistence of the attributes of the device
    - **Given** a device has been added to a room.
    - **When** the DeviceRepository is called to retrieve the device that was added.
    - **Then** the device's attributes should match the details provided during the addition process.