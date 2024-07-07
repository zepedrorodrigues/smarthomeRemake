package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.actuator.ActuatorOfBlindRoller;
import smarthome.domain.actuator.ActuatorOfLimiter;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuator.vo.DecimalLimit;
import smarthome.domain.actuator.vo.IntLimit;
import smarthome.domain.actuator.vo.Precision;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.ReadingFactory;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.ScalePercentageValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.service.IActuatorService;

import javax.naming.ConfigurationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the ActuatorServiceImpl class.
 * It uses mock objects to isolate the class under test and to provide control over the indirect inputs to the class.
 * The mock objects are created using the Mockito framework.
 */
class ActuatorServiceImplTest {

    // Mock objects for the repositories
    IActuatorRepository mockActuatorRepository;
    IDeviceRepository mockDeviceRepository;
    ISensorRepository mockSensorRepository;
    IReadingRepository mockReadingRepository;

    // Mock object for the ActuatorFactory
    ActuatorFactory mockActuatorFactory;

    // Instance of the class under test
    IActuatorService actuatorRESTService;

    // Mock objects for the ActuatorMap
    ActuatorMap mockActuatorMap;

    // Other mock objects
    DeviceId mockDeviceID;
    Actuator mockActuator;
    ActuatorOfBlindRoller mockActuatorOfBlindRoller;
    ActuatorId mockActuatorId;
    Device mockDevice;
    DeviceStatus mockDeviceStatus;
    ScalePercentageValue mockScalePercentageValue;
    Value mockValue;
    Reading mockReading;
    SensorId mockSensorId;
    Sensor mockSensor;
    ReadingFactory mockReadingFactory;
    TimeStamp mockTimeStamp;

    // Configuration file path
    String filePathName;


    /**
     * This method is executed before each test. It initializes the mock objects and the class under test.
     */
    @BeforeEach
    public void setUp() throws ConfigurationException, org.apache.commons.configuration2.ex.ConfigurationException {

        // Create mock objects for the repositories
        mockActuatorRepository = mock(IActuatorRepository.class);
        mockDeviceRepository = mock(IDeviceRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockReadingRepository = mock(IReadingRepository.class);
        mockScalePercentageValue = mock(ScalePercentageValue.class);
        mockValue = mock(Value.class);
        mockReading = mock(Reading.class);
        mockActuatorOfBlindRoller = mock(ActuatorOfBlindRoller.class);
        mockReadingFactory = mock(ReadingFactory.class);

        // Create a mock object for the ActuatorFactory
        mockActuatorFactory = mock(ActuatorFactory.class);

        // Configuration file path
        filePathName = "configTest.properties";

        // Initialize the class under test
        actuatorRESTService = new ActuatorServiceImpl(mockActuatorRepository, mockDeviceRepository,
                mockSensorRepository, mockReadingRepository, mockActuatorFactory, mockReadingFactory, filePathName);

        // Create mock objects for the ActuatorMap
        mockActuatorMap = mock(ActuatorMap.class);

        // Initialize other mock objects
        mockDeviceID = mock(DeviceId.class);
        mockActuator = mock(Actuator.class);
        mockActuatorId = mock(ActuatorId.class);
        mockDevice = mock(Device.class);
        mockDeviceStatus = mock(DeviceStatus.class);
        mockSensorId = mock(SensorId.class);
        mockSensor = mock(Sensor.class);
        mockTimeStamp = mock(TimeStamp.class);

        // Configure the mock objects
        when(mockSensor.getIdentity()).thenReturn(mockSensorId);
        when(mockActuatorMap.getActuatorId()).thenReturn(mock(ActuatorId.class));
        when(mockActuatorMap.getDeviceId()).thenReturn(mockDeviceID);
        when(mockActuatorMap.getActuatorModelName()).thenReturn(mock(ActuatorModelName.class));
        when(mockActuatorMap.getIntegerUpperLimit()).thenReturn(mock(IntLimit.class));
        when(mockActuatorMap.getIntegerLowerLimit()).thenReturn(mock(IntLimit.class));
        when(mockActuatorMap.getDecimalUpperLimit()).thenReturn(mock(DecimalLimit.class));
        when(mockActuatorMap.getDecimalLowerLimit()).thenReturn(mock(DecimalLimit.class));
        when(mockActuatorMap.getPrecision()).thenReturn(mock(Precision.class));
    }

    /**
     * This test checks that the constructor of the ActuatorServiceImpl class works correctly.
     */
    @Test
    void testActuatorRESTServiceConstructorValid() throws
            org.apache.commons.configuration2.ex.ConfigurationException {
        //Act and Assert
        ActuatorServiceImpl actuatorRESTService = new ActuatorServiceImpl(mockActuatorRepository,
                mockDeviceRepository, mockSensorRepository, mockReadingRepository, mockActuatorFactory,
                mockReadingFactory, filePathName);
        assertNotNull(actuatorRESTService, "The constructor should initialize the ActuatorServiceImpl object " +
                "when" + " the parameters are valid.");

    }

    /**
     * This test checks that the addActuator method returns a null object when the Device does not exist.
     */
    @Test
    void testAddActuatorWhenDeviceDoesNotExist() {
        // Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(false);
        // Act
        Actuator result = actuatorRESTService.addActuator(mockActuatorMap, mockDeviceID);
        // Assert
        assertNull(result, "The addActuator method should return null when the Device does not exist.");
    }

    /**
     * This test checks that the addActuator method returns a non-null Actuator object when the Device exists.
     */
    @Test
    void testAddActuatorWhenDeviceExists() {
        // Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(true);
        when(mockActuatorFactory.createActuator(mockActuatorMap)).thenReturn(mockActuator);
        when(mockActuatorRepository.save(any(Actuator.class))).thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        Actuator result = actuatorRESTService.addActuator(mockActuatorMap, mockDeviceID);
        // Assert
        assertNotNull(result, "The addActuator method should return a non-null Actuator object when the Device " +
                "exists" + ".");

    }

    /**
     * This test checks that the addActuator method returns a null object when an exception is thrown.
     */
    @Test
    void testAddActuatorWhenExceptionIsThrown() {
        // Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(true);
        when(mockActuatorFactory.createActuator(any(ActuatorMap.class))).thenThrow(new RuntimeException());
        // Act
        Actuator result = actuatorRESTService.addActuator(mockActuatorMap, mockDeviceID);
        // Assert
        assertNull(result, "The addActuator method should return null when an exception is thrown.");
    }

    /**
     * This test checks that the getActuatorById method returns a non-null Actuator object when the Actuator exists.
     */
    @Test
    void testGetActuatorById() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuator));
        // Act
        Optional<Actuator> result = actuatorRESTService.getActuatorById(mockActuatorId);
        // Assert
        assertNotNull(result, "The getActuatorById method should return a non-null Actuator object when the Actuator "
                + "exists.");
    }

    /**
     * This test checks that the getActuatorById method returns an empty Optional when the Actuator does not exist.
     */
    @Test
    void testGetActuatorByIdWhenActuatorDoesNotExist() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.empty());
        // Act
        Optional<Actuator> result = (actuatorRESTService.getActuatorById(mockActuatorId));
        // Assert
        assertTrue(result.isEmpty(),
                "The getActuatorById method should return an empty Optional when the Actuator " + "does not exist.");


    }

    /**
     * This test checks that the getActuatorIdsByDeviceIdentity method returns null when the Device does not exist.
     */
    @Test
    void testGetActuatorIdsByDeviceIdentityWhenDeviceIdDoesNotExists() {
        // Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(false);
        // Act
        Iterable<ActuatorId> result = actuatorRESTService.getActuatorIdsByDeviceIdentity(mockDeviceID);
        // Assert
        assertNull(result,
                "The getActuatorIdsByDeviceIdentity method should return null when the Device does not " + "exist.");
    }

    /**
     * This test checks that the getActuatorIdsByDeviceIdentity method returns
     * an empty list when the Device does not have any Actuator.
     */
    @Test
    void testGetActuatorIdsByDeviceIdentityWhenDeviceIdDoesNotHaveAnyActuator() {
        // Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(mockDeviceID)).thenReturn(List.of());
        // Act
        Iterable<ActuatorId> result = actuatorRESTService.getActuatorIdsByDeviceIdentity(mockDeviceID);
        // Assert
        assertFalse(result.iterator().hasNext(),
                "Method should return an empty list when the Device does not have " + "any Actuator.");
    }

    /**
     * This test checks that the getActuatorIdsByDeviceIdentity method returns a list
     * with one ActuatorId when the Device has one Actuator.
     */
    @Test
    void testGetActuatorIdsByDeviceIdentityWhenDeviceIdHasOneActuator() {
        // Arrange
        ActuatorId mockActuatorId = mock(ActuatorId.class);
        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(mockDeviceID)).thenReturn(List.of(mockActuatorId));
        // Act
        Iterable<ActuatorId> result = actuatorRESTService.getActuatorIdsByDeviceIdentity(mockDeviceID);
        List<ActuatorId> actuatorIds = (List<ActuatorId>) result;
        // Assert
        assertTrue(actuatorIds.size() == 1 && actuatorIds.contains(mockActuatorId), "Method should return a list " +
                "with" + " one ActuatorId when the Device has one Actuator.");
    }

    /**
     * This test checks that the getActuatorIdsByDeviceIdentity method returns a list
     * with two ActuatorIds when the Device has two Actuators.
     */
    @Test
    void testGetActuatorIdsByDeviceIdentityWhenDeviceIdHasManyActuators() {
        // Arrange
        ActuatorId mockActuatorId1 = mock(ActuatorId.class);
        ActuatorId mockActuatorId2 = mock(ActuatorId.class);

        when(mockDeviceRepository.containsIdentity(mockDeviceID)).thenReturn(true);
        when(mockActuatorRepository.findActuatorIdsByDeviceId(mockDeviceID)).thenReturn(List.of(mockActuatorId1,
                mockActuatorId2));
        // Act
        Iterable<ActuatorId> result = actuatorRESTService.getActuatorIdsByDeviceIdentity(mockDeviceID);
        List<ActuatorId> actuatorIds = (List<ActuatorId>) result;
        // Assert
        assertTrue(actuatorIds.size() == 2 && actuatorIds.contains(mockActuatorId1) && actuatorIds.contains(mockActuatorId2), "Method should return a list with one ActuatorId when the Device has one Actuator.");
    }

    /**
     * Test for operateBlindRoller method.
     * This test checks that the operateBlindRoller method returns null when the Actuator does not exist.
     * The test passes if the method returns null.
     */
    @Test
    void testOperateBlindRollerReturnsNullWhenActuatorDoesNotExist() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.empty());
        // Act
        Value result = actuatorRESTService.operateBlindRoller(mockActuatorId, mockScalePercentageValue);
        // Assert
        assertNull(result, "The operateBlindRoller method should return null when the Actuator does not exist.");
    }

    /**
     * Test for operateBlindRoller method.
     * This test checks that the operateBlindRoller method returns null when the Actuator is not an instance of the
     * ActuatorOfBlindRoller class. The test passes if the method returns null.
     */
    @Test
    void testOperateBlindRollerReturnsNullWhenActuatorIsNotBlindRoller() {
        // Arrange
        ActuatorOfLimiter actuatorOfLimiter = mock(ActuatorOfLimiter.class);
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(actuatorOfLimiter));
        // Act
        Value result = actuatorRESTService.operateBlindRoller(mockActuatorId, mockScalePercentageValue);
        // Assert
        assertNull(result, "The operateBlindRoller method should return null when the Actuator is not a Blind Roller.");
    }

    /**
     * Test for operateBlindRoller method.
     * This test checks that the operateBlindRoller method returns null when the Device id does not exist. The test
     * passes if the method returns null.
     */
    @Test
    void testOperateBlindRollerReturnsNullWhenDeviceIdDoesNotExist() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuatorOfBlindRoller));
        when(mockActuatorOfBlindRoller.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.empty());
        // Act
        Value result = actuatorRESTService.operateBlindRoller(mockActuatorId, mockScalePercentageValue);
        // Assert
        assertNull(result, "The operateBlindRoller method should return null when the Device id does not exist.");
    }

    /**
     * Test for operateBlindRoller method.
     * This test checks that the method returns null when the device status is not active. The test passes if the method
     * returns null.
     */
    @Test
    void testOperateBlindRollerReturnsNullWhenDeviceStatusIsNotActive() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuatorOfBlindRoller));
        when(mockActuatorOfBlindRoller.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.of(mockDevice));
        when(mockDevice.getDeviceStatus()).thenReturn(mockDeviceStatus);
        when(mockDeviceStatus.getStatus()).thenReturn(false);
        // Act
        Value result = actuatorRESTService.operateBlindRoller(mockActuatorId, mockScalePercentageValue);
        // Assert
        assertNull(result, "The operateBlindRoller method should return null when the device status is not active.");
    }

    /**
     * Test for operateBlindRoller method.
     * This test checks that the method returns null when the value is invalid.
     * The test passes if the method returns null.
     */
    @Test
    void testOperateBlindRollerReturnsNullWhenValueIsInvalid() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuatorOfBlindRoller));
        when(mockActuatorOfBlindRoller.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.of(mockDevice));
        when(mockDevice.getDeviceStatus()).thenReturn(mockDeviceStatus);
        when(mockDeviceStatus.getStatus()).thenReturn(true);
        when(mockActuatorOfBlindRoller.operate(mockScalePercentageValue)).thenReturn(null);

        // Act
        Value result = actuatorRESTService.operateBlindRoller(mockActuatorId, mockScalePercentageValue);
        // Assert
        assertNull(result, "The operateBlindRoller method should return null when the value is invalid.");
    }

    /**
     * Test for operateBlindRoller method.
     * This test checks that the method returns the value when the operation is successful.
     * The test passes if the method returns the value.
     */
    @Test
    void testOperateBlindRollerReturnsValueWhenOperationIsSuccessful() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuatorOfBlindRoller));
        when(mockActuatorOfBlindRoller.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.of(mockDevice));
        when(mockDevice.getDeviceStatus()).thenReturn(mockDeviceStatus);
        when(mockDeviceStatus.getStatus()).thenReturn(true);
        when(mockActuatorOfBlindRoller.operate(mockScalePercentageValue)).thenReturn(mockScalePercentageValue);
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(mockDeviceID), any(SensorModelName.class)))
                .thenReturn(List.of(mockSensorId));
        when(mockReadingFactory.createReading(mockScalePercentageValue, mockSensorId, mockTimeStamp)).thenReturn(mockReading);
        when(mockReadingRepository.save(mockReading)).thenReturn(mockReading);
        // Act
        Value result = actuatorRESTService.operateBlindRoller(mockActuatorId, mockScalePercentageValue);
        // Assert
        assertEquals(mockScalePercentageValue, result, "The operateBlindRoller method should return the value when " +
                "the operation is successful.");
    }

    /**
     * Test for the operateBlindRoller method.
     * This test checks that the method returns null when the reading is invalid.
     * The test passes if the method returns null.
     */
    @Test
    void testOperateBlindRollerReturnsNullWhenInvalidReading() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuatorOfBlindRoller));
        when(mockActuatorOfBlindRoller.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.of(mockDevice));
        when(mockDevice.getDeviceStatus()).thenReturn(mockDeviceStatus);
        when(mockDeviceStatus.getStatus()).thenReturn(true);
        when(mockActuatorOfBlindRoller.operate(mockScalePercentageValue)).thenReturn(mockScalePercentageValue);
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(mockDeviceID), any(SensorModelName.class)))
                .thenReturn(List.of(mockSensorId));
        when(mockReadingFactory.createReading(eq(mockScalePercentageValue), eq(mockSensorId), any(TimeStamp.class))).thenThrow(new IllegalArgumentException());        // Act
        // Act
        Value result = actuatorRESTService.operateBlindRoller(mockActuatorId, mockScalePercentageValue);
        // Assert
        assertNull(result, "The operateBlindRoller method should return null when the reading is invalid.");
    }

    /**
     * Test for the getLastPercentageReading method.
     * This test checks if the method returns the last reading when all the data is valid.
     * The test passes if the method returns a Value.
     */
    @Test
    void testGetLastPercentageReadingReturnsValueSuccessCase() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuator));
        when(mockActuator.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.of(mockDevice));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(mockDeviceID),
                any(SensorModelName.class))).thenReturn(List.of(mockSensorId));
        when(mockReadingRepository.findLastReadingBySensorId(mockSensorId)).thenReturn(Optional.of(mockReading));
        when(mockReading.getValue()).thenReturn(mockValue);
        // Act
        Optional<Value> result = actuatorRESTService.getLastPercentageReading(mockActuatorId);
        // Assert
        assertTrue(result.isPresent(), "The getLastPercentageReading method should return a Value when all the data is valid.");
    }

    /**
     * Test for the getLastPercentageReading method.
     * This test checks if the method returns null when the Actuator does not exist.
     * The test passes if the method returns an empty Optional.
     */
    @Test
    void testGetLastPercentageReadingReturnsEmptyWhenActuatorDoesNotExist() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.empty());
        Optional<Value> expected = Optional.empty();
        // Act
        Optional<Value> result = actuatorRESTService.getLastPercentageReading(mockActuatorId);
        // Assert
        assertEquals(expected, result, "The getLastPercentageReading method should return an empty Optional when the Actuator does not exist.");
    }

    /**
     * Test for the getLastPercentageReading method.
     * This test checks if the method returns null when the Device does not exist.
     * The test passes if the method returns empty Optional.
     */
    @Test
    void testGetLastPercentageReadingReturnsEmptyWhenDeviceDoesNotExist() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuator));
        when(mockActuator.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.empty());
        Optional<Value> expected = Optional.empty();
        // Act
        Optional<Value> result = actuatorRESTService.getLastPercentageReading(mockActuatorId);
        // Assert
        assertEquals(expected, result, "The getLastPercentageReading method should return an empty Optional when the Device does not exist.");
    }

    /**
     * Test for the getLastPercentageReading method.
     * This test checks if the method returns null when the Sensor does not exist.
     * The test passes if the method returns empty Optional.
     */
    @Test
    void testGetLastPercentageReadingReturnsEmptyOptionalWhenSensorDoesNotExist() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuator));
        when(mockActuator.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.of(mockDevice));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(mockDeviceID),
                any(SensorModelName.class))).thenReturn(List.of());
        Optional<Value> expected = Optional.empty();
        // Act
        Optional<Value> result = actuatorRESTService.getLastPercentageReading(mockActuatorId);
        // Assert
        assertEquals(expected, result, "The getLastPercentageReading method should return an empty Optional when the Sensor does not exist.");
    }

    /**
     * Test for the getLastPercentageReading method.
     * This test checks if the method returns null when the Reading does not exist.
     * The test passes if the method returns an empty list.
     */
    @Test
    void testGetLastPercentageReadingReturnsEmptyListWhenReadingDoesNotExist() {
        // Arrange
        when(mockActuatorRepository.findByIdentity(mockActuatorId)).thenReturn(Optional.of(mockActuator));
        when(mockActuator.getDeviceId()).thenReturn(mockDeviceID);
        when(mockDeviceRepository.findByIdentity(mockDeviceID)).thenReturn(Optional.of(mockDevice));
        when(mockSensorRepository.findSensorIdsByDeviceIdAndSensorModelName(eq(mockDeviceID),
                any(SensorModelName.class))).thenReturn(List.of(mockSensorId));
        when(mockReadingRepository.findLastReadingBySensorId(mockSensorId)).thenReturn(Optional.empty());
        // Act
        Optional<Value> result = actuatorRESTService.getLastPercentageReading(mockActuatorId);
        // Assert
        assertTrue(result.isEmpty(), "The getLastPercentageReading method should return an empty list when the Reading does not exist.");
    }
}