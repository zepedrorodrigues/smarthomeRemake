package smarthome.service;

import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;

import java.util.List;
import java.util.Optional;

public interface ISensorModelService {

    /**
     * Retrieves a list of SensorModelName objects associated with a specific SensorTypeId.
     *
     * @param sensorTypeId The identity of the SensorType for which to retrieve associated SensorModels.
     * @return List of SensorModelName objects associated with the provided SensorTypeId.
     */
    List<SensorModelName> getSensorModelsBySensorTypeIdentity(SensorTypeId sensorTypeId);

    /**
     * Retrieves a SensorModel object associated with a specific SensorModelName.
     *
     * @param sensorModelName The name of the SensorModel to retrieve.
     * @return Optional of SensorModel associated with the provided SensorModelName.
     */
    Optional<SensorModel> getSensorModelByName(SensorModelName sensorModelName);


}
