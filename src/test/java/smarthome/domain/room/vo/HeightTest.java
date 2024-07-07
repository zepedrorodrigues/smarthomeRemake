package smarthome.domain.room.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the {@link Height} class.
 * This test class verifies the correct behavior of the Height value object, including
 * its construction, value retrieval, and equality checks.
 */
class HeightTest {

    double validValue = 3;
    double borderValue = 0.000000000000002;
    double invalidValue = 0;

    /**
     * Verifies that constructing a {@link Height} with a valid value does not throw an exception.
     */
    @Test
    void testConstructorValidValueShouldNotThrowException() {
        // Act and Assert
        assertDoesNotThrow(() -> new Height(validValue), "Constructor should not throw an exception for a valid value" +
                ".");
    }

    /**
     * Verifies that constructing a {@link Height} with a value at the boundary of validity
     * does not throw an exception.
     */
    @Test
    void testConstructorBorderValueShouldNotThrowException(){
        // Act and Assert
        assertDoesNotThrow(() -> new Height(borderValue), "Constructor should not throw an exception for a border " +
                "value.");
    }

    /**
     * Verifies that constructing a {@link Height} with an invalid value throws an IllegalArgumentException.
     */
    @Test
    void testConstructorInvalidValueShouldThrowException(){
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Height(invalidValue), "Constructor should throw an " +
                "IllegalArgumentException for an invalid value.");
    }

    /**
     * Tests if the {@link Height#getHeight()} method returns the correct value with which
     * the {@link Height} instance was constructed.
     */
    @Test
    void getHeight() {
        // Arrange
        Height height = new Height(validValue);
        // Act
        double result = height.getHeight();
        // Assert
        assertEquals(validValue, result, "The value returned by getHeight " + "should be the same as the value used " +
                "to construct the object.");
    }

    /**
     * Verifies that the {@link Height#equals(Object)} method returns true when comparing
     * two different {@link Height} instances with the same value.
     */
    @Test
    void equalsReturnsTrueDifferentObjectSameValue() {
        // Arrange
        Height height = new Height(validValue);
        Height height1 = new Height(validValue);
        // Act
        boolean result = height.equals(height1);
        // Assert
        assertTrue(result, "Two different Height instances with the same value should be equal.");
    }

    /**
     * Verifies that the {@link Height#equals(Object)} method returns true when an instance
     * is compared with itself.
     */
    @Test
    void equalsReturnsTrueSameObject() {
        // Arrange
        Height height = new Height(validValue);
        // Act
        boolean result = height.equals(height);
        // Assert
        assertTrue(result, "An object should be equal to itself.");
    }

    /**
     * Verifies that the {@link Height#equals(Object)} method returns false when comparing a {@link Height}
     * instance with {@code null}.
     */
    @Test
    void equalsFalseNullObjectversusHeightObject() {
        // Arrange
        Height height = new Height(validValue);
        // Act
        boolean result = height.equals(null);
        // Assert
        assertFalse(result, "A Height object should not be equal to null.");
    }

    /**
     * Tests that the {@link Height#equals(Object)} method returns false when comparing two {@link Height}
     * instances with different values.
     */
    @Test
    void equalsFalseHeightObjectButDifferentValue() {
        // Arrange
        double newValue = 4;
        Height height = new Height(validValue);
        Height height1 = new Height(newValue);
        // Act
        boolean result = height.equals(height1);
        // Assert
        assertFalse(result, "Two different Height instances with different values should not be equal.");
    }

    /**
     * Tests that the {@link Height#hashCode()} method returns an Integer
     * and that different height objects with the same value return the same hash
     * code.
     */
    @Test
    void testHashCodeSameValueShouldBeEqual() {
        // Arrange
        Height height = new Height(validValue);
        Height height1 = new Height(validValue);
        // Act
        int result = height.hashCode();
        int result1 = height1.hashCode();
        // Assert
        assertEquals(result1, result, "Two different Height instances " + "with the same value should have the same " +
                "hash code.");
    }

    /**
     * Tests that the {@link Height#hashCode()} method returns the same hash code for two different
     * {@link Height} instances with different values.
     *
     */
    @Test
    void testHashCodeDifferentValueShouldBeNonEqual(){
        // Arrange
        double newValue = 4;
        Height height = new Height(validValue);
        Height height1 = new Height(newValue);
        // Act
        int result = height.hashCode();
        int result1 = height1.hashCode();
        // Assert
        assertNotEquals(result1, result, "Two different Height instances " + "with different values should not have " +
                "the same hash code.");
    }

    /**
     * Tests that the equals method returns true when comparing two different objects with the same value.
     * The method should return true.
     */
    @Test
    void testEqualsDifferentObjectsSameValueShouldReturnTrue() {
        // Arrange
        Height height = new Height(validValue);
        Height height1 = new Height(validValue);
        // Act
        boolean result = height.equals(height1);
        // Assert
        assertTrue(result, "Two different Height instances with the same value should be equal.");
    }

    /**
     * Tests that the equals method returns true when comparing the same object to itself.
     * The method should return true.
     */
    @Test
    void testEqualsSameObjectShouldReturnTrue() {
        // Arrange
        Height height = new Height(validValue);
        // Act
        boolean result = height.equals(height);
        // Assert
        assertTrue(result, "An object should be equal to itself.");
    }

    /**
     * Tests that the equals method returns false when comparing two different objects with different values.
     * The method should return false.
     */
    @Test
    void testEqualsDifferentObjectsDifferentValueShouldReturnFalse() {
        // Arrange
        double newValue = 4;
        Height height = new Height(validValue);
        Height height1 = new Height(newValue);
        // Act
        boolean result = height.equals(height1);
        // Assert
        assertFalse(result, "Two different Height instances with different values should not be equal.");
    }

    /**
     * Tests that the equals method returns false when comparing two different objects with the same value.
     * The method should return false.
     */
    @Test
    void testEqualsDifferentClassesOfObjectsShouldReturnFalse() {
        // Arrange
        Height height = new Height(validValue);
        Length length = new Length(validValue); // Assuming Length is a similar class to Height
        // Act
        // Assert
        assertNotEquals(height, length, "Two different classes of objects should not be equal.");
    }

    /**
     * Tests that the equals method returns false when comparing a {@link Height} instance with {@code null}.
     * The method should return false.
     */
    @Test
    void testEqualsNullObjectShouldReturnFalse() {
        // Arrange
        Height height = new Height(validValue);
        // Act
        boolean result = height.equals(null);
        // Assert
        assertFalse(result, "A Height object should not be equal to null.");
    }

}
