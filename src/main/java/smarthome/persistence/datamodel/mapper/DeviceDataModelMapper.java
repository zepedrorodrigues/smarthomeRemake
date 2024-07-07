package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.device.Device;
import smarthome.domain.device.DeviceFactory;
import smarthome.domain.device.vo.DeviceId;
import smarthome.domain.device.vo.DeviceName;
import smarthome.domain.device.vo.DeviceStatus;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.domain.room.vo.RoomId;
import smarthome.persistence.datamodel.DeviceDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for DeviceDataModel
 * <p>
 * The DeviceDataModelMapper class is responsible for mapping between DeviceDataModel objects and
 * corresponding Device domain objects. It provides methods to convert DeviceDataModel instances
 * into Device instances, and also to perform operations such as grouping devices by sensor type.
 * <p>
 */
@Component
public class DeviceDataModelMapper {

    private final DeviceFactory deviceFactory;

    /**
     * Constructs a new DeviceDataModelMapper with the specified DeviceFactory.
     *
     * @param deviceFactory The DeviceFactory to use for creating Device instances.
     */
    public DeviceDataModelMapper(DeviceFactory deviceFactory) {
        this.deviceFactory = deviceFactory;
    }

    /**
     * Converts a DeviceDataModel object into a corresponding Device domain object.
     *
     * @param deviceDataModel The DeviceDataModel to convert.
     * @return The Device domain object created from the provided DeviceDataModel.
     */
    public Device toDeviceDomain(DeviceDataModel deviceDataModel) {
        DeviceId deviceId = new DeviceId(deviceDataModel.getDeviceId());
        DeviceName deviceName = new DeviceName(deviceDataModel.getDeviceName());
        DeviceTypeName deviceTypeName = new DeviceTypeName(deviceDataModel.getDeviceTypeName());
        RoomId roomIdentity = new RoomId(deviceDataModel.getRoomIdentity());
        DeviceStatus status = new DeviceStatus(deviceDataModel.getDeviceStatus());
        return deviceFactory.createDevice(deviceId, deviceName, deviceTypeName, roomIdentity, status);
    }

    /**
     * Converts a collection of DeviceDataModel objects into a collection of corresponding Device domain objects.
     *
     * @param devicesDataModel The collection of DeviceDataModel objects to convert.
     * @return An Iterable containing Device domain objects created from the provided DeviceDataModel objects.
     */
    public Iterable<Device> toDevicesDomain(Iterable<DeviceDataModel> devicesDataModel) {
        List<Device> devices = new ArrayList<>();

        for (DeviceDataModel deviceDataModel : devicesDataModel) {
            devices.add(toDeviceDomain(deviceDataModel));
        }
        return devices;
    }
}
