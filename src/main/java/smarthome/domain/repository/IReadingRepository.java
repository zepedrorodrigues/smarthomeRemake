package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.ReadingId;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.sensor.vo.SensorId;

import java.util.Optional;

/**
 * The repository for readings of devices.
 */
public interface IReadingRepository extends IRepository<ReadingId, Reading> {

    /**
     * Finds all readings for a specific sensor in a given period.
     * <p>
     * @param sensorId the identity of the sensor.
     * @param start    the start of the period.
     * @param end      the end of the period.
     * @return all readings for the sensor in the given period.
     */
    Iterable<Reading> findReadingsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start, TimeStamp end);

    /**
     * Finds all reading identities for a specific sensor in a given period.
     * <p>
     * @param sensorId the identity of the sensor.
     * @param start    the start of the period.
     * @param end      the end of the period.
     * @return all reading identities for the sensor in the given period.
     */
    Iterable<ReadingId> findReadingIdsBySensorIdInAGivenPeriod(SensorId sensorId, TimeStamp start, TimeStamp end);

    /**
     * Finds the latest reading for a sensor.
     * <p>
     * @param sensorId the sensor ID to search for.
     * @return the latest reading for the sensor.
     */
    Optional<Reading> findLastReadingBySensorId(SensorId sensorId);

}
