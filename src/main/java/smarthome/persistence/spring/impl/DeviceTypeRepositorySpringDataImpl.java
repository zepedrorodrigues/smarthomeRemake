package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.persistence.datamodel.DeviceTypeDataModel;
import smarthome.persistence.datamodel.mapper.DeviceTypeDataModelMapper;
import smarthome.persistence.spring.IDeviceTypeRepositorySpringData;

import java.util.List;
import java.util.Optional;

/**
 * This class implements the IDeviceTypeRepository interface.
 */
@Repository
public class DeviceTypeRepositorySpringDataImpl implements IDeviceTypeRepository {

    private final DeviceTypeDataModelMapper deviceTypeDataModelMapper;
    private final IDeviceTypeRepositorySpringData deviceTypeSpringDataRepository;

    /**
     * Constructor for the DeviceTypeRepositorySpringDataImpl class.
     * @param deviceTypeDataModelMapper      The mapper to convert between the DeviceTypeDataModel persistence model
     *                                       and the DeviceType domain model.
     * @param deviceTypeSpringDataRepository The Spring Data repository for DeviceTypeDataModel.
     */
    public DeviceTypeRepositorySpringDataImpl(DeviceTypeDataModelMapper deviceTypeDataModelMapper,
                                              IDeviceTypeRepositorySpringData deviceTypeSpringDataRepository) {
        this.deviceTypeDataModelMapper = deviceTypeDataModelMapper;
        this.deviceTypeSpringDataRepository = deviceTypeSpringDataRepository;
    }

    /**
     * Save a DeviceType.
     *
     * @param deviceType The DeviceType to save.
     * @return The saved DeviceType.
     */
    @Override
    public DeviceType save(DeviceType deviceType) {
        if (deviceType == null || containsIdentity(deviceType.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DeviceTypeDataModel deviceTypeDataModel = new DeviceTypeDataModel(deviceType);
        deviceTypeSpringDataRepository.save(deviceTypeDataModel);
        return deviceType;
    }

    /**
     * Retrieve all DeviceTypes.
     *
     * @return An Iterable of all DeviceTypes.
     */
    @Override
    public Iterable<DeviceType> findAll() {
        List<DeviceTypeDataModel> deviceTypeDataModels = deviceTypeSpringDataRepository.findAll();
        return deviceTypeDataModelMapper.toDomain(deviceTypeDataModels);
    }

    /**
     * Retrieve a DeviceType by its identity.
     *
     * @param deviceTypeName The identity of the DeviceType to retrieve.
     * @return An Optional containing the DeviceType if found, or empty if not found.
     */
    @Override
    public Optional<DeviceType> findByIdentity(DeviceTypeName deviceTypeName) {
        if (deviceTypeName == null) {
            throw new IllegalArgumentException();
        }

        Optional<DeviceTypeDataModel> deviceTypeDataModel = deviceTypeSpringDataRepository.
                findById(deviceTypeName.getDeviceTypeName());

        return deviceTypeDataModel.map(deviceTypeDataModelMapper::toDomain);
    }

    /**
     * Check if a DeviceType with the given identity exists.
     *
     * @param deviceTypeName The identity of the DeviceType to check.
     * @return true if the DeviceType exists, false otherwise.
     */
    @Override
    public boolean containsIdentity(DeviceTypeName deviceTypeName) {
        if (deviceTypeName == null) {
            throw new IllegalArgumentException();
        }

        return deviceTypeSpringDataRepository.existsById(deviceTypeName.getDeviceTypeName());
    }

    /**
     * Retrieve all device type names.
     *
     * @return An Iterable of all device type names.
     */
    @Override
    public Iterable<DeviceTypeName> findDeviceTypeNames() {
        List<String> deviceTypeNames = deviceTypeSpringDataRepository.findDeviceTypeNames();
        return deviceTypeNames.stream().map(DeviceTypeName::new).toList();
    }
}
