# Acceptance Criteria

## Actuator of Limiter

- **Scenario 1**: Test for Valid Range Configuration
    - **Given** an actuator configuration process,
    - **When** an actuator is configured with a lower limit set lower than the upper limit, both being integer values,
    - **Then** the configuration should be successfully accepted without errors, indicating a valid range configuration.


- **Scenario 2**: Test for Invalid Range Configuration
    - **Given** an actuator configuration process,
    - **When** an actuator is configured with the upper limit set lower than the lower limit,
    - **Then** the configuration should be rejected, and an error message should be raised indicating an invalid range
      configuration.


- **Scenario 3**: Test for Value Within Range
    - **Given** a configured actuator with a lower limit and an upper limit,
    - **When** the actuator is set to an integer value within the specified range,
    - **Then** the actuator should successfully accept and return the value.


- **Scenario 4**: Test for Value Below Lower Limit
    - **Given** a configured actuator with a lower limit and an upper limit,
    - **When** the actuator is set to an integer value below the lower limit,
    - **Then** the actuator should reject the value and raise an error indicating the value is below the lower limit.


- **Scenario 5**: Test for Value Above Upper Limit
    - **Given** a configured actuator with a lower limit of an integer minimum and an upper limit of an integer maximum,
    - **When** the actuator is set to an integer value above the upper limit,
    - **Then** the actuator should reject the value and raise an error indicating the value is above the upper limit.