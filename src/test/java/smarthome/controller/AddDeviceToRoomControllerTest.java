package smarthome.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.DeviceTypeMapper;
import smarthome.service.IDeviceService;
import smarthome.service.impl.DeviceServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is responsible for testing the AddDeviceToRoomController class.
 * It makes use of the Mockito library to mock the repositories.
 */
class AddDeviceToRoomControllerTest {

    private IDeviceService deviceService;
    private DeviceMapper deviceMapper;
    private DeviceTypeMapper deviceTypeMapper;
    private AddDeviceToRoomController addDeviceToRoomController;
    private String deviceName;
    private String deviceType;
    private String roomId;
    private DeviceDTO deviceDTO;
    private String nonExistentRoomId;
    private String nonExistentDeviceType;

    /**
     * Set up the environment for the tests.
     * Initialize the AddDeviceToRoomController dependencies.
     * Create a new DeviceDTO and Device.
     * Configure the mock repositories.
     */
    @BeforeEach
    void setUp() {
        // Initialize the AddDeviceToRoomController dependencies
        IDeviceRepository mockDeviceRepository = mock(IDeviceRepository.class);
        ISensorRepository mockSensorRepository = mock(ISensorRepository.class);
        ISensorModelRepository mockSensorModelRepository = mock(ISensorModelRepository.class);
        ISensorTypeRepository mockSensorTypeRepository = mock(ISensorTypeRepository.class);
        IDeviceTypeRepository mockDeviceTypeRepository = mock(IDeviceTypeRepository.class);
        deviceMapper = new DeviceMapper();
        deviceTypeMapper = new DeviceTypeMapper();
        DeviceFactory deviceFactory = new DeviceFactoryImpl();
        IRoomRepository mockRoomRepository = mock(IRoomRepository.class);
        deviceService = new DeviceServiceImpl(mockRoomRepository, deviceFactory, mockDeviceRepository,
                mockSensorTypeRepository, mockSensorModelRepository, mockSensorRepository, mockDeviceTypeRepository);
        addDeviceToRoomController = new AddDeviceToRoomController(deviceService, deviceMapper, deviceTypeMapper);

        // Create a new DeviceDTO and Device
        deviceName = "deviceName";
        deviceType = "deviceType";
        roomId = "roomId";
        deviceDTO = new DeviceDTO(deviceName, deviceType, roomId);
        DeviceName deviceNameVO = new DeviceName(deviceName);
        DeviceTypeName deviceTypeVO = new DeviceTypeName(deviceType);
        RoomId roomIdVO = new RoomId(roomId);
        DeviceId deviceIdVO = new DeviceId("deviceId");
        DeviceStatus deviceStatusVO = new DeviceStatus(true);
        Device myDevice = deviceFactory.createDevice(deviceIdVO, deviceNameVO, deviceTypeVO, roomIdVO, deviceStatusVO);

        // Create a DeviceDTO with a non-existent room id and non-existent device type
        nonExistentRoomId = "nonExistentRoomId";
        RoomId nonExistentRoomIdVO = new RoomId(nonExistentRoomId);
        nonExistentDeviceType = "nonExistentDeviceType";
        DeviceTypeName nonExistentDeviceTypeVO = new DeviceTypeName(nonExistentDeviceType);

        // Configure mock repositories
        when(mockRoomRepository.containsIdentity(roomIdVO)).thenReturn(true);
        when(mockDeviceTypeRepository.containsIdentity(nonExistentDeviceTypeVO)).thenReturn(false);
        when(mockDeviceTypeRepository.containsIdentity(deviceTypeVO)).thenReturn(true);
        when(mockRoomRepository.containsIdentity(nonExistentRoomIdVO)).thenReturn(false);
        when(mockDeviceRepository.save(any(Device.class))).thenReturn(myDevice);
    }

    /**
     * Test the constructor of AddDeviceToRoomController class.
     * It should return an instance of AddDeviceToRoomController.
     */
    @Test
    void testAddDeviceToRoomControllerConstructor() {
        //Act
        AddDeviceToRoomController controller = new AddDeviceToRoomController(deviceService, deviceMapper, deviceTypeMapper);
        //Assert
        assertNotNull(controller, "The constructor should be not null.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return a DeviceDTO representing the device added to the room, with the correct device name.
     */
    @Test
    void testAddDeviceToRoomReturnDeviceDTOWithCorrectDeviceName() {
        // Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(deviceDTO);
        //Assert
        String resultDeviceName = result.getDeviceName();
        assertEquals(deviceName, resultDeviceName, "The device name should be the same as the one provided in the " +
                "DeviceDTO.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return a DeviceDTO representing the device added to the room, with the correct device type.
     */
    @Test
    void testAddDeviceToRoomReturnDeviceDTOWithCorrectDeviceType() {
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(deviceDTO);
        //Assert
        String resultDeviceType = result.getDeviceTypeName();
        assertEquals(deviceType, resultDeviceType, "The device type should be the same as the one provided in " +
                "the DeviceDTO.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return a DeviceDTO representing the device added to the room, with the correct room id.
     */
    @Test
    void testAddDeviceToRoomReturnDeviceDTOWithCorrectRoomId() {
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(deviceDTO);
        //Assert
        String resultRoomId = result.getRoomId();
        assertEquals(roomId, resultRoomId, "The room id should be the same as the one provided in the " +
                "DeviceDTO.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return a DeviceDTO representing the device added to the room, with a valid status.
     */
    @Test
    void testAddDeviceToRoomReturnDeviceDTOWithValidStatus() {
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(deviceDTO);
        //Assert
        boolean resultStatus = result.getDeviceStatus();
        assertTrue(resultStatus, "The device status should be true by default.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return a DeviceDTO representing the device added to the room, with a not null device id.
     */
    @Test
    void testAddDeviceToRoomReturnDeviceDTOWithNotNullDeviceId() {
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(deviceDTO);
        //Assert
        String resultDeviceId = result.getDeviceId();
        assertNotNull(resultDeviceId, "The device id should not be null.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the device name is null.
     */
    @Test
    void testAddDeviceReturnsNullWhenDeviceNameIsNull() {
        //Arrange
        DeviceDTO invalidDeviceDTO = new DeviceDTO(null, deviceType, roomId);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(invalidDeviceDTO);
        //Assert
        assertNull(result, "The method should return null when the device name is null.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the device name is blank.
     */
    @Test
    void testAddDeviceReturnsNullWhenDeviceNameIsBlank() {
        //Arrange
        deviceName = " ";
        DeviceDTO invalidDeviceDTO = new DeviceDTO(deviceName, deviceType, roomId);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(invalidDeviceDTO);
        //Assert
        assertNull(result, "The method should return null when the device name is blank.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the device type is null.
     */
    @Test
    void testAddDeviceReturnsNullWhenDeviceTypeIsNull() {
        //Arrange
        DeviceDTO invalidDeviceDTO = new DeviceDTO(deviceName, null, roomId);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(invalidDeviceDTO);
        //Assert
        assertNull(result, "The method should return null when the device type is null.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the device type is blank.
     */
    @Test
    void testAddDeviceReturnsNullWhenDeviceTypeIsBlank() {
        //Arrange
        deviceType = " ";
        DeviceDTO invalidDeviceDTO = new DeviceDTO(deviceName, deviceType, roomId);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(invalidDeviceDTO);
        //Assert
        assertNull(result, "The method should return null when the device type is blank.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the room name is null.
     */
    @Test
    void testAddDeviceReturnsNullWhenRoomNameIsNull() {
        //Arrange
        DeviceDTO invalidDeviceDTO = new DeviceDTO(deviceName, deviceType, null);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(invalidDeviceDTO);
        //Assert
        assertNull(result, "The method should return null when the room name is null.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the room name is blank.
     */
    @Test
    void testAddDeviceReturnsNullWhenRoomNameIsBlank() {
        //Arrange
        roomId = " ";
        DeviceDTO invalidDeviceDTO = new DeviceDTO(deviceName, deviceType, roomId);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(invalidDeviceDTO);
        //Assert
        assertNull(result, "The method should return null when the room name is blank.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the room id does not exist in the repository.
     */
    @Test
    void testAddDeviceToRoomReturnNullWhenRoomIdDoesNotExistInRepository() {
        //Arrange
        deviceDTO = new DeviceDTO(deviceName, deviceType, nonExistentRoomId);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(deviceDTO);
        //Assert
        assertNull(result, "The method should return null when the room id does not exist in the repository.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the device type name does not exist in the repository.
     */
    @Test
    void testAddDeviceToRoomReturnNullWhenDeviceTypeNameDoesNotExistInRepository() {
        //Arrange
        deviceDTO = new DeviceDTO(deviceName, nonExistentDeviceType, roomId);
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(deviceDTO);
        //Assert
        assertNull(result, "The method should return null when the room id does not exist in the repository.");
    }

    /**
     * Test the addDeviceToRoom method of AddDeviceToRoomController class.
     * It should return null when the deviceDTO is null.
     */
    @Test
    void testAddDeviceToRoomReturnNullWhenDeviceDTOIsInvalid() {
        //Act
        DeviceDTO result = addDeviceToRoomController.addDeviceToRoom(null);
        //Assert
        assertNull(result, "The method should return null when the DeviceDTO is null.");
    }
}