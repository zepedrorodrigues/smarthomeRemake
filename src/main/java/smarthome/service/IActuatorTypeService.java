package smarthome.service;

import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ActuatorType.
 */
public interface IActuatorTypeService {

    /**
     * Get all the actuator types.
     *
     * @return the list of entities.
     */
    Iterable<ActuatorType> getActuatorTypes();

    /**
     * Get all the actuator type ids.
     *
     * @return the list of actuator type ids.
     */
    List<ActuatorTypeName> getActuatorTypeIds();

    /**
     * Get the actuator type by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ActuatorType> getActuatorTypeById(ActuatorTypeName id);
}
