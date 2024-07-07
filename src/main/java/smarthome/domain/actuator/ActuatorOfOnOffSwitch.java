package smarthome.domain.actuator;

import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.LoadState;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.OnOffValue;
import smarthome.domain.sensor.vo.values.Value;

import java.util.UUID;

/**
 * This class represents an actuator of an on/off switch.
 * It implements the Actuator interface.
 */
public class ActuatorOfOnOffSwitch implements Actuator {

    private final ActuatorId actuatorId;
    private final DeviceId deviceId;
    private final ActuatorModelName actuatorModelName;
    private LoadState loadState;

    /**
     * Constructor for the ActuatorOfOnOffSwitch class.
     *
     * @param deviceId          The ID of the device.
     * @param actuatorModelName The model name of the actuator.
     * @throws IllegalArgumentException If the deviceId or actuatorModelName is null.
     */
    protected ActuatorOfOnOffSwitch(DeviceId deviceId, ActuatorModelName actuatorModelName) {
        if (deviceId == null || actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.actuatorId = new ActuatorId(UUID.randomUUID().toString());
        this.deviceId = deviceId;
        this.actuatorModelName = actuatorModelName;
        this.loadState = new LoadState(false);
    }

    /**
     * Constructor for the ActuatorOfOnOffSwitch class.
     *
     * @param actuatorId        The ID of the actuator.
     * @param deviceId          The ID of the device.
     * @param actuatorModelName The model name of the actuator.
     * @throws IllegalArgumentException If the actuatorId, deviceId, actuatorModelName or loadState is null.
     */
    protected ActuatorOfOnOffSwitch(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName) {
        if (actuatorId == null || deviceId == null || actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorModelName = actuatorModelName;
        this.loadState = new LoadState(false);

    }

    /**
     * Returns the identity of the actuator.
     *
     * @return The actuator ID.
     */
    @Override
    public ActuatorId getIdentity() {
        return actuatorId;
    }

    /**
     * Operates the actuator with the given value.
     *
     * @param value The value to operate the actuator with.
     * @return The new value of the actuator.
     * @throws IllegalArgumentException If the value is not valid.
     */
    public Value operate(Value value) {
        boolean isValueOn = Boolean.parseBoolean(value.valueToString());
        if (!isValidValue(value)) {
            throw new IllegalArgumentException();
        }
        if (isValueOn) {
            this.loadState = new LoadState(true);
            return new OnOffValue(true);
        } else {
            this.loadState = new LoadState(false);
            return new OnOffValue(false);
        }
    }

    /**
     * Checks if the given value is valid.
     *
     * @param value The value to check.
     * @return True if the value is valid, false otherwise.
     */
    private boolean isValidValue(Value value) {
        String valueString = value.valueToString().toLowerCase();
        return "true".equals(valueString) || "false".equals(valueString);
    }

    /**
     * Compares this ActuatorOfOnOffSwitch to the specified object.
     * The result is true if and only if the argument is not null and is an ActuatorOfOnOffSwitch object that
     * represents the same actuator as this object.
     *
     * @param object The object to compare this ActuatorOfOnOffSwitch against.
     * @return True if the given object represents an ActuatorOfOnOffSwitch equivalent to this ActuatorOfOnOffSwitch,
     * false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ActuatorOfOnOffSwitch that = (ActuatorOfOnOffSwitch) object;
        return actuatorId.equals(that.actuatorId);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return actuatorId.hashCode();
    }

    /**
     * Returns the model name of the actuator.
     *
     * @return The model name of the actuator.
     */
    @Override
    public ActuatorModelName getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Returns the device ID of the actuator.
     *
     * @return The device ID of the actuator.
     */
    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the load state of the actuator.
     *
     * @return The load state of the actuator.
     */
    public LoadState getLoadState() {
        return loadState;
    }
}
