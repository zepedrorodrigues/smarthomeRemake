package smarthome.persistence.mem;

import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IReadingRepository;
import smarthome.domain.sensor.vo.SensorId;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

/**
 * Repository for readings.
 * <p>
 * This class is responsible for storing and retrieving readings.
 * This repository is backed by an in-memory HashMap data structure.
 * It is an in-memory implementation of the repository.
 * </p>
 */
public class ReadingRepositoryMemImpl implements IReadingRepository {
    private final HashMap<ReadingId, Reading> DATA = new HashMap<>();

    /**
     * Saves a reading to the repository.
     * <p>
     * The method first checks if the reading parameter is null and throws an IllegalArgumentException if it is.
     * It also checks if the reading's identity is already in the repository and throws an IllegalArgumentException if it is, not allowing for duplicate identities to be saved.
     * The method then saves the reading to the repository and returns the saved reading.
     * </p>
     *
     * @param reading the reading to save
     * @return the saved reading
     */
    @Override
    public Reading save(Reading reading) {
        if (reading == null || containsIdentity(reading.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DATA.put(reading.getIdentity(), reading);

        return reading;
    }

    /**
     * Finds all readings in the repository.
     * <p>
     * The method returns an iterable collection of all readings in the repository.
     * The method returns an empty collection if no readings are in the repository.
     * </p>
     *
     * @return all readings in the repository
     */
    @Override
    public Iterable<Reading> findAll() {
        return DATA.values();
    }

    /**
     * Finds a reading by its identity.
     * <p>
     * The method first checks if the id parameter is null and throws an IllegalArgumentException if it is.
     * The method returns an optional containing the reading with the given identity if it exists in the repository.
     * The method returns an empty optional if no such reading exists.
     * </p>
     *
     * @param id the identity of the reading to find
     * @return an optional containing the reading with the given identity if it exists in the repository
     */
    @Override
    public Optional<Reading> findByIdentity(ReadingId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        return Optional.ofNullable(DATA.get(id));
    }

    /**
     * Checks if a reading with the given identity exists in the repository.
     * <p>
     * The method first checks if the id parameter is null and throws an IllegalArgumentException if it is.
     * The method returns true if a reading with the given identity exists in the repository, and false otherwise.
     * </p>
     *
     * @param id the identity of the reading to check
     * @return true if a reading with the given identity exists in the repository, and false otherwise.
     */
    @Override
    public boolean containsIdentity(ReadingId id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return DATA.containsKey(id);
    }

    /**
     * Finds readings by sensor id in a given period.
     * <p>
     * The method first checks if the sensorId, start, or end parameters are null,
     * or if the start parameter is after the end parameter,
     * and throws an IllegalArgumentException if any of these conditions are met.
     * The method then filters the readings in the repository by the sensorId and the time period,
     * returning an iterable collection of readings that match the criteria.
     * The method returns an empty collection if no readings match the criteria.
     * The method does not return duplicate devices.
     * </p>
     *
     * @param sensorId the sensor id to filter by
     * @param start    the start of the time period
     * @param end      the end of the time period
     * @return an iterable collection of readings that match the criteria
     */
    @Override
    public Iterable<Reading> findReadingsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start, TimeStamp end) {
        return DATA.values().stream()
                .filter(reading -> reading.getSensorId().equals(sensorId))
                .filter(reading -> {
                    TimeStamp readingTimeStamp = reading.getTime();
                    return ((readingTimeStamp.getValue().isAfter(start.getValue()) || readingTimeStamp.getValue().isEqual(start.getValue())) &&
                            (readingTimeStamp.getValue().isBefore(end.getValue()) || readingTimeStamp.getValue().isEqual(end.getValue())));
                })
                .toList();
    }

    /**
     * Finds reading ids by sensor id in a given period.
     *
     * @param sensorId the sensor id to filter by
     * @param start    the start of the time period
     * @param end      the end of the time period
     * @return an iterable collection of reading ids that match the criteria
     */
    @Override
    public Iterable<ReadingId> findReadingIdsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start, TimeStamp end) {
        return DATA.values().stream()
                .filter(reading -> reading.getSensorId().equals(sensorId))
                .filter(reading -> {
                    TimeStamp readingTimeStamp = reading.getTime();
                    return ((readingTimeStamp.getValue().isAfter(start.getValue()) || readingTimeStamp.getValue().isEqual(start.getValue())) &&
                            (readingTimeStamp.getValue().isBefore(end.getValue()) || readingTimeStamp.getValue().isEqual(end.getValue())));
                })
                .map(Reading::getIdentity)
                .toList();
    }

    /**
     * Finds the latest reading by sensor id.
     *
     * @param sensorId the sensor id to filter by
     * @return an optional containing the latest reading with the given sensor id if it exists in the repository
     */
    @Override
    public Optional<Reading> findLastReadingBySensorId(SensorId sensorId) {
        Reading latestReading = DATA.values().stream()
                .filter(reading -> reading.getSensorId().getSensorId().equals(sensorId.getSensorId()))
                .max(Comparator.comparing(reading -> reading.getTime().getValue()))
                .orElse(null);
        return Optional.ofNullable(latestReading);
    }
}
