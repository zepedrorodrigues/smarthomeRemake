package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;
import smarthome.service.IDeviceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The DeviceServiceImplTest class tests the methods in the DeviceServiceImpl class.
 * It uses the Mockito framework to guarantee that the tests are isolated and that the
 * dependencies are mocked.
 */
class DeviceServiceImplTest {

    IDeviceService deviceService;

    // Dependencies for the Device Service
    IDeviceRepository mockDeviceRepository;
    ISensorRepository mockSensorRepository;
    ISensorModelRepository mockSensorModelRepository;
    ISensorTypeRepository mockSensorTypeRepository;
    DeviceFactory mockDeviceFactory;
    IRoomRepository mockRoomRepository;
    IDeviceTypeRepository mockDeviceTypeRepository;

    // Sensor variables
    SensorModel sensorModelHumidity;
    SensorModel sensorModelTemperature;
    SensorModelName sensorModelNameHumidity;
    SensorModelName sensorModelNameTemperature;
    SensorTypeName sensorTypeNameTemperature;
    SensorTypeUnit sensorTypeUnitTemperature;
    SensorTypeId sensorTypeId;
    SensorTypeName sensorTypeNameHumidity;
    SensorTypeUnit sensorTypeUnitHumidity;
    SensorTypeId sensorTypeId2;
    SensorType sensorTypeTemperature;
    SensorType sensorTypeHumidity;
    String temperature;
    String celsius;
    String humidity;
    String percentage;
    String sensorOfTemperature;
    String sensorOfHumidity;
    String sensorTypeIdTemperature;
    String sensorTypeIdHumidity;

    // Device variables
    Device device;
    DeviceName deviceName;
    DeviceTypeName deviceType;
    RoomId roomId;
    DeviceId deviceId;
    Device device2;
    DeviceName deviceName2;
    DeviceTypeName deviceType2;
    RoomId roomId2;
    DeviceId deviceId2;

    /**
     * The setUp method.
     * It initializes the mock repositories, mappers, factory and the DeviceServiceImpl.
     */
    @BeforeEach
    void setUp() {
        //Mock the repositories
        mockRoomRepository = mock(IRoomRepository.class);
        mockDeviceRepository = mock(IDeviceRepository.class);
        mockSensorTypeRepository = mock(ISensorTypeRepository.class);
        mockSensorModelRepository = mock(ISensorModelRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockDeviceTypeRepository = mock(IDeviceTypeRepository.class);

        //Mock the factory
        mockDeviceFactory = mock(DeviceFactory.class);

        // Mock the Devices and their attributes
        device = mock(Device.class);
        deviceName = mock(DeviceName.class);
        deviceType = mock(DeviceTypeName.class);
        roomId = mock(RoomId.class);
        deviceId = mock(DeviceId.class);
        when(device.getIdentity()).thenReturn(deviceId);
        when(device.getDeviceName()).thenReturn(deviceName);
        when(device.getDeviceTypeName()).thenReturn(deviceType);
        when(device.getRoomId()).thenReturn(roomId);

        device2 = mock(Device.class);
        deviceName2 = mock(DeviceName.class);
        deviceType2 = mock(DeviceTypeName.class);
        roomId2 = mock(RoomId.class);
        deviceId2 = mock(DeviceId.class);
        when(device2.getIdentity()).thenReturn(deviceId2);
        when(device2.getDeviceName()).thenReturn(deviceName2);
        when(device2.getDeviceTypeName()).thenReturn(deviceType2);
        when(device2.getRoomId()).thenReturn(roomId2);

        // Initialize the DeviceServiceImpl
        deviceService = new DeviceServiceImpl(mockRoomRepository, mockDeviceFactory, mockDeviceRepository,
                mockSensorTypeRepository, mockSensorModelRepository, mockSensorRepository, mockDeviceTypeRepository);

        //Mock SensorType to fill the Repositories
        temperature = "Temperature";
        celsius = "Celsius";
        sensorTypeIdTemperature = temperature + celsius;
        humidity = "Humidity";
        percentage = "%";
        sensorOfTemperature = "SensorOfTemperature";
        sensorOfHumidity = "SensorOfHumidity";
        sensorTypeIdHumidity = humidity + percentage;
        sensorTypeNameTemperature = mock(SensorTypeName.class);
        when(sensorTypeNameTemperature.getSensorTypeName()).thenReturn(temperature);
        sensorTypeUnitTemperature = mock(SensorTypeUnit.class);
        when(sensorTypeUnitTemperature.getSensorTypeUnit()).thenReturn(celsius);
        sensorTypeId = mock(SensorTypeId.class);
        when(sensorTypeId.getSensorTypeId()).thenReturn(sensorTypeIdTemperature);
        sensorTypeNameHumidity = mock(SensorTypeName.class);
        when(sensorTypeNameHumidity.getSensorTypeName()).thenReturn(humidity);
        sensorTypeUnitHumidity = mock(SensorTypeUnit.class);
        when(sensorTypeUnitHumidity.getSensorTypeUnit()).thenReturn(percentage);
        sensorTypeId2 = mock(SensorTypeId.class);
        when(sensorTypeId2.getSensorTypeId()).thenReturn(sensorTypeIdHumidity);
        sensorTypeTemperature = mock(SensorType.class);
        sensorTypeHumidity = mock(SensorType.class);
        when(sensorTypeTemperature.getSensorTypeName()).thenReturn(sensorTypeNameTemperature);
        when(sensorTypeTemperature.getSensorTypeUnit()).thenReturn(sensorTypeUnitTemperature);
        when(sensorTypeTemperature.getIdentity()).thenReturn(sensorTypeId);
        when(sensorTypeHumidity.getSensorTypeName()).thenReturn(sensorTypeNameHumidity);
        when(sensorTypeHumidity.getSensorTypeUnit()).thenReturn(sensorTypeUnitHumidity);
        when(sensorTypeHumidity.getIdentity()).thenReturn(sensorTypeId2);
        when(mockSensorTypeRepository.findAll()).thenReturn(List.of(sensorTypeTemperature, sensorTypeHumidity));
        when(mockSensorTypeRepository.findByIdentity(sensorTypeId)).thenReturn(Optional.of(sensorTypeTemperature));
        when(mockSensorTypeRepository.findByIdentity(sensorTypeId2)).thenReturn(Optional.of(sensorTypeHumidity));
        when(mockSensorTypeRepository.findAll()).thenReturn(List.of(sensorTypeTemperature, sensorTypeHumidity));

        //Mock SensorModel to fill the Repositories
        sensorModelNameTemperature = mock(SensorModelName.class);
        when(sensorModelNameTemperature.getSensorModelName()).thenReturn(sensorOfTemperature);
        sensorModelNameHumidity = mock(SensorModelName.class);
        when(sensorModelNameHumidity.getSensorModelName()).thenReturn(sensorOfHumidity);
        sensorModelTemperature = mock(SensorModel.class);
        when(sensorModelTemperature.getIdentity()).thenReturn(sensorModelNameTemperature);
        when(sensorModelTemperature.getSensorTypeId()).thenReturn(sensorTypeId);
        sensorModelHumidity = mock(SensorModel.class);
        when(sensorModelHumidity.getIdentity()).thenReturn(sensorModelNameHumidity);
        when(sensorModelHumidity.getSensorTypeId()).thenReturn(sensorTypeId2);
        when(mockSensorModelRepository.findAll()).thenReturn(List.of(sensorModelTemperature, sensorModelHumidity));
        when(mockSensorModelRepository.findByIdentity(sensorModelNameTemperature)).thenReturn(Optional.of(sensorModelTemperature));
        when(mockSensorModelRepository.findByIdentity(sensorModelNameHumidity)).thenReturn(Optional.of(sensorModelHumidity));
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns a non-null Device when the arguments are valid.
     * The test passes if the result is not null.
     */
    @Test
    void testAddDeviceToRoomShouldBeNotNullWhenValidArguments() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceTypeRepository.containsIdentity(deviceType)).thenReturn(true);
        when(mockDeviceFactory.createDevice(deviceName, deviceType, roomId)).thenReturn(device);
        when(mockDeviceRepository.save(device)).thenReturn(device);

        // Act
        Device result = deviceService.addDeviceToRoom(deviceName, deviceType, roomId);

        // Assert
        assertNotNull(result, "The result should not be null when the arguments are valid");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns null when the room does not exist.
     * The test passes if the result is null.
     */
    @Test
    void testAddDeviceToRoomShouldBeNullWhenRoomDoesNotExist() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(false);

        // Act
        Device result = deviceService.addDeviceToRoom(deviceName, deviceType, roomId);

        // Assert
        assertNull(result, "The result should be null when the room does not exist");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns null when the device type does not exist.
     * The test passes if the result is null.
     */
    @Test
    void testAddDeviceToRoomShouldBeNullWhenDeviceTypeDoesNotExist() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceTypeRepository.containsIdentity(deviceType)).thenReturn(false);

        // Act
        Device result = deviceService.addDeviceToRoom(deviceName, deviceType, roomId);

        // Assert
        assertNull(result, "The result should be null when the device type does not exist");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns null when an exception is thrown.
     * The test passes if the result is null.
     */
    @Test
    void testAddDeviceToRoomShouldBeNullWhenInvalidArguments() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceTypeRepository.containsIdentity(deviceType)).thenReturn(true);
        when(mockDeviceFactory.createDevice(deviceName, deviceType, roomId)).thenThrow(new IllegalArgumentException());

        // Act
        Device result = deviceService.addDeviceToRoom(deviceName, deviceType, roomId);

        // Assert
        assertNull(result, "The result should be null when the arguments are invalid");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when the room does not exist.
     * This is tested by checking if the room repository contains the room identity.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDoesNotExistShouldReturnNull() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(false);
        when(mockDeviceRepository.findDevicesByRoomId(roomId)).thenReturn(List.of(device));

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(roomId);

        // Assert
        assertNull(result, "The result should be null when the room does not exist");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when the room id is null.
     * This is tested by checking if the room id is null.
     */
    @Test
    void testGetDevicesInRoomWhenRoomIdIsNullShouldReturnNull() {
        // Arrange
        RoomId nullRoomId = null;
        when(mockRoomRepository.containsIdentity(nullRoomId)).thenReturn(false);

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(nullRoomId);

        // Assert
        assertNull(result, "The result should be null when the room id is null");
    }

    /**
     * Test for the getDevicesInRoom method.
     * This test asserts that the method returns a non-null Iterable when the room exists.
     * The test pass if the result is not null.
     */
    @Test
    void testGetDevicesInRoomShouldBeNotNullWhenRoomExists() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDevicesByRoomId(roomId)).thenReturn(List.of(device));

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(roomId);

        // Assert
        assertNotNull(result, "The result should not be null when the room exists");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns the devices in the room when the room exists.
     * This is tested by checking if the room repository contains the room identity and if the device repository
     * returns the devices in the room.
     */
    @Test
    void testGetDevicesInRoomWhenRoomContainsDevices() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);

        // Mocking devices in the room
        List<Device> devices = new ArrayList<>();
        devices.add(mock(Device.class));
        devices.add(mock(Device.class));
        when(mockDeviceRepository.findDevicesByRoomId(roomId)).thenReturn(devices);

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(roomId);

        // Convert the iterable to a list
        List<Device> resultList = StreamSupport.stream(result.spliterator(), false)
                .collect(Collectors.toList());

        // Assert
        assertEquals(2, resultList.size(), "The result should contain the devices in the room");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns an empty list when the room exists but does not contain
     * any devices.
     * This is tested by checking if the room repository contains the room identity and if the device repository
     * returns an empty list of devices in the room.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDoesNotContainDevicesShouldReturnEmptyList() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDevicesByRoomId(roomId)).thenReturn(List.of());

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(roomId);

        // Convert the iterable to a list
        List<Device> resultList = StreamSupport.stream(result.spliterator(), false)
                .collect(Collectors.toList());

        // Assert
        assertTrue(resultList.isEmpty(), "The result should be an empty list when the room does not contain devices");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when an exception is thrown.
     * This is tested by checking if the device repository throws an exception when retrieving the devices in the room.
     */
    @Test
    void testGetDevicesInRoomWhenExceptionThrownShouldReturnNull() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDevicesByRoomId(roomId)).thenThrow(new IllegalArgumentException());

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(roomId);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when an exception is thrown.
     * This is tested by checking if the device repository throws an exception when retrieving the devices in the room.
     */
    @Test
    void testGetDevicesInRoomWhenExceptionThrownForNullRoomIdInDTOShouldReturnNull() {
        // Arrange
        RoomId nullRoomId = null;
        when(mockRoomRepository.containsIdentity(nullRoomId)).thenReturn(true);
        when(mockDeviceRepository.findDevicesByRoomId(nullRoomId)).thenThrow(new IllegalArgumentException());

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(nullRoomId);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when an exception is thrown.
     * This is tested by checking if the device repository throws an exception when retrieving the devices in the room the Identity does not exist.
     */
    @Test
    void testGetDevicesInRoomWhenExceptionThrownForNoIdInRepositoryShouldReturnNull() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDevicesByRoomId(roomId)).thenThrow(new IllegalArgumentException());

        // Act
        Iterable<Device> result = deviceService.getDevicesInRoom(roomId);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * Test to ensure that the getDeviceIdsInRoom method returns null when the room does not exist.
     * This is tested by checking if the room repository contains the room identity.
     */
    @Test
    void testGetDeviceIdsInRoomWhenRoomDoesNotExistShouldReturnNull() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(false);
        when(mockDeviceRepository.findDeviceIdsByRoomId(roomId)).thenReturn(List.of(deviceId));

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(roomId);

        // Assert
        assertNull(result, "The result should be null when the room does not exist");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when the room id is null.
     * This is tested by checking if the room id is null.
     */
    @Test
    void testGetDeviceIdsInRoomWhenRoomIdIsNullShouldReturnNull() {
        // Arrange
        RoomId nullRoomId = null;
        when(mockRoomRepository.containsIdentity(nullRoomId)).thenReturn(false);

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(nullRoomId);

        // Assert
        assertNull(result, "The result should be null when the room id is null");
    }

    /**
     * Test for the getDeviceIdsInRoom method.
     * This test asserts that the method returns a non-null Iterable when the room exists.
     * The test pass if the result is not null.
     */
    @Test
    void testGetDeviceIdsInRoomShouldBeNotNullWhenRoomExists() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDeviceIdsByRoomId(roomId)).thenReturn(List.of(deviceId));

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(roomId);

        // Assert
        assertNotNull(result, "The result should not be null when the room exists");
    }

    /**
     * Test to ensure that the getDeviceIdsInRoom method returns the device IDs in the room when the room exists.
     * This is tested by checking if the room repository contains the room identity and if the device repository
     * returns the device IDs in the room.
     */
    @Test
    void testGetDeviceIdsInRoomWhenRoomContainsDevices() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);

        // Mocking device IDs in the room
        List<DeviceId> deviceIds = new ArrayList<>();
        deviceIds.add(mock(DeviceId.class));
        deviceIds.add(mock(DeviceId.class));
        when(mockDeviceRepository.findDeviceIdsByRoomId(roomId)).thenReturn(deviceIds);

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(roomId);

        // Convert the iterable to a list
        List<DeviceId> resultList = StreamSupport.stream(result.spliterator(), false)
                .collect(Collectors.toList());

        // Assert
        assertEquals(2, resultList.size(), "The result should contain the device IDs in the room");
    }

    /**
     * Test to ensure that the getDeviceIdsInRoom method returns an empty list when the room exists but does not contain
     * any devices.
     * This is tested by checking if the room repository contains the room identity and if the device repository
     * returns an empty list of device IDs in the room.
     */
    @Test
    void testGetDeviceIdsInRoomWhenRoomDoesNotContainDevicesShouldReturnEmptyList() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDeviceIdsByRoomId(roomId)).thenReturn(List.of());

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(roomId);

        // Convert the iterable to a list
        List<DeviceId> resultList = StreamSupport.stream(result.spliterator(), false)
                .collect(Collectors.toList());

        // Assert
        assertTrue(resultList.isEmpty(), "The result should be an empty list when the room does not contain device IDs");
    }

    /**
     * Test to ensure that the getDeviceIdsInRoom method returns null when an exception is thrown.
     * This is tested by checking if the device repository throws an exception when retrieving the device IDs in the room.
     */
    @Test
    void testGetDeviceIdsInRoomWhenExceptionThrownShouldReturnNull() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDeviceIdsByRoomId(roomId)).thenThrow(new IllegalArgumentException());

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(roomId);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * Test to ensure that the getDeviceIdsInRoom method returns null when an exception is thrown.
     * This is tested by checking if the device repository throws an exception when retrieving the device IDs in the room.
     */
    @Test
    void testGetDeviceIdsInRoomWhenExceptionThrownForNullRoomIdInDTOShouldReturnNull() {
        // Arrange
        RoomId nullRoomId = null;
        when(mockRoomRepository.containsIdentity(nullRoomId)).thenReturn(true);
        when(mockDeviceRepository.findDeviceIdsByRoomId(nullRoomId)).thenThrow(new IllegalArgumentException());

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(nullRoomId);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * Test to ensure that the getDeviceIdsInRoom method returns null when an exception is thrown.
     * This is tested by checking if the device repository throws an exception when retrieving the device IDs in the room the Identity does not exist.
     */
    @Test
    void testGetDeviceIdsInRoomWhenExceptionThrownForNoIdInRepositoryShouldReturnNull() {
        // Arrange
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findDeviceIdsByRoomId(roomId)).thenThrow(new IllegalArgumentException());

        // Act
        Iterable<DeviceId> result = deviceService.getDeviceIdsInRoom(roomId);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * Test the deactivateDevice method when the device is present. The result should not be null. The device should be
     * deactivated. The method should return the deactivated device.
     */
    @Test
    void testDeactivateDeviceWhenDevicePresent() {
        // Arrange
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(device));
        when(mockDeviceRepository.update(device)).thenReturn(device);

        // Act
        Device result = deviceService.deactivateDevice(deviceId);

        // Assert
        assertNotNull(result, "The result should not be null when the device is present");
    }

    /**
     * Test the deactivateDevice method when the device is not present. The result should be null. The method should
     * return null. The device should not be deactivated.
     */
    @Test
    void testDeactivateDeviceWhenDeviceNotPresent() {
        // Arrange
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.empty());

        // Act
        Device result = deviceService.deactivateDevice(deviceId);

        // Assert
        assertNull(result, "The result should be null when the device is not present");
    }

    /**
     * Test the deactivateDevice method when an exception is thrown. The result should be null. The exception should be
     * caught and handled. The method should return null. The device should not be deactivated.
     */
    @Test
    void testDeactivateDeviceWhenExceptionThrown() {
        // Arrange
        when(mockDeviceRepository.findByIdentity(deviceId)).thenThrow(new IllegalArgumentException());

        // Act
        Device result = deviceService.deactivateDevice(deviceId);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * The testGetDevicesBySensorType method. It tests the getDevicesBySensorType method.
     * It should return a HashMap with the devices categorized by their sensor types. In this case, it should be an
     * empty HashMap.
     */
    @Test
    void testGetDevicesBySensorTypeWithNoDevices() throws IllegalAccessException {
        // Arrange
        when(mockDeviceRepository.findAll()).thenReturn(List.of());
        // Act
        HashMap<String, List<Device>> result = deviceService.getDevicesBySensorType();
        // Assert
        assertEquals(0, result.size(), "The result should be an empty HashMap");
    }

    /**
     * The testGetDevicesBySensorType method. It tests the getDevicesBySensorType method.
     * It should return a HashMap with the devices categorized by their sensor types. In this case, it should be an
     * empty HashMap.
     */
    @Test
    void testGetDevicesBySensorType2DevicesNoSensorsOrFunctionalitiesShouldReturnEmptyHashMap() throws IllegalAccessException {
        // Arrange
        when(mockDeviceRepository.findAll()).thenReturn(List.of(device, device));
        // Act
        HashMap<String, List<Device>> result = deviceService.getDevicesBySensorType();
        // Assert
        assertEquals(0, result.size(), "The result should be an empty HashMap");
    }

    /**
     * The testGetDevicesBySensorType method. It tests the getDevicesBySensorType method.
     * It should return a HashMap with the devices categorized by their sensor types. In this case, it should be a
     * HashMap with one entry.
     */
    @Test
    void testGetDevicesBySensorType2DevicesWithSensorsOfTemperatureShouldReturnHashMapWithOneEntry() throws IllegalAccessException {
        // Arrange
        Sensor sensor = mock(Sensor.class);
        when(sensor.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensor.getDeviceId()).thenReturn(deviceId);
        Sensor sensor2 = mock(Sensor.class);
        when(sensor2.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensor2.getDeviceId()).thenReturn(deviceId2);
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensor, sensor2));
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(device));
        when(mockDeviceRepository.findByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        // Act
        HashMap<String, List<Device>> result = deviceService.getDevicesBySensorType();
        // Assert
        assertEquals(1, result.size(), "The result should be a HashMap with one entry");
        assertTrue(result.containsKey(temperature), "The result should contain the key 'Temperature'");
        assertFalse(result.containsKey(humidity), "The result should not contain the key 'Humidity'");
        assertEquals(2, result.get(temperature).size(), "The result should contain 2 devices");
        assertTrue(result.get(temperature).contains(device), "The result should contain the device");
        assertTrue(result.get(temperature).contains(device2), "The result should contain the device");
    }

    /**
     * The testGetDevicesBySensorType method. It tests the getDevicesBySensorType method.
     * It should return a HashMap with the devices categorized by their sensor types. In this case, it should be a
     * HashMap with two entries.
     */
    @Test
    void testGetDevicesBySensorType2DevicesWithSensorsOfTemperatureAndHumidityShouldReturnHashMapWithTwoEntries() throws IllegalAccessException {
        // Arrange
        Sensor sensor = mock(Sensor.class);
        when(sensor.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensor.getDeviceId()).thenReturn(deviceId);
        Sensor sensor2 = mock(Sensor.class);
        when(sensor2.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(sensor2.getDeviceId()).thenReturn(deviceId2);
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensor, sensor2));
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(device));
        when(mockDeviceRepository.findByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        // Act
        HashMap<String, List<Device>> result = deviceService.getDevicesBySensorType();
        // Assert
        assertEquals(2, result.size(), "The result should be a HashMap with two entries");
        assertTrue(result.containsKey(temperature), "The result should contain the key 'Temperature'");
        assertTrue(result.containsKey(humidity), "The result should contain the key 'Humidity'");
        assertEquals(1, result.get(temperature).size(), "The result should contain 1 device");
        assertTrue(result.get(temperature).contains(device), "The result should contain the device");
        assertEquals(1, result.get(humidity).size(), "The result should contain 1 device");
        assertTrue(result.get(humidity).contains(device2), "The result should contain the device");
    }

    /**
     * The testGetDevicesBySensorType method. It tests the getDevicesBySensorType method.
     * It should return a HashMap with the devices categorized by their sensor types. In this case, it should be a
     * HashMap with two entries
     * and the same device should be in both entries.
     */
    @Test
    void testGetDevicesBySensorType2DevicesOnlyOneWithSensorOfTempAndHumidityShouldReturnHashMapWithOneEntry() throws IllegalAccessException {
        // Arrange
        Sensor sensor = mock(Sensor.class);
        when(sensor.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensor.getDeviceId()).thenReturn(deviceId);
        Sensor sensor2 = mock(Sensor.class);
        when(sensor2.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(sensor2.getDeviceId()).thenReturn(deviceId);
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensor, sensor2));
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(device));
        when(mockDeviceRepository.findByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        // Act
        HashMap<String, List<Device>> result = deviceService.getDevicesBySensorType();
        // Assert
        assertEquals(2, result.size(), "The result should be a HashMap with one entry");
        assertTrue(result.containsKey(temperature), "The result should contain the key 'Temperature'");
        assertTrue(result.containsKey(humidity), "The result should not contain the key 'Humidity'");
        assertEquals(1, result.get(temperature).size(), "The result should contain 1 device");
        assertTrue(result.get(temperature).contains(device), "The result should contain the device");
        assertTrue(result.get(humidity).contains(device), "The result should contain the device");
    }

    /**
     * The testGetDevicesBySensorType method. It tests the getDevicesBySensorType method.
     * It should return a HashMap with the devices categorized by their sensor types. In this case, it should be a
     * HashMap with one entry, containing only one device.
     */
    @Test
    void testGetDevicesBySensorType2DevicesOneHas2SensorsOfHumidityShouldReturnHashMapWithOneEntry() throws IllegalAccessException {
        // Arrange
        Sensor sensor = mock(Sensor.class);
        when(sensor.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(sensor.getDeviceId()).thenReturn(deviceId);
        Sensor sensor2 = mock(Sensor.class);
        when(sensor2.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(sensor2.getDeviceId()).thenReturn(deviceId);
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensor, sensor2));
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(device));
        when(mockDeviceRepository.findByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        // Act
        HashMap<String, List<Device>> result = deviceService.getDevicesBySensorType();
        // Assert
        assertEquals(1, result.size(), "The result should be a HashMap with one entry");
        assertTrue(result.containsKey(humidity), "The result should contain the key 'Humidity'");
        assertEquals(1, result.get(humidity).size(), "The result should contain 1 device");
        assertTrue(result.get(humidity).contains(device), "The result should contain the device");
        assertFalse(result.get(humidity).contains(device2), "The result should not contain the device2");
    }

    @Test
    void testGetDevicesBySensorType2DevicesOneInEachRoomOneHasTemperatureSensorOtherHasHumidityTheyShouldHaveDifferentRoomIds() throws IllegalAccessException {
        // Arrange
        Sensor sensor = mock(Sensor.class);
        when(sensor.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensor.getDeviceId()).thenReturn(deviceId);
        Sensor sensor2 = mock(Sensor.class);
        when(sensor2.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(sensor2.getDeviceId()).thenReturn(deviceId2);
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensor, sensor2));
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(device));
        when(mockDeviceRepository.findByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        // Act
        HashMap<String, List<Device>> result = deviceService.getDevicesBySensorType();
        // Assert
        assertEquals(2, result.size(), "The result should be a HashMap with two entries");
        assertTrue(result.containsKey(temperature) && result.containsKey(humidity), "The result should contain the " +
                "key 'Temperature' and the key 'Humidity'");
        assertTrue(result.get(temperature).contains(device), "The result should contain the device");
        assertFalse(result.get(temperature).contains(device2), "The result should not contain the device");
        assertTrue(result.get(humidity).contains(device2), "The result should contain the device");
        assertFalse(result.get(humidity).contains(device), "The result should not contain the device");
        assertTrue(result.get(temperature).stream().anyMatch(device -> device.getRoomId().equals(roomId)), "The " +
                "device should be in room 1");
        assertTrue(result.get(humidity).stream().anyMatch(device -> device.getRoomId().equals(roomId2)), "The device2" +
                " should be in room 2");
    }

    /**
     * This test case verifies that the getDeviceById method in the DeviceServiceImpl class returns the expected device
     * when the device exists in the repository.
     */
    @Test
    void testGetDeviceByIdShouldReturnDeviceWhenDeviceExists() {
        // Arrange
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(device));
        Device expectedDevice = device;
        // Act
        Optional<Device> optionalDevice = deviceService.getDeviceById(deviceId);
        // Assert
        Device result = optionalDevice.orElseThrow();
        assertEquals(expectedDevice, result, "The result should be the device");
    }

    /**
     * This test case verifies that the getDeviceById method in the DeviceServiceImpl class returns an empty optional
     * when the device does not exist in the repository.
     */
    @Test
    void testGetDeviceByIdShouldReturnEmptyWhenDeviceDoesNotExist() {
        // Arrange
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.empty());
        // Act
        Optional<Device> optionalDevice = deviceService.getDeviceById(deviceId);
        // Assert
        boolean result = optionalDevice.isEmpty();
        assertTrue(result, "The result should be empty when the device does not exist");
    }

    /**
     * This test case verifies that theFindDeviceIds method in the DeviceServiceImpl class returns an empty list when
     * the repository is empty.
     */
    @Test
    void testFindDeviceIdsEmptyRepository() {
        // Arrange
        int expectedSize = 0;

        // Act
        List<DeviceId> result = deviceService.findDeviceIds();

        // Assert
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize, "The result should be an empty list");
    }

    /**
     * This test case verifies that the findDeviceIds method in the DeviceServiceImpl class returns a list with one
     * device id when the repository contains one device.
     */
    @Test
    void testFindDeviceIdsWithOneRoomInRepository() {
        // Arrange
        int expectedSize = 1;
        when(mockDeviceRepository.findDeviceIds()).thenReturn(List.of(deviceId));

        // Act
        List<DeviceId> result = deviceService.findDeviceIds();

        // Assert
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize, "The result should contain one device id");
    }

    /**
     * This test case verifies that the findDeviceIds method in the DeviceServiceImpl class returns a list with two
     * device ids when the repository contains two devices.
     */
    @Test
    void testFindDeviceIdsWithTwoRoomsInRepository() {
        // Arrange
        int expectedSize = 2;
        when(mockDeviceRepository.findDeviceIds()).thenReturn(List.of(deviceId, deviceId2));

        // Act
        List<DeviceId> result = deviceService.findDeviceIds();

        // Assert
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize, "The result should contain two device ids");
    }
}