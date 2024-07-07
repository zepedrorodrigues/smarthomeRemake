# Acceptance Criteria

## Get the peak power consumption of the house in a given period 

**Scenario 1:**

- **Given** the user is a Power User or Administrator.
- **When** the user requests the peak power consumption of the house in a given period.
- **And** the user specifies a valid period for the measurements.
- **And** there are measurements in the specified period.
- **Then** the system should give the peak power consumption of the house.

**Scenario 2:**

- **Given** the user is a Power User or Administrator.
- **When** the user requests the peak power consumption of the house in a given period.
- **And** the user specifies a valid period for the measurements.
- **If** there are no measurements in the specified period.
- **Then** the system should not give any values.

**Scenario 3:**

- **Given** the user is a Power User or Administrator.
- **When** the user requests the peak power consumption of the house in a given period.
- **And** the user specifies an invalid period for the measurements (e.g., start date after end date, end date after current date, invalid date format, start date and end date are the same).
- **Then** the system should not give any values.

**Scenario 4:**

- **Given** the user is a Power User or Administrator.
- **When** the user requests the peak power consumption of the house in a given period.
- **And** the user specifies a valid period for the measurements.
- **And** there is no device acting as the grid power meter.
- **Then** the system should not give any values.


