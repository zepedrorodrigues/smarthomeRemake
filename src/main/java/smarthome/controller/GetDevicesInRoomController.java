package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.domain.room.vo.RoomId;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IDeviceService;

import java.util.List;

/**
 * The GetDevicesInRoomController class is responsible for handling requests related to retrieving devices
 * in specific rooms from the system.
 */
public class GetDevicesInRoomController {

    private final IDeviceService deviceService;
    private final DeviceMapper deviceMapper;
    private final RoomMapper roomMapper;

    /**
     * Constructor for the GetDevicesInRoomController class.
     *
     * @param deviceService The device service to be used by the controller.
     * @param deviceMapper  The device mapper to be used by the controller. This mapper is used to convert between Device and DeviceDTO objects. It is used to map the device data to the corresponding DTOs. These DeviceDTO objects are used to transfer data between the service layer and the controller.
     * @param roomMapper    The room mapper to be used by the controller. This mapper is used to convert between Room and RoomDTO objects. It is used to map the room DTOs  to the corresponding room data. These RoomDTO objects are used to transfer data between the controller and the service layer.
     */
    public GetDevicesInRoomController(IDeviceService deviceService, DeviceMapper deviceMapper, RoomMapper roomMapper) {
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
        this.roomMapper = roomMapper;
    }

    /**
     * Retrieves all devices in a given room.
     *
     * @return A list of all devices in the room chosen.
     */
    public List<DeviceDTO> getDevicesInRoom(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return null;
        }
        try {
            RoomId roomId = roomMapper.toRoomId(roomDTO);
            Iterable<Device> devicesInRoom = deviceService.getDevicesInRoom(roomId);
            if (devicesInRoom == null) {
                return null;
            }

            return deviceMapper.toDevicesDTO(devicesInRoom);
        } catch (Exception e) {
            return null;
        }
    }
}