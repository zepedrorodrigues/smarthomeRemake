package smarthome.service.impl;

import org.springframework.stereotype.Service;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.service.IDeviceTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class provides the implementation for the IDeviceTypeService interface.
 * It is annotated with @Service to indicate that it's a service component in the Spring context.
 */
@Service
public class DeviceTypeServiceImpl implements IDeviceTypeService {

    private final IDeviceTypeRepository deviceTypeRepository;

    /**
     * Constructor for DeviceTypeServiceImpl.
     *
     * @param deviceTypeRepository the device type repository
     */
    public DeviceTypeServiceImpl(IDeviceTypeRepository deviceTypeRepository) {
        this.deviceTypeRepository = deviceTypeRepository;
    }

    /**
     * Get the identity of all DeviceType entities in the repository.
     *
     * @return An Iterable of all DeviceTypeName objects.
     */
    @Override
    public List<DeviceTypeName> getDeviceTypeNames() {
        Iterable<DeviceTypeName> deviceTypes = deviceTypeRepository.findDeviceTypeNames();
        List<DeviceTypeName> deviceTypesList = new ArrayList<>();

        deviceTypes.forEach(deviceTypesList::add);
        return deviceTypesList;
    }

    /**
     * Get a device type by its identity.
     *
     * @param id The identity of the device type.
     * @return The device type entity.
     */
    @Override
    public Optional<DeviceType> getDeviceTypeById(DeviceTypeName id) {
        return deviceTypeRepository.findByIdentity(id);
    }
}
