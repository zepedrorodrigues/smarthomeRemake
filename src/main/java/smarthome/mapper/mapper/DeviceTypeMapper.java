package smarthome.mapper.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.DeviceTypeDTO;
import smarthome.mapper.DeviceTypeNameDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between DeviceType domain objects and DeviceTypeDTO data transfer objects.
 */
@Component
public class DeviceTypeMapper {

    /**
     * Converts a collection of DeviceType domain objects into a list of DeviceTypeDTOs.
     *
     * @param deviceTypes an Iterable collection of DeviceType domain objects.
     * @return a List of DeviceTypeDTOs.
     */
    public List<DeviceTypeNameDTO> toDeviceTypeNamesDTO(Iterable<DeviceTypeName> deviceTypes) {
        List<DeviceTypeNameDTO> deviceTypeNameDTOs = new ArrayList<>();
        for (DeviceTypeName deviceType : deviceTypes) {
            DeviceTypeNameDTO deviceTypeDTO = toDeviceTypeNameDTO(deviceType);
            deviceTypeNameDTOs.add(deviceTypeDTO);
        }
        return deviceTypeNameDTOs;
    }

    /**
     * Converts a DeviceTypeDTO into a DeviceTypeName value object.
     *
     * @param deviceDTO the DeviceTypeDTO to convert.
     * @return a DeviceTypeName value object.
     * @throws IllegalArgumentException if the DeviceTypeDTO cannot be converted into a DeviceTypeName.
     */
    public DeviceTypeName toDeviceTypeName(DeviceDTO deviceDTO) throws IllegalArgumentException {
        return new DeviceTypeName(deviceDTO.getDeviceTypeName());
    }

    /**
     * Converts a DeviceTypeName into a DeviceTypeNameDTO.
     *
     * @param deviceTypeName the DeviceTypeName to convert.
     * @return a DeviceTypeNameDTO.
     */
    public DeviceTypeNameDTO toDeviceTypeNameDTO(DeviceTypeName deviceTypeName) {
        return new DeviceTypeNameDTO(deviceTypeName.getDeviceTypeName());
    }

    /**
     * Converts a DeviceType into a DeviceTypeDTO.
     *
     * @param deviceType the DeviceType to convert.
     * @return a DeviceTypeDTO.
     */
    public DeviceTypeDTO toDeviceTypeDTO(DeviceType deviceType) {
        return new DeviceTypeDTO(deviceType.getIdentity().getDeviceTypeName());
    }
}
