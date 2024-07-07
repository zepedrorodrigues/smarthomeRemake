package smarthome.domain.sensor;


import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.DewPointValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.UUID;

/**
 * Represents a dew point sensor
 */
public class SensorOfDewPoint implements Sensor {

    private final SensorId sensorId;
    private final DeviceId deviceId;
    private final SensorModelName sensorModelName;
    private Value value;

    /**
     * Constructs a new SensorOfDewPoint object with the given device id and sensor model name
     * The sensor id is randomly generated using UUID
     * The default value of the sensor is set to 29.0
     *
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfDewPoint(DeviceId deviceId, SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorId = new SensorId(UUID.randomUUID().toString());
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
    }

    /**
     * Constructs a new SensorOfDewPoint object with the given sensor id, device id and sensor model name
     *
     * @param sensorId        the sensor id
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfDewPoint(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
        if (sensorId == null || deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorId = sensorId;
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
    }

    /**
     * Returns the identity of the sensor
     *
     * @return the identity of the sensor
     */
    @Override
    public SensorId getIdentity() {
        return sensorId;
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
     * Returns the sensor model name
     *
     * @return the sensor model name
     */
    @Override
    public SensorModelName getSensorModelName() {
        return sensorModelName;
    }

    /**
     * Returns the actual value of the sensor. For the time being, this value is set to a default value.
     *
     * @return the actual value of the sensor.
     */
    @Override
    public Value getValue() {
        this.value = new DewPointValue(29.0); // For the time being, this value is set to a default value
        return value;
    }

    /**
     * Determines if this SensorOfDewPoint object is equal to another object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorOfDewPoint that = (SensorOfDewPoint) o;
        return sensorId.equals(that.sensorId);
    }

    /**
     * Returns the hash code of the SensorOfDewPoint
     *
     * @return the hash code of the SensorOfDewPoint
     */
    @Override
    public int hashCode() {
        return sensorId.hashCode();
    }
}
