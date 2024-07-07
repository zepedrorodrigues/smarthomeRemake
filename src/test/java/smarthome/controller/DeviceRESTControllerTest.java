package smarthome.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.room.Room;
import smarthome.domain.room.RoomFactory;
import smarthome.domain.room.RoomFactoryImpl;
import smarthome.domain.room.vo.Dimensions;
import smarthome.domain.room.vo.Floor;
import smarthome.domain.room.vo.Height;
import smarthome.domain.room.vo.Length;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.room.vo.RoomName;
import smarthome.domain.room.vo.Width;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.SensorModelFactoryImpl;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactory;
import smarthome.domain.sensortype.SensorTypeFactoryImpl;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceIdDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.DeviceTypeMapper;
import smarthome.service.IDeviceService;
import smarthome.service.impl.DeviceServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class provides unit tests for the DeviceRESTController class,
 * which handles REST requests for device functionalities in a smart home system.
 * It tests the methods for adding a device to a room and retrieving devices in a room.
 * The tests are performed using Mockito to mock the repositories used by the controller.
 * The tests are performed using MockMvc to simulate HTTP requests and responses.
 * The tests are performed using ObjectMapper to convert objects to JSON and vice versa.    §
 */
class DeviceRESTControllerTest {

    DeviceRESTController deviceRESTController;

    // Dependencies for the Device Service
    IDeviceRepository mockDeviceRepo;
    ISensorRepository mockSensorRepo;
    ISensorModelRepository mockSensorModelRepo;
    ISensorTypeRepository mockSensorTypeRepo;
    DeviceFactory deviceFactory;
    IRoomRepository mockRoomRepo;
    IDeviceTypeRepository mockDeviceTypeRepo;

    // Dependencies for the Device Controller
    DeviceMapper deviceMapper;
    DeviceTypeMapper deviceTypeMapper;
    IDeviceService deviceService;

    // Device variables
    Device device1;
    Device device2;
    Device deviceInChosenRoom;
    Device deviceInChosenRoomFromRepository1;
    DeviceName deviceName1;
    DeviceName deviceName2;
    DeviceTypeName deviceTypeName;
    DeviceId deviceId1;
    DeviceStatus deviceStatus;
    RoomId roomId1;
    RoomId roomId2;
    DeviceDTO deviceDTO;
    String deviceNameStr;
    String deviceTypeNameStr;
    String roomIdStr;

    // Room variables
    Room room1;
    Room room2;
    RoomName roomName1;
    RoomName roomName2;
    HouseName ourHouse;
    Floor floor;
    Width width;
    Length length;
    Height height;
    Dimensions dimensions;
    RoomId roomIdAsGiven;

    // Sensor variables
    SensorTypeName sensorTypeName1;
    SensorTypeName sensorTypeName2;
    SensorTypeUnit sensorTypeUnit1;
    SensorTypeUnit sensorTypeUnit2;
    SensorType temperatureSensorType;
    SensorType humiditySensorType;
    SensorModelName sensorModelName1;
    SensorModelName sensorModelName2;
    SensorTypeId sensorTypeId1;
    SensorTypeId sensorTypeId2;
    SensorModel sensorModel1;
    SensorModel sensorModel2;

    // Factories
    RoomFactory roomFactory;
    SensorTypeFactory sensorTypeFactory;
    SensorModelFactoryImpl sensorModelFactory;
    SensorFactoryImpl sensorFactory;

    // URI and MockMvc
    String uri;
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    /**
     * Set up the test environment and initialize the mocks and objects used in the tests.
     */
    @BeforeEach
    void setUp() {
        // Mock Repositories
        mockRoomRepo = mock(IRoomRepository.class);
        mockDeviceRepo = mock(IDeviceRepository.class);
        mockSensorTypeRepo = mock(ISensorTypeRepository.class);
        mockSensorModelRepo = mock(ISensorModelRepository.class);
        mockSensorRepo = mock(ISensorRepository.class);
        mockDeviceTypeRepo = mock(IDeviceTypeRepository.class);

        // Initialize Factories
        deviceFactory = new DeviceFactoryImpl();
        sensorTypeFactory = new SensorTypeFactoryImpl();
        sensorModelFactory = new SensorModelFactoryImpl();
        sensorFactory = new SensorFactoryImpl();

        //Set up 2 Rooms
        roomName1 = new RoomName("1");
        roomName2 = new RoomName("2");
        ourHouse = new HouseName("Sweet Home");
        floor = new Floor(1);
        width = new Width(5);
        length = new Length(4);
        height = new Height(3);
        dimensions = new Dimensions(width, height, length);
        roomFactory = new RoomFactoryImpl();
        room1 = roomFactory.createRoom(roomName1, ourHouse, floor, dimensions);
        room2 = roomFactory.createRoom(roomName2, ourHouse, floor, dimensions);
        when(mockRoomRepo.findByIdentity(room1.getIdentity())).thenReturn(Optional.of(room1));
        when(mockRoomRepo.findByIdentity(room2.getIdentity())).thenReturn(Optional.of(room2));

        //Set up DeviceDTO received from the exterior
        deviceNameStr = "deviceName";
        deviceTypeNameStr = "deviceTypeName";
        roomIdStr = "roomId";
        deviceDTO = new DeviceDTO(deviceNameStr, deviceTypeNameStr);

        //Set up 2 Devices One in each room
        deviceName1 = new DeviceName(deviceNameStr);
        deviceName2 = new DeviceName("device2");
        deviceTypeName = new DeviceTypeName(deviceTypeNameStr);
        roomId1 = room1.getIdentity();
        roomId2 = room2.getIdentity();
        device1 = deviceFactory.createDevice(deviceName1, deviceTypeName, roomId1);
        device2 = deviceFactory.createDevice(deviceName2, deviceTypeName, roomId2);
        when(mockDeviceRepo.findByIdentity(device1.getIdentity())).thenReturn(Optional.of(device1));
        when(mockDeviceRepo.findByIdentity(device2.getIdentity())).thenReturn(Optional.of(device2));
        when(mockDeviceRepo.findAll()).thenReturn(List.of(device1, device2));

        // Set up a Room chosen from a list of rooms in the house
        roomIdAsGiven = new RoomId(roomIdStr);

        // Create a Device in the chosen room using the DeviceFactory
        deviceInChosenRoom = deviceFactory.createDevice(deviceName1, deviceTypeName, roomIdAsGiven);
        deviceStatus = new DeviceStatus(true);

        // Set up 2 devices in the same room retrieved from the repository
        deviceId1 = new DeviceId("deviceId1");
        deviceInChosenRoomFromRepository1 = deviceFactory.createDevice(deviceId1, deviceName1, deviceTypeName, roomIdAsGiven, deviceStatus);

        // Set up sensor types and models
        sensorTypeName1 = new SensorTypeName("Temperature");
        sensorTypeName2 = new SensorTypeName("Humidity");
        sensorTypeUnit1 = new SensorTypeUnit("Celsius");
        sensorTypeUnit2 = new SensorTypeUnit("%");
        temperatureSensorType = sensorTypeFactory.createSensorType(sensorTypeName1, sensorTypeUnit1);
        humiditySensorType = sensorTypeFactory.createSensorType(sensorTypeName2, sensorTypeUnit2);
        when(mockSensorTypeRepo.findByIdentity(temperatureSensorType.getIdentity())).thenReturn(Optional.of(temperatureSensorType));
        when(mockSensorTypeRepo.findByIdentity(humiditySensorType.getIdentity())).thenReturn(Optional.of(humiditySensorType));
        when(mockSensorTypeRepo.findAll()).thenReturn(List.of(temperatureSensorType, humiditySensorType));

        //Set up Available SensorModels
        sensorModelName1 = new SensorModelName("SensorOfTemperature");
        sensorModelName2 = new SensorModelName("SensorOfHumidity");
        sensorTypeId1 = temperatureSensorType.getIdentity();
        sensorTypeId2 = humiditySensorType.getIdentity();
        sensorModel1 = sensorModelFactory.createSensorModel(sensorModelName1, sensorTypeId1);
        sensorModel2 = sensorModelFactory.createSensorModel(sensorModelName2, sensorTypeId2);
        when(mockSensorModelRepo.findByIdentity(sensorModel1.getIdentity())).thenReturn(Optional.of(sensorModel1));
        when(mockSensorModelRepo.findByIdentity(sensorModel2.getIdentity())).thenReturn(Optional.of(sensorModel2));

        // Initialize Device Controller's dependencies
        deviceMapper = new DeviceMapper();
        deviceTypeMapper = new DeviceTypeMapper();
        deviceService = new DeviceServiceImpl(mockRoomRepo, deviceFactory, mockDeviceRepo,
                mockSensorTypeRepo, mockSensorModelRepo, mockSensorRepo, mockDeviceTypeRepo);

        // Initialize Device Controller
        deviceRESTController = new DeviceRESTController(deviceService, deviceMapper, deviceTypeMapper);

        // Set up the URI and MockMvc
        uri = "/devices";
        mockMvc = MockMvcBuilders.standaloneSetup(deviceRESTController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Test the constructor of the DeviceRESTController class, of class DeviceRESTController. It should not return a
     * null object.
     */
    @Test
    void testConstructorShouldNotBeNull() {
        // Act & Assert
        assertNotNull(deviceRESTController, "The controller should not be null");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns correct HTTP status when the device is successfully created and
     * saved The test passes if the method returns a response entity with the newly added device data and HTTP status
     * CREATED
     */
    @Test
    void testAddDeviceToRoomCorrectlySavesDeviceShouldReturnCreatedStatus() {
        //Arrange
        when(mockDeviceRepo.save(any(Device.class))).thenAnswer(invocation -> invocation.<Device>getArgument(0));
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceTypeRepo.containsIdentity(new DeviceTypeName(deviceTypeNameStr))).thenReturn(true);
        HttpStatus expected = HttpStatus.CREATED;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a successful device creation should be CREATED");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts if the method returns the correct response body when the device is successfully created and
     * saved The test passes if the method returns a response entity with the newly added device data
     */
    @Test
    void testAddDeviceToRoomCorrectlySavesDeviceShouldReturnDeviceDTO() {
        //Arrange
        when(mockDeviceRepo.save(any(Device.class))).thenAnswer(invocation -> invocation.<Device>getArgument(0));
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceTypeRepo.containsIdentity(new DeviceTypeName(deviceTypeNameStr))).thenReturn(true);
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        DeviceDTO responseBody = Objects.requireNonNull(result.getBody());
        assertTrue(responseBody.getDeviceName().equals(deviceNameStr) &&
                   responseBody.getDeviceTypeName().equals(deviceTypeNameStr) &&
                   responseBody.getRoomId().equals(roomIdStr) &&
                   responseBody.getDeviceId() != null && responseBody.getDeviceStatus(),
                "The response body should contain the newly added device data");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts if the method returns the correct HTTP status when the room ID is null. The test passes if the
     * method returns a response entity with HTTP status UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToRoomNullRoomIdShouldReturnUnprocessableEntityStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        // Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(null, deviceDTO);
        // Assert
        HttpStatusCode actual = result.getStatusCode();
        assertEquals(expected, actual, "The HTTP status for a null room ID should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns the correct HTTP status when the room ID is blank. The test passes if the
     * method returns a response entity with HTTP status UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToRoomBlankRoomIdShouldReturnUnprocessableEntityStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        roomIdStr = " ";
        // Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        // Assert
        HttpStatusCode actual = result.getStatusCode();
        assertEquals(expected, actual, "The HTTP status for a blank room ID should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns correct HTTP status when the
     * deviceDTO is null The test passes if the method returns a response entity with HTTP status BAD_REQUEST
     */
    @Test
    void testAddDeviceToRoomNullDeviceDTOShouldReturnBadRequestStatus() {
        // Arrange
        HttpStatus expected = HttpStatus.BAD_REQUEST;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, null);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a null deviceDTO should be BAD_REQUEST");
    }

    /**
     * Test for the addDeviceToRoom method. This test asserts that the method returns correct HTTP status when the
     * device name is null The test passes if the method returns a response entity with HTTP status
     * UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToRoomNullDeviceNameShouldReturnUnprocessableEntityStatus() {
        //Arrange
        deviceDTO = new DeviceDTO(null, deviceTypeNameStr);
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a null device name should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method. This test asserts that the method returns correct HTTP status when the
     * device name is blank The test passes if the method returns a response entity with HTTP status
     * UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToRoomBlankDeviceNameShouldReturnUnprocessableEntityStatus() {
        //Arrange
        deviceDTO = new DeviceDTO(" ", deviceTypeNameStr);
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a blank device name should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method. This test asserts that the method returns correct HTTP status when the
     * device type is null The test passes if the method returns a response entity with HTTP status
     * UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToRoomNullDeviceTypeShouldReturnUnprocessableEntityStatus() {
        //Arrange
        deviceDTO = new DeviceDTO(deviceNameStr, null);
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a null device type should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method. This test asserts that the method returns correct HTTP status when the
     * device type is blank The test passes if the method returns a response entity with HTTP status
     * UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToRoomBlankDeviceTypeShouldReturnUnprocessableEntityStatus() {
        //Arrange
        deviceDTO = new DeviceDTO(deviceNameStr, " ");
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a blank device type should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method. This test asserts that the method returns correct HTTP status when the room
     * id is not found The test passes if the method returns a response entity with HTTP status UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToRoomRoomIdNotFoundShouldReturnUnprocessableEntityErrorStatus() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(false);
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a room id not found should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method. This test asserts that the method returns correct HTTP status when the
     * device type name is not found The test passes if the method returns a response entity with HTTP status
     * UNPROCESSABLE_ENTITY
     */
    @Test
    void testAddDeviceToDeviceTypeNameNotFoundShouldReturnUnprocessableEntityErrorStatus() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceTypeRepo.containsIdentity(new DeviceTypeName(deviceTypeNameStr))).thenReturn(false);
        HttpStatus expected = HttpStatus.UNPROCESSABLE_ENTITY;
        //Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.addDeviceToRoom(roomIdStr, deviceDTO);
        //Assert
        HttpStatusCode resultStatusCode = result.getStatusCode();
        assertEquals(expected, resultStatusCode,
                "The HTTP status for a room id not found should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method correctly handles a post-request with valid JSON. The test passes if a POST
     * request to /devices with valid JSON results in HTTP status 201 Created.
     */
    @Test
    void testAddDeviceToRoomCorrectlyHandlesPostRequestWithValidJSON() throws Exception {
        //Arrange
        uri += "/room/" + roomIdStr;
        String dtoJSON = objectMapper.writeValueAsString(deviceDTO);
        when(mockDeviceRepo.save(any(Device.class))).thenAnswer(invocation -> invocation.<Room>getArgument(0));
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceTypeRepo.containsIdentity(new DeviceTypeName(deviceTypeNameStr))).thenReturn(true);
        int expected = HttpStatus.CREATED.value();
        //Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJSON)).andReturn();
        //Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual,
                "The HTTP status for a successful post request and correct device creation should be CREATED.");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts if the method correctly handles a post-request with valid JSON. The test passes if a POST
     * request to /devices with valid JSON results in a response body with the newly added device data.
     */
    @Test
    void testAddDeviceCorrectlyHandlesAPostRequestWithValidJSONDtaAndReturnCorrectResponseBody() throws Exception {
        //Arrange
        uri += "/room/" + roomIdStr;
        deviceDTO = new DeviceDTO(deviceNameStr, deviceTypeNameStr);
        String dtoJSON = objectMapper.writeValueAsString(deviceDTO);
        Device device = deviceFactory.createDevice(new DeviceName(deviceNameStr), new DeviceTypeName(deviceTypeNameStr),
                new RoomId(roomIdStr));
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceTypeRepo.containsIdentity(new DeviceTypeName(deviceTypeNameStr))).thenReturn(true);
        when(mockDeviceRepo.save(any(Device.class))).thenReturn(device);
        DeviceDTO expectedDeviceDTO = deviceMapper.toDeviceDTO(device);
        expectedDeviceDTO.add(Link.of("http://localhost/devices/" + expectedDeviceDTO.getDeviceId()));
        expectedDeviceDTO.add(Link.of("http://localhost/devices/" + expectedDeviceDTO.getDeviceId() + "/deactivate").withRel("deactivate"));
        String expected = objectMapper.writeValueAsString(expectedDeviceDTO);

        // Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJSON)).andReturn();

        // Assert
        String actual = result.getResponse().getContentAsString();
        assertEquals(expected, actual, "The response body should contain the newly added device data");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method correctly handles a post-request with invalid JSON (invalid device name).
     * The test passes if a POST request to /devices with invalid JSON results in HTTP status UNPROCESSABLE_ENTITY.
     */
    @Test
    void testAddDeviceCorrectlyHandlesAPostRequestWithInvalidJSONDataAndReturnsUnprocessableEntityStatus() throws Exception {
        //Arrange
        uri += "/room/" + roomIdStr;
        String invalidDeviceName = " ";
        DeviceDTO deviceDTO= new DeviceDTO(invalidDeviceName, deviceTypeNameStr);
        String dtoAsJSON = objectMapper.writeValueAsString(deviceDTO);

        int expected = HttpStatus.UNPROCESSABLE_ENTITY.value();

        // Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status for an invalid JSON should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method correctly handles a post-request with invalid JSON (room ID not found). The
     * test
     * passes if a POST request to /devices with invalid JSON results in HTTP status UNPROCESSABLE_ENTITY.
     */
    @Test
    void testAddDeviceCorrectlyHandlesAPostRequestWithInvalidJSONDataAndReturnsUnprocessableEntityStatusForRoomIdNotFound() throws Exception {
        //Arrange
        uri += "/room/" + roomIdStr;
        String dtoAsJSON = objectMapper.writeValueAsString(deviceDTO);
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(false);
        int expected = HttpStatus.UNPROCESSABLE_ENTITY.value();

        // Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status for an invalid JSON should be UNPROCESSABLE_ENTITY");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method correctly handles a post-request with invalid JSON (null device DTO). The test
     * passes if a POST request to /devices with invalid JSON results in HTTP status BAD_REQUEST.
     */
    @Test
    void testAddDeviceCorrectlyHandlesAPostRequestWithInvalidJSONDataAndReturnsBadRequestStatusForNullDeviceDTO() throws Exception {
        //Arrange
        uri += "/room/" + roomIdStr;
        String dtoAsJSON = objectMapper.writeValueAsString(null);
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status for an invalid JSON should be BAD_REQUEST");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns the correct HTTP status when the room id is null.
     * The test passes if the method returns a response entity with HTTP status BAD_REQUEST.
     */
    @Test
    void testGetDevicesInRoomWhenRoomIdIsBlankShouldReturnBadRequestStatus() {
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(" ");
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),
                "The HTTP status for a blank room ID should be BAD_REQUEST");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns the correct HTTP status when the room id is empty.
     * The test passes if the method returns a response entity with HTTP status BAD_REQUEST.
     */
    @Test
    void testGetDevicesInRoomWhenRoomIdIsEmptyShouldReturnBadRequestStatus() {
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom("");
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),
                "The HTTP status for a room ID empty should be BAD_REQUEST");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns the correct HTTP status when the room does not exist.
     * The test passes if the method returns a response entity with HTTP status BAD_REQUEST.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDoesNotExistShouldReturnBadRequestStatus() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(false);
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode(),
                "The HTTP status for a room that does not exist should be BAD_REQUEST");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns the correct HTTP status when the room exists but contains no devices.
     * The test passes if the method returns a response entity with HTTP status
     * NOT_FOUND.
     */
    @Test
    void testGetDevicesInRoomWhenRoomExistsButContainsNoDevicesShouldReturnNotFoundStatus() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(new ArrayList<>());
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);
        //Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode(),
                "The HTTP status for a room that exists but contains no devices should be NOT_FOUND");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns the correct HTTP status when the room exists and contains devices.
     * The test passes if the method returns a response entity with HTTP status OK.
     */
    @Test
    void testGetDevicesInRoomWhenThereAreOtherRoomsWithDevicesButNoDevicesInChosenRoomShouldReturnNotFoundStatus() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(new ArrayList<>());
        when(mockDeviceRepo.findDeviceIdsByRoomId(roomId1)).thenReturn(List.of(device1.getIdentity()));
        when(mockDeviceRepo.findDeviceIdsByRoomId(roomId2)).thenReturn(List.of(device2.getIdentity()));
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);
        //Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode(),
                "The HTTP status for a room that exists but contains no devices should be NOT_FOUND");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns the correct HTTP status when the room exists and contains devices.
     * The test passes if the method returns a response entity with HTTP status OK.
     */
    @Test
    void testGetDevicesInRoomWhenRoomExistsAndContainsDevicesShouldReturnOKStatus() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);
        //Assert
        assertEquals(HttpStatus.OK, result.getStatusCode(),
                "The HTTP status for a room that exists and contains devices should be OK");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns a list of DeviceDTO objects when the room exists and contains devices.
     * The test passes if the method returns a response entity with a list of DeviceIdDTO objects.
     */
    @Test
    void testGetDevicesInRoomWhenRoomExistsAndContainsDevicesShouldReturnListOfDeviceIdDTOs() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);
        //Assert
        List<DeviceIdDTO> responseBody = Objects.requireNonNull(result.getBody());
        assertFalse(responseBody.isEmpty(), "The response body should contain a list of DeviceIdDTO objects");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns a list of DeviceDTO objects with the correct device objects.
     * The test passes if the method returns a response entity with a list of DeviceIdDTO
     * objects with the correct device objects.
     */
    @Test
    void testGetDevicesInRoomWhenThereIsOneDeviceInRoomShouldReturnAListWithTheCorrectDeviceIdObject() {
        // Arrange
        // Create a valid DeviceId
        DeviceId deviceId = new DeviceId("valid_device_id");

        // Create a device using the factory with the valid DeviceId
        deviceInChosenRoom = deviceFactory.createDevice(deviceId, deviceName1, deviceTypeName, roomIdAsGiven,
                deviceStatus);

        // Mock repository behaviour
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(List.of(deviceInChosenRoom.getIdentity()));

        // Ensure that the DeviceRepository returns the created Device when queried with the valid DeviceId
        when(mockDeviceRepo.findByIdentity(deviceId)).thenReturn(Optional.of(deviceInChosenRoom));

        // Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);

        // Assert
        List<DeviceIdDTO> responseBody = Objects.requireNonNull(result.getBody());
        DeviceIdDTO deviceIdDTO = responseBody.get(0);
        assertEquals(deviceId.getIdentity(), deviceIdDTO.getDeviceId(),
                "The response body should contain the correct DeviceIdDTO object");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns a list of DeviceDTO objects with multiple device objects.
     * The test passes if the method returns a response entity with a list of DeviceDTO objects
     * with multiple device objects.
     */
    @Test
    void testGetDevicesInRoomWhenMultipleDevicesInRoomShouldReturnAListWithMultipleDeviceIds() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);
        //Assert
        List<DeviceIdDTO> responseBody = Objects.requireNonNull(result.getBody());
        assertEquals(2, responseBody.size(), "The response body should contain a list with two DeviceIdDTO objects");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns a list of DeviceIdDTO objects with
     * multiple device objects. The test passes if the method returns a response entity with a list of
     * DeviceIdDTO objects with multiple device objects.
     */
    @Test
    void testGetDevicesInRoomWhenMultipleDevicesInRoomShouldReturnAListWithMultipleDevicesWithCorrectDeviceIdObjects() {
        //Arrange
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));
        //Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);
        //Assert
        List<DeviceIdDTO> responseBody = Objects.requireNonNull(result.getBody());
        DeviceIdDTO deviceIdDTO1 = responseBody.get(0);
        DeviceIdDTO deviceIdDTO2 = responseBody.get(1);
        assertEquals(device1.getIdentity().getIdentity(), deviceIdDTO1.getDeviceId(),
                "The response body should contain the correct DeviceIdDTO object for device1");
        assertEquals(device2.getIdentity().getIdentity(), deviceIdDTO2.getDeviceId(),
                "The response body should contain the correct DeviceIdDTO object for device2");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns a list of DeviceIdDTO objects with
     * the correct device object. The test passes if the method returns a response entity with a list of
     * DeviceIdDTO objects with the correct device object.
     */
    @Test
    void testGetDevicesInRoomWhenThereIsOneDeviceInChosenRoomAndOtherDevicesInOtherRoomsShouldReturnCorrectList() {
        //Arrange
        // Create a valid DeviceId
        DeviceId deviceId = new DeviceId("valid_device_id");

        // Create a device using the factory with the valid DeviceId
        deviceInChosenRoom = deviceFactory.createDevice(deviceId, deviceName1, deviceTypeName, roomIdAsGiven,
                deviceStatus);

        // Mock repository behaviour
        when(mockRoomRepo.containsIdentity(new RoomId(roomIdStr))).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(new RoomId(roomIdStr))).thenReturn(List.of(deviceInChosenRoom.getIdentity()));

        // Ensure that the DeviceRepository returns the created Device when queried with the valid DeviceId
        when(mockDeviceRepo.findByIdentity(deviceId)).thenReturn(Optional.of(deviceInChosenRoom));

        // Create valid DeviceId objects for other devices
        DeviceId otherDeviceId1 = new DeviceId("device_id_1");
        DeviceId otherDeviceId2 = new DeviceId("device_id_2");

        // Create devices for each room with valid DeviceIds
        Device otherDevice1 = deviceFactory.createDevice(otherDeviceId1, deviceName1, deviceTypeName, roomId1,
                deviceStatus);
        Device otherDevice2 = deviceFactory.createDevice(otherDeviceId2, deviceName2, deviceTypeName, roomId2,
                deviceStatus);

        // Mock repository behavior to return devices for other rooms
        when(mockDeviceRepo.findDeviceIdsByRoomId(roomId1)).thenReturn(List.of(otherDevice1.getIdentity()));
        when(mockDeviceRepo.findDeviceIdsByRoomId(roomId2)).thenReturn(List.of(otherDevice2.getIdentity()));

        // Ensure that the DeviceRepository returns the created Device when queried with the valid DeviceId
        when(mockDeviceRepo.findByIdentity(deviceInChosenRoom.getIdentity())).thenReturn(Optional.of(deviceInChosenRoom));
        when(mockDeviceRepo.findByIdentity(device1.getIdentity())).thenReturn(Optional.of(device1));
        when(mockDeviceRepo.findByIdentity(device2.getIdentity())).thenReturn(Optional.of(device2));

        // Act
        ResponseEntity<List<DeviceIdDTO>> result = deviceRESTController.getDevicesInRoom(roomIdStr);

        // Assert
        List<DeviceIdDTO> responseBody = Objects.requireNonNull(result.getBody());
        assertEquals(1, responseBody.size(), "The response body should contain a list with one DeviceIdDTO object");
    }

    @Test
    void testGetDevicesInRoomWithMockMVCWhenRoomIdIsBlankShouldReturnBadRequestStatus() throws Exception {
        String blankRoomIdStr = " ";
        uri += "/room/" + blankRoomIdStr;
        String dtoAsJSON = objectMapper.writeValueAsString(blankRoomIdStr);
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        // Assert
        int actual = result.getResponse().getStatus();
        assertEquals(expected, actual, "The HTTP status for a blank room ID should be BAD_REQUEST");
    }

    @Test
    void testGetDevicesInRoomWithMockMVCWhenRoomDoesNotExistShouldReturnBadRequestStatus() throws Exception {
        // Arrange
        String nonExistentRoomIdStr = "nonExistentRoomId";
        when(mockRoomRepo.containsIdentity(new RoomId(nonExistentRoomIdStr))).thenReturn(false);
        uri += "/room/" + nonExistentRoomIdStr;
        String dtoAsJSON = objectMapper.writeValueAsString(nonExistentRoomIdStr);
        HttpStatus expected = HttpStatus.BAD_REQUEST;

        // Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The HTTP status for a room that does not exist should be BAD_REQUEST");
    }

    @Test
    void testGetDevicesInRoomWithMockMVCWhenRoomExistsButContainsNoDevicesShouldReturnNotFoundStatus() throws Exception {
        // Arrange
        when(mockRoomRepo.containsIdentity(roomIdAsGiven)).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(roomIdAsGiven)).thenReturn(new ArrayList<>());

        uri += "/room/" + roomIdStr;
        String dtoAsJSON = objectMapper.writeValueAsString(roomIdStr);
        HttpStatus expected = HttpStatus.NOT_FOUND;

        // Act
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        // Assert
        assertEquals(expected.value(), result.getResponse().getStatus(), "The HTTP status for a room that exists but contains no devices should be NOT_FOUND");
    }

    @Test
    void testGetDevicesInRoomWithMockMVCWhenRoomExistsAndContainsDevicesShouldReturnOKStatus() throws Exception {
        // Arrange
        String uri = "/devices/room/" + roomIdAsGiven.getRoomId();
        int expectedStatus = HttpStatus.OK.value();

        when(mockRoomRepo.containsIdentity(roomIdAsGiven)).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(roomIdAsGiven)).thenReturn(List.of(deviceInChosenRoomFromRepository1.getDeviceId()));

        List<DeviceIdDTO> expectedDevices = deviceMapper.toDeviceIdsDTO(List.of(deviceInChosenRoomFromRepository1.getDeviceId()));

        expectedDevices.get(0).add(Link.of("http://localhost/devices/" + deviceInChosenRoomFromRepository1.getDeviceId().getIdentity()));
        expectedDevices.get(0).add(Link.of("http://localhost/devices/" + deviceInChosenRoomFromRepository1.getDeviceId().getIdentity() + "/deactivate").withRel("deactivate"));

        // Act
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int actualStatus = mvcResult.getResponse().getStatus();

        // Assert
        assertEquals(expectedStatus, actualStatus, "The HTTP status for a room that exists and contains devices should be OK.");
    }

    @Test
    void testGetDevicesInRoomWithMockMVCWhenRoomExistsAndContainsDevicesShouldReturnDeviceList() throws Exception {
        // Arrange
        String uri = "/devices/room/" + roomIdAsGiven.getRoomId();
        String dtoAsJSON = objectMapper.writeValueAsString(roomIdAsGiven.getRoomId());

        when(mockRoomRepo.containsIdentity(roomIdAsGiven)).thenReturn(true);
        when(mockDeviceRepo.findDeviceIdsByRoomId(roomIdAsGiven)).thenReturn(List.of(deviceInChosenRoomFromRepository1.getDeviceId()));

        List<DeviceIdDTO> expectedDevices = deviceMapper.toDeviceIdsDTO(List.of(deviceInChosenRoomFromRepository1.getDeviceId()));
        expectedDevices.get(0).add(Link.of("http://localhost/devices/" + deviceInChosenRoomFromRepository1.getDeviceId().getIdentity()));
        expectedDevices.get(0).add(Link.of("http://localhost/devices/" + deviceInChosenRoomFromRepository1.getDeviceId().getIdentity() + "/deactivate").withRel("deactivate"));
        String expected = objectMapper.writeValueAsString(expectedDevices);

        // Act
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsJSON)).andReturn();

        // Assert
        assertEquals(expected, mvcResult.getResponse().getContentAsString(), "The response body should contain a list of DeviceIdDTO objects.");
    }

    /**
     * Test to ensure that the controller correctly deactivates a device. The device is activated and the test should
     * return a 200 status code.
     */
    @Test
    void testDeactivateDeviceShouldReturnStatusOK() {
        //Arrange
        DeviceDTO myDeviceDTO = new DeviceDTO(device1.getIdentity().getIdentity(),
                device1.getDeviceName().getDeviceName(), device1.getDeviceTypeName().getDeviceTypeName(),
                device1.getRoomId().getRoomId(), device1.getDeviceStatus().getStatus());
        HttpStatus expected = HttpStatus.OK;

        //Act
        ResponseEntity<DeviceDTO> deactivatedDevice =
                deviceRESTController.deactivateDevice(myDeviceDTO.getDeviceId());
        //Assert
        HttpStatusCode result = deactivatedDevice.getStatusCode();
        assertEquals(expected, result, "The status code should be 200");
    }

    /**
     * Test to ensure that the controller does not deactivate a device that is already deactivated. The device is
     * already deactivated and the test should return a 422 status code.
     */
    @Test
    void testDeactivateDeviceAlreadyDeactivatedShouldReturnStatusNotFound() {
        //Arrange
        device1.deactivateDevice();
        DeviceDTO myDeviceDTO = new DeviceDTO(device1.getIdentity().getIdentity(),
                device1.getDeviceName().getDeviceName(), device1.getDeviceTypeName().getDeviceTypeName(),
                device1.getRoomId().getRoomId(), device1.getDeviceStatus().getStatus());
        HttpStatus expected = HttpStatus.NOT_FOUND;

        //Act
        ResponseEntity<DeviceDTO> deactivatedDevice =
                deviceRESTController.deactivateDevice(myDeviceDTO.getDeviceId());
        //Assert
        HttpStatusCode result = deactivatedDevice.getStatusCode();
        assertEquals(expected, result, "The status code should be 404");
    }

    /**
     * Test to ensure that the controller does not deactivate a device that does not exist. The device is not found and
     * the test should return a 422 status code.
     */
    @Test
    void testDeactivateDeviceNotFoundShouldReturnStatusNotFound() {
        //Arrange
        HttpStatus expected = HttpStatus.NOT_FOUND;
        //Act
        ResponseEntity<DeviceDTO> deactivatedDevice =
                deviceRESTController.deactivateDevice("nonexistentId");
        //Assert
        HttpStatusCode result = deactivatedDevice.getStatusCode();
        assertEquals(expected, result, "The status code should be 404");
    }

    /**
     * Test to ensure that the controller correctly deactivates a device. The device is activated and the test should
     * return a response body with the deactivated device data.
     */
    @Test
    void testPutRequestToDeactivateDeviceReturnsCorrectResponseBody() {
        // Arrange
        DeviceDTO myDeviceDTO = new DeviceDTO(device1.getIdentity().getIdentity(),
                device1.getDeviceName().getDeviceName(), device1.getDeviceTypeName().getDeviceTypeName(),
                device1.getRoomId().getRoomId(), device1.getDeviceStatus().getStatus());
        // Act
        ResponseEntity<DeviceDTO> deactivatedDevice =
                deviceRESTController.deactivateDevice(myDeviceDTO.getDeviceId());
        // Assert
        DeviceDTO responseBody = Objects.requireNonNull(deactivatedDevice.getBody());
        boolean result = responseBody.getDeviceId().equals(myDeviceDTO.getDeviceId()) &&
                         responseBody.getDeviceName().equals(myDeviceDTO.getDeviceName()) &&
                         responseBody.getDeviceTypeName().equals(myDeviceDTO.getDeviceTypeName()) &&
                         responseBody.getRoomId().equals(myDeviceDTO.getRoomId()) &&
                         !responseBody.getDeviceStatus();
        assertTrue(result, "The response body should contain the deactivated device data");
    }

    /**
     * This test verifies the scenario where a device is found and needs to be deactivated. It sends a PUT request to
     * the endpoint `/devices/{deviceId}/deactivate` where `{deviceId}` is the ID of an existing device. The test
     * asserts that the HTTP status of the response is 200 (OK) and that the device's status is updated to inactivate.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testDeactivateDeviceWithDeviceFound() throws Exception {
        // Arrange
        String urlPath = "/devices/" + device1.getIdentity().getIdentity() + "/deactivate";

        // Act
        mockMvc.perform(put(urlPath).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Assert
        boolean result = device1.getDeviceStatus().getStatus();
        assertFalse(result, "The device should be deactivated");
    }

    /**
     * This test checks the scenario where a device is not found. It sends a PUT request to the endpoint
     * `/devices/{deviceId}/deactivate` where `{deviceId}` is a non-existent device ID. The test asserts that the HTTP
     * status of the response is 404 (Not Found), indicating that the device could not be found.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testDeactivateDeviceWithDeviceNotFound() throws Exception {
        // Arrange
        String urlPath = "/devices/" + "nonexistentId" + "/deactivate";
        int expected = HttpStatus.NOT_FOUND.value();

        // Act - Expecting a 404 status code
        ResultActions result = mockMvc.perform(put(urlPath).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Assert
        int resultStatusCode = result.andReturn().getResponse().getStatus();
        assertEquals(expected, resultStatusCode, "The status code should be 404");
    }

    /**
     * Test of the getDevicesByFunctionality method, of class DeviceRESTController. It should return a map with the
     * devices by functionality, without throwing an exception in this case, the method should return an empty map
     */
    @Test
    void testGetDevicesByFunctionalityHouseWithNoDevicesShouldReturnEmptyMap() {
        //Act
        Map<String, List<DeviceDTO>> result = deviceRESTController.getDevicesByFunctionality().getBody();
        //Assert
        assertTrue(Objects.requireNonNull(result).isEmpty());
    }

    /**
     * Test of the getDevicesByFunctionality method, of class DeviceRESTController. It should return a map with the
     * devices by functionality, without throwing an exception in this case, it should return an empty map
     */
    @Test
    void testGetDevicesByFunctionalityHouseWith2DevicesIn2DifferentRoomsButNoSensorsShouldReturnEmptyMap() throws Exception {
        //Arrange
        when(mockDeviceRepo.findAll()).thenReturn(List.of(device1, device2));
        ObjectMapper objectMapper = new ObjectMapper();
        //Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Map<String, List<DeviceDTO>> resultBody = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        //Assert
        assertTrue(Objects.requireNonNull(resultBody).isEmpty());
    }

    /**
     * Test of the getDevicesByFunctionality method, of class DeviceRESTController. It should return a map with the
     * devices by functionality, without throwing an exception in this case, it should return a map with one entry, one
     * device with no one SensorOfTemperature
     */
    @Test
    void testGetDevicesBySensorType2DevicesOneHasSensorOfTemperatureWithItsDeviceIdShouldReturnHashMapWithOneEntry() throws Exception {
        //Arrange
        Sensor sensor = sensorFactory.createSensor(sensorModel1.getIdentity(), device1.getIdentity());
        when(mockSensorRepo.findAll()).thenReturn(List.of(sensor));
        ObjectMapper objectMapper = new ObjectMapper();
        //Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Map<String, List<DeviceDTO>> resultBody = objectMapper.readValue(result.getResponse().getContentAsString()
                , new TypeReference<>() {
                });
        //Assert
        assertEquals(1, Objects.requireNonNull(resultBody).size());
        assertEquals(1, Objects.requireNonNull(resultBody.get("Temperature")).size());
        assertFalse(resultBody.containsKey("Humidity"));
        assertTrue(resultBody.values().stream().anyMatch(devices -> devices.stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device1.getIdentity().getIdentity()))));
        assertFalse(resultBody.values().stream().anyMatch(devices -> devices.stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device2.getIdentity().getIdentity()))));

    }


    @Test
    void testGetDevicesBySensorType2DevicesOneHasSensorOfTemperatureAndTheOtherHasSensorOfHumidityWithTheirDeviceIdsShouldReturnHashMapWithTwoEntries() throws Exception {
        //Arrange
        Sensor sensor1 = sensorFactory.createSensor(sensorModel1.getIdentity(), device1.getIdentity());
        Sensor sensor2 = sensorFactory.createSensor(sensorModel2.getIdentity(), device2.getIdentity());
        ObjectMapper objectMapper = new ObjectMapper();
        when(mockSensorRepo.findAll()).thenReturn(List.of(sensor1, sensor2));
        //Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Map<String, List<DeviceDTO>> resultBody = objectMapper.readValue(result.getResponse().getContentAsString()
                , new TypeReference<>() {
                });
        //Assert
        assertEquals(2, Objects.requireNonNull(resultBody).size());
        assertEquals(1, Objects.requireNonNull(resultBody.get("Temperature")).size());
        assertEquals(1, Objects.requireNonNull(resultBody.get("Humidity")).size());
        assertTrue(resultBody.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device1.getIdentity().getIdentity())));
        assertTrue(resultBody.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device2.getIdentity().getIdentity())));
    }

    /**
     * Test of the getDevicesByFunctionality method, of class DeviceRESTController. It should return a map with the
     * devices by functionality, without throwing an exception in this case, it should return a map with two entries,
     * one for each sensor type and one device for each sensor type
     */
    @Test
    void testGetDevicesBySensorTypeOneDeviceHasSensorOfTemperatureAndHumidityWithItsDeviceIdShouldReturnHashMapWithTwoEntries() throws Exception {
        //Arrange
        Sensor sensor1 = sensorFactory.createSensor(sensorModel1.getIdentity(), device1.getIdentity());
        Sensor sensor2 = sensorFactory.createSensor(sensorModel2.getIdentity(), device1.getIdentity());
        when(mockSensorRepo.findAll()).thenReturn(List.of(sensor1, sensor2));
        ObjectMapper objectMapper = new ObjectMapper();
        //Act
        MvcResult preResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Map<String, List<DeviceDTO>> result = objectMapper.readValue(preResult.getResponse().getContentAsString(), new TypeReference<>() {});
        //Assert
        assertEquals(2, Objects.requireNonNull(result).size());
        assertEquals(1, Objects.requireNonNull(result.get("Temperature")).size());
        assertEquals(1, Objects.requireNonNull(result.get("Humidity")).size());
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device1.getIdentity().getIdentity())));
        assertTrue(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device1.getIdentity().getIdentity())));
    }

    /**
     * Test of the getDevicesByFunctionality method, of class DeviceRESTController. It should return a map with the
     * devices by functionality, without throwing an exception in this case, it should return a map with one entry, one
     * device with 2 SensorOfTemperature
     */
    @Test
    void testGetDevicesBySensorType2DevicesOneHas2SensorsOfTemperatureShouldReturnMapWithOneEntry() throws Exception {
        //Assert
        Sensor sensor1 = sensorFactory.createSensor(sensorModel1.getIdentity(), device1.getIdentity());
        Sensor sensor2 = sensorFactory.createSensor(sensorModel1.getIdentity(), device1.getIdentity());
        when(mockSensorRepo.findAll()).thenReturn(List.of(sensor1, sensor2));
        ObjectMapper objectMapper = new ObjectMapper();
        //Act
        MvcResult preResult =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Map<String, List<DeviceDTO>> result = objectMapper.readValue(preResult.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        //Assert
        assertEquals(1, Objects.requireNonNull(result).size());
        assertEquals(1, Objects.requireNonNull(result.get("Temperature")).size());
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device1.getIdentity().getIdentity())));
        assertFalse(result.containsKey("Humidity"));
    }

    /**
     * Test to ensure that the controller returns an empty map when there are no sensors.
     */
    @Test
    void testGetDevicesBySensorTypeNoSensors() throws Exception {
        //Arrange
        when(mockSensorRepo.findAll()).thenReturn(List.of());
        ObjectMapper objectMapper = new ObjectMapper();
        //Act
        MvcResult resultContent =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Map<String, List<DeviceDTO>> result = objectMapper.readValue(resultContent.getResponse().getContentAsString(), new TypeReference<>() {
                });
        //Assert
        assertTrue(Objects.requireNonNull(result).isEmpty());
    }

    /**
     * Test to ensure that the controller correctly returns a map of devices by functionality when one device has a
     * temperature sensor and another has a humidity sensor.
     */
    @Test
    void testGetDevicesBySensorType1SensorOfTemperature1SensorOfHumidity() throws Exception {
        // Arrange
        Sensor sensor1 = sensorFactory.createSensor(sensorModel1.getIdentity(), device1.getIdentity());
        Sensor sensor2 = sensorFactory.createSensor(sensorModel2.getIdentity(), device2.getIdentity());
        when(mockSensorRepo.findAll()).thenReturn(List.of(sensor1, sensor2));
        ObjectMapper objectMapper = new ObjectMapper();
        // Act
        MvcResult resultContent =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        Map<String, List<DeviceDTO>> result = objectMapper.readValue(resultContent.getResponse().getContentAsString(), new TypeReference<>() {
        });
        // Assert
        assertEquals(2, Objects.requireNonNull(result).size());
        assertEquals(1, Objects.requireNonNull(result.get("Temperature")).size());
        assertEquals(1, Objects.requireNonNull(result.get("Humidity")).size());
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device1.getIdentity().getIdentity())));
        assertTrue(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(device2.getIdentity().getIdentity())));
        }



    @Test
    void testGetDevicesBySensorTypeMockedServiceThrowsExceptionShouldReturnInternalServerError() throws Exception {
        //Arrange
        when(mockSensorRepo.findAll()).thenThrow(new DataIntegrityViolationException("Exception"));
        //Act
        MvcResult resultContent =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/functionality").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultContent.getResponse().getStatus());
    }

    /**
     * This test case verifies that the getDeviceById method in the DeviceRESTController class returns a NOT_FOUND (404)
     * status when the device with the provided ID does not exist.
     */
    @Test
    void testGetDeviceByIdReturnsNotFoundWhenDeviceDoesNotExist() {
        // Arrange
        String deviceIdStr = "nonexistentId";
        HttpStatus expected = HttpStatus.NOT_FOUND;
        when(mockDeviceRepo.findByIdentity(new DeviceId(deviceIdStr))).thenReturn(Optional.empty());
        // Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.getDeviceById(deviceIdStr);
        // Assert
        HttpStatusCode actual = result.getStatusCode();
        assertEquals(expected, actual, "The status code should be 404");
    }

    /**
     * This test case verifies that the getDeviceById method in the DeviceRESTController class returns an OK (200)
     * status
     * when the device with the provided ID exists.
     */
    @Test
    void testGetDeviceByIdReturnsOkWhenDeviceExists() {
        // Arrange
        HttpStatus expected = HttpStatus.OK;
        when(mockDeviceRepo.findByIdentity(device1.getIdentity())).thenReturn(Optional.of(device1));
        // Act
        ResponseEntity<DeviceDTO> result = deviceRESTController.getDeviceById(device1.getIdentity().getIdentity());
        // Assert
        HttpStatusCode actual = result.getStatusCode();
        assertEquals(expected, actual, "The status code should be 200");
    }

    /**
     * This test case verifies that the getDeviceById method in the DeviceRESTController class correctly handles a GET
     * request with a valid device ID. The test mocks the behavior of the DeviceRepository to return a device when
     * queried with a valid device ID. It then sends a GET request to the endpoint `/devices/{deviceId}` where
     * `{deviceId}` is the ID of an existing device. The test asserts that the HTTP status of the response is 200 (OK),
     * indicating that the device was found and returned successfully.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetDeviceByIdCorrectHandlesGetRequestWithValidDeviceId() throws Exception {
        // Arrange
        String device1Id = device1.getIdentity().getIdentity();
        when(mockDeviceRepo.findByIdentity(device1.getIdentity())).thenReturn(Optional.of(device1));
        int expected = HttpStatus.OK.value();
        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri + "/" + device1Id)).andReturn();
        // Assert
        int resultStatusCode = result.getResponse().getStatus();
        assertEquals(expected, resultStatusCode, "The status code should be 200");
    }

    /**
     * This test case verifies that the getDeviceById method in the DeviceRESTController class correctly handles a GET
     * request with an invalid device ID. The test mocks the behavior of the DeviceRepository to return an empty
     * Optional when queried with an invalid device ID. It then sends a GET request to the endpoint
     * `/devices/{deviceId}` where `{deviceId}` is a non-existent device ID. The test asserts that the HTTP status of
     * the response is 404 (Not Found), indicating that the device could not be found.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetDeviceByIdCorrectHandlesGetRequestWithInvalidDeviceId() throws Exception {
        // Arrange
        String invalidDeviceId = "invalidId";
        when(mockDeviceRepo.findByIdentity(new DeviceId(invalidDeviceId))).thenReturn(Optional.empty());
        int expected = HttpStatus.NOT_FOUND.value();
        // Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri + "/" + invalidDeviceId)).andReturn();
        // Assert
        int resultStatusCode = result.getResponse().getStatus();
        assertEquals(expected, resultStatusCode, "The status code should be 404");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when an exception is thrown during the execution of the method. The test asserts that the response body is null,
     * indicating that an exception occurred during the execution of the method.
     */
    @Test
    void testGetDevicesThrowsExceptionCheckResponseBody() {
        // Arrange
        when(mockDeviceRepo.findDeviceIds()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<DeviceIdDTO>> response = deviceRESTController.getDevices();

        // Assert
        List<DeviceIdDTO> result = response.getBody();
        assertNull(result, "The response body should be null.");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when an exception is thrown during the execution of the method. The test asserts that the HTTP status of the
     * response is 400 (Bad Request), indicating that an exception occurred during the execution of the method.
     */
    @Test
    void getDevicesThrowsExceptionCheckStatus() {
        // Arrange
        HttpStatusCode expected = HttpStatus.BAD_REQUEST;
        when(mockDeviceRepo.findDeviceIds()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<DeviceIdDTO>> response = deviceRESTController.getDevices();

        // Assert
        HttpStatusCode result = response.getStatusCode();
        assertEquals(expected, result, "The status code should be 400.");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are no devices in the repository. The test asserts that the response body is null, indicating that
     * there are no devices in the repository.
     */
    @Test
    void testGetDevicesWithNoDevicesCheckResponseBody() {
        // Arrange
        when(mockDeviceRepo.findDeviceIds()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<DeviceIdDTO>> response = deviceRESTController.getDevices();

        // Assert
        List<DeviceIdDTO> result = response.getBody();
        assertNull(result, "The response body should be null.");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are no devices in the repository. The test asserts that the HTTP status of the response is 404 (Not
     * Found), indicating that there are no devices in the repository.
     */
    @Test
    void testGetDevicesWithNoDevicesCheckStatus() {
        // Arrange
        HttpStatusCode expected = HttpStatus.NOT_FOUND;
        when(mockDeviceRepo.findDeviceIds()).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<DeviceIdDTO>> response = deviceRESTController.getDevices();

        // Assert
        HttpStatusCode result = response.getStatusCode();
        assertEquals(expected, result, "The status code should be 404.");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are devices in the repository. The test asserts that the response body is not null, indicating that
     * the method successfully retrieved the devices from the repository.
     */
    @Test
    void testGetDevicesWithDevicesCheckResponseBody() {
        // Arrange
        when(mockDeviceRepo.findDeviceIds()).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));

        // Act
        ResponseEntity<List<DeviceIdDTO>> response = deviceRESTController.getDevices();

        // Assert
        List<DeviceIdDTO> result = response.getBody();
        assertNotNull(result, "The response body should not be null.");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are devices in the repository. The test asserts that the HTTP status of the response is 200 (OK),
     * indicating that the method successfully retrieved the devices from the repository.
     */
    @Test
    void testGetDevicesWithDevicesCheckStatus() {
        // Arrange
        HttpStatusCode expected = HttpStatus.OK;
        when(mockDeviceRepo.findDeviceIds()).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));

        // Act
        ResponseEntity<List<DeviceIdDTO>> response = deviceRESTController.getDevices();

        // Assert
        HttpStatusCode result = response.getStatusCode();
        assertEquals(expected, result, "The status code should be 200.");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are devices in the repository. The test asserts that the response body contains the correct device
     * IDs, indicating that the method successfully retrieved the devices from the repository.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetDevicesCorrectlyHandlesGetRequestCheckStatus() throws Exception {
        // Arrange
        when(mockDeviceRepo.findDeviceIds()).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));
        int expected = HttpStatus.OK.value();

        // Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int resultStatusCode = result.getResponse().getStatus();
        assertEquals(expected, resultStatusCode, "The status code should be 200");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are devices in the repository. The test asserts that the response body contains the correct device
     * IDs, indicating that the method successfully retrieved the devices from the repository.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetDevicesCorrectlyHandlesGetRequestCheckResponseBody() throws Exception {
        // Arrange
        DeviceIdDTO deviceIdDTO = new DeviceIdDTO(device1.getIdentity().getIdentity()).add(Link.of("http://localhost" +
                                                                                                   "/devices/" + device1.getIdentity().getIdentity()));
        when(mockDeviceRepo.findDeviceIds()).thenReturn(List.of(device1.getIdentity()));
        String expected =
                objectMapper.writeValueAsString(List.of(deviceIdDTO));

        // Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals(expected, resultContent, "The response body should contain the correct device IDs");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are multiple devices in the repository. The test asserts that the response body contains the correct
     * device IDs, indicating that the method successfully retrieved the devices from the repository.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetDevicesCorrectlyHandlesGetRequestCheckResponseBodyWithMultipleDevices() throws Exception {
        // Arrange
        DeviceIdDTO deviceIdDTO1 = new DeviceIdDTO(device1.getIdentity().getIdentity()).add(Link.of("http://localhost" +
                                                                                                    "/devices/" + device1.getIdentity().getIdentity()));
        DeviceIdDTO deviceIdDTO2 = new DeviceIdDTO(device2.getIdentity().getIdentity()).add(Link.of("http://localhost" +
                                                                                                    "/devices/" + device2.getIdentity().getIdentity()));
        when(mockDeviceRepo.findDeviceIds()).thenReturn(List.of(device1.getIdentity(), device2.getIdentity()));
        String expected =
                objectMapper.writeValueAsString(List.of(deviceIdDTO1, deviceIdDTO2));

        // Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        String resultContent = result.getResponse().getContentAsString();
        assertEquals(expected, resultContent, "The response body should contain the correct device IDs");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when there are no devices in the repository. The test asserts that the status code of the response is 404 (Not
     * Found), indicating that there are no devices in the repository.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetDevicesCorrectlyHandlesGetRequestCheckStatusWithNoDevices() throws Exception {
        // Arrange
        when(mockDeviceRepo.findDeviceIds()).thenReturn(new ArrayList<>());
        int expected = HttpStatus.NOT_FOUND.value();

        // Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int resultStatusCode = result.getResponse().getStatus();
        assertEquals(expected, resultStatusCode, "The status code should be 404");
    }

    /**
     * This test case is designed to verify the behavior of the getDevices() method in the DeviceRESTController class
     * when an exception is thrown during the execution of the method. The test asserts that the status code of the
     * response is 400 (Bad Request), indicating that an exception occurred during the execution of the method.
     * @throws Exception if any error occurs during the test
     */
    @Test
    void testGetDevicesCorrectlyHandlesGetRequestWhenExceptionThrown() throws Exception {
        // Arrange
        when(mockDeviceRepo.findDeviceIds()).thenThrow(new RuntimeException());
        int expected = HttpStatus.BAD_REQUEST.value();

        // Act
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON)).andReturn();

        // Assert
        int resultStatusCode = result.getResponse().getStatus();
        assertEquals(expected, resultStatusCode, "The status code should be 400");
    }

}
