package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Tests for the DewPointValue class
 */
class DewPointValueTest {

    /**
     * Test that the constructor creates a DewPointValue object.
     *
     * This test ensures that an instance of DewPointValue is successfully created.
     * It checks if the object is not null after instantiation.
     */
    @Test
    void testConstructorCreatesDewPointValue() {
        //Act
        DewPointValue dewPointValue = new DewPointValue(29.0);
        //Assert
        assertNotNull(dewPointValue
                , "DewPointValue object should not be null");
    }

    /**
     * Test that the valueToString method returns the correct string
     * for a positive dew point value
     */
    @Test
    void testValueToStringReturnsCorrectStringForPositiveValue() {
        // Arrange
        String expected = "29.0";
        DewPointValue dewPointValue = new DewPointValue(29.0);
        // Act
        String result = dewPointValue.valueToString();
        // Assert
        assertEquals(expected, result
                , "The valueToString method should return the correct string for a positive value");
    }

    /**
     * Test that the valueToString method returns the correct string
     * for a negative dew point value
     */
    @Test
    void testValueToStringReturnsCorrectStringForNegativeValue() {
        // Arrange
        String expected = "-1.0";
        DewPointValue dewPointValue = new DewPointValue(-1.0);
        // Act
        String result = dewPointValue.valueToString();
        // Assert
        assertEquals(expected, result
                , "The valueToString method should return the correct string for a negative value");
    }

    /**
     * Test that the equals method returns true for the same object.
     *
     * This test checks if the equals method returns true
     * when comparing the same object reference.
     */
    @Test
    void testEqualsForSameObjectEqualsCalledOnItselfShouldReturnTrue() {
        // Arrange
        DewPointValue dewPointValue = new DewPointValue(29.0);
        // Act
        boolean result = dewPointValue.equals(dewPointValue);
        // Assert
        assertTrue(result
                , "The equals method should return true when comparing the same object reference");
    }

    /**
     * Test that the equals method returns false for a null object.
     *
     * This test checks if the equals method returns false
     * when comparing to a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        DewPointValue dewPointValue = new DewPointValue(29.0);
        // Act
        boolean result = dewPointValue.equals(null);
        // Assert
        assertFalse(result
                , "The equals method should return false when comparing to a null object");
    }

    /**
     * Test that the equals method returns false for a different object.
     *
     * This test checks if the equals method returns false
     * when comparing to an object of a different class.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        DewPointValue dewPointValue = new DewPointValue(29.0);
        // Act
        boolean result = dewPointValue.equals(new Object());
        // Assert
        assertFalse(result
                , "The equals method should return false when comparing to an object of a different class");
    }

    /**
     * Test that the equals method returns true for the same DewPointValue.
     *
     * This test checks if the equals method correctly identifies two DewPointValue objects
     * with the same dew point value as equal.
     */
    @Test
    void testEqualsForSameDewPointValue() {
        // Arrange
        double value = 29.0;
        DewPointValue dewPointValue1 = new DewPointValue(value);
        DewPointValue dewPointValue2 = new DewPointValue(value);
        // Act
        boolean result = dewPointValue1.equals(dewPointValue2);
        // Assert
        assertTrue(result
                , "The equals method should return true for two DewPointValue objects with the same dew point value");
    }

    /**
     * Test that the equals method returns false for different DewPointValues.
     *
     * This test checks if the equals method correctly identifies two DewPointValue objects
     * with different dew point values as not equal.
     */
    @Test
    void testEqualsForDifferentDewPointValues() {
        // Arrange
        double valueA = 29.0;
        double valueB = 72.0;
        DewPointValue dewPointValue1 = new DewPointValue(valueA);
        DewPointValue dewPointValue2 = new DewPointValue(valueB);
        // Act
        boolean result = dewPointValue1.equals(dewPointValue2);
        // Assert
        assertFalse(result
                , "The equals method should return false for two DewPointValue objects with different dew point values");
    }

    /**
     * Test the hashCode method returns the same hash code for identical DewPointValues.
     *
     * This test creates two separate DewPointValue objects with the same value
     * and then compare their hash codes.
     * This test is explicit in creating two separate instances of the object
     * to ensure their hash codes are consistent.
     *
     */
    @Test
    void testHashCode() {
        // Arrange
        double value = 29.0;
        DewPointValue dewPointValue1 = new DewPointValue(value);
        DewPointValue dewPointValue2 = new DewPointValue(value);
        // Act
        int hashCode1 = dewPointValue1.hashCode();
        int hashCode2 = dewPointValue2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2
                , "The hashCode method should return the same hash code for identical DewPointValues");
    }

    /**
     * Test that the hashCode method returns the same hash code for identical DewPointValues.
     *
     * This test creates only one DewPointValue object and then compares its hash code against itself.
     * Tests the consistency of the hashCode method by verifying that the hash code
     * remains the same for the same object across multiple invocations.
     */
    @Test
    void testHashCodeConsistency() {
        // Arrange
        DewPointValue dewPointValue = new DewPointValue(29.0);
        // Act
        int hashCode1 = dewPointValue.hashCode();
        int hashCode2 = dewPointValue.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2
                , "The hashCode method should return the same hash code for the same object across multiple invocations");
    }

    /**
     * Tests the consistency between the equals method and the hashCode method by verifying
     * that if two objects are equal according to the equals method, their hash codes
     * should be the same.
     */
    @Test
    void testHashCodeConsistencyWithEquals() {
        // Arrange
        DewPointValue dewPointValue1 = new DewPointValue(29.0);
        DewPointValue dewPointValue2 = new DewPointValue(29.0);
        // Act
        boolean equals = dewPointValue1.equals(dewPointValue2);
        // Assert
        assertEquals(dewPointValue1.hashCode(), dewPointValue2.hashCode()
                , "The hash code should be the same for two DewPointValue objects that are equal according to the equals method");
    }

    /**
     * Tests the hashCode method by verifying that hash codes should be different
     * for objects with different dew point values.
     */
    @Test
    void testHashCodeUnequalObjects() {
        // Arrange
        DewPointValue dewPointValue1 = new DewPointValue(29.0);
        DewPointValue dewPointValue2 = new DewPointValue(30.0);
        // Act
        boolean equals = dewPointValue1.equals(dewPointValue2);
        // Assert
        assertNotEquals(dewPointValue1.hashCode(), dewPointValue2.hashCode()
                , "The hash code should be different for two DewPointValue objects with different dew point values");
    }
}