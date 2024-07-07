package smarthome.service;

import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorTypeDTO;

import java.util.List;

/**
 * This interface defines the contract for actuator services.
 * It provides methods to get actuator types, get actuator models based on a type, and add an actuator.
 */
public interface IActuatorService {

    /**
     * Gets a list of actuator types.
     *
     * @return a list of ActuatorTypeDTO objects.
     */
    List<ActuatorTypeDTO> getActuatorTypes();

    /**
     * Gets a list of actuator models for a given actuator type.
     *
     * @param actuatorTypeDTO the actuator type for which to get the models.
     * @return a list of ActuatorModelDTO objects.
     */
    List<ActuatorModelDTO> getActuatorModels(ActuatorTypeDTO actuatorTypeDTO);

    /**
     * Adds an actuator.
     *
     * @param actuatorDTO the actuator to add.
     * @return the added ActuatorDTO object.
     */
    ActuatorDTO addActuator(ActuatorDTO actuatorDTO);
}
