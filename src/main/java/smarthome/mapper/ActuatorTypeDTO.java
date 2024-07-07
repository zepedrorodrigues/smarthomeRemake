package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * This class represents a Data Transfer Object (DTO) for an actuator type.
 * It is used to transfer data between processes or across network connections.
 * In this case, it is used to transfer the name of an actuator type.
 */
public class ActuatorTypeDTO extends RepresentationModel<ActuatorTypeDTO> {

    private String actuatorTypeName;

    /**
     * Constructs an empty ActuatorTypeDTO.
     */

    public ActuatorTypeDTO() {
    }

    /**
     * Constructs a new ActuatorTypeDTO with the specified actuator type name.
     *
     * @param actuatorTypeName the name of the actuator type
     */
    public ActuatorTypeDTO(String actuatorTypeName) {
        this.actuatorTypeName = actuatorTypeName;
    }

    /**
     * Returns the name of the actuator type.
     *
     * @return the name of the actuator type
     */
    public String getActuatorTypeName() {
        return actuatorTypeName;
    }
}
