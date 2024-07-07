package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.mapper.ActuatorTypeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between ActuatorType domain objects and ActuatorTypeDTO data transfer objects.
 */
@Component
public class ActuatorTypeMapper {
    /**
     * Converts a collection of ActuatorType domain objects into a list of ActuatorTypeDTOs.
     *
     * @param actuatorTypes an Iterable collection of ActuatorType domain objects.
     * @return a List of ActuatorTypeDTOs.
     */
    public List<ActuatorTypeDTO> toActuatorTypesDTO(Iterable<ActuatorType> actuatorTypes) {
        List<ActuatorTypeDTO> actuatorTypeDTOs = new ArrayList<>();
        for (ActuatorType actuatorType : actuatorTypes) {
            ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorType.getIdentity().getActuatorTypeName());
            actuatorTypeDTOs.add(actuatorTypeDTO);
        }
        return actuatorTypeDTOs;
    }

    public ActuatorTypeDTO toActuatorTypeDTO(ActuatorType actuatorType) {
        return new ActuatorTypeDTO(actuatorType.getIdentity().getActuatorTypeName());
    }


    /**
     * Converts an ActuatorTypeDTO into an ActuatorTypeName value object.
     *
     * @param actuatorTypeDTO the ActuatorTypeDTO to convert.
     * @return an ActuatorTypeName value object.
     * @throws IllegalArgumentException if the ActuatorTypeDTO cannot be converted into an ActuatorTypeName.
     */
    public ActuatorTypeName toActuatorTypeName(ActuatorTypeDTO actuatorTypeDTO) throws IllegalArgumentException {
        return new ActuatorTypeName(actuatorTypeDTO.getActuatorTypeName());
    }


    /**
     * Converts a list of ActuatorTypeName value objects into a list of ActuatorTypeDTOs.
     *
     * @param actuatorTypes a list of ActuatorTypeName value objects.
     * @return a list of ActuatorTypeDTOs.
     */
    public List<ActuatorTypeDTO> toActuatorTypeIdsDTO(List<ActuatorTypeName> actuatorTypes) {
        List<ActuatorTypeDTO> actuatorTypeDTOList = new ArrayList<>();
        for (ActuatorTypeName actuatorTypeName : actuatorTypes) {
            ActuatorTypeDTO actuatorTypeDTO = new ActuatorTypeDTO(actuatorTypeName.getActuatorTypeName());
            actuatorTypeDTOList.add(actuatorTypeDTO);
        }
        return actuatorTypeDTOList;
    }

}
