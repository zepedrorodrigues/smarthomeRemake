package smarthome.domain.actuatortype;

import smarthome.domain.actuatortype.vo.ActuatorTypeName;

/**
 * A factory interface for creating ActuatorType objects.
 */
public interface ActuatorTypeFactory {

    /**
     * Creates an ActuatorType object with the specified actuator type name.
     *
     * @param actuatorTypeName the actuator type name
     * @return an ActuatorType object with the specified actuator type name
     */
    public ActuatorType createActuatorType(ActuatorTypeName actuatorTypeName);


}
