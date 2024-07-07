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
 * This class is responsible for testing the SunsetValue class.
 */
class SunsetValueTest {

    LocalDateTime localDateTime;

    SunsetValue sunsetValue;

    /**
     * This method sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        localDateTime = LocalDateTime.now();
        sunsetValue = new SunsetValue(localDateTime);
    }

    /**
     * Test to check if the constructor of the SunsetValue class is not null.
     */
    @Test
    void testConstructorIsNotNullForValidLocalDateTime() {
        //Act
        SunsetValue result = new SunsetValue(localDateTime);
        // Assert
        assertNotNull(result, "SunsetValue object should be successfully created.");
    }

    /**
     * Test to check if the constructor of the SunsetValue class throws an exception for a null LocalDateTime.
     */
    @Test
    void testConstructorThrowsExceptionForNullLocalDateTime() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SunsetValue(null), "SunsetValue object should not be " +
                "created with null LocalDateTime.");
    }

    /**
     * Test that the equals method returns true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Act
        boolean result = sunsetValue.equals(sunsetValue);
        // Assert
        assertTrue(result, "The result should be true for the same object.");
    }

    /**
     * Test that the equals method returns false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Act
        boolean result = sunsetValue.equals(null);
        // Assert
        assertFalse(result, "The result should be false for null object.");
    }

    /**
     * Test that the equals method returns false for a different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Act
        boolean result = sunsetValue.equals(new Object());
        // Assert
        assertFalse(result, "The result should be false for different class object.");
    }

    /**
     * Test that the equals method returns true for different object with same value.
     */
    @Test
    void testEqualsForDifferentObjectWithSameValue() {
        // Arrange
        SunsetValue sameSunsetValue = new SunsetValue(localDateTime);
        // Act
        boolean result = sunsetValue.equals(sameSunsetValue);
        // Assert
        assertTrue(result, "The result should be true for different object with same value.");
    }

    /**
     * Test that the equals method returns false for different object with different value.
     */
    @Test
    void testEqualsForDifferentObjectWithDifferentValue() {
        // Arrange
        SunsetValue differentSunsetValue = new SunsetValue(LocalDateTime.now().plusDays(1));
        // Act
        boolean result = sunsetValue.equals(differentSunsetValue);
        // Assert
        assertFalse(result, "The result should be false for different object with different value.");
    }

    /**
     * Test that the valueToString method returns the expected string.
     */
    @Test
    void testValueToString() {
        // Arrange
        String expected = localDateTime.toString();
        // Act
        String result = sunsetValue.valueToString();
        // Assert
        assertEquals(expected, result, "The valueToString method should return the expected string.");
    }

    /**
     * Test that the hashCode method returns the same value for the same sunsetValue.
     */
    @Test
    void testHashCodeForSameSunsetValue() {
        // Arrange
        SunsetValue sameSunsetValue = new SunsetValue(localDateTime);
        // Act
        int result = sunsetValue.hashCode();
        int sameResult = sameSunsetValue.hashCode();
        // Assert
        assertEquals(result, sameResult, "The hash code should be the same for the same sunsetValue.");
    }

    /**
     * Test that the hashCode method returns different values for different sunsetValue.
     */
    @Test
    void testHashCodeForDifferentSunsetValue() {
        // Arrange
        SunsetValue differentSunsetValue = new SunsetValue(LocalDateTime.now().plusDays(1));
        // Act
        int result = sunsetValue.hashCode();
        int differentResult = differentSunsetValue.hashCode();
        // Assert
        assertNotEquals(result, differentResult, "The hash code should be different for different sunsetValue.");
    }


}