# Acceptance Criteria

## UC - Close Blind Roller

### Acceptance Tests

**User:** Room Owner or Administrator or Power User

- **Scenario 1:** The user wants to close a blind roller with a valid percentage value.
    - **When** the user provides a valid percentage value to close the blind roller.
    - **And** the blind roller is not closed more than the specified percentage value.
    - **Then** the system must be able to close the blind roller to the provided percentage value.
    - **And** the system should confirm the blind roller was successfully closed by returning the updated percentage 
      value.


- **Scenario 2:** The user attempts to close a blind roller to a percentage higher than it already is closed.
  - **When** the user provides a valid percentage value to close the blind roller.
  - **And** the blind roller is closed more than the specified percentage value.
  - **Then** the system must return the current percentage value without updating.


- **Scenario 3:** The user attempts to close a blind roller that's already closed.
  - **When** the user provides a valid percentage value to close the blind roller.
  - **And** the blind roller is already closed.
  - **Then** the system must return the current percentage value without updating.


- **Scenario 4:** The user wants to close a blind roller with a negative percentage value.
  - **When** the user provides a negative percentage value to close the blind roller.
  - **Then** the system should deny the request and return an error status code.


- **Scenario 5:** The user wants to close a blind roller with an invalid format percentage value.
  - **When** the user provides a percentage value in an invalid format to close the blind roller.
  - **Then** the system should deny the request and return an error status code.