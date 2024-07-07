package smarthome.domain.deviceType;

import org.junit.jupiter.api.Test;
import smarthome.domain.deviceType.vo.DeviceTypeName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DeviceType
 */
class DeviceTypeTest {

    /**
     * Tests that the constructor of the DeviceType class does not throw an exception
     * when a valid device type name is provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidDeviceTypeName() {
        //Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        //Act + Assert
        assertDoesNotThrow(() -> new DeviceType(deviceTypeName), "Should not throw exception.");
    }

    /**
     * Tests that the constructor of the class DeviceType throws an exception when a null deviceTypeName is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullDeviceTypeName() {
        //Arrange
        DeviceTypeName deviceTypeName = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new DeviceType(deviceTypeName),
                "Should throw an exception.");
    }

    /**
     * Tests that the getIdentity method of the DeviceType class returns the correct value.
     */
    @Test
    void testGetIdentity() {
        //Arrange
        DeviceTypeName expected = new DeviceTypeName("GridPowerMeter");
        DeviceType deviceType = new DeviceType(expected);
        //Act
        DeviceTypeName result = deviceType.getIdentity();
        //Assert
        assertEquals(expected, result, "Should return the correct value.");
    }

    /**
     * Tests that the equals method of the DeviceType class returns false when the object to compare is null.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceType deviceType = new DeviceType(deviceTypeName);
        //Act
        boolean result = deviceType.equals(null);
        //Assert
        assertFalse(result, "Should return false.");
    }

    /**
     * Tests that the equals method of the DeviceType class returns true when the same object is passed.
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceType deviceType = new DeviceType(deviceTypeName);
        //Act
        boolean result = deviceType.equals(deviceType);
        //Assert
        assertTrue(result, "Should return true.");
    }

    /**
     * Tests that the equals method of the DeviceType class returns false when a different class object is passed.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        //Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceType deviceType = new DeviceType(deviceTypeName);
        //Act
        boolean result = deviceType.equals(new Object());
        //Assert
        assertFalse(result, "Should return false.");
    }

    /**
     * Tests that the equals method of the DeviceType class returns false when a different type is passed.
     */
    @Test
    void testEqualsForSameDeviceTypeName() {
        //Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceType deviceType = new DeviceType(deviceTypeName);
        DeviceType deviceType1 = new DeviceType(deviceTypeName);
        //Act
        boolean result = deviceType.equals(deviceType1);
        //Assert
        assertTrue(result, "Should return true.");
    }

    /**
     * Tests that the equals method of the DeviceType class returns false when a different type is passed.
     */
    @Test
    void testEqualsForDifferentDeviceTypeName() {
        //Arrange
        DeviceTypeName deviceTypeName1 = new DeviceTypeName("GridPowerMeter");
        DeviceTypeName deviceTypeName2 = new DeviceTypeName("Heater");
        DeviceType deviceType1 = new DeviceType(deviceTypeName1);
        DeviceType deviceType2 = new DeviceType(deviceTypeName2);
        //Act
        boolean result = deviceType1.equals(deviceType2);
        //Assert
        assertFalse(result, "Should return false.");
    }

    /**
     * Tests that the hashCode method of the DeviceType class returns the same hash code for the same object.
     */
    @Test
    void testHashCodeForSameObject() {
        //Arrange
        DeviceTypeName deviceTypeName = new DeviceTypeName("GridPowerMeter");
        DeviceType deviceType1 = new DeviceType(deviceTypeName);
        DeviceType deviceType2 = new DeviceType(deviceTypeName);
        //Act
        int hashCode1 = deviceType1.hashCode();
        int hashCode2 = deviceType2.hashCode();
        //Assert
        assertEquals(hashCode1, hashCode2, "Should return the same hash code.");
    }

    /**
     * Tests that the hashCode method of the DeviceType class returns different hash codes for different objects.
     */
    @Test
    void testHashCodeForDifferentObject() {
        //Arrange
        DeviceTypeName deviceTypeName1 = new DeviceTypeName("GridPowerMeter");
        DeviceTypeName deviceTypeName2 = new DeviceTypeName("Heater");
        DeviceType deviceType1 = new DeviceType(deviceTypeName1);
        DeviceType deviceType2 = new DeviceType(deviceTypeName2);
        //Act
        int hashCode1 = deviceType1.hashCode();
        int hashCode2 = deviceType2.hashCode();
        //Assert
        assertNotEquals(hashCode1, hashCode2, "Should return different hash codes.");
    }
}