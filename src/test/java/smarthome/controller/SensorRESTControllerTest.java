package smarthome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorIdDTO;
import smarthome.mapper.mapper.SensorMapper;
import smarthome.service.ISensorService;
import smarthome.service.impl.SensorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains the unit tests for the SensorRESTController class.
 */
class SensorRESTControllerTest {

    IDeviceRepository deviceRepository;

    ISensorRepository sensorRepository;

    SensorMapper sensorMapper;

    SensorFactory sensorFactory;

    SensorRESTController sensorRESTController;

    String sensorID;

    String deviceId1;

    String sensorModelName;

    ISensorService sensorService;

    String uri;

    MockMvc mvc;

    ObjectMapper objectMapper;

    Sensor sensor;

    /**
     * This method sets up the necessary objects for the tests.
     * It initializes repositories, factories, mapper, controllers, and DTOs that are used in the test methods.
     * It also saves some initial data into the repositories.
     * This method is annotated with @BeforeEach, so it runs before each test method in this class.
     *
     */
    @BeforeEach
    void setUp(){
        deviceRepository = mock(IDeviceRepository.class);
        deviceId1 = "deviceId1";

        sensorModelName = "SensorOfTemperature";
        sensorID = "validSensorTypeId";

        sensorRepository = mock(ISensorRepository.class);
        when(deviceRepository.containsIdentity(new DeviceId(deviceId1))).thenReturn(true);

        when(sensorRepository.save(any(Sensor.class))).thenAnswer(invocation
                -> invocation.getArgument(0));

        sensorFactory = new SensorFactoryImpl();
        sensor = sensorFactory.createSensor(new SensorModelName(sensorModelName), new DeviceId(deviceId1));
        when(sensorRepository.findByIdentity(new SensorId(sensorID))).thenReturn(Optional.of(sensor));
        sensorMapper = new SensorMapper();

        sensorService = new SensorServiceImpl(sensorRepository, sensorFactory, deviceRepository);

        sensorRESTController = new SensorRESTController(sensorService, sensorMapper);

        uri = "/sensors";
        mvc = MockMvcBuilders.standaloneSetup(sensorRESTController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * This test checks if the SensorRESTController can be constructed.
     */
    @Test
    void testSensorRestControllerCanBeConstructed() {
        //Arrange + Act
        SensorRESTController sensorRESTController = new SensorRESTController(sensorService, sensorMapper);
        //Assert
        assertNotNull(sensorRESTController, "SensorRESTController should be constructed");
    }

    /**
     * This test case verifies that the getSensorByIdentity method in the SensorRESTController class returns a NOT_FOUND (404)
     * status when the sensor with the provided ID does not exist. The test mocks the behavior of the SensorService to return
     * an empty Optional when queried with a non-existent sensor ID. It then sends a GET request to the endpoint
     * `/sensors/{sensorId}` where `{sensorId}` is a non-existent sensor ID. The test asserts that the HTTP status of
     * the response is 404 (Not Found), indicating that the sensor could not be found.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetSensorByIdentityDoesNotExist() throws Exception {
        // Arrange
        String sensorId = "sensorId";
        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorId}", sensorId))
                .andReturn();
        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus(),
                "The HTTP status for a sensor that does not exist should be NOT_FOUND.");
    }

    /**
     * This test case verifies that the getSensorByIdentity method in the SensorRESTController class returns a NOT_FOUND (404)
     * status when the sensor with the provided ID does not exist. The test mocks the behavior of the SensorRepository to return
     * an empty Optional when queried with a non-existent sensor ID. It then calls the getSensorByIdentity method with the
     * non-existent sensor ID. The test asserts that the HTTP status of the response is 404 (Not Found), indicating that the
     * sensor could not be found.
     */
    @Test
    void testGetSensorByIdentityReturnsNotFoundWhenSensorDoesNotExist() {
        // Arrange
        String sensorIdStr = "nonExistentId";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        // Act
        ResponseEntity<EntityModel<SensorDTO>> result = sensorRESTController.getSensorByIdentity(sensorIdStr);
        // Assert
        HttpStatus actual = (HttpStatus) result.getStatusCode();
        assertEquals(expected, actual, "The status code should be 404");
    }

    /**
     * This test verifies that the getSensorByIdentity method in the SensorRESTController class returns a OK (200)
     * status when the sensor with the provided ID exists. The test mocks the behavior of the SensorService to return
     * a Sensor when queried with an existing sensor ID. It then calls the getSensorByIdentity method with the
     * existing sensor ID. The test asserts that the HTTP status of the response is 200 (OK), indicating that the
     * sensor was found.
     */
    @Test
    void testGetSensorByIdentityShouldReturnHttpStatusOkWhenSensorExists(){
        // Arrange
        HttpStatus expected = HttpStatus.OK;
        // Act
        ResponseEntity<EntityModel<SensorDTO>> result = sensorRESTController.getSensorByIdentity(sensorID);
        // Assert
        HttpStatus actual = (HttpStatus) result.getStatusCode();
        assertEquals(expected, actual,
                "When a sensor with the provided ID exists, the status code should be 200 (OK)");
    }

    /**
     * This test verifies that the getSensorByIdentity method in the SensorRESTController class returns a OK (200)
     * status when the sensor with the provided ID exists. The test mocks the behavior of the SensorService to return
     * a Sensor when queried with an existing sensor ID. It then sends a GET request to the endpoint
     * `/sensors/{sensorId}` where `{sensorId}` is an existing sensor ID using MockMvc. The test asserts that the HTTP status of
     * the response is 200 (OK), indicating that the sensor was found.
     */
    @Test
    void testGetSensorByIdentityShouldReturnHttpStatusOkWhenSensorExistsUsingMvc() throws Exception {
        // Arrange
        HttpStatus expected = HttpStatus.OK;
        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorId}", sensorID))
                .andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "When a sensor with the provided ID exists, the status code should be 200 (OK) using Mvc");
    }

    /**
     * This test verifies that the getSensorByIdentity method in the SensorRESTController class returns the correct
     * response body when the sensor with the provided ID exists. The test mocks the behavior of the SensorService to return
     * a Sensor when queried with an existing sensor ID. It then sends a GET request to the endpoint
     * `/sensors/{sensorId}` where `{sensorId}` is an existing sensor ID using MockMvc. The test asserts that the response body
     * matches the expected SensorDTO entity, indicating that the sensor was found and the correct information was returned.
     */
    @Test
    void testGetSensorByIdentityShouldReturnCorrectResponseBodyWhenSensorExists() throws Exception {
        // Arrange
        SensorDTO sensorDTO = sensorMapper.toSensorDTO(sensor);
        Link link = (Link.of("http://localhost/sensors/" + sensorID).withSelfRel());
        EntityModel<SensorDTO> expectedSensor = EntityModel.of(sensorDTO,link);
        String expectedSensorJson = objectMapper.writeValueAsString(expectedSensor);

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.get(uri+"/{sensorId}", sensorID))
                .andReturn();
        // Assert
        String actualSensorJson = result.getResponse().getContentAsString();
        assertEquals(expectedSensorJson, actualSensorJson,
                "When a sensor with the provided ID exists," +
                        " the response body should match the expected SensorDTO entity");
    }

    /**
     * This test verifies that the addSensor method in the SensorRESTController class returns a CREATED (201)
     * status when a new sensor is successfully added. The test mocks the behavior of the SensorRepository to return
     * a Sensor when a new sensor is added. It then sends a POST request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an existing device ID using MockMvc. The test asserts that the HTTP status of
     * the response is 201 (CREATED), indicating that the sensor was successfully added.
     */
    @Test
    void testAddSensorShouldReturnHttpStatusCreatedWhenSensorIsAdded() throws Exception {
        // Arrange
        HttpStatus expected = HttpStatus.CREATED;
        SensorDTO sensorDTO = sensorMapper.toSensorDTO(sensor);
        String sensorJson = objectMapper.writeValueAsString(sensorDTO);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(uri+"/device/{deviceId}", deviceId1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sensorJson))
                .andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "When a new sensor is added, the status code should be 201 (CREATED)");
    }

    /**
     * This test verifies that the addSensor method in the SensorRESTController class returns the correct
     * response body when a new sensor is successfully added. The test mocks the behavior of the SensorRepository to return
     * a Sensor when a new sensor is added. It then sends a POST request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an existing device ID using MockMvc. The test asserts that the response body
     * matches the expected SensorDTO entity, indicating that the sensor was successfully added and the correct information was returned.
     */
    @Test
    void testAddShouldReturnCorrectResponseBodyWhenSensorIsAdded() throws Exception {
        // Arrange
        SensorDTO sensorDTO = sensorMapper.toSensorDTO(sensor);
        String sensorJson = objectMapper.writeValueAsString(sensorDTO);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(uri+"/device/{deviceId}", deviceId1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sensorJson))
                .andReturn();

        // Assert
        SensorDTO savedSensorDTO = sensorMapper.toSensorDTO(sensor);
        Link link = (Link.of("http://localhost/sensors/" + savedSensorDTO.getSensorId()).withSelfRel());
        EntityModel<SensorDTO> expectedSensor = EntityModel.of(savedSensorDTO, link);
        String expectedSensorJson = objectMapper.writeValueAsString(expectedSensor);

        String actualSensorJson = result.getResponse().getContentAsString();
        assertEquals(expectedSensorJson, actualSensorJson,
                "When a new sensor is added, the response body should match the expected SensorDTO entity");
    }

    /**
     * This test verifies that the addSensor method in the SensorRESTController class returns a BAD_REQUEST (400)
     * status when a new sensor is not successfully added. The test mocks the behavior of the SensorRepository to return
     * null when a new sensor is attempted to be added. It then sends a POST request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an existing device ID using MockMvc. The test asserts that the HTTP status of
     * the response is 400 (BAD_REQUEST), indicating that the sensor was not successfully added.
     */
    @Test
    void testAddSensorShouldReturnHttpStatusUnprocessableEntityWhenSensorIsNotAdded() throws Exception {
        // Arrange
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        SensorDTO sensorDTO = sensorMapper.toSensorDTO(sensor);
        String sensorJson = objectMapper.writeValueAsString(sensorDTO);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(null);

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(uri+"/device/{deviceId}", deviceId1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sensorJson))
                .andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "When a new sensor is not added, the status code should be 422 (UNPROCESSABLE_ENTITY)");
    }

    /**
     * This test verifies that the addSensor method in the SensorRESTController class returns a UNPROCESSABLE_ENTITY
     * (422)
     * status when an invalid sensor model name is provided. The test mocks the behavior of the SensorRepository to
     * return
     * null when a new sensor is attempted to be added. It then sends a POST request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an existing device ID using MockMvc. The test asserts that
     * the HTTP status of
     * the response is 422 (UNPROCESSABLE_ENTITY), indicating that the sensor was not successfully added due to an
     * invalid sensor model name.
     */
    @Test
    void testAddSensorShouldReturnHttpStatusUnprocessableEntityWhenInvalidSensorModelName() throws Exception {
        // Arrange
        String addSensorToDeviceUri = "/sensors/device/{deviceId}";
        String invalidSensorModelName = " ";
        SensorDTO sensorDTO = new SensorDTO(sensorID, deviceId1, invalidSensorModelName, "0");
        String sensorJson = objectMapper.writeValueAsString(sensorDTO);

        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(addSensorToDeviceUri, deviceId1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sensorJson))
                .andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "When an invalid sensor model name is provided, the status code should be 422 (UNPROCESSABLE_ENTITY)");

    }

    /**
     * This test verifies that the addSensor method in the SensorRESTController class returns a UNPROCESSABLE_ENTITY
     * (422)
     * status when an invalid device ID is provided. The test mocks the behavior of the SensorRepository to return
     * null when a new sensor is attempted to be added. It then sends a POST request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an invalid device ID using MockMvc. The test asserts that
     * the HTTP status of
     * the response is 422 (UNPROCESSABLE_ENTITY), indicating that the sensor was not successfully added due to an
     * invalid device ID.
     */
    @Test
    void testAddSensorShouldReturnHttpStatusUnprocessableEntityWhenInvalidDeviceId() throws Exception {
        // Arrange
        String addSensorToDeviceUri = "/sensors/device/{deviceId}";
        String invalidDeviceId = "InvalidDeviceId";
        SensorDTO sensorDTO = new SensorDTO(sensorID, invalidDeviceId, sensorModelName, "0");
        String sensorJson = objectMapper.writeValueAsString(sensorDTO);

        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(addSensorToDeviceUri, invalidDeviceId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sensorJson))
                .andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "When an invalid device ID is provided, the status code should be 422 (UNPROCESSABLE_ENTITY)");

    }

    /**
     * This test verifies that the addSensor method in the SensorRESTController class returns a UNPROCESSABLE_ENTITY
     * (422)
     * status when an invalid sensor device ID is provided. The test mocks the behavior of the SensorRepository to
     * return
     * null when a new sensor is attempted to be added. It then sends a POST request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an invalid sensor device ID using MockMvc. The test asserts
     * that the HTTP status of
     * the response is 422 (UNPROCESSABLE_ENTITY), indicating that the sensor was not successfully added due to an
     * invalid sensor device ID.
     */
    @Test
    void testAddSensorShouldReturnHttpStatusUnprocessableEntityWhenInvalidSensorDeviceId() throws Exception {
        // Arrange
        String addSensorToDeviceUri = "/sensors/device/{deviceId}";
        SensorDTO sensorDTO = new SensorDTO(sensorModelName);
        String sensorJson = objectMapper.writeValueAsString(sensorDTO);
        when((deviceRepository.containsIdentity(new DeviceId(deviceId1)))).thenReturn(false);

        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;

        // Act
        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders.post(addSensorToDeviceUri, deviceId1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(sensorJson))
                .andReturn();
        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(),
                "When an invalid sensor device ID is provided, the status code should be 422 (UNPROCESSABLE_ENTITY)");


    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns an OK
     * (200)
     * status when a valid device ID is provided. The test mocks the behavior of the SensorRepository to return
     * a list of sensor IDs when a valid device ID is provided. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is a valid device ID. The test asserts that the HTTP status of
     * the response is 200 (OK), indicating that the sensor IDs were successfully retrieved.
     */
    @Test
    void testGetSensorByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceIdIsValis() {
        //Arrange
        String deviceId = "deviceId";
        SensorId sensorId = new SensorId(sensorID);
        DeviceId deviceIdentity = new DeviceId(deviceId);
        when(deviceRepository.containsIdentity((new DeviceId(deviceId)))).thenReturn(true);
        when(sensorRepository.findSensorIdsByDeviceId(deviceIdentity)).thenReturn(List.of(sensorId));
        HttpStatus expected = HttpStatus.OK;
        //Act
        ResponseEntity<List<SensorIdDTO>> result = sensorRESTController.getSensorByDeviceIdentity(deviceId);
        //Assert
        HttpStatus actual = (HttpStatus) result.getStatusCode();
        assertEquals(expected, actual, "The status code should be 200");
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns a
     * BAD_REQUEST (400)
     * status when an invalid device ID is provided. The test mocks the behavior of the SensorRepository to return
     * null when an invalid device ID is provided. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an invalid device ID. The test asserts that the HTTP status of
     * the response is 400 (BAD_REQUEST), indicating that the sensor IDs were not successfully retrieved due to an
     * invalid device ID.
     */
    @Test
    void testGetSensorByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceIdDoesNotExist() {
        //Arrange
        String deviceId = "deviceId";
        when(deviceRepository.containsIdentity((new DeviceId(deviceId)))).thenReturn(false);
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        //Act
        ResponseEntity<List<SensorIdDTO>> result = sensorRESTController.getSensorByDeviceIdentity(deviceId);
        //Assert
        HttpStatus actual = (HttpStatus) result.getStatusCode();
        assertEquals(expected, actual, "The status code should be 400"); // Update the message here
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns a
     * UNPROCESSABLE_ENTITY (422)
     * status when an invalid device ID is provided. The test mocks the behavior of the SensorRepository to return
     * null when an invalid device ID is provided. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an invalid device ID. The test asserts that the HTTP status of
     * the response is 422 (UNPROCESSABLE_ENTITY), indicating that the sensor IDs were not successfully retrieved due
     * to an invalid device ID.
     */
    @Test
    void testGetSensorByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceIdIsInvalid() {
        //Arrange
        String deviceId = " ";
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        //Act
        ResponseEntity<List<SensorIdDTO>> result = sensorRESTController.getSensorByDeviceIdentity(deviceId);
        //Assert
        HttpStatus actual = (HttpStatus) result.getStatusCode();
        assertEquals(expected, actual, "The status code should be 422");
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns a
     * NOT_FOUND (404)
     * status when a device does not contain any sensors. The test mocks the behavior of the SensorRepository to return
     * an empty list when a device does not contain any sensors. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is a device ID that does not contain any sensors. The test
     * asserts that the HTTP status of
     * the response is 404 (NOT_FOUND), indicating that no sensor IDs were found for the provided device ID.
     */
    @Test
    void testGeSensorByDeviceIdentityReturnsCorrectHTTPStatusWhenDeviceDOesNotContainSensors() {
        //Arrange
        String deviceId = "deviceId";
        when(deviceRepository.containsIdentity((new DeviceId(deviceId)))).thenReturn(true);
        when(sensorRepository.findSensorIdsByDeviceId(new DeviceId(deviceId))).thenReturn(List.of());
        HttpStatus expected = HttpStatus.NOT_FOUND;
        //Act
        ResponseEntity<List<SensorIdDTO>> result = sensorRESTController.getSensorByDeviceIdentity(deviceId);
        //Assert
        HttpStatus actual = (HttpStatus) result.getStatusCode();
        assertEquals(expected, actual, "The status code should be 404");
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns an OK
     * (200)
     * status and the correct response body when a valid device ID is provided. The test mocks the behavior of the
     * SensorRepository to return
     * a list of sensor IDs when a valid device ID is provided. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is a valid device ID. The test asserts that the HTTP status of
     * the response is 200 (OK) and that the response body matches the expected list of SensorIdDTOs.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceIdIsValid() throws Exception {
        //Arrange
        String deviceId = "deviceId";
        SensorId sensorId = new SensorId(sensorID);
        DeviceId deviceIdentity = new DeviceId(deviceId);
        uri += "/device/" + deviceId;
        when(deviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(sensorRepository.findSensorIdsByDeviceId(deviceIdentity)).thenReturn(List.of(sensorId));
        int expected = HttpStatus.OK.value();
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        //Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The status code should be 200");
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns a
     * BAD_REQUEST (400)
     * status when the device ID does not exist in the repository. The test mocks the behavior of the
     * DeviceRepository to return
     * false when the device ID does not exist. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is a non-existing device ID. The test asserts that the HTTP
     * status of
     * the response is 400 (BAD_REQUEST), indicating that the sensor IDs were not successfully retrieved due to a
     * non-existing device ID.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceIdDoesNotExist() throws Exception {
        //Arrange
        String deviceId = "deviceId";
        uri += "/device/" + deviceId;
        when(deviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(false);
        int expected = HttpStatus.BAD_REQUEST.value();
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        //Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The status code should be 400");
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns a
     * UNPROCESSABLE_ENTITY (422)
     * status when the device ID is invalid. The test sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is an invalid device ID. The test asserts that the HTTP status of
     * the response is 422 (UNPROCESSABLE_ENTITY), indicating that the sensor IDs were not successfully retrieved due
     * to an invalid device ID.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceIdIsInvalid() throws Exception {
        //Arrange
        String deviceId = " ";
        uri += "/device/" + deviceId;
        int expected = HttpStatus.UNPROCESSABLE_ENTITY.value();
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        //Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The status code should be 422");
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns a
     * NOT_FOUND (404)
     * status when a device does not contain any sensors. The test mocks the behavior of the SensorRepository to return
     * an empty list when a device does not contain any sensors. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is a device ID that does not contain any sensors. The test
     * asserts that the HTTP status of
     * the response is 404 (NOT_FOUND), indicating that no sensor IDs were found for the provided device ID.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectHTTPStatusWhenDeviceDoesNotContainSensors() throws Exception {
        //Arrange
        String deviceId = "deviceId";
        uri += "/device/" + deviceId;
        when(deviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(sensorRepository.findSensorIdsByDeviceId(new DeviceId(deviceId))).thenReturn(List.of());
        int expected = HttpStatus.NOT_FOUND.value();
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        //Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The status code should be 404");
    }

    /**
     * This test verifies that the getSensorByDeviceIdentity method in the SensorRESTController class returns an OK
     * (200)
     * status and the correct response body when a valid device ID is provided. The test mocks the behavior of the
     * SensorRepository to return
     * a list of sensor IDs when a valid device ID is provided. It then sends a GET request to the endpoint
     * `/sensors/device/{deviceId}` where `{deviceId}` is a valid device ID. The test asserts that the HTTP status of
     * the response is 200 (OK) and that the response body matches the expected list of SensorIdDTOs.
     */
    @Test
    void testGetByDeviceIdentityWithMockMvcReturnsCorrectResponseBodyWhenDeviceIdIsValid() throws Exception {
        //Arrange
        String deviceId = "deviceId";
        SensorId sensorId = new SensorId(sensorID);
        DeviceId deviceIdentity = new DeviceId(deviceId);
        uri += "/device/" + deviceId;
        when(deviceRepository.containsIdentity(new DeviceId(deviceId))).thenReturn(true);
        when(sensorRepository.findSensorIdsByDeviceId(deviceIdentity)).thenReturn(List.of(sensorId));
        SensorIdDTO sensorIdDTO = new SensorIdDTO(sensorID);
        sensorIdDTO.add(Link.of("http://localhost/sensors/" + sensorID).withSelfRel()); // Add the "self" link
        List<SensorIdDTO> sensorIdDTOList = List.of(sensorIdDTO);
        String expected = objectMapper.writeValueAsString(sensorIdDTOList);
        //Act
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        //Assert
        String actual = result.getResponse().getContentAsString();
        assertEquals(expected, actual, "The response body should match the expected SensorIdDTO list");
    }
}