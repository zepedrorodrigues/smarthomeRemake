package smarthome.domain.actuator;

import smarthome.domain.actuator.vo.ActuatorMap;

/**
 * Represents a factory for creating actuators.
 */
public interface ActuatorFactory {

    /**
     * Creates an actuator from the given actuator data.
     *
     * @param actuatorData the actuator data
     * @return the created actuator
     */
    Actuator createActuator(ActuatorMap actuatorData);

}
