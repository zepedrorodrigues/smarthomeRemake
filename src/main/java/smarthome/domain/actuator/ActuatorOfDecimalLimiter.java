package smarthome.domain.actuator;

import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.DecimalLimit;
import smarthome.domain.actuator.vo.DecimalValue;
import smarthome.domain.actuator.vo.Precision;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.Value;

import java.util.UUID;

/**
 * A ActuatorOfDecimalLimiter is an actuator that limits the value to a specified range and precision.
 * The value of the ActuatorOfDecimalLimiter is rounded to the specified precision.
 */
public class ActuatorOfDecimalLimiter implements Actuator{

    private final DeviceId deviceId;
    private final ActuatorId actuatorId;
    private final ActuatorModelName actuatorModelName;
    private final DecimalLimit lowerLimit;
    private final DecimalLimit upperLimit;
    private final Precision precision;

    /**
     * Constructs a new ActuatorOfDecimalLimiter with the specified device ID, actuator model name, lower limit,
     * upper limit, and precision.
     *
     * @param deviceId the device ID of the actuator
     * @param actuatorModelName the actuator model name
     * @param lowerLimit the lower limit of the ActuatorOfDecimalLimiter
     * @param upperLimit the upper limit of the ActuatorOfDecimalLimiter
     * @param precision the precision of the ActuatorOfDecimalLimiter
     * @throws IllegalArgumentException if any of the parameters are null or if the lower limit is greater than
     * the upper limit
     */
    protected ActuatorOfDecimalLimiter(DeviceId deviceId, ActuatorModelName actuatorModelName, DecimalLimit lowerLimit,
                                    DecimalLimit upperLimit, Precision precision) {

       this(null, deviceId, actuatorModelName, lowerLimit, upperLimit, precision);
    }

    /**
     * Constructs a new ActuatorOfDecimalLimiter object with the given actuator id, device id, actuator model name,
     * lower limit, upper limit, and precision.
     * If the actuator id is null, a new actuator id is generated.
     * If the device id, actuator model name, lower limit, upper limit, or precision is null, or if the lower limit is
     * greater than the upper limit, an IllegalArgumentException is thrown.
     *
     * @param actuatorId the actuator id of this actuator. If null, a new actuator id is generated.
     * @param deviceId the device id where this actuator is located
     * @param actuatorModelName the actuator model name
     * @param lowerLimit the lower limit of the ActuatorOfDecimalLimiter
     * @param upperLimit the upper limit of the ActuatorOfDecimalLimiter
     * @param precision the precision of the ActuatorOfDecimalLimiter
     * @throws IllegalArgumentException if any of the parameters are null, or if the lower limit is greater than
     * the upper limit
     */
    protected ActuatorOfDecimalLimiter(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName,
                                       DecimalLimit lowerLimit, DecimalLimit upperLimit, Precision precision) {

        if(deviceId == null || actuatorModelName == null || lowerLimit == null || upperLimit == null
                || precision == null || !validLimits(lowerLimit, upperLimit)) {
            throw new IllegalArgumentException();
        }

        if(actuatorId != null) {
            this.actuatorId = actuatorId;
        }else {
            this.actuatorId = new ActuatorId(UUID.randomUUID().toString());
        }

        this.deviceId = deviceId;
        this.actuatorModelName = actuatorModelName;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.precision = precision;
    }

    /**
     * Returns the identity of the sensor
     *
     * @return the identity of the sensor
     */
    @Override
    public ActuatorId getIdentity() {
        return actuatorId;
    }

    /**
     * Returns the actuator model name
     *
     * @return the actuator model name
     */
    @Override
    public ActuatorModelName getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Returns the device id where the sensor is located
     *
     * @return the device id where the sensor is located
     */
    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the lower limit of the ActuatorOfDecimalLimiter
     *
     * @return the lower limit of the ActuatorOfDecimalLimiter
     */
    public DecimalLimit getLowerLimit() {
        return lowerLimit;
    }

    /**
     * Returns the upper limit of the ActuatorOfDecimalLimiter
     *
     * @return the upper limit of the ActuatorOfDecimalLimiter
     */
    public DecimalLimit getUpperLimit() {
        return upperLimit;
    }

    /**
     * Returns the precision of the ActuatorOfDecimalLimiter
     *
     * @return the precision of the ActuatorOfDecimalLimiter
     */
    public Precision getPrecision() {
        return precision;
    }

    /**
     * Sets the value of the ActuatorOfDecimalLimiter.
     * The value is rounded to the specified precision. The value must be within the limits of the
     * ActuatorOfDecimalLimiter.
     *
     * @param value the value to set
     * @return the updated value
     */
    public Value operate(Value value) {
        double decimalValue = Double.parseDouble(value.valueToString());
        if (!validValue(decimalValue)) {
            throw new IllegalArgumentException();
        }
        double newValue = Math.round(decimalValue * Math.pow(10, this.precision.getPrecision())) /
                Math.pow(10, this.precision.getPrecision());
        Value updatedValue = new DecimalValue(newValue);
        return updatedValue;
    }

    /**
     * Validates the value of the ActuatorOfDecimalLimiter.
     *
     * @param decimalValue the value of the ActuatorOfDecimalLimiter.
     * @return true if the value is valid
     */
    private boolean validValue(double decimalValue) {
        double lowerLimitDouble = lowerLimit.getDecimalLimit();
        double upperLimitDouble = upperLimit.getDecimalLimit();

        return decimalValue >= lowerLimitDouble && decimalValue <= upperLimitDouble;
    }

    /**
     * Checks if the provided object is equal to this ActuatorOfDecimalLimiter.
     * The equality is determined by comparing the actuatorId of the provided object with this
     * ActuatorOfDecimalLimiter's actuatorId.
     *
     * @param object the object to be compared for equality with this ActuatorOfDecimalLimiter
     * @return true if the provided object is of type ActuatorOfDecimalLimiter and its actuatorId is equal to this
     * ActuatorOfDecimalLimiter's actuatorId, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ActuatorOfDecimalLimiter that = (ActuatorOfDecimalLimiter) object;
        return actuatorId.equals(that.actuatorId);
    }

    /**
     * Returns a hash code value for this ActuatorOfDecimalLimiter.
     *
     * @return a hash code value for this ActuatorOfDecimalLimiter
     */
    @Override
    public int hashCode() {
        return actuatorId.hashCode();
    }

    /**
     * Validates the limits of the ActuatorOfDecimalLimiter.
     *
     * @param lowerLimit the lower limit of the ActuatorOfDecimalLimiter.
     * @param upperLimit the upper limit of the ActuatorOfDecimalLimiter.
     * @return true if the limits are valid
     */
    private boolean validLimits(DecimalLimit lowerLimit, DecimalLimit upperLimit) {
        double lowerLimitDouble = lowerLimit.getDecimalLimit();
        double upperLimitDouble = upperLimit.getDecimalLimit();
        return lowerLimitDouble <= upperLimitDouble;
    }

}
