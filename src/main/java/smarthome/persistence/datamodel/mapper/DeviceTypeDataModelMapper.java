package smarthome.persistence.datamodel.mapper;

import org.springframework.stereotype.Component;
import smarthome.domain.deviceType.DeviceType;
import smarthome.domain.deviceType.DeviceTypeFactory;
import smarthome.domain.deviceType.vo.DeviceTypeName;
import smarthome.persistence.datamodel.DeviceTypeDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for mapping between the DeviceType domain model and the DeviceTypeDataModel.
 * It uses the DeviceTypeFactory to create DeviceType domain models.
 */
@Component
public class DeviceTypeDataModelMapper {

    private final DeviceTypeFactory deviceTypeFactory;

    /**
     * Constructor for the DeviceTypeDataModelMapper class.
     *
     * @param deviceTypeFactory The factory to create DeviceType domain models.
     */
    public DeviceTypeDataModelMapper(DeviceTypeFactory deviceTypeFactory) {
        this.deviceTypeFactory = deviceTypeFactory;
    }

    /**
     * This method converts a DeviceTypeDataModel to a DeviceType domain model.
     *
     * @param deviceTypeDataModel The DeviceTypeDataModel to be converted.
     * @return The converted DeviceType domain model.
     */
    public DeviceType toDomain(DeviceTypeDataModel deviceTypeDataModel) {
        DeviceTypeName deviceTypeName = new DeviceTypeName(deviceTypeDataModel.getDeviceTypeName());
        return deviceTypeFactory.createDeviceType(deviceTypeName);
    }

    /**
     * This method converts an Iterable of DeviceTypeDataModels to an Iterable of DeviceType domain models.
     *
     * @param listDeviceTypeDataModel The Iterable of DeviceTypeDataModels to be converted.
     * @return The converted Iterable of DeviceType domain models.
     */
    public Iterable<DeviceType> toDomain(Iterable<DeviceTypeDataModel> listDeviceTypeDataModel) {
        List<DeviceType> listDomain = new ArrayList<>();
        for (DeviceTypeDataModel deviceTypeDataModel : listDeviceTypeDataModel) {
            listDomain.add(toDomain(deviceTypeDataModel));
        }
        return listDomain;
    }
}
