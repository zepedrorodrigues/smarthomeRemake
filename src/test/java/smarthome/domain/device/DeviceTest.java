package smarthome.domain.device;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the Device class.
 * Each method tests a specific functionality of the Device class.
 */
class DeviceTest {

    DeviceName deviceName;
    DeviceTypeName deviceTypeName;
    RoomId roomId;
    DeviceId deviceId;
    DeviceStatus deviceStatus;

    /**
     * This method sets up the common objects used in the test methods.
     */
    @BeforeEach
    void setUp() {
        // Variables for the Device class
        deviceName = new DeviceName("Device Name");
        deviceTypeName = new DeviceTypeName("Device Type");
        roomId = new RoomId("Room Id");
        deviceId = new DeviceId("Device Id");
        deviceStatus = new DeviceStatus(true);
    }

    /**
     * This test checks if the Device constructor throws an exception when the device name is null.
     */
    @Test
    void testConstructorThrowsExceptionWhenDeviceNameIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Device(null, deviceTypeName, roomId),
                "The constructor should throw an exception when the device name is null.");
    }

    /**
     * This test checks if the Device constructor throws an exception when the device type name is null.
     */
    @Test
    void testConstructorThrowsExceptionWhenDeviceTypeNameIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Device(deviceName, null, roomId),
                "The constructor should throw an exception when the device type is null.");
    }

    /**
     * This test checks if the Device constructor throws an exception when the room id is null.
     */
    @Test
    void testConstructorThrowsExceptionWhenRoomIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Device(deviceName, deviceTypeName, null),
                "The constructor should throw an exception when the room id is null.");
    }

    /**
     * This test checks if the Device constructor does not throw an exception when all parameters are valid.
     */
    @Test
    void testConstructorDoesNotThrowExceptionWhenAllParametersAreValid() {
        // Act & Assert
        assertDoesNotThrow(() -> new Device(deviceName, deviceTypeName, roomId),
                "The constructor should not throw an exception when all parameters are valid.");
    }

    /**
     * This test checks if the Device constructor from the DB doesn't come null.
     */
    @Test
    void testConstructorForDBReturnsNotNull() {
        // Arrange
        DeviceId deviceId = new DeviceId("Device Id");
        DeviceStatus deviceStatus = new DeviceStatus(true);

        // Act
        Device device = new Device(deviceId, deviceName, deviceTypeName, roomId, deviceStatus);

        // Assert
        assertNotNull(device, "The constructor should not return null.");
    }

    /**
     * This test checks if the getDeviceId method of the Device class returns the device id.
     */
    @Test
    void testGetDeviceIdReturnsDeviceId() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        DeviceId deviceId = device.getDeviceId();

        // Assert
        assertNotNull(deviceId, "The getDeviceId method should return the device id.");
    }

    /**
     * This test checks if the getIdentity method of the Device class returns the device id.
     */
    @Test
    void testGetIdentityReturnsDeviceId() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        DeviceId deviceId = device.getIdentity();

        // Assert
        assertNotNull(deviceId, "The getIdentity method should return the device id.");
    }

    /**
     * This test checks if the getDeviceStatus method of the Device class returns the device status.
     */
    @Test
    void testGetDeviceStatusReturnsDeviceStatus() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        DeviceStatus deviceStatus = device.getDeviceStatus();

        // Assert
        assertNotNull(deviceStatus, "The getDeviceStatus method should return the device status.");
    }

    /**
     * This test checks if the getDeviceName method of the Device class returns the device name.
     */
    @Test
    void testGetDeviceNameReturnsDeviceName() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        DeviceName result = device.getDeviceName();

        // Assert
        assertEquals(deviceName, result, "The getDeviceName method should return the device name.");
    }

    /**
     * This test checks if the getDeviceTypeName method of the Device class returns the device type.
     */
    @Test
    void testGetDeviceTypeReturnsDeviceTypeName() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        DeviceTypeName result = device.getDeviceTypeName();

        // Assert
        assertEquals(deviceTypeName, result, "The getDeviceTypeName method should return the device type.");
    }

    /**
     * This test checks if the getRoomId method of the Device class returns the room id.
     */
    @Test
    void testGetRoomIdReturnsRoomId() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        RoomId result = device.getRoomId();

        // Assert
        assertEquals(roomId, result, "The getRoomId method should return the room id.");
    }

    /**
     * This test checks if the equals method of the Device class returns false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObjectShouldReturnFalse() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        boolean result = device.equals(null);

        // Assert
        assertFalse(result, "The equals method should return false when comparing with a null object.");
    }

    /**
     * This test checks if the equals method of the Device class returns true when comparing the same object.
     */
    @Test
    void testEqualsForSameObjectShouldReturnTrue() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        boolean result = device.equals(device);

        // Assert
        assertTrue(result, "The equals method should return true when comparing the same object.");
    }

    /**
     * This test checks if the equals method of the Device class returns false when comparing with a different class.
     */
    @Test
    void testEqualsForDifferentClassShouldReturnFalse() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        boolean result = device.equals(new Object());

        // Assert
        assertFalse(result, "The equals method should return false when comparing with a different type of object.");
    }

    /**
     * This test checks if the equals method of the Device class returns false when comparing two devices with the same parameters.
     */
    @Test
    void testEqualsForSameParametersShouldReturnFalse() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);
        Device device2 = new Device(deviceName, deviceTypeName, roomId);

        // Act
        boolean result = device.equals(device2);

        // Assert
        assertFalse(result, "The equals method should return false when comparing two devices with the same " +
                "parameters.");
    }

    /**
     * This test checks if the equals method of the Device class returns true when comparing two devices with the same
     * device id.
     */
    @Test
    void testEqualsForSameDeviceIdShouldReturnTrue() {
        // Arrange
        Device device = new Device(deviceId, deviceName, deviceTypeName, roomId, deviceStatus);
        Device device2 = new Device(deviceId, deviceName, deviceTypeName, roomId, deviceStatus);

        // Act
        boolean result = device.equals(device2);

        // Assert
        assertTrue(result,
                "The equals method should return true when comparing two devices with the same device id.");
    }

    /**
     * This test checks if the hashCode method of the Device class returns the same hash code for the same object.
     */
    @Test
    void testHashCodeEqualityForSameObject() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);
        Device device2 = device;

        // Act
        int hashCode1 = device.hashCode();
        int hashCode2 = device2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2, "The hashCode method should return the same hash code for the same object.");
    }

    /**
     * This test checks if the hashCode method of the Device class returns different hash codes for different objects.
     */
    @Test
    void testHashCodeEqualityForDifferentObjects() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);
        Device device2 = new Device(deviceName, deviceTypeName, roomId);

        // Act
        int hashCode1 = device.hashCode();
        int hashCode2 = device2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return different hash codes for different objects.");
    }

    /**
     * This test checks if the deactivateDevice method of the Device class changes the device status to false.
     */
    @Test
    void testDeactivateDeviceReturnsDeviceStatusFalse() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);

        // Act
        device.deactivateDevice();

        // Assert
        DeviceStatus deviceStatus = device.getDeviceStatus();
        boolean result = deviceStatus.getStatus();
        assertFalse(result, "The deactivateDevice method should change the device status to false.");
    }

    /**
     * This test checks if the deactivateDevice method of the Device class throws an exception when the device is
     * already deactivated.
     */
    @Test
    void testDeactivateDeviceThrowsExceptionWhenDeviceIsAlreadyFalse() {
        // Arrange
        Device device = new Device(deviceName, deviceTypeName, roomId);
        device.deactivateDevice();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, device::deactivateDevice,
                "The deactivateDevice method should throw an exception when the device is already deactivated.");
    }
}