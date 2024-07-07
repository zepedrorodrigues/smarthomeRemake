package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Data transfer object for the id of an actuator.
 */
public class ActuatorIdDTO extends RepresentationModel<ActuatorIdDTO> {

    private final String actuatorId;


    /**
     * Constructs a new ActuatorIdDTO from the given actuatorId.
     *
     * @param actuatorId the id of the actuator
     */
    public ActuatorIdDTO(String actuatorId) {
        this.actuatorId = actuatorId;
    }

    /**
     * Get id of the actuator.
     *
     * @return the id of the actuator
     */
    public String getActuatorId() {
        return this.actuatorId;
    }
}
