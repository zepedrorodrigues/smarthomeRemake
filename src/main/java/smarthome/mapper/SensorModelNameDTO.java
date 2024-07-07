package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a Data Transfer Object (DTO) for the SensorModelName.
 * This class extends the RepresentationModel class from Spring HATEOAS, allowing it to be easily converted to JSON
 * or XML.
 */
public class SensorModelNameDTO extends RepresentationModel<SensorModelNameDTO> {

    private String sensorModelName;

    /**
     * Constructs a new SensorModelNameDTO with the specified sensor model name.
     *
     * @param sensorModelName the name of the sensor model
     */
    public SensorModelNameDTO(String sensorModelName) {
        this.sensorModelName = sensorModelName;
    }

    /**
     * Returns the name of the sensor model.
     *
     * @return the name of the sensor model
     */
    public String getSensorModelName() {
        return sensorModelName;
    }
}
