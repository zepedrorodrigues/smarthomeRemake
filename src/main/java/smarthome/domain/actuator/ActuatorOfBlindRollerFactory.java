package smarthome.domain.actuator;

import org.springframework.stereotype.Component;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;

/**
 * Represents a factory for creating actuators of blind roller.
 */
@Component("actuatorOfBlindRollerFactory")
public class ActuatorOfBlindRollerFactory implements ActuatorFactory {


    /**
     * Creates an actuator of blind roller.
     * Need to extract the device id and actuator model name from the actuator data.
     *
     * @param actuatorData the actuator data, which contains the device id and actuator model name
     * @return the created actuator of blind roller
     */
    @Override
    public Actuator createActuator(ActuatorMap actuatorData) {
        DeviceId deviceId = actuatorData.getDeviceId();
        ActuatorModelName actuatorModelName = actuatorData.getActuatorModelName();
        if (actuatorData.getActuatorId() == null) {
            ActuatorOfBlindRoller actuator = new ActuatorOfBlindRoller(deviceId, actuatorModelName);
               return actuator;
        }
        ActuatorId actuatorId = actuatorData.getActuatorId();
        ActuatorOfBlindRoller actuator = new ActuatorOfBlindRoller(actuatorId, deviceId, actuatorModelName);
        return actuator;
    }


}
