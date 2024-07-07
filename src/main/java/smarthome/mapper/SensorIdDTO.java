package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data transfer object for the id of a sensor.
 */
public class SensorIdDTO extends RepresentationModel<SensorIdDTO> {

    private final String sensorId;

    /**
     * Constructs a new SensorIdDTO from the given sensorId.
     *
     * @param sensorId the id of the sensor
     */
    public SensorIdDTO(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * Get id of the sensor.
     *
     * @return the id of the sensor
     */
    public String getSensorId() {
        return this.sensorId;
    }
}
