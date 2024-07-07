# Acceptance Criteria

## UC Add a Device to a Room

- **Scenario 1**: Retrieving the list of rooms before adding a device
    - **Given** the system has a list of rooms.
    - **When** a power user or administrator requests the list of rooms.
    - **Then** the system should display the list of available rooms.


- **Scenario 2**: Adding a device to a room
    - **Given** a list of rooms is available.
    - **When** a power user or administrator selects a room and specifies a new device's details.
    - **Then** the system should validate the device's details.
    - **And** the system should save the new device in the selected room.
    - **And** the system should confirm the addition by displaying the room with the new device.


- **Scenario 3**: Adding multiple devices to multiple rooms
    - **Given** a list of rooms is available.
    - **When** a power user or administrator adds multiple devices to different rooms.
    - **Then** the system should validate each device's details.
    - **And** the system should save each device in the respective room.
    - **And** the system should confirm each addition by providing the details of the newly added devices.


- **Scenario 4**: Adding a device with invalid details
    - **Given** a list of rooms is available.
    - **When** a power user or administrator attempts to add a device with incomplete or invalid details.
    - **Then** the system should reject the device addition.
    - **And** the system should indicate the addition was unsuccessful due to the provided details.


- **Scenario 5**: Test the persistence of the attributes of the device
    - **Given** a device has been added to a room.
    - **When** the DeviceRepository is called to retrieve the device that was added.
    - **Then** the device's attributes should match the details provided during the addition process.