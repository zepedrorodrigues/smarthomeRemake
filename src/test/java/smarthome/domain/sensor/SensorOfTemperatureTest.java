package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.TemperatureValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Tests for the SensorOfTemperature class
 */
class SensorOfTemperatureTest {

    SensorId sensorId;
    DeviceId deviceId;
    SensorModelName sensorModelName;

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
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act - Assert
        assertDoesNotThrow(() -> new SensorOfTemperature(sensorId, deviceId, sensorModelName),
                "The constructor should not throw an exception");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithoutSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act - Assert
        assertDoesNotThrow(() -> new SensorOfTemperature(deviceId, sensorModelName),
                "The constructor should not throw an exception");
    }

    /**
     * Test that the constructor throws an exception when the sensor id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorIdIsNull() {
        //Arrange
        SensorId invalidSensorId = null;

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfTemperature(invalidSensorId, deviceId,
                        sensorModelName),
                "The constructor should throw an IllegalArgumentException for a null sensor id");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        DeviceId invalidDeviceId = null;

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfTemperature(sensorId, invalidDeviceId,
                        sensorModelName),
                "The constructor should throw an IllegalArgumentException for a null device id");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        SensorModelName invalidSensorModelName = null;

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfTemperature(sensorId, deviceId,
                        invalidSensorModelName),
                "The constructor should throw an IllegalArgumentException for a null sensor model name");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        DeviceId invalidDeviceId = null;

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfTemperature(invalidDeviceId, sensorModelName),
                "The constructor should throw an IllegalArgumentException for a null device id");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        SensorModelName invalidSensorModelName = null;

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfTemperature(deviceId, invalidSensorModelName),
                "The constructor should throw an IllegalArgumentException for a null sensor model name");
    }

    /**
     * Test that the getIdentity method returns the correct sensor id
     * The method should return the sensor id
     */
    @Test
    void testGetIdentityReturnsCorrectSensorId() {
        //Arrange
        SensorOfTemperature sensorOfTemperature = new SensorOfTemperature(sensorId, deviceId, sensorModelName);

        //Act
        SensorId result = sensorOfTemperature.getIdentity();

        //Assert
        assertEquals(result, sensorId,
                "The getIdentity method should return the sensor id");
    }

    /**
     * Test that the getDeviceId method returns the correct device id
     * The method should return the device id
     */
    @Test
    void testGetDeviceIdReturnsCorrectDeviceId() {
        //Arrange
        SensorOfTemperature sensorOfTemperature = new SensorOfTemperature(deviceId, sensorModelName);

        //Act
        DeviceId result = sensorOfTemperature.getDeviceId();

        //Assert
        assertEquals(result, deviceId,
                "The getDeviceId method should return the device id");
    }

    /**
     * Test that the getSensorModelName method returns the correct sensor model name
     * The method should return the sensor model name
     */
    @Test
    void testGetSensorModelNameReturnsCorrectSensorModelName() {
        //Arrange
        SensorOfTemperature sensorOfTemperature = new SensorOfTemperature(deviceId, sensorModelName);

        //Act
        SensorModelName result = sensorOfTemperature.getSensorModelName();

        //Assert
        assertEquals(result, sensorModelName,
                "The getSensorModelName method should return the sensor model name");
    }

    /**
     * Test that the getValue method returns the correct default value
     * The method should return the correct value
     */
    @Test
    void testGetValueReturnsCorrectValue() {
        //Arrange
        TemperatureValue defaultValue = new TemperatureValue(25.0);
        SensorOfTemperature sensorOfTemperature = new SensorOfTemperature(deviceId, sensorModelName);

        //Act
        TemperatureValue result = (TemperatureValue) sensorOfTemperature.getValue();

        //Assert
        assertEquals(result, defaultValue,
                "The getValue method should return the correct value");

    }

    /**
     * Test that the equals method returns true for the same object
     * The method should return true
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        SensorOfTemperature sensorOfTemperature = new SensorOfTemperature(deviceId, sensorModelName);

        //Act - Assert
        assertEquals(sensorOfTemperature, sensorOfTemperature,
                "The equals method should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object
     * The method should return false
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorOfTemperature sensorOfTemperature = new SensorOfTemperature(deviceId, sensorModelName);
        SensorOfTemperature nullSensorOfTemperature = null;

        //Act - Assert
        assertNotEquals(sensorOfTemperature, nullSensorOfTemperature,
                "The equals method should return false for a null object");
    }

    /**
     * Test that the equals method returns false for a different object
     * The method should return false
     */
    @Test
    void testEqualsForDifferentObject() {
        //Arrange
        SensorOfTemperature sensorOfTemperature = new SensorOfTemperature(deviceId, sensorModelName);

        //Act - Assert
        assertNotEquals(sensorOfTemperature, new Object(),
                "The equals method should return false for a different object");
    }

    /**
     * Test that the equals method returns true for same SensorId
     * The method should return true
     */
    @Test
    void testEqualsForSameSensorId() {
        //Arrange
        SensorOfTemperature sensorOfTemperature1 = new SensorOfTemperature(sensorId, deviceId, sensorModelName);
        SensorOfTemperature sensorOfTemperature2 = new SensorOfTemperature(sensorId, deviceId, sensorModelName);

        //Act - Assert
        assertEquals(sensorOfTemperature1, sensorOfTemperature2,
                "The equals method should return true for same SensorId");
    }


    /**
     * Test that the equals method returns false for different SensorId
     * The method should return false
     */
    @Test
    void testEqualsForDifferentSensorId() {
        //Arrange
        SensorOfTemperature sensorOfTemperature1 = new SensorOfTemperature(deviceId, sensorModelName);
        SensorOfTemperature sensorOfTemperature2 = new SensorOfTemperature(deviceId, sensorModelName);

        //Act - Assert
        assertNotEquals(sensorOfTemperature1, sensorOfTemperature2,
                "The equals method should return false for different SensorId");
    }

    /**
     * Test that the hashCode method returns the same hash code for identical sensors of temperature
     */
    @Test
    void testHashCodeForSameSensorOfTemperature() {
        // Arrange
        SensorOfTemperature sensorOfTemperature1 = new SensorOfTemperature(sensorId, deviceId, sensorModelName);
        SensorOfTemperature sensorOfTemperature2 = new SensorOfTemperature(sensorId, deviceId, sensorModelName);

        // Act
        int hashCode1 = sensorOfTemperature1.hashCode();
        int hashCode2 = sensorOfTemperature2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The hashCode method should return the same hash code for identical sensors of temperature");
    }

    /**
     * Test that the hashCode method returns a different hash code for different sensors of temperature
     */
    @Test
    void testHashCodeForDifferentSensorId() {
        // Arrange
        SensorId anotherSensorId = new SensorId("sensorID2");
        SensorOfTemperature sensorOfTemperature1 = new SensorOfTemperature(sensorId, deviceId, sensorModelName);
        SensorOfTemperature sensorOfTemperature2 = new SensorOfTemperature(anotherSensorId, deviceId, sensorModelName);

        // Act
        int hashCode1 = sensorOfTemperature1.hashCode();
        int hashCode2 = sensorOfTemperature2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return a different hash code for different sensors of temperature");
    }
}