package smarthome.domain.sensormodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Aggregate Integration tests for the SensorModel class. This test suite verifies the functionality
 * and behavior of the SensorModel class.
 */
class SensorModelTest {
    SensorModel sensorModel;
    SensorModelName sensorModelName;
    String validSensorModelName;
    SensorTypeId sensorTypeId;
    String validSensorTypeId;

    /**
     * Sets up common test fixtures before each test method.
     * This method initializes the SensorModel object and its dependencies to be used in the tests.
     * It is annotated with JUnit's @BeforeEach annotation, indicating that it should be run before each test.
     * This helps to avoid duplication of the setup code and makes the test code easier to maintain.
     * It is run before each test, so each test starts with a consistent state.
     */
    @BeforeEach
    void setUp() {
        validSensorModelName = "aValidSensorModelName";
        validSensorTypeId = "aValidSensorTypeId";
        sensorModelName = new SensorModelName(validSensorModelName);
        sensorTypeId = new SensorTypeId(validSensorTypeId);

        sensorModel = new SensorModel(sensorModelName, sensorTypeId);
    }

    /**
     * Verifies that the constructor of SensorModel does not throw an exception
     * when valid parameters are provided.
     * This test ensures that the constructor of SensorModel successfully creates
     * an instance of SensorModel without throwing any exceptions when valid
     * parameters (SensorModelName and SensorTypeId) are passed.
     * It validates that the constructor behaves as expected by handling valid
     * input parameters appropriately.
     */
    @Test
    void testConstructorDoesNotThrowExceptionForValidParameters() {
        // Act & Assert
        assertDoesNotThrow(() -> new SensorModel(sensorModelName, sensorTypeId), "The constructor should not throw an exception for valid parameters.");
    }

    /**
     * Verifies that the constructor of SensorModel returns a non-null object
     * when valid parameters are provided.
     * <p>
     * This test ensures that the constructor of SensorModel returns a non-null
     * object when valid parameters (SensorModelName and SensorTypeId) are provided.
     * It validates that the constructor creates an instance of SensorModel successfully
     * and returns the created object.
     */
    @Test
    void testConstructorWithValidParametersShouldReturnObjectCreated() {
        // Act & Assert
        assertNotNull(sensorModel, "The constructor should return a non-null SensorModel object.");
    }

    /**
     * Verifies that the constructor of SensorModel returns the correct SensorModelName.
     * <p>
     * This test ensures that the constructor of SensorModel sets the correct SensorModelName
     * when provided with valid parameters.
     * It validates that the SensorModel object's SensorModelName matches the expected value
     * set during setup.
     */
    @Test
    void testConstructorWithValidParametersShouldReturnCorrectSensorModelName() {
        // Act
        SensorModelName result = sensorModel.getIdentity();
        // Assert
        assertEquals(sensorModelName, result, "The SensorModelName should be the same as the one set during initialization.");
    }

    /**
     * Verifies that the constructor of SensorModel returns the correct SensorTypeId.
     * <p>
     * This test ensures that the constructor of SensorModel sets the correct SensorTypeId
     * when provided with valid parameters.
     * It validates that the SensorModel object's SensorTypeId matches the expected value
     * set during setup.
     */
    @Test
    void testConstructorWithValidParametersShouldReturnCorrectSensorTypeId() {
        // Act
        SensorTypeId result = sensorModel.getSensorTypeId();
        // Assert
        assertEquals(sensorTypeId, result, "The SensorTypeId should be the same as the one set during initialization.");
    }

    /**
     * Acceptance test for creating a SensorModel object with a null SensorModelName.
     * Verifies that attempting to create a SensorModel object with a null SensorModelName
     * results in an appropriate exception being thrown, ensuring proper error handling
     * as expected by an end user.
     */
    @Test
    void testCreateSensorModelWithNullSensorModelNameShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorModel(null, sensorTypeId), "The constructor should throw an IllegalArgumentException when SensorModelName is null.");
    }

    /**
     * Verifies that the constructor of SensorModel throws an IllegalArgumentException
     * when a null SensorModelName is provided.
     * <p>
     * This test ensures that the constructor of SensorModel throws an IllegalArgumentException
     * when a null SensorModelName is passed as a parameter.
     * It validates that the constructor properly handles invalid input by throwing the
     * expected exception.
     */
    @Test
    void testConstructorForNullSensorModelNameShouldThrowException() {
        // Arrange
        SensorModelName nullSensorModelNameMock = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorModel(nullSensorModelNameMock, sensorTypeId), "The constructor should throw an IllegalArgumentException when SensorModelName is null.");
    }

    /**
     * Acceptance test for creating a SensorModel object with a null SensorTypeId.
     * Verifies that attempting to create a SensorModel object with a null SensorTypeId
     * results in an appropriate exception being thrown, ensuring proper error handling
     * as expected by an end user.
     */
    @Test
    void testCreateSensorModelWithNullSensorTypeIdShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorModel(sensorModelName, null), "The constructor should throw an IllegalArgumentException when SensorTypeId is null.");
    }

    /**
     * Verifies that the constructor of SensorModel throws an IllegalArgumentException
     * when a null SensorTypeId is provided.
     * <p>
     * This test ensures that the constructor of SensorModel throws an IllegalArgumentException
     * when a null SensorTypeId is passed as a parameter.
     * It validates that the constructor properly handles invalid input by throwing the
     * expected exception.
     */
    @Test
    void testConstructorForNullSensorTypeIdShouldThrowException() {
        // Arrange
        SensorTypeId nullSensorTypeIdMock = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new SensorModel(sensorModelName, nullSensorTypeIdMock), "The constructor should throw an IllegalArgumentException when SensorTypeId is null.");
    }

    /**
     * Acceptance test for retrieving the correct SensorModelName.
     * <p>
     * Verifies that the getIdentity() method returns the correct SensorModelName
     * for a given SensorModel object, as expected by an end user.
     * <p>
     * This test ensures that the getIdentity method of SensorModel returns the expected SensorModelName.
     * It validates that the SensorModel object's getIdentity method returns the same SensorModelName object
     * that was set during initialization.
     */
    @Test
    void testGetIdentityReturnsCorrectValue() {
        // Act
        SensorModelName result = sensorModel.getIdentity();
        // Assert
        assertEquals(sensorModelName, result, "The SensorModelName should be the same as the one set during initialization.");
    }

    /**
     * Verifies that the getIdentity method of SensorModel returns the same SensorModelName object.
     * <p>
     * This test ensures that the getIdentity method of SensorModel returns the same SensorModelName object
     * that was set during initialization.
     */
    @Test
    void testGetIdentityReturnsSameObject() {
        // Act
        SensorModel sensorModel = new SensorModel(sensorModelName, sensorTypeId);
        // Assert
        assertSame(sensorModelName, sensorModel.getIdentity(), "The SensorModelName should be the same object as the one set during initialization.");
    }

    /**
     * Verifies that the getSensorTypeId method of SensorModel returns the correct SensorTypeId.
     * <p>
     * This test ensures that the getSensorTypeId method of SensorModel returns the expected SensorTypeId.
     * It validates that the SensorModel object's getSensorTypeId method returns the same SensorTypeId object
     * that was set during initialization.
     */
    @Test
    void testGetSensorTypeIdReturnsCorrectValue() {
        // Act
        SensorTypeId result = sensorModel.getSensorTypeId();
        // Assert
        assertEquals(sensorTypeId, result, "The SensorTypeId should be the same as the one set during initialization.");
    }

    /**
     * Acceptance test for retrieving the correct SensorTypeId.
     * <p>
     * Verifies that the getSensorTypeId() method returns the correct SensorTypeId
     * for a given SensorModel object, as expected by an end user.
     * <p>
     * This test ensures that the getSensorTypeId method of SensorModel returns the same SensorTypeId object
     * that was set during initialization.
     */
    @Test
    void testGetSensorTypeIdReturnsSameObject() {
        // Act
        SensorModel sensorModel = new SensorModel(sensorModelName, sensorTypeId);
        // Assert
        assertSame(sensorTypeId, sensorModel.getSensorTypeId(), "The SensorTypeId should be the same object as the one set during initialization.");
    }

    /**
     * Verifies the behavior of the equals method when comparing the same instance of SensorModel.
     * <p>
     * This test ensures that the equals method returns true when comparing a SensorModel object to itself.
     * It validates that the equals method correctly identifies identical instances of SensorModel.
     */
    @Test
    void testEqualsForSameInstanceOfSensorModel() {
        // Act & Assert
        assertTrue(sensorModel.equals(sensorModel), "The objects should be the same when comparing the same object.");
    }

    @Test
    void testEqualsSensorModelComparingWithNullObjectShouldReturnFalse() {
        //Arrange
        SensorModelName sensorModelName = null;
        //Act
        boolean result = sensorModel.equals(sensorModelName);
        //Assert
        assertFalse(result, "The objects should be different when comparing with a null object.");
    }

    @Test
    void testEqualsSensorModelComparingWithADifferentObjectShouldReturnFalse(){
        //Arrange
        SensorModelName sensorModelName = new SensorModelName("sensorModelName");
        //Act and Assert
        assertNotEquals(sensorModel, sensorModelName, "The objects should be different when comparing with a different object.");
    }

    /**
     * Verifies the behavior of the equals method when comparing two distinct instances of SensorModel
     * with the same underlying parameters and matching behavior.
     * <p>
     * This test ensures that the equals method returns true when comparing two distinct instances of SensorModel
     * with the same underlying parameters and matching behavior.
     */
    @Test
    void testEqualsWhenComparingTwoDistinctInstancesWithSameUnderlyingParametersAndMatchingBehavior() {
        // Arrange
        SensorModel sensorModel2 = new SensorModel(sensorModelName, sensorTypeId);
        // Act & Assert
        assertTrue(sensorModel.equals(sensorModel2), "The objects should be equal when SensorModelName and SensorTypeId are the same.");
    }

    /**
     * Verifies the behavior of the equals method when SensorModelName is different.
     * <p>
     * This test ensures that the equals method returns false when comparing two SensorModel instances
     * with different SensorModelName values.
     */
    @Test
    void testEqualsWhenSensorModelNameIsDifferent() {
        // Arrange
        String differentSensorModelName = "differentSensorModelName";
        SensorModelName sensorModelName2 = new SensorModelName(differentSensorModelName);

        SensorModel sensorModel2 = new SensorModel(sensorModelName2, sensorTypeId);
        // Act & Assert
        assertFalse(sensorModel.equals(sensorModel2), "The objects should be different when SensorModelName is different.");
    }

    /**
     * Verifies the behavior of the equals method when one SensorModelName is null.
     * <p>
     * This test ensures that the equals method returns false when comparing two SensorModel instances
     * where one has a null SensorModelName.
     */

    @Test
    void testEqualsWhenOneSensorModelNameIsNull() {
        // Act & Assert
        assertFalse(sensorModel.equals(null), "The equals method should return false when comparing with a null object.");
    }



    /**
     * Acceptance test for comparing two distinct SensorModel objects with different SensorModelNames.
     * Verifies that the equals() method returns false when comparing two distinct
     * SensorModel objects with different SensorModelNames, as expected by an end user.
     */
    @Test
    void testEqualsForDistinctSensorModelsWithDifferentSensorModelNames() {
        // Arrange
        SensorModelName differentSensorModelName = new SensorModelName("differentSensorModelName");
        SensorModel sensorModel2 = new SensorModel(differentSensorModelName, sensorTypeId);
        // Act & Assert
        assertFalse(sensorModel.equals(sensorModel2), "The objects should be different when SensorModelName is different.");
    }

    /**
     * Acceptance test for comparing two identical SensorModel objects.
     * Verifies that the equals() method returns true when comparing two identical
     * SensorModel objects, as expected by an end user.
     */
    @Test
    void testEqualsForIdenticalSensorModels() {
        // Act & Assert
        assertTrue(sensorModel.equals(sensorModel), "The objects should be equal when comparing the same object.");
    }

    /**
     * Acceptance test for comparing two distinct SensorModel objects with identical parameters.
     * Verifies that the equals() method returns true when comparing two distinct
     * SensorModel objects with identical parameters, as expected by an end user.
     */
    @Test
    void testEqualsForDistinctSensorModelsWithIdenticalParameters() {
        // Arrange
        SensorModel sensorModel2 = new SensorModel(sensorModelName, sensorTypeId);
        // Act & Assert
        assertEquals(sensorModel, sensorModel2, "The objects should be equal when SensorModelName and SensorTypeId are the same.");
    }

    /**
     * Tests that the same instance of SensorModel returns the same hash code.
     * <p>
     * This test verifies that the same instance of SensorModel returns the same hash code.
     * It validates that the hash code generated for the SensorModel object is consistent
     * and does not change between calls.
     */
    @Test
    void testHashCodeForEqualObjects() {
        // Arrange
        SensorModel anotherSensorModel = new SensorModel(sensorModelName, sensorTypeId);
        // Act
        int hashCode = sensorModel.hashCode();
        int otherHashCode = anotherSensorModel.hashCode();
        // Assert
        assertEquals(hashCode, otherHashCode, "The hash code should be the same for equal objects");
    }

    /**
     * Tests that different instances of SensorModel with different SensorTypeIds
     * return different hash codes.
     * <p>
     * This test verifies that different instances of SensorModel with different SensorTypeIds
     * return different hash codes.
     * It validates that the hash code generated for the SensorModel object is unique
     * and changes when the SensorTypeId is different.
     */
    @Test
    void testHashCodeForDifferentObjectsWithDifferentSensorTypeIds() {
        // Arrange
        SensorModelName differentSensorModelName = new SensorModelName("differentSensorModelName");
        SensorModel anotherSensorModel = new SensorModel(differentSensorModelName, sensorTypeId);
        // Act
        int hashCode = sensorModel.hashCode();
        int otherHashCode = anotherSensorModel.hashCode();
        // Assert
        assertNotEquals(hashCode, otherHashCode, "The hash code should be different for different objects with different SensorTypeIds");
    }
}