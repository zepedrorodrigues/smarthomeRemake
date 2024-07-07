package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.SensorTypeIdDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for SensorTypeDTO
 */
@Component
public class SensorTypeMapper {

    /**
     * Converts a SensorType object to a SensorTypeDTO object.
     *
     * @param sensorType The SensorType object to be converted.
     * @return SensorTypeDTO object that contains the id, name, and unit of the SensorType.
     */
    public SensorTypeDTO toSensorTypeDTO(SensorType sensorType) {
        String id = sensorType.getIdentity().getSensorTypeId();
        String name = sensorType.getSensorTypeName().getSensorTypeName();
        String unit = sensorType.getSensorTypeUnit().getSensorTypeUnit();
        return new SensorTypeDTO(id, name, unit);
    }

    /**
     * Converts an Iterable of SensorTypeId objects to a List of SensorTypeDTO objects.
     *
     * @param sensorTypesIds The Iterable of SensorTypeId objects to be converted.
     * @return List of SensorTypeDTO objects that contains the id of each SensorTypeId.
     */
    public List<SensorTypeIdDTO> toSensorTypesIdDTO(Iterable<SensorTypeId> sensorTypesIds) {
        List<SensorTypeIdDTO> sensorTypeDTOs = new ArrayList<>();
        for (SensorTypeId sensorTypeId : sensorTypesIds) {
            String id = sensorTypeId.getSensorTypeId();
            SensorTypeIdDTO sensorTypeIdDTO = new SensorTypeIdDTO(id);
            sensorTypeDTOs.add(sensorTypeIdDTO);
        }
        return sensorTypeDTOs;
    }

    /**
     * Converts the sensor type id data in SensorTypeDTO to SensorTypeId value object
     *
     * @param sensorTypeDTO SensorTypeDTO
     * @return SensorTypeId the SensorTypeId value object
     * @throws IllegalArgumentException if the data in SensorTypeDTO is invalid
     */
    public SensorTypeId toSensorTypeId(SensorTypeDTO sensorTypeDTO) {
        return new SensorTypeId(sensorTypeDTO.getSensorTypeId());
    }
}
