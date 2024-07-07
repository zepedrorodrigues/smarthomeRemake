package smarthome.mapper;

import org.springframework.hateoas.RepresentationModel; /**
 * Data Transfer Object for Sensor.
 */
public class SensorDTO extends RepresentationModel<SensorDTO>{

     private String sensorId;
     private String deviceId;
     private String sensorModelName;
     private String value;


    /**
     * Constructs a new SensorDTO.
     */
    public SensorDTO() {
     }

    /**
     * Constructs a new SensorDTO with the given parameters.
     *
     * @param deviceId        the unique identifier of the device
     * @param sensorModelName the name of the sensor model
     */
    public SensorDTO(String deviceId, String sensorModelName) {
        this.sensorId = null;
        this.value = null;
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
    }

    /**
     * Constructs a new SensorDTO with the given parameters.
     *
     * @param sensorId        the unique identifier of the sensor
     * @param deviceId        the unique identifier of the device
     * @param sensorModelName the name of the sensor model
     * @param value           the value of the sensor
     */
    public SensorDTO(String sensorId, String deviceId, String sensorModelName, String value) {
        this.sensorId = sensorId;
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
        this.value = value;
    }

    /**
     * Constructs a new SensorDTO with the given sensor model name.
     *
     * @param sensorModelName the name of the sensor model
     */
    public SensorDTO(String sensorModelName) {
        this.sensorModelName = sensorModelName;
    }

    /**
     * Returns the unique identifier of the sensor.
     *
     * @return the unique identifier of the sensor
     */
    public String getSensorId() {
        return sensorId;
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
     * Returns the value of the sensor.
     *
     * @return the value of the sensor
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the name of the sensor model.
     *
     * @return the name of the sensor model
     */
    public String getSensorModelName() {
        return sensorModelName;
    }
}
