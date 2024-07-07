package smarthome.controller;

import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.SensorTypeIdDTO;
import smarthome.mapper.mapper.SensorMapper;
import smarthome.mapper.mapper.SensorModelMapper;
import smarthome.mapper.mapper.SensorTypeMapper;
import smarthome.service.ISensorModelService;
import smarthome.service.ISensorService;
import smarthome.service.ISensorTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller class responsible for adding sensors to devices.
 * It uses various repositories and mapper to perform its operations.
 */
public class AddSensorToDeviceController {

    private final ISensorService sensorService;

    private final ISensorTypeService sensorTypeService;

    private final ISensorModelService sensorModelService;

    private final SensorTypeMapper sensorTypeMapper;

    private final SensorModelMapper sensorModelMapper;

    private final SensorMapper sensorMapper;

    /**
     * Constructs a new AddSensorToDeviceController with the provided sensor service and mappers.
     *
     * @param sensorService the sensor service to be used by this controller.
     * @param sensorTypeMapper the mapper to convert between SensorType and SensorTypeDTO.
     * @param sensorModelMapper the mapper to convert between SensorModel and SensorModelDTO.
     * @param sensorMapper the mapper to convert between Sensor and SensorDTO.
     * @throws IllegalArgumentException if sensorService, sensorTypeMapper, sensorModelMapper, or sensorMapper is null.
     */
    public AddSensorToDeviceController(ISensorService sensorService, ISensorTypeService sensorTypeService,
                                       ISensorModelService sensorModelService, SensorTypeMapper sensorTypeMapper,
                                       SensorModelMapper sensorModelMapper, SensorMapper sensorMapper) {
        if (sensorService == null) {
            throw new IllegalArgumentException();
        }
        this.sensorService = sensorService;
        this.sensorTypeService = sensorTypeService;
        this.sensorModelService = sensorModelService;
        this.sensorTypeMapper = sensorTypeMapper;
        this.sensorModelMapper = sensorModelMapper;
        this.sensorMapper = sensorMapper;

    }

    /**
     * Retrieves a list of all sensor types.
     *
     * @return an Iterable of SensorType representing all sensor types.
     */
    public List<SensorTypeIdDTO> getSensorTypes() {
        List<SensorTypeId> sensorTypeIds = sensorTypeService.getSensorTypesIds();
        return sensorTypeMapper.toSensorTypesIdDTO(sensorTypeIds);
    }

    /**
     * Retrieves a list of all sensor models for a given sensor type.
     *
     * @param sensorTypeDTO the sensor type to retrieve sensor models for.
     * @return a List of SensorModelDTO representing all sensor models for the given sensor type.
     * @throws IllegalArgumentException if sensorTypeDTO is null.
     */
    public List<SensorModelDTO> getSensorModelsBySensorTypeIdentity(SensorTypeDTO sensorTypeDTO) {
        if (sensorTypeDTO == null) {
            throw new IllegalArgumentException();
        }
        SensorTypeId sensorTypeId = sensorTypeMapper.toSensorTypeId(sensorTypeDTO);
        Iterable<SensorModelName> sensorModelNames = sensorModelService.getSensorModelsBySensorTypeIdentity(sensorTypeId);

        List<SensorModelDTO> sensorModelDTOs = new ArrayList<>();
        for (SensorModelName sensorModelName : sensorModelNames) {
            Optional<SensorModel> sensorModelOptional = sensorModelService.getSensorModelByName(sensorModelName);
            if (sensorModelOptional.isPresent()) {
                SensorModelDTO sensorModelDTO = sensorModelMapper.toSensorModelDto(sensorModelOptional.get());
                sensorModelDTOs.add(sensorModelDTO);
            }
        }
        return sensorModelDTOs;
    }

    /**
     * Adds a sensor to a device.
     *
     * @param sensorDTO the sensor to add.
     * @return a SensorDTO representing the added sensor, or null if the sensor could not be added.
     */
    public SensorDTO addSensor(SensorDTO sensorDTO) {
        if (sensorDTO == null) {
            return null;
        }
        try {
            DeviceId deviceId = sensorMapper.toDeviceId(sensorDTO);
            SensorModelName sensorModelName = sensorMapper.toSensorModelName(sensorDTO);
            Sensor savedSensor = sensorService.addSensor(sensorModelName, deviceId);
            if (savedSensor != null) {
                return sensorMapper.toSensorDTO(savedSensor);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }
}
