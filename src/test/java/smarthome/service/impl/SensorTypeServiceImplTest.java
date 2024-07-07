package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains tests for the SensorTypeServiceImpl class.
 */
class SensorTypeServiceImplTest {

    private ISensorTypeRepository mockSensorTypeRepository;

    private SensorTypeServiceImpl mockSensorTypeService;

    private SensorTypeId mockSensorTypeId;

    private SensorType mockSensorType;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mockSensorTypeRepository = mock(ISensorTypeRepository.class);
        mockSensorTypeService = new SensorTypeServiceImpl(mockSensorTypeRepository);

        mockSensorTypeId = mock(SensorTypeId.class);

        mockSensorType = mock(SensorType.class);

        when(mockSensorTypeRepository.findByIdentity(mockSensorTypeId)).thenReturn(Optional.of(mockSensorType));
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(Arrays.asList(mockSensorTypeId));
    }

    /**
     * Tests that the getSensorTypesIds method returns the correct IDs.
     */
    @Test
    void testGetSensorTypesIdsReturnsCorrectIds() {
        // Arrange
        SensorTypeId sensorTypeId1 = new SensorTypeId("id1");
        SensorTypeId sensorTypeId2 = new SensorTypeId("id2");
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(Arrays.asList(sensorTypeId1, sensorTypeId2));

        // Act
        List<SensorTypeId> result = mockSensorTypeService.getSensorTypesIds();

        // Assert
        assertEquals(2, result.size(),
                "The size of the returned list should be 2.");
    }

    /**
     * Tests that the getSensorTypesIds method returns the correct ID values.
     */
    @Test
    void testGetSensorTypesIdsReturnsCorrectIdValues() {
        // Arrange
        SensorTypeId sensorTypeId1 = new SensorTypeId("id1");
        SensorTypeId sensorTypeId2 = new SensorTypeId("id2");
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(Arrays.asList(sensorTypeId1, sensorTypeId2));

        // Act
        List<SensorTypeId> result = mockSensorTypeService.getSensorTypesIds();

        // Assert
        assertTrue(result.contains(sensorTypeId1),
                "The returned list should contain the first sensor type id.");
    }

    /**
     * Tests that the getSensorTypesIds method returns the correct ID values.
     */
    @Test
    void testGetSensorTypesIdsReturnsCorrectIdValues2() {
        // Arrange
        SensorTypeId sensorTypeId1 = new SensorTypeId("id1");
        SensorTypeId sensorTypeId2 = new SensorTypeId("id2");
        when(mockSensorTypeRepository.findSensorTypeIds()).thenReturn(Arrays.asList(sensorTypeId1, sensorTypeId2));

        // Act
        List<SensorTypeId> result = mockSensorTypeService.getSensorTypesIds();

        // Assert
        assertTrue(result.contains(sensorTypeId2),
                "The returned list should contain the second sensor type id.");
    }

    /**
     * Tests that the getByIdentity method returns the correct sensor type.
     */
    @Test
    void testGetByIdentityReturnsCorrectSensorType() {
        // Arrange
        when(mockSensorTypeRepository.findByIdentity(mockSensorTypeId)).thenReturn(Optional.of(mockSensorType));

        // Act
        Optional<SensorType> result = mockSensorTypeService.getByIdentity(mockSensorTypeId);

        // Assert
        assertTrue(result.isPresent(),
                "The returned Optional should be non-empty.");
    }

    /**
     * Tests that the getByIdentity method returns the correct sensor type value.
     */
    @Test
    void testGetByIdentityReturnsCorrectSensorTypeValue() {
        // Arrange
        when(mockSensorTypeRepository.findByIdentity(mockSensorTypeId)).thenReturn(Optional.of(mockSensorType));

        // Act
        Optional<SensorType> result = mockSensorTypeService.getByIdentity(mockSensorTypeId);

        // Assert
        assertEquals(mockSensorType, result.get(),
                "The returned sensor type should be the same as the mock sensor type.");
    }



}