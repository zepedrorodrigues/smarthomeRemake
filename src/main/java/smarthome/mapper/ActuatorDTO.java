package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel;

/**
 * ActuatorDTO is a Data Transfer Object class for Actuator.
 */
public class ActuatorDTO extends RepresentationModel<ActuatorDTO> {

    private String actuatorId;
    private String deviceId;
    private String actuatorModelName;
    private Integer integerUpperLimit;
    private Integer integerLowerLimit;
    private Double doubleUpperLimit;
    private Double doubleLowerLimit;
    private Integer doubleLimitPrecision;

    /*
     * Default constructor for ActuatorDTO.
     */
    public ActuatorDTO() {
    }

    /**
     * Constructs a new ActuatorDTO with the given parameters.
     *
     * @param actuatorId           the unique identifier of the actuator
     * @param deviceId             the unique identifier of the device
     * @param actuatorModelName    the name of the actuator model
     * @param integerUpperLimit    the upper limit for an integer value
     * @param integerLowerLimit    the lower limit for an integer value
     * @param doubleUpperLimit     the upper limit for a double value
     * @param doubleLowerLimit     the lower limit for a double value
     * @param doubleLimitPrecision the precision for the double value
     */
    public ActuatorDTO(String actuatorId, String deviceId, String actuatorModelName, Integer integerUpperLimit,
                       Integer integerLowerLimit, Double doubleUpperLimit, Double doubleLowerLimit,
                       Integer doubleLimitPrecision) {
        this.actuatorId = actuatorId;
        this.deviceId = deviceId;
        this.actuatorModelName = actuatorModelName;
        this.integerUpperLimit = integerUpperLimit;
        this.integerLowerLimit = integerLowerLimit;
        this.doubleUpperLimit = doubleUpperLimit;
        this.doubleLowerLimit = doubleLowerLimit;
        this.doubleLimitPrecision = doubleLimitPrecision;
    }

    /**
     * Constructs a new ActuatorDTO with the given parameters.
     *
     * @param actuatorId        the unique identifier of the actuator
     * @param deviceId          the unique identifier of the device
     * @param actuatorModelName the name of the actuator model
     */
    public ActuatorDTO(String actuatorId, String deviceId, String actuatorModelName) {
        this(actuatorId, deviceId, actuatorModelName, null, null, null, null, null);
    }


    public ActuatorDTO(String actuatorId, String actuatorModelName) {
        this.actuatorId = actuatorId;
        this.actuatorModelName = actuatorModelName;
    }

    /**
     * Returns the unique identifier of the actuator.
     *
     * @return the unique identifier of the actuator
     */
    public String getActuatorId() {
        return actuatorId;
    }

    /**
     * Returns the unique identifier of the device.
     *
     * @return the unique identifier of the device
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the name of the actuator model.
     *
     * @return the name of the actuator model
     */
    public String getActuatorModelName() {
        return actuatorModelName;
    }

    /**
     * Returns the upper limit for an integer value.
     *
     * @return the upper limit for an integer value
     */
    public Integer getIntegerUpperLimit() {
        return integerUpperLimit;
    }

    /**
     * Returns the lower limit for an integer value.
     *
     * @return the lower limit for an integer value
     */
    public Integer getIntegerLowerLimit() {
        return integerLowerLimit;
    }

    /**
     * Returns the upper limit for a double value.
     *
     * @return the upper limit for a double value
     */
    public Double getDoubleUpperLimit() {
        return doubleUpperLimit;
    }

    /**
     * Returns the lower limit for a double value.
     *
     * @return the lower limit for a double value
     */
    public Double getDoubleLowerLimit() {
        return doubleLowerLimit;
    }

    /**
     * Returns the precision for the double value.
     *
     * @return the precision for the double value
     */
    public Integer getDoubleLimitPrecision() {
        return doubleLimitPrecision;
    }
}
