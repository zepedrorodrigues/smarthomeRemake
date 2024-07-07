package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;

/**
 * The ISensorTypeRepository interface represents a repository for managing SensorType entities.
 */
public interface ISensorTypeRepository extends IRepository<SensorTypeId, SensorType> {
    /**
     * Retrieves all SensorTypeIds from the repository.
     *
     * @return an Iterable of SensorTypeIds
     */
    Iterable<SensorTypeId> findSensorTypeIds();
}
