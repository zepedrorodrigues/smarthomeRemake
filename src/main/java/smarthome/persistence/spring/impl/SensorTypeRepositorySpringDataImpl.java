package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.persistence.datamodel.SensorTypeDataModel;
import smarthome.persistence.datamodel.mapper.SensorTypeDataModelMapper;
import smarthome.persistence.spring.ISensorTypeRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the ISensorTypeRepository interface and provides Spring Data-based persistence operations.
 */
@Repository
public class SensorTypeRepositorySpringDataImpl implements ISensorTypeRepository {

    private final SensorTypeDataModelMapper sensorTypeDataModelMapper;
    private final ISensorTypeRepositorySpringData sensorTypeSpringDataRepository;

    /**
     * Constructor for the SensorTypeRepositorySpringDataImpl class.
     *
     * @param sensorTypeDataModelMapper      The SensorTypeDataModelMapper to convert between the SensorType domain
     *                                       model
     *                                       and the SensorTypeDataModel persistence model.
     * @param sensorTypeSpringDataRepository The Spring Data repository for SensorTypeDataModel.
     */
    public SensorTypeRepositorySpringDataImpl(SensorTypeDataModelMapper sensorTypeDataModelMapper,
                                              ISensorTypeRepositorySpringData sensorTypeSpringDataRepository) {
        this.sensorTypeDataModelMapper = sensorTypeDataModelMapper;
        this.sensorTypeSpringDataRepository = sensorTypeSpringDataRepository;

    }

    /**
     * Save a SensorType to the database.
     *
     * @param sensorType The SensorType to save.
     * @return The saved SensorType.
     * @throws IllegalArgumentException if the SensorType is null.
     */
    @Override
    public SensorType save(SensorType sensorType) {
        if (sensorType == null) {
            throw new IllegalArgumentException();
        }
        SensorTypeDataModel sensorTypeDataModel = new SensorTypeDataModel(sensorType);
        sensorTypeSpringDataRepository.save(sensorTypeDataModel);
        return sensorType;
    }

    /**
     * Retrieve all SensorTypes from the database.
     *
     * @return An Iterable of all SensorTypes.
     */
    @Override
    public Iterable<SensorType> findAll() {
        List<SensorTypeDataModel> sensorTypeDataModels = sensorTypeSpringDataRepository.findAll();
        return sensorTypeDataModelMapper.toSensorTypesDomain(sensorTypeDataModels);
    }

    /**
     * Retrieve a SensorType by its identity.
     *
     * @param id The identity of the SensorType to retrieve.
     * @return An Optional containing the SensorType if found, or empty if not found.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public Optional<SensorType> findByIdentity(SensorTypeId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        Optional<SensorTypeDataModel> sensorTypeDataModel =
                sensorTypeSpringDataRepository.findById(id.getSensorTypeId());
        if (!sensorTypeDataModel.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(sensorTypeDataModelMapper.toSensorTypeDomain(sensorTypeDataModel.get()));
    }

    /**
     * Check if a SensorType with the provided identity exists in the repository.
     *
     * @param id The identity of the SensorType to check.
     * @return true if the SensorType exists, false otherwise.
     * @throws IllegalArgumentException if the identity is null.
     */
    @Override
    public boolean containsIdentity(SensorTypeId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return sensorTypeSpringDataRepository.existsById(id.getSensorTypeId());
    }

    /**
     * Fetches all sensor type IDs from the repository.
     *
     * @return an Iterable of SensorTypeId objects.
     */
    @Override
    public Iterable<SensorTypeId> findSensorTypeIds() {
        List<String> sensorTypeIds = sensorTypeSpringDataRepository.findSensorTypeIds();
        return sensorTypeIds.stream().map(SensorTypeId::new).toList();
    }
}
