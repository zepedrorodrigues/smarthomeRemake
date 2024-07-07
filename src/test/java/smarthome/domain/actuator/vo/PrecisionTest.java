package smarthome.domain.actuator.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Precision
 */
class PrecisionTest {

    /**
     * Tests that the constructor of the Precision class does not throw an exception when a valid precision is
     * provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidPrecision() {
        //Arrange
        int precision = 1;
        //Act + Assert
        assertDoesNotThrow(() -> new Precision(precision), "Should not throw an exception for a valid precision");
    }

    /**
     * Tests that the constructor of the Precision class throws an exception when an invalid precision is provided.
     */
    @Test
    void testConstructorThrowsExceptionForInvalidPrecision() {
        //Arrange
        int invalidPrecision = -1;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new Precision(invalidPrecision),
                "Should throw an exception for an invalid precision");
    }

    /**
     * Test to verify that the constructor of the Precision class does not throw an exception when the precision value
     * is at its boundary.
     */
    @Test
    void testConstructorThrowsExceptionForBoundaryPrecision() {
        //Arrange
        int precision = 0;
        //Act + Assert
        assertDoesNotThrow(() -> new Precision(precision), "Should not throw an exception for a valid precision");
    }

    /**
     * Tests that the getPrecision method of the Precision class returns the correct value.
     */
    @Test
    void testGetPrecision() {
        //Arrange
        int expected = 1;
        Precision precision = new Precision(expected);
        //Act
        int result = precision.getPrecision();
        //Assert
        assertEquals(expected, result, "Should return the correct precision value");
    }

    /**
     * Tests that the equals method of the Precision class returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        Precision precision = new Precision(3);
        // Act
        boolean result = precision.equals(precision);
        // Assert
        assertTrue(result, "Should return true for the same object");
    }

    /**
     * Tests that the equals method of the Precision class returns false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        Precision precision = new Precision(3);
        Precision precision1 = new Precision(1);
        // Act
        boolean result = precision.equals(precision1);
        // Assert
        assertFalse(result, "Should return false for a different object");
    }

    /**
     * Tests that the equals method of the Precision class returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        Precision precision = new Precision(3);
        // Act
        boolean result = precision.equals(null);
        // Assert
        assertFalse(result, "Should return false for a null object");
    }

    /**
     * Tests that the equals method of the Precision class returns true for the same precision.
     */
    @Test
    void testEqualsForSamePrecision() {
        // Arrange
        Precision precision = new Precision(3);
        Precision precision1 = new Precision(3);
        // Act
        boolean result = precision.equals(precision1);
        // Assert
        assertTrue(result, "Should return true for the same precision");
    }

    /**
     * Tests that the equals method of the Precision class returns false for a different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        Precision precision = new Precision(3);
        // Act
        boolean result = precision.equals(new Object());
        // Assert
        assertFalse(result, "Should return false for a different class object");
    }

    /**
     * This test verifies the hashCode method of the Precision class.
     * It creates two Precision objects with the same limit and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSamePrecision() {
        // Arrange
        Precision precision = new Precision(3);
        Precision precision1 = new Precision(3);
        // Act
        int hashCode = precision.hashCode();
        int hashCode1 = precision1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return the same hash code for the same precision");
    }

    /**
     * This test verifies the hashCode method of the Precision class.
     * It creates two Precision objects with different precision and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentPrecision() {
        // Arrange
        Precision precision = new Precision(3);
        Precision precision1 = new Precision(1);
        // Act
        int hashCode = precision.hashCode();
        int hashCode1 = precision1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hash codes for different precision");
    }


}