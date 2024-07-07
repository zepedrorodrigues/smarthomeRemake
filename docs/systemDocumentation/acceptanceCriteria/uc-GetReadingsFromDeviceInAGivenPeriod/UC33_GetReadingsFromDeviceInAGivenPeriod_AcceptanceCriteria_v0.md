# Acceptance Criteria

## UC Get Readings from Device in a Given Period

- **Scenario 1:**
    - **Given** the user is a Room Owner, Power User, or Administrator.
    - **When** the user requests a list of all measurements of a device in a room.
    - **And** the user specifies a valid period for the measurements.
    - **Then** the system should give a list of all measurements of the device in the room.


- **Scenario 2:**
    - **Given** the user is a Room Owner, Power User, or Administrator.
    - **When** the user requests a list of all measurements of a device in a room.
    - **And** the user specifies a period for the measurements.
    - **If** there are no measurements in the specified period.
    - **Then** the system should give an empty list of measurements.


- **Scenario 3:**
    - **Given** the user is a Room Owner, Power User, or Administrator.
    - **When** the user requests a list of all measurements of a device in a room.
    - **And** he user gives an invalid period for the measurements (start date after end date/ end date after current
      date/ same start and end date).
    - **Then** the system should not give a list of all measurements of the device in the room.


- **Scenario 4:**
    - **Given** the user is a Room Owner, Power User, or Administrator.
    - **When** the user requests a list of all measurements of a device in a room.
    - **And** the user specifies a device without any associated sensors.
    - **Then** the system should not give a list of all measurements of the device in the room.