package smarthome.domain.actuator;

import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.IntLimit;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.Value;

import java.util.UUID;

/**
 * This class represents an actuator with a lower and upper limit.
 * The actuator operates within these limits and is identified by a unique ActuatorId.
 */
public class ActuatorOfLimiter implements Actuator {

    IntLimit lowerLimit;

    IntLimit upperLimit;

    ActuatorModelName actuatorModelName;

    DeviceId deviceId;

    ActuatorId actuatorId;

    /**
     * Constructs an ActuatorOfLimiter with the specified lower limit, upper limit, model name, and device id.
     * @param lowerLimit the lower limit of the actuator
     * @param upperLimit the upper limit of the actuator
     * @param actuatorModelName the model name of the actuator
     * @param deviceId the device id of the actuator
     * @throws IllegalArgumentException if any of the parameters are null or if the lower limit is not less than the upper limit
     */
    protected ActuatorOfLimiter(IntLimit lowerLimit, IntLimit upperLimit, ActuatorModelName actuatorModelName, DeviceId deviceId) {
        this(null, lowerLimit, upperLimit, actuatorModelName, deviceId);
    }

    /**
     * Constructs an ActuatorOfLimiter with the specified actuator id, lower limit, upper limit, model name, and device id.
     * If the actuator id is null, a new actuator id is generated.
     * @param actuatorId the actuator id of this actuator. If null, a new actuator id is generated.
     * @param lowerLimit the lower limit of the actuator
     * @param upperLimit the upper limit of the actuator
     * @param actuatorModelName the model name of the actuator
     * @param deviceId the device id of the actuator
     * @throws IllegalArgumentException if any of the parameters are null or if the lower limit is not less than the upper limit
     */
    protected ActuatorOfLimiter(ActuatorId actuatorId, IntLimit lowerLimit, IntLimit upperLimit, ActuatorModelName actuatorModelName, DeviceId deviceId) {
        if (lowerLimit == null || upperLimit == null || actuatorModelName == null || deviceId == null ) {
            throw new IllegalArgumentException();
        }
        if (!validLimits(lowerLimit, upperLimit)) {
            throw new IllegalArgumentException();
        }
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.actuatorModelName = actuatorModelName;
        this.deviceId = deviceId;
        if (actuatorId != null) {
            this.actuatorId = actuatorId;
        } else {
            this.actuatorId = new ActuatorId(UUID.randomUUID().toString());
        }
    }

    /**
     * Checks if the lower limit is less than the upper limit.
     * @param lowerLimit the lower limit to check
     * @param upperLimit the upper limit to check
     * @return true if the lower limit is less than the upper limit, false otherwise
     */
    private boolean validLimits(IntLimit lowerLimit, IntLimit upperLimit) {
        return lowerLimit.getIntLimit() < upperLimit.getIntLimit();
    }

    /**
     * Retrieves the lower limit of the actuator.
     * @return the lower limit of the actuator
     */
    public IntLimit getLowerLimit() {
        return lowerLimit;
    }

    /**
     * Retrieves the upper limit of the actuator.
     * @return the upper limit of the actuator
     */
    public IntLimit getUpperLimit() {
        return upperLimit;
    }

    /**
     * Retrieves the model name of the actuator.
     * @return the model name of the actuator
     */
    public ActuatorModelName getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Retrieves the device id of the actuator.
     * @return the device id of the actuator
     */
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Retrieves the actuator id of the actuator.
     * @return the actuator id of the actuator
     */
    public ActuatorId getIdentity() {
        return actuatorId;
    }

    /**
     * Checks if the provided object is equal to this ActuatorOfLimiter.
     * The equality is determined by comparing the actuatorId of the provided object with this ActuatorOfLimiter's
     * actuatorId.
     *
     * @param object the object to be compared for equality with this ActuatorOfLimiter
     * @return true if the provided object is of type ActuatorOfLimiter and its actuatorId is equal to this
     * ActuatorOfLimiter's actuatorId, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ActuatorOfLimiter that = (ActuatorOfLimiter) object;
        return actuatorId.equals(that.actuatorId);
    }

    /**
     * Returns a hash code value for this ActuatorOfLimiter.
     * The hash code is generated based on the actuatorId of this ActuatorOfLimiter.
     *
     * @return a hash code value for this ActuatorOfLimiter
     */
    @Override
    public int hashCode() {
        return actuatorId.hashCode();
    }

    /**
     * Operates the actuator within the specified value.
     * If the value is within the lower and upper limits, the value is returned.
     * If the value is outside the limits, null is returned.
     * @param value the value to operate within
     * @return the value if it is within the limits, null otherwise
     * @throws NumberFormatException if the value cannot be parsed to an integer
     */
    public Value operate(Value value) throws NumberFormatException {
        int valueInt = Integer.parseInt(value.valueToString());
        if (valueInt >= lowerLimit.getIntLimit() && valueInt <= upperLimit.getIntLimit()) {
            return value;
        }

        return null;
    }

}
