package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.actuatortype.ActuatorType;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.mapper.ActuatorTypeDTO;
import smarthome.mapper.mapper.ActuatorTypeMapper;
import smarthome.service.IActuatorTypeService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller for managing actuator types.
 */
@RestController
@RequestMapping("/actuatortypes")
public class ActuatorTypeRESTController {

    private final IActuatorTypeService actuatorTypeService;
    private final ActuatorTypeMapper actuatorTypeMapper;

    /**
     * Constructor for the ActuatorTypeRESTController.
     *
     * @param actuatorTypeService The service for managing actuator types.
     * @param actuatorTypeMapper  The mapper for converting between ActuatorType and ActuatorTypeDTO.
     */
    @Autowired
    public ActuatorTypeRESTController(IActuatorTypeService actuatorTypeService, ActuatorTypeMapper actuatorTypeMapper) {
        this.actuatorTypeService = actuatorTypeService;
        this.actuatorTypeMapper = actuatorTypeMapper;
    }

    /**
     * Returns the actuator type with the given id.
     *
     * @param id The id of the actuator type.
     * @return The actuator type with the given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ActuatorTypeDTO>> getActuatorTypeById(@PathVariable("id") String id) {
        ActuatorTypeName actuatorTypeName = new ActuatorTypeName(id);
        Optional<ActuatorType> actuatorTypeOptional = actuatorTypeService.getActuatorTypeById(actuatorTypeName);
        if (actuatorTypeOptional.isPresent()) {
            ActuatorTypeDTO actuatorTypeDTO = actuatorTypeMapper.toActuatorTypeDTO(actuatorTypeOptional.get());
            Link link = linkTo(methodOn(ActuatorTypeRESTController.class).getActuatorTypeById(id)).withSelfRel();
            EntityModel<ActuatorTypeDTO> actuatorTypeEntityModel = EntityModel.of(actuatorTypeDTO, link);
            return new ResponseEntity<>(actuatorTypeEntityModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns a list of all actuator types.
     *
     * @return A list of ActuatorTypeDTOs objets representing the actuator types.
     */
    @GetMapping
    public ResponseEntity<List<ActuatorTypeDTO>> getActuatorTypes() {
        List<ActuatorTypeName> actuatorTypeIds = actuatorTypeService.getActuatorTypeIds();
        //if actuatorTypes is empty, return 404 Not Found
        if (!actuatorTypeIds.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ActuatorTypeDTO> actuatorTypesDTO = actuatorTypeMapper.toActuatorTypeIdsDTO(actuatorTypeIds);
            for (ActuatorTypeDTO actuatorTypeDTO : actuatorTypesDTO) {
                actuatorTypeDTO.add(linkTo(methodOn(ActuatorTypeRESTController.class).getActuatorTypeById(actuatorTypeDTO.getActuatorTypeName())).withSelfRel());
            }
            return new ResponseEntity<>(actuatorTypesDTO, HttpStatus.OK);
        }
    }
}