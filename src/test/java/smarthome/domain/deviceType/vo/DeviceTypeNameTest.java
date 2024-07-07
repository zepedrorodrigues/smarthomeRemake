package smarthome.domain.deviceType.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceTypeNameTest {

    /**
     * Tests the constructor of the DeviceTypeName class does not throw an exception
     * when a valid device type name is provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidDeviceTypeName() {
        //Arrange
        String deviceTypeName = "GridPowerMeter";
        //Act + Assert
        assertDoesNotThrow(() -> new DeviceTypeName(deviceTypeName), "Should not throw exception.");
    }

    /**
     * Tests the constructor of the class DeviceTypeName when an empty deviceTypeName is set
     */
    @Test
    void testConstructorThrowsExceptionForInvalidDeviceTypeName() {
        //Arrange
        String blankDeviceTypeName = "  ";
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new DeviceTypeName(blankDeviceTypeName),
                "Should throw an exception.");
    }

    /**
     * Tests the constructor of the class DeviceTypeName when a null deviceTypeName is set
     */
    @Test
    void testConstructorThrowsExceptionForNullDeviceTypeName() {
        //Arrange
        String nullDeviceTypeName = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new DeviceTypeName(nullDeviceTypeName),
                "Should throw an exception.");
    }

    /**
     * Tests that the getDeviceTypeName method returns the correct value.
     */
    @Test
    void testGetDeviceTypeNameReturnsCorrectValue() {
        //Arrange
        String expected = "GridPowerMeter";
        DeviceTypeName deviceTypeName = new DeviceTypeName(expected);
        //Act
        String result = deviceTypeName.getDeviceTypeName();
        //Assert
        assertEquals(expected, result, "Should return the correct value.");
    }

    /**
     * Tests that the equals method returns true when the same object is passed.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        // Act
        boolean result = deviceTypeName.equals(deviceTypeName);
        // Assert
        assertTrue(result, "Should return true when the same object is passed.");
    }

    /**
     * Tests that the equals method returns true when the same type is passed.
     */
    @Test
    void testEqualsForSameTypeName() {
        // Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceTypeName deviceTypeName1 = new DeviceTypeName("GridPowerMeter");
        // Act
        boolean result = deviceTypeName.equals(deviceTypeName1);
        // Assert
        assertTrue(result, "Should return true when the same type is passed.");
    }

    /**
     * Tests that the equals returns false when a different type is passed.
     */
    @Test
    void testEqualsForDifferentTypeName() {
        // Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceTypeName deviceTypeName1 = new DeviceTypeName("Heater");
        // Act
        boolean result = deviceTypeName.equals(deviceTypeName1);
        // Assert
        assertFalse(result, "Should return false when a different type is passed.");
    }

    /**
     * Tests that the equals method returns false when null is passed.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        // Act
        boolean result = deviceTypeName.equals(null);
        // Assert
        assertFalse(result, "Should return false when null is passed.");
    }

    /**
     * Tests that the equals method returns false when a different class object is passed.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        // Act
        boolean result = deviceTypeName.equals(new Object());
        // Assert
        assertFalse(result, "Should return false when a different class object is passed.");
    }

    /**
     * This test verifies the hashCode method of the DeviceTypeName class.
     * It creates two DeviceTypeName objects with the same type name and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameDeviceTypeName() {
        // Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceTypeName deviceTypeName1 = new DeviceTypeName("GridPowerMeter");
        // Act
        int hashCode = deviceTypeName.hashCode();
        int hashCode1 = deviceTypeName1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return equal hash codes for equal device type names.");
    }

    /**
     * This test verifies the hashCode method of the DeviceTypeName class.
     * It creates two DeviceTypeName objects with different type name and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentDeviceTypeName() {
        // Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("Fan");
        DeviceTypeName deviceTypeName1 = new DeviceTypeName("GridPowerMeter");
        // Act
        int hashCode = deviceTypeName.hashCode();
        int hashCode1 = deviceTypeName1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes for different device type names.");
    }

}