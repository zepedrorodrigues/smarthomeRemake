package smarthome.domain.room.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the Floor class.
 * Each method in this class represents a test case for a specific functionality of the Floor class.
 * The tests are written using the JUnit 5 framework.
 */
class FloorTest {

    /**
     * Test case for the Floor constructor with a valid positive floor number.
     * The test verifies that the constructor does not throw an exception when a valid positive floor number is passed.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidPositiveFloor() {
        // Arrange
        int validPositiveFloor = 1;

        // Act - Assert
        assertDoesNotThrow(() -> new Floor(validPositiveFloor),
                "The constructor should not throw an exception for a valid positive floor.");
    }

    /**
     * Test case for the Floor constructor with a valid negative floor number. The test verifies that the constructor
     * does not throw an exception when a valid negative floor number is passed.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidNegativeFloor() {
        // Arrange
        int validNegativeFloor = -1;

        // Act - Assert
        assertDoesNotThrow(() -> new Floor(validNegativeFloor),
                "The constructor should not throw an exception for a valid negative floor.");
    }

    /**
     * Test case for the Floor constructor with a valid zero floor number. The test verifies that the constructor does
     * not throw an exception when a valid zero floor number is passed.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidZeroFloor() {
        // Arrange
        int validZeroFloor = 0;

        // Act - Assert
        assertDoesNotThrow(() -> new Floor(validZeroFloor),
                "The constructor should not throw an exception for a valid zero floor.");
    }

    /**
     * Test case for the getFloor method of the Floor class.
     * The test verifies that the getFloor method returns the correct floor number that was used to create the Floor
     * object.
     */
    @Test
    void testGetFloorReturnsCorrectValue() {
        // Arrange
        int value = 1;
        Floor floor = new Floor(value);
        // Act
        int result = floor.getFloor();
        // Assert
        assertEquals(value, result, "The getFloor method should return the correct value.");
    }

    /**
     * Test case for the equals method of the Floor class when comparing the same object.
     * The test verifies that the equals method returns true when the same Floor object is compared.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        int value = 1;
        Floor floor = new Floor(value);

        // Act
        boolean result = floor.equals(floor);

        // Assert
        assertTrue(result, "The equals method should return true when comparing the same object.");
    }

    /**
     * Test case for the equals method of the Floor class when comparing with a null object.
     * The test verifies that the equals method returns false when the Floor object is compared with null.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        int value = -1;
        Floor floor = new Floor(value);

        // Act
        boolean result = floor.equals(null);

        // Assert
        assertFalse(result, "The equals method should return false when comparing with a null object.");
    }

    /**
     * Test case for the equals method of the Floor class when comparing with a different type of object.
     * The test verifies that the equals method returns false when the Floor object is compared with an object of a
     * different type.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        int value = 0;
        Floor floor = new Floor(value);

        // Act
        boolean result = floor.equals(new Object());

        // Assert
        assertFalse(result, "The equals method should return false when comparing with a different type of object.");
    }

    /**
     * Test case for the equals method of the Floor class when comparing two Floor objects with the same value.
     * The test verifies that the equals method returns true when two Floor objects with the same floor number are
     * compared.
     */
    @Test
    void testEqualsForSameValue() {
        // Arrange
        int value = 0;
        Floor floor1 = new Floor(value);
        Floor floor2 = new Floor(value);

        // Act
        boolean result = floor1.equals(floor2);

        // Assert
        assertTrue(result,
                "The equals method should return true when comparing two Floor objects with the same value.");
    }

    /**
     * Test case for the equals method of the Floor class when comparing two Floor objects with different values.
     * The test verifies that the equals method returns false when two Floor objects with different floor numbers are
     * compared.
     */
    @Test
    void testEqualsForDifferentValue() {
        // Arrange
        int value1 = 0;
        int value2 = 1;
        Floor floor1 = new Floor(value1);
        Floor floor2 = new Floor(value2);

        // Act
        boolean result = floor1.equals(floor2);

        // Assert
        assertFalse(result,
                "The equals method should return false when comparing two Floor objects with different values.");
    }

    /**
     * Test case for the hashCode method of the Floor class when comparing the hash codes of two equal Floor objects.
     * The test verifies that the hashCode method returns the same hash code for two Floor objects with the same
     * floor number.
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        int value = 0;
        Floor floor1 = new Floor(value);
        Floor floor2 = new Floor(value);

        // Act
        int hashCode1 = floor1.hashCode();
        int hashCode2 = floor2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The hashCode method should return the same hash code for two equal Floor objects.");
    }

    /**
     * Test case for the hashCode method of the Floor class when comparing the hash codes of two different Floor
     * objects.
     * The test verifies that the hashCode method returns different hash codes for two Floor objects with different
     * floor numbers.
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        int value1 = 0;
        int value2 = 1;
        Floor floor1 = new Floor(value1);
        Floor floor2 = new Floor(value2);

        // Act
        int hashCode1 = floor1.hashCode();
        int hashCode2 = floor2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return different hash codes for two different Floor objects.");
    }
}