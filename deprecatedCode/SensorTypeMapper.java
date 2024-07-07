package mappers;

import java.util.List;

/**
 * This class is a Mapper for SensorTypeDTO.
 * It is used to map a list of sensor types to a SensorTypeDTO object.
 */
public class SensorTypeMapper {

    /**
     * Constructs a new SensorTypeMapper.
     * The constructor is empty because it is a Mapper.
     */
    public SensorTypeMapper() {
        // Constructor, empty because it is a Mapper
    }

    /**
     * Maps a list of sensor types to a SensorTypeDTO object.
     *
     * @param sensorTypes the list of sensor types
     * @return a SensorTypeDTO object containing the list of sensor types
     */
    public SensorTypeDTO getSensorTypes(List<String> sensorTypes) {
        return new SensorTypeDTO(sensorTypes);
    }
}
