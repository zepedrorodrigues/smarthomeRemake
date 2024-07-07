package smarthome.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceIdDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.DeviceTypeMapper;
import smarthome.service.IDeviceService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST Controller for managing devices.
 * This controller provides endpoints for interacting with devices.
 */
@RestController
@RequestMapping("/devices")
public class DeviceRESTController {
    private final IDeviceService deviceService;
    private final DeviceMapper deviceMapper;
    private final DeviceTypeMapper deviceTypeMapper;
    /**
     * Constructor for the DeviceRESTController.
     * @param deviceService The service to be used for device operations.
     * @param deviceMapper The mapper to be used for converting between Device and DeviceDTO objects.
     */
    @Autowired
    public DeviceRESTController(IDeviceService deviceService, DeviceMapper deviceMapper, DeviceTypeMapper deviceTypeMapper) {
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
        this.deviceTypeMapper = deviceTypeMapper;

    }

    /**
     * Adds a new device to a room.
     *
     * @param roomId the id of the room
     * @param deviceDTO the device to be created data
     * @return the response entity with the newly added device data
     */
    @PostMapping("/room/{roomId}")
    public ResponseEntity<DeviceDTO> addDeviceToRoom(@PathVariable("roomId") String roomId,
                                                     @RequestBody DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            // Map Value Objects
            DeviceName deviceName = deviceMapper.toDeviceName(deviceDTO);
            DeviceTypeName deviceTypeName = deviceTypeMapper.toDeviceTypeName(deviceDTO);
            RoomId id = new RoomId(roomId);
            // Create and Save Device
            Device savedDevice = deviceService.addDeviceToRoom(deviceName, deviceTypeName, id);
            if (savedDevice != null) {
                DeviceDTO savedDeviceDTO = deviceMapper.toDeviceDTO(savedDevice);
                savedDeviceDTO.add(linkTo(methodOn(DeviceRESTController.class)
                        .getDeviceById(savedDeviceDTO.getDeviceId()))
                        .withSelfRel());
                savedDeviceDTO.add(linkTo(methodOn(DeviceRESTController.class)
                        .deactivateDevice(savedDeviceDTO.getDeviceId()))
                        .withRel("deactivate"));
                return new ResponseEntity<>(savedDeviceDTO, HttpStatus.CREATED);
            } else {
                // Unable to Create and/or Save Device
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (IllegalArgumentException e) {
            // Invalid Device DTO Data
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Endpoint for getting devices in a room.
     *
     * @param roomId The ID of the room to get devices from.
     * @return A ResponseEntity containing a list of DeviceDTO objects if successful, or a not found response if the
     * room does not exist.
     * The response will be a 200 OK if the room exists and contains devices, 404 Not Found if the room does not contain any devices, or 400 Bad Request if the room ID is invalid.
     * The response will contain a list of DeviceDTO objects containing only the deviceId if successful.
     */

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<DeviceIdDTO>> getDevicesInRoom(@PathVariable("roomId") String roomId) {
        try {
            RoomId roomIdProvided = new RoomId(roomId);
            Iterable<DeviceId> deviceIdsInRoom = deviceService.getDeviceIdsInRoom(roomIdProvided);
            if (deviceIdsInRoom == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
            }

            List<DeviceIdDTO> deviceIdsInRoomDTO = deviceMapper.toDeviceIdsDTO(deviceIdsInRoom);
            if (deviceIdsInRoomDTO.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 Not Found
            }
            for (DeviceIdDTO deviceIdDTO : deviceIdsInRoomDTO) {
                deviceIdDTO.add(linkTo(methodOn(DeviceRESTController.class).getDeviceById(deviceIdDTO.getDeviceId())).withSelfRel()).add(linkTo(methodOn(DeviceRESTController.class).deactivateDevice(deviceIdDTO.getDeviceId())).withRel("deactivate"));

            }
            return new ResponseEntity<>(deviceIdsInRoomDTO, HttpStatus.OK); // 200 OK
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }

    }

    /**
     * Endpoint for deactivating a device.
     * @param id The ID of the device to be deactivated.
     * @return A ResponseEntity containing the deactivated DeviceDTO if successful, or a not found response if the
     * device does not exist.
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<DeviceDTO> deactivateDevice(@PathVariable("id") String id) {

        DeviceId deviceId = new DeviceId(id);
        Device device = deviceService.deactivateDevice(deviceId);

        if (device != null) {
            DeviceDTO deviceDTO = deviceMapper.toDeviceDTO(device);
            Link selfLink = linkTo(DeviceRESTController.class).slash(deviceDTO.getDeviceId()).withSelfRel();
            deviceDTO.add(selfLink);
            return new ResponseEntity<>(deviceDTO, HttpStatus.OK); // 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

    /**
     * Endpoint for getting devices by their functionality.
     * @return A map where the keys are functionality types and the values are sets of DeviceDTO objects.
     */
    @GetMapping("/functionality")
    public ResponseEntity<Map<String, List<DeviceDTO>>> getDevicesByFunctionality() {
        try {
            // Fetch the devices grouped by sensor type
            Map<String, List<Device>> devices = deviceService.getDevicesBySensorType();

            // Convert the devices to DTOs
            Map<String, List<DeviceDTO>> devicesDTO = deviceMapper.toMapDTO(devices);

            // Add HATEOAS links to each DeviceDTO
            devicesDTO.values().forEach(this::addLinksToDeviceDTOs);

            // Wrap the resulting map in a ResponseEntity and return
            return new ResponseEntity<>(devicesDTO, HttpStatus.OK);
        } catch (Exception e) {
            // Handle the exception and return an internal server error response
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Adds HATEOAS links to each DeviceDTO in the set.
     * @param deviceDTOs The set of DeviceDTO objects to add links to.
     */
    private void addLinksToDeviceDTOs(List<DeviceDTO> deviceDTOs) {
        for (DeviceDTO deviceDTO : deviceDTOs) {
            Link selfLink = linkTo(methodOn(DeviceRESTController.class).getDeviceById(deviceDTO.getDeviceId())).withSelfRel();
            deviceDTO.add(selfLink);
        }
    }

    /**
     * Endpoint for getting a device by its ID.
     * @param id The ID of the device to retrieve.
     * @return A ResponseEntity containing the DeviceDTO if successful, or a not found response if the device does not
     * exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable("id") String id) {
        DeviceId deviceId = new DeviceId(id);
        Optional<Device> device = deviceService.getDeviceById(deviceId);
        if (device.isPresent()) {
            DeviceDTO deviceDTO = deviceMapper.toDeviceDTO(device.get());
            Link selfLink = linkTo(DeviceRESTController.class).slash(deviceDTO.getDeviceId()).withSelfRel();
            deviceDTO.add(selfLink);
            return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint for getting all devices.
     * @return A ResponseEntity containing a list of DeviceIdDTO objects if successful, or a not found response if no
     * devices exist.
     */
    @GetMapping()
    public ResponseEntity<List<DeviceIdDTO>> getDevices() {
        try {
            Iterable<DeviceId> deviceIds = deviceService.findDeviceIds();
            if (!deviceIds.iterator().hasNext()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                List<DeviceIdDTO> deviceIdsDTO = deviceMapper.toDeviceIdsDTO(deviceIds);
                for (DeviceIdDTO deviceIdDTO : deviceIdsDTO) {
                    deviceIdDTO.add(linkTo(methodOn(DeviceRESTController.class)
                            .getDeviceById(deviceIdDTO.getDeviceId())).withSelfRel());
                }
                return new ResponseEntity<>(deviceIdsDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}