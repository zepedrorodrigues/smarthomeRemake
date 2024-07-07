package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.DeviceTypeMapper;
import smarthome.service.IDeviceService;

/**
 * The AddDeviceToRoomController is a controller class that handles the addition of a new device to a room
 * in the system.
 * It receives a DeviceDTO containing the information of the device to be added to the room and calls the
 * Device Service to add the device to the room.
 * The controller returns the DeviceDTO of the newly added device.
 */
public class AddDeviceToRoomController {

    private final IDeviceService deviceService;
    private final DeviceMapper deviceMapper;
    private final DeviceTypeMapper deviceTypeMapper;

    /**
     * Constructor for AddDeviceToRoomController
     *
     * @param deviceService the IDeviceService to be used by the controller
     * @param deviceMapper  the DeviceMapper to be used by the controller
     * @param deviceTypeMapper the DeviceTypeMapper to be used by the controller
     */
    public AddDeviceToRoomController(IDeviceService deviceService, DeviceMapper deviceMapper, DeviceTypeMapper deviceTypeMapper) {
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
        this.deviceTypeMapper = deviceTypeMapper;
    }

    /**
     * Adds a new device to the room based on the provided DeviceDTO.
     *
     * @param deviceDTO the DeviceDTO containing the device information
     * @return the DeviceDTO of the newly added device, or null if the device could not be added
     */
    public DeviceDTO addDeviceToRoom(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            return null;
        }
        try {
            // Map Value Objects
            DeviceName deviceName = deviceMapper.toDeviceName(deviceDTO);
            DeviceTypeName deviceType = deviceTypeMapper.toDeviceTypeName(deviceDTO);
            RoomId roomId = deviceMapper.toRoomId(deviceDTO);
            // Create and Save Device
            Device savedDevice = deviceService.addDeviceToRoom(deviceName, deviceType, roomId);
            if (savedDevice != null) {
                return deviceMapper.toDeviceDTO(savedDevice);
            } else {
                // Unable to Create and/or Save Device
                return null;
            }
        } catch (Exception e) {
            // Invalid Device DTO Data
            return null;
        }
    }
}

