# Acceptance Criteria

## UC Add Room to House

### Acceptance Tests

- **Scenario 1:** The administrator wants to add a new room to the house for the first time.
    - **When** the administrator wants to add the first room to the house.
    - **Then** the system must be able to create the room from the administrator's input data.
    - **And** the system should be able to save the room.
    - **Then** the system should confirm the room was successfully created and stored by returning the saved room data.


- **Scenario 2:** The administrator wants to add a new room to the house (the house already has rooms).
    - **When** the administrator wants to add a new room to the house.
    - **Then** the system must be able to create the room from the administrator's input data.
    - **And** the system should be able to save the room.
    - **Then** the system should confirm the room was successfully created and stored by returning the saved room data.


- **Scenario 3:** The administrator wants to add a new room to the house with an invalid room name (room name is not
  unique).
    - **When** the administrator wants to add a new room to the house.
    - **Then** the system must be able to check if the room name is unique.
    - **And** if the room name is not unique, the system should return an error.


- **Scenario 4:** The administrator wants to add a new room to the house with invalid dimensions (width, height or
  length are zero or negative).
    - **When** the administrator wants to add a new room to the house.
    - **Then** the system must be able to check if the dimensions are not zero or negative.
    - **And** if the dimensions are zero or negative, the system should return an error.