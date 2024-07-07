# Acceptance Criteria

## UC: View Device Measurements

- **Steps to follow:**
  - **Given** The user has opened the app and is in the main page
  - **When** the user presses the "Rooms" button
  - **Then** the user can see a list of rooms
  - **When** the user clicks on "View More"
  - **Then** the user can see a list of devices of the previously chosen room
  - **And** the user can click on "View More" to see the device details


- **Scenario 1:** The user wants to view measurements of a device in a given period with measurements
  - **Given** The user can see the device details
  - **When** the user clicks on "Measurements"
  - **Then** the user can choose the measurement period
  - **When** the user chooses the correct measurement period
  - **And** the user clicks on "Submit" to set the period
  - **Then** the user can see the measurements of the device
  

- **Scenario 2:** The user wants to view measurements of a device in a given period with no measurements
  - **Given** The user can see the device details
  - **When** the user clicks on "Measurements"
  - **Then** the user can choose the measurement period
  - **When** the user chooses a measurement period with no measurements
  - **And** the user clicks on "Submit" to set the period
  - **Then** the system should display a message informing the user that there are no measurements for the selected period


- **Scenario 3:** The user wants to view measurements of a device in a given period with measurements
  - **Given** The user can see the device details
  - **When** the user clicks on "Measurements"
  - **Then** the user can choose the measurement period
  - **When** the user chooses the invalid measurement period
  - **And** the user inputs a start date equal to end date
  - **And** the user clicks on "Submit" to set the period
  - **Then** the user will get an error message
  

- **Scenario 4:** The user wants to view measurements of a device in a given period with measurements
  - **Given** The user can see the device details
  - **When** the user clicks on "Measurements"
  - **Then** the user can choose the measurement period
  - **When** the user chooses the invalid measurement period
  - **And** the user inputs a start date after an end date
  - **And** the user clicks on "Submit" to set the period
  - **Then** the user will get an error message 


- **Scenario 5:** The user wants to view measurements of a device in a given period with measurements
  - **Given** The user can see the device details
  - **When** the user clicks on "Measurements"
  - **Then** the user can choose the measurement period
  - **When** the user chooses the invalid measurement period
  - **And** the user inputs a start date or an end date that´s in the future
  - **And** the user clicks on "Submit" to set the period
  - **Then** the user will get an error message 


- **Scenario 6:** The user wants to view measurements of a device in a given period with measurements
  - **Given** The user can see the device details
  - **When** the user clicks on "Measurements"
  - **Then** the user can choose the measurement period
  - **When** the user chooses the invalid measurement period
  - **And** the user inputs a start date and an end date that´s in the future
  - **And** the user clicks on "Submit" to set the period
  - **Then** the user will get an error message 
  


