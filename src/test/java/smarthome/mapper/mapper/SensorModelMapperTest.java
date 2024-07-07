package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorModelNameDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for SensorModelMapper
 */
class SensorModelMapperTest {

    SensorModelMapper sensorModelMapper;
    SensorModelDTO sensorModelDTO;
    SensorModel sensorModel;
    SensorModelName sensorModelName;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        sensorModelMapper = new SensorModelMapper();

        sensorModelDTO = mock(SensorModelDTO.class);

        when(sensorModelDTO.getSensorModelName()).thenReturn("Temperature");
        when(sensorModelDTO.getSensorTypeId()).thenReturn("1");

        sensorModel= mock(SensorModel.class);

        SensorTypeId sensorTypeId = mock(SensorTypeId.class);
        when(sensorTypeId.getSensorTypeId()).thenReturn("1");

        sensorModelName = mock(SensorModelName.class);
        when(sensorModelName.getSensorModelName()).thenReturn("Temperature");

        when(sensorModel.getIdentity()).thenReturn(sensorModelName);
        when(sensorModel.getSensorTypeId()).thenReturn(sensorTypeId);
    }

    /**
     * Tests that the toSensorModelDto method returns a SensorModelDTO object with the correct sensor model name.
     */
    @Test
    void testToSensorModelDtoSensorModelName() {
        //Arrange
        String expectedName = "Temperature";
        //Act
        SensorModelDTO result = sensorModelMapper.toSensorModelDto(sensorModel);
        //Assert
        assertEquals(expectedName, result.getSensorModelName(), "The sensor model name should be correct");
    }

    /**
     * Tests that the toSensorModelDto method returns a SensorModelDTO object with the correct sensor type id.
     */
    @Test
    void testToSensorModelDtoSensorTypeId() {
        //Arrange
        String expectedSensorTypeId = "1";
        //Act
        SensorModelDTO result = sensorModelMapper.toSensorModelDto(sensorModel);
        //Assert
        assertEquals(expectedSensorTypeId, result.getSensorTypeId(), "The sensor type id should be correct");
    }

    /**
     * Tests that the toSensorModelName method returns a SensorModelName object with the correct sensor model name.
     */
    @Test
    void testToSensorModelName() {
        //Arrange
        String expectedSensorModelName = "Temperature";
        //Act
        SensorModelName result = sensorModelMapper.toSensorModelName(sensorModelDTO);
        //Assert
        assertEquals(expectedSensorModelName, result.getSensorModelName(), "The sensor model name should be correct");
    }

    /**
     * Tests that the toSensorModelNameDTO method returns a list of SensorModelNameDTO objects with the correct size.
     */
    @Test
    void testToSensorModelsNameDTO() {
        //Arrange
        Iterable<SensorModelName> sensorModelNames = List.of(sensorModelName);
        int expectedSize = 1;
        //Act
        List<SensorModelNameDTO> result = sensorModelMapper.toSensorModelsNameDTO(sensorModelNames);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }
}