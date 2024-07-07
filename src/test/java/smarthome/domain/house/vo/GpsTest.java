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
 * This class is responsible for testing the Gps class.
 * It uses mock objects for the Latitude and Longitude classes.
 */
class GpsTest {

    Latitude latitudeDouble;
    Longitude longitudeDouble;
    double validLatitude;
    double validLongitude;
    Gps gps;

    /**
     * Sets up the necessary variables for the tests.
     * It initializes the latitude and longitude doubles, the latitude and longitude mocks, and the gps object.
     */
    @BeforeEach
    void setUp() {
        latitudeDouble = mock(Latitude.class);
        longitudeDouble = mock(Longitude.class);
        validLatitude = 1.0;
        validLongitude = 1.0;
        when(latitudeDouble.getLatitude()).thenReturn(validLatitude);
        when(longitudeDouble.getLongitude()).thenReturn(validLongitude);
        gps = new Gps(latitudeDouble, longitudeDouble);
    }

    /**
     * Tests the constructor of the Gps class with valid parameters.
     */
    @Test
    void testConstructorValidGps() {
        // Assert
        assertNotNull(gps, "Gps object should be successfully created.");
    }

    /**
     * Tests the constructor of the Gps class with an invalid latitude.
     */
    @Test
    void testConstructorInvalidGpsLatitudeNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Gps(null, longitudeDouble), "Gps object should not be " +
                "created with null latitude.");
    }

    /**
     * Tests the constructor of the Gps class with an invalid longitude.
     */
    @Test
    void testConstructorInvalidGpsLongitudeNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Gps(latitudeDouble, null), "Gps object should not be " +
                "created with null longitude.");
    }

    /**
     * Tests the getLatitude method of the Gps class.
     */
    @Test
    void testGetLatitude() {
        // Act
        Latitude result = gps.getLatitude();
        // Assert
        assertEquals(latitudeDouble, result, "Latitude should be the same as the one passed in the constructor.");
    }

    /**
     * Tests the getLongitude method of the Gps class.
     */
    @Test
    void testGetLongitude() {
        // Act
        Longitude result = gps.getLongitude();
        // Assert
        assertEquals(longitudeDouble, result, "Longitude should be the same as the one passed in the constructor.");
    }

    /**
     * Tests the equals method of the Gps class for the same latitude and longitude.
     */
    @Test
    void testEqualsForSameLatitudeAndLongitude() {
        // Arrange
        Gps gpsToCompare = new Gps(latitudeDouble, longitudeDouble);
        // Act
        boolean result = gps.equals(gpsToCompare);
        // Assert
        assertTrue(result, "The result should be true for the same latitude and longitude.");
    }

    /**
     * Tests the equals method of the Gps class for different latitude and longitude.
     */
    @Test
    void testEqualsForSameObject() {
        // Act
        boolean result = gps.equals(gps);
        // Assert
        assertTrue(result, "The result should be true for the same object.");
    }

    /**
     * Tests the equals method of the Gps class for null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Act
        boolean result = gps.equals(null);
        // Assert
        assertFalse(result, "The result should be false for null object.");
    }

    /**
     * Tests the equals method of the Gps class for different class object.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Act
        boolean result = gps.equals(new Object());
        // Assert
        assertFalse(result, "The result should be false for different class object.");
    }

    /**
     * Tests the equals method of the Gps class for different latitude and longitude.
     */
    @Test
    void testEqualsForDifferentGps() {
        // Arrange
        Latitude differentLatitude = mock(Latitude.class);
        Longitude differentLongitude = mock(Longitude.class);
        Gps differentGps = new Gps(differentLatitude, differentLongitude);
        // Act
        boolean result = gps.equals(differentGps);
        // Assert
        assertFalse(result, "The result should be false for different latitude and longitude.");
    }

    /**
     * Tests that the hashCode method returns the same value for two equal objects.
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        Gps gpsToCompare = new Gps(latitudeDouble, longitudeDouble);
        // Act
        int hashCode1 = gps.hashCode();
        int hashCode2 = gpsToCompare.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "The hash code should be the same for two equal objects.");
    }

    /**
     * Tests that the hashCode method returns false for two different objects.
     */
    @Test
    void testHashCodeForDifferentObjects() {
        // Arrange
        Latitude differentLatitude = mock(Latitude.class);
        Longitude differentLongitude = mock(Longitude.class);
        Gps differentGps = new Gps(differentLatitude, differentLongitude);
        // Act
        int hashCode1 = gps.hashCode();
        int hashCode2 = differentGps.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hash code should be different for two different objects.");
    }
}

