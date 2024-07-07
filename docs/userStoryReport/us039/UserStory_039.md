# US039 -  Display Outside Temperature in the main page (Web App)

### Table of Contents

1. [Requirements](#1-requirements)
    - [Dependency on existing use cases and system components](#dependency-on-existing-use-cases-and-system-components)
2. [Acceptance Criteria](#2-acceptance-criteria)
3. [Backend and External Services Integration](#3-backend-and-external-services-integration)
4. [Input validation](#4-input-validation)
5. [Application Screenshots and Navigation Flow](#4-application-screenshots-and-navigation-flow)
6. [Conclusion](#5-conclusion)

## 1. Requirements

_As a User, I want to have current outside temperature on the front/main page of
my Web App (updated every 15 minutes, starting at 00:00)._

Requirements:

- The Web App should display the current outside temperature for the user's location on the home page.
- The temperature should be updated every 15 minutes, starting at 00:00.
- The temperature should be displayed in degrees Celsius.


### Dependency on existing use cases and system components

- **Weather Service**: The Web App will need to use the Weather Service to get the current outside temperature for the user's location.

## 2. Acceptance Criteria

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


## 3. Backend and External Services Integration

We utilize a weather service API to fetch accurate outside temperature based on the user's location. 
The integration involves fetching data from the API and displaying it on the main page of the web application.
The frontend component fetches the outside temperature by making a GET request to the API endpoint:
`GET http://10.9.24.170:8080/InstantaneousTemperature?groupNumber=6&hour=${currentHour}`

## 4. Input validation

- This feature does not require any user input validation as it is a read-only operation.

## 5. Application Screenshots and Navigation Flow

The following screenshot shows the main page of the Web App with the outside temperature displayed:

The outside temperature is displayed in degrees Celsius and is updated every 15 minutes, starting at 00:00.

<img src="https://i.postimg.cc/nzhWXcjT/image.png">

## 6. Conclusion

The Web App now displays the outside temperature on the main page, providing users with the information they need at a glance. 
The integration with the Weather Service API ensures that the data is accurate and up-to-date. 
The implementation of this feature enhances the user experience and makes the Web App more informative and useful.

[Back to Top](#us039---display-outside-temperature-in-the-main-page-web-app)


    

