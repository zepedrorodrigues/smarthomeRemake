# Acceptance Criteria

## UC - Open/Close Blind Roller

### Acceptance Tests

**User:** Room Owner or Administrator or Power User

- **Scenario 1:** The user wants to open or close a blind roller with a valid percentage value.
    - **When** the user provides a valid percentage value.
    - **Then** the system must be able to open or close the blind roller to the provided percentage value.
    - **And** the system should confirm the blind roller was successfully opened or closed by returning the updated percentage 
      value.


- **Scenario 2:** The user wants to close a blind roller with percentage value equal to the current percentage value.
  - **When** the user provides a percentage value equal to the current percentage value.
  - **Then** the system should deny the request and return an error status code.


- **Scenario 3:** The user wants to close a blind roller with a percentage value less than 0.
  - **When** the user provides a negative percentage value to open or close the blind roller.
  - **Then** the system should deny the request and return an error status code.


- **Scenario 4:** The user wants to close a blind roller with an invalid format percentage value.
  - **When** the user provides a percentage value in an invalid format to open or close the blind roller.
  - **Then** the system should deny the request and return an error status code.


- **Scenario 5:** The user wants to close a blind roller with a percentage value higher than 100.
  - **When** the user provides a percentage value higher than 100 to open or close the blind roller.
  - **Then** the system should deny the request and return an error status code.