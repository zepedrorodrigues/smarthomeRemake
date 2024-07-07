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
import smarthome.service.IDeviceService;
import smarthome.service.impl.DeviceServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for DeactivateDeviceController.
 * It tests the functionality of deactivating a device in a smart home system.
 */
class DeactivateDeviceControllerTest {
    // Dependencies for the Device Rest Service
    IDeviceRepository mockDeviceRepo;
    ISensorRepository mockSensorRepo;
    ISensorModelRepository mockSensorModelRepo;
    ISensorTypeRepository mockSensorTypeRepo;
    IDeviceTypeRepository mockDeviceTypeRepo;
    DeviceMapper deviceMapper;
    DeviceFactory deviceFactory;
    IRoomRepository mockRoomRepo;
    IDeviceService deviceService;

    // Test data for device DTO
    String deviceDTOName;
    String deviceDTOType;
    String deviceDTORoomId;
    String deviceDTOId;
    boolean deviceDTOStatus;

    // Device identifiers
    DeviceId deviceId;
    Device dtoDevice;
    Device mockDevice;

    // Controller for deactivating devices
    DeactivateDeviceController myController;


    /**
     * Sets up the test environment before each test.
     * Initializes the device service, device DTO, and controller.
     * Adds a device to a room and mocks the existence of the device in the repository.
     * Initializes the controller.
     */
    @BeforeEach
    void setUp() {
        // Dependencies for the Device Service
        mockRoomRepo = mock(IRoomRepository.class);
        deviceFactory = new DeviceFactoryImpl();
        mockDeviceRepo = mock(IDeviceRepository.class);
        mockSensorTypeRepo = mock(ISensorTypeRepository.class);
        mockSensorModelRepo = mock(ISensorModelRepository.class);
        mockSensorRepo = mock(ISensorRepository.class);
        mockDeviceTypeRepo = mock(IDeviceTypeRepository.class);


        // Initialize device service
        deviceService = new DeviceServiceImpl(mockRoomRepo, deviceFactory, mockDeviceRepo, mockSensorTypeRepo,
                mockSensorModelRepo, mockSensorRepo, mockDeviceTypeRepo);

        // Initialize test data for device DTO
        deviceDTOName = "testDeviceName";
        deviceDTOType = "testDeviceType";
        deviceDTORoomId = "testRoomId";

        // Add a device to a room
        RoomId roomId = new RoomId(deviceDTORoomId);
        when(mockRoomRepo.containsIdentity(roomId)).thenReturn(true);

        // Device identifiers from DTO
        DeviceName deviceName = new DeviceName(deviceDTOName);
        DeviceTypeName deviceType = new DeviceTypeName(deviceDTOType);
        dtoDevice = deviceFactory.createDevice(deviceName, deviceType, roomId);

        // Device identifiers from Repository
        deviceDTOId = dtoDevice.getIdentity().toString();
        deviceId = new DeviceId(deviceDTOId);
        deviceDTOStatus = dtoDevice.getDeviceStatus().getStatus();
        DeviceStatus deviceStatus = new DeviceStatus(deviceDTOStatus);
        mockDevice = deviceFactory.createDevice(deviceId, deviceName, deviceType, roomId, deviceStatus);

        // Mock the existence of the device in the repository
        when(mockDeviceRepo.findByIdentity(deviceId)).thenReturn(Optional.of(mockDevice));


        // Initialize the controller
        deviceMapper = new DeviceMapper();
        myController = new DeactivateDeviceController(deviceService, deviceMapper);
    }


    /**
     * Tests that the deactivateDevice method returns null when the device DTO is null.
     */
    @Test
    void testDeactivateDeviceReturnsNullWhenDeviceDTOIsNull() {
        // Act
        DeviceDTO deactivatedDevice = myController.deactivateDevice(null);
        // Assert
        assertNull(deactivatedDevice, "DeactivateDevice should return null when DeviceDTO is null");
    }

    /**
     * Tests the deactivateDevice method.
     */
    @Test
    void testDeactivateDevice() {
        // Arrange
        deviceDTOStatus = mockDevice.getDeviceStatus().getStatus(); // Device Status is true
        DeviceDTO myDeviceDTO = new DeviceDTO(deviceDTOId, deviceDTOName, deviceDTOType, deviceDTORoomId,
                deviceDTOStatus);

        // Act
        DeviceDTO deactivatedDevice = myController.deactivateDevice(myDeviceDTO);
        // Assert
        boolean result = deactivatedDevice.getDeviceStatus();
        assertFalse(result, "Device Status should be false");
    }

    /**
     * Tests the deactivateDevice method when the device is already deactivated.
     */
    @Test
    void testDeactivateDeviceAlreadyDeactivated() {
        // Arrange
        deviceDTOStatus = mockDevice.getDeviceStatus().getStatus(); // Device Status is true
        DeviceDTO myDeviceDTO = new DeviceDTO(deviceDTOId, deviceDTOName, deviceDTOType, deviceDTORoomId,
                deviceDTOStatus);
        myController.deactivateDevice(myDeviceDTO); // Device status is now false
        // Act
        DeviceDTO reDeactivation = myController.deactivateDevice(myDeviceDTO);
        // Assert
        assertNull(reDeactivation, "DeactivateDevice should return null when device is already deactivated");
    }

    /**
     * Tests the deactivateDevice method when the device does not exist.
     */
    @Test
    void testDeactivateDeviceNonExistentDevice() {
        // Arrange
        String nonExistentDeviceId = "nonExistentDeviceId";
        DeviceDTO nonExistentDeviceDTO = new DeviceDTO(nonExistentDeviceId, deviceDTOName, deviceDTOType,
                deviceDTORoomId, true);
        // Act
        DeviceDTO deactivatedDevice = myController.deactivateDevice(nonExistentDeviceDTO);
        // Assert
        assertNull(deactivatedDevice, "DeactivateDevice should return null when device does not exist");
    }
}