package smarthome.domain.actuatormodel;

import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;


/**
 * A factory interface for creating ActuatorModel objects.
 */
public interface ActuatorModelFactory {

    /**
     * Creates a new ActuatorModel object.
     *
     * @param actuatorModelName the name of the actuator model
     * @param actuatorTypeName    the type id of the actuator model
     * @return a new ActuatorModel object
     */
    ActuatorModel createActuatorModel(ActuatorModelName actuatorModelName, ActuatorTypeName actuatorTypeName);
}
