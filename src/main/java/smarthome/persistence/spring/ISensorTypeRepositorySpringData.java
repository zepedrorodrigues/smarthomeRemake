package smarthome.persistence.spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import smarthome.persistence.datamodel.SensorTypeDataModel;

import java.util.List;

/**
 * Spring Data repository for SensorTypeDataModel.
 * It extends JpaRepository and provides operations to access data in a relational database.
 */
public interface ISensorTypeRepositorySpringData extends JpaRepository<SensorTypeDataModel, String> {

    /**
     * Fetches all sensor type IDs from the repository.
     *
     * @return a List of SensorTypeId objects.
     */
    @Query("SELECT s.sensorTypeId FROM SensorTypeDataModel s")
    List<String> findSensorTypeIds();

}
