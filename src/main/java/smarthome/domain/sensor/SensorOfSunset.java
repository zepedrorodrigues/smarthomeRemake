package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.SunsetValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * This class represents a sunset sensor.
 * It implements the Sensor interface.
 */
public class SensorOfSunset implements Sensor {

    private final SensorId sensorId;
    private final DeviceId deviceId;
    private final SensorModelName sensorModelName;

    /**
     * Constructs a new SensorOfSunset object with the given device id and sensor model name
     * The sensor id is randomly generated using UUID
     * The default value of the sensor is set to 18:00
     *
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfSunset(DeviceId deviceId, SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        this.sensorId = new SensorId(UUID.randomUUID().toString());
        this.deviceId = deviceId;
        this.sensorModelName = sensorModelName;
    }

    /**
     * Constructs a new SensorOfSunset object with the given sensor id, device id and sensor model name
     *
     * @param sensorId        the sensor id
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfSunset(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
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
        return this.sensorId;
    }

    /**
     * Returns the device id where the sensor is located
     *
     * @return the device id where the sensor is located
     */
    @Override
    public DeviceId getDeviceId() {
        return this.deviceId;
    }

    /**
     * Returns the sensor model name
     *
     * @return the sensor model name
     */
    @Override
    public SensorModelName getSensorModelName() {
        return this.sensorModelName;
    }

    /**
     * Returns the value of the sensor for the current date
     *
     * @return the value of the sensor
     */
    public Value getValue() {
        LocalDate dateDate = LocalDate.now();
        return getValue(dateDate);
    }

    /**
     * Returns the value of the sensor for a specified date
     *
     * @param date the date to retrieve the value from
     * @return the value of the sensor
     */
    public Value getValue(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        final LocalTime SUNSET_TIME = LocalTime.of(18, 0); // the default time used for sunset
        LocalDateTime dateTime = LocalDateTime.of(date, SUNSET_TIME);
        return new SunsetValue(dateTime);
    }

    /**
     * Checks if the provided object is equal to this SensorOfSunset.
     * The equality is determined by comparing the sensorId of the provided object with this
     * SensorOfSunset's sensorId.
     *
     * @param object the object to be compared for equality with this SensorOfSunset
     * @return true if the provided object is equal to this SensorOfSunset, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SensorOfSunset that = (SensorOfSunset) object;
        return this.sensorId.equals(that.sensorId);
    }

    /**
     * Returns the hash code of this SensorOfSunset
     *
     * @return the hash code of this SensorOfSunset
     */
    @Override
    public int hashCode() {
        return this.sensorId.hashCode();
    }
}
