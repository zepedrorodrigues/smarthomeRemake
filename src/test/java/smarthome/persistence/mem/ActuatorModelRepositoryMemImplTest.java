package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
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
 * A test class for the ActuatorModelRepositoryMemImpl implementation.
 * This class contains unit tests for the ActuatorModelRepositoryMemImpl class.
 * These tests verify the behavior of the ActuatorModelRepositoryMemImpl class in various scenarios.
 */
class ActuatorModelRepositoryMemImplTest {

    ActuatorModelRepositoryMemImpl actuatorModelRepositoryMemImpl;
    ActuatorModelName actuatorModelNameMock;
    ActuatorModel actuatorModelMock;
    ActuatorTypeName actuatorTypeNameMock;

    /**
     * Sets up the test environment before each test method.
     * This method initializes the ActuatorModelRepositoryMemImpl instance and mock objects.
     */
    @BeforeEach
    void setUp() {
        actuatorTypeNameMock = mock(ActuatorTypeName.class);
        actuatorModelRepositoryMemImpl = new ActuatorModelRepositoryMemImpl();
        actuatorModelNameMock = mock(ActuatorModelName.class);
        actuatorModelMock = mock(ActuatorModel.class);
        when(actuatorModelMock.getIdentity()).thenReturn(actuatorModelNameMock);
        when(actuatorModelMock.getActuatorTypeName()).thenReturn(actuatorTypeNameMock);

    }

    /**
     * Unit test: Saving a valid ActuatorModel to an empty repository should return the saved ActuatorModel.
     * This test verifies that the save method of the ActuatorModelRepositoryMemImpl returns the same ActuatorModel instance.
     */
    @Test
    void testSaveValidActuatorModelToEmptyRepositoryShouldReturnSavedActuatorModel() {
        // Act
        ActuatorModel result = actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Assert
        assertEquals(actuatorModelMock, result);
    }

    /**
     * Unit test: Saving a valid ActuatorModel to an empty repository should return the saved ActuatorModel's identity.
     * This test verifies that the save method of the ActuatorModelRepositoryMemImpl returns the identity of the saved ActuatorModel.
     */
    @Test
    void testSaveValidActuatorModelToEmptyRepositoryShouldReturnSavedActuatorModelIdentity() {
        // Act
        ActuatorModel result = actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Assert
        assertEquals(actuatorModelNameMock, result.getIdentity());
    }

    /**
     * Unit test for saving a valid actuator model to an empty repository and checking if the repository contains the saved identity.
     * This test verifies that when a valid actuator model is saved to an empty repository, the repository contains the corresponding identity.
     */
    @Test
    void testSaveValidActuatorModelToAnEmptyRepositoryShouldContainIdentity() {
        // Act
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Assert
        assertTrue(actuatorModelRepositoryMemImpl.containsIdentity(actuatorModelNameMock));
    }

    /**
     * Unit test: Saving a valid ActuatorModel to a non-empty repository should return the saved ActuatorModel.
     * This test verifies that the save method of the ActuatorModelRepositoryMemImpl returns the same ActuatorModel instance.
     */
    @Test
    void testSaveValidActuatorModelToNonEmptyRepositoryShouldReturnSavedActuatorModel() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        ActuatorModel anotherActuatorModelMock = mock(ActuatorModel.class);
        ActuatorModelName anotherActuatorModelNameMock = mock(ActuatorModelName.class);
        when(anotherActuatorModelMock.getIdentity()).thenReturn(anotherActuatorModelNameMock);
        // Act
        ActuatorModel result = actuatorModelRepositoryMemImpl.save(anotherActuatorModelMock);
        // Assert
        assertEquals(anotherActuatorModelMock, result);
    }

    /**
     * Unit test: Saving a valid ActuatorModel to a non-empty repository should return the saved ActuatorModel's identity.
     * This test verifies that the save method of the ActuatorModelRepositoryMemImpl returns the identity of the saved ActuatorModel.
     */
    @Test
    void testSaveValidActuatorModelToNonEmptyRepositoryShouldReturnSavedActuatorModelIdentity() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);

        ActuatorModel anotherActuatorModelMock = mock(ActuatorModel.class);
        ActuatorModelName anotherActuatorModelNameMock = mock(ActuatorModelName.class);
        when(anotherActuatorModelMock.getIdentity()).thenReturn(anotherActuatorModelNameMock);
        // Act
        ActuatorModel result = actuatorModelRepositoryMemImpl.save(anotherActuatorModelMock);
        // Assert
        assertEquals(anotherActuatorModelNameMock, result.getIdentity());
    }

    /**
     * Unit test for saving a valid actuator model to a non-empty repository and checking if the repository contains the saved identity.
     * This test verifies that when a valid actuator model is saved to a non-empty repository, the repository contains the corresponding identity.
     */
    @Test
    void testSaveValidActuatorModelToNonEmptyRepositoryShouldContainIdentity() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);

        ActuatorModel anotherActuatorModelMock = mock(ActuatorModel.class);
        ActuatorModelName anotherActuatorModelNameMock = mock(ActuatorModelName.class);
        when(anotherActuatorModelMock.getIdentity()).thenReturn(anotherActuatorModelNameMock);
        // Act
        actuatorModelRepositoryMemImpl.save(anotherActuatorModelMock);
        // Assert
        assertTrue(actuatorModelRepositoryMemImpl.containsIdentity(anotherActuatorModelNameMock));
    }

    /**
     * Unit test: Finding all actuator models on an empty repository should return an empty iterable.
     * This test verifies that the findAll method returns an empty iterable when the repository is empty.
     */
    @Test
    void testFindAllOnEmptyRepositoryShouldReturnAnEmptyIterable() {
        // Act
        Iterable<ActuatorModel> actuatorModels = actuatorModelRepositoryMemImpl.findAll();
        // Assert
        assertFalse(actuatorModels.iterator().hasNext());
    }

    /**
     * Unit test: Finding all actuator models on an empty repository should return an empty list.
     * This test verifies that the findAll method returns an empty list when the repository is empty.
     */
    @Test
    void testFindAllOnEmptyRepositoryShouldReturnAnEmptyList() {
        // Act
        List<ActuatorModel> result = new ArrayList<>();
        actuatorModelRepositoryMemImpl.findAll().forEach(result::add);
        //Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test: Finding all actuator models with entities in the repository should return an iterable with entities.
     * This test verifies that the findAll method returns an iterable that is not empty.
     */
    @Test
    void testFindAllOnNonEmptyRepositoryWithSingleModelShouldReturnNonEmptyIterable() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Act
        Iterable<ActuatorModel> actuatorModels = actuatorModelRepositoryMemImpl.findAll();
        // Assert
        assertTrue(actuatorModels.iterator().hasNext());
    }

    /**
     * Unit test: Finding all actuator models on a non-empty repository should return an iterable with all actuator models.
     * This test verifies that the findAll method returns an iterable containing all actuator models stored in the repository.
     */
    @Test
    void testFindAllOnNonEmptyRepositoryWithMultipleModelsShouldReturnIterableWithAllActuatorModels() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);

        ActuatorModel anotherActuatorModelMock = mock(ActuatorModel.class);
        ActuatorModelName anotherActuatorModelNameMock = mock(ActuatorModelName.class);
        when(anotherActuatorModelMock.getIdentity()).thenReturn(anotherActuatorModelNameMock);
        actuatorModelRepositoryMemImpl.save(anotherActuatorModelMock);

        int size = 2;
        // Act
        Iterable<ActuatorModel> actuatorModels = actuatorModelRepositoryMemImpl.findAll();
        List<ActuatorModel> result = new ArrayList<>();
        actuatorModels.forEach(result::add);
        // Assert
        assertEquals(size, result.size());
        assertTrue(actuatorModels.iterator().hasNext());
    }

    /**
     * Unit test: Finding all actuator models on a non-empty repository should return a list with all actuator models.
     * This test verifies that the findAll method returns a list containing all actuator models stored in the repository.
     */
    @Test
    void testFindAllOnNonEmptyRepositoryShouldReturnAListWithAllSensors() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        ActuatorModel anotherActuatorModelMock = mock(ActuatorModel.class);
        ActuatorModelName anotherActuatorModelNameMock = mock(ActuatorModelName.class);
        when(anotherActuatorModelMock.getIdentity()).thenReturn(anotherActuatorModelNameMock);
        actuatorModelRepositoryMemImpl.save(anotherActuatorModelMock);
        int size = 2;
        // Act
        List<ActuatorModel> result = new ArrayList<>();
        actuatorModelRepositoryMemImpl.findAll().forEach(result::add);
        // Assert
        assertEquals(size, result.size());
        assertTrue(result.contains(actuatorModelMock));
        assertTrue(result.contains(anotherActuatorModelMock));
    }

    /**
     * Unit test: Getting an actuator model by identity on an empty repository should return an empty optional.
     * This test verifies that the getByIdentity method returns an empty optional when the repository is empty.
     */
    @Test
    void testFindByIdentityOnEmptyRepositoryShouldReturnAnEmptyOptional() {
        // Act
        Optional<ActuatorModel> result = actuatorModelRepositoryMemImpl.findByIdentity(actuatorModelNameMock);
        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test: Getting an actuator model by identity on an empty repository should not present the optional.
     * This test verifies that the getByIdentity method does not present the optional when the repository is empty.
     */
    @Test
    void findByIdentityOnEmptyRepositoryNonExistingIdentityShouldNotBePresent() {
        // Act
        Optional<ActuatorModel> retrievedModel = actuatorModelRepositoryMemImpl.findByIdentity(actuatorModelNameMock);
        // Assert
        assertFalse(retrievedModel.isPresent());
    }

    /**
     * Unit test: Getting an actuator model by identity on a non-empty repository without the identity should return an empty optional.
     * This test verifies that the getByIdentity method returns an empty optional when the repository does not contain the requested identity.
     */
    @Test
    void testFindByIdentityOnNonEmptyRepositoryButDoesNotContainThatIdentityShouldReturnAnEmptyOptional() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        ActuatorModelName anotherActuatorModelNameMock = mock(ActuatorModelName.class);
        // Act
        Optional<ActuatorModel> result = actuatorModelRepositoryMemImpl.findByIdentity(anotherActuatorModelNameMock);
        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Unit test: Getting an actuator model by identity on a non-empty repository without the identity should not present the optional.
     * This test verifies that the getByIdentity method does not present the optional when the repository does not contain the requested identity.
     */
    @Test
    void testFindByIdentityOnNonEmptyRepositoryNonExistingIdentityShouldNotBePresent() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        ActuatorModelName anotherActuatorModelNameMock = mock(ActuatorModelName.class);
        // Act
        Optional<ActuatorModel> result = actuatorModelRepositoryMemImpl.findByIdentity(anotherActuatorModelNameMock);
        // Assert
        assertFalse(result.isPresent());
    }

    /**
     * Unit test: Getting an actuator model by identity with an existing identity should return the corresponding actuator model.
     * This test verifies that the getByIdentity method returns the correct actuator model when the repository contains the requested identity.
     */
    @Test
    void testFindByIdentityWithExistingIdentityShouldReturnCorrespondingActuatorModel() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Act
        ActuatorModel result = actuatorModelRepositoryMemImpl.findByIdentity(actuatorModelNameMock).get();
        // Assert
        assertEquals(actuatorModelMock, result);
    }

    /**
     * Unit test: Checking if the repository contains an existing identity should return true.
     * This test verifies that the containsIdentity method returns true when the repository contains the specified identity.
     */
    @Test
    void testContainsIdentityWhenExistingIdentityShouldReturnTrue() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Act
        boolean result = actuatorModelRepositoryMemImpl.containsIdentity(actuatorModelNameMock);
        // Assert
        assertTrue(result);
    }

    /**
     * Unit test: Checking if the repository contains a non-existing identity should return false.
     * This test verifies that the containsIdentity method returns false when the repository does not contain the specified identity.
     */
    @Test
    void testContainsIdentityWhenNonExistingIdentityShouldReturnFalse() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        ActuatorModelName nonExistingActuatorModelNameMock = mock(ActuatorModelName.class);
        when(nonExistingActuatorModelNameMock.getActuatorModelName()).thenReturn("nonExistingActuatorModelName");
        // Act
        boolean result = actuatorModelRepositoryMemImpl.containsIdentity(nonExistingActuatorModelNameMock);
        // Assert
        assertFalse(result);
    }

    /**
     * Unit test: Checking if the repository contains an identity on an empty repository should return false.
     * This test verifies that the containsIdentity method returns false when the repository is empty.
     */
    @Test
    void testContainsIdentityOnEmptyRepositoryShouldReturnFalse() {
        // Act
        boolean result = actuatorModelRepositoryMemImpl.containsIdentity(actuatorModelNameMock);
        // Assert
        assertFalse(result);
    }

    /**
     * Unit test: Saving a null actuator model should throw an IllegalArgumentException.
     * This test verifies that the save method throws an IllegalArgumentException when a null actuator model is
     * passed as an argument.
     */
    @Test
    void testSaveThrowsIllegalArgumentExceptionWhenActuatorModelWithSameIdentityExists() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> actuatorModelRepositoryMemImpl.save(actuatorModelMock));
    }

    /**
     * Unit test: Saving a null actuator model should throw an IllegalArgumentException.
     * This test verifies that the save method throws an IllegalArgumentException when a null actuator model is
     * passed as an argument.
     */
    @Test
    void testFindActuatorModelsByActuatorTypeIdentityReturnsEmptyIterableWhenNoActuatorModelWithGivenActuatorTypeNameExists() {
        // Arrange
        ActuatorTypeName anotherActuatorTypeNameMock = mock(ActuatorTypeName.class);
        // Act
        Iterable<ActuatorModel> result =
                actuatorModelRepositoryMemImpl.findActuatorModelsByActuatorTypeName(anotherActuatorTypeNameMock);
        // Assert
        assertFalse(result.iterator().hasNext());
    }

    /**
     * Unit test: Finding actuator models by actuator type name with existing actuator type name should return an iterable with actuator models.
     * This test verifies that the findActuatorModelsByActuatorTypeName method returns an iterable with actuator models
     * when the repository contains actuator models with the specified actuator type name.
     */
    @Test
    void testFindActuatorModelsByActuatorTypeIdentityReturnsIterableWithActuatorModelsWhenActuatorModelWithGivenActuatorTypeNameExists() {
        // Arrange
        actuatorModelRepositoryMemImpl.save(actuatorModelMock);
        // Act
        Iterable<ActuatorModel> result = actuatorModelRepositoryMemImpl.findActuatorModelsByActuatorTypeName(actuatorTypeNameMock);
        // Assert
        assertTrue(result.iterator().hasNext());
    }
}

