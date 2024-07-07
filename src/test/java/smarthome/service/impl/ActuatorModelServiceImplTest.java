package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.service.IActuatorModelService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorModelServiceImplTest {

    IActuatorModelRepository mockIActuatorModelRepository;
    ActuatorModel mockActuatorModel;
    ActuatorModelName mockActuatorModelName;
    ActuatorTypeName mockActuatorTypeName;
    IActuatorModelService actuatorModelService;


    @BeforeEach
    void setUp() {
        mockIActuatorModelRepository = mock(IActuatorModelRepository.class);
        mockActuatorModel = mock(ActuatorModel.class);
        mockActuatorModelName = mock(ActuatorModelName.class);
        mockActuatorTypeName = mock(ActuatorTypeName.class);
        actuatorModelService = new ActuatorModelServiceImpl(mockIActuatorModelRepository);
        when(mockIActuatorModelRepository.findByIdentity(mockActuatorModelName)).thenReturn(Optional.of(mockActuatorModel));
    }

    @Test
    void testActuatorModelServiceImplConstructor() {
        IActuatorModelService actuatorModelService = new ActuatorModelServiceImpl(mockIActuatorModelRepository);
        assertNotNull(actuatorModelService, "The constructor of the ActuatorModelServiceImpl class should work " +
                "correctly.");
    }

    /**
     * This test checks that the getActuatorModelByName method returns a non-null Optional object.
     */
    @Test
    void testGetActuatorModelByName() {
        // Arrange
        when(mockIActuatorModelRepository.findByIdentity(mockActuatorModelName)).thenReturn(Optional.of(mockActuatorModel));
        // Act
        Optional<ActuatorModel> result = actuatorModelService.getActuatorModelByName(mockActuatorModelName);
        // Assert
        assertNotNull(result, "The getActuatorModelByName method should return a non-null Optional object.");
    }

    @Test
    void testGetActuatorModelByNameReturnsNotNullWhenRepositoryIsEmpty() {
        // Arrange
        when(mockIActuatorModelRepository.findByIdentity(mockActuatorModelName)).thenReturn(Optional.empty());
        // Act
        Optional<ActuatorModel> result = actuatorModelService.getActuatorModelByName(mockActuatorModelName);
        // Assert
        assertNotNull(result, "The getActuatorModelByName method should return a non-null Optional object.");
    }

    /**
     *
     */
    @Test
    void testGetActuatorModelsByActuatorTypeIdentity() {
        // Arrange
        List<ActuatorModelName> expected = Arrays.asList(new ActuatorModelName("TestModel1"), new ActuatorModelName(
                "TestModel2"));
        when(mockIActuatorModelRepository.findActuatorModelNamesByActuatorTypeName(mockActuatorTypeName)).thenReturn(expected);
        // Act
        Iterable<ActuatorModelName> result =
                actuatorModelService.getActuatorModelsByActuatorTypeIdentity(mockActuatorTypeName);
        // Assert
        assertEquals(expected, result, "The getActuatorModelsByActuatorTypeIdentity method should return a non-null " +
                "Iterable object.");
    }


    /**
     * This test checks that the getActuatorModelsByActuatorTypeIdentity method returns a non-null Iterable object
     * when the repository is empty.
     */
    @Test
    void testGetActuatorModelsByActuatorTypeIdentityEmpty() {
        // Arrange
        Iterable<ActuatorModelName> expected = emptyList();
        when(mockIActuatorModelRepository.findActuatorModelNamesByActuatorTypeName(mockActuatorTypeName)).thenReturn(emptyList());
        // Act
        Iterable<ActuatorModelName> result =
                actuatorModelService.getActuatorModelsByActuatorTypeIdentity(mockActuatorTypeName);
        // Assert
        assertEquals(expected, result, "The getActuatorModelsByActuatorTypeIdentity method should return a non-null " +
                "Iterable object.");
    }
}