package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit Tests for the OnOffValue class
 */
class OnOffValueTest {

    /**
     * Test that the constructor creates an OnOffValue object
     */
    @Test
    void testConstructorCreatesOnOffValue() {
        //Act
        OnOffValue onOffValue = new OnOffValue(false);

        //Assert
        assertNotNull(onOffValue,
                "Constructor should create an OnOffValue object");
    }

    /**
     * Test that the equals method returns true for the same object
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        OnOffValue onOffValue = new OnOffValue(false);

        // Act - Assert
        assertEquals(onOffValue, onOffValue,
                "Equals should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        OnOffValue onOffValue = new OnOffValue(false);
        OnOffValue nullOnOffValue = null;

        // Act - Assert
        assertNotEquals(onOffValue, nullOnOffValue,
                "Equals should return false for a null object");
    }

    /**
     * Test that the equals method returns false for a different object
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        OnOffValue onOffValue = new OnOffValue(false);

        // Act - Assert
        assertNotEquals(onOffValue, new Object(),
                "Equals should return false for a different object");
    }

    /**
     * Test that the equals method returns true for the same OnOffValue
     */
    @Test
    void testEqualsForSameOnOffValue() {
        // Arrange
        boolean value = false;
        OnOffValue onOffValue1 = new OnOffValue(value);
        OnOffValue onOffValue2 = new OnOffValue(value);

        // Act - Assert
        assertEquals(onOffValue1, onOffValue2,
                "Equals should return true for the same OnOffValue");
    }

    /**
     * Test that the equals method returns false for different OnOffValues
     */
    @Test
    void testEqualsForDifferentOnOffValues() {
        // Arrange
        boolean valueA = false;
        boolean valueB = true;
        OnOffValue onOffValue1 = new OnOffValue(valueA);
        OnOffValue onOffValue2 = new OnOffValue(valueB);

        // Act - Assert
        assertNotEquals(onOffValue1, onOffValue2,
                "Equals should return false for different OnOffValues");
    }

    /**
     * Test the hashCode method of the class OnOffValue.
     * The method should return the same hash code for the same on/off value.
     */
    @Test
    void testHashCodeForSameOnOffValue() {
        // Arrange
        boolean value = false;
        OnOffValue onOffValue1 = new OnOffValue(value);
        OnOffValue onOffValue2 = new OnOffValue(value);

        // Act
        int hashCode1 = onOffValue1.hashCode();
        int hashCode2 = onOffValue2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "HashCode should return the same hash code for the same on/off value");
    }

    /**
     * Test the hashCode method of the class OnOffValue.
     * The method should return the same hash code for the different on/off value.
     */
    @Test
    void testHashCodeForDifferentOnOffValue() {
        // Arrange
        boolean valueA = false;
        boolean valueB = true;
        OnOffValue onOffValue1 = new OnOffValue(valueA);
        OnOffValue onOffValue2 = new OnOffValue(valueB);

        // Act
        int hashCode1 = onOffValue1.hashCode();
        int hashCode2 = onOffValue2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "HashCode should return different hash code for the different on/off value");
    }

    /**
     * Test that the valueToString method returns the correct string
     * for an OnOffValue with a value of false
     */
    @Test
    void testValueToStringReturnsCorrectStringFalse() {
        // Arrange
        String expected = "false";
        OnOffValue onOffValue = new OnOffValue(false);

        // Act
        String result = onOffValue.valueToString();

        // Assert
        assertEquals(expected, result,
                "ValueToString should return the correct string for an OnOffValue with a value of false");
    }

    /**
     * Test that the valueToString method returns the correct string
     * for an OnOffValue with a value of true
     */
    @Test
    void testValueToStringReturnsCorrectStringTrue() {
        // Arrange
        String expected = "true";
        OnOffValue onOffValue = new OnOffValue(true);

        // Act
        String result = onOffValue.valueToString();

        // Assert
        assertEquals(expected, result,
                "ValueToString should return the correct string for an OnOffValue with a value of true");
    }

}