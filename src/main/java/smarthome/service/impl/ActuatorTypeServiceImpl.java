package smarthome.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.service.IActuatorTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ActuatorType.
 */
@Service
public class ActuatorTypeServiceImpl implements IActuatorTypeService {

    private final IActuatorTypeRepository actuatorTypeRepository;

    /**
     * Constructor for the ActuatorTypeServiceImpl class.
     *
     * @param actuatorTypeRepository The repository for ActuatorType.
     */
    @Autowired
    public ActuatorTypeServiceImpl(IActuatorTypeRepository actuatorTypeRepository) {
        this.actuatorTypeRepository = actuatorTypeRepository;
    }

    /**
     * Get all the actuator types.
     *
     * @return the list of entities.
     */
    @Override
    public Iterable<ActuatorType> getActuatorTypes() {
        Iterable<ActuatorType> actuatorTypes = actuatorTypeRepository.findAll();
        List<ActuatorType> actuatorTypeList = new ArrayList<>();
        actuatorTypes.forEach(actuatorTypeList::add);
        return actuatorTypeList;
    }

    /**
     * Get all the actuator type ids.
     *
     * @return the list of actuator type ids.
     */
    @Override
    public List<ActuatorTypeName> getActuatorTypeIds() {
        Iterable<ActuatorTypeName> actuatorTypeIds = actuatorTypeRepository.findActuatorTypeNames();
        List<ActuatorTypeName> actuatorTypeNameList = new ArrayList<>();
        actuatorTypeIds.forEach(actuatorTypeNameList::add);
        return actuatorTypeNameList;
    }

    /**
     * Get the actuator type by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ActuatorType> getActuatorTypeById(ActuatorTypeName id) {
        return actuatorTypeRepository.findByIdentity(id);
    }

}