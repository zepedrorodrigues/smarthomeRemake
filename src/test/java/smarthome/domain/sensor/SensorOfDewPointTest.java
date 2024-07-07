package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.DewPointValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Tests for the SensorOfDewPoint class
 */
class SensorOfDewPointTest {
    
    private SensorId sensorId;
    private DeviceId deviceId;
    private SensorModelName sensorModelName;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        this.sensorId = new SensorId("sensorID");
        this.deviceId = new DeviceId("deviceID");
        this.sensorModelName = new SensorModelName("sensorModelName");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        DeviceId invalidDeviceId = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfDewPoint(invalidDeviceId, sensorModelName)
                , "The constructor should throw an IllegalArgumentException");
    }
    
    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        SensorModelName invalidSensorModelName = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfDewPoint(deviceId, invalidSensorModelName)
                , "The constructor should throw an IllegalArgumentException");
    }
    
    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithoutSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act & Assert
        assertDoesNotThrow(() -> new SensorOfDewPoint(deviceId, sensorModelName)
                , "The constructor should not throw an exception");
    }
    
    /**
     * Test that the constructor throws an exception when the sensor id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorIdIsNull() {
        //Arrange
        SensorId invalidSensorId = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfDewPoint(invalidSensorId, deviceId, sensorModelName)
                , "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        SensorModelName invalidSensorModelName = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfDewPoint(sensorId, deviceId, invalidSensorModelName)
                , "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        DeviceId invalidDeviceId = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfDewPoint(sensorId, invalidDeviceId, sensorModelName)
                , "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act & Assert
        assertDoesNotThrow(() -> new SensorOfDewPoint(sensorId, deviceId, sensorModelName)
                , "The constructor should not throw an exception");
    }

    /**
     * Test that the getIdentity method returns the correct sensor id
     * The method should return the sensor id
     */
    @Test
    void testGetIdentityReturnsCorrectSensorId() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(sensorId, deviceId, sensorModelName);
        //Act
        SensorId result = sensorOfDewPointInstance.getIdentity();
        //Assert
        assertEquals(sensorId, result
                , "The getIdentity method should return the sensor id");
    }

    /**
     * Test that the getDeviceId method returns the correct device id
     * The method should return the device id
     */
    @Test
    void testGetDeviceIdReturnsCorrectDeviceId() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act
        DeviceId result = sensorOfDewPointInstance.getDeviceId();
        //Assert
        assertEquals(deviceId, result
                , "The getDeviceId method should return the device id");
    }

    /**
     * Test that the getSensorModelName method returns the correct sensor model name
     * The method should return the sensor model name
     */
    @Test
    void testGetSensorModelNameReturnsCorrectSensorModelName() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act
        SensorModelName result = sensorOfDewPointInstance.getSensorModelName();
        //Assert
        assertEquals(sensorModelName, result
                , "The getSensorModelName method should return the sensor model name");
    }

    /**
     * Test that the getValue method returns the correct value
     * The method should return the correct value
     */
    @Test
    void testGetValueReturnsCorrectValue() {
        //Arrange
        DewPointValue defaultValue = new DewPointValue(29.0);
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act
        Value result = sensorOfDewPointInstance.getValue();
        //Assert
        assertEquals(defaultValue, result
                , "The getValue method should return the correct value");
    }

    /**
     * Test that the equals method returns true for the same object
     * The method should return true
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfDewPointInstance.equals(sensorOfDewPointInstance);
        //Assert
        assertTrue(result
                , "The equals method should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object
     * The method should return false
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfDewPointInstance.equals(null);
        //Assert
        assertFalse(result
                , "The equals method should return false for a null object");
    }

    /**
     * Test that the equals method returns false for a different object
     * The method should return false
     */
    @Test
    void testEqualsForDifferentObject() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfDewPointInstance.equals(new Object());
        //Assert
        assertFalse(result
                , "The equals method should return false for a different object");
    }

    /**
     * Test that the equals method returns true for the same SensorId
     * The method should return true
     */
    @Test
    void testEqualsForSameSensorId() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance1 = new SensorOfDewPoint(sensorId, deviceId, sensorModelName);
        SensorOfDewPoint sensorOfDewPointInstance2 = new SensorOfDewPoint(sensorId, deviceId, sensorModelName);
        //Act
        boolean result = sensorOfDewPointInstance1.equals(sensorOfDewPointInstance2);
        //Assert
        assertTrue(result
                , "The equals method should return true for the same SensorId");
    }

    /**
     * Test that the equals method returns false for different SensorId
     * The method should return false
     */
    @Test
    void testEqualsForDifferentSensorId() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance1 = new SensorOfDewPoint(deviceId, sensorModelName);
        SensorOfDewPoint sensorOfDewPointInstance2 = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfDewPointInstance1.equals(sensorOfDewPointInstance2);
        //Assert
        assertFalse(result
                , "The equals method should return false for different SensorId");
    }

    /**
     * Test hashCode method returns the correct hash code
     * The method should return the correct hash code
     */
    @Test
    void testHashCodeForSameSensorId() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(sensorId, deviceId, sensorModelName);
        //Act & Assert
        assertEquals(sensorOfDewPointInstance.hashCode(), sensorOfDewPointInstance.getIdentity().hashCode()
                , "The hashCode method should return the correct hash code");
    }

    /**
     * Test that the hashCode method of SensorOfDewPoint returns the same hash code as its SensorId.
     * This ensures that the hashCode method of SensorOfDewPoint incorporates the hash code of its SensorId.
     */
    @Test
    void testHashCodeForDifferentSensorId() {
        //Arrange
        SensorId anotherSensorId = new SensorId("another sensor ID");
        SensorOfDewPoint sensorOfDewPointInstance1 = new SensorOfDewPoint(sensorId, deviceId, sensorModelName);
        SensorOfDewPoint sensorOfDewPointInstance2 = new SensorOfDewPoint(anotherSensorId, deviceId, sensorModelName);
        //Act & Assert
        assertNotEquals(sensorOfDewPointInstance1.hashCode(), sensorOfDewPointInstance2.hashCode()
                , "The hashCode method should return the correct hash code");
    }

    /**
     * Test that the hashCode method of SensorOfDewPoint returns the same hash code for two identical instances.
     * This ensures that the hashCode method of SensorOfDewPoint behaves consistently for identical instances.
     */
    @Test
    void testHashCodeForSameSensorOfDewPoint() {
        // Arrange
        SensorOfDewPoint sensorOfDewPointInstance1 = new SensorOfDewPoint(sensorId, deviceId, sensorModelName);
        SensorOfDewPoint sensorOfDewPointInstance2 = new SensorOfDewPoint(sensorId, deviceId, sensorModelName);
        // Act
        int hashCode1 = sensorOfDewPointInstance1.hashCode();
        int hashCode2 = sensorOfDewPointInstance2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2
                , "The hashCode method should return the same hash code for two identical instances");
    }

    /**
     * Test hashCode method returns the same hash code for identical sensors of dew point
     * The method should return the same hash code
     */
    @Test
    void testHashCodeConsistency() {
        //Arrange
        SensorOfDewPoint sensorOfDewPointInstance = new SensorOfDewPoint(deviceId, sensorModelName);
        //Act & Assert
        assertEquals(sensorOfDewPointInstance.hashCode(), sensorOfDewPointInstance.hashCode()
                , "The hashCode method should return the same hash code");
    }
}