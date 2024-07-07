package smarthome.domain.device.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for DeviceStatus
 */
class DeviceStatusTest {

    DeviceStatus deviceStatus;
    Boolean status;

    /**
     * Set up method for the tests
     * Instantiates a DeviceStatus object and sets a valid status
     */
    @BeforeEach
    void setUp() {
        // Arrange
        status = true;
        deviceStatus = new DeviceStatus(status);
    }

    /**
     * Test the constructor of the class DeviceStatus when a true status is set
     */
    @Test
    void testConstructorIsCalledWithTrue() {
        // Assert
        assertNotNull(deviceStatus, "The device status should not be null");
    }

    /**
     * Test the constructor of the class DeviceStatus when a false status is set
     */
    @Test
    void testConstructorIsCalledWithFalse() {
        // Arrange
        status = false;
        // Act
        deviceStatus = new DeviceStatus(status);
        // Assert
        assertNotNull(deviceStatus, "The device status should not be null");
    }

    /**
     * Test the method getStatus of the class DeviceStatus when the status is true
     */
    @Test
    void testGetStatusWhenTrue() {
        // Act
        boolean result = deviceStatus.getStatus();
        // Assert
        assertTrue(result, "The device status should be true");
    }

    /**
     * Test the method getStatus of the class DeviceStatus when the status is false
     */
    @Test
    void testGetStatusWhenFalse() {
        // Arrange
        status = false;
        deviceStatus = new DeviceStatus(status);
        // Act
        boolean result = deviceStatus.getStatus();
        // Assert
        assertFalse(result, "The device status should be false");
    }

    /**
     * Test the method equals of the class DeviceStatus when
     * the object is an instance of DeviceStatus and has the same status
     */
    @Test
    void testEqualsWhenEqualObjectIsCompared() {
        // Arrange
        DeviceStatus equalDeviceStatus = new DeviceStatus(status);
        // Act
        boolean result = deviceStatus.equals(equalDeviceStatus);
        // Assert
        assertTrue(result, "The objects should be the same");
    }

    /**
     * Test the method equals of the class DeviceStatus when
     * the object is the same
     */
    @Test
    void testEqualsWhenSameObjectIsCompared() {
        // Act
        boolean result = deviceStatus.equals(deviceStatus);
        // Assert
        assertTrue(result, "The objects should be the same");
    }

    /**
     * Test the method equals of the class DeviceStatus when
     * the object is different from the deviceStatus object
     */
    @Test
    void testEqualsWhenDifferentObjectIsCompared() {
        // Arrange
        Object differentObject = new Object();
        // Act
        boolean result = deviceStatus.equals(differentObject);
        // Assert
        assertFalse(result, "The objects are different, so they should not be equal");
    }

    /**
     * Test the method equals of the class DeviceStatus when
     * the object is null
     */
    @Test
    void testEqualsWhenNullObjectIsCompared() {
        // Act
        boolean result = deviceStatus.equals(null);
        // Assert
        assertFalse(result, "The object is null, so they should not be equal");
    }

    /**
     * Test the method equals of the class DeviceStatus when
     * the object is an instance of DeviceStatus but has a different status
     */
    @Test
    void testEqualsWhenSameObjectWithDifferentValuesIsCompared() {
        // Arrange
        boolean differentActive = false;
        DeviceStatus differentDeviceStatus = new DeviceStatus(differentActive);
        // Act
        boolean result = deviceStatus.equals(differentDeviceStatus);
        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Test the method hashCode of the class DeviceStatus when
     * the object is an instance of DeviceStatus and has the same status
     * the hashcode should be the same
     */
    @Test
    void testHashCodeWhenObjectsAreEqualThenHashCodesShouldBeTheSame() {
        // Arrange
        DeviceStatus anotherDeviceStatus = new DeviceStatus(status);
        // Act
        int hashCode1 = deviceStatus.hashCode();
        int hashCode2 = anotherDeviceStatus.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "The hash code should be the same");
    }

    /**
     * Test the method hashCode of the class DeviceStatus when
     * the object is an instance of DeviceStatus but has a different status
     * the hashcode should be different
     */
    @Test
    void testHashCodeWhenObjectsAreNotEqualThenHashCodesShouldBeDifferent() {
        // Arrange
        boolean differentStatus = !status;
        DeviceStatus differentDeviceStatus = new DeviceStatus(differentStatus);
        // Act
        int hashCode1 = deviceStatus.hashCode();
        int hashCode2 = differentDeviceStatus.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hash code should be different");
    }
}