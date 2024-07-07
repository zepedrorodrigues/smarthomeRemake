# Acceptance Criteria

## UC Deactivate a Device

- **Scenario 1**: The Power User [or Administrator] wants to deactivate a device.
    - **Given** that the Power User [or Administrator] knows the ID of the device.
    - **When** the Power User [or Administrator] deactivates the device.
    - **Then** the device is deactivated.

- **Scenario 2**: The Power User [or Administrator] tries to deactivate a device already deactivated.
    - **Given** that the Power User [or Administrator] knows the ID of the device.
    - **When** the Power User [or Administrator] tries to deactivate the device.
    - **Then** the device is not deactivated.
