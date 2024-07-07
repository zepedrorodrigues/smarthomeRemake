# Acceptance Criteria

## UC Get Devices In Room

- `Scenario 1:` The user wants to retrieve devices in a room for the first time.
    - **Given** the user wants to retrieve devices in a room.
    - **When** the user selects a room from the list of available rooms.
    - **Then** the system should retrieve and present the list of devices in the selected room.


- `Scenario 2:` The user wants to retrieve devices in a room with existing devices.
    - **Given** the user wants to retrieve devices in a room.
    - **When** the user selects a room with existing devices.
    - **Then** the system should retrieve and present the list of devices in the selected room.


- `Scenario 3:` The user wants to retrieve devices in a room with no devices.
    - **When** the user selects a room that does not contain any devices.
    - **Then** the system must be able to check if the room name is unique.
    - **And** The selected room exists in the system.
    - **And** The selected room does not have any devices associated with it.
    - **When**: The user requests to view the devices in the selected room.
    - **Then**: The system should return an empty list of devices when there are no devices configured in the selected
      room.
