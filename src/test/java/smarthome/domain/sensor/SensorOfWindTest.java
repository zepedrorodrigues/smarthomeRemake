package smarthome.domain.sensor;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.WindValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the SensorOfWind class.
 * Each method in this class is a test case for a specific functionality of the SensorOfWind class.
 */
class SensorOfWindTest {

    SensorId sensorId;
    DeviceId deviceId;
    SensorModelName sensorModelName;

    @BeforeEach
    void setUp(){
        this.sensorId = new SensorId("sensorId");
        this.deviceId = new DeviceId("deviceId");
        this.sensorModelName = new SensorModelName("sensorModelName");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        // Act & Assert
        assertDoesNotThrow(() -> new SensorOfWind(sensorId, deviceId, sensorModelName), "Constructor with sensor id " +
                "should not throw an exception when all parameters are valid ");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithoutSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        // Act & Assert
        assertDoesNotThrow(() -> new SensorOfWind(deviceId, sensorModelName), "Constructor without sensor id should " +
                "not throw an exception when all parameters are valid");
    }

    /**
     * Test that the constructor throws an exception when the sensor id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorIdIsNull() {
        // Arrange
        SensorId invalidSensorId = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfWind(invalidSensorId, deviceId,
                sensorModelName), "Constructor with sensor id should throw an exception when sensor id is null");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        // Arrange
        DeviceId invalidDeviceId = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfWind(sensorId, invalidDeviceId,
                sensorModelName), "Constructor with sensor id should throw an exception when device id is null");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        // Arrange
        DeviceId invalidDeviceId = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfWind(invalidDeviceId, sensorModelName),
                "Constructor without sensor id should throw an exception when device id is null");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     */

    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        // Arrange
        SensorModelName invalidSensorModelName = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfWind(sensorId, deviceId,
                invalidSensorModelName), "Constructor with sensor id should throw an exception when sensor model name" +
                " is null");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        // Arrange
        SensorModelName invalidSensorModelName = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfWind(deviceId, invalidSensorModelName),
                "Constructor without sensor id should throw an exception when sensor model name is null");
    }

    /**
     * Test that the getIdentity method returns the sensor id
     * The method should return the sensor id
     */

    @Test
    void testGetIdentityReturnsSensorId() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(sensorId, deviceId, sensorModelName);
        // Act
        SensorId result = sensorOfWind.getIdentity();
        // Assert
        assertEquals(sensorId, result, "The getIdentity method should return the sensor id");
    }

    /**
     * Test that the getDeviceId method returns the device id
     * The method should return the device id
     */
    @Test
    void testGetDeviceIdReturnsDeviceId() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(sensorId, deviceId, sensorModelName);
        // Act
        DeviceId result = sensorOfWind.getDeviceId();
        // Assert
        assertEquals(deviceId, result, "The getDeviceId method should return the device id");
    }

    /**
     * Test that the getSensorModelName method returns the sensor model name
     * The method should return the sensor model name
     */
    @Test
    void testGetSensorModelNameReturnsSensorModelName() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(sensorId, deviceId, sensorModelName);
        // Act
        SensorModelName result = sensorOfWind.getSensorModelName();
        // Assert
        assertEquals(result, sensorModelName, "The getSensorModelName method should return the sensor model name");
    }

    /**
     * Test that the getValue method returns the correct value
     * The method should return the value
     */

    @Test
    void testGetValueReturnsCorrectValue() {
        // Arrange
        WindValue defaultValue =  new WindValue(Math.PI/2, 25);
        SensorOfWind sensorOfWind = new SensorOfWind (deviceId, sensorModelName);
        // Act
        WindValue result = (WindValue) sensorOfWind.getValue();
        // Assert
        assertEquals(result, defaultValue, "The getValue method should return the value");
    }

    /**
     * Test that the equals method returns true for the same object
     * The method should return true
     */
    @Test
    void testEqualsForSameObjectReturnsTrue() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(deviceId, sensorModelName);
        // Act
        boolean result = sensorOfWind.equals(sensorOfWind);
        // Assert
        assertTrue(result, "The equals method should return true for the same object");
    }

    /**
     * Test that the equals method returns false for different object
     * The method should return false
     */
    @Test
    void testEqualsForDifferentObjectReturnsFalse() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(deviceId, sensorModelName);
        // Act
        boolean result = sensorOfWind.equals(new Object());
        // Assert
        assertFalse(result, "The equals method should return false for different object");
    }

    /**
     * Test that the equals method returns false for null object
     * The method should return false
     */
    @Test
    void testEqualsForNullObjectReturnsFalse() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(deviceId, sensorModelName);
        // Act
        boolean result = sensorOfWind.equals(null);
        // Assert
        assertFalse(result, "The equals method should return false for null object");
    }

    /**
     * Test that the equals method returns true for the same sensor id
     * The method should return true
     */

    @Test
    void testEqualsForSameSensorIdReturnsTrue() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(sensorId, deviceId, sensorModelName);
        SensorOfWind sensorOfWind2 = new SensorOfWind(sensorId, deviceId, sensorModelName);
        // Act
        boolean result = sensorOfWind.equals(sensorOfWind2);
        // Assert
        assertTrue(result, "The equals method should return true for two SensorOfWind objects with the same sensor id");
    }

    /**
     * Test that the equals method returns false for different sensor id
     * The method should return false
     */

    @Test
    void testEqualsForDifferentSensorIdReturnsFalse() {
        // Arrange
        SensorId anotherSensorId = new SensorId("anotherSensorId");
        SensorOfWind sensorOfWind = new SensorOfWind(sensorId, deviceId, sensorModelName);
        SensorOfWind sensorOfWind2 = new SensorOfWind(anotherSensorId, deviceId, sensorModelName);
        // Act
        boolean result = sensorOfWind.equals(sensorOfWind2);
        // Assert
        assertFalse(result, "The equals method should return false for two SensorOfWind objects with different sensor" +
                " id");
    }

    /**
     * Test that the hashCode method returns the same value for the same sensor of wind
     * The method should return the same value
     */
    @Test
    void testHashCodeForSameSensorOfWind() {
        // Arrange
        SensorOfWind sensorOfWind = new SensorOfWind(sensorId, deviceId, sensorModelName);
        SensorOfWind sensorOfWind2 = new SensorOfWind(sensorId, deviceId, sensorModelName);
        // Act
        int result1 = sensorOfWind.hashCode();
        int result2 = sensorOfWind2.hashCode();
        // Assert
        assertEquals(result1, result2, "The hashCode method should return the same value for the same sensor of wind");
    }

    /**
     * Test that the hashCode method returns the same value for the same sensor of wind
     * The method should return the same value
     */
    @Test
    void testHashCodeForDifferentSensorOfWind() {
        // Arrange
        SensorId anotherSensorId = new SensorId("anotherSensorId");
        SensorOfWind sensorOfWind = new SensorOfWind(sensorId, deviceId, sensorModelName);
        SensorOfWind sensorOfWind2 = new SensorOfWind(anotherSensorId, deviceId, sensorModelName);
        // Act
        int result1 = sensorOfWind.hashCode();
        int result2 = sensorOfWind2.hashCode();
        // Assert
        assertNotEquals(result1, result2, "The hashCode method should return different values for different sensor of" +
                " wind");
    }
}