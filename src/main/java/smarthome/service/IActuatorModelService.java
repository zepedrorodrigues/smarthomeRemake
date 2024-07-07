package smarthome.service;

import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

import java.util.List;
import java.util.Optional;

/**
 * Service interface used to define the methods for managing actuator models.
 */
public interface IActuatorModelService {

    /**
     * Gets all actuator models in the repository for a given actuator type.
     *
     * @param actuatorTypeName the actuator type for which to get the models.
     * @return an iterable containing all actuator models in the repository for a given actuator type.
     */

    Iterable<ActuatorModel> getActuatorModels(ActuatorTypeName actuatorTypeName);
    /**
     * Gets an actuator model by its name.
     *
     * @param actuatorModelName the name of the actuator model to get.
     * @return an optional containing the actuator model if found, or an empty optional otherwise.
     */
    Optional<ActuatorModel> getActuatorModelByName(ActuatorModelName actuatorModelName);

    /**
     * Gets a list of actuator models ids for a given actuator type.
     *
     * @param actuatorTypeName the actuator type for which to get the models.
     * @return a list of ActuatorModelName objects.
     */
    List<ActuatorModelName> getActuatorModelsByActuatorTypeIdentity(ActuatorTypeName actuatorTypeName);
}
