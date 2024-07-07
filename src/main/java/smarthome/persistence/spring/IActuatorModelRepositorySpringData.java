package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smarthome.persistence.datamodel.ActuatorModelDataModel;

import java.util.List;

/**
 * This is a Spring Data JPA repository for ActuatorModelDataModel entities.
 * It extends JpaRepository which provides JPA related methods like save(), findOne(), findAll(), count(), delete() etc.
 * You can use it to perform database operations on ActuatorModelDataModel entities.
 * <p> ActuatorModelDataModel is the entity that this repository works with.
 * String is the type of the primary key of the ActuatorModelDataModel entity.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface IActuatorModelRepositorySpringData extends JpaRepository<ActuatorModelDataModel, String> {

    /**
     * This method is used to find ActuatorModelDataModel entities by their ActuatorTypeName.
     * <p>
     * @param actuatorTypeName The name of the ActuatorType to find ActuatorModelDataModel entities for.
     * @return A List of ActuatorModelDataModel entities that have the specified ActuatorTypeName.
     */
    List<ActuatorModelDataModel> findActuatorModelsByActuatorTypeName(String actuatorTypeName);

    /**
     * This method is used to find ActuatorModelDataModel entities by their ActuatorTypeName.
     * It uses a custom query to find ActuatorModelDataModel entities by their ActuatorTypeName.
     * The query is written in JPQL (Java Persistence Query Language).
     * The query selects the actuatorModelName attribute of ActuatorModelDataModel entities that have the specified ActuatorTypeName.
     * The query is executed by Spring Data JPA.
     * <p>
     * @param actuatorTypeName The name of the ActuatorType to find ActuatorModelDataModel entities for.
     * @return A List of ActuatorModelDataModel entities that have the specified ActuatorTypeName.
     */
    @Query("SELECT s.actuatorModelName FROM ActuatorModelDataModel s WHERE s.actuatorTypeName = :actuatorTypeName")
    List<String> findActuatorModelNamesByActuatorTypeName(@Param("actuatorTypeName") String actuatorTypeName);
}
