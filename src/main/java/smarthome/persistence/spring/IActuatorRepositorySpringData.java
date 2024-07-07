package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smarthome.persistence.datamodel.ActuatorDataModel;

import java.util.List;

/**
 * Actuator repository using Spring Data
 * <p>
 * This interface extends the Spring Data JPA repository interface and adds a custom query to find all actuators ids by device id.
 * <p>
 */
public interface IActuatorRepositorySpringData extends JpaRepository<ActuatorDataModel, String> {

    /**
     * Finds all actuators ids by device id.
     * <p>
     * It uses a custom query to find all actuators ids by device id.
     * The custom query is implemented using Spring Data query annotation and the JPQL query language.
     * The query is defined as a String parameter to the @Query annotation, and the parameters are defined using the @Param annotation.
     * The query uses the @Param annotation to bind the deviceId parameter to the query parameter.
     * <p>
     * The query returns a list of strings containing the actuator ids.
     * The method signature is used to define the query.
     * <p>
     * @param deviceId the device id to search for
     * @return the actuators ids associated with the device id
     */
    @Query("SELECT a.actuatorId FROM ActuatorDataModel a WHERE a.deviceId = :deviceId")
    List<String> findActuatorIdsByDeviceId(@Param("deviceId") String deviceId);
}
