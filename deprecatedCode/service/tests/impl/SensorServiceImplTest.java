package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.mapper.SensorMapper;
import smarthome.mapper.mapper.SensorModelMapper;
import smarthome.mapper.mapper.SensorTypeMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the SensorServiceImpl class.
 */
class SensorServiceImplTest {

    private SensorTypeMapper sensorTypeMapper;

    private ISensorTypeRepository sensorTypeRepository;

    private SensorModelMapper sensorModelMapper;

    private ISensorModelRepository sensorModelRepository;

    private ISensorRepository sensorRepository;

    private SensorFactory sensorFactory;

    private SensorMapper sensorMapper;

    private IDeviceRepository deviceRepository;

    private SensorServiceImpl sensorServiceImpl;

    private SensorType sensorType;

    private SensorModelDTO sensorModelDTO;

    /**
     * Initializes the objects that are used in the tests.
     * This method is called before each test.
     * It initializes the SensorTypeMapper, SensorTypeRepository, SensorModelMapper, SensorModelRepository,
     * SensorRepository, SensorFactory and SensorMapper objects.
     */
    @BeforeEach
    void setUp() {
        sensorTypeMapper = mock(SensorTypeMapper.class);
        sensorTypeRepository = mock(ISensorTypeRepository.class);
        sensorModelMapper = mock(SensorModelMapper.class);
        sensorModelRepository = mock(ISensorModelRepository.class);
        sensorRepository = mock(ISensorRepository.class);
        sensorFactory = mock(SensorFactory.class);
        sensorMapper = mock(SensorMapper.class);
        deviceRepository = mock(IDeviceRepository.class);

        sensorType = mock(SensorType.class);

        sensorModelDTO = mock(SensorModelDTO.class);
        when(sensorModelDTO.getSensorModelName()).thenReturn("someModelName");
        when(sensorModelDTO.getSensorTypeId()).thenReturn("someTypeId");

        sensorServiceImpl = new SensorServiceImpl(sensorTypeMapper, sensorTypeRepository, sensorModelMapper,
                sensorModelRepository, sensorRepository, sensorFactory, sensorMapper, deviceRepository);
    }

    /**
     * Tests if the constructor of the SensorServiceImpl class does not throw an exception when all parameters are
     * valid.
     * This test verifies if the constructor's implementation is correct when dealing with valid parameters.
     */
    @Test
    void testConstructorSensorServiceDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new SensorServiceImpl(sensorTypeMapper, sensorTypeRepository, sensorModelMapper,
                sensorModelRepository, sensorRepository, sensorFactory, sensorMapper, deviceRepository),
                "The constructor should not throw an exception when the mock all parameters are valid");
    }


    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the sensorTypeMapper
     * parameter is null.
     * This test verifies if the constructor's implementation is correct when dealing with a null sensorTypeMapper.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullSensorTypeMapper() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(null,
                        sensorTypeRepository, sensorModelMapper, sensorModelRepository, sensorRepository, sensorFactory,
                        sensorMapper, deviceRepository),
                "The constructor should throw an exception when the mock sensorTypeMapper is null");
    }

    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the sensorTypeRepository
     * parameter is null.
     * This test verifies if the constructor's implementation is correct when dealing with a null sensorTypeRepository.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullSensorTypeRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(sensorTypeMapper, null,
                sensorModelMapper, sensorModelRepository, sensorRepository, sensorFactory, sensorMapper,
                deviceRepository),
                "The constructor should throw an exception when the mock sensorTypeRepository is null");
    }

    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the sensorModelMapper
     * parameter is null.
     * This test verifies if the constructor's implementation is correct when dealing with a null sensorModelMapper.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullSensorModelMapper() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(sensorTypeMapper,
                sensorTypeRepository, null, sensorModelRepository, sensorRepository, sensorFactory, sensorMapper,
                deviceRepository),
                "The constructor should throw an exception when the mock sensorModelMapper is null");
    }

    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the sensorModelRepository
     * parameter is null.
     * This test verifies if the constructor's implementation is correct when dealing with a null sensorModelRepository.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullSensorModelRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(sensorTypeMapper,
                sensorTypeRepository, sensorModelMapper, null, sensorRepository, sensorFactory, sensorMapper,
                deviceRepository),
                "The constructor should throw an exception when the mock thesensorModelRepository is null");
    }

    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the sensorRepository
     * parameter is null.
     * This test verifies if the constructor's implementation is correct when dealing with a null sensorRepository.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullSensorRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(sensorTypeMapper,
                sensorTypeRepository, sensorModelMapper, sensorModelRepository, null, sensorFactory, sensorMapper,
                deviceRepository),
                "The constructor should throw an exception when the mock sensorRepository is null");
    }

    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the sensorFactory parameter
     * is null.
     * This test verifies if the constructor's implementation is correct when dealing with a null sensorFactory.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullSensorFactory() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(sensorTypeMapper,
                sensorTypeRepository, sensorModelMapper, sensorModelRepository, sensorRepository, null, sensorMapper,
                deviceRepository),
                "The constructor should throw an exception when the mock sensorFactory is null");
    }

    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the sensorMapper parameter is
     * null.
     * This test verifies if the constructor's implementation is correct when dealing with a null sensorMapper.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullSensorMapper() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(sensorTypeMapper,
                sensorTypeRepository, sensorModelMapper, sensorModelRepository, sensorRepository, sensorFactory, null
                , deviceRepository),
                "The constructor should throw an exception when the mock sensorMapper is null");
    }


    /**
     * Tests if the constructor of the SensorServiceImpl class throws an exception when the deviceRepository
     * parameter is null.
     * This test verifies if the constructor's implementation is correct when dealing with a null deviceRepository.
     */
    @Test
    void testConstructorSensorServiceThrowsExceptionForNullDeviceRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new SensorServiceImpl(sensorTypeMapper,
                sensorTypeRepository, sensorModelMapper, sensorModelRepository, sensorRepository, sensorFactory,
                sensorMapper, null),
                "The constructor should throw an exceptionwhen the mock deviceRepository is null");
    }

    /**
     * Tests the getSensorTypes method when the repository is empty.
     * The expected result is an empty list.
     */
    @Test
    void testGetSensorTypesEmptyRepository() {
        // Arrange
        int expectedSize = 0;

        // Act
        List<SensorTypeDTO> result = sensorServiceImpl.getSensorTypes();

        // Assert
        assertEquals(expectedSize, result.size(),
                "The getSensorTypes method should return an empty list when"
                        + "there are not sensor types in the repository");
    }

    /**
     * Tests the getSensorTypes method when the repository contains one sensor type.
     * The expected result is a list with one sensor type.
     */
    @Test
    void testGetSensorTypesOneTypeInRepository() {
        // Arrange
        int expectedSize = 1;
        Iterable<SensorType> sensorTypes = List.of(sensorType);

        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("someId");
        when(sensorTypeRepository.findAll()).thenReturn(sensorTypes);
        when(sensorTypeMapper.toSensorTypesDTO(any())).thenReturn(List.of(sensorTypeDTO));

        // Act
        List<SensorTypeDTO> result = sensorServiceImpl.getSensorTypes();

        // Assert
        assertEquals(expectedSize, result.size(),
                "The getSensorTypes method should return a list with one sensor type when"
                        + " there is one sensor type in the repository");
    }

    /**
     * Tests the getSensorTypes method when the repository contains multiple sensor types.
     * The expected result is a list with the same number of sensor types as in the repository.
     */
    @Test
    void testGetSensorTypesMultipleTypesInRepository() {
        // Arrange
        int expectedSize = 2;
        Iterable<SensorType> sensorTypes = Arrays.asList(sensorType, sensorType);

        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("someId");
        when(sensorTypeRepository.findAll()).thenReturn(sensorTypes);
        when(sensorTypeMapper.toSensorTypesDTO(any())).thenReturn(List.of(sensorTypeDTO, sensorTypeDTO));

        // Act
        List<SensorTypeDTO> result = sensorServiceImpl.getSensorTypes();

        // Assert
        assertEquals(expectedSize, result.size(),
                "The getSensorTypes method should return a list with two sensor types when"
                        + " there are two sensor types in the repository");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method when the repository is empty.
     * The expected result is an empty list.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityEmptyRepository() {
        // Arrange
        int expectedSize = 0;
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("someId");
        when(sensorTypeMapper.toSensorTypeId(any())).thenReturn(new SensorTypeId("someId"));
        when(sensorModelRepository.findBySensorTypeIdentity(any())).thenReturn(List.of());

        // Act
        List<SensorModelDTO> result = sensorServiceImpl.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertEquals(expectedSize, result.size(),
                "The getSensorModelsBySensorTypeIdentity method should return an empty list when"
                        + " there are no sensor models for the given sensor type in the repository");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method when the repository contains one sensor model for the given sensor type.
     * The expected result is a list with one sensor model.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityOneModelInRepository() {
        // Arrange
        int expectedSize = 1;
        SensorModel sensorModel = mock(SensorModel.class);
        Iterable<SensorModel> sensorModels = List.of(sensorModel);
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("someId");

        when(sensorTypeMapper.toSensorTypeId(any())).thenReturn(new SensorTypeId("someId"));
        when(sensorModelRepository.findBySensorTypeIdentity(any())).thenReturn(sensorModels);
        when(sensorModelMapper.toSensorModelsDto(any())).thenReturn(List.of(sensorModelDTO));

        // Act
        List<SensorModelDTO> result = sensorServiceImpl.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertEquals(expectedSize, result.size(),
                "The getSensorModelsBySensorTypeIdentity method should return a list with one sensor model when"
                        + " there is one sensor model for the given sensor type in the repository");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method when the repository contains multiple sensor models for the given sensor type.
     * The expected result is a list with the same number of sensor models as in the repository.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityMultipleModelsInRepository() {
        // Arrange
        int expectedSize = 2;
        SensorModel sensorModel = mock(SensorModel.class);
        Iterable<SensorModel> sensorModels = List.of(sensorModel, sensorModel);
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("someId");

        when(sensorTypeMapper.toSensorTypeId(any())).thenReturn(new SensorTypeId("someId"));
        when(sensorModelRepository.findBySensorTypeIdentity(any())).thenReturn(sensorModels);
        when(sensorModelMapper.toSensorModelsDto(any())).thenReturn(List.of(sensorModelDTO, sensorModelDTO));

        // Act
        List<SensorModelDTO> result = sensorServiceImpl.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertEquals(expectedSize, result.size(),
                "The getSensorModelsBySensorTypeIdentity method should return a list with two sensor models"
                        + " when there are two sensor models for the given sensor type in the repository");
    }

    /**
     * Tests the getSensorModelsBySensorTypeIdentity method when an exception is thrown.
     * The expected result is null.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityThrowsException() {
        // Arrange
        SensorTypeDTO sensorTypeDTO = new SensorTypeDTO("someId");

        when(sensorTypeMapper.toSensorTypeId(any())).thenThrow(new RuntimeException());

        // Act
        List<SensorModelDTO> result = sensorServiceImpl.getSensorModelsBySensorTypeIdentity(sensorTypeDTO);

        // Assert
        assertNull(result, "The getSensorModelsBySensorTypeIdentity method should return null when"
                + " an exception is thrown");
    }

    /**
     * Tests the addSensor method when the operation is successful.
     * The expected result is the same SensorDTO as the one passed to the method.
     */
    @Test
    void testAddSensorSuccessful() {
        // Arrange
        SensorDTO sensorDTO = mock(SensorDTO.class);
        DeviceId deviceId = mock(DeviceId.class);
        SensorModelName sensorModelName = mock(SensorModelName.class);
        Sensor sensor = mock(Sensor.class);

        when(sensorMapper.toDeviceId(sensorDTO)).thenReturn(deviceId);
        when(deviceRepository.containsIdentity(deviceId)).thenReturn(true);
        when(sensorMapper.toSensorModelName(sensorDTO)).thenReturn(sensorModelName);
        when(sensorFactory.createSensor(sensorModelName, deviceId)).thenReturn(sensor);
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toSensorDTO(sensor)).thenReturn(sensorDTO);

        // Act
        SensorDTO result = sensorServiceImpl.addSensor(sensorDTO);

        // Assert
        assertEquals(sensorDTO, result, "The addSensor method should return the same SensorDTO when" + " the " +
                "operation is successful");
    }

    /**
     * Tests the addSensor method when the device is not found.
     * The expected result is null.
     */
    @Test
    void testAddSensorDeviceNotFound() {
        // Arrange
        SensorDTO sensorDTO = mock(SensorDTO.class);
        DeviceId deviceId = mock(DeviceId.class);

        when(sensorMapper.toDeviceId(sensorDTO)).thenReturn(deviceId);
        when(deviceRepository.containsIdentity(deviceId)).thenReturn(false);

        // Act
        SensorDTO result = sensorServiceImpl.addSensor(sensorDTO);

        // Assert
        assertNull(result, "The addSensor method should return null when the device is not found");
    }

    /**
     * Tests the addSensor method when an exception is thrown.
     * The expected result is null.
     */
    @Test
    void testAddSensorThrowsException() {
        // Arrange
        SensorDTO sensorDTO = mock(SensorDTO.class);

        when(sensorMapper.toDeviceId(sensorDTO)).thenThrow(new RuntimeException());

        // Act
        SensorDTO result = sensorServiceImpl.addSensor(sensorDTO);

        // Assert
        assertNull(result, "The addSensor method should return null when an exception is thrown");
    }
}