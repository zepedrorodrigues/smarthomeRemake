package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.room.vo.RoomId;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceIdDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides mapping functionalities to convert between Device domain objects and their corresponding Data Transfer
 * Objects (DTOs).
 * This class facilitates the transformation of Device details to and from various formats used within the
 * application, particularly for API interactions.
 */
@Component
public class DeviceMapper {

    /**
     * Converts a Device domain object to a DeviceDTO.
     * <p>
     * @param device the device to convert
     * @return the corresponding DeviceDTO
     */
    public DeviceDTO toDeviceDTO(Device device) {
        String deviceId = device.getIdentity().getIdentity();
        String deviceName = device.getDeviceName().getDeviceName();
        String deviceTypeName = device.getDeviceTypeName().getDeviceTypeName();
        String roomName = device.getRoomId().getRoomId();
        boolean status = device.getDeviceStatus().getStatus();
        return new DeviceDTO(deviceId, deviceName, deviceTypeName, roomName, status);
    }

    /**
     * Converts a {@link DeviceDTO} to a {@link DeviceId} based on the device ID in the DTO.
     * <p>
     * @param deviceDTO the device DTO to convert
     * @return the corresponding {@link DeviceId} object
     */
    public DeviceId toDeviceId(DeviceDTO deviceDTO) {
        return new DeviceId(deviceDTO.getDeviceId());
    }

    /**
     * Converts a {@link DeviceDTO} to a {@link DeviceName} based on the device name in
     * the DTO.
     * <p>
     * @param deviceDTO the device DTO to convert
     * @return the corresponding {@link DeviceName} object
     */
    public DeviceName toDeviceName(DeviceDTO deviceDTO) {
        return new DeviceName(deviceDTO.getDeviceName());
    }

    /**
     * Converts a {@link DeviceDTO} to a {@link RoomId} based on the room ID in the DTO.
     * <p>
     * @param deviceDTO the device DTO to convert
     * @return the corresponding {@link RoomId} object
     */
    public RoomId toRoomId(DeviceDTO deviceDTO) {
        return new RoomId(deviceDTO.getRoomId());
    }

    /**
     * Converts a map of devices grouped by sensor type to a map of {@link DeviceDTO}s grouped the same way.
     * <p>
     * @param map the original map of devices keyed by sensor type name
     * @return a map of {@link DeviceDTO}s keyed by sensor type name
     */
    public Map<String, List<DeviceDTO>> toMapDTO(Map<String, List<Device>> map) {
        Map<String, List<DeviceDTO>> mapDTO = new HashMap<>();
        for (Map.Entry<String, List<Device>> entry : map.entrySet()) {
            List<DeviceDTO> devicesDTO = new ArrayList<>();
            for (Device device : entry.getValue()) {
                devicesDTO.add(toDeviceDTO(device));
            }
            mapDTO.put(entry.getKey(), devicesDTO);
        }
        return mapDTO;
    }

    /**
     * Converts an iterable of devices to a list of DeviceDTO objects.
     * <p>
     * @param devices the list of devices to be converted
     * @return the list of DeviceDTO objects
     */
    public List<DeviceDTO> toDevicesDTO(Iterable<Device> devices) {
        List<DeviceDTO> devicesDTO = new ArrayList<>();
        for (Device device : devices) {
            devicesDTO.add(toDeviceDTO(device));
        }
        return devicesDTO;
    }

    /**
     * Converts an iterable of device ID's to a list of DeviceDTO objects containing only the deviceId.
     * <p>
     *
     * @param deviceIds the list of device IDs to be converted.
     * @return the list of DeviceDTO objects containing only the deviceId.
     */
    public List<DeviceIdDTO> toDeviceIdsDTO(Iterable<DeviceId> deviceIds) {
        List<DeviceIdDTO> deviceIdsDTO = new ArrayList<>();
        for (DeviceId deviceId : deviceIds) {
            deviceIdsDTO.add(new DeviceIdDTO(deviceId.getIdentity()));
        }
        return deviceIdsDTO;
    }

}
