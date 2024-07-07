/**
 * This class contains unit tests for the Location class.
 * Each method in this class corresponds to a method in the Location class.
 * The tests use mock objects for Address and Gps to isolate the behavior of the Location class.
 */

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

class LocationTest {

    Address addressMock;
    Gps gpsMock;
    Location location;

    /**
     * This method sets up the test environment before each test method.
     * It creates mock objects for Address and Gps and uses them to create a Location object.
     */
    @BeforeEach
    void setUp() {
        addressMock = mock(Address.class);
        gpsMock = mock(Gps.class);
        location = new Location(addressMock, gpsMock);
    }

    /**
     * This test checks if the Location constructor creates a valid Location object.
     */
    @Test
    void testConstructorValidLocation() {
        // Assert
        assertNotNull(location, "Location object should not be null when created with a valid Address and Gps");
    }

    /**
     * This test checks if the Location constructor throws an IllegalArgumentException when the Address is null.
     */
    @Test
    void testConstructorInvalidAddressNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Location(null, gpsMock), "Location constructor should "
                + "throw an IllegalArgumentException when the Address is null");
    }

    /**
     * This test checks if the Location constructor throws an IllegalArgumentException when the Gps is null.
     */
    @Test
    void testConstructorInvalidGpsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Location(addressMock, null),
                "Location constructor " + "should throw an IllegalArgumentException when the Gps is null");
    }

    /**
     * This test checks if the getAddress method returns the correct Address object.
     */
    @Test
    void testGetAddress() {
        //Act
        Address result = location.getAddress();
        //Assert
        assertEquals(addressMock, result,
                "The Address object returned by getAddress should be the same as the one " + "passed to the " +
                        "constructor");
    }

    /**
     * This test checks if the getGps method returns the correct Gps object.
     */
    @Test
    void testGetGps() {
        //Act
        Gps result = location.getGps();
        //Assert
        assertEquals(gpsMock, result, "The Gps object returned by getGps should be the same as the one passed to the "
                + "constructor");
    }

    /**
     * This test checks if the equals method returns true for two Location objects with the same Address and Gps.
     */
    @Test
    void testSameForSameAddressAndGps() {
        // Arrange
        Location location2 = new Location(addressMock, gpsMock);
        // Act
        boolean result = location.equals(location2);
        // Assert
        assertTrue(result,
                "The equals method should return true for two Location objects with the same Address and " + "Gps");
    }

    /**
     * This test checks if the equals method returns true when comparing a Location object to itself.
     */
    @Test
    void testEqualsForSameObject() {
        // Act
        boolean result = location.equals(location);
        // Assert
        assertTrue(result, "The equals method should return true when comparing a Location object to itself");
    }

    /**
     * This test checks if the equals method returns false for two Location objects with different Address and Gps.
     */
    @Test
    void testEqualsDifferentLocation() {
        // Arrange
        Address addressMock2 = mock(Address.class);
        Gps gpsMock2 = mock(Gps.class);
        Location location2 = new Location(addressMock2, gpsMock2);
        // Act
        boolean result = location.equals(location2);
        // Assert
        assertFalse(result,
                "The equals method should return false for two Location objects with different Address " + "and Gps");
    }

    /**
     * This test checks if the equals method returns false when comparing a Location object to null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Act
        boolean result = location.equals(null);
        // Assert
        assertFalse(result, "The equals method should return false when comparing a Location object to null");
    }

    /**
     * This test checks if the equals method returns false when comparing a Location object to an object of a
     * different class.
     */
    @Test
    void testEqualsDifferentObject() {
        // Act
        boolean result = location.equals(new Object());
        // Assert
        assertFalse(result,
                "The equals method should return false when comparing a Location object to an object of " + "a" + " " + "different class");
    }

    /**
     * Tests that the hashCode method returns the same value for two equal objects.
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        Location location2 = new Location(addressMock, gpsMock);
        // Act
        int hashCode1 = location.hashCode();
        int hashCode2 = location2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "The hashCode method should return the same value for two equal objects");
    }

    /**
     * Tests that the hashCode method returns different values for two different objects.
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        Address differentAddress = mock(Address.class);
        Gps differentGps = mock(Gps.class);
        Location differentLocation = new Location(differentAddress, differentGps);
        // Act
        int hashCode1 = location.hashCode();
        int hashCode2 = differentLocation.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The hashCode method should return different values for two different " + "objects");
    }
}