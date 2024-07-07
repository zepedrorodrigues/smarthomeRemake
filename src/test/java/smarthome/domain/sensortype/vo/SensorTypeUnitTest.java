package smarthome.domain.sensortype.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is responsible for testing the SensorTypeUnit class.
 */
class SensorTypeUnitTest {

    /**
     * Tests that the constructor does not throw an exception when a valid unit is provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidUnit() {
        //Arrange
        String validUnit = "Celsius";
        //Act & Assert
        assertDoesNotThrow(() -> new SensorTypeUnit(validUnit), "SensorTypeUnit object should be successfully created" +
                ".");
    }

    /**
     * Tests that the constructor throws an exception when a null unit is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullUnit() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeUnit(null), "SensorTypeUnit object should " +
                "not be created with null unit.");
    }

    /**
     * Tests that the constructor throws an exception when a blank unit is provided.
     */
    @Test
    void testConstructorThrowsExceptionForBlankUnit() {
        //Arrange
        String invalidUnit = " ";
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeUnit(invalidUnit), "SensorTypeUnit object " +
                "should not be created with blank unit.");
    }

    /**
     * Tests that the getUnit method returns the correct unit.
     */
    @Test
    void testGetUnitReturnsCorrectValue() {
        //Arrange
        String expected = "Celsius";
        SensorTypeUnit unit = new SensorTypeUnit(expected);
        //Act
        String result = unit.getSensorTypeUnit();
        //Assert
        assertEquals(expected, result, "Unit should be the same as the one provided in the constructor.");
    }

    /**
     * Tests that the equals method returns true when comparing the same object.
     */
    @Test
    void testEqualsReturnsTrueWhenComparingTheSameObject() {
        //Arrange
        SensorTypeUnit unit = new SensorTypeUnit("Celsius");
        //Act
        boolean result = unit.equals(unit);
        //Assert
        assertTrue(result, "The result should be true for the same object.");
    }

    /**
     * Tests that the equals method returns true when comparing different objects with the same unit.
     */
    @Test
    void testEqualsReturnsTrueWhenComparingDifferentObjectsWithTheSameUnit() {
        //Arrange
        SensorTypeUnit unit1 = new SensorTypeUnit("Celsius");
        SensorTypeUnit unit2 = new SensorTypeUnit("Celsius");
        //Act
        boolean result = unit1.equals(unit2);
        //Assert
        assertTrue(result, "The result should be true for different objects with the same unit.");
    }

    /**
     * Tests that the equals method returns false when comparing different objects with different units.
     */
    @Test
    void testEqualsReturnsFalseWhenComparingDifferentObjectsWithDifferentUnits() {
        //Arrange
        SensorTypeUnit unit1 = new SensorTypeUnit("Celsius");
        SensorTypeUnit unit2 = new SensorTypeUnit("Fahrenheit");
        //Act
        boolean result = unit1.equals(unit2);
        //Assert
        assertFalse(result, "The result should be false for different objects with different units.");
    }

    /**
     * Tests that the equals method returns false when comparing to a null object.
     */
    @Test
    void testEqualsReturnsFalseWhenComparingToNullObject() {
        //Arrange
        SensorTypeUnit unit = new SensorTypeUnit("Celsius");
        //Act
        boolean result = unit.equals(null);
        //Assert
        assertFalse(result, "The result should be false for null object.");
    }

    /**
     * Tests that the equals method returns false when comparing to a different class object.
     */
    @Test
    void testEqualsReturnsFalseWhenComparingToDifferentClassObject() {
        //Arrange
        SensorTypeUnit unit = new SensorTypeUnit("Celsius");
        //Act
        boolean result = unit.equals(new Object());
        //Assert
        assertFalse(result, "The result should be false for different class object.");
    }

    /**
     * Tests that the hashCode method returns the same value for two objects with the same unit.
     */
    @Test
    void testHashCodeReturnsSameValueForTwoObjectsWithSameUnit() {
        //Arrange
        SensorTypeUnit unit1 = new SensorTypeUnit("Celsius");
        SensorTypeUnit unit2 = new SensorTypeUnit("Celsius");
        //Act
        int hashCode1 = unit1.hashCode();
        int hashCode2 = unit2.hashCode();
        //Assert
        assertEquals(hashCode1, hashCode2, "The hash code should be the same for equal objects.");
    }

    /**
     * Tests that the hashCode method returns different values for two objects with different units.
     */
    @Test
    void testHashCodeReturnsDifferentValuesForTwoObjectsWithDifferentUnits() {
        //Arrange
        SensorTypeUnit unit1 = new SensorTypeUnit("Celsius");
        SensorTypeUnit unit2 = new SensorTypeUnit("Fahrenheit");
        //Act
        int hashCode1 = unit1.hashCode();
        int hashCode2 = unit2.hashCode();
        //Assert
        assertNotEquals(hashCode1, hashCode2, "The hash code should be different for different objects.");
    }
}