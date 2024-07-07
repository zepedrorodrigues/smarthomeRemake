package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.SolarIrradianceValue;
import smarthome.domain.sensormodel.vo.SensorModelName;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the SensorOfSolarIrradiance class.
 */
class SensorOfSolarIrradianceTest {

    SensorId sensorId;

    DeviceId deviceId;

    SensorModelName sensorModelName;


    /**
     * Initializes the parameters for the tests.
     */
    @BeforeEach
    void setUp() {
        this.sensorId = new SensorId("sensorID");
        this.deviceId = new DeviceId("deviceID");
        this.sensorModelName = new SensorModelName("sensorModelName");
    }

    /**
     * Test that the constructor with SensorId doesn't throw an exception when all parameters are valid.
     */
    @Test
    void testConstructorWithSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act + Assert
        assertDoesNotThrow(() -> new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName), "Constructor " +
                "doesn't throw an exception when all parameters are valid.");
    }

    /**
     * Test that the constructor without SensorId doesn't throw an exception when all parameters are valid.
     */
    @Test
    void testConstructorWithoutSensorIdDoesntThrowExceptionWhenAllParametersAreValid() {
        //Act + Assert
        assertDoesNotThrow(() -> new SensorOfSolarIrradiance(deviceId, sensorModelName), "Constructor doesn't throw " +
                "an exception when all parameters are valid.");
    }

    /**
     * Test that the constructor with SensorId throws an exception when SensorId is null.
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorIdIsNull() {
        //Arrange
        SensorId invalidSensorId = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSolarIrradiance(invalidSensorId, deviceId,
                sensorModelName), "Constructor throws an exception when SensorId is null.");
    }

    /**
     * Test that the constructor with SensorId throws an exception when SensorId is null.
     */
    @Test
    void testConstructorWithSensorModelNameThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        SensorModelName invalidSensorModelName = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSolarIrradiance(deviceId,
                invalidSensorModelName), "Constructor throws an exception when SensorModelName is null.");
    }

    /**
     * Test that the constructor with SensorId throws an exception when DeviceId is null.
     */
    @Test
    void testConstructorWithDeviceIdThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        DeviceId invalidDeviceId = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSolarIrradiance(invalidDeviceId,
                sensorModelName), "Constructor throws an exception when DeviceId is null.");
    }

    /**
     * Test that the constructor without SensorId throws an exception when DeviceId is null.
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Arrange
        DeviceId invalidDeviceId = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSolarIrradiance(invalidDeviceId,
                sensorModelName), "Constructor throws an exception when DeviceId is null.");
    }

    /**
     * Test that the constructor without SensorId throws an exception when SensorModelName is null.
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Arrange
        SensorModelName invalidSensorModelName = null;

        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSolarIrradiance(deviceId,
                invalidSensorModelName), "Constructor throws an exception when SensorModelName is null.");
    }

    /**
     * Test that the constructor throws an exception when all parameters are null.
     */
    @Test
    void testGetIdentityReturnsCorrectSensorId() {
        //Arrange
        SensorOfSolarIrradiance sensor = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);

        //Act
        SensorId result = sensor.getIdentity();

        //Assert
        assertEquals(sensorId, result, "The sensorId is not the expected one.");
    }

    /**
     * Test that the getSensorModelName method returns the correct sensor model name.
     */
    @Test
    void testGetSensorModelNameReturnsCorrectSensorModelName() {
        //Arrange
        SensorOfSolarIrradiance sensor = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);

        //Act
        SensorModelName result = sensor.getSensorModelName();

        //Assert
        assertEquals(sensorModelName, result, "The sensor model name is not the expected one.");
    }

    /**
     * Test that the getDeviceId method returns the correct device id.
     */
    @Test
    void testGetDeviceIdReturnsCorrectDeviceId() {
        //Arrange
        SensorOfSolarIrradiance sensor = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);

        //Act
        DeviceId result = sensor.getDeviceId();

        //Assert
        assertEquals(deviceId, result, "The device id is not the expected one.");
    }

    /**
     * Test that the getValue method returns the correct value.
     */
    @Test
    void testGetValueReturnsCorrectValue() {
        //Arrange
        SolarIrradianceValue defaultValue = new SolarIrradianceValue(1200.0);
        SensorOfSolarIrradiance sensorOfSolarIrradiance = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        SolarIrradianceValue result = (SolarIrradianceValue) sensorOfSolarIrradiance.getValue();
        //Assert
        assertEquals(defaultValue, result, "The value is not the expected one.");

    }

    /**
     * Test that the equals method returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance.equals(sensorOfSolarIrradiance);
        //Assert
        assertTrue(result, "The equals method returns false for the same object.");
    }

    /**
     * Test that the equals method returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance.equals(null);
        //Assert
        assertFalse(result, "The equals method returns true for a null object.");
    }

    /**
     * Test that the equals method returns false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        Object notSensor = new Object();
        //Act
        boolean result = sensorOfSolarIrradiance.equals(notSensor);
        //Assert
        assertFalse(result, "The equals method returns true for a different object.");
    }

    /**
     * Test that the equals method returns true for the same sensorId.
     */
    @Test
    void testEqualsForSameSensorId() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertTrue(result, "The equals method returns false for the same sensorId.");
    }

    /**
     * Test that the equals method returns false for the same sensorModelName.
     */
    @Test
    void testEqualsForDifferentSensorId() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertFalse(result, "The equals method returns true for the same sensorModelName.");
    }

    /**
     * Test that the equals method returns false for the same sensorId and sensorModelName.
     */
    @Test
    void testEqualsForDifferentSensorModelName() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertFalse(result, "The equals method returns true for the same sensorId and sensorModelName.");
    }

    /**
     * Test that the equals method returns false for the same sensorId, sensorModelName and deviceId.
     */
    @Test
    void testEqualsForDifferentDeviceId() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertFalse(result, "The equals method returns true for the same sensorId," + " sensorModelName and deviceId.");
    }

    /**
     * Test that the equals method returns false for the same sensorId, sensorModelName and deviceId.
     */
    @Test
    void testEqualsForDifferentSensorIdAndSensorModelName() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertFalse(result, "The equals method returns true for the same sensorId and sensorModelName.");
    }

    /**
     * Test that the equals method returns false for the same sensorId, sensorModelName and deviceId.
     */
    @Test
    void testEqualsForDifferentSensorIdAndDeviceId() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertFalse(result, "The equals method returns true for the same sensorId and deviceId.");
    }

    /**
     * Test that the equals method returns false for the same sensorId, sensorModelName and deviceId.
     */
    @Test
    void testEqualsForDifferentSensorModelNameAndDeviceId() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertFalse(result, "The equals method returns true for the same sensorModelName and deviceId.");
    }

    /**
     * Test that the equals method returns false for the same sensorId, sensorModelName and deviceId.
     */
    @Test
    void testEqualsForDifferentSensorIdSensorModelNameAndDeviceId() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSolarIrradiance1.equals(sensorOfSolarIrradiance2);
        //Assert
        assertFalse(result, "The equals method returns true for the same sensorId," + " sensorModelName and deviceId.");
    }

    /**
     * Test that the hashCode method returns the same value for the same sensorId.
     */
    @Test
    void testHashCodeForSameSensorOfSolarIrradiance() {
        //Arrange
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);
        //Act
        int result1 = sensorOfSolarIrradiance1.hashCode();
        int result2 = sensorOfSolarIrradiance2.hashCode();
        //Assert
        assertEquals(result1, result2, "The hashCode method returns a different value for the same sensorId.");
    }

    /**
     * Test that the hashCode method returns a different value for different sensorId.
     */
    @Test
    void testHashCodeForDifferentSensorId() {
        //Arrange
        SensorId anotherSensorId = new SensorId("anotherSensorId");
        SensorOfSolarIrradiance sensorOfSolarIrradiance1 = new SensorOfSolarIrradiance(sensorId, deviceId, sensorModelName);
        SensorOfSolarIrradiance sensorOfSolarIrradiance2 = new SensorOfSolarIrradiance(anotherSensorId, deviceId, sensorModelName);
        //Act
        int result1 = sensorOfSolarIrradiance1.hashCode();
        int result2 = sensorOfSolarIrradiance2.hashCode();
        //Assert
        assertNotEquals(result1, result2, "The hashCode method returns the same value for different sensorId.");
    }

    /**
     * Test that the hashCode method returns a different value for different sensorModelName.
     */
    @Test
    void testConstructorThrowsExceptionWhenDeviceIdIsNull() {
        // Arrange
        SensorId sensorId = new SensorId("sensorID");
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        DeviceId invalidDeviceId = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSolarIrradiance(sensorId, invalidDeviceId,
                sensorModelName), "Constructor throws an exception when DeviceId is null.");
    }

    /**
     * Test that the hashCode method returns a different value for different sensorModelName.
     */
    @Test
    void testConstructorThrowsExceptionWhenSensorModelNameIsNull() {
        // Arrange
        SensorId sensorId = new SensorId("sensorID");
        DeviceId deviceId = new DeviceId("deviceID");
        SensorModelName invalidSensorModelName = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSolarIrradiance(sensorId, deviceId,
                invalidSensorModelName), "Constructor throws an exception when SensorModelName is null.");
    }
}