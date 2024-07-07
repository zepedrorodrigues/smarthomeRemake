package smarthome.mapper.mapper;

import org.junit.jupiter.api.Test;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.mapper.ValueDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for the ValueMapper.
 */
class ValueMapperTest {

    /**
     * Tests that the toDTO method returns the correct value.
     */
    @Test
    void toDTO() {
        //Arrange
        ValueMapper valueMapper = new ValueMapper();
        Value value = mock(Value.class);
        when(value.valueToString()).thenReturn("30.4");

        String expected = "30.4";
        //Act
        ValueDTO valueDTO = valueMapper.toDTO(value);
        //Assert
        assertEquals(expected, valueDTO.getValue(), "The value should be 30.4");
    }
}