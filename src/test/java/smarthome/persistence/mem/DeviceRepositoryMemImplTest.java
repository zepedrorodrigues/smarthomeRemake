package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;

import java.util.ArrayList;
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
 * Unit tests for the class for DeviceRepositoryMemImpl.
 */
class DeviceRepositoryMemImplTest {

    Device deviceDouble1;
    DeviceId deviceIdDouble1;
    Device deviceDouble2;
    DeviceId deviceIdDouble2;
    DeviceRepositoryMemImpl deviceRepositoryMemImpl;
    RoomId roomIdDouble1;
    RoomId roomIdDouble2;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        deviceRepositoryMemImpl = new DeviceRepositoryMemImpl();
        deviceDouble1 = mock(Device.class);
        deviceDouble2 = mock(Device.class);
        deviceIdDouble1 = mock(DeviceId.class);
        deviceIdDouble2 = mock(DeviceId.class);
        when(deviceDouble1.getIdentity()).thenReturn(deviceIdDouble1);
        when(deviceDouble2.getIdentity()).thenReturn(deviceIdDouble2);
        roomIdDouble1 = mock(RoomId.class);
        roomIdDouble2 = mock(RoomId.class);
        when(deviceDouble1.getRoomId()).thenReturn(roomIdDouble1);
        when(deviceDouble2.getRoomId()).thenReturn(roomIdDouble2);
    }

    /**
     * Test that the save method saves a device to an empty repository.
     * The method should return the saved device.
     */
    @Test
    void testSaveToAnEmptyRepository() {
        //Act
        Device result = deviceRepositoryMemImpl.save(deviceDouble1);

        //Assert
        assertEquals(result, deviceDouble1
                , "The saved device should be the same as the one passed to the save method.");
    }

    /**
     * Test that the save method saves a device to a non-empty repository.
     * The method should return the saved device.
     */
    @Test
    void testSaveToANonEmptyRepository() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);

        //Act
        Device result = deviceRepositoryMemImpl.save(deviceDouble2);

        //Assert
        assertEquals(result, deviceDouble2
                , "The saved device should be the same as the one passed to the save method.");
    }

    /**
     * Test that the save method throws an exception when the device is null.
     */
    @Test
    void testSaveNullDeviceShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.save(null)
                , "The save method should throw an exception when the device is null.");
    }

    /**
     * Test that the save method doesn't throw an exception when the device is not null.
     */
    @Test
    void testSaveNotNullDeviceShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> deviceRepositoryMemImpl.save(deviceDouble1)
                , "The save method should not throw an exception when the device is not null.");
    }

    /**
     * Test that the save method throws an exception when the device id already exists in the repository.
     */
    @Test
    void testSaveDuplicateDeviceShouldThrowException() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.save(deviceDouble1)
                , "The save method should throw an exception when the device id already exists in the repository.");
    }

    /**
     * Test that the findAll method returns an empty iterable
     * when the repository is empty.
     */
    @Test
    void testFindAllToAnEmptyRepository() {
        //Act
        List<Device> result = new ArrayList<>();
        deviceRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(), "The findAll method should return an " +
                "empty iterable when the repository is empty.");
    }

    /**
     * Test that the findAll method returns an iterable with all devices in the repository.
     */
    @Test
    void testFindAllToANonEmptyRepositoryShouldReturnAllDevices() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);
        int size = 2;

        //Act
        List<Device> result = new ArrayList<>();
        deviceRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertEquals(size, result.size(), "The findAll method should return " +
                "an iterable with all devices in the repository.");
    }

    /**
     * Test that the findAll method returns an iterable with all devices in the repository.
     */
    @Test
    void testFindAllToANonEmptyRepositoryShouldContainAllDevices() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        //Act
        List<Device> result = new ArrayList<>();
        deviceRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.contains(deviceDouble1), "The findAll method should return " +
                "an iterable with all devices in the repository, in this case, deviceDouble1.");
        assertTrue(result.contains(deviceDouble2), "The findAll method should return " +
                "an iterable with all devices in the repository, in this case, deviceDouble2.");
    }

    /**
     * Test that the getByIdentity method returns an empty optional
     * when the repository is empty.
     */
    @Test
    void testFindByIdentityToAnEmptyRepository() {
        //Act
        Optional<Device> result = deviceRepositoryMemImpl.findByIdentity(deviceIdDouble1);

        //Assert
        assertTrue(result.isEmpty(), "The getByIdentity method should return " +
                "an empty optional when the repository is empty.");
    }

    /**
     * Test that the getByIdentity method returns the correct device
     * when the repository is non-empty.git stat
     */
    @Test
    void testFindByIdentityToANonEmptyRepository() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);

        //Act
        Device result = deviceRepositoryMemImpl.findByIdentity(deviceIdDouble1).orElseThrow();

        //Assert
        assertEquals(result, deviceDouble1, "The getByIdentity method should return " +
                "the correct device when the repository is non-empty.");
    }

    /**
     * Test that the getByIdentity method throws an exception when the device id is null.
     */
    @Test
    void testFindByIdentityNullDeviceIdShouldThrowException() {
        //Act
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.findByIdentity(null));
    }

    /**
     * Test that the getByIdentity method doesn't throw an exception when the device id is not null.
     */
    @Test
    void testFindByIdentityNotNullDeviceIdShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> deviceRepositoryMemImpl.findByIdentity(deviceIdDouble1), "The getByIdentity method " +
                "should not throw an exception when the device id is not null.");
    }

    /**
     * Test that the containsIdentity method returns true when the device id
     * exists in the repository.
     */
    @Test
    void testContainsIdentityReturnsTrueWhenDeviceIdExists() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);

        //Act
        boolean result = deviceRepositoryMemImpl.containsIdentity(deviceIdDouble1);

        //Assert
        assertTrue(result, "The containsIdentity method should return true " +
                "when the device id exists in the repository.");
    }

    /**
     * Test that the containsIdentity method returns false when the device id
     * doesn't exist in the repository.
     */
    @Test
    void testContainsIdentityReturnsFalseWhenDeviceIdDoesntExist() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);

        //Act
        boolean result = deviceRepositoryMemImpl.containsIdentity(deviceIdDouble2);

        //Assert
        assertFalse(result, "The containsIdentity method should return false " +
                "when the device id doesn't exist in the repository.");
    }

    /**
     * Test that the containsIdentity method throws an exception when the device id is null.
     */
    @Test
    void testContainsIdentityNullDeviceIdShouldThrowException() {
        //Act
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.containsIdentity(null)
                , "The containsIdentity method should throw an exception when the device id is null.");
    }

    /**
     * Test that the containsIdentity method doesn't throw an exception when the device id is not null.
     */
    @Test
    void testContainsIdentityNotNullDeviceIdShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> deviceRepositoryMemImpl.containsIdentity(deviceIdDouble1)
                , "The containsIdentity method should not throw an exception when the device id is not null.");
    }

    /**
     * Test that the findByRoomIdentity method returns an empty iterable when the repository is empty.
     */
    @Test
    void testFindDevicesByRoomIdFromAnEmptyRepository() {
        //Act
        List<Device> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDevicesByRoomId(roomIdDouble1).forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(), "The findByRoomIdentity method should return an empty iterable " +
                "when the repository is empty.");
    }

    /**
     * Test that the findByRoomIdentity method returns the correct device when the repository is not empty
     * and has only one device.
     */
    @Test
    void testFindDevicesByRoomIdentityWhenOnlyOneDeviceInRoomShouldRetrieveCorrectDevice() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        //Act
        List<Device> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDevicesByRoomId(roomIdDouble1).forEach(result::add);

        //Assert
        assertTrue(result.contains(deviceDouble1), "The findByRoomIdentity method should return " +
                "the correct device when the repository is not empty and has only one device.");
    }

    /**
     * Test that the findByRoomIdentity method returns the correct devices when the repository is not empty
     * and has multiple devices.
     */
    @Test
    void testFindDevicesByRoomIdentityWhenMultipleDevicesInRoomShouldRetrieveCorrectDevices() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        // Create a second Device in Room 1
        DeviceId deviceIdDouble3 = mock(DeviceId.class);
        Device deviceDouble3 = mock(Device.class);
        when(deviceDouble3.getIdentity()).thenReturn(deviceIdDouble3);
        when(deviceDouble3.getRoomId()).thenReturn(roomIdDouble1);
        deviceRepositoryMemImpl.save(deviceDouble3);

        //Act
        List<Device> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDevicesByRoomId(roomIdDouble1).forEach(result::add);

        //Assert
        assertTrue(result.contains(deviceDouble1), "The findByRoomIdentity method should return the correct " +
                "devices when the repository is not empty and has multiple devices, in this case, deviceDouble1.");
        assertTrue(result.contains(deviceDouble3), "The findByRoomIdentity method should return the correct " +
                "devices when the repository is not empty and has multiple devices, in this case, deviceDouble3.");
    }

    /**
     * Test that the findByRoomIdentity method doesn't retrieve incorrect devices when the repository is not empty.
     */
    @Test
    void testFindDevicesByRoomIdentityFromANonEmptyRepositoryShouldNotRetrieveIncorrectDevices() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        //Act
        List<Device> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDevicesByRoomId(roomIdDouble1).forEach(result::add);

        //Assert
        assertFalse(result.contains(deviceDouble2), "The findByRoomIdentity method " +
                "should not retrieve incorrect devices when the repository is not empty.");
    }

    /**
     * Test that the findByRoomIdentity method throws an exception when the room id is null.
     */
    @Test
    void testFindDevicesByRoomIdentityNullRoomIdShouldThrowException() {
        //Act
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.findDevicesByRoomId(null)
                , "The findByRoomIdentity method should throw an exception when the room id is null.");
    }

    /**
     * Test that the findDeviceIdsByRoomIdentity method returns an empty iterable when the repository is empty.
     */
    @Test
    void testFindDeviceIdsByRoomIdFromAnEmptyRepository() {
        // Act
        List<DeviceId> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDeviceIdsByRoomId(roomIdDouble1).forEach(result::add);

        // Assert
        assertTrue(result.isEmpty(), "The findDeviceIdsByRoomIdentity method should return an empty iterable " +
                "when the repository is empty.");
    }

    /**
     * Test that the findDeviceIdsByRoomIdentity method returns the correct device ID when the repository is not empty
     * and has only one device.
     */
    @Test
    void testFindDeviceIdsByRoomIdentityWhenOnlyOneDeviceInRoomShouldRetrieveCorrectDeviceId() {
        // Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        // Act
        List<DeviceId> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDeviceIdsByRoomId(roomIdDouble1).forEach(result::add);

        // Assert
        assertTrue(result.contains(deviceDouble1.getIdentity()), "The findDeviceIdsByRoomIdentity method should return " +
                "the correct device ID when the repository is not empty and has only one device.");
    }

    /**
     * Test that the findDeviceIdsByRoomIdentity method returns the correct device IDs when the repository is not empty
     * and has multiple devices.
     */
    @Test
    void testFindDeviceIdsByRoomIdentityWhenMultipleDevicesInRoomShouldRetrieveCorrectDeviceIds() {
        // Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        // Create a second Device in Room 1
        DeviceId deviceIdDouble3 = mock(DeviceId.class);
        Device deviceDouble3 = mock(Device.class);
        when(deviceDouble3.getIdentity()).thenReturn(deviceIdDouble3);
        when(deviceDouble3.getRoomId()).thenReturn(roomIdDouble1);
        deviceRepositoryMemImpl.save(deviceDouble3);

        // Act
        List<DeviceId> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDeviceIdsByRoomId(roomIdDouble1).forEach(result::add);

        // Assert
        assertTrue(result.contains(deviceDouble1.getIdentity()), "The findDeviceIdsByRoomIdentity method should return the correct " +
                "device IDs when the repository is not empty and has multiple devices, in this case, deviceDouble1.");
        assertTrue(result.contains(deviceIdDouble3), "The findDeviceIdsByRoomIdentity method should return the correct " +
                "device IDs when the repository is not empty and has multiple devices, in this case, deviceDouble3.");
    }

    /**
     * Test that the findDeviceIdsByRoomIdentity method doesn't retrieve incorrect device IDs when the repository is not empty.
     */
    @Test
    void testFindDeviceIdsByRoomIdentityFromANonEmptyRepositoryShouldNotRetrieveIncorrectDeviceIds() {
        // Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        // Act
        List<DeviceId> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDeviceIdsByRoomId(roomIdDouble1).forEach(result::add);

        // Assert
        assertFalse(result.contains(deviceDouble2.getIdentity()), "The findDeviceIdsByRoomIdentity method " +
                "should not retrieve incorrect device IDs when the repository is not empty.");
    }

    /**
     * Test that the findDeviceIdsByRoomIdentity method throws an exception when the room id is null.
     */
    @Test
    void testFindDeviceIdsByRoomIdentityNullRoomIdShouldThrowException() {
        // Act
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.findDeviceIdsByRoomId(null)
                , "The findDeviceIdsByRoomIdentity method should throw an exception when the room id is null.");
    }

    /**
     * Test that the update method throws an exception when the repository is empty.
     */
    @Test
    void testUpdateToAnEmptyRepositoryShouldThrowException() {
        //Act
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.update(deviceDouble1)
                , "The update method should throw an exception when the repository is empty.");
    }

    /**
     * Test that the update method updates an existing device in the repository.
     */
    @Test
    void testUpdateAnExistingDeviceShouldUpdateDevice() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);

        //Act
        deviceRepositoryMemImpl.update(deviceDouble1);

        //Assert
        assertEquals(deviceDouble1, deviceRepositoryMemImpl.findByIdentity(deviceIdDouble1).orElseThrow()
                , "The update method should update the device in the repository.");
    }

    /**
     * Test that the update method throws an exception when the device is null.
     */
    @Test
    void testUpdateWhenDeviceDoesNotExistInRepositoryShouldThrowException() {
        //Act
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.update(deviceDouble1)
                , "The update method should throw an exception when the device does not exist in the repository.");
    }

    /**
     * Test that the update method doesn't throw an exception when the device exists in the repository.
     */
    @Test
    void testUpdateWhenDeviceExistsInRepositoryShouldNotThrowException() {
        //Arrange
        deviceRepositoryMemImpl.save(deviceDouble1);

        //Act & Assert
        assertDoesNotThrow(() -> deviceRepositoryMemImpl.update(deviceDouble1)
                , "The update method should not throw an exception when the device exists in the repository.");
    }

    /**
     * Test that the GetByDeviceTypeName method returns an empty iterable when the repository is empty.
     */
    @Test
    void testGetByDeviceTypeEmptyRepository() {
        // Arrange
        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        when(deviceTypeName.getDeviceTypeName()).thenReturn("GridPowerMeter");
        List<DeviceId> devices = new ArrayList<>();

        // Act
        Iterable<DeviceId> result = deviceRepositoryMemImpl.findDeviceIdsByDeviceTypeName(deviceTypeName);
        result.forEach(devices::add);

        // Assert
        boolean isEmpty = devices.isEmpty();
        assertTrue(isEmpty, "Should be empty.");
    }

    /**
     * Test that the GetByDeviceTypeName method returns an iterable with one device when the repository is non-empty.
     */
    @Test
    void testGetByDeviceTypeNonEmptyRepository() {
        // Arrange
        int expectedSize = 1;
        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        when(deviceTypeName.getDeviceTypeName()).thenReturn("GridPowerMeter");
        when(deviceDouble1.getDeviceTypeName()).thenReturn(deviceTypeName);
        deviceRepositoryMemImpl.save(deviceDouble1);
        List<DeviceId> devices = new ArrayList<>();

        // Act
        Iterable<DeviceId> result = deviceRepositoryMemImpl.findDeviceIdsByDeviceTypeName(deviceTypeName);
        result.forEach(devices::add);

        // Assert
        int actualSize = devices.size();
        assertEquals(expectedSize, actualSize, "Should contain one device.");
    }

    /**
     * Test that the GetByDeviceTypeName method returns empty iterable when the device type does not exist in the
     * repository.
     */
    @Test
    void testGetByDeviceTypeDoesNotExist() {
        // Arrange
        int expected = 0;
        DeviceTypeName deviceTypeName = mock(DeviceTypeName.class);
        when(deviceTypeName.getDeviceTypeName()).thenReturn("GridPowerMeter");
        when(deviceDouble1.getDeviceTypeName()).thenReturn(mock(DeviceTypeName.class));
        DeviceTypeName deviceTypeName1 = mock(DeviceTypeName.class);
        when(deviceTypeName.getDeviceTypeName()).thenReturn("Heater");
        deviceRepositoryMemImpl.save(deviceDouble1);
        List<DeviceId> devices = new ArrayList<>();

        // Act
        Iterable<DeviceId> result = deviceRepositoryMemImpl.findDeviceIdsByDeviceTypeName(deviceTypeName1);
        result.forEach(devices::add);

        // Assert
        int actualSize = devices.size();
        assertEquals(expected, actualSize, "Should be empty.");
    }

    /**
     * Test that the GetByDeviceTypeName method throws an exception when the device type is null.
     */
    @Test
    void testGetByDeviceTypeNullDeviceType() {
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> deviceRepositoryMemImpl.findDeviceIdsByDeviceTypeName(null));
    }

    /**
     * This test checks that the list of device ids is empty when the repository is empty.
     */
    @Test
    void testFindDeviceIdsToAnEmptyRepository() {
        // Act
        List<DeviceId> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDeviceIds().forEach(result::add);

        // Assert
        boolean isEmpty = result.isEmpty();
        assertTrue(isEmpty, "The list of device ids should be empty.");
    }

    /**
     * This test checks that the list of device ids contains two device ids when the repository contains two devices.
     */
    @Test
    void testFindDeviceIdsToANonEmptyRepository() {
        // Arrange
        int expectedSize = 2;
        deviceRepositoryMemImpl.save(deviceDouble1);
        deviceRepositoryMemImpl.save(deviceDouble2);

        // Act
        List<DeviceId> result = new ArrayList<>();
        deviceRepositoryMemImpl.findDeviceIds().forEach(result::add);

        // Assert
        int actualSize = result.size();
        assertEquals(expectedSize, actualSize, "The list of device ids should contain two device ids.");
    }
}
