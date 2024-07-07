package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorTypeDTO;
import smarthome.mapper.SensorTypeIdDTO;
import smarthome.mapper.mapper.SensorTypeMapper;
import smarthome.service.ISensorTypeService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/sensortypes")

public class SensorTypeRESTController {

    private final ISensorTypeService sensorTypeService;

    private final SensorTypeMapper sensorTypeMapper;

    /**
     * Constructor for SensorTypeRESTController.
     *
     * @param sensorTypeService The service to be used for sensor type operations.
     * @param sensorTypeMapper The mapper to be used for converting between SensorType and SensorTypeDTO.
     */
    @Autowired
    public SensorTypeRESTController(ISensorTypeService sensorTypeService, SensorTypeMapper sensorTypeMapper) {
        this.sensorTypeService = sensorTypeService;
        this.sensorTypeMapper = sensorTypeMapper;
    }

    /**
     * Endpoint to get a SensorType by its ID.
     *
     * @param sensorTypeId The ID of the SensorType to retrieve.
     * @return ResponseEntity with the SensorTypeDTO and HTTP status code.
     */
    @GetMapping(value = "/{sensorTypeId}", produces = {"application/hal+json"})
    public ResponseEntity<EntityModel<SensorTypeDTO>> getSensorTypeByIdentity(@PathVariable("sensorTypeId")
                                                                                  String sensorTypeId) {
        SensorTypeId sensorTypeIdVO = new SensorTypeId(sensorTypeId);
        Optional<SensorType> sensorTypeOptional = sensorTypeService.getByIdentity((sensorTypeIdVO));
        if(sensorTypeOptional.isPresent()){
            SensorType sensorType = sensorTypeOptional.get();
            SensorTypeDTO sensorTypeDTO = sensorTypeMapper.toSensorTypeDTO(sensorType);

            Link link= linkTo(methodOn(SensorTypeRESTController.class).getSensorTypeByIdentity(sensorTypeId))
                    .withSelfRel();
            EntityModel<SensorTypeDTO> sensorTypeModel = EntityModel.of(sensorTypeDTO, link);
            return new ResponseEntity<>(sensorTypeModel, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get all SensorTypes.
     *
     * @return ResponseEntity with a collection of SensorTypeDTOs and HTTP status code.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<SensorTypeIdDTO>> getSensorTypes() {
        List<SensorTypeId> sensorTypeIds = sensorTypeService.getSensorTypesIds();

        List<SensorTypeIdDTO> sensorTypesIdDTO = sensorTypeMapper.toSensorTypesIdDTO(sensorTypeIds);

        for(SensorTypeIdDTO sensorTypeIdDTO : sensorTypesIdDTO){
            String sensorTypeId = sensorTypeIdDTO.getSensorTypeId();
            Link selfLink = linkTo(methodOn(SensorTypeRESTController.class).getSensorTypeByIdentity(sensorTypeId))
                    .withSelfRel();
            sensorTypeIdDTO.add(selfLink);
        }

        CollectionModel<SensorTypeIdDTO> sensorTypesResult = CollectionModel.of(sensorTypesIdDTO);

        return new ResponseEntity<>(sensorTypesResult, HttpStatus.OK);
    }
}
