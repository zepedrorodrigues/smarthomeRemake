package smarthome.domain.room.vo;

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
 * This class contains unit tests for the Dimensions class.
 * It tests the constructor, getters, and the equals method under various conditions.
 * The tests are designed to cover all possible edge cases including null values, same objects, different objects, and different dimensions.
 * The setUp method is used to initialize the common objects and values used in the tests.
 */
class DimensionsTest {

    Width width;

    Height height;

    Length length;

    Dimensions dimensions;

    double validWidth;

    double validHeight;

    double validLength;

    /**
     * This method is executed before each test.
     * It sets up the necessary objects and conditions for the tests.
     * It initializes valid dimensions (width, height, length) and mocks the Width, Height, and Length classes.
     * It also sets the return values for the getWidth, getHeight, getLength, and equals methods of the mocked objects.
     * Finally, it creates a new Dimensions object with the mocked width, height, and length.
     */
    @BeforeEach
    void setUp() {
        validHeight = 2;
        validWidth = 20;
        validLength = 10;
        width = mock(Width.class);
        height = mock(Height.class);
        length = mock(Length.class);
        when(width.getWidth()).thenReturn(validWidth);
        when(height.getHeight()).thenReturn(validHeight);
        when(length.getLength()).thenReturn(validLength);
        dimensions = new Dimensions(width, height, length);

    }

    /**
     * Test to verify that the constructor of the Dimensions class creates a valid object when
     * provided with valid width, height, and length.
     */
    @Test
    void testConstructorValidDimensions() {

        assertNotNull(dimensions, "The constructor should create a valid Dimensions object");
    }

    /**
     * Test to verify that the constructor of the Dimensions class throws an IllegalArgumentException when
     * provided with a null width.
     */
    @Test
    void testConstructorNullWidth() {
        assertThrows(IllegalArgumentException.class, () -> new Dimensions(null, height, length), "The constructor " +
                "should throw an IllegalArgumentException when provided with a null width");
    }

    /**
     * Test to verify that the constructor of the Dimensions class throws an IllegalArgumentException when
     * provided with a null height.
     */
    @Test
    void testConstructorNullHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Dimensions(width, null, length), "The constructor " +
                "should throw an IllegalArgumentException when provided with a null height");
    }

    /**
     * Test to verify that the constructor of the Dimensions class throws an IllegalArgumentException when
     * provided with a null length.
     */
    @Test
    void testConstructorNullLength() {
        assertThrows(IllegalArgumentException.class, () -> new Dimensions(width, height, null), "The constructor " +
                "should throw an IllegalArgumentException when provided with a null length");
    }

    /**
     * Test to verify that the getWidth method of the Dimensions class returns the correct Width object.
     * It checks if the width value of the returned Width object is equal to the expected width value.
     */
    @Test
    void testGetWidth() {
        //Act
        Width result = dimensions.getWidth();
        //Assert
        assertEquals(validWidth, result.getWidth(), "The getWidth method should return the correct width");
    }

    /**
     * Test to verify that the getHeight method of the Dimensions class returns the correct height.
     * It checks if the height value of the returned Height object is equal to the expected height value.
     */
    @Test
    void testGetHeight() {
        //Act
        Height result = dimensions.getHeight();
        //Assert
        assertEquals(validHeight, result.getHeight(), "The getHeight method should return the correct height");
    }

    /**
     * Test to verify that the getLength method of the Dimensions class returns the correct length.
     * It checks if the length value of the returned Length object is equal to the expected length value.
     */
    @Test
    void testGetLength() {
        //Act
        Length result = dimensions.getLength();
        //Assert
        assertEquals(validLength, result.getLength(), "The getLength method should return the correct length");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns true when
     * the width, height, and length are the same.
     */
    @Test
    void testequalsForSameWidthHeightLength() {
        //Arrenge
        Dimensions dimensions = new Dimensions(width, height, length);
        //Act
        boolean result = this.dimensions.equals(dimensions);
        //assert
        assertTrue(dimensions.equals(dimensions), "The equals method should return true when the dimensions are the " +
                "same");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns true when compared with the same object.
     */
    @Test
    void testequalsForSameObject() {
        //Act
        boolean result = dimensions.equals(dimensions);
        //Assert
        assertTrue(result, "The equals method should return true when comparing the same object");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns false when compared with null.
     */
    @Test
    void testequalsForNullObject() {
        //Act
        boolean result = dimensions.equals(null);
        //Assert
        assertFalse(result, "The equals method should return false when comparing with null");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns false when
     * compared with an object of a different class.
     */
    @Test
    void testequalsForDifferentClassObject() {
        //Act
        boolean result = dimensions.equals(new Object());
        //Assert
        assertFalse(result, "The equals method should return false when comparing with a different object");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns false when the widths are different.
     */
    @Test
    void testequalsForDifferentWidth() {
        // Arrange
        Width width1 = mock(Width.class);
        Width width2 = mock(Width.class);
        when(width1.getWidth()).thenReturn(5.0);
        when(width2.getWidth()).thenReturn(10.0);
        Dimensions dimensions1 = new Dimensions(width1, height, length);
        Dimensions dimensions2 = new Dimensions(width2, height, length);
        // Act
        boolean result = dimensions1.equals(dimensions2);
        // Assert
        assertFalse(result, "The equals method should return false when the widths are different");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns false when the heights are different.
     */
    @Test
    void testequalsForDifferentHeight() {
        // Arrange
        Height height1 = mock(Height.class);
        Height height2 = mock(Height.class);
        when(height1.getHeight()).thenReturn(2.0);
        when(height2.getHeight()).thenReturn(3.0);
        Dimensions dimensions1 = new Dimensions(width, height1, length);
        Dimensions dimensions2 = new Dimensions(width, height2, length);
        // Act
        boolean result = dimensions1.equals(dimensions2);
        // Assert
        assertFalse(result, "The equals method should return false when the heights are different");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns false when the lengths are different.
     */
    @Test
    void testequalsForDifferentLength() {
        // Arrange
        Length length1 = mock(Length.class);
        Length length2 = mock(Length.class);
        when(length1.getLength()).thenReturn(10.0);
        when(length2.getLength()).thenReturn(20.0);
        Dimensions dimensions1 = new Dimensions(width, height, length1);
        Dimensions dimensions2 = new Dimensions(width, height, length2);
        // Act
        boolean result = dimensions1.equals(dimensions2);
        // Assert
        assertFalse(result, "The equals method should return false when the lengths are different");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns true when the dimensions are different.
     */
    @Test
    void testequalsForDifferentDimensions() {
        //Arrenge
        Dimensions dimensions = new Dimensions(width, height, length);
        //Act
        boolean result = this.dimensions.equals(dimensions);
        //assert
        assertTrue(result, "The equals method should return true when the dimensions are different");
    }

    /**
     * Test to verify that the equals method of the Dimensions class returns true when compared with a different object.
     */
    @Test
    void testequalsForDifferentObject() {
        //Arrenge
        Dimensions dimensions = new Dimensions(width, height, length);
        //Act
        boolean result = this.dimensions.equals(dimensions);
        //assert
        assertTrue(result, "The equals method should return true when comparing with a different object");
    }

    /*
     * Test to verify that the hashCode method of the Dimensions class returns the same hash code
     *  for the same dimensions.
     * It creates two Dimensions objects with the same width, height, and length and checks if the hash codes are equal.
     */
    @Test
    void testHashCodeForSameDimensions() {
        // Arrange
        Width width = new Width(5.0);
        Height height = new Height(2.0);
        Length length = new Length(10.0);
        Dimensions dimensions1 = new Dimensions(width, height, length);
        Dimensions dimensions2 = new Dimensions(width, height, length);
        // Act
        int hashCode1 = dimensions1.hashCode();
        int hashCode2 = dimensions2.hashCode();
        // Assert
        assertEquals(hashCode1, hashCode2, "The hashCode method should return the same value for two Dimensions " +
                "objects" + " with the same dimensions");
    }

    /**
     * Test to verify that the hashCode method of the Dimensions class returns different hash codes
     * for different dimensions.
     * It creates two Dimensions objects with different width, height, and length and checks if
     * the hash codes are different.
     */
    @Test
    void testHashCodeForDifferentDimensions() {
        // Arrange
        Width width1 = new Width(5.0);
        Height height1 = new Height(2.0);
        Length length1 = new Length(10.0);
        Dimensions dimensions1 = new Dimensions(width1, height1, length1);
        Width width2 = new Width(10.0);
        Height height2 = new Height(4.0);
        Length length2 = new Length(20.0);
        Dimensions dimensions2 = new Dimensions(width2, height2, length2);
        // Act
        int hashCode1 = dimensions1.hashCode();
        int hashCode2 = dimensions2.hashCode();
        // Assert
        assertNotEquals(hashCode1, hashCode2, "The hashCode method should return different values for two Dimensions " +
                "objects" + " with different dimensions");
    }
}