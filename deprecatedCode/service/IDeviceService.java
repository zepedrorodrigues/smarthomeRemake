package smarthome.service;

import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.RoomDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The IDeviceService interface represents a service for Device objects.
 * It provides methods to manage devices in a smart home system.
 */
public interface IDeviceService {

    /**
     * Adds a device to a room.
     *
     * @param deviceDTO The DTO representing the device to be added to the room.
     * @return The DTO representing the device added to the room.
     */
    DeviceDTO addDeviceToRoom(DeviceDTO deviceDTO);

    /**
     * Retrieves all devices in a room.
     *
     * @param roomDTO The DTO representing the room.
     * @return A list of DTOs representing the devices in the room.
     */
    List<DeviceDTO> getDevicesInRoom(RoomDTO roomDTO);

    /**
     * Deactivates a device.
     *
     * @param deviceDTO The DTO representing the device to be deactivated.
     * @return The DTO representing the deactivated device.
     */
    DeviceDTO deactivateDevice(DeviceDTO deviceDTO);

    /**
     * Retrieves devices by their functionality.
     *
     * @return A map of device filtered by their functionality.
     */
    HashMap<String, Set<DeviceDTO>> getDevicesBySensorType() throws IllegalAccessException;

    /**
     * Retrieves readings from a device in a given period.
     *
     * @param deviceDTO The DTO representing the device.
     * @param periodDTO The DTO representing the period.
     * @return A list of DTOs representing the readings from the device in the given period.
     */
    List<ReadingDTO> getReadingsFromDeviceInAGivenPeriod(DeviceDTO deviceDTO, PeriodDTO periodDTO);

}
