package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.service.IDeviceService;

/**
 * This class is responsible for handling device deactivation requests.
 * It uses an instance of a class that implements the IDeviceService interface to perform the actual deactivation.
 */
public class DeactivateDeviceController {
    private final IDeviceService deviceService;
    private final DeviceMapper deviceMapper;

    /**
     * Constructs a new DeactivateDeviceController with the provided IDeviceService implementation.
     * @param deviceService An instance of a class that implements the IDeviceService interface.
     * @throws IllegalArgumentException if the provided deviceService is null.
     */
    public DeactivateDeviceController(IDeviceService deviceService, DeviceMapper deviceMapper) {
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
    }

    /**
     * Deactivates the provided device.
     * @param deviceDTO The device to be deactivated.
     * @return The deactivated device, or null if an exception occurred during deactivation.
     */
    public DeviceDTO deactivateDevice(DeviceDTO deviceDTO) {
        if (deviceDTO != null) {
            DeviceId deviceId = deviceMapper.toDeviceId(deviceDTO);
            Device deactivatedDevice = deviceService.deactivateDevice(deviceId);
            if (deactivatedDevice != null) {
                return deviceMapper.toDeviceDTO(deactivatedDevice);
            }
        }
        return null;
    }
}