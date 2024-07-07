package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The DeviceTypeRepositoryMemImplTest class represents a test suite for the DeviceTypeRepositoryMemImpl class.
 */
class DeviceTypeRepositoryMemImplTest {

    DeviceType deviceType;
    DeviceTypeRepositoryMemImpl deviceTypeRepositoryMemImpl;

    /**
     * Sets up the necessary test objects.
     */
    @BeforeEach
    void setUp() {
        deviceType = mock(DeviceType.class);
        when(deviceType.getIdentity()).thenReturn(mock(DeviceTypeName.class));
        deviceTypeRepositoryMemImpl = new DeviceTypeRepositoryMemImpl();
    }

    /**
     * Tests the save method returns the saved DeviceType when the repository is empty.
     */
    @Test
    void testSaveWhenRepositoryIsEmpty() {
        //Arrange
        DeviceType expected = deviceType;
        //Act
        DeviceType result = deviceTypeRepositoryMemImpl.save(deviceType);
        //Assert
        assertEquals(expected, result, "The saved DeviceType should be the same as the DeviceType that was saved.");
    }

    /**
     * Tests the save method throws an exception when the DeviceType is null.
     */
    @Test
    void testSaveWhenTheDeviceTypeIsNull() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> deviceTypeRepositoryMemImpl.save(null),
                "Should throw an exception.");
    }

    /**
     * Tests the save method throws an exception when the DeviceType already exists.
     */
    @Test
    void testSaveWhenTheDeviceTypeAlreadyExists() {
        //Arrange
        deviceTypeRepositoryMemImpl.save(deviceType);
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> deviceTypeRepositoryMemImpl.save(deviceType),
                "Should throw an exception.");
    }

    /**
     * Tests the findAll method returns an empty list when the repository is empty.
     */
    @Test
    void testFindAllForEmptyRepository() {
        //Arrange
        List<DeviceType> deviceTypes = new ArrayList<>();
        //Act
        Iterable<DeviceType> result = deviceTypeRepositoryMemImpl.findAll();
        result.forEach(deviceTypes::add);
        //Assert
        assertTrue(deviceTypes.isEmpty(), "Should be empty.");
    }

    /**
     * Tests the findAll method returns a list of DeviceType objects when the repository is not empty.
     */
    @Test
    void testFindAllForNonEmptyRepository() {
        //Arrange
        deviceTypeRepositoryMemImpl.save(deviceType);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getIdentity()).thenReturn(mock(DeviceTypeName.class));
        deviceTypeRepositoryMemImpl.save(deviceType1);

        List<DeviceType> deviceTypes = new ArrayList<>();
        int expected = 2;
        //Act
        Iterable<DeviceType> result = deviceTypeRepositoryMemImpl.findAll();
        result.forEach(deviceTypes::add);
        //Assert
        assertEquals(expected, deviceTypes.size(), "Should have 2 elements.");
    }

    /**
     * Tests the getDeviceTypeNames method returns an empty list when the repository is empty.
     */
    @Test
    void testFindDeviceTypeNamesForEmptyRepository() {
        //Arrange
        List<DeviceTypeName> deviceTypeNames = new ArrayList<>();
        //Act
        Iterable<DeviceTypeName> result = deviceTypeRepositoryMemImpl.findDeviceTypeNames();
        result.forEach(deviceTypeNames::add);
        //Assert
        assertTrue(deviceTypeNames.isEmpty(), "Should be empty and return true.");
    }

    /**
     * Tests the getDeviceTypeNames method returns a list of DeviceTypeName objects when the repository is not empty.
     */
    @Test
    void testFindDeviceTypeNamesForNonEmptyRepository() {
        //Arrange
        deviceTypeRepositoryMemImpl.save(deviceType);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getIdentity()).thenReturn(mock(DeviceTypeName.class));
        deviceTypeRepositoryMemImpl.save(deviceType1);

        List<DeviceTypeName> deviceTypeNames = new ArrayList<>();
        int expectedSize = 2;
        //Act
        Iterable<DeviceTypeName> result = deviceTypeRepositoryMemImpl.findDeviceTypeNames();
        result.forEach(deviceTypeNames::add);
        //Assert
        assertEquals(expectedSize, deviceTypeNames.size(), "Should have 2 elements.");
    }

    /**
     * Tests the getByIdentity method throws an exception when the DeviceTypeName is null.
     */
    @Test
    void testFindByIdentityNullDeviceTypeName() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> deviceTypeRepositoryMemImpl.findByIdentity(null));
    }

    /**
     * Tests the getByIdentity method returns an empty optional when the DeviceType does not exist.
     */
    @Test
    void testFindByIdentityDoesNotExist() {
        //Arrange
        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        //Act
        Optional<DeviceType> result = deviceTypeRepositoryMemImpl.findByIdentity(deviceTypeName);
        //Assert
        assertTrue(result.isEmpty(), "Should be empty.");
    }

    /**
     * Tests the getByIdentity method returns the DeviceType when the DeviceType exists.
     */
    @Test
    void testFindByIdentityExists() {
        //Arrange
        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        DeviceTypeName deviceTypeName1 = mock(DeviceTypeName.class);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getIdentity()).thenReturn(deviceTypeName);
        DeviceType deviceType2 = mock(DeviceType.class);
        when(deviceType2.getIdentity()).thenReturn(deviceTypeName1);

        deviceTypeRepositoryMemImpl.save(deviceType1);
        deviceTypeRepositoryMemImpl.save(deviceType2);
        //Act
        Optional<DeviceType> result = deviceTypeRepositoryMemImpl.findByIdentity(deviceTypeName);
        //Assert
        assertEquals(deviceType1, result.get(),
                "The device type should be the same as the device type that was saved.");
    }

    /**
     * Tests the containsIdentity method throws an exception when the DeviceTypeName is null.
     */
    @Test
    void testContainsIdentityNullDeviceTypeName() {
        //Act + assert
        assertThrows(IllegalArgumentException.class, () -> deviceTypeRepositoryMemImpl.containsIdentity(null));
    }

    /**
     * Tests the containsIdentity method returns false when the DeviceType does not exist.
     */
    @Test
    void testContainsIdentityDoesNotExist() {
        //Arrange
        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getIdentity()).thenReturn(deviceTypeName);

        //Act
        boolean result = deviceTypeRepositoryMemImpl.containsIdentity(deviceTypeName);
        //Assert
        assertFalse(result, "Should return false.");
    }

    /**
     * Tests the containsIdentity method returns true when the DeviceType exists.
     */
    @Test
    void testContainsIdentityExists() {
        //Arrange
        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        DeviceType deviceType1 = mock(DeviceType.class);
        when(deviceType1.getIdentity()).thenReturn(deviceTypeName);

        deviceTypeRepositoryMemImpl.save(deviceType1);
        //Act
        boolean result = deviceTypeRepositoryMemImpl.containsIdentity(deviceTypeName);
        //Assert
        assertTrue(result, "Should return true.");
    }
}