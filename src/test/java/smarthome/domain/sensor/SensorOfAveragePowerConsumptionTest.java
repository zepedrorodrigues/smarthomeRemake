package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.AveragePowerConsumptionValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Tests for the SensorOfAveragePowerConsumption class
 */
class SensorOfAveragePowerConsumptionTest {
    SensorId sensorId;
    DeviceId deviceId;
    SensorModelName sensorModelName;

    /**
     * Sets up the test environment.
     * Initializes the sensorId, deviceId, and sensorModelName objects.
     */
    @BeforeEach
    void setUp() {
        sensorId = new SensorId("sensorID");
        deviceId = new DeviceId("deviceID");
        sensorModelName = new SensorModelName("sensorModelName");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     * The sensorId is not provided
     */
    @Test
    void testConstructorWithoutSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        // Act - Assert
        assertDoesNotThrow(() -> new SensorOfAveragePowerConsumption(deviceId, sensorModelName),
                "The constructor should not throw an exception when all parameters are valid.");
    }

    /**
     * Test that the constructor doesn't throw an exception when all parameters are valid
     * The constructor should not throw an exception
     *
     */
    @Test
    void testConstructorWithSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act - Assert
        assertDoesNotThrow(() -> new SensorOfAveragePowerConsumption(sensorId, deviceId, sensorModelName),
                "The constructor should not throw an exception when all parameters are valid.");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     * The sensorId is not provided
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionForNullDeviceId() {
        // Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfAveragePowerConsumption(null, sensorModelName)
                , "The constructor should throw an exception when the device id is null.");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     * The sensorId is not provided
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionForNullSensorModelName() {
        // Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfAveragePowerConsumption(deviceId, null),
                "The constructor should throw an exception when the sensor model name is null.");
    }

    /**
     * Test that the constructor throws an exception when the sensor id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionForNullSensorId() {
        // Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfAveragePowerConsumption(null, deviceId,
                sensorModelName), "The constructor should throw an exception when the sensor id is null.");
    }

    /**
     * Test that the constructor throws an exception when the device id is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionForNullDeviceId() {
        // Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfAveragePowerConsumption(sensorId, null,
                sensorModelName), "The constructor should throw an exception when the device id is null.");
    }

    /**
     * Test that the constructor throws an exception when the sensor model name is null
     * The constructor should throw an IllegalArgumentException
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionForNullSensorModelName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfAveragePowerConsumption(sensorId, deviceId,
                null), "The constructor should throw an exception when the sensor model name is null.");
    }

    /**
     * Test that the getIdentity method returns the correct SensorId.
     */
    @Test
    void testGetIdentityShouldReturnCorrectSensorId() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor = new SensorOfAveragePowerConsumption(sensorId, deviceId, sensorModelName);

        // Act
        SensorId result = mySensor.getIdentity();

        //  Assert
        assertEquals(result, sensorId, "The getIdentity method should return the correct SensorId.");
    }

    /**
     * Test that the getSensorModelName method returns the correct SensorModelName.
     */
    @Test
    void testGetSensorModelNameShouldReturnCorrectSensorModelName() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        SensorModelName result = mySensor.getSensorModelName();

        // Assert
        assertEquals(result, sensorModelName,
                "The getSensorModelName method should return the correct SensorModelName.");
    }

    /**
     * Test that the getDeviceId method returns the correct DeviceId.
     */
    @Test
    void testGetDeviceIdShouldReturnCorrectDeviceId() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        DeviceId result = mySensor.getDeviceId();

        // Assert
        assertEquals(result, deviceId, "The getDeviceId method should return the correct DeviceId.");
    }

    /**
     * Test that the getValue method returns the correct value.
     */
    @Test
    void testGetValueShouldReturnCorrectValue() {
        // Arrange
        AveragePowerConsumptionValue value = new AveragePowerConsumptionValue(10.0);
        SensorOfAveragePowerConsumption mySensor = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        AveragePowerConsumptionValue result = (AveragePowerConsumptionValue) mySensor.getValue();

        // Assert
        assertEquals(result, value, "The getValue method should return the correct value.");
    }

    /**
     * Test that the equals method returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        boolean result = mySensor.equals(mySensor);

        // Assert
        assertTrue(result, "The equals method should return true when comparing the same object.");
    }

    /**
     * Test that the equals method returns false for a null object.
     */
    @Test
    void testEqualsFoNullObject() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        boolean result = mySensor.equals(null);

        // Assert
        assertFalse(result, "The equals method should return false for a null object.");
    }

    /**
     * Test that the equals method returns false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        boolean result = mySensor.equals(new Object());

        // Assert
        assertFalse(result, "The equals method should return false for a different object.");
    }

    /**
     * Test that the equals method returns true for the same sensor.
     */
    @Test
    void testEqualsForSameSensorId() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor1 = new SensorOfAveragePowerConsumption(sensorId, deviceId, sensorModelName);
        SensorOfAveragePowerConsumption mySensor2 = new SensorOfAveragePowerConsumption(sensorId, deviceId, sensorModelName);

        // Act
        boolean result = mySensor1.equals(mySensor2);

        // Assert
        assertTrue(result, "The equals method should return true for the same sensor.");
    }

    /**
     * Test that the equals method returns false for different sensors.
     */
    @Test
    void testEqualsForDifferentSensorId() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor1 = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);
        SensorOfAveragePowerConsumption mySensor2 = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        boolean result = mySensor1.equals(mySensor2);

        // Assert
        assertFalse(result, "The equals method should return false for different sensors.");
    }

    /**
     * Test that the hashCode method returns the same hash code for the same sensor.
     */
    @Test
    void testHashCodeForSameSensor() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor1 = new SensorOfAveragePowerConsumption(sensorId, deviceId, sensorModelName);
        SensorOfAveragePowerConsumption mySensor2 = new SensorOfAveragePowerConsumption(sensorId, deviceId, sensorModelName);

        // Act
        int hash1 = mySensor1.hashCode();
        int hash2 = mySensor2.hashCode();

        // Assert
        assertEquals(hash1, hash2, "The hashCode method should return the same hash code for the same sensor.");
    }

    /**
     * Test that the hashCode method returns different hash codes for different sensors.
     */
    @Test
    void testHashCodeForDifferentSensor() {
        // Arrange
        SensorOfAveragePowerConsumption mySensor1 = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);
        SensorOfAveragePowerConsumption mySensor2 = new SensorOfAveragePowerConsumption(deviceId, sensorModelName);

        // Act
        int hash1 = mySensor1.hashCode();
        int hash2 = mySensor2.hashCode();

        // Assert
        assertNotEquals(hash1, hash2, "The hashCode method should return different hash codes for different sensors.");
    }
}