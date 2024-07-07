package smarthome.controller;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorTypeDTO;
import smarthome.mapper.mapper.ActuatorMapper;
import smarthome.mapper.mapper.ActuatorModelMapper;
import smarthome.mapper.mapper.ActuatorTypeMapper;
import smarthome.service.IActuatorModelService;
import smarthome.service.IActuatorService;
import smarthome.service.IActuatorTypeService;

import java.util.List;

/**
 * Controller class for adding an actuator to a device.
 */
public class AddActuatorToDeviceController {

    private final IActuatorService actuatorService;
    private final IActuatorTypeService actuatorTypeService;
    private final IActuatorModelService actuatorModelService;
    private final ActuatorTypeMapper actuatorTypeMapper;
    private final ActuatorModelMapper actuatorModelMapper;
    private final ActuatorMapper actuatorMapper;


    /**
     * Constructs a new AddActuatorToDeviceController with the provided actuator service.
     *
     * @param actuatorService the service for handling actuator operations.
     * @throws IllegalArgumentException if the provided actuator service is null.
     */
    public AddActuatorToDeviceController(IActuatorService actuatorService, IActuatorTypeService actuatorTypeService,
                                         IActuatorModelService actuatorModelService,
                                         ActuatorTypeMapper actuatorTypeMapper,
                                         ActuatorModelMapper actuatorModelMapper, ActuatorMapper actuatorMapper) {

        this.actuatorService = actuatorService;
        this.actuatorTypeService = actuatorTypeService;
        this.actuatorModelService = actuatorModelService;
        this.actuatorTypeMapper = actuatorTypeMapper;
        this.actuatorModelMapper = actuatorModelMapper;
        this.actuatorMapper = actuatorMapper;
    }

    /**
     * Gets all the actuator types.
     *
     * @return all the actuator types.
     */
    public List<ActuatorTypeDTO> getActuatorTypes() {
        Iterable<ActuatorType> actuatorTypes = actuatorTypeService.getActuatorTypes();
        return actuatorTypeMapper.toActuatorTypesDTO(actuatorTypes);
    }

    /**
     * Gets all the actuator models by actuator type.
     *
     * @param actuatorTypeDTO the actuator type to get the actuator models for.
     * @return all the actuator models for the provided actuator type.
     */
    public List<ActuatorModelDTO> getActuatorModelsByActuatorType(ActuatorTypeDTO actuatorTypeDTO) {
        if (actuatorTypeDTO == null) {
            throw new IllegalArgumentException();
        }
        ActuatorTypeName actuatorTypeName = actuatorTypeMapper.toActuatorTypeName(actuatorTypeDTO);
        Iterable<ActuatorModel> actuatorModels = actuatorModelService.getActuatorModels(actuatorTypeName);
        return actuatorModelMapper.toActuatorModelsDTO(actuatorModels);
    }

    /**
     * Adds an actuator.
     *
     * @param actuatorDTO the actuator to add.
     * @return the added ActuatorDTO object if the actuator is added successfully, or null if an exception occurs or
     * if the provided actuator is null.
     * @throws IllegalArgumentException if the provided actuator is null.
     */
    public ActuatorDTO addActuator(ActuatorDTO actuatorDTO, DeviceId deviceId) {
        if (actuatorDTO == null) {
            return null;
        }
        ActuatorMap actuatorMap = actuatorMapper.toActuatorMap(actuatorDTO);
        Actuator savedActuator = actuatorService.addActuator(actuatorMap, deviceId);
        if (savedActuator != null) {
            return actuatorMapper.actuatorToDTO(savedActuator);
        } else {
            return null;
        }
    }
}