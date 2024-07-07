package smarthome.domain.sensor.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the class for SensorId.
 */
class SensorIdTest {

    /**
     * Test the constructor of the class SensorId.
     * The constructor should not throw an exception for a valid sensor identifier.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidSensorId() {
        //Arrange
        String validSensorId = "112345B";
        //Act - Assert
        assertDoesNotThrow(() -> new SensorId(validSensorId),
                "The constructor should not throw an exception for a valid sensor identifier.");
    }

    /**
     * Test the constructor of the class SensorId.
     * The constructor should throw an exception for a null sensor identifier.
     */
    @Test
    void testConstructorThrowsExceptionForNullSensorId() {
        //Arrange
        String invalidSensorId = null;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorId(invalidSensorId),
                "The constructor should throw an exception for a null sensor identifier.");
    }

    /**
     * Test the constructor of the class SensorId.
     * The constructor should throw an exception for a blank sensor identifier.
     */
    @Test
    void testConstructorThrowsExceptionForBlankSensorId() {
        //Arrange
        String invalidSensorId = " ";
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorId(invalidSensorId),
                "The constructor should throw an exception for a blank sensor identifier.");
    }

    /**
     * Test the getSensorId method of the class SensorId.
     * The method should return the correct sensor identifier value.
     */
    @Test
    void testGetSensorIdReturnsCorrectValue() {
        //Arrange
        String expected = "112345B";
        SensorId sensorId = new SensorId(expected);
        //Act
        String result = sensorId.getSensorId();
        //Assert
        assertEquals(expected, result,
                "The getSensorId method should return the correct sensor identifier value.");
    }

    /**
     * Test the equals method of the class SensorId.
     * The method should return true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        SensorId sensorId = new SensorId("112345B");

        // Act - Assert
        assertEquals(sensorId, sensorId,
                "The equals method should return true when comparing the same object.");
    }

    /**
     * Test the equals method of the class SensorId.
     * The method should return false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        SensorId sensorId = new SensorId("112345B");
        SensorId nullSensorId = null;

        // Act  - Assert
        assertNotEquals(sensorId, nullSensorId,
                "The equals method should return false when comparing with a null object.");

    }

    /**
     * Test the equals method of the class SensorId.
     * The method should return false when comparing with a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        SensorId sensorId = new SensorId("112345B");

        // Act
        assertNotEquals(sensorId, new Object(),
                "The equals method should return false when comparing with a different object.");
    }

    /**
     * Test the equals method of the class SensorId.
     * The method should return true for the same sensor identifier value.
     */
    @Test
    void testEqualsForSameSensorIdValue() {
        // Arrange
        String sensorId = "112345B";
        SensorId sensorId1 = new SensorId(sensorId);
        SensorId sensorId2 = new SensorId(sensorId);

        // Act - Assert
        assertEquals(sensorId1, sensorId2,
                "The equals method should return true for the same sensor identifier value.");
    }

    /**
     * Test the equals method of the class SensorId.
     * The method should return false for different sensor identifier values.
     */
    @Test
    void testEqualsForDifferentSensorIdValue() {
        // Arrange
        String sensorIdA = "112345B";
        String sensorIdB = "112345C";
        SensorId sensorId1 = new SensorId(sensorIdA);
        SensorId sensorId2 = new SensorId(sensorIdB);

        // Act - Assert
        assertNotEquals(sensorId1, sensorId2,
                "The equals method should return false for different sensor identifier values.");
    }

    /**
     * Test the hashCode method of the class SensorId.
     * The method should return the same hash code for the same sensor identifier value.
     */
    @Test
    void testHashCodeForSameSensorId() {
        // Arrange
        String sensorId = "112345B";
        SensorId sensorId1 = new SensorId(sensorId);
        SensorId sensorId2 = new SensorId(sensorId);

        // Act
        int hashCode1 = sensorId1.hashCode();
        int hashCode2 = sensorId2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The hashCode method should return the same hash code for the same sensor identifier value.");
    }

    /**
     * Test the hashCode method of the class SensorId.
     * The method should return different hash codes for different sensor identifier values.
     */
    @Test
    void testHashCodeForDifferentSensorId() {
        // Arrange
        String sensorIdA = "112345B";
        String sensorIdB = "1345C";
        SensorId sensorId1 = new SensorId(sensorIdA);
        SensorId sensorId2 = new SensorId(sensorIdB);

        // Act
        int hashCode1 = sensorId1.hashCode();
        int hashCode2 = sensorId2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return different hash codes for different sensor identifier values.");
    }
}