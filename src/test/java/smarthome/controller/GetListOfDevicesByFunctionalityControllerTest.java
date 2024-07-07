package smarthome.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
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
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.SensorModelFactoryImpl;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.SensorTypeFactoryImpl;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.persistence.mem.DeviceRepositoryMemImpl;
import smarthome.persistence.mem.RoomRepositoryMemImpl;
import smarthome.persistence.mem.SensorModelRepositoryMemImpl;
import smarthome.persistence.mem.SensorRepositoryMemImpl;
import smarthome.persistence.mem.SensorTypeRepositoryMemImpl;
import smarthome.service.IDeviceService;
import smarthome.service.impl.DeviceServiceImpl;

import java.util.Map;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is responsible for testing the GetListOfDevicesByFunctionalityController class.
 * It tests the constructor, the getDevicesBySensorType method and the getDevicesBySensorType method with
 * different scenarios.
 */
class GetListOfDevicesByFunctionalityControllerTest {

    IDeviceRepository deviceRepository;
    ISensorRepository sensorRepository;
    ISensorModelRepository sensorModelRepository;
    ISensorTypeRepository sensorTypeRepository;
    IDeviceTypeRepository deviceTypeRepository;
    DeviceMapper deviceMapper;
    DeviceFactory deviceFactory;
    IRoomRepository roomRepository;
    IDeviceService deviceService;
    RoomId roomId;
    RoomId roomId2;
    DeviceName deviceName;
    DeviceName deviceName2;
    DeviceTypeName deviceType;
    DeviceTypeName deviceType2;
    Device device;
    Device device2;
    SensorTypeFactoryImpl sensorTypeFactory;
    SensorModelFactoryImpl sensorModelFactory;
    SensorFactory sensorFactory;

    /**
     * The setUp method. It initializes the deviceRepository, sensorRepository, sensorModelRepository,
     * sensorTypeRepository, deviceMapper, roomId, roomId2, deviceName, deviceName2, deviceType, deviceType2,
     * deviceFactory, device, device2, sensorTypeFactory, sensorModelFactory, actuatorTypeFactory,
     * actuatorModelFactory, sensorFactory and repositoryLoader attributes.
     *
     * @throws InstantiationException An InstantiationException if an error occurs.
     */
    @BeforeEach
    void setUp() throws InstantiationException {

        //Mocking repositories for the service and initializing the factories
        sensorModelRepository = mock(SensorModelRepositoryMemImpl.class);
        sensorTypeRepository = mock(SensorTypeRepositoryMemImpl.class);
        deviceTypeRepository = mock(IDeviceTypeRepository.class);
        sensorTypeFactory = new SensorTypeFactoryImpl();
        sensorModelFactory = new SensorModelFactoryImpl();

        //Loading sensor types
        SensorTypeName temperatureName = new SensorTypeName("Temperature");
        SensorTypeUnit temperatureUnit = new SensorTypeUnit("Celsius");
        SensorTypeName humidity = new SensorTypeName("Humidity");
        SensorTypeUnit humidityUnit = new SensorTypeUnit("Percentage");
        SensorType temperature = sensorTypeFactory.createSensorType(temperatureName, temperatureUnit);
        SensorType humidityType = sensorTypeFactory.createSensorType(humidity, humidityUnit);

        //Loading sensor models
        SensorModelName sensorModelNameTemperature = new SensorModelName("SensorOfTemperature");
        SensorTypeId sensorTypeIdTemperature = temperature.getIdentity();
        SensorModelName sensorModelNameHumidity = new SensorModelName("SensorOfHumidity");
        SensorTypeId sensorTypeIdHumidity = humidityType.getIdentity();
        SensorModel sensorModelTemperature = sensorModelFactory.createSensorModel(sensorModelNameTemperature,
                sensorTypeIdTemperature);
        SensorModel sensorModelHumidity = sensorModelFactory.createSensorModel(sensorModelNameHumidity,
                sensorTypeIdHumidity);
        sensorModelRepository.save(sensorModelTemperature);
        sensorModelRepository.save(sensorModelHumidity);

        //Programming the mock
        when(sensorTypeRepository.findByIdentity(temperature.getIdentity())).thenReturn(Optional.of(temperature));
        when(sensorTypeRepository.findByIdentity(humidityType.getIdentity())).thenReturn(Optional.of(humidityType));
        when(sensorTypeRepository.findAll()).thenReturn(List.of(temperature, humidityType));
        when(sensorModelRepository.findAll()).thenReturn(List.of(sensorModelTemperature, sensorModelHumidity));
        when(sensorModelRepository.findByIdentity(sensorModelTemperature.getIdentity())).thenReturn(Optional.of(sensorModelTemperature));
        when(sensorModelRepository.findByIdentity(sensorModelHumidity.getIdentity())).thenReturn(Optional.of(sensorModelHumidity));

        //Mocking the other repositories and initializing the factories
        deviceRepository = mock(DeviceRepositoryMemImpl.class);
        deviceFactory = new DeviceFactoryImpl();
        sensorRepository = mock(SensorRepositoryMemImpl.class);
        sensorFactory = new SensorFactoryImpl();
        roomRepository = mock(RoomRepositoryMemImpl.class);

        //Initializing the mappers
        deviceMapper = new DeviceMapper();

        //Initializing the service
        deviceService = new DeviceServiceImpl(roomRepository, deviceFactory, deviceRepository, sensorTypeRepository,
                sensorModelRepository, sensorRepository, deviceTypeRepository);

        //Initializing the devices
        roomId = new RoomId("room1");
        roomId2 = new RoomId("room2");
        deviceName = new DeviceName("device1");
        deviceName2 = new DeviceName("device2");
        deviceType = new DeviceTypeName("type1");
        deviceType2 = new DeviceTypeName("type2");
        device = deviceFactory.createDevice(deviceName, deviceType, roomId);
        device2 = deviceFactory.createDevice(deviceName2, deviceType2, roomId2);
        when(deviceRepository.findByIdentity(device.getIdentity())).thenReturn(Optional.of(device));
        when(deviceRepository.findByIdentity(device2.getIdentity())).thenReturn(Optional.of(device2));
    }

    /**
     * The testConstructorValidParametersShouldNotThrowException method. It tests the
     * GetListOfDevicesByFunctionalityController constructor with valid parameters.
     * It should not throw an exception.
     */
    @Test
    void testConstructorValidParametersShouldNotThrowException() {
        assertDoesNotThrow(() -> new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper), "Constructor should " +
                "not throw an exception for valid parameters.");
    }

    /**
     * The testConstructorNullDeviceServiceShouldThrowException method. It tests the
     * GetListOfDevicesByFunctionalityController constructor with a null deviceService.
     * It should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorNullDeviceServiceShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new GetListOfDevicesByFunctionalityController(null,deviceMapper),
                "DeviceService must not be null.");
    }

    /**
     * The getDevicesBySensorTypeNoDevicesNoRoomShouldReturnEmptyMap method.
     * It tests the getDevicesBySensorType method with no devices and no rooms.
     * It should return an empty Map.
     */
    @Test
    void getDevicesBySensorTypeNoDevicesNoRoomShouldReturnEmptyMap() throws IllegalAccessException {
        // Arrange
        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        // Act
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        // Assert
        assertEquals(0, result.size(), "The result should be an empty Map.");
    }

    /**
     * The getDevicesBySensorType1DeviceNoSensorsOrFunctionalitiesShouldReturnEmptyMap method.
     * It tests the getDevicesBySensorType method with one device and no sensors or functionalities.
     * It should return an empty Map.
     */
    @Test
    void getDevicesBySensorType2DevicesNoSensorsOrFunctionalitiesShouldReturnEmptyMap() throws IllegalAccessException {
        // Arrange

        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        // Act
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        // Assert
        assertEquals(0, result.size(), "The result should be an empty Map.");
    }

    /**
     * The getDevicesBySensorType1DeviceOneSensorOfTemperatureWithItsDeviceIdShouldReturnMapWithOneEntry method.
     * It tests the getDevicesBySensorType method with one device and one sensor of temperature.
     * It should return a Map with one entry.
     */
    @Test
    void getDevicesBySensorType2DevicesOneHasSensorOfTemperatureWithItsDeviceIdShouldReturnMapWithOneEntry() throws IllegalAccessException {
        // Arrange
        DeviceId deviceId = device.getIdentity();
        SensorModel sensorModel = sensorModelRepository.findByIdentity(new SensorModelName("SensorOfTemperature")).get();
        Sensor sensor = sensorFactory.createSensor(sensorModel.getIdentity(), deviceId);
        when(sensorRepository.findAll()).thenReturn(List.of(sensor));
        //Act
        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        //Assert
        assertEquals(1, result.size(), "The result should have one entry.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should have" + " the device with the id device1.");
    }

    /**
     * The getDevicesBySensorType1DeviceOneSensorOfTemperatureWithItsDeviceIdShouldReturnMapWithOneEntryWithRoomId
     * method. It tests the getDevicesBySensorType method with one device and one sensor of temperature.
     * It should return a Map with one entry with the room id.
     */
    @Test
    void getDevicesBySensorType2DevicesHaveSensorOfTemperatureShouldReturnMapWithOneKeyWithAListWithTwoDeviceDTOs() throws IllegalAccessException {
        // Arrange
        DeviceId deviceId = device.getIdentity();
        DeviceId deviceId2 = device2.getIdentity();
        SensorModel sensorModel = sensorModelRepository.findByIdentity(new SensorModelName("SensorOfTemperature")).get();
        Sensor sensor = sensorFactory.createSensor(sensorModel.getIdentity(), deviceId);
        Sensor sensor2 = sensorFactory.createSensor(sensorModel.getIdentity(), deviceId2);
        when(sensorRepository.findAll()).thenReturn(List.of(sensor, sensor2));
        //Act
        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        //Assert
        assertEquals(1, result.size(), "The result should have one entry.");
        assertTrue(result.containsKey("Temperature") && !result.containsKey("Humidity"), "The result should have the " +
                "key Temperature.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should have" + " the device with the id device1.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2.getIdentity())), "The result should have" + " the device with the id device2.");
        assertEquals(2, result.get("Temperature").size(), "The result should have two devices.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId.getRoomId())), "The result should have the room id room1.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())), "The result should have the room id room2.");
    }

    /**
     * The getDevicesBySensorType2DevicesOneHasSensorOfTemperatureAndTheOtherHasSensorOfTemperatureAndHumidity
     * WithTheirDeviceIdsShouldReturnMapWithTwoKeysWithAListWithOneDeviceDTO method. It tests the
     * getDevicesBySensorType method with two devices, one with a sensor of temperature and the other with a sensor of
     * temperature and humidity.
     * It should return a Map with two keys with a List with one DeviceDTO.
     */
    @Test
    void getDevicesBySensorType2Devices1TemperatureAnd1TemperatureAndHumidityShouldReturnTwoKeysWithOneDeviceDTO() throws IllegalAccessException {
        // Arrange
        DeviceId deviceId = device.getIdentity();
        DeviceId deviceId2 = device2.getIdentity();
        SensorModel sensorModelTemperature = sensorModelRepository.findByIdentity(new SensorModelName(
                "SensorOfTemperature")).get();
        SensorModel sensorModelHumidity =
                sensorModelRepository.findByIdentity(new SensorModelName("SensorOfHumidity")).get();
        Sensor sensorTemperature = sensorFactory.createSensor(sensorModelTemperature.getIdentity(), deviceId);
        Sensor sensorHumidity = sensorFactory.createSensor(sensorModelHumidity.getIdentity(), deviceId2);
        when(sensorRepository.findAll()).thenReturn(List.of(sensorTemperature, sensorHumidity));
        //Act
        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        //Assert
        assertEquals(2, result.size(), "The result should have two keys.");
        assertTrue(result.containsKey("Temperature"), "The result should have the key Temperature.");
        assertTrue(result.containsKey("Humidity"), "The result should have the key Humidity.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should have the device with the id device1.");
        assertFalse(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2.getIdentity())), "The result should not have the device with the id device2.");
        assertTrue(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2.getIdentity())), "The result should have the device with the id device2.");
        assertFalse(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should not have the device with the id device1.");
        assertEquals(1, result.get("Temperature").size(), "The result should have one device with the key Temperature");
        assertEquals(1, result.get("Humidity").size(), "The result should have one device with the key Humidity");
    }

    /**
     * The getDevices2DevicesOneTemperatureAndOneTemperatureAndHumidityShouldReturn2KeysOneDeviceDTO method.
     * It tests the getDevicesBySensorType method with two devices, one with a sensor of temperature and the other
     * with a sensor of temperature and humidity.
     * It should return a Map with two keys with a List with one DeviceDTO with the room id.
     */
    @Test
    void getDevices2DevicesOneTemperatureAndOneTemperatureAndHumidityShouldReturn2KeysOneDeviceDTO() throws IllegalAccessException {
        // Arrange
        DeviceId deviceId = device.getIdentity();
        DeviceId deviceId2 = device2.getIdentity();
        SensorModel sensorModelTemperature = sensorModelRepository.findByIdentity(new SensorModelName(
                "SensorOfTemperature")).get();
        SensorModel sensorModelHumidity =
                sensorModelRepository.findByIdentity(new SensorModelName("SensorOfHumidity")).get();
        Sensor sensorTemperature = sensorFactory.createSensor(sensorModelTemperature.getIdentity(), deviceId);
        Sensor sensorHumidity = sensorFactory.createSensor(sensorModelHumidity.getIdentity(), deviceId2);
        Sensor sensorTemperature2 = sensorFactory.createSensor(sensorModelTemperature.getIdentity(), deviceId2);
        when(sensorRepository.findAll()).thenReturn(List.of(sensorTemperature, sensorHumidity, sensorTemperature2));
        //Act
        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        //Assert
        assertEquals(2, result.size());
        assertTrue(result.containsKey("Temperature"));
        assertTrue(result.containsKey("Humidity"));
        assertEquals(2, result.get("Temperature").size());
        assertEquals(1, result.get("Humidity").size());
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should have the device with the id device1.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId.getRoomId())), "The result should have the room id room1.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())), "The result should have the room id room2.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2.getIdentity())), "The result should have the device with the id device2.");
        assertTrue(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2.getIdentity())), "The result should have the device with the id device2.");
        assertTrue(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())), "The result should have the room id room2.");
        assertFalse(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId.getRoomId())), "The result should not have the room id room1.");
        assertFalse(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should not have the device with the id device1.");
    }

    /**
     * getDevicesBySensorType2DevicesOneTemperatureOther2HumidityShouldReturnMapWith2EntriesBothSize1 method.
     * It tests the getDevicesBySensorType method with two devices, one with one sensor of temperature and the
     * other with two sensors of humidity.
     * It should return a map with two entries, both with size 1.
     */
    @Test
    void getDevicesBySensorType2DevicesOneTemperatureOther2HumidityShouldReturnMapWith2EntriesBothSize1() throws IllegalAccessException {
        // Arrange
        DeviceId deviceId = device.getIdentity();
        DeviceId deviceId2 = device2.getIdentity();
        SensorModel sensorModelTemperature = sensorModelRepository.findByIdentity(new SensorModelName(
                "SensorOfTemperature")).get();
        SensorModel sensorModelHumidity =
                sensorModelRepository.findByIdentity(new SensorModelName("SensorOfHumidity")).get();
        Sensor sensorTemperature = sensorFactory.createSensor(sensorModelTemperature.getIdentity(), deviceId);
        Sensor sensorHumidity = sensorFactory.createSensor(sensorModelHumidity.getIdentity(), deviceId2);
        Sensor sensorHumidity2 = sensorFactory.createSensor(sensorModelHumidity.getIdentity(), deviceId2);
        when(sensorRepository.findAll()).thenReturn(List.of(sensorTemperature, sensorHumidity, sensorHumidity2));
        //Act
        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        //Assert
        assertEquals(2, result.size());
        assertTrue(result.containsKey("Temperature"));
        assertTrue(result.containsKey("Humidity"));
        assertEquals(1, result.get("Temperature").size());
        assertEquals(1, result.get("Humidity").size());
        assertFalse(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2.getIdentity())), "The result should not have the device with the id device2.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should have" + " the device with the id device1.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId.getRoomId())), "The result should have the room id room1.");
        assertFalse(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should not have the device with the id device1.");
        assertTrue(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId2.getIdentity())), "The result should have" + " the device with the id device2.");
        assertTrue(result.get("Humidity").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())), "The result should have the room id room2.");
    }

    /**
     * The getDevicesBySensorType2DevicesInTheSameRoomEachHasOneSensorOfTemperatureShouldReturnMapWith1EntrySize2
     * method. It tests the getDevicesBySensorType method with two devices in the same room, each with one sensor of
     * temperature.
     * It should return a map with one entry, size 2.
     */
    @Test
    void getDevicesBySensorType2DevicesInTheSameRoomEachHasOneSensorOfTemperatureShouldReturnMapWith1EntrySize2() throws IllegalAccessException {
        // Arrange
        Device device2 = deviceFactory.createDevice(deviceName2, deviceType, roomId);
        when(deviceRepository.findByIdentity(device2.getIdentity())).thenReturn(Optional.of(device2));
        DeviceId deviceId = device.getIdentity();
        DeviceId deviceId3 = device2.getIdentity();
        SensorModel sensorModelTemperature = sensorModelRepository.findByIdentity(new SensorModelName(
                "SensorOfTemperature")).get();
        Sensor sensorTemperature = sensorFactory.createSensor(sensorModelTemperature.getIdentity(), deviceId);
        Sensor sensorTemperature2 = sensorFactory.createSensor(sensorModelTemperature.getIdentity(), deviceId3);
        when(sensorRepository.findAll()).thenReturn(List.of(sensorTemperature, sensorTemperature2));
        //Act
        GetListOfDevicesByFunctionalityController getListOfDevicesByFunctionalityController =
                new GetListOfDevicesByFunctionalityController(deviceService,deviceMapper);
        Map<String, List<DeviceDTO>> result = getListOfDevicesByFunctionalityController.getDevicesBySensorType();
        //Assert
        assertEquals(1, result.size());
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId.getIdentity())), "The result should have" + " the device with the id device1.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getDeviceId().equals(deviceId3.getIdentity())), "The result should have" + " the device with the id device2.");
        assertTrue(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId.getRoomId())), "The result should have the room id room1.");
        assertFalse(result.get("Temperature").stream().anyMatch(deviceDTO -> deviceDTO.getRoomId().equals(roomId2.getRoomId())), "The result should not have the room id room2.");
    }
}