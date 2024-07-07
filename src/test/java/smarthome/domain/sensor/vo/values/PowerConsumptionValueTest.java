package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;
import smarthome.domain.sensortype.vo.SensorTypeName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class represents the tests of the PowerConsumptionValue class.
 */
class PowerConsumptionValueTest {

    /**
     * Test the constructor of the PowerConsumptionValue class.
     * The value is valid, so it should not throw an exception.
     */
    @Test
    void testConstructorValidShouldNotThrowException() {
        //Arrange
        double expectedNumber = 15.0;
        String expected = "15.0";
        //Act
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        //Assert
        assertEquals(expected, powerConsumptionValue.valueToString(), "The constructor" + " should not throw an " +
                "exception for a valid value.");
    }

    /**
     * Test the constructor of the PowerConsumptionValue class.
     * The value is invalid, so it should throw an exception.
     */
    @Test
    void testConstructorInvalidShouldThrowException() {
        //Arrange
        double expectedNumber = -2.0;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PowerConsumptionValue(expectedNumber), "The " +
                "constructor should throw an exception for an invalid value.");
    }

    /**
     * Test the constructor of the PowerConsumptionValue class.
     * The value is the border value, so it should not throw an exception.
     */
    @Test
    void testConstructorValidBorderShouldNotThrowException(){
        //Arrange
        double expectedNumber = 0.0;
        String expected = "0.0";
        //Act
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        //Assert
        assertEquals(expected, powerConsumptionValue.valueToString(), "The constructor " + "should not throw an " +
                "exception for a border value.");
    }

    /**
     * Test the valueToString method of the PowerConsumptionValue class.
     * It should return the value as a string.
     */
    @Test
    void valueToString() {
        //Arrange
        double expectedNumber = 15.0;
        String expected = "15.0";
        //Act
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        //Assert
        assertEquals(expected, powerConsumptionValue.valueToString(), "The valueToString " + "method should return " +
                "the value as a string.");
    }

    /**
     * Test the equals method of the PowerConsumptionValue class.
     * The object is null, so it should return false.
     */

    @Test
    void equalsNullObjectShouldReturnFalse() {
        //Arrange
        double expectedNumber = 15.0;
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        //Act
        boolean result = powerConsumptionValue.equals(null);
        // Assert
        assertFalse(result, "The equals method should return false when comparing with a null object.");
    }

    /**
     * Test the equals method of the PowerConsumptionValue class.
     * The object is of a different class, so it should return false.
     */

    @Test
    void equalsDifferentObjectShouldReturnFalse() {
        //Arrange
        double expectedNumber = 15.0;
        String temperature = "Temperature";
        //Act
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        SensorTypeName sensorTypeName = new SensorTypeName(temperature);
        //Assert
        assertNotEquals(powerConsumptionValue, sensorTypeName, "The equals method" + " should return false when " +
                "comparing with an object of a different type.");
    }

    /**
     * Test the equals method of the PowerConsumptionValue class.
     * The value inside the object is different, so it should return false.
     */
    @Test
    void equalsDifferentValueShouldReturnFalse() {
        //Arrange
        double expectedNumber = 15.0;
        double expectedNumber1 = 20.0;
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        PowerConsumptionValue powerConsumptionValue2 = new PowerConsumptionValue(expectedNumber1);
        //Act
        boolean result = powerConsumptionValue.equals(powerConsumptionValue2);
        // Assert
        assertFalse(result, "The equals method should return false for two different values.");
    }

    /**
     * Test the equals method of the PowerConsumptionValue class.
     * The value inside the object is the same, so it should return true.
     */
    @Test
    void equalsSameValueShouldReturnTrue() {
        //Arrange
        double expectedNumber = 15.0;
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        PowerConsumptionValue powerConsumptionValue2 = new PowerConsumptionValue(expectedNumber);
        //Act
        boolean result = powerConsumptionValue.equals(powerConsumptionValue2);
        // Assert
        assertTrue(result, "The equals method should return true for two equal values.");
    }

    /**
     * Test the equals method of the PowerConsumptionValue class.
     * The object is the same, so it should return true.
     */

    @Test
    void equalsSameObjectShouldReturnTrue() {
        //Arrange
        double expectedNumber = 15.0;
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        //Act
        boolean result = powerConsumptionValue.equals(powerConsumptionValue);
        // Assert
        assertTrue(result, "The equals method should return true when comparing an object with itself.");
    }

    /**
     * Test the hashCode method of the PowerConsumptionValue class.
     * The hash code should be the same for the same value.
     */
    @Test
    void hashCodeSameValueShouldReturnTrue() {
        //Arrange
        double expectedNumber = 15.0;
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        PowerConsumptionValue powerConsumptionValue2 = new PowerConsumptionValue(expectedNumber);
        //Act
        int result = powerConsumptionValue.hashCode();
        int result2 = powerConsumptionValue2.hashCode();
        // Assert
        assertEquals(result, result2, "Two different PowerConsumptionValue instances " + "with the same value should " +
                "have the same hash code.");
    }

    /**
     * Test the hashCode method of the PowerConsumptionValue class.
     * The hash code should be different for different values.
     */
    @Test
    void hashCodeDifferentValueShouldReturnFalse() {
        //Arrange
        double expectedNumber = 15.0;
        double expectedNumber1 = 20.0;
        PowerConsumptionValue powerConsumptionValue = new PowerConsumptionValue(expectedNumber);
        PowerConsumptionValue powerConsumptionValue2 = new PowerConsumptionValue(expectedNumber1);
        //Act
        int result = powerConsumptionValue.hashCode();
        int result2 = powerConsumptionValue2.hashCode();
        // Assert
        assertNotEquals(result, result2, "Two different PowerConsumptionValue instances " + "with different values " +
                "should not have the same hash code.");
    }
}