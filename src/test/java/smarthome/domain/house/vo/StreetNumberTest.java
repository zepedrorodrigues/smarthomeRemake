package smarthome.domain.house.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the StreetNumber class.
 */
class StreetNumberTest {

    /**
     * Test the constructor of the class StreetNumber.
     * The constructor should not throw an exception for a valid street number.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidStreetNumber() {
        //Arrange
        String validStreetNumber = "13A";
        //Act - Assert
        assertDoesNotThrow(() -> new StreetNumber(validStreetNumber),
                "Constructor should not throw an exception for a valid street number.");
    }

    /**
     * Test the constructor of the class StreetNumber.
     * The constructor should throw an exception for a null street number.
     */
    @Test
    void testConstructorThrowsExceptionForNullStreetNumber() {
        //Arrange
        String invalidStreetNumber = null;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new StreetNumber(invalidStreetNumber),
                "Constructor should throw an exception for a null street number.");
    }

    /**
     * Test the constructor of the class StreetNumber.
     * The constructor should throw an exception for a blank street number.
     */
    @Test
    void testConstructorThrowsExceptionForBlankStreetNumber() {
        //Arrange
        String invalidStreetNumber = " ";
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new StreetNumber(invalidStreetNumber),
                "Constructor should throw an exception for a blank street number.");
    }

    /**
     * Test the getStreetNumber method of the class StreetNumber.
     * The method should return the correct street number value.
     */
    @Test
    void testGetStreetNumberReturnsCorrectValue() {
        //Arrange
        String expected = "13A";
        StreetNumber streetNumber = new StreetNumber(expected);
        //Act
        String result = streetNumber.getStreetNumber();
        //Assert
        assertEquals(expected, result,
                "getStreetNumber should return the correct street number value.");
    }

    /**
     * Test the equals method of the class StreetNumber.
     * The method should return true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        StreetNumber streetNumber = new StreetNumber("13A");

        // Act - Assert
        assertEquals(streetNumber, streetNumber,
                "The equals method should return true for the same object.");
    }

    /**
     * Test the equals method of the class StreetNumber.
     * The method should return false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        StreetNumber streetNumber = new StreetNumber("13A");
        StreetNumber nullStreetNumber = null;

        // Act - Assert
        assertNotEquals(streetNumber, nullStreetNumber,
                "The equals method should return false for a null object.");
    }

    /**
     * Test the equals method of the class StreetNumber.
     * The method should return false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        StreetNumber streetNumber = new StreetNumber("13A");

        // Act - Assert
        assertNotEquals(streetNumber, new Object(),
                "The equals method should return false for a different object.");
    }

    /**
     * Test the equals method of the class StreetNumber.
     * The method should return true for the same street number.
     */
    @Test
    void testEqualsForSameStreetNumber() {
        // Arrange
        String streetNumber = "13A";
        StreetNumber streetNumber1 = new StreetNumber(streetNumber);
        StreetNumber streetNumber2 = new StreetNumber(streetNumber);

        // Act - Assert
        assertEquals(streetNumber1, streetNumber2,
                "The equals method should return true for the same street number.");
    }


    /**
     * Test the equals method of the class StreetNumber.
     * The method should return false for different street numbers.
     */
    @Test
    void testEqualsForDifferentStreetNumber() {
        // Arrange
        String streetNumberA = "13A";
        String streetNumberB = "13B";
        StreetNumber streetNumber1 = new StreetNumber(streetNumberA);
        StreetNumber streetNumber2 = new StreetNumber(streetNumberB);

        // Act - Assert
        assertNotEquals(streetNumber1, streetNumber2,
                "The equals method should return false for different street numbers.");
    }

    /**
     * Test the hashCode method of the class StreetNumber.
     * The method should return the same hash code for the same value.
     */
    @Test
    void testHashCodeForSameValue() {
        // Arrange
        StreetNumber streetNumber1 = new StreetNumber("13A");
        StreetNumber streetNumber2 = new StreetNumber("13A");

        // Act
        int result1 = streetNumber1.hashCode();
        int result2 = streetNumber2.hashCode();

        // Assert
        assertEquals(result1, result2,
                "The hashCode method should return the same hash code for the same value.");
    }

    /**
     * Test the hashCode method of the class StreetNumber.
     * The method should return different hash codes for different values.
     */
    @Test
    void testHashCodeForDifferentValue() {
        // Arrange
        StreetNumber streetNumber1 = new StreetNumber("13A");
        StreetNumber streetNumber2 = new StreetNumber("10B");

        // Act
        int result1 = streetNumber1.hashCode();
        int result2 = streetNumber2.hashCode();

        // Assert
        assertNotEquals(result1, result2,
                "The hashCode method should return different hash codes for different values.");
    }
}
