package smarthome.persistence.spring.impl;

import org.springframework.stereotype.Repository;
import smarthome.domain.device.Device;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.persistence.datamodel.DeviceDataModel;
import smarthome.persistence.datamodel.mapper.DeviceDataModelMapper;
import smarthome.persistence.spring.IDeviceRepositorySpringData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository for devices.
 * Implementation using Spring Data.
 */
@Repository
public class DeviceRepositorySpringDataImpl implements IDeviceRepository {

    private final DeviceDataModelMapper deviceDataModelMapper;
    private final IDeviceRepositorySpringData deviceRepositorySpringData;

    /**
     * Constructor of the Device Repository Spring Data
     * Initializes the DeviceDataModelMapper and the IDeviceRepositorySpringData.
     *
     * @param deviceDataModelMapper      the device data model mapper
     * @param deviceSpringDataRepository the device spring data repository
     */
    public DeviceRepositorySpringDataImpl(DeviceDataModelMapper deviceDataModelMapper,
                                          IDeviceRepositorySpringData deviceSpringDataRepository) {
        this.deviceDataModelMapper = deviceDataModelMapper;
        this.deviceRepositorySpringData = deviceSpringDataRepository;
    }

    /**
     * Save a Device entity to the database.
     * It creates a new DeviceDataModel from the Device domain object and persists it to the database.
     *
     * @param device the Device entity to save
     * @return the saved Device entity
     */
    @Override
    public Device save(Device device) {
        if (device == null || containsIdentity(device.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);
        deviceRepositorySpringData.save(deviceDataModel);
        return device;
    }

    /**
     * Find all Device entities in the repository.
     *
     * @return An Iterable of all Device entities.
     */
    @Override
    public Iterable<Device> findAll() {
        List<DeviceDataModel> deviceDataModels = deviceRepositorySpringData.findAll();
        return deviceDataModelMapper.toDevicesDomain(deviceDataModels);
    }

    /**
     * Get a Device entity by its identity.
     *
     * @param deviceId The identity of the Device entity.
     * @return An Optional that may contain the Device entity if it exists.
     */
    @Override
    public Optional<Device> findByIdentity(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        Optional<DeviceDataModel> deviceDataModel = deviceRepositorySpringData.findById(deviceId.getIdentity());
        return deviceDataModel.map(deviceDataModelMapper::toDeviceDomain);
    }

    /**
     * Check if a Device entity with a given identity exists in the repository.
     *
     * @param deviceId The identity of the Device entity.
     * @return true if the Device entity exists, false otherwise.
     */
    @Override
    public boolean containsIdentity(DeviceId deviceId) {
        if (deviceId == null) {
            throw new IllegalArgumentException();
        }
        return deviceRepositorySpringData.existsById(deviceId.getIdentity());
    }

    /**
     * Find all Device entities in the repository by room identity.
     *
     * @param roomId The identity of the Room entity.
     * @return An Iterable of all Device entities in the given Room.
     */
    @Override
    public Iterable<Device> findDevicesByRoomId(RoomId roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException();
        }
        List<DeviceDataModel> deviceDataModels = deviceRepositorySpringData.findDevicesByRoomIdentity(roomId.getRoomId());
        return deviceDataModelMapper.toDevicesDomain(deviceDataModels);
    }

    /**
     * Find all Device identities in the repository by room identity.
     *
     * @param roomId The identity of the Room entity.
     * @return An Iterable of all Device identities in the given Room.
     */
    @Override
    public Iterable<DeviceId> findDeviceIdsByRoomId(RoomId roomId) {
        if (roomId == null) {
            throw new IllegalArgumentException();
        }
        List<String> deviceIds = deviceRepositorySpringData.findDeviceIdsByRoomIdentity(roomId.getRoomId());

        List<DeviceId> deviceIdObjects = new ArrayList<>();
        for (String id : deviceIds) {
            deviceIdObjects.add(new DeviceId(id));
        }

        return deviceIdObjects;
    }

    /**
     * Update a Device entity in the repository.
     *
     * @param device The Device entity to update.
     * @return The updated Device entity.
     */
    @Override
    public Device update(Device device) {
        if (device == null || !containsIdentity(device.getIdentity())) {
            throw new IllegalArgumentException();
        }
        DeviceDataModel deviceDataModel = new DeviceDataModel(device);

        deviceRepositorySpringData.save(deviceDataModel);

        return device;
    }

    /**
     * Retrieves all device IDs of a specified type.
     * @param deviceTypeName the name of the device type to search for
     * @return an iterable collection of device IDs of the specified type
     */
    @Override
    public Iterable<DeviceId> findDeviceIdsByDeviceTypeName(DeviceTypeName deviceTypeName) {
        if (deviceTypeName == null)
            throw new IllegalArgumentException();

        List<String> deviceIdStr = deviceRepositorySpringData.findDeviceIdsByDeviceTypeName(deviceTypeName.getDeviceTypeName());

        List<DeviceId> deviceIds = new ArrayList<>();
        for (String id : deviceIdStr) {
            deviceIds.add(new DeviceId(id));
        }
        return deviceIds;
    }

    /**
     * Retrieves all device IDs in the repository.
     * @return an iterable collection of all device IDs
     */
    @Override
    public Iterable<DeviceId> findDeviceIds() {
        List<String> deviceIds = deviceRepositorySpringData.findDeviceIds();
        return deviceIds.stream().map(DeviceId::new).toList();
    }
}
