package smarthome.service.impl;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.actuator.ActuatorOfBlindRoller;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.ReadingFactory;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.service.IActuatorService;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service implementation for managing actuators.
 * This class is responsible for handling all the business logic related to actuators.
 */
@Service
public class ActuatorServiceImpl implements IActuatorService {

    private final IActuatorRepository actuatorRepository;
    private final IDeviceRepository deviceRepository;
    private final ISensorRepository sensorRepository;
    private final IReadingRepository readingRepository;
    private final ActuatorFactory actuatorFactory;
    private final SensorModelName sensorModelForCloseBlinds;
    private final ReadingFactory readingFactory;

    /**
     * Constructs a new ActuatorServiceImpl with the given repositories and factories.
     *
     * @param actuatorRepository the repository for actuators
     * @param deviceRepository   the repository for devices
     */
    @Autowired
    public ActuatorServiceImpl(IActuatorRepository actuatorRepository, IDeviceRepository deviceRepository,
                               ISensorRepository sensorRepository, IReadingRepository readingRepository, @Qualifier("actuatorFactoryImpl")
                               ActuatorFactory actuatorFactory, ReadingFactory readingFactory, @Qualifier("filePathModels") String filePathName) throws ConfigurationException {
        Configurations config = new Configurations();

        this.sensorModelForCloseBlinds = createSensorModelNameForCloseBinds(config, filePathName);
        this.actuatorRepository = actuatorRepository;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.readingRepository = readingRepository;
        this.actuatorFactory = actuatorFactory;
        this.readingFactory = readingFactory;
    }

    /**
     * Creates a sensor model name for close binds.
     *
     * @param config       the configurations
     * @param filePathName the file path name
     * @return the sensor model name
     * @throws ConfigurationException if an exception occurs during the creation of the sensor model name
     */
    private SensorModelName createSensorModelNameForCloseBinds(Configurations config, String filePathName) throws ConfigurationException {
        Configuration configuration = config.properties(new File(filePathName));
        String sensorModelName = configuration.getString("closeBlinds.Sensor");
        return new SensorModelName(sensorModelName);
    }

    /**
     * Adds an actuator with the given actuator map.
     * If the device ID in the actuator map does not exist, or if an exception occurs during the creation of the
     * actuator, null is returned.
     *
     * @param actuatorMap the actuator map
     * @return the added actuator, or null if the actuator could not be added
     */
    @Override
    public Actuator addActuator(ActuatorMap actuatorMap, DeviceId deviceId) {
        try {
            if (!deviceRepository.containsIdentity(deviceId)) {
                return null;
            }
            actuatorMap.setDeviceId(deviceId);
            Actuator actuator = actuatorFactory.createActuator(actuatorMap);
            //save the actuator
            return actuatorRepository.save(actuator);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets an actuator by its id.
     *
     * @param id the id of the actuator to get.
     * @return the actuator with the given id.
     */
    @Override
    public Optional<Actuator> getActuatorById(ActuatorId id) {
        return actuatorRepository.findByIdentity(id);
    }

    /**
     * Gets all the actuators ids by device identity.
     *
     * @param deviceId the id of the device to get the actuators from.
     * @return all the actuators ids.
     */
    @Override
    public Iterable<ActuatorId> getActuatorIdsByDeviceIdentity(DeviceId deviceId) {
        if (!deviceRepository.containsIdentity(deviceId)) {
            return null;
        }
        return actuatorRepository.findActuatorIdsByDeviceId(deviceId);
    }

    /**
     * Operate (open/close) the blind roller with the given id and value.
     *
     * @param id    the id of the actuator to operate
     * @param value the value to operate the actuator with
     */
    @Override
    public Value operateBlindRoller(ActuatorId id, Value value) {
        try {
            Optional<Actuator> actuator = actuatorRepository.findByIdentity(id);
            if (actuator.isEmpty() || !(actuator.get() instanceof ActuatorOfBlindRoller blindRoller)) {
                return null;
            }
            DeviceId deviceId = actuator.get().getDeviceId();
            Optional<Device> device = deviceRepository.findByIdentity(deviceId);
            if (device.isEmpty() || !device.get().getDeviceStatus().getStatus()) {
                return null;
            }

            Value currentValue = blindRoller.operate(value);
            if (currentValue == null) {
                return null;
            }
            Iterable<SensorId> sensorIds = sensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId,
                    sensorModelForCloseBlinds);
            if (!sensorIds.iterator().hasNext()) {
                return null;
            }
            Reading reading = readingFactory.createReading(currentValue, sensorIds.iterator().next(), new TimeStamp(LocalDateTime.now()));
            readingRepository.save(reading);
            return currentValue;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the last percentage reading of the actuator with the given id.
     *
     * @param id the id of the actuator to get the last percentage reading from.
     * @return the last percentage reading of the actuator.
     */
    @Override
    public Optional<Value> getLastPercentageReading(ActuatorId id) {

        Optional<Actuator> actuator = actuatorRepository.findByIdentity(id);
        if (actuator.isEmpty()) {
            return Optional.empty();
        }
        DeviceId deviceId = actuator.get().getDeviceId();
        Optional<Device> device = deviceRepository.findByIdentity(deviceId);
        if (device.isEmpty()) {
            return Optional.empty();
        }
        Iterable<SensorId> sensorIds = sensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId,
                sensorModelForCloseBlinds);
        if (!sensorIds.iterator().hasNext()) {
            return Optional.empty();
        }
        Optional<Reading> lastReading = readingRepository.findLastReadingBySensorId(sensorIds.iterator().next());
        if (lastReading.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(lastReading.get().getValue());
    }
}