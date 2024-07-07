package smarthome.domain.actuator.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class represents the IntLimit value object.
 */
class IntLimitTest {


    /**
     * Tests if the IntLimit constructor does not return null when a valid value is provided.
     */
    @Test
    void testConstructorNotNullForValidIntLimit() {
        //Arrange
        int intLimit = 50;
        //Act
        IntLimit result = new IntLimit(intLimit);
        // Assert
        assertNotNull(result, "The constructor should not return null.");
    }


    /**
     * Tests if the getIntLimit method returns the correct value.
     */
    @Test
    void testGetIntLimit() {
        //Arrange
        int expected = 30;
        IntLimit intLimit = new IntLimit(expected);
        //Act
        int result = intLimit.getIntLimit();
        //Assert
        assertEquals(expected, result, "The getIntLimit method should return the correct value.");
    }

    /**
     * Tests if the equals method returns true when the same object is provided.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        IntLimit intLimit = new IntLimit(71);
        // Act
        boolean result = intLimit.equals(intLimit);
        // Assert
        assertTrue(result, "The equals method should return true for the same object.");
    }

    /**
     * Tests if the equals method returns false when a different object is provided.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        IntLimit intLimit = new IntLimit(20);
        IntLimit intLimit1 = new IntLimit(30);
        // Act
        boolean result = intLimit.equals(intLimit1);
        // Assert
        assertFalse(result, "The equals method should return false for a different object.");
    }

    /**
     * This test verifies that the equals method of the IntLimit class returns false when null is passed as an argument.
     * The equals method is expected to return false in this case because a null object is not the same as any instance
     * of IntLimit.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        IntLimit intLimit = new IntLimit(20);
        // Act
        boolean result = intLimit.equals(null);
        // Assert
        assertFalse(result, "The equals method should return false for a null object.");
    }

    /**
     * This test verifies that the equals method of the IntLimit class returns false when an object of a different class
     * is passed as an argument.
     * The equals method is expected to return false in this case because an object of a different class is not the same
     * as an instance of IntLimit.
     */
    @Test
    void testEqualsForDifferentClass() {
        // Arrange
        IntLimit intLimit = new IntLimit(20);
        Object object = new Object();
        // Act
        boolean result = intLimit.equals(object);
        // Assert
        assertFalse(result, "The equals method should return false for a different class object.");
    }

    /**
     * This test verifies that the equals method of the IntLimit class returns true when two instances of IntLimit
     * have the same int limit.
     * The equals method is expected to return true in this case because the two instances have the same int limit.
     */
    @Test
    void testEqualsForSameIntLimit() {
        // Arrange
        IntLimit intLimit1 = new IntLimit(71);
        IntLimit intLimit2 = new IntLimit(71);
        // Act
        boolean result = intLimit1.equals(intLimit2);
        // Assert
        assertTrue(result, "The equals method should return true for the same IntLimit object.");
    }

    /**
     * This test verifies that the equals method of the IntLimit class returns true when two instances of IntLimit
     * have the same int limit.
     * The equals method is expected to return true in this case because the two instances have the same int limit.
     */
    @Test
    void testHashCodeForSameIntLimit() {
        // Arrange
        IntLimit intLimit1 = new IntLimit(71);
        IntLimit intLimit2 = new IntLimit(71);
        // Act
        int hashCode1 = intLimit1.hashCode();
        int hashCode2 = intLimit2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "The hashCodes should be equal for the same IntLimit object.");
    }

    /**
     * This test verifies that the equals method of the IntLimit class returns false when two instances of IntLimit
     * have different int limits.
     * The equals method is expected to return false in this case because the two instances have different int limits.
     */
    @Test
    void testHashCodeForDifferentIntLimit() {
        // Arrange
        IntLimit intLimit1 = new IntLimit(71);
        IntLimit intLimit2 = new IntLimit(72);
        // Act
        int hashCode1 = intLimit1.hashCode();
        int hashCode2 = intLimit2.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hashCodes should not be equal for different IntLimit objects.");
    }
}