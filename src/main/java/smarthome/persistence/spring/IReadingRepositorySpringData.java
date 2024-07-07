package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smarthome.persistence.datamodel.ReadingDataModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Reading repository for Spring Data JPA.
 * The ReadingDataModel is the entity that this repository works with.
 * The String is the type of the primary key of the ReadingDataModel entity.
 */
public interface IReadingRepositorySpringData extends JpaRepository<ReadingDataModel, String> {

    /**
     * Find all readings by sensor ID and timestamp in a given period (includes start time and end time).
     *
     * @param sensorId the sensor ID to search for.
     * @param start    the start time.
     * @param end      the end time.
     * @return a list of readings that match the search criteria.
     */
    List<ReadingDataModel> findBySensorIdAndTimeStampBetween(String sensorId, LocalDateTime start, LocalDateTime end);


    /**
     * Find all reading IDs by sensor ID and timestamp between start and end (inclusive).
     *
     * @param sensorId the sensor ID to search for.
     * @param start   the start timestamp.
     * @param end    the end timestamp.
     * @return a list of reading IDs that match the search criteria.
     */
    @Query("SELECT r.readingId FROM ReadingDataModel r WHERE r.sensorId = :sensorId AND r.timeStamp BETWEEN :start " +
            "AND " + ":end")
    List<String> findIdsBySensorIdAndTimeStampBetween(@Param("sensorId") String sensorId,
                                                      @Param("start") LocalDateTime start,
                                                      @Param("end") LocalDateTime end);

    /**
     * Find the latest reading for a sensor.
     *
     * @param sensorId the sensor ID to search for.
     * @return the latest reading for the sensor.
     */
    ReadingDataModel findTopBySensorIdOrderByTimeStampDesc(String sensorId);


}