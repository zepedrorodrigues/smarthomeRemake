package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smarthome.persistence.datamodel.ActuatorTypeDataModel;

import java.util.List;

/**
 * This interface extends JpaRepository and provides CRUD operations for ActuatorTypeDataModel. It is used by Spring
 * Data JPA to access data in a relational database.
 * <p>
 * This interface is used to retrieve all actuator type ids from the database.
 * </p>
 */
public interface IActuatorTypeRepositorySpringData extends JpaRepository<ActuatorTypeDataModel, String> {

    /**
     * Retrieve all actuator type ids from the database.
     *
     * @return A list of all actuator type ids.
     */
    @Query("SELECT r.actuatorTypeName FROM ActuatorTypeDataModel r")
    List<String> findActuatorTypeNames();

}