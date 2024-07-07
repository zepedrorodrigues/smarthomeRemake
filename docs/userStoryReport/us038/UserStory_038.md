# US038 - Sunset and Sunrise (Web App)

### Table of Contents

1. [Requirements](#1-requirements)
    - [Dependency on existing use cases and system components](#dependency-on-existing-use-cases-and-system-components)
2. [Acceptance Criteria](#2-acceptance-criteria)
3. [Backend and External Services Integration](#3-backend-and-external-services-integration)
4. [Input validation](#4-input-validation)
5. [Application Screenshots and Navigation Flow](#4-application-screenshots-and-navigation-flow)
6. [Conclusion](#5-conclusion)

## 1. Requirements

_As a User, I want to have today’s sunrise and sunset hours on the front/main
page of my Web App._

Requirements:

- The Web App should display the sunrise hour for the current day, on the home page.
- The Web App should display the sunrise hour for the current day, on the home page.

### Dependency on existing use cases and system components

- **Weather Service**: The Web App will need to use the Weather Service to get the sunrise and sunset hours for the
  current day.

## 2. Acceptance Criteria

- **Scenario 1:** User opens the app and the main page is displayed.
    - **Given** the user has opened the app
    - **Then** the main page is displayed
    - **And** the sunrise and sunset times are displayed

## 3. Backend and External Services Integration

To add a device, the frontend sends a POST request to the backend with the device details. The endpoint for adding a
device is: `POST http://10.9.24.232:8080/switch2023project_g6-1.0-SNAPSHOT/devices/room/${roomId}`. If successful, the new
device is added to the application's state and displayed to the user.

## 4. Input validation

This feature does not require any input validation as the data is fetched from the Weather Service API and displayed
on the main page of the Web App.

## 5. Application Screenshots and Navigation Flow

The following screenshot shows the main page of the Web App with the sunrise and sunset times displayed:

<img src="https://i.postimg.cc/nzhWXcjT/image.png">

## 6. Conclusion

The Web App now displays the sunrise and sunset times on the main page, providing users with the information they need
at a glance. The integration with the Weather Service API ensures that the data is accurate and up-to-date. The
implementation of this feature enhances the user experience and makes the Web App more informative and useful.

[Back to Top](#us038---sunset-and-sunrise-web-app)