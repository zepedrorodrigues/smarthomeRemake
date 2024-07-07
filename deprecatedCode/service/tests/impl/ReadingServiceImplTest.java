package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingValue;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.PeriodMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Unit tests for the ReadingServiceImpl class.
 */
class ReadingServiceImplTest {


    ReadingServiceImpl validReadingService;
    DeviceMapper validDeviceMapper;
    ISensorRepository validSensorRepository;
    IReadingRepository validReadingRepository;
    PeriodMapper validPeriodMapper;
    String pathToDelta;

    @BeforeEach
    void setUp() {
        validReadingRepository = mock(IReadingRepository.class);
        validSensorRepository = mock(ISensorRepository.class);
        validDeviceMapper = mock(DeviceMapper.class);
        validPeriodMapper = mock(PeriodMapper.class);
        pathToDelta = "configTest.properties";

    }


    /**
     * Test that the constructor of ReadingServiceImpl throws an exception when the readingRepository is null.
     */
    @Test
    void testConstructorOfReadingServiceImplThrowsAnExceptionForNullReadingRepository() {
        // Arrange
        ISensorRepository sensorRepository = mock(ISensorRepository.class);
        PeriodMapper periodMapper = mock(PeriodMapper.class);
        DeviceMapper deviceMapper = mock(DeviceMapper.class);
        String pathToDelta = "pathToDelta";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReadingServiceImpl(null, sensorRepository,
                        deviceMapper, periodMapper, pathToDelta),
                "Constructor should throw an IllegalArgumentException when the readingRepository is null");
    }

    /**
     * Test that the constructor of ReadingServiceImpl throws an exception when the sensorRepository is null.
     */
    @Test
    void testConstructorOfReadingServiceImplThrowsAnExceptionForNullSensorRepository() {
        // Arrange
        IReadingRepository readingRepository = mock(IReadingRepository.class);
        PeriodMapper periodMapper = mock(PeriodMapper.class);
        DeviceMapper deviceMapper = mock(DeviceMapper.class);
        String pathToDelta = "pathToDelta";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReadingServiceImpl(readingRepository, null,
                        deviceMapper, periodMapper, pathToDelta),
                "Constructor should throw an IllegalArgumentException when the sensorRepository is null");
    }

    /**
     * Test that the constructor of ReadingServiceImpl throws an exception when the deviceMapper is null.
     */
    @Test
    void testConstructorOfReadingServiceImplThrowsAnExceptionForNullDeviceMapper() {
        // Arrange
        IReadingRepository readingRepository = mock(IReadingRepository.class);
        ISensorRepository sensorRepository = mock(ISensorRepository.class);
        PeriodMapper periodMapper = mock(PeriodMapper.class);
        String pathToDelta = "pathToDelta";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReadingServiceImpl(readingRepository, sensorRepository,
                        null, periodMapper, pathToDelta),
                "Constructor should throw an IllegalArgumentException when the deviceMapper is null");
    }

    /**
     * Test that the constructor of ReadingServiceImpl throws an exception when the periodMapper is null.
     */
    @Test
    void testConstructorOfReadingServiceImplThrowsAnExceptionForNullPeriodMapper() {
        // Arrange
        IReadingRepository readingRepository = mock(IReadingRepository.class);
        ISensorRepository sensorRepository = mock(ISensorRepository.class);
        DeviceMapper deviceMapper = mock(DeviceMapper.class);
        String pathToDelta = "pathToDelta";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReadingServiceImpl(readingRepository, sensorRepository,
                        deviceMapper, null, pathToDelta),
                "Constructor should throw an IllegalArgumentException when the periodMapper is null");
    }

    /**
     * Test that the constructor of ReadingServiceImpl does not throw an exception when all arguments are not-null.
     */
    @Test
    void testConstructorOfReadingServiceImplDoesNotThrowAnExceptionForNonNullArguments()
            throws InstantiationException {
        // Arrange
        IReadingRepository readingRepository = mock(IReadingRepository.class);
        ISensorRepository sensorRepository = mock(ISensorRepository.class);
        DeviceMapper deviceMapper = mock(DeviceMapper.class);
        PeriodMapper periodMapper = mock(PeriodMapper.class);

        // Act
        ReadingServiceImpl result = new ReadingServiceImpl(readingRepository, sensorRepository, deviceMapper,
                periodMapper, pathToDelta);

        // Assert
        assertNotNull(result,
                "Constructor should not throw an exception when all arguments are non-null");
    }

    /**
     * Test getMaxTemperatureDifference method with null DeviceDTO1 should throw an exception
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDeviceDTO1IsNull()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock the arguments of the method
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                null, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, meaning that the method throws an exception");
    }

    /**
     * Test getMaxTemperatureDifference method with null DeviceDTO2 should throw an exception
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDeviceDTO2IsNull()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock the arguments of the method
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, null, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, meaning that the method throws an exception");
    }

    /**
     * Test getMaxTemperatureDifference method with null PeriodDTO should throw an exception
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsNull()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock the arguments of the method
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, null);

        // Assert
        assertNull(result,
                "The result should be null, meaning that the method throws an exception");
    }


    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensor() throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)), new TimeStamp(
                        LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(List.of(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(List.of(reading2));

        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 50));
        when(reading1.getValue()).thenReturn(new ReadingValue("-10.0"));
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(reading2.getValue()).thenReturn(new ReadingValue("-20.0"));

        Double differenceExpected = 10.0; // - 10 - 20.0

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the absolute difference between 10.0 and 20.0");
    }


    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor with negative values
     * in the period should return the absolute difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorWithNegativeValues()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(List.of(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(List.of(reading2));

        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 50));
        when(reading1.getValue()).thenReturn(new ReadingValue("20.0"));
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(reading2.getValue()).thenReturn(new ReadingValue("10.0"));

        Double differenceExpected = 10.0; // 10.0 - (-10.0) from the 1st reading of each sensor

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the absolute difference between -10.0 and -20.0");
    }


    /**
     * Test getMaxTemperatureDifference method when exist two readings in each sensor
     * in the period should return the maximum difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistTwoReadingsInEachSensor()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 20));
        when(reading1.getValue()).thenReturn(new ReadingValue("12.0"));

        Reading reading1B = mock(Reading.class);
        TimeStamp timeStamp1B = mock(TimeStamp.class);
        when(reading1B.getTimeStamp()).thenReturn(timeStamp1B);
        when(timeStamp1B.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 54));
        when(reading1B.getValue()).thenReturn(new ReadingValue("30.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(reading2.getValue()).thenReturn(new ReadingValue("9.0"));

        Reading reading2B = mock(Reading.class);
        TimeStamp timeStamp2B = mock(TimeStamp.class);
        when(reading2B.getTimeStamp()).thenReturn(timeStamp2B);
        when(timeStamp2B.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 4));
        when(reading2B.getValue()).thenReturn(new ReadingValue("10.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(reading1, reading1B));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(reading2, reading2B));

        Double differenceExpected = 20.0; // 30.0 - 10.0

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);
        // Assert
        assertEquals(differenceExpected, result, "The result should be 20.0");
    }


    /**
     * Test getMaxTemperatureDifference method when exist two readings in one sensor
     * and one in the other sensor in the period should return the difference between the two readings
     * comparing the two readings of the sensor with two times the same reading of the other sensor
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingTwoReadingWithOne()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 54));
        when(reading1.getValue()).thenReturn(new ReadingValue("10.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 50));
        when(reading2.getValue()).thenReturn(new ReadingValue("15.0"));

        Reading reading2B = mock(Reading.class);
        TimeStamp timeStamp2B = mock(TimeStamp.class);
        when(reading2B.getTimeStamp()).thenReturn(timeStamp2B);
        when(timeStamp2B.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 4));
        when(reading2B.getValue()).thenReturn(new ReadingValue("20.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(List.of(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(reading2, reading2B));

        Double differenceExpected = 10.0; // 10-20 from the 1st reading of Device 1 against the 2nd reading of Device 2

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertEquals(differenceExpected, result, "The result should be 10.0");
    }


    /**
     * Test getMaxTemperatureDifference method when the two readings are equal in value
     * should return 0.0
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingEqualReadingValues()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 54));
        when(reading1.getValue()).thenReturn(new ReadingValue("10.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 50));
        when(reading2.getValue()).thenReturn(new ReadingValue("10.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(List.of(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(List.of(reading2));

        Double differenceExpected = 0.0; // 10- 10

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertEquals(differenceExpected, result, "The result should be 0.0");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta
     * and there are readings in each sensor in that period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsLessThanDeltaAndThereAreReadingsInEachSensorInThatPeriod()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 5));
        when(reading1.getValue()).thenReturn(new ReadingValue("20.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 10));
        when(reading2.getValue()).thenReturn(new ReadingValue("10.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading2));

        Double differenceExpected = 10.0; // 10.0 - 20.0

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertEquals(differenceExpected, result, "The result should be 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta by one second
     * and there are readings in each sensor in that period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsLessThanDeltaByOneSecond()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(reading1.getValue()).thenReturn(new ReadingValue("20.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 59));
        when(reading2.getValue()).thenReturn(new ReadingValue("10.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading2));

        Double differenceExpected = 10.0; // 10.0 - 20.0

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the absolute difference between 10.0 and 20.0");
    }

    /**
     * Test getMaxTemperatureDifference method when period is equals to delta
     * and there are readings in each sensor in that period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsEqualsToDelta()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(reading1.getValue()).thenReturn(new ReadingValue("20.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 1, 0));
        when(reading2.getValue()).thenReturn(new ReadingValue("10.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading2));

        Double differenceExpected = 10.0; // 10.0 - 20.0

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the absolute difference between 10.0 and 20.0");
    }


    /**
     * Test getMaxTemperatureDifference method when period is biggest than delta by one second
     * and there are readings in each sensor in that period should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsBiggestThanDeltaByOneSecond()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(reading1.getValue()).thenReturn(new ReadingValue("20.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 1, 1));
        when(reading2.getValue()).thenReturn(new ReadingValue("10.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Arrays.asList(reading2));

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, the period is bigger than delta");
    }


    /**
     * Test getMaxTemperatureDifference method when period is in incorrect order
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodInIncorrectOrder()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, the period is in incorrect order");
    }


    /**
     * Test getMaxTemperatureDifference method when period has end date in future
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodHasEndDateInFuture()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2300, 1, 10, 7, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, the period cannot be in the future");
    }

    /**
     * Test getMaxTemperatureDifference method when period has equal dates
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsWithEqualDates()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, the period cannot have equal dates");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodInIncorrectFormat()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        // When the period is in incorrect format, the method toStart and toEnd will throw an exception
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenThrow(new IllegalArgumentException());
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenThrow(new IllegalArgumentException());

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result, "The result should be null, the period is in incorrect format");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in that period
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInThatPeriod()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock reading repositories

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(List.of());
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(List.of());

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, there is no readings in that period and");
    }


    /**
     * Test getMaxTemperatureDifference method when there are readings in that period
     * but not comparable by delta time should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereReadingsInThatPeriodButNotComparableByDeltaTime()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice2Double));
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Mock readings
        //Device 1
        Reading reading1 = mock(Reading.class);
        TimeStamp timeStamp1 = mock(TimeStamp.class);
        when(reading1.getTimeStamp()).thenReturn(timeStamp1);
        when(timeStamp1.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 7, 20, 54));
        when(reading1.getValue()).thenReturn(new ReadingValue("20.0"));

        //Device 2
        Reading reading2 = mock(Reading.class);
        TimeStamp timeStamp2 = mock(TimeStamp.class);
        when(reading2.getTimeStamp()).thenReturn(timeStamp2);
        when(timeStamp2.getValue()).thenReturn(LocalDateTime.of(2023, 1, 10, 8, 20, 50));
        when(reading2.getValue()).thenReturn(new ReadingValue("10.0"));

        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice1IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(reading1));
        when(validReadingRepository.findBySensorIdInAGivenPeriod(sensorTempDevice2IdDouble,
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(reading2));

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, " +
                        "there are readings in that period but not comparable by delta time");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in any sensor
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAreNoReadingsInAnySensor()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);
        SensorId sensorTempDevice1IdDouble = mock(SensorId.class);
        Sensor sensorTempDevice2Double = mock(Sensor.class);
        SensorId sensorTempDevice2IdDouble = mock(SensorId.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(List.of());
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(List.of());
        when(sensorTempDevice1Double.getIdentity()).thenReturn(sensorTempDevice1IdDouble);
        when(sensorTempDevice2Double.getIdentity()).thenReturn(sensorTempDevice2IdDouble);

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, " +
                        "there are no readings in any sensor");
    }


    /**
     * Test getMaxTemperatureDifference method when the device 1 has no sensor of temperature
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice1HasNoSensorOfTemperature()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice2Double = mock(Sensor.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(List.of());
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice2Double));

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

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
    void testGetMaxInstantaneousTempDifferenceWhenDevice2HasNoSensorOfTemperature()
            throws InstantiationException {
        // Arrange
        validReadingService = new ReadingServiceImpl(validReadingRepository, validSensorRepository, validDeviceMapper,
                validPeriodMapper, pathToDelta);
        // Mock device
        DeviceDTO deviceDTODouble1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTODouble2 = mock(DeviceDTO.class);
        DeviceId deviceIdDouble1 = mock(DeviceId.class);
        DeviceId deviceIdDouble2 = mock(DeviceId.class);
        when(validDeviceMapper.toDeviceId(deviceDTODouble1)).thenReturn(deviceIdDouble1);
        when(validDeviceMapper.toDeviceId(deviceDTODouble2)).thenReturn(deviceIdDouble2);

        // Mock period
        PeriodDTO periodDTODouble = mock(PeriodDTO.class);
        TimeStamp startDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0));
        TimeStamp endDateTime = new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0));
        when(validPeriodMapper.toStart(any(PeriodDTO.class))).thenReturn(startDateTime);
        when(validPeriodMapper.toEnd(any(PeriodDTO.class))).thenReturn(endDateTime);

        // Mock sensors
        Sensor sensorTempDevice1Double = mock(Sensor.class);

        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble1), any(SensorModelName.class)))
                .thenReturn(Arrays.asList(sensorTempDevice1Double));
        when(validSensorRepository.getByDeviceIdentityAndSensorModel(eq(deviceIdDouble2), any(SensorModelName.class)))
                .thenReturn(List.of());

        // Act
        Double result = validReadingService.getMaxInstantTemperatureDifference(
                deviceDTODouble1, deviceDTODouble2, periodDTODouble);

        // Assert
        assertNull(result,
                "The result should be null, " +
                        "there are no temperature sensor in device 2");
    }


}