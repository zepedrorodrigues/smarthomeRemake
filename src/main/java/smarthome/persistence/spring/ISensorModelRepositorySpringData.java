package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smarthome.persistence.datamodel.SensorModelDataModel;

import java.util.List;

/**
 * This interface extends JpaRepository and provides CRUD operations for SensorModelDataModel. It is used by Spring
 * Data JPA to access data in a relational database.
 * <p>
 * SensorModelDataModel is the entity that this repository works with.
 * String is the type of the primary key of the SensorModelDataModel entity.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface ISensorModelRepositorySpringData extends JpaRepository<SensorModelDataModel, String> {

    /**
     * This method retrieves all SensorModelDataModel entities associated with a specific SensorTypeId from the
     * database.
     * It takes the identity of a SensorTypeId as a parameter and returns a list of SensorModelDataModel
     * entities associated with that SensorTypeId.
     * The identity is a string representation of the SensorTypeId.
     *
     * @param sensorTypeId The identity of the SensorTypeId.
     * @return A list of SensorModelDataModel entities associated with the provided SensorTypeId.
     */
    List<SensorModelDataModel> findSensorModelsBySensorTypeId(String sensorTypeId);

    /**
     * This method retrieves all SensorModelDataModel entities associated with a specific SensorTypeId from the
     * database. It uses a custom query to select the sensorModelName from the SensorModelDataModel entities
     * where the sensorTypeId matches the provided parameter.
     *
     * @param sensorTypeId The identity of the SensorTypeId.
     * @return A list of sensor model names associated with the provided SensorTypeId.
     */
    @Query("SELECT s.sensorModelName FROM SensorModelDataModel s WHERE s.sensorTypeId = :sensorTypeId")
    List<String> findSensorModelNamesBySensorTypeId(@Param("sensorTypeId") String sensorTypeId);
}
