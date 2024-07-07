package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.PowerConsumptionValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.UUID;

/**
 * Represents a sensor of power consumption.
 * It is a value object and mutable object, an implementation of the Sensor interface.
 */
public class SensorOfPowerConsumption  implements Sensor{

    /**
     * The name of the Sensor.
     */
    private final SensorModelName sensorModelName;

    /**
     * The DeviceId of the Sensor.
     */
    private final DeviceId deviceId;

    /**
     * The SensorId of the Sensor.
     */
    private final SensorId sensorId;

    /**
     * Creates a new SensorOfPowerConsumption.
     * @param sensorModelName the name of the Sensor
     * @param deviceId the DeviceId of the Sensor
     * @throws IllegalArgumentException if sensorModelName or DeviceId are null
     */
    protected SensorOfPowerConsumption(DeviceId deviceId, SensorModelName sensorModelName) {
        if(sensorModelName == null || deviceId == null){
            throw new IllegalArgumentException();}
        this.sensorModelName = sensorModelName;
        this.deviceId = deviceId;
        this.sensorId = new SensorId(UUID.randomUUID().toString());}

    /**
     * Creates a new SensorOfPowerConsumption, using the given SensorId (necessary
     * for memory and persistence management)
     * @param sensorId the SensorId of the Sensor
     * @param deviceId the DeviceId of the Sensor
     * @param sensorModelName the name of the Sensor
     * @throws IllegalArgumentException if sensorModelName, DeviceId or SensorId are null
     */
    protected SensorOfPowerConsumption(SensorId sensorId ,DeviceId deviceId, SensorModelName sensorModelName) {
        if(sensorModelName == null || deviceId == null|| sensorId == null){
            throw new IllegalArgumentException();}
        this.sensorModelName = sensorModelName;
        this.deviceId = deviceId;
        this.sensorId = sensorId;
    }

    /**
     * Returns the identity of the Sensor.
     * @return the sensorId
     */
    @Override
    public SensorId getIdentity() {
        return sensorId;
    }

    /**
     * Returns the name of the Sensor.
     *
     * @return the name of the Sensor
     */
    @Override
    public SensorModelName getSensorModelName() {
        return sensorModelName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SensorOfPowerConsumption that = (SensorOfPowerConsumption) object;
        return sensorId.equals(that.sensorId);
    }

    /**
     * Returns the hash code of the Sensor.
     * @return the hash code of the Sensor
     */

    @Override
    public int hashCode() {
        return sensorId.hashCode();
    }

    /**
     * Returns the DeviceId of the Sensor.
     *
     * @return the DeviceId of the Sensor
     */
    @Override
    public DeviceId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the Value of the Sensor. Return a default value of 15.0
     *
     * @return the Value of the Sensor
     */
    @Override
    public Value getValue() {
        return new PowerConsumptionValue(15.0);
    }


}
