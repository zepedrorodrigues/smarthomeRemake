package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit Tests for the TemperatureValue class
 */
class TemperatureValueTest {

    /**
     * Test that the constructor creates a TemperatureValue object
     */
    @Test
    void testConstructorCreatesTemperatureValue() {
        //Act
        TemperatureValue temperatureValue = new TemperatureValue(25.0);

        //Assert
        assertNotNull(temperatureValue,
                "The constructor should create a TemperatureValue object");
    }

    /**
     * Test that the equals method returns true for the same object
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        TemperatureValue temperatureValue = new TemperatureValue(25.0);

        // Act - Assert
        assertEquals(temperatureValue, temperatureValue,
                "The equals method should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        TemperatureValue temperatureValue = new TemperatureValue(25.0);
        TemperatureValue nullTemperatureValue = null;

        // Act - Assert
        assertNotEquals(temperatureValue, nullTemperatureValue,
                "The equals method should return false for a null object");
    }

    /**
     * Test that the equals method returns false for a different object
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        TemperatureValue temperatureValue = new TemperatureValue(25.0);

        // Act - Assert
        assertNotEquals(temperatureValue, new Object(),
                "The equals method should return false for a different object");
    }

    /**
     * Test that the equals method returns true for the same TemperatureValue
     */
    @Test
    void testEqualsForSameTemperatureValue() {
        // Arrange
        double value = 25.0;
        TemperatureValue temperatureValue1 = new TemperatureValue(value);
        TemperatureValue temperatureValue2 = new TemperatureValue(value);

        // Act - Assert
        assertEquals(temperatureValue1, temperatureValue2,
                "The equals method should return true for the same TemperatureValue");
    }

    /**
     * Test that the equals method returns false for different TemperatureValues
     */
    @Test
    void testEqualsForDifferentTemperatureValues() {
        // Arrange
        double valueA = 25.0;
        double valueB = 30.0;
        TemperatureValue temperatureValue1 = new TemperatureValue(valueA);
        TemperatureValue temperatureValue2 = new TemperatureValue(valueB);

        // Act - Assert
        assertNotEquals(temperatureValue1, temperatureValue2,
                "The equals method should return false for different TemperatureValues");
    }

    /**
     * Test the hashCode method of the class TemperatureValue.
     * The method should return the same hash code for the same temperature value.
     */
    @Test
    void testHashCodeForSameTemperatureValue() {
        // Arrange
        double value = 25.0;
        TemperatureValue temperatureValue1 = new TemperatureValue(value);
        TemperatureValue temperatureValue2 = new TemperatureValue(value);

        // Act
        int hashCode1 = temperatureValue1.hashCode();
        int hashCode2 = temperatureValue2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The hashCode method should return the same hash code for the same temperature value");
    }

    /**
     * Test the hashCode method of the class TemperatureValue.
     * The method should return different hash code for the different temperature value.
     */
    @Test
    void testHashCodeForDifferentTemperatureValues() {
        // Arrange
        double valueA = 25.0;
        double valueB = 30.0;
        TemperatureValue temperatureValue1 = new TemperatureValue(valueA);
        TemperatureValue temperatureValue2 = new TemperatureValue(valueB);

        // Act
        int hashCode1 = temperatureValue1.hashCode();
        int hashCode2 = temperatureValue2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return different hash code for the different temperature value");
    }

    /**
     * Test that the valueToString method returns the correct string
     * for a positive temperature value
     */
    @Test
    void testValueToStringReturnsCorrectStringForPositiveValue() {
        // Arrange
        String expected = "25.0";
        TemperatureValue temperatureValue = new TemperatureValue(25.0);

        // Act
        String result = temperatureValue.valueToString();

        // Assert
        assertEquals(expected, result,
                "The valueToString method should return the correct string for a positive temperature value");
    }

    /**
     * Test that the valueToString method returns the correct string
     * for a negative temperature value
     */
    @Test
    void testValueToStringReturnsCorrectStringForNegativeValue() {
        // Arrange
        String expected = "-5.0";
        TemperatureValue temperatureValue = new TemperatureValue(-5.0);

        // Act
        String result = temperatureValue.valueToString();

        // Assert
        assertEquals(expected, result,
                "The valueToString method should return the correct string for a negative temperature value");
    }
}