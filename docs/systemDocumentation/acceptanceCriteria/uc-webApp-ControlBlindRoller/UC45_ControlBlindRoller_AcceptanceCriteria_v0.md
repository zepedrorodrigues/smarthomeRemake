# Acceptance Criteria

## UC: Control Blind Roller

- **Steps to follow:**
  - **Given** The user has opened the app and is in the main page
  - **When** the user presses the "Rooms" button
  - **Then** the user can see a list of rooms
  - **When** the user clicks on "View More"
  - **Then** the user can see a list of devices of the previously chosen room
  - **And** the user can click on "View More" to see the device details


- **Scenario 1:** The user wants to see the current position of the blind roller
    - **Given** The user can see the device details
    - **Then** the user can see the current state of the blind roller in the actuator's area


- **Scenario 2:** The user wants to control the blind roller
    - **Given** The user can see the device details
    - **Then** the user can see a slide button to control the blind roller in the actuator's area
    - **When** the user slides the button to the desired position
    - **And** the user clicks on "Operate"
    - **Then** the app should inform the user that the operation was successful

- **Scenario 3:** The user wants to control the blind roller but the device is inactive
  - **Given** The user can see the device details
  - **And** The device is inactive
  - **Then** the user can see a slide button to control the blind roller in the actuator's area
  - **But** the slide button is disabled and cannot be moved
  - **And** the "Operate" button is disabled and cannot be clicked
  - **When** the user tries to slide the button to a different position
  - **Then** the slide button does not move
  - **When** the user tries to click on "Operate"
  - **Then** the app should not perform any operation and no success message is shown


    