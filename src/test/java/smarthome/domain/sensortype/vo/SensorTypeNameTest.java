package smarthome.domain.sensortype.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the SensorTypeName class.
 */
class SensorTypeNameTest {

    /**
     * Test if the constructor does not throw an exception for a valid sensor type name.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidSensorTypeName() {
        //Arrange
        String sensorTypeName = "Temperature";
        //Act + Assert
        assertDoesNotThrow(() -> new SensorTypeName(sensorTypeName), "The constructor should not throw an exception " +
                "for a valid sensor type name.");
    }

    /**
     * Test if the constructor throws an exception for an invalid sensor type name.
     */
    @Test
    void testConstructorThrowsExceptionForInvalidSensorTypeName() {
        //Arrange
        String blankSensorTypeName = " ";
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeName(blankSensorTypeName), "The constructor " +
                "should throw an exception for an invalid sensor type name.");
    }

    /**
     * Test if the constructor throws an exception for a null sensor type name.
     */
    @Test
    void testConstructorThrowsExceptionForNullSensorTypeName() {
        //Arrange
        String nullSensorTypeName = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorTypeName(nullSensorTypeName), "The constructor " +
                "should throw an exception for a null sensor type name.");

    }

    /**
     * Test if the getSensorTypeName method returns the correct sensor type name.
     */
    @Test
    void testGetSensorTypeName() {
        //Arrange
        String expected = "Temperature";
        SensorTypeName sensorTypeName = new SensorTypeName(expected);
        //Act
        String result = sensorTypeName.getSensorTypeName();
        //Assert
        assertEquals(expected, result, "The getSensorTypeName method should return the correct sensor type name.");

    }

    /**
     * Test if the equals method returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        //Arrange
        SensorTypeName sensorTypeName = new SensorTypeName("Temperature");
        //Act
        boolean result = sensorTypeName.equals(sensorTypeName);
        //Assert
        assertTrue(result, "The equals method should return true for the same object.");
    }

    /**
     * Test if the equals method returns false for a different sensor type name.
     */
    @Test
    void testEqualsForDifferentSensorTypeName() {
        //Arrange
        SensorTypeName sensorTypeName = new SensorTypeName("Temperature");
        SensorTypeName sensorTypeName1 = new SensorTypeName("Humidity");
        //Act
        boolean result = sensorTypeName.equals(sensorTypeName1);
        //Assert
        assertFalse(result, "The equals method should return false for a different sensor type name.");
    }

    /**
     * Test if the equals method returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SensorTypeName sensorTypeName = new SensorTypeName("Temperature");
        //Act
        boolean result = sensorTypeName.equals(null);
        //Assert
        assertFalse(result, "The equals method should return false for a null object.");
    }

    /**
     * Test if the equals method returns true for the same type name.
     */
    @Test
    void testEqualsForSameTypeName() {
        //Arrange
        SensorTypeName sensorTypeName = new SensorTypeName("Temperature");
        SensorTypeName sensorTypeName1 = new SensorTypeName("Temperature");
        //Act
        boolean result = sensorTypeName.equals(sensorTypeName1);
        //Assert
        assertTrue(result, "The equals method should return true for the same type name.");
    }

    /**
     * Test if the equals method returns false for a different object type.
     */
    @Test
    void testEqualsForDifferentObject() {
        //Arrange
        SensorTypeName sensorTypeName = new SensorTypeName("Temperature");
        //Act
        boolean result = sensorTypeName.equals(new Object());
        //Assert
        assertFalse(result, "The equals method should return false for a different object type.");
    }

    /**
     * Test if the hashCode method returns the same value for the same object.
     */
    @Test
    void testHashCodeWhenDifferentObjectIsCompared() {
        //Arrange
        SensorTypeName sensorTypeName = new SensorTypeName("Temperature");
        SensorTypeName sensorTypeName1 = new SensorTypeName("Humidity");
        //Act
        int hashCode = sensorTypeName.hashCode();
        int hashCode1 = sensorTypeName1.hashCode();
        //Assert
        assertNotEquals(hashCode, hashCode1, "The method should return different hash codes for different objects.");
    }

    /**
     * Test if the hashCode method returns the same value for the same object.
     */
    @Test
    void testHashCodeWhenSameObjectIsCompared() {
        //Arrange
        SensorTypeName sensorTypeName = new SensorTypeName("Temperature");
        SensorTypeName sensorTypeName1 = new SensorTypeName("Temperature");
        //Act
        int result = sensorTypeName.hashCode();
        int result1 = sensorTypeName1.hashCode();
        //Assert
        assertEquals(result, result1, "The hashCode method should return the same value for the same object.");
    }
}