package smarthome.domain.reading.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the class for Reading Id.
 */
class ReadingIdTest {

    /**
     * Test the constructor of the class ReadingId.
     * The constructor should not throw an exception for a valid reading identifier.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidReadingId() {
        //Arrange
        String validReadingId = "112345B";
        //Act - Assert
        assertDoesNotThrow(() -> new ReadingId(validReadingId),
                "The constructor should not throw an exception for a valid reading identifier.");
    }

    /**
     * Test the constructor of the class ReadingId.
     * The constructor should throw an exception for a null reading identifier.
     */
    @Test
    void testConstructorThrowsExceptionForNullReadingId() {
        //Arrange
        String invalidReadingId = null;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new ReadingId(invalidReadingId),
                "The constructor should throw an exception for a null reading identifier.");
    }

    /**
     * Test the constructor of the class ReadingId.
     * The constructor should throw an exception for a blank reading identifier.
     */
    @Test
    void testConstructorThrowsExceptionForBlankReadingId() {
        //Arrange
        String invalidReadingId = " ";
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new ReadingId(invalidReadingId),
                "The constructor should throw an exception for a blank reading identifier.");
    }

    /**
     * Test the getId method of the class ReadingId.
     * The method should return the correct reading identifier value.
     */
    @Test
    void testGetReadingIdReturnsCorrectValue() {
        //Arrange
        String expected = "112345B";
        ReadingId readingId = new ReadingId(expected);
        //Act
        String result = readingId.getId();
        //Assert
        assertEquals(expected, result,
                "The method should return the correct reading identifier value.");
    }

    /**
     * Test the equals method of the class ReadingId.
     * The method should return true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        ReadingId readingId = new ReadingId("112345B");

        // Act - Assert
        assertEquals(readingId, readingId,
                "The method should return true when comparing the same object.");
    }

    /**
     * Test the equals method of the class ReadingId.
     * The method should return false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        ReadingId readingId = new ReadingId("112345B");
        ReadingId nullReadingId = null;

        // Act - Assert
        assertNotEquals(readingId, nullReadingId,
                "The method should return false when comparing with a null object.");
    }

    /**
     * Test the equals method of the class ReadingId.
     * The method should return false when comparing with a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        ReadingId readingId = new ReadingId("112345B");

        // Act - Assert
        assertNotEquals(readingId, new Object(),
                "The method should return false when comparing with a different object.");
    }

    /**
     * Test the equals method of the class ReadingId.
     * The method should return true for the same reading identifier value.
     */
    @Test
    void testEqualsForSameReadingIdValue() {
        // Arrange
        String readingId = "112345B";
        ReadingId readingId1 = new ReadingId(readingId);
        ReadingId readingId2 = new ReadingId(readingId);

        // Act - Assert
        assertEquals(readingId1, readingId2,
                "The method should return true for the same reading identifier value.");
    }

    /**
     * Test the equals method of the class ReadingId.
     * The method should return false for different reading identifier values.
     */
    @Test
    void testEqualsForDifferentReadingId() {
        // Arrange
        String readingIdA = "112345B";
        String readingIdB = "112345C";
        ReadingId readingId1 = new ReadingId(readingIdA);
        ReadingId readingId2 = new ReadingId(readingIdB);

        // Act - Assert
        assertNotEquals(readingId1, readingId2,
                "The method should return false for different reading identifier values.");
    }

    /**
     * Test the hashCode method of the class ReadingId.
     * The method should return the same hash code for the same reading identifier value.
     */
    @Test
    void testHashCodeForSameReadingId() {
        // Arrange
        String readingId = "112345B";
        ReadingId readingId1 = new ReadingId(readingId);
        ReadingId readingId2 = new ReadingId(readingId);

        // Act
        int hashCode1 = readingId1.hashCode();
        int hashCode2 = readingId2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The method should return the same hash code for the same reading identifier value.");
    }

    /**
     * Test the hashCode method of the class ReadingId.
     * The method should return different hash codes for different reading identifier values.
     */
    @Test
    void testHashCodeForDifferentReadingId() {
        // Arrange
        String readingIdA = "112345B";
        String readingIdB = "1345C";
        ReadingId readingId1 = new ReadingId(readingIdA);
        ReadingId readingId2 = new ReadingId(readingIdB);

        // Act
        int hashCode1 = readingId1.hashCode();
        int hashCode2 = readingId2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The method should return different hash codes for different reading identifier values.");
    }
}