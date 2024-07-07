package smarthome.domain.sensor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.SunriseValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.domain.sensormodel.vo.SensorModelName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the SensorOfSunrise class.
 */
class SensorOfSunriseTest {

    SensorId sensorId;
    DeviceId deviceId;
    SensorModelName sensorModelName;
    SensorOfSunrise sensorOfSunrisePrepare;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        sensorId = new SensorId("sensorId");
        deviceId = new DeviceId("deviceId");
        sensorModelName = new SensorModelName("sensorModelName");
        sensorOfSunrisePrepare = new SensorOfSunrise(sensorId, deviceId, sensorModelName);

    }

    /**
     * Tests the constructor of the SensorOfSunrise class does not throw an exception
     * when valid parameters are provided.
     */
    @Test
    void testConstructorWithoutSensorIdDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new SensorOfSunrise(deviceId, sensorModelName), "Should not throw an exception");
    }

    /**
     * Tests the constructor of the SensorOfSunrise class throws an exception
     * when a null DeviceId is provided.
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionForNullDeviceId() {
        //Arrange
        DeviceId nullDeviceId = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunrise(nullDeviceId, sensorModelName),
                "Should throw an exception");
    }

    /**
     * Tests the constructor of the SensorOfSunrise class throws an exception
     * when a null SensorModelName is provided.
     */
    @Test
    void testConstructorWithoutSensorIdThrowsExceptionForNullSensorModelName() {
        //Arrange
        SensorModelName nullSensorModelName = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunrise(deviceId, nullSensorModelName),
                "Should throw an exception");
    }

    /**
     * Tests the constructor of the SensorOfSunrise class does not throw an exception
     * when valid parameters are provided.
     */
    @Test
    void testConstructorWithSensorIdDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new SensorOfSunrise(sensorId, deviceId, sensorModelName),
                "Should not throw an exception");
    }

    /**
     * Tests the constructor of the SensorOfSunrise class does not throw an exception
     * when valid parameters are provided.
     */
    @Test
    void testConstructorWithSensorIdDoesNotThrowExceptionForNullSensorId() {
        //Arrange
        SensorId nullSensorId = null;
        //Act + assert
        assertDoesNotThrow(() -> new SensorOfSunrise(nullSensorId, deviceId, sensorModelName),
                "Should not throw an exception");
    }

    /**
     * Tests the constructor of the SensorOfSunrise class throws an exception
     * when a null DeviceId is provided.
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionForNullDeviceId() {
        //Arrange
        DeviceId nullDeviceId = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunrise(sensorId, nullDeviceId,
                sensorModelName), "Should throw an exception");
    }

    /**
     * Tests the constructor of the SensorOfSunrise class throws an exception
     * when a null SensorModelName is provided.
     */
    @Test
    void testConstructorWithSensorIdThrowsExceptionForNullSensorModelName() {
        //Arrange
        SensorModelName nullSensorModelName = null;
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorOfSunrise(sensorId, deviceId,
                nullSensorModelName), "Should throw an exception");
    }

    /**
     * Test to verify that the getId method returns the correct id of the sensor of sunrise.
     */
    @Test
    void testGetIdentity() {
        //Arrange
        SensorId expected = sensorId;
        //Act
        SensorId result = sensorOfSunrisePrepare.getIdentity();
        //Assert
        assertEquals(expected, result, "Should return the correct sensor id");
    }

    /**
     * Test to verify that the getId method does not return null for a random sensor id.
     */
    @Test
    void testGetIdentityRandomSensorId() {
        //Arrange
        SensorOfSunrise sensorOfSunrise = new SensorOfSunrise(deviceId, sensorModelName);
        //Act
        SensorId result = sensorOfSunrise.getIdentity();
        //Assert
        assertNotNull(result, "Should not return null");
    }

    /**
     * Test to verify that the getSensorModelName method returns the correct sensor model name of the
     * sensor of sunrise.
     */
    @Test
    void testGetSensorModelName() {
        //Arrange
        SensorModelName expected = sensorModelName;
        //Act
        SensorModelName result = sensorOfSunrisePrepare.getSensorModelName();
        //Assert
        assertEquals(expected, result, "Should return the correct sensor model name");
    }

    /**
     * Test to verify that the getDeviceId method returns the correct device id of the sensor of sunrise.
     */
    @Test
    void testGetDeviceId() {
        //Arrange
        DeviceId expected = deviceId;
        //Act
        DeviceId result = sensorOfSunrisePrepare.getDeviceId();
        //Assert
        assertEquals(expected, result, "Should return the correct device id");
    }

    /**
     * Test to verify that the getValue method returns the correct value of the sensor of sunrise for today.
     */
    @Test
    void getValueCurrentDate() {
        //Arrange
        Value expected = new SunriseValue(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0)));
        //Act
        Value result = sensorOfSunrisePrepare.getValue();
        //Assert
        assertEquals(expected, result, "Should return the correct value");
    }

    /**
     * Test to verify that the getValue method returns the correct value of the sensor of sunrise for a specified date.
     */
    @Test
    void getValueGivenCalendarDate() {
        //Arrange
        LocalDate date = LocalDate.of(2024, 12, 27);
        Value expected = new SunriseValue(LocalDateTime.of(date, LocalTime.of(6, 0)));
        //Act
        Value result = sensorOfSunrisePrepare.getValue(date);
        //Assert
        assertEquals(expected, result, "Should return the correct value");
    }

    /**
     * Test to verify that the getValue method throws an exception when a null date is provided.
     */
    @Test
    void testGetValueWithNullDateShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorOfSunrisePrepare.getValue(null),
                "Should throw an exception");
    }

    /**
     * Test to verify that the equals method returns true when the object is the same.
     */
    @Test
    void testEqualsForSameObject() {
        //Act
        boolean result = sensorOfSunrisePrepare.equals(sensorOfSunrisePrepare);
        //Assert
        assertTrue(result, "Should return true for the same object");
    }

    /**
     * Test to verify that the equals method returns false when the object is null.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorOfSunrise nullSensorOfSunrise = null;
        //Act
        boolean result = sensorOfSunrisePrepare.equals(nullSensorOfSunrise);
        //Assert
        assertFalse(result, "Should return false for a null object");
    }

    /**
     * Test to verify that the equals method returns false when a different class object is passed.
     */
    @Test
    void testEqualsForDifferentObject() {
        //Act
        boolean result = sensorOfSunrisePrepare.equals(new Object());
        //Assert
        assertFalse(result, "Should return false for a different class object");
    }

    /**
     * Test to verify that the equals method returns false when the sensor id is different.
     */
    @Test
    void testEqualsForDifferentSensorId() {
        //Arrange
        SensorOfSunrise sensorOfSunrise = new SensorOfSunrise(deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSunrise.equals(sensorOfSunrisePrepare);
        //Assert
        assertFalse(result, "Should return false for different sensor ids");

    }

    /**
     * Test to verify that the equals method returns true when the sensor id is the same.
     */
    @Test
    void testEqualsForSameSensorId() {
        //Arrange
        SensorOfSunrise sensorOfSunrise = new SensorOfSunrise(sensorId, deviceId, sensorModelName);
        //Act
        boolean result = sensorOfSunrise.equals(sensorOfSunrisePrepare);
        //Assert
        assertTrue(result, "Should return true for the same sensor id");

    }

    /**
     * Test to verify that the hashCode method returns different hash codes for SensorOfSunrise objects with
     * different sensor ids.
     * The test creates a new SensorOfSunrise object with a different sensor id, calculates the hash codes for the
     * new object and the object created in the setUp method, and asserts that the hash codes are not equal.
     */
    @Test
    void testHashCodeForDifferentSensorId() {
        //Arrange
        SensorOfSunrise sensorOfSunrise = new SensorOfSunrise(deviceId, sensorModelName);
        //Act
        int hashCode = sensorOfSunrise.hashCode();
        int hashCode1 = sensorOfSunrisePrepare.hashCode();
        //Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes");

    }

    /**
     * Test to verify that the hashCode method returns the same hash code for SensorOfSunrise objects with the same
     * sensor ids.
     * The test creates a new SensorOfSunrise object with the same sensor id as the object created in the setUp method,
     * calculates the hash codes for both objects, and asserts that the hash codes are equal.
     */
    @Test
    void testHashCodeForSameSensorId() {
        SensorOfSunrise sensorOfSunrise = new SensorOfSunrise(sensorId, deviceId, sensorModelName);
        //Act
        int hashCode = sensorOfSunrise.hashCode();
        int hashCode1 = sensorOfSunrisePrepare.hashCode();
        //Assert
        assertEquals(hashCode, hashCode1, "Should return the same hash code");

    }

}