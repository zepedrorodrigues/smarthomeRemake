package smarthome.domain.room.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class contains unit tests for the Width class.
 */
class WidthTest {

    /**
     * Test to verify that the constructor of the Width class throws an IllegalArgumentException when the value is zero.
     */
    @Test
    void testConstructorThrowsExceptionWhenValueIsZero() {
        // Arrange
        int value = 0;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Width(value),
                "The constructor" + " should throw an " + "IllegalArgumentException when the value is zero");
    }

    /**
     * Test to verify that the constructor of the Width class throws an IllegalArgumentException when the value is
     * negative.
     */
    @Test
    void testConstructorThrowsExceptionWhenValueIsNegative() {
        // Arrange
        int value = -1;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Width(value), "The constructor" + " should throw an " + "IllegalArgumentException when the value" + " is negative");
    }

    /**
     * Test to verify that the getWidth method of the Width class returns the correct value.
     */
    @Test
    void testGetWidthReturnsCorrectValue() {
        // Arrange
        int expectedValue = 5;
        Width width = new Width(expectedValue);

        // Act
        double actualValue = width.getWidth();

        // Assert
        assertEquals(expectedValue, actualValue, "The getWidth method should return the correct value");
    }

    /**
     * Test to verify that the equals method of the Width class returns true when compared with the same object.
     */
    @Test
    void testEqualsReturnsTrueWhenComparedWithSameObject() {
        // Arrange
        Width width1 = new Width(5);

        // Act
        boolean result = width1.equals(width1);

        // Assert
        assertTrue(result, "The equals method should return true when comparing the same object");
    }

    /**
     * Test to verify that the equals method of the Width class returns false when compared with a different object.
     */
    @Test
    void testEqualsReturnsFalseWhenComparedWithDifferentObject() {
        // Arrange
        Width width1 = new Width(5);

        // Act
        boolean result = width1.equals(new Object());

        // Assert
        assertFalse(result, "The equals method should return false when comparing with a different object");
    }

    /**
     * Test to verify that the equals method of the Width class returns false when compared with null.
     */
    @Test
    void testEqualsReturnsFalseWhenComparedWithNull() {
        // Arrange
        Width width = new Width(5);

        // Act
        boolean result = width.equals(null);

        // Assert
        assertFalse(result, "The equals method should return false when comparing with null");
    }

    /**
     * Test to verify that the equals method of the Width class returns true when two Width objects have the same
     * width value.
     * The test creates two Width objects with the same width value and checks if the equals method returns true.
     */
    @Test
    void testEqualsReturnsTrueWhenComparedWithSameWidth() {
        // Arrange
        Width width1 = new Width(5);
        Width width2 = new Width(5);

        // Act
        boolean result = width1.equals(width2);

        // Assert
        assertTrue(result, "The equals method should return true when comparing two Width objects with" + " the same "
                + "width" + " value");
    }

    /**
     * Test to verify that the equals method of the Width class returns false when two Width objects have different
     * width values.
     * The test creates two Width objects with different width values and checks if the equals method returns false.
     */
    @Test
    void testEqualsReturnsFalseWhenComparedWithDifferentWidth() {
        // Arrange
        Width width1 = new Width(5);
        Width width2 = new Width(10);

        // Act
        boolean result = width1.equals(width2);

        // Assert
        assertFalse(result, "The equals method should return false when comparing two Width objects with different " + "width " + "values");
    }

    /**
     * Test to verify that the equals method of the Width class returns false when two Width objects have different
     * width values.
     * The test creates two Width objects with different width values and checks if the equals method returns false.
     */
    @Test
    void testHashCodeForSameWidth() {
        // Arrange
        Width width1 = new Width(5.0);
        Width width2 = new Width(5.0);
        // Act
        int hashCode1 = width1.hashCode();
        int hashCode2 = width2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "The hashCode method should return the same value for two" + " Width" + " "
                + "objects with the same width value");
    }

    /**
     * Test to verify that the equals method of the Width class returns false when two Width objects have different
     * width values.
     * The test creates two Width objects with different width values and checks if the equals method returns false.
     */
    @Test
    void testHashCodeForDifferentWidth() {
        // Arrange
        Width width1 = new Width(5.0);
        Width width2 = new Width(10.0);
        // Act
        int hashCode1 = width1.hashCode();
        int hashCode2 = width2.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hashCode method should return different values for" + " two Width" + " objects with different " + "width values");
    }
}