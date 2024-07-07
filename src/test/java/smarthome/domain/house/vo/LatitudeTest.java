package smarthome.domain.house.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the Latitude class.
 */
class LatitudeTest {

    /**
     * Test the constructor of the class Latitude.
     * The constructor should not throw an exception for a valid latitude.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidLatitude() {
        //Arrange
        double validLatitude = -90.0;
        //Act - Assert
        assertDoesNotThrow(() -> new Latitude(validLatitude),
                "The constructor should not throw an exception for a valid latitude.");
    }

    /**
     * Test the constructor of the class Latitude.
     * The constructor should throw an exception for an invalid latitude.
     * The latitude is below -90.
     */
    @Test
    void testConstructorThrowsExceptionForLatitudeBelowNegative90() {
        //Arrange
        double invalidLatitude = -90.1;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new Latitude(invalidLatitude),
                "The constructor should throw an exception for an invalid latitude.");
    }

    /**
     * Test the constructor of the class Latitude.
     * The constructor should throw an exception for an invalid latitude.
     * The latitude is above 90.
     */
    @Test
    void testConstructorThrowsExceptionForLatitudeAbove90() {
        //Arrange
        double invalidLatitude = 90.1;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new Latitude(invalidLatitude),
                "The constructor should throw an exception for an invalid latitude.");
    }

    /**
     * Test the getLatitude method of the class Latitude.
     * The method should return the correct latitude value.
     */
    @Test
    void testGetLatitudeValueReturnsCorrect() {
        //Arrange
        double value = 90.0;
        Latitude latitude = new Latitude(value);
        //Act
        double result = latitude.getLatitude();
        //Assert
        assertEquals(value, result,
                "The method should return the correct latitude value.");
    }

    /**
     * Test the equals method of the class Latitude.
     * The method should return true for the same object.
     */
    @Test
    void testEqualsAsForSameObject() {
        // Arrange
        Latitude latitude = new Latitude(30.5);

        // Act - Assert
        assertEquals(latitude, latitude,
                "The method should return true for the same object.");
    }

    /**
     * Test the equals method of the class Latitude.
     * The method should return false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        Latitude latitude = new Latitude(30.5);
        Latitude nullLatitude = null;

        // Act - Assert
        assertNotEquals(latitude, nullLatitude,
                "The method should return false for a null object.");
    }

    /**
     * Test the equals method of the class Latitude.
     * The method should return false for a different class object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        Latitude latitude = new Latitude(30.5);

        // Act - Assert
        assertNotEquals(latitude, new Object(),
                "The method should return false for a different class object.");
    }

    /**
     * Test the equals method of the class Latitude.
     * The method should return true for the same latitude value.
     */
    @Test
    void testEqualsForSameLatitudeValue() {
        // Arrange
        double value1 = 30.5;
        double value2 = 30.5;
        Latitude latitude1 = new Latitude(value1);
        Latitude latitude2 = new Latitude(value2);

        // Act - Assert
        assertEquals(latitude1, latitude2,
                "The method should return true for the same latitude value.");
    }

    /**
     * Test the equals method of the class Latitude.
     * The method should return false for a different latitude value.
     * The latitude value is higher.
     */
    @Test
    void testEqualsForHigherLatitudeValue() {
        // Arrange
        double value1 = 30.5;
        double value2 = 30.501;
        Latitude latitude1 = new Latitude(value1);
        Latitude latitude2 = new Latitude(value2);

        // Act - Assert
        assertNotEquals(latitude1, latitude2,
                "The method should return false for a different latitude value.");
    }

    /**
     * Test the equals method of the class Latitude.
     * The method should return false for a different latitude value.
     * The latitude value is lower.
     */
    @Test
    void testEqualsForLowerLatitudeValue() {
        // Arrange
        double value1 = 30.5;
        double value2 = 30.499;
        Latitude latitude1 = new Latitude(value1);
        Latitude latitude2 = new Latitude(value2);

        // Act - Assert
        assertNotEquals(latitude1, latitude2,
                "The method should return false for a different latitude value.");
    }

    /**
     * Test the hashCode method of the class Latitude.
     * The method should return the same value for two objects with the same latitude value.
     */
    @Test
    void testHashCodeForSameValue() {
        // Arrange
        Latitude latitude1 = new Latitude(30.5);
        Latitude latitude2 = new Latitude(30.5);

        // Act
        int result1 = latitude1.hashCode();
        int result2 = latitude2.hashCode();

        // Assert
        assertEquals(result1, result2,
                "The method should return the same value for two objects with the same latitude value.");
    }

    /**
     * Test the hashCode method of the class Latitude.
     * The method should return different values for two objects with different latitude values.
     */
    @Test
    void testHashCodeForDifferentValue() {
        // Arrange
        Latitude latitude1 = new Latitude(30.5);
        Latitude latitude2 = new Latitude(25.5);

        // Act
        int result1 = latitude1.hashCode();
        int result2 = latitude2.hashCode();

        // Assert
        assertNotEquals(result1, result2,
                "The method should return different values for two objects with different latitude values.");
    }
}