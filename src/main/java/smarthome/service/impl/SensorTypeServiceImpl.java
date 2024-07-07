package smarthome.service.impl;

import org.springframework.stereotype.Service;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.service.ISensorTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SensorTypeServiceImpl implements ISensorTypeService {

    private final ISensorTypeRepository sensorTypeRepository;

    /**
     * Constructor for SensorTypeServiceImpl.
     *
     * @param sensorTypeRepository The repository to be used for sensor type operations.
     */
    public SensorTypeServiceImpl(ISensorTypeRepository sensorTypeRepository) {
        this.sensorTypeRepository = sensorTypeRepository;
    }

    /**
     * Method to get all SensorTypeIds.
     *
     * @return List of SensorTypeId objects.
     */
    @Override
    public List<SensorTypeId> getSensorTypesIds() {
        Iterable<SensorTypeId> sensorTypes = sensorTypeRepository.findSensorTypeIds();
        List<SensorTypeId> sensorTypeIds = new ArrayList<>();

        for (SensorTypeId sensorType : sensorTypes) {
            sensorTypeIds.add(sensorType);
        }
        return sensorTypeIds;
    }

    /**
     * Method to get a SensorType by its ID.
     *
     * @param id The ID of the SensorType to retrieve.
     * @return Optional of SensorType.
     */
    @Override
    public Optional<SensorType> getByIdentity(SensorTypeId id) {

        return sensorTypeRepository.findByIdentity(id);
    }
}

