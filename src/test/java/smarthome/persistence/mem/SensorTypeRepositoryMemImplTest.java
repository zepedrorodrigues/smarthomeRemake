package smarthome.persistence.mem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains tests for the SensorTypeRepositoryMemImpl class.
 */
class SensorTypeRepositoryMemImplTest {

    private SensorType sensorType;
    private SensorTypeRepositoryMemImpl sensorTypeRepositoryMemImpl;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        sensorType = mock(SensorType.class);
        when(sensorType.getIdentity()).thenReturn(mock(SensorTypeId.class));
        sensorTypeRepositoryMemImpl = new SensorTypeRepositoryMemImpl();
    }


    /**
     * Tests that a SensorType can be saved to an empty repository.
     */
    @Test
    void testSaveToAnEmptyRepository() {
        //Act
        SensorType result = sensorTypeRepositoryMemImpl.save(sensorType);

        //Assert
        assertEquals(sensorType, result,
                "The saved SensorType should be the same as the SensorType that was saved.");
    }

    /**
     * Tests that a SensorType can be saved to a non-empty repository.
     */
    @Test
    void testSaveToANonEmptyRepository() {
        //Arrange
        SensorType sensorType2 = mock(SensorType.class);
        when(sensorType2.getIdentity()).thenReturn(mock(SensorTypeId.class));
        sensorTypeRepositoryMemImpl.save(sensorType);

        //Act
        SensorType result = sensorTypeRepositoryMemImpl.save(sensorType2);

        //Assert
        assertEquals(sensorType2, result,
                "The saved SensorType should be the same as the SensorType that was saved.");
    }

    /**
     * Tests that saving a null SensorType throws an IllegalArgumentException.
     */
    @Test
    void testSaveNullSensorType() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorTypeRepositoryMemImpl.save(null),
                "Saving a null SensorType should throw an IllegalArgumentException.");
    }

    /**
     * Tests that saving a SensorType with the same identity as a SensorType already in the repository
     * throws an IllegalArgumentException.
     */
    @Test
    void testSaveDuplicateSensorType() {
        //Arrange
        SensorType sensorType2 = mock(SensorType.class);
        when(sensorType2.getIdentity()).thenReturn(mock(SensorTypeId.class));
        sensorTypeRepositoryMemImpl.save(sensorType);

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorTypeRepositoryMemImpl.save(sensorType),
                "Saving a SensorType with the same identity as a SensorType already in the" +
                        "repository should throw an IllegalArgumentException.");
    }

    /**
     * Tests that findAll returns an empty list when the repository is empty.
     */
    @Test
    void testFindAllEmptyRepository() {
        //Act
        List<SensorType> result = new ArrayList<>();
        sensorTypeRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertTrue(result.isEmpty(),
                "The list of SensorTypes should be empty when the repository is empty.");
    }

    /**
     * Tests that findAll returns all SensorTypes when the repository is non-empty.
     */
    @Test
    void testFindAllNonEmptyRepository() {
        //Arrange
        SensorTypeId sensorTypeId1 = mock(SensorTypeId.class);
        SensorType sensorType1 = mock(SensorType.class);
        when(sensorType1.getIdentity()).thenReturn(sensorTypeId1);
        SensorTypeId sensorTypeId2 = mock(SensorTypeId.class);
        SensorType sensorType2 = mock(SensorType.class);
        when(sensorType2.getIdentity()).thenReturn(sensorTypeId2);
        sensorTypeRepositoryMemImpl.save(sensorType1);
        sensorTypeRepositoryMemImpl.save(sensorType2);
        int expectedSize = 2;


        //Act
        List<SensorType> result = new ArrayList<>();
        sensorTypeRepositoryMemImpl.findAll().forEach(result::add);

        //Assert
        assertEquals(expectedSize, result.size(),
                "The size of the SensorType list should match the expected number.");
        assertTrue(result.contains(sensorType1),
                "The list should contain the first saved SensorType.");
        assertTrue(result.contains(sensorType2),
                "The list should contain the second saved SensorType.");
    }

    /**
     * Tests that getByIdentity returns an empty Optional when the repository is empty.
     */
    @Test
    void testFindByIdentityEmptyRepository() {
        //Arrange
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);

        //Act
        var result = sensorTypeRepositoryMemImpl.findByIdentity(sensorTypeId);

        //Assert
        assertTrue(result.isEmpty(),
                "The result should be an empty Optional when the repository is empty.");
    }

    /**
     * Tests that getByIdentity returns the correct SensorType when the repository is non-empty.
     */
    @Test
    void testFindByIdentityNonEmptyRepository() {
        //Arrange
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);
        SensorType sensorType = mock(SensorType.class);
        when(sensorType.getIdentity()).thenReturn(sensorTypeId);
        sensorTypeRepositoryMemImpl.save(sensorType);
        //Act
        var result = sensorTypeRepositoryMemImpl.findByIdentity(sensorTypeId);

        //Assert
        assertTrue(result.isPresent(),
                "The result should be a present Optional when the repository is not empty.");
        assertEquals(sensorType, result.get(),
                "The retrieved SensorType should be the same as the one saved.");
    }

    /**
     * Tests that getByIdentity throws an IllegalArgumentException when the identity is null.
     */
    @Test
    void testFindByIdentityNullIdentity() {
        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorTypeRepositoryMemImpl.findByIdentity(null), "An " +
                "IllegalArgumentException should be thrown when attempting to get a SensorType" + "with a null " +
                "identity.");
    }

    /**
     * Tests that getByIdentity returns an empty Optional when the repository does not contain the SensorType.
     */
    @Test
    void testContainsIdentityEmptyRepository() {
        // Arrange
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);

        // Act
        boolean result = sensorTypeRepositoryMemImpl.containsIdentity(sensorTypeId);

        // Assert
        assertFalse(result,
                "Should return false when the repository does not contain the SensorType identity.");
    }

    /**
     * Tests that containsIdentity returns true when the repository contains the SensorType.
     */
    @Test
    void testContainsIdentityNonEmptyRepository() {
        // Arrange
        SensorTypeId sensorTypeId = mock(SensorTypeId.class);
        SensorType sensorType = mock(SensorType.class);
        when(sensorType.getIdentity()).thenReturn(sensorTypeId);
        sensorTypeRepositoryMemImpl.save(sensorType);

        // Act
        boolean result = sensorTypeRepositoryMemImpl.containsIdentity(sensorTypeId);

        // Assert
        assertTrue(result,
                "Should return true when the repository contains the SensorType identity.");
    }

    /**
     * Tests that containsIdentity throws an IllegalArgumentException when the identity is null.
     */
    @Test
    void testContainsIdentityNullIdentity() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sensorTypeRepositoryMemImpl.containsIdentity(null),
                "An IllegalArgumentException should be thrown when attempting to check for a null identity"
                        + "in the repository.");
    }

    /**
     * This test checks the behavior of the getSensorTypesIds method when the repository is empty.
     * The expected behavior is that the method should return an empty set.
     * The test first calls the getSensorTypesIds method and stores the result.
     * It then asserts that the result does not have a next element, indicating that the set is empty.
     */
    @Test
    void testFindSensorTypeIdsEmptyRepository() {
        // Act
        Iterable<SensorTypeId> result = sensorTypeRepositoryMemImpl.findSensorTypeIds();

        // Assert
        assertTrue(!result.iterator().hasNext(),
                "The set of SensorTypeIds should be empty when the repository is empty.");
    }

    /**
     * Tests that getSensorTypesIds returns the correct number of SensorTypeIds when the repository is non-empty.
     */
    @Test
    void testFindSensorTypeIdsNonEmptyRepositorySize() {
        // Arrange
        SensorTypeId sensorTypeId1 = mock(SensorTypeId.class);
        SensorType sensorType1 = mock(SensorType.class);
        when(sensorType1.getIdentity()).thenReturn(sensorTypeId1);
        SensorTypeId sensorTypeId2 = mock(SensorTypeId.class);
        SensorType sensorType2 = mock(SensorType.class);
        when(sensorType2.getIdentity()).thenReturn(sensorTypeId2);
        sensorTypeRepositoryMemImpl.save(sensorType1);
        sensorTypeRepositoryMemImpl.save(sensorType2);

        // Act
        Iterable<SensorTypeId> result = sensorTypeRepositoryMemImpl.findSensorTypeIds();

        // Assert
        List<SensorTypeId> sensorTypeIds = new ArrayList<>();
        result.forEach(sensorTypeIds::add);
        assertEquals(2, sensorTypeIds.size(),
                "The size of the SensorTypeId set should match the expected number.");
    }

    /**
     * Tests that getSensorTypesIds returns the first SensorTypeId when the repository is non-empty.
     */
    @Test
    void testFindSensorTypeIdsNonEmptyRepositoryFirstContent() {
        // Arrange
        SensorTypeId sensorTypeId1 = mock(SensorTypeId.class);
        SensorType sensorType1 = mock(SensorType.class);
        when(sensorType1.getIdentity()).thenReturn(sensorTypeId1);
        SensorTypeId sensorTypeId2 = mock(SensorTypeId.class);
        SensorType sensorType2 = mock(SensorType.class);
        when(sensorType2.getIdentity()).thenReturn(sensorTypeId2);
        sensorTypeRepositoryMemImpl.save(sensorType1);
        sensorTypeRepositoryMemImpl.save(sensorType2);

        // Act
        Iterable<SensorTypeId> result = sensorTypeRepositoryMemImpl.findSensorTypeIds();

        // Assert
        List<SensorTypeId> sensorTypeIds = new ArrayList<>();
        result.forEach(sensorTypeIds::add);
        assertTrue(sensorTypeIds.contains(sensorTypeId1),
                "The set should contain the first saved SensorTypeId.");
    }

    /**
     * Tests that getSensorTypesIds returns the second SensorTypeId when the repository is non-empty.
     */
    @Test
    void testFindSensorTypeIdsNonEmptyRepositorySecondContent() {
        // Arrange
        SensorTypeId sensorTypeId1 = mock(SensorTypeId.class);
        SensorType sensorType1 = mock(SensorType.class);
        when(sensorType1.getIdentity()).thenReturn(sensorTypeId1);
        SensorTypeId sensorTypeId2 = mock(SensorTypeId.class);
        SensorType sensorType2 = mock(SensorType.class);
        when(sensorType2.getIdentity()).thenReturn(sensorTypeId2);
        sensorTypeRepositoryMemImpl.save(sensorType1);
        sensorTypeRepositoryMemImpl.save(sensorType2);

        // Act
        Iterable<SensorTypeId> result = sensorTypeRepositoryMemImpl.findSensorTypeIds();

        // Assert
        List<SensorTypeId> sensorTypeIds = new ArrayList<>();
        result.forEach(sensorTypeIds::add);
        assertTrue(sensorTypeIds.contains(sensorTypeId2),
                "The set should contain the second saved SensorTypeId.");
    }
}