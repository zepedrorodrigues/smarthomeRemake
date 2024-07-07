package smarthome.service.impl;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.mapper.SensorMapper;
import smarthome.mapper.mapper.SensorModelMapper;
import smarthome.mapper.mapper.SensorTypeMapper;
import smarthome.service.ISensorService;

import java.util.List;

/**
 * Service class responsible for managing sensors.
 * It uses various repositories and mapper to perform its operations.
 */
public class SensorServiceImpl implements ISensorService {

    private final SensorTypeMapper sensorTypeMapper;

    private final ISensorTypeRepository sensorTypeRepository;

    private final SensorModelMapper sensorModelMapper;

    private final ISensorModelRepository sensorModelRepository;

    private final ISensorRepository sensorRepository;

    private final SensorFactory sensorFactory;

    private final SensorMapper sensorMapper;

    private final IDeviceRepository deviceRepository;


    /**
     * Constructor for the SensorServiceImpl class.
     *
     * @param sensorTypeMapper
     * @param sensorTypeRepository
     * @param sensorModelMapper
     * @param sensorModelRepository
     * @param sensorRepository
     * @param sensorFactory
     * @param sensorMapper
     */
    public SensorServiceImpl(SensorTypeMapper sensorTypeMapper, ISensorTypeRepository sensorTypeRepository, SensorModelMapper sensorModelMapper, ISensorModelRepository sensorModelRepository, ISensorRepository sensorRepository, SensorFactory sensorFactory, SensorMapper sensorMapper, IDeviceRepository deviceRepository) {
        if (sensorTypeMapper == null || sensorTypeRepository == null || sensorModelMapper == null || sensorModelRepository == null || sensorRepository == null || sensorFactory == null || sensorMapper == null || deviceRepository == null) {
            throw new IllegalArgumentException();
        }
        this.sensorTypeMapper = sensorTypeMapper;
        this.sensorTypeRepository = sensorTypeRepository;
        this.sensorModelMapper = sensorModelMapper;
        this.sensorModelRepository = sensorModelRepository;
        this.sensorRepository = sensorRepository;
        this.sensorFactory = sensorFactory;
        this.sensorMapper = sensorMapper;
        this.deviceRepository = deviceRepository;
    }


    /**
     * Returns a list of all available sensor types.
     * The list is obtained by converting all sensor types retrieved from the repository to SensorTypeDTOs.
     *
     * @return a list of SensorTypeDTOs representing all available sensor types.
     */
    public List<SensorTypeDTO> getSensorTypes() {
        return sensorTypeMapper.toSensorTypesDTO(sensorTypeRepository.findAll());
    }


    /**
     * Returns a list of all sensor models for a given sensor type.
     * The list is obtained by converting all sensor models retrieved from the repository to SensorModelDTOs.
     *
     * @param sensorTypeDTO the sensor type for which to retrieve the models.
     * @return a list of SensorModelDTOs representing all sensor models for the provided sensor type.
     */
    public List<SensorModelDTO> getSensorModelsBySensorTypeIdentity(SensorTypeDTO sensorTypeDTO) {
        try {
            SensorTypeId sensorTypeId = sensorTypeMapper.toSensorTypeId(sensorTypeDTO);
            Iterable<SensorModel> sensorModels = sensorModelRepository.findBySensorTypeIdentity(sensorTypeId);
            return sensorModelMapper.toSensorModelsDto(sensorModels);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Adds a sensor to a device.
     * The sensor is created using the sensor factory and then saved in the sensor repository.
     *
     * @param sensorDTO the sensor to be added.
     * @return a SensorDTO representing the added sensor, or null if the sensor could not be added.
     */
    public SensorDTO addSensor(SensorDTO sensorDTO) {
        try {
            DeviceId deviceId = sensorMapper.toDeviceId(sensorDTO);
            if (!deviceRepository.containsIdentity(deviceId)) {
                return null;
            }

            SensorModelName sensorModelName = sensorMapper.toSensorModelName(sensorDTO);
            Sensor sensor = sensorFactory.createSensor(sensorModelName, deviceId);
            Sensor savedSensor = sensorRepository.save(sensor);

            return sensorMapper.toSensorDTO(savedSensor);
        } catch (Exception e) {
            return null;
        }
    }
}
