package smarthome.domain.house.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the City class.
 */
class CityTest {

    /**
     * Tests that the constructor does not throw an exception when provided with a valid city name.
     * <p>
     * This test verifies that the City constructor successfully creates a new instance
     * of City when provided with a valid city name. It verifies the correctness of the
     * constructor's behavior under normal conditions.
     * </p>
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidCity() {
        // Arrange
        String validCity = "Porto";
        // Act & Assert
        assertDoesNotThrow(() -> new City(validCity)
                , "City constructor should not throw an exception for a valid city name.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when provided with a null city name.
     * <p>
     * This test verifies that the City constructor throws an IllegalArgumentException
     * when provided with a null city name, as it is an invalid argument. It ensures that the constructor
     * performs proper input validation and handles invalid input as expected.
     * </p>
     */
    @Test
    void testConstructorThrowsExceptionForNullCity() {   // can be considered acceptance test
        // Arrange
        String nullCity = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new City(nullCity)
                , "City constructor should throw an exception for a null city name.");
    }

    /**
     * Tests that the constructor throws an IllegalArgumentException when provided with a blank city name.
     *
     * <p>
     * This test verifies that the City constructor throws an IllegalArgumentException
     * when provided with a blank (empty or whitespace-only) city name, as it is considered invalid.
     * It ensures that the constructor properly handles invalid input to maintain data integrity.
     * </p>
     */
    @Test
    void testConstructorThrowsExceptionForBlankCity() {  // can be considered acceptance test
        // Arrange
        String blankCity = " ";
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new City(blankCity)
                , "City constructor should throw an exception for a blank city name.");
    }

    /**
     * Tests that the getCity() method returns the correct city name.
     *
     * <p>
     * This test verifies that the getCity() method returns the correct city name
     * set during object construction. It ensures the getter method's functionality to retrieve
     * the city name stored within a City object.
     * </p>
     */
    @Test
    void testGetCityReturnsCorrectValue() {                     // can be considered acceptance test (usability test)
        //Arrange
        String expected = "Coimbra";
        City homeCity = new City(expected);
        //Act
        String result = homeCity.getCity();
        //Assert
        assertEquals(expected, result
                , "getCity() should return the correct city name.");
    }

    /**
     * Tests the equals(Object) method for comparing the same city objects.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two references to the same City object as equal. It ensures the method's
     * functionality to compare object equality based on their city names.
     * </p>
     */
    @Test
    void testEqualsForSameObjectUsingSameCityObjects() {
        // Arrange
        String cityValue = "Ovar";
        City homeCity = new City(cityValue);
        // Act
        boolean result = homeCity.equals(homeCity);
        // Assert
        assertTrue(result
                , "equals() should return true when comparing the same City object.");
    }

    /**
     * Tests the equals(Object) method for comparing different city objects with the same city name.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two different City objects with the same city name as equal. It ensures the method's
     * functionality to compare object equality based on their city names.
     * </p>
     */
    @Test
    void testEqualsForSameZipCodeComparingDifferentObjects() {
        // Arrange
        String cityValue = "Oliveira de Azeméis";
        City homeCity1 = new City(cityValue);
        City homeCity2 = new City(cityValue);
        // Act & Assert
        assertTrue(homeCity1.equals(homeCity2)
                , "equals() should return true when comparing different City objects with the same city name.");
    }

    /**
     * Tests the equals(Object) method for comparing different city objects with different city names.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * two different City objects with different city names as not equal. It ensures the method's
     * functionality to compare object equality based on their city names.
     * </p>
     */
    @Test
    void testEqualsForDifferentCity() {
        // Arrange
        String cityValue1 = "São Martinho da Gândara";
        String cityValue2 = "Sao Martinho da Gandara";
        City homeCity1 = new City(cityValue1);
        City homeCity2 = new City(cityValue2);
        // Act & Assert
        assertFalse(homeCity1.equals(homeCity2)
                , "equals() should return false when comparing different City objects with different city names.");
    }

    /**
     * Tests the equals(Object) method for comparing a City object with a null object.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * a City object as not equal to a null object. It ensures the method's
     * functionality to compare object equality based on their city names.
     * </p>
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        String cityValue = "Porto Côvo";
        City homeCity = new City(cityValue);
        // Act & Assert
        assertFalse(homeCity.equals(null)
                , "equals() should return false when comparing a City object with a null object.");
    }

    /**
     * Tests the equals(Object) method for comparing a City object with an object of a different class.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies
     * a City object as not equal to an object of a different class. It ensures the method's
     * functionality to compare object equality based on their city names.
     * </p>
     */
    @Test
    void testequalsForDifferentClass() {
        // Arrange
        String cityValue = "Sintra";
        City homeCity = new City(cityValue);
        // Act
        boolean result = homeCity.equals(new Object());
        // Assert
        assertFalse(result
                , "equals() should return false when comparing a City object with an object of a different class.");
    }


    /**
     * Acceptance test: Tests that a valid city name containing special characters is handled correctly.
     *
     * <p>
     * This test verifies that the City constructor properly handles a valid city name containing
     * special characters (e.g., hyphen). It ensures that the constructor correctly initializes a City
     * object with the provided city name, preserving data integrity and allowing for diverse city name formats.
     * </p>
     */
    @Test
    void testValidCityNameWithSpecialCharacters() {
        // Arrange
        String cityValue = "Peneda-Gerês";
        // Act
        City homeCity = new City(cityValue);
        // Assert
        assertNotNull(homeCity);
        assertEquals(cityValue, homeCity.getCity()
                , "City constructor should handle a valid city name containing special characters.");
    }

    /**
     * Acceptance test: Tests that a valid city name containing numbers is handled correctly.
     *
     * <p>
     * This test verifies that the City constructor properly handles a valid city name containing
     * numeric characters. It ensures that the constructor correctly initializes a City object with
     * the provided city name, allowing for city names that include numerical identifiers or designations.
     * </p>
     */
    @Test
    void testValidCityNameWithNumbers() {
        // Arrange
        String cityValue = "Caminha 123";
        // Act
        City homeCity = new City(cityValue);
        // Assert
        assertNotNull(homeCity);
        assertEquals(cityValue, homeCity.getCity()
                , "City constructor should handle a valid city name containing numbers.");
    }

    /**
     * Acceptance test: Tests that a short city name is handled correctly.
     *
     * <p>
     * This test verifies that the City constructor properly handles a short city name
     * consisting of only one character. It ensures that the constructor correctly initializes
     * a City object with the provided short city name, allowing for city names of varying lengths.
     * </p>
     */
    @Test
    void testShortCityName() {
        // Arrange
        String shortCityName = "A";
        // Act
        City homeCity = new City(shortCityName);
        // Assert
        assertNotNull(homeCity);
        assertEquals(shortCityName, homeCity.getCity()
                , "City constructor should handle a short city name.");
    }

    /**
     * Acceptance test: Tests that a long city name is handled correctly.
     *
     * <p>
     * This test verifies that the City constructor properly handles a long city name,
     * consisting of multiple words or hyphenated segments. It ensures that the constructor
     * correctly initializes a City object with the provided long city name, allowing
     * for city names of extended lengths.
     * </p>
     */
    @Test
    void testLongCityName() {
        // Arrange
        String longCityName = "Freixo-de-Espada-à-Cinta";
        // Act
        City homeCity = new City(longCityName);
        // Assert
        assertNotNull(homeCity);
        assertEquals(longCityName, homeCity.getCity()
                , "City constructor should handle a long city name.");
    }

    /**
     * Acceptance test: Tests that two different city objects with different city names are not considered equal.
     *
     * <p>
     * This test verifies that the equals(Object) method correctly identifies two different
     * City objects with distinct city names as not equal. It ensures that the method's behavior
     * adheres to the requirement that equality is based on the city name, and objects with different city names
     * should not be considered equal.
     * </p>
     */
    @Test
    void testNotEqualForDifferentCityNames() {
        // Arrange
        City homeCity1 = new City("Faro");
        City homeCity2 = new City("Chaves");
        // Act & Assert
        assertNotEquals(homeCity1, homeCity2
                , "City objects with different city names should not be considered equal.");
    }

    /**
     * <p>
     * This test verifies that the hashCode() method returns the same hash code for equal City objects.
     * It ensures that the method's behavior adheres to the requirement that equal objects must have the same hash code.
     * </p>
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        City homeCity = new City("Porto");
        City anotherCity = new City("Porto");
        // Act
        int hashCode = homeCity.hashCode();
        int otherHashCode = anotherCity.hashCode();
        // Assert
        assertEquals(hashCode, otherHashCode
                , "Equal City objects should have the same hash code.");
    }

    /**
     * <p>
     * This test verifies that the hashCode() method returns different hash codes for different City objects
     * with distinct city names. It ensures that the method's behavior adheres to the requirement that equal objects
     * must have the same hash code, while different objects should have different hash codes.
     * </p>
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        City homeCity = new City("Porto");
        City anotherCity = new City("Paris");
        // Act
        int hashCode = homeCity.hashCode();
        int otherHashCode = anotherCity.hashCode();
        // Assert
        assertNotEquals(hashCode, otherHashCode
                , "Different City objects should have different hash codes.");
    }
}