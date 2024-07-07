package smarthome.domain.actuatortype;

import org.springframework.stereotype.Component;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

/**
 * This class is an implementation of the ActuatorTypeFactory interface.
 * It is responsible for creating instances of ActuatorType.
 */
@Component
public class ActuatorTypeFactoryImpl implements ActuatorTypeFactory {

    /**
     * This method creates a new instance of ActuatorType.
     *
     * @param actuatorTypeName The name of the actuator type to be created.
     * @return A new instance of ActuatorType with the provided name.
     */
    public ActuatorType createActuatorType(ActuatorTypeName actuatorTypeName) {
        return new ActuatorType(actuatorTypeName);
    }
}