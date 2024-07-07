package smarthome.domain.actuator;


import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.Value;

import java.util.UUID;

/**
 * Represents an actuator of a blind roller.
 */
public class ActuatorOfBlindRoller implements Actuator {

    private final ActuatorId actuatorId;
    private final DeviceId deviceId;
    private final ActuatorModelName actuatorModelName;


    /**
     * Constructs a new ActuatorOfBlindRoller object with the given device id and actuator model name
     * The actuator id is randomly generated using UUID
     *
     * @param deviceId          the device id where this actuator is located
     * @param actuatorModelName the actuator model name
     */
    protected ActuatorOfBlindRoller(DeviceId deviceId, ActuatorModelName actuatorModelName) {
        if (deviceId == null || actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.deviceId = deviceId;
        this.actuatorModelName = actuatorModelName;
        this.actuatorId = new ActuatorId(UUID.randomUUID().toString());
    }

    /**
     * Constructs a new ActuatorOfBlindRoller object with the given actuator id, device id and actuator model name.
     *
     * @param actuatorId        the actuator id
     * @param deviceId          the device id where this actuator is located
     * @param actuatorModelName the actuator model name
     */
    protected ActuatorOfBlindRoller(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName) {
        if (actuatorId == null || deviceId == null || actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorModelName = actuatorModelName;
    }

    /**
     * Activates the ActuatorOfBlindRoller.
     * The actuator is activated if the value is between 0.0 and 100.0
     *
     * @param value The new state of the ActuatorOfBlindRoller.
     * @return The new state of the ActuatorOfBlindRoller.
     */
    public Value operate(Value value) {
        double valueDouble = Double.parseDouble(value.valueToString());
        if (valueDouble >= 0.0 && valueDouble <= 100.0) {
            return value;
        }
        return null;
    }

    /**
     * Returns the identity of the actuator
     *
     * @return the identity of the actuator
     */
    @Override
    public ActuatorId getIdentity() {
        return actuatorId;
    }

    /**
     * Returns the name of the Actuator.
     *
     * @return the name of the Actuator
     */
    @Override
    public ActuatorModelName getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Returns the DeviceId of the Actuator.
     *
     * @return the DeviceId of the Actuator
     */
    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Compares this ActuatorOfBlindRoller object with another object to determine if they are the same.
     *
     * @param object the object to compare
     * @return true if the objects are the same or have the same identity, otherwise false
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ActuatorOfBlindRoller that = (ActuatorOfBlindRoller) object;
        return actuatorId.equals(that.actuatorId);
    }

    /**
     * Compares this ActuatorOfBlindRoller object with another object to determine if they are equal.
     *
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public int hashCode() {
        return actuatorId.hashCode();
    }
}
