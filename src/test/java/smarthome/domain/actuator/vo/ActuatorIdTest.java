package smarthome.domain.actuator.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for ActuatorId
 */
class ActuatorIdTest {

    /**
     * Tests the constructor of the ActuatorId class does not throw an exception
     * when a valid actuator id is provided.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidActuatorId() {
        //Arrange
        String actuatorId = "123f5";
        //Act + Assert
        assertDoesNotThrow(() -> new ActuatorId(actuatorId), "Should not throw an exception");
    }

    /**
     * Tests the constructor of the class ActuatorId when an empty id is set
     */
    @Test
    void testConstructorThrowsExceptionForInvalidActuatorId() {
        //Arrange
        String blankActuatorId = "  ";
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new ActuatorId(blankActuatorId),
                "Should throw an exception");
    }

    /**
     * Tests the constructor of the class ActuatorId when a null id is set
     */
    @Test
    void testConstructorThrowsExceptionForNullActuatorId() {
        //Arrange
        String nullActuatorId = null;
        //Act + Assert
        assertThrows(IllegalArgumentException.class, ()-> new ActuatorId(nullActuatorId),
                "Should throw an exception");
    }

    /**
     * Tests that the getActuatorId method of the ActuatorId class returns the correct value.
     */
    @Test
    void testGetActuatorIdReturnsCorrectValue() {
        //Arrange
        String expected = "123f5";
        ActuatorId actuatorId = new ActuatorId(expected);
        //Act
        String result = actuatorId.getActuatorId();
        //Assert
        assertEquals(expected, result, "Should return the correct actuator id");
    }

    /**
     * Tests that the equals method of the ActuatorId class returns true when the same object is passed.
     */
    @Test
    void testEqualsForSameObject() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("123f5");
        // Act
        boolean result = actuatorId.equals(actuatorId);
        // Assert
        assertTrue(result, "Should return true when the same object is passed");
    }

    /**
     * Tests that the equals method of the ActuatorId class returns true when the same id is passed.
     */
    @Test
    void testEqualsForSameId() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("123f5");
        ActuatorId actuatorId1 = new ActuatorId("123f5");
        // Act
        boolean result = actuatorId.equals(actuatorId1);
        // Assert
        assertTrue(result, "Should return true when the same id is passed");
    }

    /**
     * Tests that the equals method of the ActuatorId class returns false when null is passed.
     */
    @Test
    void testEqualsForNullObject() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("123f5");
        // Act
        boolean result = actuatorId.equals(null);
        // Assert
        assertFalse(result, "Should return false when null is passed");
    }

    /**
     * Tests that the equals method of the ActuatorId class returns false when a different class object is passed.
     */
    @Test
    void testEqualsForDifferentClassObject() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("123f5");
        // Act
        boolean result = actuatorId.equals(new Object());
        // Assert
        assertFalse(result, "Should return false when a different class object is passed");
    }

    /**
     * This test verifies the hashCode method of the ActuatorId class.
     * It creates two ActuatorId objects with the same id and checks if their hashCodes are equal.
     */
    @Test
    void testHashCodeForSameActuatorId() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("71839");
        ActuatorId actuatorId1 = new ActuatorId("71839");
        // Act
        int hashCode = actuatorId.hashCode();
        int hashCode1 = actuatorId1.hashCode();
        // Assert
        assertEquals(hashCode, hashCode1, "Should return the same hashCode");
    }

    /**
     * This test verifies the hashCode method of the ActuatorId class.
     * It creates two ActuatorId objects with different id and checks if their hashCodes are different.
     */
    @Test
    void testHashCodeForDifferentActuatorId() {
        // Arrange
        ActuatorId actuatorId = new ActuatorId("71839");
        ActuatorId actuatorId1 = new ActuatorId("019375");
        // Act
        int hashCode = actuatorId.hashCode();
        int hashCode1 = actuatorId1.hashCode();
        // Assert
        assertNotEquals(hashCode, hashCode1, "Should return different hashCodes");
    }

}