package smarthome.domain.actuatortype.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the ActuatorTypeName identifier class.
 */
class ActuatorTypeNameTest {

    /**
     * Tests that the constructor does not throw an exception when provided with a valid actuator type name.
     *
     * <p>
     * This test verifies that the ActuatorTypeName constructor successfully creates a new instance
     * of ActuatorTypeName when provided with a valid actuator type name. It ensures the correctness
     * of the constructor's behavior under normal conditions.
     * </p>
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidActuatorTypeName() {
        // Arrange
        String validActuatorTypeName = "Light Switch";
        // Act & Assert
        assertDoesNotThrow(() -> new ActuatorTypeName(validActuatorTypeName)
                , "Constructor should not throw an exception for a valid actuator type name.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when provided with a null actuator type name.
     *
     * <p>
     * This test verifies that the ActuatorTypeName constructor throws an IllegalArgumentException
     * when provided with a null actuator type name, as it is an invalid argument. It ensures that the constructor performs proper input validation and handles invalid input as expected.
     * </p>
     * <p>
     * This test serves as an acceptance test, ensuring that the ActuatorTypeName constructor behaves correctly
     * in rejecting null input. It validates the expected behavior of the constructor to maintain data integrity
     * by refusing invalid inputs.
     * </p>
     */
    @Test
    void testConstructorThrowsExceptionForNullActuatorTypeName() {   // can be considered acceptance test
        // Arrange
        String nullActuatorTypeName = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorTypeName(nullActuatorTypeName)
                , "Constructor should throw an exception for a null actuator type name.");
    }


    /**
     * Tests that the constructor throws an IllegalArgumentException when provided with a blank actuator type name.
     * <p>
     * This test verifies that the ActuatorTypeName constructor throws an IllegalArgumentException
     * when provided with a blank (empty or whitespace-only) actuator type name, as it is considered invalid.
     * It ensures that the constructor properly handles invalid input to maintain data integrity.
     * </p>
     * <p>
     * This test serves as an acceptance test, ensuring that the ActuatorTypeName constructor correctly
     * rejects blank input. It validates the expected behavior of the constructor to enforce constraints
     * on the input data, preserving the integrity of the actuator type name.
     * </p>
     */
    @Test
    void testConstructorThrowsExceptionForBlankActuatorTypeName() {  // can be considered acceptance test
        // Arrange
        String blankActuatorTypeName = " ";
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorTypeName(blankActuatorTypeName)
                , "Constructor should throw an exception for a blank actuator type name.");
    }

    /**
     * Tests that the getActuatorTypeName() method returns the correct actuator type name.
     * <p>
     * This test verifies that the getActuatorTypeName() method returns the correct actuator
     * type name set during object construction. It ensures the getter method's functionality to retrieve the actuator type name stored within an ActuatorTypeName object.
     * </p>
     * <p>
     * This test serves as an acceptance test, specifically a usability test, ensuring that the getActuatorTypeName() method
     * correctly retrieves the stored actuator type name. It validates the usability of the ActuatorTypeName class
     * by confirming the successful retrieval of data set during object construction.
     * </p>
     */
    @Test
    void testGetActuatorTypeNameReturnsCorrectValue() {                     // can be considered acceptance test (usability test)
        //Arrange
        String expected = "Blind Controller";
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(expected);
        //Act
        String result = someActuatorTypeName.getActuatorTypeName();
        //Assert
        assertEquals(expected, result
                , "getActuatorTypeName() should return the correct actuator type name.");
    }

    /**
     * Tests the equals(Object) method for comparing the same actuator type name objects.
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two references to the same ActuatorTypeName object as equal. It ensures the method's
     * functionality to compare object equality based on their actuator type name strings.
     * </p>
     */
    @Test
    void testEqualsForSameObjectUsingSameActuatorTypeNameObjects() {
        // Arrange
        String actuatorTypeNameValue = "Smart Plug Actuator";
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(actuatorTypeNameValue);
        // Act
        boolean result = someActuatorTypeName.equals(someActuatorTypeName);
        // Assert
        assertTrue(result
                , "equals() should return true for the same ActuatorTypeName object.");
    }

    /**
     * Tests the equals(Object) method for comparing different actuator type name objects with the same name.
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two different ActuatorTypeName objects with the same actuator type name as equal. It ensures
     * the method's functionality to compare object equality based on their actuator type name strings.
     * </p>
     */
    @Test
    void testEqualsForSameActuatorTypeNameComparingDifferentObjects() {
        // Arrange
        String actuatorTypeNameValue = "Door Lock";
        ActuatorTypeName someActuatorTypeName1 = new ActuatorTypeName(actuatorTypeNameValue);
        ActuatorTypeName someActuatorTypeName2 = new ActuatorTypeName(actuatorTypeNameValue);
        // Act & Assert
        assertTrue(someActuatorTypeName1.equals(someActuatorTypeName2)
                , "equals() should return true for different ActuatorTypeName objects with the same actuator type name.");
    }

    /**
     * Tests the equals(Object) method for comparing different actuator type name objects with different names.
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two different ActuatorTypeName objects with different actuator type names as not equal. It ensures
     * the method's functionality to compare object equality based on their actuator type name strings.
     * </p>
     */
    @Test
    void testEqualsForDifferentActuatorTypeName() {
        // Arrange
        String actuatorTypeNameValue1 = "Ceiling Fan Controller";
        String actuatorTypeNameValue2 = "Drape Controller";
        ActuatorTypeName someActuatorTypeName1 = new ActuatorTypeName(actuatorTypeNameValue1);
        ActuatorTypeName someActuatorTypeName2 = new ActuatorTypeName(actuatorTypeNameValue2);
        // Act & Assert
        assertFalse(someActuatorTypeName1.equals(someActuatorTypeName2)
                , "equals() should return false for different ActuatorTypeName objects with different actuator type names.");
    }

    /**
     * Tests the equals(Object) method for comparing with a not null object.
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * a ActuatorTypeName object as not equal to a non-null object. It ensures the method's functionality
     * to compare object equality even when one of the objects is not null.
     * </p>
     */
    @Test
    void testEqualsForNotNullObject() {
        // Arrange
        String actuatorTypeNameValue = "Water Valve";
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(actuatorTypeNameValue);
        // Act & Assert
        assertFalse(someActuatorTypeName.equals(null)
                , "equals() should return false for a non-null ActuatorTypeName object compared with a null object.");
    }

    /**
     * Tests the ameAs(Object) method for comparing with an object of a different class.
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * a ActuatorTypeName object as not equal to an object of a different class. It checks the behavior
     * of the method when comparing with objects from unrelated classes.
     * </p>
     */
    @Test
    void testEqualsForDifferentClass() {
        // Arrange
        String actuatorTypeNameValue = "Security Camera Controller";
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(actuatorTypeNameValue);
        // Act
        boolean result = someActuatorTypeName.equals(new Object());
        // Assert
        assertFalse(result
                , "equals() should return false for a ActuatorTypeName object compared with an object of a different class.");
    }

    /**
     * Tests for valid actuator type names including special characters.
     * <p>
     * This test is an acceptance test, ensuring that ActuatorTypeName constructor can handle
     * actuator type names containing special characters such as slashes. It verifies the correctness of the constructor's
     * behavior when creating instances with names including special characters.
     * </p>
     */
    @Test
    void testValidActuatorTypeNameWithSpecialCharacters() {
        // Arrange
        String actuatorTypeNameValue = "On/Off Switch";
        // Act
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(actuatorTypeNameValue);
        // Assert
        assertNotNull(someActuatorTypeName);
        assertEquals(actuatorTypeNameValue, someActuatorTypeName.getActuatorTypeName()
                , "ActuatorTypeName should handle actuator type names with special characters.");
    }

    /**
     * Tests for valid actuator type names including numbers.
     * <p>
     * This test is an acceptance test, ensuring that ActuatorTypeName constructor can handle
     * actuator type names containing numbers. It verifies the correctness of the constructor's
     * behavior when creating instances with names including numeric characters.
     * </p>
     */
    @Test
    void testValidActuatorTypeNameWithNumbers() {
        // Arrange
        String actuatorTypeNameValue = "Sprinkler System 123";
        // Act
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(actuatorTypeNameValue);
        // Assert
        assertNotNull(someActuatorTypeName);
        assertEquals(actuatorTypeNameValue, someActuatorTypeName.getActuatorTypeName()
                , "ActuatorTypeName should handle actuator type names with numbers.");
    }

    /**
     * Tests for a short actuator type name.
     * <p>
     * This test verifies that ActuatorTypeName constructor can handle short actuator type names
     * with only one character. It ensures that the constructor properly constructs instances with short names
     * while maintaining the integrity of the actuator type name data.
     * </p>
     */
    @Test
    void testShortActuatorTypeName() {
        // Arrange
        String shortActuatorTypeName = "A";
        // Act
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(shortActuatorTypeName);
        // Assert
        assertNotNull(someActuatorTypeName);
        assertEquals(shortActuatorTypeName, someActuatorTypeName.getActuatorTypeName()
                , "ActuatorTypeName should handle short actuator type names.");
    }

    /**
     * Tests for a long actuator type name.
     * <p>
     * This test verifies that ActuatorTypeName constructor can handle long actuator type names
     * containing multiple words and characters. It ensures that the constructor properly constructs instances with long names
     * while maintaining the integrity of the actuator type name data.
     * </p>
     */
    @Test
    void testLongActuatorTypeName() {
        // Arrange
        String longActuatorTypeName = "Heating, Ventilation, and Air Conditioning (HVAC) Controller";
        // Act
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName(longActuatorTypeName);
        // Assert
        assertNotNull(someActuatorTypeName);
        assertEquals(longActuatorTypeName, someActuatorTypeName.getActuatorTypeName()
                , "ActuatorTypeName should handle long actuator type names.");
    }

    /**
     * Tests that two different actuator type names are not considered the same.
     * <p>
     * This test is an acceptance test, ensuring that the ActuatorTypeName objects with different
     * actuator type names are correctly identified as not equal. It verifies the correctness of the
     * equals(Object) method's behavior when comparing different actuator type names.
     * </p>
     */
    @Test
    void testNotEqualsForDifferentActuatorTypeNames() {
        // Arrange
        ActuatorTypeName someActuatorTypeName1 = new ActuatorTypeName("Pool Pump Controller");
        ActuatorTypeName someActuatorTypeName2 = new ActuatorTypeName("Irrigation System Controller");
        // Act & Assert
        assertFalse(someActuatorTypeName1.equals(someActuatorTypeName2)
                , "equals() should return false for different ActuatorTypeName objects with different actuator type names.");
    }

    /**
     * Tests that the same actuator type name objects have the same hash code.
     * <p>
     * This test verifies that ActuatorTypeName objects with the same actuator type name
     * produce the same hash code. It ensures the correctness of the hashCode() method's behavior
     * when calculating hash codes for equal objects.
     * </p>
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName("Light Switch");
        ActuatorTypeName anotherActuatorTypeName = new ActuatorTypeName("Light Switch");
        // Act
        int hashCode = someActuatorTypeName.hashCode();
        int otherHashCode = anotherActuatorTypeName.hashCode();
        // Assert
        assertEquals(hashCode, otherHashCode
                , "Equal ActuatorTypeName objects should have the same hash code.");
    }

    /**
     * Tests that different actuator type name objects have different hash codes.
     * <p>
     * This test verifies that ActuatorTypeName objects with different actuator type names
     * produce different hash codes. It ensures the correctness of the hashCode() method's behavior
     * when calculating hash codes for unequal objects.
     * </p>
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        ActuatorTypeName someActuatorTypeName = new ActuatorTypeName("Sprinkler System");
        ActuatorTypeName anotherActuatorTypeName = new ActuatorTypeName("Water Pump Controller");
        // Act
        int hashCode = someActuatorTypeName.hashCode();
        int otherHashCode = anotherActuatorTypeName.hashCode();
        // Assert
        assertNotEquals(hashCode, otherHashCode
                , "Different ActuatorTypeName objects should have different hash codes.");
    }
}