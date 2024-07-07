package smarthome.domain.house.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the Longitude class.
 */
class LongitudeTest {

    /**
     * Test to ensure that the Longitude constructor does not throw an exception when a valid longitude is provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidLongitude() {
        // Arrange
        double validLongitude = -180.0;
        // Act - Assert
        assertDoesNotThrow(() -> new Longitude(validLongitude),
                "The constructor should not throw an exception for a valid longitude.");
    }

    /**
     * Test to ensure that the Longitude constructor throws an exception when a longitude below -180 is provided.
     */
    @Test
    void testConstructorThrowsExceptionForLongitudeBelowNegative180() {
        // Arrange
        double invalidLongitude = -180.1;
        // Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new Longitude(invalidLongitude),
                "The constructor should throw an exception for a longitude below -180.");
    }

    /**
     * Test to ensure that the Longitude constructor throws an exception when a longitude above 180 is provided.
     */
    @Test
    void testConstructorThrowsExceptionForLongitudeAbove180() {
        // Arrange
        double invalidLongitude = 180.1;
        // Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new Longitude(invalidLongitude),
                "The constructor should throw an exception for a longitude above 180.");
    }

    /**
     * Test to ensure that the getLongitude method returns the correct value.
     */
    @Test
    void testGetLongitudeReturnsCorrectValue() {
        // Arrange
        double value = 180.0;
        Longitude longitude = new Longitude(value);
        // Act
        double result = longitude.getLongitude();
        // Assert
        assertEquals(value, result, "The getLongitude method should return the correct value.");
    }

    /**
     * Test to ensure that the equals method returns true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        double value = 100.5;
        Longitude longitude = new Longitude(value);

        // Act
        boolean result = longitude.equals(longitude);

        // Assert
        assertTrue(result, "The equals method should return true when comparing the same object.");
    }

    /**
     * Test to ensure that the equals method returns false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        double value = 100.5;
        Longitude longitude = new Longitude(value);

        // Act
        boolean result = longitude.equals(null);

        // Assert
        assertFalse(result, "The equals method should return false when comparing with a null object.");
    }

    /**
     * Test to ensure that the equals method returns false when comparing with a different type of object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        double value = 100.5;
        Longitude longitude = new Longitude(value);

        // Act
        boolean result = longitude.equals(new Object());

        // Assert
        assertFalse(result, "The equals method should return false when comparing with a different type of object.");
    }

    /**
     * Test to ensure that the equals method returns true when comparing two Longitude objects with the same value.
     */
    @Test
    void testEqualsForSameLongitudeValue() {
        // Arrange
        double value = 100.5;
        Longitude longitude1 = new Longitude(value);
        Longitude longitude2 = new Longitude(value);

        // Act
        boolean result = longitude1.equals(longitude2);

        // Assert
        assertTrue(result,
                "The equals method should return true when comparing two Longitude objects with the same value.");
    }

    /**
     * Test to ensure that the equals method returns false when comparing two Longitude objects with different values.
     */
    @Test
    void testEqualsForHigherLongitudeValue() {
        // Arrange
        double value1 = 100.5;
        double value2 = 100.501;
        Longitude longitude1 = new Longitude(value1);
        Longitude longitude2 = new Longitude(value2);

        // Act
        boolean result = longitude1.equals(longitude2);

        // Assert
        assertFalse(result,
                "The equals method should return false when comparing two Longitude objects with different values.");
    }

    /**
     * Test to ensure that the equals method returns false when comparing two Longitude objects with different values.
     */
    @Test
    void testEqualsForLowerLongitudeValue() {
        // Arrange
        double value1 = 100.5;
        double value2 = 100.499;
        Longitude longitude1 = new Longitude(value1);
        Longitude longitude2 = new Longitude(value2);

        // Act
        boolean result = longitude1.equals(longitude2);

        // Assert
        assertFalse(result,
                "The equals method should return false when comparing two Longitude objects with different values.");
    }

    /**
     * Test to ensure that the hashCode method returns the same hash code for two equal Longitude objects.
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        double value = 100.5;
        Longitude longitude1 = new Longitude(value);
        Longitude longitude2 = new Longitude(value);

        // Act
        int hashCode1 = longitude1.hashCode();
        int hashCode2 = longitude2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The hashCode method should return the same hash code for two equal Longitude objects.");
    }

    /**
     * Test to ensure that the hashCode method returns different hash codes for two different Longitude objects.
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        double value1 = 100.5;
        double value2 = value1 + 1;
        Longitude longitude1 = new Longitude(value1);
        Longitude longitude2 = new Longitude(value2);

        // Act
        int hashCode1 = longitude1.hashCode();
        int hashCode2 = longitude2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return different hash codes for two different Longitude objects.");
    }
}