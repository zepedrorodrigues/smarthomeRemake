package smarthome.domain.device.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for DeviceName
 */
class DeviceNameTest {

    /**
     * Tests the constructor of the DeviceName class does not throw an exception
     * when a valid device name is provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidDeviceName() {
        //Arrange
        String deviceName = "Fan";
        //Act + Assert
        assertDoesNotThrow(() -> new DeviceName(deviceName), "Should not throw exception.");
    }

    /**
     * Tests the constructor of the class DeviceName when an empty device name is set
     */
    @Test
    void testConstructorThrowsExceptionForInvalidDeviceName() {
        //Arrange
        String blankDeviceName = "  ";
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new DeviceName(blankDeviceName),
                "Should throw an exception.");
    }

    /**
     * Tests the constructor of the class DeviceName when a null device name is set
     */
    @Test
    void testConstructorThrowsExceptionForNullDeviceName() {
        //Arrange
        String nullDeviceName = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new DeviceName(nullDeviceName),
                "Should throw an exception.");
    }

    /**
     * Tests that the getDeviceName method of the DeviceName class returns the correct value.
     */
    @Test
    void testGetDeviceNameReturnsCorrectValue() {
        //Arrange
        String expected = "Fan";
        DeviceName deviceName = new DeviceName(expected);
        //Act
        String result = deviceName.getDeviceName();
        //Assert
        assertEquals(expected, result, "Should return the correct value.");
    }

    /**
     * Tests that the equals method of the DeviceName class returns true when the same object is passed.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        DeviceName deviceName = new DeviceName("Fan");
        // Act
        boolean result = deviceName.equals(deviceName);
        // Assert
        assertTrue(result, "Should return true when the same object is passed.");
    }

    /**
     * Tests that the equals method of the DeviceName class returns true when the same name is passed.
     */
    @Test
    void testEqualsForSameName() {
        // Arrange
        DeviceName deviceName = new DeviceName("Fan");
        DeviceName deviceName1 = new DeviceName("Fan");
        // Act
        boolean result = deviceName.equals(deviceName1);
        // Assert
        assertTrue(result, "Should return true when the same name is passed.");
    }

    /**
     * Tests that the equals method of the DeviceName class returns false when a different name is passed.
     */
    @Test
    void testEqualsForDifferentName() {
        // Arrange
        DeviceName deviceName = new DeviceName("Fan");
        DeviceName deviceName1 = new DeviceName("Heater");
        // Act
        boolean result = deviceName.equals(deviceName1);
        // Assert
        assertFalse(result, "Should return false when a different name is passed.");
    }

    /**
     * Tests that the equals method of the DeviceName class returns false when null is passed.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        DeviceName deviceName = new DeviceName("Fan");
        // Act
        boolean result = deviceName.equals(null);
        // Assert
        assertFalse(result, "Should return false when null is passed.");
    }

    /**
     * Tests that the equals method of the DeviceName class returns false when a different class object is passed.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        DeviceName deviceName = new DeviceName("Fan");
        // Act
        boolean result = deviceName.equals(new Object());
        // Assert
        assertFalse(result, "Should return false when a different class object is passed.");
    }

    /**
     * This test verifies the hashCode method of the DeviceName class.
     * It creates two DeviceName objects with the same name and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameDeviceName() {
        // Arrange
        DeviceName deviceName = new DeviceName("Fan");
        DeviceName deviceName1 = new DeviceName("Fan");
        // Act
        int hashCode = deviceName.hashCode();
        int hashCode1 = deviceName1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return the same hash code for the same device name.");
    }

    /**
     * This test verifies the hashCode method of the DeviceName class.
     * It creates two DeviceName objects with different names and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentDeviceName() {
        // Arrange
        DeviceName deviceName = new DeviceName("Fan");
        DeviceName deviceName1 = new DeviceName("Light");
        // Act
        int hashCode = deviceName.hashCode();
        int hashCode1 = deviceName1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes for different device names.");
    }

}