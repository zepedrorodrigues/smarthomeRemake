package smarthome.domain.house.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the ZipCode class.
 */
class ZipCodeTest {

    /**
     * Tests that the constructor does not throw an exception when provided with a valid zip code.
     *
     * <p>
     * This test verifies that the ZipCode constructor successfully creates a new instance
     * of ZipCode when provided with a valid zip code. It ensures the correctness of the
     * constructor's behavior under normal conditions.
     * </p>
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidZipCode() {
        // Arrange
        String validZipCode = "4100-366";
        // Act & Assert
        assertDoesNotThrow(() -> new ZipCode(validZipCode)
                , "The constructor should not throw an exception for a valid zip code.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when provided with a null zip code.
     *
     * <p>
     * This test verifies that the ZipCode constructor throws an IllegalArgumentException
     * when provided with a null zip code, as it is an invalid argument. It ensures that the constructor
     * performs proper input validation and handles invalid input as expected.
     * </p>
     */
    @Test
    void testConstructorThrowsExceptionForNullZipCode() {
        // Arrange
        String nullZipCode = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(nullZipCode)
                , "The constructor should throw an exception for a null zip code.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when provided with a blank zip code.
     *
     * <p>
     * This test verifies that the ZipCode constructor throws an IllegalArgumentException
     * when provided with a blank (empty or whitespace-only) zip code, as it is considered invalid.
     * It ensures that the constructor properly handles invalid input to maintain data integrity.
     * </p>
     */
    @Test
    void testConstructorThrowsExceptionForBlankZipCode() {
        // Arrange
        String blankZipCode = " ";
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(blankZipCode)
                , "The constructor should throw an exception for a blank zip code.");
    }

    /**
     * Test the constructor of the class ZipCode.
     * The constructor should throw an exception for a zip code in the wrong format.
     * The zip code as the wrong number of digits.
     */
    @Test
    void testConstructorThrowsExceptionForZipCodeFormatNotInTheSystemWrongNumberOfDigits() {
        // Arrange
        String invalidZipCodeFormat = "123456"; //wrong number of digits
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(invalidZipCodeFormat)
                , "The constructor should throw an exception for a zip code with the wrong number of digits.");
    }

    /**
     * Test the constructor of the class ZipCode.
     * The constructor should throw an exception for a zip code in the wrong format.
     * The zip code has the correct number of digits but with a letter.
     */
    @Test
    void testConstructorThrowsExceptionForZipCodeFormatNotInTheSystemWrongUsageOfLetters() {
        // Arrange
        String invalidZipCodeFormat = "1234A"; //correct number of digits but with a letter
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(invalidZipCodeFormat)
                , "The constructor should throw an exception for a zip code with the wrong usage of letters.");
    }

    /**
     * Test the constructor of the class ZipCode.
     * The constructor should throw an exception for a zip code in the wrong format.
     * The zip code has the correct number of digits but is missing the correct symbol.
     */
    @Test
    void testConstructorThrowsExceptionForZipCodeFormatNotInTheSystemMissingCorrectSymbols() {
        // Arrange
        String invalidZipCodeFormat = "1234123"; //correct number of digits missing the hyphen
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(invalidZipCodeFormat)
                , "The constructor should throw an exception for a zip code with the wrong usage of symbols.");
    }

    /**
     * Test the constructor of the class ZipCode.
     * The constructor should throw an exception for a zip code in the wrong format.
     * The zip code has the correct number of digits but using a wrong symbol.
     */
    @Test
    void testConstructorThrowsExceptionForZipCodeFormatNotInTheSystemWrongSymbols() {
        // Arrange
        String invalidZipCodeFormat = "1234.123"; //correct number of digits using a wrong symbol
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ZipCode(invalidZipCodeFormat)
                , "The constructor should throw an exception for a zip code with the wrong usage of symbols.");
    }

    /**
     * Tests that the getZipCode() method returns the correct zip code.
     *
     * <p>
     * This test verifies that the ZipCode#getZipCode() method returns the correct zip code
     * set during object construction. It ensures the getter method's functionality to retrieve
     * the zip code stored within a ZipCode object.
     * </p>
     */
    @Test
    void testGetZipCodeReturnsCorrectValue() {
        //Arrange
        String expected = "3040-382";
        ZipCode zipCode = new ZipCode(expected);
        //Act
        String result = zipCode.getZipCode();
        //Assert
        assertEquals(expected, result
                , "The getZipCode() method should return the correct zip code.");
    }

    /**
     * Tests the {@link ZipCode#equals(Object)} method for comparing the same zip code objects.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two references to the same ZipCode object as equal. It ensures the method's
     * functionality to compare object equality based on their zip code strings.
     * </p>
     */
    @Test
    void testEqualsForSameForSameObjectUsingSameZipCodeObjects() {
        // Arrange
        String zipCodeValue = "4730-062";
        ZipCode zipCode = new ZipCode(zipCodeValue);
        // Act
        boolean result = zipCode.equals(zipCode);
        // Assert
        assertTrue(result
                , "The equals(Object) method should return true for the same ZipCode object.");
    }

    /**
     * Tests the ameAs(Object) method for comparing different zip code objects with the same zip code.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two different ZipCode objects with the same zip code as equal. It ensures the method's
     * functionality to compare object equality based on their zip code strings.
     * </p>
     */
    @Test
    void testEqualsForSameZipCodeComparingDifferentObjects() {
        // Arrange
        String zipCodeValue = "3250-206";
        ZipCode zipCode1 = new ZipCode(zipCodeValue);
        ZipCode zipCode2 = new ZipCode(zipCodeValue);
        // Act & Assert
        assertTrue(zipCode1.equals(zipCode2)
                , "The equals(Object) method should return true for different ZipCode objects with the same zip code.");
    }

    /**
     * Tests the equals(Object) method for comparing different zip code objects with different zip codes.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two different ZipCode objects with different zip codes as not equal. It ensures the method's
     * functionality to compare object equality based on their zip code strings.
     * </p>
     */
    @Test
    void testEqualsForDifferentZipCode() {
        // Arrange
        String zipCodeValue1 = "8400-317";
        String zipCodeValue2 = "8009-012";
        ZipCode zipCode1 = new ZipCode(zipCodeValue1);
        ZipCode zipCode2 = new ZipCode(zipCodeValue2);
        // Act & Assert
        assertFalse(zipCode1.equals(zipCode2)
                , "The equals(Object) method should return false for different ZipCode objects with different zip codes.");
    }

    /**
     * Tests the equals(Object) method for comparing with a null object.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * a ZipCode object as not equal to null. It ensures the method's functionality
     * to compare object equality even when one of the objects is null.
     * </p>
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        String zipCodeValue = "3500-003";
        ZipCode zipCode = new ZipCode(zipCodeValue);
        // Act & Assert
        assertFalse(zipCode.equals(null)
                , "The equals(Object) method should return false for a null object.");
    }

    /**
     * Tests the equals(Object) method for comparing with an object of a different class.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies a ZipCode object
     * as not equal to an object of a different class. It ensures the method's functionality to compare object equality
     * across different classes. In this test scenario, a ZipCode object is compared with an instance of Object,
     * which is expected to result in inequality. This test validates the proper behavior of the equals(Object)
     * method when comparing with objects of different types.
     * </p>
     */
    @Test
    void testEqualsForDifferentClass() {
        // Arrange
        String zipCodeValue = "2714-557";
        ZipCode zipCode = new ZipCode(zipCodeValue);
        // Act
        boolean result = zipCode.equals(new Object());
        // Assert
        assertFalse(result
                , "The equals(Object) method should return false for an object of a different class.");
    }

    /**
     * Acceptance test.
     *
     * <p>
     * Verifies that the equals(Object) method correctly identifies two different zip codes as not equal.
     * This test ensures that the method's functionality properly handles distinct zip codes, indicating that they are indeed
     * different values. It validates the expected behavior of the equals(Object) method for comparing
     * different zip code values.
     * </p>
     */
    @Test
    void testEqualsForDifferentZipCodes() {
        // Arrange
        ZipCode zipCode1 = new ZipCode("1749-110");
        ZipCode zipCode2 = new ZipCode("1349-040");
        // Act
        boolean result = zipCode1.equals(zipCode2);
        // Assert
        assertFalse(result
                , "The equals(Object) method should return false for different ZipCode objects.");
    }

/**
     * Acceptance test.
     *
     * <p>
     * Verifies that the equals(Object) method correctly identifies a ZipCode object as equal to itself.
     * This test ensures that the method's functionality properly handles the comparison of a ZipCode object with itself,
     * indicating that they are indeed the same object. It validates the expected behavior of the equals(Object) method
     * for comparing the same ZipCode object.
     * </p>
     */
    @Test
    void testEqualsForSameZipCodeObject() {
        // Arrange
        ZipCode zipCode = new ZipCode("1749-110");
        // Act
        boolean result = zipCode.equals(zipCode);
        // Assert
        assertTrue(result
                , "The equals(Object) method should return true for the same ZipCode object.");
    }

    /**
     * Tests the hashCode() method for equal objects.
     * <p>
     * This test verifies that the hashCode() method correctly generates the same hash code for equal ZipCode objects.
     * It ensures that the method's functionality to generate hash codes is consistent with the equals(Object) method,
     * where equal objects are expected to have the same hash code.
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        ZipCode ourZipCode = new ZipCode("4200-072");
        ZipCode anotherZipCode = new ZipCode("4200-072");
        // Act
        int hashCode = ourZipCode.hashCode();
        int otherHashCode = anotherZipCode.hashCode();
        // Assert
        assertEquals(hashCode, otherHashCode
                , "The hashCode() method should return the same hash code for equal ZipCode objects.");
    }

    /**
     * Tests the hashCode() method for different objects.
     *
     * <p>
     * This test verifies that the hashCode() method correctly generates different hash codes for different ZipCode objects.
     * It ensures that the method's functionality to generate hash codes is consistent with the equals(Object) method,
     * where different objects with different zip codes are expected to have different hash codes.
     * </p>
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        ZipCode ourZipCode = new ZipCode("4200-072");
        ZipCode anotherZipCode = new ZipCode("4200-027");
        // Act
        int hashCode = ourZipCode.hashCode();
        int otherHashCode = anotherZipCode.hashCode();
        // Assert
        assertNotEquals(hashCode, otherHashCode
                , "The hashCode() method should return different hash codes for different ZipCode objects.");
    }
}
