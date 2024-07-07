package smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.mapper.DeviceTypeDTO;
import smarthome.mapper.DeviceTypeNameDTO;
import smarthome.mapper.mapper.DeviceTypeMapper;
import smarthome.service.IDeviceTypeService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST Controller for managing device types.
 * This controller provides endpoints for interacting with device types.
 */
@RestController
@RequestMapping("/devicetypes")
public class DeviceTypeRESTController {

    private final IDeviceTypeService deviceTypeService;
    private final DeviceTypeMapper deviceTypeMapper;

    /**
     * Constructor for the DeviceTypeRESTController.
     *
     * @param deviceTypeService The service to be used for device type operations.
     * @param deviceTypeMapper  The mapper to be used for converting between DeviceType and DeviceTypeDTO objects.
     */
    @Autowired
    public DeviceTypeRESTController(IDeviceTypeService deviceTypeService, DeviceTypeMapper deviceTypeMapper) {
        this.deviceTypeService = deviceTypeService;
        this.deviceTypeMapper = deviceTypeMapper;
    }

    /**
     * Gets the available device types.
     *
     * @return the response entity with the list of available device types
     */
    @GetMapping
    public ResponseEntity<List<DeviceTypeNameDTO>> getDeviceTypes() {
        try {
            List<DeviceTypeName> deviceTypeNames = deviceTypeService.getDeviceTypeNames();
            if (deviceTypeNames.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<DeviceTypeNameDTO> deviceTypeNameDTOs = deviceTypeMapper.toDeviceTypeNamesDTO(deviceTypeNames);

            for (DeviceTypeNameDTO deviceTypeNameDTO : deviceTypeNameDTOs) {
                deviceTypeNameDTO.add(linkTo(methodOn(DeviceTypeRESTController.class).getDeviceTypeById(deviceTypeNameDTO.getDeviceTypeName())).withSelfRel());
            }
            return new ResponseEntity<>(deviceTypeNameDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets a device type by its id.
     *
     * @param id The id of the device type to get.
     * @return the response entity with the device type
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceTypeDTO> getDeviceTypeById(@PathVariable("id") String id) {
        try {
            DeviceTypeName deviceTypeName = new DeviceTypeName(id);
            Optional<DeviceType> deviceType = deviceTypeService.getDeviceTypeById(deviceTypeName);
            if (deviceType.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DeviceTypeDTO deviceTypeDTO = deviceTypeMapper.toDeviceTypeDTO(deviceType.get())
                    .add(linkTo(methodOn(DeviceTypeRESTController.class)
                            .getDeviceTypeById(id)).withSelfRel());
            return new ResponseEntity<>(deviceTypeDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}