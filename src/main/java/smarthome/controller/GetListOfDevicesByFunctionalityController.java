package smarthome.controller;

import smarthome.domain.device.Device;
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.service.IDeviceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller class responsible for orchestrating the retrieval
 * of devices grouped by their functionalities based on the types
 * of sensors they use.
 * It utilizes the {@link IDeviceService} to interact with
 * the underlying device and sensor data.
 * The main purpose of this class is to provide a mapping from sensor types
 * to sets of {@link DeviceDTO} that represent the devices equipped with the respective sensors.
 */
public class GetListOfDevicesByFunctionalityController {
    private final IDeviceService deviceService;
    private final DeviceMapper deviceMapper;

    /**
     * Constructs a new controller with the specified device service.
     *
     * @param deviceService the device service to use for fetching device data; must not be null.
     * @throws IllegalArgumentException if {@code deviceService} is null.
     */
    public GetListOfDevicesByFunctionalityController(IDeviceService deviceService, DeviceMapper deviceMapper) {
        if (deviceService == null)
            throw new IllegalArgumentException("Device service cannot be null.");
        this.deviceService = deviceService;
        this.deviceMapper = deviceMapper;
    }

    /**
     * Retrieves a map of device sets categorized by sensor type.
     * Each key in the map is a sensor type represented as a string,
     * and the corresponding value is a set of {@link DeviceDTO}s that contain sensors of that type.
     *
     * @return A {@link HashMap} where each key is a String denoting the sensor
     * type and each value is a {@link Set} of {@link DeviceDTO}s
     *         associated with that sensor type.
     * @throws IllegalAccessException if there is an issue accessing the underlying data which could be
     * due to data retrieval restrictions or permissions.
     */
    public Map<String, List<DeviceDTO>> getDevicesBySensorType() throws IllegalAccessException {
        Map<String,List<Device>> mapDevices = deviceService.getDevicesBySensorType();
        return deviceMapper.toMapDTO(mapDevices);}
}
