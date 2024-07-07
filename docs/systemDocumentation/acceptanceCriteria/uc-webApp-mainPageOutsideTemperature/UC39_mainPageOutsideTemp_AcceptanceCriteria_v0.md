# Acceptance Criteria

## UC: Display Outside Temperature in the main page

- **Scenario 1:** User opens the app and the main page is displayed.
    - **Given** the user has opened the app
    - **Then** the main page is displayed
    - **And** the outside temperature is displayed in Celsius
    - **And** is updated every 15 minutes, starting at 00:00


- **Scenario 2:** User opens the app and the main page is displayed.
    - **Given** the user has opened the app
    - **Then** the main page is displayed
    - **When** there is no connection to the server
    - **Then** the outside temperature is displayed as "Loading..." until the connection is restored