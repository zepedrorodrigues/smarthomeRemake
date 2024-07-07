package controllers;

import domain.House;

/**
 * This class represents the controller for the user story 04
 * This controller is responsible for the creation of a new Device type
 */
public class AddSensorTypeController {

    /**
     * House object
     */
    private House myHouse;

    /**
     * Constructor of the controller UC04
     */
    public AddSensorTypeController(House house) {
        this.myHouse = house;
        //Empty constructor because it is a controller, does not require parameters saved in attributes
    }

    /**
     * This method creates a new sensortype
     *
     * @param typeDTO dto with the information of the new sensortype
     * @return true if the sensortype was created successfully, false if not
     */

    public boolean defineSensorType(String typeDTO) {
        return myHouse.getCatalog().addSensorType(typeDTO);}
}