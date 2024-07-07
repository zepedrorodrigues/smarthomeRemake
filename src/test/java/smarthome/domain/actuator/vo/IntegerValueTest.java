package smarthome.domain.actuator.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegerValueTest {

    IntegerValue integerValue;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        integerValue = new IntegerValue(3);
    }

    /**
     * Test to check if the constructor of the IntegerValue class is not null.
     */
    @Test
    void testConstructorIsNotNullForValidIntegerValue() {
        //Act
        IntegerValue result = new IntegerValue(3);
        // Assert
        assertNotNull(result, "The constructor should not return null.");
    }

    /**
     * Test that the equals method returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Act
        boolean result = integerValue.equals(integerValue);
        // Assert
        assertTrue(result, "The equals method should return true for the same object.");
    }

    /**
     * Test that the equals method returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        IntegerValue nullIntegerValue = null;
        // Act
        boolean result = integerValue.equals(nullIntegerValue);
        // Assert
        assertFalse(result, "The equals method should return false for a null object.");
    }

    /**
     * Test that the equals method returns false for a different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Act
        boolean result = integerValue.equals(new Object());
        // Assert
        assertFalse(result, "The equals method should return false for a different class object.");
    }

    /**
     * Test that the equals method returns true for different objects with the same value.
     */
    @Test
    void testEqualsForDifferentObjectsWithSameValue() {
        // Arrange
        IntegerValue integerValue1 = new IntegerValue(3);
        // Act
        boolean result = integerValue.equals(integerValue1);
        // Assert
        assertTrue(result, "The equals method should return true for different objects with the same value.");
    }

    /**
     * Test that the equals method returns false for different objects with different value.
     */
    @Test
    void testEqualsForDifferentObjectsWithDifferentValue() {
        // Arrange
        IntegerValue integerValue1 = new IntegerValue(1);
        // Act
        boolean result = integerValue.equals(integerValue1);
        // Assert
        assertFalse(result, "The equals method should return false for different objects with different value.");
    }

    /**
     * This test verifies the hashCode method of the IntegerValue class.
     * It creates two IntegerValue objects with the same value and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameIntegerValue() {
        // Arrange
        IntegerValue integerValue1 = new IntegerValue(3);
        // Act
        int hashCode = integerValue.hashCode();
        int hashCode1 = integerValue1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "The hashCodes should be equal for the same IntegerValue object.");
    }

    /**
     * This test verifies the hashCode method of the IntegerValue class.
     * It creates two IntegerValue objects with different value and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentIntegerValue() {
        // Arrange
        IntegerValue integerValue1 = new IntegerValue(1);
        // Act
        int hashCode = integerValue.hashCode();
        int hashCode1 = integerValue1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "The hashCodes should be different for different" + " IntegerValue " +
                "objects.");
    }

    /**
     * Test to check if the valueToString method generates the correct string representation.
     */
    @Test
    void testValueToString() {
        //Arrange
        String expected = "3";
        //Act
        String result = integerValue.valueToString();
        //Assert
        assertEquals(expected, result, "The valueToString method should return the correct" + " string representation" +
                ".");
    }

}