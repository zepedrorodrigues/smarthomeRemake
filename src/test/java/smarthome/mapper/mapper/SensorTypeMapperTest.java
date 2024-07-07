package smarthome.mapper.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.domain.sensortype.vo.SensorTypeName;
import smarthome.domain.sensortype.vo.SensorTypeUnit;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.SensorTypeIdDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for SensorTypeMapper
 */
class SensorTypeMapperTest {

    SensorTypeMapper sensorTypeMapper;
    SensorType sensorType;
    SensorTypeDTO sensorTypeDTO;
    SensorTypeId sensorTypeId;

    /**
     * Set up for the tests
     */
    @BeforeEach
    void setUp() {
        sensorTypeMapper = new SensorTypeMapper();

        sensorType = mock(SensorType.class);

        SensorTypeName sensorTypeName = mock(SensorTypeName.class);
        when(sensorTypeName.getSensorTypeName()).thenReturn("Temperature");

        SensorTypeUnit sensorTypeUnit = mock(SensorTypeUnit.class);
        when(sensorTypeUnit.getSensorTypeUnit()).thenReturn("Celsius");

        sensorTypeId = mock(SensorTypeId.class);
        when(sensorTypeId.getSensorTypeId()).thenReturn("1");

        when(sensorType.getSensorTypeName()).thenReturn(sensorTypeName);
        when(sensorType.getSensorTypeUnit()).thenReturn(sensorTypeUnit);
        when(sensorType.getIdentity()).thenReturn(sensorTypeId);

        sensorTypeDTO = mock(SensorTypeDTO.class);
        when(sensorTypeDTO.getSensorTypeId()).thenReturn("1");
        when(sensorTypeDTO.getSensorTypeName()).thenReturn("Temperature");
        when(sensorTypeDTO.getSensorTypeUnit()).thenReturn("Celsius");

    }

    /**
     * Tests that the toSensorTypeDTO method returns a SensorTypeDTO object with the correct sensor type id.
     */
    @Test
    void testToSensorTypeDTOSensorTypeId() {
        //Arrange
        String expectedId = "1";
        //Act
        SensorTypeDTO result = sensorTypeMapper.toSensorTypeDTO(sensorType);
        //Assert
        assertEquals(expectedId, result.getSensorTypeId(), "The sensor type id should be correct");
    }

    /**
     * Tests that the toSensorTypeDTO method returns a SensorTypeDTO object with the correct sensor type name.
     */
    @Test
    void testToSensorTypeDTOSensorTypeName() {
        //Arrange
        String expectedSensorTypeName = "Temperature";
        //Act
        SensorTypeDTO result = sensorTypeMapper.toSensorTypeDTO(sensorType);
        //Assert
        assertEquals(expectedSensorTypeName, result.getSensorTypeName(), "The sensor type name should be correct");
    }

    /**
     * Tests that the toSensorTypeDTO method returns a SensorTypeDTO object with the correct sensor type unit.
     */
    @Test
    void testToSensorTypeDTOSensorTypeUnit() {
        //Arrange
        String expectedSensorTypeUnit = "Celsius";
        //Act
        SensorTypeDTO result = sensorTypeMapper.toSensorTypeDTO(sensorType);
        //Assert
        assertEquals(expectedSensorTypeUnit, result.getSensorTypeUnit(), "The sensor type unit should be correct");
    }

    /**
     * Tests that the toSensorTypesIdDTO method returns a list of SensorTypeIdDTO objects with the correct size.
     */
    @Test
    void testToSensorTypesIdDTO() {
        //Arrange
        Iterable<SensorTypeId> sensorTypeIds = List.of(sensorTypeId);
        int expectedSize = 1;
        //Act
        List<SensorTypeIdDTO> result = sensorTypeMapper.toSensorTypesIdDTO(sensorTypeIds);
        //Assert
        assertEquals(expectedSize, result.size(), "The list should have one element");
    }

    /**
     * Tests that the toSensorTypeId method returns a SensorTypeId object with the correct sensor type id.
     */
    @Test
    void testToSensorTypeId() {
        //Arrange
        String expectedSensorTypeId = "1";
        //Act
        SensorTypeId result = sensorTypeMapper.toSensorTypeId(sensorTypeDTO);
        //Assert
        assertEquals(expectedSensorTypeId, result.getSensorTypeId(), "The sensor type id should be correct");
    }
}