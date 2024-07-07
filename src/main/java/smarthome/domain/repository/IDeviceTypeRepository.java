package smarthome.domain.repository;

import smarthome.ddd.IRepository;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;

/**
 * The IDeviceTypeRepository interface represents a repository for managing DeviceType entities.
 */
public interface IDeviceTypeRepository extends IRepository<DeviceTypeName, DeviceType> {

    /**
     * Retrieves all DeviceType names in the repository.
     *
     * @return all DeviceType names in the repository
     */
    Iterable<DeviceTypeName> findDeviceTypeNames();
}
