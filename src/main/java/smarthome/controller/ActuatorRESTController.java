package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.actuator.Actuator;
import smarthome.domain.actuator.vo.ActuatorId;
import smarthome.domain.actuator.vo.ActuatorMap;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.sensor.vo.values.ScalePercentageValue;
import smarthome.domain.sensor.vo.values.Value;
import smarthome.mapper.ActuatorDTO;
import smarthome.mapper.ActuatorIdDTO;
import smarthome.mapper.ValueDTO;
import smarthome.mapper.mapper.ActuatorMapper;
import smarthome.mapper.mapper.ValueMapper;
import smarthome.service.IActuatorService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing actuators.
 */
@RestController
@RequestMapping("/actuators")
public class ActuatorRESTController {

    private final IActuatorService actuatorService;
    private final ActuatorMapper actuatorMapper;
    private final ValueMapper valueMapper;

    /**
     * Constructor for the ActuatorRESTController.
     *
     * @param actuatorService The service for managing actuators.
     * @param actuatorMapper  The mapper for converting between Actuator and ActuatorDTO.
     * @param valueMapper     The mapper for converting between Value and ValueDTO.
     */
    @Autowired
    public ActuatorRESTController(IActuatorService actuatorService, ActuatorMapper actuatorMapper, ValueMapper valueMapper) {
        this.actuatorService = actuatorService;
        this.actuatorMapper = actuatorMapper;
        this.valueMapper = valueMapper;
    }

    /**
     * Returns the actuator with the given id.
     *
     * @param actuatorId The id of the actuator to get.
     * @return The actuator with the given id.
     */

    @GetMapping("/{actuatorId}")
    public ResponseEntity<EntityModel<ActuatorDTO>> getActuatorByIdentity(@PathVariable("actuatorId") String actuatorId) {
        ActuatorId actuatorIdVO = new ActuatorId(actuatorId);
        Optional<Actuator> actuatorOptional = actuatorService.getActuatorById(actuatorIdVO);
        if (actuatorOptional.isPresent()) {
            Actuator actuator = actuatorOptional.get();
            ActuatorDTO actuatorDTO = actuatorMapper.actuatorToDTO(actuator);
            Link link = linkTo(methodOn(ActuatorRESTController.class).getActuatorByIdentity(actuatorId)).withSelfRel();
            EntityModel<ActuatorDTO> actuatorModel = EntityModel.of(actuatorDTO, link);
            return new ResponseEntity<>(actuatorModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Adds a new actuator to a device.
     *
     * @param actuatorDTO the actuator to be created data
     * @return the response entity with the newly added actuator data
     */
    @PostMapping("/device/{deviceId}")
    public ResponseEntity<ActuatorDTO> addActuator(@PathVariable("deviceId") String deviceId,
                                                   @RequestBody ActuatorDTO actuatorDTO) {
        if (actuatorDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DeviceId deviceIdVo = new DeviceId(deviceId);
        // Map Value Objects
        ActuatorMap actuatorMap = actuatorMapper.toActuatorMap(actuatorDTO);
        // Create and Save Actuator
        Actuator savedActuator = actuatorService.addActuator(actuatorMap, deviceIdVo);
        if (savedActuator != null) {
            ActuatorDTO savedActuatorDTO = actuatorMapper.actuatorToDTO(savedActuator);
            savedActuatorDTO.add(linkTo(methodOn(ActuatorRESTController.class).getActuatorByIdentity(savedActuatorDTO.getActuatorId())).withSelfRel());
            return new ResponseEntity<>(actuatorMapper.actuatorToDTO(savedActuator), HttpStatus.CREATED);
        } else {
            //Unable to Create and/or Save Actuator
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Get actuators by device identity.
     *
     * @param id the device id
     * @return the response entity with the list of actuator ids
     */
    @GetMapping("/device/{id}")
    public ResponseEntity<List<ActuatorIdDTO>> getByDeviceIdentity(@PathVariable("id") String id) {
        try {
            DeviceId deviceId = new DeviceId(id);
            Iterable<ActuatorId> actuatorIds = actuatorService.getActuatorIdsByDeviceIdentity(deviceId);
            if (actuatorIds == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (!actuatorIds.iterator().hasNext()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<ActuatorIdDTO> actuatorIdsList = actuatorMapper.toActuatorIdsDTO(actuatorIds);
            for (ActuatorIdDTO actuatorIdDTO : actuatorIdsList) {
                actuatorIdDTO.add(linkTo(methodOn(ActuatorRESTController.class).getActuatorByIdentity(actuatorIdDTO.getActuatorId())).withSelfRel());
            }
            return new ResponseEntity<>(actuatorIdsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    /**
     * Operate (open/close) the blind roller with the given id and value.
     *
     * @param actuatorId the id of the actuator to operate
     * @param percentage the value to operate the actuator with
     * @return the percentage of the operation
     */
    @PutMapping("/{actuatorId}/operate-blind-roller")
    public ResponseEntity<ValueDTO> operateBlindRoller(@PathVariable("actuatorId") String actuatorId,
                                                       @RequestParam("percentage") String percentage) {
        try {
            ActuatorId actuatorIdVO = new ActuatorId(actuatorId);
            ScalePercentageValue percentageValue = new ScalePercentageValue(Double.parseDouble(percentage));
            Value result = actuatorService.operateBlindRoller(actuatorIdVO, percentageValue);
            if (result == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(valueMapper.toDTO(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Get the last percentage reading of the actuator with the given id.
     *
     * @param id the id of the actuator to get the last percentage reading from.
     * @return the response entity with the last percentage reading of the actuator.
     */
    @GetMapping("/{id}/current-percentage-value")
    public ResponseEntity<ValueDTO> getLastPercentageReading(@PathVariable("id") String id) {
        try {
            ActuatorId actuatorId = new ActuatorId(id);
            Optional<Value> lastPercentageReading = actuatorService.getLastPercentageReading(actuatorId);
            if (lastPercentageReading.isPresent()) {
                ValueDTO lastPercentageReadingDTO = valueMapper.toDTO(lastPercentageReading.get());
                return new ResponseEntity<>(lastPercentageReadingDTO, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}