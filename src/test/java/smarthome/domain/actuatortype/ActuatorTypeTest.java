package smarthome.domain.actuatortype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains unit tests for the ActuatorType class.
 */
class ActuatorTypeTest {

    ActuatorType actuatorType;
    ActuatorTypeName actuatorTypeName;
    String validActuatorTypeName;

    /**
     * This method sets up the common test data and state for each test case.
     * Set mock object for ActuatorTypeName and create an ActuatorType object.
     */
    @BeforeEach
    void setUp() {
        validActuatorTypeName = "actuatorTypeName";
        actuatorTypeName = new ActuatorTypeName(validActuatorTypeName);

        actuatorType = new ActuatorType(actuatorTypeName);
    }


    /**
     * This test case checks if the ActuatorType constructor creates a non-null object.
     * The constructor should create an ActuatorType object.
     */
    @Test
    void testConstructorWhenValidActuatorTypeNameInserted() {
        // Assert
        assertNotNull(actuatorType, "The actuator type should not be null");
    }

    /**
     * This test case checks if the ActuatorType constructor does throw an exception when a null actuator type name
     * is passed.
     * The constructor should throw an IllegalArgumentException.
     */
    @Test
    void testConstructorShouldThrowExceptionWhenActuatorTypeNameIsNull() {
        // Arrange
        ActuatorTypeName nullActuatorTypeName = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorType(nullActuatorTypeName),
                "The constructor " + "should throw an IllegalArgumentException when the actuator type name is null");
    }


    /**
     * This test case checks if the getIdentity method returns the correct actuatorTypeName.
     * The method should return the actuatorTypeName that was set in the constructor.
     */
    @Test
    void testGetIdentityShouldReturnActuatorTypeId() {
        // Act
        ActuatorTypeName result = actuatorType.getIdentity();
        // Assert
        assertNotNull(result, "The actuator type name should not be null");
    }

    /**
     * This test case checks if the equals method returns false when an ActuatorType object with different
     * id(ActuatorTypeName) is compared.
     * The method should return false.
     */
    @Test
    void testEqualsWhenEqualObjectButDifferentTypeNameCompared() {
        // Arrange
        ActuatorTypeName differentActuatorTypeName = new ActuatorTypeName("differentActuatorTypeName");
        ActuatorType differentActuatorType = new ActuatorType(differentActuatorTypeName);
        // Act
        boolean result = actuatorType.equals(differentActuatorType);
        // Assert
        assertFalse(result, "The actuator type should not be equal");
    }

    @Test
    void testEqualsWhenEqualObjectHasSameTypeNameCompared() {
        // Arrange;
        ActuatorType differentActuatorType = new ActuatorType(actuatorTypeName);
        // Act
        boolean result = actuatorType.equals(differentActuatorType);
        // Assert
        assertTrue(result, "The actuator type should be equal");
    }

    /**
     * This test case checks if the equals method when the same ActuatorType object is compared with itself.
     * The method should return true.
     */
    @Test
    void testEqualsWhenSameObjectIsCompared() {
        // Act
        boolean result = actuatorType.equals(actuatorType);
        // Assert
        assertTrue(result, "The actuator type should be equal");
    }

    /**
     * This test case checks if the equals method returns false when an object of a different class is compared.
     * The method should return false.
     */
    @Test
    void testEqualsWhenDifferentObjectIsCompared() {
        // Arrange
        Object differentObject = new Object();
        // Act
        boolean result = actuatorType.equals(differentObject);
        // Assert
        assertFalse(result, "The objects are different, so they should not be equal");
    }

    /**
     * This test case checks if the equals method returns false when null object is compared.
     * The method should return false.
     */
    @Test
    void testEqualsWhenNullObjectIsCompared() {
        // Arrange
        ActuatorType nullActuatorType = null;
        // Act
        boolean result = actuatorType.equals(nullActuatorType);
        // Assert
        assertFalse(result, "The object is null, so they should not be equal");
    }

    /**
     * Test the method hashCode of the class ActuatorType when
     * the object is an instance of ActuatorType and has the same actuator type name
     * should return the same hash code.
     */
    @Test
    void testHashCodeWhenSameObjectIsCompared() {
        // Arrange
        ActuatorType actuatorType2 = new ActuatorType(actuatorTypeName);
        // Act
        int result = actuatorType.hashCode();
        int result2 = actuatorType2.hashCode();
        // Assert
        assertEquals(result, result2, "The hash code should be the same");

    }

    /**
     * Test the method hashCode of the class ActuatorType when
     * the object is an instance of ActuatorType and has the same actuator type name
     * should return the same hash code.
     */
    @Test
    void testHashCodeWhenDifferentObjectIsCompared() {
        // Arrange
        ActuatorTypeName differentActuatorTypeName = new ActuatorTypeName("differentActuatorTypeName");
        ActuatorType differentActuatorType = new ActuatorType(differentActuatorTypeName);
        // Act
        int result = actuatorType.hashCode();
        int result2 = differentActuatorType.hashCode();
        // Assert
        assertNotEquals(result, result2, "The hash code should be different");
    }
}