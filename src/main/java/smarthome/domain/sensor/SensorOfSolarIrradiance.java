package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.SolarIrradianceValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.UUID;

/**
 * This class represents a sensor of solar irradiance.
 * It implements the Sensor interface.
 */
public class SensorOfSolarIrradiance implements Sensor {

    private final SensorModelName sensorModelName;

    private final DeviceId deviceId;

    private final SensorId sensorId;

    /**
     * Constructs a new SensorOfSolarIrradiance.
     *
     * @param sensorModelName the model name of the sensor. Must not be null.
     * @param deviceId        the device ID of the sensor. Must not be null.
     * @throws IllegalArgumentException if the provided sensorModelName or deviceId is null.
     */
    protected SensorOfSolarIrradiance(DeviceId deviceId, SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorModelName = sensorModelName;
        this.deviceId = deviceId;
        this.sensorId = new SensorId(UUID.randomUUID().toString());
    }


    /**
     * Constructs a new SensorOfSolarIrradiance.
     *
     * @param sensorId        the sensor ID. Must not be null.
     * @param deviceId        the device ID of the sensor. Must not be null.
     * @param sensorModelName the model name of the sensor. Must not be null.
     * @throws IllegalArgumentException if the provided sensorId, deviceId or sensorModelName is null.
     */
    protected SensorOfSolarIrradiance(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
        if (sensorId == null || deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorId = sensorId;
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
    }

    /**
     * Returns the sensor ID.
     *
     * @return the sensor ID.
     */
    @Override
    public SensorId getIdentity() {
        return sensorId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SensorOfSolarIrradiance that = (SensorOfSolarIrradiance) object;
        return sensorId.equals(that.sensorId);
    }

    @Override
    public int hashCode() {
        return sensorId.hashCode();
    }

    /**
     * Returns the model name of the sensor.
     *
     * @return the model name of the sensor.
     */
    @Override
    public SensorModelName getSensorModelName() {
        return sensorModelName;
    }

    /**
     * Returns the device ID of the sensor.
     *
     * @return the device ID of the sensor.
     */
    @Override
    public DeviceId getDeviceId() {

        return deviceId;
    }

    /**
     * Returns the value of the sensor.
     *
     * @return the value of the sensor.
     */
    @Override
    public Value getValue() {
        return new SolarIrradianceValue(1200.0);

    }
}
