package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorTypeDTO;
import smarthome.mapper.mapper.ActuatorMapper;
import smarthome.mapper.mapper.ActuatorModelMapper;
import smarthome.mapper.mapper.ActuatorTypeMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorServiceImplTest {

    //Device/Device Repository Test Data
    DeviceId mockDeviceID;
    String deviceId;
    IDeviceRepository mockDeviceRepository;

    //Actuator Repository Test Data
    IActuatorRepository mockActuatorRepository;

    //Actuator/Actuator Factory Test Data
    Actuator mockActuator;
    String actuatorTypeName;
    ActuatorId mockActuatorId;
    String actuatorId;
    ActuatorTypeName mockActuatorTypeName;
    ActuatorTypeMapper mockActuatorTypeMapper;
    IActuatorTypeRepository mockActuatorTypeRepository;
    String actuatorModelName;
    ActuatorModelName mockActuatorModelName;
    ActuatorModelMapper mockActuatorModelMapper;
    IActuatorModelRepository mockActuatorModelRepository;
    ActuatorFactory mockActuatorFactory;
    ActuatorMap mockActuatorMap;

    //Actuator DTO/Actuator Mapper Test Data
    ActuatorMapper mockActuatorMapper;
    ActuatorDTO mockActuatorDTO;
    ActuatorTypeDTO mockActuatorTypeDTO;

    //Actuator Service
    ActuatorServiceImpl actuatorService;

    ActuatorModelDTO mockActuatorModelDTO;


    @BeforeEach
    void setUp() {
        actuatorTypeName = "testActuatorTypeName";
        actuatorModelName = "testActuatorModelName";
        deviceId = "testDeviceId";
        actuatorId = "testActuatorId";

        mockActuatorTypeName = mock(ActuatorTypeName.class);
        mockActuatorModelName = mock(ActuatorModelName.class);
        mockDeviceID = mock(DeviceId.class);
        mockActuator = mock(Actuator.class);
        mockActuatorId = mock(ActuatorId.class);

        //Mock the repositories
        mockActuatorTypeRepository = mock(IActuatorTypeRepository.class);
        when(mockActuatorTypeRepository.containsIdentity(mockActuatorTypeName)).thenReturn(true);
        when(mockActuatorTypeRepository.findAll()).thenReturn(new ArrayList<>());

        mockActuatorModelRepository = mock(IActuatorModelRepository.class);
        when(mockActuatorModelRepository.findByActuatorTypeIdentity(mockActuatorTypeName)).thenReturn(new ArrayList<>());

        mockDeviceRepository = mock(IDeviceRepository.class);
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(true);

        mockActuatorRepository = mock(IActuatorRepository.class);
        when(mockActuatorRepository.save(mockActuator)).thenReturn(mockActuator);


        //Mock the mapper
        mockActuatorTypeMapper = mock(ActuatorTypeMapper.class);
        when(mockActuatorTypeMapper.toActuatorTypeName(mockActuatorTypeDTO)).thenReturn(mockActuatorTypeName);

        mockActuatorMapper = mock(ActuatorMapper.class);
        when(mockActuatorMapper.toActuatorId(mockActuatorDTO)).thenReturn(mockActuatorId);
        when(mockActuatorMapper.toActuatorModelName(mockActuatorDTO)).thenReturn(mockActuatorModelName);
        when(mockActuatorMapper.toDeviceId(mockActuatorDTO)).thenReturn(mockDeviceID);
        when(mockActuatorMapper.toActuatorMap(mockActuatorDTO)).thenReturn(mockActuatorMap);
        when(mockActuatorMapper.actuatorToDTO(mockActuator)).thenReturn(mockActuatorDTO);

        mockActuatorModelMapper = mock(ActuatorModelMapper.class);
        //        when(mockActuatorModelMapper.toActuatorModelDTO(mockActuatorModel)).thenReturn(mockActuatorDTO);

        //Mock the factory
        mockActuatorFactory = mock(ActuatorFactory.class);
        when(mockActuatorFactory.createActuator(mockActuatorMap)).thenReturn(mockActuator);

        //Mock the ActuatorMap, mockActuatorDTO, actuatorTypeDTO and its attributes
        mockActuatorMap = mock(ActuatorMap.class);
        mockActuatorDTO = mock(ActuatorDTO.class);
        when(mockActuatorDTO.getDeviceId()).thenReturn(deviceId);
        when(mockActuatorDTO.getActuatorId()).thenReturn(actuatorId);
        when(mockActuatorDTO.getActuatorModelName()).thenReturn(actuatorModelName);

        mockActuatorModelDTO = mock(ActuatorModelDTO.class);
        when(mockActuatorModelDTO.getActuatorModelName()).thenReturn(actuatorModelName);
        when(mockActuatorModelDTO.getActuatorTypeName()).thenReturn(actuatorTypeName);


        mockActuatorTypeDTO = mock(ActuatorTypeDTO.class);
        when(mockActuatorTypeDTO.getActuatorTypeName()).thenReturn(actuatorTypeName);


        //Initialize the ActuatorServiceImp
        actuatorService = new ActuatorServiceImpl(mockActuatorTypeMapper, mockActuatorTypeRepository,
                mockActuatorModelRepository, mockActuatorFactory, mockActuatorRepository, mockActuatorMapper,
                mockActuatorModelMapper, mockDeviceRepository);

    }

    // Test that the constructor of ActuatorServiceImpl does not throw an exception when valid parameters are passed
    @Test
    void testConstructorActuatorServiceDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new ActuatorServiceImpl(mockActuatorTypeMapper, mockActuatorTypeRepository,
                mockActuatorModelRepository, mockActuatorFactory, mockActuatorRepository, mockActuatorMapper,
                mockActuatorModelMapper, mockDeviceRepository), "Constructor should not throw an exception when " +
                "valid" + " parameters are passed");
    }

    // Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the
    // ActuatorTypeMapper parameter is null
    @Test
    void testConstructorOfActuatorServiceThrowsExceptionForNullActuatorTypeMapper() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(null, mockActuatorTypeRepository,
                mockActuatorModelRepository, mockActuatorFactory, mockActuatorRepository, mockActuatorMapper,
                mockActuatorModelMapper, mockDeviceRepository), "Constructor should throw an exception when " +
                "ActuatorTypeMapper is null");
    }

    // Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the
    // IActuatorTypeRepository parameter is null
    @Test
    void testConstructorActuatorServiceThrowsExceptionForNullActuatorTypeRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(mockActuatorTypeMapper, null,
                mockActuatorModelRepository, mockActuatorFactory, mockActuatorRepository, mockActuatorMapper,
                mockActuatorModelMapper, mockDeviceRepository), "Constructor should throw an exception when " +
                "IActuatorTypeRepository is null");
    }

    // Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the
    // IActuatorModelRepository parameter is null
    @Test
    void testConstructorActuatorServiceThrowsExceptionForNullActuatorModelRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(mockActuatorTypeMapper,
                mockActuatorTypeRepository, null, mockActuatorFactory, mockActuatorRepository, mockActuatorMapper,
                mockActuatorModelMapper, mockDeviceRepository), "Constructor should throw an exception when " +
                "IActuatorModelRepository is null");
    }

    // Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the ActuatorFactory
    // parameter is null
    @Test
    void testConstructorActuatorServiceThrowsExceptionForNullActuatorFactory() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(mockActuatorTypeMapper,
                mockActuatorTypeRepository, mockActuatorModelRepository, null, mockActuatorRepository,
                mockActuatorMapper, mockActuatorModelMapper, mockDeviceRepository), "Constructor should throw an " +
                "exception when ActuatorFactory " + "is null");
    }

    // Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the
    // IActuatorRepository parameter is null
    @Test
    void testConstructorActuatorServiceThrowsExceptionForNullActuatorRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(mockActuatorTypeMapper,
                mockActuatorTypeRepository, mockActuatorModelRepository, mockActuatorFactory, null,
                mockActuatorMapper, mockActuatorModelMapper, mockDeviceRepository), "Constructor should throw an " +
                "exception when " + "IActuatorRepository is null");
    }

    // Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the ActuatorMapper
    // parameter is null
    @Test
    void testConstructorActuatorServiceThrowsExceptionForNullActuatorMapper() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(mockActuatorTypeMapper,
                mockActuatorTypeRepository, mockActuatorModelRepository, mockActuatorFactory, mockActuatorRepository,
                null, mockActuatorModelMapper, mockDeviceRepository),
                "Constructor should throw an exception when " + "ActuatorMapper is" + " null");
    }

    // Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the
    // ActuatorModelMapper parameter is null
    @Test
    void testConstructorActuatorServiceThrowsExceptionForNullActuatorModelMapper() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(mockActuatorTypeMapper,
                mockActuatorTypeRepository, mockActuatorModelRepository, mockActuatorFactory, mockActuatorRepository,
                mockActuatorMapper, null, mockDeviceRepository), "Constructor should throw an exception when " +
                "ActuatorModelMapper is null");
    }

    /*
     * Test that the constructor of ActuatorServiceImpl throws an IllegalArgumentException when the IDeviceRepository
     *  parameter is null
     */
    @Test
    void testConstructorActuatorServiceThrowsExceptionForNullDeviceRepository() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorServiceImpl(mockActuatorTypeMapper,
                mockActuatorTypeRepository, mockActuatorModelRepository, mockActuatorFactory, mockActuatorRepository,
                mockActuatorMapper, mockActuatorModelMapper, null), "Constructor should throw an exception when " +
                "IDeviceRepository is null");
    }

    /*
     * Test that the getActuatorTypes method returns a non-null list of ActuatorTypeDTO objects
     */
    @Test
    void testGetActuatorTypes() {
        //Arrange
        when(mockActuatorTypeRepository.findAll()).thenReturn(new ArrayList<>());
        when(mockActuatorTypeMapper.toActuatorTypesDTO(new ArrayList<>())).thenReturn(new ArrayList<>());
        //Act
        List<ActuatorTypeDTO> result = actuatorService.getActuatorTypes();
        //Assert
        assertNotNull(result, "The getActuatorTypes method should return a non-null list of ActuatorTypeDTO objects");
    }

    /*
     * Test that the getActuatorTypes method returns a list of ActuatorTypeDTO objects with the same size as the list
     * of ActuatorType objects when the repository is empty
     */

    @Test
    void testGetActuatorTypesEmptyRepository() {
        // Arrange
        int expectedSize = 0;

        // Act
        List<ActuatorTypeDTO> result = actuatorService.getActuatorTypes();
        // Assert
        assertEquals(expectedSize, result.size(), "The getActuatorTypes method should return a list of " +
                "ActuatorTypeDTO " + "objects with the same size as the list of ActuatorType objects when the " +
                "repository is empty");
    }

    /*
     * Test that the getActuatorTypes method returns a list of ActuatorTypeDTO objects with the same size as the list
     *  of ActuatorType objects when the repository is not empty
     */
    @Test
    void testGetActuatorTypesOneTypeInRepository() {
        // Arrange
        int expectedSize = 1;
        List<ActuatorTypeDTO> actuatorTypeDTOList = new ArrayList<>();
        actuatorTypeDTOList.add(mockActuatorTypeDTO);
        when(mockActuatorTypeRepository.findAll()).thenReturn(new ArrayList<>());
        when(mockActuatorTypeMapper.toActuatorTypesDTO(new ArrayList<>())).thenReturn(actuatorTypeDTOList);

        // Act
        List<ActuatorTypeDTO> result = actuatorService.getActuatorTypes();
        // Assert
        assertEquals(expectedSize, result.size(), "The getActuatorTypes method should return a list of " +
                "ActuatorTypeDTO " + "objects with the same size as the list of ActuatorType objects");
    }


    /**
     * Test that the getActuatorModels method returns a non-null list of ActuatorModelDTO objects when the
     * ActuatorTypeName exists
     */
    @Test
    void testGetActuatorModelsWhenActuatorTypeNameExists() {
        //Arrange
        when(mockActuatorTypeMapper.toActuatorTypeName(mockActuatorTypeDTO)).thenReturn(mockActuatorTypeName);
        when(mockActuatorTypeRepository.containsIdentity(mockActuatorTypeName)).thenReturn(true);
        //Act
        List<ActuatorModelDTO> result = actuatorService.getActuatorModels(mockActuatorTypeDTO);
        //Assert
        assertNotNull(result, "The getActuatorModels method should return a non-null list of ActuatorModelDTO " +
                "objects" + " when the ActuatorTypeName exists");
    }

    /**
     * Test that the getActuatorModels method returns null when an exception is thrown
     */
    @Test
    void testGetActuatorModelsReturnNullWhenExceptionThrown() {
        //Arrange
        when(mockActuatorTypeMapper.toActuatorTypeName(mockActuatorTypeDTO)).thenReturn(mockActuatorTypeName);
        when(mockActuatorModelRepository.findByActuatorTypeIdentity(mockActuatorTypeName)).thenThrow(new IllegalArgumentException());
        //Act
        List<ActuatorModelDTO> result = actuatorService.getActuatorModels(mockActuatorTypeDTO);
        //Assert
        assertNull(result, "The getActuatorModels method should return null when an exception is thrown");
    }

    /*
     * Test that the getActuatorModels method returns a list of ActuatorModelDTO objects with the same size as the
     * list of ActuatorModel objects when the repository is empty
     */
    @Test
    void testGetActuatorModelsEmptyRepository() {
        // Arrange
        int expectedSize = 0;
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO("actuatorTypeName");
        when(mockActuatorTypeMapper.toActuatorTypeName(any())).thenReturn(new ActuatorTypeName("actuatorTypeName"));
        when(mockActuatorModelRepository.findByActuatorTypeIdentity(any())).thenReturn(new ArrayList<>());

        // Act
        List<ActuatorModelDTO> result = actuatorService.getActuatorModels(actuatorTypeDTO);

        // Assert
        assertEquals(expectedSize, result.size(), "The getActuatorModels method should return a list of " +
                "ActuatorModelDTO " + "objects with the same size as the list of ActuatorModel objects when the " +
                "repository is empty");
    }

    /*
     * Test that the getActuatorModels method returns a list of ActuatorModelDTO objects with the same size as the
     * list of ActuatorModel objects when the repository is not empty
     */
    @Test
    void testGetActuatorModelsOneModelsInRepository() {
        // Arrange
        int expectedSize = 1;
        List<ActuatorModelDTO> actuatorModelDTOList = new ArrayList<>();
        actuatorModelDTOList.add(mockActuatorModelDTO);
        when(mockActuatorModelRepository.findAll()).thenReturn(new ArrayList<>());
        when(mockActuatorModelMapper.toActuatorModelsDTO(new ArrayList<>())).thenReturn(actuatorModelDTOList);

        // Act
        List<ActuatorModelDTO> result = actuatorService.getActuatorModels(mockActuatorTypeDTO);
        // Assert
        assertEquals(expectedSize, result.size(), "The getActuatorTypes method should return a list of " +
                "ActuatorTypeDTO " + "objects with the same size as the list of ActuatorType objects");
    }

    /**
     * Test that the addActuator method returns a non-null ActuatorDTO object when the mockActuator is added
     * successfully
     */
    @Test
    void testAddActuatorShouldReturnNotNullActuator() {
        when(mockActuatorMapper.toDeviceId(mockActuatorDTO)).thenReturn(mockDeviceID);
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(true);
        when(mockActuatorMapper.toActuatorMap(mockActuatorDTO)).thenReturn(mockActuatorMap);
        when(mockActuatorFactory.createActuator(mockActuatorMap)).thenReturn(mockActuator);
        when(mockActuatorRepository.save(mockActuator)).thenReturn(mockActuator);
        when(mockActuatorMapper.actuatorToDTO(mockActuator)).thenReturn(mockActuatorDTO);
        //Act
        ActuatorDTO result = actuatorService.addActuator(mockActuatorDTO);
        //Assert
        assertNotNull(result, "The addActuator method should return a non-null ActuatorDTO object when the " +
                "mockActuator " + "is added " + "successfully");
    }

    /**
     * Test that the addActuator method returns null when the mockActuatorDTO is null
     */
    @Test
    void testAddActuatorInvalidDeviceIdShouldReturnNull() {
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(false);
        //Act
        ActuatorDTO result = actuatorService.addActuator(mockActuatorDTO);
        //Assert
        assertNull(result, "The addActuator method should return null when the device ID is invalid");
    }

    /**
     * Test that the addActuator method returns null when an exception is thrown
     */

    @Test
    void testAddActuatorShouldReturnNullWhenExceptionThrown() {
        //Arrange
        ActuatorDTO actuatorDTO = mock(ActuatorDTO.class);
        when(mockActuatorMapper.toDeviceId(actuatorDTO)).thenThrow(new RuntimeException());

        //Act
        ActuatorDTO result = actuatorService.addActuator(actuatorDTO);

        //Assert
        assertNull(result);
    }
}