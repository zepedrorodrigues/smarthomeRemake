package smarthome.domain.actuator.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the DecimalValue class.
 */
class DecimalValueTest {

    DecimalValue decimalValue;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        decimalValue = new DecimalValue(3);
    }

    /**
     * Test to check if the constructor of the DecimalValue class is not null.
     */
    @Test
    void testConstructorIsNotNullForValidDecimalValue() {
        //Act
        DecimalValue result = new DecimalValue(3.8);
        // Assert
        assertNotNull(result, "The constructor should not return null");
    }

    /**
     * Test that the equals method returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Act
        boolean result = decimalValue.equals(decimalValue);
        // Assert
        assertTrue(result, "Should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        DecimalValue nullDecimalValue = null;
        // Act
        boolean result = decimalValue.equals(nullDecimalValue);
        // Assert
        assertFalse(result, "Should return false for a null object");
    }

    /**
     * Test that the equals method returns false for a different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Act
        boolean result = decimalValue.equals(new Object());
        // Assert
        assertFalse(result, "Should return false for a different class object");
    }

    /**
     * Test that the equals method returns false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        DecimalValue decimalValue1 = new DecimalValue(7.88);
        // Act
        boolean result = decimalValue.equals(decimalValue1);
        // Assert
        assertFalse(result, "Should return false for a different object");
    }

    /**
     * Test that the equals method returns true for different objects with the same value.
     */
    @Test
    void testEqualsForDifferentObjectsWithSameValue() {
        // Arrange
        DecimalValue decimalValue1 = new DecimalValue(3);
        // Act
        boolean result = decimalValue.equals(decimalValue1);
        // Assert
        assertTrue(result, "Should return true for different objects with the same value");
    }

    /**
     * This test verifies the hashCode method of the DecimalValue class.
     * It creates two DecimalValue objects with the same value and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameDecimalValue() {
        // Arrange
        DecimalValue decimalValue1 = new DecimalValue(3);
        // Act
        int hashCode = decimalValue.hashCode();
        int hashCode1 = decimalValue1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return equal hashCodes for the same value");
    }

    /**
     * This test verifies the hashCode method of the DecimalValue class.
     * It creates two DecimalValue objects with different value and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentDecimalValue() {
        // Arrange
        DecimalValue decimalValue1 = new DecimalValue(1);
        // Act
        int hashCode = decimalValue.hashCode();
        int hashCode1 = decimalValue1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hashCodes for different values");
    }

    /**
     * Test to check if the valueToString method generates the correct string representation.
     */
    @Test
    void testValueToString() {
        //Arrange
        String expected = "3.0";
        //Act
        String result = decimalValue.valueToString();
        //Assert
        assertEquals(expected, result, "Should return the correct string representation");
    }

}