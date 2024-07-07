package smarthome.domain.actuator;

import smarthome.ddd.AggregateRoot;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;

/**
 * Represents an Actuator in the domain.
 */
public interface Actuator extends AggregateRoot<ActuatorId> {

    /**
     * Returns the name of the Actuator.
     *
     * @return the name of the Actuator
     */
    ActuatorModelName getActuatorModelName();

    /**
     * Returns the DeviceId of the Actuator.
     *
     * @return the DeviceId of the Actuator
     */
    DeviceId getDeviceId();

}
