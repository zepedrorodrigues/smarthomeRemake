## Sensor of Wind

- **Scenario 1**: Retrieving wind speed and direction
    - **Given** the system is equipped with wind sensors.
    - **When** a user requests the current wind speed and direction.
    - **Then** the system should display the current wind speed and direction in the specified format.

- **Scenario 2**: Retrieving wind speed and direction with invalid data
    - **Given** the system is equipped with wind sensors.
    - **When** a user requests the current wind speed and direction with invalid data.
    - **Then** the system should display an error message indicating the invalid data.
    - **And** the system should not display the wind speed and direction.