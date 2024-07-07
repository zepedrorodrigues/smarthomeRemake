package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the SolarIrradianceValue class.
 */
class SolarIrradianceValueTest {

    /**
     * Test to ensure that the constructor does not throw an exception when a valid value is provided.
     */
    @Test
    void testConstructorValidShouldNotThrowException() {
        //Arrange
        double expectedNumber = 100.0;
        String expected = "100.0";
        //Act
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        //Assert
        assertEquals(expected, solarIrradianceValue.valueToString(), "The value should be 100.0");
    }

    /**
     * Test to ensure that the constructor throws an exception when an invalid value is provided.
     */
    @Test
    void testConstructorInvalidShouldThrowException() {
        //Arrange
        double expectedNumber = -100.0;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SolarIrradianceValue(expectedNumber), "The value " +
                "should be invalid");
    }

    /**
     * Test to ensure that the constructor does not throw an exception when the border value (0.0) is provided.
     */
    @Test
    void testConstructorValidBorderShouldNotThrowException(){
        //Arrange
        double expectedNumber = 0.0;
        String expected = "0.0";
        //Act
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        //Assert
        assertEquals(expected, solarIrradianceValue.valueToString(), "The value should be 0.0");
    }

    /**
     * Test to ensure that the equals method returns false when a null object is provided.
     */
    @Test
    void equalsNullObjectShouldReturnFalse() {
        //Arrange
        double expectedNumber = 100.0;
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        //Act
        boolean result = solarIrradianceValue.equals(null);
        //Assert
        assertFalse(result, "The result should be false");
    }

    /**
     * Test to ensure that the equals method returns false when a different object is provided.
     */
    @Test
    void equalsDifferenteObjectShouldReturnFalse() {
        //Arrange
        double expectedNumber = 100.0;
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        //Act
        boolean result = solarIrradianceValue.equals(new Object());
        //Assert
        assertFalse(result, "The result should be false");
    }

    /**
     * Test to ensure that the equals method returns false when a SolarIrradianceValue with a different value is provided.
     */
    @Test
    void equalsDifferentValueShouldReturnFalse() {
        //Arrange
        double expectedNumber = 150.0;
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        SolarIrradianceValue solarIrradianceValue2 = new SolarIrradianceValue(200.0);
        //Act
        boolean result = solarIrradianceValue.equals(solarIrradianceValue2);
        //Assert
        assertFalse(result, "The result should be false");
    }

    /**
     * Test to ensure that the equals method returns true when a SolarIrradianceValue with the same value is provided.
     */
    @Test
    void equalsValueShouldReturnTrue() {
        //Arrange
        double expectedNumber = 139.0;
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        SolarIrradianceValue solarIrradianceValue2 = new SolarIrradianceValue(expectedNumber);
        //Act
        boolean result = solarIrradianceValue.equals(solarIrradianceValue2);
        //Assert
        assertTrue(result, "The result should be true");
    }

    /**
     * Test to ensure that the equals method returns true when the same SolarIrradianceValue object is provided.
     */
    @Test
    void equalsObjectShouldReturnTrue() {
        //Arrange
        double expectedNumber = 120.0;
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        //Act
        boolean result = solarIrradianceValue.equals(solarIrradianceValue);
        //Assert
        assertTrue(result, "The result should be true");
    }

    /**
     * Test to ensure that the valueToString method returns the correct string representation of the solar irradiance value.
     */
    @Test
    void valueToString() {
        //Arrange
        double expectedNumber = 950.0;
        String expected = "950.0";
        SolarIrradianceValue solarIrradianceValue = new SolarIrradianceValue(expectedNumber);
        //Act
        String result = solarIrradianceValue.valueToString();
        //Assert
        assertEquals(expected, result, "The result should be 950.0");
    }

    /**
     * Test to ensure that the equals method returns true when the same SolarIrradianceValue object is provided.
     */
    @Test
    void testHashCodeForSameSolarIrradianceValue() {
        // Arrange
        SolarIrradianceValue solarIrradianceValue1 = new SolarIrradianceValue(71.0);
        SolarIrradianceValue solarIrradianceValue2 = new SolarIrradianceValue(71.0);
        // Act
        int hashCode1 = solarIrradianceValue1.hashCode();
        int hashCode2 = solarIrradianceValue2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "The hash code should be the same");
    }

    /**
     * Test to ensure that the equals method returns false when a different SolarIrradianceValue object is provided.
     */
    @Test
    void testHashCodeForDifferentSolarIrradianceValue() {
        // Arrange
        SolarIrradianceValue solarIrradianceValue1 = new SolarIrradianceValue(71.0);
        SolarIrradianceValue solarIrradianceValue2 = new SolarIrradianceValue(72.0);
        // Act
        int hashCode1 = solarIrradianceValue1.hashCode();
        int hashCode2 = solarIrradianceValue2.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hash code should be different");
    }
}