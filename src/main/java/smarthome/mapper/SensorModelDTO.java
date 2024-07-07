package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data Transfer Object for SensorModel
 */
public class SensorModelDTO extends RepresentationModel<SensorModelDTO>{

     private String sensorModelName;
     private String sensorTypeId;


    /**
     * Constructs a new SensorModelDTO.
     */
    public SensorModelDTO() {
        // empty constructor
        }

    /**
     * Constructs a new SensorModelDTO with the given parameters.
     *
     * @param sensorModelName the name of the sensor model
     * @param sensorTypeId    the unique identifier of the sensor type
     */
    public SensorModelDTO(String sensorModelName, String sensorTypeId) {
        this.sensorModelName = sensorModelName;
        this.sensorTypeId = sensorTypeId;
    }

    /**
     * Returns the name of the sensor model.
     *
     * @return the name of the sensor model
     */
    public String getSensorModelName() {
        return sensorModelName;
    }

    /**
     * Returns the unique identifier of the sensor type.
     *
     * @return the unique identifier of the sensor type
     */
    public String getSensorTypeId() {
        return sensorTypeId;
    }
}
