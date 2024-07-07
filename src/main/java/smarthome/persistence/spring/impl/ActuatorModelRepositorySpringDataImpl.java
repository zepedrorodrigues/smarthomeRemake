package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.domain.repository.IActuatorModelRepository;
import smarthome.persistence.datamodel.ActuatorModelDataModel;
import smarthome.persistence.datamodel.mapper.ActuatorModelDataModelMapper;
import smarthome.persistence.spring.IActuatorModelRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the IActuatorModelRepository interface.
 * It provides the functionality to interact with the database using Spring Data.
 */
@Repository
public class ActuatorModelRepositorySpringDataImpl implements IActuatorModelRepository {
    private final ActuatorModelDataModelMapper actuatorModelDataModelMapper;
    private final IActuatorModelRepositorySpringData actuatorModelSpringDataRepository;

    /**
     * Constructor for the ActuatorModelRepositorySpringDataImpl class.
     *
     * @param actuatorModelDataModelMapper      The mapper to convert between the ActuatorModel and
     *                                          ActuatorModelDataModel.
     * @param actuatorModelSpringDataRepository The Spring Data repository for ActuatorModelDataModel.
     */
    public ActuatorModelRepositorySpringDataImpl(ActuatorModelDataModelMapper actuatorModelDataModelMapper,
                                                 IActuatorModelRepositorySpringData actuatorModelSpringDataRepository) {
        this.actuatorModelDataModelMapper = actuatorModelDataModelMapper;
        this.actuatorModelSpringDataRepository = actuatorModelSpringDataRepository;
    }

    /**
     * This method is used to save an ActuatorModel.
     *
     * @param actuatorModel The ActuatorModel to save.
     * @return The saved ActuatorModel.
     * @throws IllegalArgumentException if actuatorModel is null or if an ActuatorModel with the same identity
     *                                  already exists.
     */
    @Override
    public ActuatorModel save(ActuatorModel actuatorModel) {
        if (actuatorModel == null || containsIdentity(actuatorModel.getIdentity())) {
            throw new IllegalArgumentException();
        }
        ActuatorModelDataModel actuatorModelDataModel = new ActuatorModelDataModel(actuatorModel);
        actuatorModelSpringDataRepository.save(actuatorModelDataModel);
        return actuatorModel;
    }

    /**
     * This method is used to find all ActuatorModels.
     *
     * @return An Iterable of all ActuatorModels.
     */
    @Override
    public Iterable<ActuatorModel> findAll() {
        return actuatorModelDataModelMapper.toActuatorModelsDomain(actuatorModelSpringDataRepository.findAll());
    }

    /**
     * This method is used to find an ActuatorModel by its identity.
     *
     * @param actuatorModelName The identity of the ActuatorModel to find.
     * @return An Optional containing the found ActuatorModel, or an empty Optional if no ActuatorModel was found.
     * @throws IllegalArgumentException if actuatorModelName is null.
     */
    @Override
    public Optional<ActuatorModel> findByIdentity(ActuatorModelName actuatorModelName) {
        if (actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        Optional<ActuatorModelDataModel> actuatorModelDataModel =
                actuatorModelSpringDataRepository.findById(actuatorModelName.getActuatorModelName());
        if (actuatorModelDataModel.isPresent()) {
            return Optional.of(actuatorModelDataModelMapper.toActuatorModelDomain(actuatorModelDataModel.get()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * This method is used to check if an ActuatorModel with a certain identity exists.
     *
     * @param actuatorModelName The identity of the ActuatorModel to check for.
     * @return A boolean indicating whether an ActuatorModel with the specified identity exists.
     * @throws IllegalArgumentException if actuatorModelName is null.
     */
    @Override
    public boolean containsIdentity(ActuatorModelName actuatorModelName) {
        if (actuatorModelName == null) {
            throw new IllegalArgumentException();
        }
        return actuatorModelSpringDataRepository.existsById(actuatorModelName.getActuatorModelName());
    }

    /**
     * This method is used to find ActuatorModels by their ActuatorType identity.
     *
     * @param actuatorTypeName The identity of the ActuatorType to find ActuatorModels for.
     * @return An Iterable of ActuatorModels that have the specified ActuatorType identity.
     * @throws IllegalArgumentException if actuatorTypeName is null.
     */
    @Override
    public Iterable<ActuatorModel> findActuatorModelsByActuatorTypeName(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        List<ActuatorModelDataModel> actuatorModelDataModels =
                actuatorModelSpringDataRepository.findActuatorModelsByActuatorTypeName(actuatorTypeName.getActuatorTypeName());
        return actuatorModelDataModelMapper.toActuatorModelsDomain(actuatorModelDataModels);
    }

    /**
     * This method is used to find ActuatorModel names by their ActuatorType identity.
     *
     * @param actuatorTypeName The identity of the ActuatorType to find ActuatorModel names for.
     * @return An Iterable of ActuatorModel names that have the specified ActuatorType identity.
     * @throws IllegalArgumentException if actuatorTypeName is null.
     */
    @Override
    public Iterable<ActuatorModelName> findActuatorModelNamesByActuatorTypeName(ActuatorTypeName actuatorTypeName) {
        if (actuatorTypeName == null) {
            throw new IllegalArgumentException();
        }
        String actuatorTypeNameString = actuatorTypeName.getActuatorTypeName();
        List<String> actuatorModelNames = actuatorModelSpringDataRepository.findActuatorModelNamesByActuatorTypeName(actuatorTypeNameString);
        return actuatorModelNames.stream().map(ActuatorModelName::new).toList();
    }
}
