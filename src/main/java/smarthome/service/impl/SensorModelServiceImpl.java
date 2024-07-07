package smarthome.service.impl;

import org.springframework.stereotype.Service;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.service.ISensorModelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for SensorModel related operations.
 */
@Service
public class SensorModelServiceImpl implements ISensorModelService {

    private final ISensorModelRepository sensorModelRepository;

    /**
     * Constructor for SensorModelServiceImpl.
     *
     * @param sensorModelRepository The repository to be used for sensor model operations.
     */
public SensorModelServiceImpl(ISensorModelRepository sensorModelRepository) {
        this.sensorModelRepository = sensorModelRepository;
    }

    /**
     * Retrieves a list of SensorModelName objects associated with a specific SensorTypeId.
     *
     * @param sensorTypeId The identity of the SensorType for which to retrieve associated SensorModels.
     * @return List of SensorModelName objects associated with the provided SensorTypeId.
     */
    @Override
    public List<SensorModelName> getSensorModelsBySensorTypeIdentity(SensorTypeId sensorTypeId) {
        Iterable<SensorModelName> iterableSensorModelsName =
                sensorModelRepository.findSensorModelNamesBySensorTypeId(sensorTypeId);

        List<SensorModelName> sensorModelNames = new ArrayList<>();

        for(SensorModelName sensorModelName : iterableSensorModelsName){
            sensorModelNames.add(sensorModelName);
        }
        return sensorModelNames;
    }

    /**
     * Retrieves a SensorModel object associated with a specific SensorModelName.
     *
     * @param sensorModelName The name of the SensorModel to retrieve.
     * @return Optional of SensorModel associated with the provided SensorModelName.
     */
    @Override
    public Optional<SensorModel> getSensorModelByName(SensorModelName sensorModelName) {
        return sensorModelRepository.findByIdentity(sensorModelName);
    }
}
