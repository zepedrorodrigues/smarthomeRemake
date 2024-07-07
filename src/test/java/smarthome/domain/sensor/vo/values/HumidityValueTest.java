package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Tests for the HumidityValue class
 */
class HumidityValueTest {

    /**
     * Test that the constructor creates a HumidityValue object
     */
    @Test
    void testConstructorCreatesHumidityValue() {
        //Act
        HumidityValue humidityValue = new HumidityValue(27.0);
        //Assert
        assertNotNull(humidityValue
                , "HumidityValue object should not be null");
    }

    /**
     * Test that the valueToString method returns the correct string
     * for a valid humidity value (between 0 and 100)
     */
    @Test
    void testValueToStringReturnsCorrectString() {
        // Arrange
        String expected = "27.0";
        HumidityValue humidityValue = new HumidityValue(27.0);
        // Act
        String result = humidityValue.valueToString();
        // Assert
        assertEquals(expected, result
                , "The valueToString method should return the correct string for a valid value");
    }

    /**
     * Test that the equals method returns true for the same object
     */
    @Test
    void testEqualsForSameObjectEqualsCalledOnItselfShouldReturnTrue() {
        // Arrange
        HumidityValue humidityValue = new HumidityValue(27.0);
        // Act
        boolean result = humidityValue.equals(humidityValue);
        // Assert
        assertTrue(result
                , "The equals method should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        HumidityValue humidityValue = new HumidityValue(27.0);
        // Act
        boolean result = humidityValue.equals(null);
        // Assert
        assertFalse(result
                , "The equals method should return false for a null object");
    }

    /**
     * Test that the equals method returns false for a different object
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        HumidityValue humidityValue = new HumidityValue(27.0);
        // Act
        boolean result = humidityValue.equals(new Object());
        // Assert
        assertFalse(result
                , "The equals method should return false for an object of a different class");
    }

    /**
     * Test that the equals method returns true for the same HumidityValue
     */
    @Test
    void testEqualsForSameHumidityValue() {
        // Arrange
        double value = 27.0;
        HumidityValue humidityValue1 = new HumidityValue(value);
        HumidityValue humidityValue2 = new HumidityValue(value);
        // Act
        boolean result = humidityValue1.equals(humidityValue2);
        // Assert
        assertTrue(result
                , "The equals method should return true for the same HumidityValue");
    }

    /**
     * Test that the equals method returns false for different HumidityValues
     */
    @Test
    void testEqualsForDifferentHumidityValues() {
        // Arrange
        double valueA = 27.0;
        double valueB = 72.0;
        HumidityValue humidityValue1 = new HumidityValue(valueA);
        HumidityValue humidityValue2 = new HumidityValue(valueB);
        // Act
        boolean result = humidityValue1.equals(humidityValue2);
        // Assert
        assertFalse(result
                , "The equals method should return false for different HumidityValues");
    }

    /**
     * Test the hashCode method returns the same hash code for identical HumidityValues.
     *
     * This test creates two separate HumidityValue objects with the same value
     * and then compare their hash codes.
     * This test is explicit in creating two separate instances of the object
     * to ensure their hash codes are consistent.
     *
     */
    @Test
    void testHashCode() {
        // Arrange
        double value = 27.0;
        HumidityValue humidityValue1 = new HumidityValue(value);
        HumidityValue humidityValue2 = new HumidityValue(value);
        // Act
        int hashCode1 = humidityValue1.hashCode();
        int hashCode2 = humidityValue2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2
                , "The hashCode method should return the same hash code for identical HumidityValues");
    }

    /**
     * Test that the hashCode method returns the same hash code for identical HumidityValues.
     *
     * This test creates only one HumidityValue object and then compares its hash code against itself.
     * Tests the consistency of the hashCode method by verifying that the hash code
     * remains the same for the same object across multiple invocations.
     */
    @Test
    void testHashCodeConsistency() {
        // Arrange
        HumidityValue humidityValue = new HumidityValue(27.0);
        // Act
        int hashCode1 = humidityValue.hashCode();
        int hashCode2 = humidityValue.hashCode();
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
        HumidityValue humidityValue1 = new HumidityValue(27.0);
        HumidityValue humidityValue2 = new HumidityValue(27.0);
        // Act
        boolean equals = humidityValue1.equals(humidityValue2);
        // Assert
        assertEquals(humidityValue1.hashCode(), humidityValue2.hashCode()
                , "The hash code should be the same for two HumidityValue objects that are equal according to the equals method");
    }

    /**
     * Tests the hashCode method by verifying that hash codes should be different
     * for objects with different dew point values.
     */
    @Test
    void testHashCodeUnequalObjects() {
        // Arrange
        HumidityValue humidityValue1 = new HumidityValue(27.0);
        HumidityValue humidityValue2 = new HumidityValue(72.0);
        // Act
        boolean equals = humidityValue1.equals(humidityValue2);
        // Assert
        assertNotEquals(humidityValue1.hashCode(), humidityValue2.hashCode()
                , "The hash code should be different for two HumidityValue objects with different humidity values");
    }
}