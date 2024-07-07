package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.OnOffValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.UUID;

/**
 * Represents a binary sensor
 */
public class SensorOfOnOff implements Sensor {

    private final SensorId sensorId;
    private final DeviceId deviceId;
    private final SensorModelName sensorModelName;

    /**
     * Constructs a new SensorOfOnOff object with the given device id and sensor model name
     * The sensor id is randomly generated using UUID
     *
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfOnOff(DeviceId deviceId, SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorId = new SensorId(UUID.randomUUID().toString());
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;

    }

    /**
     * Constructs a new SensorOfOnOff object with the given sensor id, device id and sensor model name
     *
     * @param sensorId        the sensor id
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfOnOff(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
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
     * Returns the actual value of the sensor
     *
     * @return the actual value of the sensor
     */
    @Override
    public Value getValue() {
        return new OnOffValue(false); //Default value
    }

    /**
     * Checks if the given object is equal to this sensor
     *
     * @param object the object to compare with this sensor
     * @return true if the given object is a sensor and its sensor id is equal, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final SensorOfOnOff that = (SensorOfOnOff) object;
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
