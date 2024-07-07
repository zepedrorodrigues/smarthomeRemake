package smarthome.domain.actuator.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LoadStateTest is a test class for LoadState.
 * It contains unit tests that check the functionality of LoadState.
 */

class LoadStateTest {

    /**
     * Test to ensure that the constructor of LoadState does not throw an exception for a valid load state.
     */

    @Test
    void testConstructorDoesNotThrowExceptionForValidLoadState() {
        //Arrange
        boolean state = true;
        //Act + Assert
        assertDoesNotThrow(() -> new LoadState(state), "Constructor should not throw an exception for a valid load " +
                "state.");
    }

    /**
     * Test to check the getLoadState method of LoadState.
     * It checks if the method returns the correct load state.
     */
    @Test
    void testGetLoadState() {
        //Arrange
        boolean expected = true;
        LoadState loadState = new LoadState(expected);
        //Act
        boolean result = loadState.getLoadState();
        //Assert
        assertEquals(expected, result, "The getLoadState method should return the correct load state.");
    }

    /**
     * Test to check the getLoadState method of LoadState when the load is off.
     * It checks if the method returns false.
     */
    @Test
    void testGetLoadStateReturnsFalseWhenLoadIsOff() {
        // Arrange
        LoadState loadState = new LoadState(false);
        // Act
        boolean result = loadState.getLoadState();
        // Assert
        assertFalse(result, "The getLoadState method should return false when the load is off.");
    }

    /**
     * Test to check the equals method of LoadState when called with the same object.
     * It checks if the method returns true.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        LoadState loadState = new LoadState(true);
        // Act
        boolean result = loadState.equals(loadState);
        // Assert
        assertTrue(result, "The equals method should return true when called with the same object.");
    }

    /**
     * Test to check the equals method of LoadState when called with a different object.
     * It checks if the method returns false.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        LoadState loadState = new LoadState(true);
        LoadState loadState2 = new LoadState(false);
        // Act
        boolean result = loadState.equals(loadState2);
        // Assert
        assertFalse(result, "The equals method should return false when called with a different object.");
    }

    /**
     * Test to check the equals method of LoadState when called with a null object.
     * It checks if the method returns false.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        LoadState loadState = new LoadState(true);
        // Act
        boolean result = loadState.equals(null);
        // Assert
        assertFalse(result, "The equals method should return false when called with a null object.");
    }

    /**
     * Test to check the equals method of LoadState when called with an object of a different class.
     * It checks if the method returns false.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        LoadState loadState = new LoadState(true);
        Object object = new Object();
        // Act
        boolean result = loadState.equals(object);
        // Assert
        assertFalse(result, "The equals method should return false when called with an object of a different class.");
    }

    /**
     * Test to check the equals method of LoadState when called with a LoadState object with a different load state.
     * It checks if the method returns true.
     */
    @Test
    void testEqualsForDifferentLoadStateObject() {
        // Arrange
        LoadState loadState = new LoadState(true);
        LoadState loadState2 = new LoadState(true);
        // Act
        boolean result = loadState.equals(loadState2);
        // Assert
        assertTrue(result, "The equals method should return true when called with a LoadState object with a " +
                "different" + " load state.");
    }

    /**
     * Test to check the hashCode method of LoadState.
     * It checks if the method returns the correct hash code.
     */

    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        LoadState loadState = new LoadState(true);
        LoadState otherLoadState = new LoadState(true);
        // Act
        int hashCode = loadState.hashCode();
        int otherHashCode = otherLoadState.hashCode();
        // Assert
        assertEquals(hashCode, otherHashCode, "The hashCode method should return the correct hash code for equal " +
                "objects.");
    }

    /**
     * This test checks the hashCode method of LoadState.
     * It verifies that the method returns different hash codes for two LoadState objects with different load states.
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        LoadState loadState = new LoadState(true);
        LoadState otherLoadState = new LoadState(false);
        // Act
        int hashCode = loadState.hashCode();
        int otherHashCode = otherLoadState.hashCode();
        // Assert
        assertNotEquals(hashCode, otherHashCode, "The hashCode method should return different hash codes for " +
                "different objects.");
    }
}