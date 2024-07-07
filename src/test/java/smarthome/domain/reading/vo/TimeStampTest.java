package smarthome.domain.reading.vo;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the TimeStamp class.
 */
class TimeStampTest
{
    // A LocalDateTime instance for testing.
    LocalDateTime localDateTime = LocalDateTime.now();

    /**
     * Test to ensure that the TimeStamp constructor does not return null when given a valid LocalDateTime.
     */
    @Test
    void testConstructorIsNotNullForValidLocalDateTime()
    {
        // Act
        TimeStamp result = new TimeStamp(localDateTime);
        // Assert
        assertNotNull(result, "The constructor should not return null");
    }

    /**
     * Test to ensure that the TimeStamp constructor throws an IllegalArgumentException when given a null value.
     */
    @Test
    void testConstructorWithNullValue()
    {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new TimeStamp(null),
                "The constructor should throw an IllegalArgumentException when the value is null");
    }

    /**
     * Test to ensure that the getValue method of the TimeStamp class returns a non-null value.
     */
    @Test
    void testGetValueShouldReturnCorrectValue()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        // Act
        LocalDateTime result = timeStamp.getValue();
        // Assert
        assertNotNull(result, "The getValue method should return a non-null value");
    }

    /**
     * Test to ensure that the equals method of the TimeStamp class returns true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        // Act
        boolean result = timeStamp.equals(timeStamp);
        // Assert
        assertTrue(result, "The equals method should return true for the same object");
    }

    /**
     * Test to ensure that the equals method of the TimeStamp class returns false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObject()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        // Act
        boolean result = timeStamp.equals(null);
        // Assert
        assertFalse(result, "The equals method should return false for null object");
    }

    /**
     * Test to ensure that the equals method of the TimeStamp class returns false when comparing with an object of a
     * different class.
     */
    @Test
    void testEqualsForDifferentClassObject()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        // Act
        boolean result = timeStamp.equals(new Object());
        // Assert
        assertFalse(result, "The equals method should return false for different class object");
    }

    /**
     * Test to ensure that the equals method of the TimeStamp class returns true when comparing different objects with
     * the same value.
     */
    @Test
    void testEqualsForDifferentObjectWithSameValue()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        TimeStamp timeStamp2 = new TimeStamp(localDateTime);
        // Act
        boolean result = timeStamp.equals(timeStamp2);
        // Assert
        assertTrue(result, "The equals method should return true for different objects with the same value");
    }

    /**
     * Test to ensure that the equals method of the TimeStamp class returns false when comparing different objects with
     * different values.
     */
    @Test
    void testEqualsForDifferentObjectWithDifferentValue()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        TimeStamp timeStamp2 = new TimeStamp(LocalDateTime.now().plusDays(1));
        // Act
        boolean result = timeStamp.equals(timeStamp2);
        // Assert
        assertFalse(result, "The equals method should return false for different objects with different values");
    }

    /**
     * Test to ensure that the valueToString method of the TimeStamp class returns the expected string.
     */
    @Test
    void testValueToString()
    {
        // Arrange
        String expected = localDateTime.toString();
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        // Act
        String result = timeStamp.valueToString();
        // Assert
        assertEquals(expected, result, "The valueToString method should return the expected string");
    }

    /**
     * Test to ensure that the hashCode method of the TimeStamp class returns the same value for the same TimeStamp.
     */
    @Test
    void testHashCodeForSameTimeStamp()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        TimeStamp sameTimeStamp = new TimeStamp(localDateTime);
        // Act
        int result = timeStamp.hashCode();
        int sameResult = sameTimeStamp.hashCode();
        // Assert
        assertEquals(result, sameResult, "The hashCode method should return the same value for the same TimeStamp");
    }

    /**
     * Test to ensure that the hashCode method of the TimeStamp class returns different values for different TimeStamp.
     */
    @Test
    void testHashCodeForDifferentTimeStamp()
    {
        // Arrange
        TimeStamp timeStamp = new TimeStamp(localDateTime);
        TimeStamp differentTimeStamp = new TimeStamp(LocalDateTime.now().plusDays(1));
        // Act
        int result = timeStamp.hashCode();
        int differentResult = differentTimeStamp.hashCode();
        // Assert
        assertNotEquals(result, differentResult,
                "The hashCode method should return different values for different TimeStamp");
    }
}