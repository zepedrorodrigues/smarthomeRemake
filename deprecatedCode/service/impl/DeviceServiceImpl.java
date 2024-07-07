package smarthome.service.impl;

import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceType;
import smarthome.domain.reading.Reading;
import smarthome.domain.reading.vo.TimeStamp;
import smarthome.domain.repository.IDeviceRepository;
import smarthome.domain.repository.IReadingRepository;
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
import smarthome.mapper.DeviceDTO;
import smarthome.mapper.PeriodDTO;
import smarthome.mapper.ReadingDTO;
import smarthome.mapper.RoomDTO;
import smarthome.mapper.mapper.DeviceMapper;
import smarthome.mapper.mapper.PeriodMapper;
import smarthome.mapper.mapper.ReadingMapper;
import smarthome.mapper.mapper.RoomMapper;
import smarthome.service.IDeviceService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Provides implementation for the IDeviceService interface to manage devices within a smart home system. This service
 * is responsible for device management tasks such as adding, deactivating devices, and organizing devices based on
 * sensor types.
 */
public class DeviceServiceImpl implements IDeviceService {

    private final IDeviceRepository deviceRepository;
    private final ISensorRepository sensorRepository;
    private final ISensorModelRepository sensorModelRepository;
    private final ISensorTypeRepository sensorTypeRepository;
    private final DeviceMapper deviceMapper;
    private final DeviceFactory deviceFactory;
    private final RoomMapper roomMapper;
    private final IRoomRepository roomRepository;
    private final IReadingRepository readingRepository;
    private final PeriodMapper periodMapper;
    private final ReadingMapper readingMapper;

    /**
     * Constructs a new DeviceServiceImpl with necessary repositories and a device mapper.
     * <p>
     * Throws an IllegalArgumentException if any of the parameters are null.
     * </p>
     * @param deviceRepository      the repository for device data access
     * @param sensorRepository      the repository for sensor data access
     * @param sensorModelRepository the repository for sensor model data access
     * @param sensorTypeRepository  the repository for sensor type data access
     * @param deviceMapper          the mapper to convert devices to device DTOs
     * @param deviceFactory         the factory to create devices
     * @param roomMapper            the mapper to convert rooms to room DTOs
     * @param roomRepository        the repository for room data access
     * @param readingRepository     the repository for reading data access
     * @param periodMapper          the mapper to convert periods to period DTOs
     * @param readingMapper         the mapper to convert readings to reading DTOs
     * @throws IllegalArgumentException if any of the parameters are null
     */
    public DeviceServiceImpl(IDeviceRepository deviceRepository,
                             ISensorRepository sensorRepository,
                             ISensorModelRepository sensorModelRepository,
                             ISensorTypeRepository sensorTypeRepository,
                             DeviceMapper deviceMapper,
                             DeviceFactory deviceFactory,
                             RoomMapper roomMapper,
                             IRoomRepository roomRepository, IReadingRepository readingRepository,
                             PeriodMapper periodMapper, ReadingMapper readingMapper) {
        if (deviceRepository == null || sensorRepository == null || sensorModelRepository == null ||
            sensorTypeRepository == null || deviceMapper == null || deviceFactory == null || roomMapper == null ||
            roomRepository == null || readingRepository == null || periodMapper == null || readingMapper == null) {
            throw new IllegalArgumentException();
        }
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.sensorModelRepository = sensorModelRepository;
        this.sensorTypeRepository = sensorTypeRepository;
        this.deviceMapper = deviceMapper;
        this.deviceFactory = deviceFactory;
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
        this.readingRepository = readingRepository;
        this.periodMapper = periodMapper;
        this.readingMapper = readingMapper;
    }

    /**
     * Adds a device to a room based on the provided DeviceDTO.
     * @param deviceDTO the device data transfer object to add
     * @return the added DeviceDTO with updated information
     */
    @Override
    public DeviceDTO addDeviceToRoom(DeviceDTO deviceDTO) {
        try {
            DeviceName deviceName = deviceMapper.toDeviceName(deviceDTO);
            DeviceType deviceType = deviceMapper.toDeviceType(deviceDTO);
            RoomId roomId = deviceMapper.toRoomId(deviceDTO);
            if (!roomRepository.containsIdentity(roomId)) {
                return null;
            }
            Device device = deviceFactory.createDevice(deviceName, deviceType, roomId);
            Device savedDevice = deviceRepository.save(device);
            return deviceMapper.toDeviceDTO(savedDevice);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves all devices located in a specified room.
     * @param roomDTO the room data transfer object to query devices in
     * @return a list of DeviceDTOs that are located in the specified room
     */
    @Override
    public List<DeviceDTO> getDevicesInRoom(RoomDTO roomDTO) {
        try {
            RoomId roomId = roomMapper.toRoomId(roomDTO);
            if (!roomRepository.containsIdentity(roomId)) {
                return null;
            }
            Iterable<Device> devicesInRoom = deviceRepository.findByRoomIdentity(roomId);

            return deviceMapper.toDevicesDTO(devicesInRoom);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Deactivates a specified device.
     * @param deviceDTO the device data transfer object to deactivate
     * @return the deactivated DeviceDTO
     */
    @Override
    public DeviceDTO deactivateDevice(DeviceDTO deviceDTO) {
        try {
            DeviceId deviceId = deviceMapper.toDeviceId(deviceDTO);
            Optional<Device> deviceOptional = deviceRepository.getByIdentity(deviceId);
            if (deviceOptional.isPresent()) {
                Device device = deviceOptional.get();
                device.deactivateDevice();

                deviceRepository.update(device);

                return deviceMapper.toDeviceDTO(device);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves devices categorized by their sensor types.
     * @return a HashMap where each key is a sensor type name and the value is a set of DeviceDTOs that have sensors of
     * that type
     * @throws IllegalAccessException if there is an issue accessing sensor data
     */
    @Override
    public HashMap<String, Set<DeviceDTO>> getDevicesBySensorType() throws IllegalAccessException {
        Iterable<SensorType> sensorTypes = sensorTypeRepository.findAll();
        HashMap<String, Set<Device>> devicesBySensorType = new HashMap<>();
        for (SensorType sensorType : sensorTypes) {
            devicesBySensorType.put(sensorType.getSensorTypeName().getSensorTypeName(), new HashSet<>());
        }
        Iterable<Sensor> sensors = sensorRepository.findAll();
        HashMap<String, Set<Device>> devicesBySensor = fillMapWithDevices(devicesBySensorType, sensors);
        return deviceMapper.toMapDTO(clearHashMap(devicesBySensor));
    }

    /**
     * Retrieves all readings from a device in a given period.
     * @param deviceDTO the device dto object with the device id to retrieve readings from
     * @param periodDTO the period data transfer object to specify the period to retrieve readings from
     * @return a list of ReadingDTOs from the device in the specified period
     */
    @Override
    public List<ReadingDTO> getReadingsFromDeviceInAGivenPeriod(DeviceDTO deviceDTO, PeriodDTO periodDTO) {
        try {
            DeviceId deviceId = deviceMapper.toDeviceId(deviceDTO);
            TimeStamp start = periodMapper.toStart(periodDTO);
            TimeStamp end = periodMapper.toEnd(periodDTO);

            if (start.getValue().isAfter(end.getValue()) || start.getValue().isEqual(end.getValue()) ||
                end.getValue().isAfter(LocalDateTime.now())) {
                return null;
            }

            Iterable<Sensor> sensors = sensorRepository.getByDeviceIdentity(deviceId);

            if (isIterableEmpty(sensors)) {
                return null;
            }

            List<ReadingDTO> readingsDTO = new ArrayList<>();
            for (Sensor sensor : sensors) {
                Iterable<Reading> readingsBySensorId =
                        readingRepository.findBySensorIdInAGivenPeriod(sensor.getIdentity(),
                                start, end);
                readingsDTO.addAll(readingMapper.toReadingsDTO(readingsBySensorId));
            }
            return readingsDTO;

        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Helper method to fill a map with devices based on sensor associations.
     * @param map     the map to fill with devices keyed by sensor type names
     * @param sensors the iterable of sensors to process
     * @return the filled map
     * @throws IllegalAccessException if there is an issue accessing the data
     */
    private HashMap<String, Set<Device>> fillMapWithDevices(HashMap<String, Set<Device>> map,
                                                            Iterable<Sensor> sensors) throws IllegalAccessException {
        for (Sensor sensor : sensors) {
            SensorModelName sensorModelName = sensor.getSensorModelName();
            SensorModel sensorModel = sensorModelRepository.getByIdentity(sensorModelName).orElseThrow(
                    IllegalAccessException::new);
            SensorTypeId sensorTypeId = sensorModel.getSensorTypeId();
            SensorType sensorType = sensorTypeRepository.getByIdentity(sensorTypeId).orElseThrow(
                    IllegalAccessException::new);
            String sensorTypeName = sensorType.getSensorTypeName().getSensorTypeName();
            DeviceId deviceId = sensor.getDeviceId();
            Device device = deviceRepository.getByIdentity(deviceId).orElseThrow(IllegalAccessException::new);
            map.get(sensorTypeName).add(device);
        }
        return map;
    }

    /**
     * Helper method to clear empty entries from a map of devices categorized by sensor types.
     * @param map the map to clear of empty values
     * @return the cleared map
     */
    private HashMap<String, Set<Device>> clearHashMap(HashMap<String, Set<Device>> map) {
        HashMap<String, Set<Device>> clearedMap = new HashMap<>();
        for (Map.Entry<String, Set<Device>> entry : map.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                clearedMap.put(entry.getKey(), entry.getValue());
            }
        }
        return clearedMap;
    }

    /**
     * Helper method to check if an iterable is empty.
     * @param iterable the iterable to check
     * @return true if the iterable is empty, false otherwise
     */
    private boolean isIterableEmpty(Iterable<?> iterable) {
        return !iterable.iterator().hasNext();
    }
}
