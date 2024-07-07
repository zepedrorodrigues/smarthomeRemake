package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.persistence.datamodel.ReadingDataModel;
import smarthome.persistence.datamodel.mapper.ReadingDataModelMapper;
import smarthome.persistence.spring.IReadingRepositorySpringData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Reading repository Spring Data implementation.
 * This class is responsible for managing Reading entities in the database using Spring Data.
 */
@Repository
public class ReadingRepositorySpringDataImpl implements IReadingRepository {


    private final ReadingDataModelMapper readingDataModelMapper;
    private final IReadingRepositorySpringData readingSpringDataRepository;

    /**
     * Instantiates a new Reading repository Spring Data implementation.
     *
     * @param readingModelMapper          The reading data model mapper.
     * @param readingSpringDataRepository The reading Spring Data repository.
     */
    public ReadingRepositorySpringDataImpl(
            ReadingDataModelMapper readingModelMapper, IReadingRepositorySpringData readingSpringDataRepository) {
        this.readingSpringDataRepository = readingSpringDataRepository;
        this.readingDataModelMapper = readingModelMapper;
    }

    /**
     * Save a Reading entity to the repository.
     *
     * @param reading The Reading entity to be saved.
     * @return The saved Reading entity.
     * @throws IllegalArgumentException if the Reading is null
     */
    @Override
    public Reading save(Reading reading) {
        if (reading == null) {
            throw new IllegalArgumentException();
        }
        ReadingDataModel readingDataModel = new ReadingDataModel(reading);
        readingSpringDataRepository.save(readingDataModel);
        return reading;
    }

    /**
     * Retrieve all Reading entities from the repository.
     *
     * @return An Iterable of Reading entities.
     * @throws IllegalArgumentException if the Reading is null
     */
    @Override
    public Iterable<Reading> findAll() {
        List<ReadingDataModel> readingDataModels = readingSpringDataRepository.findAll();
        return readingDataModelMapper.toReadingDomainModels(readingDataModels);
    }

    /**
     * Retrieve a Reading entity by its identity.
     *
     * @param id The Reading identity.
     * @return An Optional with the Reading entity if found, empty otherwise.
     * @throws IllegalArgumentException if the Reading identity is null
     */
    @Override
    public Optional<Reading> findByIdentity(ReadingId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        Optional<ReadingDataModel> readingDataModel = readingSpringDataRepository.findById(id.getId());
        return readingDataModel.map(readingDataModelMapper::toReadingDomainModel);
    }

    /**
     * Check if a Reading entity with a given identity exists in the repository.
     *
     * @param readingId The Reading identity.
     * @return true if a Reading entity with the given identity exists, false otherwise.
     * @throws IllegalArgumentException if the Reading identity is null
     */
    @Override
    public boolean containsIdentity(ReadingId readingId) {
        if (readingId == null) {
            throw new IllegalArgumentException();
        }
        return readingSpringDataRepository.existsById(readingId.getId());
    }

    /**
     * Find all Reading entities with a given Sensor identity in a given period.
     *
     * @param sensorId The Sensor identity.
     * @param start    The start of the period.
     * @param end      The end of the period.
     * @return An Iterable of Reading entities.
     */
    @Override
    public Iterable<Reading> findReadingsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start, TimeStamp end) {
        List<ReadingDataModel> readingDataModels =
                readingSpringDataRepository.findBySensorIdAndTimeStampBetween(sensorId.getSensorId(), start.getValue(), end.getValue());
        return readingDataModelMapper.toReadingDomainModels(readingDataModels);
    }

    /**
     * Find all Reading identities with a given Sensor identity in a given period.
     *
     * @param sensorId The Sensor identity.
     * @param start    The start of the period.
     * @param end      The end of the period.
     * @return An Iterable of Reading identities.
     */
    @Override
    public Iterable<ReadingId> findReadingIdsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start,
                                                                      TimeStamp end) {
        List<String> readingIds =
                readingSpringDataRepository.findIdsBySensorIdAndTimeStampBetween(sensorId.getSensorId(),
                        start.getValue(), end.getValue());
        List<ReadingId> readingIdList = new ArrayList<>();
        for (String readingId : readingIds) {
            ReadingId readingId1 = new ReadingId(readingId);
            readingIdList.add(readingId1);
        }
        return readingIdList;
    }

    /**
     * Get the latest Reading entity for a given Sensor identity.
     *
     * @param sensorId the sensor ID to search for.
     * @return an Optional of the latest Reading entity.
     */
    @Override
    public Optional<Reading> findLastReadingBySensorId(SensorId sensorId) {
        if (sensorId == null) {
            throw new IllegalArgumentException();
        }
        ReadingDataModel readingDataModel = readingSpringDataRepository
                .findTopBySensorIdOrderByTimeStampDesc(sensorId.getSensorId());
        return Optional.ofNullable(readingDataModelMapper.toReadingDomainModel(readingDataModel));
    }

}