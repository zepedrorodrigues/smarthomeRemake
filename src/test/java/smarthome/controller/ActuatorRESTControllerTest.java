package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.actuator.ActuatorFactoryImpl;
import smarthome.domain.actuator.ActuatorOfBlindRoller;
import smarthome.domain.actuator.ActuatorOfBlindRollerFactory;
import smarthome.domain.actuator.ActuatorOfDecimalLimiterFactory;
import smarthome.domain.actuator.ActuatorOfLimiterFactory;
import smarthome.domain.actuator.ActuatorOfOnOffSwitchFactory;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
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
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.ScalePercentageValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorIdDTO;
import smarthome.mapper.ValueDTO;
import smarthome.mapper.mapper.ActuatorMapper;
import smarthome.mapper.mapper.ValueMapper;
import smarthome.service.IActuatorService;
import smarthome.service.impl.ActuatorServiceImpl;

import javax.naming.ConfigurationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the ActuatorRESTController.
 * It tests all the endpoints and their responses under different conditions.
 * The tests are written using JUnit 5 and Mockito for mocking dependencies.
 */
class ActuatorRESTControllerTest {

    String actuatorModelName;

    DeviceFactory deviceFactory;
    IActuatorService actuatorService;
    IActuatorRepository mockActuatorRepository;
    IDeviceRepository mockDeviceRepository;
    ISensorRepository mockSensorRepository;
    IReadingRepository mockReadingRepository;
    String deviceId;
    DeviceId deviceId1;
    SensorFactory sensorFactory;
    ReadingFactory readingFactory;
    Device device;

    ActuatorFactory actuatorFactory;
    String actuatorId1;
    String filePathName = "configTest.properties";
    ActuatorMapper actuatorMapper;
    ValueMapper valueMapper;

    ActuatorRESTController actuatorRESTController;

    private String uri;
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    /**
     * Setup before each test.
     */
    @BeforeEach
    void setUp() throws ConfigurationException, org.apache.commons.configuration2.ex.ConfigurationException {

        deviceFactory = new DeviceFactoryImpl();
        mockDeviceRepository = mock(IDeviceRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockReadingRepository = mock(IReadingRepository.class);
        deviceId = "deviceId";
        deviceId1 = new DeviceId(deviceId);

        actuatorModelName = "ActuatorOfBlindRoller";
        actuatorId1 = "actuatorId";

        mockActuatorRepository = mock(IActuatorRepository.class);
        when(mockDeviceRepository.containsIdentity(deviceId1)).thenReturn(true);
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        actuatorFactory = new ActuatorFactoryImpl(new ActuatorOfBlindRollerFactory(), new ActuatorOfDecimalLimiterFactory(), new ActuatorOfLimiterFactory(), new ActuatorOfOnOffSwitchFactory());
        actuatorMapper = new ActuatorMapper();
        valueMapper = new ValueMapper();
        sensorFactory = new SensorFactoryImpl();
        readingFactory = new ReadingFactoryImpl();

        device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"), new RoomId("room"));

        actuatorService = new ActuatorServiceImpl(mockActuatorRepository, mockDeviceRepository, mockSensorRepository,
                mockReadingRepository, actuatorFactory, readingFactory, filePathName);

        actuatorRESTController = new ActuatorRESTController(actuatorService, actuatorMapper, valueMapper);

        uri = "/actuators";
        mvc = MockMvcBuilders.standaloneSetup(actuatorRESTController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * This test checks if the ActuatorRESTController can be constructed successfully.
     */
    @Test
    void testActuatorRESTControllerCanBeConstructed() {
        ActuatorRESTController actuatorRESTController = new ActuatorRESTController(actuatorService, actuatorMapper,
                valueMapper);
        assertNotNull(actuatorRESTController, "ActuatorRESTController should be constructed successfully");
    }

    /**
     * This test checks if the getActuatorById method returns the correct HTTP status when the Actuator exists.
     */
    @Test
    void testGetActuatorByIdReturnsCorrectHttpStatusWhenActuatorExists() {
        // Arrange
        String actuatorId = "actuatorId";
        Actuator actuator = actuatorFactory.createActuator(new ActuatorMap(new ActuatorId(actuatorId), new DeviceId(
                "deviceId"), new ActuatorModelName("ActuatorOfBlindRoller"), null, null, null, null, null));
        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId))).thenReturn(Optional.of(actuator));
        HttpStatus expectedStatus = HttpStatus.OK;
        // Act
        ResponseEntity<EntityModel<ActuatorDTO>> response = actuatorRESTController.getActuatorByIdentity(actuatorId);
        // Assert
        assertEquals(expectedStatus, response.getStatusCode(), "The HTTP status for an existing actuator should be OK");
    }

    /**
     * This test checks if the getActuatorById method returns the correct HTTP status when the Actuator does not exist.
     */
    @Test
    void testGetActuatorByIdReturnsCorrectHttpStatusWhenActuatorDoesNotExist() {
        // Arrange
        String actuatorId = "actuatorId";
        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId))).thenReturn(Optional.empty());
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        // Act
        ResponseEntity<EntityModel<ActuatorDTO>> response = actuatorRESTController.getActuatorByIdentity(actuatorId);
        // Assert
        assertEquals(expectedStatus, response.getStatusCode(),
                "The HTTP status for a non-existent actuator should " + "be" + " NOT_FOUND");
    }


    /**
     * This test checks if the getActuatorById method returns the correct HTTP status when the ActuatorId does not
     * exist.
     */
    @Test
    void testGetActuatorByIdWithMockMvcReturnsCorrectHttpStatusWhenActuatorIdentityDoesNotExist() throws Exception {
        // Arrange
        String actuatorId = "actuatorId";
        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId))).thenReturn(Optional.empty());
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorId}", actuatorId)).andReturn();
        // Assert
        assertEquals(expectedStatus.value(), result.getResponse().getStatus(),
                "The HTTP status for a non-existent " + "actuator should be NOT_FOUND");
    }

    /**
     * This test checks if the getActuatorById method returns the correct HTTP status when the ActuatorId exists.
     */

    @Test
    void testGetActuatorByIdWithMockMvcReturnsCorrectHttpStatusWhenActuatorIdentityExists() throws Exception {
        // Arrange
        String actuatorId = "actuatorId";
        Actuator actuator = actuatorFactory.createActuator(new ActuatorMap(new ActuatorId(actuatorId), new DeviceId(
                "deviceId"), new ActuatorModelName("ActuatorOfBlindRoller"), null, null, null, null, null));
        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId))).thenReturn(Optional.of(actuator));
        HttpStatus expectedStatus = HttpStatus.OK;
        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri + "/{actuatorId}", actuatorId)).andReturn();
        // Assert
        assertEquals(expectedStatus.value(), result.getResponse().getStatus(), "The HTTP status for an existing " +
                "actuator should be OK");
    }

    /**
     * This test checks if the getActuatorById method returns the correct HTTP status when the ActuatorId is invalid.
     */
    @Test
    void testGetActuatorByIdReturnsCorrectHttpStatusWhenActuatorIdentityIsInvalid() {
        // Arrange
        String actuatorId = "invalidActuatorId";
        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId))).thenReturn(Optional.empty());
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        // Act
        ResponseEntity<EntityModel<ActuatorDTO>> response = actuatorRESTController.getActuatorByIdentity(actuatorId);
        // Assert
        assertEquals(expectedStatus, response.getStatusCode(),
                "The HTTP status for an invalid actuatorId should be " + "NOT_FOUND");
    }


    /**
     * This test checks if the addActuator method returns the correct HTTP status when the Actuator is added to the
     * Device.
     */
    @Test
    void testAddActuatorReturnsCreatedWhenActuatorIsAddedToDevice() {
        //Arrange
        String deviceId = "deviceId";
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        String actuatorModelName = "ActuatorOfBlindRoller";
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId1, actuatorModelName);
        when(mockDeviceRepository.containsIdentity(deviceId1)).thenReturn(true);
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.ofNullable(device1));
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        ResponseEntity<ActuatorDTO> result = actuatorRESTController.addActuator(deviceId, actuatorDTO);
        //Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode(),
                "The HTTP status for a valid ActuatorDTO should be " + "CREATED");
    }

    /**
     * This test checks if the addActuator method returns the correct HTTP status when the ActuatorDTO is null.
     */
    @Test
    void testAddActuatorReturnsCorrectHTTPStatusWhenActuatorDTOIsNull() {
        //Arrange
        ActuatorDTO nullActuatorDTO = null;
        //Act
        ResponseEntity<ActuatorDTO> result = actuatorRESTController.addActuator(deviceId, nullActuatorDTO);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),
                "The HTTP status for a null ActuatorDTO should " + "be BAD_REQUEST");
    }

    /**
     * This test checks if the addActuator method returns the correct HTTP status when the ActuatorModelName is not
     * valid.
     */
    @Test
    void testAddActuatorReturnsCorrectHTTPStatusWhenActuatorModelNameIsNotValid() {
        //Arrange
        String deviceId = "deviceId";
        String actuatorModelName = "invalidActuatorModelName";
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId1, deviceId, actuatorModelName);
        when(mockDeviceRepository.containsIdentity(deviceId1)).thenReturn(true);
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.ofNullable(device1));
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        ResponseEntity<ActuatorDTO> result = actuatorRESTController.addActuator(deviceId, actuatorDTO);
        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(), "The HTTP status for an invalid " +
                "actuatorId should be UNPROCESSABLE_ENTITY");
    }

    /**
     * This test checks if the addActuator method returns the correct HTTP status when the DeviceId is not valid.
     */
    @Test
    void testAddActuatorReturnsCorrectHTTPStatusWhenDeviceIdIsNotValid() {
        //Arrange
        String deviceId = "deviceId";
        String actuatorModelName = "ActuatorOfDecimalLimiter";
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId1, deviceId, actuatorModelName, null, null, 2.0, 1.0, -1);
        when(mockDeviceRepository.containsIdentity(deviceId1)).thenReturn(false);
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.ofNullable(device1));
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        ResponseEntity<ActuatorDTO> result = actuatorRESTController.addActuator(deviceId, actuatorDTO);
        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(), "The HTTP status for an invalid " +
                "deviceId should be UNPROCESSABLE_ENTITY");
    }

    /**
     * This test checks if the addActuator method returns the correct HTTP status when the ActuatorModelName is null.
     */
    @Test
    void testAddActuatorReturnsCorrectHTTPStatusWhenActuatorModelNameIsNull() {
        // Arrange
        String deviceId = "deviceId";
        String actuatorModelName = null;
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId1, deviceId, actuatorModelName, null, null, null, null,
                null);
        when(mockDeviceRepository.containsIdentity(deviceId1)).thenReturn(true);
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.ofNullable(device1));
        // Act
        ResponseEntity<ActuatorDTO> result = actuatorRESTController.addActuator(deviceId, actuatorDTO);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(), "The HTTP status for a null " +
                "actuatorModelName should be UNPROCESSABLE_ENTITY");
    }

    /*
     * This test checks if the addActuator method returns the correct HTTP status when the DeviceId is not valid.
     */
    @Test
    void testAddActuatorReturnsCorrectHTTPStatusWhenActuatorIsNotAddedToDevice() {
        //Arrange
        String deviceId = "deviceId";
        String actuatorModelName = "ActuatorOfBlindRoller";
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId1, deviceId, actuatorModelName, null, null, null, null,
                null);
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(false);
        when(mockDeviceRepository.findByIdentity(new DeviceId(deviceId))).thenReturn(Optional.ofNullable(device1));
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        ResponseEntity<ActuatorDTO> result = actuatorRESTController.addActuator(deviceId, actuatorDTO);
        //Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode(), "The HTTP status for an invalid " +
                "deviceId should be UNPROCESSABLE_ENTITY");
    }

    /**
     * This test checks if the addActuator method returns the correct response body when the Actuator is added and
     * saved to the Device.
     */
    @Test
    void testAddActuatorCorrectlySavesActuatorAndReturnsCorrectResponseBody() {
        //Arrange
        String deviceId = "deviceId";
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId1, deviceId, actuatorModelName, null, null, null, null,
                null);
        when(mockDeviceRepository.findByIdentity(new DeviceId(deviceId))).thenReturn(Optional.ofNullable(device1));
        //Act
        ResponseEntity<ActuatorDTO> responseEntity = actuatorRESTController.addActuator(deviceId, actuatorDTO);

        //Assert
        ActuatorDTO responseBody = Objects.requireNonNull(responseEntity.getBody());
        assertTrue(!responseBody.getActuatorId().isBlank() && responseBody.getDeviceId().equals(deviceId) && responseBody.getActuatorModelName().equals("ActuatorOfBlindRoller"), "The response body should contain the correct actuator data");
    }

    /**
     * This test checks if the addActuator method correctly handles a post request with valid JSON data and returns
     * the correct HTTP status code.
     */
    @Test
    void testAddActuatorCorrectlyHandlesAPostRequestWithValidJSONDataAndReturnsCorrectHTTPStatusCode() throws Exception {
        //Arrange
        String addActuatorUri = "/actuators/device/" + deviceId;
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId1, deviceId, "ActuatorOfBlindRoller", null, null, null,
                null, null);
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(mockDeviceRepository.findByIdentity(new DeviceId(deviceId))).thenReturn(Optional.ofNullable(device1));
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String dtoAsJSON = objectMapper.writeValueAsString(actuatorDTO);
        int expected = HttpStatus.CREATED.value();
        //Act
        int result =
                mvc.perform(MockMvcRequestBuilders.post(addActuatorUri).contentType(MediaType.APPLICATION_JSON).content(dtoAsJSON)).andReturn().getResponse().getStatus();
        //Assert
        assertEquals(expected, result, "The response body should contain the " + "correct actuator data");
    }

    /**
     * This test checks if the addActuator method correctly handles a post request with invalid JSON data and returns
     * the Response Body.
     */
    @Test
    void testAddActuatorCorrectlyHandlesAPostRequestWithValidJSONDataAndReturnsCorrectResponseBody() throws Exception {
        //Arrange
        String addActuatorUri = "/actuators/device/" + deviceId;
        String validActuatorId = "ActuatorId";
        ActuatorDTO actuatorDTO = new ActuatorDTO(validActuatorId, deviceId, "ActuatorOfBlindRoller");
        String dtoJSON = objectMapper.writeValueAsString(actuatorDTO);
        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));
        Actuator actuator = actuatorFactory.createActuator(new ActuatorMap(new ActuatorId(validActuatorId),
                new DeviceId(deviceId), new ActuatorModelName("ActuatorOfBlindRoller"), null, null, null, null, null));
        when(mockDeviceRepository.findByIdentity(new DeviceId(deviceId))).thenReturn(Optional.ofNullable(device1));
        when(mockActuatorRepository.save(any(Actuator.class))).thenReturn(actuator);
        ActuatorDTO expectedActuatorDTO = actuatorMapper.actuatorToDTO(actuator);
        String expectedResponseBody = objectMapper.writeValueAsString(expectedActuatorDTO);
        //Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.post(addActuatorUri).contentType(MediaType.APPLICATION_JSON).content(dtoJSON)).andReturn();
        //Assert
        String resultBody = result.getResponse().getContentAsString();
        assertEquals(expectedResponseBody, resultBody, "The response body should contain the correct actuator data");
    }

    /*
     * This test checks if the addActuator method correctly handles a bad post request with invalid JSON data and
     * returns the correct HTTP status code.
     */
    @Test
    void testAddActuatorCorrectlyHandlesAPostRequestWithInvalidJSONAndReturnsCorrectHTTPStatus() throws Exception {
        //Arrange
        String addActuatorUri = "/actuators/device/" + deviceId;
        ActuatorDTO actuatorDTO = null;
        String dtoJSON = objectMapper.writeValueAsString(actuatorDTO);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        //Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.post(addActuatorUri).contentType(MediaType.APPLICATION_JSON).content(dtoJSON)).andReturn();
        //Assert

        assertEquals(expectedStatus.value(), result.getResponse().getStatus(), "The HTTP status for a null " +
                "ActuatorDTO should be BAD_REQUEST");
    }

    /**
     * This test checks if the addActuator method correctly handles a post request with invalid JSON data and returns
     * the correct HTTP status code.
     */
    @Test
    void testAddActuatorReturnsUnprocessableEntity() throws Exception {
        // Arrange
        String deviceId = "deviceId";
        ActuatorDTO actuatorDTO = new ActuatorDTO("actuatorId", deviceId, "ActuatorModelName");
        String addActuatorUri = "/actuators/device/" + deviceId;
        HttpStatus expectedStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.post(addActuatorUri).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(actuatorDTO))).andReturn();

        // Assert
        assertEquals(expectedStatus.value(), result.getResponse().getStatus(), "The HTTP status for an unprocessable "
                + "entity should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the getByDeviceIdentity method.
     * This test asserts that the getByDeviceIdentity method returns the correct HTTP status when the DeviceId is
     * valid. The test passes if the HTTP status is OK.
     */
    @Test
    void testGetByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceIdIsValid() {
        // Arrange
        String deviceId = "deviceId";
        ActuatorId actuatorIdentity = new ActuatorId(actuatorId1);
        DeviceId deviceIdentity = new DeviceId(deviceId);
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(deviceIdentity)).thenReturn(List.of(actuatorIdentity));
        HttpStatus expectedStatus = HttpStatus.OK;
        // Act
        ResponseEntity<List<ActuatorIdDTO>> response = actuatorRESTController.getByDeviceIdentity(deviceId);
        // Assert
        HttpStatusCode actualStatus = response.getStatusCode();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for a valid deviceId should be OK");
    }

    /**
     * Test for the getByDeviceIdentity method.
     * This test asserts that the getByDeviceIdentity method returns the correct body response when the DeviceId is
     * valid. The test passes if the response body contains the correct actuator data.
     */
    @Test
    void testGetByDeviceIdentityReturnsCorrectBodyResponseWhenDeviceIdIsValid() {
        // Arrange
        String deviceId = "deviceId";
        ActuatorId actuatorIdentity = new ActuatorId(actuatorId1);
        DeviceId deviceIdentity = new DeviceId(deviceId);
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(deviceIdentity)).thenReturn(List.of(actuatorIdentity));
        // Act
        ResponseEntity<List<ActuatorIdDTO>> response = actuatorRESTController.getByDeviceIdentity(deviceId);
        // Assert
        List<ActuatorIdDTO> responseBody = response.getBody();
        boolean conditions =
                responseBody != null && responseBody.size() == 1 && responseBody.get(0).getActuatorId().equals(actuatorId1);
        assertTrue(conditions, "The response body should contain the correct actuator data");
    }

    /**
     * Test for the getByDeviceIdentity method.
     * This test asserts that the getByDeviceIdentity method returns the correct HTTP status when the DeviceId is
     * doesn't exist. The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testGetByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceIdDoesNotExist() {
        // Arrange
        String deviceId = "deviceId";
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(false);
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        // Act
        ResponseEntity<List<ActuatorIdDTO>> response = actuatorRESTController.getByDeviceIdentity(deviceId);
        // Assert
        HttpStatusCode actualStatus = response.getStatusCode();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for an invalid deviceId should be BAD_REQUEST");
    }

    /**
     * Test for the getByDeviceIdentity method.
     * This test asserts that the method returns the correct HTTP status when the DeviceId is invalid. The test passes
     * if the HTTP status is UNPROCESSABLE_ENTITY.
     */
    @Test
    void testGetByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceIdIsInvalid() {
        // Arrange
        String deviceId = " ";
        HttpStatus expectedStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        // Act
        ResponseEntity<List<ActuatorIdDTO>> response = actuatorRESTController.getByDeviceIdentity(deviceId);
        // Assert
        HttpStatusCode actualStatus = response.getStatusCode();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for an invalid deviceId should be " +
                "UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the getByDeviceIdentity method.
     * This test asserts that the getByDeviceIdentity method returns the correct HTTP status when the device does not
     * contain any actuators. The test passes if the HTTP status is NOT_FOUND.
     */
    @Test
    void testGetByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceDoesNotContainActuators() {
        // Arrange
        String deviceId = "deviceId";
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(new DeviceId(deviceId))).thenReturn(List.of());
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        // Act
        ResponseEntity<List<ActuatorIdDTO>> response = actuatorRESTController.getByDeviceIdentity(deviceId);
        // Assert
        HttpStatusCode actualStatus = response.getStatusCode();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for a device without actuators should be " +
                "NOT_FOUND");
    }

    /**
     * Test for the getByDeviceIdentity method with JSON.
     * This test asserts that the getByDeviceIdentity method returns the correct HTTP status when the DeviceId is
     * valid. The test passes if the HTTP status is OK.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceIdIsValid() throws Exception {
        // Arrange
        String deviceId = "deviceId";
        ActuatorId actuatorIdentity = new ActuatorId(actuatorId1);
        DeviceId deviceIdentity = new DeviceId(deviceId);
        uri += "/device/" + deviceId;
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(deviceIdentity)).thenReturn(List.of(actuatorIdentity));
        int expectedStatus = HttpStatus.OK.value();
        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        // Assert
        int actualStatus = result.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for a valid deviceId should be OK");
    }

    /**
     * Test for the getByDeviceIdentity method with JSON.
     * This test asserts that the getByDeviceIdentity method returns the correct body response when the DeviceId is
     * valid. The test passes if the response body contains the correct actuator data.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectBodyResponseWhenDeviceIdIsValid() throws Exception {
        // Arrange
        String deviceId = "deviceId";
        ActuatorId actuatorIdentity = new ActuatorId(actuatorId1);
        DeviceId deviceIdentity = new DeviceId(deviceId);
        ActuatorIdDTO actuatorIdDTO =
                new ActuatorIdDTO(actuatorId1).add(Link.of("http://localhost/actuators/" + actuatorId1));
        uri += "/device/" + deviceId;
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(deviceIdentity)).thenReturn(List.of(actuatorIdentity));
        String expectedJSON = objectMapper.writeValueAsString(List.of(actuatorIdDTO));
        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();
        // Assert
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(expectedJSON, responseBody, "The response body should contain the correct actuator data");
    }

    /**
     * Test for the getByDeviceIdentity method with JSON.
     * This test asserts that the getByDeviceIdentity method returns the correct HTTP status when the DeviceId is
     * does not exist. The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceIdDoesNotExist() throws Exception {
        // Arrange
        String deviceId = "deviceId";
        uri += "/device/" + deviceId;
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(false);
        int expectedStatus = HttpStatus.BAD_REQUEST.value();
        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        // Assert
        int actualStatus = result.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for an invalid deviceId should be BAD_REQUEST");
    }

    /**
     * Test for the getByDeviceIdentity method with JSON.
     * This test asserts that the getByDeviceIdentity method returns the correct HTTP status when the DeviceId is
     * invalid. The test passes if the HTTP status is UNPROCESSABLE_ENTITY.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceIdIsInvalid() throws Exception {
        // Arrange
        String deviceId = " ";
        uri += "/device/" + deviceId;
        int expectedStatus = HttpStatus.UNPROCESSABLE_ENTITY.value();
        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        // Assert
        int actualStatus = result.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for an invalid deviceId should be " +
                "UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the getByDeviceIdentity method with JSON.
     * This test asserts that the getByDeviceIdentity method returns the correct HTTP status when the device does not
     * contain any actuators. The test passes if the HTTP status is NOT_FOUND.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceDoesNotContainActuators() throws Exception {
        // Arrange
        String deviceId = "deviceId";
        uri += "/device/" + deviceId;
        when(mockDeviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(new DeviceId(deviceId))).thenReturn(List.of());
        int expectedStatus = HttpStatus.NOT_FOUND.value();
        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        // Assert
        int actualStatus = result.getResponse().getStatus();
        assertEquals(expectedStatus, actualStatus, "The HTTP status for a device without actuators should be " +
                "NOT_FOUND");
    }

    // Mock MVC tests for the operateBlindRoller method


    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is achieved. The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerWithMockMvcWhenActuatorExistsAndGoalIsAchievedCheckResponseBody() throws Exception {
        // Arrange
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(0.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String uri = "/actuators/" + actuatorId1 + "/operate-blind-roller";
        String expected = objectMapper.writeValueAsString(new ValueDTO(goalValue.valueToString()));

        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.put(uri).param("percentage", goalValue.valueToString())).andReturn();
        String actual = result.getResponse().getContentAsString();

        // Assert
        assertEquals(expected, actual, "The response body should contain the correct value");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is achieved. The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerWithMockMvcWhenActuatorExistsAndGoalIsAchievedCheckHttpStatus() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(0.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String uri = "/actuators/" + actuatorId1 + "/operate-blind-roller";
        int expected = HttpStatus.OK.value();

        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.put(uri).param("percentage", goalValue.valueToString()).contentType(MediaType.APPLICATION_JSON)).andReturn();
        int actual = result.getResponse().getStatus();

        // Assert
        assertEquals(expected, actual, "The HTTP status for a valid actuator should be OK");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is negative. The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWithMockMvcWhenActuatorExistsAndGoalIsNegativeCheckHttpStatus() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(-10.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String uri = "/actuators/" + actuatorId1 + "/operate-blind-roller";
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.put(uri).param("percentage", goalValue.valueToString())).andReturn();
        int actual = result.getResponse().getStatus();

        // Assert
        assertEquals(expected, actual, "The HTTP status with a negative goal value should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is with an invalid format. The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWithMockMvcWhenGoalIsWithInvalidFormatCheckHttpStatusForUnprocessableEntity() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value currentValue = new ScalePercentageValue(50.0);
        String goalValue = "True";

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String uri = "/actuators/" + actuatorId1 + "/operate-blind-roller";
        int expected = HttpStatus.UNPROCESSABLE_ENTITY.value();

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri).param("percentage", goalValue)).andReturn();
        int actual = result.getResponse().getStatus();

        // Assert
        assertEquals(expected, actual, "The HTTP status with an invalid goal value should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator does not exist.
     * The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWithMockMvcWhenActuatorDoesNotExistCheckHttpStatus() throws Exception {
        // Arrange
        Value goalValue = new ScalePercentageValue(30.0);
        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.empty());

        String uri = "/actuators/" + actuatorId1 + "/operate-blind-roller";
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.put(uri).param("percentage", goalValue.valueToString())).andReturn();
        int actual = result.getResponse().getStatus();

        // Assert
        assertEquals(expected, actual, "The HTTP status when the actuator does not exist should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Device does not exist.
     * The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWithMockMvcWhenDeviceDoesNotExistCheckHttpStatus() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Value goalValue = new ScalePercentageValue(30.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.empty());

        String uri = "/actuators/" + actuatorId1 + "/operate-blind-roller";
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.put(uri).param("percentage", goalValue.valueToString())).andReturn();
        int actual = result.getResponse().getStatus();

        // Assert
        assertEquals(expected, actual, "The HTTP status when the device does not exist should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Device does not have
     * sensors.
     * The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWithMockMvcWhenDeviceDoesNotHaveSensorsCheckHttpStatus() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Value goalValue = new ScalePercentageValue(30.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of());
        String uri = "/actuators/" + actuatorId1 + "/operate-blind-roller";
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result =
                mvc.perform(MockMvcRequestBuilders.put(uri).param("percentage", goalValue.valueToString())).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status when the device does not have sensors should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal valid.
     * The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerWhenActuatorExistsAndCurrentValueValidCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(40.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The HTTP status for a valid actuator should be OK");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is valid.
     * The test passes if the response body contains the correct value.
     */
    @Test
    void testOperateBlindRollerWhenActuatorExistsAndGoalValueIsAchievedCheckResponseBody() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(40.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(goalValue.valueToString(), Objects.requireNonNull(response.getBody()).getValue(), "The response "
                + "body should contain the correct value");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is valid.
     * The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerWhenActuatorExistsAndGoalValueIsAchievedCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(40.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The HTTP status for a valid actuator should be OK");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is fully closed.
     * The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerFullyClosesABlindRollerCheckResponseBody() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(0.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(goalValue.valueToString(), Objects.requireNonNull(response.getBody()).getValue(), "The response "
                + "body should contain the correct value - 0.0");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the blind roller is fully
     * closed.
     * The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerFullyClosesABlindRollerCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(0.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The HTTP status for a valid actuator should be OK");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is fully opened.
     * The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerFullyOpensABlindRollerCheckResponseBody() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(100.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(goalValue.valueToString(), Objects.requireNonNull(response.getBody()).getValue(), "The response "
                + "body should contain the correct value - 100.0");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the blind roller is fully
     * opened.
     * The test passes if the HTTP status is OK.
     */
    @Test
    void testOperateBlindRollerFullyOpensABlindRollerCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(0.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The HTTP status for a valid actuator should be OK");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is negative. The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWhenActuatorExistsAndGoalIsNegativeCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(-10.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "The HTTP status with a negative goal value " + "should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is over 100. The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWhenActuatorExistsAndGoalIsOverOneHundredCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        Value goalValue = new ScalePercentageValue(101.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "The HTTP status with a goal over one hundred "
                + "value should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator exists and
     * the goal is with an invalid format. The test passes if the HTTP status is UNPROCESSABLE_ENTITY.
     */
    @Test
    void testOperateBlindRollerWhenGoalIsWithInvalidFormatCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Sensor sensor = sensorFactory.createSensor(new SensorModelName("SensorOfScalePercentage"), deviceId1);
        SensorId sensorId1 = sensor.getIdentity();
        String goalValue = "True";

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId1));
        when(mockReadingRepository.save(any(Reading.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1, goalValue);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode(), "The HTTP status with an invalid " +
                "goal" + " value should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Actuator does not exist.
     * The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWhenActuatorDoesNotExistCheckHttpStatus() {
        // Arrange
        Value goalValue = new ScalePercentageValue(30.0);
        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                "The HTTP status when the actuator does not " + "exist should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Device does not exist.
     * The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWhenDeviceDoesNotExistCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Value goalValue = new ScalePercentageValue(30.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "The HTTP status when the device does not " +
                "exist should be BAD_REQUEST");
    }

    /**
     * Test asserts that the operateBlindRoller method returns the correct HTTP status when the Device does not have
     * sensors.
     * The test passes if the HTTP status is BAD_REQUEST.
     */
    @Test
    void testOperateBlindRollerWhenDeviceDoesNotHaveSensorsCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceName("device"), new DeviceTypeName("device"),
                new RoomId("room"));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        Value goalValue = new ScalePercentageValue(30.0);

        when(mockActuatorRepository.findByIdentity(new ActuatorId(actuatorId1))).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorsByDeviceIdAndSensorModelName(eq(deviceId1), any(SensorModelName.class))).thenReturn(List.of());

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.operateBlindRoller(actuatorId1,
                goalValue.valueToString());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "The HTTP status when the device does not " +
                "have" + " sensors should be BAD_REQUEST");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct HTTP status when the Actuator exists
     * and the last reading is valid.
     * The test passes if the method returns an OK status.
     */
    @Test
    void testGetLastPercentageReadingWhenActuatorExistsAndLastReadingIsValidCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceId("id"), new DeviceName("device"), new DeviceTypeName(
                "device"), new RoomId("room"), new DeviceStatus(true));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        SensorId sensorId = new SensorId("id");
        Value value = new ScalePercentageValue(30.0);
        TimeStamp timeStamp = new TimeStamp(LocalDateTime.now());
        ReadingId readingId = new ReadingId("id");
        Reading reading = readingFactory.createReading(readingId, value, sensorId, timeStamp);

        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId));
        when(mockReadingRepository.findLastReadingBySensorId(sensorId)).thenReturn(Optional.of(reading));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.getLastPercentageReading(actuatorId1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "The HTTP status for a valid actuator should be OK");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct body response when the actuator
     * exists and the last reading is valid.
     * The test passes if the method returns the expected value.
     */
    @Test
    void testGetLastPercentageReadingWhenActuatorExistsAndLastReadingIsValidCheckResponseBody() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceId("id"), new DeviceName("device"), new DeviceTypeName(
                "device"), new RoomId("room"), new DeviceStatus(true));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        SensorId sensorId = new SensorId("id");
        Value value = new ScalePercentageValue(30.0);
        TimeStamp timeStamp = new TimeStamp(LocalDateTime.now());
        ReadingId readingId = new ReadingId("id");
        Reading reading = readingFactory.createReading(readingId, value, sensorId, timeStamp);

        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId));
        when(mockReadingRepository.findLastReadingBySensorId(sensorId)).thenReturn(Optional.of(reading));

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.getLastPercentageReading(actuatorId1);

        // Assert
        assertEquals(value.valueToString(), Objects.requireNonNull(response.getBody()).getValue(), "The response " +
                "body should contain the correct value");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct HTTP status when the Actuator exists
     * but has no readings.
     * The test passes if the method returns a NOT_FOUND status.
     */
    @Test
    void testGetLastPercentageReadingWhenActuatorExistsButHasNoReadingsCheckHttpStatus() {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceId("id"), new DeviceName("device"), new DeviceTypeName(
                "device"), new RoomId("room"), new DeviceStatus(true));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        SensorId sensorId = new SensorId("id");

        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId));
        when(mockReadingRepository.findLastReadingBySensorId(any(SensorId.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.getLastPercentageReading(actuatorId1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "The HTTP status when the actuator has no " +
                "readings " + "should be NOT_FOUND");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct HTTP status when the Actuator does not
     * exist.
     * The test passes if the method returns a BAD_REQUEST status.
     */
    @Test
    void testGetLastPercentageReadingWhenActuatorDoesNotExistCheckHttpStatus() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ValueDTO> response = actuatorRESTController.getLastPercentageReading(actuatorId1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "The HTTP status when the actuator does not " +
                "exist should be NOT_FOUND");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct HTTP status when the Actuator exists
     * and the last reading is valid.
     * The test passes if the HTTP status is OK.
     * This test uses MockMvc.
     */
    @Test
    void testGetLastPercentageReadingWithMockMvcWhenActuatorExistsAndLastReadingIsValidCheckHttpStatus() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceId("id"), new DeviceName("device"), new DeviceTypeName(
                "device"), new RoomId("room"), new DeviceStatus(true));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        SensorId sensorId = new SensorId("id");
        Value value = new ScalePercentageValue(30.0);
        TimeStamp timeStamp = new TimeStamp(LocalDateTime.now());
        ReadingId readingId = new ReadingId("id");
        Reading reading = readingFactory.createReading(readingId, value, sensorId, timeStamp);

        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId));
        when(mockReadingRepository.findLastReadingBySensorId(sensorId)).thenReturn(Optional.of(reading));

        String uri = "/actuators/" + actuatorId1 + "/current-percentage-value";
        int expected = HttpStatus.OK.value();

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status for a valid actuator should be OK");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct body response when the Actuator exists
     * and the last reading is valid.
     * The test passes if the method returns the expected value.
     * This test uses MockMvc.
     */
    @Test
    void testGetLastPercentageReadingWithMockMvcWhenActuatorExistsAndLastReadingIsValidCheckResponseBody() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceId("id"), new DeviceName("device"), new DeviceTypeName(
                "device"), new RoomId("room"), new DeviceStatus(true));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        SensorId sensorId = new SensorId("id");
        Value value = new ScalePercentageValue(30.0);
        TimeStamp timeStamp = new TimeStamp(LocalDateTime.now());
        ReadingId readingId = new ReadingId("id");
        Reading reading = readingFactory.createReading(readingId, value, sensorId, timeStamp);

        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId));
        when(mockReadingRepository.findLastReadingBySensorId(sensorId)).thenReturn(Optional.of(reading));

        ValueDTO expectedDTO = new ValueDTO(value.valueToString());
        String expected = objectMapper.writeValueAsString(expectedDTO);
        String uri = "/actuators/" + actuatorId1 + "/current-percentage-value";

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        // Assert
        String actual = result.getResponse().getContentAsString();
        assertEquals(expected, actual, "The response body should contain the correct value");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct HTTP status when the Actuator exists
     * but has no readings.
     * The test passes if the method returns a NOT_FOUND status.
     * This test uses MockMvc.
     */
    @Test
    void testGetLastPercentageReadingWithMockMvcWhenActuatorExistsButHasNoReadingsCheckHttpStatus() throws Exception {
        // Arrange
        Device device = deviceFactory.createDevice(new DeviceId("id"), new DeviceName("device"), new DeviceTypeName(
                "device"), new RoomId("room"), new DeviceStatus(true));
        DeviceId deviceId1 = device.getDeviceId();
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        ActuatorModelName actuatorModelName = new ActuatorModelName("ActuatorOfBlindRoller");
        ActuatorOfBlindRoller actuator =
                (ActuatorOfBlindRoller) actuatorFactory.createActuator(new ActuatorMap(actuatorId, deviceId1,
                        actuatorModelName, null, null, null, null, null));
        SensorId sensorId = new SensorId("id");

        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.of(actuator));
        when(mockDeviceRepository.findByIdentity(deviceId1)).thenReturn(Optional.of(device));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(deviceId1),
                any(SensorModelName.class))).thenReturn(List.of(sensorId));
        when(mockReadingRepository.findLastReadingBySensorId(any(SensorId.class))).thenReturn(Optional.empty());

        String uri = "/actuators/" + actuatorId1 + "/current-percentage-value";
        int expected = HttpStatus.NOT_FOUND.value();

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status when the actuator has no readings should be NOT_FOUND");
    }

    /**
     * Test asserts that the getLastPercentageReading method returns the correct HTTP status when the Actuator does not
     * exist.
     * The test passes if the method returns a NOT_FOUND.
     */
    @Test
    void testGetLastPercentageReadingWithMockMvcWhenActuatorDoesNotExistCheckHttpStatus() throws Exception {
        // Arrange
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        when(mockActuatorRepository.findByIdentity(actuatorId)).thenReturn(Optional.empty());

        String uri = "/actuators/" + actuatorId1 + "/current-percentage-value";
        int expected = HttpStatus.NOT_FOUND.value();

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status when the actuator does not exist should be NOT_FOUND");
    }


    /**
     * Test asserts that the getLastPercentageReading method returns the correct HTTP status when the repository throws
     * an exception.
     */
    @Test
    void testGetLastPercentageReadingWithMockMvcWhenRepositoryThrowsExceptionCheckHttpStatus() throws Exception {
        // Arrange
        ActuatorId actuatorId = new ActuatorId(actuatorId1);
        when(mockActuatorRepository.findByIdentity(actuatorId)).thenThrow(new RuntimeException());

        String uri = "/actuators/" + actuatorId1 + "/current-percentage-value";
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual,
                "The HTTP status when the repository throws an exception should be BAD_REQUEST");
    }



}