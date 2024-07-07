# Acceptance Criteria

## Configure House Location

**Scenario 1:** The administrator wants to configure the location of the house for the first time

- **When** the administrator wants to configure the location of the house.
- **Then** the system must allow the administrator to configure the location of the house.
- **And** the system must store the location of the house.
- **And** the system must confirm the location of the house was stored by returning the house with the new location.

**Scenario 2:** The administrator wants to configure the location of the house after the first time the location was
configured

- **When** the administrator wants to configure the location of the house for the second time.
- **Then** the system must allow the administrator to configure the location of the house.
- **And** the system must store the new location of the house.
- **And** the system must confirm the location of the house was stored by returning the house with the new location.

**Scenario 3:** The administrator wants to configure the location of the house with invalid parameters (such as blank,
null or invalid values that do not match the rules, syntax, or semantics)

- **When** the administrator wants to configure the location of the house with invalid parameters.
- **Then** the system must not allow the administrator to configure the location of the house.
- **And** the system must not store the location of the house.
- **And** the system must confirm the location of the house was not stored by returning an error message.

**Scenario 4:** Test the persistence of the location of the house

- **Given** the administrator has configured the location of the house.
- **When** the HouseRepository is called to retrieve the house that was configured
- **And** the house is retrieved
- **Then** the house must have the location that was configured, if the location were valid.
- **Or** the house must not have the location th at was configured if the location were invalid.
