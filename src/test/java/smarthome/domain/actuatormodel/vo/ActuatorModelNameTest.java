package smarthome.domain.actuatormodel.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the class for ActuatorModelName.
 */
class ActuatorModelNameTest {

    /**
     * Test the constructor of the class ActuatorModelName.
     * The constructor should not throw an exception for a valid name.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidActuatorModelName() {
        //Arrange
        String validActuatorName = "actuator name";
        //Act - Assert
        assertDoesNotThrow(() -> new ActuatorModelName(validActuatorName),
                "The constructor should not throw an exception for a valid name.");
    }

    /**
     * Test the constructor of the class ActuatorModelName.
     * The constructor should throw an exception for a null name.
     */
    @Test
    void testConstructorThrowsExceptionForNullActuatorModelName() {
        //Arrange
        String invalidActuatorName = null;
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorModelName(invalidActuatorName),
                "The constructor should throw an exception for a null name.");
    }

    /**
     * Test the constructor of the class ActuatorModelName.
     * The constructor should throw an exception for a blank name.
     */
    @Test
    void testConstructorThrowsExceptionForBlankActuatorModelName() {
        //Arrange
        String invalidActuatorName = " ";
        //Act - Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorModelName(invalidActuatorName),
                "The constructor should throw an exception for a blank name.");
    }

    /**
     * Test the getActuatorModelName method of the class ActuatorModelName.
     * The method should return the correct name value.
     */
    @Test
    void testGetActuatorModelNameReturnsCorrectValue() {
        //Arrange
        String expected = "actuator name";
        ActuatorModelName actuatorModelName = new ActuatorModelName(expected);
        //Act
        String result = actuatorModelName.getActuatorModelName();
        //Assert
        assertEquals(expected, result,
                "The method should return the correct name value.");
    }

    /**
     * Test the equals method of the class ActuatorModelName.
     * The method should return true when comparing the same object.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuator name");

        // Act - Assert
        assertEquals(actuatorModelName, actuatorModelName,
                "The method should return true when comparing the same object.");
    }

    /**
     * Test the equals method of the class ActuatorModelName.
     * The method should return false when comparing with a null object.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuator name");
        ActuatorModelName nullActuatorModelName = null;

        // Act - Assert
        assertNotEquals(actuatorModelName, nullActuatorModelName,
                "The method should return false when comparing with a null object.");
    }

    /**
     * Test the equals method of the class ActuatorModelName.
     * The method should return false when comparing with a different object.
     */
    @Test
    void testEqualsForDifferentObject() {
        // Arrange
        ActuatorModelName actuatorModelName = new ActuatorModelName("actuator name");

        // Act - Assert
        assertNotEquals(actuatorModelName, new Object(),
                "The method should return false when comparing with a different object.");
    }

    /**
     * Test the equals method of the class ActuatorModelName.
     * The method should return true for the same name value.
     */
    @Test
    void testEqualsForSameActuatorModelNameValue() {
        // Arrange
        String actuatorModelName = "actuator name";
        ActuatorModelName actuatorModelName1 = new ActuatorModelName(actuatorModelName);
        ActuatorModelName actuatorModelName2 = new ActuatorModelName(actuatorModelName);

        // Act - Assert
        assertEquals(actuatorModelName1, actuatorModelName2,
                "The method should return true for the same name value.");
    }

    /**
     * Test the equals method of the class ActuatorModelName.
     * The method should return false for different name values.
     */
    @Test
    void testEqualsForDifferentActuatorModelNameValue() {
        // Arrange
        String actuatorModelNameA = "actuator name";
        String actuatorModelNameB = "another actuator name";
        ActuatorModelName actuatorModelName1 = new ActuatorModelName(actuatorModelNameA);
        ActuatorModelName actuatorModelName2 = new ActuatorModelName(actuatorModelNameB);

        // Act - Assert
        assertNotEquals(actuatorModelName1, actuatorModelName2,
                "The method should return false for different name values.");
    }

    /**
     * Test the hashCode method of the class ActuatorModelName.
     * The method should return the same hash code for the same name value.
     */
    @Test
    void testHashCodeForSameActuatorModelNameValue() {
        // Arrange
        String actuatorModelName = "actuator model name";
        ActuatorModelName actuatorModelName1 = new ActuatorModelName(actuatorModelName);
        ActuatorModelName actuatorModelName2 = new ActuatorModelName(actuatorModelName);

        // Act
        int hashCode1 = actuatorModelName1.hashCode();
        int hashCode2 = actuatorModelName2.hashCode();

        // Assert
        assertEquals(hashCode1, hashCode2,
                "The method should return the same hash code for the same name value.");
    }

    /**
     * Test the hashCode method of the class ActuatorModelName.
     * The method should return different hash codes for different name values.
     */
    @Test
    void testHashCodeForDifferentActuatorModelName() {
        // Arrange
        String actuatorModelNameA = "actuator model name";
        String actuatorModelNameB = "another actuator model name";
        ActuatorModelName actuatorModelName1 = new ActuatorModelName(actuatorModelNameA);
        ActuatorModelName actuatorModelName2 = new ActuatorModelName(actuatorModelNameB);

        // Act
        int hashCode1 = actuatorModelName1.hashCode();
        int hashCode2 = actuatorModelName2.hashCode();

        // Assert
        assertNotEquals(hashCode1, hashCode2,
                "The method should return different hash codes for different name values.");
    }
}