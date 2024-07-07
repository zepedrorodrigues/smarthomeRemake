package smarthome.domain.house.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the Country class.
 */
class CountryTest {

    /**
     * Test the constructor of the class Country.
     * The constructor should not throw an exception for a valid country in the system.
     */
    @Test
    void testConstructorDoesNotThrowsExceptionForValidCountry() {
        //Arrange
        String validCountry = "Portugal";

        //Act and Assert
        assertDoesNotThrow(() -> new Country(validCountry),
                "Country constructor should not throw an exception for a valid country");
    }

    /**
     * Test the constructor of the class Country.
     * The constructor should throw an exception for a null country.
     */
    @Test
    void testConstructorThrowsExceptionForNullCountry() {
        //Arrange
        String blankCountry = null;

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Country(blankCountry),
                "Country constructor should throw an exception for a null country");
    }

    /**
     * Test the constructor of the class Country.
     * The constructor should throw an exception for a blank country.
     */
    @Test
    void testConstructorThrowsExceptionForBlankCountry() {
        //Arrange
        String blankCountry = " ";

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Country(blankCountry),
                "Country constructor should throw an exception for a blank country");
    }

    /**
     * Test the constructor of the class Country.
     * The constructor should throw an exception for a country not in the system.
     */
    @Test
    void testConstructorThrowsExceptionForCountryNotInTheSystem() {
        //Arrange
        String invalidCountry = "Germany";

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new Country(invalidCountry),
                "Country constructor should throw an exception for a country not in the system");
    }

    /**
     * Test the getCountry method of the class Country.
     * The method should return the correct country value.
     */
    @Test
    void testGetCountryReturnsCorrectValue() {
        //Arrange
        String expected = "Portugal";
        Country country = new Country(expected);

        //Act
        String result = country.getCountryName();

        //Assert
        assertEquals(expected, result,
                "Country getCountryName method should return the correct country value");
    }

    /**
     * Test the equals method of the class Country.
     * The method should return true for the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        Country country = new Country("Portugal");

        // Act - Assert
        assertEquals(country, country,
                "Country equals method should return true for the same object");
    }

    /**
     * Test the equals method of the class Country.
     * The method should return false for a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        Country country = new Country("Portugal");
        Country nullCountry = null;

        // Act - Assert
        assertNotEquals(country, nullCountry,
                "Country equals method should return false for a null object");
    }

    /**
     * Test the equals method of the class Country.
     * The method should return false for a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        Country country = new Country("Portugal");

        // Act - Assert
        assertNotEquals(country, new Object(),
                "Country equals method should return false for a different object");
    }

    /**
     * Test the equals method of the class Country.
     * The method should return true for the same country value.
     */
    @Test
    void testEqualsForSameCountryValue() {
        // Arrange
        String validCountry = "Portugal";
        Country country1 = new Country(validCountry);
        Country country2 = new Country(validCountry);

        // Act - Assert
        assertEquals(country1, country2,
                "Country equals method should return true for the same country value");
    }

    /**
     * Test the equals method of the class Country.
     * The method should return false for a different country value.
     */
    @Test
    void testEqualsForDifferentCountryValue() {
        // Arrange
        String validCountryA = "Portugal";
        String validCountryB = "Spain";
        Country country1 = new Country(validCountryA);
        Country country2 = new Country(validCountryB);

        // Act - Assert
        assertNotEquals(country1, country2,
                "Country equals method should return false for a different country value");
    }

    /**
     * Test the hashCode method of the class Country.
     * The method should return the same hash code for the same country value.
     */
    @Test
    void testHashCodeForSameCountryValue() {
        // Arrange
        String validCountry = "Portugal";
        Country country1 = new Country(validCountry);
        Country country2 = new Country(validCountry);

        // Act
        int hashCode1 = country1.hashCode();
        int hashCode2 = country2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "Country hashCode method should return the same hash code for the same country value");
    }

    /**
     * Test the hashCode method of the class Country.
     * The method should return different hash codes for different country values.
     */
    @Test
    void testHashCodeForDifferentCountryValue() {
        // Arrange
        String validCountryA = "Portugal";
        String validCountryB = "Spain";
        Country country1 = new Country(validCountryA);
        Country country2 = new Country(validCountryB);

        // Act
        int hashCode1 = country1.hashCode();
        int hashCode2 = country2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "Country hashCode method should return different hash codes for different country values");
    }
}
