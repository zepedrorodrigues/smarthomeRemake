package smarthome.domain.house;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.house.vo.Address;
import smarthome.domain.house.vo.City;
import smarthome.domain.house.vo.Country;
import smarthome.domain.house.vo.Gps;
import smarthome.domain.house.vo.HouseName;
import smarthome.domain.house.vo.Latitude;
import smarthome.domain.house.vo.Location;
import smarthome.domain.house.vo.Longitude;
import smarthome.domain.house.vo.StreetName;
import smarthome.domain.house.vo.StreetNumber;
import smarthome.domain.house.vo.ZipCode;
import smarthome.utils.AvailableCountries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The HouseTest class contains unit tests for the House class.
 * It tests the constructor, getter methods, and the equals method of the House class.
 */
class HouseTest {
    HouseName houseName;

    Location location;

    House house;

    /**
     * Sets up the test environment before each test.
     * It initializes the mocked HouseName and Location, and the House instance.
     */
    @BeforeEach
    void setUp() {
        houseName = new HouseName("House 1");
        StreetName streetName = new StreetName("Street 1");
        StreetNumber streetNumber = new StreetNumber("1");
        ZipCode zipCode = new ZipCode("4000-000"); // Use a valid zip code for Portugal
        City city = new City("City 1");
        Country country = new Country(AvailableCountries.PORTUGAL.getCountry());
        Address address = new Address(streetName, streetNumber, zipCode, city, country);

        Latitude latitude = new Latitude(1.0);
        Longitude longitude = new Longitude(1.0);
        Gps gps = new Gps(latitude, longitude);

        location = new Location(address, gps);
        house = new House(houseName, location);
    }

    /**
     * Test to verify that the constructor of the House class creates a valid object when provided with
     * valid HouseName and Location.
     */
    @Test
    void testConstructorValidHouse() {
        // Assert
        assertNotNull(house, "The house should not be null");
    }

    /**
     * Test to verify that the constructor of the House class throws an IllegalArgumentException when
     * provided with a null HouseName.
     */
    @Test
    void testConstructorInvalidHouseNameNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new House(null, location), "The constructor should throw " +
                "an IllegalArgumentException when provided with a null HouseName");
    }

    /**
     * Test to verify that the constructor of the House class throws an IllegalArgumentException when
     * provided with a null Location.
     */
    @Test
    void testConstructorInvalidLocationNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new House(houseName, null), "The constructor should throw " +
                "an IllegalArgumentException when provided with a null Location");
    }

    /**
     * Test to verify that the getLocation method of the House class returns the correct Location.
     */
    @Test
    void testGetLocation() {
        // Act
        Location result = house.getLocation();
        // Assert
        assertEquals(location, result, "The location should be the same");
    }

    /**
     * Test to verify that the getIdentity method of the House class returns the correct HouseName.
     */
    @Test
    void testGetIdentity() {
        // Act
        HouseName result = house.getIdentity();
        // Assert
        assertEquals(houseName, result, "The house name should be the same");
    }

    /**
     * Test to verify that the equals method of the House class returns true when the HouseName is the same.
     */
    @Test
    void testequalsHouseName() {
        //Arrange
        House house2 = new House(houseName, location);
        // Act
        boolean result = house.equals(house);
        // Assert
        assertTrue(result, "The houses should be the same");
    }

    /**
     * Test to verify that the equals method of the House class returns true when the Location is the same.
     */
    @Test
    void testequalsLocation() {
        //Arrange
        House house2 = new House(houseName, location);
        // Act
        boolean result = house.equals(house2);
        // Assert
        assertTrue(result, "The houses should be the same");
    }

    /**
     * Test to verify that the equals method of the House class returns false when the HouseNames are different.
     */
    @Test
    void testequalsDifferentHouseName() {
        //Arrange
        HouseName houseName2 = new HouseName("House 2"); // Use um valor diferente de "House 1"
        House house2 = new House(houseName2, location);
        // Act
        boolean result = house.equals(house2);
        // Assert
        assertFalse(result, "The houses should be different");
    }


    /**
     * Test to verify that the equals method of the House class returns false when compared with an object of a different class.
     */
    @Test
    void testequalsDifferentObject() {
        // Act
        boolean result = house.equals(new Object());
        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Test to verify that the equals method of the House class returns false when compared with null.
     */
    @Test
    void testequalsNullObject() {
        // Act
        boolean result = house.equals(null);
        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Test to verify that the equals method of the House class returns true when compared with the same object.
     */
    @Test
    void testequalsSameObject() {
        // Act
        boolean result = house.equals(house);
        // Assert
        assertTrue(result, "The objects should be the same");
    }


    /**
     * Test to verify that the configLocation method of the House class returns IllegalArgumentException
     * when provided with a null Location.
     */
    @Test
    void configLocationWhenLocationIsNullShouldReturnIllegalArgumentException() {
        // Arrange
        Location newLocation = null;
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> house.configLocation(newLocation),
                "The location is null, so it should throw an IllegalArgumentException");
    }

    /**
     * Test to verify that the configLocation method of the House class does not change the location when provided
     * with a null Location.
     */
    @Test
    void configLocationWhenLocationIsNullShouldNotChangeLocation() {
        // Arrange
        Location newLocation = null;
        // Act
        try {
            house.configLocation(newLocation);
        } catch (Exception e) {
            // Do nothing, continue with the test
        }
        Location resultLocation = house.getLocation();
        // Assert
        assertEquals(location, resultLocation, "The location should be the same");
    }

    /**
     * Test to verify that the configLocation method of the House class changes the location when provided with
     * a valid Location new location.
     */
    @Test
    void configLocationWhenLocationIsNotNullShouldChangeLocation() {
        // Arrange
        StreetName streetName2 = new StreetName("Street 2");
        StreetNumber streetNumber2 = new StreetNumber("2");
        ZipCode zipCode2 = new ZipCode("23456");
        City city2 = new City("City 2");
        Country country2 = new Country("FRANCE");
        Address address2 = new Address(streetName2, streetNumber2, zipCode2, city2, country2);

        Latitude latitude2 = new Latitude(2.0);
        Longitude longitude2 = new Longitude(2.0);
        Gps gps2 = new Gps(latitude2, longitude2);

        Location newLocation = new Location(address2, gps2);

        // Act
        house.configLocation(newLocation);
        boolean result = house.getLocation().equals(newLocation);

        // Assert
        assertTrue(result, "The location should be the same");
    }

    /**
     * Test to verify that the configLocation method of the House class does accept the same location to be configured.
     * The location should stay with the same value.
     */
    @Test
    void configLocationWhenTheNewLocationIsTheequalsTheOldOneShouldStayTheSameLocation() {
        // Arrange + Act
        house.configLocation(location);
        boolean result = house.getLocation().equals(location);

        // Assert
        assertTrue(result, "The location should be the same");
    }

    /**
     * Test to verify that the configLocation method of the House class returns the new location when provided with
     * a valid Location new location.
     */
    @Test
    void configLocationWhenLocationIsNotNullShouldReturnNewLocation() {
        // Arrange
        StreetName streetName2 = new StreetName("Street 2");
        StreetNumber streetNumber2 = new StreetNumber("2");
        ZipCode zipCode2 = new ZipCode("23456");
        City city2 = new City("City 2");
        Country country2 = new Country("SPAIN");
        Address address2 = new Address(streetName2, streetNumber2, zipCode2, city2, country2);

        Latitude latitude2 = new Latitude(2.0);
        Longitude longitude2 = new Longitude(2.0);
        Gps gps2 = new Gps(latitude2, longitude2);

        Location newLocation = new Location(address2, gps2);

        // Act
        Location resultLocation = house.configLocation(newLocation);

        // Assert
        assertEquals(newLocation, resultLocation, "The location should be the same");
    }

    /**
     * Test to verify that the configLocation method of the House class returns a not null location when provided with
     * a valid Location new location.
     */
    @Test
    void configLocationWhenLocationIsNotNullShouldReturnNotNull() {
        // Arrange
        StreetName streetName2 = new StreetName("Street 2");
        StreetNumber streetNumber2 = new StreetNumber("2");
        ZipCode zipCode2 = new ZipCode("23456");
        City city2 = new City("City 2");
        Country country2 = new Country("SPAIN");
        Address address2 = new Address(streetName2, streetNumber2, zipCode2, city2, country2);

        Latitude latitude2 = new Latitude(2.0);
        Longitude longitude2 = new Longitude(2.0);
        Gps gps2 = new Gps(latitude2, longitude2);

        Location newLocation = new Location(address2, gps2);

        // Act
        Location resultLocation = house.configLocation(newLocation);

        // Assert
        assertNotNull(resultLocation, "The location should not be null");
    }

    /**
     * Test to verify that the hashCode method of the House class returns the same hash code when
     * the HouseNames are the same.
     */
    @Test
    void testHashCodeSameHouseName() {
        // Arrange
        HouseName houseName2 = new HouseName("House 1");
        House house2 = new House(houseName2, location);

        // Act
        int hashCode1 = house.hashCode();
        int hashCode2 = house2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2, "The hash codes should be the same");
    }

    /**
     * Test to verify that the hashCode method of the House class returns different hash codes when
     * the HouseNames are different.
     */
    @Test
    void testHashCodeDifferentHouseName() {
        // Arrange
        HouseName houseName2 = new HouseName("House 2");
        House house2 = new House(houseName2, location);

        // Act
        int hashCode1 = house.hashCode();
        int hashCode2 = house2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hash codes should be different");
    }
}