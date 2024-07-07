package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.ScalePercentageValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.UUID;

/**
 * Represents a sensor of scale percentage
 */
public class SensorOfScalePercentage implements Sensor {

    private final SensorId sensorId;
    private final DeviceId deviceId;
    private final SensorModelName sensorModelName;

    /**
     * Constructs a new SensorOfScalePercentage object with the given device id and sensor model name
     * The sensor id is randomly generated using UUID
     *
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfScalePercentage(DeviceId deviceId, SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorId = new SensorId(UUID.randomUUID().toString());
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
    }

    /**
     * Constructs a new SensorOfScalePercentage object with the given device id and sensor model name
     * The sensor id is randomly generated using UUID
     *
     * @param sensorId        the sensor id
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfScalePercentage(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null || sensorId == null) {
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
        return this.sensorId;
    }

    /**
     * Returns the DeviceId of the Sensor.
     *
     * @return the DeviceId of the Sensor
     */
    @Override
    public DeviceId getDeviceId() {
        return this.deviceId;
    }


    /**
     * Returns the model name of the Sensor.
     *
     * @return the model name of the Sensor
     */
    @Override
    public SensorModelName getSensorModelName() {
        return this.sensorModelName;
    }


    /**
     * Returns the Value of the Sensor.
     *
     * @return the Value of the Sensor
     */
    @Override
    public Value getValue() {
        return new ScalePercentageValue(75.0); //Default value
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SensorOfScalePercentage that = (SensorOfScalePercentage) object;
        return sensorId.equals(that.sensorId);
    }

    @Override
    public int hashCode() {
        return sensorId.hashCode();
    }
}
