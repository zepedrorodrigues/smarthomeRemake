# Acceptance Criteria

## Actuator On/Off Switch

### Acceptance Tests

- **Scenario 1**: Turning the load ON
    - **Given** the system contains an actuator for load ON/OFF.
    - **When** a command is issued to turn the load ON.
    - **Then** the actuator should switch the load ON successfully.

- **Scenario 2**: Turning the load OFF
    - **Given** the system contains an actuator for load ON/OFF.
    - **When** a command is issued to turn the load OFF.
    - **Then** the actuator should switch the load OFF successfully.

- **Scenario 3**: Turning the load ON with invalid data
    - **Given** the system contains an actuator for load ON/OFF.
    - **When** a command is issued to turn the load ON with invalid data.
    - **Then** the actuator should not switch the load ON.
    - **And** an appropriate error message should be displayed indicating the failure.

- **Scenario 4**: Turning the load OFF with invalid data
    - **Given** the system contains an actuator for load ON/OFF.
    - **When** a command is issued to turn the load OFF with invalid data.
    - **Then** the actuator should not switch the load OFF.
    - **And** an appropriate error message should be displayed indicating the failure.

