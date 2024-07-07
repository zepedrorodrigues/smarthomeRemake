package smarthome.domain.room.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Length}. This class contains unit tests that verify the behavior of the Length value object,
 * including constructor validation, value retrieval, and equality checks.
 */
class LengthTest {

    double validValue = 3;
    double borderValue = 0.000000000000002;
    double invalidValue = 0;

    /**
     * Tests that the constructor does not throw an exception for a valid value.
     */
    @Test
    void testConstructorValidValueShouldNotThrowException() {
        assertDoesNotThrow(() -> new Length(validValue), "Constructor should not throw an exception for a valid value" +
                ".");
    }

    /**
     * Tests that the constructor does not throw an exception for a value at the boundary of validity.
     */
    @Test
    void testConstructorBorderValueShouldNotThrowException(){
        assertDoesNotThrow(() -> new Length(borderValue), "Constructor should not throw an exception for a border " +
                "value.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException for an invalid value.
     */
    @Test
    void testConstructorInvalidValueShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> new Length(invalidValue), "Constructor should throw an " +
                "IllegalArgumentException for an invalid value.");
    }

    /**
     * Tests that the getHeight method returns the correct value.
     */
    @Test
    void getValue() {
        //Arrange
        Length length = new Length(validValue);
        //Act
        double result = length.getLength();
        //Assert
        assertEquals(validValue, result, "The value returned by getLength " + "should be the same as the value passed" +
                " to the constructor.");
    }

    /**
     * Tests that the equals method returns true when comparing two different Length objects with the same value.
     */
    @Test
    void equalsTrueDifferentObjectsSameValue() {
        //Arrange
        Length length = new Length(validValue);
        Length length1 = new Length(validValue);
        //Act
        boolean result = length.equals(length1);
        //Assert
        assertTrue(result, "The equals method should return " + "true for two different Length objects with the same " +
                "value.");
    }

    /**
     * Tests that the equals method returns true when an object is compared with itself.
     */
    @Test
    void equalsTrueSameObjects(){
        //Arrange
        Length length = new Length(validValue);
        //Act
        boolean result = length.equals(length);
        //Assert
        assertTrue(result, "The equals method should return true when comparing an object with itself.");
    }

    /**
     * Tests that the equals method returns false when compared with a null object.
     */
    @Test
    void equalsFalseNullObject(){
        //Arrange
        Length length = new Length(validValue);
        //Act
        boolean result = length.equals(null);
        //Assert
        assertFalse(result, "The equals method should return false when comparing with a null object.");
    }

    /**
     * Tests that the equals method returns false when compared with an object of a different type.
     */
    @Test
    void equalsFalseDifferentObject(){
        //Arrange
        Length length = new Length(validValue);
        Width width = new Width(validValue);
        //Act and Assert
        assertNotEquals(length, width, "The equals method should return false " + "when comparing with an object of a" +
                " different type.");
    }

    /**
     * Tests that the equals method returns false when comparing two Length objects with different values.
     */
    @Test
    void equalsFalseDifferentValue(){
        //Arrange
        double newValue = 7;
        Length length = new Length(validValue);
        Length length1 = new Length(newValue);
        //Act
        boolean result = length.equals(length1);
        //Assert
        assertFalse(result, "The equals method should return false " + "when comparing two Length objects with " +
                "different values.");
    }

    /**
     * Tests the hashCode method for two different Length objects with the same value.
     * The hash code should be the same for both objects.
     */
    @Test
    void testHashCodeSameValueShouldBeEqual() {
        //Arrange
        Length length = new Length(validValue);
        Length length1 = new Length(validValue);
        //Act & Assert
        assertEquals(length.hashCode(), length1.hashCode(), "Two different Length instances " + "with the same value " +
                "should have the same hash code.");
    }

    /**
     * Tests the hashCode method for two different Length objects with different values.
     * The hash code should not be the same for both objects.
     */
    @Test
    void testHashCodeDifferentValueShouldNotBeEqual() {
        //Arrange
        double newValue = 7;
        Length length = new Length(validValue);
        Length length1 = new Length(newValue);
        //Act & Assert
        assertNotEquals(length.hashCode(), length1.hashCode(), "Two different Length instances " + "with different " +
                "values should not have the same hash code.");
    }

    /**
     * Tests the equals method for two different Length objects with the same value.
     * The method should return true.
     */
    @Test
    void testEqualsSameObjectShouldReturnTrue(){
        //Arrange
        Length length = new Length(validValue);
        //Act & Assert
        assertEquals(length, length, "An object should be equal to itself.");
    }

    /**
     * Test the equals method for one length object and a null object.
     * The method should return false.
     */
    @Test
    void testEqualsNullObjectShouldReturnFalse(){
        //Arrange
        Length length = new Length(validValue);
        //Act & Assert
        assertNotEquals(length, null, "The equals method should return false" + " when comparing with a null object.");
    }

    /**
     * Test the equals method for one length object and an object of a different type.
     * The method should return false.
     */
    @Test
    void testEqualsDifferentClassObjectShouldReturnFalse(){
        //Arrange
        Length length = new Length(validValue);
        Width width = new Width(validValue);
        //Act & Assert
        assertNotEquals(length, width, "The equals method should return false" + " when comparing with an object of a" +
                " different type.");
    }

    /**
     * Test the equals method for two different length objects with the same value.
     * The method should return true.
     */
    @Test
    void testEqualsDifferentObjectsSameValueShouldReturnTrue(){
        //Arrange
        Length length = new Length(validValue);
        Length length1 = new Length(validValue);
        //Act & Assert
        assertEquals(length, length1, "Two different Length instances with the same value should be equal.");
    }

    /**
     * Test the equals method for two different length objects with different values.
     * The method should return false.
     */
    @Test
    void testEqualsDifferentObjectsDifferentValueShouldReturnFalse(){
        //Arrange
        double newValue = 7;
        Length length = new Length(validValue);
        Length length1 = new Length(newValue);
        //Act & Assert
        assertNotEquals(length, length1, "Two different Length instances with different " + "values should not be " +
                "equal.");
    }

}
