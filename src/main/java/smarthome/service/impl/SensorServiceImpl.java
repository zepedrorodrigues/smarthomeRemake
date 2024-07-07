package smarthome.service.impl;

import org.springframework.stereotype.Service;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.service.ISensorService;

import java.util.Optional;

/**
 * This class provides the implementation for the ISensorService interface.
 * It is annotated with @Service to indicate that it's a service component in the Spring context.
 */
@Service
public class SensorServiceImpl implements ISensorService {

    private final ISensorRepository sensorRepository;

    private final SensorFactory sensorFactory;

    private final IDeviceRepository deviceRepository;


    /**
     * Constructor for SensorServiceImpl.
     *
     * @param sensorRepository the repository for Sensor entities.
     * @param sensorFactory the factory for creating Sensor entities.
     * @param deviceRepository the repository for Device entities.
     */
    public SensorServiceImpl(ISensorRepository sensorRepository,
                             SensorFactory sensorFactory, IDeviceRepository deviceRepository) {

        this.sensorRepository = sensorRepository;
        this.sensorFactory = sensorFactory;
        this.deviceRepository = deviceRepository;
    }
    /**
     * Adds a new sensor with the specified sensor model name and device id.
     *
     * @param sensorModelName the name of the sensor model.
     * @param deviceId the identity of the device.
     * @return the created Sensor object, or null if the sensor could not be created or an exception occurs.
     */
    @Override
    public Sensor addSensor(SensorModelName sensorModelName, DeviceId deviceId) {
        try {
            if (!deviceRepository.containsIdentity(deviceId)) {
                return null;
            }

            Sensor sensor = sensorFactory.createSensor(sensorModelName, deviceId);
            return sensorRepository.save(sensor);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves a Sensor by its identity.
     *
     * @param sensorId The identity of the Sensor to retrieve.
     * @return An Optional of Sensor if found, empty Optional otherwise.
     */
    @Override
    public Optional<Sensor> getByIdentity(SensorId sensorId) {

        return sensorRepository.findByIdentity(sensorId);
    }

    /**
     * Retrieves all SensorId entities associated with a specific device from the repository.
     * This method first checks if the device exists in the repository. If it does not, it returns null.
     * If the device exists, it retrieves all SensorId entities associated with the device.
     *
     * @param deviceId The identity of the device whose associated SensorId entities are to be retrieved.
     * @return An Iterable collection containing all SensorId entities associated with the specified device, or null
     * if the device does not exist.
     */
    @Override
    public Iterable<SensorId> getSensorIdsByDeviceIdentity(DeviceId deviceId) {
        if (!deviceRepository.containsIdentity(deviceId)) {
            return null;
        }
        return sensorRepository.findSensorIdsByDeviceId(deviceId);
    }
}