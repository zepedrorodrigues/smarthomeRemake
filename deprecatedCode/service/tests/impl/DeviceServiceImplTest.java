package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.device.vo.DeviceType;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorOfHumidity;
import smarthome.domain.sensor.SensorOfTemperature;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.PeriodMapper;
import smarthome.mapper.mapper.ReadingMapper;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IDeviceService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    DeviceMapper mockDeviceMapper;
    DeviceFactory mockDeviceFactory;
    RoomMapper mockRoomMapper;
    IRoomRepository mockRoomRepository;
    IReadingRepository mockReadingRepository;
    PeriodMapper mockPeriodMapper;
    ReadingMapper mockReadingMapper;

    // Sensor variables
    Sensor sensor;
    SensorId sensorId;
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
    DeviceDTO deviceDTO;
    DeviceName deviceName;
    DeviceType deviceType;
    RoomId roomId;
    DeviceId deviceId;

    // Room variables
    RoomDTO roomDTO;

    // Reading variables
    Reading reading;
    PeriodDTO periodDTO;
    TimeStamp start;
    TimeStamp end;

    /**
     * The setUp method.
     * It initializes the mock repositories, mappers, factory and the DeviceServiceImpl.
     */
    @BeforeEach
    void setUp() {
        //Mock the repositories
        mockDeviceRepository = mock(IDeviceRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockSensorModelRepository = mock(ISensorModelRepository.class);
        mockSensorTypeRepository = mock(ISensorTypeRepository.class);
        mockRoomRepository = mock(IRoomRepository.class);
        mockReadingRepository = mock(IReadingRepository.class);

        //Mock the mapper
        mockDeviceMapper = mock(DeviceMapper.class);
        mockRoomMapper = mock(RoomMapper.class);
        mockPeriodMapper = mock(PeriodMapper.class);
        mockReadingMapper = mock(ReadingMapper.class);

        //Mock the factory
        mockDeviceFactory = mock(DeviceFactory.class);

        // Mock the Device, DeviceDTO and its attributes
        device = mock(Device.class);
        deviceDTO = mock(DeviceDTO.class);
        deviceName = mock(DeviceName.class);
        deviceType = mock(DeviceType.class);
        roomId = mock(RoomId.class);
        deviceId = mock(DeviceId.class);

        // Mock the Reading, ReadingDTO, PeriodDTO and its attributes
        reading = mock(Reading.class);
        periodDTO = mock(PeriodDTO.class);
        start = mock(TimeStamp.class);
        end = mock(TimeStamp.class);

        // Initialize the DeviceServiceImpl
        deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository, mockSensorModelRepository,
                mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory, mockRoomMapper, mockRoomRepository,
                mockReadingRepository, mockPeriodMapper, mockReadingMapper);

        // Mock RoomDTO
        roomDTO = mock(RoomDTO.class);

        // Mock Sensor
        sensor = mock(Sensor.class);
        sensorId = mock(SensorId.class);

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
        when(mockSensorTypeRepository.getByIdentity(sensorTypeId)).thenReturn(Optional.of(sensorTypeTemperature));
        when(mockSensorTypeRepository.getByIdentity(sensorTypeId2)).thenReturn(Optional.of(sensorTypeHumidity));
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
        when(mockSensorModelRepository.getByIdentity(sensorModelNameTemperature)).thenReturn(Optional.of(sensorModelTemperature));
        when(mockSensorModelRepository.getByIdentity(sensorModelNameHumidity)).thenReturn(Optional.of(sensorModelHumidity));
    }

    /**
     * The testConstructorOfDeviceServiceShouldNotThrowException method. It tests the DeviceServiceImpl constructor
     * with all the parameters not null.
     * It should not throw an exception.
     */
    @Test
    void testConstructorOfDeviceServiceShouldNotThrowException() {
        // Act and Assert
        assertDoesNotThrow(() -> new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper));
    }

    /**
     * The testConstructorOfDeviceServiceNullDeviceRepositoryShouldThrowException method. It tests the
     * DeviceServiceImpl constructor with a null mockDeviceRepository.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullDeviceRepositoryShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(null, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper), "The"
                + " mockDeviceRepository should not be null");
    }

    /**
     * The testConstructorOfDeviceServiceNullSensorRepositoryShouldThrowException method. It tests the
     * DeviceServiceImpl constructor with a null mockSensorRepository.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullSensorRepositoryShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository, null,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper), "The"
                + " mockSensorRepository should not be null");
    }

    /**
     * The testConstructorOfDeviceServiceNullSensorModelRepositoryShouldThrowException method. It tests the
     * DeviceServiceImpl constructor with a null mockSensorModelRepository.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullSensorModelRepositoryShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, null, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper), "The"
                + " mockSensorModelRepository should not be null");
    }

    /**
     * The testConstructorOfDeviceServiceNullSensorTypeRepositoryShouldThrowException method. It tests the
     * DeviceServiceImpl constructor with a null mockSensorTypeRepository.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullSensorTypeRepositoryShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, null, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper), "The"
                + " mockSensorTypeRepository should not be null");
    }

    /**
     * The testConstructorOfDeviceServiceNullDeviceMapperShouldThrowException method. It tests the DeviceServiceImpl
     * constructor with a null mockDeviceMapper.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullDeviceMapperShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, mockSensorTypeRepository, null, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper), "The"
                + " mockDeviceMapper should not be null");
    }

    /**
     * The testConstructorOfDeviceServiceNullDeviceFactoryShouldThrowException method. It tests the DeviceServiceImpl
     * constructor with a null mockDeviceFactory.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullDeviceFactoryShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, null,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper), "The"
                + " mockDeviceFactory should not be null");
    }

    /**
     * The testConstructorOfDeviceServiceNullRoomMapperShouldThrowException method. It tests the DeviceServiceImpl
     * constructor with a null mockRoomMapper.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullRoomMapperShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper,
                mockDeviceFactory, null, mockRoomRepository, mockReadingRepository, mockPeriodMapper,
                mockReadingMapper), "The mockRoomMapper should not be null");
    }

    /**
     * The testConstructorOfDeviceServiceNullRoomRepositoryShouldThrowException method. It tests the DeviceServiceImpl
     * constructor with a null mockRoomRepository.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorOfDeviceServiceNullRoomRepositoryShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper,
                mockDeviceFactory, mockRoomMapper, null, mockReadingRepository, mockPeriodMapper, mockReadingMapper),
                "The mockRoomRepository should not be null");
    }

    /**
     * This test checks if the constructor of the DeviceServiceImpl class throws an IllegalArgumentException
     * when the mockReadingRepository parameter is null. The test will pass if the exception is thrown.
     */
    @Test
    void testConstructorOfDeviceServiceNullReadingRepositoryShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper,
                mockDeviceFactory, mockRoomMapper, mockRoomRepository, null, mockPeriodMapper, mockReadingMapper),
                "The mockReadingRepository should not be null");
    }

    /**
     * This test checks if the constructor of the DeviceServiceImpl class throws an IllegalArgumentException
     * when the mockPeriodMapper parameter is null. The test will pass if the exception is thrown.
     */
    @Test
    void testConstructorOfDeviceServiceNullPeriodMapperShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper,
                mockDeviceFactory, mockRoomMapper, mockRoomRepository, mockReadingRepository, null,
                mockReadingMapper), "The mockPeriodMapper should not be null");
    }

    /**
     * This test checks if the constructor of the DeviceServiceImpl class throws an IllegalArgumentException
     * when the mockReadingMapper parameter is null. The test will pass if the exception is thrown.
     */
    @Test
    void testConstructorOfDeviceServiceNullReadingMapperShouldThrowException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new DeviceServiceImpl(mockDeviceRepository,
                mockSensorRepository, mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper,
                mockDeviceFactory, mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, null)
                , "The mockReadingMapper should not be null");
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns a DeviceDTO when the device is successfully added.
     * The test will pass if the result is not null.
     */
    @Test
    void testAddDeviceToRoomValidDeviceShouldReturnNotNullDevice() {
        // Arrange
        when(mockDeviceMapper.toDeviceName(deviceDTO)).thenReturn(deviceName);
        when(mockDeviceMapper.toDeviceType(deviceDTO)).thenReturn(deviceType);
        when(mockDeviceMapper.toRoomId(deviceDTO)).thenReturn(roomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceFactory.createDevice(deviceName, deviceType, roomId)).thenReturn(device);
        when(mockDeviceRepository.save(device)).thenReturn(device);
        when(mockDeviceMapper.toDeviceDTO(device)).thenReturn(deviceDTO);
        // Act
        DeviceDTO result = deviceService.addDeviceToRoom(deviceDTO);
        // Assert
        assertNotNull(result);
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns null when the room identity is not present in the repository.
     * The test will pass if the result is null.
     */
    @Test
    void testAddDeviceToRoomInvalidRoomIdShouldReturnNull() {
        // Arrange
        when(mockDeviceMapper.toDeviceName(deviceDTO)).thenReturn(deviceName);
        when(mockDeviceMapper.toDeviceType(deviceDTO)).thenReturn(deviceType);
        when(mockDeviceMapper.toRoomId(deviceDTO)).thenReturn(roomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(false);
        // Act
        DeviceDTO result = deviceService.addDeviceToRoom(deviceDTO);
        // Assert
        assertNull(result);
    }

    /**
     * Test for the addDeviceToRoom method.
     * This test asserts that the method returns null when an exception is thrown.
     * The test will pass if the result is null.
     */
    @Test
    void testAddDeviceToRoomShouldReturnNullWhenExceptionThrown() {
        // Arrange
        when(mockDeviceMapper.toDeviceName(deviceDTO)).thenReturn(deviceName);
        when(mockDeviceMapper.toDeviceType(deviceDTO)).thenReturn(deviceType);
        when(mockDeviceMapper.toRoomId(deviceDTO)).thenReturn(roomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceFactory.createDevice(deviceName, deviceType, roomId)).thenThrow(new IllegalArgumentException());
        // Act
        DeviceDTO result = deviceService.addDeviceToRoom(deviceDTO);
        // Assert
        assertNull(result);
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when the room does not exist.
     * This is tested by checking if the room repository contains the room identity.
     */
    @Test
    void testGetDevicesInRoomWhenRoomDoesNotExistShouldReturnNull() {
        // Arrange
        when(mockRoomMapper.toRoomId(roomDTO)).thenReturn(roomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(false);

        // Act
        List<DeviceDTO> result = deviceService.getDevicesInRoom(roomDTO);

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
        when(mockRoomMapper.toRoomId(roomDTO)).thenReturn(null);

        // Act
        List<DeviceDTO> result = deviceService.getDevicesInRoom(roomDTO);

        // Assert
        assertNull(result, "The result should be null when the room id is null");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns the devices in the room when the room exists.
     * This is tested by checking if the room repository contains the room identity and if the device repository
     * returns the devices in the room.
     */
    @Test
    void testGetDevicesInRoomWhenRoomContainsDevices() {
        // Arrange
        when(mockRoomMapper.toRoomId(roomDTO)).thenReturn(roomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);

        // Mocking devices in the room
        List<Device> devices = new ArrayList<>();
        devices.add(mock(Device.class));
        devices.add(mock(Device.class));
        when(mockDeviceRepository.findByRoomIdentity(roomId)).thenReturn(devices);

        // Mocking DTO conversion
        DeviceDTO deviceDTO1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTO2 = mock(DeviceDTO.class);
        List<DeviceDTO> deviceDTOs = new ArrayList<>();
        deviceDTOs.add(deviceDTO1);
        deviceDTOs.add(deviceDTO2);
        when(mockDeviceMapper.toDevicesDTO(devices)).thenReturn(deviceDTOs);

        // Act
        List<DeviceDTO> result = deviceService.getDevicesInRoom(roomDTO);

        // Assert
        assertEquals(2, result.size(), "The result should contain 2 devices");
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
        when(mockRoomMapper.toRoomId(roomDTO)).thenReturn(roomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findByRoomIdentity(roomId)).thenReturn(new ArrayList<>());

        // Act
        List<DeviceDTO> result = deviceService.getDevicesInRoom(roomDTO);

        // Assert
        assertTrue(result.isEmpty(), "The result should be an empty list when the room does not contain devices");
    }

    /**
     * Test to ensure that the getDevicesInRoom method returns null when an exception is thrown.
     * This is tested by checking if the device repository throws an exception when retrieving the devices in the room.
     */
    @Test
    void testGetDevicesInRoomWhenExceptionThrownShouldReturnNull() {
        // Arrange
        when(mockRoomMapper.toRoomId(roomDTO)).thenReturn(roomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);
        when(mockDeviceRepository.findByRoomIdentity(roomId)).thenThrow(new IllegalArgumentException());

        // Act
        List<DeviceDTO> result = deviceService.getDevicesInRoom(roomDTO);

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
        when(mockRoomMapper.toRoomId(roomDTO)).thenThrow(new IllegalArgumentException());

        // Act
        List<DeviceDTO> result = deviceService.getDevicesInRoom(roomDTO);

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
        when(mockRoomRepository.containsIdentity(null)).thenThrow(new IllegalArgumentException());

        // Act
        List<DeviceDTO> result = deviceService.getDevicesInRoom(roomDTO);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * The testDeactivateDeviceWhenDevicePresent method tests the deactivateDevice method of the DeviceServiceImpl class
     * when the device is present in the repository. It should return the deviceDTO. The test will pass if the result is
     * not null.
     */
    @Test
    void testDeactivateDeviceWhenDevicePresent() {
        // Arrange
        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockDeviceRepository.getByIdentity(deviceId)).thenReturn(Optional.of(device));
        when(mockDeviceRepository.update(device)).thenReturn(device);
        when(mockDeviceMapper.toDeviceDTO(device)).thenReturn(deviceDTO);

        // Act
        DeviceDTO result = deviceService.deactivateDevice(deviceDTO);

        // Assert
        assertNotNull(result, "The result should not be null when the device is present in the repository");
    }

    /**
     * The testDeactivateDeviceWhenDeviceNotPresent method tests the deactivateDevice method of the DeviceServiceImpl
     * class when the device is not present in the repository. It should return null. The test will pass if the result
     * is null.
     */
    @Test
    void testDeactivateDeviceWhenDeviceNotPresent() {
        // Arrange
        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockDeviceRepository.getByIdentity(deviceId)).thenReturn(Optional.empty());

        // Act
        DeviceDTO result = deviceService.deactivateDevice(deviceDTO);

        // Assert
        assertNull(result, "The result should be null when the device is not present in the repository");
    }

    /**
     * The testDeactivateDeviceWhenExceptionThrown method tests the deactivateDevice method of the DeviceServiceImpl
     * class when an exception is thrown. It should return null. The test will pass if the result is null.
     */
    @Test
    void testDeactivateDeviceWhenExceptionThrown() {
        // Arrange
        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenThrow(new IllegalArgumentException());

        // Act
        DeviceDTO result = deviceService.deactivateDevice(deviceDTO);

        // Assert
        assertNull(result, "The result should be null when an exception is thrown");
    }

    /**
     * This method tests the getDevicesBySensorType method of the DeviceServiceImpl class in a house with no devices.
     * It should return an empty HashMap.
     */
    @Test
    void testGetDevicesBySensorTypeWithNoDevices() throws IllegalAccessException {
        // Arrange
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        // Act
        HashMap<String, Set<DeviceDTO>> result = deviceService.getDevicesBySensorType();
        // Assert
        boolean resultIsEmpty = result.isEmpty();
        assertTrue(resultIsEmpty, "The result should be an empty HashMap.");
    }

    /**
     * The getDevicesBySensorType1DeviceNoSensorsOrFunctionalitiesShouldReturnEmptyHashMap method.
     * It tests the getDevicesBySensorType method with one device and no sensors or functionalities.
     * It should return an empty HashMap.
     */
    @Test
    void testGetDevicesBySensorType2DevicesNoSensorsOrFunctionalitiesShouldReturnEmptyHashMap() throws IllegalAccessException {
        // Arrange
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        // Act
        HashMap<String, Set<DeviceDTO>> result = deviceService.getDevicesBySensorType();
        // Assert
        boolean resultIsEmpty = result.isEmpty();
        assertTrue(resultIsEmpty, "The result should be an empty HashMap.");
    }

    /**
     * The getDevicesBySensorType1DeviceOneSensorOfTemperatureWithItsDeviceIdShouldReturnHashMapWithOneEntry method.
     * It tests the getDevicesBySensorType method with one device and one sensor of temperature.
     * It should return a HashMap with one entry.
     */
    @Test
    void testGetDevicesBySensorType2DevicesOneHasSensorOfTemperatureWithItsDeviceIdShouldReturnHashMapWithOneEntry() throws IllegalAccessException {
        //Arrange
        SensorId sensorId = mock(SensorId.class);
        DeviceId deviceId1 = mock(DeviceId.class);
        String deviceName = "DeviceName";
        String deviceType = "DeviceType";
        String deviceId = "validDeviceId;";
        String deviceId2String = "validDeviceId2";
        String roomIdString = "validRoomId1";
        String roomIdString2 = "validRoomId2";
        when(deviceId1.getIdentity()).thenReturn(deviceId);
        boolean status = true;
        DeviceName deviceName1 = mock(DeviceName.class);
        when(deviceName1.getDeviceName()).thenReturn(deviceName);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getDeviceType()).thenReturn(deviceType);
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(deviceStatus.getStatus()).thenReturn(status);
        Sensor sensorOfTemperature = mock(SensorOfTemperature.class);
        when(sensorOfTemperature.getDeviceId()).thenReturn(deviceId1);
        when(sensorOfTemperature.getIdentity()).thenReturn(sensorId);
        when(sensorOfTemperature.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        RoomId roomId = mock(RoomId.class);
        when(device1.getRoomId()).thenReturn(roomId);
        when(roomId.getRoomId()).thenReturn(roomIdString);
        RoomId roomId2 = mock(RoomId.class);
        when(device2.getRoomId()).thenReturn(roomId2);
        when(roomId2.getRoomId()).thenReturn(roomIdString2);
        when(device1.getDeviceName()).thenReturn(deviceName1);
        when(device1.getDeviceType()).thenReturn(deviceType1);
        when(device2.getDeviceName()).thenReturn(deviceName1);
        when(device2.getDeviceType()).thenReturn(deviceType1);
        when(device1.getIdentity()).thenReturn(deviceId1);
        when(device2.getIdentity()).thenReturn(deviceId2);
        when(mockDeviceRepository.getByIdentity(deviceId1)).thenReturn(Optional.of(device1));
        when(mockDeviceRepository.getByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensorOfTemperature));
        DeviceDTO deviceDTO = mock(DeviceDTO.class);
        when(deviceDTO.getDeviceId()).thenReturn(deviceId);
        when(deviceDTO.getRoomId()).thenReturn(roomIdString);
        when(mockDeviceMapper.toDeviceDTO(device1)).thenReturn(deviceDTO);
        DeviceDTO deviceDTO1 = mock(DeviceDTO.class);
        when(deviceDTO1.getDeviceId()).thenReturn(deviceId2String);
        when(deviceDTO1.getRoomId()).thenReturn(roomIdString2);
        HashMap<String, Set<Device>> map = new HashMap<>();
        map.put(temperature, Set.of(device1));
        HashMap<String, Set<DeviceDTO>> mapDTO = new HashMap<>();
        mapDTO.put(temperature, Set.of(deviceDTO));
        when(mockDeviceMapper.toMapDTO(map)).thenReturn(mapDTO);
        //Act
        HashMap<String, Set<DeviceDTO>> result = deviceService.getDevicesBySensorType();
        //Assert
        assertEquals(1, result.size(), "The result should have one entry.");
        assertTrue(result.containsKey("Temperature"), "The result should contain the key Temperature");
        assertFalse(result.containsKey("Humidity"), "The result should not contain the key Humidity");
        assertEquals(1, result.get("Temperature").size(), "The result should have one DeviceDTO.");
        assertTrue(result.get("Temperature").contains(deviceDTO), "The result should contain the DeviceDTO.");
        assertFalse(result.get("Temperature").contains(deviceDTO1), "The result should not contain the DeviceDTO1.");
    }

    /**
     * The getDevicesBySensorType1DeviceOneSensorOfTemperatureWithItsDeviceIdShouldReturnHashMapWithOneEntryWithRoomId
     * method. It tests the getDevicesBySensorType method with one device and one sensor of temperature.
     * It should return a HashMap with one entry with the room id.
     */
    @Test
    void testGetDevicesBySensorType2DevicesHaveSensorOfTemperatureShouldReturnHashMapWithOneKeyWithASetWithTwoDeviceDTOs() throws IllegalAccessException {
        //Arrange
        SensorId sensorId = mock(SensorId.class);
        SensorId sensorId2 = mock(SensorId.class);
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        RoomId roomId1 = mock(RoomId.class);
        RoomId roomId2 = mock(RoomId.class);
        String deviceName = "DeviceName";
        String deviceType = "DeviceType";
        String deviceId = "validDeviceId;";
        String deviceId2String = "validDeviceId2";
        String roomIdString = "validRoomId1";
        String roomIdString2 = "validRoomId2";
        when(deviceId1.getIdentity()).thenReturn(deviceId);
        when(deviceId2.getIdentity()).thenReturn(deviceId2String);
        when(roomId1.getRoomId()).thenReturn(roomIdString);
        when(roomId2.getRoomId()).thenReturn(roomIdString2);
        boolean status = true;
        DeviceName deviceName1 = mock(DeviceName.class);
        when(deviceName1.getDeviceName()).thenReturn(deviceName);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getDeviceType()).thenReturn(deviceType);
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(deviceStatus.getStatus()).thenReturn(status);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        when(device1.getDeviceName()).thenReturn(deviceName1);
        when(device1.getDeviceType()).thenReturn(deviceType1);
        when(device1.getIdentity()).thenReturn(deviceId1);
        when(device1.getDeviceStatus()).thenReturn(deviceStatus);
        when(device2.getIdentity()).thenReturn(deviceId2);
        when(device2.getDeviceType()).thenReturn(deviceType1);
        when(device2.getDeviceName()).thenReturn(deviceName1);
        when(device2.getDeviceStatus()).thenReturn(deviceStatus);
        Sensor sensorOfTemperature1 = mock(SensorOfTemperature.class);
        Sensor sensorOfTemperature2 = mock(SensorOfTemperature.class);
        when(sensorOfTemperature1.getDeviceId()).thenReturn(deviceId1);
        when(sensorOfTemperature2.getDeviceId()).thenReturn(deviceId2);
        when(sensorOfTemperature1.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensorOfTemperature1.getIdentity()).thenReturn(sensorId);
        when(sensorOfTemperature2.getIdentity()).thenReturn(sensorId2);
        when(sensorOfTemperature2.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(mockDeviceRepository.getByIdentity(deviceId1)).thenReturn(Optional.of(device1));
        when(mockDeviceRepository.getByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensorOfTemperature1, sensorOfTemperature2));
        DeviceDTO deviceDTO1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTO2 = mock(DeviceDTO.class);
        when(deviceDTO1.getRoomId()).thenReturn(roomIdString);
        when(deviceDTO2.getRoomId()).thenReturn(roomIdString2);
        when(deviceDTO1.getDeviceId()).thenReturn(deviceId);
        when(deviceDTO2.getDeviceId()).thenReturn(deviceId2String);
        HashMap<String, Set<Device>> map = new HashMap<>();
        map.put("Temperature", Set.of(device1, device2));
        HashMap<String, Set<DeviceDTO>> mapDTO = new HashMap<>();
        mapDTO.put("Temperature", Set.of(deviceDTO1, deviceDTO2));
        when(mockDeviceMapper.toDeviceDTO(device1)).thenReturn(deviceDTO1);
        when(mockDeviceMapper.toDeviceDTO(device2)).thenReturn(deviceDTO2);
        when(mockDeviceMapper.toMapDTO(map)).thenReturn(mapDTO);
        //Act
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        HashMap<String, Set<DeviceDTO>> mapResult = deviceService.getDevicesBySensorType();
        //Assert
        assertEquals(1, mapResult.size());
        assertTrue(mapResult.get("Temperature").contains(deviceDTO1) && mapResult.get("Temperature").contains(deviceDTO2));
        assertFalse(mapResult.containsKey("Humidity"));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId)));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2String)));
    }

    /**
     * The getDevicesBySensorType2DevicesOneHasSensorOfTemperatureAndTheOtherHasSensorOfTemperatureAndHumidity
     * WithTheirDeviceIdsShouldReturnHashMapWithTwoKeysWithASetWithOneDeviceDTO method. It tests the
     * getDevicesBySensorType method with two devices, one with a sensor of temperature and the other with a sensor of
     * temperature and humidity.
     * It should return a HashMap with two keys with a set with one DeviceDTO.
     */
    @Test
    void testGetDevicesBySensorType2Devices1TemperatureAnd1TemperatureAndHumidityShouldReturnTwoKeysWithOneDeviceDTO() throws IllegalAccessException {
        //Arrange
        SensorId sensorId = mock(SensorId.class);
        SensorId sensorId2 = mock(SensorId.class);
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        RoomId roomId1 = mock(RoomId.class);
        RoomId roomId2 = mock(RoomId.class);
        String deviceName = "DeviceName";
        String deviceType = "DeviceType";
        String deviceId = "validDeviceId;";
        String deviceId2String = "validDeviceId2";
        String roomIdString = "validRoomId1";
        String roomIdString2 = "validRoomId2";
        when(deviceId1.getIdentity()).thenReturn(deviceId);
        when(deviceId2.getIdentity()).thenReturn(deviceId2String);
        when(roomId1.getRoomId()).thenReturn(roomIdString);
        when(roomId2.getRoomId()).thenReturn(roomIdString2);
        boolean status = true;
        DeviceName deviceName1 = mock(DeviceName.class);
        when(deviceName1.getDeviceName()).thenReturn(deviceName);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getDeviceType()).thenReturn(deviceType);
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(deviceStatus.getStatus()).thenReturn(status);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        when(device1.getDeviceName()).thenReturn(deviceName1);
        when(device1.getDeviceType()).thenReturn(deviceType1);
        when(device1.getIdentity()).thenReturn(deviceId1);
        when(device1.getDeviceStatus()).thenReturn(deviceStatus);
        when(device2.getIdentity()).thenReturn(deviceId2);
        when(device2.getDeviceType()).thenReturn(deviceType1);
        when(device2.getDeviceName()).thenReturn(deviceName1);
        when(device2.getDeviceStatus()).thenReturn(deviceStatus);
        Sensor sensorOfTemperature1 = mock(SensorOfTemperature.class);
        Sensor sensorOfHumidity = mock(SensorOfHumidity.class);
        when(sensorOfTemperature1.getDeviceId()).thenReturn(deviceId1);
        when(sensorOfHumidity.getDeviceId()).thenReturn(deviceId2);
        when(sensorOfTemperature1.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensorOfTemperature1.getIdentity()).thenReturn(sensorId);
        when(sensorOfHumidity.getIdentity()).thenReturn(sensorId2);
        when(sensorOfHumidity.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(mockDeviceRepository.getByIdentity(deviceId1)).thenReturn(Optional.of(device1));
        when(mockDeviceRepository.getByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensorOfTemperature1, sensorOfHumidity));
        DeviceDTO deviceDTO1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTO2 = mock(DeviceDTO.class);
        when(deviceDTO1.getRoomId()).thenReturn(roomIdString);
        when(deviceDTO2.getRoomId()).thenReturn(roomIdString2);
        when(deviceDTO1.getDeviceId()).thenReturn(deviceId);
        when(deviceDTO2.getDeviceId()).thenReturn(deviceId2String);
        HashMap<String, Set<Device>> map = new HashMap<>();
        map.put("Temperature", Set.of(device1));
        map.put("Humidity", Set.of(device2));
        HashMap<String, Set<DeviceDTO>> mapDTO = new HashMap<>();
        mapDTO.put("Temperature", Set.of(deviceDTO1));
        mapDTO.put("Humidity", Set.of(deviceDTO2));
        when(mockDeviceMapper.toDeviceDTO(device1)).thenReturn(deviceDTO1);
        when(mockDeviceMapper.toDeviceDTO(device2)).thenReturn(deviceDTO2);
        when(mockDeviceMapper.toMapDTO(map)).thenReturn(mapDTO);
        //Act
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        HashMap<String, Set<DeviceDTO>> mapResult = deviceService.getDevicesBySensorType();
        //Assert
        assertEquals(2, mapResult.size());
        assertTrue(mapResult.get("Temperature").contains(deviceDTO1));
        assertTrue(mapResult.get("Humidity").contains(deviceDTO2));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId)));
        assertTrue(mapResult.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2String)));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId1.getRoomId())));
        assertTrue(mapResult.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())));
    }

    /**
     * The getDevices2DevicesOneTemperatureAndOneTemperatureAndHumidityShouldReturn2KeysOneDeviceDTO method.
     * It tests the getDevicesBySensorType method with two devices, one with a sensor of temperature and the other
     * with a sensor of temperature and humidity.
     * It should return a HashMap with two keys with a set with one DeviceDTO with the room id.
     */
    @Test
    void testGetDevicesBySensorType2DevicesOneTemperatureAndOneTemperatureAndHumidityShouldReturn2KeysOneDeviceDTO() throws IllegalAccessException {
        //Arrange
        SensorId sensorId = mock(SensorId.class);
        SensorId sensorId2 = mock(SensorId.class);
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        RoomId roomId1 = mock(RoomId.class);
        RoomId roomId2 = mock(RoomId.class);
        String deviceName = "DeviceName";
        String deviceType = "DeviceType";
        String deviceId = "validDeviceId;";
        String deviceId2String = "validDeviceId2";
        String roomIdString = "validRoomId1";
        String roomIdString2 = "validRoomId2";
        when(deviceId1.getIdentity()).thenReturn(deviceId);
        when(deviceId2.getIdentity()).thenReturn(deviceId2String);
        when(roomId1.getRoomId()).thenReturn(roomIdString);
        when(roomId2.getRoomId()).thenReturn(roomIdString2);
        boolean status = true;
        DeviceName deviceName1 = mock(DeviceName.class);
        when(deviceName1.getDeviceName()).thenReturn(deviceName);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getDeviceType()).thenReturn(deviceType);
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(deviceStatus.getStatus()).thenReturn(status);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        when(device1.getDeviceName()).thenReturn(deviceName1);
        when(device1.getDeviceType()).thenReturn(deviceType1);
        when(device1.getIdentity()).thenReturn(deviceId1);
        when(device1.getDeviceStatus()).thenReturn(deviceStatus);
        when(device2.getIdentity()).thenReturn(deviceId2);
        when(device2.getDeviceType()).thenReturn(deviceType1);
        when(device2.getDeviceName()).thenReturn(deviceName1);
        when(device2.getDeviceStatus()).thenReturn(deviceStatus);
        Sensor sensorOfTemperature1 = mock(SensorOfTemperature.class);
        Sensor sensorOfTemperature2 = mock(SensorOfTemperature.class);
        when(sensorOfTemperature1.getDeviceId()).thenReturn(deviceId1);
        when(sensorOfTemperature2.getDeviceId()).thenReturn(deviceId2);
        when(sensorOfTemperature1.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensorOfTemperature1.getIdentity()).thenReturn(sensorId);
        when(sensorOfTemperature2.getIdentity()).thenReturn(sensorId2);
        when(sensorOfTemperature2.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(mockDeviceRepository.getByIdentity(deviceId1)).thenReturn(Optional.of(device1));
        when(mockDeviceRepository.getByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensorOfTemperature1, sensorOfTemperature2));
        DeviceDTO deviceDTO1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTO2 = mock(DeviceDTO.class);
        when(deviceDTO1.getRoomId()).thenReturn(roomIdString);
        when(deviceDTO2.getRoomId()).thenReturn(roomIdString2);
        when(deviceDTO1.getDeviceId()).thenReturn(deviceId);
        when(deviceDTO2.getDeviceId()).thenReturn(deviceId2String);
        HashMap<String, Set<Device>> map = new HashMap<>();
        map.put("Temperature", Set.of(device1, device2));
        HashMap<String, Set<DeviceDTO>> mapDTO = new HashMap<>();
        mapDTO.put("Temperature", Set.of(deviceDTO1, deviceDTO2));
        when(mockDeviceMapper.toDeviceDTO(device1)).thenReturn(deviceDTO1);
        when(mockDeviceMapper.toDeviceDTO(device2)).thenReturn(deviceDTO2);
        when(mockDeviceMapper.toMapDTO(map)).thenReturn(mapDTO);
        //Act
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        HashMap<String, Set<DeviceDTO>> mapResult = deviceService.getDevicesBySensorType();
        //Assert
        assertEquals(1, mapResult.size());
        assertTrue(mapResult.get("Temperature").contains(deviceDTO1) && mapResult.get("Temperature").contains(deviceDTO2));
        assertFalse(mapResult.containsKey("Humidity"));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId)));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2String)));
    }

    /**
     * getDevicesBySensorType2DevicesOneTemperatureOther2HumidityShouldReturnMapWith2EntriesBothSize1 method.
     * It tests the getDevicesBySensorType method with two devices, one with one sensor of temperature and the
     * other with two sensors of humidity.
     * It should return a map with two entries, both with size 1.
     */
    @Test
    void testGetDevicesBySensorType2DevicesOneTemperatureOther2HumidityShouldReturnMapWith2EntriesBothSize1() throws IllegalAccessException {
        //Arrange
        SensorId sensorId = mock(SensorId.class);
        SensorId sensorId2 = mock(SensorId.class);
        SensorId sensorId3 = mock(SensorId.class);
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        RoomId roomId1 = mock(RoomId.class);
        RoomId roomId2 = mock(RoomId.class);
        String deviceName = "DeviceName";
        String deviceType = "DeviceType";
        String deviceId = "validDeviceId;";
        String deviceId2String = "validDeviceId2";
        String roomIdString = "validRoomId1";
        String roomIdString2 = "validRoomId2";
        when(deviceId1.getIdentity()).thenReturn(deviceId);
        when(deviceId2.getIdentity()).thenReturn(deviceId2String);
        when(roomId1.getRoomId()).thenReturn(roomIdString);
        when(roomId2.getRoomId()).thenReturn(roomIdString2);
        boolean status = true;
        DeviceName deviceName1 = mock(DeviceName.class);
        when(deviceName1.getDeviceName()).thenReturn(deviceName);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getDeviceType()).thenReturn(deviceType);
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(deviceStatus.getStatus()).thenReturn(status);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        when(device1.getDeviceName()).thenReturn(deviceName1);
        when(device1.getDeviceType()).thenReturn(deviceType1);
        when(device1.getIdentity()).thenReturn(deviceId1);
        when(device1.getDeviceStatus()).thenReturn(deviceStatus);
        when(device2.getIdentity()).thenReturn(deviceId2);
        when(device2.getDeviceType()).thenReturn(deviceType1);
        when(device2.getDeviceName()).thenReturn(deviceName1);
        when(device2.getDeviceStatus()).thenReturn(deviceStatus);
        Sensor sensorOfTemperature1 = mock(SensorOfTemperature.class);
        Sensor sensorOfHumidity1 = mock(SensorOfHumidity.class);
        Sensor sensorOfHumidity2 = mock(SensorOfHumidity.class);
        when(sensorOfTemperature1.getDeviceId()).thenReturn(deviceId1);
        when(sensorOfHumidity1.getDeviceId()).thenReturn(deviceId2);
        when(sensorOfHumidity2.getDeviceId()).thenReturn(deviceId2);
        when(sensorOfTemperature1.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensorOfTemperature1.getIdentity()).thenReturn(sensorId);
        when(sensorOfHumidity1.getIdentity()).thenReturn(sensorId2);
        when(sensorOfHumidity2.getIdentity()).thenReturn(sensorId3);
        when(sensorOfHumidity1.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(sensorOfHumidity2.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(mockDeviceRepository.getByIdentity(deviceId1)).thenReturn(Optional.of(device1));
        when(mockDeviceRepository.getByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensorOfTemperature1, sensorOfHumidity1,
                sensorOfHumidity2));
        DeviceDTO deviceDTO1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTO2 = mock(DeviceDTO.class);
        when(deviceDTO1.getRoomId()).thenReturn(roomIdString);
        when(deviceDTO2.getRoomId()).thenReturn(roomIdString2);
        when(deviceDTO1.getDeviceId()).thenReturn(deviceId);
        when(deviceDTO2.getDeviceId()).thenReturn(deviceId2String);
        HashMap<String, Set<Device>> map = new HashMap<>();
        map.put("Temperature", Set.of(device1));
        map.put("Humidity", Set.of(device2));
        HashMap<String, Set<DeviceDTO>> mapDTO = new HashMap<>();
        mapDTO.put("Temperature", Set.of(deviceDTO1));
        mapDTO.put("Humidity", Set.of(deviceDTO2));
        when(mockDeviceMapper.toDeviceDTO(device1)).thenReturn(deviceDTO1);
        when(mockDeviceMapper.toDeviceDTO(device2)).thenReturn(deviceDTO2);
        when(mockDeviceMapper.toMapDTO(map)).thenReturn(mapDTO);
        //Act
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        HashMap<String, Set<DeviceDTO>> mapResult = deviceService.getDevicesBySensorType();
        //Assert
        assertEquals(2, mapResult.size());
        assertEquals(mapResult.get("Temperature").size(), 1);
        assertEquals(mapResult.get("Humidity").size(), 1);
        assertTrue(mapResult.get("Temperature").contains(deviceDTO1));
        assertTrue(mapResult.get("Humidity").contains(deviceDTO2));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId)));
        assertTrue(mapResult.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2String)));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId1.getRoomId())));
        assertTrue(mapResult.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())));
    }

    /**
     * The getDevicesBySensorType2DevicesInTheSameRoomEachHasOneSensorOfTemperatureShouldReturnMapWith1EntrySize2
     * method. It tests the getDevicesBySensorType method with two devices in the same room, each with one sensor of
     * temperature.
     * It should return a map with one entry, size 2.
     */
    @Test
    void testGetDevicesBySensorType2DevicesInTheSameRoomEachHasOneSensorOfTemperatureShouldReturnMapWith1EntrySize2() throws IllegalAccessException {
        //Arrange
        SensorId sensorId = mock(SensorId.class);
        SensorId sensorId2 = mock(SensorId.class);
        DeviceId deviceId1 = mock(DeviceId.class);
        DeviceId deviceId2 = mock(DeviceId.class);
        RoomId roomId1 = mock(RoomId.class);
        RoomId roomId2 = mock(RoomId.class);
        String deviceName = "DeviceName";
        String deviceType = "DeviceType";
        String deviceId = "validDeviceId;";
        String deviceId2String = "validDeviceId2";
        String roomIdString = "validRoomId1";
        when(deviceId1.getIdentity()).thenReturn(deviceId);
        when(deviceId2.getIdentity()).thenReturn(deviceId2String);
        when(roomId1.getRoomId()).thenReturn(roomIdString);
        boolean status = true;
        DeviceName deviceName1 = mock(DeviceName.class);
        when(deviceName1.getDeviceName()).thenReturn(deviceName);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getDeviceType()).thenReturn(deviceType);
        DeviceStatus deviceStatus = mock(DeviceStatus.class);
        when(deviceStatus.getStatus()).thenReturn(status);
        Device device1 = mock(Device.class);
        Device device2 = mock(Device.class);
        when(device1.getDeviceName()).thenReturn(deviceName1);
        when(device1.getDeviceType()).thenReturn(deviceType1);
        when(device1.getIdentity()).thenReturn(deviceId1);
        when(device1.getDeviceStatus()).thenReturn(deviceStatus);
        when(device1.getRoomId()).thenReturn(roomId1);
        when(device2.getIdentity()).thenReturn(deviceId2);
        when(device2.getDeviceType()).thenReturn(deviceType1);
        when(device2.getDeviceName()).thenReturn(deviceName1);
        when(device2.getDeviceStatus()).thenReturn(deviceStatus);
        when(device2.getRoomId()).thenReturn(roomId1);
        Sensor sensorOfTemperature1 = mock(SensorOfTemperature.class);
        Sensor sensorOfHumidity1 = mock(SensorOfHumidity.class);
        when(sensorOfTemperature1.getDeviceId()).thenReturn(deviceId1);
        when(sensorOfHumidity1.getDeviceId()).thenReturn(deviceId2);
        when(sensorOfTemperature1.getSensorModelName()).thenReturn(sensorModelNameTemperature);
        when(sensorOfTemperature1.getIdentity()).thenReturn(sensorId);
        when(sensorOfHumidity1.getIdentity()).thenReturn(sensorId2);
        when(sensorOfHumidity1.getSensorModelName()).thenReturn(sensorModelNameHumidity);
        when(mockDeviceRepository.getByIdentity(deviceId1)).thenReturn(Optional.of(device1));
        when(mockDeviceRepository.getByIdentity(deviceId2)).thenReturn(Optional.of(device2));
        when(mockSensorRepository.findAll()).thenReturn(List.of(sensorOfTemperature1, sensorOfHumidity1));
        DeviceDTO deviceDTO1 = mock(DeviceDTO.class);
        DeviceDTO deviceDTO2 = mock(DeviceDTO.class);
        when(deviceDTO1.getRoomId()).thenReturn(roomIdString);
        when(deviceDTO2.getRoomId()).thenReturn(roomIdString);
        when(deviceDTO1.getDeviceId()).thenReturn(deviceId);
        when(deviceDTO2.getDeviceId()).thenReturn(deviceId2String);
        HashMap<String, Set<Device>> map = new HashMap<>();
        map.put("Temperature", Set.of(device1));
        map.put("Humidity", Set.of(device2));
        HashMap<String, Set<DeviceDTO>> mapDTO = new HashMap<>();
        mapDTO.put("Temperature", Set.of(deviceDTO1));
        mapDTO.put("Humidity", Set.of(deviceDTO2));
        when(mockDeviceMapper.toDeviceDTO(device1)).thenReturn(deviceDTO1);
        when(mockDeviceMapper.toDeviceDTO(device2)).thenReturn(deviceDTO2);
        when(mockDeviceMapper.toMapDTO(map)).thenReturn(mapDTO);
        //Act
        DeviceServiceImpl deviceService = new DeviceServiceImpl(mockDeviceRepository, mockSensorRepository,
                mockSensorModelRepository, mockSensorTypeRepository, mockDeviceMapper, mockDeviceFactory,
                mockRoomMapper, mockRoomRepository, mockReadingRepository, mockPeriodMapper, mockReadingMapper);
        HashMap<String, Set<DeviceDTO>> mapResult = deviceService.getDevicesBySensorType();
        //Assert
        assertEquals(2, mapResult.size());
        assertEquals(mapResult.get("Temperature").size(), 1);
        assertEquals(mapResult.get("Humidity").size(), 1);
        assertTrue(mapResult.get("Temperature").contains(deviceDTO1));
        assertTrue(mapResult.get("Humidity").contains(deviceDTO2));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId)));
        assertTrue(mapResult.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2String)));
        assertTrue(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId1.getRoomId())));
        assertTrue(mapResult.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId1.getRoomId())));
        assertFalse(mapResult.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())));
        assertFalse(mapResult.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())));
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns a list of ReadingDTOs when the device has readings in the given period.
     * The test will pass if the result is not null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnListOfReadingDTOsWhenDeviceHasReadingsInGivenPeriod() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);

        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenReturn(start);
        when(mockPeriodMapper.toEnd(periodDTO)).thenReturn(end);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.getByDeviceIdentity(deviceId)).thenReturn(List.of(sensor));
        when(mockReadingRepository.findBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(List.of(reading));

        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);

        // Assert
        assertNotNull(result);
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns an empty list when the device has no readings in the given period.
     * The test will pass if the result is an empty list.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnEmptyListWhenDeviceHasNoReadingsInGivenPeriod() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);

        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenReturn(start);
        when(mockPeriodMapper.toEnd(periodDTO)).thenReturn(end);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.getByDeviceIdentity(deviceId)).thenReturn(List.of(sensor));
        when(mockReadingRepository.findBySensorIdInAGivenPeriod(sensorId, start, end)).thenReturn(new ArrayList<>());

        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is after the end date.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenStartDateIsAfterEndDate() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 12, 31, 23, 59);
        LocalDateTime endValue = LocalDateTime.of(2022, 1, 1, 0, 0);

        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenReturn(start);
        when(mockPeriodMapper.toEnd(periodDTO)).thenReturn(end);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);

        // Assert
        assertNull(result);
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the start date is equal to the end date.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenStartDateIsEqualToEndDate() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 1, 1, 0, 0);

        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenReturn(start);
        when(mockPeriodMapper.toEnd(periodDTO)).thenReturn(end);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);

        // Assert
        assertNull(result);
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the end date is in the future.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenEndDateIsInFuture() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2025, 1, 1, 0, 0);

        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenReturn(start);
        when(mockPeriodMapper.toEnd(periodDTO)).thenReturn(end);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);

        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);

        // Assert
        assertNull(result);
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the device does not exist.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenDeviceDoesNotExist() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);

        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenReturn(start);
        when(mockPeriodMapper.toEnd(periodDTO)).thenReturn(end);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.getByDeviceIdentity(deviceId)).thenReturn(new ArrayList<>());

        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);

        // Assert
        assertNull(result);
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the device has no sensors.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenDeviceHasNoSensors() {
        // Arrange
        LocalDateTime startValue = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endValue = LocalDateTime.of(2022, 12, 31, 23, 59);

        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenReturn(start);
        when(mockPeriodMapper.toEnd(periodDTO)).thenReturn(end);
        when(start.getValue()).thenReturn(startValue);
        when(end.getValue()).thenReturn(endValue);
        when(mockSensorRepository.getByDeviceIdentity(deviceId)).thenReturn(new ArrayList<>());

        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);

        // Assert
        assertNull(result);
    }

    /**
     * Test for the getReadingsFromDeviceInAGivenPeriod method.
     * This test asserts that the method returns null when the data format is invalid.
     * The test will pass if the result is null.
     */
    @Test
    void testGetReadingsFromDeviceShouldReturnNullWhenDataFormatIsInvalid() {
        // Arrange
        when(mockDeviceMapper.toDeviceId(deviceDTO)).thenReturn(deviceId);
        when(mockPeriodMapper.toStart(periodDTO)).thenThrow(RuntimeException.class);
        // Act
        List<ReadingDTO> result = deviceService.getReadingsFromDeviceInAGivenPeriod(deviceDTO, periodDTO);
        // Assert
        assertNull(result);
    }

}