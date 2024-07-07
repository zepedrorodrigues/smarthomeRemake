package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a sensor type in a smart home system. This class provides details about the sensor type,
 * including its unique identifier, name, and the unit of measurement it uses.
 */

public class SensorTypeDTO extends RepresentationModel<SensorTypeDTO> {


    private String sensorTypeId;

    /**
     * The name of the sensor type.
     */
    private String sensorTypeName;

    /**
     * The unit of measurement used by the sensor type.
     */
    private String sensorTypeUnit;


    /**
     * Constructs a new SensorTypeDTO.
     */
    public SensorTypeDTO() {

    }

    /**
     * Constructs a new SensorTypeDTO with the specified details.
     *
     * @param sensorTypeId   the unique identifier for the sensor type
     * @param sensorTypeName the name of the sensor type
     * @param sensorTypeUnit the unit of measurement used by the sensor type
     */
    public SensorTypeDTO(String sensorTypeId, String sensorTypeName, String sensorTypeUnit) {
        this.sensorTypeId = sensorTypeId;
        this.sensorTypeName = sensorTypeName;
        this.sensorTypeUnit = sensorTypeUnit;
    }

    /**
     * Constructs a new SensorTypeDTO with the specified unique identifier.
     * @param sensorTypeId the unique identifier for the sensor type
     */
    public SensorTypeDTO(String sensorTypeId){
        this.sensorTypeId = sensorTypeId;
        this.sensorTypeName = null;
        this.sensorTypeUnit = null;
    }

    /**
     * Returns the unique identifier of the sensor type.
     *
     * @return the sensor type's unique identifier
     */
    public String getSensorTypeId() {
        return sensorTypeId;
    }

    /**
     * Returns the name of the sensor type.
     *
     * @return the sensor type's name
     */
    public String getSensorTypeName() {
        return sensorTypeName;
    }

    /**
     * Returns the unit of measurement used by the sensor type.
     *
     * @return the sensor type's unit of measurement
     */
    public String getSensorTypeUnit() {
        return sensorTypeUnit;
    }
}
