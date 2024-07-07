# Acceptance Criteria

# UC34 - Get the maximum instantaneous temperature difference between a device in the room and the outside, in a given period.

- **Scenario 1:**
    - **Given** the user is a Room Owner, Power User, or Administrator.
    - **When** the user requests the maximum instantaneous temperature difference between a device in the room and the
      outside.
    - **And** the user specifies a valid period for the measurements.
    - **And** there are measurements in the specified period.
    - **Then** the system should give the maximum instantaneous temperature difference between a device in the room and
      the outside.


- **Scenario 2:**
  - **Given** the user is a Room Owner, Power User, or Administrator.
  - **When** the user requests the maximum instantaneous temperature difference between a device in the room and the
    outside.
  - **And** the user specifies a valid period for the measurements.
  - **If** there are no measurements in the specified period.
  - **Then** the system should not give any values.


- **Scenario 3:**
  - **Given** the user is a Room Owner, Power User, or Administrator.
  - **When** the user requests the maximum instantaneous temperature difference between a device in the room and the
    outside.
  - **And** he user gives an invalid period for the measurements
    - **As** Start date after end date.
    - **As** End date after current date.
    - **As** Invalid date format.
    - **As** Start date and end date are the same.
  - **Then** the system should not give any values.


- **Scenario 4:**
    - **Given** the user is a Room Owner, Power User, or Administrator.
    - **When** the user requests the maximum instantaneous temperature difference between a device in the room and the
      outside.
    - **And** the user specifies a valid period for the measurements.
    - **And** the user specifies a invalid device.
    - **As** The device does not have sensor of type temperature.
  - **As** The device does not exist in the system.
    - **Then** the system should not give any values.