package smarthome.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IDeviceTypeRepository;
import smarthome.domain.repository.IRoomRepository;
import smarthome.domain.repository.ISensorModelRepository;
import smarthome.domain.repository.ISensorRepository;
import smarthome.domain.repository.ISensorTypeRepository;
import smarthome.domain.room.vo.RoomId;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensormodel.SensorModel;
import smarthome.domain.sensormodel.vo.SensorModelName;
import smarthome.domain.sensortype.SensorType;
import smarthome.domain.sensortype.vo.SensorTypeId;
import smarthome.service.IDeviceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class implements the {@link IDeviceService} interface and provides services related to devices in a
 * smart home system.
 * It manages operations such as adding a device to a room, retrieving devices in a room,
 * deactivating a device, and retrieving devices grouped by sensor type.
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    IRoomRepository roomRepository;
    DeviceFactory deviceFactory;
    IDeviceRepository deviceRepository;
    ISensorTypeRepository sensorTypeRepository;
    ISensorModelRepository sensorModelRepository;
    ISensorRepository sensorRepository;
    IDeviceTypeRepository deviceTypeRepository;

    /**
     * Constructs a new DeviceServiceImpl with the given dependencies.
     *
     * @param roomRepository       The repository for room management.
     * @param deviceFactory        The factory for creating devices.
     * @param deviceRepository     The repository for device management.
     * @param sensorTypeRepository The repository for sensor type management.
     * @param sensorRepository     The repository for sensor management.
     */
    @Autowired
    public DeviceServiceImpl(IRoomRepository roomRepository, DeviceFactory deviceFactory,
                             IDeviceRepository deviceRepository, ISensorTypeRepository sensorTypeRepository,
                             ISensorModelRepository sensorModelRepository, ISensorRepository sensorRepository,
                             IDeviceTypeRepository deviceTypeRepository) {
        this.roomRepository = roomRepository;
        this.deviceFactory = deviceFactory;
        this.deviceRepository = deviceRepository;
        this.sensorTypeRepository = sensorTypeRepository;
        this.sensorModelRepository = sensorModelRepository;
        this.sensorRepository = sensorRepository;
        this.deviceTypeRepository = deviceTypeRepository;
    }

    /**
     * Adds a new device to a room.
     *
     * @param deviceName The name of the device.
     * @param deviceType The type of the device.
     * @param roomId     The ID of the room to which the device will be added.
     * @return The newly added device, or null if the room does not exist.
     */
    @Override
    public Device addDeviceToRoom(DeviceName deviceName, DeviceTypeName deviceType, RoomId roomId) {
        try {
            if (!roomRepository.containsIdentity(roomId) || !deviceTypeRepository.containsIdentity(deviceType)) {
                return null;
            }
            Device device = deviceFactory.createDevice(deviceName, deviceType, roomId);
            return deviceRepository.save(device);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves all devices in a given room.
     *
     * @param roomId The ID of the room.
     * @return An iterable collection of devices in the room, or null if the room does not exist.
     */
    public Iterable<Device> getDevicesInRoom(RoomId roomId) {
        try {
            if (!roomRepository.containsIdentity(roomId)) {
                return null;
            }

            return deviceRepository.findDevicesByRoomId(roomId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves all device IDs in a given room.
     *
     * @param roomId The ID of the room.
     * @return An iterable collection of device IDs in the room, or null if the room does not exist.
     */
    public Iterable<DeviceId> getDeviceIdsInRoom(RoomId roomId) {
        try {
            if (!roomRepository.containsIdentity(roomId)) {
                return null;
            }
            return deviceRepository.findDeviceIdsByRoomId(roomId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Deactivates a device with the given ID.
     * @param deviceId the ID of the device to deactivate
     * @return the deactivated device, or null if the device does not exist
     */
    @Override
    public Device deactivateDevice(DeviceId deviceId) {
        try {
            Optional<Device> deviceOptional = deviceRepository.findByIdentity(deviceId);
            if (deviceOptional.isPresent()) {
                Device device = deviceOptional.get();
                device.deactivateDevice();

                deviceRepository.update(device);

                return device;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves devices grouped by sensor type.
     *
     * @return A map containing devices grouped by sensor type.
     * @throws IllegalAccessException If illegal access occurs.
     */
    @Override
    public HashMap<String, List<Device>> getDevicesBySensorType() throws IllegalAccessException {
        Iterable<SensorType> sensorTypes = sensorTypeRepository.findAll();
        HashMap<String, List<Device>> devicesBySensorType = new HashMap<>();
        for (SensorType sensorType : sensorTypes) {
            devicesBySensorType.put(sensorType.getSensorTypeName().getSensorTypeName(), new ArrayList<>());
        }
        Iterable<Sensor> sensors = sensorRepository.findAll();
        return clearHashMap(fillMapWithDevices(devicesBySensorType, sensors));
    }

    /**
     * Fills a map with devices grouped by sensor type.
     *
     * @param devicesBySensorType The map to fill.
     * @param sensors             The sensors to process.
     * @return A map containing devices grouped by sensor type.
     */
    private HashMap<String, List<Device>> fillMapWithDevices(HashMap<String, List<Device>> devicesBySensorType,
                                                            Iterable<Sensor> sensors) {
        for (Sensor sensor : sensors) {
            SensorModelName sensorModelName = sensor.getSensorModelName();
            Optional<SensorModel> sensorModelOptional = sensorModelRepository.findByIdentity(sensorModelName);
            if (sensorModelOptional.isPresent()) {
                SensorModel sensorModel = sensorModelOptional.get();
                SensorTypeId sensorTypeId = sensorModel.getSensorTypeId();
                Optional<SensorType> sensorType = sensorTypeRepository.findByIdentity(sensorTypeId);
                if (sensorType.isPresent()) {
                    String sensorTypeName = sensorType.get().getSensorTypeName().getSensorTypeName();
                    List<Device> devices = devicesBySensorType.get(sensorTypeName);
                    Optional<Device> deviceOptional = deviceRepository.findByIdentity(sensor.getDeviceId());
                    if (deviceOptional.isPresent()&& devices.stream().noneMatch(d -> d.getIdentity().equals(deviceOptional.get().getIdentity()))) {
                        devices.add(deviceOptional.get());
                        devicesBySensorType.put(sensorTypeName, devices);
                    }
                }
            }
        }
        return devicesBySensorType;
    }

    /**
     * Removes empty entries from a map.
     *
     * @param devicesBySensorType The map to clear.
     * @return A map with empty entries removed.
     */
    private HashMap<String, List<Device>> clearHashMap(HashMap<String, List<Device>> devicesBySensorType) {
        HashMap<String, List<Device>> clearedMap = new HashMap<>();
        for (Map.Entry<String, List<Device>> entry : devicesBySensorType.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                clearedMap.put(entry.getKey(), entry.getValue());
            }
        }
        return clearedMap;
    }

    /**
     * Retrieves a device by its ID.
     * @param deviceId the ID of the device to retrieve
     * @return the device with the specified ID
     */
    @Override
    public Optional<Device> getDeviceById(DeviceId deviceId) {
        return deviceRepository.findByIdentity(deviceId);
    }

    /**
     * Retrieves all device IDs in the repository.
     * @return an iterable collection of all device IDs
     */
    @Override
    public List<DeviceId> findDeviceIds() {
        Iterable<DeviceId> deviceIds = deviceRepository.findDeviceIds();
        List<DeviceId> deviceIdList = new ArrayList<>();
        deviceIds.forEach(deviceIdList::add);
        return deviceIdList;
    }
}
