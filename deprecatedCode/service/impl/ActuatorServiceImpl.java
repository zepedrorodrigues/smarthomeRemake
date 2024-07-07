package smarthome.service.impl;

import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.ActuatorFactory;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.domain.repository.IActuatorRepository;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorTypeDTO;
import smarthome.mapper.mapper.ActuatorMapper;
import smarthome.mapper.mapper.ActuatorModelMapper;
import smarthome.mapper.mapper.ActuatorTypeMapper;
import smarthome.service.IActuatorService;

import java.util.List;

/**
 * This class is an implementation of the IActuatorService interface.
 * It provides methods to get actuator types, get actuator models based on a type, and add an actuator.
 */
public class ActuatorServiceImpl implements IActuatorService {

    private final IActuatorRepository actuatorRepository;
    private final IDeviceRepository deviceRepository;
    private final IActuatorTypeRepository actuatorTypeRepository;
    private final IActuatorModelRepository actuatorModelRepository;
    private final ActuatorFactory actuatorFactory;
    private final ActuatorTypeMapper actuatorTypeMapper;
    private final ActuatorMapper actuatorMapper;
    private final ActuatorModelMapper actuatorModelMapper;


    /**
     * Constructs a new ActuatorServiceImpl with the provided dependencies.
     *
     * @param actuatorTypeMapper      the mapper for converting between ActuatorType and ActuatorTypeDTO objects.
     * @param actuatorTypeRepository  the repository for accessing actuator type data.
     * @param actuatorModelRepository the repository for accessing actuator model data.
     * @param actuatorFactory         the factory for creating new Actuator objects.
     * @param actuatorRepository      the repository for accessing actuator data.
     * @param actuatorMapper          the mapper for converting between Actuator and ActuatorDTO objects.
     * @param actuatorModelMapper     the mapper for converting between ActuatorModel and ActuatorModelDTO objects.
     * @throws IllegalArgumentException if any of the provided dependencies are null.
     */
    public ActuatorServiceImpl(ActuatorTypeMapper actuatorTypeMapper, IActuatorTypeRepository actuatorTypeRepository,
                               IActuatorModelRepository actuatorModelRepository, ActuatorFactory actuatorFactory, IActuatorRepository actuatorRepository, ActuatorMapper actuatorMapper, ActuatorModelMapper actuatorModelMapper, IDeviceRepository deviceRepository) {
        if (actuatorTypeMapper == null || actuatorTypeRepository == null || actuatorModelRepository == null || actuatorFactory == null || actuatorRepository == null || actuatorMapper == null || actuatorModelMapper == null || deviceRepository == null) {
            throw new IllegalArgumentException();
        }
        this.actuatorTypeMapper = actuatorTypeMapper;
        this.actuatorTypeRepository = actuatorTypeRepository;
        this.actuatorModelRepository = actuatorModelRepository;
        this.actuatorFactory = actuatorFactory;
        this.actuatorRepository = actuatorRepository;
        this.actuatorMapper = actuatorMapper;
        this.actuatorModelMapper = actuatorModelMapper;
        this.deviceRepository = deviceRepository;
    }

    /**
     * Gets a list of actuator types.
     *
     * @return a list of ActuatorTypeDTO objects.
     */
    @Override
    public List<ActuatorTypeDTO> getActuatorTypes() {
        return actuatorTypeMapper.toActuatorTypesDTO(actuatorTypeRepository.findAll());
    }

    /**
     * Gets a list of actuator models for a given actuator type.
     *
     * @param actuatorTypeDTO the actuator type for which to get the models.
     * @return a list of ActuatorModelDTO objects, or null if the provided actuator type is null.
     */
    @Override
    public List<ActuatorModelDTO> getActuatorModels(ActuatorTypeDTO actuatorTypeDTO) {
        try {
        ActuatorTypeName actuatorTypeName = actuatorTypeMapper.toActuatorTypeName(actuatorTypeDTO);
        Iterable<ActuatorModel> actuatorModels = actuatorModelRepository.findByActuatorTypeIdentity(actuatorTypeName);
        return actuatorModelMapper.toActuatorModelsDTO(actuatorModels);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Adds an actuator.
     *
     * @param actuatorDTO the actuator to add.
     * @return the added ActuatorDTO object if the actuator is added successfully, or null if the provided actuator
     * is null.
     */
    @Override
    public ActuatorDTO addActuator(ActuatorDTO actuatorDTO) {
        try {
            DeviceId deviceId = actuatorMapper.toDeviceId(actuatorDTO);
            if (!deviceRepository.containsIdentity(deviceId)) {
                return null;
            }
            //create actuator
            ActuatorMap actuatorMap = actuatorMapper.toActuatorMap(actuatorDTO);
            Actuator actuator = actuatorFactory.createActuator(actuatorMap);
            //save the actuator
            actuatorRepository.save(actuator);
            return actuatorMapper.actuatorToDTO(actuator);

        } catch (Exception e) {
            return null;
        }
    }
}