package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

/**
 * The ISensorModelRepository interface provides methods to interact with the SensorModel data.
 */
public interface ISensorModelRepository extends IRepository<SensorModelName, SensorModel> {

    /**
     * Find all SensorModel entities in the repository by sensor type identity.
     * <p>
     * @param sensorTypeId The identity of the sensor type.
     * @return An Iterable of all SensorModel entities.
     */
    Iterable<SensorModel> findSensorModelsBySensorTypeId(SensorTypeId sensorTypeId);

    /**
     * Retrieves all SensorModelName entities associated with a given sensor type ID.
     * <p>
     * @param sensorTypeId The ID of the sensor type.
     * @return An Iterable of SensorModelName entities.
     */
    Iterable<SensorModelName> findSensorModelNamesBySensorTypeId(SensorTypeId sensorTypeId);
}
