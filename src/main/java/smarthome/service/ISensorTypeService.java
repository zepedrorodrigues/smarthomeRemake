package smarthome.service;

import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorTypeDTO;

import java.util.List;
import java.util.Optional;

public interface ISensorTypeService {
    /**
     * Retrieves a list of all SensorTypeIds.
     *
     * @return List of SensorTypeId objects.
     */
    List<SensorTypeId> getSensorTypesIds();

    /**
     * Retrieves a SensorType by its ID.
     *
     * @param id The ID of the SensorType to retrieve.
     * @return Optional of SensorType.
     */
    Optional<SensorType> getByIdentity(SensorTypeId id);
}
