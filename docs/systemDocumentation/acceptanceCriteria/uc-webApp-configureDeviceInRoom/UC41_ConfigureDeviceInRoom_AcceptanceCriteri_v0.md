## Acceptance Criteria

## UC: Configure Device in Room


- **Steps to follow:**
  - **Given** The user has opened the app and is in the main page
  - **When** the user presses the "Rooms" button
  - **Then** the user can see a list of rooms
  - **When** the user clicks on "View More"
  - **Then** the user can see a list of devices of the previously chosen room
  - **And** the user can click on "View More" to see the device details


- **Scenario 1:** The user wants to configure a device in the room to deactivate it if it is still active
  - **Given** The user can see the device details
  - **And** the device is currently active
  - **When** the user slides the "Deactivate Device" button
  - **Then** the device is deactivated and its status is updated to "Inactive"


- **Scenario 2:** The user wants to configure a device in the room to add a sensor
  - **Given** The user can see the device details
  - **When** the user clicks on "Add Sensor"
  - **Then** the user can choose the sensor type from a dropdown list
  - **When** the user chooses the sensor type
  - **Then** the user can choose the sensor model from a dropdown list
  - **When** the user chooses the sensor model
  - **Then** the user can click on "Save" to add the sensor to the device


- **Scenario 3:** The user wants to configure a device in the room to add an actuator,
  specifically if the actuator type is Blinds Roller or On/Off Switch
  - **Given** The user can see the device details
  - **When** the user clicks on "Add Actuator"
  - **Then** the user can choose the actuator type from a dropdown list
  - **When** the user chooses the actuator type
  - **Then** the user can choose the actuator model from a dropdown list
  - **When** the user chooses the actuator model
  - **Then** the user can click on "Save" to add the actuator to the device


- **Scenario 4:** The user wants to configure a device in the room to add an actuator,
  specifically if the actuator type is Limiter
  - **Given** The user can see the device details
  - **When** the user clicks on "Add Actuator"
  - **Then** the user can choose the actuator type from a dropdown list
  - **When** the user chooses the actuator type
  - **Then** the user can choose the actuator model from a dropdown list
  - **When** the user chooses the actuator model
  - **Then** the user can fill in the upper and lower limit values
  - **And** the user can click on "Save" to add the actuator to the device


- **Scenario 5:** The user wants to configure a device in the room to add an actuator,
  specifically if the actuator type is Decimal Limiter
  - **Given** The user can see the device details
  - **When** the user clicks on "Add Actuator"
  - **Then** the user can choose the actuator type from a dropdown list
  - **When** the user chooses the actuator type
  - **Then** the user can choose the actuator model from a dropdown list
  - **When** the user chooses the actuator model
  - **Then** the user can fill in the upper and lower limit and precision values
  - **And** the user can click on "Save" to add the actuator to the device

