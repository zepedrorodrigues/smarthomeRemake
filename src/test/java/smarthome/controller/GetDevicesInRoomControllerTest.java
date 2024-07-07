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
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IDeviceService;
import smarthome.service.impl.DeviceServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for GetDevicesInRoomController class.
 */
class GetDevicesInRoomControllerTest {

    GetDevicesInRoomController getDevicesInRoomController;
    IDeviceService deviceService;

    // Dependencies for the Device Service
    IDeviceRepository mockDeviceRepository;
    ISensorRepository mockSensorRepository;
    ISensorModelRepository mockSensorModelRepository;
    ISensorTypeRepository mockSensorTypeRepository;
    DeviceMapper deviceMapper;
    DeviceFactory deviceFactory;
    RoomMapper roomMapper;
    IRoomRepository mockRoomRepository;
    IDeviceTypeRepository mockDeviceTypeRepository;

    // Common variables
    RoomDTO roomDTOAsGiven;
    Room chosenRoom;
    RoomFactory roomFactory;
    HouseName ourHouse;
    RoomId roomIdAsGiven;
    RoomName roomName;
    Floor floor;
    Width width;
    Length length;
    Height height;
    Dimensions dimensions;

    // Device variables
    DeviceId deviceId;
    DeviceName deviceName;
    DeviceTypeName deviceType;
    DeviceStatus deviceStatus;
    Device deviceInChosenRoom;

    @BeforeEach
    void setUp() {
        // Initialize Device Service dependencies
        mockDeviceRepository = mock(IDeviceRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockSensorModelRepository = mock(ISensorModelRepository.class);
        mockSensorTypeRepository = mock(ISensorTypeRepository.class);
        deviceMapper = new DeviceMapper();
        deviceFactory = new DeviceFactoryImpl();
        roomMapper = new RoomMapper();
        mockRoomRepository = mock(IRoomRepository.class);
        mockDeviceTypeRepository = mock(IDeviceTypeRepository.class);

        // Initialize factories
        roomFactory = new RoomFactoryImpl();

        // Initialize common variables
        ourHouse = new HouseName("Sweet Home");

        roomDTOAsGiven = new RoomDTO("RoomId1", "Sweet Home", "Master Bedroom", 1, 3.0, 5.0, 4.0);

        roomIdAsGiven = roomMapper.toRoomId(roomDTOAsGiven);
        roomName = new RoomName("Master Bedroom");
        floor = new Floor(1);
        width = new Width(5);
        length = new Length(4);
        height = new Height(3);
        dimensions = new Dimensions(width, height, length);

        // Initialize device variables
        deviceId = new DeviceId("DeviceId1");
        deviceName = new DeviceName("DeviceName1");
        deviceType = new DeviceTypeName("DeviceType1");
        deviceStatus = new DeviceStatus(true);

        // Create the chosen room using the RoomFactory
        chosenRoom = roomFactory.createRoom(roomIdAsGiven, roomName, ourHouse, floor, dimensions);

        // Create a Device in the chosen room using the DeviceFactory
        deviceInChosenRoom = deviceFactory.createDevice(deviceId, deviceName, deviceType, roomIdAsGiven, deviceStatus);

        // Initialize GetDevicesInRoomController and DeviceServiceImpl
        deviceService = new DeviceServiceImpl(mockRoomRepository, deviceFactory, mockDeviceRepository,
                mockSensorTypeRepository, mockSensorModelRepository, mockSensorRepository, mockDeviceTypeRepository);
        getDevicesInRoomController = new GetDevicesInRoomController(deviceService, deviceMapper, roomMapper);
    }

    /**
     * Test the constructor of the GetDevicesInRoomController class.
     * The constructor should not throw an exception when all dependencies are valid.
     */
    @Test
    void testCreateConstructorWhenAllDependenciesAreValid() {
        //Act & Assert
        assertNotNull(getDevicesInRoomController
                , "The constructor should be instantiated when all dependencies are valid.");
    }

    /**
     * Test the constructor of the GetDevicesInRoomController class.
     * The constructor should throw an exception when the device service is null.
     */
    @Test
    void testConstructorDoesNotThrowExceptionWhenAllDependenciesAreValid() {
        //Act & Assert
        assertDoesNotThrow(() -> getDevicesInRoomController
                , "The constructor should not throw an exception when all dependencies are valid.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return null when the room DTO is null.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDTOIsNullShouldReturnNull() {
        //Act & Assert
        assertNull(getDevicesInRoomController.getDevicesInRoom(null)
                , "The method should throw an exception when the room DTO is null.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should not throw an exception when the room DTO is not null.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDTOIsNullShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> getDevicesInRoomController.getDevicesInRoom(null)
                , "The method should not throw an exception when the room DTO is null.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should not throw an exception when the room DTO is null.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDTOIsNotNullShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven)
                , "The method should not throw an exception when the room DTO is not null.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should not return null when the room DTO is not null.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDTOIsNotNullShouldReturnNotNull() {
        // Arrange
        // Create a valid roomDTO
        RoomDTO roomDTONotNull = new RoomDTO("roomId", "houseName", "roomName", 1, 1.0, 1.0, 1.0);
        RoomId roomIdWhenDTONotNull = roomMapper.toRoomId(roomDTONotNull);

        // Set up mock repository to return true when containsIdentity method is called with the expected roomId
        when(mockRoomRepository.containsIdentity(roomIdWhenDTONotNull)).thenReturn(true);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTONotNull);

        // Assert
        assertNotNull(result
                , "The result should not be null when the room DTO is not null.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should not throw an exception when the room DTO is valid.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDTOIsValidShouldNotThrowException() {
        // Arrange
        // Create a valid roomDTO
        RoomDTO validRoomDTO = new RoomDTO("RoomId1", "Sweet Home", "Master Bedroom", 1, 3.0, 5.0, 4.0);
        RoomId roomIdWhenDTONotNull = roomMapper.toRoomId(validRoomDTO);

        // Set up mock repository to return true when containsIdentity method is called with the expected roomId
        when(mockRoomRepository.containsIdentity(roomIdWhenDTONotNull)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> getDevicesInRoomController.getDevicesInRoom(validRoomDTO)
                , "The method should not throw an exception when the room DTO is valid.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return null when the room ID in the RoomDTO is null.
     */
    @Test
    void testGetDevicesInRoomWhenRoomIdIsNullShouldReturnNull() {
        // Arrange
        // Create a Room DTO with a null ID
        RoomDTO idInRoomDTOIsNull = new RoomDTO(null, "Sweet Home", "Master Bedroom", 1, 3.0, 5.0, 4.0);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(idInRoomDTOIsNull);

        // Assert
        assertNull(result, "The result should be null when the room ID in the RoomDTO is null.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return null when the room DTO is valid but
     * the room ID given in the RoomDTO does not match any of the room IDs in the repository.
     */
    @Test
    void testGetDevicesInRoomWhenRoomIdDoesNotMatchShouldReturnNull() {
        // Arrange
        // Create a room DTO that does not match any RoomId in the repository
        RoomDTO idInRoomDTONotMatching = new RoomDTO("RoomId1", "Sweet Home", "Master Bedroom", 1, 3.0, 5.0, 4.0);
        RoomId roomIdWhenIdNotMatching = roomMapper.toRoomId(idInRoomDTONotMatching);

        // Set up mock repository to return false when containsIdentity method is called with any RoomId
        when(mockRoomRepository.containsIdentity(roomIdWhenIdNotMatching)).thenReturn(false);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(idInRoomDTONotMatching);

        // Assert
        assertNull(result, "The list should be empty " +
                "when the room ID in the RoomDTO does not match any room ID in the repository.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return an empty list when the room chosen is the only room available, but it has no devices.
     */
    @Test
    void testGetDevicesInRoomWhenRoomHasNoDevicesShouldReturnAnEmptyList() {
        // Save the chosen room in the RoomRepository
        when(mockRoomRepository.containsIdentity(roomIdAsGiven)).thenReturn(true);
        when(mockDeviceRepository.findDevicesByRoomId(roomIdAsGiven)).thenReturn(Collections.emptyList());

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertTrue(result.isEmpty()
                , "The list should be empty when there are no devices in the chosen room.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return an empty list when there are other rooms with devices but the room chosen has no devices.
     */
    @Test
    void testGetDevicesInRoomWhenThereAreOtherRoomsWithDevicesButNoDevicesInChosenRoomShouldReturnEmptyList() {
        // Arrange
        // Mock the behaviour of RoomRepository
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);
        when(mockRoomRepository.containsIdentity(new RoomId("Different ID"))).thenReturn(true);

        // Mock the DeviceRepository to return no devices for the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(chosenRoom.getIdentity())).thenReturn(Collections.emptyList());

        // Create a different room and a device associated with it
        Room differentRoom = roomFactory.createRoom(new RoomId("Different ID"), roomName, ourHouse, floor, dimensions);
        Device deviceInDifferentRoom = deviceFactory.createDevice(new DeviceId("Id"), new DeviceName("Device"),
                new DeviceTypeName("Type"), differentRoom.getIdentity(), new DeviceStatus(true));

        // Save the different room and device in the repositories
        when(mockRoomRepository.save(differentRoom)).thenReturn(differentRoom);
        when(mockDeviceRepository.save(deviceInDifferentRoom)).thenReturn(deviceInDifferentRoom);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertTrue(result.isEmpty()
                , "The list should be empty when there are no devices in the chosen room.");

    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return a list with one item when there is one device in the chosen room.
     */
    @Test
    void testGetDevicesInRoomWhenThereIsOneDeviceInRoomShouldReturnAListWithOneDevice() {
        // Arrange
        // Mock the behaviour of the RoomRepository
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);

        // Create a list of devices in the chosen room
        List<Device> devicesInRoom = new ArrayList<>();
        devicesInRoom.add(deviceInChosenRoom);

        // Mock the behaviour of the DeviceRepository to return the list of devices in the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(chosenRoom.getIdentity())).thenReturn(devicesInRoom);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertEquals(1, result.size()
                , "The list should contain one device when there is one device in the chosen room.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return a list with the correct device object when there is one device in the chosen room.
     */
    @Test
    void testGetDevicesInRoomWhenThereIsOneDeviceInRoomShouldReturnAListWithTheCorrectDeviceObject() {
        // Arrange
        // Mock the behaviour of the RoomRepository
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);

        // Create a list of devices in the chosen room
        List<Device> devicesInRoom = new ArrayList<>();
        devicesInRoom.add(deviceInChosenRoom);

        // Mock the behaviour of the DeviceRepository to return the list of devices in the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(chosenRoom.getIdentity())).thenReturn(devicesInRoom);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertEquals(deviceInChosenRoom.getIdentity().getIdentity(), result.get(0).getDeviceId()
                , "The device in the chosen room should match the device in the repository.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return a list with the correct quantity of devices
     * when there are multiple devices in the chosen room.
     */
    @Test
    void testGetDevicesInRoomWhenMultipleDevicesInRoomShouldReturnAListWithMultipleDevices() {
        // Arrange
        // Mock the behaviour of the RoomRepository to return the chosen room
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);

        // Create a second device in the chosen room
        Device secondDeviceInChosenRoom = deviceFactory.createDevice(new DeviceId("DeviceId2"), deviceName,
                deviceType, roomIdAsGiven, deviceStatus);

        // Mock the behaviour for saving devices in the repository
        when(mockDeviceRepository.save(deviceInChosenRoom)).thenReturn(deviceInChosenRoom);
        when(mockDeviceRepository.save(secondDeviceInChosenRoom)).thenReturn(secondDeviceInChosenRoom);

        // Create a list of devices in the chosen room
        List<Device> devicesInRoom = new ArrayList<>();
        devicesInRoom.add(deviceInChosenRoom);
        devicesInRoom.add(secondDeviceInChosenRoom);

        // Mock the behaviour of the DeviceRepository to return the list of devices in the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(chosenRoom.getIdentity())).thenReturn(devicesInRoom);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertEquals(2, result.size()
                , "The list should contain two devices when there are two devices in the chosen room.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return a list with the correct multiple device objects
     * when there are multiple devices in the chosen room.
     */
    @Test
    void testGetDevicesInRoomWhenMultipleDevicesInRoomShouldReturnAListWithMultipleDevicesWithCorrectDeviceObjects() {
        // Arrange
        // Mock the behaviour of the RoomRepository to return the chosen room
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);

        // Create a second device in the chosen room
        Device secondDeviceInChosenRoom = deviceFactory.createDevice(new DeviceId("DeviceId2"), deviceName,
                deviceType, roomIdAsGiven, deviceStatus);

        // Mock the behaviour for saving devices in the repository
        when(mockDeviceRepository.save(deviceInChosenRoom)).thenReturn(deviceInChosenRoom);
        when(mockDeviceRepository.save(secondDeviceInChosenRoom)).thenReturn(secondDeviceInChosenRoom);

        // Create a list of devices in the chosen room
        List<Device> devicesInRoom = new ArrayList<>();
        devicesInRoom.add(deviceInChosenRoom);
        devicesInRoom.add(secondDeviceInChosenRoom);

        // Mock the behaviour of the DeviceRepository to return the list of devices in the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(chosenRoom.getIdentity())).thenReturn(devicesInRoom);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertEquals(deviceInChosenRoom.getIdentity().getIdentity(), result.get(0).getDeviceId());
        assertEquals(secondDeviceInChosenRoom.getIdentity().getIdentity(), result.get(1).getDeviceId()
                , "The second device in the chosen room should match the second device in the repository.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return a list with the correct device object
     * when there is one device in the chosen room and other devices in other rooms.
     */
    @Test
    void testGetDevicesInRoomWhenThereIsOneDeviceInChosenRoomAndOtherDevicesInOtherRoomsShouldReturnCorrectList() {
        // Arrange
        // Mock the behaviour of the RoomRepository to return the chosen room
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);

        // Mock the behaviour for saving devices in the repository
        when(mockDeviceRepository.save(deviceInChosenRoom)).thenReturn(deviceInChosenRoom);

        // Mock the behaviour of the RoomRepository to return a different room
        when(mockRoomRepository.containsIdentity(new RoomId("Different ID"))).thenReturn(true);

        // Create a different room and a device associated with it
        Room differentRoom = roomFactory.createRoom(new RoomId("Different ID"), roomName, ourHouse, floor, dimensions);
        Device deviceInDifferentRoom = deviceFactory.createDevice(new DeviceId("Id"), new DeviceName("Device"),
                new DeviceTypeName("Type"), differentRoom.getIdentity(), new DeviceStatus(true));

        // Save the different room and device in the repositories
        when(mockRoomRepository.save(differentRoom)).thenReturn(differentRoom);
        when(mockDeviceRepository.save(deviceInDifferentRoom)).thenReturn(deviceInDifferentRoom);

        // Create a list of devices in the chosen room
        List<Device> devicesInRoom = new ArrayList<>();
        devicesInRoom.add(deviceInChosenRoom);
        devicesInRoom.add(deviceInDifferentRoom);

        // Mock the behavior of the DeviceRepository to return the list of devices in the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(chosenRoom.getIdentity())).thenReturn(devicesInRoom);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertEquals(deviceInChosenRoom.getIdentity().getIdentity(), result.get(0).getDeviceId()
                , "The device in the chosen room should match the device in the repository.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The method should return a list with the correct device objects
     * when there are multiple devices in the chosen room and other devices in other rooms.
     */
    @Test
    void testGetDevicesInRoomWhenThereAreMultDevicesInChosenRoomAndOtherDevicesInOtherRoomsShouldReturnCorrectList() {
        // Arrange
        // Mock the behaviour of the RoomRepository to return the chosen room
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);
        // Create a second device in the chosen room
        Device secondDeviceInChosenRoom = deviceFactory.createDevice(new DeviceId("DeviceId2"), deviceName,
                deviceType, roomIdAsGiven, deviceStatus);

        // Mock the behaviour for saving devices in the repository
        when(mockDeviceRepository.save(deviceInChosenRoom)).thenReturn(deviceInChosenRoom);
        when(mockDeviceRepository.save(secondDeviceInChosenRoom)).thenReturn(secondDeviceInChosenRoom);

        // Mock the behaviour of the RoomRepository to return a different room
        when(mockRoomRepository.containsIdentity(new RoomId("Different ID"))).thenReturn(true);

        // Create a different room and a device associated with it
        Room differentRoom = roomFactory.createRoom(new RoomId("Different ID"), roomName, ourHouse, floor, dimensions);
        Device deviceInDifferentRoom = deviceFactory.createDevice(new DeviceId("Id"), new DeviceName("Device"),
                new DeviceTypeName("Type"), differentRoom.getIdentity(), new DeviceStatus(true));

        // Save the different room and device in the repositories
        when(mockRoomRepository.save(differentRoom)).thenReturn(differentRoom);
        when(mockDeviceRepository.save(deviceInDifferentRoom)).thenReturn(deviceInDifferentRoom);

        // Create a list of devices in the chosen room
        List<Device> devicesInRoom = new ArrayList<>();
        devicesInRoom.add(deviceInChosenRoom);
        devicesInRoom.add(secondDeviceInChosenRoom);
        devicesInRoom.add(deviceInDifferentRoom);

        // Mock the behavior of the DeviceRepository to return the list of devices in the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(chosenRoom.getIdentity())).thenReturn(devicesInRoom);

        // Act
        List<DeviceDTO> result = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Assert
        assertEquals(deviceInChosenRoom.getIdentity().getIdentity(), result.get(0).getDeviceId()
                , "The device in the chosen room should match the device in the repository.");
        assertEquals(secondDeviceInChosenRoom.getIdentity().getIdentity(), result.get(1).getDeviceId()
                , "The device in the chosen room should match the device in the repository.");
    }

    /**
     * Test the getDevicesInRoom method of the GetDevicesInRoomController class.
     * The test compares the device in the chosen room with the device in the repository.
     * The device in the chosen room should match the device in the repository.
     */
    @Test
    void testGetDevicesInRoomMatchesRepository() {
        // Arrange
        // Mock the behaviour of the RoomRepository to return the chosen room
        when(mockRoomRepository.save(chosenRoom)).thenReturn(chosenRoom);
        when(mockRoomRepository.containsIdentity(chosenRoom.getIdentity())).thenReturn(true);

        // Create a list of devices containing only the device in the chosen room
        List<Device> devicesInChosenRoom = new ArrayList<>();
        devicesInChosenRoom.add(deviceInChosenRoom);

        // Mock the behaviour of the DeviceRepository to return the list of devices in the chosen room
        when(mockDeviceRepository.findDevicesByRoomId(roomIdAsGiven)).thenReturn(devicesInChosenRoom);

        // Act
        List<DeviceDTO> controllerResult = getDevicesInRoomController.getDevicesInRoom(roomDTOAsGiven);

        // Get the expected result from the repository and map to DTOs
        List<DeviceDTO> expectedRepositoryResult = deviceMapper.toDevicesDTO(devicesInChosenRoom);

        // Assert
        assertEquals(expectedRepositoryResult.get(0).getDeviceId(), controllerResult.get(0).getDeviceId()
                , "The device in the chosen room should match the device in the repository.");
    }
}


