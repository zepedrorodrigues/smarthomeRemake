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
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensor.SensorFactoryImpl;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.SensorTypeIdDTO;
import smarthome.mapper.mapper.SensorMapper;
import smarthome.mapper.mapper.SensorModelMapper;
import smarthome.mapper.mapper.SensorTypeMapper;
import smarthome.service.ISensorModelService;
import smarthome.service.ISensorService;
import smarthome.service.ISensorTypeService;
import smarthome.service.impl.SensorServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the AddSensorToDeviceController class.
 * It verifies the behavior of the AddSensorToDeviceController under different scenarios.
 */
class AddSensorToDeviceControllerTest {


    IDeviceRepository deviceRepository;

    ISensorRepository sensorRepository;

    SensorMapper sensorMapper;

    SensorTypeMapper sensorTypeMapper;

    SensorModelMapper sensorModelMapper;

    SensorFactory sensorFactory;

    DeviceFactory deviceFactory;

    AddSensorToDeviceController addSensorToDeviceController;

    SensorDTO sensorDTO;

    String deviceId1;

    String sensorModelName;

    ISensorService sensorService;

    ISensorTypeService sensorTypeService;

    ISensorModelService sensorModelService;


    /**
     * This method sets up the necessary objects for the tests.
     * It initializes repositories, factories, mapper, controllers, and DTOs that are used in the test methods.
     * It also saves some initial data into the repositories.
     * This method is annotated with @BeforeEach, so it runs before each test method in this class.
     *
     * @throws InstantiationException if an application error occurs
     */
    @BeforeEach
    void setUp() throws InstantiationException {
        deviceFactory = new DeviceFactoryImpl();

        deviceRepository = mock(IDeviceRepository.class);

        Device device1 = deviceFactory.createDevice(new DeviceName("deviceName1"), new DeviceTypeName("deviceType1"),
                new RoomId("roomId1"));

        deviceId1 = device1.getIdentity().getIdentity();

        sensorTypeMapper = new SensorTypeMapper();


        sensorModelMapper = new SensorModelMapper();


        sensorRepository = mock(ISensorRepository.class);
        sensorFactory = new SensorFactoryImpl();
        sensorMapper = new SensorMapper();


        sensorTypeService = mock(ISensorTypeService.class);
        sensorModelService = mock(ISensorModelService.class);

        sensorService = new SensorServiceImpl(sensorRepository,
                sensorFactory, deviceRepository);

        addSensorToDeviceController = new AddSensorToDeviceController(sensorService, sensorTypeService,
                sensorModelService, sensorTypeMapper, sensorModelMapper, sensorMapper);

        sensorModelName = "SensorOfTemperature";
        sensorDTO = new SensorDTO(deviceId1, sensorModelName);

    }

    /**
     * Tests the behavior of the AddSensorToDeviceController's constructor when the sensorService parameter is null.
     * The test asserts that the constructor throws an IllegalArgumentException in this scenario.
     */
    @Test
    void testConstructorWithNullSensorService() {
        // Arrange
        ISensorService sensorService = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new AddSensorToDeviceController(sensorService, sensorTypeService, sensorModelService, sensorTypeMapper,
                    sensorModelMapper, sensorMapper);
        }, "The constructor should throw an IllegalArgumentException when the sensorService parameter is null");
    }

    /**
     * Tests the behavior of the AddSensorToDeviceController's constructor when the sensorService parameter is valid.
     * The test asserts that the constructor successfully creates an instance of AddSensorToDeviceController in this scenario.
     */
    @Test
    void testConstructorWithValidSensorService() {
        // Arrange
        ISensorService sensorService = new SensorServiceImpl(sensorRepository, sensorFactory, deviceRepository);
        SensorTypeMapper sensorTypeMapper = new SensorTypeMapper();
        SensorModelMapper sensorModelMapper = new SensorModelMapper();
        SensorMapper sensorMapper = new SensorMapper();

        // Act
        AddSensorToDeviceController controller = new AddSensorToDeviceController(sensorService, sensorTypeService,
                sensorModelService, sensorTypeMapper, sensorModelMapper, sensorMapper);

        // Assert
        assertNotNull(controller, "The controller should not be null");
    }

    /**
     * Tests if the getSensorModelsBySensorTypeIdentity method of the AddSensorToDeviceController class returns null
     * when the sensorTypeDTO parameter is null.
     * This test verifies if the method's implementation is correct when dealing with a null sensorTypeDTO.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityWithNullSensorTypeDTO() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);
        }, "The getSensorModelsBySensorTypeIdentity method should throw an IllegalArgumentException when" +
                " the sensorTypeDTO is null");
    }

    /**
     * This test verifies the behavior of the getSensorModelsBySensorTypeIdentity method when an invalid
     * SensorTypeDTO is provided.
     * It creates a SensorTypeDTO with an empty string as the sensor type ID, which is considered invalid.
     * The test asserts that the getSensorModelsBySensorTypeIdentity method of the controller should return null in
     * this case.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdWithInvalidSensorTypeDTO() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO(" ");

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);
        }, "The method should throw an IllegalArgumentException for an invalid SensorTypeDTO");
    }

    /**
     * This test verifies the behavior of the getSensorTypes method in the AddSensorToDeviceController class.
     * It checks if the method returns a non-null result when the sensorTypeService.getSensorTypesIds method returns a non-empty list.
     * The test uses Mockito to mock the behavior of the sensorTypeService.getSensorTypesIds method.
     * The test asserts that the result of the getSensorTypes method is not null.
     */
    @Test
    void testGetSensorTypes_resultIsNotNull() {
        // Arrange
        SensorTypeId sensorTypeId1 = new SensorTypeId("TemperatureCelsius");
        SensorTypeId sensorTypeId2 = new SensorTypeId("HumidityPercent");
        when(sensorTypeService.getSensorTypesIds()).thenReturn(Arrays.asList(sensorTypeId1, sensorTypeId2));

        // Act
        List<SensorTypeIdDTO> result = addSensorToDeviceController.getSensorTypes();

        // Assert
        assertNotNull(result, "The result should not be null");
    }

    /**
     * This test verifies the behavior of the getSensorTypes method in the AddSensorToDeviceController class.
     * It checks if the method returns a non-empty list when the sensorTypeService.getSensorTypesIds method returns a non-empty list.
     * The test uses Mockito to mock the behavior of the sensorTypeService.getSensorTypesIds method.
     * The test asserts that the result of the getSensorTypes method is not empty.
     */
    @Test
    void testGetSensorTypes_resultIsNotEmpty() {
        // Arrange
        SensorTypeId sensorTypeId1 = new SensorTypeId("TemperatureCelsius");
        SensorTypeId sensorTypeId2 = new SensorTypeId("HumidityPercent");
        when(sensorTypeService.getSensorTypesIds()).thenReturn(Arrays.asList(sensorTypeId1, sensorTypeId2));

        // Act
        List<SensorTypeIdDTO> result = addSensorToDeviceController.getSensorTypes();

        // Assert
        assertFalse(result.isEmpty(), "The result should not be empty");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method of the AddSensorToDeviceController with a null SensorTypeDTO.
     * Checks if the method throws an IllegalArgumentException when the SensorTypeDTO is null.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityWithNullSensorTypeDTOs() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);
        }, "The method should throw an IllegalArgumentException when sensorTypeDTO is null");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method of the AddSensorToDeviceController with a valid SensorTypeDTO and no SensorModelNames.
     * Checks if the method returns an empty list when there are no SensorModelNames for the provided SensorTypeDTO.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityWithValidSensorTypeDTOAndEmptySensorModelNames() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("TemperatureCelsius");
        when(sensorModelService.getSensorModelsBySensorTypeIdentity(any(SensorTypeId.class)))
                .thenReturn(new ArrayList<>());

        // Act
        List<SensorModelDTO> result = addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertTrue(result.isEmpty(), "The result list should be empty");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method of the AddSensorToDeviceController with a valid SensorTypeDTO and a SensorModelName, but no corresponding SensorModel.
     * Checks if the method returns an empty list when the SensorModelName does not have a corresponding SensorModel.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityWithValidSensorTypeDTOAndEmptyOptionalSensorModel() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("TemperatureCelsius");
        SensorModelName sensorModelName = new SensorModelName("TemperatureModel1");
        when(sensorModelService.getSensorModelsBySensorTypeIdentity(any(SensorTypeId.class)))
                .thenReturn(Arrays.asList(sensorModelName));
        when(sensorModelService.getSensorModelByName(any(SensorModelName.class)))
                .thenReturn(Optional.empty());

        // Act
        List<SensorModelDTO> result = addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertTrue(result.isEmpty(), "The result list should be empty");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method of the AddSensorToDeviceController with a valid SensorTypeDTO and a SensorModelName with a corresponding SensorModel.
     * Checks if the method returns a list that is not empty when the SensorModelName has a corresponding SensorModel.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityWithValidSensorTypeDTOAndNonEmptyOptionalSensorModel() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("TemperatureCelsius");
        SensorModelName sensorModelName = new SensorModelName("TemperatureModel1");
        SensorModel sensorModel = new SensorModel(sensorModelName, new SensorTypeId("TemperatureCelsius"));
        when(sensorModelService.getSensorModelsBySensorTypeIdentity(any(SensorTypeId.class)))
                .thenReturn(Arrays.asList(sensorModelName));
        when(sensorModelService.getSensorModelByName(any(SensorModelName.class)))
                .thenReturn(Optional.of(sensorModel));

        // Act
        List<SensorModelDTO> result = addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertFalse(result.isEmpty(), "The result list should not be empty");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method of the AddSensorToDeviceController with a valid SensorTypeDTO and a SensorModelName with a corresponding SensorModel.
     * Checks if the method returns a list of size 1 when there is exactly one SensorModel corresponding to the SensorModelName.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityWithValidSensorTypeDTOAndNonEmptyOptionalSensorModelSize() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("TemperatureCelsius");
        SensorModelName sensorModelName = new SensorModelName("TemperatureModel1");
        SensorModel sensorModel = new SensorModel(sensorModelName, new SensorTypeId("TemperatureCelsius"));
        when(sensorModelService.getSensorModelsBySensorTypeIdentity(any(SensorTypeId.class)))
                .thenReturn(Arrays.asList(sensorModelName));
        when(sensorModelService.getSensorModelByName(any(SensorModelName.class)))
                .thenReturn(Optional.of(sensorModel));

        // Act
        List<SensorModelDTO> result = addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertEquals(1, result.size(), "The result list should contain one element");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method of the AddSensorToDeviceController with a valid SensorTypeDTO and a SensorModelName with a corresponding SensorModel.
     * Checks if the method returns a list whose first element has the same name as the SensorModelName when there is exactly one SensorModel corresponding to the SensorModelName.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityWithValidSensorTypeDTOAndNonEmptyOptionalSensorModelName() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("TemperatureCelsius");
        SensorModelName sensorModelName = new SensorModelName("TemperatureModel1");
        SensorModel sensorModel = new SensorModel(sensorModelName, new SensorTypeId("TemperatureCelsius"));
        when(sensorModelService.getSensorModelsBySensorTypeIdentity(any(SensorTypeId.class)))
                .thenReturn(Arrays.asList(sensorModelName));
        when(sensorModelService.getSensorModelByName(any(SensorModelName.class)))
                .thenReturn(Optional.of(sensorModel));

        // Act
        List<SensorModelDTO> result = addSensorToDeviceController.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertEquals(sensorModelName.getSensorModelName(), result.get(0).getSensorModelName(),
                "The sensor model name should match");
    }

    /**
     * Test case for the addSensor method of the AddSensorToDeviceController class.
     * This test checks the behavior of the method when an invalid SensorDTO is provided.
     * An invalid SensorDTO is one that has a device ID and sensor model name that do not exist in the system.
     * The expected result is that the method should return null.
     */
    @Test
    void testAddSensorWithInvalidSensorDTO() {
        // Arrange
        SensorDTO invalidSensorDTO = new SensorDTO("invalidDeviceId", "invalidSensorModelName");

        // Act
        SensorDTO result = addSensorToDeviceController.addSensor(invalidSensorDTO);

        // Assert
        assertNull(result, "The sensor DTO should be null");
    }

    /**
     * Test to verify that the addSensor method of AddSensorToDeviceController returns null when a null SensorDTO is
     * provided.
     */
    @Test
    void testAddSensorWithNullSensorDTO() {
        // Arrange
        SensorDTO invalidSensorDTO = null;
        //Act
        SensorDTO result = addSensorToDeviceController.addSensor(invalidSensorDTO);
        // Act and Assert
        assertNull(result, "The sensor DTO should be null");
    }

    /**
     * Test to verify that the addSensor method of AddSensorToDeviceController returns a SensorDTO with the expected
     * device ID and sensor model name when a valid SensorDTO is provided.
     * It checks if the returned SensorDTO is not null and if it has the expected device ID and sensor model name.
     */
    @Test
    void testAddSensorValid() {
        //Arrange
        DeviceId deviceId = new DeviceId(deviceId1);
        when(deviceRepository.containsIdentity(deviceId)).thenReturn(true);
        when(sensorRepository.save(any(Sensor.class))).thenAnswer(invocation -> invocation.<Sensor>getArgument(0));
        String sensorValue = "25.0";
        //Act
        SensorDTO sensorDTOResult = addSensorToDeviceController.addSensor(sensorDTO);

        boolean result = !sensorDTOResult.getSensorId().isBlank() && deviceId1.equals(sensorDTOResult.getDeviceId()) &&
                sensorModelName.equals(sensorDTOResult.getSensorModelName()) &&
                sensorValue.equals(sensorDTOResult.getValue());
        //Assert
        assertTrue(result, "The device ID and sensor model name should be correct");
    }

    /**
     * Test to verify that the addSensor method of AddSensorToDeviceController returns null when a SensorDTO with a
     * non-existent device ID is provided.
     * It checks if the returned SensorDTO is null.
     */
    @Test
    void testAddSensorNonExistentDeviceId() {
        //Arrange
        DeviceId deviceId = new DeviceId("nonExistentDeviceId");
        when(deviceRepository.containsIdentity(deviceId)).thenReturn(false);
        SensorDTO sensorDTO = new SensorDTO("nonExistentDeviceId", sensorModelName);
        //Act
        SensorDTO sensorDTOResult = addSensorToDeviceController.addSensor(sensorDTO);
        //Assert
        assertNull(sensorDTOResult, "The sensor DTO should be null");
    }

    /**
     * Test to verify that the addSensor method of AddSensorToDeviceController returns null when a SensorDTO with a
     * null device ID is provided.
     * It checks if the returned SensorDTO is null.
     */
    @Test
    void testAddSensorEmptyRepositoryInvalidDeviceId() {
        //Arrange
        SensorDTO sensorDTO = new SensorDTO(null, sensorModelName);
        //Act
        SensorDTO sensorDTOResult = addSensorToDeviceController.addSensor(sensorDTO);
        //Assert
        assertNull(sensorDTOResult, "The sensor DTO should be null");
    }

    /**
     * Test to verify that the addSensor method of AddSensorToDeviceController returns null when a SensorDTO with a
     * null sensor model name is provided.
     * It checks if the returned SensorDTO is null.
     */
    @Test
    void testAddSensorEmptyRepositoryInvalidSensorModelName() {
        //Arrange
        SensorDTO sensorDTO = new SensorDTO(deviceId1, null);
        //Act
        SensorDTO sensorDTOResult = addSensorToDeviceController.addSensor(sensorDTO);
        //Assert
        assertNull(sensorDTOResult, "The sensor DTO should be null");
    }

    /**
     * Test to verify that the getDevicesInRoom method of AddSensorToDeviceController returns a list of DeviceDTO
     * with the expected device ID, device name, and device type when the IDeviceRepository is not empty.
     * It checks if the first DeviceDTO in the list has the expected device ID, device name, and device type.
     */
    @Test
    void testCreateSensorWithInvalidModelNameReturnsNull() {
        // Arrange
        DeviceId deviceId = new DeviceId(deviceId1);
        when(deviceRepository.containsIdentity(deviceId)).thenReturn(true);
        String invalidSensorModelName = "SensorFactoryImpl";
        SensorDTO sensorDTO = new SensorDTO(deviceId1, invalidSensorModelName);

        // Act
        SensorDTO result = addSensorToDeviceController.addSensor(sensorDTO);

        // Assert
        assertNull(result, "The sensor DTO should be null");
    }
}
