package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScalePercentageValueTest {


    /**
     * Verifies the equals method returns true when comparing the same object with the same value.
     */
    @Test
    void testEqualsWhenSameObjectWithSameValueThenReturnTrue() {
        // Arrange
        double value = 50.0;
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        // Act
        boolean result = scalePercentageValue.equals(scalePercentageValue);
        // Assert
        assertTrue(result,
                "The equals method should return true when comparing the same object with the same value.");
    }

    /**
     * Verifies the equals method returns false when comparing the same object with a different value.
     */
    @Test
    void testEqualsWhenDifferentInstanceWithSameValueThenReturnTrue() {
        // Arrange
        double value = 50.0;
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        ScalePercentageValue scalePercentageValue2 = new ScalePercentageValue(value);
        // Act
        boolean result = scalePercentageValue.equals(scalePercentageValue2);
        // Assert
        assertTrue(result,
                "The equals method should return true when comparing the same object with the same value.");
    }

    /**
     * Verifies the equals method returns false when comparing different objects with different values.
     */
    @Test
    void testEqualsWhenDifferentInstanceWithDifferentValueThenReturnFalse() {
        // Arrange
        double value = 50.0;
        double value2 = 60.0;
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        ScalePercentageValue scalePercentageValue2 = new ScalePercentageValue(value2);
        // Act
        boolean result = scalePercentageValue.equals(scalePercentageValue2);
        // Assert
        assertFalse(result,
                "The equals method should return false when comparing different objects with different values.");
    }

    /**
     * Verifies the equals method returns false when comparing the object with a different type of object.
     */
    @Test
    void testEqualsWhenDifferentObjectThenReturnFalse() {
        // Arrange
        double value = 50.0;
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        Object object = new Object();
        // Act
        boolean result = scalePercentageValue.equals(object);
        // Assert
        assertFalse(result,
                "Should return false when comparing the object with a different type of object.");
    }

    /**
     * Verifies the equals method returns false when comparing the object with null.
     */
    @Test
    void testEqualsWhenNullObjectThenReturnFalse() {
        // Arrange
        double value = 50.0;
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        // Act
        boolean result = scalePercentageValue.equals(null);
        // Assert
        assertFalse(result,
                "The equals method should return false when comparing the object with null.");
    }

    /**
     * Verifies the ScalePercentageValue constructor is instantiating the object with
     * a valid value (positive number).
     */
    @Test
    void valueConstructorInstantiationWhenValueIsPositiveThenObjectIsInstantiated() {
        // Arrange
        double value = 50.0;
        // Act
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        // Assert
        assertNotNull(scalePercentageValue,
                "Should instantiate the object with a valid value (positive number).");
    }

    /**
     * Verifies the ScalePercentageValue constructor is instantiating the object with a valid
     * value (negative number).
     */
    @Test
    void valueConstructorInstantiationWhenValueIsNegativeThenObjectIsInstantiated() {
        // Arrange
        double value = -50.3;
        // Act
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        // Assert
        assertNotNull(scalePercentageValue,
                "Should instantiate the object with a valid value (negative number).");
    }

    /**
     * Verifies the ScalePercentageValue constructor is instantiating the object with a valid value (zero).
     */
    @Test
    void valueConstructorInstantiation() {
        // Arrange
        double value = 0.0;
        // Act
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        // Assert
        assertNotNull(scalePercentageValue,
                "Should instantiate the object with a valid value (zero).");
    }

    /**
     * Verifies the valueToString method generates the correct string representation of a positive number
     * (representing a
     * percentage value).
     */
    @Test
    void testValueToString_positiveNumber() {
        // Arrange
        double value = 50.5;
        String valueExpected = "50.5";
        // Act
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        String valueResult = scalePercentageValue.valueToString();
        // Assert
        assertEquals(valueExpected, valueResult,
                "Should generate the correct string representation of a positive number.");
    }

    /**
     * Verifies the valueToString method generates the correct string representation of a negative number
     * (representing a
     * percentage value).
     */
    @Test
    void testValueToString_negativeNumber() {
        // Arrange
        double value = -5.4;
        String valueExpected = "-5.4";
        // Act
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        String valueResult = scalePercentageValue.valueToString();
        // Assert
        assertEquals(valueExpected, valueResult,
                "Should generate the correct string representation of a negative number.");
    }

    /**
     * Verifies the valueToString method generates the correct string representation of zero (representing a
     * percentage value).
     */
    @Test
    void testValueToString_zeroNumber() {
        // Arrange
        double value = 0;
        String valueExpected = "0.0";
        // Act
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        String valueResult = scalePercentageValue.valueToString();
        // Assert
        assertEquals(valueExpected, valueResult,
                "Should generate the correct string representation of zero.");
    }

    /**
     * Test the method hashCode of the class ScalePercentageValue when
     * the object is an instance of ScalePercentageValue and has the same value
     * the hashcode should be the same
     */
    @Test
    void testHashCodeWhenSameObjectWithSameValue() {
        // Arrange
        double value = 50.0;
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        ScalePercentageValue scalePercentageValue2 = new ScalePercentageValue(value);
        // Act
        int result = scalePercentageValue.hashCode();
        int result2 = scalePercentageValue2.hashCode();
        // Assert
        assertEquals(result, result2,
                "The hashcode should be the same when the object is an instance of ScalePercentageValue" +
                        "and has the same value.");
    }

    /**
     * Test the method hashCode of the class ScalePercentageValue when
     * the object is an instance of ScalePercentageValue and has a different value
     * the hashcode should be different
     */
    @Test
    void testHashCodeWhenSameObjectWithDifferentValue() {
        // Arrange
        double value = 50.0;
        double value2 = 60.0;
        ScalePercentageValue scalePercentageValue = new ScalePercentageValue(value);
        ScalePercentageValue scalePercentageValue2 = new ScalePercentageValue(value2);
        // Act
        int result = scalePercentageValue.hashCode();
        int result2 = scalePercentageValue2.hashCode();
        // Assert
        assertNotEquals(result, result2,
                "The hashcode should be different when the object is an instance of ScalePercentageValue" +
                        "and has a different value.");
    }


}