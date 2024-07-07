package smarthome.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.service.IActuatorTypeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ActuatorTypeServiceImplTest {

    //Mock objects for the repositories
    IActuatorTypeRepository mockActuatorTypeRepository;

    IActuatorTypeService actuatorTypeService;

    ActuatorType mockActuatorType;
    ActuatorTypeName mockActuatorTypeName;

    @BeforeEach
    void setUp() {
        mockActuatorType = mock(ActuatorType.class);
        mockActuatorTypeName = mock(ActuatorTypeName.class);
        mockActuatorTypeRepository = mock(IActuatorTypeRepository.class);
        actuatorTypeService = new ActuatorTypeServiceImpl(mockActuatorTypeRepository);
    }

    @Test
    void testActuatorTypeServiceImplConstructor() {
        IActuatorTypeService actuatorTypeService = new ActuatorTypeServiceImpl(mockActuatorTypeRepository);
        assertNotNull(actuatorTypeService, "The constructor of the ActuatorTypeServiceImpl class should work " +
                "correctly.");
    }

    /**
     * This test checks that the constructor of the ActuatorServiceImpl class works correctly.
     */
    @Test
    void testGetActuatorTypes() {
        // Arrange
        when(mockActuatorTypeRepository.findAll()).thenReturn(new ArrayList<>());
        // Act
        Iterable<ActuatorType> result = actuatorTypeService.getActuatorTypes();
        // Assert
        assertNotNull(result, "The getActuatorTypes method should return a non-null Iterable object.");
    }

    /**
     * This test checks that the getActuatorTypes method returns a non-null Iterable object.
     */
    @Test
    void testGetActuatorTypesReturnsNotNullWhenRepositoryIsEmpty() {
        // Arrange
        when(mockActuatorTypeRepository.findAll()).thenReturn(Collections.emptyList());
        // Act
        Iterable<ActuatorType> result = actuatorTypeService.getActuatorTypes();
        // Assert
        assertNotNull(result, "The getActuatorTypes method should return a non-null Iterable object.");
    }


    /**
     * This test checks that the getActuatorTypes method returns a non-empty list.
     */
    @Test
    void testGetActuatorTypesReturnsNonEmptyList() {
        // Arrange
        List<ActuatorType> actuatorTypes = Arrays.asList(mock(ActuatorType.class), mock(ActuatorType.class));
        when(mockActuatorTypeRepository.findAll()).thenReturn(actuatorTypes);

        // Act
        Iterable<ActuatorType> result = actuatorTypeService.getActuatorTypes();

        // Assert
        assertTrue(result.iterator().hasNext(), "The getActuatorTypes method should return a non-empty list.");
    }

    /**
     * This test checks that the getActuatorTypes method returns a list with two elements when the repository has two
     * ActuatorType objects.
     */
    @Test
    void testGetActuatorTypeIdsEmptyRepository() {
        //Arrange
        int expectedSize = 0;
        //Act
        List<ActuatorTypeName> result = actuatorTypeService.getActuatorTypeIds();
        //Assert
        assertEquals(expectedSize, result.size(), "The getActuatorTypeIds method should return an empty list when " +
                "the" + " repository is empty.");
    }

    /*
     * This test checks that the getActuatorTypeIds method returns a list with one element when the repository has one
     * ActuatorType object.
     */
    @Test
    void testGetActuatorTypeIdsOneActuatorType() {
        //Arrange
        int expectedSize = 1;
        Iterable<ActuatorTypeName> actuatorTypeNames = List.of(mockActuatorTypeName);
        when(mockActuatorTypeRepository.findActuatorTypeNames()).thenReturn(actuatorTypeNames);
        //Act
        List<ActuatorTypeName> result = actuatorTypeService.getActuatorTypeIds();
        //Assert
        assertEquals(expectedSize, result.size(), "The getActuatorTypeIds method should return a list with one " +
                "element when the repository has one ActuatorType.");
    }

    /*
     * This test checks that the getActuatorTypeIds method returns a list with two elements when the repository has two
     * ActuatorType objects.
     */
    @Test
    void testGetActuatorTypeIdsTwoActuatorTypes() {
        //Arrange
        int expectedSize = 2;
        Iterable<ActuatorTypeName> actuatorTypeNames = List.of(mockActuatorTypeName, mockActuatorTypeName);
        when(mockActuatorTypeRepository.findActuatorTypeNames()).thenReturn(actuatorTypeNames);
        //Act
        List<ActuatorTypeName> result = actuatorTypeService.getActuatorTypeIds();
        //Assert
        assertEquals(expectedSize, result.size(), "The getActuatorTypeIds method should return a list with two " +
                "elements when the repository has two ActuatorTypes.");
    }

    @Test
    void testGetActuatorTypeById() {
        //Arrange
        Optional<ActuatorType> expected = Optional.of(mockActuatorType);
        when(mockActuatorTypeRepository.findByIdentity(mockActuatorTypeName)).thenReturn(Optional.of(mockActuatorType));
        //Act
        Optional<ActuatorType> result = actuatorTypeService.getActuatorTypeById(mockActuatorTypeName);
        //Assert
        assertEquals(expected, result, "The getActuatorTypeById method should return an Optional object with the " +
                "ActuatorType object when the repository has the ActuatorType object.");
    }

    /*
     * This test checks that the getActuatorTypeIds method returns a list with three elements when the repository has
     * three ActuatorType objects.
     */
    @Test
    void testGetActuatorTypeByIdEmpty() {
        //Arrange
        Optional<ActuatorType> expected = Optional.empty();
        ActuatorTypeName actuatorTypeName = mock(ActuatorTypeName.class);
        when(mockActuatorTypeRepository.findByIdentity(actuatorTypeName)).thenReturn(Optional.empty());
        //Act
        Optional<ActuatorType> result = actuatorTypeService.getActuatorTypeById(actuatorTypeName);
        //Assert
        assertEquals(expected, result, "The getActuatorTypeById method should return an empty Optional object when " +
                "the repository is empty.");

    }
}