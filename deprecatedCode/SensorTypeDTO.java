package dto;

import java.util.List;

/**
 * This class is a Data Transfer Object (dto) for SensorType.
 * It is used to transfer data about sensor types between processes or layers in an application.
 * The SensorTypeDTO class includes a field for the list of sensor types.
 */
public class SensorTypeDTO {

    /**
     * The list of sensor types.
     */
    List<String> sensorTypes;

    /**
     * Constructs a new SensorTypeDTO with the given list of sensor types.
     *
     * @param sensorTypes the list of sensor types
     */
    public SensorTypeDTO(List<String> sensorTypes) {
        this.sensorTypes = sensorTypes;
    }

    /**
     * Returns the list of sensor types.
     *
     * @return the list of sensor types
     */
    public List<String> getSensorTypes() {
        return sensorTypes;
    }
}
