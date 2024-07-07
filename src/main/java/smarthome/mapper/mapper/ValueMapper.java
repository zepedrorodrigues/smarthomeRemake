package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.mapper.ValueDTO;

/**
 * Mapper for the value.
 */
@Component
public class ValueMapper {

    /**
     * Constructs a new ValueMapper.
     */
    public ValueMapper() {
        // Empty constructor
    }

    /**
     * Maps a value to a DTO.
     *
     * @param value the value
     * @return the DTO
     */
    public ValueDTO toDTO(Value value) {
        return new ValueDTO(value.valueToString());
    }

}
