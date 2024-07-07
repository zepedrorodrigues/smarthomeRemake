package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.mapper.ActuatorTypeDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class represents a test class for the ActuatorTypeMapper class.
 */
class ActuatorTypeMapperTest {

    ActuatorTypeMapper actuatorTypeMapper;
    ActuatorType actuatorType;
    ActuatorTypeDTO actuatorTypeDTO;

    /**
     * Set up before each test
     */
    @BeforeEach
    void setUp() {
        actuatorTypeMapper = new ActuatorTypeMapper();

        actuatorType = mock(ActuatorType.class);

        ActuatorTypeName actuatorTypeName = mock(ActuatorTypeName.class);
        when(actuatorTypeName.getActuatorTypeName()).thenReturn("actuatorTypeName");

        when(actuatorType.getIdentity()).thenReturn(actuatorTypeName);

        actuatorTypeDTO = mock(ActuatorTypeDTO.class);
        when(actuatorTypeDTO.getActuatorTypeName()).thenReturn("actuatorTypeName");
    }

    /**
     * Tests that the toActuatorTypesDTO method returns a list of ActuatorTypeDTOs with the correct size
     */
    @Test
    void toActuatorTypesDTO() {
        //Arrange
        Iterable<ActuatorType> actuatorTypes = List.of(actuatorType);
        int expectedSize = 1;
        //Act
        List<ActuatorTypeDTO> result = actuatorTypeMapper.toActuatorTypesDTO(actuatorTypes);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }

    /**
     * Tests that the toActuatorTypeDTO method returns an ActuatorTypeDTO
     */
    @Test
    void toActuatorTypeDTO() {
        //Arrange
        String expectedActuatorTypeName = "actuatorTypeName";
        //Act
        ActuatorTypeDTO result = actuatorTypeMapper.toActuatorTypeDTO(actuatorType);
        //Assert
        assertEquals(expectedActuatorTypeName, result.getActuatorTypeName(),
                "The actuator type name should be the expected one");
    }

    /**
     * Tests that the toActuatorTypeName method returns an ActuatorTypeName
     */
    @Test
    void toActuatorTypeName() {
        //Arrange
        String expectedActuatorTypeName = "actuatorTypeName";
        //Act
        ActuatorTypeName result = actuatorTypeMapper.toActuatorTypeName(actuatorTypeDTO);
        //Assert
        assertEquals(expectedActuatorTypeName, result.getActuatorTypeName(),
                "The actuator type name should be the expected one");
    }

    /**
     * Tests that the toActuatorTypeIdsDTO method returns a list of ActuatorTypeDTOs with the correct size
     */
    @Test
    void toActuatorTypeIdsDTO() {
        //Arrange
        List<ActuatorTypeName> actuatorTypes = List.of(mock(ActuatorTypeName.class));
        int expectedSize = 1;
        //Act
        List<ActuatorTypeDTO> result = actuatorTypeMapper.toActuatorTypeIdsDTO(actuatorTypes);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }
}