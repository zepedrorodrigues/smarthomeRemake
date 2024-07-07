package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the class for ActuatorTypeRepositoryMemImpl.
 */
class ActuatorTypeRepositoryMemImplTest {

    private ActuatorType actuatorType1;
    private ActuatorType actuatorType2;
    private ActuatorTypeRepositoryMemImpl actuatorTypeRepositoryMemImpl;


    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        actuatorType1 = mock(ActuatorType.class);
        when(actuatorType1.getIdentity()).thenReturn(mock(ActuatorTypeName.class));
        actuatorType2 = mock(ActuatorType.class);
        when(actuatorType2.getIdentity()).thenReturn(mock(ActuatorTypeName.class));
        actuatorTypeRepositoryMemImpl = new ActuatorTypeRepositoryMemImpl();
    }


    /**
     * Test that the save method saves an actuator type to an empty repository.
     * The method should return the saved actuator type.
     */
    @Test
    void saveWhenRepositoryIsEmpty() {
        //Act
        ActuatorType result = actuatorTypeRepositoryMemImpl.save(actuatorType1);

        //Assert
        assertEquals(actuatorType1, result,
                "The saved ActuatorType should be the same as the ActuatorType that was " + "saved.");
    }

    /**
     * Test that the save method saves an actuator type to a non-empty repository.
     * The method should return the saved actuator type.
     */
    @Test
    void saveWhenRepositoryIsWithOneElementAlreadyAdded() {
        //Arrange
        actuatorTypeRepositoryMemImpl.save(actuatorType1);
        ActuatorType actuatorType2 = mock(ActuatorType.class);
        when(actuatorType2.getIdentity()).thenReturn(mock(ActuatorTypeName.class), mock(ActuatorTypeName.class));

        //Act
        ActuatorType result = actuatorTypeRepositoryMemImpl.save(actuatorType2);

        //Assert
        assertEquals(actuatorType2, result,
                "The saved ActuatorType should be the same as the ActuatorType that was " + "saved.");
    }

    /**
     * Test that the findAll method returns an empty iterable when the repository is empty.
     */
    @Test
    void findAllWhenRepositoryIsEmpty() {
        //Arrange
        List<ActuatorType> actuatorTypes = new ArrayList<>();

        //Act
        actuatorTypeRepositoryMemImpl.findAll().forEach(actuatorTypes::add);

        //Assert
        boolean result = actuatorTypes.isEmpty();
        assertTrue(result, "The list of ActuatorTypes should be empty when the repository is empty.");
    }

    /**
     * Test that the save method throws an IllegalArgumentException when trying to save a null actuator type.
     */
    @Test
    void TestSaveThrowsIllegalArgumentExceptionWhenActuatorTypeIsNull() {
        //Arrange
        ActuatorType actuatorType = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorTypeRepositoryMemImpl.save(actuatorType), "An " +
                "IllegalArgumentException should be thrown when trying to save a null actuator type.");
    }

    /**
     * Test that the save method throws an IllegalArgumentException when trying to save an actuator type with an
     * existing
     * identity.
     */
    @Test
    void TestSaveThrowsIllegalArgumentExceptionWhenActuatorTypeIdExists() {
        //Arrange
        ActuatorTypeName actuatorTypeName = mock(ActuatorTypeName.class);
        ActuatorType actuatorType = mock(ActuatorType.class);
        when(actuatorType.getIdentity()).thenReturn(actuatorTypeName);
        actuatorTypeRepositoryMemImpl.save(actuatorType);
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorTypeRepositoryMemImpl.save(actuatorType), "An " +
                "IllegalArgumentException should be thrown when trying to save an actuator type with an existing " +
                "identity.");
    }

    /**
     * Test that the findAll method returns all the two actuator types in the repository.
     */
    @Test
    void findAllWhenRepositoryWithTwoElements() {
        //Arrange
        ActuatorTypeName actuatorTypeName1 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType1 = mock(ActuatorType.class);
        when(actuatorType1.getIdentity()).thenReturn(actuatorTypeName1);
        ActuatorTypeName actuatorTypeName2 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType2 = mock(ActuatorType.class);
        when(actuatorType2.getIdentity()).thenReturn(actuatorTypeName2);
        actuatorTypeRepositoryMemImpl.save(actuatorType1);
        actuatorTypeRepositoryMemImpl.save(actuatorType2);
        List<ActuatorType> result = new ArrayList<>();

        //Act
        actuatorTypeRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.contains(actuatorType1) && result.contains(actuatorType2), "The list of ActuatorTypes " +
                "should contain all the ActuatorTypes in the repository.");
    }

    /**
     * Test that the getByIdentity method returns an empty optional when the repository is empty.
     */
    @Test
    void findByIdentityWhenRepositoryIsEmpty() {
        //Arrange
        ActuatorTypeName actuatorTypeName = mock(ActuatorTypeName.class);

        //Act
        Optional<ActuatorType> result = actuatorTypeRepositoryMemImpl.findByIdentity(actuatorTypeName);

        //Assert
        boolean resultIsEmpty = result.isEmpty();
        assertTrue(resultIsEmpty, "The optional should be empty when the repository is empty.");
    }

    /**
     * Test that the getByIdentity method returns an optional containing the actuator type with the given identity.
     */
    @Test
    void findByIdentityWhenRepositoryIsWithTwoElements() {
        //Arrange
        ActuatorTypeName actuatorTypeName1 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType1 = mock(ActuatorType.class);
        when(actuatorType1.getIdentity()).thenReturn(actuatorTypeName1);
        ActuatorTypeName actuatorTypeName2 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType2 = mock(ActuatorType.class);
        when(actuatorType2.getIdentity()).thenReturn(actuatorTypeName2);

        //Act
        actuatorTypeRepositoryMemImpl.save(actuatorType1);
        actuatorTypeRepositoryMemImpl.save(actuatorType2);
        ActuatorType actuatorTypeResult = actuatorTypeRepositoryMemImpl.findByIdentity(actuatorTypeName1).orElseThrow();

        //Assert
        assertEquals(actuatorType1, actuatorTypeResult, "The ActuatorType returned should be the same as the " +
                "ActuatorType with the given identity.");
    }

    /**
     * Test that the getByIdentity method throws an IllegalArgumentException when the identity is null.
     */
    @Test
    void findByIdentityThrowsIllegalArgumentExceptionWhenActuatorTypeNameIdentityIsNull() {
        //Arrange
        ActuatorTypeName actuatorTypeName = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorTypeRepositoryMemImpl.findByIdentity(actuatorTypeName),
                "An " + "IllegalArgumentException should be thrown when the identity is null.");
    }

    /**
     * Test that the containsIdentity method returns false when the repository is empty.
     */
    @Test
    void containsIdentityWhenRepositoryIsEmpty() {
        //Arrange
        ActuatorTypeName actuatorTypeName = mock(ActuatorTypeName.class);

        //Act
        boolean result = actuatorTypeRepositoryMemImpl.containsIdentity(actuatorTypeName);

        //Assert
        assertFalse(result, "The repository should not contain the identity when it is empty.");
    }

    /**
     * Test that the containsIdentity method returns true when the identity is in the repository.
     */
    @Test
    void containsIdentityWhenRepositoryIsWithTwoElementsAndIdentityIsOneOfThem() {
        //Arrange
        ActuatorTypeName actuatorTypeName1 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType1 = mock(ActuatorType.class);
        when(actuatorType1.getIdentity()).thenReturn(actuatorTypeName1);
        ActuatorTypeName actuatorTypeName2 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType2 = mock(ActuatorType.class);
        when(actuatorType2.getIdentity()).thenReturn(actuatorTypeName2);
        actuatorTypeRepositoryMemImpl.save(actuatorType1);
        actuatorTypeRepositoryMemImpl.save(actuatorType2);

        //Act
        boolean result = actuatorTypeRepositoryMemImpl.containsIdentity(actuatorTypeName2);

        //Assert
        assertTrue(result, "The repository should contain the identity when it is in the repository.");
    }

    /**
     * Test that the containsIdentity method returns false when the identity is not in the repository.
     */
    @Test
    void containsIdentityWhenTheIdentityIsNotInRepository() {
        //Arrange
        ActuatorTypeName nonExistingActuatorTypeName = mock(ActuatorTypeName.class);
        ActuatorTypeName actuatorTypeName1 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType1 = mock(ActuatorType.class);
        when(actuatorType1.getIdentity()).thenReturn(actuatorTypeName1);
        actuatorTypeRepositoryMemImpl.save(actuatorType1);

        //Act
        boolean result = actuatorTypeRepositoryMemImpl.containsIdentity(nonExistingActuatorTypeName);

        //Assert
        assertFalse(result, "The repository should not contain the identity when it is not in the repository.");
    }

    /**
     * Test that the containsIdentity method throws an IllegalArgumentException when the identity is null.
     */
    @Test
    void containsIdentityThrowsIllegalArgumentExceptionWhenActuatorTypeNameIsNull() {
        //Arrange
        ActuatorTypeName actuatorTypeName = null;
        //Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> actuatorTypeRepositoryMemImpl.containsIdentity(actuatorTypeName), "An " + "IllegalArgumentException"
                        + " should be thrown when the identity is null.");
    }

    /**
     * This test checks that the list of ActuatorTypeIds is empty when the repository is empty.
     */
    @Test
    void testFindActuatorTypeNamesWhenRepositoryIsEmpty() {
        //Arrange
        List<ActuatorTypeName> result = new ArrayList<>();
        //Act
        actuatorTypeRepositoryMemImpl.findActuatorTypeNames().forEach(result::add);
        //Assert
        assertTrue(result.isEmpty(), "The list of ActuatorTypeIds should be empty when the repository is empty.");
    }

    /**
     * This test checks that the list of ActuatorTypeIds contains all the ActuatorTypeIds in the repository when the
     * repository is not empty.
     */
    @Test
    void testFindActuatorTypeNamesWhenRepositoryIsWithTwoElements() {
        //Arrange
        ActuatorTypeName actuatorTypeName1 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType1 = mock(ActuatorType.class);
        when(actuatorType1.getIdentity()).thenReturn(actuatorTypeName1);
        ActuatorTypeName actuatorTypeName2 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType2 = mock(ActuatorType.class);
        when(actuatorType2.getIdentity()).thenReturn(actuatorTypeName2);
        actuatorTypeRepositoryMemImpl.save(actuatorType1);
        actuatorTypeRepositoryMemImpl.save(actuatorType2);
        List<ActuatorTypeName> result = new ArrayList<>();
        //Act
        actuatorTypeRepositoryMemImpl.findActuatorTypeNames().forEach(result::add);
        //Assert
        assertTrue(result.contains(actuatorTypeName1) && result.contains(actuatorTypeName2), "The list of " +
                "ActuatorTypeIds " + "should contain all the ActuatorTypeIds in the repository.");
    }

    /**
     * Test checks if the list of ActuatorTypeIds contains the saved ActuatorTypeIds when the repository is not empty.
     */
    @Test
    void testGetActuatorIdsToANonEmptyRepository() {
        //Arrange
        ActuatorTypeName actuatorTypeName1 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType1 = mock(ActuatorType.class);
        when(actuatorType1.getIdentity()).thenReturn(actuatorTypeName1);
        ActuatorTypeName actuatorTypeName2 = mock(ActuatorTypeName.class);
        ActuatorType actuatorType2 = mock(ActuatorType.class);
        when(actuatorType2.getIdentity()).thenReturn(actuatorTypeName2);
        actuatorTypeRepositoryMemImpl.save(actuatorType1);
        actuatorTypeRepositoryMemImpl.save(actuatorType2);
        List<ActuatorTypeName> result = new ArrayList<>();
        //Act
        actuatorTypeRepositoryMemImpl.findActuatorTypeNames().forEach(result::add);
        //Assert
        assertTrue(result.contains(actuatorTypeName1) && result.contains(actuatorTypeName2), "The list of " +
                "ActuatorTypeIds " + "should contain all the ActuatorTypeIds in the repository.");
    }
}
