package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class contains unit tests for the SensorModelServiceImpl class.
 * It uses Mockito to create mock objects for the SensorModelServiceImpl and its dependencies.
 * Each test method in this class corresponds to a method in the SensorModelServiceImpl class.
 */
class SensorModelServiceImplTest {

    private ISensorModelRepository mockSensorModelRepository;

    private SensorModelServiceImpl mockSensorModelService;

    private SensorModel mockSensorModel;

    private SensorTypeId mockSensorTypeId;

    private SensorModelName mockSensorModelName;

    /**
     * Sets up the mocks before each test.
     */
    @BeforeEach
    void setUp() {
        mockSensorModelRepository = mock(ISensorModelRepository.class);
        mockSensorModelService = new SensorModelServiceImpl(mockSensorModelRepository);

        mockSensorTypeId = mock(SensorTypeId.class);

        mockSensorModelName = mock(SensorModelName.class);

        // Initialize mockSensorModel before using it
        mockSensorModel = mock(SensorModel.class);

        when(mockSensorModelRepository.findSensorModelNamesBySensorTypeId(mockSensorTypeId))
                .thenReturn(Arrays.asList(mockSensorModelName));
        when(mockSensorModelRepository.findByIdentity(mockSensorModelName)).thenReturn(Optional.of(mockSensorModel));
    }

    /**
     * Tests that the getSensorModelsBySensorTypeIdentity method returns the correct names.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityReturnsCorrectNames() {
        // Arrange
        SensorModelName sensorModelName1 = new SensorModelName("name1");
        SensorModelName sensorModelName2 = new SensorModelName("name2");
        when(mockSensorModelRepository.findSensorModelNamesBySensorTypeId(mockSensorTypeId))
                .thenReturn(Arrays.asList(sensorModelName1, sensorModelName2));

        // Act
        List<SensorModelName> result = mockSensorModelService.getSensorModelsBySensorTypeIdentity(mockSensorTypeId);

        // Assert
        assertEquals(2, result.size(), "The size of the returned list should be 2.");
    }

    /**
     * Tests that the getSensorModelsBySensorTypeIdentity method returns the correct name values.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityReturnsCorrectNameValues() {
        // Arrange
        SensorModelName sensorModelName1 = new SensorModelName("name1");
        SensorModelName sensorModelName2 = new SensorModelName("name2");
        when(mockSensorModelRepository.findSensorModelNamesBySensorTypeId(mockSensorTypeId))
                .thenReturn(Arrays.asList(sensorModelName1, sensorModelName2));

        // Act
        List<SensorModelName> result = mockSensorModelService.getSensorModelsBySensorTypeIdentity(mockSensorTypeId);

        // Assert
        assertTrue(result.contains(sensorModelName1), "The returned list should contain the first sensor model name.");
    }

    /**
     * Tests that the getSensorModelsBySensorTypeIdentity method returns the correct name values.
     */
    @Test
    void testGetSensorModelsBySensorTypeIdentityReturnsCorrectNameValues2() {
        // Arrange
        SensorModelName sensorModelName1 = new SensorModelName("name1");
        SensorModelName sensorModelName2 = new SensorModelName("name2");
        when(mockSensorModelRepository.findSensorModelNamesBySensorTypeId(mockSensorTypeId))
                .thenReturn(Arrays.asList(sensorModelName1, sensorModelName2));

        // Act
        List<SensorModelName> result = mockSensorModelService.getSensorModelsBySensorTypeIdentity(mockSensorTypeId);

        // Assert
        assertTrue(result.contains(sensorModelName2), "The returned list should contain the second sensor model name.");
    }

    /**
     * Tests that the getSensorModelByName method returns the correct SensorModel.
     */
    @Test
    void testGetSensorModelByNameReturnsCorrectSensorModel() {
        // Arrange
        when(mockSensorModelRepository.findByIdentity(mockSensorModelName)).thenReturn(Optional.of(mockSensorModel));

        // Act
        Optional<SensorModel> result = mockSensorModelService.getSensorModelByName(mockSensorModelName);

        // Assert
        assertTrue(result.isPresent(), "The returned Optional should be non-empty.");
    }

    /**
     * Tests that the getSensorModelByName method returns the correct SensorModel value.
     */
    @Test
    void testGetSensorModelByNameReturnsCorrectSensorModelValue() {
        // Arrange
        when(mockSensorModelRepository.findByIdentity(mockSensorModelName)).thenReturn(Optional.of(mockSensorModel));

        // Act
        Optional<SensorModel> result = mockSensorModelService.getSensorModelByName(mockSensorModelName);

        // Assert
        assertEquals(mockSensorModel, result.get(), "The returned sensor model should be the same as the mock sensor model.");
    }

}