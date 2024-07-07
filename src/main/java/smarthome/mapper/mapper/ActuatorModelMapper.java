package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorModelNameDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between the ActuatorModel domain object and the ActuatorModelDTO data transfer
 * object.
 */
@Component
public class ActuatorModelMapper {

    /**
     * Constructs a new ActuatorModelMapper object.
     */
    public ActuatorModelMapper() {
        // Empty constructor
    }

    /**
     * Converts an ActuatorModel domain object to an ActuatorModelDTO.
     *
     * @param actuatorModel The ActuatorModel domain object to be converted.
     * @return The converted ActuatorModelDTO.
     */
    public ActuatorModelDTO toActuatorModelDTO(ActuatorModel actuatorModel) {
        ActuatorModelName actuatorModelName = actuatorModel.getIdentity();
        String actuatorModelNameStr = actuatorModelName.getActuatorModelName();
        ActuatorTypeName actuatorTypeName = actuatorModel.getActuatorTypeName();
        String actuatorTypeNameStr = actuatorTypeName.getActuatorTypeName();
        return new ActuatorModelDTO(actuatorModelNameStr, actuatorTypeNameStr);
    }

    /**
     * Converts a list of ActuatorModel domain objects to a list of ActuatorModelDTOs.
     *
     * @param actuatorModels The iterable of ActuatorModel domain objects to be converted.
     * @return The list of converted ActuatorModelDTOs.
     */
    public List<ActuatorModelDTO> toActuatorModelsDTO(Iterable<ActuatorModel> actuatorModels) {
        List<ActuatorModelDTO> actuatorModelDTOs = new ArrayList<>();
        for (ActuatorModel actuatorModel : actuatorModels) {
            ActuatorModelDTO actuatorModelDTO = toActuatorModelDTO(actuatorModel);
            actuatorModelDTOs.add(actuatorModelDTO);
        }
        return actuatorModelDTOs;
    }

    /**
     * Converts a list of ActuatorModel domain objects to a list of ActuatorModelDTOs.
     *
     * @param actuatorModelNames The iterable of ActuatorModel domain objects to be converted.
     * @return The list of converted ActuatorModelDTOs.
     */

    public List<ActuatorModelNameDTO> toActuatorModelNamesDTO(List<ActuatorModelName> actuatorModelNames) {
        List<ActuatorModelNameDTO> actuatorModelNameDTOs = new ArrayList<>();
        for (ActuatorModelName actuatorModelName : actuatorModelNames) {
            ActuatorModelNameDTO actuatorModelNameDTO =
                    new ActuatorModelNameDTO(actuatorModelName.getActuatorModelName());
            actuatorModelNameDTOs.add(actuatorModelNameDTO);
        }
        return actuatorModelNameDTOs;
    }
}