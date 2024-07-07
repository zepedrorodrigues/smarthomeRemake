package smarthome.domain.reading.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the ReadingValue class.
 */
public class ReadingValueTest {

    /**
     * Tests the constructor of the ReadingValue class.
     * Verifies that the class returns the exp
     */
    @Test
    public void ReadingValue() {
        // Arrange
        String value = "abc";
        ReadingValue expected = new ReadingValue("abc");
        // Act
        ReadingValue actual = new ReadingValue(value);
        // Assert
        assertEquals(expected, actual);
    }

    /**
     * Tests the constructor of the ReadingValue class.
     * Verifies that the class throws an IllegalArgumentException when the value is null.
     */
    @Test
    public void ReadingValueThrowsExceptionWhenValueIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ReadingValue(null));
    }

    /**
     * Tests the equals method of the ReadingValue class.
     * Verifies that the class returns true when the object is equal to the value of the reading.
     */
    @Test
    public void equalsReturnsTrueWhenObjectIsEqualToValue() {
        // Arrange
        ReadingValue readingValue = new ReadingValue("abc");
        // Act
        boolean actual = readingValue.equals(new ReadingValue("abc"));
        // Assert
        assertTrue(actual);
    }

    /**
     * Tests the equals method of the ReadingValue class.
     * Verifies that the class returns true when the object is the same as the value of the reading.
     */
    @Test
    public void equalsReturnsTrueWhenObjectIsSameAsValue() {
        // Arrange
        ReadingValue readingValue = new ReadingValue("abc");
        // Act
        boolean actual = readingValue.equals(readingValue);
        // Assert
        assertTrue(actual);
    }

    /**
     * Tests the equals method of the ReadingValue class.
     * Verifies that the class returns false when the object is null.
     */
    @Test
    public void equalsReturnsFalseWhenObjectIsNull() {
        // Arrange
        ReadingValue readingValue = new ReadingValue("abc");
        // Act
        boolean actual = readingValue.equals(null);
        // Assert
        assertFalse(actual);
    }

    /**
     * Tests the equals method of the ReadingValue class.
     * Verifies that the class returns false when the object is different.
     */
    @Test
    public void equalsReturnsFalseWhenObjectIsDifferent() {
        // Arrange
        ReadingValue readingValue = new ReadingValue("abc");
        // Act
        boolean actual = readingValue.equals(new Object());
        // Assert
        assertFalse(actual);
    }

    /**
     * Tests the equals method of the ReadingValue class.
     * Verifies that the class returns false when the value of the reading is different.
     */
    @Test
    public void equalsReturnsFalseWhenValueIsDifferent() {
        // Arrange
        ReadingValue readingValue = new ReadingValue("abc");
        // Act
        boolean actual = readingValue.equals(new ReadingValue("def"));
        // Assert
        assertFalse(actual);
    }

    /**
     * Tests the hashCode method of the ReadingValue class.
     * Verifies that the class returns different hash codes for different values of the reading.
     */
    @Test
    public void hashCodeReturnsDifferentHashCodesForDifferentValues() {
        // Arrange
        ReadingValue readingValue1 = new ReadingValue("abc");
        ReadingValue readingValue2 = new ReadingValue("def");
        // Act
        int hashCode1 = readingValue1.hashCode();
        int hashCode2 = readingValue2.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2);
    }

    /**
     * Tests the hashCode method of the ReadingValue class.
     * Verifies that the class returns the same hash code for the same value of the reading.
     */
    @Test
    public void hashCodeReturnsSameHashCodeForSameValue() {
        // Arrange
        ReadingValue readingValue1 = new ReadingValue("abc");
        ReadingValue readingValue2 = new ReadingValue("abc");
        // Act
        int hashCode1 = readingValue1.hashCode();
        int hashCode2 = readingValue2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2);
    }

    /**
     * Tests the valueToString method of the ReadingValue class.
     * Verifies that the class returns the value of the reading.
     */
    @Test
    public void valueToStringReturnsValueOfReading() {
        // Arrange
        String value = "abc";
        ReadingValue readingValue = new ReadingValue(value);
        // Act
        String actual = readingValue.valueToString();
        // Assert
        assertEquals(value, actual);
    }


}
