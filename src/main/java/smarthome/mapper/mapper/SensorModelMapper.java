package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorModelNameDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for SensorModelDTO
 */
@Component
public class SensorModelMapper {

    /**
     * SensorModelMapper constructor
     */
    public SensorModelMapper() {
        // Empty constructor
    }

    /**
     * Converts a SensorModel to a SensorModelDTO
     *
     * @param sensorModel the SensorModel
     * @return the SensorModelDTO
     */
    public SensorModelDTO toSensorModelDto(SensorModel sensorModel) {
        SensorModelName sensorModelName = sensorModel.getIdentity();
        String sensorModelNameStr = sensorModelName.getSensorModelName();
        SensorTypeId sensorTypeId = sensorModel.getSensorTypeId();
        String sensorTypeIdStr = sensorTypeId.getSensorTypeId();
        return new SensorModelDTO(sensorModelNameStr, sensorTypeIdStr);
    }

    /**
     * Converts a SensorModelDTO to a SensorModelName value object
     *
     * @param sensorModelDTO the SensorModelDTO to convert
     * @return the SensorModelName value object
     */
    public SensorModelName toSensorModelName(SensorModelDTO sensorModelDTO) {
        return new SensorModelName(sensorModelDTO.getSensorModelName());
    }

    /**
     * Converts a list of SensorModelName objects into a list of SensorModelNameDTO objects.
     *
     * @param sensorModelNames an Iterable of SensorModelName objects to be converted
     * @return a list of SensorModelNameDTO objects corresponding to the provided SensorModelNames
     */
    public List<SensorModelNameDTO> toSensorModelsNameDTO(Iterable<SensorModelName> sensorModelNames) {
        List<SensorModelNameDTO> sensorModelNameDTOList = new ArrayList<>();
        for(SensorModelName sensorModelName : sensorModelNames) {
            SensorModelNameDTO sensorModelNameDTO = new SensorModelNameDTO(sensorModelName.getSensorModelName());
            sensorModelNameDTOList.add(sensorModelNameDTO);
        }
        return sensorModelNameDTOList;
    }
}
