package smarthome.domain.sensor;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;

/**
 * This interface defines a factory for creating Sensor objects.
 * It is used in the domain layer of the application.
 */
public interface SensorFactory {

    /**
     * Creates a Sensor object with the given parameters.
     *
     * @param sensorModelName the model name of the sensor
     * @param deviceId        the unique identifier of the device the sensor is attached to
     * @return a Sensor object
     */
    Sensor createSensor(SensorModelName sensorModelName, DeviceId deviceId);

    /**
     * Creates a Sensor object with the given parameters.
     *
     * @param sensorId the unique identifier of the sensor. It can be null.
     * @param sensorModelName the model name of the sensor. It cannot be null.
     * @param deviceId the unique identifier of the device the sensor is attached to. It cannot be null.
     * @return a Sensor object
     */
    Sensor createSensor(SensorId sensorId, SensorModelName sensorModelName, DeviceId deviceId);

}