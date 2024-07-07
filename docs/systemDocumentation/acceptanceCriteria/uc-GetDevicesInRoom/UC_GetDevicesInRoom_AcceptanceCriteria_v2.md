# Acceptance Criteria

## UC Get Devices In Room

- `Scenario 1:` Retrieving Devices in a Room
    - **Given** the user wants to retrieve devices in a room.
    - **When** the user selects a room from the list of available rooms.
    - **Then** the system should take the room ID provided and check if the room exists.
    - **And** the system, having identified the room, should retrieve and present the list of devices in the selected
      room.


- `Scenario 2:` Invalid Room ID Provided
    - **Given** the user wants to retrieve devices in a room.
    - **When** the user provides an invalid room ID.
    - **Then** the system should prompt the user to provide a valid room ID.


- `Scenario 3:` Room ID Provided is non-existent
    - **Given** the user wants to retrieve devices in a room.
    - **When** the user provides a non-existent room ID.
    - **Then** the system should prompt the user to provide a valid room ID.


- `Scenario 4:` Devices Successfully Retrieved and Filtered
    - **Given** the user wants to retrieve devices in a room.
    - **When** the system should successfully retrieve devices based on the provided room ID.
    - **Then** the system should filter the devices listed to include only devices associated with the specified room.


- `Scenario 5:` Valid Device Attributes in the Retrieved List
    - **Given** the user wants to retrieve devices in a room.
    - **When** the system retrieves the list of devices associated with the specified room.
    - **Then** each device in the retrieved list should have valid attributes including a unique device ID, a valid
      device name, a valid device type, a valid room ID matching the provided room ID, and a valid device status.


- `Scenario 6:` Retrieving Devices in a Room with No Devices.
    - **When** the user selects a room that does not contain any devices.
    - **Then** the system must be able to check if the room name is unique.
    - **And** The selected room exists in the system.
    - **And** The selected room does not have any devices associated with it.
    - **When**: The user requests to view the devices in the selected room.
    - **Then**: The system should return an empty list of devices when there are no devices configured in the selected
      room.

By ensuring the fulfillment of these scenarios, the system should demonstrate its capability to effectively handle user
requests for retrieving and configuring devices within specific rooms.