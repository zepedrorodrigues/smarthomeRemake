package smarthome.domain.sensortype.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the class for SensorTypeId.
 */
class SensorTypeIdTest {

    /**
     * Test the constructor of the class SensorTypeId.
     * The constructor should not throw an exception for a valid sensor type identifier.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidSensorTypeId() {
        //Arrange
        String validSensorTypeId = "123ABC";
        //Act - Assert
        assertDoesNotThrow(() -> new SensorTypeId(validSensorTypeId),
                "The constructor should not throw an exception for a valid sensor type identifier.");
    }

    /**
     * Test the constructor of the class SensorTypeId.
     * The constructor should throw an exception for a null sensor type identifier.
     */
    @Test
    void testConstructorThrowsExceptionForNullSensorTypeId() {
        //Arrange
        String invalidSensorTypeId = null;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeId(invalidSensorTypeId),
                "The constructor should throw an exception for a null sensor type identifier.");
    }

    /**
     * Test the constructor of the class SensorTypeId.
     * The constructor should throw an exception for a blank sensor type identifier.
     */
    @Test
    void testConstructorThrowsExceptionForBlankSensorTypeId() {
        //Arrange
        String invalidSensorTypeId = " ";
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeId(invalidSensorTypeId),
                "The constructor should throw an exception for a blank sensor type identifier.");
    }

    /**
     * Test the getSensorId method of the class SensorTypeId.
     * The method should return the correct sensor type identifier value.
     */
    @Test
    void testGetSensorTypeIdReturnsCorrectValue() {
        //Arrange
        String expected = "123ABC";
        SensorTypeId sensorTypeId = new SensorTypeId(expected);
        //Act
        String result = sensorTypeId.getSensorTypeId();
        //Assert
        assertEquals(expected, result,
                "The getSensorTypeId method should return the correct sensor type identifier value.");
    }

    /**
     * Test the equals method of the class SensorTypeId.
     * The method should return true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("123ABC");

        // Act - Assert
        assertEquals(sensorTypeId, sensorTypeId,
                "The equals method should return true when comparing the same object.");
    }

    /**
     * Test the equals method of the class SensorTypeId.
     * The method should return false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("123ABC");
        SensorTypeId nullSensorTypeId = null;

        // Act - Assert
        assertNotEquals(sensorTypeId, nullSensorTypeId,
                "The equals method should return false when comparing with a null object.");
    }

    /**
     * Test the equals method of the class SensorTypeId.
     * The method should return false when comparing with a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("123ABC");

        // Act - Assert
        assertNotEquals(sensorTypeId, new Object(),
                "The equals method should return false when comparing with a different object.");
    }

    /**
     * Test the equals method of the class SensorTypeId.
     * The method should return true for the same sensor type identifier value.
     */
    @Test
    void testEqualsForSameSensorTypeIdValue() {
        // Arrange
        String sensorTypeId = "123ABC";
        SensorTypeId sensorTypeId1 = new SensorTypeId(sensorTypeId);
        SensorTypeId sensorTypeId2 = new SensorTypeId(sensorTypeId);

        // Act - Assert
        assertEquals(sensorTypeId1, sensorTypeId2,
                "The equals method should return true for the same sensor type identifier value.");
    }

    /**
     * Test the equals method of the class SensorTypeId.
     * The method should return false for different sensor type identifier values.
     */
    @Test
    void testEqualsForDifferentSensorIdValue() {
        // Arrange
        String sensorTypeIdA = "123ABC";
        String sensorTypeIdB = "456DEF";
        SensorTypeId sensorTypeId1 = new SensorTypeId(sensorTypeIdA);
        SensorTypeId sensorTypeId2 = new SensorTypeId(sensorTypeIdB);

        // Act - Assert
        assertNotEquals(sensorTypeId1, sensorTypeId2,
                "The equals method should return false for different sensor type identifier values.");
    }

    /**
     * Test the hashCode method of the class SensorTypeId.
     * The method should return the same hash code for the same sensor type identifier value.
     */
    @Test
    void testHashCodeForSameSensorTypeIdValue() {
        // Arrange
        String sensorTypeId = "123ABC";
        SensorTypeId sensorTypeId1 = new SensorTypeId(sensorTypeId);
        SensorTypeId sensorTypeId2 = new SensorTypeId(sensorTypeId);

        // Act
        int hashCode1 = sensorTypeId1.hashCode();
        int hashCode2 = sensorTypeId2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The hashCode method should return the same hash code for the same sensor type identifier value.");
    }

    /**
     * Test the hashCode method of the class SensorTypeId.
     * The method should return different hash codes for different sensor type identifier values.
     */
    @Test
    void testHashCodeForDifferentSensorTypeIdValue() {
        // Arrange
        String sensorTypeIdA = "123ABC";
        String sensorTypeIdB = "456F";
        SensorTypeId sensorTypeId1 = new SensorTypeId(sensorTypeIdA);
        SensorTypeId sensorTypeId2 = new SensorTypeId(sensorTypeIdB);

        // Act
        int hashCode1 = sensorTypeId1.hashCode();
        int hashCode2 = sensorTypeId2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return different hash codes for different sensor " +
                        "type identifier values.");
    }
}