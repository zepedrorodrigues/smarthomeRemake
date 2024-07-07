package smarthome.service.impl;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.PeriodMapper;
import smarthome.service.IReadingService;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The ReadingServiceImpl class is responsible for providing services related to reading operations.
 */
public class ReadingServiceImpl implements IReadingService {

    private final IReadingRepository readingRepository;
    private final ISensorRepository sensorRepository;
    private final DeviceMapper deviceMapper;
    private final PeriodMapper periodMapper;
    private final Long delta;


    /**
     * Constructs a new ReadingServiceImpl with the specified repositories and mapper.
     *
     * @param readingRepository The repository for readings.
     * @param sensorRepository  The repository for sensors.
     * @param deviceMapper      The mapper for devices.
     * @param periodMapper      The mapper for periods.
     * @param filePathName      The path to the configuration file. The configuration file contains the delta value.
     * @throws IllegalArgumentException If any of the given repositories or mapper is null or if the configuration file is not found.
     */
    public ReadingServiceImpl(IReadingRepository readingRepository, ISensorRepository sensorRepository,
                              DeviceMapper deviceMapper, PeriodMapper periodMapper, String filePathName) throws InstantiationException {
        if (readingRepository == null || sensorRepository == null || deviceMapper == null || periodMapper == null) {
            throw new IllegalArgumentException();
        }

        Configurations configurations = new Configurations();
        Configuration configuration;
        try {
            configuration = configurations.properties(new File(filePathName));

        } catch (ConfigurationException | NullPointerException e) {
            throw new InstantiationException();
        }
        List<String> deltaList = List.of(configuration.getStringArray("delta"));
        String deltaString = deltaList.get(0);

        this.delta = Long.parseLong(deltaString);
        this.readingRepository = readingRepository;
        this.sensorRepository = sensorRepository;
        this.deviceMapper = deviceMapper;
        this.periodMapper = periodMapper;
    }

    /**
     * This implementation calculates the maximum temperature difference between two devices in a given period.
     * It first checks if the given DeviceDTOs and PeriodDTO are null. If not, it extracts the device IDs from the DTOs
     * and converts the period DTO to start and end TimeStamp objects. It then gets the sensors associated with the
     * devices and of model SensorOfTemperature. It then gets the readings for each device in the given period and
     * calculates the maximum temperature difference between the two devices.
     * If any of the given DeviceDTOs, PeriodDTO, sensors, readings are null or empty, it returns null.
     * If the maximum difference is -1, it returns null.
     * Otherwise, it returns the maximum difference.
     * </p>
     *
     * @param deviceDTO1 The first device DTO.
     * @param deviceDTO2 The second device DTO.
     * @param periodDTO  The period DTO.
     * @return The maximum temperature difference between the two devices in the given period.
     * If any of the given DeviceDTOs, PeriodDTO, sensors, readings are null or empty, it returns null.
     */
    @Override
    public Double getMaxInstantTemperatureDifference(DeviceDTO deviceDTO1, DeviceDTO deviceDTO2, PeriodDTO periodDTO) {
        try {
            if (deviceDTO1 == null || deviceDTO2 == null || periodDTO == null) {
                return null;
            }

            // Extract device IDs from the DTOs
            DeviceId deviceId1 = deviceMapper.toDeviceId(deviceDTO1);
            DeviceId deviceId2 = deviceMapper.toDeviceId(deviceDTO2);

            // Convert period DTO to start and end TimeStamp objects
            TimeStamp startTime = periodMapper.toStart(periodDTO);
            TimeStamp endTime = periodMapper.toEnd(periodDTO);

            if (startTime.getValue().isAfter(endTime.getValue()) ||
                    startTime.getValue().isEqual(endTime.getValue()) ||
                    endTime.getValue().isAfter(LocalDateTime.now())) {
                return null;
            }

            // Get sensors associated with the devices that are of model SensorOfTemperature
            Iterable<Sensor> sensorsOnDevice1 = getByDeviceIdentityAndSensorOfTemperature(deviceId1);
            Iterable<Sensor> sensorsOnDevice2 = getByDeviceIdentityAndSensorOfTemperature(deviceId2);
            if (isIterableEmpty(sensorsOnDevice1) || isIterableEmpty(sensorsOnDevice2)) {
                return null;
            }

            // Get readings for each device in the given period
            Iterable<Reading> readingsFromDevice1 = findBySensorIdInAGivenPeriod(sensorsOnDevice1, startTime, endTime);
            Iterable<Reading> readingsFromDevice2 = findBySensorIdInAGivenPeriod(sensorsOnDevice2, startTime, endTime);
            if (isIterableEmpty(readingsFromDevice1) || isIterableEmpty(readingsFromDevice2)) {
                return null;
            }

            // Calculate the maximum temperature difference between the two devices
            return calculateMaxInstantReadingDifference(readingsFromDevice1, readingsFromDevice2);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Helper method to calculate the maximum temperature difference between two devices in a given period.
     * This implementation calculates the maximum temperature difference between two devices in a given period.
     * It first checks if the given DeviceIds and PeriodDTO are null. If not, it gets the sensors associated with the
     * devices and of model SensorOfTemperature. It then gets the readings for each device in the given period and
     * calculates the maximum temperature difference between the two devices.
     * If any of the given DeviceIds, PeriodDTO, sensors, readings are null or empty, it returns null.
     * If the maximum difference is -1, it returns null.
     * Otherwise, it returns the maximum difference.
     *
     * @param readings1 The list of readings from the first device.
     * @param readings2 The list of readings from the second device.
     * @return The maximum temperature difference between the two devices in the given period.
     */
    private Double calculateMaxInstantReadingDifference(Iterable<Reading> readings1, Iterable<Reading> readings2) {
        double maximumDifference = -1;
        for (Reading r1 : readings1) {
            for (Reading r2 : readings2) {
                long differenceInSeconds = Math.abs(Duration.between(r1.getTimeStamp().getValue(),
                        r2.getTimeStamp().getValue()).getSeconds());

                if (differenceInSeconds <= delta) {
                    double differenceInTemperature =
                            Math.abs(Double.parseDouble(r1.getValue().valueToString()) -
                                    Double.parseDouble(r2.getValue().valueToString()));
                    if (differenceInTemperature > maximumDifference) {
                        maximumDifference = differenceInTemperature;
                    }
                }
            }
        }
        if (maximumDifference == -1) {
            return null;
        }
        return maximumDifference;
    }

    /**
     * Helper method to get the sensors associated with the device and of model SensorOfTemperature.
     * <p>
     * This implementation gets the sensors associated with the device and of model SensorOfTemperature.
     * If the given DeviceId is null, it returns an empty list.
     * Otherwise, it returns the sensors associated with the device and of model SensorOfTemperature.
     * </p>
     * @param deviceID The device ID.
     * @return The sensors associated with the device and of model SensorOfTemperature.
     */
    private Iterable<Sensor> getByDeviceIdentityAndSensorOfTemperature(DeviceId deviceID) {
        SensorModelName sensorModelName = new SensorModelName("SensorOfTemperature");
        return sensorRepository.getByDeviceIdentityAndSensorModel(deviceID, sensorModelName);
    }

    /**
     * Helper method to get the readings for each sensor in the given period.
     * <p>
     * This implementation gets the readings for each sensor in the given period.
     * If the given sensors, start time, or end time are null, it returns an empty list.
     * Otherwise, it returns the readings for each sensor in the given period.
     * </p>
     * @param sensors The sensors.
     * @param startTime The start time.
     * @param endTime The end time.
     * @return The readings for each sensor in the given period.
     */
    private Iterable<Reading> findBySensorIdInAGivenPeriod(Iterable<Sensor> sensors, TimeStamp startTime,
                                                           TimeStamp endTime) {
        List<Reading> readings = new ArrayList<>();
        for (Sensor sensor : sensors) {
            Iterable<Reading> savedReadings = readingRepository.findBySensorIdInAGivenPeriod(sensor.getIdentity(),
                    startTime, endTime);
            savedReadings.forEach(readings::add);
        }
        return readings;
    }

    /**
     * Helper method to check if the given iterable is empty.
     * <p>
     * This implementation checks if the given iterable is empty.
     * If the iterable is empty, it returns true. Otherwise, it returns false.
     * </p>
     * @param iterable The iterable to be checked.
     * @return true if the iterable is empty, false otherwise.
     */
    private boolean isIterableEmpty(Iterable<?> iterable) {
        return !iterable.iterator().hasNext();
    }
}
