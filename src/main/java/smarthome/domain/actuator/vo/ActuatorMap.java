package smarthome.domain.actuator.vo;

import smarthome.ddd.ValueObject;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;

import java.util.Objects;

/**
 * This class represents a map of Actuator values. It implements the ValueObject interface.
 * It contains various properties related to an Actuator and provides methods to manipulate and access these
 * properties.
 */
public class ActuatorMap implements ValueObject {
    private final ActuatorId actuatorId;

    private DeviceId deviceId;

    private final ActuatorModelName actuatorModelName;

    private final IntLimit integerUpperLimit;

    private final IntLimit integerLowerLimit;

    private final DecimalLimit decimalUpperLimit;

    private final DecimalLimit decimalLowerLimit;

    private final Precision precision;

    /**
     * Constructs an ActuatorMap object with the specified parameters.
     *
     * @param actuatorId        the unique identifier of the actuator
     * @param deviceId          the unique identifier of the device
     * @param actuatorModelName the name of the actuator model
     * @param integerUpperLimit the upper limit for an integer value
     * @param integerLowerLimit the lower limit for an integer value
     * @param decimalUpperLimit the upper limit for a decimal value
     * @param decimalLowerLimit the lower limit for a decimal value
     * @param precision         the precision for the decimal value
     */
    public ActuatorMap(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName,
                       IntLimit integerUpperLimit, IntLimit integerLowerLimit, DecimalLimit decimalUpperLimit,
                       DecimalLimit decimalLowerLimit, Precision precision) {
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorModelName = actuatorModelName;
        this.integerUpperLimit = integerUpperLimit;
        this.integerLowerLimit = integerLowerLimit;
        this.decimalUpperLimit = decimalUpperLimit;
        this.decimalLowerLimit = decimalLowerLimit;
        this.precision = precision;
    }

    /**
     * Returns the actuator id.
     *
     * @return the actuator id
     */
    public ActuatorId getActuatorId() {
        return actuatorId;
    }

    /**
     * Returns the device id.
     *
     * @return the device id
     */
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the actuator model name.
     *
     * @return the actuator model name
     */
    public ActuatorModelName getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Returns the integer upper limit.
     *
     * @return the integer upper limit
     */
    public IntLimit getIntegerUpperLimit() {
        return integerUpperLimit;
    }

    /**
     * Returns the integer lower limit.
     *
     * @return the integer lower limit
     */
    public IntLimit getIntegerLowerLimit() {
        return integerLowerLimit;
    }

    /**
     * Returns the decimal upper limit.
     *
     * @return the decimal upper limit
     */
    public DecimalLimit getDecimalUpperLimit() {
        return decimalUpperLimit;
    }

    /**
     * Returns the decimal lower limit.
     *
     * @return the decimal lower limit
     */
    public DecimalLimit getDecimalLowerLimit() {
        return decimalLowerLimit;
    }

    /**
     * Sets the device id.
     *
     * @param deviceId the device id to set
     */
    public void setDeviceId(DeviceId deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Overrides the equals method from the Object class.
     * This method is used to compare two ActuatorMap objects for equality.
     * Two ActuatorMap objects are considered equal if all their respective properties are equal.
     *
     * @param object the object to be compared with the current object
     * @return true if the specified object is equal to the current object, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ActuatorMap that = (ActuatorMap) object;
        return actuatorId.equals(that.actuatorId)
                && deviceId.equals(that.deviceId)
                && actuatorModelName.equals(that.actuatorModelName)
                && integerUpperLimit.equals(that.integerUpperLimit)
                && integerLowerLimit.equals(that.integerLowerLimit)
                && decimalUpperLimit.equals(that.decimalUpperLimit)
                && decimalLowerLimit.equals(that.decimalLowerLimit)
                && precision.equals(that.precision);
    }

    /**
     * Overrides the hashCode method from the Object class.
     * This method returns a hash code value for this object based on the values of its properties.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(actuatorId, deviceId, actuatorModelName, integerUpperLimit, integerLowerLimit,
                decimalUpperLimit, decimalLowerLimit, precision);
    }

    /**
     * Returns the precision.
     *
     * @return the precision
     */
    public Precision getPrecision() {
        return precision;}
}