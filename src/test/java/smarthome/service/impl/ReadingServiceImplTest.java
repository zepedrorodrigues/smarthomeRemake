package smarthome.service.impl;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.vo.DecimalValue;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.ReadingValue;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.service.IReadingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ReadingServiceImpl class.
 */
class ReadingServiceImplTest {

    ISensorRepository mockSensorRepository;
    IReadingRepository mockReadingRepository;
    IDeviceRepository mockDeviceRepository;
    IReadingService service;
    String pathToDelta;
    String pathToModels;
    DeviceId deviceId;
    DeviceId deviceId2;
    DeviceTypeName gridPowerMeter;
    DeviceTypeName powerSourcePowerMeter;
    TimeStamp start;
    TimeStamp end;
    Sensor sensor;
    SensorId sensorId2;
    SensorId sensorId;
    SensorModelName sensorOfPowerConsumption;
    Sensor sensor2;
    Reading reading;
    ReadingValue readingValue;
    ReadingId readingId;
    Reading reading2;
    ReadingId readingId2;
    ReadingValue readingValue2;
    Reading reading3;
    ReadingId readingId3;
    ReadingValue readingValue3;
    Reading reading4;
    ReadingId readingId4;
    ReadingValue readingValue4;
    SensorModelName sensorOfTemperature;
    TimeStamp timeReading1;
    TimeStamp timeReading2;
    TimeStamp timeReading3;
    TimeStamp timeReading4;

    /**
     * Set up the mock objects and the service before each test.
     *
     * @throws ConfigurationException if the configuration file is not valid
     */
    @BeforeEach
    void setUp() throws ConfigurationException {
        mockReadingRepository = mock(IReadingRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockDeviceRepository = mock(IDeviceRepository.class);
        pathToDelta = "configDelta.properties"; // Path contains a valid delta value
        pathToModels ="configModels.properties";
        service = new ReadingServiceImpl(mockReadingRepository, mockSensorRepository, mockDeviceRepository,pathToDelta,pathToModels);
        deviceId = mock(DeviceId.class);
        deviceId2 = mock(DeviceId.class);
        gridPowerMeter = new DeviceTypeName("GridPowerMeter");
        powerSourcePowerMeter = new DeviceTypeName("PowerSourcePowerMeter");
        start = mock(TimeStamp.class);
        end = mock(TimeStamp.class);
        sensor = mock(Sensor.class);
        sensor2 = mock(Sensor.class);
        sensorId = mock(SensorId.class);
        sensorId2 = mock(SensorId.class);
        sensorOfTemperature = new SensorModelName("SensorOfTemperature");
        sensorOfPowerConsumption = new SensorModelName("SensorOfPowerConsumption");
        reading = mock(Reading.class);
        readingId = mock(ReadingId.class);
        readingValue = mock(ReadingValue.class);
        reading2 = mock(Reading.class);
        readingId2 = mock(ReadingId.class);
        readingValue2 = mock(ReadingValue.class);
        reading3 = mock(Reading.class);
        readingId3 = mock(ReadingId.class);
        readingValue3 = mock(ReadingValue.class);
        reading4 = mock(Reading.class);
        readingId4 = mock(ReadingId.class);
        readingValue4 = mock(ReadingValue.class);
        timeReading1 = mock(TimeStamp.class);
        timeReading2 = mock(TimeStamp.class);
        timeReading3 = mock(TimeStamp.class);
        timeReading4 = mock(TimeStamp.class);

        // Mock reading repository
        when(mockReadingRepository.findByIdentity(readingId)).thenReturn(Optional.of(reading));
        when(mockReadingRepository.findByIdentity(readingId2)).thenReturn(Optional.of(reading2));
        when(mockReadingRepository.findByIdentity(readingId3)).thenReturn(Optional.of(reading3));
        when(mockReadingRepository.findByIdentity(readingId4)).thenReturn(Optional.of(reading4));

        // Give corresponding timestamps to the readings
        when(reading.getTime()).thenReturn(timeReading1);
        when(reading2.getTime()).thenReturn(timeReading2);
        when(reading3.getTime()).thenReturn(timeReading3);
        when(reading4.getTime()).thenReturn(timeReading4);

        // Give the corresponding values to the readings
        when(reading.getValue()).thenReturn(readingValue);
        when(reading2.getValue()).thenReturn(readingValue2);
        when(reading3.getValue()).thenReturn(readingValue3);
        when(reading4.getValue()).thenReturn(readingValue4);

        // Give corresponding id to the sensors
        when(sensor.getIdentity()).thenReturn(sensorId);
        when(sensor2.getIdentity()).thenReturn(sensorId2);

        // Give corresponding device id to sensor model
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(eq(deviceId), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(eq(deviceId2), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensor2));
    }


    // UNIT TESTS FOR THE CONSTRUCTOR OF ReadingServiceImpl:

    /**
     * Test that the constructor of ReadingServiceImpl throws an exception when the pathToDelta is
     * not a valid path.
     */
    @Test
    void testConstructorOfReadingRESTServiceImplThrowsAnExceptionWhenPathToDeltaIsNotValid() {
        // Arrange
        String pathToDelta = "this Path^Does´Not`Exist »"; // Path should not exist

        // Act & Assert
        assertThrows(ConfigurationException.class, () -> new ReadingServiceImpl(
                        mockReadingRepository, mockSensorRepository, mockDeviceRepository,pathToDelta,pathToModels),
                "Constructor should throw an IllegalArgumentException when the pathToDelta does not exist");
    }

    /**
     * Test that the constructor of ReadingServiceImpl throws an exception when the pathToDelta does not
     * contain a valid delta value.
     */
    @Test
    void testConstructorOfReadingRESTServiceImplThrowsAnExceptionWhenPathLeadsToNonExistentDelta() {
        // Arrange
        String invalidPathToDelta = "configTest2.properties"; // Path does not contain a valid delta value

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ReadingServiceImpl(
                        mockReadingRepository, mockSensorRepository, mockDeviceRepository,invalidPathToDelta,pathToModels),
                "Constructor should throw an NullPointerException" +
                        "when the pathToDelta does not contain a valid delta value");
    }

    /**
     *
     */
    @Test
    void testConstructorOfReadingRESTServiceImplThrowsAnExceptionWhenPathLeadsToNonExistentsensorModels() {
        // Arrange
        String invalidPathToModels = "configTest3.properties"; // Path does not contain a valid delta value

        // Act & Assert
        assertThrows(ConfigurationException.class, () -> new ReadingServiceImpl(
                        mockReadingRepository, mockSensorRepository, mockDeviceRepository,pathToDelta,invalidPathToModels),
                "Constructor should throw an NullPointerException" +
                        "when the pathToDelta does not contain a valid delta value");
    }

    /**
     * Test that the constructor of ReadingServiceImpl does not throw an exception when all arguments are valid.
     */
    @Test
    void testConstructorOfReadingRESTServiceImplDoesNotThrowAnExceptionWhenAllArgumentsAreValid() {
        // Arrange + Act + Assert
        assertNotNull(service,
                "Constructor should not throw an exception when all arguments are valid");
    }


    // (GetMaxInstantaneousTempDifference method)
    // UNIT TESTS FOR DEVICE ID VALIDATION:

    /**
     * Test getMaxTemperatureDifference method with null DeviceID1 should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDeviceID1IsNull() {
        // Arrange// Mock the dependencies of the method
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));
        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                null, deviceId, start, end);
        // Assert
        assertNull(result,
                "The result should be null, meaning that the method throws an exception");
    }

    /**
     * Test getMaxTemperatureDifference method with null DeviceID2 should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDeviceID2IsNull() {
        // Arrange// Mock the dependencies of the method
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, null, start, end);
        // Assert
        assertNull(result,
                "The result should be null, meaning that the method throws an exception");
    }


    // (GetMaxInstantaneousTempDifference method)
    // UNIT TESTS FOR SENSOR VALIDATION:

    /**
     * Test getMaxTemperatureDifference method when the device 1 has no sensor of temperature
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice1HasNoSensorOfTemperature() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        // Mock sensors
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(List.of());
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, " +
                        "there are no temperature sensor in device 1");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 2 has no sensor of temperature
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice2HasNoSensorOfTemperature() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        // Mock sensors
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(List.of());

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, " +
                        "there are no temperature sensor in device 2");
    }

    // (GetMaxInstantaneousTempDifference method)
    // UNIT TESTS FOR PERIOD VALIDATION:

    /**
     * Test getMaxTemperatureDifference method with null StartTime should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenStartTimeIsNullShouldReturnNull() {
        // Arrange
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, null, end);

        // Assert
        assertNull(result,
                "The result should be null, meaning that the method rejects the null StartTime");
    }

    /**
     * Test getMaxTemperatureDifference method with null EndTime should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenEndTimeIsNullShouldReturnNull() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, null);

        // Assert
        assertNull(result,
                "The result should be null, meaning that the method rejects the null EndTime");
    }

    /**
     * Test getMaxTemperatureDifference method when startTime and endTime are in incorrect order
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodInIncorrectOrder() {
        // Arrange
        // End time before start time:
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, the period is in incorrect order");
    }

    /**
     * Test getMaxTemperatureDifference method when period has end date in future
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodHasEndDateInFuture() {
        // Arrange
        // The end date is in the future:
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2223, 1, 10, 8, 0, 0));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, the period cannot be in the future");
    }

    /**
     * Test getMaxTemperatureDifference method when period has equal dates
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsWithEqualDates() {
        // Arrange
        // The start and end dates are equal:
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, the period cannot have equal dates");
    }


    // (GetMaxInstantaneousTempDifference method)
    // UNIT TESTS FOR READINGS VALIDATION:

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorShouldReturnAbsoluteDifference() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        List<Sensor> sensorList1 = List.of(sensor);
        List<Sensor> sensorList2 = List.of(sensor2);
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature)).thenReturn(sensorList1);
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature)).thenReturn(sensorList2);
        // Mock reading objects
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 50));
        when(readingValue.valueToString()).thenReturn("20.0");
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(readingValue2.valueToString()).thenReturn("10.0");
        // Mock reading repository
        List<ReadingId> readingIds1 = List.of(readingId);
        List<ReadingId> readingIds2 = List.of(readingId2);
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(readingIds1);
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start, end)).thenReturn(readingIds2);

        String differenceExpected = "10.0";

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertEquals(differenceExpected, result.valueToString(),
                "The result should be 10.0, the absolute difference between 20.0 and 10.0");

    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor with negative values
     * in the period should return the absolute difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorWithNegativeValues() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock reading objects
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 50));
        when(readingValue.valueToString()).thenReturn("-10.0");
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(readingValue2.valueToString()).thenReturn("-20.0");

        // Mock reading repository
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        String differenceExpected = "10.0"; // - 10.0 - (-20.0) from the 1st reading of each sensor

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertEquals(differenceExpected, result.valueToString(),
                "The result should be 10.0, the absolute difference between -10.0 and -20.0");
    }


    /**
     * Test getMaxTemperatureDifference method when exist two readings in each sensor
     * in the period should return the maximum difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistTwoReadingsInEachSensor() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock readings
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 20));
        when(readingValue.valueToString()).thenReturn("30.0");
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 54));
        when(readingValue2.valueToString()).thenReturn("12.0");
        //Device 2
        when(timeReading3.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(readingValue3.valueToString()).thenReturn("10.0");
        when(timeReading4.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 4));
        when(readingValue4.valueToString()).thenReturn("9.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Arrays.asList(readingId, readingId2));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Arrays.asList(readingId3, readingId4));

        String differenceExpected = "20.0"; // 30.0 - 10.0

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);
        // Assert
        assertEquals(differenceExpected, result.valueToString(), "The result should be 20.0");
    }


    /**
     * Test getMaxTemperatureDifference method when exist two readings in one sensor
     * and one in the other sensor in the period should return the difference between the two readings
     * comparing the two readings of the sensor with two times the same reading of the other sensor
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingTwoReadingWithOne() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock readings
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 20));
        when(readingValue.valueToString()).thenReturn("10.0");
        //Device 2
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 54));
        when(readingValue2.valueToString()).thenReturn("15.0");
        when(timeReading3.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 4));
        when(readingValue3.valueToString()).thenReturn("20.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Arrays.asList(readingId2, readingId3));

        String differenceExpected = "10.0"; // 10-20 from the 1st reading of Device 1 against the 2nd reading of Device 2

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertEquals(differenceExpected, result.valueToString(),
                "The result should be 10.0, the difference between 10.0 and 20.0");
    }


    /**
     * Test getMaxTemperatureDifference method when the two readings are equal in value
     * should return 0.0
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingEqualReadingValues() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock reading objects
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 50));
        when(readingValue.valueToString()).thenReturn("10.0");
        //Device 2
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(readingValue2.valueToString()).thenReturn("10.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        String differenceExpected = "0.0"; // 10.0 - 10.0 from the 1st reading of each sensor

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertEquals(differenceExpected, result.valueToString(),
                "The result should be 0.0, the difference between 10.0 and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta
     * and there are readings in each sensor in that period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsLessThanDeltaAndThereAreValidReadingsToCompare() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 30));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock readings
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 5));
        when(readingValue.valueToString()).thenReturn("20.0");
        //Device 2
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 10));
        when(readingValue2.valueToString()).thenReturn("10.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        String differenceExpected = "10.0"; // 20.0 - 10.0

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertEquals(differenceExpected, result.valueToString(),
                "The result should be 10.0, the difference between 10.0 and 20.0");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is less than delta by one second
     * and there are readings in each sensor in that period should return the difference calculated
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsLessThanDeltaByOneSecond() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock readings
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 1, 0));
        when(readingValue.valueToString()).thenReturn("20.0");
        //Device 2
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 1, 59));
        when(readingValue2.valueToString()).thenReturn("10.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        String differenceExpected = "10.0"; // 20.0 - 10.0

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertEquals(differenceExpected, result.valueToString(),
                "The result should be 10.0, the difference between 10.0 and 20.0");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is equals to delta
     * and there are readings in each sensor in that period should return the difference calculated
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsAreEqualsToDelta() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock readings
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 1, 0));
        when(readingValue.valueToString()).thenReturn("20.0");
        //Device 2
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 2, 0));
        when(readingValue2.valueToString()).thenReturn("10.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        String differenceExpected = "10.0"; // 20.0 - 10.0

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);


        // Assert
        assertEquals(differenceExpected, result.valueToString(),
                "The result should be 10.0, the absolute difference between 10.0 and 20.0");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is bigger than delta by one second
     * and there are readings in each sensor in that period should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsAreBiggerThanDeltaByOneSecond() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock readings
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 1, 0));
        when(readingValue.valueToString()).thenReturn("20.0");
        //Device 2
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 2, 1));
        when(readingValue2.valueToString()).thenReturn("10.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, the readings are not in the same delta period");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in that period
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInThatPeriod() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock reading repository, no readings in the period
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(List.of());
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(List.of());

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, there is no readings in that period and");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in device 1
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInDevice1() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock reading repository, device 1 has no readings but device 2 has
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(List.of());
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, there is no readings in that period and");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in device 2
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInDevice2() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock reading repository, device 2 has no readings but device 1 has
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(List.of(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(List.of());

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, there is no readings in that period and");
    }

    /**
     * Test getMaxTemperatureDifference method when there are readings in that period
     * but not comparable by delta time should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereReadingsInThatPeriodButNotComparableByDeltaTime() {
        // Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));

        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfTemperature))
                .thenReturn(Collections.singletonList(sensor2));

        // Mock readings
        //Device 1
        when(timeReading1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 5, 0));
        when(readingValue.valueToString()).thenReturn("20.0");
        //Device 2
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 10, 0));
        when(readingValue2.valueToString()).thenReturn("10.0");

        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start,
                end)).thenReturn(Collections.singletonList(readingId));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId2, start,
                end)).thenReturn(Collections.singletonList(readingId2));

        // Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(
                deviceId, deviceId2, start, end);

        // Assert
        assertNull(result,
                "The result should be null, " +
                        "there are readings in that period but not comparable by delta time");
    }

    @Test
    void testGetMaxInstantaneousTempDifferentThrowsExceptionShouldReturnNull() {
        //Arrange
        when(start.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(end.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorOfTemperature))
                .thenThrow(new IllegalArgumentException());
        //Act
        Value result = service.getMaxInstantTemperatureDifferenceInAGivenPeriod(deviceId, deviceId2, start, end);
        //Assert
        assertNull(result);
    }

    // getReadingsFromDeviceInAGivenPeriod method

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns a list of Readings when the device has readings in the given period.
     * The test will pass if the result is not null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnListOfReadingsWhenDeviceHasReadingsInGivenPeriod() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(List.of(sensor));
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(List.of(reading));

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNotNull(result, "The result should be not null, because the parameters are valid and there are" +
                " readings in the given period associated with the given device.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns an empty list when the device has no readings in the given period.
     * The test will pass if the result is an empty list.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnEmptyListWhenDeviceHasNoReadingsInGivenPeriod() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(List.of(sensor));
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(new ArrayList<>());

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertTrue(result.isEmpty(), "The result should be an empty list, because there are no readings in the given " +
                "period.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is after the end date.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenStartDateIsAfterEndDate() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        LocalDateTime endValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNull(result, "The result should be null, because the start date is after the end date.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is equal to the end date.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenStartDateIsEqualToEndDate() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNull(result, "The result should be null, because the start date is equal to end date.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the end date is in the future.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenEndDateIsInFuture() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2025, 1, 1, 0, 0);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNull(result, "The result should be null, because the end date is in the future.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the device is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenDeviceIsNull() {
        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(null, start, end);

        // Assert
        assertNull(result, "The result should be null because the device is null.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is null.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenStartDateIsNull() {
        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, null, end);

        // Assert
        assertNull(result, "The result should be null because the start date is null.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the end date is null.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenEndDateIsNull() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, null);

        // Assert
        assertNull(result, "The result should be null because the end date is null.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns an empty list when the device does not exist.
     * The test will pass if the result is an empty list
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnEmptyListWhenDeviceDoesNotExist() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(new ArrayList<>());

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        boolean isEmpty = result.isEmpty();
        assertTrue(isEmpty, "The result should be an empty list, because the device does not exist.");
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns an empty list when the device has no sensors.
     * The test will pass if the result is an empty list.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnEmptyListWhenDeviceHasNoSensors() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(new ArrayList<>());

        // Act
        List<Reading> result = service.getReadingsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        boolean isEmpty = result.isEmpty();
        assertTrue(isEmpty, "The result should be an empty list, because the device has no sensors.");
    }

    // getReadingIdsFromDeviceInAGivenPeriod method

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns a list of Reading IDs when the device has readings in the given period.
     * The test will pass if the result is not null.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnListOfReadingIdsWhenDeviceHasReadingsInGivenPeriod() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(List.of(sensor));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(List.of(readingId));

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNotNull(result, "The result should be not null, because the parameters are valid and there are" +
        " readings in the given period associated with the given device.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns an empty list when the device has no readings in the given period.
     * The test will pass if the result is an empty list.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnEmptyListWhenDeviceHasNoReadingsInGivenPeriod() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(List.of(sensor));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(new ArrayList<>());

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        boolean isEmpty = result.isEmpty();
        assertTrue(isEmpty, "The result should be an empty list, because there are no readings in the given period.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is after the end date.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnNullWhenStartDateIsAfterEndDate() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        LocalDateTime endValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNull(result, "The result should be null, because the start date is after the end date.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is equal to the end date.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnNullWhenStartDateIsEqualToEndDate() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNull(result, "The result should be null, because the start date is equal to end date.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the end date is in the future.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnNullWhenEndDateIsInFuture() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2025, 1, 1, 0, 0);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        assertNull(result, "The result should be null, because the end date is in the future.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the device is null.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnNullWhenDeviceIsNull() {
        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(null, start, end);

        // Assert
        assertNull(result, "The result should be null because the device is null.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is null.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnNullWhenStartDateIsNull() {
        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, null, end);

        // Assert
        assertNull(result, "The result should be null because the start date is null.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the end date is null.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnNullWhenEndDateIsNull() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, null);

        // Assert
        assertNull(result, "The result should be null because the end date is null.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns an empty list when the device does not exist.
     * The test will pass if the result is an empty list
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnEmptyListWhenDeviceDoesNotExist() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(new ArrayList<>());

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        boolean isEmpty = result.isEmpty();
        assertTrue(isEmpty, "The result should be an empty list, because the device does not exist.");
    }

    /**
     * Test for the getReadingIdsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns an empty list when the device has no sensors.
     * The test will pass if the result is an empty list.
     */
    @Test
    void testGetReadingIdsFromDeviceShouldReturnEmptyListWhenDeviceHasNoSensors() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(new ArrayList<>());

        // Act
        List<ReadingId> result = service.getReadingIdsFromDeviceInAGivenPeriod(deviceId, start, end);

        // Assert
        boolean isEmpty = result.isEmpty();
        assertTrue(isEmpty, "The result should be an empty list, because the device has no sensors.");
    }



    // UNIT TEST TO GET READING BY ID

    /**
     * Test getReading method when the reading exists, should return an optional of reading.
     */
    @Test
    void testGetReadingWhenExistsShouldReturnOptionalOfReading() {
        // Arrange
        when(mockReadingRepository.findByIdentity(readingId)).thenReturn(Optional.of(reading));

        // Act
        Optional<Reading> result = service.getReading(readingId);

        // Assert
        assertTrue(result.isPresent() && reading.equals(result.get()),
                "The optional Reading object should not be empty, meaning the reading exists.");
    }

    /**
     * Test getReading method when the reading does not exist, should return an optional empty.
     */
    @Test
    void testGetReadingWhenNotExistsShouldReturnOptionalEmpty() {
        // Arrange
        when(mockReadingRepository.findByIdentity(readingId)).thenReturn(Optional.empty());

        // Act
        Optional<Reading> result = service.getReading(readingId);

        // Assert
        assertTrue(result.isEmpty(),
                "The optional Reading object should be empty, meaning the reading does not exist.");
    }

    /**
     * Test getReading method when an exception is thrown, should throw an exception.
     */
    @Test
    void testGetReadingWhenAnExceptionIsThrownShouldThrowException() {
        // Arrange
        when(mockReadingRepository.findByIdentity(readingId)).thenThrow(RuntimeException.class);

        // Act - Assert
        assertThrows(RuntimeException.class, () -> service.getReading(readingId),
                "Should throw an exception when an error occurs.");
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with null start date.
     * Should return null
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodInvalidStartNullShouldReturnNull(){
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(null,timeReading1);
        //Assert
        assertNull(peakPowerConsumption);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with null end date.
     * Should return null
     */
    @Test
    void testGetPeakPowerCconsumptionInAGivenPeriodInvalidEndNullShouldReturnNull(){
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(timeReading1,null);
        //Assert
        assertNull(peakPowerConsumption);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with start date after end date.
     * Should return null
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodInvalidStartAfterEndShouldReturnNull(){
        //Arrange
        TimeStamp timeReading2 = mock(TimeStamp.class);
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp timeReading3 = mock(TimeStamp.class);
        when(timeReading3.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 10));
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(timeReading3,timeReading2);
        //Assert
        assertNull(peakPowerConsumption);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with start date equal to end date.
     * Should return null
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodInvalidStartInTheSameMomentAsEndShouldReturnNull(){
        //Arrange
        TimeStamp timeReading2 = mock(TimeStamp.class);
        when(timeReading2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(timeReading2,timeReading2);
        //Assert
        assertNull(peakPowerConsumption);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with start date after now.
     * should return null
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodInvalidStartAfterNowShouldReturnNull(){
        //Arrange
        TimeStamp timeReading2 = mock(TimeStamp.class);
        when(timeReading2.getValue()).thenReturn(LocalDateTime.now().plusSeconds(10));
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(timeReading2,timeReading2);
        //Assert
        assertNull(peakPowerConsumption);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with one grid device and one power source device
     * and no readings in the period.
     * Should return null
     */

    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodOneGridPowerMeterOnePowerSourcePowerMeterNoReadingsInThePeriodShouldReturnNull(){
        //Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        Sensor sensor1 = mock(Sensor.class);
        when(sensor1.getSensorModelName()).thenReturn(sensorOfPowerConsumption);
        Sensor sensor2 = mock(Sensor.class);
        when(sensor2.getSensorModelName()).thenReturn(sensorOfPowerConsumption);
        SensorId sensorId1 = mock(SensorId.class);
        SensorId sensorId3 = mock(SensorId.class);
        when(sensor1.getIdentity()).thenReturn(sensorId1);
        when(sensor2.getIdentity()).thenReturn(sensorId3);
        TimeStamp start = mock(TimeStamp.class);
        String startString = "2023-01-10T07:00:00";
        when(start.getValue()).thenReturn(LocalDateTime.parse(startString));
        TimeStamp end = mock(TimeStamp.class);
        String endString = "2023-01-10T07:02:00";
        String middleString = "2023-01-10T07:01:00";
        when(end.getValue()).thenReturn(LocalDateTime.parse(endString));
        TimeStamp middlePeriod = mock(TimeStamp.class);
        when(middlePeriod.getValue()).thenReturn(LocalDateTime.parse(middleString));
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(gridPowerMeter)).thenReturn(List.of(deviceId1));
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(powerSourcePowerMeter)).thenReturn(List.of(deviceId2));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId1,sensorOfPowerConsumption)).thenReturn((List.of(sensorId1)));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId2,sensorOfPowerConsumption)).thenReturn(List.of(sensorId3));
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId1, start, middlePeriod)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId3, start, middlePeriod)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId1, middlePeriod, end)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId3, middlePeriod, end)).thenReturn(List.of());
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(start,end);
        //Assert
        assertNull(peakPowerConsumption);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with one grid device and one power source device
     * and one reading of grid power and no readings of power source.
     * Should return the value of the reading of the grid power
     */
    @Test
    void getPeakPowerConsumptionInAGivenPeriodOneGridPowerMeterOnePowerSourcePowerMeter1ReadingOfGridPowerNoReadingsOfPowerSourceShouldReturnTheValueOfTheReading() {
        // Setup
        Double expectedValue = 123.45;
        TimeStamp startTime = new TimeStamp(LocalDateTime.parse("2023-01-01T07:00:00"));
        TimeStamp endTime = new TimeStamp(LocalDateTime.parse("2023-01-01T07:02:00"));
        TimeStamp middleTime = new TimeStamp(LocalDateTime.parse("2023-01-01T07:01:00"));
        // Mocking device and sensor IDs
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(gridPowerMeter))
                .thenReturn(Collections.singletonList(deviceId)); // One grid power meter device
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(powerSourcePowerMeter))
                .thenReturn(Collections.singletonList(deviceId2)); // One power source power meter device

        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId, sensorOfPowerConsumption))
                .thenReturn(Collections.singletonList(sensorId)); // Sensor for grid power meter
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId2, sensorOfPowerConsumption))
                .thenReturn(Collections.singletonList(sensorId2)); // No sensor for power source power meter
        // Mocking readings
        Iterable<Reading> gridPowerMeterReadings = Collections.singletonList(reading);
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, startTime, middleTime)).thenReturn(gridPowerMeterReadings);
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId2, startTime, middleTime)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, middleTime, endTime)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId2, middleTime, endTime)).thenReturn(List.of());
        // One reading for grid power meter
        when(reading.getValue().valueToString()).thenReturn("123.45"); // Value of the reading
        // Test
        DecimalValue peakPower = service.getPeakPowerConsumptionInAGivenPeriod(startTime, endTime);
        Double result = Double.parseDouble(peakPower.valueToString());
        // Assertion
        assertEquals(expectedValue,result);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() with zero grid devices
     * should return null
     */
    @Test
    void getPeakPowerConsumptionInPeriodOneGridPowerMeterZeroGridDevicesOnePowerSourceDeviceShouldReturnNull(){
        //Arrange
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        Sensor sensor1 = mock(Sensor.class);
        when(sensor1.getSensorModelName()).thenReturn(sensorOfPowerConsumption);
        Sensor sensor2 = mock(Sensor.class);
        when(sensor2.getSensorModelName()).thenReturn(sensorOfPowerConsumption);
        SensorId sensorId1 = mock(SensorId.class);
        SensorId sensorId3 = mock(SensorId.class);
        when(sensor1.getIdentity()).thenReturn(sensorId1);
        when(sensor2.getIdentity()).thenReturn(sensorId3);
        TimeStamp start = mock(TimeStamp.class);
        String startString = "2023-01-10T07:00:00";
        when(start.getValue()).thenReturn(LocalDateTime.parse(startString));
        TimeStamp end = mock(TimeStamp.class);
        String endString = "2023-01-10T09:00:00";
        when(end.getValue()).thenReturn(LocalDateTime.parse(endString));
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(gridPowerMeter)).thenReturn(List.of());
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(powerSourcePowerMeter)).thenReturn(List.of(deviceId2));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId1, sensorOfPowerConsumption)).thenReturn(List.of(sensor1));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorOfPowerConsumption)).thenReturn(List.of(sensor2));
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId1, start, end)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId3, start, end)).thenReturn(List.of());
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(start,end);
        //Assert
        assertNull(peakPowerConsumption);
    }

    /**
     * Test to method getPeakPowerConsumptionInAGivenPeriod() where one process throws Exception
     * Checks that the return is null
     */
    @Test
    void getPeakPowerConsumptionThrowsExceptionShouldReturnNull(){
        TimeStamp start = mock(TimeStamp.class);
        String startString = "2023-01-10T07:00:00";
        when(start.getValue()).thenReturn(LocalDateTime.parse(startString));
        TimeStamp end = mock(TimeStamp.class);
        String endString = "2023-01-10T09:00:00";
        when(end.getValue()).thenReturn(LocalDateTime.parse(endString));
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(gridPowerMeter)).thenThrow(new IllegalArgumentException());
        //Act
        DecimalValue peakPowerConsumption = service.getPeakPowerConsumptionInAGivenPeriod(start,end);
        //Assert
        assertNull(peakPowerConsumption);
    }

    @Test
    void getPeakPowerConsumptionPeriod90SecondsOneReadingOfGridPowerConsumptionOneReadingOfPowerSourceShouldReturn100(){
        Double expectedValue = 123.45;
        TimeStamp startTime = new TimeStamp(LocalDateTime.parse("2023-01-01T07:00:00"));
        TimeStamp endTime = new TimeStamp(LocalDateTime.parse("2023-01-01T07:01:30"));
        TimeStamp middleTime = new TimeStamp(LocalDateTime.parse("2023-01-01T07:01:00"));
        // Mocking device and sensor IDs
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(gridPowerMeter))
                .thenReturn(Collections.singletonList(deviceId)); // One grid power meter device
        when(mockDeviceRepository.findDeviceIdsByDeviceTypeName(powerSourcePowerMeter))
                .thenReturn(Collections.singletonList(deviceId2)); // One power source power meter device

        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId, sensorOfPowerConsumption))
                .thenReturn(Collections.singletonList(sensorId)); // Sensor for grid power meter
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(deviceId2, sensorOfPowerConsumption))
                .thenReturn(Collections.singletonList(sensorId2)); // No sensor for power source power meter
        // Mocking readings
        Iterable<Reading> gridPowerMeterReadings = Collections.singletonList(reading);
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, startTime, middleTime)).thenReturn(gridPowerMeterReadings);
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId2, startTime, middleTime)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, middleTime, endTime)).thenReturn(List.of());
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId2, middleTime, endTime)).thenReturn(List.of());
        // One reading for grid power meter
        when(reading.getValue().valueToString()).thenReturn("123.45"); // Value of the reading
        // Test
        DecimalValue peakPower = service.getPeakPowerConsumptionInAGivenPeriod(startTime, endTime);
        Double result = Double.parseDouble(peakPower.valueToString());
        // Assertion
        assertEquals(expectedValue,result);


    }
}