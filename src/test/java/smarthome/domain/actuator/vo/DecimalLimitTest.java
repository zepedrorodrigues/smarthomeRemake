package smarthome.domain.actuator.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for DecimalLimit
 */
class DecimalLimitTest {

    /**
     * Tests the constructor of the DecimalLimit class is not null when a valid decimal limit is provided.
     */
    @Test
    void testConstructorNotNullForValidDecimalLimit() {
        //Arrange
        double decimalLimit = 50.3;
        //Act
        DecimalLimit result = new DecimalLimit(decimalLimit);
        // Assert
        assertNotNull(result, "The DecimalLimit object should not be null");
    }

    /**
     * Tests that the getDecimalLimit method of the DecimalLimit class returns the correct value.
     */
    @Test
    void testGetDecimalLimit() {
        //Arrange
        double expected = 30;
        DecimalLimit decimalLimit = new DecimalLimit(expected);
        //Act
        double result = decimalLimit.getDecimalLimit();
        //Assert
        assertEquals(expected, result, "Should return the correct decimal limit");
    }

    /**
     * Tests the equals method of the DecimalLimit class returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(71.22);
        // Act
        boolean result = decimalLimit.equals(decimalLimit);
        // Assert
        assertTrue(result, "Should return true for the same object");
    }

    /**
     * Tests the equals method of the DecimalLimit class returns true for the same limit.
     */
    @Test
    void testEqualsForSameLimit() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(71.22);
        DecimalLimit decimalLimit1 = new DecimalLimit(71.22);
        // Act
        boolean result = decimalLimit.equals(decimalLimit1);
        // Assert
        assertTrue(result, "Should return true for the same limit");
    }

    /**
     * Tests the equals method of the DecimalLimit class returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(71.22);
        // Act
        boolean result = decimalLimit.equals(null);
        // Assert
        assertFalse(result, "Should return false for a null object");
    }

    /**
     * Tests the equals method of the DecimalLimit class returns false for a different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(20);
        // Act
        boolean result = decimalLimit.equals(new Object());
        // Assert
        assertFalse(result, "Should return false for a different class object");
    }

    /**
     * Tests the equals method of the DecimalLimit class returns false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(20);
        DecimalLimit decimalLimit1 = new DecimalLimit(30.2);
        // Act
        boolean result = decimalLimit.equals(decimalLimit1);
        // Assert
        assertFalse(result, "Should return false for a different object");
    }

    /**
     * This test verifies the hashCode method of the DecimalLimit class.
     * It creates two DecimalLimit objects with the same limit and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameDecimalLimit() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(20);
        DecimalLimit decimalLimit1 = new DecimalLimit(20);
        // Act
        int hashCode = decimalLimit.hashCode();
        int hashCode1 = decimalLimit1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return the same hashCode for the same limit");
    }

    /**
     * This test verifies the hashCode method of the DecimalLimit class.
     * It creates two DecimalLimit objects with different limit and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentDecimalLimit() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(20);
        DecimalLimit decimalLimit1 = new DecimalLimit(10);
        // Act
        int hashCode = decimalLimit.hashCode();
        int hashCode1 = decimalLimit1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hashCodes for different limits");
    }

    /**
     * This test verifies the valueToString method of the DecimalLimit class.
     * It creates a DecimalLimit object with a limit of 20 and checks if the valueToString method returns
     * the correct string representation.
     */
    @Test
    void testValueToString() {
        // Arrange
        DecimalLimit decimalLimit = new DecimalLimit(20);
        String expected = "20.0";
        // Act
        String result = decimalLimit.valueToString();
        // Assert
        assertEquals(expected, result, "Should return the correct string representation of the decimal limit");
    }

}