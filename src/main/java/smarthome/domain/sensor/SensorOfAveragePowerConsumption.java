package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.AveragePowerConsumptionValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.UUID;

/**
 * This class represents a sensor that measures average power consumption.
 * It implements the Sensor interface and contains a value of type AveragePowerConsumptionValue.
 */
public class SensorOfAveragePowerConsumption implements Sensor {
    private final SensorModelName sensorModelName;
    private final DeviceId deviceId;
    private final SensorId sensorId;

    /**
     * Constructs a new SensorOfAveragePowerConsumption with the specified deviceId and sensorModelName.
     *
     * @param deviceId        the id of the device this sensor is attached to.
     * @param sensorModelName the model name of this sensor.
     * @throws IllegalArgumentException if either deviceId or sensorModelName is null.
     */
    protected SensorOfAveragePowerConsumption(DeviceId deviceId, SensorModelName sensorModelName) {
        if (sensorModelName == null || deviceId == null) {
            throw new IllegalArgumentException();
        }
        this.sensorModelName = sensorModelName;
        this.deviceId = deviceId;
        this.sensorId = new SensorId(UUID.randomUUID().toString());
    }

    /**
     * Constructs a new SensorOfAveragePowerConsumption with the specified sensorId, deviceId and sensorModelName.
     *
     * @param sensorId        the id of this sensor.
     * @param deviceId        the id of the device this sensor is attached to.
     * @param sensorModelName the model name of this sensor.
     * @throws IllegalArgumentException if either sensorId, deviceId or sensorModelName is null.
     */
    protected SensorOfAveragePowerConsumption(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
        if (sensorId == null || deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorId = sensorId;
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
    }

    /**
     * Returns the identity of this sensor.
     *
     * @return the identity of this sensor.
     */
    @Override
    public SensorId getIdentity() {
        return sensorId;
    }

    /**
     * Returns the model name of this sensor.
     *
     * @return the model name of this sensor.
     */
    @Override
    public SensorModelName getSensorModelName() {
        return sensorModelName;
    }

    /**
     * Returns the id of the device this sensor is attached to.
     *
     * @return the id of the device this sensor is attached to.
     */
    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the value this sensor measures.
     * The value is of type AveragePowerConsumptionValue.
     *
     * @return the value this sensor measures.
     */
    @Override
    public Value getValue() {
        return new AveragePowerConsumptionValue(10.0); // Default value
    }

    /**
     * Checks if the given object is equal to this sensor.
     * Two sensors are considered equal if they have the same sensor id.
     *
     * @param object the object to compare with
     * @return true if the given object is equal to this sensor, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SensorOfAveragePowerConsumption that = (SensorOfAveragePowerConsumption) object;
        return sensorId.equals(that.sensorId);
    }

    /**
     * Returns the hash code of this sensor.
     * The hash code is computed based on the sensor id.
     *
     * @return the hash code of this sensor
     */
    @Override
    public int hashCode() {
        return sensorId.hashCode();
    }
}