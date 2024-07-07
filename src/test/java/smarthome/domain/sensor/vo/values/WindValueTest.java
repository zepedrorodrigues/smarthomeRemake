package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the WindValue class.
 * Each method in this class is a test case for a specific functionality of the WindValue class.
 */
class WindValueTest {

    /**
     * Test case for WindValue constructor.
     * The test verifies that the constructor creates a WindValue object.
     */
    @Test
    void testConstructorCreatesWindValue() {
        //Act
        WindValue windValue = new WindValue(Math.PI, 1.0);
        //Assert
        assertNotNull(windValue, "WindValue object should not be null when created with valid direction and speed");
    }

    /**
     * Test case for WindValue constructor with negative direction.
     * The test verifies that an IllegalArgumentException is thrown when creating a WindValue with a negative direction.
     */
    @Test
    void testConstructorCreatesWindValueWithNegativeDirectionShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new WindValue(-1.0, 1.0),
                "WindValue constructor should " + "throw an IllegalArgumentException when the direction is negative");
    }

    /**
     * Test case for WindValue constructor with direction greater than 2*PI.
     * The test verifies that an IllegalArgumentException is thrown when creating a WindValue with a direction
     * greater than 2*PI.
     */
    @Test
    void testConstructorCreatesWindValueWithDirectionGreaterThan2PiShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new WindValue(7, 1.0), "WindValue constructor should " +
                "throw" + " an IllegalArgumentException when the direction is greater than 2*PI");
    }

    /**
     * Test case for WindValue constructor with negative speed.
     * The test verifies that an IllegalArgumentException is thrown when creating a WindValue with a negative speed.
     */
    @Test
    void testConstructorCreatesWindValueWithNegativeSpeedShouldThrowException() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new WindValue(Math.PI, -1.0), "WindValue constructor " +
                "should throw an IllegalArgumentException when the speed is negative");
    }

    /**
     * Test case for WindValue constructor with zero direction.
     * The test verifies that the constructor does not throw an exception when creating a WindValue with a direction
     * of zero.
     */
    @Test
    void testConstructorCreatesWindValueWithZeroDirectionShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> new WindValue(0.0, 1.0),
                "WindValue constructor should not throw an exception when " + "the direction is zero");
    }

    /**
     * Test case for WindValue constructor with direction equal to 2*PI.
     * The test verifies that the constructor does not throw an exception when creating a WindValue with a direction
     * equal to 2*PI.
     */
    @Test
    void testConstructorCreatesWindValueWithDirectionEqualTo2PiShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> new WindValue(2 * Math.PI, 1.0), "WindValue constructor should not throw an " +
                "exception when the direction is equal to 2*PI");
    }

    /**
     * Test case for WindValue constructor with zero speed.
     * The test verifies that the constructor does not throw an exception when creating a WindValue with a speed of
     * zero.
     */
    @Test
    void testConstructorCreatesWindValueWithZeroSpeedShouldNotThrowException() {
        //Act & Assert
        assertDoesNotThrow(() -> new WindValue(Math.PI, 0.0),
                "WindValue constructor should not throw an exception " + "when the speed is zero");
    }


    /**
     * Test case for the equals method when the same object is passed as a parameter.
     * The test verifies that the equals method returns true when the same object is passed as a parameter.
     */
    @Test
    void testEqualsForSameObjectShouldReturnTrue() {
        // Arrange
        WindValue windValue = new WindValue(Math.PI / 2, 1.0);
        // Act
        boolean result = windValue.equals(windValue);
        // Assert
        assertTrue(result, "The equals method should return true when comparing a WindValue object to itself");
    }

    /**
     * Test case for the equals method when null is passed as a parameter.
     * The test verifies that the equals method returns false when null is passed as a parameter.
     */
    @Test
    void testEqualsForNullObjectShouldReturnFalse() {
        // Arrange
        WindValue windValue = new WindValue(Math.PI / 2, 1.0);
        ;
        // Act
        boolean result = windValue.equals(null);
        // Assert
        assertFalse(result, "The equals method should return false when comparing a WindValue object to null");
    }

    /**
     * Test case for the equals method when a different object is passed as a parameter.
     * The test verifies that the equals method returns false when a different object is passed as a parameter.
     */
    @Test
    void testEqualsForDifferentObjectShouldReturnFalse() {
        // Arrange
        WindValue windValue = new WindValue(Math.PI / 2, 1.0);
        // Act
        boolean result = windValue.equals(new Object());
        // Assert
        assertFalse(result, "The equals method should return false when comparing a WindValue object to an object of "
                + "a different class");
    }

    /**
     * Test case for the equals method when an object with a different direction value is passed as a parameter.
     * The test verifies that the equals method returns false when an object with a different direction value is
     * passed as a parameter.
     */
    @Test
    void testEqualsForDifferentDirectionValueShouldReturnFalse() {
        // Arrange
        WindValue windValue1 = new WindValue(Math.PI / 2, 1.0);
        WindValue windValue2 = new WindValue(Math.PI, 1.0);
        // Act
        boolean result = windValue1.equals(windValue2);
        // Assert
        assertFalse(result, "The equals method should return false when comparing two WindValue objects with " +
                "different direction values");

    }

    /**
     * Test case for the equals method when an object with a different speed value is passed as a parameter.
     * The test verifies that the equals method returns false when an object with a different speed value is passed
     * as a parameter.
     */
    @Test
    void testequalsForDifferentSpeedValueShouldReturnFalse() {
        // Arrange
        WindValue windValue1 = new WindValue(Math.PI / 2, 1.0);
        WindValue windValue2 = new WindValue(Math.PI / 2, 10.0);
        // Act
        boolean result = windValue1.equals(windValue2);
        // Assert
        assertFalse(result, "The equals method should return false when comparing two WindValue objects with " +
                "different speed values");
    }

    /**
     * Test case for the equals method when an object with the same wind value is passed as a parameter.
     * The test verifies that the equals method returns true when an object with the same wind value is passed as a
     * parameter.
     */
    @Test
    void testEqualsForSameWindValueShouldReturnTrue() {
        // Arrange
        WindValue windValue1 = new WindValue(Math.PI / 2, 1.0);
        WindValue windValue2 = new WindValue(Math.PI / 2, 1.0);
        // Act
        boolean result = windValue1.equals(windValue2);
        // Assert
        assertTrue(result,
                "The equals method should return true when comparing two WindValue objects with the same " + "wind " +
                        "value");
    }

    /**
     * Test case for the hashCode method when the hash code is the same for two WindValue objects with the same wind
     * value.
     */

    @Test
    void testHashCodeForSameWindValue() {
        // Arrange
        WindValue windValue1 = new WindValue(Math.PI / 2, 1.0);
        WindValue windValue2 = new WindValue(Math.PI / 2, 1.0);
        // Act
        int result1 = windValue1.hashCode();
        int result2 = windValue2.hashCode();
        // Assert
        assertEquals(result1, result2,
                "The hashCode method should return the same value for two WindValue objects " + "with the same wind " +
                        "value");
    }

    /**
     * Test case for the hashCode method when the hash code is different for two WindValue objects with different
     * wind values.
     */
    @Test
    void testHashCodeForDifferentWindValue() {
        // Arrange
        WindValue windValue1 = new WindValue(Math.PI / 2, 1.0);
        WindValue windValue2 = new WindValue(Math.PI, 2.0);
        // Act
        int result1 = windValue1.hashCode();
        int result2 = windValue2.hashCode();
        // Assert
        assertNotEquals(result1, result2, "The hashCode method should return different values for two WindValue " +
                "objects with different wind values");
    }

    /**
     * Test case for the valueToString method when the wind value has positive values.
     * The test verifies that the valueToString method returns the correct string representation of the wind value.
     */
    @Test
    void testValueToStringReturnsCorrectStringForPositiveValues() {
        //Arrange
        double expectedDirection = 1.0;
        double expectedSpeed = 1.0;
        String expected = "1.0::1.0";
        WindValue windValue = new WindValue(expectedDirection, expectedSpeed);
        //Act
        String result = windValue.valueToString();
        //Assert
        assertEquals(expected, result,
                "The valueToString method should return the correct string representation of " + "the wind value with" +
                        " positive values");

    }

    /**
     * Test case for the valueToString method when the Direction is equal to 2*PI.
     * The test verifies that the valueToString method returns the correct string representation of the wind value.
     */
    @Test
    void testValueToStringReturnsCorrectStringWithDirectionEqualTo2Pi() {
        //Arrange
        double expectedDirection = 2 * Math.PI;
        double expectedSpeed = 1.0;
        String expected = "6.283185307179586::1.0";
        WindValue windValue = new WindValue(expectedDirection, expectedSpeed);
        //Act
        String result = windValue.valueToString();
        //Assert
        assertEquals(expected, result,
                "The valueToString method should return the correct string representation of " + "the wind value with" +
                        " direction equal to 2*PI");
    }
}