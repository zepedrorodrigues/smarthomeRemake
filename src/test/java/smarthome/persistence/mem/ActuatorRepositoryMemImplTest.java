package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.device.vo.DeviceId;

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
 * Unit tests for the class for ActuatorRepositoryMemImpl.
 */
class ActuatorRepositoryMemImplTest {

    ActuatorRepositoryMemImpl actuatorRepositoryMemImpl;

    Actuator actuator;

    ActuatorId actuatorId;

    String validIdentity;

    @BeforeEach
    void setUp() {
        validIdentity = "actuator1";
        actuatorRepositoryMemImpl = new ActuatorRepositoryMemImpl();
        actuatorId = mock(ActuatorId.class);
        when(actuatorId.getActuatorId()).thenReturn(validIdentity);
        actuator = mock(Actuator.class);
        when(actuator.getIdentity()).thenReturn(actuatorId);
        when(actuator.getDeviceId()).thenReturn(mock(DeviceId.class));
    }

    /**
     * Test that the save method throws an exception when the actuator is null.
     */
    @Test
    void testSaveNullActuatorShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorRepositoryMemImpl.save(null));
    }

    /**
     * Test that the save method saves an actuator to an empty repository.
     */
    @Test
    void testSaveToEmptyRepositoryShouldReturnActuator() {
        //Act
        Actuator result = actuatorRepositoryMemImpl.save(actuator);
        //Assert
        assertEquals(actuator, result);
        assertTrue(actuatorRepositoryMemImpl.containsIdentity(actuatorId));
    }

    /**
     * Test that the save method saves an actuator to a non-empty repository.
     */
    @Test
    void testSaveOnARepositoryThatAlreadyContainsActuatorShouldReturnActuator() {
        //Arrange
        String validIdentity2 = "actuator2";
        ActuatorId actuatorId2 = mock(ActuatorId.class);
        when(actuatorId2.getActuatorId()).thenReturn(validIdentity2);
        Actuator actuator2 = mock(Actuator.class);
        when(actuator2.getIdentity()).thenReturn(actuatorId2);
        //Act
        Actuator actuator1 = actuatorRepositoryMemImpl.save(actuator);
        Actuator actuator3 = actuatorRepositoryMemImpl.save(actuator2);
        boolean result = actuatorRepositoryMemImpl.containsIdentity(actuatorId);
        boolean result2 = actuatorRepositoryMemImpl.containsIdentity(actuatorId2);
        //Assert
        assertEquals(actuator, actuator1);
        assertEquals(actuator2, actuator3);
        assertTrue(result);
        assertTrue(result2);
    }

    /**
     * Test that the findAll method returns an empty iterable when the repository is empty.
     */
    @Test
    void testFindAllEmptyRepositoryShouldReturnEmptyIterable() {
        //Act
        Iterable<Actuator> actuators = actuatorRepositoryMemImpl.findAll();
        List<Actuator> actuatorList = new ArrayList<>();
        actuators.forEach(actuatorList::add);
        //Assert
        assertFalse(actuators.iterator().hasNext());
        assertTrue(actuatorList.isEmpty());
    }

    /**
     * Test that the findAll method returns an iterable with all actuators in the repository.
     */
    @Test
    void testFindAllInNonEmptyRepositoryShouldReturnIterableWithAllActuators() {
        //Arrange
        String validIdentity2 = "actuator2";
        ActuatorId actuatorId2 = mock(ActuatorId.class);
        when(actuatorId2.getActuatorId()).thenReturn(validIdentity2);
        Actuator actuator2 = mock(Actuator.class);
        when(actuator2.getIdentity()).thenReturn(actuatorId2);
        //Act
        actuatorRepositoryMemImpl.save(actuator);
        actuatorRepositoryMemImpl.save(actuator2);
        Iterable<Actuator> actuators = actuatorRepositoryMemImpl.findAll();
        List<Actuator> actuatorList = new ArrayList<>();
        actuators.forEach(actuatorList::add);
        //Assert
        assertTrue(actuators.iterator().hasNext());
        assertEquals(2, actuatorList.size());
    }

    /**
     * Test that the getByIdentity method returns an empty optional when the repository is empty.
     */
    @Test
    void testFindByIdentityEmptyRepositoryShouldReturnEmptyOptional() {
        //Act
        Optional<Actuator> result = actuatorRepositoryMemImpl.findByIdentity(actuatorId);
        //Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test that the getByIdentity method throws an exception when the identity is null.
     */
    @Test
    void testFindByIdentityNullIdentityShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorRepositoryMemImpl.findByIdentity(null));
    }

    /**
     * Test that the getByIdentity method returns an optional with no actuator when the repository is non-empty but does not contain that identity.
     */
    @Test
    void testFindByIdentityNonEmptyRepositoryButDoesNotContainThatIdentityShouldReturnOptionalWithNoActuator() {
        //Arrange
        ActuatorId actuatorId2 = mock(ActuatorId.class);
        when(actuatorId2.getActuatorId()).thenReturn("actuator2");
        //Act
        actuatorRepositoryMemImpl.save(actuator);
        Optional<Actuator> result = actuatorRepositoryMemImpl.findByIdentity(actuatorId2);
        //Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Test that the getByIdentity method returns an optional with the actuator with the given identity when the repository is non-empty and contains that actuator.
     */
    @Test
    void testFindByIdentityNonEmptyRepositoryAndContainsThatIdentityShouldReturnOptionalWithActuator() {
        //Act
        actuatorRepositoryMemImpl.save(actuator);
        Optional<Actuator> result = actuatorRepositoryMemImpl.findByIdentity(actuatorId);
        //Assert
        assertTrue(result.isPresent());
        assertEquals(actuator, result.get());
    }

    /**
     * Test that the getByIdentity method returns an optional with the actuator with the given identity when the repository is non-empty and contains multiple actuators.
     */
    @Test
    void testFindByIdentityNonEmptyRepositoryWith2ActuatorsAndContainsThatIdentityShouldReturnOptionalWithActuator() {
        //Arrange
        String validIdentity2 = "actuator2";
        ActuatorId actuatorId2 = mock(ActuatorId.class);
        when(actuatorId2.getActuatorId()).thenReturn(validIdentity2);
        Actuator actuator2 = mock(Actuator.class);
        when(actuator2.getIdentity()).thenReturn(actuatorId2);
        //Act
        actuatorRepositoryMemImpl.save(actuator);
        actuatorRepositoryMemImpl.save(actuator2);
        Optional<Actuator> result = actuatorRepositoryMemImpl.findByIdentity(actuatorId);
        Optional<Actuator> result2 = actuatorRepositoryMemImpl.findByIdentity(actuatorId2);
        //Assert
        assertTrue(result.isPresent());
        assertEquals(actuator, result.get());
        assertTrue(result2.isPresent());
        assertEquals(actuator2, result2.get());
    }

    /**
     * Test that the containsIdentity method returns false when the repository is empty.
     */
    @Test
    void testcontainsIdentityEmptyRepositoryShouldReturnFalse() {
        //Arrange
        ActuatorId actuatorId2 = mock(ActuatorId.class);
        when(actuatorId2.getActuatorId()).thenReturn("actuator2");
        //Act
        boolean result = actuatorRepositoryMemImpl.containsIdentity(actuatorId2);
        //Assert
        assertFalse(result);
    }

    /**
     * Test that the containsIdentity method throws an exception when the identity is null.
     */
    @Test
    void testcontainsIdentityNullIdentityShouldThrowException() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorRepositoryMemImpl.containsIdentity(null));
    }

    /**
     * Test that the containsIdentity method returns false when the repository is non-empty but does not contain the identity.
     */
    @Test
    void testcontainsIdentityNonEmptyRepositoryButDoesNotContainIdentityShouldReturnFalse() {
        //Arrange
        ActuatorId actuatorId2 = mock(ActuatorId.class);
        when(actuatorId2.getActuatorId()).thenReturn("actuator2");
        //Act
        actuatorRepositoryMemImpl.save(actuator);
        boolean result = actuatorRepositoryMemImpl.containsIdentity(actuatorId2);
        //Assert
        assertFalse(result);
    }

    /**
     * Test that the containsIdentity method returns true when the repository is non-empty and contains the identity.
     *
     */
    @Test
    void testContainsIdentityNonEmptyRepositoryContainsIdentityShouldReturnTrue() {
        //Act
        actuatorRepositoryMemImpl.save(actuator);
        boolean result = actuatorRepositoryMemImpl.containsIdentity(actuatorId);
        //Assert
        assertTrue(result);
    }

    @Test
    void testFindActuatorIdsByDeviceIdWithANullDeviceId() {
        // Arrange
        DeviceId nullDeviceId = null;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorRepositoryMemImpl.findActuatorIdsByDeviceId(nullDeviceId));
    }

    @Test
    void testFindActuatorIdsByDeviceIdWhenDeviceIdDoesNotExists() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("InvalidId");
        actuatorRepositoryMemImpl.save(actuator);

        // Act
        Iterable<ActuatorId> actuators = actuatorRepositoryMemImpl.findActuatorIdsByDeviceId(deviceId);

        // Assert
        assertFalse(actuators.iterator().hasNext());
    }

    @Test
    void testFindActuatorIdsByDeviceIdWhenDeviceIdExists() {
        // Arrange
        DeviceId deviceId = mock(DeviceId.class);
        when(deviceId.getIdentity()).thenReturn("ValidId");
        actuatorRepositoryMemImpl.save(actuator);
        when(actuator.getDeviceId()).thenReturn(deviceId);

        // Act
        Iterable<ActuatorId> actuators = actuatorRepositoryMemImpl.findActuatorIdsByDeviceId(deviceId);
        List<ActuatorId> actuatorList = new ArrayList<>();
        actuators.forEach(actuatorList::add);

        // Assert
        assertTrue(actuatorList.contains(actuatorId));
    }
}