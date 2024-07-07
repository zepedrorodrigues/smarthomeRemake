package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.actuatormodel.ActuatorModel;
import smarthome.domain.actuatormodel.vo.ActuatorModelName;
import smarthome.domain.actuatortype.vo.ActuatorTypeName;
import smarthome.mapper.ActuatorModelDTO;
import smarthome.mapper.ActuatorModelNameDTO;
import smarthome.mapper.mapper.ActuatorModelMapper;
import smarthome.service.IActuatorModelService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is a REST controller that handles requests related to ActuatorModel entities.
 */
@RestController
@RequestMapping("/actuatormodels")
public class ActuatorModelRESTController {

    private final IActuatorModelService actuatorModelService;
    private final ActuatorModelMapper actuatorModelMapper;

    /**
     * Constructor for the ActuatorModelRESTController.
     *
     * @param actuatorModelService The ActuatorModelService to use.
     * @param actuatorModelMapper  The ActuatorModelMapper to use.
     */
    @Autowired
    public ActuatorModelRESTController(IActuatorModelService actuatorModelService,
                                       ActuatorModelMapper actuatorModelMapper) {
        this.actuatorModelService = actuatorModelService;
        this.actuatorModelMapper = actuatorModelMapper;
    }

    /**
     * This method retrieves an ActuatorModel entity by its name.
     *
     * @param actuatorModelName The name of the ActuatorModel entity to retrieve.
     * @return An ActuatorModelDTO containing the ActuatorModel entity if found, otherwise a NOT_FOUND status.
     */
    @GetMapping("/{actuatorModelName}")
    public ResponseEntity<EntityModel<ActuatorModelDTO>> getActuatorModelByName(@PathVariable("actuatorModelName") String actuatorModelName) {
        ActuatorModelName actuatorModelNameVo = new ActuatorModelName(actuatorModelName);
        Optional<ActuatorModel> actuatorModelOptional =
                actuatorModelService.getActuatorModelByName(actuatorModelNameVo);
        if (actuatorModelOptional.isPresent()) {
            ActuatorModel actuatorModel = actuatorModelOptional.get();
            ActuatorModelDTO actuatorModelDTO = actuatorModelMapper.toActuatorModelDTO(actuatorModel);
            Link link =
                    linkTo(methodOn(ActuatorModelRESTController.class).getActuatorModelByName(actuatorModelName)).withSelfRel();
            EntityModel<ActuatorModelDTO> actuatorModelEntity = EntityModel.of(actuatorModelDTO, link);
            return new ResponseEntity<>(actuatorModelEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method retrieves all ActuatorModel entities by their ActuatorType name.
     *
     * @param actuatorTypeName The name of the ActuatorType to retrieve ActuatorModel entities by.
     * @return A CollectionModel containing all ActuatorModel entities with the specified ActuatorType name if found,
     * otherwise a NOT_FOUND status.
     */
    @GetMapping("/type/{actuatorTypeName}")
    public ResponseEntity<CollectionModel<ActuatorModelNameDTO>> getActuatorModelsByActuatorTypeIdentity(@PathVariable(
            "actuatorTypeName") String actuatorTypeName) {
        ActuatorTypeName actuatorTypeNameVo = new ActuatorTypeName(actuatorTypeName);
        List<ActuatorModelName> actuatorModelNames =
                actuatorModelService.getActuatorModelsByActuatorTypeIdentity(actuatorTypeNameVo);
        if (!actuatorModelNames.isEmpty()) {
            List<ActuatorModelNameDTO> actuatorModelNameDTOs = actuatorModelMapper.toActuatorModelNamesDTO(actuatorModelNames);
            for (ActuatorModelNameDTO actuatorModelNameDTO : actuatorModelNameDTOs) {
                Link selflink =
                        linkTo(methodOn(ActuatorModelRESTController.class).getActuatorModelByName(actuatorModelNameDTO.getActuatorModelName())).withSelfRel();
                actuatorModelNameDTO.add(selflink);
            }

            CollectionModel<ActuatorModelNameDTO> actuatorModelNameResult = CollectionModel.of(actuatorModelNameDTOs);
            return new ResponseEntity<>(actuatorModelNameResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
