package smarthome.service.impl;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import smarthome.domain.actuator.vo.DecimalValue;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.service.IReadingService;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a service for readings.
 */
@Service
public class ReadingServiceImpl implements IReadingService {
    private final IReadingRepository readingRepository;
    private final ISensorRepository sensorRepository;
    private final IDeviceRepository deviceRepository;
    private final long delta;
    private final SensorModelName sensorOfPowerConsumption;
    private final SensorModelName sensorOfTemperature;
    private final DeviceTypeName deviceGridPowerMeter;
    private final DeviceTypeName devicePowerSourcePowerMeter;
    /**
     * Constructs a new ReadingServiceImpl with the specified repositories and mapper.
     *
     * @param readingRepository The repository for readings.
     * @param sensorRepository  The repository for sensors.
     * @param filePathName      The path to the configuration file.
     */
    @Autowired
    public ReadingServiceImpl(IReadingRepository readingRepository, ISensorRepository sensorRepository,
                              IDeviceRepository deviceRepository, @Qualifier("filePathDelta") String filePathName, @Qualifier("filePathModels") String filePathModels) throws ConfigurationException {
        Configurations config = new Configurations();
        this.delta = createDelta(config, filePathName);
        this.readingRepository = readingRepository;
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
        this.sensorOfPowerConsumption = createSensorModelNameOfPowerConsumption(config,filePathModels);
        this.sensorOfTemperature = createSensorModelNameOfTemperature(config,filePathModels);
        this.deviceGridPowerMeter = createDeviceTypeGridPowerMeter(config,filePathModels);
        this.devicePowerSourcePowerMeter = createDeviceTypePowerSourcePowerMeter(config,filePathModels);
    }

    /**
     * Creates a new Reading entity with the given value, sensor ID, and timestamp.
     *
     * @param config The configuration object to read the configuration file.
     * @param filePath The path to the configuration file containing the delta value.
     * @return The delta value.
     * @throws ConfigurationException If the configuration file cannot be read.
     */
    private long createDelta(Configurations config, String filePath)
            throws ConfigurationException {
        Configuration configuration = config.properties(new File(filePath));
        List<String> deltaValues = configuration.getList(String.class, "delta");
        String deltaString = deltaValues.get(0);

        return Long.parseLong(deltaString);
    }

    /**
     * Method to create a SensorModelName of SensorOfTemperature
     * @param configs the configurations object that enables this process
     * @param filePathModels the path to the configModels file
     * @return SensorModelName if everything goes alright
     * @throws ConfigurationException if any mistake occurs
     */
    private SensorModelName createSensorModelNameOfTemperature(Configurations configs, String filePathModels)
            throws ConfigurationException {
        Configuration configuration = configs.properties(new File(filePathModels));
        List<String> sensorValues = List.of(configuration.getStringArray("sensorModel"));
        return new SensorModelName(sensorValues.get(0).substring(0, sensorValues.get(0).indexOf(".")));
    }

    /**
     * Method to create a SensorModelName of SensorOfPowerConsumption
     * @param configs the configurations object that enables this process
     * @param filePathModels the path to the configModels file
     * @return SensorModelName if everything goes alright
     * @throws ConfigurationException if any mistake occurs
     */
    private SensorModelName createSensorModelNameOfPowerConsumption(Configurations configs, String filePathModels)
            throws ConfigurationException {
        Configuration configuration = configs.properties(new File(filePathModels));
        List<String> sensorValues = List.of(configuration.getStringArray("sensorModel"));
        return new SensorModelName(sensorValues.get(9).substring(0, sensorValues.get(9).indexOf(".")));
    }

    /**
     * Method to create a DeviceTypeName of GridPowerMeter
     * @param configs the configurations object that enables this process
     * @param filePathModels the path to the configModels file
     * @return DeviceTypeName if everything goes alright
     * @throws ConfigurationException
     * if any mistake occurs
     */
    private DeviceTypeName createDeviceTypeGridPowerMeter(Configurations configs, String filePathModels)
            throws ConfigurationException {
        Configuration configuration = configs.properties(new File(filePathModels));
        List<String> deviceTypeValues = configuration.getList(String.class,"deviceType");
        return new DeviceTypeName(deviceTypeValues.get(0));
    }

    /**
        * Method to create a DeviceTypeName of PowerSourcePowerMeter
     * @param configs the configurations object that enables this process
     * @param filePathModels the path to the configModels file
     * @return DeviceTypeName if everything goes alright
     * @throws ConfigurationException if any mistake occurs
     */
    private DeviceTypeName createDeviceTypePowerSourcePowerMeter(Configurations configs, String filePathModels)
            throws ConfigurationException{
        Configuration configuration = configs.properties(new File(filePathModels));
        List<String> deviceTypeValues = configuration.getList(String.class,"deviceType");
        return new DeviceTypeName(deviceTypeValues.get(1));
    }


    /**
     * Returns a list of readings from a device in a given period.
     *
     * @param deviceId The id of the device.
     * @param start    The start time of the period.
     * @param end      The end time of the period.
     * @return A list of readings from the device in the given period or null if the parameters are invalid.
     */
    @Override
    public List<Reading> getReadingsFromDeviceInAGivenPeriod(DeviceId deviceId, TimeStamp start, TimeStamp end) {
        if (deviceId == null || !isValidPeriod(start, end)) {
            return null;
        }

        Iterable<Sensor> sensors = sensorRepository.findSensorsByDeviceId(deviceId);

        List<Reading> allReadings = new ArrayList<>();
        for (Sensor sensor : sensors) {
            Iterable<Reading> readingsBySensorId =
                    readingRepository.findReadingsBySensorIdInAGivenPeriod(sensor.getIdentity(), start, end);
            readingsBySensorId.forEach(allReadings::add);
        }
        return allReadings;
    }

    /**
     * Return a list of reading IDs from a device in a given period.
     *
     * @param deviceId The id of the device
     * @param start The start time of the period.
     * @param end The end time of the period.
     * @return A list of reading IDs
     */
    @Override
    public List<ReadingId> getReadingIdsFromDeviceInAGivenPeriod(DeviceId deviceId, TimeStamp start, TimeStamp end) {
        if (deviceId == null || !isValidPeriod(start, end)) {
            return null;
        }
        Iterable<Sensor> sensors = sensorRepository.findSensorsByDeviceId(deviceId);
        List<ReadingId> allReadingIds = new ArrayList<>();
        for (Sensor sensor : sensors) {
            Iterable<ReadingId> readingIdsBySensorId =
                    readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(), start, end);
            readingIdsBySensorId.forEach(allReadingIds::add);
        }
        return allReadingIds;
    }

    /**
     * Retrieves the Reading entity with the given id.
     *
     * @param id The id of the Reading entity.
     * @return The Reading entity with the given id or an empty Optional if no such entity exists.
     */
    @Override
    public Optional<Reading> getReading(ReadingId id) {
        return readingRepository.findByIdentity(id);
    }

    /**
     * Returns the maximum temperature difference between two devices in a given period.
     *
     * @param deviceId1       The id of the first device.
     * @param deviceId2       The id of the second device.
     * @param startTime       The start time of the period.
     * @param endTime         The end time of the period.
     * @return The maximum temperature difference between the two devices in the given period or null if the parameters
     * are invalid.
     */
    @Override
    public Value getMaxInstantTemperatureDifferenceInAGivenPeriod(DeviceId deviceId1, DeviceId deviceId2, TimeStamp startTime,
                                                                  TimeStamp endTime) {
        if (deviceId1 == null || deviceId2 == null || !isValidPeriod(startTime, endTime)) {
            return null;
        }
        try {
            // Get sensors associated with the devices and of model SensorOfTemperature
            Iterable<Sensor> sensorsOnDevice1 = getByDeviceIdentityAndSensorModel(deviceId1, sensorOfTemperature);
            Iterable<Sensor> sensorsOnDevice2 = getByDeviceIdentityAndSensorModel(deviceId2, sensorOfTemperature);
            if (isIterableEmpty(sensorsOnDevice1) || isIterableEmpty(sensorsOnDevice2)) {
                return null;
            }
            // Get readings for each device in the given period
            Iterable<Reading> readingsFromDevice1 = findReadingIdsBySensorIdInAGivenPeriod(sensorsOnDevice1, startTime, endTime);
            Iterable<Reading> readingsFromDevice2 = findReadingIdsBySensorIdInAGivenPeriod(sensorsOnDevice2, startTime, endTime);
            if (isIterableEmpty(readingsFromDevice1) || isIterableEmpty(readingsFromDevice2)) {
                return null;
            }
            // Calculate the maximum temperature difference between the two devices
            Double result = calculateMaxInstantReadingDifference(readingsFromDevice1, readingsFromDevice2);
            if (result == null) {
                return null;
            }
            return new DecimalValue(result);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the peak power consumption in a given period.
     * @param start The start time of the period.
     * @param end The end time of the period.
     * @return The peak power consumption in the given period or null if the parameters are invalid.
     */

    @Override
    public DecimalValue getPeakPowerConsumptionInAGivenPeriod(TimeStamp start, TimeStamp end) {
        if (!isValidPeriod(start, end)) {
            return null;}
        try {
            //Assuming there is only one device of GridPowerMeter type (the return must be an iterable, but we are assuming there is only one device)
            Iterable<DeviceId> gridPowerMeters = deviceRepository.findDeviceIdsByDeviceTypeName(deviceGridPowerMeter);
            //Assuming there can be multiple devices of PowerSourcePowerMeter type
            Iterable<DeviceId> powerSourcePowerMeters = deviceRepository.findDeviceIdsByDeviceTypeName(devicePowerSourcePowerMeter);
            if (!gridPowerMeters.iterator().hasNext()) {
                return null;}
            Iterable<SensorId> sensorIdsGridPower = sensorRepository.findSensorIdsByDeviceIdAndSensorModelName(gridPowerMeters.iterator().next(), sensorOfPowerConsumption);
            List<SensorId> sensorIdsPowerSource = new ArrayList<>();
            for (DeviceId device : powerSourcePowerMeters) {
                Iterable<SensorId> sensorIds = sensorRepository.findSensorIdsByDeviceIdAndSensorModelName(device, sensorOfPowerConsumption);
                for(SensorId sensorId : sensorIds){
                    sensorIdsPowerSource.add(sensorId);}}
            Double peakPowerConsumption = calculatePeakPowerConsumption(sensorIdsGridPower,sensorIdsPowerSource,start,end);
            return new DecimalValue(peakPowerConsumption);
        }
        catch (Exception e) {
            return null;}}

    /**
     * Method to calculate the maximum reading value difference between two devices in a given period
     * based on the delta value.
     *
     * @param readings1 The readings from the first device.
     * @param readings2 The readings from the second device.
     * @return The maximum reading value difference between the two devices in the given period.
     */
    private Double calculateMaxInstantReadingDifference(Iterable<Reading> readings1, Iterable<Reading> readings2) {
        double maximumDifference = -1; // Initialize to -1 to indicate no difference found
        for (Reading r1 : readings1) {
            for (Reading r2 : readings2) {
                long differenceInSeconds = differenceInSecondsBetweenTwoReadings(r1, r2);

                if (differenceInSeconds <= delta) {
                    double differenceInTemperature = differenceInValueBetweenTwoReadings(r1, r2);
                    maximumDifference = Math.max(maximumDifference, differenceInTemperature);
                }
            }
        }
        if (maximumDifference == -1) {
            return null;
        }
        return maximumDifference;
    }

    /**
     * Method to calculate the difference in seconds between two readings.
     *
     * @param r1 The first reading to be compared.
     * @param r2 The second reading to be compared.
     * @return The difference in seconds between the two readings.
     */
    private long differenceInSecondsBetweenTwoReadings(Reading r1, Reading r2) {
        LocalDateTime value1 = r1.getTime().getValue();
        LocalDateTime value2 = r2.getTime().getValue();
        long difference = Duration.between(value1, value2).getSeconds();
        return Math.abs(difference);
    }

    /**
     * Method to calculate the difference in value between two readings.
     *
     * @param r1 The first reading to be compared.
     * @param r2 The second reading to be compared.
     * @return The difference in value between the two readings.
     */
    private double differenceInValueBetweenTwoReadings(Reading r1, Reading r2) {
        return Math.abs(Double.parseDouble(r1.getValue().valueToString())
                - Double.parseDouble(r2.getValue().valueToString()));
    }

    /**
     * Method to check if the given iterable is empty.
     *
     * @param iterable The iterable to be checked.
     * @return true if the iterable is empty, false otherwise.
     */
    private boolean isIterableEmpty(Iterable<?> iterable) {
        return !iterable.iterator().hasNext();
    }

    /**
     * Method to check if the given period is valid.
     *
     * @param startTime The start time of the period.
     * @param endTime   The end time of the period.
     * @return true if the period is valid, false otherwise.
     */
    private boolean isValidPeriod(TimeStamp startTime, TimeStamp endTime) {
        return startTime != null && endTime != null &&
                !startTime.getValue().isAfter(endTime.getValue()) &&
                !startTime.getValue().isEqual(endTime.getValue()) &&
                !endTime.getValue().isAfter(LocalDateTime.now());
    }

    /**
     * Method to get the sensors associated with a given device ID and sensor model.
     *
     * @param deviceID        The device ID for which the sensors are to be retrieved.
     * @param sensorModelName The sensor model name for which the sensors are to be retrieved.
     * @return The sensors associated with the device and of model SensorOfTemperature.
     */
    private Iterable<Sensor> getByDeviceIdentityAndSensorModel(DeviceId deviceID, SensorModelName sensorModelName) {
        return sensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceID, sensorModelName);
    }

    /**
     * Method to get the readings for a list of sensors in a given period.
     *
     * @param sensors   The sensors for which the readings are to be retrieved.
     * @param startTime The start time of the period.
     * @param endTime   The end time of the period.
     * @return The readings for each sensor in the given period.
     */
    private Iterable<Reading> findReadingIdsBySensorIdInAGivenPeriod(Iterable<Sensor> sensors, TimeStamp startTime,
                                                                     TimeStamp endTime) {
        List<Reading> readings = new ArrayList<>();
        for (Sensor sensor : sensors) {
            Iterable<ReadingId> savedReadings = readingRepository.findReadingIdsBySensorIdInAGivenPeriod(
                    sensor.getIdentity(), startTime, endTime);
            for (ReadingId readingId : savedReadings) {
                Optional<Reading> reading = readingRepository.findByIdentity(readingId);
                reading.ifPresent(readings::add);
            }
        }
        return readings;
    }

    /**
     * Method to calculate the peak power consumption in a given period.
     * @param sensorIdsGrid The sensor IDs of the grid power meter.
     * @param sensorIdsPowerSource The sensor IDs of the power source power meter.
     * @param start The start time of the period.
     * @param end The end time of the period.
     * @return The peak power consumption in the given period.
     */
    private Double calculatePeakPowerConsumption(Iterable<SensorId> sensorIdsGrid, Iterable<SensorId>sensorIdsPowerSource, TimeStamp start, TimeStamp end){
        List<TimeStamp[]> splitPeriods = splitTimePeriod(start,end);
        Double peakPowerConsumption = 0.0;
        for(TimeStamp[] timePeriod : splitPeriods){
            TimeStamp startSubPeriod = Arrays.stream(timePeriod).toList().get(0);
            TimeStamp endSubPeriod = Arrays.stream(timePeriod).toList().get(1);
            HashMap<SensorId,Double> powerConsumptionReadingsBySensorId = getAveragePowerConsumptionInPeriodBySensorId(startSubPeriod,endSubPeriod,sensorIdsGrid,sensorIdsPowerSource);
            Double subPeriodPowerConsumption = sumPowerConsumptionDoubles(powerConsumptionReadingsBySensorId);
            peakPowerConsumption = Math.max(peakPowerConsumption,subPeriodPowerConsumption);
        }
        if(peakPowerConsumption == 0.0){
            throw new IllegalArgumentException();
        }
        return peakPowerConsumption;
    }

    /**
     * Method to split a given time period into sub-periods based on the delta value.
     * @param start The start time of the period.
     * @param end The end time of the period.
     * @return A list of sub-periods.
     */
    public List<TimeStamp[]> splitTimePeriod(TimeStamp start, TimeStamp end) {
        List<TimeStamp[]> subPeriods = new ArrayList<>();
        LocalDateTime currentStart = start.getValue();
        Duration deltaNumber = Duration.ofSeconds(this.delta);
        while (currentStart.isBefore(end.getValue())) {
            LocalDateTime currentEnd = currentStart.plus(deltaNumber);
            if (currentEnd.isAfter(end.getValue())) {
                currentEnd = end.getValue();}
            subPeriods.add(new TimeStamp[]{new TimeStamp(currentStart), new TimeStamp(currentEnd)});
            currentStart = currentEnd;}
        return subPeriods;}

    /**
     * Method to calculate the average power consumption in a given period by sensor ID.
     * @param start The start time of the period.
     * @param end The end time of the period.
     * @param sensorIdsGrid The sensor IDs of the grid power meter.
     * @param sensorIdsPowerSource The sensor IDs of the power source power meter.
     * @return A map of sensor IDs and their average power consumption in the given period.
     */
    private HashMap<SensorId,Double> getAveragePowerConsumptionInPeriodBySensorId(TimeStamp start, TimeStamp end, Iterable<SensorId> sensorIdsGrid, Iterable<SensorId> sensorIdsPowerSource){
        HashMap<SensorId,Double> powerConsumptionReadingsBySensorId = new HashMap<>();
        for (SensorId sensorId : sensorIdsGrid){
            Iterable<Reading> readings = readingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end);
            Double averagePowerConsumption = calculateAveragePowerConsumption(readings);
            powerConsumptionReadingsBySensorId.put(sensorId,averagePowerConsumption);}
        for (SensorId sensorId : sensorIdsPowerSource){
            Iterable<Reading> readings = readingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end);
            Double averagePowerConsumption = calculateAveragePowerConsumption(readings);
            powerConsumptionReadingsBySensorId.put(sensorId,averagePowerConsumption);}
        return powerConsumptionReadingsBySensorId;}

    /**
     * Method to sum the power consumption values in a given period.
     * @param powerConsumptionReadingsBySensorId A map of sensor IDs and their power consumption values.
     * @return The sum of the power consumption values.
     */
    private Double sumPowerConsumptionDoubles(HashMap<SensorId,Double> powerConsumptionReadingsBySensorId){
        Double sum = 0.0;
        for (Double value : powerConsumptionReadingsBySensorId.values()){
            sum += value;}
        return sum;
    }

    /**
     * Method to calculate the average power consumption in a given period.
     * @param readings The readings in the given period.
     * @return The average power consumption in the given period.
     */
    private Double calculateAveragePowerConsumption(Iterable<Reading> readings){
        Double sum = 0.0;
        int count = 0;
        for (Reading reading : readings){
            sum += Double.parseDouble(reading.getValue().valueToString());
            count++;}
        if(count == 0){return 0.0;}
        return sum/count;
    }
}