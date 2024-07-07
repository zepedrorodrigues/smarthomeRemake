# Acceptance Criteria

## Sensor of Sunrise

- **Scenario 1**: Retrieving sunrise time for a given date
    - **Given** the system is equipped with sunrise sensors.
    - **When** a user requests the sunrise time for a specific date.
    - **Then** the system should display the sunrise time for that date.


- **Scenario 2**: Retrieving sunrise time without specifying a date
    - **Given** the system is equipped with sunrise sensors.
    - **When** a user requests the sunrise time without specifying a date.
    - **Then** the system should automatically use the current date.
    - **And** the system should display the sunrise time for the current date.
