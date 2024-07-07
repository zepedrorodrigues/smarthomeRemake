package smarthome.domain.actuator;

import smarthome.domain.actuator.vo.ActuatorId;
import org.springframework.stereotype.Component;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuator.vo.IntLimit;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;

/**
 * This class is a factory for creating ActuatorOfLimiter objects.
 * It implements the ActuatorFactory interface.
 */
@Component("actuatorOfLimiterFactory")
public class ActuatorOfLimiterFactory implements ActuatorFactory {

    /**
     * This method creates an ActuatorOfLimiter object.
     * It takes an ActuatorMap object as input, which contains the necessary data for creating the Actuator.
     *
     * @param actuatorData An ActuatorMap object containing the necessary data for creating the Actuator.
     * @return An ActuatorOfLimiter object.
     */
    @Override
    public Actuator createActuator(ActuatorMap actuatorData) {
        DeviceId deviceId = actuatorData.getDeviceId();
        ActuatorModelName actuatorModelName = actuatorData.getActuatorModelName();
        IntLimit lowerLimit = actuatorData.getIntegerLowerLimit();
        IntLimit upperLimit = actuatorData.getIntegerUpperLimit();
        if (actuatorData.getActuatorId() == null) {
            ActuatorOfLimiter actuator = new ActuatorOfLimiter(lowerLimit, upperLimit, actuatorModelName, deviceId);
            return actuator;
        }
        ActuatorId actuatorId = actuatorData.getActuatorId();
        ActuatorOfLimiter actuator = new ActuatorOfLimiter(actuatorId, lowerLimit, upperLimit, actuatorModelName, deviceId);
        return actuator;
    }

}