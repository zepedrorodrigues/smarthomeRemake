package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;

/**
 * The ISensorRepository interface represents a repository for managing Sensor entities.
 * It provides methods to save, retrieve, and manipulate Sensor objects.
 */
public interface ISensorRepository extends IRepository<SensorId, Sensor> {

    /**
     * Retrieves all Sensor entities associated with a specific DeviceId from the repository.
     * <p>
     * @param Id The device id.
     * @return An Iterable collection containing all Sensor entities associated with the provided DeviceId.
     */
    Iterable<Sensor> findSensorsByDeviceId(DeviceId Id);

    /**
     * Retrieves all Sensor entities associated with a specific DeviceId and SensorModelName from the repository.
     * <p>
     * @param deviceId        The device id.
     * @param sensorModelName The name of the SensorModel.
     * @return An Iterable collection containing all Sensor entities associated with the
     * provided DeviceId and SensorModelName.
     */
    Iterable<Sensor> findSensorsByDeviceIdAndSensorModelName(DeviceId deviceId, SensorModelName sensorModelName);

    /**
     * Retrieves all Sensor entities associated with a specific DeviceId and SensorModelName from the repository.
     * <p>
     * @param deviceId        The device id.
     * @param sensorModelName The name of the SensorModel.
     * @return An Iterable collection containing all Sensor entities associated with the
     * provided DeviceId and SensorModelName.
     */
    Iterable<SensorId> findSensorIdsByDeviceIdAndSensorModelName(DeviceId deviceId, SensorModelName sensorModelName);

    /**
     * Retrieves all SensorId entities associated with a specific DeviceId from the repository.
     *
     * @param deviceId The device id.
     * @return An Iterable collection containing all SensorId entities associated with the provided DeviceId.
     */
    Iterable<SensorId> findSensorIdsByDeviceId(DeviceId deviceId);
}
