package smarthome.persistence.mem;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The SensorRepositoryMemImpl class represents a repository for managing Sensor entities.
 * It provides methods to save, retrieve, and manipulate Sensor objects.
 * This repository is backed by an in-memory HashMap data structure.
 * SensorId representing the identity of Sensor entities.
 * Sensor   representing Sensor entities.
 */
public class SensorRepositoryMemImpl implements ISensorRepository {

    // Internal storage for Sensor entities using a HashMap
    private final Map<SensorId, Sensor> DATA = new HashMap<>();

    /**
     * Saves a Sensor entity to the repository.
     *
     * @param sensor The Sensor entity to save.
     * @return The saved Sensor entity.
     */
    @Override
    public Sensor save(Sensor sensor) {
        DATA.put(sensor.getIdentity(), sensor);
        return sensor;
    }

    /**
     * Retrieves all Sensor entities stored in the repository.
     *
     * @return An Iterable collection containing all Sensor entities.
     */
    @Override
    public Iterable<Sensor> findAll() {
        return DATA.values();
    }

    /**
     * Retrieves a Sensor entity by its identity from the repository.
     *
     * @param id The identity of the Sensor entity to retrieve.
     * @return An Optional containing the retrieved Sensor entity if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<Sensor> findByIdentity(SensorId id) {
        return Optional.ofNullable(DATA.get(id));
    }

    /**
     * Checks if the repository contains a Sensor entity with the specified identity.
     *
     * @param id The identity of the Sensor entity to check for.
     * @return true if the repository contains a Sensor entity with the specified identity, false otherwise.
     */
    @Override
    public boolean containsIdentity(SensorId id) {
        return DATA.containsKey(id);
    }

    /**
     * Retrieves all Sensor entities associated with a specific device from the repository.
     *
     * @param deviceId The identity of the device whose associated Sensor entities are to be retrieved.
     * @return An Iterable collection containing all Sensor entities associated with the specified device.
     */
    public Iterable<Sensor> findSensorsByDeviceId(DeviceId deviceId) {
        return DATA.values().stream().filter(sensor -> sensor.getDeviceId().equals(deviceId)).toList();
    }

    /**
     * Retrieves all Sensor entities associated with a specific device and sensor model from the repository.
     *
     * @param deviceId        The identity of the device whose associated Sensor entities are to be retrieved.
     * @param sensorModelName The name of the sensor model whose associated Sensor entities are to be retrieved.
     * @return An Iterable collection containing all Sensor entities associated with the specified device and
     * sensor model.
     */
    @Override
    public Iterable<Sensor> findSensorsByDeviceIdAndSensorModelName(DeviceId deviceId, SensorModelName sensorModelName) {

        return DATA.values().stream().filter(sensor ->
                sensor.getDeviceId().equals(deviceId) && sensor.getSensorModelName().equals(sensorModelName)
        ).toList();
    }

    /**
     * Retrieves all Sensor entities associated with a specific device and sensor model from the repository.
     * @param deviceId        The identity of the device whose associated Sensor entities are to be retrieved.
     * @param sensorModelName The name of the sensor model whose associated Sensor entities are to be retrieved.
     * @return An Iterable collection containing all Sensor entities associated with the specified device and sensor
     * model.
     */
    @Override
    public Iterable<SensorId> findSensorIdsByDeviceIdAndSensorModelName(DeviceId deviceId,
                                                                        SensorModelName sensorModelName) {
        if (deviceId == null || sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        return DATA.values().stream().filter(sensor ->
                sensor.getDeviceId().equals(deviceId) && sensor.getSensorModelName().equals(sensorModelName)
        ).map(Sensor::getIdentity).toList();
    }

    /**
     * Retrieves all SensorId entities associated with a specific device from the repository.
     *
     * @param deviceId The identity of the device whose associated SensorId entities are to be retrieved.
     * @return An Iterable collection containing all SensorId entities associated with the specified device.
     * @throws IllegalArgumentException if the deviceId is null.
     */
    @Override
    public Iterable<SensorId> findSensorIdsByDeviceId(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        return DATA.values().stream().filter(sensor -> sensor.getDeviceId().equals(deviceId)).map(Sensor::getIdentity).toList();
    }
}
