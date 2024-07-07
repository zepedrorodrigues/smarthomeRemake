package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;


/**
 * This class represents a DTO for the name of an actuator model.
 */
public class ActuatorModelNameDTO extends RepresentationModel<ActuatorModelNameDTO> {

    private String actuatorModelName;

    /**
     * Constructs a new ActuatorModelNameDTO with the specified actuator model name.
     *
     * @param actuatorModelName the name of the actuator model
     */
    public ActuatorModelNameDTO(String actuatorModelName) {
        this.actuatorModelName = actuatorModelName;
    }

    /**
     * Retrieves the name of the actuator model.
     *
     * @return The name of the actuator model.
     */
    public String getActuatorModelName() {
        return actuatorModelName;
    }
}
