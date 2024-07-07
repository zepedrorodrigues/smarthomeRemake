package smarthome.service;


import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorTypeDTO;

import java.util.List;

/**
 * Interface for the SensorService class.
 */
public interface ISensorService {

    /**
     * Retrieves a list of all sensor types.
     */
    List<SensorTypeDTO> getSensorTypes();

    /**
     * Retrieves a list of all sensor models for a given sensor type.
     *
     * @param sensorTypeDTO
     */
    List<SensorModelDTO> getSensorModelsBySensorTypeIdentity(SensorTypeDTO sensorTypeDTO);

    /**
     * Adds a sensor to a device.
     *
     * @param sensorDTO
     */
    SensorDTO addSensor(SensorDTO sensorDTO);
}
