package smarthome.domain.house.vo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for HouseName
 */
class HouseNameTest {


    HouseName houseName;
    String name;

    /**
     * Set up method for the tests
     * Instantiates a HouseName object and sets a valid name
     */
    @BeforeEach
    void setUp() {
        name = "House Name";
        houseName = new HouseName(name);
    }

    /**
     * Test the constructor of the class HouseName when a valid name is set
     */
    @Test
    void testConstructorWhenValidHouseName() {
        // Assert
        assertNotNull(houseName, "The house name should not be null");
    }

    /**
     * Test the constructor of the class HouseName when a null name is set
     */
    @Test
    void testConstructorWhenNullHouseNameAndThrowsException() {
        // Arrange
        String name = null;
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new HouseName(name),
                "The constructor should throw an IllegalArgumentException when the house name is null");
    }

    /**
     * Test the constructor of the class HouseName when an empty name is set
     */
    @Test
    void testConstructorWhenEmptyHouseNameAndThrowsException() {
        // Arrange
        String name = "";
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new HouseName(name),
                "The constructor should throw an IllegalArgumentException when the house name is empty");
    }

    /**
     * Test the constructor of the class HouseName when a blank name is set
     */
    @Test
    void testConstructorWhenBlankHouseNameAndThrowsException() {
        // Arrange
        String name = " ";
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new HouseName(name),
                "The constructor should throw an IllegalArgumentException when the house name is blank");
    }

    /**
     * Test the getHouseName method of the class HouseName when a valid name is set
     */
    @Test
    void testGetHouseNameWhenValidHouseName() {
        // Act
        String result = houseName.getName();
        // Assert
        assertEquals(name, result, "The house name should be the same");
    }

    /**
     * Test the equals method of the class HouseName when an equal object is compared
     */
    @Test
    void testEqualsWhenEqualObjectHouseNameCompared() {
        // Arrange
        HouseName houseName2 = new HouseName(name);
        // Act
        boolean result = houseName.equals(houseName2);
        // Assert
        assertTrue(result, "The house names are the same, so the objects should be equal");
    }

    /**
     * Test the equals method of the class HouseName when the same object is compared
     */
    @Test
    void testEqualsWhenSameObjectHouseNameCompared() {
        // Act
        boolean result = houseName.equals(houseName);
        // Assert
        assertTrue(result, "The objects should be the same");
    }

    /**
     * Test the equals method of the class HouseName when a different object is compared
     */
    @Test
    void testEqualsWhenDifferentObjectHouseNameCompared() {
        // Arrange
        Object differentObject = new Object();
        // Act
        boolean result = houseName.equals(differentObject);
        // Assert
        assertFalse(result, "The objects are different, so they should not be equal");
    }

    /**
     * Test the equals method of the class HouseName when a null object is compared
     */
    @Test
    void testEqualsWhenNullObjectIsCompared() {
        // Act
        boolean result = houseName.equals(null);
        // Assert
        assertFalse(result, "The object is null, so they should not be equal");
    }

    /**
     * Test the equals method of the class HouseName when the same object with different values is compared
     */
    @Test
    void testEqualsWhenSameObjectWithDifferentValuesIsCompared() {
        // Arrange
        String differentHouseName = "Different Name";
        HouseName houseName2 = new HouseName(differentHouseName);
        // Act
        boolean result = houseName.equals(houseName2);
        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Test the method hashCode of the class HouseName when
     * the object is an instance of HouseName and has the same name
     * the hashcode should be the same
     */
    @Test
    void testHashCodeWhenSameObjectWithSameName() {
        // Arrange
        HouseName houseName2 = new HouseName(name);
        // Act
        int result = houseName.hashCode();
        int result2 = houseName2.hashCode();
        // Assert
        assertEquals(result, result2, "The hash code should be the same");
    }

    /**
     * Test the method hashCode of the class HouseName when
     * the object is an instance of HouseName and has a different name
     * the hashcode should be different
     */
    @Test
    void testHashCodeWhenSameObjectWithDifferentName() {
        // Arrange
        String differentHouseName = "Different Name";
        HouseName houseName2 = new HouseName(differentHouseName);
        // Act
        int result = houseName.hashCode();
        int result2 = houseName2.hashCode();
        // Assert
        assertNotEquals(result, result2, "The hash code should be different");
    }


}