package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorTypeRepository;
import smarthome.persistence.datamodel.ActuatorTypeDataModel;
import smarthome.persistence.datamodel.mapper.ActuatorTypeDataModelMapper;
import smarthome.persistence.spring.IActuatorTypeRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the IActuatorTypeRepository interface and provides Spring Data-based persistence operations.
 */
@Repository
public class ActuatorTypeRepositorySpringDataImpl implements IActuatorTypeRepository {

    private final ActuatorTypeDataModelMapper actuatorTypeDataModelMapper;
    private final IActuatorTypeRepositorySpringData actuatorTypeSpringDataRepository;

    /**
     * Constructor for the ActuatorTypeRepositorySpringDataImpl class.
     *
     * @param actuatorTypeDataModelMapper      The mapper to convert between the ActuatorType domain model and the
     *                                         ActuatorTypeDataModel persistence model.
     * @param actuatorTypeSpringDataRepository The Spring Data repository for ActuatorTypeDataModel.
     */
    public ActuatorTypeRepositorySpringDataImpl(ActuatorTypeDataModelMapper actuatorTypeDataModelMapper,
                                                IActuatorTypeRepositorySpringData actuatorTypeSpringDataRepository) {
        this.actuatorTypeDataModelMapper = actuatorTypeDataModelMapper;
        this.actuatorTypeSpringDataRepository = actuatorTypeSpringDataRepository;
    }

    /**
     * Save an ActuatorType to the database.
     *
     * @param actuatorType The ActuatorType to save.
     * @return The saved ActuatorType.
     */
    @Override
    public ActuatorType save(ActuatorType actuatorType) {
        if (actuatorType == null || containsIdentity(actuatorType.getIdentity())) {
            throw new IllegalArgumentException();
        }
        ActuatorTypeDataModel actuatorTypeDataModel = new ActuatorTypeDataModel(actuatorType);
        actuatorTypeSpringDataRepository.save(actuatorTypeDataModel);
        return actuatorType;
    }

    /**
     * Retrieve all ActuatorTypes from the database.
     *
     * @return An Iterable of all ActuatorTypes.
     */
    @Override
    public Iterable<ActuatorType> findAll() {
        List<ActuatorTypeDataModel> actuatorTypeDataModels = actuatorTypeSpringDataRepository.findAll();
        return actuatorTypeDataModelMapper.toDomain(actuatorTypeDataModels);
    }

    /**
     * Retrieve an ActuatorType by its identity.
     *
     * @param actuatorTypeName The identity of the ActuatorType to retrieve.
     * @return An Optional containing the ActuatorType if found, or empty if not found.
     */
    @Override
    public Optional<ActuatorType> findByIdentity(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        Optional<ActuatorTypeDataModel> actuatorTypeDataModel =
                actuatorTypeSpringDataRepository.findById(actuatorTypeName.getActuatorTypeName());
        return actuatorTypeDataModel.map(actuatorTypeDataModelMapper::toDomain);
    }

    /**
     * Check if an ActuatorType with the given identity exists in the database.
     *
     * @param actuatorTypeName The identity of the ActuatorType to check.
     * @return true if the ActuatorType exists, false otherwise.
     */
    @Override
    public boolean containsIdentity(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        return actuatorTypeSpringDataRepository.existsById(actuatorTypeName.getActuatorTypeName());
    }

    /**
     * Retrieve all actuator type ids from the database.
     *
     * @return An Iterable of all actuator type ids.
     */
    @Override
    public Iterable<ActuatorTypeName> findActuatorTypeNames() {
        List<String> actuatorTypeIds = actuatorTypeSpringDataRepository.findActuatorTypeNames();
        return actuatorTypeIds.stream().map(ActuatorTypeName::new).toList();
    }
}