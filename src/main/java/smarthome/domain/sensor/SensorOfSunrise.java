package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.SunriseValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * This class represents a sunrise sensor.
 * It implements the Sensor interface.
 */
public class SensorOfSunrise implements Sensor{

    private final SensorId sensorId;
    private final DeviceId deviceId;
    private final SensorModelName sensorModelName;


    /**
     * Constructs a new SensorOfSunrise object with the given device id and sensor model name
     * The sensor id is randomly generated using UUID
     *
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     */
    protected SensorOfSunrise(DeviceId deviceId, SensorModelName sensorModelName) {
        this(null, deviceId, sensorModelName);
    }

    /**
     * Constructs a new SensorOfSunrise object with the given sensor id, device id and sensor model name.
     * If the device id or sensor model name is null, an IllegalArgumentException is thrown.
     *
     * @param sensorId        the sensor id of this sensor. If the sensorId is not provided, a random one will
     *                        be generated.
     * @param deviceId        the device id where this sensor is located
     * @param sensorModelName the sensor model name
     * @throws IllegalArgumentException if deviceId or sensorModelName is null
     */
    protected SensorOfSunrise(SensorId sensorId, DeviceId deviceId, SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }

        if(sensorId != null) {
            this.sensorId = sensorId;
        } else {
            this.sensorId = new SensorId(UUID.randomUUID().toString());
        }

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
    public SensorModelName getSensorModelName() {
        return sensorModelName;
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
     * Returns the value from the sensor for today.
     * The default value of the sensor is set to 07:00
     *
     * @return the value from the sensor
     */
    @Override
    public Value getValue() {
        return new SunriseValue(LocalDateTime.of(LocalDate.now(), LocalTime.of(7,0))); //Default value;
    }

    /**
     * Returns the value from the sensor for a specified date.
     *
     * @return the value from the sensor
     */
    public Value getValue(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(6,0));
        return new SunriseValue(dateTime);

    }

    /**
     * Checks if the provided object is equal to this SensorOfSunrise.
     * The equality is determined by comparing the sensorId of the provided object with this SensorOfSunrise's
     * sensorId.
     *
     * @param object the object to be compared for equality with this SensorOfSunrise
     * @return true if the provided object is of type SensorOfSunrise and its sensorId is equal to this
     * SensorOfSunrise's sensorId, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SensorOfSunrise that = (SensorOfSunrise) object;
        return sensorId.equals(that.sensorId);
    }


    /**
     * Returns a hash code value for this SensorOfSunrise.
     * The hash code is generated based on the sensorId of this SensorOfSunrise.
     *
     * @return a hash code value for this SensorOfSunrise
     */
    @Override
    public int hashCode() {
        return sensorId.hashCode();
    }
}
