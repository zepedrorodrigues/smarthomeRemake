package smarthome.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
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
import smarthome.mapper.*;
import smarthome.mapper.mapper.ReadingMapper;
import smarthome.mapper.mapper.ValueMapper;
import smarthome.service.IReadingService;
import smarthome.service.impl.ReadingServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class contains integration tests for the ReadingRESTController class.
 * It uses the mockito framework to mock the repositories.
 */
class ReadingRESTControllerTest {

    IReadingRepository mockReadingRepository;
    IReadingRepository mockReadingRepository1;
    ISensorRepository mockSensorRepository1;
    IDeviceRepository mockDeviceRepository;
    ReadingRESTController readingRESTController;
    ReadingRESTController readingRESTController1;
    ReadingFactory readingFactory;
    DeviceId deviceId;
    String deviceIDValue;
    DeviceId deviceId2;
    String deviceIDValue2;
    String deviceWithoutSensors;
    String startPeriod;
    String endPeriod;
    ReadingId readingId;
    ReadingIdDTO readingIdDTO;
    ReadingIdDTO readingIdDTO1;
    SensorModelDTO sensorModelNameTemperatureDTO;
    SensorModelName sensorModelNameTemp;
    Sensor sensor;
    Sensor sensor2;
    ObjectMapper objectMapper;
    MockMvc mockMvc;
    Reading reading;
    ReadingMapper readingMapper;
    String uriReadingController;
    ValueMapper valueMapper;
    String filePathName;
    String filePathModels;

    /**
     * This method sets up the necessary objects and mocks for the tests.
     */
    @BeforeEach
    void setUp() throws ConfigurationException {
        // Initialize repositories with mock objects
        ISensorRepository mockSensorRepository = mock(ISensorRepository.class);
        mockSensorRepository1 = mock(ISensorRepository.class);
        mockReadingRepository1 = mock(IReadingRepository.class);
        this.mockReadingRepository = mock(IReadingRepository.class);
        mockDeviceRepository = mock(IDeviceRepository.class);
        // Initialize the mappers
        readingMapper = new ReadingMapper();
        // Initialize the factories
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        // Initialize the configuration file
        filePathName = "configDelta.properties";
        filePathModels = "configModels.properties";
        // Initialize the service
        IReadingService readingService = new ReadingServiceImpl(mockReadingRepository, mockSensorRepository,
                mockDeviceRepository, filePathName,filePathModels);
        IReadingService readingService1 = new ReadingServiceImpl(mockReadingRepository1, mockSensorRepository1,
                mockDeviceRepository, filePathName,filePathModels);
        // Initialize the controller
        valueMapper = new ValueMapper();
        readingRESTController = new ReadingRESTController(readingService, readingMapper, valueMapper);
        readingRESTController1 = new ReadingRESTController(readingService1, readingMapper, valueMapper);
        // Create a device and device DTO
        DeviceName deviceName = new DeviceName("deviceName");
        DeviceTypeName deviceType = new DeviceTypeName("deviceType");
        RoomId roomId = new RoomId("roomId");
        deviceIDValue = "deviceId";
        deviceId = new DeviceId(deviceIDValue);
        deviceIDValue2 = "deviceId2";
        deviceId2 = new DeviceId("deviceId2");
        // Create another device (expected to return no readings)
        Device anotherDevice = deviceFactory.createDevice(deviceName, deviceType, roomId);
        deviceWithoutSensors = anotherDevice.getIdentity().getIdentity();
        // Create a sensor to associate with the device
        sensorModelNameTemperatureDTO = new SensorModelDTO("SensorOfTemperature", "temperature");
        sensorModelNameTemp = new SensorModelName(sensorModelNameTemperatureDTO.getSensorModelName());
        SensorFactoryImpl sensorFactory = new SensorFactoryImpl();
        SensorId sensorId = new SensorId("sensorId");
        SensorId sensorId2 = new SensorId("sensorId2");
        sensor = sensorFactory.createSensor(sensorId, sensorModelNameTemp, deviceId);
        sensor2 = sensorFactory.createSensor(sensorId2, sensorModelNameTemp, deviceId2);
        // Create a valid PeriodDTO (expected to return 2 readings)
        TimeStamp start = new TimeStamp(LocalDateTime.of(2024, 4, 24, 9, 0, 0));
        TimeStamp end = new TimeStamp(LocalDateTime.of(2024, 4, 24, 11, 0, 0));
        startPeriod = start.valueToString();
        endPeriod = end.valueToString();
        // Create a valid PeriodDTO (expected to return 1 reading)
        TimeStamp end1 = new TimeStamp(LocalDateTime.of(2024, 4, 24, 10, 0, 0));
        // Create a reading and a reading DTO
        readingFactory = new ReadingFactoryImpl();
        ReadingValue value = new ReadingValue("value");
        TimeStamp timeStamp = new TimeStamp(LocalDateTime.of(2024, 4, 24, 9, 37, 0));
        readingId = new ReadingId("readingId");
        reading = readingFactory.createReading(readingId, value, sensorId, timeStamp);
        readingIdDTO = new ReadingIdDTO(readingId.getId());
        // Create another reading and reading DTO
        ReadingValue value1 = new ReadingValue("value1");
        TimeStamp timeStamp1 = new TimeStamp(LocalDateTime.of(2024, 4, 24, 10, 38, 0));
        ReadingId readingId1 = new ReadingId("readingId1");
        readingFactory.createReading(readingId1, value1, sensorId, timeStamp1);
        readingIdDTO1 = new ReadingIdDTO(readingId1.getId());
        // Configure mock repositories
        when(mockSensorRepository.findSensorsByDeviceId(deviceId)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository.findSensorsByDeviceId(anotherDevice.getIdentity())).thenReturn(List.of());
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(Arrays.asList(readingId, readingId1));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(sensorId, start, end1)).thenReturn(Collections.singletonList(readingId));
        // Setup the controller and HTTP request
        uriReadingController = "/readings";
        mockMvc = MockMvcBuilders.standaloneSetup(readingRESTController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * This test checks that the ReadingRESTController constructor creates a non-null object.
     * The test passes if the object is not null.
     */
    @Test
    void testReadingRESTControllerConstructorShouldBeNotNull() {
        // Act & Assert
        assertNotNull(readingRESTController);
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns OK status code.
     * The test passes if the status code is 200 OK.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnTheOKStatus() {
        // Assert
        HttpStatus codeStatus = HttpStatus.OK;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, startPeriod, endPeriod);
        // Assert
        HttpStatusCode resultStatus = readings.getStatusCode();
        assertEquals(codeStatus, resultStatus, "The status code should be 200 OK");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns a non-null object
     * with non-blank fields.
     * The test passes if the reading ID value is non-null and non-blank.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnNonNullNonBlankData() {
        // Act
        ResponseEntity<List<ReadingIdDTO>> responseEntity =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, startPeriod, endPeriod);
        ReadingIdDTO reading = Objects.requireNonNull(responseEntity.getBody()).get(0); // get the first reading
        // Assert
        boolean conditions = reading.getReadingId() != null && !reading.getReadingId().isBlank();
        assertTrue(conditions, "The reading Id value should be non-null and non-blank");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns the expected reading id.
     * The test passes if the reading data is the same as the expected reading data.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedReadingId() {
        // Act
        ResponseEntity<List<ReadingIdDTO>> responseEntity =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, startPeriod, endPeriod);
        ReadingIdDTO reading = Objects.requireNonNull(responseEntity.getBody()).get(0);
        // Assert
        boolean conditions = reading.getReadingId().equals(readingIdDTO.getReadingId());
        assertTrue(conditions, "The reading id should be the same as the expected reading id");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns a list of reading Ids with the
     * expected size.
     * The test passes if the list size is the same as the expected size.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodValidParametersShouldReturnCorrectListSize() {
        // Arrange
        int expectedSize = 2;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, startPeriod, endPeriod);
        // Assert
        List<ReadingIdDTO> body = readings.getBody();
        assert body != null;
        assertEquals(expectedSize, body.size(), "The method should return a list of readings when the device DTO " +
                "and period DTO are valid");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns a list with the expected reading
     * Ids.
     * The test passes if the list contains all the expected reading Ids.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnExpectedReadingsIds() {
        // Arrange
        List<ReadingIdDTO> expectedReadings = Arrays.asList(readingIdDTO, readingIdDTO1);
        // Act
        ResponseEntity<List<ReadingIdDTO>> responseEntity =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, startPeriod, endPeriod);
        // Assert
        List<ReadingIdDTO> actualReadings = responseEntity.getBody();
        boolean allMatch = expectedReadings.stream().allMatch(exp -> {
            assert actualReadings != null;
            return actualReadings.stream().anyMatch(act -> act.getReadingId().equals(exp.getReadingId()));
        });
        assertTrue(allMatch, "The list should contain all the expected readings");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns a status code NOT FOUND when no
     * readings are registered.
     * The test passes if the status code is 404 NOT FOUND.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnStatusNotFoundWhenNoReadingsRegistered() {
        // Arrange
        TimeStamp start = new TimeStamp(LocalDateTime.of(2019, 1, 24, 9, 0, 0));
        TimeStamp end = new TimeStamp(LocalDateTime.of(2019, 2, 24, 9, 0, 0));
        when(mockReadingRepository.findReadingIdsBySensorIdInAGivenPeriod(new SensorId("sensorId"), start, end)).thenReturn(List.of());
        HttpStatus codeStatus = HttpStatus.NOT_FOUND;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, start.valueToString(),
                        end.valueToString());
        // Assert
        HttpStatusCode resultStatus = readings.getStatusCode();
        assertEquals(codeStatus, resultStatus, "The method should return a NOT_FOUND status because there are no " +
                "readings registered.");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns NOT FOUND status when the device
     * has no sensors.
     * The test passes if the status code is 404 NOT FOUND.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnNotFoundStatusWhenDeviceHasNoSensors() {
        // Arrange
        HttpStatus codeStatus = HttpStatus.NOT_FOUND;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceWithoutSensors, startPeriod, endPeriod);
        // Assert
        HttpStatusCode resultStatus = readings.getStatusCode();
        assertEquals(codeStatus, resultStatus, "The method should return a NOT_FOUND status because there are no" +
                " sensors associated with the device");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns BadRequest status when the end
     * date is before the start date.
     * The test passes if the status code is 400 BAD_REQUEST.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnBadRequestStatusWhenEndDateIsBeforeStartDate() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2024, 5, 24, 11, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        HttpStatus codeStatus = HttpStatus.BAD_REQUEST;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, start.toString(),
                        end.toString());
        // Assert
        HttpStatusCode resultStatus = readings.getStatusCode();
        assertEquals(codeStatus, resultStatus, "The method should return a BAD_REQUEST status because the end date " +
                "is" + " before the start date");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns BadRequest status when the end
     * date is after the current date.
     * The test passes if the status code is 400 BAD_REQUEST.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnBadRequestStatusWhenEndDateIsAfterCurrentDate() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        HttpStatus codeStatus = HttpStatus.BAD_REQUEST;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, start.toString(),
                        end.toString());
        // Assert
        HttpStatusCode resultStatus = readings.getStatusCode();
        assertEquals(codeStatus, resultStatus, "The method should return a BAD_REQUEST status because the end" +
                "date is after the current date");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns BadRequest status when the start
     * date is after the current date.
     * The test passes if the status code is 400 BAD_REQUEST.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldReturnBadRequestStatusWhenStartDateIsEqualToEndDate() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 24, 9, 0, 0);
        HttpStatus codeStatus = HttpStatus.BAD_REQUEST;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, start.toString(),
                        end.toString());
        // Assert
        HttpStatusCode resultStatus = readings.getStatusCode();
        assertEquals(codeStatus, resultStatus, "The method should return a BAD_REQUEST status because the start date "
                + "is equal to the start date");
    }

    /**
     * This test checks that the getReadingsFromDeviceInAGivenPeriod method returns UnprocessableEntity status when the
     * date format is invalid.
     * The test passes if the status code is 422 UNPROCESSABLE_ENTITY.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodShouldThrowExceptionWhenInvalidDateFormat() {
        // Arrange
        startPeriod = "today";
        endPeriod = "tomorrow";
        HttpStatus codeStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        // Act
        ResponseEntity<List<ReadingIdDTO>> readings =
                readingRESTController.getReadingsFromDeviceInAGivenPeriod(deviceIDValue, startPeriod, endPeriod);
        // Assert
        HttpStatusCode resultStatus = readings.getStatusCode();
        assertEquals(codeStatus, resultStatus,
                "The method should return a UNPROCESSABLE_ENTITY status because the " + "date format is invalid");
    }

    /**
     * Test the getReadingsFromDeviceInAGivenPeriod method with JSON request.
     * This test asserts that the HTTP response status is OK when the request is successful.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodWithJsonRequestShouldReturnOkStatus() throws Exception {
        // Arrange
        int expectedStatusCode = 200;
        String uri = uriReadingController + "/device/" + deviceIDValue;
        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("startPeriod", startPeriod)
                .param("endPeriod", endPeriod)).andReturn();
        // Assert
        int actualStatusCode = result.getResponse().getStatus();
        assertEquals(expectedStatusCode, actualStatusCode, "The actualStatusCode should be 200 OK");
    }

    /**
     * Test the getReadingsFromDeviceInAGivenPeriod method with JSON request.
     * This test asserts that the body response corresponds to the expected readings, when the request is successful.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodWithJsonRequestShouldReturnExpectedReadings() throws Exception {
        // Arrange
        String uri = uriReadingController + "/device/" + deviceIDValue;
        readingIdDTO.add(Link.of("http://localhost/readings/" + readingIdDTO.getReadingId()));
        readingIdDTO1.add(Link.of("http://localhost/readings/" + readingIdDTO1.getReadingId()));
        String expectedJson = objectMapper.writeValueAsString(List.of(readingIdDTO, readingIdDTO1));
        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("startPeriod", startPeriod)
                .param("endPeriod", endPeriod)).andReturn();
        // Assert
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(expectedJson, responseBody, "The response body should be the expected readings");
    }

    /**
     * Test the getReadingsFromDeviceInAGivenPeriod method with JSON request.
     * This test asserts that the HTTP response status is BAD_REQUEST when the period is invalid.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodWithJsonRequestShouldReturnBadRequestStatus() throws Exception {
        // Arrange
        int expectedStatusCode = HttpStatus.BAD_REQUEST.value();
        String uri = uriReadingController + "/device/" + deviceIDValue;
        LocalDateTime start = LocalDateTime.of(2024, 5, 24, 11, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 4, 24, 9, 0, 0);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("startPeriod", start.toString())
                        .param("endPeriod", end.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        int actualStatusCode = result.getResponse().getStatus();
        assertEquals(expectedStatusCode, actualStatusCode, "The actual status code should be 400 BAD_REQUEST");
    }

    /**
     * Test the getReadingsFromDeviceInAGivenPeriod method with JSON request.
     * This test asserts that the HTTP response status is NOT_FOUND when there are no readings registered.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodWithJsonRequestShouldReturnNotFoundStatus() throws Exception {
        // Arrange
        int expectedStatusCode = HttpStatus.NOT_FOUND.value();
        String uri = uriReadingController + "/device/" + deviceIDValue;
        LocalDateTime start = LocalDateTime.of(2010, 1, 24, 9, 0, 0);
        LocalDateTime end = LocalDateTime.of(2010, 2, 24, 9, 0, 0);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("startPeriod", start.toString())
                        .param("endPeriod", end.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        int actualStatusCode = result.getResponse().getStatus();
        assertEquals(expectedStatusCode, actualStatusCode, "The actual status code should be 404 NOT_FOUND");
    }

    /**
     * Test the getReadingsFromDeviceInAGivenPeriod method with JSON request.
     * This test asserts that the HTTP response status is NOT_FOUND when the device has no sensors.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodWithJsonRequestShouldReturnNotFoundStatusWhenDeviceHasNoSensors() throws Exception {
        // Arrange
        int expectedStatusCode = HttpStatus.NOT_FOUND.value();
        String uri = uriReadingController + "/device/" + deviceWithoutSensors;

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("startPeriod", startPeriod)
                        .param("endPeriod", endPeriod)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        int actualStatusCode = result.getResponse().getStatus();
        assertEquals(expectedStatusCode, actualStatusCode, "The actual status code should be 404 NOT_FOUND");
    }

    /**
     * Test the getReadingsFromDeviceInAGivenPeriod method with JSON request.
     * This test asserts that the HTTP response status is UNPROCESSABLE_ENTITY when the date format is invalid.
     */
    @Test
    void testGetReadingsFromDeviceInAGivenPeriodWithJsonRequestShouldReturnUnprocessableEntityStatus() throws Exception {
        // Arrange
        int expectedStatusCode = HttpStatus.UNPROCESSABLE_ENTITY.value();
        String uri = uriReadingController + "/device/" + deviceIDValue;
        String start = "today";
        String end = "tomorrow";

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("startPeriod", start)
                        .param("endPeriod", end)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Assert
        int actualStatusCode = result.getResponse().getStatus();
        assertEquals(expectedStatusCode, actualStatusCode, "The actual status code should be 422 UNPROCESSABLE_ENTITY");
    }


    // Test MVC

    /**
     * Test with mvc the getMaxInstantaneousTempDifference method when valid data is provided and exist readings
     * to compare, should return response with status OK
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceIfCorrectlyHandleGetWhenHttpStatusOk() throws Exception {
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                eq(deviceId), any(SensorModelName.class))).thenReturn(List.of(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                eq(deviceId2), any(SensorModelName.class))).thenReturn((List.of(sensor2)));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        int expectedStatusCode = HttpStatus.OK.value();
        String uri = "/readings/device/" + deviceId.getIdentity() + "/max-temperature-difference/" + deviceId2.getIdentity();
        mockMvc = MockMvcBuilders.standaloneSetup(readingRESTController1).build();

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("startPeriod", startTime)
                .param("endPeriod", endTime)).andReturn();

        // Assert
        assertEquals(expectedStatusCode, mvcResult.getResponse().getStatus());
    }

    /**
     * Test with mvc the getMaxInstantaneousTempDifference method when valid data is provided and exist readings
     * to compare, should return the expected response body (JSON)
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceIfCorrectlyHandleGetWhenHttpStatusOkShouldReturnExpectedResponseBody()
            throws Exception {
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                eq(deviceId), any(SensorModelName.class))).thenReturn(List.of(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                eq(deviceId2), any(SensorModelName.class))).thenReturn((List.of(sensor2)));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        ValueDTO expectedDTO = new ValueDTO("10.0");
        String jsonExpected = objectMapper.writeValueAsString(expectedDTO);
        String uri = "/readings/device/" + deviceId.getIdentity() + "/max-temperature-difference/" + deviceId2.getIdentity();
        mockMvc = MockMvcBuilders.standaloneSetup(readingRESTController1).build();

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("startPeriod", startTime)
                .param("endPeriod", endTime)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        assertEquals(jsonExpected, mvcResult.getResponse().getContentAsString());
    }

    /**
     * Test with mvc the getMaxInstantaneousTempDifference method when the device 1 has no sensor of temperature
     * should return response with status BAD_REQUEST
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceIfCorrectlyHandleGetWhenHttpStatusBadRequestWhenNoSensors
    () throws Exception {
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                eq(deviceId), any(SensorModelName.class))).thenReturn(Collections.emptyList());
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                eq(deviceId2), any(SensorModelName.class))).thenReturn(Collections.emptyList());

        int expectedStatusCode = HttpStatus.BAD_REQUEST.value();
        String uri = "/readings/device/" + deviceId.getIdentity() + "/max-temperature-difference/" + deviceId2.getIdentity();
        mockMvc = MockMvcBuilders.standaloneSetup(readingRESTController1).build();

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("startPeriod", startTime)
                .param("endPeriod", endTime)).andReturn();

        // Assert
        assertEquals(expectedStatusCode, mvcResult.getResponse().getStatus());
    }

    /**
     * Test with mvc the getMaxInstantaneousTempDifference end time is with invalid format,
     * should return response with status UNPROCESSABLE_ENTITY
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceIfCorrectlyHandleGetWhenHttpStatusUnprocessableEntity()
            throws Exception {
        String startTime = "2023-01-10T07:00:00";
        String endTime = "NOT A DATE";

        int expectedStatusCode = HttpStatus.UNPROCESSABLE_ENTITY.value();
        String uri = "/readings/device/" + deviceId.getIdentity() + "/max-temperature-difference/" + deviceId2.getIdentity();
        mockMvc = MockMvcBuilders.standaloneSetup(readingRESTController1).build();

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("startPeriod", startTime)
                .param("endPeriod", endTime)).andReturn();

        // Assert
        assertEquals(expectedStatusCode, mvcResult.getResponse().getStatus());
    }


    //     (GetMaxInstantaneousTempDifference method)
    //     ** INTEGRATION TESTS FOR INVALID SCENARIOS OF SENSOR MODEL NAME **
    //     ** WHEN DEVICE 1 AND/OR DEVICE 2 HAS NO SENSOR OF TEMPERATURE **

    /**
     * Test getMaxTemperatureDifference method when the device 1 has no sensor of temperature
     * should return response with null body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice1HasNoSensorOfTemperatureCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";

        // Mock sensors repository
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.emptyList());
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null, " + "there are no temperature sensor in device 1");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 1 has no sensor of temperature
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice1HasNoSensorOfTemperatureCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        int expectedStatusCode = 400;

        // Mock sensors repository
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.emptyList());
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400, " + "there are no " +
                "temperature sensor in device 1");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 1 has no sensor of temperature
     * should return response with null body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice2HasNoSensorOfTemperatureCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";

        // Mock sensors repository
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null, " + "there are no temperature sensor in device 2");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 1 has no sensor of temperature
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice2HasNoSensorOfTemperatureCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        int expectedStatusCode = 400;

        // Mock sensors repository
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400, " + "there are no " +
                "temperature sensor in device 2");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 1 and 2 have no sensor of temperature
     * should return response with null body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice1And2HaveNoSensorOfTemperatureCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";

        // Mock sensors repository
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.emptyList());
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null, " + "there are no temperature sensor in device 1 " +
                "and" + " 2");
    }

    /**
     * Test getMaxTemperatureDifference method when the device 1 and 2 have no sensor of temperature
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDevice1And2HaveNoSensorOfTemperatureCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        int expectedStatusCode = 400;

        // Mock sensors repository
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.emptyList());
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400, " + "there are no " +
                "temperature sensor in device 1 and 2");
    }


    // (GetMaxInstantaneousTempDifference method)
    // ** INTEGRATION TESTS WHEN PERIOD IS INVALID **
    // ** NULL START TIME AND/OR END TIME, END DATE IN FUTURE, EQUAL DATES, INCORRECT ORDER **

    /**
     * Test getMaxTemperatureDifference method when period has end date in future
     * should return response with null body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodHasEndDateInFutureCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2200-01-10T08:00:00";

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null because the end date is in the future");
    }

    /**
     * Test getMaxTemperatureDifference method when period has end date in future
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodHasEndDateInFutureCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2200-01-10T08:00:00";
        int expectedStatusCode = 400;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400 because the end date is in "
                + "the future");
    }

    /**
     * Test getMaxTemperatureDifference method when period has equal dates
     * should return response with null body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsWithEqualDatesCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:00:00";

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null because the period has equal dates");
    }

    /**
     * Test getMaxTemperatureDifference method when period has equal dates
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsWithEqualDatesCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:00:00";
        int expectedStatusCode = 400;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400 because the period has " +
                "equal dates");
    }

    /**
     * Test getMaxTemperatureDifference method when period has end date before start date
     * should return response with null body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodInIncorrectOrderCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T08:00:00";
        String endTime = "2023-01-10T07:00:00";

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null because the period is in incorrect order");
    }

    /**
     * Test getMaxTemperatureDifference method when period has end date before start date
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodInIncorrectOrderCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T08:00:00";
        String endTime = "2023-01-10T07:00:00";
        int expectedStatusCode = 400;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode,
                "The status code should be 400 because the period is in " + "incorrect order");
    }


    // (GetMaxInstantaneousTempDifference method)
    // ** INTEGRATION TESTS FOR READINGS SCENARIOS **
    // ** WHEN THERE IS ONLY ONE OR MORE THAN ONE READING IN THE PERIOD **
    // ** POSITIVE AND NEGATIVE VALUES **
    // ** WHEN THERE ARE NO READINGS IN THE PERIOD, FOR ONE OR BOTH SENSORS **

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return the difference between the two readings in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(
                Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(
                Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        ValueDTO differenceExpected = new ValueDTO("10.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertEquals(differenceExpected, result.getBody(),
                "The result should be 10.0, the difference between 20.0 " + "and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor
     * in the period should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor with negative values
     * in the period should return the absolute difference between the two readings in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorNegativeValuesCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("-20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("-10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        ValueDTO differenceExpected = new ValueDTO("10.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertEquals(differenceExpected, result.getBody(), "The result should be 10.0, the absolute difference " +
                "between -20.0 and -10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist one reading in each sensor with negative values
     * in the period should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistOneReadingInEachSensorNegativeValuesCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("-20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("-10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }

    /**
     * Test getMaxTemperatureDifference method when the two readings are equal in value
     * should return 0.0 in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingEqualReadingValuesCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        ValueDTO differenceExpected = new ValueDTO("0.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertEquals(differenceExpected, result.getBody(),
                "The result should be 20.0, the difference between 30.0 " + "and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when the two readings are equal in value
     * should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingEqualReadingValuesCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 54)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(
                sensor2.getIdentity(), startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }


    /**
     * Test getMaxTemperatureDifference method when exist two readings in each sensor
     * in the period should return the maximum difference between the two readings in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistTwoReadingsInEachSensorCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("12.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 54)));
        Reading reading3 = readingFactory.createReading(new ReadingValue("10.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 52)));
        Reading reading4 = readingFactory.createReading(new ReadingValue("10.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 56)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Arrays.asList(reading1.getIdentity(),
                reading2.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Arrays.asList(reading3.getIdentity(),
                reading4.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        when(mockReadingRepository1.findByIdentity(reading3.getIdentity())).thenReturn(Optional.of(reading3));
        when(mockReadingRepository1.findByIdentity(reading4.getIdentity())).thenReturn(Optional.of(reading4));

        ValueDTO differenceExpected = new ValueDTO("20.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertEquals(differenceExpected, result.getBody(),
                "The result should be 20.0, the difference between 30.0 " + "and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist two readings in each sensor
     * in the period should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenExistTwoReadingsInEachSensorCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("12.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 54)));
        Reading reading3 = readingFactory.createReading(new ReadingValue("10.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 52)));
        Reading reading4 = readingFactory.createReading(new ReadingValue("10.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 56)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Arrays.asList(reading1.getIdentity(),
                reading2.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Arrays.asList(reading3.getIdentity(),
                reading4.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        when(mockReadingRepository1.findByIdentity(reading3.getIdentity())).thenReturn(Optional.of(reading3));
        when(mockReadingRepository1.findByIdentity(reading4.getIdentity())).thenReturn(Optional.of(reading4));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }

    /**
     * Test getMaxTemperatureDifference method when exist two readings in one sensor
     * and one in the other sensor should return the maximum difference between the two readings in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingTwoReadingWithOneReadingCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("12.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 54)));
        Reading reading3 = readingFactory.createReading(new ReadingValue("10.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 52)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Arrays.asList(reading1.getIdentity(),
                reading2.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading3.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        when(mockReadingRepository1.findByIdentity(reading3.getIdentity())).thenReturn(Optional.of(reading3));

        ValueDTO differenceExpected = new ValueDTO("20.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertEquals(differenceExpected, result.getBody(),
                "The result should be 20.0, the difference between 30.0 " + "and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when exist two readings in one sensor
     * and one in the other sensor should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenComparingTwoReadingWithOneReadingCheckResponseStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T08:00:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("30.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 50)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("12.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 54)));
        Reading reading3 = readingFactory.createReading(new ReadingValue("10.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 10, 52)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Arrays.asList(reading1.getIdentity(),
                reading2.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading3.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        when(mockReadingRepository1.findByIdentity(reading3.getIdentity())).thenReturn(Optional.of(reading3));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta
     * and there are readings in each sensor in that period should return the difference between the two readings
     * in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsLessThanDeltaWithValidReadingsToCompareCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:00:30";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 5)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 10)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        ValueDTO differenceExpected = new ValueDTO("10.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        // Assert
        assertEquals(differenceExpected, result.getBody(),
                "The result should be 10.0, the difference between 20.0 " + "and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when period is less than delta
     * and there are readings in each sensor in that period should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenPeriodIsLessThanDeltaWithValidReadingsToCompareCheckStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:00:30";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 5)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 0, 10)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();
        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is less than delta by one second
     * and there are readings in each sensor in that period should return the difference calculated
     * in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsLessThanDeltaBy1SecondCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 59)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        ValueDTO differenceExpected = new ValueDTO("10.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        // Assert
        assertEquals(differenceExpected, result.getBody(),
                "The result should be 10.0, the difference between 20.0 " + "and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is less than delta by one second
     * and there are readings in each sensor in that period should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsLessThanDeltaBy1SecondCheckStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 59)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();
        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is equals to delta
     * and there are readings in each sensor in that period should return the difference calculated
     * in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsAreEqualsToDeltaCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 21, 0)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        ValueDTO differenceExpected = new ValueDTO("10.0");

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        // Assert
        assertEquals(differenceExpected, result.getBody(),
                "The result should be 10.0, the difference between 20.0 " + "and 10.0");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is equals to delta
     * and there are readings in each sensor in that period should return response with http status code 200
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsAreEqualsToDeltaCheckStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 21, 0)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        int expectedStatusCode = 200;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();
        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 200, the response is successful");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is bigger than delta by one second
     * and there are readings in each sensor in that period should return null in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsBiggerThanDelta1SecondCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 21, 1)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        // Assert
        assertNull(result.getBody(), "The result should be null, cannot be compared");
    }

    /**
     * Test getMaxTemperatureDifference method when difference between readings is bigger than delta by one second
     * and there are readings in each sensor in that period should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenDifferenceBetweenReadingsBiggerThanDelta1SecondCheckStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        Reading reading2 = readingFactory.createReading(new ReadingValue("10.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 21, 1)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading2.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));
        when(mockReadingRepository1.findByIdentity(reading2.getIdentity())).thenReturn(Optional.of(reading2));
        int expectedStatusCode = 400;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();
        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400, the response is " +
                "unsuccessful");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in that period
     * should return null in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenNoReadingsInThatPeriodCheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null, there are no readings to compare");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in that period
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenNoReadingsInThatPeriodCheckStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());
        int expectedStatusCode = 400;

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400, the response is " +
                "unsuccessful");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in device 1
     * should return null in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInDevice1CheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null, there are no readings to compare");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in device 1
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInDevice1CheckStatusCode() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        int expectedStatusCode = 400;
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400, the response is " +
                "unsuccessful");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in device 2
     * should return null in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInDevice2CheckResponseBody() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null, there are no readings to compare");
    }

    /**
     * Test getMaxTemperatureDifference method when there are no readings in device 2
     * should return response with http status code 400
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThereAnyReadingsInDevice2CheckStatusCode() {
        // Arrange
        int expectedStatusCode = 400;
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";
        TimeStamp startTimeS = new TimeStamp(LocalDateTime.parse(startTime));
        TimeStamp endTimeS = new TimeStamp(LocalDateTime.parse(endTime));
        // Readings
        Reading reading1 = readingFactory.createReading(new ReadingValue("20.0"), sensor2.getIdentity(),
                new TimeStamp(LocalDateTime.of(2023, 1, 10, 7, 20, 0)));
        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor2));
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(deviceId2, sensorModelNameTemp)).thenReturn(Collections.singletonList(sensor));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor2.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.singletonList(reading1.getIdentity()));
        when(mockReadingRepository1.findReadingIdsBySensorIdInAGivenPeriod(sensor.getIdentity(),
                startTimeS, endTimeS)).thenReturn(Collections.emptyList());
        when(mockReadingRepository1.findByIdentity(reading1.getIdentity())).thenReturn(Optional.of(reading1));

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);
        int actualStatusCode = result.getStatusCode().value();

        // Assert
        assertEquals(expectedStatusCode, actualStatusCode, "The status code should be 400, the response is " +
                "unsuccessful");
    }

    /**
     * Test getMaxTemperatureDifference method when sensor repository throws an exception
     * should return null in the response body
     */
    @Test
    void testGetMaxInstantaneousTempDifferenceWhenThrowExceptionInRepository() {
        // Arrange
        String startTime = "2023-01-10T07:00:00";
        String endTime = "2023-01-10T07:30:00";

        // Repositories
        when(mockSensorRepository1.findSensorsByDeviceIdAndSensorModelName(
                deviceId, sensorModelNameTemp)).thenThrow(NullPointerException.class);

        // Act
        ResponseEntity<ValueDTO> result = readingRESTController1.getMaxTemperatureDifference(deviceIDValue,
                deviceIDValue2, startTime, endTime);

        // Assert
        assertNull(result.getBody(), "The result should be null, there are no readings to compare");
    }


    // SCENARIOS TO GET READINGS

    /**
     * Test case for when the reading exists. It should return a response body with the correct reading data.
     */
    @Test
    void testGetReadingWhenExistsShouldReturnResponseBodyWithCorrectReadingData() {
        // Arrange
        String readingIdString = reading.getIdentity().getId();
        ReadingDTO expected = readingMapper.toReadingDTO(reading)
                .add(linkTo(methodOn(ReadingRESTController.class)
                        .getReading(readingIdString)).withSelfRel());
        when(mockReadingRepository.findByIdentity(reading.getIdentity())).thenReturn(Optional.of(reading));

        // Act
        ResponseEntity<ReadingDTO> result = readingRESTController.getReading(readingIdString);
        ReadingDTO resultBody = result.getBody();

        // Assert
        assertEquals(expected, resultBody,
                "The response body should contain the correct reading data.");
    }

    /**
     * Test case for when the reading does not exist. It should return a null response body.
     */
    @Test
    void testGetReadingWhenDoesNotExistShouldReturnNullResponseBody() {
        // Arrange
        String readingIdString = readingId.getId();
        when(mockReadingRepository1.findByIdentity(readingId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ReadingDTO> result = readingRESTController1.getReading(readingIdString);

        // Assert
        assertNull(result.getBody(),
                "The response body should be null, meaning that the reading does not exist.");
    }

    /**
     * Test case for when an exception is thrown. It should return a null response body.
     */
    @Test
    void testGeReadingWhenAnExceptionIsThrownShouldReturnNullResponseBody() {
        // Arrange
        String readingIdString = readingId.getId();
        when(mockReadingRepository1.findByIdentity(readingId)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<ReadingDTO> result = readingRESTController1.getReading(readingIdString);
        // Assert
        assertNull(result.getBody(),
                "The response body should be null, meaning that an exception was thrown.");
    }

    /**
     * Test case for when the reading exists. It should return the correct HTTP status.
     */
    @Test
    void testGetReadingWhenExistsShouldReturnCorrectHTTPStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.OK;
        String readingIdString = readingId.getId();
        when(mockReadingRepository1.findByIdentity(readingId)).thenReturn(Optional.of(reading));

        // Act
        ResponseEntity<ReadingDTO> result = readingRESTController1.getReading(readingIdString);

        // Assert
        assertEquals(expected, result.getStatusCode(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test case for when the reading does not exist. It should return the correct HTTP status.
     */
    @Test
    void testGetReadingWhenDoesNotExistShouldReturnCorrectHTTPStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.NOT_FOUND;
        String readingIdString = readingId.toString();
        when(mockReadingRepository1.findByIdentity(readingId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ReadingDTO> result = readingRESTController1.getReading(readingIdString);

        // Assert
        assertEquals(expected, result.getStatusCode(),
                "The HTTP status should be NOT_FOUND, indicating that the reading does not exist.");
    }

    /**
     * Test case for when an exception is thrown. It should return the correct HTTP status.
     */
    @Test
    void testGetReadingWhenExceptionIsThrownShouldReturnCorrectHTTPStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        String readingIdString = readingId.getId();
        when(mockReadingRepository1.findByIdentity(readingId)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<ReadingDTO> result = readingRESTController1.getReading(readingIdString);

        // Assert
        assertEquals(expected, result.getStatusCode(),
                "The HTTP status should be BAD_REQUEST, indicating that an exception was thrown.");
    }

    /**
     * Test case using Mock MVC for when the reading exists. It should return the correct HTTP status.
     */
    @Test
    void testGetReadingCorrectlyHandlesAGetRequestWhenExistsCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/readings/" + readingId.getId();
        int expectedStatus = HttpStatus.OK.value();
        when(mockReadingRepository.findByIdentity(readingId)).thenReturn(Optional.of(reading));

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test case using Mock MVC for when the reading does not exist. It should return the correct HTTP status.
     */
    @Test
    void testGetReadingCorrectlyHandlesAGetRequestWhenDoesNotExistCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/readings/" + readingId.getId();
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        when(mockReadingRepository.findByIdentity(readingId)).thenReturn(Optional.empty());

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be NOT_FOUND, indicating that the request was" +
                        "but there is no content.");
    }

    /**
     * Test case using Mock MVC for when an exception is thrown. It should return the correct HTTP status.
     */
    @Test
    void testGetReadingCorrectlyHandlesAGetRequestWhenAnExceptionIsThrownCheckHttpStatus()
            throws Exception {
        // Arrange
        String uri = "/readings/" + readingId.getId();
        int expectedStatus = HttpStatus.BAD_REQUEST.value();
        when(mockReadingRepository.findByIdentity(readingId)).thenThrow(RuntimeException.class);

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, status,
                "The HTTP status should be BAD_REQUEST, indicating that the request was unsuccessful.");
    }

    /**
     * Test case using Mock MVC for when the reading exists. It should return the correct response body.
     */
    @Test
    void testGetReadingCorrectlyHandlesAGetRequestWhenReadingExistsCheckResponseBody()
            throws Exception {
        // Arrange
        ReadingDTO expectedDTO = readingMapper.toReadingDTO(reading)
                .add(Link.of("http://localhost" + uriReadingController + "/" + reading.getIdentity().getId()));
        String expected = objectMapper.writeValueAsString(expectedDTO);
        String uri = "/readings/" + reading.getIdentity().getId();
        when(mockReadingRepository.findByIdentity(reading.getIdentity())).thenReturn(Optional.of(reading));

        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        // Assert
        assertEquals(expected, mvcResult.getResponse().getContentAsString(),
                "The HTTP status should be OK, indicating that the request was successful.");
    }

    /**
     * Test case using Mock MVC for when the parameters are invalid. It should return the correct response body.
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodInvalidParameters() throws Exception {
        //Arrange
        String uri = "/readings/peakPowerConsumption";
        //Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("start", "start").param("end", "end")
        ).andReturn();
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),
                "The HTTP status should be BAD_REQUEST");
    }

    /**
     * Test case using Mock MVC for when the end time is not provided. It should return the correct response body.
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodWithoutEntTime() throws Exception {
        //Arrange
        String uri = "/readings/peakPowerConsumption";
        //Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("start", "2024-01-01T00:00:00")
        ).andReturn();
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),
                "The HTTP status should be BAD_REQUEST");
    }

    /**
     * Test case using Mock MVC for when it is not possible to find readings.
     * It should return the correct response body.
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodWithoutReadings() throws Exception {
        //Arrange
        String uri = "/readings/peakPowerConsumption";
        //Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .param("start", "2024-01-01T00:00:00").param("end", "2024-01-01T01:00:00")
        ).andReturn();
        //Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus(),
                "The HTTP status should be NOT_FOUND");
    }


    /**
     * Test case for when the parameters are valid and a peak of power consumption is found.
     * It should return the correct response body.
     */
    @Test
    void testGetPeakPowerConsumptionInAGivenPeriodValidParameters() throws Exception {
        //Arrange
        String uri = "/readings/peakPowerConsumption";

        IDeviceRepository mockDeviceRepositoryPeakPower = mock(IDeviceRepository.class);
        ISensorRepository mockSensorRepositoryPeakPower = mock(ISensorRepository.class);

        DeviceId gridPowerMeterDeviceId = new DeviceId("gridPowerMeterDeviceId");
        Iterable<DeviceId> gridPowerMeterDeviceIds = List.of(gridPowerMeterDeviceId);
        DeviceTypeName gridPowerMeterDeviceTypeName = new DeviceTypeName("GridPowerMeter");
        when(mockDeviceRepositoryPeakPower.findDeviceIdsByDeviceTypeName(gridPowerMeterDeviceTypeName))
                .thenReturn(gridPowerMeterDeviceIds);

        DeviceId powerSourcePowerMeterDeviceId = new DeviceId("powerSourcePowerMeterDeviceId");
        Iterable<DeviceId> powerSourcePowerMeterDeviceIds = List.of(powerSourcePowerMeterDeviceId);
        DeviceTypeName powerSourcePowerMeterDeviceTypeName = new DeviceTypeName("PowerSourcePowerMeter");
        when(mockDeviceRepositoryPeakPower.findDeviceIdsByDeviceTypeName(powerSourcePowerMeterDeviceTypeName))
                .thenReturn(powerSourcePowerMeterDeviceIds);

        SensorId powerConsumptionSensorId = new SensorId("powerConsumptionSensorId");
        SensorModelName powerConsumptionSensorModelName = new SensorModelName("SensorOfPowerConsumption");

        when(mockSensorRepositoryPeakPower.findSensorIdsByDeviceIdAndSensorModelName(gridPowerMeterDeviceId,
                powerConsumptionSensorModelName)).thenReturn(List.of(powerConsumptionSensorId));

        SensorId powerSourceSensorId = new SensorId("powerSourceSensorId");
        SensorModelName powerSourceSensorModelName = new SensorModelName("SensorOfPowerConsumption");

        when(mockSensorRepositoryPeakPower.findSensorIdsByDeviceIdAndSensorModelName(powerSourcePowerMeterDeviceId,
                powerSourceSensorModelName)).thenReturn(List.of(powerSourceSensorId));

        IReadingRepository mockReadingRepository = mock(IReadingRepository.class);
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime time1 = LocalDateTime.of(2024, 1, 1, 0, 0, 59);
        LocalDateTime time2 = LocalDateTime.of(2024, 1, 1, 0, 1, 20);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, 0, 2, 0);

        //TimeStamps for the interval
        LocalDateTime midTime = LocalDateTime.of(2024, 1, 1, 0, 1, 0);

        ReadingFactory readingFactory = new ReadingFactoryImpl();
        Reading powerConsReading1 = readingFactory.createReading(new ReadingId("reading1"), new ReadingValue("0"),
                powerConsumptionSensorId, new TimeStamp(startTime));
        Reading powerConsReading2 = readingFactory.createReading(new ReadingId("reading2"), new ReadingValue("100"),
                powerConsumptionSensorId, new TimeStamp(time1));
        Reading powerConsReading3 = readingFactory.createReading(new ReadingId("reading3"), new ReadingValue("700"),
                powerConsumptionSensorId, new TimeStamp(time2));

        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(powerConsumptionSensorId, new TimeStamp(startTime),
                new TimeStamp(midTime))).thenReturn(List.of(powerConsReading1,powerConsReading2));
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(powerConsumptionSensorId, new TimeStamp(midTime),
                new TimeStamp(endTime))).thenReturn(List.of(powerConsReading3));

        Reading powerSourceReading1 = readingFactory.createReading(new ReadingId("reading1"), new ReadingValue("100"),
                powerSourceSensorId, new TimeStamp(time1));
        Reading powerSourceReading2 = readingFactory.createReading(new ReadingId("reading2"), new ReadingValue("200"),
                powerSourceSensorId, new TimeStamp(time2));
        Reading powerSourceReading3 = readingFactory.createReading(new ReadingId("reading3"), new ReadingValue("500"),
                powerSourceSensorId, new TimeStamp(endTime));

        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(powerSourceSensorId, new TimeStamp(startTime),
                new TimeStamp(midTime))).thenReturn(List.of(powerSourceReading1));
        when(mockReadingRepository.findReadingsBySensorIdInAGivenPeriod(powerSourceSensorId, new TimeStamp(midTime),
                new TimeStamp(endTime))).thenReturn(List.of(powerSourceReading2,powerSourceReading3));

        IReadingService readingService = new ReadingServiceImpl(mockReadingRepository, mockSensorRepositoryPeakPower,
                mockDeviceRepositoryPeakPower, filePathName,filePathModels );

        ReadingRESTController controller = new ReadingRESTController(readingService, readingMapper, valueMapper);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .param("start", "2024-01-01T00:00:00").param("end", "2024-01-01T01:00:00")
                ).andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.value").value("1050.0"));


    }


}