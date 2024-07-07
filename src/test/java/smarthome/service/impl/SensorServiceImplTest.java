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
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.service.ISensorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class provides the unit tests for the SensorServiceImpl class.
 */
class SensorServiceImplTest {

    private ISensorTypeRepository mockSensorTypeRepository;

    private ISensorModelRepository mockSensorModelRepository;

    private ISensorRepository mockSensorRepository;

    private IDeviceRepository mockDeviceRepository;

    private SensorFactory mockSensorFactory;

    private ISensorService sensorService;

    private SensorTypeId mockSensorTypeId;

    private SensorModelName mockSensorModelName;

    private DeviceId mockDeviceId;

    private SensorType mockSensorType;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mockSensorTypeRepository = mock(ISensorTypeRepository.class);
        mockSensorModelRepository = mock(ISensorModelRepository.class);
        mockSensorRepository = mock(ISensorRepository.class);
        mockDeviceRepository = mock(IDeviceRepository.class);
        mockSensorFactory = mock(SensorFactory.class);
        sensorService = new SensorServiceImpl(mockSensorRepository, mockSensorFactory, mockDeviceRepository);

        Sensor mockSensor = mock(Sensor.class);
        mockSensorType = mock(SensorType.class);
        mockSensorTypeId = mock(SensorTypeId.class);
        mockSensorModelName = mock(SensorModelName.class);
        mockDeviceId = mock(DeviceId.class);

        when(mockSensorFactory.createSensor(mockSensorModelName, mockDeviceId)).thenReturn(mockSensor);

        when(mockSensorTypeRepository.findAll()).thenReturn(Collections.singletonList(mockSensorType));

        when(mockSensorModelRepository.findSensorModelsBySensorTypeId(mockSensorTypeId)).thenReturn(null);

        when(mockSensorRepository.save(any(Sensor.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    /**
     * Tests that the SensorServiceImpl can be constructed.
     */
    @Test
    void testSensorRESTServiceImplCanBeConstructed() {
        SensorServiceImpl sensorRESTService = new SensorServiceImpl(mockSensorRepository, mockSensorFactory,
                mockDeviceRepository);
        assertNotNull(sensorRESTService, "SensorServiceImpl should be able to be constructed");
    }

    /**
     * Tests the addSensor method when the device is not in the repository.
     */
    @Test
    void testAddSensorDeviceNotInRepositoryReturnsNull() {
        //Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceId)).thenReturn(false);

        //Act
        Sensor result = sensorService.addSensor(mockSensorModelName, mockDeviceId);

        //Assert
        assertNull(result, "The addSensor method should return null when the device is not in the repository");
    }

    /**
     * Tests the addSensor method when the sensor is successfully created.
     */
    @Test
    void testAddSensorSuccessfulCreationReturnsSensor() {
        //Arrange
        Sensor mockSensor = mock(Sensor.class);
        when(mockDeviceRepository.containsIdentity(mockDeviceId)).thenReturn(true);
        when(mockSensorFactory.createSensor(mockSensorModelName, mockDeviceId)).thenReturn(mockSensor);
        when(mockSensorRepository.save(mockSensor)).thenReturn(mockSensor);

        //Act
        Sensor result = sensorService.addSensor(mockSensorModelName, mockDeviceId);

        //Assert
        assertNotNull(result, "The addSensor method should not return null when the sensor is successfully created");
        assertEquals(mockSensor, result, "The addSensor method should return the created sensor when the sensor is " +
                "successfully created");
    }

    /**
     * Tests the addSensor method when an exception is thrown.
     */
    @Test
    void testAddSensorThrowsExceptionReturnsNull() {
        // Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceId)).thenReturn(true);
        when(mockSensorFactory.createSensor(mockSensorModelName, mockDeviceId)).thenThrow(new RuntimeException());

        // Act
        Sensor result = sensorService.addSensor(mockSensorModelName, mockDeviceId);

        // Assert
        assertNull(result, "The addSensor method should return null when an exception is thrown");
    }

    /**
     * Tests that the getByIdentity method returns a non-empty Optional when the sensor exists in the repository.
     */
    @Test
    void testGetByIdentityReturnsNonEmptyOptionalWhenSensorExists() {
        // Arrange
        SensorId mockSensorId = mock(SensorId.class);
        Sensor mockSensor = mock(Sensor.class);
        when(mockSensorRepository.findByIdentity(mockSensorId)).thenReturn(Optional.of(mockSensor));

        // Act
        Optional<Sensor> result = sensorService.getByIdentity(mockSensorId);

        // Assert
        assertTrue(result.isPresent(), "The returned Optional should be non-empty.");
    }

    /**
     * Tests that the getByIdentity method returns the correct sensor when the sensor exists in the repository.
     */
    @Test
    void testGetByIdentityReturnsCorrectSensor() {
        // Arrange
        SensorId mockSensorId = mock(SensorId.class);
        Sensor mockSensor = mock(Sensor.class);
        when(mockSensorRepository.findByIdentity(mockSensorId)).thenReturn(Optional.of(mockSensor));

        // Act
        Optional<Sensor> result = sensorService.getByIdentity(mockSensorId);

        // Assert
        assertEquals(mockSensor, result.get(), "The returned sensor should be the same as the mock sensor.");
    }

    /**
     * Tests that the getByIdentity method returns an empty Optional when the sensor does not exist in the repository.
     */
    @Test
    void testGetByIdentityReturnsEmptyOptionalWhenSensorDoesNotExist() {
        // Arrange
        SensorId mockSensorId = mock(SensorId.class);
        when(mockSensorRepository.findByIdentity(mockSensorId)).thenReturn(Optional.empty());

        // Act
        Optional<Sensor> result = sensorService.getByIdentity(mockSensorId);

        // Assert
        assertFalse(result.isPresent(), "The returned Optional should be empty.");
    }

    /**
     * This test checks that the getSensorIdsByDeviceIdentity method returns null when the Device does not exist.
     */
    @Test
    void testGetSensorIdsByDeviceIdentityWhenDeviceIdDoesNotExists() {
        //Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceId)).thenReturn(false);
        //Act
        Iterable<SensorId> result = sensorService.getSensorIdsByDeviceIdentity(mockDeviceId);
        //Assert
        assertNull(result, "The getSensorIdsByDeviceIdentity method should return null when the Device does not exist" +
                ".");
    }

    /**
     * This test checks that the getSensorIdsByDeviceIdentity method returns
     * an empty list when the Device does not have any Sensor.
     */
    @Test
    void testGetSensorIdsByDeviceIdentityWhenDeviceIdDoesNotHaveAnySesnor() {
        // Arrange
        when(mockDeviceRepository.containsIdentity(mockDeviceId)).thenReturn(true);
        when(mockSensorRepository.findSensorIdsByDeviceId(mockDeviceId)).thenReturn(List.of());
        // Act
        Iterable<SensorId> result = sensorService.getSensorIdsByDeviceIdentity(mockDeviceId);
        // Assert
        assertFalse(result.iterator().hasNext(), "Method should return an empty list when the Device does not have " +
                "any Sensor.");
    }

    /**
     * This test checks that the getSensorIdsByDeviceIdentity method returns a list
     * with one SensorId when the Device has one Sensor.
     */
    @Test
    void testGetSensorIdsByDeviceIdentityWhenDeviceIdHasOneSensor() {
        // Arrange
        SensorId mockSensorId = mock(SensorId.class);
        when(mockDeviceRepository.containsIdentity(mockDeviceId)).thenReturn(true);
        when(mockSensorRepository.findSensorIdsByDeviceId(mockDeviceId)).thenReturn(List.of(mockSensorId));
        // Act
        Iterable<SensorId> result = sensorService.getSensorIdsByDeviceIdentity(mockDeviceId);
        List<SensorId> SensorIds = (List<SensorId>) result;
        // Assert
        assertTrue(SensorIds.size() == 1 && SensorIds.contains(mockSensorId), "Method should return a list with one " +
                "SensorId when the Device has one Sensor.");
    }

    /**
     * This test checks that the getSensorIdsByDeviceIdentity method returns a list
     * with two SensorIds when the Device has two Sensors.
     */
    @Test
    void testGetSensorIdsByDeviceIdentityWhenDeviceIdHasManySensors() {
        // Arrange
        SensorId mockSensorId1 = mock(SensorId.class);
        SensorId mockSensorId2 = mock(SensorId.class);

        when(mockDeviceRepository.containsIdentity(mockDeviceId)).thenReturn(true);
        when(mockSensorRepository.findSensorIdsByDeviceId(mockDeviceId)).thenReturn(List.of(mockSensorId1,
                mockSensorId2));
        // Act
        Iterable<SensorId> result = sensorService.getSensorIdsByDeviceIdentity(mockDeviceId);
        List<SensorId> SensorIds = (List<SensorId>) result;
        // Assert
        assertTrue(SensorIds.size() == 2 && SensorIds.contains(mockSensorId1) && SensorIds.contains(mockSensorId2),
                "Method should return a list with one SensorId when the Device has one Sensor.");
    }
}