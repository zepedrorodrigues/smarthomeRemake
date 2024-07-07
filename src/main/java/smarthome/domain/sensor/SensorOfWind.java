package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensor.vo.values.WindValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.UUID;

/**
 * This class represents a wind sensor in a smart home system.
 * It implements the Sensor interface and provides the necessary methods to interact with the sensor.
 */
public class SensorOfWind implements Sensor {

    private final SensorModelName sensorModelName;
    private final DeviceId deviceId;
    private final SensorId sensorId;

    /**
     * Constructs a new SensorOfWind object with the given device id and sensor model name
     * The sensor id is randomly generated using UUID
     *
     * @param sensorModelName The model name of the sensor. It cannot be null.
     * @param deviceId        The device ID of the sensor. It cannot be null.
     * @throws IllegalArgumentException if either sensorModelName or deviceId is null.
     */
    protected SensorOfWind(DeviceId deviceId, SensorModelName sensorModelName) {
        if (sensorModelName == null || deviceId == null) {
            throw new IllegalArgumentException();
        }
        this.sensorModelName = sensorModelName;
        this.deviceId = deviceId;
        this.sensorId = new SensorId(UUID.randomUUID().toString());

    }

    /**
     * Constructs a new SensorOfWind object with the given sensor id, device id and sensor model name
     *
     * @param sensorId        The identity of the sensor. It cannot be null.
     * @param deviceId        The device ID of the sensor. It cannot be null.
     * @param sensorModelName The model name of the sensor. It cannot be null.
     * @throws IllegalArgumentException if either sensorId, sensorModelName or deviceId is null.
     */
    protected SensorOfWind(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
        if (sensorId == null || sensorModelName == null || deviceId == null) {
            throw new IllegalArgumentException();
        }
        this.sensorModelName = sensorModelName;
        this.deviceId = deviceId;
        this.sensorId = sensorId;
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
     * Returns the model name of the sensor.
     *
     * @return The sensor model name.
     */
    @Override
    public SensorModelName getSensorModelName() {
        return sensorModelName;
    }

    /**
     * Returns the device ID of the sensor.
     *
     * @return The device ID.
     */
    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the value of the sensor.
     * The value is a WindValue object that contains the direction and speed of the wind.
     *
     * @return The sensor value.
     */
    @Override
    public Value getValue() {
        return new WindValue(Math.PI / 2, 25.0);
    }

    /**
     * Checks if the given object is equal to this sensor
     *
     * @param object the object to compare with this sensor
     * @return true if the given object is a sensor and its sensor id is equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SensorOfWind that = (SensorOfWind) object;
        return sensorId.equals(that.sensorId);
    }

    /**
     * Returns the hash code of the sensor
     *
     * @return the hash code of the sensor
     */
    @Override
    public int hashCode() {
        return sensorId.hashCode();
    }

}
