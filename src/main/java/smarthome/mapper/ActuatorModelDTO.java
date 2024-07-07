package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * Represents a Data Transfer Object (DTO) for ActuatorModel entities.
 * <p>
 * This DTO class encapsulates the data of an ActuatorModel entity and provides
 * a convenient way to transfer this data between different layers of the application.
 * It contains the actuator model name and type name as immutable fields.
 * <p>
 */
public class ActuatorModelDTO extends RepresentationModel<ActuatorModelDTO> {

    private String actuatorModelName;
    private String actuatorTypeName;

    /**
     * Constructs an empty ActuatorModelDTO.
     */
    public ActuatorModelDTO() {
    }

    /**
     * Constructs an ActuatorModelDTO with the specified actuator model name
     * and actuator type name.
     *
     * @param actuatorModelName The name of the actuator model.
     * @param actuatorTypeName  The name of the actuator type associated with the model.
     */
    public ActuatorModelDTO(String actuatorModelName, String actuatorTypeName) {
        this.actuatorModelName = actuatorModelName;
        this.actuatorTypeName = actuatorTypeName;
    }


    /**
     * Retrieves the name of the actuator model.
     *
     * @return The name of the actuator model.
     */
    public String getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Retrieves the name of the actuator type associated with the model.
     *
     * @return The name of the actuator type.
     */
    public String getActuatorTypeName() {
        return actuatorTypeName;
    }
}
