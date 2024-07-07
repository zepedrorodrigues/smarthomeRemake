package smarthome.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.vo.SensorId;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.mapper.SensorDTO;
import smarthome.mapper.SensorIdDTO;
import smarthome.mapper.mapper.SensorMapper;
import smarthome.service.ISensorService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * This class provides the REST controller for sensors.
 * It is annotated with @RestController to indicate that it's a RESTful web service controller.
 * The @RequestMapping("/sensors") annotation is used to map web requests to the SensorRESTController class.
 */
@RestController
@RequestMapping("/sensors")
public class SensorRESTController {

    private final ISensorService sensorService;

    private final SensorMapper sensorMapper;

    /**
     * Constructor for SensorRESTController.
     *
     * @param sensorService     the service that provides the RESTful web service for sensors.
     * @param sensorMapper      the mapper for Sensor entities and DTOs.
     */
    @Autowired
    public SensorRESTController(ISensorService sensorService, SensorMapper sensorMapper) {
        this.sensorService = sensorService;
        this.sensorMapper = sensorMapper;

    }

    /**
     * Handles the GET request to retrieve a sensor by its identity.
     *
     * @param sensorId the identity of the sensor to be retrieved.
     * @return a ResponseEntity containing an EntityModel of the SensorDTO if the sensor is found,
     *         or a ResponseEntity with an HTTP status code of NOT_FOUND if the sensor is not found.
     */
    @GetMapping(value = "/{sensorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EntityModel<SensorDTO>> getSensorByIdentity(@PathVariable("sensorId") String sensorId) {
        SensorId sensorIdVO = new SensorId(sensorId);
        Optional<Sensor> sensorOptional = sensorService.getByIdentity(sensorIdVO);
        if (sensorOptional.isPresent()) {
            Sensor sensor = sensorOptional.get();
            SensorDTO sensorDTO = sensorMapper.toSensorDTO(sensor);

            Link link = linkTo(methodOn(SensorRESTController.class).getSensorByIdentity(sensorId)).withSelfRel();

            EntityModel<SensorDTO> sensorModel = EntityModel.of(sensorDTO, link);
            return new ResponseEntity<>(sensorModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Adds a new sensor with the specified sensor model name and device id.
     *
     * @param sensorDTO the DTO of the sensor to be added.
     * @return a ResponseEntity containing the created SensorDTO object and an HTTP status code.
     */
    @PostMapping("/device/{deviceId}")
    public ResponseEntity<EntityModel<SensorDTO>> addSensor(@PathVariable("deviceId") String deviceId, @RequestBody SensorDTO sensorDTO) {
        try {
            DeviceId deviceIdVO = new DeviceId(deviceId);
            SensorModelName sensorModelNameVO = sensorMapper.toSensorModelName(sensorDTO);
            Sensor savedSensor = sensorService.addSensor(sensorModelNameVO, deviceIdVO);
            if (savedSensor != null) {
                SensorDTO sensor = sensorMapper.toSensorDTO(savedSensor);
                String sensorId = sensor.getSensorId();
                Link selfLink = linkTo(methodOn(SensorRESTController.class).getSensorByIdentity(sensorId))
                        .withSelfRel();
                EntityModel<SensorDTO> sensorModel = EntityModel.of(sensor, selfLink);
                return new ResponseEntity<>(sensorModel, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Handles the GET request to retrieve all sensor ids associated with a specific device.
     *
     * @param id The identity of the device whose associated sensor ids are to be retrieved.
     * @return A ResponseEntity containing a list of SensorIdDTOs if the sensors are found,
     * or a ResponseEntity with an HTTP status code of NOT_FOUND if no sensors are found,
     * BAD_REQUEST if the provided sensor ids are null, or UNPROCESSABLE_ENTITY if an exception occurs.
     */
    @GetMapping("/device/{id}")
    public ResponseEntity<List<SensorIdDTO>> getSensorByDeviceIdentity(@PathVariable("id") String id) {
        try {
            DeviceId deviceId = new DeviceId(id);
            Iterable<SensorId> sensorIds = sensorService.getSensorIdsByDeviceIdentity(deviceId);
            if (sensorIds == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (!sensorIds.iterator().hasNext()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<SensorIdDTO> sensorIdDTOs = sensorMapper.toSensorIdsDTO(sensorIds);
            for (SensorIdDTO sensorIdDTO : sensorIdDTOs) {
                sensorIdDTO.add(linkTo(methodOn(SensorRESTController.class).getSensorByIdentity(sensorIdDTO.getSensorId())).withSelfRel());
            }
            return new ResponseEntity<>(sensorIdDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
