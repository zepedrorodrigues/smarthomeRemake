package smarthome.domain.reading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.ReadingValue;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensor.vo.values.Value;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains tests for the Reading class.
 */
class ReadingTest {

    Value value;
    SensorId sensorId;
    TimeStamp timeStamp;
    ReadingId readingId;
    Reading readingPrepare;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        value = new ReadingValue("SunriseValue");
        sensorId = new SensorId("sensorId");
        timeStamp = new TimeStamp(LocalDateTime.now());
        readingId = new ReadingId("readingId");

        readingPrepare = new Reading(readingId, value, sensorId, timeStamp);

    }

    /**
     * Tests the constructor of the Reading class does not throw an exception when valid parameters are provided.
     */
    @Test
    void testConstructorWithoutReadingIdDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new Reading(value, sensorId, timeStamp),
                "Does not throw an exception when valid parameters are provided.");
    }

    /**
     * Tests the constructor of the Reading class throws an exception when a null reading Value is provided.
     */
    @Test
    void testConstructorWithoutReadingIdThrowsExceptionForNullReadingValue() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Reading(null, sensorId, timeStamp),
                "Throws an exception when a null ReadingValue is provided.");
    }

    /**
     * Tests the constructor of the Reading class throws an exception when a null SensorId is provided.
     */
    @Test
    void testConstructorWithoutReadingIdThrowsExceptionForNullSensorId() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Reading(value, null, timeStamp),
                "Throws an exception when a null SensorId is provided.");
    }

    /**
     * Tests the constructor of the Reading class throws an exception when a null TimeStamp is provided.
     */
    @Test
    void testConstructorWithoutReadingIdThrowsExceptionForNullTimeStamp() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Reading(value, sensorId, null),
                "Throws an exception when a null TimeStamp is provided.");
    }

    /**
     * Tests the constructor of the Reading class does not throw an exception when valid parameters are provided.
     */
    @Test
    void testConstructorWithReadingIdDoesNotThrowExceptionForValidParameters() {
        //Act + assert
        assertDoesNotThrow(() -> new Reading(readingId, value, sensorId, timeStamp),
                "Does not throw an exception when valid parameters are provided.");
    }

    /**
     * Tests the constructor of the Reading class does not throw an exception when a null ReadingId is provided.
     */
    @Test
    void testConstructorWithReadingIdDoesNotThrowExceptionForNullReadingId() {
        //Act + assert
        assertDoesNotThrow(() -> new Reading(null, value, sensorId, timeStamp),
                "Does not throw an exception when a null ReadingId is provided.");
    }

    /**
     * Tests the constructor of the Reading class throws an exception when a null reading Value is provided.
     */
    @Test
    void testConstructorWithReadingIdThrowsExceptionForNullReadingValue() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Reading(readingId, null, sensorId, timeStamp),
                "Throws an exception when a null ReadingValue is provided.");
    }

    /**
     * Tests the constructor of the Reading class throws an exception when a null SensorId is provided.
     */
    @Test
    void testConstructorWithReadingIdThrowsExceptionForNullSensorId() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Reading(readingId, value, null, timeStamp),
                "Throws an exception when a null SensorId is provided.");
    }

    /**
     * Tests the constructor of the Reading class throws an exception when a null TimeStamp is provided.
     */
    @Test
    void testConstructorWithReadingIdThrowsExceptionForNullTimeStamp() {
        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Reading(readingId, value, sensorId, null),
                "Throws an exception when a null TimeStamp is provided.");
    }

    /**
     * Test to verify that the getIdentity method returns the correct id of the ReadingId.
     */
    @Test
    void testGetIdentity() {
        //Arrange
        ReadingId expected = readingId;
        //Act
        ReadingId result = readingPrepare.getIdentity();
        //Assert
        assertEquals(expected, result,
                "Verifies that the getIdentity method returns the correct id of the ReadingId.");
    }

    /**
     * Test to verify that the getIdentity method does not return null for a random readingId.
     */
    @Test
    void testGetIdentityRandomReadingId() {
        //Arrange
        Reading reading = new Reading(value, sensorId, timeStamp);
        //Act
        ReadingId result = reading.getIdentity();
        //Assert
        assertNotNull(result,
                "Verifies that the getIdentity method does not return null for a random readingId.");
    }

    /**
     * Test to verify that the getValue method returns the correct value of the Reading.
     */
    @Test
    void testGetValue() {
        //Arrange
        Value expected = value;
        //Act
        Value result = readingPrepare.getValue();
        //Assert
        assertEquals(expected, result,
                "Verifies that the getValue method returns the correct value of the Reading.");
    }

    /**
     * Test to verify that the getSensorId method returns the correct SensorId of the Reading.
     */
    @Test
    void testGetSensorId() {
        //Arrange
        SensorId expected = sensorId;
        //Act
        SensorId result = readingPrepare.getSensorId();
        //Assert
        assertEquals(expected, result,
                "Verifies that the getSensorId method returns the correct SensorId of the Reading.");
    }

    /**
     * Test to verify that the getTimeStamp method returns the correct TimeStamp of the Reading.
     */
    @Test
    void testGetTime() {
        //Arrange
        TimeStamp expected = timeStamp;
        //Act
        TimeStamp result = readingPrepare.getTime();
        //Assert
        assertEquals(expected, result,
                "Verifies that the getTimeStamp method returns the correct TimeStamp of the Reading.");
    }

    /**
     * Test to verify that the equals method returns true when the object is the same.
     */
    @Test
    void testEqualsForSameObject() {
        //Act
        boolean result = readingPrepare.equals(readingPrepare);
        //Assert
        assertTrue(result, "Verifies that the equals method returns true when the object is the same.");
    }

    /**
     * Test to verify that the equals method returns false when the object is null.
     */
    @Test
    void testEqualsForNullObject() {
        //Act
        boolean result = readingPrepare.equals(null);
        //Assert
        assertFalse(result, "Verifies that the equals method returns false when the object is null.");
    }

    /**
     * Test to verify that the equals method returns false when a different class object is passed.
     */
    @Test
    void testEqualsForDifferentObject() {
        //Act
        boolean result = readingPrepare.equals(new Object());
        //Assert
        assertFalse(result,
                "Verifies that the equals method returns false when a different class object is passed.");
    }

    /**
     * Test to verify that the equals method returns false when the ReadingId is different.
     */
    @Test
    void testEqualsForDifferentReadingId() {
        //Arrange
        Reading reading = new Reading(value, sensorId, timeStamp);
        //Act
        boolean result = readingPrepare.equals(reading);
        //Assert
        assertFalse(result, "Verifies that the equals method returns false when the ReadingId is different.");

    }

    /**
     * Test to verify that the equals method returns true when the ReadingId is the same.
     */
    @Test
    void testEqualsForSameReadingId() {
        //Arrange
        Reading reading = new Reading(readingId, value, sensorId, timeStamp);
        //Act
        boolean result = readingPrepare.equals(reading);
        //Assert
        assertTrue(result, "Verifies that the equals method returns true when the ReadingId is the same.");

    }

    /**
     * Test to verify that the hashCode method returns different hash codes for Reading objects with different
     * reading ids.
     * The test creates a new Reading object with a different ReadingId, calculates the hash codes for the new object
     * and the object created in the setUp method, and asserts that the hash codes are not equal.
     */
    @Test
    void testHashCodeForDifferentReadingId() {
        //Arrange
        Reading reading = new Reading(value, sensorId, timeStamp);
        //Act
        int hashCode = reading.hashCode();
        int hashCode1 = readingPrepare.hashCode();
        //Assert
        assertNotEquals(hashCode, hashCode1, "Verifies that the hash codes are different");

    }

    /**
     * Test to verify that the hashCode method returns the same hash code for Reading objects with the same reading
     * ids.
     * The test creates a new Reading object with the same ReadingId as the object created in the setUp method,
     * calculates the hash codes for both objects, and asserts that the hash codes are equal.
     */
    @Test
    void testHashCodeForSameReadingId() {
        Reading reading = new Reading(readingId, value, sensorId, timeStamp);
        //Act
        int hashCode = reading.hashCode();
        int hashCode1 = readingPrepare.hashCode();
        //Assert
        assertEquals(hashCode, hashCode1, "Verifies that the hash codes are equal.");

    }


}