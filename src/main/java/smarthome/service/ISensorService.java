package smarthome.service;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.Optional;

/**
 * This interface represents the REST service for sensors.
 */
public interface ISensorService {

    /**
     * Adds a new sensor with the specified sensor model name and device id.
     *
     * @param sensorModelName the name of the sensor model.
     * @param deviceId the identity of the device.
     * @return the created Sensor object, or null if the sensor could not be created.
     */
    Sensor addSensor(SensorModelName sensorModelName, DeviceId deviceId);

    /**
     * Retrieves a Sensor by its identity.
     *
     * @param sensorId The identity of the Sensor to retrieve.
     * @return An Optional of Sensor if found, empty Optional otherwise.
     */
    Optional<Sensor> getByIdentity(SensorId sensorId);

    /**
     * Retrieves all Sensor entities associated with a specific device and sensor model from the repository.
     *
     * @param deviceId
     * @return
     */
    Iterable<SensorId> getSensorIdsByDeviceIdentity(DeviceId deviceId);
}