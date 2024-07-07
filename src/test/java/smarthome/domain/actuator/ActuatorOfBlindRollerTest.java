package smarthome.domain.actuator;

import org.junit.jupiter.api.Test;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.ScalePercentageValue;
import smarthome.domain.sensor.vo.values.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Represents a test class for ActuatorOfBlindRoller
 * It cover scenarios for when the actuator is created with valid attributes,
 * operate when the blind roller value is between 0 and 100, when the value is 0, when the value is 100,
 * when the value is less than 0, when the value is greater than 100, when the sameAs method is called.
 */
class ActuatorOfBlindRollerTest {

    
    /**
     * Tests the constructor of ActuatorOfBlindRoller when it is created with valid parameters
     * Should not be null
     * Constructor: ActuatorOfBlindRoller(DeviceId deviceId, ActuatorModelName actuatorModelName)
     */
    @Test
    void testConstructorWhenIsCreatedWithValidParametersShouldNotBeNull() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller;

        // Act
        actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Assert
        assertNotNull(actuatorOfBlindRoller, "The actuator should not be null");
    }

    /**
     * Tests the constructor of ActuatorOfBlindRoller when it is created with invalid device id
     * Should throw IllegalArgumentException
     * Constructor: ActuatorOfBlindRoller(DeviceId deviceId, ActuatorModelName actuatorModelName)
     */
    @Test
    void testConstructorWhenIsCreatedWithInvalidDeviceIdShouldThrowIllegalArgumentException() {
        // Arrange
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        DeviceId invalidDeviceId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfBlindRoller(
                        invalidDeviceId, actuatorModelName),
                "Should throw IllegalArgumentException");

    }

    /**
     * Tests the constructor of ActuatorOfBlindRoller when it is created with invalid actuator model name
     * Should throw IllegalArgumentException
     * Constructor: ActuatorOfBlindRoller(DeviceId deviceId, ActuatorModelName actuatorModelName)
     */
    @Test
    void testConstructorWhenIsCreatedWithInvalidActuatorModelNameShouldThrowIllegalArgumentException() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName invalidActuatorModelName = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfBlindRoller(
                deviceId, invalidActuatorModelName), "Should throw IllegalArgumentException");
    }


    /**
     * Tests the constructor of ActuatorOfBlindRoller when it is created with valid parameters
     * Should not be null
     * Constructor: ActuatorOfBlindRoller(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName)
     */
    @Test
    void testConstructorWithActuatorIdWhenIsCreatedWithValidParametersShouldNotBeNull() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorIdDouble = mock(ActuatorId.class);
        ActuatorOfBlindRoller actuatorOfBlindRoller;

        // Act
        actuatorOfBlindRoller = new ActuatorOfBlindRoller(actuatorIdDouble, deviceId, actuatorModelName);

        // Assert
        assertNotNull(actuatorOfBlindRoller, "The actuator should not be null");
    }

    /**
     * Tests the constructor of ActuatorOfBlindRoller when it is created with invalid device id
     * Should throw IllegalArgumentException
     * Constructor: ActuatorOfBlindRoller(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName)
     */
    @Test
    void testConstructorWithActuatorIdWhenIsCreatedWithInvalidDeviceIdShouldThrowIllegalArgumentException() {
        // Arrange
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        DeviceId invalidDeviceId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfBlindRoller(actuatorId,
                        invalidDeviceId, actuatorModelName),
                "Should throw IllegalArgumentException");

    }

    /**
     * Tests the constructor of ActuatorOfBlindRoller when it is created with invalid actuator model name
     * Should throw IllegalArgumentException
     * Constructor: ActuatorOfBlindRoller(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName)
     */
    @Test
    void testConstructorWithActuatorIdWhenIsCreatedWithInvalidActuatorModelNameShouldThrowIllegalArgumentException() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorModelName invalidActuatorModelName = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfBlindRoller(actuatorId,
                        deviceId, invalidActuatorModelName),
                "Should throw IllegalArgumentException");
    }

    /**
     * Tests the constructor of ActuatorOfBlindRoller when it is created with invalid actuator id
     * Should throw IllegalArgumentException
     * Constructor: ActuatorOfBlindRoller(ActuatorId actuatorId, DeviceId deviceId, ActuatorModelName actuatorModelName)
     */
    @Test
    void testConstructorWithActuatorIdWhenIsCreatedWithInvalidActuatorIdShouldThrowIllegalArgumentException() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId invalidActuatorId = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorOfBlindRoller(invalidActuatorId,
                        deviceId, actuatorModelName),
                "Should throw IllegalArgumentException");
    }


    /**
     * Tests the getIdentity method of ActuatorOfBlindRoller
     * Should return the actuator id
     */
    @Test
    void testGetIdentityWhenMethodIsCalled() {
        ActuatorId actuatorId = new ActuatorId("11");
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(actuatorId, deviceId, actuatorModelName);

        //Act
        ActuatorId actuatorIdResult = actuatorOfBlindRoller.getIdentity();

        //Assert
        assertEquals(actuatorId, actuatorIdResult, "The actuator id should be the same");
    }

    /**
     * Tests the getActuatorModelName method of ActuatorOfBlindRoller
     * Should return the actuator model name
     */
    @Test
    void testGetActuatorModelNameWhenMethodIsCalled() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        ActuatorModelName result = actuatorOfBlindRoller.getActuatorModelName();

        // Assert
        assertEquals(actuatorModelName, result, "The actuator model name should be the same");
    }

    /**
     * Tests the getDeviceId method of ActuatorOfBlindRoller
     * Should return the device id
     */
    @Test
    void testGetDeviceIdWhenMethodIsCalled() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        DeviceId result = actuatorOfBlindRoller.getDeviceId();

        // Assert
        assertEquals(deviceId, result, "The device id should be the same");
    }

    /**
     * Tests the operate method of ActuatorOfBlindRoller when the value is between 0 and 100
     * Should return the value
     */
    @Test
    void testOperateWhenValueIsBetween0And100ShouldReturnTheValue() {
        // Arrange
        Value value = new ScalePercentageValue(10.0);
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        Value result = actuatorOfBlindRoller.operate(value);

        // Assert
        assertEquals(value, result, "The value should be the same");
    }

    /**
     * Tests the operate method of ActuatorOfBlindRoller when the value is 0
     * Should return the value
     */
    @Test
    void testOperateBoundaryTestWhenValue0ShouldReturnTheValue() {
        // Arrange
        Value value = new ScalePercentageValue(0.0);
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);


        // Act
        Value result = actuatorOfBlindRoller.operate(value);

        // Assert
        assertEquals(value, result, "The value should be the same");
    }

    /**
     * Tests the operate method of ActuatorOfBlindRoller when the value is 100
     * Should return the value
     */
    @Test
    void testOperateBoundaryTestWhenValue100ShouldReturnTheValue() {
        // Arrange
        Value value = new ScalePercentageValue(100.0);
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        Value result = actuatorOfBlindRoller.operate(value);

        // Assert
        assertEquals(value, result, "The value should be the same");
    }

    /**
     * Tests the operate method of ActuatorOfBlindRoller when the value is less than 0
     * Should return null
     */
    @Test
    void testOperateWhenValueIsLessThan0ShouldReturnNull() {
        // Arrange
        Value value = new ScalePercentageValue(-1.0);
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        Value result = actuatorOfBlindRoller.operate(value);

        // Assert
        assertNull(result, "The value should be null");
    }

    /**
     * Tests the operate method of ActuatorOfBlindRoller when the value is greater than 100
     * Should return null
     */
    @Test
    void testOperateWhenValueIsGreaterThan100ShouldReturnNull() {
        // Arrange
        Value value = new ScalePercentageValue(101.0);
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        Value result = actuatorOfBlindRoller.operate(value);

        // Assert
        assertNull(result, "The value should be null");
    }

    /**
     * Tests equals method for the same object. Should return true
     */

    @Test
    void testEqualsWhenSameObjectShouldReturnTrue() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        boolean result = actuatorOfBlindRoller.equals(actuatorOfBlindRoller);

        // Assert
        assertTrue(result, "The objects should be the same");
    }

    /**
     * Test equals for Null object. Should return false
     */
    @Test
    void testEqualsWhenNullObjectShouldReturnFalse() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        boolean result = actuatorOfBlindRoller.equals(null);

        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Tests equals method for different class objects. Should return false
     */
    @Test
    void testEqualsWhenDifferentClassObjectsShouldReturnFalse() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);
        Object object = new Object();

        // Act
        boolean result = actuatorOfBlindRoller.equals(object);

        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Tests equals method for different actuator id. Should return false
     */
    @Test
    void testEqualsWhenDifferentActuatorIdShouldReturnFalse() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);
        ActuatorOfBlindRoller actuatorOfBlindRoller2 = new ActuatorOfBlindRoller(deviceId, actuatorModelName);

        // Act
        boolean result = actuatorOfBlindRoller.equals(actuatorOfBlindRoller2);

        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Tests equals method for equal actuator id. Should return true
     */
    @Test
    void testEqualsWhenEqualActuatorIdShouldReturnTrue() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorId actuatorId = new ActuatorId("1");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(actuatorId, deviceId, actuatorModelName);
        ActuatorOfBlindRoller actuatorOfBlindRoller2 = new ActuatorOfBlindRoller(actuatorId, deviceId, actuatorModelName);

        // Act
        boolean result = actuatorOfBlindRoller.equals(actuatorOfBlindRoller2);

        // Assert
        assertTrue(result, "The objects should be different");
    }

    /**
     * Tests the hashCode method when comparing two equal objects
     * Should return the same hash code
     */
    @Test
    void testHashCodeWhenComparingTwoEqualObjectsShouldReturnTheSameHashCode() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("1");
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(actuatorId, deviceId, actuatorModelName);
        ActuatorOfBlindRoller actuatorOfBlindRoller2 = new ActuatorOfBlindRoller(actuatorId, deviceId, actuatorModelName);

        // Act
        int hashCode1 = actuatorOfBlindRoller.hashCode();
        int hashCode2 = actuatorOfBlindRoller2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2, "The hash code should be the same");
    }

    /**
     * Tests the hashCode method when comparing two different objects
     * Should return different hash codes
     */
    @Test
    void testHashCodeWhenComparingTwoDifferentObjectsShouldReturnDifferentHashCodes() {
        // Arrange
        DeviceId deviceId = new DeviceId("1");
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuatorModelName");
        ActuatorModelName actuatorModelName2 = new ActuatorModelName("actuatorModelName2");
        ActuatorOfBlindRoller actuatorOfBlindRoller = new ActuatorOfBlindRoller(deviceId, actuatorModelName);
        ActuatorOfBlindRoller actuatorOfBlindRoller2 = new ActuatorOfBlindRoller(deviceId, actuatorModelName2);

        // Act
        int hashCode1 = actuatorOfBlindRoller.hashCode();
        int hashCode2 = actuatorOfBlindRoller2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hash code should be different");
    }
    

}