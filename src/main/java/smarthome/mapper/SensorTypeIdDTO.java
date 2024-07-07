package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a Data Transfer Object (DTO) for the SensorTypeId.
 * This class extends the RepresentationModel class from Spring HATEOAS, allowing it to be easily converted to JSON or XML.
 */
public class SensorTypeIdDTO extends RepresentationModel<SensorTypeIdDTO> {

    private String sensorTypeId;

    /**
     * Default constructor for SensorTypeIdDTO.
     */
    public SensorTypeIdDTO() {
    }

    /**
     * Constructs a new SensorTypeIdDTO with the specified sensor type ID.
     *
     * @param sensorTypeId the unique identifier for the sensor type
     */
    public SensorTypeIdDTO(String sensorTypeId) {
        this.sensorTypeId = sensorTypeId;
    }

    /**
     * Returns the unique identifier of the sensor type.
     *
     * @return the sensor type's unique identifier
     */
    public String getSensorTypeId() {
        return sensorTypeId;
    }
}
