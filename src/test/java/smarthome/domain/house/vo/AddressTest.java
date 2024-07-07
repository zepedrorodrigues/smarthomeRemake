package smarthome.domain.house.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is responsible for testing the Address class.
 * It uses mock objects for StreetName, StreetNumber, ZipCode, and City to isolate the Address class for testing.
 * It tests the constructor, getter methods, and the equals method of the Address class.
 * Each test method corresponds to a method in the Address class.
 * The tests use mock objects to isolate the behavior of the Address class.
 */
class AddressTest {

    StreetName streetNameDouble;
    StreetNumber streetNumberDouble;
    ZipCode zipCodeDouble;
    City cityDouble;
    Country countryDouble;

    String validStreetName;
    String validStreetNumber;
    String validZipCode;
    String validCity;
    String validCountry;
    Address address;

    /**
     * Sets up the test environment before each test method.
     */
    @BeforeEach
    void setUp() {
        streetNameDouble = mock(StreetName.class);
        streetNumberDouble = mock(StreetNumber.class);
        zipCodeDouble = mock(ZipCode.class);
        cityDouble = mock(City.class);
        countryDouble = mock(Country.class);
        validStreetName = "Main";
        validStreetNumber = "123";
        validZipCode = "1234-000";
        validCity = "TestCity";
        validCountry = "Portugal";
        when(streetNameDouble.getStreetName()).thenReturn(validStreetName);
        when(streetNumberDouble.getStreetNumber()).thenReturn(validStreetNumber);
        when(zipCodeDouble.getZipCode()).thenReturn(validZipCode);
        when(cityDouble.getCity()).thenReturn(validCity);
        when(countryDouble.getCountryName()).thenReturn(validCountry);
        address = new Address(streetNameDouble, streetNumberDouble, zipCodeDouble, cityDouble, countryDouble);
    }

    /**
     * This test checks if the Address object is not null when valid data is provided.
     */
    @Test
    void testConstructorValidAddress() {

        // Assert
        assertNotNull(address, "Address object should not be null when valid data is provided");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when the StreetName is null.
     */
    @Test
    void testConstructorInvalidAddressStreetNameNull() {
        // Arrange
        StreetName streetNameInvalid = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(streetNameInvalid, streetNumberDouble,
                zipCodeDouble, cityDouble, countryDouble), "Throws IllegalArgumentException when StreetName is null");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when the StreetNumber is null.
     */

    @Test
    void testConstructorInvalidAddressStreetNumberNull() {
        // Arrange
        StreetNumber streetNumberInvalid = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(streetNameDouble, streetNumberInvalid,
                zipCodeDouble, cityDouble, countryDouble), "Throws IllegalArgumentException when StreetNumber is null");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when the ZipCode does not match the Country.
     */
    @Test
    void testConstructorZipCodeNotMatchCountry() {
        // Arrange
        String zipcodeSpain = "12343";
        String countryPT = "Portugal";
        ZipCode zipCodeDoubleES = mock(ZipCode.class);
        Country countryDouble = mock(Country.class);
        when(zipCodeDoubleES.getZipCode()).thenReturn(zipcodeSpain);
        when(countryDouble.getCountryName()).thenReturn(countryPT);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(streetNameDouble, streetNumberDouble,
                zipCodeDoubleES, cityDouble, countryDouble),
                "Throws IllegalArgumentException when ZipCode does not " + "match the Country");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when the Country is null.
     */
    @Test
    void testConstructorCountryNull() {
        // Arrange
        String zipcodeSpain = "12343";
        String countryPT = null;
        ZipCode zipCodeDoubleES = mock(ZipCode.class);
        Country countryDouble = mock(Country.class);
        when(zipCodeDoubleES.getZipCode()).thenReturn(zipcodeSpain);
        when(countryDouble.getCountryName()).thenReturn(countryPT);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(streetNameDouble, streetNumberDouble,
                zipCodeDoubleES, cityDouble, countryDouble),
                "Throws IllegalArgumentException when ZipCode does not " + "match the Country");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when the ZipCode is null.
     */
    @Test
    void testConstructorInvalidAddressZipCodeNull() {
        // Arrange
        ZipCode zipCodeInvalid = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(streetNameDouble, streetNumberDouble,
                zipCodeInvalid, cityDouble, countryDouble), "Throws IllegalArgumentException when ZipCode is null");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when the City is null.
     */
    @Test
    void testConstructorInvalidAddressCityNull() {
        // Arrange
        City cityInvalid = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(streetNameDouble, streetNumberDouble,
                zipCodeDouble, cityInvalid, countryDouble), "Throws IllegalArgumentException when City is null");
    }

    /**
     * This test checks if an IllegalArgumentException is thrown when the Country is null.
     */
    @Test
    void testConstructorInvalidAddressCountryNull() {
        // Arrange
        Country countryInvalid = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Address(streetNameDouble, streetNumberDouble,
                zipCodeDouble, cityDouble, countryInvalid), "Throws IllegalArgumentException when Country is null");
    }

    /**
     * This test checks if the getStreetName method returns the correct StreetName.
     */
    @Test
    void testGetStreetName() {
        // Act
        StreetName result = address.getStreetName();
        // Assert
        assertEquals(streetNameDouble, result, "The result should be the same as the one provided in the constructor");
    }

    /**
     * This test checks if the getStreetNumber method returns the correct StreetNumber.
     */
    @Test
    void testGetStreetNumber() {
        // Act
        StreetNumber result = address.getStreetNumber();
        // Assert
        assertEquals(streetNumberDouble, result, "The result should be the same as the one provided in the " +
                "constructor");
    }

    /**
     * This test checks if the getZipCode method returns the correct ZipCode.
     */
    @Test
    void testGetZipCode() {
        // Act
        ZipCode result = address.getZipCode();
        // Assert
        assertEquals(zipCodeDouble, result, "The result should be the same as the one provided in the constructor");
    }

    /**
     * This test checks if the getCity method returns the correct City.
     */
    @Test
    void testGetCity() {
        // Act
        City result = address.getCity();
        // Assert
        assertEquals(cityDouble, result, "The result should be the same as the one provided in the constructor");
    }

    /**
     * This test checks if the getCountry method returns the correct Country.
     */
    @Test
    void testGetCountry() {
        // Act
        Country result = address.getCountry();
        // Assert
        assertEquals(countryDouble, result, "The result should be the same as the one provided in the constructor");
    }

    /**
     * This test checks if the equals method returns true when two identical Address objects are compared.
     */
    @Test
    void testEqualsForSameAddress() {
        //Arrange
        Address address2 = new Address(streetNameDouble, streetNumberDouble, zipCodeDouble, cityDouble, countryDouble);
        //Act
        boolean result = address.equals(address2);
        // Assert
        assertTrue(result, "The result should be true for the same address.");
    }

    /**
     * This test checks if the equals method returns true when comparing an Address object to itself.
     */
    @Test
    void testEqualsForSameObject() {
        // Act
        boolean result = address.equals(address);
        // Assert
        assertTrue(result, "The result should be true for the same object.");
    }

    /**
     * This test checks if the equals method returns false when two different Address objects are compared.
     */
    @Test
    void testEqualsDifferentAddress() {
        //Arrange
        StreetName streetName2 = mock(StreetName.class);
        StreetNumber streetNumber2 = mock(StreetNumber.class);
        ZipCode zipCode2 = mock(ZipCode.class);
        City city2 = mock(City.class);
        Country country2 = mock(Country.class);
        when(country2.getCountryName()).thenReturn(validCountry);
        when(zipCode2.getZipCode()).thenReturn(validZipCode);
        when(city2.getCity()).thenReturn(validCity);
        when(streetName2.getStreetName()).thenReturn(validStreetName);
        when(streetNumber2.getStreetNumber()).thenReturn(validStreetNumber);
        Address address2 = new Address(streetName2, streetNumber2, zipCode2, city2, country2);
        //Act
        boolean result = address.equals(address2);
        //Act & Assert
        assertFalse(result, "The result should be false for different addresses.");
    }

    /**
     * This test checks if the equals method returns false when the Address object is compared with null.
     */
    @Test
    void testEqualsNullObject() {
        //Act
        boolean result = address.equals(null);
        // Assert
        assertFalse(result, "The result should be false for null object.");
    }

    /**
     * This test checks if the equals method returns false when the Address object is compared with a different type
     * of object.
     */
    @Test
    void testEqualsDifferentObject() {
        //Act
        boolean result = address.equals(new Object());
        // Assert
        assertFalse(result, "The result should be false for different class object.");
    }

    /**
     * This test checks if the equals method returns false when the StreetName is different.
     */
    @Test
    void testEqualsDifferentStreetName() {
        //Arrange
        StreetName differentStreetName = mock(StreetName.class);
        when(differentStreetName.getStreetName()).thenReturn("Rua");
        Address differentAddress = new Address(differentStreetName, streetNumberDouble, zipCodeDouble, cityDouble,
                countryDouble);
        //Act
        boolean result = address.equals(differentAddress);
        // Assert
        assertFalse(result, "The result should be false for different street names.");
    }

    /**
     * This test checks if the equals method returns false when the StreetNumber is different.
     */
    @Test
    void testEqualsDifferentStreetNumber() {
        //Arrange
        StreetNumber differentStreetNumber = mock(StreetNumber.class);
        when(differentStreetNumber.getStreetNumber()).thenReturn("100");
        Address differentAddress = new Address(streetNameDouble, differentStreetNumber, zipCodeDouble, cityDouble,
                countryDouble);
        //Act
        boolean result = address.equals(differentAddress);
        // Assert
        assertFalse(result, "The result should be false for different street numbers.");
    }

    /**
     * This test checks if the equals method returns false when the ZipCode is different.
     */
    @Test
    void testEqualsDifferentZipCode() {
        //Arrange
        ZipCode differentZipCode = mock(ZipCode.class);
        when(differentZipCode.getZipCode()).thenReturn("1234-567");
        Address differentAddress = new Address(streetNameDouble, streetNumberDouble, differentZipCode, cityDouble,
                countryDouble);
        //Act
        boolean result = address.equals(differentAddress);
        // Assert
        assertFalse(result, "The result should be false for different zip codes.");
    }

    /**
     * This test checks if the equals method returns false when the City is different.
     */
    @Test
    void testEqualsDifferentCity() {
        //Arrange
        City differentCity = mock(City.class);
        when(differentCity.getCity()).thenReturn("DefaultCity");
        Address differentAddress = new Address(streetNameDouble, streetNumberDouble, zipCodeDouble, differentCity,
                countryDouble);
        //Act
        boolean result = address.equals(differentAddress);
        // Assert
        assertFalse(result, "The result should be false for different cities.");
    }

    /**
     * This test checks if the equals method returns false when the Country is different.
     */
    @Test
    void testEqualsDifferentCountry() {
        //Arrange
        Country differentCountry = mock(Country.class);
        ZipCode differentZipCode = mock(ZipCode.class);
        when(differentCountry.getCountryName()).thenReturn("Spain");
        when(differentZipCode.getZipCode()).thenReturn("01111");
        Address differentAddress = new Address(streetNameDouble, streetNumberDouble, differentZipCode, cityDouble,
                differentCountry);
        //Act
        boolean result = address.equals(differentAddress);
        // Assert
        assertFalse(result, "The result should be false for different countries.");
    }


    /**
     * Tests that the hashCode method returns the same value for two equal objects.
     */
    @Test
    void testHashCodeForEqualObjects() {
        //Arrange
        Address addressToCompare = new Address(streetNameDouble, streetNumberDouble, zipCodeDouble, cityDouble,
                countryDouble);
        //Act
        int result1 = address.hashCode();
        int result2 = addressToCompare.hashCode();
        // Assert
        assertEquals(result1, result2, "Hashcode should be the same for equal objects");
    }

    /**
     * Tests that the hashCode method returns false for two different objects.
     */
    @Test
    void testHashCodeForDifferentObjects() {

        StreetName streetName2 = mock(StreetName.class);
        StreetNumber streetNumber2 = mock(StreetNumber.class);
        ZipCode zipCode2 = mock(ZipCode.class);
        City city2 = mock(City.class);
        Country country2 = mock(Country.class);
        when(streetName2.getStreetName()).thenReturn("Rua");
        when(streetNumber2.getStreetNumber()).thenReturn("100");
        when(zipCode2.getZipCode()).thenReturn("1234-567");
        when(city2.getCity()).thenReturn("DefaultCity");
        when(country2.getCountryName()).thenReturn("Portugal");
        //Act
        Address differentAddress = new Address(streetName2, streetNumber2, zipCode2, city2, country2);
        int result1 = address.hashCode();
        int result2 = differentAddress.hashCode();
        // Assert
        assertNotEquals(result1, result2, "Hashcode should be different for different objects");
    }
}
