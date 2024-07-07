# Acceptance Criteria

## Actuator of Blind Roller

**Scenario 1**: The actuator is created with valid attributes.

- **Given** that the actuator is created with a unique identifier, model name, and device identifier.
- **When** the actuator is created.
- **Then** the actuator should be successfully created.

**Scenario 2**: The actuator of the blind roller operate when the blind roller value is between 0 and 100.

- **Given** that the blind roller value is between 0 and 100.
- **When** the actuator of the blind roller called to operate.
- **Then** the actuator should operate the blind roller, but now returning the value.

**Scenario 3**: The actuator of the blind roller operate when the blind roller value is lower than 0.

- **Given** that the blind roller value is lower than 0.
- **When** the actuator of the blind roller called to operate.
- **Then** the actuator should not operate the blind roller, returning null.

**Scenario 4**: The actuator of the blind roller operate when the blind roller value is greater than 100.

- **Given** that the blind roller value is greater than 100.
- **When** the actuator of the blind roller called to operate.
- **Then** the actuator should not operate the blind roller, returning null.
