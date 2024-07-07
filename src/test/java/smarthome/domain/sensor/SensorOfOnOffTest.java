package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.OnOffValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Tests for the SensorOfOnOff class
 */
class SensorOfOnOffTest {

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
        assertDoesNotThrow(() -> new SensorOfOnOff(sensorId, deviceId, sensorModelName),
                "The constructor should not throw an exception when all parameters are valid");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithoutSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act - Assert
        assertDoesNotThrow(() -> new SensorOfOnOff(deviceId, sensorModelName),
                "The constructor should not throw an exception when all parameters are valid");
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfOnOff(invalidSensorId, deviceId,
                        sensorModelName),
                "The constructor should throw an IllegalArgumentException when the sensor id is null");
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfOnOff(sensorId, invalidDeviceId,
                        sensorModelName),
                "The constructor should throw an IllegalArgumentException when the device id is null");
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfOnOff(sensorId, deviceId,
                        invalidSensorModelName),
                "The constructor should throw an IllegalArgumentException when the sensor model name is null");
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfOnOff(invalidDeviceId, sensorModelName),
                "The constructor should throw an IllegalArgumentException when the device id is null");
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
        assertThrows(IllegalArgumentException.class, () -> new SensorOfOnOff(deviceId, invalidSensorModelName),
                "The constructor should throw an IllegalArgumentException when the sensor model name is null");
    }

    /**
     * Test that the getIdentity method returns the correct sensor id
     * The method should return the sensor id
     */
    @Test
    void testGetIdentityReturnsCorrectSensorId() {
        //Arrange
        SensorOfOnOff sensorOfOnOff = new SensorOfOnOff(sensorId, deviceId, sensorModelName);

        //Act
        SensorId result = sensorOfOnOff.getIdentity();

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
        SensorOfOnOff sensorOfOnOff = new SensorOfOnOff(deviceId, sensorModelName);

        //Act
        DeviceId result = sensorOfOnOff.getDeviceId();

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
        SensorOfOnOff sensorOfOnOff = new SensorOfOnOff(deviceId, sensorModelName);

        //Act
        SensorModelName result = sensorOfOnOff.getSensorModelName();

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
        OnOffValue defaultValue = new OnOffValue(false);
        SensorOfOnOff sensorOfOnOff = new SensorOfOnOff(deviceId, sensorModelName);

        //Act
        OnOffValue result = (OnOffValue) sensorOfOnOff.getValue();

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
        SensorOfOnOff sensorOfOnOff = new SensorOfOnOff(deviceId, sensorModelName);

        //Act - Assert
        assertEquals(sensorOfOnOff, sensorOfOnOff,
                "The equals method should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object
     * The method should return false
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorOfOnOff sensorOfOnOff = new SensorOfOnOff(deviceId, sensorModelName);
        SensorOfOnOff nullSensorOfOnOff = null;

        //Act - Assert
        assertNotEquals(sensorOfOnOff, nullSensorOfOnOff,
                "The equals method should return false for a null object");
    }

    /**
     * Test that the equals method returns false for a different object
     * The method should return false
     */
    @Test
    void testEqualsForDifferentObject() {
        //Arrange
        SensorOfOnOff sensorOfOnOff = new SensorOfOnOff(deviceId, sensorModelName);

        //Act - Assert
        assertNotEquals(sensorOfOnOff, new Object(),
                "The equals method should return false for a different object");
    }

    /**
     * Test that the equals method returns true for same SensorId
     * The method should return true
     */
    @Test
    void testEqualsForSameSensorId() {
        //Arrange
        SensorOfOnOff sensorOfOnOff1 = new SensorOfOnOff(sensorId, deviceId, sensorModelName);
        SensorOfOnOff sensorOfOnOff2 = new SensorOfOnOff(sensorId, deviceId, sensorModelName);

        //Act - Assert
        assertEquals(sensorOfOnOff1, sensorOfOnOff2,
                "The equals method should return true for the same SensorId");
    }


    /**
     * Test that the equals method returns false for different SensorId
     * The method should return false
     */
    @Test
    void testEqualsForDifferentSensorId() {
        //Arrange
        SensorOfOnOff sensorOfOnOff1 = new SensorOfOnOff(deviceId, sensorModelName);
        SensorOfOnOff sensorOfOnOff2 = new SensorOfOnOff(deviceId, sensorModelName);

        //Act - Assert
        assertNotEquals(sensorOfOnOff1, sensorOfOnOff2,
                "The equals method should return false for different SensorId");
    }

    /**
     * Test that the hashCode method returns the same hash code for identical sensors of temperature
     */
    @Test
    void testHashCodeForSameSensorOfTemperature() {
        // Arrange
        SensorOfOnOff sensorOfOnOff1 = new SensorOfOnOff(sensorId, deviceId, sensorModelName);
        SensorOfOnOff sensorOfOnOff2 = new SensorOfOnOff(sensorId, deviceId, sensorModelName);

        // Act
        int hashCode1 = sensorOfOnOff1.hashCode();
        int hashCode2 = sensorOfOnOff2.hashCode();

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
        SensorOfOnOff sensorOfOnOff1 = new SensorOfOnOff(sensorId, deviceId, sensorModelName);
        SensorOfOnOff sensorOfOnOff2 = new SensorOfOnOff(anotherSensorId, deviceId, sensorModelName);

        // Act
        int hashCode1 = sensorOfOnOff1.hashCode();
        int hashCode2 = sensorOfOnOff2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return a different hash code for different sensors of temperature");
    }
}