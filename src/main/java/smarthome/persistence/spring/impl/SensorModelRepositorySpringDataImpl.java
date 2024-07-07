package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.persistence.datamodel.SensorModelDataModel;
import smarthome.persistence.datamodel.mapper.SensorModelDataModelMapper;
import smarthome.persistence.spring.ISensorModelRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * This class provides a concrete implementation of the ISensorModelRepository interface using Spring Data JPA for
 * database operations. It uses a SensorModelDataModelMapper to map between SensorModel objects and
 * SensorModelDataModel entities.
 */
@Repository
public class SensorModelRepositorySpringDataImpl implements ISensorModelRepository {

    private final SensorModelDataModelMapper sensorModelDataModelMapper;

    private final ISensorModelRepositorySpringData sensorModelSpringDataRepository;

    /**
     * Constructor for the SensorModelRepositorySpringDataImpl class.
     * This constructor initializes the SensorModelDataModelMapper and ISensorModelRepositorySpringData instances that will be
     * used by this repository.
     *
     * @param sensorModelDataModelMapper      An instance of SensorModelDataModelMapper.
     * @param sensorModelSpringDataRepository An instance of ISensorModelRepositorySpringData.
     */
    public SensorModelRepositorySpringDataImpl(SensorModelDataModelMapper sensorModelDataModelMapper,
                                               ISensorModelRepositorySpringData sensorModelSpringDataRepository) {
        this.sensorModelDataModelMapper = sensorModelDataModelMapper;
        this.sensorModelSpringDataRepository = sensorModelSpringDataRepository;
    }

    /**
     * This method saves a SensorModel object to the database.
     *
     * @param sensorModel The SensorModel object to be saved.
     * @return The saved SensorModel object.
     * @throws IllegalArgumentException if the SensorModel object is null.
     */
    @Override
    public SensorModel save(SensorModel sensorModel) {
        if (sensorModel == null || containsIdentity(sensorModel.getIdentity())) {
            throw new IllegalArgumentException();
        }
        SensorModelDataModel sensorModelDataModel = new SensorModelDataModel(sensorModel);
        sensorModelSpringDataRepository.save(sensorModelDataModel);
        return sensorModel;
    }

    /**
     * This method retrieves all SensorModel objects from the database.
     *
     * @return An Iterable of all SensorModel objects.
     */
    @Override
    public Iterable<SensorModel> findAll() {
        return sensorModelDataModelMapper.toSensorModelsDomain(sensorModelSpringDataRepository.findAll());
    }

    /**
     * This method retrieves a SensorModel object by its identity.
     *
     * @param sensorModelName The identity of the SensorModel object.
     * @return An Optional of SensorModel object if it exists, Optional.empty() otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public Optional<SensorModel> findByIdentity(SensorModelName sensorModelName) {
        if (sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        Optional<SensorModelDataModel> sensorModelDataModel =
                sensorModelSpringDataRepository.findById(sensorModelName.getSensorModelName());
        return sensorModelDataModel.map(sensorModelDataModelMapper::toSensorModelDomain);
    }

    /**
     * This method checks if a SensorModel object with the given identity exists in the database.
     *
     * @param sensorModelName The identity of the SensorModel object.
     * @return true if the SensorModel object exists, false otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(SensorModelName sensorModelName) {
        if (sensorModelName == null) {
            throw new IllegalArgumentException();
        }
        return sensorModelSpringDataRepository.existsById(sensorModelName.getSensorModelName());
    }

    /**
     * This method retrieves all SensorModel objects with a specific SensorTypeId from the database.
     *
     * @param sensorTypeId The SensorTypeId of the SensorModel objects to be retrieved.
     * @return An Iterable of SensorModel objects with the specified SensorTypeId.
     * @throws IllegalArgumentException if the SensorTypeId is null.
     */
    @Override
    public Iterable<SensorModel> findSensorModelsBySensorTypeId(SensorTypeId sensorTypeId) {

        if (sensorTypeId == null) {
            throw new IllegalArgumentException();
        }

        List<SensorModelDataModel> sensorModelDataModels =
                sensorModelSpringDataRepository.findSensorModelsBySensorTypeId(sensorTypeId.getSensorTypeId());
        return sensorModelDataModelMapper.toSensorModelsDomain(sensorModelDataModels);
    }

    /**
     * Retrieves a list of SensorModelName objects by sensor type ID.
     *
     * @param sensorTypeId The ID of the sensor type.
     * @return List of SensorModelName objects that match the given sensor type ID.
     * @throws IllegalArgumentException if the sensorTypeId is null.
     */
    @Override
    public List<SensorModelName> findSensorModelNamesBySensorTypeId(SensorTypeId sensorTypeId) {
        if (sensorTypeId == null) {
            throw new IllegalArgumentException();
        }

        String sensorTypeIdString = sensorTypeId.getSensorTypeId();
        List<String> sensorModelIds = sensorModelSpringDataRepository.findSensorModelNamesBySensorTypeId(sensorTypeIdString);
        return sensorModelIds.stream().map(SensorModelName::new).toList();
    }
}
