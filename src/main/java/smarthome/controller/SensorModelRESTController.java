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
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.mapper.SensorModelDTO;
import smarthome.mapper.SensorModelNameDTO;
import smarthome.mapper.mapper.SensorModelMapper;
import smarthome.service.ISensorModelService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/sensormodels")
/**
 * REST Controller for SensorModel related operations.
 */ public class SensorModelRESTController {

    private final ISensorModelService sensorModelService;

    private final SensorModelMapper sensorModelMapper;


    /**
     * Constructor for SensorModelRESTController.
     *
     * @param sensorModelService The service to be used for sensor model operations.
     * @param sensorModelMapper  The mapper to be used for sensor model operations.
     */
    @Autowired
    public SensorModelRESTController(ISensorModelService sensorModelService, SensorModelMapper sensorModelMapper) {
        this.sensorModelService = sensorModelService;
        this.sensorModelMapper = sensorModelMapper;

    }

    /**
     * Endpoint to get a SensorModel by its name.
     *
     * @param sensorModelName The name of the SensorModel to retrieve.
     * @return ResponseEntity containing the SensorModelDTO and HTTP status.
     */
    @GetMapping(value = "/{sensorModelName}", produces = {"application/hal+json"})
    public ResponseEntity<EntityModel<SensorModelDTO>> getSensorModelByName(@PathVariable("sensorModelName")
                                                                                String sensorModelName) {
        SensorModelName sensorModelNameVO = new SensorModelName(sensorModelName);
        Optional<SensorModel> sensorModelOptional = sensorModelService.getSensorModelByName(sensorModelNameVO);
        if (sensorModelOptional.isPresent()) {
            SensorModel sensorModel = sensorModelOptional.get();
            SensorModelDTO sensorModelDTO = sensorModelMapper.toSensorModelDto(sensorModel);

            Link link =
                    linkTo(methodOn(SensorModelRESTController.class).getSensorModelByName(sensorModelName)).withSelfRel();
            EntityModel<SensorModelDTO> sensorModelEntity = EntityModel.of(sensorModelDTO, link);

            return new ResponseEntity<>(sensorModelEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Endpoint to get a list of SensorModelNames associated with a specific SensorTypeId.
     *
     * @param sensorTypeId The ID of the SensorType to retrieve associated SensorModels.
     * @return ResponseEntity containing the list of SensorModelNameDTOs and HTTP status.
     */
    @GetMapping( "/type/{sensorTypeId}")
    public ResponseEntity<CollectionModel<SensorModelNameDTO>> getSensorModelsBySensorTypeIdentity(@PathVariable(
            "sensorTypeId") String sensorTypeId) {

        SensorTypeId sensorTypeIdVO = new SensorTypeId(sensorTypeId);
        List<SensorModelName> sensorModelNames = sensorModelService.getSensorModelsBySensorTypeIdentity(sensorTypeIdVO);

        if (!sensorModelNames.isEmpty()) {
            List<SensorModelNameDTO> sensorModelNameDTOs = sensorModelMapper.toSensorModelsNameDTO(sensorModelNames);

            for (SensorModelNameDTO sensorModelNameDTO : sensorModelNameDTOs) {
                Link selfLink =
                        linkTo(methodOn(SensorModelRESTController.class).getSensorModelByName(sensorModelNameDTO
                                .getSensorModelName())).withSelfRel();
                sensorModelNameDTO.add(selfLink);
            }
            CollectionModel<SensorModelNameDTO> sensorModelNameResult = CollectionModel.of(sensorModelNameDTOs);

            return new ResponseEntity<>(sensorModelNameResult, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}










