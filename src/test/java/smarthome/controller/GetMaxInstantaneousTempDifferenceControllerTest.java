package smarthome.controller;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.ReadingFactory;
import smarthome.domain.reading.ReadingFactoryImpl;
import smarthome.domain.reading.vo.ReadingValue;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.ValueDTO;
import smarthome.mapper.mapper.PeriodMapper;
import smarthome.mapper.mapper.ValueMapper;
import smarthome.service.IReadingService;
import smarthome.service.impl.ReadingServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Integration test class for GetMaxInstantaneousTempDifferenceController
 */
class GetMaxInstantaneousTempDifferenceControllerTest {

    private GetMaxInstantaneousTempDifferenceController controller;
    private String deviceId1;
    private String deviceID2;
    private PeriodDTO validPeriodDTO;
    private IReadingRepository readingRepository;
    private ReadingFactory readingFactory;
    private Sensor sensorTempDevice1;
    private Sensor sensorTempDevice2;
    private ISensorRepository sensorRepository;
    private IDeviceRepository deviceRepository;
    private SensorModelName sensorModelName;
    private Device device1;
    private Device device2;
    private ValueMapper valueMapper;
    private PeriodMapper periodMapper;
    private IReadingService readingService;

    /**
     * Setup for the tests
     */
    @BeforeEach
    void setUp() throws InstantiationException, ConfigurationException {
        // Create a valid reading service, repository and factory
        readingFactory = new ReadingFactoryImpl();
        readingRepository = mock(IReadingRepository.class);
        sensorRepository = mock(ISensorRepository.class);
        deviceRepository = mock(IDeviceRepository.class);
        periodMapper = new PeriodMapper();
        valueMapper = new ValueMapper();
        String pathToDelta = "configTest.properties";
        String pathToModels = "configModels.properties";
        readingService = new ReadingServiceImpl(readingRepository, sensorRepository, deviceRepository,pathToDelta,pathToModels);

        // Create a valid controller
        controller = new GetMaxInstantaneousTempDifferenceController(
                readingService, periodMapper, valueMapper);

        // Two valid devices with one sensor of temperature each
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        SensorFactory sensorFactory = new SensorFactoryImpl();
        sensorModelName = new SensorModelName("SensorOfTemperature");

        // Device 1
        device1 = deviceFactory.createDevice(
                new DeviceName("device1"),
                new DeviceTypeName("deviceType"),
                new RoomId("roomId1"));
        deviceId1 = device1.getIdentity().getIdentity();
        sensorTempDevice1 = sensorFactory.createSensor(sensorModelName, device1.getIdentity());
        // Device 2
        device2 = deviceFactory.createDevice(
                new DeviceName("device2"),
                new DeviceTypeName("deviceType"),
                new RoomId("roomId2"));
        deviceID2 = device2.getIdentity().getIdentity();
        sensorTempDevice2 = sensorFactory.createSensor(sensorModelName, device2.getIdentity());

        // Sensor model of temperature
        sensorModelName = new SensorModelName("SensorOfTemperature");

        // Mock the sensorRepository
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(eq(device1.getIdentity()), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice1));
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(eq(device2.getIdentity()), any(SensorModelName.class)))
                .thenReturn(Collections.singletonList(sensorTempDevice2));
    }

    /**
     * Test the constructor of GetMaxInstantaneousTempDifferenceController
     * with a valid reading service should create an instance
     */
    @Test
    void testConstructorWithValidReadingServiceShouldCreateInstance() {
        // Arrange + Act + Assert
        assertNotNull(controller, "Controller should not be null");
    }

    /**
     * Test the constructor of GetMaxInstantaneousTempDifferenceController
     * with a null reading service should throw an exception
     */
    @Test
    void testConstructorWithInvalidReadingServiceShouldThrowException() {
        // Arrange
        IReadingService readingService = null;
        // Act + Assert
        assertThrows(
                IllegalArgumentException.class, () -> new GetMaxInstantaneousTempDifferenceController(
                        readingService, periodMapper, valueMapper),
                "Controller should throw an exception when readingService is null");
    }

    /**
     * Test constructor of GetMaxInstantaneousTempDifferenceController with null
     * periodMapper should throw an exception
     */
    @Test
    void testConstructorWithNullPeriodMapperShouldThrowException() {
        // Arrange
        PeriodMapper periodMapper = null;
        // Act + Assert
        assertThrows(
                IllegalArgumentException.class, () -> new GetMaxInstantaneousTempDifferenceController(
                        readingService, periodMapper, valueMapper),
                "Controller should throw an exception when periodMapper is null");
    }


    /**
     * Test getMaxTemperatureDifference method with null Device id 1 should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDeviceID1IsNull() {
        // Arrange + Act
        ValueDTO result = controller.getMaxTemperatureDifference(
                null, deviceID2, validPeriodDTO);

        // Assert
        assertNull(result, "The result should be null, the device id 1 is null");
    }

    /**
     * Test getMaxTemperatureDifference method with null DeviceID2 return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDeviceID2IsNull() {
        // Arrange + Act
        ValueDTO result = controller.getMaxTemperatureDifference(
                deviceId1, null, validPeriodDTO);

        // Assert
        assertNull(result, "The result should be null, the device id 2 is null");
    }

    /**
     * Test getMaxTemperatureDifference method with null PeriodDTO should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsNull() {
        // Arrange + Act
        ValueDTO result = controller.getMaxTemperatureDifference(
                deviceId1, deviceID2, null);

        // Assert
        assertNull(result, "The result should be null, the PeriodDTO is null");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorComparable() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T08:00:00");
        ValueDTO differenceExpected = new ValueDTO("10.0");
        // Add one reading to each sensor
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(readingRepository.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(readingRepository.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertEquals(differenceExpected, result, "The result should be 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor with negative values
     * in the period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorWithNegativeValues() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T08:00:00");
        ValueDTO differenceExpected = new ValueDTO("10.0");
        // Add one reading to each sensor
        Reading reading1 = readingFactory.createReading(new ReadingValue("-10.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("-20.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Mock the sensorRepository
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(device1.getIdentity(), sensorModelName))
                .thenReturn(Collections.singletonList(sensorTempDevice1));
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(device2.getIdentity(), sensorModelName))
                .thenReturn(Collections.singletonList(sensorTempDevice2));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(readingRepository.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(readingRepository.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the absolute difference between -10.0 and -20.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return the difference in absolute value between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorShouldReturnTheAbsoluteDifference() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T08:00:00");
        ValueDTO differenceExpected = new ValueDTO("10.0");
        // Add one reading to each sensor
        Reading reading1 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 0))))
                .thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(readingRepository.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(readingRepository.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the absolute difference between 10.0 and 20.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist two readings in each sensor
     * in the period should return the maximum difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistTwoReadingsInEachSensor() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T09:00:00");
        ValueDTO differenceExpected = new ValueDTO("20.0");
        // Device 1
        Reading readingA1 = readingFactory.createReading(new ReadingValue("12.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 20)));
        Reading readingA2 = readingFactory.createReading(new ReadingValue("30.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 54)));
        // Device 2
        Reading readingB1 = readingFactory.createReading(new ReadingValue("9.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        Reading readingB2 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 4)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(readingA1.getIdentity(), readingA2.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(readingB1.getIdentity(), readingB2.getIdentity()));
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));
        when(readingRepository.findByIdentity(readingA2.getIdentity())).thenReturn(Optional.of(readingA2));
        when(readingRepository.findByIdentity(readingB1.getIdentity())).thenReturn(Optional.of(readingB1));
        when(readingRepository.findByIdentity(readingB2.getIdentity())).thenReturn(Optional.of(readingB2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 20.0 because the maximum difference is 20.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist two readings in one sensor
     * and one in the other sensor in the period should return the difference between the two readings
     * comparing the two readings of the sensor with two times the same reading of the other sensor
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingTwoReadingWithOne() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T09:00:00");
        ValueDTO differenceExpected = new ValueDTO("10.0");
        // Device 1
        Reading readingA1 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 54)));
        // Device 2
        Reading readingB1 = readingFactory.createReading(new ReadingValue("15.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 50)));
        Reading readingB2 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 4)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Arrays.asList(readingB1.getIdentity(), readingB2.getIdentity()));
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));
        when(readingRepository.findByIdentity(readingB1.getIdentity())).thenReturn(Optional.of(readingB1));
        when(readingRepository.findByIdentity(readingB2.getIdentity())).thenReturn(Optional.of(readingB2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertEquals(differenceExpected, result, "The result should be 10.0, comparing the two readings of" +
                "Device 1 against the 2nd reading of Device 2");
    }

    /**
     * Test getMaxTemperatureDifference method when the two readings are equal in value
     * should return 0.0
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingEqualReadingValues() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T09:00:00");
        ValueDTO differenceExpected = new ValueDTO("0.0");
        // Device 1
        Reading readingA1 = readingFactory.createReading(new ReadingValue("15.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 54)));
        // Device 2
        Reading readingB1 = readingFactory.createReading(new ReadingValue("15.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 50)));

        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Collections.singletonList(readingB1.getIdentity()));
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));
        when(readingRepository.findByIdentity(readingB1.getIdentity())).thenReturn(Optional.of(readingB1));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertEquals(differenceExpected, result, "The result should be 0.0");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta
     * and there are readings in each sensor in that period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsLessThanDeltaAndThereAreReadingsInEachSensorInThatPeriod() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T07:00:30");
        ValueDTO differenceExpected = new ValueDTO("10.0");

        // Add one reading to each sensor
        Reading readingA1 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 5)));
        Reading readingB2 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 10)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Collections.singletonList(readingB2.getIdentity()));
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));
        when(readingRepository.findByIdentity(readingB2.getIdentity())).thenReturn(Optional.of(readingB2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);
        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the period is less than delta");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta by one second
     * and there are readings in each sensor in that period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsLessThanDeltaByOneSecond() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T08:00:30"); // delta = 60 seconds
        ValueDTO differenceExpected = new ValueDTO("10.0");
        // Add one reading to each sensor
        Reading readingA1 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)));
        Reading readingB2 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 59)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 30))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 0, 30))))
                .thenReturn(Collections.singletonList(readingB2.getIdentity()));
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));
        when(readingRepository.findByIdentity(readingB2.getIdentity())).thenReturn(Optional.of(readingB2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);
        // Assert
        assertEquals(differenceExpected, result,
                "The result should be 10.0, the period is less than delta by one second");
    }

    /**
     * Test getMaxTemperatureDifference method when period is equals to delta
     * and there are readings in each sensor in that period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsEqualsToDelta() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T06:00:00", "2023-01-10T08:01:00"); // delta = 60 seconds
        ValueDTO differenceExpected = new ValueDTO("10.0");
        // Add one reading to each sensor
        Reading readingA1 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)));
        Reading readingB1 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 1, 0)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 6, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 1, 0))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 6, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 1, 0))))
                .thenReturn(Collections.singletonList(readingB1.getIdentity()));
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));
        when(readingRepository.findByIdentity(readingB1.getIdentity())).thenReturn(Optional.of(readingB1));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertEquals(differenceExpected, result, "The result should be 10.0" +
                " because the period is equals to delta");
    }

    /**
     * Test getMaxTemperatureDifference method when period is bigger than delta by one second
     * and there are readings in each sensor in that period should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsBiggestThanDeltaByOneSecond() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T07:01:00"); // delta = 60 seconds
        // Add one reading to each sensor
        Reading readingA1 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)));
        Reading readingB1 = readingFactory.createReading(new ReadingValue("10.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 1, 1)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 1, 0))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 1, 0))))
                .thenReturn(Collections.singletonList(readingB1.getIdentity()));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertNull(result, "The result should be null" +
                " because the period is biggest than delta by one second");
    }

    /**
     * Test getMaxTemperatureDifference method when period is in incorrect order
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodInIncorrectOrder() {
        // Arrange
        PeriodDTO invalidPeriodDTO = new PeriodDTO("2023-01-10T08:00:00", "2023-01-10T07:00:00");

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, invalidPeriodDTO);

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
        PeriodDTO invalidPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2300-01-10T07:00:00");

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, invalidPeriodDTO);

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
        PeriodDTO invalidPeriodDTO = new PeriodDTO("2300-01-10T08:00:00", "2300-01-10T08:00:00");

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, invalidPeriodDTO);

        // Assert
        assertNull(result,
                "The result should be null, the period cannot have equal dates");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return the difference between the two readings
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodInIncorrectFormat() {
        // Arrange
        PeriodDTO invalidPeriodDTO = new PeriodDTO("Yesterday", "Today");

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, invalidPeriodDTO);

        // Assert
        assertNull(result, "The result should be null, the period is in incorrect format");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta
     * and there is one reading in that period and the other out of that period
     * but in the delta range should return null
     */
    @Test
    void testGetMaxInstTempDifferenceWheExistOnlyOneReadingInThatPeriod() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T07:00:30");

        // Add one reading to each sensor
        Reading readingA1 = readingFactory.createReading(new ReadingValue("20.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 5)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 30))))
                .thenReturn(List.of());
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertNull(result,
                "The result should be null," +
                        "there is only one reading in that period");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in that period
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereNoReadingsInThatPeriod() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-02-10T07:00:00", "2023-02-10T09:00:00");
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 2, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 2, 10, 9, 0, 0))))
                .thenReturn(List.of());
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 2, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 2, 10, 9, 0, 0))))
                .thenReturn(List.of());

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertNull(result,
                "The result should be null, there are no readings in that period");
    }

    /**
     * Test getMaxTemperatureDifference method when there are readings in that period
     * but not comparable by delta time should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereReadingsInThatPeriodButNotComparableByDeltaTime() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T09:00:00");
        // Device 1
        Reading readingA1 = readingFactory.createReading(new ReadingValue("15.0"), sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Device 2
        Reading readingB1 = readingFactory.createReading(new ReadingValue("15.0"), sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 8, 20, 50)));
        // Mock the readingRepository
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice1.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Collections.singletonList(readingA1.getIdentity()));
        when(readingRepository.findReadingIdsBySensorIdInAGivenPeriod (sensorTempDevice2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 0)),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 9, 0, 0))))
                .thenReturn(Collections.singletonList(readingB1.getIdentity()));
        when(readingRepository.findByIdentity(readingA1.getIdentity())).thenReturn(Optional.of(readingA1));
        when(readingRepository.findByIdentity(readingB1.getIdentity())).thenReturn(Optional.of(readingB1));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertNull(result,
                "The result should be null, the readings are not comparable by delta time");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 1 has no sensor of temperature
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhen1stDeviceHasNoSensorOfTemperature() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T08:00:00");

        // Mock the sensorRepository
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(device1.getIdentity(), sensorModelName))
                .thenReturn(List.of());
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(device2.getIdentity(),
                sensorModelName)).thenReturn(Collections.singletonList(sensorTempDevice2));

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertNull(result,
                "The result should be null, 1st device has no sensor of temperature");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 2 has no sensor of temperature
     * should return null
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhen2ndDeviceHasNoSensorOfTemperature() {
        // Arrange
        validPeriodDTO = new PeriodDTO("2023-01-10T07:00:00", "2023-01-10T08:00:00");

        // Mock the sensorRepository
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(device1.getIdentity(), sensorModelName))
                .thenReturn(Collections.singletonList(sensorTempDevice1));
        when(sensorRepository.findSensorsByDeviceIdAndSensorModelName(device2.getIdentity(),
                sensorModelName)).thenReturn(List.of());

        // Act
        ValueDTO result = controller.getMaxTemperatureDifference(deviceId1, deviceID2, validPeriodDTO);

        // Assert
        assertNull(result,
                "The result should be null, 2nd device has no sensor of temperature");
    }
}