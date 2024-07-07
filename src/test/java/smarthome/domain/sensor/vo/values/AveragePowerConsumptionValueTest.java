package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the AveragePowerConsumptionValue class.
 * It tests the behavior of the class under different scenarios such as when the constructor parameters are valid or invalid.
 */
class AveragePowerConsumptionValueTest {

    /**
     * This test verifies that the constructor of AveragePowerConsumptionValue class doesn't throw an exception when the value is valid.
     */
    @Test
    void testConstructorForValidValue() {
        // Arrange
        double defaultValue = 10.0;

        // Act
        AveragePowerConsumptionValue myValue = new AveragePowerConsumptionValue(defaultValue);

        // Assert
        assertNotNull(myValue);
    }

    /**
     * This test verifies that the constructor of AveragePowerConsumptionValue class throws an exception when the value is invalid.
     */
    @Test
    void testConstructorForInvalidValue() {
        // Arrange
        double invalidValue = -10.0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new AveragePowerConsumptionValue(invalidValue));
    }

    /**
     * This test verifies that the constructor of AveragePowerConsumptionValue class doesn't throw an exception when the value is at the border (0.0).
     */
    @Test
    void testConstructorForBorderValue() {
        // Arrange
        double borderValue = 0.0;

        // Act
        AveragePowerConsumptionValue myValue = new AveragePowerConsumptionValue(borderValue);

        // Assert
        assertNotNull(myValue);
    }

    /**
     * This test verifies that the equals method of AveragePowerConsumptionValue class returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        double defaultValue = 10.0;
        AveragePowerConsumptionValue myValue = new AveragePowerConsumptionValue(defaultValue);

        // Act
        boolean result = myValue.equals(myValue);

        // Assert
        assertTrue(result);
    }

    /**
     * This test verifies that the equals method of AveragePowerConsumptionValue class returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        double defaultValue = 10.0;
        AveragePowerConsumptionValue myValue = new AveragePowerConsumptionValue(defaultValue);

        // Act
        boolean result = myValue.equals(null);

        // Assert
        assertFalse(result);
    }

    /**
     * This test verifies that the equals method of AveragePowerConsumptionValue class returns false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        double defaultValue = 10.0;
        AveragePowerConsumptionValue myValue = new AveragePowerConsumptionValue(defaultValue);

        // Act
        boolean result = myValue.equals(new Object());

        // Assert
        assertFalse(result);
    }

    /**
     * This test verifies that the equals method of AveragePowerConsumptionValue class returns true for the same value.
     */
    @Test
    void testEqualsForSameValue() {
        // Arrange
        double defaultValue = 10.0;
        AveragePowerConsumptionValue myValue1 = new AveragePowerConsumptionValue(defaultValue);
        AveragePowerConsumptionValue myValue2 = new AveragePowerConsumptionValue(defaultValue);

        // Act
        boolean result = myValue1.equals(myValue2);

        // Assert
        assertTrue(result);
    }

    /**
     * This test verifies that the equals method of AveragePowerConsumptionValue class returns false for different values.
     */
    @Test
    void testEqualsForDifferentValue() {
        // Arrange
        double defaultValue1 = 10.0;
        double defaultValue2 = 20.0;
        AveragePowerConsumptionValue myValue1 = new AveragePowerConsumptionValue(defaultValue1);
        AveragePowerConsumptionValue myValue2 = new AveragePowerConsumptionValue(defaultValue2);

        // Act
        boolean result = myValue1.equals(myValue2);

        // Assert
        assertFalse(result);
    }

    /**
     * This test verifies that the hashCode method of AveragePowerConsumptionValue class returns the same hash code for the same value.
     */
    @Test
    void testHashCodeForSameValue() {
        // Arrange
        double defaultValue = 10.0;
        AveragePowerConsumptionValue myValue1 = new AveragePowerConsumptionValue(defaultValue);
        AveragePowerConsumptionValue myValue2 = new AveragePowerConsumptionValue(defaultValue);

        // Act
        int hashCode1 = myValue1.hashCode();
        int hashCode2 = myValue2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    /**
     * This test verifies that the hashCode method of AveragePowerConsumptionValue class returns different hash codes for different values.
     */
    @Test
    void testHashCodeForDifferentValue() {
        // Arrange
        double defaultValue1 = 10.0;
        double defaultValue2 = 20.0;
        AveragePowerConsumptionValue myValue1 = new AveragePowerConsumptionValue(defaultValue1);
        AveragePowerConsumptionValue myValue2 = new AveragePowerConsumptionValue(defaultValue2);

        // Act
        int hashCode1 = myValue1.hashCode();
        int hashCode2 = myValue2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

    /**
     * This test verifies that the valueToString method of AveragePowerConsumptionValue class returns the correct string representation for a positive value.
     */
    @Test
    void testValueToStringWithPositiveValue() {
        // Arrange
        double defaultValue = 10.0;
        AveragePowerConsumptionValue myValue = new AveragePowerConsumptionValue(defaultValue);
        String expected = "10.0";

        // Act
        String result = myValue.valueToString();

        // Assert
        assertEquals(expected, result);
    }
}