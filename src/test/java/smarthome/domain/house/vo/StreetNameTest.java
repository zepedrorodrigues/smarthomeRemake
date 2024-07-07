package smarthome.domain.house.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is responsible for testing the StreetName class.
 */
class StreetNameTest {

    /**
     * Tests that the constructor does not throw an exception when a valid street name is provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidStreetName() {
        //Arrange
        String validStreetName = "Main Street";
        //Act & Assert
        assertDoesNotThrow(() -> new StreetName(validStreetName), "StreetName object should be successfully created.");
    }

    /**
     * Tests that the constructor throws an exception when a null street name is provided.
     */
    @Test
    void testConstructorThrowsExceptionForNullStreetName() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new StreetName(null), "StreetName object should not be " +
                "created with null street name.");
    }

    /**
     * Tests that the constructor throws an exception when a blank street name is provided.
     */
    @Test
    void testConstructorThrowsExceptionForBlankStreetName() {
        //Arrange
        String invalidStreetName = " ";
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new StreetName(invalidStreetName), "StreetName object " +
                "should not be created with blank street name.");
    }

    /**
     * Tests that the getStreetName method returns the correct street name.
     */
    @Test
    void testGetStreetNameReturnsCorrectValue() {
        //Arrange
        String expected = "Main Street";
        StreetName streetName = new StreetName(expected);
        //Act
        String result = streetName.getStreetName();
        //Assert
        assertEquals(expected, result, "Street name should be the same as the one provided in the constructor.");
    }

    /**
     * Tests that the equals method returns true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        StreetName streetName = new StreetName("Main Street");
        // Act
        boolean result = streetName.equals(streetName);
        // Assert
        assertTrue(result, "The result should be true for the same object.");
    }

    /**
     * Tests that the equals method returns false when comparing to a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        StreetName streetName = new StreetName("Main Street");
        // Act
        boolean result = streetName.equals(null);
        // Assert
        assertFalse(result, "The result should be false for null object.");
    }

    /**
     * Tests that the equals method returns false when comparing to a different object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        StreetName streetName = new StreetName("Main Street");
        // Act
        boolean result = streetName.equals(new Object());
        // Assert
        assertFalse(result, "The result should be false for different class object.");
    }

    /**
     * Tests that the equals method returns false when comparing to a different street name.
     */
    @Test
    void testEqualsForDifferentStreetName() {
        // Arrange
        StreetName streetName = new StreetName("Main Street");
        StreetName otherStreetName = new StreetName("Second Street");
        // Act
        boolean result = streetName.equals(otherStreetName);
        // Assert
        assertFalse(result, "The result should be false for different street name.");
    }

    /**
     * Tests that the equals method returns true when comparing to the same street name.
     */
    @Test
    void testEqualsForSameStreetName() {
        // Arrange
        StreetName streetName = new StreetName("Main Street");
        StreetName otherStreetName = new StreetName("Main Street");
        // Act
        boolean result = streetName.equals(otherStreetName);
        // Assert
        assertTrue(result, "The result should be true for the same street name.");
    }

    /**
     * Tests that the hashCode method returns the same value for two equal objects.
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        StreetName streetName = new StreetName("Main Street");
        StreetName otherStreetName = new StreetName("Main Street");
        // Act
        int hashCode = streetName.hashCode();
        int otherHashCode = otherStreetName.hashCode();
        // Assert
        assertEquals(hashCode, otherHashCode, "The hash code should be the same for equal objects.");
    }

    /**
     * Tests that the hashCode method returns different values for two different objects.
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        StreetName streetName = new StreetName("Main Street");
        StreetName otherStreetName = new StreetName("Second Street");
        // Act
        int hashCode = streetName.hashCode();
        int otherHashCode = otherStreetName.hashCode();
        // Assert
        assertNotEquals(hashCode, otherHashCode, "The hash code should be different for different objects.");
    }
}