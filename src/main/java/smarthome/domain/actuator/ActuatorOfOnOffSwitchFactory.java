package smarthome.domain.actuator;

import org.springframework.stereotype.Component;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;

/**
 * OnOffSwitchActuatorCreator is a factory class for creating ActuatorOfOnOffSwitch objects.
 * It implements the ActuatorFactory interface.
 */
@Component("actuatorOfOnOffSwitchFactory")
public class ActuatorOfOnOffSwitchFactory implements ActuatorFactory{

    /**
     * Creates an actuator from the given actuator data.
     *
     * @param actuatorData the actuator data
     * @return the created actuator
     */
    @Override
    public Actuator createActuator(ActuatorMap actuatorData) {
        DeviceId deviceId = actuatorData.getDeviceId();
        ActuatorModelName actuatorModelName = actuatorData.getActuatorModelName();
        if (actuatorData.getActuatorId() == null) {
            ActuatorOfOnOffSwitch actuator = new ActuatorOfOnOffSwitch(deviceId, actuatorModelName);
            return actuator;
        }
        ActuatorId actuatorId = actuatorData.getActuatorId();
        ActuatorOfOnOffSwitch actuator = new ActuatorOfOnOffSwitch(actuatorId, deviceId, actuatorModelName);
        return actuator;
    }
}
