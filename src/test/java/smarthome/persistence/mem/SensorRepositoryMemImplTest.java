package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A test class for the SensorRepositoryMemImpl implementation.
 * This class contains unit tests for the SensorRepositoryMemImpl class.
 * These tests verify the behavior of the SensorRepositoryMemImpl class in various scenarios.
 */
class SensorRepositoryMemImplTest {

    SensorRepositoryMemImpl sensorRepositoryMemImpl;
    Sensor sensorMock;
    SensorId sensorIdMock;
    String validSensorId;

    DeviceId deviceIdMock;

    /**
     * Sets up the test environment before each test method execution.
     * It initializes the SensorRepositoryMemImpl instance, mock objects, and configurations.
     * - Initializes a new instance of SensorRepositoryMemImpl.
     * - Mocks a SensorId object to represent a valid sensor identity.
     * - Configures the mocked SensorId to return a predefined valid sensor identity when queried for ID.
     * - Configures the mocked SensorId to return true when compared to itself.
     * - Mocks a Sensor object to represent a sensor entity.
     * - Configures the mocked Sensor to return the mocked SensorId when queried for identity.
     */
    @BeforeEach
    void setUp() {
        sensorRepositoryMemImpl = new SensorRepositoryMemImpl();
        sensorIdMock = mock(SensorId.class);
        validSensorId = "validSensorIdentity";
        when(sensorIdMock.getSensorId()).thenReturn(validSensorId);
        sensorMock = mock(Sensor.class);
        when(sensorMock.getIdentity()).thenReturn(sensorIdMock);

        deviceIdMock = mock(DeviceId.class);
        when(deviceIdMock.getIdentity()).thenReturn("deviceId1");
        when(sensorMock.getDeviceId()).thenReturn(deviceIdMock);
    }

    /**
     * Unit test: Saving a valid Sensor to an empty repository should return the saved Sensor.
     * This test verifies that the save method of the SensorRepositoryMemImpl returns the same Sensor instance.
     */
    @Test
    void testSaveValidSensorToAnEmptyRepositoryShouldReturnSavedSensor() {
        // Act
        Sensor result = sensorRepositoryMemImpl.save(sensorMock);
        // Assert
        assertEquals(sensorMock, result, "Should return the saved Sensor");
    }

    /**
     * Unit test: Saving a valid Sensor to an empty repository should return the saved Sensor's identity.
     * This test verifies that the save method of the SensorRepositoryMemImpl returns the identity of the saved Sensor.
     */
    @Test
    void testSaveValidSensorToEmptyRepositoryShouldReturnSavedSensorIdentity() {
        // Act
        Sensor result = sensorRepositoryMemImpl.save(sensorMock);
        // Assert
        assertEquals(sensorIdMock, result.getIdentity(), "Should return the saved Sensor's identity");
    }

    /**
     * Unit test for saving a valid sensor to an empty repository and checking if the repository contains the
     * saved identity.
     * This test verifies that when a valid actuator model is saved to an empty repository, the repository contains
     * the corresponding identity.
     */
    @Test
    void testSaveValidSensorToAnEmptyRepositoryShouldContainIdentity() {
        // Act
        sensorRepositoryMemImpl.save(sensorMock);
        // Assert
        boolean result = sensorRepositoryMemImpl.containsIdentity(sensorIdMock);
        assertTrue(result, "Should contain the saved Sensor's identity");
    }

    /**
     * Unit test: Saving a valid Sensor to a non-empty repository should return the saved Sensor.
     * This test verifies that the save method of the SensorRepositoryMemImpl returns the same Sensor instance.
     */
    @Test
    void testSaveValidSensorToNonEmptyRepositoryShouldReturnSavedSensor() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);
        String anotherValidSensorId = "anotherValidSensorIdentity";
        Sensor anotherSensorMock = mock(Sensor.class);
        SensorId anotherSensorIdMock = mock(SensorId.class);
        when(anotherSensorIdMock.getSensorId()).thenReturn(anotherValidSensorId);
        // Act
        Sensor result = sensorRepositoryMemImpl.save(anotherSensorMock);
        // Assert
        assertEquals(anotherSensorMock, result, "Should return the saved Sensor");
    }

    /**
     * Unit test: Saving a valid Sensor to a non-empty repository should return the saved Sensor's identity.
     * This test verifies that the save method of the SensorRepositoryMemImpl returns the identity of the saved Sensor.
     */
    @Test
    void testSaveValidSensorToNonEmptyRepositoryShouldReturnSavedSensorIdentity() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);

        String anotherValidSensorId = "anotherValidSensorIdentity";
        SensorId anotherSensorIdMock = mock(SensorId.class);
        when(anotherSensorIdMock.getSensorId()).thenReturn(anotherValidSensorId);
        Sensor anotherSensorMock = mock(Sensor.class);
        when(anotherSensorMock.getIdentity()).thenReturn(anotherSensorIdMock);
        // Act
        Sensor result = sensorRepositoryMemImpl.save(anotherSensorMock);
        // Assert
        assertEquals(anotherSensorIdMock, result.getIdentity(), "Should return the saved Sensor's identity");
    }

    /**
     * Unit test for saving a valid sensor to a non-empty repository and checking if the repository contains the
     * saved identity.
     * This test verifies that when a valid sensor is saved to a non-empty repository, the repository contains the
     * corresponding identity.
     */
    @Test
    void testSaveValidSensorToNonEmptyRepositoryShouldContainIdentity() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);

        SensorId anotherSensorIDMock = mock(SensorId.class);
        Sensor anotherSensorMock = mock(Sensor.class);
        when(anotherSensorMock.getIdentity()).thenReturn(anotherSensorIDMock);
        // Act
        sensorRepositoryMemImpl.save(anotherSensorMock);
        // Assert
        assertTrue(sensorRepositoryMemImpl.containsIdentity(anotherSensorIDMock),
                "Should contain the saved Sensor's identity");
    }

    /**
     * Unit test: Finding all sensors on an empty repository should return an empty iterable.
     * This test verifies that the findAll method returns an empty iterable when the repository is empty.
     */
    @Test
    void testFindAllOnEmptyRepositoryShouldReturnAnEmptyIterable() {
        // Act
        Iterable<Sensor> sensors = sensorRepositoryMemImpl.findAll();
        // Assert
        assertFalse(sensors.iterator().hasNext(), "Should return an empty iterable");
    }

    /**
     * Unit test: Finding all sensors on an empty repository should return an empty list.
     * This test verifies that the findAll method returns an empty list when the repository is empty.
     */
    @Test
    void testFindAllOnEmptyRepositoryShouldReturnAnEmptyList() {
        //Act
        List<Sensor> result = new ArrayList<>();
        sensorRepositoryMemImpl.findAll().forEach(result::add);
        //Assert
        assertTrue(result.isEmpty(), "Should return an empty list");
    }

    /**
     * Unit test: Finding all sensors with entities in the repository should return an iterable with entities.
     * This test verifies that the findAll method returns an iterable that is not empty.
     */
    @Test
    void testFindAllOnNonEmptyRepositoryWithSingleModelShouldReturnNonEmptyIterable() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);
        // Act
        Iterable<Sensor> sensors = sensorRepositoryMemImpl.findAll();
        // Assert
        assertTrue(sensors.iterator().hasNext(), "Should return a non-empty iterable");
    }

    /**
     * Unit test: Finding all sensors on a non-empty repository should return an iterable with all sensors.
     * This test verifies that the findAll method returns an iterable containing all sensors stored in the repository.
     */
    @Test
    void testFindAllOnNonEmptyRepositoryWithMultipleModelsShouldReturnIterableWithAllSensors() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);

        SensorId anotherSensorIdMock = mock(SensorId.class);
        Sensor anotherSensorMock = mock(Sensor.class);
        when(anotherSensorMock.getIdentity()).thenReturn(anotherSensorIdMock);
        sensorRepositoryMemImpl.save(anotherSensorMock);

        List<Sensor> result = new ArrayList<>();
        int size = 2;
        // Act
        Iterable<Sensor> sensors = sensorRepositoryMemImpl.findAll();
        // Assert
        sensors.forEach(result::add);
        assertEquals(size, result.size(), "Should return a list with two sensors");
    }

    /**
     * Unit test: Finding all sensors on a non-empty repository should return a list with all sensors.
     * This test verifies that the findAll method returns a list with the same size of the sensors stored in
     * the repository.
     */
    @Test
    void testFindAllOnNonEmptyRepositoryShouldReturnAListWithAllSensors() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);

        SensorId anotherSensorIdMock = mock(SensorId.class);
        Sensor anotherSensorMock = mock(Sensor.class);
        when(anotherSensorMock.getIdentity()).thenReturn(anotherSensorIdMock);
        sensorRepositoryMemImpl.save(anotherSensorMock);

        int size = 2;

        // Act
        List<Sensor> result = new ArrayList<>();
        sensorRepositoryMemImpl.findAll().forEach(result::add);
        // Assert
        assertEquals(size, result.size(), "Should return a list with two sensors");
    }

    /**
     * Unit test: Finding all sensors on a non-empty repository should return a list with all sensors.
     * This test verifies that the findAll method returns a list containing all sensors stored in the repository.
     */
    @Test
    void testFindAllOnNonEmptyRepositoryShouldContainSensors() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);

        SensorId anotherSensorIdMock = mock(SensorId.class);
        Sensor anotherSensorMock = mock(Sensor.class);
        when(anotherSensorMock.getIdentity()).thenReturn(anotherSensorIdMock);
        sensorRepositoryMemImpl.save(anotherSensorMock);

        // Act
        List<Sensor> result = new ArrayList<>();
        sensorRepositoryMemImpl.findAll().forEach(result::add);
        // Assert
        assertTrue(result.contains(sensorMock), "Should contain the first sensor");
        assertTrue(result.contains(anotherSensorMock), "Should contain the second sensor");
    }

    /**
     * Unit test: Getting a sensor by identity on an empty repository should return an empty optional.
     * This test verifies that the getByIdentity method returns an empty optional when the repository is empty.
     */
    @Test
    void testFindByIdentityOnEmptyRepositoryShouldReturnAnEmptyOptional() {
        // Act
        Optional<Sensor> result = sensorRepositoryMemImpl.findByIdentity(sensorIdMock);
        // Assert
        assertTrue(result.isEmpty(), "Should return an empty optional");
    }

    /**
     * Unit test: Getting a sensor by identity on an empty repository should not present the optional.
     * This test verifies that the getByIdentity method does not present the optional when the repository is empty.
     */
    @Test
    void findByIdentityOnEmptyRepositoryNonExistingIdentityShouldNotBePresent() {
        // Act
        Optional<Sensor> retrievedSensor = sensorRepositoryMemImpl.findByIdentity(sensorIdMock);
        // Assert
        assertFalse(retrievedSensor.isPresent(), "Should not present the sensor");
    }

    /**
     * Unit test: Getting a sensor by identity on a non-empty repository without the identity should return an
     * empty optional.
     * This test verifies that the getByIdentity method returns an empty optional when the repository does not contain
     * the requested identity.
     */
    @Test
    void testFindByIdentityOnNonEmptyRepositoryButDoesNotContainThatIdentityShouldReturnAnEmptyOptional() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);
        SensorId anotherSensorIdMock = mock(SensorId.class);
        // Act
        Optional<Sensor> result = sensorRepositoryMemImpl.findByIdentity(anotherSensorIdMock);
        // Assert
        assertTrue(result.isEmpty(), "Should return an empty optional");
    }

    /**
     * Unit test: Getting a sensor by identity on a non-empty repository without the identity should not present
     * the optional.
     * This test verifies that the getByIdentity method does not present the optional when the repository does not
     * contain the requested identity.
     */
    @Test
    void testFindByIdentityOnNonEmptyRepositoryNonExistingIdentityShouldNotBePresent() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);
        SensorId anotherSensorIdMock = mock(SensorId.class);
        // Act
        Optional<Sensor> result = sensorRepositoryMemImpl.findByIdentity(anotherSensorIdMock);
        // Assert
        assertFalse(result.isPresent(), "Should not present the sensor");
    }

    /**
     * Unit test: Getting a sensor by identity with an existing identity should return the corresponding sensor.
     * This test verifies that the getByIdentity method returns the correct sensor when the repository contains the
     * requested identity.
     */
    @Test
    void testFindByIdentityWithExistingIdentityShouldReturnCorrespondingSensor() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);
        // Act
        Sensor result = sensorRepositoryMemImpl.findByIdentity(sensorIdMock).orElseThrow();
        // Assert
        assertEquals(sensorMock, result, "Should return the corresponding sensor");
    }

    /**
     * Unit test: Getting a sensor by identity with an existing identity should return an optional with the
     * corresponding sensor.
     * This test verifies that the getByIdentity method returns an optional containing the correct sensor when the
     * repository contains the requested identity.
     */
    @Test
    void testFindByIdentityWithExistingIdentityShouldReturnOptionalWithCorrespondingSensor() {
        // Act
        sensorRepositoryMemImpl.save(sensorMock);
        Optional<Sensor> optionalSensor = sensorRepositoryMemImpl.findByIdentity(sensorIdMock);
        // Assert
        assertTrue(optionalSensor.isPresent(), "Should return an optional with the corresponding sensor");
    }

    /**
     * Unit test: Checking if the repository contains an existing identity should return true.
     * This test verifies that the containsIdentity method returns true when the repository contains the
     * specified identity.
     */
    @Test
    void testContainsIdentityWhenExistingIdentityShouldReturnTrue() {
        // Act
        sensorRepositoryMemImpl.save(sensorMock);
        boolean result = sensorRepositoryMemImpl.containsIdentity(sensorIdMock);
        // Assert
        assertTrue(result, "Should return true as the identity exists");
    }

    /**
     * Unit test: Checking if the repository contains a non-existing identity should return false.
     * This test verifies that the containsIdentity method returns false when the repository does not contain
     * the specified identity.
     */
    @Test
    void testContainsIdentityWhenNonExistingIdentityShouldReturnFalse() {
        // Arrange
        sensorRepositoryMemImpl.save(sensorMock);
        SensorId nonExistingSensorId = mock(SensorId.class);
        when(nonExistingSensorId.getSensorId()).thenReturn("nonExistingSensorId");
        // Act
        boolean result = sensorRepositoryMemImpl.containsIdentity(nonExistingSensorId);
        // Assert
        assertFalse(result, "Should return false as the identity does not exist");
    }

    /**
     * Unit test: Checking if the repository contains an identity on an empty repository should return false.
     * This test verifies that the containsIdentity method returns false when the repository is empty.
     */
    @Test
    void testContainsIdentityOnEmptyRepositoryShouldReturnFalse() {
        // Act
        boolean result = sensorRepositoryMemImpl.containsIdentity(sensorIdMock);
        // Assert
        assertFalse(result, "Should return false as the repository is empty");
    }

    /**
     * Unit test: Getting all sensors associated with a device on a repository should return an iterable.
     * This test verifies that the getByDeviceIdentity method returns a non-empty iterable when the repository
     * contains sensors associated with the specified device.
     */
    @Test
    void testFindSensorsByDeviceId() {
        //Arrange
        sensorRepositoryMemImpl.save(sensorMock);

        List<Sensor> sensors = new ArrayList<>();
        int expected = 1;

        //Act
        Iterable<Sensor> result = sensorRepositoryMemImpl.findSensorsByDeviceId(deviceIdMock);
        //Assert
        result.forEach(sensors::add);

        assertEquals(expected, sensors.size(), "Should return an iterable with the sensor associated with the device");
    }

    /**
     * Unit test: Getting all sensors associated with a device that does not exist in the repository should return
     * an empty iterable.
     * This test verifies that the getByDeviceIdentity method returns an empty iterable when the repository does
     * not contain sensors associated with the specified device.
     */
    @Test
    void testFindSensorsByDeviceIdDoesNotExist() {
        //Arrange
        sensorRepositoryMemImpl.save(sensorMock);

        DeviceId deviceIdMock2 = mock(DeviceId.class);
        when(deviceIdMock2.getIdentity()).thenReturn("deviceId1");

        List<Sensor> sensors = new ArrayList<>();
        int expected = 0;

        //Act
        Iterable<Sensor> result = sensorRepositoryMemImpl.findSensorsByDeviceId(deviceIdMock2);
        //Assert
        result.forEach(sensors::add);

        assertEquals(expected, sensors.size(), "Should return an empty iterable as the device id does not exist");
    }

    /**
     * Unit test: Getting all sensors associated with a device and sensor model in the repository should return
     * an iterable.
     * This test verifies that the getByDeviceIdentityAndSensorModel method returns a non-empty iterable when
     * the repository contains sensors associated with the specified device and sensor model.
     */
    @Test
    void testFindSensorsByDeviceIdAndSensorModelName() {
        //Arrange
        DeviceId deviceIdMock2 = mock(DeviceId.class);
        when(deviceIdMock2.getIdentity()).thenReturn("deviceId2");

        SensorModelName sensorModelNameMock2 = mock(SensorModelName.class);
        when(sensorModelNameMock2.getSensorModelName()).thenReturn("SensorOfHumidity");

        SensorModelName sensorModelNameMock3 = mock(SensorModelName.class);
        when(sensorModelNameMock3.getSensorModelName()).thenReturn("SensorOfTemperature");

        SensorId sensorIdMock2 = mock(SensorId.class);
        when(sensorIdMock2.getSensorId()).thenReturn("sensorId2");

        Sensor sensorMock2 = mock(Sensor.class);
        when(sensorMock2.getIdentity()).thenReturn(sensorIdMock2);
        when(sensorMock2.getDeviceId()).thenReturn(deviceIdMock2);
        when(sensorMock2.getSensorModelName()).thenReturn(sensorModelNameMock2);

        SensorId sensorIdMock3 = mock(SensorId.class);
        when(sensorIdMock3.getSensorId()).thenReturn("sensorId3");

        Sensor sensorMock3 = mock(Sensor.class);
        when(sensorMock3.getIdentity()).thenReturn(sensorIdMock3);
        when(sensorMock3.getDeviceId()).thenReturn(deviceIdMock2);
        when(sensorMock3.getSensorModelName()).thenReturn(sensorModelNameMock3);

        sensorRepositoryMemImpl.save(sensorMock2);
        sensorRepositoryMemImpl.save(sensorMock3);

        List<Sensor> sensors = new ArrayList<>();
        int expected = 1;

        //Act
        Iterable<Sensor> result = sensorRepositoryMemImpl.
                findSensorsByDeviceIdAndSensorModelName(deviceIdMock2, sensorModelNameMock3);
        //Assert
        result.forEach(sensors::add);
        assertEquals(expected, sensors.size(),
                "Should return an iterable with the sensor associated with the device and sensor model");
    }

    /**
     * Unit test: Getting all sensors associated with a device and sensor model that do not exist in the repository
     * should return an empty iterable.
     * This test verifies that the getByDeviceIdentityAndSensorModel method returns an empty iterable when the
     * repository does not contain sensors associated with the specified sensor model.
     */
    @Test
    void testFindSensorsByDeviceIdAndSensorModelNameDoesNotExist() {
        //Arrange
        DeviceId deviceIdMock2 = mock(DeviceId.class);
        when(deviceIdMock2.getIdentity()).thenReturn("deviceId2");

        SensorModelName sensorModelNameMock2 = mock(SensorModelName.class);
        when(sensorModelNameMock2.getSensorModelName()).thenReturn("SensorOfHumidity");

        SensorModelName sensorModelNameMock3 = mock(SensorModelName.class);
        when(sensorModelNameMock3.getSensorModelName()).thenReturn("SensorOfTemperature");

        SensorId sensorIdMock2 = mock(SensorId.class);
        when(sensorIdMock2.getSensorId()).thenReturn("sensorId2");

        Sensor sensorMock2 = mock(Sensor.class);
        when(sensorMock2.getIdentity()).thenReturn(sensorIdMock2);
        when(sensorMock2.getDeviceId()).thenReturn(deviceIdMock2);
        when(sensorMock2.getSensorModelName()).thenReturn(sensorModelNameMock2);

        SensorId sensorIdMock3 = mock(SensorId.class);
        when(sensorIdMock3.getSensorId()).thenReturn("sensorId3");

        Sensor sensorMock3 = mock(Sensor.class);
        when(sensorMock3.getIdentity()).thenReturn(sensorIdMock3);
        when(sensorMock3.getDeviceId()).thenReturn(deviceIdMock2);
        when(sensorMock3.getSensorModelName()).thenReturn(sensorModelNameMock3);

        sensorRepositoryMemImpl.save(sensorMock2);

        List<Sensor> sensors = new ArrayList<>();
        int expected = 0;

        //Act
        Iterable<Sensor> result = sensorRepositoryMemImpl.
                findSensorsByDeviceIdAndSensorModelName(deviceIdMock2, sensorModelNameMock3);
        //Assert
        result.forEach(sensors::add);
        assertEquals(expected, sensors.size(), "Should return an empty iterable as the sensor model does not exist");
    }

    /**
     * Unit test: Getting all sensors associated with a device and sensor model that do not exist in the repository
     * should return an empty iterable.
     * This test verifies that the getByDeviceIdentityAndSensorModel method returns an empty iterable when the
     * repository does not contain sensors associated with the specified device id.
     */
    @Test
    void testFindSensorsByDeviceIdentityAndSensorModelDeviceIdDoesNotExist() {
        //Arrange
        DeviceId deviceIdMock2 = mock(DeviceId.class);
        when(deviceIdMock2.getIdentity()).thenReturn("deviceId2");

        SensorModelName sensorModelNameMock2 = mock(SensorModelName.class);
        when(sensorModelNameMock2.getSensorModelName()).thenReturn("SensorOfHumidity");

        SensorModelName sensorModelNameMock3 = mock(SensorModelName.class);
        when(sensorModelNameMock3.getSensorModelName()).thenReturn("SensorOfTemperature");

        SensorId sensorIdMock2 = mock(SensorId.class);
        when(sensorIdMock2.getSensorId()).thenReturn("sensorId2");

        Sensor sensorMock2 = mock(Sensor.class);
        when(sensorMock2.getIdentity()).thenReturn(sensorIdMock2);
        when(sensorMock2.getDeviceId()).thenReturn(deviceIdMock2);
        when(sensorMock2.getSensorModelName()).thenReturn(sensorModelNameMock2);

        SensorId sensorIdMock3 = mock(SensorId.class);
        when(sensorIdMock3.getSensorId()).thenReturn("sensorId3");

        Sensor sensorMock3 = mock(Sensor.class);
        when(sensorMock3.getIdentity()).thenReturn(sensorIdMock3);
        when(sensorMock3.getDeviceId()).thenReturn(deviceIdMock2);
        when(sensorMock3.getSensorModelName()).thenReturn(sensorModelNameMock3);

        sensorRepositoryMemImpl.save(sensorMock2);
        sensorRepositoryMemImpl.save(sensorMock3);

        DeviceId nonExistantDevieId = mock(DeviceId.class);
        when(nonExistantDevieId.getIdentity()).thenReturn("nonExistantDeviceId");

        List<Sensor> sensors = new ArrayList<>();
        int expected = 0;

        //Act
        Iterable<Sensor> result = sensorRepositoryMemImpl.
                findSensorsByDeviceIdAndSensorModelName(nonExistantDevieId, sensorModelNameMock3);
        //Assert
        result.forEach(sensors::add);
        assertEquals(expected, sensors.size(), "Should return an empty iterable as the device id does not exist");
    }

    /**
     * Test that the findSensorIdsByDeviceIdAndSensorModelName method returns an empty list when the repository is
     * empty.
     */
    @Test
    void testFindSensorIdsByDeviceIdAndSensorModelNameEmptyRepository() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("deviceId");
        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModelName.getSensorModelName()).thenReturn("sensorModelName");
        List<SensorId> sensorIds = new ArrayList<>();

        // Act
        Iterable<SensorId> result = sensorRepositoryMemImpl.findSensorIdsByDeviceIdAndSensorModelName(deviceId,
                sensorModelName);
        result.forEach(sensorIds::add);

        // Assert
        boolean isEmpty = sensorIds.isEmpty();
        assertTrue(isEmpty, "Should return an empty list");
    }

    /**
     * Test that the findSensorIdsByDeviceIdAndSensorModelName method returns a list with one sensor id when the
     * repository contains a sensor with the specified device id and sensor model name.
     */
    @Test
    void testFindSensorIdsByDeviceIdAndSensorModelNameNonEmptyRepository() {
        // Arrange
        int expectedSize = 1;
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("deviceId");
        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModelName.getSensorModelName()).thenReturn("sensorModelName");
        Sensor sensor = mock(Sensor.class);
        when(sensor.getDeviceId()).thenReturn(deviceId);
        when(sensor.getSensorModelName()).thenReturn(sensorModelName);
        sensorRepositoryMemImpl.save(sensor);
        List<SensorId> sensorIds = new ArrayList<>();

        // Act
        Iterable<SensorId> result = sensorRepositoryMemImpl.findSensorIdsByDeviceIdAndSensorModelName(deviceId,
                sensorModelName);
        result.forEach(sensorIds::add);

        // Assert
        int actualSize = sensorIds.size();
        assertEquals(expectedSize, actualSize, "Should return a list with one sensor id");
    }

    /**
     * Test that the findSensorIdsByDeviceIdAndSensorModelName method returns an empty list when the repository does not
     * contain a sensor with the specified device id and sensor model name.
     */
    @Test
    void testFindSensorIdsByDeviceIdAndSensorModelNameWhenDeviceIdDoesNotExist() {
        // Arrange
        int expectedSize = 0;
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("deviceId");
        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModelName.getSensorModelName()).thenReturn("sensorModelName");
        Sensor sensor = mock(Sensor.class);
        when(sensor.getDeviceId()).thenReturn(mock(DeviceId.class));
        when(sensor.getSensorModelName()).thenReturn(sensorModelName);
        sensorRepositoryMemImpl.save(sensor);
        List<SensorId> sensorIds = new ArrayList<>();

        // Act
        Iterable<SensorId> result = sensorRepositoryMemImpl.findSensorIdsByDeviceIdAndSensorModelName(deviceId,
                sensorModelName);
        result.forEach(sensorIds::add);

        // Assert
        int actualSize = sensorIds.size();
        assertEquals(expectedSize, actualSize, "Should return an empty list");
    }

    /**
     * Test that the findSensorIdsByDeviceIdAndSensorModelName method returns an empty list when the repository does not
     * contain a sensor with the specified device id and sensor model name.
     */
    @Test
    void testFindSensorIdsByDeviceIdAndSensorModelNameWhenSensorModelNameDoesNotExist() {
        // Arrange
        int expectedSize = 0;
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("deviceId");
        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModelName.getSensorModelName()).thenReturn("sensorModelName");
        Sensor sensor = mock(Sensor.class);
        when(sensor.getDeviceId()).thenReturn(deviceId);
        when(sensor.getSensorModelName()).thenReturn(mock(SensorModelName.class));
        sensorRepositoryMemImpl.save(sensor);
        List<SensorId> sensorIds = new ArrayList<>();

        // Act
        Iterable<SensorId> result = sensorRepositoryMemImpl.findSensorIdsByDeviceIdAndSensorModelName(deviceId,
                sensorModelName);
        result.forEach(sensorIds::add);

        // Assert
        int actualSize = sensorIds.size();
        assertEquals(expectedSize, actualSize, "Should return an empty list");
    }

    /**
     * Test that the findSensorIdsByDeviceIdAndSensorModelName method throws an IllegalArgumentException when the device
     * id is null.
     */
    @Test
    void testFindSensorIdsByDeviceIdAndSensorModelNameWhenDeviceIdIsNull() {
        // Arrange
        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModelName.getSensorModelName()).thenReturn("sensorModelName");

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> sensorRepositoryMemImpl.findSensorIdsByDeviceIdAndSensorModelName(null, sensorModelName),
                "Should throw an IllegalArgumentException");
    }

    /**
     * Test that the findSensorIdsByDeviceIdAndSensorModelName method throws an IllegalArgumentException when the sensor
     * model name is null.
     */
    @Test
    void testFindSensorIdsByDeviceIdAndSensorModelNameWhenSensorModelNameIsNull() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("deviceId");

        // Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> sensorRepositoryMemImpl.findSensorIdsByDeviceIdAndSensorModelName(deviceId, null),
                "Should throw an IllegalArgumentException");
    }

    /**
     * This test verifies that the findSensorIdsByDeviceId method in the SensorRepositoryMemImpl class throws an
     * IllegalArgumentException when the device id is null. The test asserts that an IllegalArgumentException is thrown
     * when the findSensorIdsByDeviceId method is called with a null device id.
     */
    @Test
    void testFindSensorIdsByDeviceIdWithANullDeviceId() {
        //Arrange
        DeviceId deviceId = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> sensorRepositoryMemImpl.findSensorIdsByDeviceId(deviceId),
                "Should throw an IllegalArgumentException");
    }

    /**
     * This test verifies that the findSensorIdsByDeviceId method in the SensorRepositoryMemImpl class returns an empty
     * iterable when the device id does not exist in the repository. The test mocks the behavior of the
     * SensorRepository to return
     * an empty iterable when a non-existing device id is provided. It then asserts that the returned iterable is empty.
     */
    @Test
    void testFindSensorIdsByDeviceIdWhenDeviceIdDoesNotExist() {
        //Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("Invalid");
        sensorRepositoryMemImpl.save(sensorMock);

        //Act
        Iterable<SensorId> sensors = sensorRepositoryMemImpl.findSensorIdsByDeviceId(deviceId);

        //Assert
        assertFalse(sensors.iterator().hasNext(), "Should return an empty iterable");
    }

    /**
     * This test verifies that the findSensorIdsByDeviceId method in the SensorRepositoryMemImpl class returns an
     * iterable
     * with the sensor ids associated with the device id when the device id exists in the repository. The test mocks
     * the behavior of the SensorRepository to return
     * an iterable with the sensor ids when an existing device id is provided. It then asserts that the returned
     * iterable is not empty.
     */
    @Test
    void testFindSensorIdsByDeviceIdWhenDeviceIdExists() {
        //Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("deviceId1");
        sensorRepositoryMemImpl.save(sensorMock);
        when(sensorMock.getDeviceId()).thenReturn(deviceId);

        //Act
        Iterable<SensorId> sensors = sensorRepositoryMemImpl.findSensorIdsByDeviceId(deviceId);
        List<SensorId> sensorIds = new ArrayList<>();
        sensors.forEach(sensorIds::add);

        //Assert
        assertTrue(sensors.iterator().hasNext(),
                "Should return an iterable with the sensor ids associated with the " + "device id");
    }
}