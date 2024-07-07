package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

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
 * Unit tests for the class for SensorModelRepositoryMemImpl.
 */
class SensorModelRepositoryMemImplTest {

    private SensorModelRepositoryMemImpl sensorModelRepositoryMemImpl;

    private SensorModel sensorModel;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        sensorModel = mock(SensorModel.class);
        when(sensorModel.getIdentity()).thenReturn(mock(SensorModelName.class));

        sensorModelRepositoryMemImpl = new SensorModelRepositoryMemImpl();
    }

    /**
     * Test that the save method saves a SensorModel to an empty repository.
     * The method should return the saved SensorModel.
     */
    @Test
    void testSaveToAnEmptyRepository() {
        //Act
        SensorModel result = sensorModelRepositoryMemImpl.save(sensorModel);

        //Assert
        assertEquals(sensorModel, result, "The saved SensorModel should be returned.");
    }

    /**
     * Test that the save method saves a SensorModel to a non-empty repository.
     * The method should return the saved SensorModel.
     */
    @Test
    void testSaveToANonEmptyRepository() {
        //Arrange
        SensorModel sensorModel1 = mock(SensorModel.class);
        when(sensorModel1.getIdentity()).thenReturn(mock(SensorModelName.class));
        sensorModelRepositoryMemImpl.save(sensorModel1);

        SensorModel sensorModel2 = mock(SensorModel.class);
        when(sensorModel2.getIdentity()).thenReturn(mock(SensorModelName.class));

        //Act
        SensorModel result = sensorModelRepositoryMemImpl.save(sensorModel2);

        //Assert
        assertEquals(sensorModel2, result, "The saved SensorModel should be returned.");
    }

    /**
     * Test that the findAll method returns an empty iterable
     * when the repository is empty.
     */
    @Test
    void testFindAllToAnEmptyRepository() {
        //Act
        List<SensorModel> result = new ArrayList<>();
        sensorModelRepositoryMemImpl.findAll().forEach(result::add);
        //Assert
        assertTrue(result.isEmpty(), "The iterable should be empty.");
    }

    /**
     * Test that the findAll method returns an iterable with all sensorModels in the repository.
     */
    @Test
    void testFindAllToANonEmptyRepository() {
        //Arrange
        SensorModelName sensorModelName = mock(SensorModelName.class);
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);
        //Act
        sensorModelRepositoryMemImpl.save(sensorModel);
        List<SensorModel> result = new ArrayList<>();
        sensorModelRepositoryMemImpl.findAll().forEach(result::add);
        int size = result.size();
        int expectedSize = 1;
        //Assert
        assertEquals(expectedSize, size);
        assertTrue(result.contains(sensorModel), "The iterable should contain the SensorModel.");
    }

    /**
     * Test that the getByIdentity method returns an empty optional
     * when the repository is empty.
     */
    @Test
    void testFindByIdentityToAnEmptyRepository() {
        //Arrange
        SensorModelName sensorModelName = mock(SensorModelName.class);

        //Act
        Optional<SensorModel> result = sensorModelRepositoryMemImpl.findByIdentity(sensorModelName);

        //Assert
        assertTrue(result.isEmpty(), "The optional should be empty.");
    }

    /**
     * Test that the getByIdentity method returns the correct SensorModel
     * when the repository is non-empty.git stat
     */
    @Test
    void testFindByIdentityToANonEmptyRepository() {
        //Arrange
        String validIdentity = "sensorModelName";
        SensorModelName sensorModelName = mock(SensorModelName.class);
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);
        when(sensorModelName.getSensorModelName()).thenReturn(validIdentity);

        //Act
        sensorModelRepositoryMemImpl.save(sensorModel);
        SensorModel result = sensorModelRepositoryMemImpl.findByIdentity(sensorModelName).get();

        //Assert
        assertEquals(sensorModel, result, "The correct SensorModel should be returned.");
    }

    /**
     * Test that the getByIdentity method returns an empty optional
     * when the repository is non-empty but the SensorModel is not found.
     */
    @Test
    void testFindByIdentityToANonEmptyRepositoryButSensorModelNotFound() {
        //Arrange
        SensorModelName sensorModelName = mock(SensorModelName.class);
        SensorModelName sensorModelName1 = mock(SensorModelName.class);
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);

        //Act
        sensorModelRepositoryMemImpl.save(sensorModel);
        Optional<SensorModel> result = sensorModelRepositoryMemImpl.findByIdentity(sensorModelName1);

        //Assert
        assertTrue(result.isEmpty(), "The optional should be empty.");
    }

    /**
     * Test that the getByIdentity method returns the correct SensorModel
     * when the repository is non-empty and there are multiple SensorModels.
     */
    @Test
    void testFindByIdentityToANonEmptyRepositoryWithMultipleSensorModels() {
        //Arrange
        String validIdentity = "sensorModelName";
        SensorModelName sensorModelName = mock(SensorModelName.class);
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);
        when(sensorModelName.getSensorModelName()).thenReturn(validIdentity);
        SensorModelName sensorModelName1 = mock(SensorModelName.class);
        SensorModel sensorModel1 = mock(SensorModel.class);
        when(sensorModel1.getIdentity()).thenReturn(sensorModelName1);
        when(sensorModelName1.getSensorModelName()).thenReturn("sensorModelName1");

        //Act
        sensorModelRepositoryMemImpl.save(sensorModel);
        sensorModelRepositoryMemImpl.save(sensorModel1);
        SensorModel result = sensorModelRepositoryMemImpl.findByIdentity(sensorModelName).get();

        //Assert
        assertEquals(sensorModel, result, "The correct SensorModel should be returned.");
    }

    /**
     * Test that the containsIdentity method in an empty repository returns false.
     */
    @Test
    void testContainsIdentityInAnEmptyRepository() {
        //Arrange
        SensorModelName sensorModelName = mock(SensorModelName.class);

        //Act
        boolean result = sensorModelRepositoryMemImpl.containsIdentity(sensorModelName);

        //Assert
        assertFalse(result, "The method should return false.");
    }

    /**
     * Test that the containsIdentity method in a non-empty repository returns true.
     */
    @Test
    void testContainsIdentityInANonEmptyRepository() {
        //Arrange
        SensorModelName sensorModelName = mock(SensorModelName.class);
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);

        //Act
        sensorModelRepositoryMemImpl.save(sensorModel);
        boolean result = sensorModelRepositoryMemImpl.containsIdentity(sensorModelName);

        //Assert
        assertTrue(result, "The method should return true.");
    }

    /**
     * Test that the containsIdentity method in a non-empty repository returns false
     */
    @Test
    void testContainsIdentityInANonEmptyRepositoryButSensorModelNotFound() {
        //Arrange
        SensorModelName sensorModelName = mock(SensorModelName.class);
        SensorModelName sensorModelName1 = mock(SensorModelName.class);
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);

        //Act
        sensorModelRepositoryMemImpl.save(sensorModel);
        boolean result = sensorModelRepositoryMemImpl.containsIdentity(sensorModelName1);

        //Assert
        assertFalse(result, "The method should return false.");
    }

    /**
     * Test that the containsIdentity method in a non-empty repository returns true
     * when there are multiple SensorModels.
     */
    @Test
    void testSaveWithNullEntity() {
        // Arrange
        SensorModel sensorModel = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorModelRepositoryMemImpl.save(sensorModel), "The method " +
                "should throw an IllegalArgumentException.");
    }

    /**
     * Test that the save method throws an IllegalArgumentException when trying to save a SensorModel
     * with an identity that already exists in the repository.
     * The test first saves a SensorModel with a certain identity to the repository.
     * Then it tries to save another SensorModel with the same identity.
     * The save method is expected to throw an IllegalArgumentException in this case.
     */
    @Test
    void testSaveDuplicateSensorModel() {
        // Arrange
        SensorModel sensorModel1 = mock(SensorModel.class);
        SensorModel sensorModel2 = mock(SensorModel.class);
        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModel1.getIdentity()).thenReturn(sensorModelName);
        when(sensorModel2.getIdentity()).thenReturn(sensorModelName);

        // Act
        sensorModelRepositoryMemImpl.save(sensorModel1);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> sensorModelRepositoryMemImpl.save(sensorModel2), "The method " +
                "should throw an IllegalArgumentException.");
    }

    /**
     * Test that the getByIdentity method throws an IllegalArgumentException when the id is null.
     * The test calls the getByIdentity method with null as the argument and expects an IllegalArgumentException.
     */
    @Test
    void testFindByIdentityWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorModelRepositoryMemImpl.findByIdentity(null), "The method " +
                "should throw an IllegalArgumentException.");
    }

    /**
     * Test that the containsIdentity method throws an IllegalArgumentException when the id is null.
     * The test calls the containsIdentity method with null as the argument and expects an IllegalArgumentException.
     */
    @Test
    void testContainsIdentityWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorModelRepositoryMemImpl.containsIdentity(null), "The " +
                "method should throw an IllegalArgumentException.");
    }

    /**
     * Test that the findBySensorTypeIdentity method throws an IllegalArgumentException when the id is null.
     * The test calls the findBySensorTypeIdentity method with null as the argument and expects an
     * IllegalArgumentException.
     */
    @Test
    void testFindSensorModelsBySensorTypeIdWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorModelRepositoryMemImpl.findSensorModelsBySensorTypeId(null),
                "The method should throw an IllegalArgumentException.");
    }

    /**
     * Test that the findBySensorTypeIdentity method returns an empty iterable when there are no SensorModels with
     * the provided sensorTypeId.
     * The test first creates a mock SensorTypeId, then calls the findBySensorTypeIdentity method with this id.
     * The method is expected to return an empty iterable.
     */
    @Test
    void testFindSensorModelBySensorTypeIdentityWithNoMatchingSensorModels() {
        // Arrange
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);

        // Act
        Iterable<SensorModel> result = sensorModelRepositoryMemImpl.findSensorModelsBySensorTypeId(sensorTypeId);

        // Assert
        assertFalse(result.iterator().hasNext(), "The iterable should be empty.");
    }

    /**
     * Test that the findBySensorTypeIdentity method returns an iterable containing the correct SensorModels when
     * there are SensorModels with the provided sensorTypeId.
     * The test first creates two SensorModels with the same sensorTypeId and different identities, and saves them to
     * the repository.
     * Then it calls the findBySensorTypeIdentity method with this sensorTypeId.
     * The method is expected to return an iterable containing the two SensorModels.
     */
    @Test
    void testFindSensorModelBySensorTypeIdentityWithMatchingSensorModels() {
        // Arrange
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);
        SensorModel sensorModel1 = mock(SensorModel.class);
        when(sensorModel1.getSensorTypeId()).thenReturn(sensorTypeId);
        when(sensorModel1.getIdentity()).thenReturn(mock(SensorModelName.class)); // Add this line

        SensorModel sensorModel2 = mock(SensorModel.class);
        when(sensorModel2.getSensorTypeId()).thenReturn(sensorTypeId);
        when(sensorModel2.getIdentity()).thenReturn(mock(SensorModelName.class)); // Add this line

        sensorModelRepositoryMemImpl.save(sensorModel1);
        sensorModelRepositoryMemImpl.save(sensorModel2);

        // Act
        Iterable<SensorModel> result = sensorModelRepositoryMemImpl.findSensorModelsBySensorTypeId(sensorTypeId);

        // Assert
        List<SensorModel> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.contains(sensorModel1), "The iterable should contain sensorModel1.");
        assertTrue(resultList.contains(sensorModel2), "The iterable should contain sensorModel2.");
    }

    /**
     * Test the getSensorModelBySensorTypeId method with a null ID.
     * The method is expected to throw an IllegalArgumentException.
     */
    @Test
    void testFindSensorModelNamesBySensorTypeIdWithNullId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorModelRepositoryMemImpl.findSensorModelNamesBySensorTypeId(null), "The method should throw an IllegalArgumentException.");
    }

    /**
     * Test the getSensorModelBySensorTypeId method with a sensor type ID that does not match any SensorModels.
     * The method is expected to return an empty iterable.
     */
    @Test
    void testFindSensorModelBySensorTypeIdWithNoMatchingSensorModelsNames() {
        // Arrange
        SensorTypeId sensorTypeId = new SensorTypeId("sensorTypeId");

        // Act
        Iterable<SensorModelName> result = sensorModelRepositoryMemImpl.findSensorModelNamesBySensorTypeId(sensorTypeId);

        // Assert
        assertFalse(result.iterator().hasNext(), "The iterable should be empty.");
    }

    /**
     * Test the getSensorModelBySensorTypeId method with a sensor type ID that does not match the SensorTypeId of any SensorModels.
     * The method is expected to return an empty iterable.
     */
    @Test
    void testFindSensorModelBySensorTypeIdWithNonMatchingSensorModelsNames() {
        // Arrange
        SensorTypeId validSensorTypeId = new SensorTypeId("sensorTypeId");
        String nonMatchingSensorTypeId = "nonMatchingSensorTypeId";
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);
        when(sensorTypeId.getSensorTypeId()).thenReturn(nonMatchingSensorTypeId);

        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getSensorTypeId()).thenReturn(sensorTypeId);
        SensorModelName sensorModelName = mock(SensorModelName.class);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);

        sensorModelRepositoryMemImpl.save(sensorModel);

        // Act
        Iterable<SensorModelName> result = sensorModelRepositoryMemImpl.findSensorModelNamesBySensorTypeId(validSensorTypeId);

        // Assert
        assertFalse(result.iterator().hasNext(), "The iterable should be empty.");
    }

    /**
     * Test the getSensorModelBySensorTypeId method with a sensor type ID that matches a SensorModel.
     * The method is expected to return an iterable containing the SensorModelName of the matching SensorModel.
     * The test first creates a SensorModel with a specific SensorTypeId and SensorModelName, and saves it to the repository.
     * Then it calls the getSensorModelBySensorTypeId method with the same SensorTypeId.
     * The method is expected to return an iterable containing the SensorModelName of the saved SensorModel.
     */
    @Test
    void testFindSensorModelBySensorTypeIdWithMatchingSensorModelsNames() {
        // Arrange
        SensorTypeId validSensorTypeId = new SensorTypeId("sensorTypeId");

        SensorModelName sensorModelName = new SensorModelName("name");
        SensorModel sensorModel = mock(SensorModel.class);
        when(sensorModel.getSensorTypeId()).thenReturn(validSensorTypeId);
        when(sensorModel.getIdentity()).thenReturn(sensorModelName);

        sensorModelRepositoryMemImpl.save(sensorModel);

        // Act
        Iterable<SensorModelName> result = sensorModelRepositoryMemImpl.findSensorModelNamesBySensorTypeId(validSensorTypeId);

        // Assert
        List<SensorModelName> resultList = new ArrayList<>();
        result.forEach(resultList::add);
        assertTrue(resultList.contains(sensorModelName), "The iterable should contain the SensorModelName.");
    }

}
