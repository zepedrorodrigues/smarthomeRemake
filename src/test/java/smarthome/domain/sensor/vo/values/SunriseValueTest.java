package smarthome.domain.sensor.vo.values;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the SunriseValue class.
 */
class SunriseValueTest {

    LocalDateTime localDateTime;
    SunriseValue sunriseValue;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp () {
        localDateTime = LocalDateTime.now();
        sunriseValue = new SunriseValue(localDateTime);
    }

    /**
     * Test to check if the constructor of the SunriseValue class is not null.
     */
    @Test
    void testConstructorIsNotNullForValidLocalDateTime() {
        //Act
        SunriseValue result = new SunriseValue(localDateTime);
        // Assert
        assertNotNull(result, "Should not be null");
    }

    /**
     * Test to check if the constructor of the SunriseValue class throws an exception for a null LocalDateTime.
     */
    @Test
    void testConstructorThrowsExceptionForNullLocalDateTime() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SunriseValue(null),
                "Should throw IllegalArgumentException");
    }

    /**
     * Test that the equals method returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Act
        boolean result = sunriseValue.equals(sunriseValue);
        // Assert
        assertTrue(result, "Should return true for the same object");
    }

    /**
     * Test that the equals method returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        //Arrange
        SunriseValue nullSunriseValue = null;
        // Act
        boolean result = sunriseValue.equals(nullSunriseValue);
        // Assert
        assertFalse(result, "Should return false for null object");
    }

    /**
     * Test that the equals method returns false for a different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Act
        boolean result = sunriseValue.equals(new Object());
        // Assert
        assertFalse(result, "Should return false for different class object");
    }

    /**
     * Test that the equals method returns true for different objects with the same value.
     */
    @Test
    void testEqualsForDifferentObjectsWithSameValue() {
        // Arrange
        SunriseValue sunriseValue1 = new SunriseValue(localDateTime);
        // Act
        boolean result = sunriseValue.equals(sunriseValue1);
        // Assert
        assertTrue(result, "Should return true for different objects with the same value");
    }

    /**
     * Test that the equals method returns false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        SunriseValue sunriseValue1 = new SunriseValue(LocalDateTime.of(2025,2,27, 6, 30));
        // Act
        boolean result = sunriseValue.equals(sunriseValue1);
        // Assert
        assertFalse(result, "Should return false for different object");
    }

    /**
     * This test verifies the hashCode method of the SunriseValue class.
     * It creates two SunriseValue objects with the same value and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameSunriseValue() {
        // Arrange
        SunriseValue sunriseValue1 = new SunriseValue(localDateTime);
        // Act
        int hashCode = sunriseValue.hashCode();
        int hashCode1 = sunriseValue1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return the same hashCode");
    }

    /**
     * This test verifies the hashCode method of the SunriseValue class.
     * It creates two SunriseValue objects with different value and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentSunriseValue() {
        // Arrange
        SunriseValue sunriseValue1 = new SunriseValue(LocalDateTime.of(2025,2,27, 6, 30));
        // Act
        int hashCode = sunriseValue.hashCode();
        int hashCode1 = sunriseValue1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hashCodes");
    }

    /**
     * Test to check if the valueToString method generates the correct string representation.
     */
    @Test
    void testValueToString() {
        //Arrange
        String expected = localDateTime.toString();
        //Act
        String result = sunriseValue.valueToString();
        //Assert
        assertEquals(expected, result, "Should return the correct string representation");
    }


}