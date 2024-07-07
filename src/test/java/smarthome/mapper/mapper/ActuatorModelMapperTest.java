package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorModelNameDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the ActuatorModelMapper.
 */
class ActuatorModelMapperTest {

    ActuatorModelMapper actuatorModelMapper;
    ActuatorModel actuatorModel;
    ActuatorModelDTO actuatorModelDTO;
    ActuatorModelName actuatorModelName;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    void setUp() {
        actuatorModelMapper = new ActuatorModelMapper();

        actuatorModelDTO = mock(ActuatorModelDTO.class);
        when(actuatorModelDTO.getActuatorModelName()).thenReturn("actuatorModelName");
        when(actuatorModelDTO.getActuatorTypeName()).thenReturn("actuatorTypeName");

        actuatorModel = mock(ActuatorModel.class);

        actuatorModelName = mock(ActuatorModelName.class);
        when(actuatorModelName.getActuatorModelName()).thenReturn("actuatorModelName");

        ActuatorTypeName actuatorTypeName = mock(ActuatorTypeName.class);
        when(actuatorTypeName.getActuatorTypeName()).thenReturn("actuatorTypeName");

        when(actuatorModel.getIdentity()).thenReturn(actuatorModelName);
        when(actuatorModel.getActuatorTypeName()).thenReturn(actuatorTypeName);

    }

    /**
     * Test that the toActuatorModelDTO method returns an ActuatorModelDTO object with the correct actuatorModelName.
     */
    @Test
    void toActuatorModelDTOActuatorModelName() {
        //Arrange
        String expectedActuatorModelName = "actuatorModelName";
        //Act
        ActuatorModelDTO result = actuatorModelMapper.toActuatorModelDTO(actuatorModel);
        //Assert
        assertEquals(expectedActuatorModelName, result.getActuatorModelName(), "The actuatorModelName should be the " +
                "expected one.");
    }

    /**
     * Test that the toActuatorModelDTO method returns an ActuatorModelDTO object with the correct actuatorTypeName.
     */
    @Test
    void toActuatorModelDTOActuatorTypeName() {
        //Arrange
        String expectedActuatorTypeName = "actuatorTypeName";
        //Act
        ActuatorModelDTO result = actuatorModelMapper.toActuatorModelDTO(actuatorModel);
        //Assert
        assertEquals(expectedActuatorTypeName, result.getActuatorTypeName(), "The actuatorTypeName should be the " +
                "expected one.");
    }

    /**
     * Test that the toActuatorModelsDTO method returns a list of ActuatorModelDTO objects with the correct size.
     */
    @Test
    void toActuatorModelsDTO() {
        //Arrange
        Iterable<ActuatorModel> actuatorModels = List.of(actuatorModel);
        int expectedSize = 1;
        //Act
        List<ActuatorModelDTO> result = actuatorModelMapper.toActuatorModelsDTO(actuatorModels);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element.");
    }

    /**
     * Test that the toActuatorModelNamesDTO method returns a list of ActuatorModelNameDTO objects
     * with the correct size.
     */
    @Test
    void toActuatorModelNamesDTO() {
        //Arrange
        List<ActuatorModelName> actuatorModelNames = List.of(mock(ActuatorModelName.class));
        int expectedSize = 1;
        //Act
        List<ActuatorModelNameDTO> result = actuatorModelMapper.toActuatorModelNamesDTO(actuatorModelNames);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element.");

    }
}