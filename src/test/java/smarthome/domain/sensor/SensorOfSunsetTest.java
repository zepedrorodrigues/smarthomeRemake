package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.SunsetValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the SensorOfSunset class.
 */
class SensorOfSunsetTest {

    SensorId sensorId;
    DeviceId deviceId;
    SensorModelName sensorModelName;
    SensorOfSunset sensorOfSunset;

    /**
     * Sets up the sensor of sunset instance for the tests.
     * The sensor id, device id and sensor model name are initialized.
     * The sensor of sunset instance is also initialized.
     */
    @BeforeEach
    void setUp() {
        sensorId = new SensorId("sensorId");
        deviceId = new DeviceId("deviceId");
        sensorModelName = new SensorModelName("sensorModelName");
        sensorOfSunset = new SensorOfSunset(sensorId, deviceId, sensorModelName);
    }

    /**
     * Tests the constructor without sensor id of the SensorOfSunset class.
     * Verifies that the class throws an IllegalArgumentException when the device id is null.
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunset(null, sensorModelName), "SensorOfSunset" +
                " object should not be created with null device id.");
    }

    /**
     * Tests the constructor without sensor id of the SensorOfSunset class.
     * Verifies that the class throws an IllegalArgumentException when the sensor model name is null.
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunset(deviceId, null), "SensorOfSunset object" +
                " should not be created with null sensor model name.");
    }

    /**
     * Tests the constructor without sensor id of the SensorOfSunset class.
     * Verifies that the class does not throw an exception when all parameters are valid.
     */
    @Test
    void testConstructorWithoutSensorIdWhenAllParametersAreValid() {
        // Act & Assert
        assertDoesNotThrow(() -> new SensorOfSunset(deviceId, sensorModelName), "SensorOfSunset object should be " +
                "successfully created.");
    }

    /**
     * Tests the constructor with sensor id of the SensorOfSunset class.
     * Verifies that the class throws an IllegalArgumentException when the sensor id is null.
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorIdIsNull() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunset(null, deviceId, sensorModelName),
                "SensorOfSunset object should not be created with null sensor id.");
    }

    /**
     * Tests the constructor with sensor id of the SensorOfSunset class.
     * Verifies that the class throws an IllegalArgumentException when the device id is null.
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenDeviceIdIsNull() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunset(sensorId, null, sensorModelName),
                "SensorOfSunset object should not be created with null device id.");
    }

    /**
     * Tests the constructor with sensor id of the SensorOfSunset class.
     * Verifies that the class throws an IllegalArgumentException when the sensor model name is null.
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionWhenSensorModelNameIsNull() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunset(sensorId, deviceId, null),
                "SensorOfSunset object should not be created with null sensor model name.");
    }

    /**
     * Tests the constructor with sensor id of the SensorOfSunset class.
     * Verifies that the class does not throw an exception when all parameters are valid.
     */
    @Test
    void testConstructorWithSensorIdWhenAllParametersAreValid() {
        // Act & Assert
        assertDoesNotThrow(() -> new SensorOfSunset(sensorId, deviceId, sensorModelName), "SensorOfSunset object " +
                "should be successfully created.");
    }

    /**
     * Tests the getIdentity method of the SensorOfSunset class.
     * Verifies that the method returns the correct sensor id.
     */
    @Test
    void testGetIdentityReturnsCorrectSensorId() {
        // Arrange
        SensorId expected = sensorId;
        // Act
        SensorId result = sensorOfSunset.getIdentity();
        // Assert
        assertEquals(expected, result, "Sensor id should be the same as the one passed in the constructor.");
    }

    /**
     * Tests the getDeviceId method of the SensorOfSunset class.
     * Verifies that the method returns the correct device id.
     */
    @Test
    void testGetDeviceIdReturnsCorrectDeviceId() {
        // Arrange
        DeviceId expected = deviceId;
        // Act
        DeviceId result = sensorOfSunset.getDeviceId();
        // Assert
        assertEquals(expected, result, "Device id should be the same as the one passed in the constructor.");
    }

    /**
     * Tests the getSensorModelName method of the SensorOfSunset class.
     * Verifies that the method returns the correct sensor model name.
     */
    @Test
    void testGetSensorModelNameReturnsCorrectSensorModelName() {
        // Arrange
        SensorModelName expected = sensorModelName;
        // Act
        SensorModelName result = sensorOfSunset.getSensorModelName();
        // Assert
        assertEquals(expected, result, "Sensor model name should be the same as the one passed in the constructor.");
    }

    /**
     * Tests the getValue method of the SensorOfSunset class.
     * Verifies that the method returns the correct value for the current date.
     */
    @Test
    void testGetValueReturnsCurrentDate() {
        // Arrange
        Value expected = new SunsetValue(LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));
        // Act
        Value result = sensorOfSunset.getValue();
        // Assert
        assertEquals(expected, result, "Value should be the default sunset time for the current date.");
    }

    /**
     * Tests the getValue method of the SensorOfSunset class.
     * Verifies that the method returns the correct value for the specified date.
     */
    @Test
    void getValueGivenCalendarDateReturnsCorrectDate() {
        //Arrange
        LocalDate date = LocalDate.of(2025, 3, 5);
        Value expected = new SunsetValue(LocalDateTime.of(date, LocalTime.of(18, 0)));
        //Act
        Value result = sensorOfSunset.getValue(date);
        //Assert
        assertEquals(expected, result, "Value should be the default sunset time for the specified date.");
    }


    /**
     * Tests the getValue method of the SensorOfSunset class.
     * Verifies that the method throws an IllegalArgumentException when the date is null.
     */
    @Test
    void testGetValueWithNullDateShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorOfSunset.getValue(null), "Value should not be " +
                "retrieved for a null date.");
    }

    /**
     * Tests the equals method of the SensorOfSunset class.
     * Verifies that the method returns true when the same object is passed as a parameter.
     */
    @Test
    void testEqualsForSameObjectShouldReturnTrue() {
        //Act
        boolean result = sensorOfSunset.equals(sensorOfSunset);
        //Assert
        assertTrue(result, "The result should be true for the same object.");
    }

    /**
     * Tests the equals method of the SensorOfSunset class.
     * Verifies that the method returns false when a null object is passed as a parameter.
     */
    @Test
    void testEqualsForNullObjectShouldReturnFalse() {
        //Act
        boolean result = sensorOfSunset.equals(null);
        //Assert
        assertFalse(result, "The result should be false for null object.");
    }

    /**
     * Tests the equals method of the SensorOfSunset class.
     * Verifies that the method returns false when a different object is passed as a parameter.
     */
    @Test
    void testEqualsForDifferentObject() {
        //Act
        boolean result = sensorOfSunset.equals(new Object());
        //Assert
        assertFalse(result, "The result should be false for different class object.");
    }


    /**
     * Tests the equals method of the SensorOfSunset class.
     * Verifies that the method returns false when the sensor id is different.
     */
    @Test
    void testEqualsForDifferentSensorIdShouldReturnFalse() {
        SensorOfSunset sensorOfSunset2 = new SensorOfSunset(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSunset.equals(sensorOfSunset2);
        //Assert
        assertFalse(result, "The result should be false for different sensor id.");
    }

    /**
     * Tests the equals method of the SensorOfSunset class.
     * Verifies that the method returns true when the sensor id is the same.
     */
    @Test
    void testEqualsForSameSensorIdShouldReturnTrue() {
        SensorOfSunset sensorOfSunset2 = new SensorOfSunset(sensorId, deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSunset.equals(sensorOfSunset2);
        //Assert
        assertTrue(result, "The result should be true for the same sensor id.");
    }

    /**
     * Tests the hashCode method of the SensorOfSunset class.
     * Verifies that the method returns different hash codes for different sensor ids.
     */
    @Test
    void testHashCodeForDifferentSensorIdShouldReturnFalse() {
        SensorOfSunset sensorOfSunset2 = new SensorOfSunset(deviceId, sensorModelName);
        //Act
        int hashCode1 = sensorOfSunset.hashCode();
        int hashCode2 = sensorOfSunset2.hashCode();
        //Assert
        assertNotEquals(hashCode1, hashCode2, "The hash code should be different for different sensor ids.");
    }

    /**
     * Tests the hashCode method of the SensorOfSunset class.
     * Verifies that the method returns the same hash code for the same sensor id.
     */
    @Test
    void testHashCodeForSameSensorIdShouldReturnTrue() {
        SensorOfSunset sensorOfSunset2 = new SensorOfSunset(sensorId, deviceId, sensorModelName);
        //Act
        int hashCode1 = sensorOfSunset.hashCode();
        int hashCode2 = sensorOfSunset2.hashCode();
        //Assert
        assertEquals(hashCode1, hashCode2, "The hash code should be the same for the same sensor id.");
    }
}