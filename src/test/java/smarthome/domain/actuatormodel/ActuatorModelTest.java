package smarthome.domain.actuatormodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for ActuatorModel
 */
class ActuatorModelTest {


    ActuatorModel actuatorModel;
    ActuatorModelName actuatorModelName;
    String validActuatorModelName;
    ActuatorTypeName actuatorTypeName;
    String validActuatorTypeName;


    /**
     * Set up method for the tests
     */
    @BeforeEach
    void setUp() {
        // Arrange
        validActuatorModelName = "TestActuatorModelName";
        validActuatorTypeName = "TestActuatorTypeName";
        actuatorModelName = new ActuatorModelName(validActuatorModelName);
        actuatorTypeName = new ActuatorTypeName(validActuatorTypeName);


        // Act
        actuatorModel = new ActuatorModel(actuatorModelName, actuatorTypeName);
    }

    /**
     * Test the constructor of the class ActuatorModel when a valid actuator model and actuator type name are set
     */
    @Test
    void testConstructorWhenValidActuatorModel() {
        // Assert
        assertNotNull(actuatorModel, "The actuator model should not be null");
    }

    /**
     * Test the constructor of the class ActuatorModel when a null actuator model is set
     */
    @Test
    void testConstructorWhenNullActuatorModelName() {
        // Arrange
        ActuatorModelName nullActuatorModelName = null;
        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorModel(nullActuatorModelName,
                actuatorTypeName), "The actuator model name should not be null");
    }

    /**
     * Test the constructor of the class ActuatorModel when a null actuator type name is set
     */
    @Test
    void testConstructorWhenNullActuatorTypeName() {
        // Arrange + Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new ActuatorModel(actuatorModelName, null),
                "The actuator type name should not be null");
    }

    /**
     * Test the method getActuatorTypeName of the class ActuatorModel
     */
    @Test
    void testGetActuatorTypeName() {
        // Act
        ActuatorTypeName result = actuatorModel.getActuatorTypeName();
        // Assert
        assertEquals(actuatorTypeName, result, "The actuator type name should be the same");
    }

    /**
     * Test the method getIdentity of the class ActuatorModel
     */
    @Test
    void testGetIdentity() {
        // Act
        ActuatorModelName result = actuatorModel.getIdentity();
        // Assert
        assertEquals(validActuatorModelName, result.getActuatorModelName(),
                "The actuator model name should be the " + "same");
    }

    /**
     * Test the method equals of the class ActuatorModel when the same object is compared
     */
    @Test
    void testEqualsWhenSameObject() {
        // Act
        boolean result = actuatorModel.equals(actuatorModel);
        // Assert
        assertTrue(result, "The objects should be the same");
    }

    /**
     * Test the method equals of the class ActuatorModel when a different object with the same type (ActuatorModelName)
     * and name is compared
     */
    @Test
    void testEqualsWhenDifferentObject() {
        // Arrange
        ActuatorModel actuatorModel2 = new ActuatorModel(actuatorModelName, actuatorTypeName);
        // Act
        boolean result = actuatorModel.equals(actuatorModel2);
        // Assert
        assertTrue(result, "The objects should be the same");
    }

    /**
     * Test the method equals of the class ActuatorModel when a different object with the same type
     * (ActuatorModelName) and different name is compared
     */
    @Test
    void testEqualsWhenDifferentName() {
        // Arrange
        ActuatorModelName actuatorModelNameDouble2 = new ActuatorModelName("DifferentName");
        ActuatorModel actuatorModel2 = new ActuatorModel(actuatorModelNameDouble2, actuatorTypeName);
        // Act
        boolean result = actuatorModel.equals(actuatorModel2);
        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Test the method equals of the class ActuatorModel when a different object with the same type
     * (ActuatorModelName), same model name but different actuator type name is compared
     */
    @Test
    void testEqualsWhenDifferentActuatorTypeId() {
        // Arrange
        ActuatorTypeName actuatorTypeNameDouble2 = new ActuatorTypeName("Different");
        ActuatorModel actuatorModel2 = new ActuatorModel(actuatorModelName, actuatorTypeNameDouble2);
        // Act
        boolean result = actuatorModel.equals(actuatorModel2);
        // Assert
        assertTrue(result, "The objects should be equals, only the identity of the actuator model is compared");
    }

    /**
     * Test the method equals of the class ActuatorModel when a different object with a different type is compared
     */
    @Test
    void testEqualsWhenDifferentObjectThatIsNotActuatorModel() {
        // Arrange
        Object otherObject = new Object();
        // Act
        boolean result = actuatorModel.equals(otherObject);
        // Assert
        assertFalse(result, "The objects should be different");
    }


    /**
     * Test the method equals of the class ActuatorModel when a different object with the same type
     * (ActuatorModelName) and null name is compared
     */
    @Test
    void testEqualsWhenNullIsCompared() {
        // Arrange + Act
        boolean result = actuatorModel.equals(null);
        // Assert
        assertFalse(result, "The objects should be different");
    }

    /**
     * Test the method equals of the class ActuatorModel when a null object is compared
     * The method should return false
     */
    @Test
    void testHashCodeWhenSameObjectIsCompared() {
        // Arrange
        ActuatorModel actuatorModel2 = new ActuatorModel(actuatorModelName, actuatorTypeName);
        // Act
        int result = actuatorModel.hashCode();
        int result2 = actuatorModel2.hashCode();
        // Assert
        assertEquals(result, result2, "The hash code should be the same");
    }

    /**
     * Test the method hashCode of the class ActuatorModel when a different object is compared
     * The hash code should be different
     */
    @Test
    void testHashCodeWhenDifferentObjectIsCompared() {
        // Arrange
        ActuatorModelName differentActuatorModelName = new ActuatorModelName("DifferentName");
        ActuatorTypeName differentActuatorTypeName = new ActuatorTypeName("DifferentTypeName");
        ActuatorModel differentActuatorModel = new ActuatorModel(
                differentActuatorModelName, differentActuatorTypeName);
        // Act
        int result = actuatorModel.hashCode();
        int result2 = differentActuatorModel.hashCode();
        // Assert
        assertNotEquals(result, result2, "The hash code should be different");
    }


}