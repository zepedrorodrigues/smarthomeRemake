package smarthome.controller;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.ReadingFactory;
import smarthome.domain.reading.ReadingFactoryImpl;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.ReadingValue;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.PeriodMapper;
import smarthome.mapper.mapper.ReadingMapper;
import smarthome.service.IReadingService;
import smarthome.service.impl.ReadingServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the GetReadingsFromDeviceController class.
 * It makes use of the Mockito library to mock the repositories.
 */
class GetReadingsFromDeviceControllerTest {

    IReadingRepository readingRepository;
    IReadingService readingService;
    GetReadingsFromDeviceController getReadingsFromDeviceController;
    IDeviceRepository deviceRepository;
    DeviceDTO deviceDTO;
    DeviceDTO deviceWithoutSensorsDTO;
    PeriodDTO periodDTO;
    PeriodDTO periodDTO2;
    ReadingDTO readingDTO0;
    ReadingDTO readingDTO1;

    /**
     * Set up the environment for the tests.
     * The dependencies of the GetReadingsFromDeviceController class are initialized.
     * Two device DTOs are created, one expected to return 2 readings and the other expected to return no readings.
     * Two valid period DTOs are created, one expected to return 2 readings and the other expected to return 1 reading.
     * The mock repositories are configured to return the expected readings.
     */
    @BeforeEach
    void setUp() throws ConfigurationException {
        // Initialize the GetReadingsFromDeviceController dependencies
        ISensorRepository sensorRepository = mock(ISensorRepository.class);
        deviceRepository = mock(IDeviceRepository.class);
        DeviceMapper deviceMapper = new DeviceMapper();
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        readingRepository = mock(IReadingRepository.class);
        PeriodMapper periodMapper = new PeriodMapper();
        ReadingMapper readingMapper = new ReadingMapper();
        String filePathName = "configDelta.properties";
        String filePathModels = "configModels.properties";
        readingService = new ReadingServiceImpl(readingRepository, sensorRepository, deviceRepository,filePathName,filePathModels);
        getReadingsFromDeviceController = new GetReadingsFromDeviceController(readingService, readingMapper,
                periodMapper, deviceMapper);

        // Create a device and device DTO
        DeviceName deviceName = new DeviceName("deviceName");
        DeviceTypeName deviceType = new DeviceTypeName("deviceType");
        RoomId roomId = new RoomId("roomId");
        DeviceId deviceId = new DeviceId("deviceId");
        DeviceStatus deviceStatus = new DeviceStatus(true);
        Device device = deviceFactory.createDevice(deviceId, deviceName, deviceType, roomId, deviceStatus);
        deviceDTO = new DeviceDTO(device.getIdentity().getIdentity(), deviceName.getDeviceName(),
                deviceType.getDeviceTypeName(), roomId.getRoomId(), deviceStatus.getStatus());

        // Create another device (expected to return no readings)
        Device anotherDevice = deviceFactory.createDevice(deviceName, deviceType, roomId);
        deviceWithoutSensorsDTO = new DeviceDTO(anotherDevice.getIdentity().getIdentity(), deviceName.getDeviceName()
                , deviceType.getDeviceTypeName(), roomId.getRoomId(), deviceStatus.getStatus());

        // Create a sensor to associate with the device
        SensorFactoryImpl sensorFactory = new SensorFactoryImpl();
        SensorModelName sensorModelName = new SensorModelName("SensorOfTemperature");
        SensorId sensorId = new SensorId("sensorId");
        Sensor sensor = sensorFactory.createSensor(sensorId, sensorModelName, deviceId);

        // Create a valid PeriodDTO (expected to return 2 readings)
        TimeStamp start = new TimeStamp(LocalDateTime.of(2024, 4, 24, 9, 0, 0));
        TimeStamp end = new TimeStamp(LocalDateTime.of(2024, 4, 24, 11, 0, 0));
        periodDTO = new PeriodDTO("2024-04-24T09:00:00", "2024-04-24T11:00:00");
        //periodDTO = new PeriodDTO(start.toString(), end.toString());

        // Create a valid PeriodDTO (expected to return 1 reading)
        TimeStamp end1 = new TimeStamp(LocalDateTime.of(2024, 4, 24, 10, 0, 0));
        periodDTO2 = new PeriodDTO("2024-04-24T09:00:00", "2024-04-24T10:00:00");
        //periodDTO2 = new PeriodDTO(start.toString(), end1.toString());

        // Create a reading and a reading DTO
        ReadingFactory readingFactory = new ReadingFactoryImpl();
        ReadingValue value0 = new ReadingValue("value0");
        TimeStamp timeStamp0 = new TimeStamp(LocalDateTime.of(2024, 4, 24, 9, 37, 0));
        ReadingId readingId0 = new ReadingId("readingId0");
        Reading reading0 = readingFactory.createReading(readingId0, value0, sensorId, timeStamp0);
        readingDTO0 = new ReadingDTO(readingId0.getId(), sensorId.getSensorId(), value0.valueToString(),
                timeStamp0.valueToString());

        // Create another reading and reading DTO
        ReadingValue value1 = new ReadingValue("value1");
        TimeStamp timeStamp1 = new TimeStamp(LocalDateTime.of(2024, 4, 24, 10, 38, 0));
        ReadingId readingId1 = new ReadingId("readingId1");
        Reading reading1 = readingFactory.createReading(readingId1, value1, sensorId, timeStamp1);
        readingDTO1 = new ReadingDTO(readingId1.getId(), sensorId.getSensorId(), value1.valueToString(),
                timeStamp1.valueToString());

        // Configure mock repositories
        when(sensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(Collections.singletonList(sensor));
        when(sensorRepository.findSensorsByDeviceId(anotherDevice.getIdentity())).thenReturn(List.of());
        when(readingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(Arrays.asList(reading0,
                reading1));
        when(readingRepository.findReadingsBySensorIdInAGivenPeriod(sensorId, start, end1)).thenReturn(Collections.singletonList(reading0));
    }

    /**
     * Test the constructor of the GetReadingsFromDeviceController class
     * The constructor should create a new instance of the GetReadingsFromDeviceController class
     */
    @Test
    void testGetReadingsFromDeviceControllerValidDeviceServiceShouldNotThrowAnException() {
        // Act & Assert
        assertNotNull(getReadingsFromDeviceController);
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return a list of readings when the device DTO and period DTO are valid
     * The result list size should match the expected size
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodValidDeviceDTOAndPeriodDTOShouldReturnListOfReadings() {
        // Arrange
        int expectedSize = 2;
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO);
        // Assert
        assertEquals(expectedSize, readings.size(), "The method should return a list of readings when the device DTO "
                + "and period DTO are valid");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should contain all the expected readings, based on their IDs
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedReadings() {
        // Arrange
        List<ReadingDTO> expectedReadings = Arrays.asList(readingDTO0, readingDTO1);
        // Act
        List<ReadingDTO> actualReadings =
                getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);
        // Assert
        boolean allMatch =
                expectedReadings.stream().allMatch(exp -> actualReadings.stream().anyMatch(act -> act.getId().equals(exp.getId())));
        assertTrue(allMatch, "The list should contain all the expected readings based on their IDs");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return a list of one reading when there is only one reading in the given period
     * The result list size should match the expected size
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedReadingOneElement() {
        //Arrange
        int expectedSize = 1;
        //Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO2);
        //Assert
        assertEquals(expectedSize, readings.size(), "The method should return one reading when the device DTO " +
                "and period DTO are valid");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return a reading with the attributes not blank
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedReadingWithNotBlankAttributes() {
        //Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO2);
        //Assert
        boolean actualReading = !readings.get(0).getId().isBlank() && !readings.get(0).getSensorId().isBlank() &&
                !readings.get(0).getReadingValue().isBlank() && !readings.get(0).getTimestamp().isBlank();
        assertTrue(actualReading, "The method should return one reading with the expected attributes not blank when " +
                "the device " + "DTO and period DTO are valid");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return a reading with the expected ID
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedReadingId() {
        //Arrange
        String expectedId = readingDTO0.getId();
        //Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO2);
        //Assert
        String actualId = readings.get(0).getId();
        assertEquals(expectedId, actualId, "The method should return one reading with the expected id when the " +
                "device DTO and period DTO are valid");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return a reading with the expected sensor ID
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedSensorId() {
        //Arrange
        String expectedSensorId = readingDTO0.getSensorId();
        //Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO2);
        //Assert
        String actualSensorId = readings.get(0).getSensorId();
        assertEquals(expectedSensorId, actualSensorId, "The method should return one reading with the expected " +
                "sensor id when the device DTO and period DTO are valid");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return a reading with the expected value
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedValue() {
        //Arrange
        String expectedValue = readingDTO0.getReadingValue();
        //Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO2);
        //Assert
        String actualValue = readings.get(0).getReadingValue();
        assertEquals(expectedValue, actualValue,
                "The method should return one reading with the expected value when the device DTO and period " +
                        "DTO are valid");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return a reading with the expected timestamp
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedTimeStamp() {
        //Arrange
        String expectedTimeStamp = readingDTO0.getTimestamp();
        //Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO2);
        //Assert
        String actualTimeStamp = readings.get(0).getTimestamp();
        assertEquals(expectedTimeStamp, actualTimeStamp, "The method should return one reading with the expected " +
                "timestamp when the device DTO and period DTO are valid");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return an empty list when there are no readings in the given period
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnNullWhenNoReadingsRegistered() {
        // Arrange
        TimeStamp start = new TimeStamp(LocalDateTime.of(2019, 1, 24, 9, 0, 0));
        TimeStamp end = new TimeStamp(LocalDateTime.of(2019, 2, 24, 9, 0, 0));
        periodDTO = new PeriodDTO(String.valueOf(start), String.valueOf(end));
        when(readingRepository.findReadingsBySensorIdInAGivenPeriod(new SensorId("sensorId"), start, end)).thenReturn(List.of());
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO);
        // Assert
        assertNull(readings, "The method should return an empty list because there are no readings in the given period");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return an empty list when there are no sensors associated with the device
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnEmptyListWhenNoSensorsAssociatedWithDevice() {
        // Act
        List<ReadingDTO> readings =
                getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceWithoutSensorsDTO, periodDTO);
        // Assert
        boolean isEmpty = readings.isEmpty();
        assertTrue(isEmpty, "The method should return an empty list because there are no sensors associated with " +
                "the device");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return null when the end date is before the start date
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnNullWhenEndDateIsBeforeStartDate() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2024, 5, 24, 11, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        periodDTO = new PeriodDTO(String.valueOf(start), String.valueOf(end));
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO);
        // Assert
        assertNull(readings, "The method should return null because the end date is before the start date");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return null when the end date is after the current date
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnNullWhenEndDateIsAfterCurrentDate() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        periodDTO = new PeriodDTO(String.valueOf(start), String.valueOf(end));
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO);
        // Assert
        assertNull(readings, "The method should return null because the end date is after the current date");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return null when the start date is equal to the end date
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnNullWhenStartDateIsEqualToEndDate() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        periodDTO = new PeriodDTO(String.valueOf(start), String.valueOf(end));
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO);
        // Assert
        assertNull(readings, "The method should return null because the start date is equal to the start date");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return null when the data format is invalid
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldThrowExceptionWhenInvalidDateFormat() {
        // Arrange
        periodDTO = new PeriodDTO("today", "tomorrow");
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                periodDTO);
        // Assert
        assertNull(readings, "The method should return null because the start date is equal to the start date");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return null when the device DTO is null
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodNullDeviceDTOShouldThrowAnException() {
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(null,
                periodDTO);
        // Assert
        assertNull(readings, "The method should return null when the device DTO is null");
    }

    /**
     * Test the getReadingsFromDeviceInAGiVenPeriod method of the GetReadingsFromDeviceController class
     * The method should return null when the period DTO is null
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodNullPeriodDTOShouldThrowAnException() {
        // Act
        List<ReadingDTO> readings = getReadingsFromDeviceController.getReadingsFromDeviceInAGivenPeriod(deviceDTO,
                null);
        // Assert
        assertNull(readings, "The method should return null when the period DTO is null");
    }
}