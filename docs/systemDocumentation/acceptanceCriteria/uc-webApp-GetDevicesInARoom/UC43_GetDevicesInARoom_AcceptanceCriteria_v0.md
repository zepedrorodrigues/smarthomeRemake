# Acceptance Criteria

## UC: View List of Devices in a Room

- **Scenario 1:** The user wants to view the list of all devices in a room
    - **Given** The user has opened the app and is in the main page
    - **When** the user presses the "Rooms" button
    - **Then** the user can see a list of rooms
    - **And** the user can choose a room from the list
    - **When** the user clicks on "View More" to see the devices of the previously chosen room
    - **Then** the user can see a list of devices of that room

- **Scenario 2:** The user wants to view the list of all devices in a room
    - **Given** The user has opened the app and is in the main page
    - **When** the user presses the "Rooms" button
    - **Then** the user can see a list of rooms
    - **And** the user can choose a room from the list
    - **When** the user clicks on "View More" to see the devices of the previously chosen room
    - **When** the user presses the "Devices" 
    - **When** the room has no devices
    - **Then** the user can see an empty list of devices