package smarthome.persistence.datamodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorOfDecimalLimiter;
import smarthome.domain.actuator.ActuatorOfLimiter;

/**
 * Represents a data model class for actuators within a smart home system.
 * This class maps actuator details to a database using the Jakarta Persistence API.
 * Each actuator is uniquely identified and can have both decimal and integer limits
 * for operations, where applicable.
 */
@Entity
@Table(name = "ACTUATOR")
@AllArgsConstructor
public class ActuatorDataModel {
    @Id
    String actuatorId;
    String deviceId;
    String actuatorModelName;
    Integer integerUpperLimit;
    Integer integerLowerLimit;
    Double decimalUpperLimit;
    Double decimalLowerLimit;
    Integer precisionValue;

    /**
     * Default constructor for ActuatorDataModel.
     */
    public ActuatorDataModel() {
    }

    /**
     * Constructs an ActuatorDataModel with properties derived from an Actuator object.
     *
     * @param actuator The Actuator object to extract properties from.
     */
    public ActuatorDataModel(Actuator actuator) {
        this.actuatorId = actuator.getIdentity().getActuatorId();
        this.deviceId = actuator.getDeviceId().getIdentity();
        this.actuatorModelName = actuator.getActuatorModelName().getActuatorModelName();
        if (actuator instanceof ActuatorOfDecimalLimiter) {
            this.decimalUpperLimit = ((ActuatorOfDecimalLimiter) actuator).getUpperLimit().getDecimalLimit();
            this.decimalLowerLimit = ((ActuatorOfDecimalLimiter) actuator).getLowerLimit().getDecimalLimit();
            this.precisionValue = ((ActuatorOfDecimalLimiter) actuator).getPrecision().getPrecision();
        }
        if (actuator instanceof ActuatorOfLimiter) {
            this.integerUpperLimit = ((ActuatorOfLimiter) actuator).getUpperLimit().getIntLimit();
            this.integerLowerLimit = ((ActuatorOfLimiter) actuator).getLowerLimit().getIntLimit();
        }
    }

    /**
     * Returns the unique ID of the actuator.
     *
     * @return the actuator ID.
     */
    public String getActuatorId() {
        return actuatorId;
    }

    /**
     * Returns the device ID associated with the actuator.
     *
     * @return the device ID.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the model name of the actuator.
     *
     * @return the actuator model name.
     */
    public String getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Returns the integer upper limit for this actuator, if applicable.
     *
     * @return the integer upper limit.
     */
    public Integer getIntegerUpperLimit() {
        return integerUpperLimit;
    }

    /**
     * Returns the integer lower limit for this actuator, if applicable.
     *
     * @return the integer lower limit.
     */
    public Integer getIntegerLowerLimit() {
        return integerLowerLimit;
    }

    /**
     * Returns the decimal upper limit for this actuator, if applicable.
     *
     * @return the decimal upper limit.
     */
    public Double getDoubleUpperLimit() {
        return decimalUpperLimit;
    }

    /**
     * Returns the decimal lower limit for this actuator, if applicable.
     *
     * @return the decimal lower limit.
     */
    public Double getDoubleLowerLimit() {
        return decimalLowerLimit;
    }

    /**
     * Returns the precision_value of the decimal limits for this actuator, if applicable.
     *
     * @return the precision_value of decimal limits.
     */
    public Integer getDoubleLimitPrecision() {
        return precisionValue;
    }
}
