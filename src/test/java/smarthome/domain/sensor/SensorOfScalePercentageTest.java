package smarthome.domain.sensor;

import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.ScalePercentageValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit Tests for the SensorOfScalePercentage class
 */
class SensorOfScalePercentageTest {


    /**
     * Test that the constructor throws an exception when the device id is null
     * Constructor without SensorId.
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfScalePercentage(
                        null, sensorModelName),
                "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * Constructor without SensorId.
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        DeviceId deviceId = new DeviceId("deviceId");

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfScalePercentage(deviceId,
                null), "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * Constructor without SensorId.
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorDoesntThrowExceptionWhenAllParametersAreValid() {
        //Arrange
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");

        //Act - Assert
        assertDoesNotThrow(() -> new SensorOfScalePercentage(deviceId, sensorModelName),
                "The constructor should not throw an exception");
    }


    /**
     * Test that the constructor throws an exception when the device id is null
     * Constructor with SensorId.
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfScalePercentage(
                        sensorId, null, sensorModelName),
                "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * Constructor with SensorId.
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfScalePercentage(
                        sensorId, deviceId, null),
                "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor throws an exception when the sensor id is null
     * Constructor with SensorId.
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorIdIsNull() {
        //Arrange
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");

        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfScalePercentage(
                        null, deviceId, sensorModelName),
                "The constructor should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid.
     * Constructor with SensorId.
     * The constructor should not throw an exception
     */
    @Test
    void testConstructorWithSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");

        //Act - Assert
        assertDoesNotThrow(() -> new SensorOfTemperature(sensorId, deviceId, sensorModelName),
                "The constructor should not throw an exception");
    }


    /**
     * Test that the getIdentity method returns the correct sensor id
     * The method should return the sensor id
     */
    @Test
    void testGetIdentityReturnsCorrectSensorId() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(
                sensorId, deviceId, sensorModelName);

        //Act
        SensorId result = sensorOfScalePercentage.getIdentity();

        //Assert
        assertEquals(sensorId, result, "The method should return the sensor id");
    }

    /**
     * Test that the getDeviceId method returns the correct device id
     * The method should return the device id
     */
    @Test
    void testGetDeviceIdReturnsCorrectDeviceId() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(
                sensorId, deviceId, sensorModelName);

        //Act
        DeviceId deviceIdResult = sensorOfScalePercentage.getDeviceId();

        //Assert
        assertEquals(deviceId, deviceIdResult,
                "The method should return the device id");

    }

    /**
     * Test that the getSensorModelName method returns the correct sensor model name
     * The method should return the sensor model name
     */
    @Test
    void testGetSensorModelNameReturnsCorrectSensorModelName() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(
                sensorId, deviceId, sensorModelName);

        //Act
        SensorModelName sensorModelNameResult = sensorOfScalePercentage.getSensorModelName();

        //Assert
        assertEquals(sensorModelName, sensorModelNameResult,
                "The method should return the sensor model name");
    }

    /**
     * Test that the getValue method returns the value
     * The method should return the correct value
     */
    @Test
    void testGetValueReturnsCorrectValueShouldReturnCorrectValue() {
        //Arrange
        Double valueExpected = 75.0; // Default value
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(
                sensorId, deviceId, sensorModelName);

        //Act
        ScalePercentageValue valueResultObject = (ScalePercentageValue) sensorOfScalePercentage.getValue();
        Double valueResultDouble = Double.parseDouble(valueResultObject.valueToString());

        //Assert
        assertEquals(valueExpected, valueResultDouble,
                "The method should return the value");
    }

    /**
     * Test that the getValue method returns the value
     * The method should not return null
     */
    @Test
    void testGetValueReturnsCorrectValueShouldNotReturnNull() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(
                sensorId, deviceId, sensorModelName);

        //Act
        ScalePercentageValue valueResultObject = (ScalePercentageValue) sensorOfScalePercentage.getValue();

        //Assert
        assertNotNull(valueResultObject, "The method should return the value");
    }

    /**
     * Test that the equals method returns true for the same object. The method should return true
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(
                sensorId, deviceId, sensorModelName);

        //Act
        boolean result = sensorOfScalePercentage.equals(sensorOfScalePercentage);

        //Assert
        assertTrue(result, "The method should return true");
    }

    /**
     * Test that the equals method returns false for a null object. The method should return false.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);

        //Act
        boolean result = sensorOfScalePercentage.equals(null);

        //Assert
        assertFalse(result, "The method should return false");
    }

    /**
     * Test that the equals method returns false for a different object
     * The method should return false
     */
    @Test
    void testEstEqualsForDifferentObject() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);
        Object object = new Object();

        //Act
        boolean result = sensorOfScalePercentage.equals(object);

        //Assert
        assertFalse(result, "The method should return false");
    }

    /**
     * Test that the equals method returns true for the same SensorId. The method should return true
     */
    @Test
    void testEqualsForSameSensorId() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage1 = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);
        SensorOfScalePercentage sensorOfScalePercentage2 = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);

        //Act
        boolean result = sensorOfScalePercentage1.equals(sensorOfScalePercentage2);

        //Assert
        assertTrue(result, "The method should return true");
    }

    /**
     * Test that the equals method returns false for different SensorId. The method should return false
     */
    @Test
    void testEqualsForDifferentSensorId() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        SensorId sensorId2 = new SensorId("sensorId2");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);
        SensorOfScalePercentage sensorOfScalePercentage2 = new SensorOfScalePercentage(sensorId2, deviceId,
                sensorModelName);

        //Act
        boolean result = sensorOfScalePercentage.equals(sensorOfScalePercentage2);

        //Assert
        assertFalse(result, "The method should return false");
    }

    /**
     * Test that the hashCode method returns the same value for equal objects
     * The method should return the same value
     */
    @Test
    void testHashCodeWhenEqualsObjectsShouldBeTheSame() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);
        SensorOfScalePercentage sensorOfScalePercentage2 = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);

        //Act
        int hashCode1 = sensorOfScalePercentage.hashCode();
        int hashCode2 = sensorOfScalePercentage2.hashCode();

        //Assert
        assertEquals(hashCode1, hashCode2, "The method should return the same value");
    }

    /**
     * Test that the hashCode method returns different values for different objects
     * The method should return different values
     */
    @Test
    void testHashCodeWhenDifferentObjectsShouldBeDifferent() {
        //Arrange
        SensorId sensorId = new SensorId("sensorId");
        SensorId sensorId2 = new SensorId("sensorId2");
        DeviceId deviceId = new DeviceId("deviceId");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        SensorModelName sensorModelName2 = new SensorModelName("sensorModelName2");
        SensorOfScalePercentage sensorOfScalePercentage = new SensorOfScalePercentage(sensorId, deviceId,
                sensorModelName);
        SensorOfScalePercentage sensorOfScalePercentage2 = new SensorOfScalePercentage(sensorId2, deviceId,
                sensorModelName2);

        //Act
        int hashCode1 = sensorOfScalePercentage.hashCode();
        int hashCode2 = sensorOfScalePercentage2.hashCode();

        //Assert
        assertNotEquals(hashCode1, hashCode2,
                "The method should return different values");
    }


}