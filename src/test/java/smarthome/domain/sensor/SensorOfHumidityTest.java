package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.HumidityValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Tests for the SensorOfHumidity class
 */
class SensorOfHumidityTest {

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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfHumidity(invalidDeviceId, sensorModelName)
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfHumidity(deviceId, invalidSensorModelName)
                , "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithoutSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act & Assert
        assertDoesNotThrow(() -> new SensorOfHumidity(deviceId, sensorModelName)
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfHumidity(invalidSensorId, deviceId, sensorModelName)
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfHumidity(sensorId, deviceId, invalidSensorModelName)
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfHumidity(sensorId, invalidDeviceId, sensorModelName)
                , "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act & Assert
        assertDoesNotThrow(() -> new SensorOfHumidity(sensorId, deviceId, sensorModelName)
                , "The constructor should not throw an exception");
    }

    /**
     * Test that the getIdentity method returns the correct sensor id
     * The method should return the sensor id
     */
    @Test
    void testGetIdentityReturnsCorrectSensorId() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(sensorId, deviceId, sensorModelName);
        //Act
        SensorId result = sensorOfHumidityInstance.getIdentity();
        //Assert
        assertEquals(sensorId, result
                , "The method should return the sensor id");
    }

    /**
     * Test that the getDeviceId method returns the correct device id
     * The method should return the device id
     */
    @Test
    void testGetDeviceIdReturnsCorrectDeviceId() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(deviceId, sensorModelName);
        //Act
        DeviceId result = sensorOfHumidityInstance.getDeviceId();
        //Assert
        assertEquals(deviceId, result
                , "The method should return the device id");
    }

    /**
     * Test that the getSensorModelName method returns the correct sensor model name
     * The method should return the sensor model name
     */
    @Test
    void testGetSensorModelNameReturnsCorrectSensorModelName() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(deviceId, sensorModelName);
        //Act
        SensorModelName result = sensorOfHumidityInstance.getSensorModelName();
        //Assert
        assertEquals(sensorModelName, result
                , "The method should return the sensor model name");
    }

    /**
     * Test that the getValue method returns the correct value
     * The method should return the correct value
     */
    @Test
    void testGetValueReturnsCorrectValue() {
        //Arrange
        HumidityValue defaultValue = new HumidityValue(27.0);
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(deviceId, sensorModelName);
        //Act
        Value result = sensorOfHumidityInstance.getValue();
        //Assert
        assertEquals(defaultValue, result
                , "The method should return the correct value");
    }

    /**
     * Test that the equals method returns true for the same object
     * The method should return true
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfHumidityInstance.equals(sensorOfHumidityInstance);
        //Assert
        assertTrue(result
                , "The method should return true");
    }

    /**
     * Test that the equals method returns false for a null object
     * The method should return false
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfHumidityInstance.equals(null);
        //Assert
        assertFalse(result
                , "The method should return false");
    }

    /**
     * Test that the equals method returns false for a different object
     * The method should return false
     */
    @Test
    void testEqualsForDifferentObject() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfHumidityInstance.equals(new Object());
        //Assert
        assertFalse(result
                , "The method should return false");
    }

    /**
     * Test that the equals method returns true for the same SensorId
     * The method should return true
     */
    @Test
    void testEqualsForSameSensorId() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance1 = new SensorOfHumidity(sensorId, deviceId, sensorModelName);
        SensorOfHumidity sensorOfHumidityInstance2 = new SensorOfHumidity(sensorId, deviceId, sensorModelName);
        //Act
        boolean result = sensorOfHumidityInstance1.equals(sensorOfHumidityInstance2);
        //Assert
        assertTrue(result
                , "The method should return true");
    }

    /**
     * Test that the equals method returns false for different SensorId
     * The method should return false
     */
    @Test
    void testEqualsForDifferentSensorId() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance1 = new SensorOfHumidity(deviceId, sensorModelName);
        SensorOfHumidity sensorOfHumidityInstance2 = new SensorOfHumidity(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfHumidityInstance1.equals(sensorOfHumidityInstance2);
        //Assert
        assertFalse(result
                , "The method should return false");
    }

    /**
     * Test hashCode method returns the correct hash code
     * The method should return the correct hash code
     */
    @Test
    void testHashCodeForSameSensorId() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(sensorId, deviceId, sensorModelName);
        //Act & Assert
        assertEquals(sensorOfHumidityInstance.hashCode(), sensorOfHumidityInstance.getIdentity().hashCode()
                , "The method should return the correct hash code");
    }

    /**
     * Test that the hashCode method of SensorOfHumidity returns the same hash code as its SensorId.
     * This ensures that the hashCode method of SensorOfHumidity incorporates the hash code of its SensorId.
     */
    @Test
    void testHashCodeForDifferentSensorId() {
        //Arrange
        SensorId anotherSensorId = new SensorId("another sensor ID");
        SensorOfHumidity sensorOfHumidityInstance1 = new SensorOfHumidity(sensorId, deviceId, sensorModelName);
        SensorOfHumidity sensorOfHumidityInstance2 = new SensorOfHumidity(anotherSensorId, deviceId, sensorModelName);
        //Act & Assert
        assertNotEquals(sensorOfHumidityInstance1.hashCode(), sensorOfHumidityInstance2.hashCode()
                , "The method should return different hash codes");
    }

    /**
     * Test that the hashCode method of SensorOfHumidity returns the same hash code for two identical instances.
     * This ensures that the hashCode method of SensorOfHumidity behaves consistently for identical instances.
     */
    @Test
    void testHashCodeForSameSensorOfHumidity() {
        // Arrange
        SensorOfHumidity sensorOfHumidityInstance1 = new SensorOfHumidity(sensorId, deviceId, sensorModelName);
        SensorOfHumidity sensorOfHumidityInstance2 = new SensorOfHumidity(sensorId, deviceId, sensorModelName);
        // Act
        int hashCode1 = sensorOfHumidityInstance1.hashCode();
        int hashCode2 = sensorOfHumidityInstance2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2
                , "The method should return the same hash code for identical instances");
    }

    /**
     * Test hashCode method returns the same hash code for identical sensors of humidity
     * The method should return the same hash code
     */
    @Test
    void testHashCodeConsistency() {
        //Arrange
        SensorOfHumidity sensorOfHumidityInstance = new SensorOfHumidity(deviceId, sensorModelName);
        //Act & Assert
        assertEquals(sensorOfHumidityInstance.hashCode(), sensorOfHumidityInstance.hashCode()
                , "The method should return the same hash code");
    }
}