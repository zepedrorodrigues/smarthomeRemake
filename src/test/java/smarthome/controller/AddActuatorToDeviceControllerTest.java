package smarthome.controller;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.actuator.ActuatorFactoryImpl;
import smarthome.domain.actuator.ActuatorOfBlindRollerFactory;
import smarthome.domain.actuator.ActuatorOfDecimalLimiterFactory;
import smarthome.domain.actuator.ActuatorOfLimiterFactory;
import smarthome.domain.actuator.ActuatorOfOnOffSwitchFactory;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.ActuatorModelFactory;
import smarthome.domain.actuatormodel.ActuatorModelFactoryImpl;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.ActuatorTypeFactory;
import smarthome.domain.actuatortype.ActuatorTypeFactoryImpl;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.DeviceFactoryImpl;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.reading.ReadingFactory;
import smarthome.domain.reading.ReadingFactoryImpl;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorTypeDTO;
import smarthome.mapper.mapper.ActuatorMapper;
import smarthome.mapper.mapper.ActuatorModelMapper;
import smarthome.mapper.mapper.ActuatorTypeMapper;
import smarthome.persistence.mem.ActuatorModelRepositoryMemImpl;
import smarthome.service.IActuatorModelService;
import smarthome.service.IActuatorService;
import smarthome.service.IActuatorTypeService;
import smarthome.service.impl.ActuatorModelServiceImpl;
import smarthome.service.impl.ActuatorServiceImpl;
import smarthome.service.impl.ActuatorTypeServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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
 * This class is responsible for testing the AddActuatorToDeviceController class.
 * It includes tests for the constructor, and methods of the AddActuatorToDeviceController class.
 * Each test case is designed to test a specific functionality or a specific scenario.
 */
class AddActuatorToDeviceControllerTest {

    //Dependencies for the Actuator Service
    IActuatorRepository mockActuatorRepository;
    IActuatorTypeRepository mockActuatorTypeRepository;
    IActuatorModelRepository mockActuatorModelRepository;
    IDeviceRepository mockDeviceRepository;
    ISensorRepository mockSensorRepository;
    IReadingRepository mockReadingRepository;
    ActuatorFactory actuatorFactory;
    ActuatorMapper actuatorMapper;
    ActuatorTypeMapper actuatorTypeMapper;
    ActuatorModelMapper actuatorModelMapper;
    IActuatorService actuatorService;
    IActuatorTypeService actuatorTypeService;
    IActuatorModelService actuatorModelService;
    ReadingFactory readingFactory;
    IRoomRepository mockRoomRepository;
    DeviceFactory deviceFactory;

    //Test data for the ActuatorDTO//ActuatorTypeDTO //ActuatorModelDTO
    String actuatorModelName;
    String actuatorTypeName;
    String actuatorId;
    DeviceId deviceId;

    Device mockDevice;
    String deviceDTOName;
    String deviceDTOType;
    String deviceDTORoomId;
    String deviceDTOId;

    String filePathName = "configTest.properties";

    //Controller for adding actuators to devices
    AddActuatorToDeviceController addActuatorToDeviceController;

    /**
     * This method is executed before each test.
     * It sets up the necessary objects and state for the tests.
     */
    @BeforeEach
    void setUp() throws ConfigurationException {
        //Dependencies for the Actuator Service
        mockActuatorRepository = mock(IActuatorRepository.class);
        mockActuatorTypeRepository = mock(IActuatorTypeRepository.class);
        mockActuatorModelRepository = mock(IActuatorModelRepository.class);
        mockDeviceRepository = mock(IDeviceRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockReadingRepository = mock(IReadingRepository.class);
        actuatorFactory = new ActuatorFactoryImpl(new ActuatorOfBlindRollerFactory(),new ActuatorOfDecimalLimiterFactory(), new ActuatorOfLimiterFactory(), new ActuatorOfOnOffSwitchFactory());
        actuatorMapper = new ActuatorMapper();
        actuatorTypeMapper = new ActuatorTypeMapper();
        actuatorModelMapper = new ActuatorModelMapper();
        mockRoomRepository = mock(IRoomRepository.class);
        deviceFactory = new DeviceFactoryImpl();
        readingFactory = new ReadingFactoryImpl();


        //Initialize the Actuator Service
        actuatorService = new ActuatorServiceImpl(mockActuatorRepository, mockDeviceRepository, mockSensorRepository,
                mockReadingRepository, actuatorFactory, readingFactory, filePathName);

        actuatorTypeService = new ActuatorTypeServiceImpl(mockActuatorTypeRepository);
        actuatorModelService = new ActuatorModelServiceImpl(mockActuatorModelRepository);

        //Test data for the DTO
        actuatorModelName = "testActuatorModel";
        actuatorTypeName = "testActuatorName";
        actuatorId = "testActuatorId";


        //Add a device to a room
        deviceDTOName = "testDeviceName";
        deviceDTOType = "testDeviceType";
        deviceDTORoomId = "testRoomId";


        // Add a device to a room
        RoomId roomId = new RoomId(deviceDTORoomId);
        when(mockRoomRepository.containsIdentity(roomId)).thenReturn(true);

        // Mock the existence of the device in the repository
        mockDevice = deviceFactory.createDevice(new DeviceName(deviceDTOName), new DeviceTypeName(deviceDTOType),
                roomId);
        when(mockDeviceRepository.save(mockDevice)).thenReturn(mockDevice);
        deviceDTOId = mockDevice.getIdentity().toString();
        deviceId = new DeviceId(deviceDTOId);
        when(mockDeviceRepository.findByIdentity(deviceId)).thenReturn(Optional.of(mockDevice));
        when(mockDeviceRepository.update(mockDevice)).thenReturn(mockDevice);


        //Controller for adding actuators to devices
        addActuatorToDeviceController = new AddActuatorToDeviceController(actuatorService, actuatorTypeService,
                actuatorModelService, actuatorTypeMapper, actuatorModelMapper, actuatorMapper);

    }

    /**
     * This test case checks if the constructor creates an instance of the AddActuatorToDeviceController class when
     * all dependencies are valid.
     */
    @Test
    void testConstructorWithValidActuatorService() {
        //Act
        AddActuatorToDeviceController controller = new AddActuatorToDeviceController(actuatorService,
                actuatorTypeService, actuatorModelService, actuatorTypeMapper, actuatorModelMapper, actuatorMapper);
        //Assert
        assertNotNull(controller, "The controller should not be null when the ActuatorService is valid");
    }

    /**
     * This test case checks if the getActuatorTypes method returns an empty list when the repository is empty.
     */
    @Test
    void getActuatorTypesEmptyRepositoryReturnsEmptyList() {
        //Arrange
        when(mockActuatorTypeRepository.findAll()).thenReturn(Collections.emptyList());
        //Act
        List<ActuatorTypeDTO> actuatorTypes = addActuatorToDeviceController.getActuatorTypes();
        //Assert
        assertTrue(actuatorTypes.isEmpty(), "The list of actuator types should be empty");
    }

    /**
     * This test case checks if the getActuatorTypes method returns a list of actuator types when the repository is not
     * empty.
     */
    @Test
    void getActuatorTypesListNonEmptyRepositoryReturnsActuatorTypes() {
        //Arrange
        ActuatorType actuatorType = new ActuatorType(new ActuatorTypeName(actuatorTypeName));
        when(mockActuatorTypeRepository.findAll()).thenReturn(Collections.singletonList(actuatorType));
        //Act
        List<ActuatorTypeDTO> actuatorTypes = addActuatorToDeviceController.getActuatorTypes();
        //Assert
        assertFalse(actuatorTypes.isEmpty(), "The list of actuator types should not be empty");
    }

    /**
     * This test case checks if the getActuatorTypes method returns a list of actuator types when the repository is not
     * empty.
     */
    @Test
    void testGetActuatorTypesNonEmptyRepositoryVerifyDTO() {
        // Arrange
        ActuatorTypeFactory actuatorTypeFactory = new ActuatorTypeFactoryImpl();
        ActuatorType actuatorType1 = actuatorTypeFactory.createActuatorType(new ActuatorTypeName(actuatorTypeName));
        ActuatorType actuatorType2 = actuatorTypeFactory.createActuatorType(new ActuatorTypeName("OnOff"));
        List<ActuatorType> actuatorTypes = Arrays.asList(actuatorType1, actuatorType2);
        when(mockActuatorTypeRepository.findAll()).thenReturn(actuatorTypes);
        // Act
        List<ActuatorTypeDTO> actuatorTypeDTOs = addActuatorToDeviceController.getActuatorTypes();
        // Assert
        assertEquals(actuatorTypeDTOs.size(), 2, "The list of actuator types should have 2 elements");
    }

    /**
     * This test case checks if the getActuatorModelsByActuatorType method throws an IllegalArgumentException when the
     * ActuatorTypeDTO is null.
     */

    @Test
    void testGetActuatorModelsByActuatorTypeNullActuatorTypeDTOThrowsException() {
        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> addActuatorToDeviceController.getActuatorModelsByActuatorType(null));
    }

    /**
     * This test case checks if the getActuatorModelsByActuatorType method returns an empty list when the repository is
     * empty.
     */
    @Test
    void testGetActuatorModelsEmptyRepository() throws ConfigurationException {
        //Arrange
        IActuatorModelRepository emptyActuatorModelRepository = new ActuatorModelRepositoryMemImpl();
        ActuatorModelMapper actuatorModelMapper = new ActuatorModelMapper();
        IActuatorService emptyActuatorService = new ActuatorServiceImpl(mockActuatorRepository, mockDeviceRepository,
                mockSensorRepository, mockReadingRepository, actuatorFactory, readingFactory, filePathName);
        AddActuatorToDeviceController controller = new AddActuatorToDeviceController(emptyActuatorService,
                actuatorTypeService, new ActuatorModelServiceImpl(emptyActuatorModelRepository), actuatorTypeMapper,
                actuatorModelMapper, actuatorMapper);
        ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO("testActuatorType");
        //Act
        List<ActuatorModelDTO> actuatorModels = controller.getActuatorModelsByActuatorType(actuatorTypeDTO);
        boolean result = !actuatorModels.iterator().hasNext(); //isEmpty
        //Assert
        assertTrue(result, "The list should be empty when the repository is empty");
    }

    /*
     * This test case checks if the getActuatorModelsByActuatorType method returns a list of actuator models when the
     * repository is not empty.
     */
    @Test
    void testGetActuatorModelsListNonEmptyRepository() {
        //Arrange
        ActuatorModelFactory actuatorModelFactory = new ActuatorModelFactoryImpl();
        ActuatorModel actuatorModel =
                actuatorModelFactory.createActuatorModel(new ActuatorModelName(actuatorModelName),
                        new ActuatorTypeName("OnOff"));
        ActuatorModel actuatorModel2 = actuatorModelFactory.createActuatorModel(new ActuatorModelName(
                "testActuatorModel2"), new ActuatorTypeName("OnOff"));
        List<ActuatorModel> actuatorModels = Arrays.asList(actuatorModel, actuatorModel2);
        when(mockActuatorModelRepository.findActuatorModelsByActuatorTypeName(new ActuatorTypeName("OnOff"))).thenReturn(actuatorModels);
        int expectedSize = 2;
        //Act
        Iterable<ActuatorModelDTO> actuatorModelsDTO =
                addActuatorToDeviceController.getActuatorModelsByActuatorType(new ActuatorTypeDTO("OnOff"));
        long result = StreamSupport.stream(actuatorModelsDTO.spliterator(), false).count();
        //Assert
        assertEquals(expectedSize, result, "The size of the list should be 2 when the repository is not empty");
    }

    /**
     * This test case checks if the getActuatorModelsByActuatorType method returns an empty list when the actuator type
     * does not exist in the repository.
     */

    @Test
    void testGetActuatorModelsNonEmptyRepositoryVerifyDTO() {
        // Arrange
        ActuatorModelFactory actuatorModelFactory = new ActuatorModelFactoryImpl();
        ActuatorModel actuatorModel1 =
                actuatorModelFactory.createActuatorModel(new ActuatorModelName(actuatorModelName),
                        new ActuatorTypeName("OnOff"));
        ActuatorModel actuatorModel2 = actuatorModelFactory.createActuatorModel(new ActuatorModelName(
                "testActuatorModel2"), new ActuatorTypeName(actuatorTypeName));
        List<ActuatorModel> actuatorModels = Arrays.asList(actuatorModel1, actuatorModel2);

        when(mockActuatorModelRepository.findActuatorModelsByActuatorTypeName(new ActuatorTypeName(actuatorTypeName))).thenReturn(actuatorModels);

        // Act
        List<ActuatorModelDTO> actuatorModelsDTO =
                addActuatorToDeviceController.getActuatorModelsByActuatorType(new ActuatorTypeDTO(actuatorTypeName));
        ActuatorModelDTO resultActuatorModelDTO = actuatorModelsDTO.get(0);

        boolean result = actuatorModelName.equals(resultActuatorModelDTO.getActuatorModelName());
        // Assert
        assertTrue(result, "The actuator model name should be correct");
    }

    /**
     * This test case checks if the getActuatorModelsByActuatorType method returns an empty list when the actuator type
     * does not exist in the repository.
     */
    @Test
    void testGetActuatorModelsWrongActuatorName() {
        //Arrange
        ActuatorTypeDTO wrongActuatorTypeDTO = new ActuatorTypeDTO("wrongActuatorType");
        //Act
        Iterable<ActuatorModelDTO> actuatorModels =
                addActuatorToDeviceController.getActuatorModelsByActuatorType(wrongActuatorTypeDTO);
        boolean result = !actuatorModels.iterator().hasNext(); //isEmpty
        //Assert
        assertTrue(result, "The list should be empty because the actuator type does not exist");
    }

    /**
     * Test to verify that the addActuator method of AddActuatorToDeviceController returns a valid ActuatorDTO when
     * the repository is empty and the actuator model name is "ActuatorOfOnOffSwitch".
     * It checks if the returned ActuatorDTO is not null and has the expected device ID and actuator model name.
     */
    @Test
    void testAddOnOffActuatorEmptyRepositoryReturnsTrue() {
        //Arrange
        DeviceId deviceId = new DeviceId(deviceDTOId);
        when(mockDeviceRepository.containsIdentity(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String actuatorModelName = "ActuatorOfOnOffSwitch";
        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null, null, null, null);
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);

        boolean result =
                !resultActuatorDTO.getActuatorId().isBlank() && deviceDTOId.equals(resultActuatorDTO.getDeviceId()) && actuatorModelName.equals(resultActuatorDTO.getActuatorModelName());
        //Assert
        assertTrue(result,
                "The result should be true because the actuator model name, device id, and actuator id " + "are" + " "
                        + "valid");
    }

    /**
     * Test to verify that the addActuator method of AddActuatorToDeviceController returns a valid ActuatorDTO when
     * the repository is empty and the actuator model name is "ActuatorOfBlindRoller".
     * It checks if the returned ActuatorDTO is not null and has the expected device ID and actuator model name.
     */
    @Test
    void testAddBlindRollerActuatorEmptyRepositoryReturnsTrue() {
        //Arrange
        DeviceId deviceId = new DeviceId(deviceDTOId);
        when(mockDeviceRepository.containsIdentity(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String actuatorModelName = "ActuatorOfBlindRoller";
        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null, null, null, null);
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);

        boolean result =
                !resultActuatorDTO.getActuatorId().isBlank() && deviceDTOId.equals(resultActuatorDTO.getDeviceId()) && actuatorModelName.equals(resultActuatorDTO.getActuatorModelName());
        //Assert
        assertTrue(result,
                "The result should be true because the actuator model name, device id, and actuator id " + "are" + " "
                        + "valid");
    }

    /**
     * Test to verify that the addActuator method of AddActuatorToDeviceController returns a valid ActuatorDTO when
     * the repository is empty and the actuator model name is "ActuatorOfLimiter".
     * It checks if the returned ActuatorDTO is not null and has the expected device ID, actuator model name, and
     * integer upper and lower limits.
     */
    @Test
    void testAddLimiterActuatorEmptyRepositoryReturnsTrue() {
        //Arrange
        DeviceId deviceId = new DeviceId(deviceDTOId);
        when(mockDeviceRepository.containsIdentity(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String actuatorModelName = "ActuatorOfLimiter";
        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, 10, 9, null, null, null);
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);

        boolean result =
                !resultActuatorDTO.getActuatorId().isBlank() && deviceDTOId.equals(resultActuatorDTO.getDeviceId()) && actuatorModelName.equals(resultActuatorDTO.getActuatorModelName()) && resultActuatorDTO.getIntegerUpperLimit() == 10 && resultActuatorDTO.getIntegerLowerLimit() == 9;
        //Assert
        assertTrue(result,
                "The result should be true because the actuator model name, device id, and actuator id " + "are" + " "
                        + "valid");
    }


    /**
     * Test to verify that the addActuator method of AddActuatorToDeviceController returns a valid ActuatorDTO when
     * the repository is empty and the actuator model name is "ActuatorOfDecimalLimiter".
     * It checks if the returned ActuatorDTO is not null and has the expected device ID, actuator model name, and
     * double upper and lower limits, and precision.
     */
    @Test
    void testAddLimiterDecimalActuatorEmptyRepositoryReturnsTrue() {
        //Arrange
        DeviceId deviceId = new DeviceId(deviceDTOId);
        when(mockDeviceRepository.containsIdentity(deviceId)).thenReturn(true);
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        String actuatorModelName = "ActuatorOfDecimalLimiter";
        //Act
        ActuatorDTO actuatorDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null, 11.2, 10.5, 1);
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);

        boolean result =
                !resultActuatorDTO.getActuatorId().isBlank() && deviceDTOId.equals(resultActuatorDTO.getDeviceId()) && actuatorModelName.equals(resultActuatorDTO.getActuatorModelName()) && resultActuatorDTO.getDoubleUpperLimit() == 11.2 && resultActuatorDTO.getDoubleLowerLimit() == 10.5 && resultActuatorDTO.getDoubleLimitPrecision() == 1;
        //Assert
        assertTrue(result,
                "The result should be true because the actuator model name, device id, and actuator id " + "are" + " "
                        + "valid");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a blank device id.
     */

    @Test
    void testAddActuatorEmptyRepositoryActuatorModelNameNull() {
        //Arrange
        String invalidActuatorModelName = null;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, invalidActuatorModelName, null, null, null, null,
                null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the actuator model name is null");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a blank model name.
     */
    @Test
    void testAddActuatorEmptyRepositoryBlankModelName() {
        //Arrange
        String invalidActuatorModelName = " ";
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, invalidActuatorModelName, null, null, null, null,
                null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the actuator model name is blank");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has invalid actuator model
     * name.
     */
    @Test
    void testAddActuatorEmptyRepositoryDifferentModelName() {
        //Arrange
        String invalidActuatorModelName = "NonExistentModelName";
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, invalidActuatorModelName, null, null, null, null,
                null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the actuator model name does not exist");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a null device id.
     */
    @Test
    void testAddActuatorEmptyRepositoryNullDeviceId() {
        //Arrange
        String invalidDeviceId = null;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, invalidDeviceId, actuatorModelName, null, null, null, null, null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the device id is null");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a blank device id.
     */
    @Test
    void testAddActuatorEmptyRepositoryBlankDeviceId() {
        //Arrange
        String invalidDeviceId = " ";
        ActuatorDTO onOffDTO = new ActuatorDTO(null, invalidDeviceId, actuatorModelName, null, null, null, null, null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the device id is blank");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a null integer upper limit.
     */
    @Test
    void testAddActuatorOfLimiterEmptyRepositoryNullIntegerUpperLimit() {
        //Arrange
        String actuatorModelName = "ActuatorOfLimiter";
        Integer invalidIntegerUpperLimit = null;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, invalidIntegerUpperLimit, 10,
                null, null, null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the upper limit is null");
    }


    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a null integer lower limit.
     */
    @Test
    void testAddActuatorOfLimiterEmptyRepositoryNullIntegerLowerLimit() {
        //Arrange
        String actuatorModelName = "ActuatorOfLimiter";
        Integer invalidIntegerLowerLimit = null;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, 19, invalidIntegerLowerLimit,
                null, null, null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the lower limit is null");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a lower limit greater
     * than the upper limit.
     */
    @Test
    void testAddActuatorOfLimiterEmptyRepositoryLowerLimitGreaterThanUpperLimit() {
        //Arrange
        String actuatorModelName = "ActuatorOfLimiter";
        Integer invalidIntegerLowerLimit = 20;
        Integer invalidIntegerUpperLimit = 19;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, invalidIntegerUpperLimit,
                invalidIntegerLowerLimit, null, null, null);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO,
                "The result should be null because the lower limit is greater than the upper " + "limit");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a null double upper limit.
     */
    @Test
    void testAddActuatorOfDecimalLimiterEmptyRepositoryNullDoubleUpperLimit() {
        //Arrange
        String actuatorModelName = "ActuatorOfDecimalLimiter";
        Double invalidDoubleUpperLimit = null;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null,
                invalidDoubleUpperLimit, 10.5, 1);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the upper limit is null");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a null double lower limit.
     */
    @Test
    void testAddActuatorOfDecimalLimiterEmptyRepositoryNullDoubleLowerLimit() {
        //Arrange
        String actuatorModelName = "ActuatorOfDecimalLimiter";
        Double invalidDoubleLowerLimit = null;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null, 10.5,
                invalidDoubleLowerLimit, 1);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the lower limit is null");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a lower limit greater
     * than the upper limit.
     */
    @Test
    void testAddActuatorOfDecimalLimiterEmptyRepositoryDoubleLowerLimitGreaterThanUpperLimit() {
        //Arrange
        String actuatorModelName = "ActuatorOfDecimalLimiter";
        Double invalidDoubleLowerLimit = 20.3;
        Double invalidDoubleUpperLimit = 19.3;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null,
                invalidDoubleUpperLimit, invalidDoubleLowerLimit, 1);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO,
                "The result should be null because the lower limit is greater than the upper " + "limit");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a null precision.
     */
    @Test
    void testAddActuatorOfDecimalLimiterEmptyRepositoryNullPrecision() {
        //Arrange
        String actuatorModelName = "ActuatorOfDecimalLimiter";
        Integer invalidPrecision = null;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null, 20.5, 19.5,
                invalidPrecision);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the precision is null");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a zero precision.
     */
    @Test
    void testAddActuatorIOfLimiterEmptyRepositoryPrecisionZero() {
        //Arrange
        String actuatorModelName = "ActuatorOfLimiter";
        Integer invalidPrecision = 0;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null, 20.5, 19.5,
                invalidPrecision);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the precision is zero");
    }

    /**
     * This test case checks if the addActuator method returns null when the ActuatorDTO has a negative precision.
     */
    @Test
    void testAddActuatorOfDecimalLimiterEmptyRepositoryNegativePrecision() {
        //Arrange
        String actuatorModelName = "ActuatorOfDecimalLimiter";
        Integer invalidPrecision = -1;
        ActuatorDTO onOffDTO = new ActuatorDTO(null, deviceDTOId, actuatorModelName, null, null, 20.5, 19.5,
                invalidPrecision);
        //Act
        ActuatorDTO resultActuatorDTO = addActuatorToDeviceController.addActuator(onOffDTO, deviceId);
        //Assert
        assertNull(resultActuatorDTO, "The result should be null because the precision is negative");
    }

    /**
     * This test case checks if the addActuator method returns null when the device id is null.
     */
    @Test
    void testAddActuatorWhenDeviceIdIsNull() {
        //Arrange
        actuatorId = "actuatorId";
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId, null, actuatorModelName, null, null, null, null, null);
        //Act
        ActuatorDTO result = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);
        //Assert
        assertNull(result, "The result should be null because the device id is null");
    }

    /**
     * This test case checks if the addActuator method returns null when the device id is blank.
     */
    @Test
    void testAddActuatorWhenDeviceIdIsBlank() {
        //Arrange
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId, " ", actuatorModelName, null, null, null, null, null);
        //Act
        ActuatorDTO result = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);
        //Assert
        assertNull(result, "The result should be null because the device id is blank");
    }

    /**
     * This test case checks if the addActuator method returns null when the device id does not exist.
     */
    @Test
    void testAddActuatorWhenDeviceIdDoesNotExist() {
        //Arrange
        String nonExistentDeviceId = "nonExistentDeviceId";
        ActuatorDTO actuatorDTO = new ActuatorDTO(actuatorId, nonExistentDeviceId, actuatorModelName, null, null,
                null, null, null);
        //Act
        ActuatorDTO result = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);
        //Assert
        assertNull(result, "The result should be null because the device id does not exist");
    }

    /*
     * This test case checks if the addActuator method returns null when the actuator model name is null.
     */
    @Test
    void testAddActuatorReturnsNullWhenActuatorDTOIsNull() {
        //Arrange
        ActuatorDTO actuatorDTO = null;
        //Act
        ActuatorDTO result = addActuatorToDeviceController.addActuator(actuatorDTO, deviceId);
        //Assert
        assertNull(result, "The result should be null because the ActuatorDTO is null");
    }

    /**
     * This test case checks if the createActuator method of the ActuatorFactory class throws an
     * IllegalArgumentException
     * when a null ActuatorMap object is passed as an argument.
     */
    @Test
    void testCreateActuatorDataMapNull() {
        //Arrange
        ActuatorMap actuatorMap = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorFactory.createActuator(actuatorMap),
                "The method " + "should throw an exception for a null ActuatorMap object");
    }
}


